package com.islam.basepropject.project_base.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.islam.basepropject.project_base.base.fragments.BaseFragment

@SuppressLint("StaticFieldLeak")
class LocationUtils : LifecycleObserver {

    companion object {
        var instance: LocationUtils? = null
            get() {
                if (field == null)
                    field = LocationUtils()
                return field
            }
    }

    private var locationRequest: LocationRequest? = null
    var fragment: BaseFragment<*>? = null
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    var onLocation: ((location: Location) -> Unit)? =null
    var onFailed: (()->Unit)? =null
    var singleRequest = false // when user need the location just once this should be true

    fun getUserLocationSingle(fragment: BaseFragment<*>,
                               priority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY,
                               onFailed: (()->Unit) ={},
                               onLocation: (location: Location) -> Unit) {
        this.onLocation = onLocation
        this.onFailed = onFailed
        this.fragment = fragment
        this.singleRequest= true

        locationRequest = LocationRequest.create()?.apply {
            this.priority = priority
        }

      initfusedClient()
    }



    fun getUserLocationUpdates(fragment: BaseFragment<*>,
                               priority: Int = LocationRequest.PRIORITY_HIGH_ACCURACY,
                               interval: Long = 5000,
                               fastestInterval: Long = 2000,
                               onFailed: (()->Unit) ={},
                               onLocation: (location: Location) -> Unit) {
        this.onLocation = onLocation
        this.onFailed = onFailed
        this.fragment = fragment

        locationRequest = LocationRequest.create()?.apply {
            this.interval = interval
            this.fastestInterval = fastestInterval
            this.priority = priority
        }

        initfusedClient()
    }

    fun initfusedClient(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(fragment!!.context!!)
        createLocationRequest()
    }


    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            if (locationResult == null)  return
            onLocation?.invoke(locationResult.lastLocation)
            if(singleRequest) release()
        }

        override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
            super.onLocationAvailability(locationAvailability)
            locationAvailability?.isLocationAvailable?.run {onFailed?.invoke()}
        }
    }


    private fun createLocationRequest() {

        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest!!)

        val client = LocationServices.getSettingsClient(fragment?.context!!)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { checkPermissionAndStartTrack() }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                registerGpsBroadcastReceiver(e)
            }
        }

    }


    fun checkPermissionAndStartTrack() {
        //by adding this observer requestLocationPermission() method will be called
        fragment?.lifecycle?.addObserver(this)
    }

    @SuppressLint("MissingPermission")
    private fun startTracking() {
        //remove all callbacks if exist so preventing to have more than one on location change updates
        mFusedLocationClient!!.removeLocationUpdates(mLocationCallback)
        mFusedLocationClient!!.requestLocationUpdates(locationRequest, mLocationCallback, null)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun requestLocationPermission() {
        fragment!!.requestPermission(PermissionsManager.COARSE_LOCATION) { startTracking() }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopLocationUpdates() {
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun release(){
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
        mFusedLocationClient = null
        fragment = null
        onFailed= null
        onLocation=null
    }

    private fun registerGpsBroadcastReceiver(e: Exception) {
        runCatching {
            val resolvable = e as ResolvableApiException
            resolvable.startResolutionForResult(fragment?.activity, 50)
        }
    }

}