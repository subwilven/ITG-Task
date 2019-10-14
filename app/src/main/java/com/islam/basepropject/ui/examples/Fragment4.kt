package com.islam.basepropject.ui.examples

import android.os.Bundle
import android.view.View
import androidx.lifecycle.viewModelScope

import com.islam.basepropject.R
import com.islam.basepropject.data.Repository
import com.islam.basepropject.project_base.base.fragments.BaseSuperFragment
import com.islam.basepropject.project_base.base.other.BaseViewModel
import com.islam.basepropject.ui.examples.ExViewPager.FragmentTab1
import com.islam.basepropject.ui.examples.ExViewPager.FragmentTab2
import kotlinx.coroutines.launch

class Fragment4 : BaseSuperFragment<Fragment4.ViewModel>() {
    override var fragmentTag = "Fragment4"
    override fun onLaunch() {
        initContentView(R.layout.fragment4, true)
        initToolbar(R.string.title2, true)
        initViewModel(this, ViewModel::class.java)
    }

    override fun onViewCreated(view: View, viewModel: ViewModel, instance: Bundle?) {
        createViewPagerWithTabLayout(
                arrayOf(Pair(FragmentTab2::class.java, "fragment2"),
                        Pair(FragmentTab1::class.java, "fragment1"),
                        Pair(FragmentTab1::class.java, "fragment11")))
    }

    override fun setUpObservers() {

    }

    override fun onStop() {
        super.onStop()
    }


    class ViewModel : BaseViewModel(){
        val repository = Repository()

        fun loadProviders(viewId :Int?) {
            viewModelScope.launch {
                val providersList = networkCall(viewId) { repository.getProvidersList() }
                markAsCompleted(listOf(providersList))
            }

        }
    }

}