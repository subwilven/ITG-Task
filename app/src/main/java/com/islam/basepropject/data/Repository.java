package com.islam.basepropject.data;

import com.google.gson.JsonElement;
import com.islam.basepropject.BuildConfig;
import com.islam.basepropject.MyApplication;
import com.islam.basepropject.dagger.network.NetworkComponent;

import io.reactivex.Single;

public class Repository {

    public Single<JsonElement> getProvidresList(){
        NetworkComponent component = MyApplication.getInstance().getNetworkArticleComponent();
        ClientApi clientApi = component.getClientApi();
        return clientApi.getProviders("sources", BuildConfig.NEWS_API_KEY);
    }
}
