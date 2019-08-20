package com.islam.basepropject.ui


import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.View

import com.islam.basepropject.R
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.fragments.BaseFragment



/**
 * A simple [Fragment] subclass.
 */
class Fragment2 : BaseFragment<Fragment2.ViewModel>() {

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment2)
        initToolbar(R.string.title2, false)
        initViewModel(activity!!, ViewModel::class.java)
    }


    override fun onViewCreated(view: View, viewModel: ViewModel?, instance: Bundle?) {

    }

    override fun setUpObservers() {
        // mViewModel.loadProviders();
    }


    class ViewModel : BaseViewModel() {
        //        public ViewModel(SchedulerProvider schedulerProvider) {
        //            super(schedulerProvider);
        //        }

        fun loadProviders() {
            val repository = Repository()
//            addDisposable(repository.providresList
//                    .subscribeOn(schedulerProvider.io())
//                    .observeOn(schedulerProvider.ui())
//                    .subscribeWith(object : RetrofitObserver<JsonElement>(this) {
//                        override fun onResultSuccess(o: JsonElement) {
//
//                        }
//                    }))

        }
    }
}
