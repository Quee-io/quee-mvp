package io.quee.mvp.manager

import android.util.Log.d
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.Subject
import java.util.concurrent.ConcurrentHashMap

class RxBus private constructor() {
    private val subjectMapper = ConcurrentHashMap<Any, MutableList<Subject<*>>>()

    fun unregister(
        tag: Any,
        observable: Observable<*>,
    ): RxBus {
        val subjects: MutableList<Subject<*>>? = subjectMapper[tag]
        if (null != subjects) {
            subjects.remove(observable)
            if (isEmpty(subjects)) {
                subjectMapper.remove(tag)
                d("unregister", tag.toString() + "  size:" + subjects.size)
            }
        }
        return instance()
    }

    companion object {
        private var instance: RxBus? = null

        @Synchronized
        fun instance(): RxBus {
            if (null == instance) {
                instance = RxBus()
            }
            return instance!!
        }

        private fun isEmpty(collection: Collection<Subject<*>>?): Boolean {
            return null == collection || collection.isEmpty()
        }
    }
}