package com.islam.basepropject

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.res.Configuration
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatDelegate
import com.islam.basepropject.dagger.network.DaggerNetworkComponent
import com.islam.basepropject.dagger.network.NetworkComponent
import com.islam.basepropject.dagger.network.NetworkModel
import com.islam.basepropject.project_base.common.boradcast.ConnectivityReceiver
import com.islam.basepropject.project_base.utils.LocalManager
import com.islam.basepropject.project_base.utils.NotificationManager

class MyApplication : Application() {
    internal var broadcastReceiver: BroadcastReceiver? = null
    internal var networkComponent: NetworkComponent? = null

    val networkArticleComponent: NetworkComponent
        get() {
            if (networkComponent == null)

                networkComponent =  DaggerNetworkComponent.builder().networkModel(NetworkModel()).build()
            return networkComponent as NetworkComponent
        }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocalManager.updateResources(base, LocalManager.getLanguage(base)))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocalManager.setLocale(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initConnectivityBroadcast()
        NotificationManager.initNotificationChannels(this)
    }

    fun initConnectivityBroadcast() {
        broadcastReceiver = ConnectivityReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(broadcastReceiver, filter)
    }

    fun setConnectivityListener(listener: ConnectivityReceiver.ConnectivityReceiverListener?) {
        ConnectivityReceiver.listener = listener
    }

    override fun onTerminate() {
        unregisterReceiver(broadcastReceiver)
        super.onTerminate()
    }

    companion object {

        //TODO replace google services file withyour own file

        @get:Synchronized
        var instance: MyApplication? = null
            private set

        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

}
