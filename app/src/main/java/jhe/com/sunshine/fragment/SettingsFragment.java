package jhe.com.sunshine.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jhe.com.sunshine.R;

/**
 * Created by jens on 15.02.16.
 */


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.settings);

    }
}
