package com.wyl.home


import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.wyl.base.*
import com.wyl.base.activity.openArticleDetailActivity
import com.wyl.base.activity.openArticleTypeActivity
import com.wyl.base.bean.ArticleBean
import com.wyl.home.bean.FriendData
import com.wyl.home.bean.HotKeyData
import com.wyl.home.databinding.HomeFragmentBinding
import com.wyl.home.databinding.HomeItemBannerBinding
import com.wyl.home.databinding.HomeItemHotkeyBinding
import com.wyl.libbase.base.BindingFragment
import com.wyl.libbase.binding.recyclerview.RecyclerViewDivider
import com.wyl.libbase.binding.recyclerview.RecyclerViewSpace
import com.wyl.libbase.utils.openActivity
import com.wyl.libbase.utils.toast
import com.youth.banner.listener.OnBannerListener
import io.ditclear.bindingadapter.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.android.viewmodel.ext.android.viewModel

const val TYPE_BANNER = 3
const val TYPE_HOTKEY = 1
const val TYPE_ARTICLE = 0

@Route(path = HomeFragment)
class HomeFragment : BindingFragment<HomeFragmentBinding>(), ItemDecorator, ItemClickPresenter<Any> {
    val itemDecoration by lazy { RecyclerViewDivider(context!!, RecyclerViewDivider.BOTH) }

    val viewModel: HomeViewModel by viewModel()

    val layoutManager by lazy { LinearLayoutManager(context) }

    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun initView() {
        binding.vm = viewModel
        binding.recyclerView.layoutManager = layoutManager
    }

    override fun loadData() {
        binding.refreshLayout.startRefresh()

        viewModel.error.observe(this, Observer {
            context?.toast(it ?: "")
        })
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration = RecyclerViewSpace()

    override fun getAdapter(): RecyclerView.Adapter<*> =
        MultiTypeAdapter(
            context!!,
            viewModel.dataSource,
            object : MultiTypeAdapter.MultiViewTyper {
                override fun getViewType(item: Any): Int = when (item) {
                    is HomeViewModel.BannerItemViewModel -> TYPE_BANNER
                    is HomeViewModel.HotKeyItemViewModel -> TYPE_HOTKEY
                    else -> TYPE_ARTICLE
                }
            }).apply {
            addViewTypeToLayoutMap(TYPE_BANNER, R.layout.home_item_banner)
            addViewTypeToLayoutMap(TYPE_HOTKEY, R.layout.home_item_hotkey)
            addViewTypeToLayoutMap(TYPE_ARTICLE, R.layout.ui_item_article)
            itemDecorator = this@HomeFragment
            itemPresenter = this@HomeFragment
        }

    override fun decorator(holder: BindingViewHolder<ViewDataBinding>, position: Int, viewType: Int) {
        when (viewType) {
            TYPE_BANNER -> {
                val binding = holder.binding as HomeItemBannerBinding
                binding.listener = OnBannerListener {
                    val data = binding.item!!.data[it]
                    openArticleDetailActivity(data.url, id = data.id)
                }
            }
            TYPE_HOTKEY -> itemHotKeyInitView(holder.binding as HomeItemHotkeyBinding)
        }
    }

    override fun onItemClick(v: View, item: Any) {
        when (v.id) {
            R.id.iv_like -> {
                if (ACacheHelper.hasLogin()) {
                    val data = item as ArticleBean
                    if (data.collect) viewModel.unCollect(data) else viewModel.collect(data)
                } else {
                    openActivity(LoginActivity)
                }
            }
            R.id.layoutArticle -> {
                val bean = item as ArticleBean
                openArticleDetailActivity(bean.link, bean.title, bean.id, bean.author, bean.collect)
            }
            R.id.tv_chapter_name -> {
                val bean = item as ArticleBean
                openArticleTypeActivity(context!!, bean.chapterName, bean.chapterId)
            }
        }
    }

    private fun itemHotKeyInitView(binding: HomeItemHotkeyBinding) {
        binding.rvGrid.apply {
            layoutManager = GridLayoutManager(context, 3, RecyclerView.HORIZONTAL, false)
            removeItemDecoration(itemDecoration)
            addItemDecoration(itemDecoration)
            when (binding.item!!.title) {
                "热词搜索" -> {
                    adapter = SingleTypeAdapter(
                        context,
                        R.layout.home_item2_hotkey,
                        binding.item!!.hotKeyList
                    ).apply {
                        itemPresenter = object : ItemClickPresenter<HotKeyData> {
                            override fun onItemClick(v: View, item: HotKeyData) {
                                openActivity(SearchActivity) { withString("key", item.name) }
                            }
                        }
                    }
                }
                "常用网站" -> {
                    adapter = SingleTypeAdapter(
                        context,
                        R.layout.home_item2_friend,
                        binding.item!!.FriendList
                    ).apply {
                        itemPresenter = object : ItemClickPresenter<FriendData> {
                            override fun onItemClick(v: View, item: FriendData) {
                                openArticleDetailActivity(item.link, item.name)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        EventBus.getDefault().register(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe
    fun onEvent(event: String) {
        if (event == EVENT_LOGIN) {
            binding.refreshLayout.startRefresh()
        }
    }

}
