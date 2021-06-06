package io.quee.mvp.common

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import io.quee.mvp.manager.RxManager


@SuppressLint("StaticFieldLeak")
abstract class QueePresenter<M : QueeModel?, V : QueeView?> : ViewModel(), LifecycleObserver {
    protected var view: V? = null
    protected var model: M? = null

    private var viewLifecycle: Lifecycle? = null
    private var manager = RxManager()


    open fun attach(view: V, model: M, viewLifecycle: Lifecycle? = null) {
        this.view = view
        this.model = model
        this.viewLifecycle = viewLifecycle?.apply {
            addObserver(this@QueePresenter)
        }
        onStart()
    }

    open fun detach() {
        manager.clear()
        view = null
        model = null
    }

    protected fun manager() = manager

    protected fun onStart() {}

    override fun onCleared() {
        super.onCleared()
        manager.clear()
        view = null
        model = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        view = null
        viewLifecycle = null
    }
}