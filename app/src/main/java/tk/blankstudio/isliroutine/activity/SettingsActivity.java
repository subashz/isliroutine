package tk.blankstudio.isliroutine.activity;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;

/**
 * Created by deadsec on 11/21/17.
 */

public class SettingsActivity extends PreferenceActivity{
    public static final String TAG=SettingsActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Prefs prefs=new Prefs();
        //if(getFragmentManager().findFragmentById(android.R.id.content)==null) {
            //getFragmentManager().beginTransaction().add(android.R.id.content,new Prefs()).commit();
        //}
        getFragmentManager().beginTransaction().replace(android.R.id.content,prefs).commit();
    }

    public static class Prefs extends PreferenceFragment {
        private Preference about;
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);


        }

        @Override
        public void onResume() {
            super.onResume();
           about=findPreference("about");
            if(about!=null) {
                about.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        Log.d(TAG, "onPreferenceClick: ");
                        return true;
                    }
                });
            }else {
                Log.d(TAG, "onCreate: null object");
            }
        }
    }

//    @Override
//    public void onBuildHeaders(List<Header> target) {
//        loadHeadersFromResource(R.xml.pref_header,target);
//    }
//
//    @Override
//    public void onHeaderClick(Header header, int position) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if(isValidFragment(Prefs.class.getName())) {
//                this.startPreferencePanel(Prefs.class.getName(), header.fragmentArguments, header.titleRes, header.title, null, 0);
//            }
//        }else {
//            this.startPreferencePanel(Prefs.class.getName(), header.fragmentArguments, header.titleRes, header.title, null, 0);
//        }
//    }
}
