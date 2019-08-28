package com.islam.basepropject.ui.ExFetchData


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.islam.basepropject.R
import com.islam.basepropject.data.Repository
import com.islam.basepropject.pojo.Article
import com.islam.basepropject.project_base.POJO.ApiResponse
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.other.network.Result
import com.islam.basepropject.project_base.views.OnViewStatusChange
import com.islam.basepropject.ui.ExDialogs.Fragment5
import kotlinx.android.synthetic.main.fragment_fragment1.*
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 */
class Fragment1 : BaseFragment<Fragment1.ViewModel>() {
    override var fragmentTag = "Fragment1"

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment1)
        initToolbar(R.string.title1)
        initViewModel(this, ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: ViewModel?, instance: Bundle?) {
        //markScreenAsCompleted()
        btnFetch.setOnClickListener {
            viewModel!!.loadProviders(btnFetch.id)
        }
        addSensitiveInputs(editText)
        activity?.findViewById<View>(R.id.fab)?.setOnClickListener {

        }
        view.setOnClickListener { navigate(Fragment5(), addToBackStack = true) }

    }


    override fun setUpObservers() {

    }

    class ViewModel : BaseViewModel() {
        val repository = Repository()

        override fun loadInitialData() {
            loadProviders(null)
        }

        var providersList: Result<ApiResponse<List<Article>>>? = null
        fun loadProviders(viewId: Int?) {
                viewModelScope.launch {
                    providersList = networkCall(viewId) { repository.getProvidersList() }
                    markAsCompleted(listOf(providersList!!))
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






