package com.islam.basepropject.ui.ExPaging

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer

import androidx.recyclerview.widget.RecyclerView

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment

class ExFragment : BaseSuperFragment<ExViewModel>() {

    private var mAdapter: ExAdapter? = null

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment2)
        initToolbar(0, false)
        initViewModel(this, ExViewModel::class.java)
    }


    override fun onViewCreated(view: View, viewModel: ExViewModel?, instance: Bundle?) {
        mAdapter = ExAdapter()

        createRecyclerView(mAdapter as ExAdapter )
    }

    override fun setUpObservers() {
        viewModel!!.orders.subscribe { strings -> mAdapter!!.submitList(strings) }

        viewModel?.networkState?.observe(viewLifecycleOwner, Observer
                { networkRequestState -> mAdapter!!.setNetworkState(networkRequestState) })
    }

}
