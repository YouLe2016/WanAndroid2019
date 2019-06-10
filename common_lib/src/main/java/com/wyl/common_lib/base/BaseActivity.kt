package com.wyl.common_lib.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.wyl.common_lib.inter.Presenter

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

}