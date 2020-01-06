package com.islam.task.ui.marvels

import com.islam.task.BuildConfig
import com.islam.task.pojo.Marvel
import com.islam.task.project_base.base.other.BaseDataSource
import com.islam.task.project_base.base.other.BaseViewModel
import com.islam.task.project_base.base.other.network.NetworkModel
import com.islam.task.util.Utils.toMD5
import kotlinx.coroutines.delay

class MarvelsDataSource(baseView: BaseViewModel, val viewId: Int?, val serachQurey: String) : BaseDataSource<Marvel>(baseView) {

    // internal var number = 1
    override suspend fun fetchData(key: Int): List<Marvel> {
        val result = baseViewModel.networkCall(viewId) { getData(key = key) }
        baseViewModel.markAsCompleted(listOf(result))
        return result.value ?: listOf()
    }

    private suspend fun getData(key: Int): List<Marvel> {
        delay(1000)//just to have time to show the loading in the recycler view
        val timestamp = (System.currentTimeMillis() / 1000).toString()

        return if (serachQurey.isBlank())
            NetworkModel.clientApi!!.getMarvels(
                    BuildConfig.PUBLC_KEY,
                    timestamp,
                    (key * 20).toString(),
                    (timestamp + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLC_KEY).toMD5()).data.results
        else
            NetworkModel.clientApi!!.searchMarvelsByName(
                    BuildConfig.PUBLC_KEY,
                    timestamp,
                    (key * 20).toString(),
                    serachQurey,
                    (timestamp + BuildConfig.PRIVATE_KEY + BuildConfig.PUBLC_KEY).toMD5()).data.results
    }
}
