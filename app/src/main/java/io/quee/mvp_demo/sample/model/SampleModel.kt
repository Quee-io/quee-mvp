package io.quee.mvp_demo.sample.model

import io.quee.mvp.service.RxService
import io.quee.mvp.utils.RxUtil
import io.quee.mvp_demo.api.SampleApi
import io.quee.mvp_demo.data.SampleData
import io.quee.mvp_demo.sample.contract.SampleContract
import io.reactivex.Observable

class SampleModel : SampleContract.SampleModel {
    override fun sampleData(): Observable<Array<SampleData>> {
        return RxService.createApi(SampleApi::class.java)
            .loadSampleData()
            .compose(RxUtil.rxSchedulerHelper())
    }
}