package tk.blankstudio.isliroutine.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.loader.ClassDataLab;
import tk.blankstudio.isliroutine.model.YearGroup;
import tk.blankstudio.isliroutine.utils.ApiClient;
import tk.blankstudio.isliroutine.utils.ApiInterface;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by deadsec on 11/8/17.
 */

public class GroupSelectActivity extends AppCompatActivity {
    private ApiInterface mApiInterface;
    private List<YearGroup> mYearGroup;
    private Spinner mSpinner;
    private List<String> items;
    private Button showRoutineBtn;
    private int groupIndex;
    public static final String TAG=GroupSelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_select);
        if(isNetworkAvailableAndConnected()) {
            loadDataOfGroup();
        }else {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.enable_wifi_text))
                    .setTitle(getString(R.string.no_internet_text))
                    .setNeutralButton(getString(R.string.go_to_setting_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                           startActivity(i);
                        }
                    }).create();
            dialog.show();
        }

    }

    public void loadDataOfGroup() {
        mApiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        items = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        mSpinner = (Spinner) findViewById(R.id.group_select_spn);
        showRoutineBtn = (Button) findViewById(R.id.show_routine_btn);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GroupSelectActivity.this, mYearGroup.get(position).getGroup(), Toast.LENGTH_SHORT).show();
                groupIndex = mYearGroup.get(position).getId();
                PreferenceUtils.get(GroupSelectActivity.this).setGroupYear(groupIndex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        showRoutineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupSelectActivity.this, RoutineActivity.class);
                intent.putExtra("GROUPINDEX", groupIndex);
                startActivity(intent);
            }
        });


        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(GroupSelectActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("This usualbly takes less than a second ");
        progressDoalog.setTitle("Downloading available groups");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();

        Call<List<YearGroup>> call = mApiInterface.groupList();
        Log.d(TAG, "loadDataOfGroup: Enqueing the api request calls ");
        call.enqueue(new Callback<List<YearGroup>>() {
            @Override
            public void onResponse(Call<List<YearGroup>> call, Response<List<YearGroup>> response) {
                if (response.isSuccessful()) {
                    mYearGroup = response.body();
                    for (YearGroup yearGroup : mYearGroup) {
                        items.add(yearGroup.getGroup());
                        ClassDataLab.get(GroupSelectActivity.this).addToYearGroup(yearGroup);
                    }
                   PreferenceUtils.get(GroupSelectActivity.this).setGroupYearInitialized(true);
                    Log.d("Loaded","Loaded data");
                    progressDoalog.dismiss();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<YearGroup>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                finish();
            }
        });
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo()!=null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }
}
