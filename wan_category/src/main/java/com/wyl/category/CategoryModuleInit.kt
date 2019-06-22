package com.wyl.category

import android.app.Application
import com.wyl.libbase.init.IModuleInit
import com.wyl.libbase.utils.KLog

class CategoryModuleInit : IModuleInit {
    override fun onInitAhead(app: Application) {
        KLog.e("分类模块初始化 -- onInitAhead")
    }

    override fun onInitLow(app: Application) {
        KLog.e("分类模块初始化 -- onInitLow")

    }
}