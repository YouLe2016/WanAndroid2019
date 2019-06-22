package com.wyl.libbase

import android.support.v4.app.Fragment
import com.wyl.libbase.base.NormalActivity
import com.wyl.libbase.utils.transact

abstract class DebugActivity : NormalActivity() {

    override fun getLayoutId(): Int = R.layout.debug_activity

    override fun initView() {
        transact { replace(R.id.frameLayout, createFragment()) }
    }

    abstract fun createFragment(): Fragment


    override fun loadData() {

    }
}
