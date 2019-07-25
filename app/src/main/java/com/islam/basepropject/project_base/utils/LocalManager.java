package com.islam.basepropject.project_base.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.preference.PreferenceManager;

import java.util.Locale;

public class LocalManager {

    public static void setLocale(Context c) {
        setNewLocale(c, getLanguage(c));
    }

    public static void setNewLocale(Context c, String language) {
        persistLanguage(c, language);
        updateResources(c, language);
    }

    public static boolean isCurrentLanguage(Context c,String lang) {
        return getLanguage(c).equalsIgnoreCase(lang);
    }

    public static String getLanguage(Context c) {
        return PreferenceManager.getDefaultSharedPreferences(c).getString("language","en");
    }

    private static void persistLanguage(Context c, String language) {
       // AuthSharedDataSource.setShareLocale(c, language);
    }

    public static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());

        }
        return context;
    }
}
