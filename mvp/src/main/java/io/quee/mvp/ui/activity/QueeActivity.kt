package io.quee.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.quee.fragmentation.swipeback.SwipeBackActivity
import io.quee.mvp.base.QueeStructure
import io.quee.mvp.manager.AppManager
import io.quee.mvp.utils.LocalManager

abstract class QueeActivity<B : ViewDataBinding>(
    @param:LayoutRes open val layout: Int,
    private val isSecure: Boolean = false
) : SwipeBackActivity(), QueeStructure {

    private var _binding: B? = null

    val binding: B
        get() = _binding!!

    protected fun executeInBinding(command: B.() -> Unit) {
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
        _binding = DataBindingUtil.setContentView(this, layout)
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

    @SuppressLint("MissingSuperCall")
    @CallSuper
    override fun onDestroy() {
        _binding = null
        AppManager.appManager.finishActivity(this)
        super.onDestroy()
    }
}