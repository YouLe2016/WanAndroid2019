package com.wyl.category

import android.support.v4.app.Fragment
import com.wyl.category.category.CategoryFragment
import com.wyl.libbase.DebugActivity

class FragmentActivity : DebugActivity() {

    override fun createFragment(): Fragment = CategoryFragment()

}
