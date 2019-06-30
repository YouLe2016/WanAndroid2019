package com.wyl.base.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.MenuItem
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.LinearLayout
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebSettingsImpl
import com.just.agentweb.IAgentWebSettings
import com.wyl.base.R
import com.wyl.base.WebActivity
import com.wyl.base.databinding.UiWebActivityBinding
import com.wyl.libbase.base.BindingActivity
import com.wyl.libbase.utils.KLog
import com.wyl.libbase.utils.openActivity

fun openWebActivity(url: String, title: String? = null, id: Int? = null, author: String? = null) {
    openActivity(
        WebActivity,
        Bundle().apply {
            putString("url", url)
            if (title != null) putString("title", title)
            if (id != null) putInt("id", id)
            if (author != null) putString("author", author)
        }
    )
}

@Route(path = WebActivity)
class WebActivity : BindingActivity<UiWebActivityBinding>() {

    @Autowired(name = "url")
    @JvmField
    var mUrl: String = ""
    @Autowired(name = "title")
    @JvmField
    var mTitle: String = ""

    /**
     * 页面显示适配屏幕
     */
    private val agentWebSettings by lazy {
        object : AgentWebSettingsImpl() {
            override fun toSetting(webView: WebView): IAgentWebSettings<*> {
                val iAgentWebSettings = super.toSetting(webView)
                iAgentWebSettings.webSettings.run {
                    builtInZoomControls = true
                    displayZoomControls = false
                    setSupportZoom(true)
                    loadWithOverviewMode = true
                    useWideViewPort = true
                }
                return iAgentWebSettings
            }
        }
    }

    private val mAgentWeb by lazy {
        // AgentWeb 基础用法
        AgentWeb.with(this)
            .setAgentWebParent(binding.frameLayout, LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT))
            .useDefaultIndicator()
            .setAgentWebWebSettings(agentWebSettings)
            .setWebChromeClient(object : WebChromeClient() {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    KLog.i("on receiver title ============ $title  ${TextUtils.isEmpty(mTitle)}")
                    if (mTitle.isEmpty()) binding.titleBar.toolbar.title = title
                }
            })
            .createAgentWeb()
            .ready()
            .go(mUrl)
    }

    override fun getLayoutId() = R.layout.ui_web_activity

    override fun initView() {
    }

    override fun loadData() {
        ARouter.getInstance().inject(this)
        val toolbar = binding.titleBar.toolbar
        toolbar.title = mTitle
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        // AgentWeb 事件处理
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    // AgentWeb 跟随 Activity Or Fragment生命周期，释放 CPU 更省电 。
    override fun onPause() {
        super.onPause()
        mAgentWeb.webLifeCycle.onPause()
    }

    override fun onResume() {
        super.onResume()
        mAgentWeb.webLifeCycle.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

}