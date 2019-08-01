package com.islam.basepropject.project_base.utils.network

import com.google.gson.Gson
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.POJO.ApiError
import com.islam.basepropject.project_base.base.POJO.ErrorModel
import com.islam.basepropject.project_base.base.POJO.Message
import com.islam.basepropject.project_base.base.POJO.ScreenStatus
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.OnViewStatusChange

import java.io.IOException
import java.net.SocketTimeoutException

import io.reactivex.observers.DisposableSingleObserver
import retrofit2.HttpException

abstract class RetrofitObserver<T> : DisposableSingleObserver<T> {

    private var baseViewModel: BaseViewModel<*>? = null
    private var view: OnViewStatusChange? = null

    private val isStartingFragment: Boolean
        get() = baseViewModel!!.lastRegisterdFragmentStatus == ScreenStatus.STARTING

    protected constructor(viewModel: BaseViewModel<*>) {
        baseViewModel = viewModel
    }

    protected constructor(viewModel: BaseViewModel<*>, view: OnViewStatusChange) {
        baseViewModel = viewModel
        this.view = view
    }

    abstract fun onResultSuccess(o: T)

    override fun onStart() {
        super.onStart()
        if (isStartingFragment) {
            baseViewModel!!.showNoConnectionFullScreen(ErrorModel.freeError())
            baseViewModel!!.showLoadingFullScreen(true)
        } else {
            view?.showLoading(true)
        }
    }


    override fun onError(e: Throwable) {
        e.printStackTrace()
        if (e is HttpException) {
            handelState500Error(e)
        } else if (e is SocketTimeoutException) {
            showError(Message(R.string.cannot_reach_the_server), ErrorModel.timeOut())
        } else if (e is ConnectivityInterceptor.NoConnectivityException) {
            showError(Message(R.string.no_network_available),ErrorModel.noConnection())
        } else if (e is IOException) {
            baseViewModel!!.showToastMessage(Message("IOException"))
        } else {
            baseViewModel!!.showToastMessage(Message("Something Went wrong"))
        }

        if (isStartingFragment)
            baseViewModel!!.showLoadingFullScreen(false)
        else view?.showLoading(false)
    }

    //handel errors like validation error or authentication error
    private fun handelState500Error(e: Throwable) {
        try {
            val error = (e as HttpException).response().errorBody()!!.string()
            val apiError = Gson().fromJson(error, ApiError::class.java)
            onResultFailed(apiError.message)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

    }

    private fun showError(msg: Message, errorModel: ErrorModel) {
        if (isStartingFragment)
            baseViewModel!!.showNoConnectionFullScreen(errorModel)
        else
            baseViewModel!!.showToastMessage(msg)
    }

    override fun onSuccess(o: T) {
        if (isStartingFragment)
            baseViewModel!!.showLoadingFullScreen(false)
        else view?.showLoading(false)

        onResultSuccess(o)
    }

    //to make it easy to handel errors in each case individually
    fun onResultFailed(msg: String?) {
        msg?.let { baseViewModel!!.showToastMessage(Message(it)) }

    }
}
