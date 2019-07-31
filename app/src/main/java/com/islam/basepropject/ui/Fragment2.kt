package com.islam.basepropject.ui


import android.os.Bundle

import androidx.fragment.app.Fragment

import android.util.Log
import android.view.View

import com.google.gson.JsonElement
import com.islam.basepropject.R
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.utils.network.RetrofitObserver


/**
 * A simple [Fragment] subclass.
 */
class Fragment2 : BaseFragment<Fragment2.ViewModel>() {

    internal var mViewModel: ViewModel? = null

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment2)
        initToolbar(R.string.title2, false)
        initViewModel(activity!!, ViewModel::class.java)
    }


    override fun onViewCreated(view: View, viewModel: ViewModel?, instance: Bundle?) {
        mViewModel = viewModel
    }

    override fun setUpObservers() {
        // mViewModel.loadProviders();
    }


    class ViewModel : BaseViewModel<Unit>() {
        //        public ViewModel(SchedulerProvider schedulerProvider) {
        //            super(schedulerProvider);
        //        }

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