package io.quee.mvp.ui.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import com.treebo.internetavailabilitychecker.InternetConnectivityListener
import io.quee.fragmentation.core.anim.DefaultVerticalAnimator
import io.quee.fragmentation.core.anim.FragmentAnimator
import io.quee.fragmentation.swipeback.SwipeBackActivity
import io.quee.mvp.base.QueeStructure
import io.quee.mvp.manager.AppManager
import io.quee.mvp.utils.LocalManager


abstract class QueeActivity<B : ViewDataBinding>(
    @param:LayoutRes open val layout: Int,
    private val isSecure: Boolean = false
) :
    SwipeBackActivity(), QueeStructure, InternetConnectivityListener {

    lateinit var binding: B
    private lateinit var networkReceiver: BroadcastReceiver

    protected open fun addSwipeLayout(): Boolean {
        return false
    }

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isSecure) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
        binding = DataBindingUtil.setContentView(this, layout)
        InternetAvailabilityChecker.getInstance().addInternetConnectivityListener(this)
        afterBindingLayout(savedInstanceState)
        AppManager.appManager.addActivity(this)
    }

    final override fun onInternetConnectivityChanged(isConnected: Boolean) {
        onNetworkConnectionChanged(isConnected = isConnected)
    }

    protected open fun onNetworkConnectionChanged(isConnected: Boolean) {
        Log.d(
            javaClass.canonicalName,
            "onNetworkConnectionChanged: is Connected --> $isConnected"
        )
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocalManager.setLocale(newBase!!))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocalManager.setLocale(this)
    }

    override fun setContentView(layoutResID: Int) {
        setSwipeBackEnable(addSwipeLayout())
        super.setContentView(layoutResID)
    }

    override fun setContentView(view: View?) {
        setSwipeBackEnable(addSwipeLayout())
        super.setContentView(view)
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        setSwipeBackEnable(addSwipeLayout())
        super.setContentView(view, params)
    }

    override fun onBackPressedSupport() {
        supportFinishAfterTransition()
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator? {
        return DefaultVerticalAnimator()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        AppManager.appManager.finishActivity(this)
    }
}