package com.wyl.home.cc.processor

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.wyl.common_lib.cc.ACTION_SHOW_ACTIVITY
import com.wyl.common_lib.cc.IActionProcessor


class ShowActivityProcessor : IActionProcessor {
    override fun getActionName(): String = ACTION_SHOW_ACTIVITY

    override fun onActionCall(cc: CC): Boolean {
//        val className: String = cc.getParamItem(KEY_CLASS_NAME)
//        CCUtil.navigateTo(cc,)
        CC.sendCCResult(cc.callId, CCResult.success())
        return false
    }

}