package com.islam.task.ui

import com.islam.task.R
import com.islam.task.project_base.base.activities.BaseActivityFragment
import com.islam.task.project_base.base.fragments.BaseFragment
import com.islam.task.ui.marvels.MarvelsFragment

class MainActivity : BaseActivityFragment() {
    override fun getStartFragment(): BaseFragment<*> {
        return MarvelsFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
       invalidateOptionsMenu()
    }
}

