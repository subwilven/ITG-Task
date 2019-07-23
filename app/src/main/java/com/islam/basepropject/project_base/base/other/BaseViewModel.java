package com.islam.basepropject.project_base.base.other;

import androidx.lifecycle.ViewModel;

import com.islam.basepropject.project_base.base.POJO.ErrorModel;
import com.islam.basepropject.project_base.base.POJO.ScreenStatus;
import com.islam.basepropject.project_base.utils.others.SchedulerProvider;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseViewModel<N> extends ViewModel {

    private final SchedulerProvider mSchedulerProvider;

    private final CompositeDisposable mCompositeDisposable;
    private final PublishSubject<String> mSnackBarMessage;
    private final PublishSubject<String> mToastMessage;
    private final PublishSubject<String> mDialogMessage;
    private final PublishSubject<Boolean> mShowLoadingFullScreen;
    private final PublishSubject<ErrorModel> mShowErrorFullScreen;
    private final HashMap<String, ScreenStatus> registeredFragments;
    private WeakReference<N> mNavigator;
    private String lastRegisteredFragment;

    public BaseViewModel() {
        this.mSchedulerProvider = new SchedulerProvider();
        this.mCompositeDisposable = new CompositeDisposable();
        mSnackBarMessage = PublishSubject.create();
        mToastMessage = PublishSubject.create();
        mDialogMessage = PublishSubject.create();
        mShowLoadingFullScreen = PublishSubject.create();
        mShowErrorFullScreen = PublishSubject.create();
        registeredFragments = new HashMap<>();
    }

    public void registerFragment(String fragmentClassName) {
        if (!registeredFragments.containsKey(fragmentClassName))
            registeredFragments.put(fragmentClassName, ScreenStatus.STARTING);
        lastRegisteredFragment = fragmentClassName;
    }

    public ScreenStatus getLastRegisterdFragmentStatus() {
        return registeredFragments.get(lastRegisteredFragment);
    }


    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    protected void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }


    public N getNavigator() {
        return mNavigator.get();
    }

    public void setNavigator(N navigator) {
        this.mNavigator = new WeakReference<>(navigator);
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public void showSnackBarMessage(String s) {
        mSnackBarMessage.onNext(s);
    }

    public void showToastMessage(String s) {
        mToastMessage.onNext(s);
    }

    public void showDialogMessage(String s) {
        mDialogMessage.onNext(s);
    }

    public void showLoadingFullScreen(boolean b) {
        mShowLoadingFullScreen.onNext(b);
    }

    public void showNoConnectionFullScreen(ErrorModel errorModel) {
        mShowErrorFullScreen.onNext(errorModel);
    }

    //to prevent dublicate observing because observeDefault called twice in OnViewCreated and in onStart
    public boolean isDefaultObserved() {
        return mDialogMessage.hasObservers()
                && mToastMessage.hasObservers()
                && mSnackBarMessage.hasObservers();
    }


    public void observeDialogMessage(Consumer<String> consumer) {
        addDisposable(mDialogMessage.subscribe(consumer));
    }

    public void observeToastMessage(Consumer<String> consumer) {
        addDisposable(mToastMessage.subscribe(consumer));
    }

    public void observeSnackBarMessage(Consumer<String> consumer) {
        addDisposable(mSnackBarMessage.subscribe(consumer));
    }

    public void observeShowLoadingFullScreen(Consumer<Boolean> consumer) {
        addDisposable(mShowLoadingFullScreen.subscribe(consumer));

    }

    public void observeShowNoConnectionFullScreen(Consumer<ErrorModel> consumer) {
        addDisposable(mShowErrorFullScreen.subscribe(consumer));
    }

    public void unSubscribe() {
        mCompositeDisposable.clear();
    }

    public void unRegister(String fragmentClassName) {
        registeredFragments.remove(fragmentClassName);
    }
}
