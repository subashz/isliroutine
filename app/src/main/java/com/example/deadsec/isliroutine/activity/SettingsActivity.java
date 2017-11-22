package com.example.deadsec.isliroutine.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deadsec.isliroutine.R;

/**
 * Created by deadsec on 11/21/17.
 */

public class SettingsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getFragmentManager().findFragmentById(android.R.id.content)==null) {
            getFragmentManager().beginTransaction().add(android.R.id.content,new Prefs()).commit();
        }
        getActionBar();
    }

    public static class Prefs extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
        }
    }
}
