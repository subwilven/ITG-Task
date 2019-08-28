package com.islam.basepropject.ui

import com.islam.basepropject.R
import com.islam.basepropject.project_base.POJO.NavigationType
import com.islam.basepropject.project_base.base.activities.BaseNavigationActivity
import com.islam.basepropject.ui.ExFetchData.Fragment1
import com.islam.basepropject.ui.ExViewPager.Fragment4

class Main2Activity : BaseNavigationActivity() {
    override val layoutId = R.layout.activity_main2

    override val navigationType = NavigationType.BottomNavigation

    override val menuIdsListWithFragment: MutableList<Pair<Int, Class<*>>>
        get() = mutableListOf<Pair<Int, Class<*>>>().run {
            add(Pair(R.id.navigation_home, Fragment1::class.java))
            add(Pair(R.id.navigation_dashboard, Fragment2::class.java))
            add(Pair(R.id.navigation_notifications, Fragment4::class.java))
            this
        }
}
