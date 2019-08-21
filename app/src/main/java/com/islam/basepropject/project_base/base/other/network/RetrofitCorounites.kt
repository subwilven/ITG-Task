package com.islam.basepropject.project_base.base.other.network

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.islam.basepropject.R
import com.islam.basepropject.project_base.POJO.ApiError
import com.islam.basepropject.project_base.POJO.ErrorModel
import com.islam.basepropject.project_base.POJO.Message
import com.islam.basepropject.project_base.POJO.ScreenStatus
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.OnViewStatusChange
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException

open class RetrofitCorounites(private val baseViewModel: BaseViewModel) {

    private var view: OnViewStatusChange? = null

    private val isStartingFragment: Boolean
        get() = baseViewModel.lastRegisterdFragmentStatus == ScreenStatus.STARTING

    constructor(baseViewModel: BaseViewModel, view: OnViewStatusChange?) : this(baseViewModel) {
        this.view = view;
    }

    private fun showLoading() {
        baseViewModel.enableSensitiveInputs(false)
        if (isStartingFragment) {
            baseViewModel.showNoConnectionFullScreen(null)
            baseViewModel.showLoadingFullScreen(true)
        } else {
            view?.showLoading(true)
        }
    }

    private fun hideLoading() {
        baseViewModel.enableSensitiveInputs(true)
        if (isStartingFragment)
            baseViewModel.showLoadingFullScreen(false)
        else view?.showLoading(false)
    }

    //handel errors like validation error or authentication error
    private fun getHttpErrorMessage(e: Throwable): String {
        return try {
            val error = (e as HttpException).response()?.errorBody()!!.string()
            val apiError = Gson().fromJson(error, ApiError::class.java)
            apiError.error.message
        } catch (e1: Exception) {
            e1.printStackTrace()
            "Cannot Handel Error Message"
        }
    }

    private fun handelNetworkError(e: Throwable) {
        e.printStackTrace()
        when (e) {
            is CancellationException ->{}
            is HttpException ->
                onErrorReceived(getHttpErrorMessage(e))
            is SocketTimeoutException ->
                showError(Message(R.string.cannot_reach_the_server), ErrorModel.timeOut())
            is ConnectivityInterceptor.NoConnectivityException ->
                showError(Message(R.string.no_network_available), ErrorModel.noConnection())
            is JsonSyntaxException ->
                showError(Message(R.string.server_error))
            is IOException ->
                showError(Message(R.string.something_went_wrong))
            else ->
                showError(Message(R.string.unexpected_error_happened))
        }
    }

    open fun onErrorReceived(errorMsg :String){
        showError(Message(errorMsg), ErrorModel.serverError(Message(errorMsg)))
    }


    private fun showError(msg: Message, errorModel: ErrorModel = ErrorModel.serverError(msg)) {

        if (isStartingFragment)
            baseViewModel.showNoConnectionFullScreen(errorModel)
        else
            baseViewModel.showToastMessage(msg)
    }


    suspend fun <T> networkCall(block: suspend () -> T): Result<T> {
        return withContext(Dispatchers.Main) {
            try {
                showLoading()
                withContext(Dispatchers.IO) { Success(block.invoke()) }
            } catch (e: Throwable) {
                handelNetworkError(e)
                Failure()
            } finally {
                hideLoading()
            }
        }
    }
}