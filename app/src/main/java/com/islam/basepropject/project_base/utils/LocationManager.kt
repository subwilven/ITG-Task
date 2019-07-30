package com.islam.basepropject.project_base.utils

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.IntentSender
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task

class LocationManager {

    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var mContext: Context? = null
    private var mActivity: Activity? = null

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            if (locationResult == null) {
                return
            }

        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
            super.onLocationAvailability(locationAvailability)
        }
    }

    private val mGpsSwitchStateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action != null && intent.action!!
                            .matches("android.location.PROVIDERS_CHANGED".toRegex())) {
                startLocationTracker()
                // to run once after the user enable the gps after the dialog appears to him
                mContext!!.unregisterReceiver(this)
            }
        }
    }


    //TODO need to implement CONSUMER to take the location updates recicved to your activity
    constructor(fragment: Fragment, locationRequest: LocationRequest) {
        this.mContext = fragment.context
        this.mActivity = fragment.activity
        this.mLocationRequest = locationRequest
        init()
    }

    constructor(mActivity: Activity, locationRequest: LocationRequest) {
        this.mContext = mActivity
        this.mActivity = mActivity
        this.mLocationRequest = locationRequest
        init()
    }

    fun init() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity!!)
        createLocationRequest()
    }


    protected fun createLocationRequest() {

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest!!)

        val client = LocationServices.getSettingsClient(mContext!!)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { startLocationTracker() }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                this@LocationManager.registerGpsBroadcastReceiver(e)
            }
        }

    }

    private fun registerGpsBroadcastReceiver(e: Exception) {
        try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            mContext!!
                    .registerReceiver(mGpsSwitchStateReceiver,
                            IntentFilter(android.location.LocationManager.PROVIDERS_CHANGED_ACTION))

            val resolvable = e as ResolvableApiException
            resolvable.startResolutionForResult(mActivity, 50)
            //TODO should make the acitity recicve the result of enabiling gps dialog

        } catch (sendEx: IntentSender.SendIntentException) {
            // Ignore the error.
        }

    }


    private fun startLocationTracker() {
        if (mContext != null)
            if (ActivityCompat.checkSelfPermission(mContext!!,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext!!,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission()
            } else {
                //remove all callbacks if exist so preventing to have more than one on location change updates
                mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
                mFusedLocationClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, null)
            }
    }

    private fun requestLocationPermission() {
        //TODO
    }

    fun release() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
        }
    }

}
