package com.wyl.base.activity

import android.text.TextUtils
import android.view.KeyEvent
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebSettingsImpl
import com.just.agentweb.IAgentWebSettings
import com.wyl.base.R
import com.wyl.libbase.base.BindingActivity
import com.wyl.libbase.databinding.UiFrameActivityBinding
import com.wyl.libbase.utils.KLog
import com.wyl.libbase.utils.autoWired

open class WebActivity : BindingActivity<UiFrameActivityBinding>() {

    val mUrl: String by lazy { autoWired("url", "") }
    val mTitle: String by lazy { autoWired("title", "") }

    /**页面显示适配屏幕 */
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

    override fun getLayoutId() = R.layout.ui_frame_activity

    override fun initView() {
        val toolbar = binding.titleBar.toolbar
        toolbar.title = mTitle
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun loadData() {

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        KLog.d("keyCode = $keyCode, event = $event")
        // AgentWeb 事件处理
        return if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        KLog.d("before")
        super.onBackPressed()
        KLog.d("after")
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