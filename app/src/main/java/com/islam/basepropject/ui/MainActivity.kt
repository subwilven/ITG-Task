package com.islam.basepropject.ui

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.POJO.NavigationType
import com.islam.basepropject.project_base.base.activities.BaseNavigationActivity
import com.islam.basepropject.project_base.common.ui.settings.SettingsFragment
import com.islam.basepropject.ui.ExFetchData.Fragment1
import com.islam.basepropject.ui.ExRecyclerView.Fragment3
import com.islam.basepropject.ui.ExPaging.ExFragment
import com.islam.basepropject.ui.ExViewPager.Fragment4

class MainActivity : BaseNavigationActivity() {


    override fun onLaunch() {
        initContentView(R.layout.activity_main)
        initNavigation(arrayOf<Class<*>>(Fragment1::class.java, Fragment3::class.java, Fragment4::class.java, ExFragment::class.java, SettingsFragment::class.java),
                intArrayOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_tools, R.id.nav_settings),
                NavigationType.DrawerNavigation)
    }


}
