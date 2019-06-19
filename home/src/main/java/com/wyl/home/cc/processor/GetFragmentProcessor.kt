package com.wyl.home.cc.processor

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.wyl.common_lib.cc.ACTION_GET_FRAGMENT
import com.wyl.common_lib.cc.IActionProcessor
import com.wyl.common_lib.cc.KEY_CLASS_NAME
import com.wyl.common_lib.utils.logD
import com.wyl.common_ui.HOME_FRAGMENT
import com.wyl.home.HomeFragment


class GetFragmentProcessor : IActionProcessor {
    override fun getActionName(): String = ACTION_GET_FRAGMENT

    override fun onActionCall(cc: CC): Boolean {
        val fragment: String = cc.getParamItem<String>(KEY_CLASS_NAME)
        logD(fragment, "Look")
        val result: CCResult = when (fragment) {
            HOME_FRAGMENT -> CCResult.success(CC.CC_NULL_KEY, HomeFragment())
            else -> CCResult.error("未发现$fragment")
        }

        CC.sendCCResult(cc.callId, result)
        return false
    }

}