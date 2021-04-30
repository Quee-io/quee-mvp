package io.quee.mvp.ui.sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.quee.mvp.base.QueeStructure

abstract class QueeBottomSheetFragment<B : ViewDataBinding>(@param:LayoutRes open val layout: Int) :
    BottomSheetDialogFragment(),
    QueeStructure {

    var binding: B? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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