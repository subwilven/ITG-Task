package com.islam.basepropject.ui.ExPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder

import com.islam.basepropject.project_base.base.POJO.NetworkRequestState
import com.islam.basepropject.project_base.base.other.BaseDataFactory
import com.islam.basepropject.project_base.base.other.BaseDataSource
import com.islam.basepropject.project_base.base.other.BaseViewModel

import io.reactivex.Observable

class ExViewModel : BaseViewModel() {

    var networkState: LiveData<NetworkRequestState>? = null
        private set
    private var dataFactory: BaseDataFactory<String>? = null

    val orders: Observable<PagedList<String>>
        get() {
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

            return RxPagedListBuilder(dataFactory!!, pagedListConfig).buildObservable()
        }
}
