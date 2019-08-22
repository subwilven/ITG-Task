package com.islam.basepropject.ui.ExPaging

import com.islam.basepropject.project_base.base.other.BaseDataSource
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.OnViewStatusChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*

class ExDataSource(baseView: BaseViewModel,val view: OnViewStatusChange) : BaseDataSource<String>(baseView) {

    internal var number = 1
    override suspend fun fetchData(key: Int): List<String> {
     val result =  baseViewModel.networkCall(view) {getData()}
        baseViewModel.markAsCompleted(listOf(result))
        return result.value!!
    }
    private suspend fun getData():List<String>{
        return withContext(Dispatchers.IO) {
            delay(2500)
            val strings = ArrayList<String>()
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings.add("string " + number++)
            strings
        }
    }
}
