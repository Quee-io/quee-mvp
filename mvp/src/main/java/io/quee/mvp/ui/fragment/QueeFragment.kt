package io.quee.mvp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker
import com.treebo.internetavailabilitychecker.InternetConnectivityListener
import io.quee.fragmentation.CoreFragment
import io.quee.mvp.base.QueeStructure

abstract class QueeFragment<B : ViewDataBinding>(
    @param:LayoutRes open val layout: Int,
    private val isSecure: Boolean = false
) :
    CoreFragment(),
    QueeStructure,
    InternetConnectivityListener {

    lateinit var binding: B

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isSecure) {
            coreActivity.window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        InternetAvailabilityChecker.getInstance().addInternetConnectivityListener(this)
        afterBindingLayout(savedInstanceState)
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
}