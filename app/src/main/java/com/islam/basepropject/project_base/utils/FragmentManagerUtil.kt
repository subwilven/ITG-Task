package com.islam.basepropject.project_base.utils

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import com.islam.basepropject.R

object FragmentManagerUtil {


    fun replaceFragment(fragmentManager: FragmentManager,
                        fragment: Fragment,
                        @IdRes containerId: Int = R.id.container,
                        setToBackStack: Boolean = false) {
        val tag = fragment.javaClass.name
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment, tag)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        if (setToBackStack)
            transaction.addToBackStack(tag)

        transaction.commit()
    }


    fun addFragment(fragmentManager: FragmentManager,
                    fragment: Fragment, frameId: Int,
                    setToBackStack: Boolean) {

        val tag = fragment.javaClass.name

        val transaction = fragmentManager.beginTransaction()
        transaction.add(frameId, fragment, tag)

        if (setToBackStack)
            transaction.addToBackStack(null)

        transaction.commit()
    }

    fun getBackStackCount(fragmentManager: FragmentManager): Int {
        return fragmentManager.backStackEntryCount
    }

    fun isFragmentActive(fragmentManager: FragmentManager, fragmentClass: Class<*>): Boolean {
        val fragment = fragmentManager.findFragmentByTag(fragmentClass.name)
        return if (fragment != null && fragment.isVisible) true else false
    }

}
