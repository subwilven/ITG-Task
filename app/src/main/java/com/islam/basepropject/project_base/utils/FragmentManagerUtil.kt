package com.islam.basepropject.project_base.utils

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseFragment

object FragmentManagerUtil {


    fun replaceFragment(fragmentManager: FragmentManager,
                        fragment: BaseFragment<*>,
                        @IdRes containerId: Int = R.id.container,
                        setToBackStack: Boolean = false) {

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment, fragment.fragmentTag)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        if (setToBackStack)
            transaction.addToBackStack(fragment.fragmentTag)

        transaction.commit()
    }


    fun addFragment(fragmentManager: FragmentManager,
                    fragment: BaseFragment<*>, frameId: Int,
                    setToBackStack: Boolean) {


        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment, fragment.fragmentTag)

        if (setToBackStack)
            transaction.addToBackStack(null)

        transaction.commit()
    }

    fun getBackStackCount(fragmentManager: FragmentManager): Int {
        return fragmentManager.backStackEntryCount
    }

    fun isFragmentActive(fragmentManager: FragmentManager, tag: String): Boolean {
        val fragment = fragmentManager.findFragmentByTag(tag)
        return fragment != null && fragment.isVisible
    }

}
