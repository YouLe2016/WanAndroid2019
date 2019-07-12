package com.wyl.base.activity

import android.content.Context
import android.content.Intent
import com.wyl.base.R
import com.wyl.base.fragment.getArticleTypeFragment
import com.wyl.libbase.base.BindingActivity
import com.wyl.libbase.databinding.UiFrameActivityBinding
import com.wyl.libbase.utils.autoWired
import com.wyl.libbase.utils.transact

fun openArticleTypeActivity(context: Context, chapterName: String, chapterId: Int) {
    context.startActivity(
        Intent(context, ArticleTypeActivity::class.java)
            .putExtra("title", chapterName)
            .putExtra("id", chapterId)
    )
}

class ArticleTypeActivity : BindingActivity<UiFrameActivityBinding>() {

    override fun getLayoutId() = R.layout.ui_frame_activity

    override fun initView() {
        val toolbar = binding.titleBar.toolbar
        toolbar.title = autoWired("title", "")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = autoWired("id", -1)
        val fragment = getArticleTypeFragment(id = id)
        transact { replace(R.id.frameLayout, fragment) }
    }

    override fun loadData() {

    }

}
