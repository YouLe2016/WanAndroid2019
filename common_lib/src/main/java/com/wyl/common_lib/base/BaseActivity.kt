package com.wyl.common_lib.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseActivity : AppCompatActivity(), Presenter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        initView()
        loadData()
    }

    abstract fun setContentView()

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun loadData()

    override fun onClick(v: View) {}

    override fun getItemDecoration(): RecyclerView.ItemDecoration? = null

    override fun getAdapter(): RecyclerView.Adapter<*>? = null


}