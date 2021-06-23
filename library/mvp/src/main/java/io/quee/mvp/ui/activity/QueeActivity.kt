package io.quee.mvp.ui.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import io.quee.mvp.base.QueeStructure
import io.quee.mvp.manager.AppManager
import io.quee.mvp.utils.LocalManager

abstract class QueeActivity<VB : ViewBinding>(
    private val isSecure: Boolean = false,
) : AppCompatActivity(), QueeStructure<VB> {

    private var _binding: VB? = null

    protected abstract fun inflate(layoutInflater: LayoutInflater): VB

    protected fun executeInBinding(command: VB.() -> Unit) {
        _binding?.command()
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isSecure) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
        inflate(layoutInflater).apply {
            _binding = this
            setContentView(root)
            afterBindingLayout(savedInstanceState)
        }
        AppManager.appManager.addActivity(this)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocalManager.setLocale(newBase!!))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocalManager.setLocale(this)
    }

    @CallSuper
    override fun onDestroy() {
        _binding = null
        AppManager.appManager.finishActivity(this)
        super.onDestroy()
    }
}