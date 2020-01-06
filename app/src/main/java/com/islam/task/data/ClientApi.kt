package com.islam.task.data

import com.islam.task.pojo.Item
import com.islam.task.pojo.Marvel
import com.islam.task.project_base.POJO.ApiResponse

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientApi {

    @GET("characters")
    suspend fun getMarvels(
            @Query("apikey") key: String,
            @Query("ts") timestamp: String,
            @Query("offset") offset: String,
            @Query("hash") hash:String): ApiResponse<List<Marvel>>

    @GET("characters")
    suspend fun searchMarvelsByName(
            @Query("apikey") key: String,
            @Query("ts") timestamp: String,
            @Query("offset") offset: String,
            @Query("nameStartsWith") name: String,
            @Query("hash") hash:String): ApiResponse<List<Marvel>>

    @GET("characters/{path}/comics")
    suspend fun getComics(
            @Path(value = "path") id: String,
            @Query("apikey") key: String,
            @Query("ts") timestamp: String,
            @Query("hash") hash:String): ApiResponse<List<Item>>


    @GET("characters/{path}/events")
    suspend fun getEvents(
            @Path(value = "path") id: String,
            @Query("apikey") key: String,
            @Query("ts") timestamp: String,
            @Query("hash") hash:String): ApiResponse<List<Item>>

    @GET("characters/{path}/series")
    suspend fun getSeries(
            @Path(value = "path") id: String,
            @Query("apikey") key: String,
            @Query("ts") timestamp: String,
            @Query("hash") hash:String): ApiResponse<List<Item>>


    @GET("characters/{path}/stories")
    suspend fun getStories(
            @Path(value = "path") id: String,
            @Query("apikey") key: String,
            @Query("ts") timestamp: String,
            @Query("hash") hash:String): ApiResponse<List<Item>>

}
