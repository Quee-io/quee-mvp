package io.quee.mvp.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.quee.mvp.QueeApplication
import io.quee.mvp.utils.RxUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created By [**Ibrahim Al-Tamimi **](https://www.linkedin.com/in/iloom/)
 * Created At **Tuesday **24**, November 2020**
 */

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

    private fun createOkHttpClient(): OkHttpClient {
        return QueeApplication.instance.httpClient
    }
}

fun <T, RS> RxService.create(
    clazz: Class<T>, invocation: T.() -> Observable<RS>,
): Observable<RS> {
    return createApi(clazz).run {
        invocation()
    }.compose(rxSchedulerHelper())
}

fun <T, RS> RxService.create(
    clazz: Class<T>,
    url: String,
    invocation: T.() -> Observable<RS>,
): Observable<RS> {
    return createApi(clazz, url).run {
        invocation()
    }.compose(RxUtil.rxSchedulerHelper())
}

fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> {
    return ObservableTransformer { upstream: Observable<T> ->
        upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}