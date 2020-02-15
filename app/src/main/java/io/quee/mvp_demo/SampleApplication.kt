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

    override fun certificateRaw(): Int {
        return 0
    }

    override fun serverUrl(): String {
        return "https://my-json-server.typicode.com"
    }
}