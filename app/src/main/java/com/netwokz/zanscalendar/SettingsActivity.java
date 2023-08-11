package com.netwokz.zanscalendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NavUtils;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    String mUiModePref;
    String mUiMode = "-1";
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mUiModePref = getString(R.string.pref_system);

        // Storing data into SharedPreferences
//        mSharedPreferences = getSharedPreferences(mUiModePref, MODE_PRIVATE);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

        // Creating an Editor object to edit(write to the file)
//        SharedPreferences.Editor myEdit = mSharedPreferences.edit();
//        mUiMode = mSharedPreferences.getInt(mUiModePref,0);

        // Storing the key and its value as the data fetched from edittext
//        myEdit.putString("name", name.getText().toString());
//        myEdit.putInt("age", Integer.parseInt(age.getText().toString()));

        // Once the changes have been made,
        // we need to commit to apply those changes made,
        // otherwise, it will throw an error
//        myEdit.apply();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(mUiModePref)){
            mUiMode = mSharedPreferences.getString(key,"-1");
            setUiTheme(mUiMode);
        }
    }

    public void setUiTheme(String mode){
        if (mode.equals("-1")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if (mode.equals("1")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }else if (mode.equals("2")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
        }
    }
}
