package io.quee.mvp.base

import android.os.Bundle
import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView

interface MvpQueeStructure<P : QueePresenter<M, V>, M : QueeModel, V : QueeView> :
    QueeStructure {

    fun createView(): V
    fun createModel(): M
    fun createPresenter(): P

    fun afterMvpInit(bundle: Bundle?)
}