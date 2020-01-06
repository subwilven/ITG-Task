package com.islam.task.ui

import com.islam.task.R
import com.islam.task.project_base.POJO.NavigationType
import com.islam.task.project_base.base.activities.BaseNavigationActivity
import com.islam.task.project_base.common.ui.settings.SettingsFragment
import com.islam.task.ui.examples.ExFetchData.Fragment1
import com.islam.task.ui.examples.ExPaging.ExFragment
import com.islam.task.ui.examples.ExRecyclerView.Fragment3
import com.islam.task.ui.examples.Fragment4

class MainActivity : BaseNavigationActivity() {

    override val layoutId = R.layout.activity_main

    override val navigationType = NavigationType.DrawerNavigation

    override val menuIdsListWithFragment: MutableList<Pair<Int, Class<*>>>
        get() = mutableListOf<Pair<Int, Class<*>>>().run {
                add(Pair(R.id.nav_home, Fragment1::class.java))
                add(Pair(R.id.nav_gallery, Fragment3::class.java))
                add(Pair(R.id.nav_slideshow, Fragment4::class.java))
                add(Pair(R.id.nav_tools, ExFragment::class.java))
                add(Pair(R.id.nav_settings, SettingsFragment::class.java))
                this
            }


}

