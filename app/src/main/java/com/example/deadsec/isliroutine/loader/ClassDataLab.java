package com.example.deadsec.isliroutine.loader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.deadsec.isliroutine.database.DbHelper;
import com.example.deadsec.isliroutine.database.TableCreator;
import com.example.deadsec.isliroutine.model.ClassModel;
import com.example.deadsec.isliroutine.model.Course;
import com.example.deadsec.isliroutine.model.Day;
import com.example.deadsec.isliroutine.R;
import com.example.deadsec.isliroutine.model.Lession;
import com.example.deadsec.isliroutine.model.Room;
import com.example.deadsec.isliroutine.model.Teacher;
import com.example.deadsec.isliroutine.model.TimeTable;
import com.example.deadsec.isliroutine.model.YearGroup;
import com.framgia.library.calendardayview.data.IEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by deadsec on 10/30/17.
 * Singleton Class that retrives all the
 * data from the database
 */

public class ClassDataLab {
    public static final String TAG = ClassDataLab.class.getSimpleName();
    public static ClassDataLab sDataLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static ClassDataLab get(Context context) {
        if(sDataLab == null) {
            sDataLab=new ClassDataLab(context);
        }
        return sDataLab;
    }

    private ClassDataLab(Context context) {
        mContext= context.getApplicationContext();
        mDatabase=new TableCreator(mContext).getWritableDatabase();
    }

    public void addToYearGroup(YearGroup yearGroup) {
        DbHelper.addToYearGroup(mDatabase,yearGroup);
    }

    public List<YearGroup> getYearGroups() {
        return DbHelper.getYearGroup(mDatabase);
    }

    public void addToTimeTable(TimeTable timeTable) {
        DbHelper.addToTimeTable(mDatabase,timeTable);
    }

    public List<TimeTable> getTimeTables() {return DbHelper.getTimeTable(mDatabase);}

    public void addToTeacher(Teacher teacher) {
        DbHelper.addToTeacher(mDatabase,teacher);
    }

    public List<Teacher> getTeachers() {return DbHelper.getTeacher(mDatabase);}

    public void addToLession(Lession lession) {
        DbHelper.addToLession(mDatabase,lession);
    }

    public List<Lession> getLession() {return DbHelper.getLession(mDatabase);}

    public List<Room> getRooms() {return DbHelper.getRoom(mDatabase);}

    public void addToRoom(Room room) {
        DbHelper.addToRoom(mDatabase,room);
    }

    public List<Course> getCourses() {return DbHelper.getCourse(mDatabase);}

    public void addToCourse(Course course) {
        DbHelper.addToCourse(mDatabase,course);
    }

    public List<IEvent> getEvents(String day) {
        List<IEvent> events = new ArrayList<>();
        return DbHelper.getEvents(mContext,mDatabase,day);
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }
}
