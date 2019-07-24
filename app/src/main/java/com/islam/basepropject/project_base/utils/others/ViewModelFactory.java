package com.islam.basepropject.project_base.utils.others;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.islam.basepropject.ui.ExFetchData.Fragment1;
import com.islam.basepropject.ui.ExViewPager.FragmentTab1;
import com.islam.basepropject.ui.Fragment2;
import com.islam.basepropject.ui.ExPaging.ExViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static ViewModelFactory INSTANCE;
    ;

    public static ViewModelFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory();
                }
                return INSTANCE;
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory() {}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(Fragment1.ViewModel.class)) {
            return (T) new Fragment1.ViewModel();
        }
        if (modelClass.isAssignableFrom(Fragment2.ViewModel.class)) {
            return (T) new Fragment2.ViewModel();
        }
        if (modelClass.isAssignableFrom(ExViewModel.class)) {
            return (T) new ExViewModel();
        }

        if (modelClass.isAssignableFrom(FragmentTab1.ViewModel.class)) {
            return (T) new FragmentTab1.ViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

}
