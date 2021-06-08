package io.quee.mvp_demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import io.quee.mvp.ui.activity.QueeMvpActivity
import io.quee.mvp_demo.databinding.ActivitySampleBinding
import io.quee.mvp_demo.func.MessageListener
import io.quee.mvp_demo.sample.contract.SampleContract
import io.quee.mvp_demo.sample.model.SampleModel
import io.quee.mvp_demo.sample.presenter.SamplePresenter
import io.quee.mvp_demo.sample.view.SampleView
import kotlinx.coroutines.launch

class SampleMvpActivity :
    QueeMvpActivity<ActivitySampleBinding, SampleContract.SamplePresenter, SampleContract.SampleModel, SampleContract.SampleView>(
    ), MessageListener {

    override fun initData() {
        lifecycleScope.launch {
            executeInPresenter {
                sampleData()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d(localClassName, "onStop: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(localClassName, "onStart: ")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(localClassName, "onActivityResult: ")
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

    override fun ActivitySampleBinding.afterMvpInit(bundle: Bundle?) {
        executeInBinding {
            samll.setOnClickListener {
                startActivityForResult(
                    Intent(
                        this@SampleMvpActivity,
                        MainActivity::class.java
                    ),
                    0
                )
            }

        }
    }

    override fun onMessage(value: String) {
        executeInBinding {
            textView.text = "${textView.text} \n\n $value"
        }
    }

    override fun inflate(layoutInflater: LayoutInflater) =
        ActivitySampleBinding.inflate(layoutInflater)
}
