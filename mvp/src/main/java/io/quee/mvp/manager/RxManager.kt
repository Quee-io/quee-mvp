package io.quee.mvp.manager

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

class RxManager {
    private val mRxBus: RxBus = RxBus.instance()
    private val mObservables: MutableMap<String, Observable<*>?> = HashMap()
    private val mCompositeSubscription = CompositeDisposable()

    fun add(m: Disposable?) {
        mCompositeSubscription.add(m!!)
    }

    fun clear() {
        mCompositeSubscription.clear()
        mObservables.forEach {
            it.value?.apply {
                mRxBus.unregister(it.key, this)
            }
        }
    }
}