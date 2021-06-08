package io.quee.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import io.quee.fragmentation.CoreFragment
import io.quee.mvp.base.QueeStructure

abstract class QueeFragment<VB : ViewBinding>(
    @param:LayoutRes open val layout: Int,
    private val isSecure: Boolean = false,
) : CoreFragment(),
    QueeStructure<VB> {

    private var _binding: VB? = null

    protected fun executeInBinding(command: VB.() -> Unit) {
        _binding?.command()
    }

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        activity?.takeIf { isSecure }?.apply {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
        val dataBinding: VB = DataBindingUtil.inflate(inflater, layout, container, false)
        _binding = dataBinding
        return dataBinding.root
    }

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.afterBindingLayout(savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}