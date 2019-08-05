package com.islam.basepropject.project_base.base.activities

import android.os.Bundle
import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.project_base.common.ui.intro.IntroFragment

abstract class BaseActivityFragment :BaseActivity(){

    override fun onLaunch() {
        initContentView(R.layout.activity_empty)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            navigate(supportFragmentManager, getStartFragment())
    }

    abstract fun getStartFragment():BaseFragment<*>
}