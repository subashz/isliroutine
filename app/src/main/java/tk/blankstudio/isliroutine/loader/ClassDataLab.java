package tk.blankstudio.isliroutine.loader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import tk.blankstudio.isliroutine.database.DbHelper;
import tk.blankstudio.isliroutine.database.TableCreator;
import tk.blankstudio.isliroutine.model.ClassModel;
import tk.blankstudio.isliroutine.model.Course;
import tk.blankstudio.isliroutine.model.Day;
import tk.blankstudio.isliroutine.R;
import tk.blankstudio.isliroutine.model.Lession;
import tk.blankstudio.isliroutine.model.Room;
import tk.blankstudio.isliroutine.model.Teacher;
import tk.blankstudio.isliroutine.model.TimeTable;
import tk.blankstudio.isliroutine.model.YearGroup;
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
        return DbHelper.getYearGroup(mDatabase,null);
    }

    public void addToTimeTable(TimeTable timeTable) {
        DbHelper.addToTimeTable(mDatabase,timeTable);
    }

    public List<TimeTable> getTimeTables(int groupIndex) {return DbHelper.getTimeTable(mDatabase,groupIndex);}

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

    public String getGroupName(String uid) { return DbHelper.getYearGroup(mDatabase,uid).get(0).getGroup(); }

    public List<IEvent> getEvents(String day,int id) {
        return DbHelper.getEvents(mContext,mDatabase,day,id);
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }
}
