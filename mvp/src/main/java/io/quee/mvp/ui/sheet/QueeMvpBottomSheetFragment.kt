package io.quee.mvp.ui.sheet

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import io.quee.mvp.base.MvpQueeStructure
import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView

abstract class QueeMvpBottomSheetFragment<B : ViewDataBinding, P : QueePresenter<M, V>, M : QueeModel, V : QueeView>
    (layout: Int) :
    QueeBottomSheetFragment<B>(layout), MvpQueeStructure<P, M, V> {

    var model: M = this.createModel()
    var view: V = this.createView()
    var presenter: P = this.createPresenter()

    final override fun afterBindingLayout(bundle: Bundle?) {
        presenter.attach(view, model)
        afterMvpInit(bundle)
    }

    final override fun onResume() {
        super.onResume()
        initData()
    }

    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }
}