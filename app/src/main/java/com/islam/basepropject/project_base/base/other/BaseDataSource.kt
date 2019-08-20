package com.islam.basepropject.project_base.base.other

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource

import com.islam.basepropject.project_base.POJO.NetworkRequestState

import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

abstract class BaseDataSource<T>(val baseViewModel: BaseViewModel) : PageKeyedDataSource<Long, T>() {


    val networkState: MutableLiveData<NetworkRequestState>

    init {
        networkState = MutableLiveData()
    }

    abstract fun fetchData(key: Int): Single<List<T>>

    override fun loadInitial(params: PageKeyedDataSource.LoadInitialParams<Long>, callback: PageKeyedDataSource.LoadInitialCallback<Long, T>) {
        baseViewModel.showLoadingFullScreen(true)

        //TODO to be replaced with Rertrofit Observer
        fetchData(1).subscribe(object : SingleObserver<List<T>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(result: List<T>) {
                callback.onResult(result, null, 1L)
                baseViewModel.showLoadingFullScreen(false)
            }

            override fun onError(e: Throwable) {

            }
        })

    }


    override fun loadBefore(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, T>) {

    }


    override fun loadAfter(params: PageKeyedDataSource.LoadParams<Long>, callback: PageKeyedDataSource.LoadCallback<Long, T>) {
        networkState.postValue(NetworkRequestState.LOADING)

        val nextKey = (params.key + 1).toInt().toLong()

        //TODO to be replaced with Rertrofit Observer
        fetchData((params.key + 1).toInt()).subscribe(object : SingleObserver<List<T>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onSuccess(result: List<T>) {
                callback.onResult(result, nextKey)
                networkState.postValue(NetworkRequestState.COMPLETE)
            }

            override fun onError(e: Throwable) {
                networkState.postValue(NetworkRequestState.COMPLETE)
            }
        })
    }
}