package com.wyl.home

import android.app.Application
import com.wyl.libbase.init.IModuleInit
import com.wyl.libbase.utils.KLog
import org.koin.core.context.startKoin

class HomeModuleInit : IModuleInit {
    override fun onInitAhead(app: Application) {
        KLog.e("首页模块初始化 -- onInitAhead")
    }

    override fun onInitLow(app: Application) {
        KLog.e("首页模块初始化 -- onInitLow")

    }
}