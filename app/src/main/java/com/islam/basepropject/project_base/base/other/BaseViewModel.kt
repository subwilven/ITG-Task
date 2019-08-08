package com.islam.basepropject.project_base.base.other

import androidx.lifecycle.ViewModel

import com.islam.basepropject.project_base.base.POJO.ErrorModel
import com.islam.basepropject.project_base.base.POJO.Message
import com.islam.basepropject.project_base.base.POJO.ScreenStatus

import java.util.HashMap

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel: ViewModel() {

    val schedulerProvider: SchedulerProvider

    private val mCompositeDisposable: CompositeDisposable
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
        this.schedulerProvider = SchedulerProvider()
        this.mCompositeDisposable = CompositeDisposable()
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

    fun markAsCompleted(name: String) {
        registeredFragments[name] = ScreenStatus.COMPLETED
    }


    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

    fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
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

    fun showNoConnectionFullScreen(errorModel: ErrorModel) {
        mShowErrorFullScreen.value =errorModel
    }


    fun unRegister(fragmentClassName: String) {
        registeredFragments.remove(fragmentClassName)
    }


}
