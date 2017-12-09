package tk.blankstudio.isliroutine.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

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

public class SettingsActivity extends AppCompatPreferenceActivity {
    public static final String TAG = SettingsActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    public static class TimeTableSettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        private ListPreference defaultListGroup;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);

            addPreferencesFromResource(R.xml.pref_timetable_setting);

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
            if (preference.getKey().equals("default_group_year")) {
                Intent intent = new Intent(getActivity().getApplicationContext(), RoutineWidgetProvider.class);
                intent.setAction("update");
                getActivity().getApplicationContext().sendBroadcast(intent);
            }
            return false;
        }

        // go to previous menu
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    public static class NotificationSettingFragment extends PreferenceFragment {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            addPreferencesFromResource(R.xml.pref_notification_setting);
        }

        // go to previous menu
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                getActivity().onBackPressed();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_header, target);
    }

    @Override
    protected boolean isValidFragment(String fragmentName) {

        if (TimeTableSettingFragment.class.getName().equals(fragmentName)) {
            getSupportActionBar().setTitle("TimeTableSetting");

            return true;
        } else if (NotificationSettingFragment.class.getName().equals(fragmentName)) {
            getSupportActionBar().setTitle("NotificationSetting");
            return true;
        }
        return false;
    }


    private void setupActionBar() {
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
