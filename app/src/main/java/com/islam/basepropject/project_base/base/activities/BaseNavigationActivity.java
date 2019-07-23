package com.islam.basepropject.project_base.base.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.POJO.NavigationType;
import com.islam.basepropject.project_base.utils.FragmentManagerUtil;

import java.util.Arrays;
import java.util.List;

public abstract class BaseNavigationActivity extends BaseActivity {


    private NavigationType navigationType;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView drawerNavigationView;
    private BottomNavigationView bottomNavigationView;
    private List<Class<?>> fragmentsClassesList;
    private int[] menuItemIds;
    private int currentFragmentIndex = -1;



    Handler closeDrawerHandler = new Handler();
    final Runnable r = new Runnable() {
        public void run() {
            drawer.closeDrawer(GravityCompat.START);
        }
    };

    public void initNavigation(Class<?>[] fragmentsClasses, int[] menuItemIds, NavigationType navigationType) {
        this.fragmentsClassesList = Arrays.asList(fragmentsClasses);
        this.menuItemIds = menuItemIds;
        this.navigationType = navigationType;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (menuItemIds.length != fragmentsClassesList.size()) {
            throw new RuntimeException("Numbers of Ids should be equal to number of fragment classes");
        }

        if (navigationType == NavigationType.BottomNavigation)
            setUpBottomNavigation(savedInstanceState);
        else if (navigationType == NavigationType.DrawerNavigation)
            setUpNavigationDrawer(savedInstanceState);

    }

    private void setUpBottomNavigation(Bundle savedInstanceState) {
        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener);

        //launch the frist fragment for the frist time
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(menuItemIds[0]);
            onBottomNavigationItemSelectedListener
                    .onNavigationItemSelected(bottomNavigationView.getMenu().getItem(0));
        }
    }


    private void setUpNavigationDrawer(Bundle savedInstanceState) {

        drawer = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawerNavigationView = findViewById(R.id.nav_view);
        drawerNavigationView.setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener);

        //launch the frist fragment for the frist time
        if (savedInstanceState == null) {
            drawerNavigationView.setCheckedItem(menuItemIds[0]);
            onDrawerNavigationItemSelectedListener
                    .onNavigationItemSelected(drawerNavigationView.getMenu().getItem(0));
        }
    }

    @Override
    public void enableBackButton(boolean enableBackButton) {

        if (navigationType == NavigationType.DrawerNavigation)
            if (enableBackButton) {
                toggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                toggle.setToolbarNavigationClickListener(v -> onBackPressed());
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                toggle.setDrawerIndicatorEnabled(true);
                toggle.setToolbarNavigationClickListener(null);
                toggle.syncState();
            }

    }

    NavigationView.OnNavigationItemSelectedListener
            onDrawerNavigationItemSelectedListener = this::onItemSelected;

    BottomNavigationView.OnNavigationItemSelectedListener
            onBottomNavigationItemSelectedListener = this::onItemSelected;

    public boolean onItemSelected(@NonNull MenuItem item) {

        Class<?> fragmentClass = null;
        int newFragmentIndex = -1;

        //get the fragment class of clicked item to create new instance of it
        for (int i = 0; i < menuItemIds.length; i++) {
            if (item.getItemId() == menuItemIds[i]) {
                fragmentClass = fragmentsClassesList.get(i);
                newFragmentIndex = i;
            }
        }

        //if user click the same running fragment do nothing and close the drawer
        if (fragmentClass == null || currentFragmentIndex == newFragmentIndex) {
            if (navigationType == NavigationType.DrawerNavigation)
                drawer.closeDrawer(GravityCompat.START);
            return true;
        }

        currentFragmentIndex = newFragmentIndex;
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            FragmentManagerUtil.replaceFragment(getSupportFragmentManager(), fragment);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        if (navigationType == NavigationType.DrawerNavigation)
            closeDrawerHandler.postDelayed(r, 200);

        return true;

    }

    @Override
    public void onBackPressed() {

        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isDrawerFragment()) {// if any fragment of the drawer fragment active make the frist one active
            if (navigationType == NavigationType.DrawerNavigation) {
                drawerNavigationView.setCheckedItem(menuItemIds[0]);
                onDrawerNavigationItemSelectedListener
                        .onNavigationItemSelected(drawerNavigationView.getMenu().getItem(0));
            } else if (navigationType == NavigationType.BottomNavigation) {
                bottomNavigationView.setSelectedItemId(menuItemIds[0]);
                onBottomNavigationItemSelectedListener
                        .onNavigationItemSelected(bottomNavigationView.getMenu().getItem(0));
            }
        } else {
            super.onBackPressed();
        }
    }

    private boolean isDrawerFragment() {
        return FragmentManagerUtil.getBackStackCount(getSupportFragmentManager()) == 0 &&
                !FragmentManagerUtil.isFragmentActive(getSupportFragmentManager(), fragmentsClassesList.get(0));
    }

    public void setElevation(float f) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            appBarLayout.setElevation(f);
//        }
    }
}
