package com.wyl.login

import android.arch.lifecycle.Observer
import com.wyl.common_lib.base.BindingActivity
import com.wyl.common_lib.utils.toast
import com.wyl.login.databinding.LoginActivityBinding
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : BindingActivity<LoginActivityBinding>() {
    val viewModel: LoginViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.login_activity

    override fun initView() {
        binding.vm = viewModel
    }

    override fun loadData() {
        viewModel.success.observe(this, Observer {
            finish()
        })

        // 注册成功和失败一样的处理
        viewModel.error.observe(this, Observer {
            toast(it ?: "")
        })

    }
}

