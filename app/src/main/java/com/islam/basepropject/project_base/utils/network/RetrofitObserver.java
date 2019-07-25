package com.islam.basepropject.project_base.utils.network;

import com.google.gson.Gson;
import com.islam.basepropject.project_base.base.POJO.ApiError;
import com.islam.basepropject.project_base.base.POJO.ErrorModel;
import com.islam.basepropject.project_base.base.POJO.ScreenStatus;
import com.islam.basepropject.project_base.base.other.BaseViewModel;
import com.islam.basepropject.project_base.views.OnViewStatusChange;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.HttpException;

public abstract class RetrofitObserver<T> extends DisposableSingleObserver<T> {

    private BaseViewModel baseViewModel;
    private OnViewStatusChange view;

    protected RetrofitObserver(BaseViewModel viewModel) {
        baseViewModel = viewModel;
    }
    protected RetrofitObserver(BaseViewModel viewModel, OnViewStatusChange view) {
        baseViewModel = viewModel;
        this.view=view;
    }

    public abstract void onResultSuccess(T o);

    private boolean isStartingFragment() {
        return baseViewModel.getLastRegisterdFragmentStatus() == ScreenStatus.STARTING;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isStartingFragment()) {
            baseViewModel.showNoConnectionFullScreen(ErrorModel.freeError());
            baseViewModel.showLoadingFullScreen(true);
        }else{
            if(view!=null)
                view.showLoading(true);
        }
    }


    @Override
    public final void onError(Throwable e) {
        e.printStackTrace();
        if (isStartingFragment())
            baseViewModel.showLoadingFullScreen(false);
        else
        if(view!=null)
            view.showLoading(false);

        if (e instanceof HttpException) {
            handelState500Error(e);
        } else if (e instanceof SocketTimeoutException) {
            showError("SocketTimeoutException", ErrorModel.timeOut());
        } else if (e instanceof ConnectivityInterceptor.NoConnectivityException) {
            showError("No network available, please check your WiFi or Data connection",
                    ErrorModel.noConnection());
        } else if (e instanceof IOException) {
            baseViewModel.showToastMessage("IOException");
        } else {
            baseViewModel.showToastMessage("Something Went wrong");
        }
    }

    //handel errors like validation error or authentication error
    private void handelState500Error(Throwable e) {
        try {
            String error = ((HttpException) e).response().errorBody().string();
            ApiError apiError = new Gson().fromJson(error, ApiError.class);
            onResultFailed(apiError.message);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void showError(String msg, ErrorModel errorModel) {
        if (isStartingFragment())
            baseViewModel.showNoConnectionFullScreen(errorModel);
        else
            baseViewModel.showToastMessage(msg);
    }

    @Override
    public final void onSuccess(T o) {
        if (isStartingFragment())
            baseViewModel.showLoadingFullScreen(false);
        else
        if(view!=null)
            view.showLoading(false);

        onResultSuccess(o);
    }

    //to make it easy to handel errors in each case individually
    public void onResultFailed(String msg){
        baseViewModel.showToastMessage(msg);
    }
}
