package com.islam.basepropject.ui.ExPaging

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment

class ExFragment : BaseSuperFragment<ExViewModel>() {
    override var fragmentTag = "ExFragment"
    private var mAdapter = ExAdapter()

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment2)
        initToolbar(0, false)
        initViewModel(this, ExViewModel::class.java)
    }


    override fun onViewCreated(view: View, viewModel: ExViewModel?, instance: Bundle?) {
        createRecyclerView(mAdapter)
        mViewModel?.loadOrders(mAdapter)
    }

    override fun setUpObservers() {
        mViewModel!!.orders?.observe(viewLifecycleOwner, Observer {mAdapter.submitList(it)})
    }

}
