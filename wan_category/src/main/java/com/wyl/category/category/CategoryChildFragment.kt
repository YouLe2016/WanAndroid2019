package com.wyl.category.category


import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wyl.category.R
import com.wyl.category.databinding.CategoryChildFragmentBinding
import com.wyl.category.vm.GroupItemModel
import com.wyl.category.vm.StringItemModel
import com.wyl.libbase.base.BindingFragment
import com.wyl.libbase.utils.KLog
import com.wyl.libbase.utils.autoWired
import io.ditclear.bindingadapter.ItemClickPresenter
import io.ditclear.bindingadapter.MultiTypeAdapter
import org.koin.android.viewmodel.ext.android.viewModel

const val TYPE_GROUP = 3
const val TYPE_CHILD = 1

/**
 * 分类右侧页面
 */
class CategoryChildFragment : BindingFragment<CategoryChildFragmentBinding>(), ItemClickPresenter<Any> {
    private val viewModel: CategoryChildViewModel by viewModel()

    private val mAdapter by lazy {
        MultiTypeAdapter(
            binding.recyclerView.context,
            viewModel.dataSource,
            object : MultiTypeAdapter.MultiViewTyper {
                override fun getViewType(item: Any): Int = when (item) {
                    is GroupItemModel -> TYPE_GROUP
                    else -> TYPE_CHILD
                }
            }).apply {
            addViewTypeToLayoutMap(TYPE_GROUP, R.layout.category_item_group)
            addViewTypeToLayoutMap(TYPE_CHILD, R.layout.category_item_child)
            itemPresenter = this@CategoryChildFragment
        }
    }

    private val mGridLayoutManager by lazy {
        GridLayoutManager(context, 3).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = mAdapter.getItemViewType(position)
            }
        }
    }

    /**
     * 用户滑动才触发左侧tab联动
     */
    private var isUserScroll = false

    private val mScrollListener = object : RecyclerView.OnScrollListener() {
        /**
         * RecyclerView滚动时回调
         *
         * 以RecyclerView本身为参考系
         *
         * [dx]: x方向上的滚动量   右+左-
         *
         * [dy]: y方向上的滚动量   下+上-
         *
         * LayoutManager scrollTo系列方法: dx = [0], dy = [0], 只触发一次
         */
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            KLog.d("dx = [$dx], dy = [$dy], isUserScroll = $isUserScroll")
            if (isUserScroll) {
                val position = mGridLayoutManager.findFirstVisibleItemPosition()
                (parentFragment as CategoryFragment).checked(position = (viewModel.dataSource[position] as StringItemModel).position)
            }
        }

        /**
         * RecyclerView滚动状态发生改变时回调，
         *
         * RecyclerView/LayoutManager scrollTo系列方法不触发该回调
         * RecyclerView/LayoutManager smoothScrollTo系列方法触发该回调
         *
         *  [newState]:
         *  SCROLL_STATE_IDLE = 0; 空闲状态
         *  SCROLL_STATE_DRAGGING = 1; 拖动
         * SCROLL_STATE_SETTLING = 2; 滑动
         */
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            KLog.d("newState = [${newState}]")
            isUserScroll = newState != 0
        }
    }

    override fun initView() {
        binding.recyclerView.apply {
            layoutManager = mGridLayoutManager
            adapter = mAdapter
            addOnScrollListener(mScrollListener)
        }
    }

    override fun getLayoutId() = R.layout.category_child_fragment

    override fun onItemClick(v: View, item: Any) {

    }

    private val type by lazy { autoWired("type", "")!! }

    override fun loadData() {
        val fragment = parentFragment as CategoryFragment
        if (type == "Navigation") {
            fragment.viewModel.loadNavData(viewModel)
        } else {
            fragment.viewModel.loadTreeData(viewModel)
        }
    }

    /**
     * 平滑滚动所使用的类
     */
    private val scroller by lazy {
        object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
    }

    fun moveToPosition(position: Int) {
        KLog.d("position = [$position]")
        mGridLayoutManager.scrollToPositionWithOffset(position, 0)
//        scroller.targetPosition = position
//        mGridLayoutManager.startSmoothScroll(scroller)

//        binding.recyclerView.scrollToPosition(position)
    }

}
