package com.wyl.category.category


import android.content.Context
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.wyl.base.CategoryFragment
import com.wyl.category.R
import com.wyl.category.databinding.CategoryFragmentBinding
import com.wyl.category.vm.CategoryChildViewModel
import com.wyl.category.vm.CategoryViewModel
import com.wyl.category.vm.LeftItemModel
import com.wyl.libbase.base.BindingFragment
import com.wyl.libbase.binding.recyclerview.RecyclerViewDivider
import com.wyl.libbase.utils.KLog
import com.wyl.libbase.utils.transact
import io.ditclear.bindingadapter.ItemClickPresenter
import io.ditclear.bindingadapter.SingleTypeAdapter
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * 体系和导航页面
 */
@Route(path = CategoryFragment)
class CategoryFragment : BindingFragment<CategoryFragmentBinding>(), ItemClickPresenter<LeftItemModel> {
    private val viewModel: CategoryViewModel by viewModel()
    private val childViewModel: CategoryChildViewModel by sharedViewModel()

    private val childFragment = CategoryChildFragment()

    override fun onItemClick(v: View, item: LeftItemModel) {
        checked(item = item)
        childFragment.moveToPosition(item.position)
    }

    override fun getLayoutId(): Int = R.layout.category_fragment

    override fun initView() {
        transact { replace(R.id.frameLayout, childFragment) }

        binding.recyclerView.apply {
            addItemDecoration(RecyclerViewDivider(context, RecyclerViewDivider.VERTICAL))
            adapter = SingleTypeAdapter(context, R.layout.category_item_left, viewModel.leftData)
                .apply {
                    itemPresenter = this@CategoryFragment
                }
        }
    }


    override fun loadData() {
        viewModel.loadData(childViewModel)
    }

    fun checked(item: LeftItemModel? = null, position: Int = -1) {
        viewModel.checked(item, position)
        if (position != -1) {
            binding.recyclerView.smoothScrollToPosition(position)
        }
    }

}
