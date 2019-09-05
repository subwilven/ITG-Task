package com.islam.basepropject.ui.ExViewPager

import android.os.Bundle
import android.view.View

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.ui.Fragment2

class FragmentTab2 : BaseSuperFragment<Fragment4.ViewModel>() {

    override var fragmentTag = "FragmentTab2"
    override fun onLaunch() {
        initContentView(R.layout.fragment_tab2)
        initViewModel(parentFragment!!, Fragment4.ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: Fragment4.ViewModel?, instance: Bundle?) {

    }

    override fun setUpObservers() {

    }
}
