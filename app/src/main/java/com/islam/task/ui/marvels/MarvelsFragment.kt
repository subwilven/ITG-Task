package com.islam.task.ui.marvels

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import com.islam.task.R
import com.islam.task.project_base.base.fragments.BaseSuperFragment
import com.islam.task.ui.marvel_Details.MarvelDetailFragment
import com.islam.task.util.DefaultTextWatcher
import kotlinx.android.synthetic.main.fragment_fragment2.*
import kotlinx.android.synthetic.main.layout_search.*

class MarvelsFragment : BaseSuperFragment<MarvelsViewModel>() {
    override var fragmentTag = "MarvelsFragment"
    private var mAdapter: MarvelsAdapter? = null

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment2)
        initToolbar(0, false, R.menu.fragment_marvel)
        initViewModel(activity!!, MarvelsViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: MarvelsViewModel, instance: Bundle?) {
        markScreenAsCompleted()
        mAdapter = MarvelsAdapter(viewModel)
        initUI()
        loadMarvels()
    }

    override fun setUpObservers() {
        mViewModel.marvels?.observe(viewLifecycleOwner, Observer { mAdapter?.submitList(it) })

        mViewModel.navigateToMarvelDetails.observes(viewLifecycleOwner, Observer {
            navigate(MarvelDetailFragment.newInstance(it), addToBackStack = true)
        })
    }


    private fun initUI() {

        createRecyclerView(mAdapter!!)

        tv_cancel.setOnClickListener {
            viewSwitcher.showNext()
            et_search.setText("")
            loadMarvels()
        }
        et_search.addTextChangedListener(DefaultTextWatcher { loadMarvels(it) })
    }

    private fun loadMarvels(searchQuery: String = "") {
        mViewModel.loadOrders(R.id.recyclerView, searchQuery)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            viewSwitcher.showNext()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}