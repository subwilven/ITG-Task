package com.islam.basepropject.ui.ExViewPager;

import android.os.Bundle;
import android.view.View;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment;
import com.islam.basepropject.ui.Fragment2;

public class FragmentTab2 extends BaseSuperFragment<Fragment2.ViewModel> {


    @Override
    protected void onLaunch() {
        initContentView(R.layout.fragment_tab2);
        initViewModel(getParentFragment(),Fragment2.ViewModel.class);
    }

    @Override
    protected void onViewCreated(View view, Fragment2.ViewModel viewModel, Bundle instance) {

    }

    @Override
    protected void setUpObservers() {

    }
}
