package com.islam.basepropject.ui.examples.ExFetchData


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.ui.examples.ExDialogs.Fragment5
import kotlinx.android.synthetic.main.fragment_fragment1.*


/**
 * A simple [Fragment] subclass.
 */
class Fragment1 : BaseFragment<ViewModel>() {
    override var fragmentTag = "Fragment1"

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment1)
        initToolbar(R.string.title1)
        initViewModel(this, ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: ViewModel, instance: Bundle?) {

        btnFetch.setOnClickListener { viewModel!!.loadProviders(btnFetch.id) }
        addSensitiveInputs(editText)
        view.setOnClickListener { navigate(Fragment5(), addToBackStack = true) }
    }


    override fun setUpObservers() {}
}






