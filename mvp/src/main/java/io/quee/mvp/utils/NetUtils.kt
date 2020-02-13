package io.quee.mvp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetUtils private constructor() {
    companion object {
        @SuppressLint("MissingPermission")
        fun isConnected(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            if (null != info && info.isConnected) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
            return false
        }

        @SuppressLint("MissingPermission")
        fun isWifi(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            if (null != info) {
                if (info.type == ConnectivityManager.TYPE_WIFI) {
                    return true
                }
            }
            return false
        }

        fun openSetting(activity: Activity, requestCode: Int) {
            val intent = Intent("/")
            val cm = ComponentName(
                "com.android.settings",
                "com.android.settings.WirelessSettings"
            )
            intent.component = cm
            intent.action = Intent.ACTION_VIEW
            activity.startActivityForResult(intent, requestCode)
        }
    }

    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }
}