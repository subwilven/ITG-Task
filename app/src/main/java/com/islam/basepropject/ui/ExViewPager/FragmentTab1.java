package com.islam.basepropject.ui.ExViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonElement;
import com.islam.basepropject.R;
import com.islam.basepropject.data.Repository;
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment;
import com.islam.basepropject.project_base.base.other.BaseViewModel;
import com.islam.basepropject.project_base.utils.network.RetrofitObserver;

public class FragmentTab1 extends BaseSuperFragment<FragmentTab1.ViewModel> {


    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment_tab1);
        initViewModel(getParentFragment(), FragmentTab1.ViewModel.class);
    }

    @Override
    protected void onViewCreated(View view, FragmentTab1.ViewModel viewModel, Bundle instance) {

        view.findViewById(R.id.btn_fetch2).setOnClickListener(v -> viewModel.loadProviders());

    }

    @Override
    protected void setUpObservers() {

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
