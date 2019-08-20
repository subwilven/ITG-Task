package com.islam.basepropject.project_base.base.other.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.islam.basepropject.BuildConfig
import com.islam.basepropject.data.ClientApi
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.OnViewStatusChange
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object NetworkModel {

    var retrofit: Retrofit? = null
    var clientApi: ClientApi? = null
        get() {
            if (field == null)
                clientApi = getRetrofitObject()!!.create(ClientApi::class.java)
            return field
        }

    private fun getGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    private fun getInterceptor(): Interceptor {
        return ConnectivityInterceptor()
    }


    private fun getOkhttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(7, TimeUnit.SECONDS)
        builder.connectTimeout(7, TimeUnit.SECONDS)
        builder.addInterceptor(getInterceptor())

        //Your headers
        builder.addInterceptor { chain ->
            var request = chain.request()
            val url = request.url().newBuilder().build()
            request = request.newBuilder().url(url)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Api-Access", "application/mobile")
                    .build()
            chain.proceed(request)
        }

        //if debug mood show retrofit logging
        if (BuildConfig.DEBUG) {
            val debugInterceptor = HttpLoggingInterceptor()
            debugInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(debugInterceptor)
        }

        return builder.build()
    }

    private fun getRetrofitObject(): Retrofit? {
        if (retrofit == null)
            retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .baseUrl("http://164.68.105.174:8069/api/")
          //          .baseUrl("https://newsapi.org/v2/")
                    .client(getOkhttpClient())
                    .build()

        return retrofit
    }

}