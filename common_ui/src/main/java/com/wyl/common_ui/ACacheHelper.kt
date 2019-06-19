package com.wyl.common_ui

import android.content.Context
import android.text.TextUtils
import com.wyl.common_lib.utils.ACache

object ACacheHelper {
    private lateinit var cache: ACache

    fun init(context: Context) {
        cache = ACache.get(context)
    }

    fun hasLogin() = !TextUtils.isEmpty(cache.getAsString(USERNAME))


}