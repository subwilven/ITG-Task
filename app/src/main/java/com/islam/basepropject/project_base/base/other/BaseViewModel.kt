package com.islam.basepropject.project_base.base.other

import androidx.lifecycle.ViewModel

import com.islam.basepropject.project_base.base.POJO.ErrorModel
import com.islam.basepropject.project_base.base.POJO.Message
import com.islam.basepropject.project_base.base.POJO.ScreenStatus
import com.islam.basepropject.project_base.utils.others.SchedulerProvider

import java.lang.ref.WeakReference
import java.util.HashMap

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

abstract class BaseViewModel<N> : ViewModel() {

    val schedulerProvider: SchedulerProvider

    private val mCompositeDisposable: CompositeDisposable
    private val mSnackBarMessage: PublishSubject<Message>
    private val mToastMessage: PublishSubject<Message>
    private val mDialogMessage: PublishSubject<Message>
    private val mShowLoadingFullScreen: PublishSubject<Boolean>
    private val mShowErrorFullScreen: PublishSubject<ErrorModel>
    private val registeredFragments: HashMap<String, ScreenStatus>
    private var mNavigator: WeakReference<N>? = null
    private var lastRegisteredFragment: String? = null

    val lastRegisterdFragmentStatus: ScreenStatus?
        get() = registeredFragments[lastRegisteredFragment]


    var navigator: N?
        get() = mNavigator!!.get()
        set(navigator) {
            this.mNavigator = WeakReference<N>(navigator)
        }

    //to prevent dublicate observing because observeDefault called twice in OnViewCreated and in onStart
    val isDefaultObserved: Boolean
        get() = (mDialogMessage.hasObservers()
                && mToastMessage.hasObservers()
                && mSnackBarMessage.hasObservers())

    init {
        this.schedulerProvider = SchedulerProvider()
        this.mCompositeDisposable = CompositeDisposable()
        mSnackBarMessage = PublishSubject.create()
        mToastMessage = PublishSubject.create()
        mDialogMessage = PublishSubject.create()
        mShowLoadingFullScreen = PublishSubject.create()
        mShowErrorFullScreen = PublishSubject.create()
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

    protected fun addDisposable(disposable: Disposable) {
        mCompositeDisposable.add(disposable)
    }

    fun showSnackBarMessage(s: Message) {
        mSnackBarMessage.onNext(s)
    }

    fun showToastMessage(s: Message) {
        mToastMessage.onNext(s)
    }

    fun showDialogMessage(s: Message) {
        mDialogMessage.onNext(s)
    }

    fun showLoadingFullScreen(b: Boolean) {
        mShowLoadingFullScreen.onNext(b)
    }

    fun showNoConnectionFullScreen(errorModel: ErrorModel) {
        mShowErrorFullScreen.onNext(errorModel)
    }


    fun observeDialogMessage(consumer: Consumer<Message>) {
        addDisposable(mDialogMessage.observeOn(schedulerProvider.ui()).subscribe(consumer))
    }

    fun observeToastMessage(consumer: Consumer<Message>) {
        addDisposable(mToastMessage.observeOn(schedulerProvider.ui()).subscribe(consumer))
    }

    fun observeSnackBarMessage(consumer: Consumer<Message>) {
        addDisposable(mSnackBarMessage.observeOn(schedulerProvider.ui()).subscribe(consumer))
    }

    fun  observeShowLoadingFullScreen(consumer: Consumer<Boolean>){
        addDisposable(mShowLoadingFullScreen.observeOn(schedulerProvider.ui()).subscribe(consumer))

    }

    fun observeShowNoConnectionFullScreen(consumer: Consumer<ErrorModel>) {
        addDisposable(mShowErrorFullScreen.observeOn(schedulerProvider.ui()).subscribe(consumer))
    }

    fun unSubscribe() {
        mCompositeDisposable.clear()
    }

    fun unRegister(fragmentClassName: String) {
        registeredFragments.remove(fragmentClassName)
    }


}
