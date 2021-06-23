package io.quee.mvp.scene.ui.app_compat_scene

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import io.quee.mvp.base.MvpQueeStructure
import io.quee.mvp.common.QueeModel
import io.quee.mvp.common.QueePresenter
import io.quee.mvp.common.QueeView

/**
 * Created By [*Ibrahim Al-Tamimi *](https://www.linkedin.com/in/iloom/)
 * Created At 24, **Thu June, 2021**
 * Project *mvp-architecture* [https://quee.io]
 */
abstract class MvpAppCompatScene<VB : ViewBinding, P : QueePresenter<M, V>, M : QueeModel, V : QueeView>(
    isSecure: Boolean = false,
) : BaseAppCompatScene<VB>(isSecure), MvpQueeStructure<VB, P, M, V> {
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
        presenter?.command()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        presenter?.apply { attach(createView(), createModel(), lifecycle) }
    }

    protected abstract fun initData()

}