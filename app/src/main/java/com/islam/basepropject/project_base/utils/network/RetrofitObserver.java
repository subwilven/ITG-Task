package com.islam.basepropject.project_base.utils.network;

import com.islam.basepropject.project_base.base.other.BaseViewModel;
import com.islam.basepropject.project_base.base.POJO.ErrorModel;
import com.islam.basepropject.project_base.base.POJO.ScreenStatus;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

public abstract class RetrofitObserver<T> extends DisposableSingleObserver<T> {

    private BaseViewModel baseViewModel;

    public abstract void onResultSuccess(T o);
    public RetrofitObserver(BaseViewModel viewModel) {
        baseViewModel = viewModel;

        if (isStartingFragment()) {
            baseViewModel.showNoConnectionFullScreen(ErrorModel.freeError());
            baseViewModel.showLoadingFullScreen(true);
        }
    }

    private boolean isStartingFragment() {
        return baseViewModel.getLastRegisterdFragmentStatus() == ScreenStatus.STARTING;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
            baseViewModel.showLoadingFullScreen(false);

        if (e instanceof SocketTimeoutException) {
            if (isStartingFragment())
                baseViewModel.showNoConnectionFullScreen(ErrorModel.timeOut());
            else
                baseViewModel.showToastMessage("SocketTimeoutException");

        } else if (e instanceof ConnectivityInterceptor.NoConnectivityException) {
            if (isStartingFragment())
                baseViewModel.showNoConnectionFullScreen(ErrorModel.noConnection());
            else
                baseViewModel.showToastMessage("No network available, please check your WiFi or Data connection");
        } else if (e instanceof IOException) {
            baseViewModel.showToastMessage("IOException");
        } else {
            baseViewModel.showToastMessage("Something Went wrong");
        }
    }

    public final void onSuccess(T o) {
        if (isStartingFragment())
            baseViewModel.showLoadingFullScreen(false);
        onResultSuccess(o);
    }
}
