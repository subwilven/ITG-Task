package com.islam.basepropject.project_base.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.provider.Settings;

import androidx.browser.customtabs.CustomTabsIntent;

import com.islam.basepropject.MyApplication;
import com.islam.basepropject.R;

public final class IntentManager {
    private IntentManager() {
    }

    private static boolean isPackageInstalledAndEnabled(String packageName, PackageManager packageManager) {
        try {
            return packageManager.getApplicationInfo(packageName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static void launchExternalApp(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    public static void openTabBrowser(Context context, String url) {
        openTabBrowser(context, "https://www.facebook.com/YOUR_PAGE_URL", context.getResources().getColor(R.color.colorPrimary));
    }

    public static void openTabBrowser(Context context, String url, int color) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();

        builder.setToolbarColor(color);
        //set start and exit animations
        builder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left);
        builder.setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(url));
    }

    public static void openAppSettings() {
        Context context = MyApplication.getInstance().getApplicationContext();

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }


    public static void openFacebookPage(Context context) {
        boolean isInstalled = isPackageInstalledAndEnabled("com.facebook.katana",
                context.getPackageManager());

        if (isInstalled) {
            launchExternalApp(context, "fb://page/YOUR_PAGE_ID");
            //ex:fb://page/314086229325675
        } else {
            openTabBrowser(context, "https://www.facebook.com/YOUR_PAGE_URL");
            //ex :https://www.facebook.com/thaipeoplepowerpartypage
        }
    }

    public static void openTwitterPage(Context context) {
        boolean isInstalled = isPackageInstalledAndEnabled("com.twitter.android",
                context.getPackageManager());

        if (isInstalled) {
            launchExternalApp(context, "twitter://user?user_id=YOUR_USER_ID");
            //ex :twitter://user?user_id=1049571254137643009
        } else {
            openTabBrowser(context, "https://twitter.com/YOUR_PAGE_URL");
            //ex :https://twitter.com/0xREjn0OHxcFkQg
        }
    }

    public static void openInstagramPage(Context context) {
        boolean isInstalled = isPackageInstalledAndEnabled("com.instagram.android",
                context.getPackageManager());

        if (isInstalled) {
            launchExternalApp(context, "http://instagram.com/_u/YOUR_PAGE_URL");
            //ex :http://instagram.com/_u/thaipeoplepowerparty
        } else {
            openTabBrowser(context, "https://www.instagram.com/YOUR_PAGE_URL/");
            //ex :https://www.instagram.com/thaipeoplepowerparty/
        }
    }

    public static void openYoutubeChannel(Context context) {
        boolean isInstalled = isPackageInstalledAndEnabled("com.google.android.youtube",
                context.getPackageManager());

        if (isInstalled) {
            launchExternalApp(context, "https://www.youtube.com/channel/YOUR_PAGE_URL");
            //ex :https://www.youtube.com/channel/UC-Mz1RS-96VrxZv_N7mYzEw
        } else {
            openTabBrowser(context, "https://www.youtube.com/channel/YOUR_PAGE_URL");
            //ex :https://www.youtube.com/channel/UC-Mz1RS-96VrxZv_N7mYzEw
        }
    }

    private void openGoogleMapAtSpecificLocation(Context context, Location location) {
        boolean isInstalled = isPackageInstalledAndEnabled("com.google.android.apps.map",
                context.getPackageManager());
        if (isInstalled) {
            Uri uri = Uri.parse("google.navigation:q=" + location.getLatitude() + "," + location.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        }else{
            ActivityManager.showToastLong(R.string.google_map_app_not_found);
        }
    }

    public static void dialNumber(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }
}
