package io.quee.mvp.common

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel

abstract class QueeView(
    protected val appContext: ComponentActivity,
    protected val queeViewNotifier: QueeViewNotifier
) :
    ViewModel(), QueeLoadingView {
    fun onError(throwable: Throwable) {
        queeViewNotifier.showError(appContext, throwable)
    }

    override fun showLoading() {
        queeViewNotifier.startLoading(appContext)
    }

    override fun hideLoading() {
        queeViewNotifier.hideLoading(appContext)
    }
}