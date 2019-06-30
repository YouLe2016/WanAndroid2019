package com.wyl.category.category

import android.databinding.ObservableArrayList
import com.wyl.category.bean.CategoryBean
import com.wyl.category.vm.ChildItemModel
import com.wyl.category.vm.GroupItemModel
import com.wyl.libbase.base.BaseViewModel

class CategoryChildViewModel : BaseViewModel() {
    val dataSource = ObservableArrayList<Any>()

//    /**
//     * 模拟数据
//     */
//    fun initData() {
//        for (i in 0..108) {
//            if (i % 5 == 0) {
//                // group
//                val element = GroupItemModel("第 ${i / 5 + 1} 组")
//                element.position = i / 5
//                dataSource.add(element)
//            } else {
//                // child
//                val element = ChildItemModel("成员$i")
//                element.position = i / 5
//                dataSource.add(element)
//            }
//        }
//    }

    fun initData(list: List<CategoryBean>) {
        var position = 0
        list.forEach {
            val groupItem = GroupItemModel(it, position++)
            dataSource.add(groupItem)
            it.children?.forEach { child ->
                val childItem = ChildItemModel(child, position)
                dataSource.add(childItem)
            }
        }
    }

}

