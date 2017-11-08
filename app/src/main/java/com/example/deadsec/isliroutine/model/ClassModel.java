package com.example.deadsec.isliroutine.model;

import com.framgia.library.calendardayview.data.IEvent;

import java.util.Calendar;

/**
 * Created by deadsec on 10/30/17.
 */

public class ClassModel implements IEvent {

    private long mId;
    private Calendar mStartTime;
    private Calendar mEndTime;
    private String mCourseName;
    private String mLocation;
    private String mTeacherName;
    private String mModuleid;
    private int mColor;

    public ClassModel() {

    }

    public ClassModel(long id, Calendar startTime, Calendar endTime, String courseName, String location, String teacherName, String moduleid, int color) {
        mId = id;
        mStartTime = startTime;
        mEndTime = endTime;
        mCourseName = courseName;
        mLocation = location;
        mTeacherName = teacherName;
        mModuleid = moduleid;
        mColor = color;
    }

    public String getCourseName() {
        return mCourseName;
    }

    public void setCourseName(String courseName) {
        mCourseName = courseName;
    }

    public String getTeacherName() {
        return mTeacherName;
    }

    public void setTeacherName(String teacherName) {
        mTeacherName = teacherName;
    }

    public String getModuleid() {
        return mModuleid;
    }

    public void setModuleid(String moduleid) {
        mModuleid = moduleid;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        this.mStartTime = startTime;
    }

    public Calendar getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Calendar endTime) {
        this.mEndTime = endTime;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    @Override
    public String getName() {
        return mCourseName;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }
}
