package com.islam.basepropject.data

import com.islam.basepropject.pojo.Article
import com.islam.basepropject.project_base.POJO.ApiResponse

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientApi {

    @GET("hr.leave/get_emp_leaves?employeeId=1")
    suspend fun getProviders(): ApiResponse<List<Article>>

    @GET("{path}")
    suspend fun getProviders(
            @Path(value = "path") path: String,
            @Query("apiKey") key: String): ApiResponse<List<Article>>
}
