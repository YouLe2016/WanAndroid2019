package com.lzg.extend.observer

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<T> : Observer<T> {
    var disposable: Disposable? = null
        private set

    override fun onSubscribe(d: Disposable) {
        this.disposable = d
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        onFail(e.message!!)
    }

    override fun onComplete() {

    }

    abstract fun onSuccess(t: T)
    abstract fun onFail(failMsg: String)
    open fun onFinish() {}
    open fun onStart() {}

}