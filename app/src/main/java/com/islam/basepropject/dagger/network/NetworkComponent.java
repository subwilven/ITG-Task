package com.islam.basepropject.dagger.network;

import com.islam.basepropject.data.ClientApi;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModel.class})
public interface NetworkComponent {

    ClientApi getClientApi();

}
