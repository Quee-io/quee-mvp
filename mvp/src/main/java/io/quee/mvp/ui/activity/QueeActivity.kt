package io.quee.mvp.ui.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.quee.mvp.base.QueeStructure
import io.quee.mvp.manager.AppManager
import io.quee.mvp.utils.LocalManager

abstract class QueeActivity<B : ViewDataBinding>(
    @param:LayoutRes open val layout: Int,
    private val isSecure: Boolean = false
) : SwipeBackActivity(), QueeStructure {

    private var binding: B? = null

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isSecure) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
        binding = DataBindingUtil.setContentView(this, layout)
        afterBindingLayout(savedInstanceState)
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
        super.onDestroy()
        binding = null
        AppManager.appManager.finishActivity(this)
    }

    protected fun executeInBinding(command: B.() -> Unit) {
        binding?.command()
    }
}