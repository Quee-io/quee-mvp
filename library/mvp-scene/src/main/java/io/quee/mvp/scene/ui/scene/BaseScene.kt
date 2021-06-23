package io.quee.mvp.scene.ui.scene

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import com.bytedance.scene.Scene
import io.quee.mvp.base.QueeStructure

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 24, **Thu June, 2021**
 * Project *mvp-architecture* [https://quee.io]
 */
abstract class BaseScene<VB : ViewBinding>(
    private val isSecure: Boolean = false,
) : Scene(), QueeStructure<VB> {

    private var _binding: VB? = null

    protected abstract fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
    ): VB

    protected fun executeInBinding(command: VB.() -> Unit) {
        _binding?.command()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup,
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

    final override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _binding?.afterBindingLayout(savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}