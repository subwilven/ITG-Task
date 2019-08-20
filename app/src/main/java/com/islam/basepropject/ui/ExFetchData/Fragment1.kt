package com.islam.basepropject.ui.ExFetchData


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.islam.basepropject.R
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.other.network.RetrofitCorounites
import com.islam.basepropject.project_base.views.OnViewStatusChange
import com.islam.basepropject.ui.ExDialogs.Fragment5
import kotlinx.android.synthetic.main.fragment_fragment1.*
import kotlinx.coroutines.launch

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
        //markScreenAsCompleted()
        btnFetch.setOnClickListener {
            viewModel!!.loadProviders(btnFetch as OnViewStatusChange)
        }

        activity?.findViewById<View>(R.id.fab)?.setOnClickListener {
            viewModel!!.loadProviders(activity
                    ?.findViewById<View>(R.id.fab) as OnViewStatusChange)
        }
        view.setOnClickListener { navigate(Fragment5(), addToBackStack = true) }

    }

    override fun loadStartUpData() {
        mViewModel!!.loadProviders(null)
    }

    override fun setUpObservers() {

    }

    class ViewModel : BaseViewModel() {
        val repository = Repository()

        fun loadProviders(view: OnViewStatusChange?) {
            viewModelScope.launch {
                val providersList = RetrofitCorounites(this@ViewModel,view).networkCall {repository.getProvidersList()}
                markAsCompleted(listOf(providersList))
            }

//
//            addDisposable(repository.providresList
//                    .subscribeOn(schedulerProvider.io())
//                    .observeOn(schedulerProvider.ui())
//                    .subscribeWith(object : RetrofitObserver<JsonElement>(this, onViewStatusChange) {
//                        override fun onResultSuccess(o: JsonElement) {
//
//                        }
//                    }))

        }

    }
}// Required empty public constructor






