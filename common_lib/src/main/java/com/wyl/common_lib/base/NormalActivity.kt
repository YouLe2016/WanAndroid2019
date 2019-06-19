package com.wyl.common_lib.base

abstract class NormalActivity : BaseActivity() {
    override fun setContentView() {
        setContentView(getLayoutId())
    }
}