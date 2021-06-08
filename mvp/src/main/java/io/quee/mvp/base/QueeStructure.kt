package io.quee.mvp.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding

interface QueeStructure<VB : ViewBinding> {
    fun VB.afterBindingLayout(bundle: Bundle?)
}