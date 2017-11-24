package tk.blankstudio.isliroutine.activity;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import tk.blankstudio.isliroutine.loader.ClassDataLab;
import tk.blankstudio.isliroutine.model.Course;
import tk.blankstudio.isliroutine.model.Lession;
import tk.blankstudio.isliroutine.model.Room;
import tk.blankstudio.isliroutine.model.Teacher;
import tk.blankstudio.isliroutine.model.TimeTable;
import tk.blankstudio.isliroutine.utils.ApiClient;
import tk.blankstudio.isliroutine.utils.ApiInterface;
import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.fragment.DailyClassFragment;
import tk.blankstudio.isliroutine.model.Day;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;
import tk.blankstudio.isliroutine.notification.NotificationService;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

public class RoutineActivity extends AppCompatActivity {

    public static final String TAG = RoutineActivity.class.getSimpleName();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ApiInterface apiInterface;
    private ProgressDialog progressDoalog;
    private int dissmissCounter;
    private int dissmissMax;
    private int groupIndex;
    private boolean isServerProblem;
    private boolean doubleBackPressStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_routine);

        progressDoalog = new ProgressDialog(RoutineActivity.this);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setMax(100);
        groupIndex = getIntent().getIntExtra("GROUPINDEX", 0);
        if (groupIndex == 0) {
            groupIndex = PreferenceUtils.get(this).getGroupYear();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!PreferenceUtils.get(this).getTimeTableInitialized()) {
            loadTimeTable(groupIndex);
        } else {
            init();
        }


    }

    public void init() {

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        CoordinatorLayout mainView = (CoordinatorLayout) findViewById(R.id.main_content);
        viewPager.setAdapter(mSectionsPagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            toolbar.getOverflowIcon().setTint(Color.BLACK);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        String groupName =ClassDataLab.get(this).getGroupName(String.valueOf(PreferenceUtils.get(this).getGroupYear())).toLowerCase();
        String coloredText = getString(R.string.title_activity_routine,groupName);
        toolbarTitle.setText(Html.fromHtml(coloredText));

        NotificationService.setDailyRepeatingNotification(this,true);

        viewPager.setOffscreenPageLimit(7);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        viewPager.setCurrentItem(day, true);


        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Snackbar snack=Snackbar.make(mainView,getString(R.string.enable_disturb_mode_text),Snackbar.LENGTH_INDEFINITE);
            snack.setActionTextColor(ContextCompat.getColor(this,R.color.colorAccent));
            snack.setAction(getString(R.string.go_to_setting_text), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( Settings .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent);
                }
            }).show();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        mSectionsPagerAdapter.addFragment(DailyClassFragment.newInstance(Day.SUNDAY,1), "Sun");
        mSectionsPagerAdapter.addFragment(DailyClassFragment.newInstance(Day.MONDAY,2), "Mon");
        mSectionsPagerAdapter.addFragment(DailyClassFragment.newInstance(Day.TUESDAY,3), "Tue");
        mSectionsPagerAdapter.addFragment(DailyClassFragment.newInstance(Day.WEDNESDAY,4), "Wed");
        mSectionsPagerAdapter.addFragment(DailyClassFragment.newInstance(Day.THURSDAY,5), "Thu");
        mSectionsPagerAdapter.addFragment(DailyClassFragment.newInstance(Day.FRIDAY,6), "Fri");
        viewPager.setAdapter(mSectionsPagerAdapter);
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
            startActivity(new Intent(this,SettingsActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
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

    public void loadTimeTable(int groupId) {

        isServerProblem=false;
        progressDoalog.setMessage("This usually takes less than a second ");
        progressDoalog.setTitle("Downloading Classes");
        progressDoalog.show();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<TimeTable>> call = apiInterface.timeTableList(String.valueOf(groupId));
        call.enqueue(new Callback<List<TimeTable>>() {
            @Override
            public void onResponse(Call<List<TimeTable>> call, Response<List<TimeTable>> response) {
                if(response.isSuccessful()) {
                    List<TimeTable> timeTables = response.body();
                    Log.d(TAG, "onResponse: " + response.toString());
                    dissmissMax = (timeTables.size() * 4) + 1;

                    for (TimeTable timeTable : timeTables) {
                        ClassDataLab.get(RoutineActivity.this).addToTimeTable(timeTable);
                        loadTeacherTable(timeTable.getTeacherId());
                        loadCourseTable(timeTable.getCourseId());
                        loadLessionTable(timeTable.getLessionId());
                        loadRoomTable(timeTable.getRoomId());
                    }
                    dissmissCounter();
                    PreferenceUtils.get(RoutineActivity.this).setTimeTableInitialized(true);
                }
            }

            @Override
            public void onFailure(Call<List<TimeTable>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ",t);
                handleFailureThrowable(t);
            }
        });
        Log.d(TAG, "loadTimeTable: response successfully completed");
    }


    public void loadTeacherTable(int groupId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Teacher>> call = apiInterface.teacherList(String.valueOf(groupId));
        call.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                List<Teacher> teachers = response.body();
                Log.d(TAG, "onResponse: "+response.toString());
                for (Teacher teacher : teachers) {
                    ClassDataLab.get(RoutineActivity.this).addToTeacher(teacher);
                }
                dissmissCounter();
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ",t);
                handleFailureThrowable(t);
            }
        });
        Log.d("response is: ", "Completed");
    }


    public void loadLessionTable(int groupId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Lession>> call = apiInterface.lessionList(String.valueOf(groupId));
        call.enqueue(new Callback<List<Lession>>() {
            @Override
            public void onResponse(Call<List<Lession>> call, Response<List<Lession>> response) {
                List<Lession> lessions = response.body();
                Log.d(TAG, "onResponse: "+response.toString());
                for (Lession lession : lessions) {
                    ClassDataLab.get(RoutineActivity.this).addToLession(lession);
                }
                dissmissCounter();
            }

            @Override
            public void onFailure(Call<List<Lession>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ",t);
                handleFailureThrowable(t);
            }
        });
        Log.d(TAG, "loadLessionTable: successfully completed");
    }


    public void loadRoomTable(int groupId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Room>> call = apiInterface.roomList(String.valueOf(groupId));
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                List<Room> rooms = response.body();
                Log.d("response is: ", response.toString());
                for (Room room : rooms) {
                    ClassDataLab.get(RoutineActivity.this).addToRoom(room);
                }
                dissmissCounter();
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ",t);
                handleFailureThrowable(t);
            }
        });
        Log.d(TAG, "loadRoomTable: completed");
    }

    public void loadCourseTable(int groupId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Course>> call = apiInterface.courseList(String.valueOf(groupId));
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                List<Course> courses = response.body();
                Log.d("response is: ", response.toString());
                for (Course course : courses) {
                    ClassDataLab.get(RoutineActivity.this).addToCourse(course);
                }
                dissmissCounter();
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ",t);
                handleFailureThrowable(t);
            }
        });
        Log.d(TAG, "loadCourseTable: completed");
    }

    public void dissmissCounter() {
        dissmissCounter++;
        if(dissmissCounter==dissmissMax) {
            progressDoalog.dismiss();
            init();
        }
    }

    public void showRetryDialog(String title,String message) {
        AlertDialog alertDialog= new AlertDialog.Builder(this)
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
                    loadTimeTable(groupIndex);
                    }
                }).create();
        alertDialog.show();
    }

    public void handleFailureThrowable(Throwable t) {
        if(!isServerProblem) {
            progressDoalog.dismiss();
            isServerProblem=true;
            if (t instanceof SocketTimeoutException) {
                showRetryDialog("Server Timeout", "Mr. Server seems busy. Try again after some time");
            }else {
                showRetryDialog("Server Error", "Cannot contact Mr. Server. Try later.");
            }
        }
    }

    @Override
    public void onBackPressed() {
        if(doubleBackPressStatus) {
            finish();
            super.onBackPressed();
        }
        doubleBackPressStatus=true;
        Toast.makeText(this,getString(R.string.back_key_press_text), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackPressStatus=false;
            }
        },2000);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
