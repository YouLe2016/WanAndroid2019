package com.wyl.lewanandroid

import android.app.Application
import com.wyl.base.init.initModuleAhead
import com.wyl.base.init.initModuleLow
import com.wyl.category.categoryModule
import com.wyl.home.homeModule
import com.wyl.login.loginModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initModuleAhead(this)

        startKoin {
            modules(homeModule + loginModule + categoryModule)
        }

        initModuleLow(this)


    }
}