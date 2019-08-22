package com.islam.basepropject.project_base.base.other

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PageKeyedDataSource
import com.islam.basepropject.project_base.POJO.NetworkRequestState
import com.islam.basepropject.project_base.views.OnViewStatusChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseDataSource<T>(val baseViewModel: BaseViewModel) : PageKeyedDataSource<Long, T>() {

    abstract suspend fun fetchData(key: Int): List<T>

    override fun  loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, T>) {

        baseViewModel.viewModelScope.launch(Dispatchers.Main) {
            val result = fetchData(1)
            callback.onResult(result, null, 1L)
        }
    }


    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, T>) {

    }


    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, T>) {

        baseViewModel.viewModelScope.launch(Dispatchers.Main) {
            val nextKey = (params.key + 1).toInt().toLong()
            val result = fetchData((params.key + 1).toInt())
            callback.onResult(result, nextKey)
        }

    }
}