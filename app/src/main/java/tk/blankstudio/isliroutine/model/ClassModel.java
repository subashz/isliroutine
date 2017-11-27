package tk.blankstudio.isliroutine.model;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import tk.blankstudio.isliroutine.R;
import com.framgia.library.calendardayview.data.IEvent;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by deadsec on 10/30/17.
 * this model is for the class event mapping in the item_fragments
 * this stores the info to be displayed in the ui
 */

public class ClassModel implements IEvent {

    private long mId;
    private Calendar mStartTime;
    private Calendar mEndTime;
    private String mCourseName;
    private String mLocation;
    private String mTeacherName;
    private String mModuleid;
    private String mType;
    private int mColor;

    public String getType() {
        return mType;
    }

    public ClassModel() {

    }

    public ClassModel(long id, Calendar startTime, Calendar endTime, String courseName, String location, String teacherName, String moduleid, int color,String type) {
        mId = id;
        mStartTime = startTime;
        mEndTime = endTime;
        mCourseName = courseName;
        mLocation = location;
        mTeacherName = teacherName;
        mModuleid = moduleid;
        mColor = color;
        mType=type;
    }

    public static ClassModel getClassModel(Context context, int id, int startHour, int startMinute, int stopHour, int stopMinute, String module, String location, String teacher, String mId,String type) {
        int eventColor = ContextCompat.getColor(context, R.color.eventColor1);
        //Random rand = new Random();
        //int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        int color = Color.argb(20,62,64,149);
        Calendar timeStart = Calendar.getInstance();
        timeStart.set(Calendar.HOUR_OF_DAY, startHour);
        timeStart.set(Calendar.MINUTE, startMinute);
        Calendar timeEnd = (Calendar) timeStart.clone();
        timeEnd.set(Calendar.HOUR_OF_DAY, stopHour);
        timeEnd.set(Calendar.MINUTE, stopMinute);
        return new ClassModel(id, timeStart, timeEnd, module, location, teacher, mId, color,type);
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
