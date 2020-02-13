package io.quee.mvp

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import androidx.multidex.MultiDexApplication

abstract class QueeApplication(val serverUrl: String?) : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    @RawRes
    abstract fun certificateRaw(): Int

    abstract fun httpsEnabled(): Boolean

    companion object {
        @get:Synchronized
        var instance: QueeApplication? = null
            private set

        val appContext: Context
            get() = instance!!.applicationContext

        val appResources: Resources
            get() = instance!!.resources
    }
}