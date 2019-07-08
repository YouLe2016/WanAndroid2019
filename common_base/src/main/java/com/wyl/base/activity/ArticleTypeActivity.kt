package com.wyl.base.activity

import android.os.Bundle
import com.wyl.base.R
import com.wyl.base.fragment.ArticleTypeFragment
import com.wyl.libbase.base.BindingActivity
import com.wyl.libbase.databinding.UiFrameActivityBinding
import com.wyl.libbase.utils.autoWired
import com.wyl.libbase.utils.transact

class ArticleTypeActivity : BindingActivity<UiFrameActivityBinding>() {

    override fun getLayoutId() = R.layout.ui_frame_activity

    override fun initView() {
        val toolbar = binding.titleBar.toolbar
        toolbar.title = autoWired("title", "")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment = ArticleTypeFragment()
        fragment.arguments = Bundle().apply { putInt("id", autoWired("id", -1)) }
        transact { replace(R.id.frameLayout, fragment) }
    }

    override fun loadData() {

    }

}
