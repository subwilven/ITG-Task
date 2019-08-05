package com.islam.basepropject.ui.ExFetchData


import android.graphics.drawable.Animatable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView

import androidx.fragment.app.Fragment

import com.google.gson.JsonElement
import com.islam.basepropject.R
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.utils.FragmentManagerUtil
import com.islam.basepropject.project_base.utils.network.RetrofitObserver
import com.islam.basepropject.project_base.views.OnViewStatusChange
import com.islam.basepropject.ui.ExDialogs.Fragment5
import com.islam.basepropject.ui.ExRecyclerView.Fragment3

import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver


/**
 * A simple [Fragment] subclass.
 */
class Fragment1 : BaseFragment<Fragment1.ViewModel>() {

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment1)
        initToolbar(R.string.title1)
        initViewModel(activity!!, ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: ViewModel?, instance: Bundle?) {
        markScreenAsCompleted()
        view.findViewById<View>(R.id.btn_fetch)
                .setOnClickListener {
                    viewModel!!.loadProviders(view.findViewById<View>(R.id.btn_fetch) as OnViewStatusChange)
                                }
        // loadData();

        activity?.findViewById<View>(R.id.fab)?.setOnClickListener {  viewModel!!.loadProviders(activity
                ?.findViewById<View>(R.id.fab) as OnViewStatusChange)}
        view.setOnClickListener { navigate(Fragment5(),addToBackStack = true) }

    }


    override fun setUpObservers() {

    }

    class ViewModel : BaseViewModel() {

        fun loadProviders(onViewStatusChange: OnViewStatusChange) {
            val repository = Repository()
            addDisposable(repository.providresList
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribeWith(object : RetrofitObserver<JsonElement>(this, onViewStatusChange) {
                        override fun onResultSuccess(o: JsonElement) {

                        }
                    }))

        }
    }
}// Required empty public constructor
