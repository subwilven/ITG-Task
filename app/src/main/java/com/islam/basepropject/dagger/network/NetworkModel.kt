package com.islam.basepropject.dagger.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.islam.basepropject.data.ClientApi
import com.islam.basepropject.project_base.utils.network.ConnectivityInterceptor

import java.util.concurrent.TimeUnit

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModel {


    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideInterceptor(): Interceptor {
        return ConnectivityInterceptor()
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(interceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(7, TimeUnit.SECONDS)
        builder.connectTimeout(7, TimeUnit.SECONDS)
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitForNewsApis(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://newsapi.org/v2/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    internal fun provideArticleApi(retrofit: Retrofit): ClientApi {
        return retrofit.create(ClientApi::class.java)
    }

}
