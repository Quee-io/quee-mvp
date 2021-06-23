package io.quee.mvp.common

interface QueeView : QueeLoadingView {
    fun onError(throwable: Throwable)
}