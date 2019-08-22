package com.islam.basepropject.ui.ExPaging

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.islam.basepropject.project_base.POJO.NetworkRequestState
import com.islam.basepropject.project_base.base.other.BaseDataFactory
import com.islam.basepropject.project_base.base.other.BaseDataSource
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.OnViewStatusChange

class ExViewModel : BaseViewModel() {

    private var dataFactory: BaseDataFactory<String>? = null

    var orders: LiveData<PagedList<String>>? = null


    fun loadOrders(view:OnViewStatusChange) {
        if (orders?.value != null) return

        if (dataFactory == null)
            dataFactory = object : BaseDataFactory<String>(this) {
                override fun onCreateDataSource(baseViewModel: BaseViewModel): BaseDataSource<String> {
                    return ExDataSource(this@ExViewModel,view)
                }
            }

        val pagedListConfig = PagedList.Config.Builder()
                .setInitialLoadSizeHint(2)
                .setPageSize(20).build()
        orders = LivePagedListBuilder(dataFactory!!, pagedListConfig).build()

    }
}
