package com.islam.basepropject;

import com.islam.basepropject.project_base.base.POJO.NavigationType;
import com.islam.basepropject.project_base.base.activities.BaseNavigationActivity;
import com.islam.basepropject.project_base.common.ui.settings.SettingsFragment;
import com.islam.basepropject.ui.ExPaging.ExFragment;
import com.islam.basepropject.ui.Fragment2;
import com.islam.basepropject.ui.ExViewPager.Fragment4;

public class MainActivity extends BaseNavigationActivity {


    @Override
    public void onLaunch() {
        initNavigation(new Class[]{Fragment1.class, Fragment2.class, Fragment4.class, ExFragment.class, SettingsFragment.class},
                new int[]{R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_tools,R.id.nav_settings},
                NavigationType.DrawerNavigation);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
