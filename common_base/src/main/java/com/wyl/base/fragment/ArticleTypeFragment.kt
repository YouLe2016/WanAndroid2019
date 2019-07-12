package com.wyl.base.fragment

import android.arch.lifecycle.Observer
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wyl.base.*
import com.wyl.base.activity.openArticleDetailActivity
import com.wyl.base.bean.ArticleBean
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
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

fun getArticleTypeFragment(id: Int = -1, key: String = ""): ArticleTypeFragment {
    val fragment = ArticleTypeFragment()
    fragment.arguments = Bundle().apply {
        if (id != -1) putInt("id", id)
        if (key.isNotEmpty()) putString("key", key)
    }
    return fragment
}

class ArticleTypeFragment : BindingFragment<UiListFragmentBinding>(), ItemClickPresenter<ArticleBean>, ItemDecorator {

    private val mId by lazy { autoWired("id", -1) }
    private val mKey by lazy { autoWired("key", "") }

    private val articleViewModel: ArticleTypeViewModel by viewModel { parametersOf(mId, mKey) }
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
            if (mId != -1) itemDecorator = this@ArticleTypeFragment
        }
    }

    override fun onItemClick(v: View, item: ArticleBean) {
        when (v.id) {
            R.id.iv_like -> {
                if (ACacheHelper.hasLogin()) {
                    if (item.collect) commonViewModel.unCollect(item) else commonViewModel.collect(item)
                } else {
                    openActivity(LoginActivity)
                }
            }
            R.id.layoutArticle -> {
                openArticleDetailActivity(item.link, item.title, item.id, item.author, item.collect)
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

        commonViewModel.success.observe(this, Observer {
            context?.toast(it ?: "")
        })

        commonViewModel.error.observe(this, Observer {
            context?.toast(it ?: "")
        })

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