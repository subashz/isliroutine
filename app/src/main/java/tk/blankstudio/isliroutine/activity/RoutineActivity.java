package tk.blankstudio.isliroutine.activity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import tk.blankstudio.isliroutine.loader.ClassDataLab;
import tk.blankstudio.isliroutine.download.ApiClient;
import tk.blankstudio.isliroutine.download.ApiInterface;
import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.fragment.DailyClassFragment;
import tk.blankstudio.isliroutine.model.Day;
import tk.blankstudio.isliroutine.download.Downloader;
import tk.blankstudio.isliroutine.download.OnDownloadListener;
import tk.blankstudio.isliroutine.utils.AlarmUtils;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;
import tk.blankstudio.isliroutine.notification.NotificationService;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.crashlytics.android.Crashlytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.fabric.sdk.android.Fabric;
import tk.blankstudio.isliroutine.utils.YearGroupUtils;

public class RoutineActivity extends AppCompatActivity {

    public static final String TAG = RoutineActivity.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ProgressDialog progressDoalog;
    private AlertDialog mAlertDialog;
    private int groupIndex;
    private boolean doubleBackPressStatus;
    private Spinner groupSelectSpinner;
    private boolean downloadStatus;
    private int firstTimeCheckOnItemSelected;
    private int previousSelectedIndex;
    DailyClassFragment mDailyClassFragment[]=new DailyClassFragment[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_routine);

        progressDoalog = new ProgressDialog(RoutineActivity.this);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setMax(100);

