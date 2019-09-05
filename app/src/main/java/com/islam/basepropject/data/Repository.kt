package com.islam.basepropject.data

import android.util.Log
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
        delay(4000)
        return NetworkModel.clientApi!!.getProviders("sources", BuildConfig.NEWS_API_KEY)
    }

    suspend fun login(email: String, password: String): ApiResponse<Auth> {
        Log.i("NETWORKING","start Loing")
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

