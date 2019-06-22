package com.wyl.category

import android.app.Application
import com.wyl.libbase.init.IModuleInit
import com.wyl.libbase.utils.KLog
import org.koin.core.context.startKoin

class DebugCategoryModuleInit : IModuleInit {
    override fun onInitAhead(app: Application) {
        KLog.e("分类模块初始化 -- onInitAhead")

        startKoin {
            modules(categoryModule)
        }
    }

    override fun onInitLow(app: Application) {
        KLog.e("分类模块初始化 -- onInitLow")

    }
}