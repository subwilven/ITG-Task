package com.islam.task.data

import android.util.Log
import com.islam.task.BuildConfig
import com.islam.task.pojo.Article
import com.islam.task.pojo.Auth
import com.islam.task.project_base.POJO.ApiResponse

import com.islam.task.project_base.base.other.network.NetworkModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class Repository{

    suspend fun getProvidersList(): ApiResponse<List<Article>> {
        delay(3500)
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

