package com.example.deadsec.isliroutine.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deadsec on 11/8/17.
 */

public class YearGroup {
    @SerializedName("id")
    private int mId;
    @SerializedName("year")
    private int myear;
    @SerializedName("start_day")
    private int mStartDay ;
    @SerializedName("end_day")
    private int mEndDay ;
    @SerializedName("start_year")
    private int mStartYear ;
    @SerializedName("end_year")
    private int mEndYear ;
    @SerializedName("start_month")
    private int mStartMonth ;
    @SerializedName("end_month")
    private int mEndMonth ;
    @SerializedName("group")
    private String mGroup;
    @SerializedName("updated_at")
    private String mUpdatedAt;
    @SerializedName("created_at")
    private String mCreatedAt;

    public int getId() {
        return mId;
    }

    public int getMyear() {
        return myear;
    }

    public int getStartDay() {
        return mStartDay;
    }

    public int getEndDay() {
        return mEndDay;
    }

    public int getStartYear() {
        return mStartYear;
    }

    public int getEndYear() {
        return mEndYear;
    }

    public int getStartMonth() {
        return mStartMonth;
    }

    public int getEndMonth() {
        return mEndMonth;
    }

    public String getGroup() {
        return mGroup;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public YearGroup(int id, int myear, int startDay, int endDay, int startYear, int endYear, int startMonth, int endMonth, String group, String updatedAt, String createdAt) {
        mId = id;
        this.myear = myear;
        mStartDay = startDay;
        mEndDay = endDay;
        mStartYear = startYear;
        mEndYear = endYear;
        mStartMonth = startMonth;
        mEndMonth = endMonth;
        mGroup = group;
        mUpdatedAt = updatedAt;
        mCreatedAt = createdAt;
    }
}
