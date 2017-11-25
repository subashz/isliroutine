package tk.blankstudio.isliroutine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import tk.blankstudio.isliroutine.utils.PreferenceUtils;


/**
 * Created by deadsec on 11/13/17.
 */

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        boolean isTimeTableInitialized = PreferenceUtils.get(this).getTimeTableInitialized();

        Intent intent;
        if(isTimeTableInitialized) {
            intent = new Intent(this,RoutineActivity.class);
        }else {
            intent = new Intent(this,GroupSelectActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        }
        startActivity(intent);
        finish();
    }
}
