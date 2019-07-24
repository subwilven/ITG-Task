package com.islam.basepropject.ui.ExViewPager;

import android.os.Bundle;
import android.view.View;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment;

public class Fragment4 extends BaseSuperFragment<FragmentTab1.ViewModel> {

    public Fragment4() {}

    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment4,true);
        initToolbar(R.string.title2, true);
        initViewModel(getActivity(), FragmentTab1.ViewModel.class);
    }

    @Override
    protected void onViewCreated(View view, FragmentTab1.ViewModel viewModel, Bundle instance) {
        createTabLayout(new Class[]{ FragmentTab2.class,FragmentTab1.class,FragmentTab1.class},new String[]{"fragment2","fragment1","fragment11"});
    }

    @Override
    protected void setUpObservers() {

    }

}