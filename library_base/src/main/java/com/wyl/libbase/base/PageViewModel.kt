package com.wyl.libbase.base

import android.databinding.ObservableBoolean
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import com.wyl.libbase.utils.logD


abstract class PageViewModel : BaseViewModel() {

    val loadListener by lazy {
        object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout) {
                refresh.set(true)
                refresh()
//                Handler().postDelayed({ refreshLayout.finishRefreshing() }, 2000)
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout) {
                loadMore.set(true)
                loadMore()
//                Handler().postDelayed({ refreshLayout.finishRefreshing() }, 2000)
            }

            override fun onRefreshCanceled() {
                logD("onRefreshCanceled")
            }

            override fun onFinishRefresh() {
                logD("onFinishRefresh")
            }

        }

    }

    val enableLoadMore = ObservableBoolean(true)

    /**
     * 是否正在刷新
     */
    val refresh = ObservableBoolean(false)

    /**
     * 是否正在加载更多
     */
    val loadMore = ObservableBoolean(false)

    /**
     * 是否展示空页面
     */
    val empty = ObservableBoolean(false)

    abstract fun refresh()

    abstract fun loadMore()
}