package com.wyl.category.vm

import android.databinding.ObservableArrayList
import com.lzg.extend.BaseResponse
import com.lzg.extend.JsonConvert
import com.lzg.extend.toDisposables
import com.lzy.okgo.OkGo
import com.lzy.okrx2.adapter.ObservableBody
import com.wyl.base.TREE_CATEGORY
import com.wyl.category.bean.CategoryBean
import com.wyl.libbase.base.BaseViewModel

class CategoryViewModel : BaseViewModel() {
    val leftData = ObservableArrayList<LeftItemModel>()

//    /**
//     * 模拟数据
//     */
//    fun loadData() {
//        repeat(21) {
//            val item = LeftItemModel((it + 1).toString())
//            item.position = it * 5
//            leftData.add(item)
//        }
//    }

    fun loadData(childViewModel: CategoryChildViewModel) {
        OkGo.get<BaseResponse<List<CategoryBean>>>(TREE_CATEGORY)
            .converter(object : JsonConvert<BaseResponse<List<CategoryBean>>>() {})
            .adapt(ObservableBody<BaseResponse<List<CategoryBean>>>())
            .map { it.data }
            .doOnSubscribe { loading.set(true) }
            .doFinally { loading.set(false) }
            .subscribe({ list ->
                var position = 0
                list.mapTo(leftData) {
                    val model = LeftItemModel(it, position++)
                    position += it.children?.size ?: 0
                    model
                }
                childViewModel.initData(list)
            }, {
                onError(it)
            }).toDisposables(disposables)
    }

    /**
     * 为了减少遍历leftData和修改选中的次数
     */
    private var currentItem: LeftItemModel? = null
    private var currentPosition = -1

    fun checked(item: LeftItemModel? = null, position: Int = -1) {
        if (item != null && currentItem != item) {
            currentItem = item
            currentPosition = leftData.indexOf(item)
            leftData.forEach { it.checked.set(false) }
            item.checked.set(true)
        } else if (position != -1 && currentPosition != position) {
            currentPosition = position
            currentItem = leftData[position]
            leftData.forEach { it.checked.set(false) }
            currentItem!!.checked.set(true)
        }
    }

}
