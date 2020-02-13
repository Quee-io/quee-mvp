package io.quee.mvp.ui.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.quee.fragmentation.CoreActivity
import io.quee.mvp.base.QueeStructure

abstract class QueeActivity<B : ViewDataBinding>(@param:LayoutRes open val layout: Int) :
    CoreActivity(), QueeStructure {

    lateinit var binding: B

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
        afterBindingLayout(savedInstanceState)
    }
}