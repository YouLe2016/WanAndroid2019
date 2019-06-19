package com.wyl.common_lib.base

import android.support.v7.widget.RecyclerView
import android.view.View

interface Presenter : View.OnClickListener {

    fun getAdapter(): RecyclerView.Adapter<*>?

    fun getItemDecoration(): RecyclerView.ItemDecoration?

}