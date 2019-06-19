package com.wyl.common_lib.cc

import com.billy.cc.core.component.CC

interface IActionProcessor {

    fun getActionName(): String

    fun onActionCall(cc: CC): Boolean

}