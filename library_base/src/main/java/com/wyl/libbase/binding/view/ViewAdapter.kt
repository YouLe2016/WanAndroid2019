package com.wyl.libbase.binding.view

import android.databinding.BindingAdapter
import android.view.View


@BindingAdapter("selected")
fun isSelected(view: View, isSelected: Boolean) {
    view.isSelected = isSelected
}






