package com.islam.basepropject.data

import com.google.gson.JsonElement

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientApi {

    @GET("{path}    ")
    fun getProviders(
            @Path(value = "path") path: String,
            @Query("apiKey") key: String): Single<JsonElement>
}
