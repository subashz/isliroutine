package tk.blankstudio.isliroutine.download;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.blankstudio.isliroutine.loader.ClassDataLab;
import tk.blankstudio.isliroutine.model.Course;
import tk.blankstudio.isliroutine.model.Lession;
import tk.blankstudio.isliroutine.model.Room;
import tk.blankstudio.isliroutine.model.Teacher;
import tk.blankstudio.isliroutine.model.TimeTable;
import tk.blankstudio.isliroutine.model.YearGroup;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by deadsec on 11/25/17.
 */

public class Downloader {
    OnDownloadListener mOnDownloadListener;
    private ApiInterface apiInterface;
    public static final String TAG = Downloader.class.getSimpleName();
    private Context context;
    private int dissmissCounter;
    private int groupIndex=-1;
    private int dissmissMax;
    private boolean isServerProblem;

    public Downloader(Context context) {
        this.context = context;
    }

    public Downloader setOnDownloadListener(OnDownloadListener onDownloadListener) {
        mOnDownloadListener = onDownloadListener;
        return this;
    }

    public void loadTimeTable(final int groupId) {
        groupIndex = groupId;
        if (mOnDownloadListener != null && !isNetworkAvailableAndConnected()) {
            mOnDownloadListener.noInternet();
            return;
        }

        if (mOnDownloadListener != null)
            mOnDownloadListener.onStart();

        isServerProblem = false;


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<TimeTable>> call = apiInterface.timeTableList(String.valueOf(groupId));
        call.enqueue(new Callback<List<TimeTable>>() {
            @Override
            public void onResponse(Call<List<TimeTable>> call, Response<List<TimeTable>> response) {
                if (response.isSuccessful()) {
                    List<TimeTable> timeTables = response.body();
                    Log.d(TAG, "onResponse: " + response.toString());
                    dissmissMax = (timeTables.size() * 4) + 1;

                    for (TimeTable timeTable : timeTables) {
                        timeTable.setYearGroupId(groupId);
                        ClassDataLab.get(context).addToTimeTable(timeTable);
                        loadTeacherTable(timeTable.getTeacherId());
                        loadCourseTable(timeTable.getCourseId());
                        loadLessionTable(timeTable.getLessionId());
                        loadRoomTable(timeTable.getRoomId());
                    }
                    dissmissCounter();
                }
            }

            @Override
            public void onFailure(Call<List<TimeTable>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ", t);
                handleFailureThrowable(t);
            }
        });
        Log.d(TAG, "loadTimeTable: response successfully completed");
    }


    private void loadTeacherTable(int groupId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Teacher>> call = apiInterface.teacherList(String.valueOf(groupId));
        call.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, Response<List<Teacher>> response) {
                List<Teacher> teachers = response.body();
                Log.d(TAG, "onResponse: " + response.toString());
                for (Teacher teacher : teachers) {
                    ClassDataLab.get(context).addToTeacher(teacher);
                }
                dissmissCounter();
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ", t);
                handleFailureThrowable(t);
            }
        });
        Log.d("response is: ", "Completed");
    }


    private void loadLessionTable(int groupId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Lession>> call = apiInterface.lessionList(String.valueOf(groupId));
        call.enqueue(new Callback<List<Lession>>() {
            @Override
            public void onResponse(Call<List<Lession>> call, Response<List<Lession>> response) {
                List<Lession> lessions = response.body();
                Log.d(TAG, "onResponse: " + response.toString());
                for (Lession lession : lessions) {
                    ClassDataLab.get(context).addToLession(lession);
                }
                dissmissCounter();
            }

            @Override
            public void onFailure(Call<List<Lession>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ", t);
                handleFailureThrowable(t);
            }
        });
        Log.d(TAG, "loadLessionTable: successfully completed");
    }


    private void loadRoomTable(int groupId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Room>> call = apiInterface.roomList(String.valueOf(groupId));
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                List<Room> rooms = response.body();
                Log.d("response is: ", response.toString());
                for (Room room : rooms) {
                    ClassDataLab.get(context).addToRoom(room);
                }
                dissmissCounter();
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ", t);
                handleFailureThrowable(t);
            }
        });
        Log.d(TAG, "loadRoomTable: completed");
    }

    private void loadCourseTable(int groupId) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Course>> call = apiInterface.courseList(String.valueOf(groupId));
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, Response<List<Course>> response) {
                List<Course> courses = response.body();
                Log.d("response is: ", response.toString());
                for (Course course : courses) {
                    ClassDataLab.get(context).addToCourse(course);
                }
                dissmissCounter();
            }

            @Override
            public void onFailure(Call<List<Course>> call, Throwable t) {
                Log.d(TAG, "onFailure: because of ", t);
                handleFailureThrowable(t);
            }
        });
        Log.d(TAG, "loadCourseTable: completed");
    }

    public void loadGroupYear() {

        if (mOnDownloadListener != null)
            mOnDownloadListener.onStart();

        if (mOnDownloadListener != null && !isNetworkAvailableAndConnected()) {
            mOnDownloadListener.noInternet();
            return;
        }

        isServerProblem = false;

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<YearGroup>> call = apiInterface.groupList();
        Log.d(TAG, "loadDataOfGroup: Enqueing the api request calls ");
        call.enqueue(new Callback<List<YearGroup>>() {
            @Override
            public void onResponse(Call<List<YearGroup>> call, Response<List<YearGroup>> response) {
                if (response.isSuccessful()) {
                    List<YearGroup> mYearGroup = response.body();
                    for (YearGroup yearGroup : mYearGroup) {
                        ClassDataLab.get(context).addToYearGroup(yearGroup);
                    }
                    dissmissMax = 1;
                    dissmissCounter();
                    PreferenceUtils.get(context).setGroupYearInitialized(true);
                    Log.d("Loaded", "Loaded data");
                }
            }

            @Override
            public void onFailure(Call<List<YearGroup>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                handleFailureThrowable(t);
            }
        });
    }


    private void dissmissCounter() {
        dissmissCounter++;
        if (dissmissCounter == dissmissMax) {
            if (mOnDownloadListener != null)
                mOnDownloadListener.onSuccessfull();

            //make sure that the table preference is initialized after all data has been loaded
            if(groupIndex!=-1) {
                PreferenceUtils.get(context).setTimeTableInitialized(true);
            }
        }
    }

    private void handleFailureThrowable(Throwable t) {
        if (!isServerProblem) {
            isServerProblem = true;
            if (mOnDownloadListener != null) {
                String title;
                String message;
                if (t instanceof SocketTimeoutException) {
                    title = "Server Timeout";
                    message = "Mr. Server seems busy. Try again after some time";
                } else {
                    title = "Server Error";
                    message = "Cannot contact Mr. Server. Try later.";
                }
                mOnDownloadListener.onFailure(title, message);
            }
        }
    }

    public void retryLoadTimeTable() {
        isServerProblem = false;
        if (mOnDownloadListener != null)
            mOnDownloadListener.onRetry();
        loadTimeTable(groupIndex);
    }

    public void retryGroupYear() {
        isServerProblem = false;
        if (mOnDownloadListener != null)
            mOnDownloadListener.onRetry();
        loadGroupYear();
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }


}

