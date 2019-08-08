package com.islam.basepropject.ui.ExRecyclerView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

import com.google.gson.JsonElement
import com.islam.basepropject.R
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.base.other.network.RetrofitObserver
import com.islam.basepropject.project_base.views.OnViewStatusChange

import java.util.ArrayList

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

class Fragment3 : BaseSuperFragment<Fragment3.ViewModel>() {

    internal var mAdapter: Adapter? =null

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment2)
        initToolbar(R.string.title2)
        initViewModel(this, Fragment3.ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: Fragment3.ViewModel?, instance: Bundle?) {
        mAdapter = Adapter()
        createRecyclerView(mAdapter!!)
    }

    override fun loadStartUpData() {
        mViewModel!!.loadProviders(recyclerView as OnViewStatusChange)
    }

    override fun setUpObservers() {
        mViewModel!!.getData().observe(viewLifecycleOwner, Observer { strings -> mAdapter?.setData(strings) })
    }

    class ViewModel : BaseViewModel() {

        internal var data = MutableLiveData<MutableList<String>>()

        fun loadProviders(onViewStatusChange: OnViewStatusChange) {
            if(data.value!=null)
                return
            val repository = Repository()
            addDisposable(repository.providresList
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribeWith(object : RetrofitObserver<JsonElement>(this, onViewStatusChange) {
                        override fun onResultSuccess(o: JsonElement) {
                            val strings = ArrayList<String>()
                            strings.add("dsdsd")
                            strings.add("sdsdsdsdsd")
                            data.postValue(strings)
                        }
                    }))

        }

        fun getData(): LiveData<MutableList<String>> {
            return data
        }
    }


}
