package com.wyl.libbase.utils

import android.support.v4.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

fun openActivity(pathName: String) {
    ARouter.getInstance().build(pathName).navigation()
}

fun getFragment(pathName: String): Fragment {
    return ARouter.getInstance().build(pathName).navigation() as Fragment
}