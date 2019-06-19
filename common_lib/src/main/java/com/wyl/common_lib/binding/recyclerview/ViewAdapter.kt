package com.wyl.common_lib.binding.recyclerview

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView

@BindingAdapter("itemDecoration")
fun addItemDecoration(recyclerView: RecyclerView, itemDecoration: RecyclerView.ItemDecoration) {
    recyclerView.addItemDecoration(itemDecoration)
}

