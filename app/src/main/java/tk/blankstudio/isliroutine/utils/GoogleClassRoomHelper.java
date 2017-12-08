package tk.blankstudio.isliroutine.utils;

/**
 * Created by deadsec on 11/27/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.classroom.ClassroomScopes;
import com.google.api.services.classroom.model.Course;
import com.google.api.services.classroom.model.DriveFolder;
import com.google.api.services.classroom.model.ListCoursesResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tk.blankstudio.isliroutine.database.DataLab;
import tk.blankstudio.isliroutine.model.ClassRoomCourse;
import tk.blankstudio.isliroutine.routinedownload.OnDownloadListener;

public class GoogleClassRoomHelper {

    OnDownloadListener mOnDownloadListener;
    Context context;
    GoogleAccountCredential mCredential;
    private static final String[] SCOPES = {ClassroomScopes.CLASSROOM_COURSES_READONLY};

    public GoogleClassRoomHelper(Context context) {
        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                context, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        this.context=context;

    }

    public void downloadCourse() {
        String account = PreferenceUtils.get(context).getUserAccount();
        if (account != null) {
            mCredential.setSelectedAccountName(account);
        } else {
            mOnDownloadListener.onRetry();
        }
        new ClassRoomCourseDownloader(mCredential, context, mOnDownloadListener).execute();
    }

    public GoogleClassRoomHelper setOnDownloadListener(OnDownloadListener onDownloadListener) {
        this.mOnDownloadListener = onDownloadListener;
        return this;
    }


    private class ClassRoomCourseDownloader extends AsyncTask<Void, Void, List<Course>> {
        private com.google.api.services.classroom.Classroom mService = null;
        private Context context;
        public final String TAG = ClassRoomCourseDownloader.class.getSimpleName();
        OnDownloadListener mOnDownloadListener;

        ClassRoomCourseDownloader(GoogleAccountCredential credential, Context context, OnDownloadListener onDownloadListener) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mOnDownloadListener = onDownloadListener;
            this.context = context;
            mService = new com.google.api.services.classroom.Classroom.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Classroom API Android Quickstart")
                    .build();
        }

        @Override
        protected List<Course> doInBackground(Void... params) {

            ListCoursesResponse response = null;
            try {
                response = mService.courses().list()
                        .setPageSize(10)
                        .execute();
                return response.getCourses();
            } catch (IOException e) {
                e.printStackTrace();
                mOnDownloadListener.onFailure(e);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mOnDownloadListener.onStart();
        }

        @Override
        protected void onPostExecute(List<Course> courses) {
            if (courses != null) {
                for (Course course : courses) {
                    DriveFolder driveFolder = course.getTeacherFolder();
                    String driveLink = "";
                    if (driveFolder != null) {
                        driveLink = driveFolder.getAlternateLink();
                    }
                    ClassRoomCourse classRoomCourse = new ClassRoomCourse(course.getId(), course.getName(), course.getSection(), course.getDescriptionHeading(), course.getAlternateLink(), course.getEnrollmentCode(), driveLink, course.getDescription(), course.getCourseState());
                    // names.add(course.getName());
                    DataLab.get(context).addToClassRoomCourse(classRoomCourse);
                    Log.d(TAG, "getDataFromApi: saving to database: " + classRoomCourse.getName());
                }
            }
            mOnDownloadListener.onSuccessfull();
        }
    }

}
