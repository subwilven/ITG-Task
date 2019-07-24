package com.islam.basepropject.ui.ExFetchData;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.gson.JsonElement;
import com.islam.basepropject.R;
import com.islam.basepropject.data.Repository;
import com.islam.basepropject.project_base.base.fragments.BaseFragment;
import com.islam.basepropject.project_base.base.other.BaseViewModel;
import com.islam.basepropject.project_base.utils.FragmentManagerUtil;
import com.islam.basepropject.project_base.utils.network.RetrofitObserver;
import com.islam.basepropject.ui.ExRecyclerView.Fragment3;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends BaseFragment<Fragment1.ViewModel> {


    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment_fragment1);
        initToolbar(R.string.title1, false);
        initViewModel(getActivity(), ViewModel.class);
    }

    @Override
    protected void onViewCreated(View view, ViewModel viewModel, Bundle instance) {

        view.findViewById(R.id.btn_fetch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        // loadData();
        (view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerUtil.replaceFragment(getFragmentManager(), new Fragment3(), true);
            }
        });

    }

    public void loadData() {
        getViewModel().loadProviders();
    }

    @Override
    protected void setUpObservers() {

    }

    @Override
    protected void onRetry() {
        loadData();
    }


    public static class ViewModel extends BaseViewModel {

        public void loadProviders() {
            Repository repository = new Repository();
            addDisposable(repository.getProvidresList()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribeWith(new RetrofitObserver<JsonElement>(this) {
                        @Override
                        public void onResultSuccess(JsonElement o) {
                            Log.i("network", o.toString());
                        }
                    }));

        }
    }
}
