package com.islam.basepropject.ui.ExViewPager

import android.os.Bundle
import android.util.Log
import android.view.View

import com.google.gson.JsonElement
import com.islam.basepropject.R
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.utils.network.RetrofitObserver

class FragmentTab1 : BaseSuperFragment<FragmentTab1.ViewModel>() {


    override fun onLaunch() {
        initContentView(R.layout.fragment_tab1)
        initViewModel(parentFragment!!, FragmentTab1.ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: FragmentTab1.ViewModel?, instance: Bundle?) {
        view.findViewById<View>(R.id.btn_fetch2).setOnClickListener { v -> viewModel!!.loadProviders() }
    }

    override fun setUpObservers() {

    }

    class ViewModel : BaseViewModel() {
        fun loadProviders() {
            val repository = Repository()
            addDisposable(repository.providresList
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribeWith(object : RetrofitObserver<JsonElement>(this) {
                        override fun onResultSuccess(o: JsonElement) {

                        }
                    }))

        }
    }
}
