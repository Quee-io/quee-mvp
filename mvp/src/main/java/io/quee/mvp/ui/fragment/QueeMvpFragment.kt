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

    private var presenter: P? = null

    final override fun afterBindingLayout(bundle: Bundle?) {
        presenter = createPresenter().apply {
            attach(createView(), createModel())
        }
        afterMvpInit(bundle)
        initData()
    }

    protected fun executeInPresenter(command: P.() -> Unit) {
        presenter?.command()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        presenter?.apply { attach(createView(), createModel()) }
    }

    abstract fun initData()

    @CallSuper
    override fun onDestroy() {
        presenter?.detach()
        super.onDestroy()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        presenter?.detach()
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        presenter?.detach()
    }

    @CallSuper
    override fun onDestroyView() {
        presenter?.detach()
        super.onDestroyView()
    }
}