package com.wyl.home.cc

import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.IComponent
import com.wyl.common_lib.cc.ACTION_APP_MODULE
import com.wyl.common_lib.cc.ACTION_GET_FRAGMENT
import com.wyl.common_lib.cc.ACTION_SHOW_ACTIVITY
import com.wyl.common_lib.cc.IActionProcessor
import com.wyl.common_lib.utils.logD
import com.wyl.common_ui.COMPONENT_HOME
import com.wyl.home.cc.processor.AppModuleProcessor
import com.wyl.home.cc.processor.GetFragmentProcessor
import com.wyl.home.cc.processor.ShowActivityProcessor

class HomeComponent : IComponent {
    private val map: MutableMap<String, IActionProcessor> by lazy {
        mutableMapOf(
            ACTION_SHOW_ACTIVITY to ShowActivityProcessor(),
            ACTION_APP_MODULE to AppModuleProcessor(),
            ACTION_GET_FRAGMENT to GetFragmentProcessor()
        )
    }

    override fun getName(): String = COMPONENT_HOME

    override fun onCall(cc: CC): Boolean {
        logD(cc.actionName, "Look")
        val actionProcessor: IActionProcessor? = map[cc.actionName]
        return if (actionProcessor != null) {
            actionProcessor.onActionCall(cc)
        } else {
            CC.sendCCResult(cc.callId, CCResult.errorUnsupportedActionName())
            false
        }
    }


}