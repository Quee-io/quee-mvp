package io.quee.mvp_demo

import android.os.Bundle
import io.quee.mvp.ui.activity.QueeMvpActivity
import io.quee.mvp_demo.databinding.ActivitySampleBinding
import io.quee.mvp_demo.func.MessageListener
import io.quee.mvp_demo.sample.contract.SampleContract
import io.quee.mvp_demo.sample.model.SampleModel
import io.quee.mvp_demo.sample.presenter.SamplePresenter
import io.quee.mvp_demo.sample.view.SampleView

class SampleMvpActivity :
    QueeMvpActivity<ActivitySampleBinding, SampleContract.SamplePresenter, SampleContract.SampleModel, SampleContract.SampleView>(
        R.layout.activity_sample
    ), MessageListener {

    override fun initData() {
        presenter.sampleData()
    }

    override fun createView(): SampleContract.SampleView {
        return SampleView(this)
    }

    override fun createModel(): SampleContract.SampleModel {
        return SampleModel()
    }

    override fun createPresenter(): SampleContract.SamplePresenter {
        return SamplePresenter()
    }

    override fun afterMvpInit(bundle: Bundle?) {
    }

    override fun onMesssage(value: String) {
        binding.textView.text = "${binding.textView.text} \n\n $value"
    }
}
