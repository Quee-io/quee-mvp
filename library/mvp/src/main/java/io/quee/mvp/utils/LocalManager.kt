package io.quee.mvp.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*

object LocalManager {
    fun setLocale(c: Context): Context {
        return updateResources(c, language)
    }

    fun setNewLocale(c: Context, language: String) {
        persistLanguage(language)
        updateResources(c, language)
    }

    val language: String
        get() = LanguagePreferenceUtil.language

    private fun persistLanguage(language: String) {
        LanguagePreferenceUtil.updateLanguage(language)
    }

    private fun updateResources(
        context: Context,
        language: String,
    ): Context {
        var updatedContext = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config =
            Configuration(res.configuration)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            updatedContext = context.createConfigurationContext(config)
        }
        return updatedContext
    }

    val deviceLanguage: String
        get() = Locale.getDefault().language

    fun getLocale(res: Resources): Locale {
        val config = res.configuration
        return if (Build.VERSION.SDK_INT >= 24) config.locales[0] else config.locale
    }
}