package io.quee.mvp_demo.notifier

import androidx.activity.ComponentActivity
import io.quee.mvp.impl.DefQueeViewNotifier
import io.quee.mvp_demo.func.MessageListener

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

class SampleNotifier(private val messageListener: MessageListener) : DefQueeViewNotifier() {
    override fun showError(componentActivity: ComponentActivity, throwable: Throwable) {
        super.showError(componentActivity, throwable)
        messageListener.onMesssage("error ${throwable.message}")
    }

    override fun startLoading(componentActivity: ComponentActivity) {
        super.startLoading(componentActivity)
        messageListener.onMesssage("start loading")
    }

    override fun hideLoading(componentActivity: ComponentActivity) {
        super.hideLoading(componentActivity)
        messageListener.onMesssage("hide loading")
    }
}