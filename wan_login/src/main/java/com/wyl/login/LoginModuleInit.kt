package com.wyl.login

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.wyl.libbase.init.IModuleInit
import com.wyl.libbase.utils.KLog
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

@SuppressLint("StaticFieldLeak")
lateinit var context: Context

class LoginModuleInit : IModuleInit {
    override fun onInitAhead(app: Application) {
        KLog.e("登录业务模块初始化 -- onInitAhead")
        context = app
    }

    override fun onInitLow(app: Application) {
        KLog.e("登录业务模块初始化 -- onInitLow")
    }
}