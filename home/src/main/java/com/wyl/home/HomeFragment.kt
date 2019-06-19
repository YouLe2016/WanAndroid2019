package com.wyl.home


import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wyl.common_lib.base.BindingFragment
import com.wyl.common_lib.binding.recyclerview.RecyclerViewDivider
import com.wyl.common_lib.binding.recyclerview.RecyclerViewSpace
import com.wyl.common_lib.utils.toast
import com.wyl.common_ui.ACacheHelper
import com.wyl.home.bean.ArticleData
import com.wyl.home.databinding.HomeFragmentBinding
import com.wyl.home.databinding.HomeItemBannerBinding
import com.wyl.home.databinding.HomeItemHotkeyBinding
import com.youth.banner.listener.OnBannerListener
import io.ditclear.bindingadapter.*
import org.koin.android.viewmodel.ext.android.viewModel

const val TYPE_BANNER = 3
const val TYPE_HOTKEY = 1
const val TYPE_FRIEND = TYPE_HOTKEY

class HomeFragment : BindingFragment<HomeFragmentBinding>() {

    val itemDecoration by lazy { RecyclerViewDivider(context!!, RecyclerViewDivider.BOTH) }

    val viewModel: HomeViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun initView() {
        binding.vm = viewModel
    }

    override fun loadData() {
        binding.refreshLayout.startRefresh()

        viewModel.error.observe(this, Observer {
            context?.toast(it ?: "")
        })
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration = RecyclerViewSpace()

    override fun getAdapter(): RecyclerView.Adapter<*> =
        MultiTypeAdapter(context!!, viewModel.dataSource, object : MultiTypeAdapter.MultiViewTyper {
            override fun getViewType(item: Any): Int = when (item) {
                is HomeViewModel.BannerItemViewModel -> TYPE_BANNER
                is HomeViewModel.HotKeyItemViewModel -> TYPE_HOTKEY
                else -> 0
            }
        }).apply {
            addViewTypeToLayoutMap(TYPE_BANNER, R.layout.home_item_banner)
            addViewTypeToLayoutMap(TYPE_HOTKEY, R.layout.home_item_hotkey)
            addViewTypeToLayoutMap(TYPE_FRIEND, R.layout.home_item_hotkey)
            addViewTypeToLayoutMap(0, R.layout.home_item_article)
            itemDecorator = object : ItemDecorator {
                override fun decorator(
                    holder: BindingViewHolder<ViewDataBinding>,
                    position: Int,
                    viewType: Int
                ) = when (viewType) {
                    TYPE_BANNER -> {
                        val binding = holder.binding as HomeItemBannerBinding
                        binding.listener = OnBannerListener {
                            context?.toast(binding.item!!.titles[it])
                        }
                    }
                    TYPE_HOTKEY -> itemHotKeyInitView(holder.binding as HomeItemHotkeyBinding)
                    else -> {
                        itemPresenter = object : ItemClickPresenter<Any> {
                            override fun onItemClick(v: View, item: Any) {
                                when (v.id) {
                                    R.id.iv_like -> {
                                        if (ACacheHelper.hasLogin()) {
                                            val data = item as ArticleData.DatasBean
                                            data.collect = !data.collect
                                        } else {

                                        }
                                    }
                                    else -> {
                                    }
                                }
                            }
                        }
                    }
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
