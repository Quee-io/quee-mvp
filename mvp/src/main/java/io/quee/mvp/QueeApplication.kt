package io.quee.mvp

import android.content.Context
import android.content.res.Resources
import androidx.annotation.CallSuper
import androidx.annotation.RawRes
import androidx.multidex.MultiDexApplication
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

abstract class QueeApplication() : MultiDexApplication() {
    @CallSuper
    override fun onCreate() {
        super.onCreate()
        instance = this
        InternetAvailabilityChecker.init(this)
    }

    @RawRes
    abstract fun certificateRaw(): Int

    abstract fun serverUrl(): String

    open fun createHttpClient(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

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