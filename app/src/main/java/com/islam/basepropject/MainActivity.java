package com.islam.basepropject;

import android.os.Bundle;

import com.islam.basepropject.project_base.base.BaseViewModel;
import com.islam.basepropject.project_base.base.POJO.NavigationType;
import com.islam.basepropject.project_base.base.activities.BaseDrawerActivity;

public class MainActivity extends BaseDrawerActivity<MainActivity.ViewModel> {


    @Override
    public void onLaunch() {
        initNavigationDrawer(new Class[]{Fragment1.class, Fragment2.class, Fragment4.class},
                new int[]{R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow},
                NavigationType.DrawerNavigation);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    public Class<ViewModel> getViewModel() {
        return ViewModel.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, ViewModel viewModel) {


    }


    public static class ViewModel extends BaseViewModel {


        public ViewModel() {
            super();
        }


    }
}
