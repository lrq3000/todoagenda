package org.andstatus.todoagenda.prefs;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.andstatus.todoagenda.R;

public class ColorsPreferencesFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_colors);
    }
}