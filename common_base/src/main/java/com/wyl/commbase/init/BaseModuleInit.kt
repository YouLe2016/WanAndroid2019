package com.wyl.commbase.init

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.lzg.extend.OkGoInitializer
import com.wyl.commbase.ACacheHelper
import com.wyl.commbase.BuildConfig
import com.wyl.libbase.init.IModuleInit
import com.wyl.libbase.utils.KLog

class BaseModuleInit : IModuleInit {
    override fun onInitAhead(app: Application) {
        KLog.e("基础层初始化 -- onInitAhead")
        //初始化阿里路由框架
        initARouter(app)
        OkGoInitializer.initOkGo(app)
        ACacheHelper.init(app)
    }

    private fun initARouter(app: Application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(app) // 尽可能早，推荐在Application中初始化
    }

    override fun onInitLow(app: Application) {
        KLog.e("基础层初始化 -- onInitLow")

    }

}