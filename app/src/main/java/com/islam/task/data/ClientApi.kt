package com.islam.task.data

import com.islam.task.pojo.Article
import com.islam.task.project_base.POJO.ApiResponse

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
