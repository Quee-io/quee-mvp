package io.quee.mvp.manager

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

class AppManager private constructor() {
    init {
        instance = this
    }

    fun addActivity(activity: Activity?) {
        activityStack!!.add(activity)
    }

    fun currentActivity(): Activity? {
        return activityStack.lastElement()
    }

    @JvmOverloads
    fun finishActivity(activity: Activity? = activityStack.lastElement()) {
        if (activity != null) {
            activityStack.remove(activity)
            activity.finish()
        }
    }

    fun finishActivity(cls: Class<*>) {
        for (activity in activityStack) {
            if (activity.javaClass == cls) {
                finishActivity(activity)
            }
        }
    }

    fun finishAllActivity() {
        var i = 0
        val size = activityStack.size
        while (i < size) {
            if (null != activityStack[i]) {
                activityStack[i]!!.finish()
            }
            i++
        }
        activityStack.clear()
    }

    @SuppressLint("MissingPermission")
    fun AppExit(context: Context) {
        try {
            finishAllActivity()
            val activityMgr =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityMgr.killBackgroundProcesses(context.packageName)
            System.exit(0)
        } catch (e: Exception) {
        }
    }

    val isAppExit: Boolean
        get() = activityStack.isEmpty()

    companion object {
        private var activityStack: Stack<Activity> = Stack()
        private var instance: AppManager = AppManager()
        val appManager: AppManager
            get() = instance
    }
}