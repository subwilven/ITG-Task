package com.islam.basepropject.project_base.common.ui.settings;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.islam.basepropject.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

    }
}
