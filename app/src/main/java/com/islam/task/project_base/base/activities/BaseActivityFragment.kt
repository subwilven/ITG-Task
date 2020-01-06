package com.islam.task.project_base.base.activities

import android.os.Bundle
import com.islam.task.R
import com.islam.task.project_base.base.fragments.BaseFragment

abstract class BaseActivityFragment :BaseActivity(){

    override val layoutId = R.layout.activity_empty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null)
            navigate(supportFragmentManager, getStartFragment())
    }

    abstract fun getStartFragment():BaseFragment<*>
}