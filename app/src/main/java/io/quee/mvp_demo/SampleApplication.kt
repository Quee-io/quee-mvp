package io.quee.mvp_demo

import com.securepreferences.SecurePreferences
import io.quee.mvp.QueeApplication
import io.quee.mvp.utils.SharedPreferencesHelper

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

class SampleApplication : QueeApplication() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesHelper.init(SecurePreferences(this))
    }

    override val serverUrl: String = "https://my-json-server.typicode.com"
}