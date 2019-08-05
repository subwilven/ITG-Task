package com.islam.basepropject.project_base.utils

import android.content.Context
import androidx.preference.PreferenceManager

object PrefManager {

    private const val PREF_LANGUAGE = "language"
    private const val PREF_FIRST_LAUNCH = "first_launch"

    fun saveFirstLaunch(context: Context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREF_FIRST_LAUNCH, false).apply()
    }

    fun isFristLaunch(context: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_FIRST_LAUNCH, true)
    }

    fun saveAppLanguage(context: Context, lang: String) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_LANGUAGE, lang).commit()
    }

    fun getAppLanguage(context: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_LANGUAGE, "en")
    }
}
