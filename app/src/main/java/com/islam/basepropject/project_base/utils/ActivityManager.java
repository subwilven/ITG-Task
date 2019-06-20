package com.islam.basepropject.project_base.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.islam.basepropject.MyApplication;

public final class ActivityManager {

    private ActivityManager() {
    }

    public static void setVisibility(int v, View... views) {
        for (View view : views) {
            if (view != null)
                view.setVisibility(v);
        }
    }

    public static void showToastShort(int stringId) {
        Context context = MyApplication.getInstance().getApplicationContext();
        showToast(context.getString(stringId), Toast.LENGTH_SHORT);
    }

    public static void showToastLong(int stringId) {
        Context context = MyApplication.getInstance().getApplicationContext();
        showToast(context.getString(stringId), Toast.LENGTH_LONG);
    }

    public static void showToastShort(String string) {
        showToast(string, Toast.LENGTH_SHORT);
    }

    public static void showToastLong(String string) {
        showToast(string, Toast.LENGTH_LONG);
    }

    private static void showToast(String string, int duration) {
        Toast.makeText(MyApplication.getInstance().getApplicationContext(), string, duration).show();
    }
}
