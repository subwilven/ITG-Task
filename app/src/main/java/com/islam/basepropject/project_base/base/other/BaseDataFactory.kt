package com.islam.basepropject.project_base.base.other

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

abstract class BaseDataFactory<T>(private val baseViewModel: BaseViewModel<*>) : DataSource.Factory<Long, T>() {

    val mutableDataSource: MutableLiveData<BaseDataSource<T>>
    abstract fun onCreateDataSource(baseViewModel: BaseViewModel<*>): BaseDataSource<T>

    init {
        this.mutableDataSource = MutableLiveData()
    }

    override fun create(): DataSource<Long,T> {
        val dataSource = onCreateDataSource(baseViewModel)
        mutableDataSource.postValue(dataSource)
        return dataSource
    }
}