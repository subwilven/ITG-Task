package com.islam.basepropject.project_base.common.boradcast;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.islam.basepropject.project_base.utils.NetworkManager;

public class ConnectivityReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener listener;

    public ConnectivityReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        boolean isConnected = NetworkManager.isNetworkConnected(context);
        if (listener != null) {
            listener.onNetworkConnectionChanged(isConnected);
        }
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }
}