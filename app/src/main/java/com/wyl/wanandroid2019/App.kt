package com.wyl.wanandroid2019

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.lzg.extend.OkGoInitializer
import com.wyl.common_ui.ACacheHelper
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModule)
        }
        OkGoInitializer.initOkGo(this)
        ACacheHelper.init(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

}