package com.islam.basepropject.project_base.common.ui.settings

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

import com.islam.basepropject.R
import com.islam.basepropject.project_base.base.activities.BaseActivity
import com.islam.basepropject.project_base.base.fragments.BaseFragment
import com.islam.basepropject.ui.ExFetchData.Fragment1

class SettingsFragment : PreferenceFragmentCompat(),TagedFragment {

    override val fragmentTag: String
        get() = "SettingsFragment"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as BaseActivity).setToolbarTitle(R.string.settings)
    }

    override fun onCreatePreferences(state: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        val listPreference = findPreference<ListPreference>("language")
        listPreference?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            activity!!.recreate()
            true
        }
        listPreference?.summary = listPreference?.entry

    }
}
