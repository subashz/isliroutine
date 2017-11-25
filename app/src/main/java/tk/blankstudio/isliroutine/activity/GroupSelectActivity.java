package tk.blankstudio.isliroutine.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import org.json.JSONArray;

import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.download.Downloader;
import tk.blankstudio.isliroutine.download.OnDownloadListener;
import tk.blankstudio.isliroutine.loader.ClassDataLab;
import tk.blankstudio.isliroutine.loader.TimeTableLoader;
import tk.blankstudio.isliroutine.model.YearGroup;
import tk.blankstudio.isliroutine.download.ApiClient;
import tk.blankstudio.isliroutine.download.ApiInterface;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;

import java.net.SocketTimeoutException;
import java.security.acl.Group;
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
    private Spinner mSpinner;
    private List<String> items;
    private Button showRoutineBtn;
    private int groupIndex;
    private ProgressDialog progressDoalog;
    public static final String TAG = GroupSelectActivity.class.getSimpleName();
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_select);
        progressDoalog = new ProgressDialog(GroupSelectActivity.this);
        progressDoalog.setMax(100);
        boolean groupDataInitialized=PreferenceUtils.get(this).getGroupYearInitialized();

        if(groupDataInitialized) {
            initData();
        }else {
            loadDataOfGroup();
        }

    }

    public void initData() {
        items = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        final List<YearGroup> mYearGroup = ClassDataLab.get(this).getYearGroups();

        final List<Integer> itemsIds=new ArrayList<>();

        //dont add to list, if the group has been already downloaded
        List<Integer> downloadedGroupsId=getYearGroupIds(this);
        for (YearGroup yearGroup : mYearGroup) {
            if(downloadedGroupsId.contains(yearGroup.getId()))
                continue;
            items.add(yearGroup.getGroup());
            itemsIds.add(yearGroup.getId());
        }

        mSpinner = (Spinner) findViewById(R.id.group_select_spn);
        showRoutineBtn = (Button) findViewById(R.id.show_routine_btn);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(GroupSelectActivity.this, items.get(position), Toast.LENGTH_SHORT).show();
                groupIndex = itemsIds.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        showRoutineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(GroupSelectActivity.this, RoutineActivity.class);
                intent.putExtra("GROUPINDEX", groupIndex);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                AlertDialog alertDialog = new AlertDialog.Builder(GroupSelectActivity.this)
                        .setTitle("Do you want to make "+ClassDataLab.get(GroupSelectActivity.this).getGroupName(String.valueOf(groupIndex))+" your default group")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PreferenceUtils.get(GroupSelectActivity.this).setDefaultGroupYear(groupIndex);
                                saveGroupId(groupIndex);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                saveGroupId(groupIndex);
                                startActivity(intent);
                            }
                        }).setCancelable(false).create();
                alertDialog.show();
            }
        });
    }


    private void loadDataOfGroup() {
        final Downloader downloader = new Downloader(this);
        downloader.setOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onStart() {
                progressDoalog.setMessage("This usually takes less than a second ");
                progressDoalog.setTitle("Downloading available groups");
                progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDoalog.show();
            }

            @Override
            public void onRetry() {
                alertDialog.dismiss();
            }


            @Override
            public void onSuccessfull() {
                progressDoalog.dismiss();
                initData();
            }

            @Override
            public void onFailure(String errorTitle, String errorMessage) {

                alertDialog = new AlertDialog.Builder(GroupSelectActivity.this)
                        .setTitle(errorTitle)
                        .setMessage(errorMessage)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                downloader.retryGroupYear();
                            }
                        }).create();
                progressDoalog.dismiss();
                alertDialog.show();
            }

            @Override
            public void noInternet() {
                AlertDialog dialog = new AlertDialog.Builder(GroupSelectActivity.this)
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
        }).loadGroupYear();
    }

    private void saveGroupId(int id) {
        List<Integer> yearGroupIds = getYearGroupIds(this);

        if (yearGroupIds.contains(id)) {
            return;
        }

        yearGroupIds.add(id);

        saveIdsInPreferences(yearGroupIds);
    }

    private void removeYearGroupId(int id) {
        List<Integer> yearGroupIds = getYearGroupIds(this);

        for (int i = 0; i < yearGroupIds.size(); i++) {
            if (yearGroupIds.get(i) == id)
                yearGroupIds.remove(i);
        }

        saveIdsInPreferences(yearGroupIds);
    }

    public static List<Integer> getYearGroupIds(Context context){
        List<Integer> ids = new ArrayList<>();
        try {
            JSONArray jsonArray2 = new JSONArray(PreferenceUtils.get(context).getDownloadedGroupYear());

            for (int i = 0; i < jsonArray2.length(); i++) {
                ids.add(jsonArray2.getInt(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ids;
    }


    private void saveIdsInPreferences(List<Integer> listIds) {
        JSONArray jsonArray = new JSONArray();
        for (Integer yearGroupId : listIds) {
            jsonArray.put(yearGroupId);
        }
        PreferenceUtils.get(this).setDownloadedGroupYear(jsonArray.toString());
    }


}
