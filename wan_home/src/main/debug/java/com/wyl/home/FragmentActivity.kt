package com.wyl.home

import android.support.v4.app.Fragment
import com.wyl.libbase.DebugActivity
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin

class FragmentActivity : DebugActivity() {
    override fun createFragment(): Fragment = HomeFragment()

    override fun loadData() {
        startKoin {
            modules(homeModule)
        }

    }

}
