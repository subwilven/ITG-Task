package com.islam.basepropject.ui.ExPaging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import com.islam.basepropject.project_base.base.POJO.NetworkRequestState;
import com.islam.basepropject.project_base.base.other.BaseDataFactory;
import com.islam.basepropject.project_base.base.other.BaseDataSource;
import com.islam.basepropject.project_base.base.other.BaseViewModel;

import io.reactivex.Observable;

public class ExViewModel extends BaseViewModel {

    private LiveData<NetworkRequestState> networkState;
    private BaseDataFactory<String> dataFactory;

    public Observable<PagedList<String>> getOrders() {
        if (dataFactory == null)
            dataFactory = new BaseDataFactory<String>(this) {
                @Override
                public BaseDataSource<String> onCreateDataSource(BaseViewModel baseViewModel) {
                    return new ExDataSource(ExViewModel.this);
                }
            };

        networkState = Transformations.switchMap(dataFactory.getMutableDataSource(),
                input -> dataFactory.getMutableDataSource().getValue().getNetworkState());

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setInitialLoadSizeHint(2)
                        .setPageSize(20).build();

        return new RxPagedListBuilder(dataFactory, pagedListConfig).buildObservable();
    }

    public LiveData<NetworkRequestState> getNetworkState(){
        return networkState;
    }
}
