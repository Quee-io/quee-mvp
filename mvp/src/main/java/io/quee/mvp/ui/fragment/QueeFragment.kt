package io.quee.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.quee.fragmentation.CoreFragment
import io.quee.mvp.base.QueeStructure

abstract class QueeFragment<B : ViewDataBinding>(@param:LayoutRes open val layout: Int) :
    CoreFragment(),
    QueeStructure {

    lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterBindingLayout(savedInstanceState)
    }
}