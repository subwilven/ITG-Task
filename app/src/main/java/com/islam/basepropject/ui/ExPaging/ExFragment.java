package com.islam.basepropject.ui.ExPaging;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment;

public class ExFragment extends BaseSuperFragment<ExViewModel> {

    private ExAdapter mAdapter;

    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment_fragment2);
        initToolbar(0, false);
        initViewModel(this, ExViewModel.class);
    }


    @Override
    protected void onViewCreated(View view, ExViewModel viewModel, Bundle instance) {
        mAdapter = new ExAdapter();

        createRecyclerView(mAdapter);
    }

    @Override
    protected void setUpObservers() {
        mViewModel.getOrders().subscribe(strings -> mAdapter.submitList(strings));

        mViewModel.getNetworkState().observe(getViewLifecycleOwner(),
                networkRequestState -> mAdapter.setNetworkState(networkRequestState));
    }

}
