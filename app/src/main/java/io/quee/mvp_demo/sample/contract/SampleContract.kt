package io.quee.mvp_demo.sample.contract

import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView
import io.quee.mvp_demo.data.SampleData
import io.reactivex.rxjava3.core.Observable

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

interface SampleContract {
    interface SampleView : QueeView {
        fun onItemLoaded(items: Array<SampleData>)
    }

    interface SampleModel : QueeModel {
        fun sampleData(): Observable<Array<SampleData>>
    }

    abstract class SamplePresenter : QueePresenter<SampleModel, SampleView>() {
        abstract fun sampleData();
    }
}