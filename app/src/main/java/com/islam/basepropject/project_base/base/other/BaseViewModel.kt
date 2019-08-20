package com.islam.basepropject.project_base.base.other

import androidx.lifecycle.ViewModel
import com.islam.basepropject.project_base.POJO.ErrorModel
import com.islam.basepropject.project_base.POJO.Message
import com.islam.basepropject.project_base.POJO.ScreenStatus
import com.islam.basepropject.project_base.base.other.network.Failure
import com.islam.basepropject.project_base.base.other.network.Result
import java.util.*

abstract class BaseViewModel : ViewModel() {

    val mSnackBarMessage: SingleLiveEvent<Message>
    val mToastMessage: SingleLiveEvent<Message>
    val mDialogMessage: SingleLiveEvent<Message>
    val mShowLoadingFullScreen: SingleLiveEvent<Boolean>
    val mShowErrorFullScreen: SingleLiveEvent<ErrorModel>
    private val registeredFragments: HashMap<String, ScreenStatus>
    private var lastRegisteredFragment: String? = null

    val lastRegisterdFragmentStatus: ScreenStatus?
        get() = registeredFragments[lastRegisteredFragment]


    //to prevent dublicate observing because observeDefault called twice in OnViewCreated and in onStart
    val isDefaultObserved: Boolean
        get() = (mDialogMessage.hasObservers()
                && mToastMessage.hasObservers()
                && mSnackBarMessage.hasObservers())

    init {
        mSnackBarMessage = SingleLiveEvent()
        mToastMessage = SingleLiveEvent()
        mDialogMessage = SingleLiveEvent()
        mShowLoadingFullScreen = SingleLiveEvent()
        mShowErrorFullScreen = SingleLiveEvent()
        registeredFragments = HashMap()
    }

    fun registerFragment(fragmentClassName: String) {
        if (!registeredFragments.containsKey(fragmentClassName))
            registeredFragments[fragmentClassName] = ScreenStatus.STARTING
        lastRegisteredFragment = fragmentClassName
    }

    //get the last screen

    fun markAsCompleted( results: List<Result<Any>>) {
        for (result in results) {
            if(result is Failure)
                return
        }
        registeredFragments[lastRegisteredFragment!!] = ScreenStatus.COMPLETED
    }


    fun markAsCompleted(name: String) {
        registeredFragments[name] = ScreenStatus.COMPLETED
    }


    fun showSnackBarMessage(s: Message) {
        mSnackBarMessage.value = s
    }

    fun showToastMessage(s: Message) {
        mToastMessage.value = s
    }

    fun showDialogMessage(s: Message) {
        mDialogMessage.value = s
    }

    fun showLoadingFullScreen(b: Boolean) {
        mShowLoadingFullScreen.value = b
    }

    fun showNoConnectionFullScreen(errorModel: ErrorModel?) {
        mShowErrorFullScreen.value = errorModel
    }


    fun unRegister(fragmentClassName: String) {
        registeredFragments.remove(fragmentClassName)
    }


}
