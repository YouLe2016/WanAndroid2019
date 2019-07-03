package com.wyl.home

import android.app.Application
import com.wyl.base.baseModule
import com.wyl.libbase.init.IModuleInit
import com.wyl.libbase.utils.KLog
import org.koin.core.context.startKoin

class DebugHomeModuleInit : IModuleInit {
    override fun onInitAhead(app: Application) {
        KLog.e("首页模块初始化 -- onInitAhead")

        startKoin {
            modules(homeModule + baseModule)
        }
    }

    override fun onInitLow(app: Application) {
        KLog.e("首页模块初始化 -- onInitLow")

    }
}