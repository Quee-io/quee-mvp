package io.quee.mvp.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.quee.mvp.QueeApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object RxService {
    private val interceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    fun <T> createApi(clazz: Class<T>): T {
        return createApi(clazz, QueeApplication.instance?.serverUrl!!)
    }

    fun <T> createApi(clazz: Class<T>, url: String): T {
        val okHttpClient = createOkHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(
                JacksonConverterFactory.create(
                    ObjectMapper().registerModule(
                        KotlinModule()
                    )
                )
            )
            .build()
        return retrofit.create(clazz)
    }

    fun createOkHttpClient(): OkHttpClient {
        val certificateRaw: Int = QueeApplication.instance?.certificateRaw()!!
        var okHttpClient = okHttpClient
        if (certificateRaw != 0 && QueeApplication.instance?.httpsEnabled()!!) {
            okHttpClient = SslOkHttpClient.generate(
                okHttpClient,
                QueeApplication.appResources.openRawResource(certificateRaw)
            )
        }
        return okHttpClient
    }
}