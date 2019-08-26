package com.islam.basepropject.ui.ExRecyclerView

import android.os.Bundle
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.project_base.views.MyRecyclerView
import com.islam.basepropject.project_base.views.OnViewStatusChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Fragment3 : BaseSuperFragment<Fragment3.ViewModel>() {
    override var fragmentTag = "Fragment3"
    private var mAdapter: Adapter? = null
    private var myRecyclerView: MyRecyclerView? = null

    override fun onLaunch() {
        initContentView(R.layout.fragment_fragment2)
        initToolbar(R.string.title2)
        initViewModel(this, Fragment3.ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: Fragment3.ViewModel?, instance: Bundle?) {
        mAdapter = Adapter()
        myRecyclerView = createRecyclerView(mAdapter!!)

    }

    override fun setUpObservers() {
        mViewModel!!.getData().observe(viewLifecycleOwner, Observer { strings -> mAdapter?.setData(strings) })
    }

    class ViewModel : BaseViewModel() {

        internal var data = MutableLiveData<MutableList<String>>()
        override fun loadInitialData() {
            loadProviders(null)
        }

        fun loadProviders(onViewStatusChange: OnViewStatusChange?) {
            viewModelScope.launch(Dispatchers.IO) {
                delay(2500)
                val strings = ArrayList<String>()
                strings.add("dsdsd")
                strings.add("sdsdsdsdsd")
                data.postValue(strings)

            }

        }

        fun getData(): LiveData<MutableList<String>> {
            return data
        }
    }


}
