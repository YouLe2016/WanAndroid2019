package com.wyl.base

import android.content.Context
import android.text.TextUtils
import com.lzy.okgo.OkGo
import com.wyl.libbase.utils.ACache


object ACacheHelper {
    lateinit var cache: ACache

    fun init(context: Context) {
        cache = ACache.get(context)
    }

    fun hasLogin() = !TextUtils.isEmpty(cache.getAsString(USERNAME))

    fun login(username: String) {
        cache.put(USERNAME, username)
    }

    fun logout() {
        cache.remove(USERNAME)
        OkGo.getInstance().cookieJar.cookieStore.removeAllCookie()
    }

}