package com.wyl.login

import android.databinding.ObservableField
import com.lzg.extend.BaseResponse
import com.lzg.extend.JsonConvert
import com.lzg.extend.toDisposables
import com.lzy.okgo.OkGo
import com.lzy.okrx2.adapter.ObservableBody
import com.wyl.commbase.LOGIN_LOGIN
import com.wyl.commbase.LOGIN_REGISTER
import com.wyl.libbase.base.BaseViewModel
import com.wyl.login.bean.LoginData

class LoginViewModel : BaseViewModel() {

    val username: ObservableField<String> = ObservableField()

    val pwd: ObservableField<String> = ObservableField()

    val confrimPwd: ObservableField<String> by lazy { ObservableField<String>() }

    fun register() {
        if (!invalid()) return
        OkGo.post<BaseResponse<LoginData>>(LOGIN_REGISTER)
            .params("username", username.get())
            .params("password", pwd.get())
            .params("repassword", pwd.get())
            .converter(object : JsonConvert<BaseResponse<LoginData>>() {})
            .adapt(ObservableBody<BaseResponse<LoginData>>())
            .doOnSubscribe { loading.set(true) }
            .doFinally { loading.set(false) }
            .subscribe({
                error.value = context.getString(R.string.login_register_success)
            }, {
                onError(it)
            }).toDisposables(disposables)
    }

    fun login() {
        if (!invalid()) return
        OkGo.post<BaseResponse<LoginData>>(LOGIN_LOGIN)
            .params("username", username.get())
            .params("password", pwd.get())
            .converter(object : JsonConvert<BaseResponse<LoginData>>() {})
            .adapt(ObservableBody<BaseResponse<LoginData>>())
            .doOnSubscribe { loading.set(true) }
            .doFinally { loading.set(false) }
            .subscribe({
                success.value = it.msg
            }, {
                onError(it)
            }).toDisposables(disposables)
    }

    private fun invalid(): Boolean = when {
        username.get().isNullOrEmpty() -> {
            error.value = context.getString(R.string.login_empty_invalid_user_name)
            false
        }
        username.get()!!.length < 5 -> {
            error.value = context.getString(R.string.login_error_invalid_user_name)
            false
        }
        pwd.get().isNullOrEmpty() -> {
            error.value = context.getString(R.string.login_empty_invalid_password)
            false
        }
        pwd.get()!!.length < 7 -> {
            error.value = context.getString(R.string.login_error_invalid_password)
            false
        }
        else -> true
    }

}
