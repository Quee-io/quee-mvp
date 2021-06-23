package io.quee.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import io.quee.mvp.base.QueeStructure

abstract class QueeFragment<VB : ViewBinding>(
    private val isSecure: Boolean = false,
) : Fragment(),
    QueeStructure<VB> {

    private var _binding: VB? = null

    protected abstract fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): VB

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
        return inflateBinding(inflater, container).run {
            _binding = this
            root
        }
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