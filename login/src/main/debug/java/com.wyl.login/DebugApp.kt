package com.wyl.login

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.billy.cc.core.component.CC
import com.lzg.extend.OkGoInitializer
import com.wyl.common_ui.ACacheHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DebugApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DebugApp)
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