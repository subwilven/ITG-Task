package com.islam.basepropject.dagger.network

import com.islam.basepropject.data.ClientApi

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [NetworkModel::class])
interface NetworkComponent {

    val clientApi: ClientApi

}
