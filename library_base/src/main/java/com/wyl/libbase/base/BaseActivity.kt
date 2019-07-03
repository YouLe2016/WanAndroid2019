package com.wyl.libbase.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import com.google.gson.JsonSyntaxException
import com.lzy.okgo.exception.HttpException
import com.lzy.okgo.exception.StorageException
import com.wyl.libbase.utils.KLog
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


abstract class BaseActivity : AppCompatActivity(), Presenter {
    override fun onCreate(savedInstanceState: Bundle?) {
        KLog.d("before")
        super.onCreate(savedInstanceState)
        KLog.d("after")
        setContentView()
        initView()
        loadData()
    }

    override fun onContentChanged() {
        KLog.d("before")
        super.onContentChanged()
        KLog.d("after")

    }

    abstract fun setContentView()

    abstract fun getLayoutId(): Int

    abstract fun initView()

    abstract fun loadData()

    override fun onClick(v: View) {}

    override fun getItemDecoration(): RecyclerView.ItemDecoration? = null

    override fun getAdapter(): RecyclerView.Adapter<*>? = null

    /** 让menu条目的icon可见 */
    @SuppressLint("RestrictedApi")
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val builder = menu as MenuBuilder
        builder.setOptionalIconsVisible(true)
        return true
    }

    protected fun onError(exception: Throwable): String =
        when (exception) {
            is IllegalStateException -> exception.message!!
            is UnknownHostException, is ConnectException -> "网络连接失败，请连接网络！"
            is SocketTimeoutException -> "网络请求超时！"
            is HttpException -> "服务器发生未知错误！"
            is StorageException -> "SD卡不存在或没有权限！"
            is JsonSyntaxException, is JSONException -> "数据格式错误或解析失败！"
            else -> "网络数据请求失败！"
        }


    protected val disposables by lazy { CompositeDisposable() }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

}