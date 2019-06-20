package com.wyl.libbase.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.google.gson.JsonSyntaxException
import com.lzy.okgo.exception.HttpException
import com.lzy.okgo.exception.StorageException
import io.reactivex.disposables.CompositeDisposable
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseViewModel : ViewModel() {
    protected val disposables by lazy { CompositeDisposable() }

    /**
     * 正在请求数据加载中...
     */
    val loading: ObservableBoolean = ObservableBoolean(true)

    val error: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val success: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    override fun onCleared() {
        disposables.clear()
    }

    protected fun onError(exception: Throwable) {
        if (exception is IllegalStateException) {
            error.value = exception.message
        } else if (exception is UnknownHostException || exception is ConnectException) {
            error.value = "网络连接失败，请连接网络！"
        } else if (exception is SocketTimeoutException) {
            error.value = "网络请求超时！"
        } else if (exception is HttpException) {
            error.value = "服务器发生未知错误！"
        } else if (exception is StorageException) {
            error.value = "SD卡不存在或没有权限！"
        } else if (exception is JsonSyntaxException || exception is JSONException) {
            error.value = "数据格式错误或解析失败！"
        } else {
            error.value = "网络数据请求失败！"
        }
    }


}