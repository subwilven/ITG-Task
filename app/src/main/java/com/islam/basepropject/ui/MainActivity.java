package com.islam.basepropject.ui;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.POJO.NavigationType;
import com.islam.basepropject.project_base.base.activities.BaseNavigationActivity;
import com.islam.basepropject.project_base.common.ui.settings.SettingsFragment;
import com.islam.basepropject.ui.ExFetchData.Fragment1;
import com.islam.basepropject.ui.ExRecyclerView.Fragment3;
import com.islam.basepropject.ui.ExPaging.ExFragment;
import com.islam.basepropject.ui.ExViewPager.Fragment4;

public class MainActivity extends BaseNavigationActivity {


    @Override
    public void onLaunch() {
        initContentView(R.layout.activity_main);
        initNavigation(new Class[]{Fragment1.class, Fragment3.class, Fragment4.class, ExFragment.class, SettingsFragment.class},
                new int[]{R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_tools,R.id.nav_settings},
                NavigationType.DrawerNavigation);
    }


}
