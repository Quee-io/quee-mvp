package io.quee.mvp.ui.fragment

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import io.quee.mvp.base.MvpQueeStructure
import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView

abstract class QueeMvpFragment<B : ViewDataBinding, P : QueePresenter<M, V>, M : QueeModel, V : QueeView>
    (layout: Int, isSecure: Boolean = false) :
    QueeFragment<B>(layout, isSecure), MvpQueeStructure<P, M, V> {

    lateinit var model: M
    lateinit var view: V
    lateinit var presenter: P

    final override fun afterBindingLayout(bundle: Bundle?) {
        model = createModel()
        view = createView()
        presenter = createPresenter()
        presenter.attach(view, model)
        afterMvpInit(bundle)
    }

    final override fun onResume() {
        super.onResume()
        initData()
        internalOnResume()
    }

    open fun internalOnResume() {
    }

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}