        groupIndex = getIntent().getIntExtra("GROUPINDEX", -1);
        if (groupIndex == -1) {
            groupIndex = PreferenceUtils.get(this).getDefaultGroupYear();
            if(groupIndex==-1) {
                startActivity(new Intent(this,GroupSelectActivity.class));
                finish();
            }
        }else {
            // this is true, when the group select activity passes the group index
            // this happens only for the new routine to download
           downloadStatus=true;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!PreferenceUtils.get(this).getTimeTableInitialized() || downloadStatus) {
            downloadTimeTable(groupIndex);
        } else {
            init();
        }


    }

    private void downloadTimeTable(final int groupIndex) {
        final Downloader downloader = new Downloader(this);
        downloader.setOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onStart() {
                progressDoalog.setMessage("This usually takes less than a second ");
                progressDoalog.setTitle("Downloading Classes");
                progressDoalog.show();
            }

            @Override
            public void onRetry() {
                mAlertDialog.dismiss();
            }

            @Override
            public void onSuccessfull() {
                progressDoalog.dismiss();
                  AlertDialog alertDialog = new AlertDialog.Builder(RoutineActivity.this)
                        .setTitle("Do you want to make "+ClassDataLab.get(RoutineActivity.this).getGroupName(String.valueOf(groupIndex))+" your default group")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // this cancels all the previous notification alarms and
                                //new schedule alarms are added when init() gets called
                                AlarmUtils.cancelAllAlarms(RoutineActivity.this);
                                PreferenceUtils.get(RoutineActivity.this).setDefaultGroupYear(groupIndex);
                                YearGroupUtils.saveGroupId(RoutineActivity.this,groupIndex);
                                init();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                YearGroupUtils.saveGroupId(RoutineActivity.this,groupIndex);
                                init();
                            }
                        }).setCancelable(false).create();
                  if(YearGroupUtils.getYearGroupIds(RoutineActivity.this).size()!=0) {
                      alertDialog.show();
                  }else {
                      PreferenceUtils.get(RoutineActivity.this).setDefaultGroupYear(groupIndex);
                      YearGroupUtils.saveGroupId(RoutineActivity.this,groupIndex);
                      init();
                  }

            }


            @Override
            public void onFailure(String title, String message) {

                mAlertDialog = new AlertDialog.Builder(RoutineActivity.this)
                        .setTitle(title)
                        .setMessage(message)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ApiClient.getOkHttpClient().dispatcher().cancelAll();
                               downloader.retryLoadTimeTable();
                            }
                        }).create();
                progressDoalog.dismiss();
                mAlertDialog.show();

            }

            @Override
            public void noInternet() {

            }
        }).loadTimeTable(groupIndex);

    }


    public void init() {

        groupSelectSpinner=(Spinner)findViewById(R.id.toolbar_group_select);

        final List<String> groupsName=new ArrayList<>();
        final List<Integer> groupsId=new ArrayList<>();

        try {
            JSONArray groups= new JSONArray(PreferenceUtils.get(this).getDownloadedGroupYear());
            for(int i=0;i<groups.length();i++) {
                groupsName.add(ClassDataLab.get(this).getGroupName(String.valueOf(groups.getInt(i))));
                groupsId.add(groups.getInt(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,R.layout.item_spinner_year_group,groupsName);
        groupSelectSpinner.setAdapter(adapter);
        previousSelectedIndex=groupsId.indexOf(groupIndex);
        groupSelectSpinner.setSelection(previousSelectedIndex);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        CoordinatorLayout mainView = (CoordinatorLayout) findViewById(R.id.main_content);
        viewPager.setAdapter(mSectionsPagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.getOverflowIcon().setTint(Color.BLACK);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        String groupName = ClassDataLab.get(this).getGroupName(String.valueOf(groupIndex));
        String coloredText = getString(R.string.title_activity_routine);
        toolbarTitle.setText(Html.fromHtml(coloredText));

        NotificationService.setDailyRepeatingNotification(this, true);

        viewPager.setOffscreenPageLimit(6);
        setupViewPager(mSectionsPagerAdapter,viewPager,groupIndex);
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        viewPager.setCurrentItem(day, true);


        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Snackbar snack = Snackbar.make(mainView, getString(R.string.enable_disturb_mode_text), Snackbar.LENGTH_INDEFINITE);
            snack.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            snack.setAction(getString(R.string.go_to_setting_text), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }
            }).show();
        }

        groupSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // re setup the view pager
                if(++firstTimeCheckOnItemSelected>1) {
                    // fucked up logic
                    // kam chalu matra ho yo code.. // it just reloads all viewpager, tablayout and feels like a refresh layout
                    // bholi... :)
                    if(previousSelectedIndex!=position) {
                        removeAllFragment();
                        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                        setupViewPager(sectionsPagerAdapter, viewPager, groupsId.get(position));
                        viewPager.setAdapter(sectionsPagerAdapter);
                        viewPager.setOffscreenPageLimit(6);
                        viewPager.setCurrentItem(day, true);
                        tabLayout.setupWithViewPager(viewPager);
                        previousSelectedIndex=position;
                    }

                    Log.d(TAG, "onItemSelected: item is: " + groupsId.get(position) + " group is: " + groupsName.get(position));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupViewPager(SectionsPagerAdapter sectionsPagerAdapter,ViewPager viewPager,int groupIndex) {
        Log.d(TAG, "setupViewPager: Setting view pager");
        sectionsPagerAdapter.notifyDataSetChanged();
        for(int i=0;i<6;i++) {
            DailyClassFragment dailyClassFragment=DailyClassFragment.newInstance(Day.getDay(i),i,groupIndex);
            sectionsPagerAdapter.addFragment(dailyClassFragment,Day.getDay(i));
            mDailyClassFragment[i]=dailyClassFragment;
        }
    }
    private void removeAllFragment() {
        Log.d(TAG, "removeAllFragment: ");
         for(int i=0;i<6;i++) {
            getSupportFragmentManager().beginTransaction().remove(mDailyClassFragment[i]).commit();
            mDailyClassFragment[i]=null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mFragmentList.size();
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    @Override
    public void onBackPressed() {
        if (doubleBackPressStatus) {
            finish();
            super.onBackPressed();
        }
        doubleBackPressStatus = true;
        Toast.makeText(this, getString(R.string.back_key_press_text), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressStatus = false;
            }
        }, 2000);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
