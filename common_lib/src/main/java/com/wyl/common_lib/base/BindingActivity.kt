package com.wyl.common_lib.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import com.wyl.common_lib.BR

abstract class BindingActivity<VB : ViewDataBinding> : BaseActivity() {
    protected lateinit var binding: VB

    override fun setContentView() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.setVariable(BR.presenter, this)
        binding.executePendingBindings()
        binding.lifecycleOwner = this
    }
}