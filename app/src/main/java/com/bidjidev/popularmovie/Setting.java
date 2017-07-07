package com.bidjidev.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Setting extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_setting);
        bindPrefernenceSummaryToValue(findPreference(getString(R.string.pref_sorting_criteria_key)));

    }

    private void bindPrefernenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        onPreferenceChange(preference,
                PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), ""));


    }

    @Override
    public boolean onPreferenceChange(Preference pref, Object value) {
        String stringValue = value.toString();
        if (pref instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) pref;
            int indexPref = listPreference.findIndexOfValue(stringValue);
            if (indexPref >= 0) {
                pref.setSummary(listPreference.getEntries()[indexPref]);
            }
        } else {
            pref.setSummary(stringValue);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}
