package io.quee.mvp.utils

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.internal.schedulers.IoScheduler
import io.reactivex.rxjava3.schedulers.Schedulers


object RxUtil {
    fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream: Observable<T> ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(IoScheduler())
        }
    }
}