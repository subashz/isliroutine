package tk.blankstudio.isliroutine.activity;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import java.util.List;

import tk.blankstudio.isliroutine.R;

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
    }

    public static class Prefs extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
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
