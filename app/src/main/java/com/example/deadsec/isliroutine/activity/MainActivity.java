package com.example.deadsec.isliroutine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.deadsec.isliroutine.utils.PreferenceUtils;

/**
 * Created by deadsec on 11/13/17.
 */

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      boolean isDataLoaded = PreferenceUtils.get(this).getGroupYearInitialized();
        if(isDataLoaded) {
            Intent i = new Intent(this,RoutineActivity.class);
            startActivity(i);
        }else {
            Intent i = new Intent(this,GroupSelectActivity.class);
            startActivity(i);
        }
        finish();
    }
}
