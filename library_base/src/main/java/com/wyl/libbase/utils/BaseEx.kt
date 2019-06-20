package com.wyl.libbase.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.wyl.libbase.BuildConfig

fun Any.logD(msg: Any?, tag: String? = null) {
    if (BuildConfig.DEBUG) {
        Log.d(tag ?: javaClass.simpleName, msg.toString())
    }
    // Activity.componentName.shortClassName == javaClass.simpleName
}

fun Any.logE(msg: Any?, tag: String? = null) {
    if (BuildConfig.DEBUG) {
        Log.e(tag ?: javaClass.simpleName, msg.toString())
    }
    // Activity.componentName.shortClassName == javaClass.simpleName
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.applicationContext, text, duration).show()
}