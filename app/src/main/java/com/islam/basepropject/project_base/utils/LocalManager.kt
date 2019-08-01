package com.islam.basepropject.project_base.utils

import android.content.Context
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import java.util.*

object LocalManager {

    fun setLocale(c: Context) {
        setNewLocale(c, getLanguage(c))
    }

    fun setNewLocale(c: Context, language: String?) {
        persistLanguage(c, language)
        updateResources(c, language)
    }

    fun isCurrentLanguage(c: Context, lang: String): Boolean {
        return getLanguage(c)!!.equals(lang, ignoreCase = true)
    }

    fun getLanguage(c: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("language", "en")
    }

    private fun persistLanguage(c: Context, language: String?) {
        PreferenceManager.getDefaultSharedPreferences(c).edit().putString("language", language).apply()
    }

    fun updateResources(context: Context, language: String?): Context {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)

        val res = context.resources
        val config = Configuration(res.configuration)

        config.setLocale(locale)
        context = context.createConfigurationContext(config)

        return context
    }
}
