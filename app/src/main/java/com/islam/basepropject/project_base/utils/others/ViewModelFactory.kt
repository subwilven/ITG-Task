package com.islam.basepropject.project_base.utils.others

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.islam.basepropject.ui.ExFetchData.Fragment1
import com.islam.basepropject.ui.ExRecyclerView.Fragment3
import com.islam.basepropject.ui.ExViewPager.FragmentTab1
import com.islam.basepropject.ui.Fragment2
import com.islam.basepropject.ui.ExPaging.ExViewModel

class ViewModelFactory private constructor() : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(Fragment1.ViewModel::class.java)) {
            return Fragment1.ViewModel() as T
        }
        if (modelClass.isAssignableFrom(Fragment2.ViewModel::class.java)) {
            return Fragment2.ViewModel() as T
        }
        if (modelClass.isAssignableFrom(ExViewModel::class.java)) {
            return ExViewModel() as T
        }

        if (modelClass.isAssignableFrom(FragmentTab1.ViewModel::class.java)) {
            return FragmentTab1.ViewModel() as T
        }

        if (modelClass.isAssignableFrom(Fragment3.ViewModel::class.java)) {
            return Fragment3.ViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        private var INSTANCE: ViewModelFactory? = null

        val instance: ViewModelFactory
            get() {
                if (INSTANCE == null) {
                    synchronized(ViewModelFactory::class.java) {
                        if (INSTANCE == null) {
                            INSTANCE = ViewModelFactory()
                        }
                        return INSTANCE as ViewModelFactory
                    }
                }
                return INSTANCE as ViewModelFactory
            }
    }

}
