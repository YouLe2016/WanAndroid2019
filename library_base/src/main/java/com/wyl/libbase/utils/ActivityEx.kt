package com.wyl.libbase.utils

import android.graphics.Rect
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager

/* ************************************************ TitleBar ************************************************* */
/**
 * 获取状态栏高度
 * 在 onWindowFocusChanged 方法中可以获取到值
 */
fun AppCompatActivity.getActionBarHeight(): Int {
    val contentTop = window.findViewById<View>(Window.ID_ANDROID_CONTENT).top
    //statusBarHeight是状态栏的高度 
    return contentTop - getStatusBarHeight2()
}

/* ************************************************ StatusBar ************************************************* */

/**
 * 获取状态栏高度
 * 在 onWindowFocusChanged 方法中可以获取到值
 */
fun AppCompatActivity.getStatusBarHeight(): Int = Rect().let {
    window.decorView.getWindowVisibleDisplayFrame(it)
    it.top
}

/**
 * 获取状态栏高度
 * 在onCreate方法中也可以获取
 */
fun AppCompatActivity.getStatusBarHeight2(): Int {
    // 获得状态栏高度
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

/**
 * 设置全屏
 *
 * 另一种方式: <item name="android:windowFullscreen">true</item>
 */
fun AppCompatActivity.noStatusBar() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}

/* ************************************************ Fragment ************************************************* */

/**
 * 开启事务处理fragment
 */
fun AppCompatActivity.transact(block: FragmentTransaction.() -> FragmentTransaction) =
    supportFragmentManager.beginTransaction().block().commit()

@Deprecated(
    "弃用",
    ReplaceWith("transact替代"),
    level = DeprecationLevel.WARNING
)
fun AppCompatActivity.replaceFragment(containerViewId: Int, fragment: Fragment, tag: String? = null) =
    supportFragmentManager.beginTransaction().replace(
        containerViewId,
        fragment,
        tag
    ).commit()



