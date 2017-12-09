package tk.blankstudio.isliroutine.activity;

/**
 * Created by deadsec on 11/27/17.
 */

import android.Manifest;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.classroom.ClassroomScopes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.database.DataLab;
import tk.blankstudio.isliroutine.model.ClassRoomCourse;
import tk.blankstudio.isliroutine.routinedownload.OnDownloadListener;
import tk.blankstudio.isliroutine.utils.GoogleClassRoomHelper;
import tk.blankstudio.isliroutine.utils.MiscUtils;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;

public class ClassRoomActivity extends AppCompatActivity
        implements EasyPermissions.PermissionCallbacks {
    public static final String TAG = ClassRoomActivity.class.getSimpleName();
    GoogleAccountCredential mCredential;
    private TextView mOutputText;
    private Button classRoomSignInButton;
    ProgressDialog mProgress;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;

    private static final String[] SCOPES = {ClassroomScopes.CLASSROOM_COURSES_READONLY};

    /**
     * Create the main activity.
     *
     * @param savedInstanceState previously saved instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_room);
        classRoomSignInButton = findViewById(R.id.btn_login_google);
        classRoomSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classRoomSignInButton.setEnabled(false);
                mOutputText.setText("");
                getResultsFromApi();
                classRoomSignInButton.setEnabled(true);
            }
        });

        mOutputText = findViewById(R.id.tv_class_room_result);
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Getting classroom courses ...");

        // Initialize credentials and service object.
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }


    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private void getResultsFromApi() {
        if (!MiscUtils.isGooglePlayServicesAvailable(this)) {
            MiscUtils.acquireGooglePlayServices(this);
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (!MiscUtils.isDeviceOnline(this)) {
            mOutputText.setText("No network connection available.");
        } else {
            //new MakeRequestTask(mCredential).execute();
            GoogleClassRoomHelper helper = new GoogleClassRoomHelper(this);
            helper.setOnDownloadListener(new OnDownloadListener() {
                @Override
                public void onStart() {
                    mOutputText.setText("");
                    mProgress.show();
                }

                @Override
                public void onRetry() {

                }

                @Override
                public void onSuccessfull() {
                    mProgress.hide();
                    updateUI();
                }

                @Override
                public void onFailure(Throwable mLastError) {
                    if (mLastError != null) {
                        if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                            MiscUtils.showGooglePlayServicesAvailabilityErrorDialog(ClassRoomActivity.this,
                                    ((GooglePlayServicesAvailabilityIOException) mLastError)
                                            .getConnectionStatusCode());
                        } else if (mLastError instanceof UserRecoverableAuthIOException) {
                            startActivityForResult(
                                    ((UserRecoverableAuthIOException) mLastError).getIntent(),
                                    ClassRoomActivity.REQUEST_AUTHORIZATION);
                        } else {
                            mOutputText.setText("The following error occurred:\n"
                                    + mLastError.getMessage());
                        }
                    } else {
                        mOutputText.setText("Login process has been cancelled.");
                    }
                }

                @Override
                public void noInternet() {

                }
            });
            helper.downloadCourse();
        }
    }

    /**
     * Attempts to set the account used with the API credentials. If an account
     * name was previously saved it will use that one; otherwise an account
     * picker dialog will be shown to the user. Note that the setting the
     * account to use with the credentials object requires the app to have the
     * GET_ACCOUNTS permission, which is requested here if it is not already
     * present. The AfterPermissionGranted annotation indicates that this
     * function will be rerun automatically whenever the GET_ACCOUNTS permission
     * is granted.
     */
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
                this, Manifest.permission.GET_ACCOUNTS)) {
            //String accountName = getPreferences(Context.MODE_PRIVATE)
            //.getString(PREF_ACCOUNT_NAME, null);
            String accountName = PreferenceUtils.get(this).getUserAccount();
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    Manifest.permission.GET_ACCOUNTS);
        }
    }

    /**
     * Called when an activity launched here (specifically, AccountPicker
     * and authorization) exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode code indicating which activity result is incoming.
     * @param resultCode  code indicating the result of the incoming
     *                    activity result.
     * @param data        Intent (containing result data) returned by incoming
     *                    activity result.
     */
    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                    mOutputText.setText(
                            "This app requires Google Play Services. Please install " +
                                    "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        PreferenceUtils.get(this).setUserAccount(accountName);
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }

    /**
     * Respond to requests for permissions at runtime for API 23 and above.
     *
     * @param requestCode  The request code passed in
     *                     requestPermissions(android.app.Activity, String, int, String[])
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }

    /**
     * Callback for when a permission is granted using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Callback for when a permission is denied using the EasyPermissions
     * library.
     *
     * @param requestCode The request code associated with the requested
     *                    permission
     * @param list        The requested permission list. Never null.
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // Do nothing.
    }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */


    public void updateUI() {
        classRoomSignInButton.setVisibility(View.INVISIBLE);
        ListView listView = (ListView)findViewById(R.id.lv_class_room_course_list);
        List<ClassRoomCourse> classRoomCourses = DataLab.get(this).getClassRoomCourse();
        List<String> courses = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,courses);
        for (ClassRoomCourse k : classRoomCourses) {
            courses.add(k.getName());
            Log.d(TAG, "updateUI: from database: " + k.getName());
        }
        listView.setAdapter(arrayAdapter);
    }
}
