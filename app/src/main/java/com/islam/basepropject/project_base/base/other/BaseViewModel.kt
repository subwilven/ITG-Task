package com.islam.basepropject.project_base.base.other

import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.islam.basepropject.MyApplication
import com.islam.basepropject.project_base.POJO.ErrorModel
import com.islam.basepropject.project_base.POJO.Message
import com.islam.basepropject.project_base.POJO.ScreenStatus
import com.islam.basepropject.project_base.base.other.network.Failure
import com.islam.basepropject.project_base.base.other.network.Result
import com.islam.basepropject.project_base.base.other.network.RetrofitCorounites
import com.islam.basepropject.project_base.views.OnViewStatusChange
import java.util.*

abstract class BaseViewModel : ViewModel() {

    /*
     Do not forget to check if liveData value is null before requesting the data
     Use appScope when you need the response of network request even that user has left the screen
     Use viewModelScope when the network response don't represent any importance if the user left the screen
    */

    val appScope = ProcessLifecycleOwner.get().lifecycleScope

    val mSnackBarMessage: SingleLiveEvent<Message> = SingleLiveEvent()
    val mEnableSensitiveInputs: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val mToastMessage: SingleLiveEvent<Message> = SingleLiveEvent()
    val mDialogMessage: SingleLiveEvent<Message> = SingleLiveEvent()
    val mShowLoadingFullScreen: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val mShowErrorFullScreen: SingleLiveEvent<ErrorModel> = SingleLiveEvent()
    private val registeredFragments: HashMap<String, ScreenStatus> = HashMap()
    private var lastRegisteredFragment: String? = null

    val lastRegisterdFragmentStatus: ScreenStatus?
        get() = registeredFragments[lastRegisteredFragment]


    fun registerFragment(fragmentClassName: String) {
        if (!registeredFragments.containsKey(fragmentClassName))
            registeredFragments[fragmentClassName] = ScreenStatus.STARTING
        lastRegisteredFragment = fragmentClassName
    }

    //if all network jobs finished successfully mark the screen as completed
    fun markAsCompleted(results: List<Result<Any>>) {
        for (result in results) {
            if (result is Failure)
                return
        }
        registeredFragments[lastRegisteredFragment!!] = ScreenStatus.COMPLETED
    }


    suspend fun <T> networkCall(onViewStatusChange: OnViewStatusChange? = null, block: suspend () -> T): Result<T> {
        return RetrofitCorounites(this, onViewStatusChange).networkCall(block)
    }


    fun markAsCompleted(name: String) {
        registeredFragments[name] = ScreenStatus.COMPLETED
    }


    fun showSnackBarMessage(s: Message) {
        mSnackBarMessage.value = s
    }

    fun showToastMessage(s: Message) {
        if (mToastMessage.hasActiveObservers())
            mToastMessage.value = s
        else
            showAlertBar(s)
    }

    fun showDialogMessage(s: Message) {
        mDialogMessage.value = s
    }

    fun enableSensitiveInputs(b: Boolean) {
        mEnableSensitiveInputs.value = b
    }

    fun showLoadingFullScreen(b: Boolean) {
        mShowLoadingFullScreen.value = b
    }

    fun showNoConnectionFullScreen(errorModel: ErrorModel?) {
        mShowErrorFullScreen.value = errorModel
    }

    fun showAlertBar(message: Message) {
        MyApplication.instance?.showAlertBar(message)
    }


    fun unRegister(fragmentClassName: String) {
        registeredFragments.remove(fragmentClassName)
    }


}
