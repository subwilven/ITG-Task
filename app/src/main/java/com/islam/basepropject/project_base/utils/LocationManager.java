package com.islam.basepropject.project_base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

public final class LocationManager {

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private Context mContext;
    private Activity mActivity;


    //TODO need to implement CONSUMER to take the location updates recicved to your activity
    public LocationManager(Fragment fragment, LocationRequest locationRequest) {
        this.mContext = fragment.getContext();
        this.mActivity = fragment.getActivity();
        this.mLocationRequest = locationRequest;
        init();    }

    public LocationManager(Activity mActivity,LocationRequest locationRequest) {
        this.mContext = mActivity;
        this.mActivity = mActivity;
        this.mLocationRequest = locationRequest;
        init();
    }

    public void init(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
        createLocationRequest();
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }

        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability) {
            super.onLocationAvailability(locationAvailability);
        }
    };

    private BroadcastReceiver mGpsSwitchStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() != null && intent.getAction()
                    .matches("android.location.PROVIDERS_CHANGED")) {
                startLocationTracker();
                // to run once after the user enable the gps after the dialog appears to him
                mContext.unregisterReceiver(this);
            }
        }
    };


    protected void createLocationRequest() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(mContext);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(locationSettingsResponse -> startLocationTracker());
        task.addOnFailureListener(e -> {
            if (e instanceof ResolvableApiException) {
                LocationManager.this.registerGpsBroadcastReceiver(e);
            }
        });

    }

    private void registerGpsBroadcastReceiver(Exception e){
        try {
            // Show the dialog by calling startResolutionForResult(),
            // and check the result in onActivityResult().
            mContext
                    .registerReceiver(mGpsSwitchStateReceiver,
                            new IntentFilter(android.location.LocationManager.PROVIDERS_CHANGED_ACTION));

            ResolvableApiException resolvable = (ResolvableApiException) e;
            resolvable.startResolutionForResult(mActivity, 50);
            //TODO should make the acitity recicve the result of enabiling gps dialog

        } catch (IntentSender.SendIntentException sendEx) {
            // Ignore the error.
        }
    }


    private void startLocationTracker() {
        if (mContext != null)
            if (ActivityCompat.checkSelfPermission(mContext,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestLocationPermission();
            } else {
                //remove all callbacks if exist so preventing to have more than one on location change updates
                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            }
    }

    private void requestLocationPermission() {
        //TODO
    }

    public void release() {
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

}
