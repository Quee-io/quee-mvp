package io.quee.mvp.http

import io.quee.mvp.QueeApplication
import okhttp3.Cache
import java.io.File

object HttpCache {
    private const val HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 50 * 1024 * 1024
    val cache: Cache
        get() = Cache(
            File(QueeApplication.appContext.cacheDir.absolutePath + File.separator + "data/NetCache"),
            HTTP_RESPONSE_DISK_CACHE_MAX_SIZE.toLong()
        )
}