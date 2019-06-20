package com.islam.basepropject.project_base.base.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private Class<?>[] fragmentClasses;

    public ViewPagerAdapter(Fragment fragment, Class<?>[] fragmentClasses) {
        super(fragment);
        this.fragmentClasses = fragmentClasses;
    }

    @Override
    public int getItemCount() {
        return fragmentClasses.length;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        try {
            return (Fragment) fragmentClasses[position].newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


}