package com.islam.basepropject.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;

import com.google.gson.JsonElement;
import com.islam.basepropject.R;
import com.islam.basepropject.data.Repository;
import com.islam.basepropject.project_base.base.other.BaseViewModel;
import com.islam.basepropject.project_base.base.fragments.BaseFragment;
import com.islam.basepropject.project_base.utils.network.RetrofitObserver;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends BaseFragment<Fragment2.ViewModel> {

    ViewModel mViewModel;

    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment_fragment2);
        initToolbar(R.string.title2, false);
        initViewModel(getActivity(),ViewModel.class);
    }


    @Override
    protected void onViewCreated(View view, ViewModel viewModel, Bundle instance) {
        mViewModel =viewModel;
    }

    @Override
    protected void setUpObservers() {
       // mViewModel.loadProviders();
    }


    public static class ViewModel extends BaseViewModel {
//        public ViewModel(SchedulerProvider schedulerProvider) {
//            super(schedulerProvider);
//        }

        public void loadProviders() {
            Repository repository = new Repository();
            repository.getProvidresList()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribeWith(new RetrofitObserver<JsonElement>(this) {
                        @Override
                        public void onResultSuccess(JsonElement o) {
                            Log.i("network",o.toString());
                        }
                    });

        }
    }
}
