package com.islam.basepropject.ui;

import android.os.Bundle;

import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.POJO.NavigationType;
import com.islam.basepropject.project_base.base.activities.BaseNavigationActivity;
import com.islam.basepropject.ui.ExFetchData.Fragment1;
import com.islam.basepropject.ui.ExViewPager.Fragment4;

public class Main2Activity extends BaseNavigationActivity {

    @Override
    public void onLaunch() {
        initContentView(R.layout.activity_main2);
        initNavigation(new Class[]{Fragment1.class, Fragment2.class, Fragment4.class},
                new int[]{R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications},
                NavigationType.BottomNavigation);    }


}
