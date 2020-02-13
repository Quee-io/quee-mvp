package io.quee.mvp_demo.api

import io.quee.mvp_demo.data.SampleData
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

interface SampleApi {
    @GET("/typicode/demo/comments")
    fun loadSampleData(): Observable<Array<SampleData>>
}