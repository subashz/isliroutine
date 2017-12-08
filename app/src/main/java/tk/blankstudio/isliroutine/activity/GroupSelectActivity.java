package tk.blankstudio.isliroutine.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.routinedownload.Downloader;
import tk.blankstudio.isliroutine.routinedownload.OnDownloadListener;
import tk.blankstudio.isliroutine.database.DataLab;
import tk.blankstudio.isliroutine.model.YearGroup;
import tk.blankstudio.isliroutine.routinedownload.ApiInterface;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import tk.blankstudio.isliroutine.utils.YearGroupUtils;

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
        boolean groupDataInitialized = PreferenceUtils.get(this).getGroupYearInitialized();

        if (groupDataInitialized) {
            initData();
        } else {
            loadDataOfGroup();
        }

    }

    public void initData() {
        items = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner_year_group, items);
        final List<YearGroup> mYearGroup = DataLab.get(this).getYearGroups();

        final List<Integer> itemsIds = new ArrayList<>();

        //dont add to list, if the group has been already downloaded
        List<Integer> downloadedGroupsId = YearGroupUtils.getYearGroupIds(this);
        for (YearGroup yearGroup : mYearGroup) {
            if (downloadedGroupsId.contains(yearGroup.getId()))
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
                startActivity(intent);
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
            public void onFailure(Throwable t) {
                String errorTitle;
                String errorMessage;
                if (t instanceof SocketTimeoutException) {
                    errorTitle = "Server Timeout";
                    errorMessage = "Mr. Server seems busy. Try again after some time";
                } else {
                    errorTitle = "Server Error";
                    errorMessage = "Cannot contact Mr. Server. Try later.";
                }

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


}
