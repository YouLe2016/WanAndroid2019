package com.wyl.home

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import com.lzg.extend.BaseResponse
import com.lzg.extend.JsonConvert
import com.lzg.extend.toDisposables
import com.lzy.okgo.OkGo
import com.lzy.okrx2.adapter.ObservableBody
import com.wyl.common_lib.base.PageViewModel
import com.wyl.home.bean.ArticleData
import com.wyl.home.bean.BannerData
import com.wyl.home.bean.FriendData
import com.wyl.home.bean.HotKeyData

class HomeViewModel : PageViewModel() {
    val dataSource by lazy { ObservableArrayList<Any>() }

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
            }
            .subscribe({
                dataSource.add(bannerItemViewModel)
                dataSource.add(hotKeyItemViewModel)
                dataSource.add(friendItemViewModel)
                it.datas?.let { data ->
//                    data.flatMap { }
                    dataSource.addAll(data)
                }
                enableLoadMore.set(!it.over)
            }, {
                onError(it)
            })
            .toDisposables(disposables)
    }

    override fun loadMore() {
        loadArticleList()
            .doFinally { loadMore.set(false) }
            .doOnNext {
                page = it.curPage
            }
            .subscribe {
                it.datas?.let { data ->
                    dataSource.addAll(data)
                }
                enableLoadMore.set(!it.over)
            }
            .toDisposables(disposables)
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
        OkGo.get<BaseResponse<ArticleData>>("$HOME_ARTICLE_LIST$page/json")
            .converter(object : JsonConvert<BaseResponse<ArticleData>>() {})
            .adapt(ObservableBody<BaseResponse<ArticleData>>())
            .map { it.data }


    class BannerItemViewModel(data: List<BannerData>) : ViewModel() {
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
        val titles = ObservableArrayList<String>()

        init {
            data?.forEach {
                titles.add(it.name)
            }
            data2?.forEach {
                titles.add(it.name)
            }
        }
    }

}