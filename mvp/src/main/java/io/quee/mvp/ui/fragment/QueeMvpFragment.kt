package io.quee.mvp.ui.fragment

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.databinding.ViewDataBinding
import io.quee.mvp.base.MvpQueeStructure
import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView

abstract class QueeMvpFragment<B : ViewDataBinding, P : QueePresenter<M, V>, M : QueeModel, V : QueeView>
    (layout: Int, isSecure: Boolean = false) :
    QueeFragment<B>(layout, isSecure), MvpQueeStructure<P, M, V> {

    private var model: M? = null
    private var view: V? = null
    private var presenter: P? = null

    final override fun afterBindingLayout(bundle: Bundle?) {
        model = createModel()
        view = createView()
        presenter = createPresenter()
        presenter?.attach(this.view!!, model!!)
        afterMvpInit(bundle)
        initData()
    }

    protected fun executeInPresenter(command: P.() -> Unit) {
        presenter?.command()
    }

    abstract fun initData()

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        presenter?.detach()
    }
}