package com.islam.basepropject.ui.ExViewPager

import android.os.Bundle
import android.view.View

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment

class Fragment4 : BaseSuperFragment<FragmentTab1.ViewModel>() {
    override var fragmentTag = "Fragment4"
    override fun onLaunch() {
        initContentView(R.layout.fragment4,true)
        initToolbar(R.string.title2, true)
        initViewModel(activity!!, FragmentTab1.ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: FragmentTab1.ViewModel?, instance: Bundle?) {
        createViewPagerWithTabLayout(arrayOf(FragmentTab2::class.java, FragmentTab1::class.java, FragmentTab1::class.java), arrayOf("fragment2", "fragment1", "fragment11"))
    }

    override fun setUpObservers() {

    }

}