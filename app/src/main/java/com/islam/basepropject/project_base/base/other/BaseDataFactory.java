package com.islam.basepropject.project_base.base.other;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public abstract class BaseDataFactory<T> extends DataSource.Factory {

    private MutableLiveData<BaseDataSource<T>> mutableLiveData;
    private BaseViewModel baseViewModel;
    public  abstract BaseDataSource<T>  onCreateDataSource(BaseViewModel baseViewModel);

    public BaseDataFactory(BaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
        this.mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        BaseDataSource<T> dataSource = onCreateDataSource(baseViewModel);
        mutableLiveData.postValue(dataSource);
        return dataSource;
    }

    public MutableLiveData<BaseDataSource<T>> getMutableDataSource() {
        return mutableLiveData;
    }
}