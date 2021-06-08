package io.quee.mvp.ui.sheet

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import io.quee.mvp.base.MvpQueeStructure
import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView

abstract class QueeMvpBottomSheetFragment<VB : ViewBinding, P : QueePresenter<M, V>, M : QueeModel, V : QueeView>
    (layout: Int) :
    QueeBottomSheetFragment<VB>(layout), MvpQueeStructure<VB, P, M, V> {

    private var presenter: P? = null

    final override fun VB.afterBindingLayout(bundle: Bundle?) {
        presenter = createPresenter().apply {
            attach(
                view = createView(),
                model = createModel(),
                lifecycle = lifecycle
            )
        }
        afterMvpInit(bundle)
        initData()
    }

    protected fun executeInPresenter(command: P.() -> Unit) {
        presenter?.apply(command)
    }

    protected abstract fun initData()

    @CallSuper
    override fun onResume() {
        super.onResume()
        presenter?.apply {
            attach(
                view = createView(),
                model = createModel(),
                lifecycle = lifecycle
            )
        }
    }
}