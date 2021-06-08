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

    private var lifecycle: Lifecycle? = null
    private var manager = RxManager()


    open fun attach(view: V, model: M) {
        attach(view, model, null)
    }

    open fun attach(
        view: V,
        model: M,
        lifecycle: Lifecycle? = null
    ) {
        this.view = view
        this.model = model
        this.lifecycle = lifecycle?.apply {
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
        lifecycle?.removeObserver(this)
        view = null
        model = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        detach()
    }
}