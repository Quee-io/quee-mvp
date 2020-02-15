package io.quee.mvp_demo.sample.view

import com.fasterxml.jackson.databind.ObjectMapper
import io.quee.mvp_demo.data.SampleData
import io.quee.mvp_demo.func.MessageListener
import io.quee.mvp_demo.sample.contract.SampleContract

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

class SampleView(
    val messageListener: MessageListener
) : SampleContract.SampleView {
    override fun showLoading() {
        messageListener.onMesssage("start loading")
    }

    override fun hideLoading() {
        messageListener.onMesssage("hide loading")
    }

    override fun onItemLoaded(items: Array<SampleData>) {
        val value = ObjectMapper().writeValueAsString(items)
        messageListener.onMesssage(value)
    }

    override fun onError(throwable: Throwable) {
        messageListener.onMesssage("error ${throwable.message}")
    }
}