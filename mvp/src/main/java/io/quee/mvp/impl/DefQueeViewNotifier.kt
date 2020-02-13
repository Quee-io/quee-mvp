package io.quee.mvp.impl

import android.util.Log.d
import androidx.activity.ComponentActivity
import io.quee.mvp.common.QueeViewNotifier

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

open class DefQueeViewNotifier : QueeViewNotifier {
    override fun showError(componentActivity: ComponentActivity, throwable: Throwable) {
        d(javaClass.canonicalName, "showError", throwable)
    }

    override fun startLoading(componentActivity: ComponentActivity) {
        d(javaClass.canonicalName, "startLoading")
    }

    override fun hideLoading(componentActivity: ComponentActivity) {
        d(javaClass.canonicalName, "hideLoading")
    }
}