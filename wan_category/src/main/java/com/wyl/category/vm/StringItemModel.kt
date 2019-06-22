package com.wyl.category.vm

import android.databinding.ObservableBoolean
import android.databinding.ObservableField

open class StringItemModel(
    var id: Int,
    content: String,
    /**
     * 在关联侧对应的position
     */
    var position: Int
) {
    var content = ObservableField(content)
    var checked = ObservableBoolean()
}