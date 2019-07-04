package com.islam.basepropject.project_base.base.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.islam.basepropject.R;
import com.islam.basepropject.project_base.base.BaseViewModel;
import com.islam.basepropject.project_base.base.adapters.BaseAdapter;
import com.islam.basepropject.project_base.base.adapters.ViewPagerAdapter;

public abstract class BaseSuperFragment<V extends BaseViewModel> extends BaseFragment<V> {


    private RecyclerView mRecyclerView;
    private ViewPager2 mViewPager;

    public void createRecyclerView(BaseAdapter baseAdapter) {
        createRecyclerView(baseAdapter, new LinearLayoutManager(getContext()), true);
    }

    public void createRecyclerView(BaseAdapter baseAdapter, RecyclerView.LayoutManager layoutManager) {
        createRecyclerView(baseAdapter, layoutManager, true);
    }

    public void createRecyclerView(BaseAdapter baseAdapter, boolean hasFixedSize) {
        createRecyclerView(baseAdapter, new LinearLayoutManager(getContext()), hasFixedSize);
    }


    public void createRecyclerView(BaseAdapter baseAdapter, RecyclerView.LayoutManager layoutManager, boolean hasFixedSize) {
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        if (mRecyclerView == null)
            throw new IllegalStateException("There is no RecyclerView included in xml with id \"recyclerView\" ");
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(baseAdapter);
        mRecyclerView.setHasFixedSize(hasFixedSize);
    }

    public void createTabLayout(Class<?>[] fragmentsClass, String[] tabsNames) {

        TabLayout tabLayout = getView().findViewById(R.id.tabLayout);
        mViewPager = null;
        try {
            mViewPager = getView().findViewById(R.id.viewPager);
        } catch (ClassCastException e) {
            throw new IllegalStateException("use ViewPager2 instead of ViewPager ");
        }

        if (tabLayout == null)
            throw new IllegalStateException("There is no tabLayout included in xml with id \"tabLayout\" ");

        if (mViewPager == null)
            throw new IllegalStateException("There is no viewPager included in xml with id \"viewPager\" ");


        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragmentsClass);
        mViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        mViewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, mViewPager, true, (tab, position) -> {}).attach();

        for (int i = 0; i < tabsNames.length; i++) {
            tabLayout.getTabAt(i).setText(tabsNames[i]);
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public ViewPager2 getViewPager() {
        return mViewPager;
    }

    @Override
    public void onDestroy() {
        mRecyclerView = null;
        mViewPager = null;
        super.onDestroy();
    }
}
