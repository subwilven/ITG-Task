package com.islam.basepropject.project_base.utils

import android.Manifest
import android.annotation.SuppressLint
import com.islam.basepropject.project_base.base.fragments.BaseFragment

object PermissionsManager {

    val READ_SMS = Manifest.permission.READ_SMS
    val READ_CONTACTS = Manifest.permission.READ_CONTACTS
    val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    val NETWORK_STATE = Manifest.permission.ACCESS_NETWORK_STATE
    val INTERNET = Manifest.permission.INTERNET
    val CAMERA = Manifest.permission.CAMERA
    val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

    @SuppressLint("CheckResult")
    fun requestPermission(fragment: BaseFragment<*>,
                          vararg permissions: String,
                          onGranted: (() -> Unit)? =null,
                          onDenied: (() -> Unit)? = null) {
//        RxPermissions(fragment)
//                .requestEachCombined(*permissions)
//                .subscribeOn(Schedulers.io())
//               // .observeOn(AndroidSchedulers.mainThread())
//                .subscribe { permission ->
//
//                    if (permission.granted) {
//                        onGranted?.invoke()
//                    } else if (permission.shouldShowRequestPermissionRationale) {
//                        onDenied?.invoke()
//                    } else {
//                        IntentManager.openAppSettings()
//                    }
//                }
//
                }
    }
