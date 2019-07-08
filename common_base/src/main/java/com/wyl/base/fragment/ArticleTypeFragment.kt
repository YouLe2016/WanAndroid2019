package com.wyl.base.fragment


import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wyl.base.ACacheHelper
import com.wyl.base.CommonViewModel
import com.wyl.base.LoginActivity
import com.wyl.base.R
import com.wyl.base.activity.openArticleDetailActivity
import com.wyl.base.bean.ArticleData
import com.wyl.base.databinding.UiItemArticleBinding
import com.wyl.libbase.base.BindingFragment
import com.wyl.libbase.binding.recyclerview.RecyclerViewSpace
import com.wyl.libbase.databinding.UiListFragmentBinding
import com.wyl.libbase.utils.autoWired
import com.wyl.libbase.utils.openActivity
import com.wyl.libbase.utils.toast
import io.ditclear.bindingadapter.BindingViewHolder
import io.ditclear.bindingadapter.ItemClickPresenter
import io.ditclear.bindingadapter.ItemDecorator
import io.ditclear.bindingadapter.SingleTypeAdapter
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ArticleTypeFragment : BindingFragment<UiListFragmentBinding>(), ItemClickPresenter<ArticleData>, ItemDecorator {

    private val articleViewModel: ArticleTypeViewModel by viewModel { parametersOf(autoWired("id", -1)) }
    private val commonViewModel: CommonViewModel by viewModel()

    private val mItemDecoration by lazy { RecyclerViewSpace() }

    private val mLayoutManager by lazy { LinearLayoutManager(context) }

    private val mAdapter by lazy {
        SingleTypeAdapter(
            binding.recyclerView.context,
            R.layout.ui_item_article,
            articleViewModel.dataSource
        ).apply {
            itemPresenter = this@ArticleTypeFragment
            itemDecorator = this@ArticleTypeFragment
        }
    }

    override fun onItemClick(v: View, item: ArticleData) {
        when (v.id) {
            R.id.iv_like -> {
                if (ACacheHelper.hasLogin()) {
                    if (item.collect) commonViewModel.unCollect(item) else commonViewModel.collect(item)
                } else {
                    openActivity(LoginActivity)
                }
            }
            R.id.layoutArticle -> {
                val bean = item as ArticleData
                openArticleDetailActivity(bean.link, bean.title, bean.id, bean.author, bean.collect)
            }
        }
    }

    override fun decorator(holder: BindingViewHolder<ViewDataBinding>, position: Int, viewType: Int) {
        val binding = holder.binding as UiItemArticleBinding
        binding.tvChapterName.visibility = View.GONE
    }

    override fun getLayoutId() = R.layout.ui_list_fragment

    override fun initView() {
        binding.vm = articleViewModel
        binding.recyclerView.apply {
            addItemDecoration(mItemDecoration)
            layoutManager = mLayoutManager
            adapter = mAdapter
        }
    }

    override fun loadData() {
        binding.refreshLayout.startRefresh()

        commonViewModel.error.observe(this, Observer {
            context?.toast(it ?: "")
        })
    }


}