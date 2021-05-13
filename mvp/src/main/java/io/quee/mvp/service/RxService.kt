package io.quee.mvp.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.quee.mvp.QueeApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

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
        return QueeApplication.instance.httpClient
    }
}