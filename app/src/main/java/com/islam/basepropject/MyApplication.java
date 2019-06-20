package com.islam.basepropject;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;

import androidx.appcompat.app.AppCompatDelegate;

import com.islam.basepropject.dagger.network.DaggerNetworkComponent;
import com.islam.basepropject.dagger.network.NetworkComponent;
import com.islam.basepropject.project_base.common.boradcast.ConnectivityReceiver;
import com.islam.basepropject.project_base.utils.LocalManager;
import com.islam.basepropject.project_base.utils.NotificationManager;

public class MyApplication extends Application {

    //TODO replace google services file withyour own file

    private static MyApplication mInstance;
    BroadcastReceiver broadcastReceiver;
    NetworkComponent networkComponent;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocalManager.updateResources(base, LocalManager.getLanguage(base)));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocalManager.setLocale(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initConnectivityBroadcast();
        NotificationManager.initNotificationChannels(this);
    }

    public NetworkComponent getNetworkArticleComponent() {
        if ((networkComponent == null))
            networkComponent = DaggerNetworkComponent.create();
        return networkComponent;
    }

    public void initConnectivityBroadcast(){
        broadcastReceiver = new ConnectivityReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.listener = listener;
    }

    @Override
    public void onTerminate() {
        unregisterReceiver(broadcastReceiver);
        super.onTerminate();
    }

}
