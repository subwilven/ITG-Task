package com.islam.basepropject.ui.ExViewPager

import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope

import com.islam.basepropject.R
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.POJO.Message
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import kotlinx.android.synthetic.main.fragment_tab1.*
import kotlinx.coroutines.launch

class FragmentTab1 : BaseSuperFragment<Fragment4.ViewModel>() {

    override var fragmentTag = "FragmentTab1"
    override fun onLaunch() {
        initContentView(R.layout.fragment_tab1)
        initViewModel(parentFragment!!, Fragment4.ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: Fragment4.ViewModel, instance: Bundle?) {
        markScreenAsCompleted()
        btn_fetch2.setOnClickListener {viewModel!!.loadProviders(it.id)}
    }

    override fun setUpObservers() {

    }
}
