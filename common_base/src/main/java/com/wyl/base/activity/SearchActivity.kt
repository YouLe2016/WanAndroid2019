package com.wyl.base.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.wyl.base.R
import com.wyl.base.SearchActivity
import com.wyl.base.fragment.getArticleTypeFragment
import com.wyl.libbase.base.BindingActivity
import com.wyl.libbase.databinding.UiFrameActivityBinding
import com.wyl.libbase.utils.autoWired
import com.wyl.libbase.utils.transact

@Route(path = SearchActivity)
class SearchActivity : BindingActivity<UiFrameActivityBinding>() {

    override fun getLayoutId() = R.layout.ui_frame_activity

    override fun initView() {

    }

    override fun loadData() {
        val toolbar = binding.titleBar.toolbar
        val key = autoWired("key", "")
        toolbar.title = key
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment = getArticleTypeFragment(key = key)
        transact { replace(R.id.frameLayout, fragment) }
    }

}
