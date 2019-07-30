package com.islam.basepropject.data

import com.google.gson.JsonElement
import com.islam.basepropject.BuildConfig
import com.islam.basepropject.MyApplication
import com.islam.basepropject.dagger.network.NetworkComponent

import io.reactivex.Single

class Repository {

    val providresList: Single<JsonElement>
        get() {
            val component = MyApplication.instance!!.networkArticleComponent
            val clientApi = component.clientApi
            return clientApi.getProviders("sources", BuildConfig.NEWS_API_KEY)
        }
}
