package io.quee.mvp_demo.sample.view

import android.util.Log.d
import androidx.activity.ComponentActivity
import com.fasterxml.jackson.databind.ObjectMapper
import io.quee.mvp.common.QueeViewNotifier
import io.quee.mvp_demo.data.SampleData
import io.quee.mvp_demo.func.MessageListener
import io.quee.mvp_demo.sample.contract.SampleContract

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

class SampleView(
    appContext: ComponentActivity,
    val messageListener: MessageListener,
    queeViewNotifier: QueeViewNotifier
) : SampleContract.SampleView(appContext, queeViewNotifier) {

    override fun onItemLoaded(items: Array<SampleData>) {
        d(javaClass.canonicalName, ObjectMapper().writeValueAsString(items))
        messageListener.onMesssage(ObjectMapper().writeValueAsString(items))
    }
}