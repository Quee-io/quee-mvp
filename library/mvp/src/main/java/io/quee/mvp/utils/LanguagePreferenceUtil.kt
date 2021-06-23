package io.quee.mvp.utils

object LanguagePreferenceUtil {
    private const val CHOOSE_LANGUAGE = "CHOOSE_LANGUAGE"
    private const val LANGUAGE = "LANGUAGE"
    fun updateLanguage(language: String) {
        SharedPreferencesHelper.get()
            .writer()
            .put(LANGUAGE, language)
            .put(CHOOSE_LANGUAGE, true)
    }

    val language: String
        get() = SharedPreferencesHelper.get()
            .reader()[LANGUAGE, "en"]!!

    val chooseLanguage: Boolean
        get() = SharedPreferencesHelper.get()
            .reader()[LANGUAGE, false]
}