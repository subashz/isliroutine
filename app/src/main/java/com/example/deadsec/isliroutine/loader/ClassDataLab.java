package com.example.deadsec.isliroutine.loader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.example.deadsec.isliroutine.database.DbHelper;
import com.example.deadsec.isliroutine.database.TableCreator;
import com.example.deadsec.isliroutine.model.ClassModel;
import com.example.deadsec.isliroutine.model.Day;
import com.example.deadsec.isliroutine.R;
import com.example.deadsec.isliroutine.model.TimeTable;
import com.example.deadsec.isliroutine.model.YearGroup;
import com.framgia.library.calendardayview.data.IEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by deadsec on 10/30/17.
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

    public SQLiteDatabase getDatabase() {
        return mDatabase;
    }

    public static List<IEvent> getEvents(Context context, Day day) {
        List<IEvent> events = new ArrayList<>();
        switch (day) {
            case SUNDAY:
                events.add(getClassModel(context, 1, 7, 0, 8, 30, "Artificial Intelligence", "SR01-Tower Bridge", "Mr. Ashim Lamichhane", "SITY1cdw"));
                break;
            case MONDAY:
                events.add(getClassModel(context, 3, 9, 0, 10, 0, "Advance Database Systems Development", "Machapuchrae", "Mr. Rohit Panday", "SITY1cdw"));
                break;
            case TUESDAY:
                events.add(getClassModel(context, 4, 7, 0, 8, 0, "Application Development", "SR03-Piccadilly Circus", "Mr. Dhurba Sen", "SITY1cdw"));
                events.add(getClassModel(context, 10, 8, 30, 10, 0, "Advance Database System Developement", "SR02-Trafalgar Law", "Mr. Rohit Panday", "SITY1cdw"));
                break;
            case WEDNESDAY:
                events.add(getClassModel(context, 61, 7, 0, 8, 0, "Artificial Intelligence", "TR04-Lumbini", "Mr. Ashim Lamichhane", "SITY1cdw"));
                break;
            case THURSDAY:
                events.add(getClassModel(context, 73, 7, 0, 8, 30, "Application Development", "BuckingHam Palace", "Mr. Dhurba Sen", "SITY1cdw"));
                events.add(getClassModel(context, 65, 8, 30, 10, 0, "Artificial Intelligence", "TR02-Kensington Palace", "Mr. Ashim Lamichhane", "SITY1cdw"));
                break;
            case FRIDAY:
                events.add(getClassModel(context, 88, 7, 0, 8, 30, "Advance Database Systems Development", "BuckingHam Palace", "Mr. Rohit Panday", "SITY1cdw"));
                events.add(getClassModel(context, 91, 9, 0, 10, 30, "Application Development", "Liverpool", "Mr. Dhurba Sen", "SITY1cdw"));
                break;
            default:
                break;
        }
        return events;
    }


    public static ClassModel getClassModel(Context context, int id, int startHour, int startMinute, int stopHour, int stopMinute, String module, String location, String teacher, String mId) {
        int eventColor = ContextCompat.getColor(context, R.color.eventColor1);
        Random rand = new Random();
        int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        Calendar timeStart = Calendar.getInstance();
        timeStart.set(Calendar.HOUR_OF_DAY, startHour);
        timeStart.set(Calendar.MINUTE, startMinute);
        Calendar timeEnd = (Calendar) timeStart.clone();
        timeEnd.set(Calendar.HOUR_OF_DAY, stopHour);
        timeEnd.set(Calendar.MINUTE, stopMinute);
        return new ClassModel(id, timeStart, timeEnd, module, location, teacher, mId, color);
    }

    // mock datas
    /****
     * {
     "l1c4": {
     "id": "1",
     "uid": "L1C4",
     "created_at": "Artificial Intelligence"
     "updated_at":"Mr. Dhruba Sen"
     "from_time:"5:30"
     "to_time":"4:30"
     "room" : {
     "id":"r1",
     "block":"Nepal",
     "class_room":"Kanchanjunga",
     "description":"No descriptioin"
     },
     "course": {
     "id":"1",
     "uid":"C1C042",
     "created_at":"2017-04-02",
     "updated_at":"2017-04-05",
     "title":"Artificial Intelligence",
     "module_id":"S10423",
     "module_leader":"Prakash Shrestha",
     "module_info":"Information about the modules",
     "module_resource":"Resource of the modules"
     },
     "teacher": {
     "id":"1",
     "uuid":"233",
     "updated_at":"2017-02-30",
     "created_at":"2017-20-20",
     "first_name":"",
     "last_name":"",
     "office_hours":"",
     "phone":"",
     "mail":""
     "website":"",
     "qualification":"",
     "experience":"",
     "misc_description"
     }
     }
     }
     *
     */
}
