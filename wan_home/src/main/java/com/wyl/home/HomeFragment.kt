package com.wyl.home


import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.wyl.base.ACacheHelper
import com.wyl.base.HomeFragment
import com.wyl.base.LoginActivity
import com.wyl.base.activity.openArticleDetailActivity
import com.wyl.home.bean.ArticleData
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
import org.koin.android.viewmodel.ext.android.viewModel

const val TYPE_BANNER = 3
const val TYPE_HOTKEY = 1
const val TYPE_ARTICLE = 0
const val TYPE_FRIEND = TYPE_HOTKEY

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
            addViewTypeToLayoutMap(TYPE_ARTICLE, R.layout.home_item_article)
            itemDecorator = this@HomeFragment
            itemPresenter = this@HomeFragment
        }

    override fun decorator(holder: BindingViewHolder<ViewDataBinding>, position: Int, viewType: Int) {
        when (viewType) {
            TYPE_BANNER -> {
                val binding = holder.binding as HomeItemBannerBinding
                binding.listener = OnBannerListener {
                    context?.toast(binding.item!!.titles[it])
                }
            }
            TYPE_HOTKEY -> itemHotKeyInitView(holder.binding as HomeItemHotkeyBinding)
        }
    }

    override fun onItemClick(v: View, item: Any) {
        when (v.id) {
            R.id.iv_like -> {
                if (ACacheHelper.hasLogin()) {
                    val data = item as ArticleData.DatasBean
                    if (data.collect) viewModel.unCollect(data) else viewModel.collect(data)
                } else {
                    openActivity(LoginActivity)
                }
            }
            R.id.layoutArticle -> {
                val bean = item as ArticleData.DatasBean
                openArticleDetailActivity(bean.link, bean.title, bean.id, bean.author)
            }
        }
    }

    private fun itemHotKeyInitView(binding: HomeItemHotkeyBinding) {
        binding.rvGrid.apply {
            layoutManager = GridLayoutManager(context, 3, RecyclerView.HORIZONTAL, false)
            removeItemDecoration(itemDecoration)
            addItemDecoration(itemDecoration)
            adapter = SingleTypeAdapter(
                context,
                R.layout.home_item2_hotkey,
                binding.item!!.titles
            ).apply {
                itemPresenter = object : ItemClickPresenter<String> {
                    override fun onItemClick(v: View, item: String) {
                        context?.toast(item)
                    }
                }
            }
        }
    }

}
