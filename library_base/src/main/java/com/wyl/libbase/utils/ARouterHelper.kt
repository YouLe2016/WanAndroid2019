package com.wyl.libbase.utils

import android.os.Bundle
import android.support.v4.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

fun openActivity(pathName: String, bundle: Bundle? = null) {
    ARouter.getInstance().build(pathName)
        .with(bundle)
        .navigation()
}

fun getFragment(pathName: String, bundle: Bundle? = null): Fragment =
    ARouter.getInstance().build(pathName)
        .with(bundle)
        .navigation() as Fragment