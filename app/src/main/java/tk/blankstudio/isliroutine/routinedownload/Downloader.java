package tk.blankstudio.isliroutine.routinedownload;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tk.blankstudio.isliroutine.database.DataLab;
import tk.blankstudio.isliroutine.model.RoutineCourse;
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
                        DataLab.get(context).addToTimeTable(timeTable);
                        loadTeacherTable(timeTable.getTeacherId());
                        loadCourseTable(timeTable.getCourseId());
                        loadLessionTable(timeTable.getLessionId());
                        loadRoomTable(timeTable.getRoomId());
                    }
                    dismissCounter();
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
                    DataLab.get(context).addToTeacher(teacher);
                }
                dismissCounter();
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
                    DataLab.get(context).addToLession(lession);
                }
                dismissCounter();
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
                    DataLab.get(context).addToRoom(room);
                }
                dismissCounter();
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
        Call<List<RoutineCourse>> call = apiInterface.courseList(String.valueOf(groupId));
        call.enqueue(new Callback<List<RoutineCourse>>() {
            @Override
            public void onResponse(Call<List<RoutineCourse>> call, Response<List<RoutineCourse>> response) {
                List<RoutineCourse> cours = response.body();
                Log.d("response is: ", response.toString());
                for (RoutineCourse course : cours) {
                    DataLab.get(context).addToCourse(course);
                }
                dismissCounter();
            }

            @Override
            public void onFailure(Call<List<RoutineCourse>> call, Throwable t) {
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
                        DataLab.get(context).addToYearGroup(yearGroup);
                    }
                    dissmissMax = 1;
                    dismissCounter();
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


    private void dismissCounter() {
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

                mOnDownloadListener.onFailure(t);
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

