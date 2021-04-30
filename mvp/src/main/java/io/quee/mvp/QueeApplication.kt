package io.quee.mvp

import android.content.Context
import android.content.res.Resources
import androidx.annotation.CallSuper
import androidx.annotation.RawRes
import androidx.multidex.MultiDexApplication
import io.quee.mvp.http.HttpClientProvider
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

abstract class QueeApplication() : MultiDexApplication(), HttpClientProvider {
    @CallSuper
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    @RawRes
    open fun certificateRaw(): Int? = null

    abstract val serverUrl: String

    override val httpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    companion object {
        @get:Synchronized
        lateinit var instance: QueeApplication
            private set

        val appContext: Context
            get() = instance.applicationContext

        val appResources: Resources
            get() = instance.resources
    }
}