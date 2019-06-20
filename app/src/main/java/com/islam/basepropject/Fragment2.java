package com.islam.basepropject;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonElement;
import com.islam.basepropject.data.Repository;
import com.islam.basepropject.project_base.base.BaseViewModel;
import com.islam.basepropject.project_base.base.fragments.BaseFragment;
import com.islam.basepropject.project_base.utils.network.RetrofitObserver;
import com.islam.basepropject.project_base.utils.others.SchedulerProvider;
import com.islam.basepropject.project_base.utils.others.ViewModelFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends BaseFragment<Fragment2.ViewModel> {

    ViewModel mViewModel;

    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment_fragment2);
        initToolbar(R.string.title2, false);
        initViewModel(ViewModelProviders.of(getActivity(), ViewModelFactory.getInstance()).get(Fragment2.ViewModel.class));
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
                        public void onSuccess(JsonElement o) {
                            super.onSuccess(o);
                            Log.i("network",o.toString());
                        }
                    });

        }
    }
}
