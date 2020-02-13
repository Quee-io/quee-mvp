package io.quee.mvp_demo

import android.content.Intent
import android.os.Bundle
import io.quee.mvp.ui.activity.QueeActivity
import io.quee.mvp_demo.databinding.ActivityMainBinding

class MainActivity : QueeActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun afterBindingLayout(bundle: Bundle?) {
        binding.loadSampleData.setOnClickListener {
            startActivity(Intent(this, SampleMvpActivity::class.java))
        }
    }
}