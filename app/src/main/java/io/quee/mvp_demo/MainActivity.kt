package io.quee.mvp_demo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import io.quee.mvp.ui.activity.QueeActivity
import io.quee.mvp_demo.databinding.ActivityMainBinding

class MainActivity : QueeActivity<ActivityMainBinding>() {

    override fun ActivityMainBinding.afterBindingLayout(bundle: Bundle?) {
        executeInBinding {
            loadSampleData.setOnClickListener {
                startActivity(Intent(this@MainActivity, SampleMvpActivity::class.java))
            }
        }
    }

    override fun inflate(layoutInflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)
}
