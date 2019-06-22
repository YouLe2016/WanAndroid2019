package com.wyl.libbase.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction


/**
 * 开启事务处理fragment
 */
fun Fragment.transact(block: FragmentTransaction.() -> FragmentTransaction) {
    childFragmentManager.beginTransaction().block().commit()
}
