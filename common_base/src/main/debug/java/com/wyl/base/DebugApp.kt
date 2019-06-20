package com.wyl.base

import android.app.Application

class DebugApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initModuleAhead(this)

        initModuleLow(this)
    }

//    override fun attachBaseContext(base: Context) {
//        super.attachBaseContext(base)
//        MultiDex.install(base)
//    }

}