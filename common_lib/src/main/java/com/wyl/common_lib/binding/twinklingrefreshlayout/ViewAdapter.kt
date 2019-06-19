package com.wyl.common_lib.binding.twinklingrefreshlayout

import android.databinding.BindingAdapter
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout


@BindingAdapter("refresh")
fun onRefreshFinish(layout: TwinklingRefreshLayout, refresh: Boolean) {
    if (!refresh) layout.finishRefreshing()
}

@BindingAdapter("loadMore")
fun onLoadMoreFinish(layout: TwinklingRefreshLayout, loadMore: Boolean) {
    if (!loadMore) layout.finishLoadmore()
}

@BindingAdapter("refreshListener")
fun setOnLoadListener(layout: TwinklingRefreshLayout, refreshListener: RefreshListenerAdapter?) {
    layout.setOnRefreshListener(refreshListener)
}

@BindingAdapter("enableLoadMore")
fun enableLoadMore(layout: TwinklingRefreshLayout, enableLoadMore: Boolean) =
    layout.setEnableLoadmore(enableLoadMore)

@BindingAdapter("enableRefresh")
fun enableRefresh(layout: TwinklingRefreshLayout, enableRefresh: Boolean) {
    layout.setEnableRefresh(enableRefresh)
}





