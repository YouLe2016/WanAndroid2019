package com.wyl.lewanandroid

import android.app.Application
import com.wyl.commbase.init.initModuleAhead
import com.wyl.commbase.init.initModuleLow
import com.wyl.home.homeModule
import com.wyl.login.loginModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initModuleAhead(this)

        startKoin {
            modules(homeModule + loginModule)
        }

        initModuleLow(this)


    }
}