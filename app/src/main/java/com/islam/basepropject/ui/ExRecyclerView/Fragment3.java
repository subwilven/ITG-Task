package com.islam.basepropject.ui.ExRecyclerView;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.gson.JsonElement;
import com.islam.basepropject.R;
import com.islam.basepropject.data.Repository;
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment;
import com.islam.basepropject.project_base.base.other.BaseViewModel;
import com.islam.basepropject.project_base.utils.network.RetrofitObserver;
import com.islam.basepropject.project_base.views.OnViewStatusChange;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class Fragment3 extends BaseSuperFragment<Fragment3.ViewModel> {

    Adapter mAdapter;

    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment_fragment2);
        initToolbar(R.string.title2, true);
        initViewModel(getActivity(), Fragment3.ViewModel.class);
    }

    @Override
    protected void onViewCreated(View view, Fragment3.ViewModel viewModel, Bundle instance) {

        mAdapter = new Adapter();
        createRecyclerView(mAdapter);
        markScreenAsCompleted();
    }

    @Override
    protected void loadStartUpData() {
        mViewModel.loadProviders(getRecyclerView());
    }

    @Override
    protected void setUpObservers() {
        mViewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {

                mAdapter.setData(strings);
            }
        });
    }

    public static class ViewModel extends BaseViewModel {

        MutableLiveData<List<String>> data = new MutableLiveData<>();

//        public Single<Boolean> whenAll(List<Single<?>> tasks) {
//            return Single.merge(tasks)
//                    .flatMap(new Function<Object, Publisher<?>>() {
//                @Override
//                public Publisher<?> apply(Object o) throws Exception {
//                    return null;
//                }
//            }).map(new Function<Object, Object>() {
//                @Override
//                public Object apply(Object o) throws Exception {
//                    return null;
//                }
//            });
//
//        }

        public void loadProviders(OnViewStatusChange onViewStatusChange) {
            Repository repository = new Repository();
            addDisposable(repository.getProvidresList()
                    .subscribeOn(getSchedulerProvider().io())
                    .doOnSuccess(new Consumer<JsonElement>() {
                        @Override
                        public void accept(JsonElement jsonElement) throws Exception {

                        }
                    })
                    .observeOn(getSchedulerProvider().ui())
                    .subscribeWith(new RetrofitObserver<JsonElement>(this, onViewStatusChange) {
                        @Override
                        public void onResultSuccess(JsonElement o) {
                            List<String> strings = new ArrayList<>();
                            strings.add("dsdsd");
                            strings.add("sdsdsdsdsd");
                            data.postValue(strings);
                        }
                    }));

        }

        public LiveData<List<String>> getData() {
            return data;
        }
    }


}
