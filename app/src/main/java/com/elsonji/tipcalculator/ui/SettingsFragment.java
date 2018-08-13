package com.elsonji.tipcalculator.ui;

import android.support.v4.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

import com.elsonji.tipcalculator.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences
        .OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preferences, false);

        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = prefScreen.getPreference(i);
            updatePreference(preference, preference.getKey());
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);

        if (key.equals(getString(R.string.pref_default_tip_key))) {
            updatePreference(preference, key);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        DialogFragment dialogFragment = null;
        if (preference instanceof NumberPickerPreference) {
            dialogFragment = NumberPreferenceDialogFragmentCompat.newInstance(preference.getKey());
        }

        if (dialogFragment != null) {
            dialogFragment.setTargetFragment(this, 0);
            if (this.getFragmentManager() != null) {
                dialogFragment.show(this.getFragmentManager(),
                        "android.support.v7.preference" +
                                ".PreferenceFragment.DIALOG");
            }
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

    private void updatePreference(Preference preference, String key) {
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        if (preference instanceof NumberPickerPreference) {
            NumberPickerPreference numberPickerPreference = (NumberPickerPreference) preference;
            String value = String.valueOf(sharedPreferences.getInt(key, -1));
            numberPickerPreference.setSummary("Your default tip percentage is set at " + value + " percent.");
        }

        /*if (preference instanceof SwitchPreferenceCompat) {
            SwitchPreferenceCompat switchPref = (SwitchPreferenceCompat)preference;
            boolean value = sharedPreferences.getBoolean(key, false);
            if (value) {

            } else {

            }
        }*/
    }


}
