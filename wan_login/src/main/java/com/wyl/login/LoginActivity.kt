package com.wyl.login

import android.arch.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.wyl.base.EVENT_LOGIN
import com.wyl.base.LoginActivity
import com.wyl.libbase.base.BindingActivity
import com.wyl.libbase.utils.toast
import com.wyl.login.databinding.LoginActivityBinding
import org.greenrobot.eventbus.EventBus
import org.koin.android.viewmodel.ext.android.viewModel

@Route(path = LoginActivity)
class LoginActivity : BindingActivity<LoginActivityBinding>() {
    val viewModel: LoginViewModel by viewModel()

    override fun getLayoutId(): Int = R.layout.login_activity

    override fun initView() {
        binding.vm = viewModel
    }

    override fun loadData() {
        viewModel.success.observe(this, Observer {
            EventBus.getDefault().post(EVENT_LOGIN)
            finish()
        })

        // 注册成功和失败一样的处理
        viewModel.error.observe(this, Observer {
            toast(it ?: "")
        })

    }
}

