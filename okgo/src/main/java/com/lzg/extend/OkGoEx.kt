package com.lzg.extend

import com.lzy.okgo.model.Response
import com.lzy.okgo.request.GetRequest
import com.lzy.okrx2.adapter.ObservableResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> GetRequest<T>.toObservable(): Observable<Response<T>> =
    converter(object : JsonConvert<T>() {})
        .adapt(ObservableResponse<T>())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<Response<BaseResponse<T>>>.getData(): Observable<T> =
    map {
        it.body().data
    }

fun <T> Observable<Response<BaseResponse<T>>>.getMsg(): Observable<String> =
    map {
        it.body().msg
    }

fun Disposable.toDisposables(disposables: CompositeDisposable) {
    disposables.add(this)
}
