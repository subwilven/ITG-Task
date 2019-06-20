package com.islam.basepropject;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProviders;

import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment;
import com.islam.basepropject.project_base.utils.others.ViewModelFactory;

public class Fragment4 extends BaseSuperFragment<Fragment2.ViewModel> {

    public Fragment4() {
    }

    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment4);
        initToolbar(R.string.title2, true);
        initViewModel(ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(Fragment2.ViewModel.class));
    }

    @Override
    protected void onViewCreated(View view, Fragment2.ViewModel viewModel, Bundle instance) {
        createTabLayout(new Class[]{FragmentTab1.class,FragmentTab2.class},new String[]{"fragment1","fragment2"});
    }

    @Override
    protected void setUpObservers() {

    }

}