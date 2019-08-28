package com.islam.basepropject.data

import com.islam.basepropject.BuildConfig
import com.islam.basepropject.pojo.Article
import com.islam.basepropject.project_base.POJO.ApiResponse

import com.islam.basepropject.project_base.base.other.network.NetworkModel
import kotlinx.coroutines.delay


class Repository {

     suspend fun getProvidersList() : ApiResponse<List<Article>> {
         delay(5000)
        return NetworkModel.clientApi!!.getProviders("sources", BuildConfig.NEWS_API_KEY)
       // return NetworkModel.clientApi!!.getProviders()
    }
}
