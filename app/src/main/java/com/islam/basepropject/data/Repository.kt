package com.islam.basepropject.data

import com.islam.basepropject.BuildConfig
import com.islam.basepropject.pojo.Article
import com.islam.basepropject.pojo.Auth
import com.islam.basepropject.project_base.POJO.ApiResponse

import com.islam.basepropject.project_base.base.other.network.NetworkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class Repository{

    suspend fun getProvidersList(): ApiResponse<List<Article>> {
        //delay(5000)
        return NetworkModel.clientApi!!.getProviders("sources", BuildConfig.NEWS_API_KEY)
        // return NetworkModel.clientApi!!.getProviders()
    }

    suspend fun login(email: String, password: String): ApiResponse<Auth> {
        return withContext(Dispatchers.IO) {
            delay(2000)
            ApiResponse(status = "",sources = Auth(isLogged = true))
        }
    }

    suspend fun signUp(email: String, password: String, username: String): ApiResponse<Auth>  {
        return withContext(Dispatchers.IO) {
            delay(2000)
            ApiResponse(status = "",sources = Auth(isLogged = true))
        }
    }

}

