package com.islam.basepropject.ui.ExPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.islam.basepropject.data.Repository
import com.islam.basepropject.pojo.Order
import com.islam.basepropject.project_base.POJO.AdatperItemLoading
import com.islam.basepropject.project_base.POJO.NetworkRequestState
import com.islam.basepropject.project_base.base.other.BaseDataFactory
import com.islam.basepropject.project_base.base.other.BaseDataSource
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.other.SingleLiveEvent
import com.islam.basepropject.project_base.base.other.network.Success
import com.islam.basepropject.project_base.views.OnViewStatusChange
import kotlinx.coroutines.launch

class ExViewModel : BaseViewModel() {

    val repository = Repository()

    private var dataFactory: BaseDataFactory<Order>? = null

    var orders: LiveData<PagedList<Order>>? = null
    var notifyItemChanged: SingleLiveEvent<Int> = SingleLiveEvent()

    override fun loadInitialData() {}


    fun loadOrders(viewId:Int?) {
        if (orders?.value != null) return

        if (dataFactory == null)
            dataFactory = object : BaseDataFactory<Order>(this) {
                override fun onCreateDataSource(baseViewModel: BaseViewModel): BaseDataSource<Order> {
                    return ExDataSource(this@ExViewModel,viewId)
                }
            }

        val pagedListConfig = PagedList.Config.Builder()
                .setInitialLoadSizeHint(2)
                .setPageSize(20).build()
        orders = LivePagedListBuilder(dataFactory!!, pagedListConfig).build()

    }

    override fun notifyItemChanges(adapterItem: AdatperItemLoading) {
        val itemIndex = orders?.value?.indexOf(adapterItem)
        notifyItemChanged.value = itemIndex
    }

    fun addToCart(order: Order){
        viewModelScope.launch {
            val providersList = networkCall(order) { repository.getProvidersList() }
            if(providersList is Success){

        }
    }
}}
