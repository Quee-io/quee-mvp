package io.quee.mvp.common

import io.quee.mvp.manager.RxManager


abstract class QueePresenter<M : QueeModel?, V : QueeView?> {
    protected var view: V? = null
    protected var model: M? = null
    private var manager = RxManager()

    fun attach(view: V, model: M) {
        this.view = view
        this.model = model
        onStart()
    }

    fun detach() {
        manager.clear()
        view = null
        model = null
    }

    protected fun manager(): RxManager {
        return manager
    }

    protected fun onStart() {}
}