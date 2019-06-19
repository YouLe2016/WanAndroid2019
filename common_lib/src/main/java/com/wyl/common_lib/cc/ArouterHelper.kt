package com.wyl.common_lib.cc

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.billy.cc.core.component.CC
import org.koin.core.module.Module

object ArouterHelper {
    fun getAppModule(componentName: String): List<Module> =
        CC.obtainBuilder(componentName)
            .setActionName(ACTION_APP_MODULE)
            .build()
            .call()
            .getDataItemWithNoKey()

    fun openActivity(componentName: String, activity: String): AppCompatActivity =
        CC.obtainBuilder(componentName)
            .setActionName(ACTION_SHOW_ACTIVITY)
            .addParam(KEY_CLASS_NAME, activity)
            .build()
            .call()
            .getDataItemWithNoKey()


    fun getFragment(componentName: String, fragment: String): Fragment =
        CC.obtainBuilder(componentName)
            .setActionName(ACTION_GET_FRAGMENT)
            .addParam(KEY_CLASS_NAME, fragment)
            .build()
            .call()
            .getDataItemWithNoKey()

}