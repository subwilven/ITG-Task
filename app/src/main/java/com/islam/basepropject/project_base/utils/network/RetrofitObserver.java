package com.islam.basepropject.project_base.utils.network;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.BaseViewModel;
import com.islam.basepropject.project_base.base.POJO.ErrorModel;
import com.islam.basepropject.project_base.base.POJO.ScreenStatus;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public abstract class RetrofitObserver<T> implements SingleObserver<T> {

    private BaseViewModel baseViewModel;

    public RetrofitObserver(BaseViewModel viewModel) {
        baseViewModel = viewModel;

        if (isStartingFragment()) {
            baseViewModel.showNoConnectionFullScreen(ErrorModel.freeError());
            baseViewModel.showLoadingFullScreen(true);
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

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

    public void onSuccess(T o) {
        if (isStartingFragment())
            baseViewModel.showLoadingFullScreen(false);

    }
}
