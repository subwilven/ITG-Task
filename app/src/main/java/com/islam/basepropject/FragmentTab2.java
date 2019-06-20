package com.islam.basepropject;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment;
import com.islam.basepropject.project_base.utils.others.ViewModelFactory;

public class FragmentTab2 extends BaseSuperFragment<Fragment2.ViewModel> {


    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment_tab2);
        initViewModel(ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(Fragment2.ViewModel.class));
    }

    @Override
    protected void onViewCreated(View view, Fragment2.ViewModel viewModel, Bundle instance) {

    }

    @Override
    protected void setUpObservers() {

    }
}
