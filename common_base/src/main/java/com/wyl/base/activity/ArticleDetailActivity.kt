package com.wyl.base.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
import com.lzg.extend.toDisposables
import com.wyl.base.*
import com.wyl.base.repository.ArticleRepository
import com.wyl.libbase.utils.autoWired
import com.wyl.libbase.utils.openActivity
import com.wyl.libbase.utils.toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
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
        menuItem = menu?.getItem(0)!!
        menuItem.title = if (mCollect) getString(R.string.ui_unCollect) else getString(R.string.ui_collect)
        return true
    }

    lateinit var menuItem: MenuItem

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_collect) {
            if (ACacheHelper.hasLogin()) {
                collectOrUnCollect()
            } else {
                openActivity(LoginActivity)
            }
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun collectOrUnCollect() = if (mCollect) unCollect() else collect()

    private fun unCollect() {
        repository.unCollectHome(mId)
            .doOnSubscribe { }
            .doFinally { }
            .subscribe({
                mCollect = false
                menuItem.title = getString(R.string.ui_collect)
                toast(getString(R.string.ui_unCollect))
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
                mCollect = true
                menuItem.title = getString(R.string.ui_unCollect)
                toast(getString(R.string.ui_collect_success))
            }, {
                toast(onError(it))
            }).toDisposables(disposables)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe
    fun onEvent(event: String) {
        if (event == EVENT_LOGIN) {
            collectOrUnCollect()
        }
    }

}