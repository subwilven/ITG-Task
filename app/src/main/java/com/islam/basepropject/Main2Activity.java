package com.islam.basepropject;

import android.os.Bundle;

import com.islam.basepropject.project_base.base.POJO.NavigationType;
import com.islam.basepropject.project_base.base.activities.BaseNavigationActivity;
import com.islam.basepropject.ui.Fragment2;
import com.islam.basepropject.ui.ExViewPager.Fragment4;

public class Main2Activity extends BaseNavigationActivity {

    @Override
    public void onLaunch() {
        initNavigation(new Class[]{Fragment1.class, Fragment2.class, Fragment4.class},
                new int[]{R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications},
                NavigationType.BottomNavigation);    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}
