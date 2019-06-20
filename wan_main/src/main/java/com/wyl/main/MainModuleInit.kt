package com.wyl.main

import android.app.Application
import com.wyl.libbase.init.IModuleInit
import com.wyl.libbase.utils.KLog

class MainModuleInit : IModuleInit {
    override fun onInitAhead(app: Application) {
        KLog.e("主业务模块初始化 -- onInitAhead")
    }

    override fun onInitLow(app: Application) {
        KLog.e("主业务模块初始化 -- onInitLow")
    }
}