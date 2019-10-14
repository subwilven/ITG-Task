package com.islam.basepropject.ui.examples.ExRecyclerView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.OnViewStatusChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ViewModel : BaseViewModel() {

    public var data = MutableLiveData<MutableList<String>>()
    override fun loadInitialData() {
        loadProviders(null)
    }

    fun loadProviders(onViewStatusChange: OnViewStatusChange?) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2500)
            val strings = ArrayList<String>()
            strings.add("dsdsd")
            strings.add("sdsdsdsdsd")
            data.postValue(strings)
            delay(2500)
            val strings2= ArrayList<String>()
            strings2.add("dsdsd")
            strings2.add("sdsdsdsdsd")
            strings2.add("sdsdsdsdsd2")
            data.postValue(strings2)
            delay(2500)
            val strings3= ArrayList<String>()
            strings3.add("dsdsd")
            strings3.add("sdsdsdsdsd2")
            data.postValue(strings3)
        }

    }

    fun getData(): LiveData<MutableList<String>> {
        return data
    }
}

