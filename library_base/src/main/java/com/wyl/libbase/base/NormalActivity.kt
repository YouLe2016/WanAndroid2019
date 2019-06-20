package com.wyl.libbase.base

abstract class NormalActivity : BaseActivity() {
    override fun setContentView() {
        setContentView(getLayoutId())
    }
}