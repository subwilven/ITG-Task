package com.islam.basepropject.project_base.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.islam.basepropject.R;

public class FragmentManagerUtil {


    public static void replaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, boolean setToBackStack) {
        replaceFragment(fragmentManager, fragment, R.id.container, setToBackStack);
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        replaceFragment(fragmentManager, fragment, frameId, false);
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment) {
        replaceFragment(fragmentManager, fragment, R.id.container, false);
    }

    public static void replaceFragment(@NonNull FragmentManager fragmentManager,
                                       @NonNull Fragment fragment, int frameId,
                                       boolean setToBackStack) {
        String tag = fragment.getClass().getName();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
        if (setToBackStack)
            transaction.addToBackStack(tag);

        transaction.commit();
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment, int frameId,
                                   boolean setToBackStack) {

        String tag = fragment.getClass().getName();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);

        if (setToBackStack)
            transaction.addToBackStack(null);

        transaction.commit();
    }

    public static int getBackStackCount(FragmentManager fragmentManager) {
        return fragmentManager.getBackStackEntryCount();
    }

    public static boolean isFragmentActive( FragmentManager fragmentManager ,Class fragmentClass) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        if(fragment!=null && fragment.isVisible())
            return true;
        return false;
    }

}
