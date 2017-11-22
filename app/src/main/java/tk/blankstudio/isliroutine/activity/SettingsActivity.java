package tk.blankstudio.isliroutine.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import tk.blankstudio.isliroutine.R;

/**
 * Created by deadsec on 11/21/17.
 */

public class SettingsActivity extends PreferenceActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar();
        if(getFragmentManager().findFragmentById(android.R.id.content)==null) {
            getFragmentManager().beginTransaction().add(android.R.id.content,new Prefs()).commit();
        }
    }

    public static class Prefs extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
        }
    }
}
