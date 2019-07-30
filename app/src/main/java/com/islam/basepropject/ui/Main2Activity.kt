package com.islam.basepropject.ui

import android.os.Bundle

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.POJO.NavigationType
import com.islam.basepropject.project_base.base.activities.BaseNavigationActivity
import com.islam.basepropject.ui.ExFetchData.Fragment1
import com.islam.basepropject.ui.ExViewPager.Fragment4

class Main2Activity : BaseNavigationActivity() {


    override fun onLaunch() {
        initContentView(R.layout.activity_main2)
        initNavigation(arrayOf<Class<*>>(Fragment1::class.java, Fragment2::class.java, Fragment4::class.java),
                intArrayOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications),
                NavigationType.BottomNavigation)
    }


}
