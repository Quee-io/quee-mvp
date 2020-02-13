package io.quee.mvp_demo

import io.quee.mvp.QueeApplication

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

class SampleApplication : QueeApplication("https://my-json-server.typicode.com") {
    override fun certificateRaw(): Int {
        return 0
    }

    override fun httpsEnabled(): Boolean {
        return false
    }
}