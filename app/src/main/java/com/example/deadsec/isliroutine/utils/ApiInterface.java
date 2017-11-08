package com.example.deadsec.isliroutine.utils;

import android.text.format.Time;

import com.example.deadsec.isliroutine.model.Lession;
import com.example.deadsec.isliroutine.model.TimeTable;
import com.example.deadsec.isliroutine.model.YearGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by deadsec on 11/8/17.
 */

public interface ApiInterface {

    @GET("lession/{id}")
    Call<List<Lession>> lessionList(@Path("id") String id);

    @GET("yeargroup/all")
    Call<List<YearGroup>> groupList();

    @GET("timetable/{id}")
    Call<List<TimeTable>> timeTableList(@Path("id") String id);

}
