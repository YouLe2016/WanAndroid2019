package com.wyl.category.category


//import android.support.v7.widget.GridLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.view.View
//import com.wyl.category.R
//import com.wyl.category.databinding.CategoryChildFragmentBinding
//import com.wyl.category.category.CategoryChildViewModel
//import com.wyl.category.vm.GroupItemModel
//import com.wyl.libbase.base.BindingFragment
//import com.wyl.libbase.utils.KLog
//import io.ditclear.bindingadapter.ItemClickPresenter
//import io.ditclear.bindingadapter.MultiTypeAdapter
//import org.koin.android.viewmodel.ext.android.viewModel
//
//const val TYPE_GROUP = 3
//const val TYPE_CHILD = 1
//
///**
// * 分类右侧页面
// */
//class CategoryChildFragment : BindingFragment<CategoryChildFragmentBinding>(), ItemClickPresenter<Any> {
//    private val viewModel: CategoryChildViewModel by viewModel()
//
//    private val mAdapter by lazy {
//        MultiTypeAdapter(binding.recyclerView.context, viewModel.dataSource, object : MultiTypeAdapter.MultiViewTyper {
//            override fun getViewType(item: Any): Int = when (item) {
//                is GroupItemModel -> TYPE_GROUP
//                else -> TYPE_CHILD
//            }
//        }).apply {
//            addViewTypeToLayoutMap(TYPE_GROUP, R.layout.category_item_group)
//            addViewTypeToLayoutMap(TYPE_CHILD, R.layout.category_item_child)
//            itemPresenter = this@CategoryChildFragment
//        }
//    }
//
//    private val mGridLayoutManager by lazy {
//        GridLayoutManager(context, 3).apply {
//            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                override fun getSpanSize(position: Int): Int = mAdapter.getItemViewType(position)
//            }
//        }
//    }
//
//    private val mScrollListener = object : RecyclerView.OnScrollListener() {
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            if (isMove) {
//                isMove = false
//                moveToPosition(mIndex)
//            }
//        }
//    }
//
//    override fun initView() {
//        binding.recyclerView.apply {
//            layoutManager = mGridLayoutManager
//            adapter = mAdapter
//            removeOnScrollListener(mScrollListener)
//            addOnScrollListener(mScrollListener)
//        }
//    }
//
//    override fun getLayoutId() = R.layout.category_child_fragment
//
//    override fun onItemClick(v: View, item: Any) {
//
//    }
//
//    override fun loadData() {
//        viewModel.loadData()
//    }
//
//    /**
//     * 由点击行为产生的滚动，解决RecyclerView无法将目标position滚动到顶部所需要的变量
//     */
//    private var isMove = false
//    /**
//     * 商品需要滚动的位置
//     */
//    private var mIndex = 0
//
//    fun moveToPosition(position: Int) {
////        mGridLayoutManager.scrollToPositionWithOffset(position, 0)
//
//        mIndex = position
//        val firstItem = mGridLayoutManager.findFirstVisibleItemPosition()
//        val lastItem = mGridLayoutManager.findLastVisibleItemPosition()
//        KLog.d("position=$position firstItem=$firstItem lastItem=$lastItem ")
//
//        if (position in (firstItem + 1)..lastItem) {
//            val top = binding.recyclerView.getChildAt(position - firstItem).top
//            binding.recyclerView.scrollBy(0, top)
//        } else {
//            isMove = true
//            binding.recyclerView.scrollToPosition(position)
//        }
//    }
//
//
//}
