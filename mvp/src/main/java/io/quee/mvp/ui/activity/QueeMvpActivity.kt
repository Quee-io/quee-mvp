package io.quee.mvp.ui.activity

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import io.quee.mvp.base.MvpQueeStructure
import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView

abstract class QueeMvpActivity<B : ViewDataBinding, P : QueePresenter<M, V>, M : QueeModel, V : QueeView>
    (layout: Int, isSecure: Boolean = false) :
    QueeActivity<B>(layout, isSecure), MvpQueeStructure<P, M, V> {

    private var model: M? = null
    private var view: V? = null
    private var presenter: P? = null

    final override fun afterBindingLayout(bundle: Bundle?) {
        model = this.createModel()
        view = this.createView()
        presenter = this.createPresenter()
        presenter?.attach(view!!, model!!)
        afterMvpInit(bundle)
        initData()
    }

    protected fun executeInPresenter(command: P.() -> Unit) {
        presenter?.command()
    }

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        presenter?.detach()
    }
}