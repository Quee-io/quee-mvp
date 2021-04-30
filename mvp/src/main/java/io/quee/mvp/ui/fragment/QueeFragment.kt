package io.quee.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import io.quee.mvp.base.QueeStructure

abstract class QueeFragment<B : ViewDataBinding>(
    @param:LayoutRes open val layout: Int,
    private val isSecure: Boolean = false
) : Fragment(),
    QueeStructure {

    var binding: B? = null

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.takeIf { isSecure }?.apply {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
        val dataBinding: B = DataBindingUtil.inflate(inflater, layout, container, false)
        binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterBindingLayout(savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}