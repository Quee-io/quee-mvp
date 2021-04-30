package io.quee.mvp.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.quee.mvp.QueeApplication
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object RxService {
    fun <T> createApi(clazz: Class<T>): T {
        return createApi(clazz, QueeApplication.instance.serverUrl)
    }

    fun <T> createApi(clazz: Class<T>, url: String): T {
        val okHttpClient = createOkHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }
}