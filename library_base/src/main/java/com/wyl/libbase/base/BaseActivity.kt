package com.wyl.libbase.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.View
import com.wyl.libbase.utils.KLog

abstract class BaseActivity : AppCompatActivity(), Presenter {
    override fun onCreate(savedInstanceState: Bundle?) {
        KLog.d("before")
        super.onCreate(savedInstanceState)
        KLog.d("after")
        setContentView()
        initView()
        loadData()
    }

//    protected lateinit var mToolbar: Toolbar
//    open val useToolbar = false
//    open val homeUp = false

    override fun onContentChanged() {
        KLog.d("before")
        super.onContentChanged()
        KLog.d("after")

        /*if (useToolbar) {
            mToolbar = findViewById(R.id.toolbar)
//            setSupportActionBar(mToolbar)
            // 初始化Toolbar
            UltimateBar.with(this)
                .applyNavigation(true)
                .create()
                .drawableBar()
            mToolbar.setBackgroundColor(resources.getColor(R.color.ui_colorPrimary))
            if (homeUp) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }*/

    }

    abstract fun setContentView()

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun loadData()

    override fun onClick(v: View) {}

    override fun getItemDecoration(): RecyclerView.ItemDecoration? = null

    override fun getAdapter(): RecyclerView.Adapter<*>? = null


}