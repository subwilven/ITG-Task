package com.islam.basepropject.project_base.utils;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PermissionsManager {

    public static final String READ_SMS = Manifest.permission.READ_SMS;
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE;
    public static final String INTERNET = Manifest.permission.INTERNET;
    public static final String CAMERA = Manifest.permission.CAMERA;
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    @SuppressLint("CheckResult")
    public static void requestPermission(FragmentActivity activity, String... permissions) {
        new RxPermissions(activity)
                .requestEachCombined(permissions)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(permission -> {
                    if (permission.granted) {
                        ActivityManager.showToastShort("granted");
                    } else if (permission.shouldShowRequestPermissionRationale) {
                        ActivityManager.showToastShort("not granted");
                    } else {
                        IntentManager.openAppSettings();
                    }
                });

    }
}
