package com.islam.basepropject.dagger.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.islam.basepropject.data.ClientApi;
import com.islam.basepropject.project_base.utils.network.ConnectivityInterceptor;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class NetworkModel {


    @Provides
    @Singleton
    static Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    static Interceptor provideInterceptor() {
        return new ConnectivityInterceptor();
    }

    @Provides
    @Singleton
    static OkHttpClient provideOkhttpClient(Interceptor interceptor) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(7, TimeUnit.SECONDS);
        builder.connectTimeout(7, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofitForNewsApis(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://newsapi.org/v2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    static ClientApi provideArticleApi(Retrofit retrofit) {
        return retrofit.create(ClientApi.class);
    }

}
