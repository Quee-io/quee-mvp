package io.quee.mvp.common

import androidx.activity.ComponentActivity

interface QueeViewNotifier {
    fun showError(componentActivity: ComponentActivity, throwable: Throwable)
    fun startLoading(componentActivity: ComponentActivity)
    fun hideLoading(componentActivity: ComponentActivity)
}