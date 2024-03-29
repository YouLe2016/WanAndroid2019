package com.wyl.base.init

import android.app.Application
import com.wyl.libbase.init.IModuleInit

const val host = "com.wyl"

val ModuleInit = arrayOf(
    "$host.base.init.BaseModuleInit",
    "$host.main.MainModuleInit",
    "$host.home.HomeModuleInit",
    "$host.login.LoginModuleInit",
    "$host.category.CategoryModuleInit"
)

fun initModuleAhead(app: Application) {
    ModuleInit.forEach {
        try {
            val clazz = Class.forName(it)
            val init = clazz.newInstance() as IModuleInit
            //调用初始化方法
            init.onInitAhead(app)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun initModuleLow(app: Application) {
    ModuleInit.forEach {
        try {
            val clazz = Class.forName(it)
            val init = clazz.newInstance() as IModuleInit
            //调用初始化方法
            init.onInitLow(app)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}