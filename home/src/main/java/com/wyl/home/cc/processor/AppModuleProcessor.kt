package com.wyl.home.cc.processor

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.wyl.common_lib.cc.ACTION_APP_MODULE
import com.wyl.common_lib.cc.IActionProcessor
import com.wyl.home.appModule


class AppModuleProcessor : IActionProcessor {
    override fun getActionName(): String = ACTION_APP_MODULE

    override fun onActionCall(cc: CC): Boolean {
        CC.sendCCResult(cc.callId, CCResult.success(CC.CC_NULL_KEY, appModule))
        return false
    }

}