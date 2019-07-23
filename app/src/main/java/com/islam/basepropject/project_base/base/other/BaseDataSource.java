package com.islam.basepropject.project_base.base.other;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.islam.basepropject.project_base.base.POJO.NetworkRequestState;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public abstract class BaseDataSource<T> extends PageKeyedDataSource<Long, T> {


    private MutableLiveData<NetworkRequestState> networkState;
    private BaseViewModel baseViewModel;

    public BaseDataSource(BaseViewModel baseView) {
        networkState = new MutableLiveData<>();
        this.baseViewModel = baseView;
    }

    public abstract Single<List<T>> fetchData(int key);

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, T> callback) {
        baseViewModel.showLoadingFullScreen(true);

        //TODO to be replaced with Rertrofit Observer
        fetchData(1).subscribe(new SingleObserver<List<T>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<T> result) {
                callback.onResult(result, null, 1L);
                baseViewModel.showLoadingFullScreen(false);
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }


    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, T> callback) {

    }


    @Override
    public void loadAfter(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, T> callback) {
        networkState.postValue(NetworkRequestState.LOADING);

        long nextKey = (int) (params.key + 1);

        //TODO to be replaced with Rertrofit Observer
        fetchData((int) (params.key + 1)).subscribe(new SingleObserver<List<T>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<T> result) {
                callback.onResult(result, nextKey);
                networkState.postValue(NetworkRequestState.COMPLETE);
            }

            @Override
            public void onError(Throwable e) {
                networkState.postValue(NetworkRequestState.COMPLETE);
            }
        });
    }


    public MutableLiveData<NetworkRequestState> getNetworkState() {
        return networkState;
    }

    public BaseViewModel getBaseViewModel() {
        return baseViewModel;
    }
}