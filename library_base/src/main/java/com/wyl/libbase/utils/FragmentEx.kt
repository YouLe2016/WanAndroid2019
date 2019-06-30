package com.wyl.libbase.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

/**
 * 自动获取属性值
 */
fun <T> Fragment.autoWired(key: String, default: T? = null): T? {
    return arguments?.let {
        if (it.get(key) != null) {
            try {
                it.get(key) as T
            } catch (e: ClassCastException) {
                e.printStackTrace()
                null
            }
        } else default
    }
}

/**
 * 开启事务处理fragment
 */
fun Fragment.transact(block: FragmentTransaction.() -> FragmentTransaction) {
    childFragmentManager.beginTransaction().block().commit()
}
