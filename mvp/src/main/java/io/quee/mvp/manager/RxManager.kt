package io.quee.mvp.manager

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

class RxManager {
    private val mRxBus: RxBus? = RxBus.`$`()
    private val mObservables: MutableMap<String, Observable<*>?> = HashMap()
    private val mCompositeSubscription = CompositeDisposable()

    fun add(m: Disposable?) {
        mCompositeSubscription.add(m!!)
    }

    fun clear() {
        mCompositeSubscription.clear()
        for ((key, value) in mObservables) mRxBus!!.unregister(key, value!!)
    }
}