package io.quee.mvp.service

import io.quee.mvp.BuildConfig
import io.quee.mvp.QueeApplication
import io.quee.mvp.utils.NetUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CacheInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetUtils.isConnected(QueeApplication.appContext)) {
            request = request.newBuilder()
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        val response = chain.proceed(request)
        if (BuildConfig.DEBUG) {
            if (NetUtils.isConnected(QueeApplication.appContext)) {
                val maxAge = 0
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .removeHeader("Pragma")
                    .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                    .header(
                        name = "Cache-Control",
                        value = "public, only-if-cached, max-stale=$maxStale"
                    )
                    .removeHeader("Pragma")
                    .build()
            }
        }
        return response
    }
}