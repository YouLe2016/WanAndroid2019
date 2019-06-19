package com.wyl.common_lib.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wyl.common_lib.BR

abstract class BindingFragment<VB : ViewDataBinding> : BaseFragment() {
    protected lateinit var binding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.setVariable(BR.presenter, this)
        binding.executePendingBindings()
        binding.lifecycleOwner = this
        return binding.root
    }
}