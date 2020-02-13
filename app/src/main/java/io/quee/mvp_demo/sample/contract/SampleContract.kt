package io.quee.mvp_demo.sample.contract

import androidx.activity.ComponentActivity
import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView
import io.quee.mvp.common.QueeViewNotifier
import io.quee.mvp_demo.data.SampleData
import io.reactivex.Observable

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

interface SampleContract {
    abstract class SampleView(
        appContext: ComponentActivity,
        queeViewNotifier: QueeViewNotifier
    ) :
        QueeView(appContext, queeViewNotifier) {
        abstract fun onItemLoaded(items: Array<SampleData>)
    }

    interface SampleModel : QueeModel {
        fun sampleData(): Observable<Array<SampleData>>
    }

    abstract class SamplePresenter : QueePresenter<SampleModel, SampleView>() {
        abstract fun sampleData();
    }
}