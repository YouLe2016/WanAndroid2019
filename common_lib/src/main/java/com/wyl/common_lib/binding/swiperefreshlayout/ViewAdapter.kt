package com.wyl.common_lib.binding.swiperefreshlayout

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout


@BindingAdapter("isRefresh")
fun isRefresh(layout: SwipeRefreshLayout, isRefresh: Boolean) {
    layout.isRefreshing = isRefresh
}

@BindingAdapter("onRefreshListener")
fun setOnRefreshLisner(layout: SwipeRefreshLayout, onRefreshListener: SwipeRefreshLayout.OnRefreshListener) =
    layout.setOnRefreshListener(onRefreshListener)





