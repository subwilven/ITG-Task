package com.islam.basepropject.ui.ExRecyclerView

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.MyRecyclerView
import com.islam.basepropject.project_base.views.OnViewStatusChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Fragment3 : BaseSuperFragment<ViewModel>() {
    override var fragmentTag = "Fragment3"
    private var mAdapter: Adapter? = null
    private var myRecyclerView: MyRecyclerView? = null

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment2)
        initToolbar(R.string.title2)
        initViewModel(this, ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: ViewModel?, instance: Bundle?) {
        mAdapter = Adapter()
        myRecyclerView = createRecyclerView(mAdapter!!)
        activity?.findViewById<FloatingActionButton>(R.id.fab)?.setOnClickListener {
            AddStringDialog().show(childFragmentManager)
        }

    }

    override fun setUpObservers() {
        mViewModel!!.getData().observe(viewLifecycleOwner, Observer { strings -> mAdapter?.submitList(strings) })
    }


}
