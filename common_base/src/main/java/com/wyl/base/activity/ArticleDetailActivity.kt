package com.wyl.base.activity

import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.lzg.extend.toDisposables
import com.wyl.base.ACacheHelper
import com.wyl.base.ArticleDetailActivity
import com.wyl.base.LoginActivity
import com.wyl.base.R
import com.wyl.base.repository.ArticleRepository
import com.wyl.libbase.utils.autoWired
import com.wyl.libbase.utils.openActivity
import com.wyl.libbase.utils.toast
import org.koin.android.ext.android.inject

fun openArticleDetailActivity(
    url: String,
    title: String? = null,
    id: Int? = null,
    author: String? = null,
    collect: Boolean? = null
) {
    openActivity(ArticleDetailActivity) {
        val postcard = withString("url", url)
        if (title != null) postcard.withString("title", title)
        if (id != null) postcard.withInt("id", id)
        if (author != null) postcard.withString("author", author)
        if (collect != null) postcard.withBoolean("collect", collect)
        postcard
    }
}

@Route(path = ArticleDetailActivity)
class ArticleDetailActivity : WebActivity() {
    private val repository: ArticleRepository by inject()

    private val mAuthor: String by lazy { autoWired("author", "") }
    private val mId: Int by lazy { autoWired("id", -1) }
    private var mCollect: Boolean = false

    override fun loadData() {
        mCollect = autoWired("collect", false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.ui_menu_web, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_collect) {
            if (ACacheHelper.hasLogin()) {
                if (mCollect) unCollect() else collect()
            } else {
                openActivity(LoginActivity)
            }
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun unCollect() {
        repository.unCollectHome(mId)
            .doOnSubscribe { }
            .doFinally { }
            .subscribe({
                toast("取消收藏")
            }, {
                toast(onError(it))
            }).toDisposables(disposables)
    }


    private fun collect() {
        if (mId == -1) {
            repository.collectOut(mTitle, mAuthor, mUrl)
        } else {
            repository.collectIn(mId)
        }.doOnSubscribe { }
            .doFinally { }
            .subscribe({
                toast("收藏成功")
            }, {
                toast(onError(it))
            }).toDisposables(disposables)
    }

}