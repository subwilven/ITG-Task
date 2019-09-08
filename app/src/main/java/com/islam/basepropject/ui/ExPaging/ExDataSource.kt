package com.islam.basepropject.ui.ExPaging

import com.islam.basepropject.pojo.Order
import com.islam.basepropject.project_base.base.other.BaseDataSource
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.OnViewStatusChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*

class ExDataSource(baseView: BaseViewModel,val viewId: Int?) : BaseDataSource<Order>(baseView) {

    internal var number = 1
    override suspend fun fetchData(key: Int): List<Order> {
     val result =  baseViewModel.networkCall(viewId) {getData()}
        baseViewModel.markAsCompleted(listOf(result))
        return result.value!!
    }
    private suspend fun getData():List<Order>{
        return withContext(Dispatchers.IO) {
            delay(2500)
            val strings = ArrayList<Order>()
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings.add(Order("string " + number++))
            strings
        }
    }
}
