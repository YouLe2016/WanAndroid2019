package com.wyl.home

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import com.lzg.extend.BaseResponse
import com.lzg.extend.JsonConvert
import com.lzg.extend.toDisposables
import com.lzy.okgo.OkGo
import com.lzy.okrx2.adapter.ObservableBody
import com.wyl.base.HOME_ARTICLE_LIST
import com.wyl.base.HOME_BANNER
import com.wyl.base.HOME_FRIEND
import com.wyl.base.HOME_HOTKEY
import com.wyl.base.bean.ArticleBean
import com.wyl.base.bean.ArticleListData
import com.wyl.base.repository.ArticleRepository
import com.wyl.home.bean.BannerData
import com.wyl.home.bean.FriendData
import com.wyl.home.bean.HotKeyData
import com.wyl.libbase.base.PageViewModel

class HomeViewModel(private val repository: ArticleRepository) : PageViewModel() {
    val dataSource = ObservableArrayList<Any>()

    private var bannerItemViewModel: BannerItemViewModel? = null
    private var hotKeyItemViewModel: HotKeyItemViewModel? = null
    private var friendItemViewModel: HotKeyItemViewModel? = null

    private var page = 0

    override fun refresh() {
        page = 0
        loadBannerData()
            .flatMap { loadHotKeyData() }
            .flatMap { loadFriendData() }
            .flatMap { loadArticleList() }
            .doFinally { refresh.set(false) }
            .doOnNext {
                dataSource.clear()
                page = it.curPage
            }.subscribe({
                dataSource.add(bannerItemViewModel)
                dataSource.add(hotKeyItemViewModel)
                dataSource.add(friendItemViewModel)
                it.datas?.let { data ->
                    // data.flatMap { }
                    dataSource.addAll(data)
                }
                enableLoadMore.set(!it.over)
            }, {
                onError(it)
            }).toDisposables(disposables)
    }

    override fun loadMore() {
        loadArticleList()
            .doFinally { loadMore.set(false) }
            .doOnNext {
                page = it.curPage
            }.subscribe {
                it.datas?.let { data ->
                    dataSource.addAll(data)
                }
                enableLoadMore.set(!it.over)
            }.toDisposables(disposables)
    }

    private fun loadBannerData() =
        OkGo.get<BaseResponse<List<BannerData>>>(HOME_BANNER)
            .converter(object : JsonConvert<BaseResponse<List<BannerData>>>() {})
            .adapt(ObservableBody<BaseResponse<List<BannerData>>>())
            .map {
                val data = it.data
                bannerItemViewModel = BannerItemViewModel(data)
            }

    private fun loadHotKeyData() =
        OkGo.get<BaseResponse<List<HotKeyData>>>(HOME_HOTKEY)
            .converter(object : JsonConvert<BaseResponse<List<HotKeyData>>>() {})
            .adapt(ObservableBody<BaseResponse<List<HotKeyData>>>())
            .map { hotKeyItemViewModel = HotKeyItemViewModel("热词搜索", data = it.data) }

    private fun loadFriendData() =
        OkGo.get<BaseResponse<List<FriendData>>>(HOME_FRIEND)
            .converter(object : JsonConvert<BaseResponse<List<FriendData>>>() {})
            .adapt(ObservableBody<BaseResponse<List<FriendData>>>())
            .map { friendItemViewModel = HotKeyItemViewModel("常用网站", data2 = it.data) }

    private fun loadArticleList() =
        OkGo.get<BaseResponse<ArticleListData>>("$HOME_ARTICLE_LIST$page/json")
            .converter(object : JsonConvert<BaseResponse<ArticleListData>>() {})
            .adapt(ObservableBody<BaseResponse<ArticleListData>>())
            .map { it.data }

    /** 收藏文章 */
    fun collect(data: ArticleBean) {
        if (data.id == -1) {
            repository.collectOut(data.title, data.author, data.link)
        } else {
            repository.collectIn(data.id)
        }.doOnSubscribe { loading.set(true) }
            .doFinally { loading.set(false) }
            .subscribe({
                data.collect = !data.collect
                error.value = "收藏成功"
            }, {
                onError(it)
            }).toDisposables(disposables)
    }


    /** 取消收藏 */
    fun unCollect(data: ArticleBean) {
        repository.unCollectHome(data.id)
            .doOnSubscribe { loading.set(true) }
            .doFinally { loading.set(false) }
            .subscribe({
                data.collect = !data.collect
                error.value = "取消收藏"
            }, {
                onError(it)
            }).toDisposables(disposables)
    }


    class BannerItemViewModel(val data: List<BannerData>) : ViewModel() {
        val urls: MutableList<String> = mutableListOf()
        val titles: MutableList<String> = mutableListOf()

        init {
            data.forEach {
                urls.add(it.imagePath)
                titles.add(it.title)
            }
        }
    }

    /**
     * 热词和常用网站通用数据模型
     */
    class HotKeyItemViewModel(
        val title: String,
        data: List<HotKeyData>? = null,
        data2: List<FriendData>? = null
    ) {
        val hotKeyList = ObservableArrayList<HotKeyData>()
        val FriendList = ObservableArrayList<FriendData>()

        init {
            data?.let {
                hotKeyList.addAll(it)
            }
            data2?.let {
                FriendList.addAll(it)
            }
        }
    }

}