package com.islam.basepropject.ui.ExViewPager

import android.os.Bundle
import android.view.View

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.ui.Fragment2

class FragmentTab2 : BaseSuperFragment<Fragment2.ViewModel>() {

    override var fragmentTag = "FragmentTab2"
    override fun onLaunch() {
        initContentView(R.layout.fragment_tab2)
        initViewModel(parentFragment!!, Fragment2.ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: Fragment2.ViewModel?, instance: Bundle?) {

    }

    override fun setUpObservers() {

    }
}
