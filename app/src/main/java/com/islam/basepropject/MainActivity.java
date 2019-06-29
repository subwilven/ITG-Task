package com.islam.basepropject;

import com.islam.basepropject.project_base.base.POJO.NavigationType;
import com.islam.basepropject.project_base.base.activities.BaseNavigationActivity;

public class MainActivity extends BaseNavigationActivity {


    @Override
    public void onLaunch() {
        initNavigation(new Class[]{Fragment1.class, Fragment2.class, Fragment4.class},
                new int[]{R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow},
                NavigationType.DrawerNavigation);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}
