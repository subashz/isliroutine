package tk.blankstudio.isliroutine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.database.DataLab;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;
import tk.blankstudio.isliroutine.widget.RoutineWidgetProvider;

/**
 * Created by deadsec on 11/21/17.
 */

public class SettingsActivity extends PreferenceActivity {
    public static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Prefs prefs = new Prefs();
        //if(getFragmentManager().findFragmentById(android.R.id.content)==null) {
        //getFragmentManager().beginTransaction().add(android.R.id.content,new Prefs()).commit();
        //}
        getFragmentManager().beginTransaction().replace(android.R.id.content, prefs).commit();
        getFragmentManager().executePendingTransactions();
    }

    public static class Prefs extends PreferenceFragment implements Preference.OnPreferenceChangeListener{
        private ListPreference defaultListGroup;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_setting);

            defaultListGroup = (ListPreference) findPreference("default_group_year");

            // get download group name with its relevant id
            // and append to list
            final List<String> groupsName = new ArrayList<>();
            final List<String> groupsId = new ArrayList<>();

            try {
                JSONArray groups = new JSONArray(PreferenceUtils.get(getActivity().getApplicationContext()).getDownloadedGroupYear());
                for (int i = 0; i < groups.length(); i++) {
                    groupsName.add(DataLab.get(getActivity().getApplicationContext()).getGroupName(String.valueOf(groups.getInt(i))));
                    groupsId.add(String.valueOf(groups.getInt(i)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            defaultListGroup.setEntries(groupsName.toArray(new CharSequence[groupsName.size()]));
            defaultListGroup.setEntryValues(groupsId.toArray(new CharSequence[groupsId.size()]));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if(preference.getKey().equals("default_group_year")) {
                Intent intent = new Intent(getActivity().getApplicationContext(), RoutineWidgetProvider.class);
                intent.setAction("update");
                getActivity().getApplicationContext().sendBroadcast(intent);
            }
            return false;
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
