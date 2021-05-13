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
import io.quee.fragmentation.CoreFragment
import io.quee.mvp.base.QueeStructure

abstract class QueeFragment<B : ViewDataBinding>(
    @param:LayoutRes open val layout: Int,
    private val isSecure: Boolean = false
) : CoreFragment(),
    QueeStructure {

    private var _binding: B? = null

    val binding: B
        get() = _binding!!

    protected fun executeInBinding(command: B.() -> Unit) {
        _binding?.command()
    }

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
        _binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterBindingLayout(savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}