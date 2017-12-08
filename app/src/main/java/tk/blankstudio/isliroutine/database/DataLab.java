package tk.blankstudio.isliroutine.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import tk.blankstudio.isliroutine.model.ClassRoomCourse;
import tk.blankstudio.isliroutine.model.RoutineCourse;
import tk.blankstudio.isliroutine.model.Lession;
import tk.blankstudio.isliroutine.model.Room;
import tk.blankstudio.isliroutine.model.Teacher;
import tk.blankstudio.isliroutine.model.TimeTable;
import tk.blankstudio.isliroutine.model.YearGroup;
import com.framgia.library.calendardayview.data.IEvent;
import com.google.api.services.classroom.model.Course;

import java.util.List;


/**
 * Created by deadsec on 10/30/17.
 * Singleton Class that retrives all the
 * data from the database
 */

public class DataLab {
    public static final String TAG = DataLab.class.getSimpleName();
    public static DataLab sDataLab;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public static DataLab get(Context context) {
        if(sDataLab == null) {
            sDataLab=new DataLab(context);
        }
        return sDataLab;
    }

    private DataLab(Context context) {
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

    public List<RoutineCourse> getCourses() {return DbHelper.getRoutineCourse(mDatabase);}

    public void addToCourse(RoutineCourse routineCourse) {
        DbHelper.addToCourse(mDatabase, routineCourse);
    }

    public void addToClassRoomCourse(ClassRoomCourse course) { DbHelper.addToClassRoomCourse(mDatabase,course);}

    public List<ClassRoomCourse> getClassRoomCourse() { return DbHelper.getClassRoomCourse(mDatabase);}

    public String getGroupName(String uid) { return DbHelper.getYearGroup(mDatabase,uid).get(0).getGroup(); }

    public List<IEvent> getEvents(String day,int id) {
        return DbHelper.getEvents(mContext,mDatabase,day,id);
    }

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }
}
