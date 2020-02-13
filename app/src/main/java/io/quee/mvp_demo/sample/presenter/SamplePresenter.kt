package io.quee.mvp_demo.sample.presenter

import io.quee.mvp_demo.sample.contract.SampleContract

/**
 * Created by Ibrahim Al-Tamimi on 2020-02-13.
 * Licensed for Quee.io
 */

class SamplePresenter : SampleContract.SamplePresenter() {
    override fun sampleData() {
        view?.showLoading()
        manager().add(model?.sampleData()?.subscribe({
            view?.hideLoading()
            view?.onItemLoaded(it)
        }, {
            view?.hideLoading()
            view?.onError(it)
        }))
    }
}