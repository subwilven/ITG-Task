package com.islam.task.ui.marvels

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.islam.task.data.Repository
import com.islam.task.pojo.Marvel
import com.islam.task.project_base.base.other.BaseDataFactory
import com.islam.task.project_base.base.other.BaseDataSource
import com.islam.task.project_base.base.other.BaseViewModel
import com.islam.task.project_base.base.other.SingleLiveEvent

class MarvelsViewModel : BaseViewModel() {


    private var dataFactory: MutableLiveData<BaseDataFactory<Marvel>> = MutableLiveData()

    var marvels: LiveData<PagedList<Marvel>>? = null
    var navigateToMarvelDetails: SingleLiveEvent<Marvel> = SingleLiveEvent()

    override fun loadInitialData() {}


    fun loadOrders(viewId: Int?, searchQuery: String = "") {

        dataFactory.value = object : BaseDataFactory<Marvel>(this) {
            override fun onCreateDataSource(baseViewModel: BaseViewModel): BaseDataSource<Marvel> {
                return MarvelsDataSource(this@MarvelsViewModel, viewId, searchQuery)
            }
        }


        val pagedListConfig = PagedList.Config.Builder()
                .setInitialLoadSizeHint(2)
                .setPageSize(20).build()

        marvels = Transformations.switchMap(dataFactory, Function {
            return@Function LivePagedListBuilder(dataFactory.value!!, pagedListConfig).build()
        })

    }


}