package com.islam.basepropject.data;

import com.google.gson.JsonElement;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClientApi  {

    @GET("{path}    ")
    Single<JsonElement> getProviders(
            @Path(value = "path") String path,
            @Query("apiKey") String key);
}
