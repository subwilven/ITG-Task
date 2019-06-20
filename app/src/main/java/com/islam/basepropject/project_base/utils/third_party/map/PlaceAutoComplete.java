package com.islam.basepropject.project_base.utils.third_party.map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

public final class PlaceAutoComplete {

    private  PlaceAutoComplete() {
    }

    public static Intent getPlaceAutoCompleteIntent(Activity context) {

        String locale;
        //get the local country by the network service provider
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        locale = tm.getNetworkCountryIso();

        if (locale == null) // if it still null get it with the device local
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = context.getResources().getConfiguration().getLocales().get(0).getCountry();
            } else {
                locale = context.getResources().getConfiguration().locale.getCountry();
            }

        Intent intent = null;
        try {
            AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                    .setCountry(locale)
                    .build();

            intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(typeFilter)
                    .build(context);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        return intent;

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            Place place = PlaceAutocomplete.getPlace(this, data);
//        }
//    }
}
