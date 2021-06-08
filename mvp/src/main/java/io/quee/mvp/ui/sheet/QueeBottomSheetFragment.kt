package io.quee.mvp.ui.sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.quee.mvp.base.QueeStructure

abstract class QueeBottomSheetFragment<VB : ViewBinding>(@param:LayoutRes open val layout: Int) :
    BottomSheetDialogFragment(),
    QueeStructure<VB> {

    private var _binding: VB? = null

    protected fun executeInBinding(command: VB.() -> Unit) {
        _binding?.command()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val dataBinding: VB = DataBindingUtil.inflate(inflater, layout, container, false)
        _binding = dataBinding
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.afterBindingLayout(savedInstanceState)
    }

    @CallSuper
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}