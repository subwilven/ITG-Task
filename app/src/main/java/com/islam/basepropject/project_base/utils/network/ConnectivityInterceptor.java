package com.islam.basepropject.project_base.utils.network;

import com.islam.basepropject.MyApplication;
import com.islam.basepropject.project_base.utils.NetworkManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        if (!NetworkManager.isNetworkConnected(MyApplication.getInstance())) {
            throw new NoConnectivityException();
        }
        else {
            return chain.proceed(chain.request());
        }
    }


    public static class NoConnectivityException extends IOException {

    }
}
