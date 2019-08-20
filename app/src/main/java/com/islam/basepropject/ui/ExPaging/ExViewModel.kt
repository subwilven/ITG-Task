package com.islam.basepropject.ui.ExPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.islam.basepropject.project_base.POJO.NetworkRequestState
import com.islam.basepropject.project_base.base.other.BaseDataFactory
import com.islam.basepropject.project_base.base.other.BaseDataSource
import com.islam.basepropject.project_base.base.other.BaseViewModel

class ExViewModel : BaseViewModel() {
    private var dataFactory: BaseDataFactory<String>? = null

    var networkState: LiveData<NetworkRequestState>? = null
        private set

    val orders: MutableLiveData<PagedList<String>> = MutableLiveData()


    fun loadOrders() {
        if (orders.value != null) return

        if (dataFactory == null)
            dataFactory = object : BaseDataFactory<String>(this) {
                override fun onCreateDataSource(baseViewModel: BaseViewModel): BaseDataSource<String> {
                    return ExDataSource(this@ExViewModel)
                }
            }

        networkState = Transformations.switchMap(dataFactory!!.mutableDataSource
        ) { input -> dataFactory!!.mutableDataSource.value!!.networkState }

        val pagedListConfig = PagedList.Config.Builder()
                .setInitialLoadSizeHint(2)
                .setPageSize(20).build()

//        addDisposable(RxPagedListBuilder(dataFactory!!, pagedListConfig)
//                .buildObservable()
//                .observeOn(schedulerProvider.ui())
//                .subscribeOn(schedulerProvider.io())
//                .subscribe { orders?.value = it})
    }
}
