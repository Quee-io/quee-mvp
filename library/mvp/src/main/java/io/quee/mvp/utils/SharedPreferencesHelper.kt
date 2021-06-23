package io.quee.mvp.utils

import android.content.SharedPreferences

/**
 * Created By Ibrahim Al-Tamimi
 * Email: ibm.iloom@gmail.com
 */
class SharedPreferencesHelper private constructor(private val preferences: SharedPreferences) {
    private fun checkIfIsValidHelper() {
        checkNotNull(helper) { "You must call init method before use!!" }
    }

    fun reader(): Reader {
        checkIfIsValidHelper()
        return Reader(preferences)
    }

    fun writer(): Writer {
        checkIfIsValidHelper()
        return Writer(preferences)
    }

    class Reader(private val preferences: SharedPreferences) {
        operator fun get(key: String?, defValue: String?): String? {
            return preferences.getString(key, defValue)
        }

        operator fun get(key: String?, defValue: Int?): Int {
            return preferences.getInt(key, defValue!!)
        }

        operator fun get(key: String?, defValue: Float?): Float {
            return preferences.getFloat(key, defValue!!)
        }

        operator fun get(key: String?, defValue: Boolean?): Boolean {
            return preferences.getBoolean(key, defValue!!)
        }

        operator fun get(key: String?, defValue: Long?): Long {
            return preferences.getLong(key, defValue!!)
        }

    }

    class Writer(private val preferences: SharedPreferences) {
        fun put(
            key: String?,
            value: String?,
        ): Writer {
            preferences.edit().putString(key, value)
                .apply()
            return this
        }

        fun put(
            key: String?,
            value: Int?,
        ): Writer {
            preferences.edit().putInt(key, value!!)
                .apply()
            return this
        }

        fun put(
            key: String?,
            value: Long?,
        ): Writer {
            preferences.edit().putLong(key, value!!)
                .apply()
            return this
        }

        fun put(
            key: String?,
            value: Boolean?,
        ): Writer {
            preferences.edit().putBoolean(key, value!!)
                .apply()
            return this
        }

        fun put(
            key: String?,
            value: Float?,
        ): Writer {
            preferences.edit().putFloat(key, value!!)
                .apply()
            return this
        }

        fun clear() {
            preferences.edit().clear().apply()
        }

    }

    companion object {
        private lateinit var helper: SharedPreferencesHelper
        fun init(preferences: SharedPreferences) {
            helper = SharedPreferencesHelper(preferences)
        }

        fun get(): SharedPreferencesHelper {
            return helper
        }
    }

    init {
        helper = this
    }
}