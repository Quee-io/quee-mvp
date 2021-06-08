package io.quee.mvp.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView

interface MvpQueeStructure<VB : ViewBinding, P : QueePresenter<M, V>, M : QueeModel, V : QueeView> :
    QueeStructure<VB> {

    fun createView(): V
    fun createModel(): M
    fun createPresenter(): P

    fun VB.afterMvpInit(bundle: Bundle?)
}