package com.islam.basepropject.project_base.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class AppManager {

    private AppManager() {}

    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id="+ appPackageName)));
        }
    }

    public static void checkForAppUpdate(){

    }

}
