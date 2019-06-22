package com.wyl.base

import android.content.Context
import android.text.TextUtils
import com.wyl.libbase.utils.ACache

object ACacheHelper {
    private lateinit var cache: ACache

    fun init(context: Context) {
        cache = ACache.get(context)
    }

    fun hasLogin() = !TextUtils.isEmpty(cache.getAsString(USERNAME))


}