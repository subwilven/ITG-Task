package com.islam.basepropject.ui.ExFetchData

import androidx.lifecycle.viewModelScope
import com.islam.basepropject.data.Repository
import com.islam.basepropject.pojo.Article
import com.islam.basepropject.project_base.POJO.ApiResponse
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.other.network.Result
import kotlinx.coroutines.launch

class ViewModel : BaseViewModel() {
    val repository = Repository()

    override fun loadInitialData() {
        loadProviders(null)
    }

    var providersList: Result<ApiResponse<List<Article>>>? = null
    fun loadProviders(viewId: Int?) {
        viewModelScope.launch {
            providersList = networkCall(viewId) { repository.getProvidersList() }
            markAsCompleted(listOf(providersList!!))
        }
    }
}