package com.islam.basepropject.project_base.utils.network

import com.islam.basepropject.MyApplication
import com.islam.basepropject.project_base.utils.NetworkManager

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!NetworkManager.isNetworkConnected(MyApplication.instance!!)) {
            throw NoConnectivityException()
        } else {
            chain.proceed(chain.request())
        }
    }


    class NoConnectivityException : IOException()
}
