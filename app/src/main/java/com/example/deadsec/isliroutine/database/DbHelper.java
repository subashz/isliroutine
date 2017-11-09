package com.example.deadsec.isliroutine.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.deadsec.isliroutine.loader.ClassDataLab;
import com.example.deadsec.isliroutine.model.ClassModel;
import com.example.deadsec.isliroutine.model.Course;
import com.example.deadsec.isliroutine.model.Lession;
import com.example.deadsec.isliroutine.model.Room;
import com.example.deadsec.isliroutine.model.Teacher;
import com.example.deadsec.isliroutine.model.TimeTable;
import com.example.deadsec.isliroutine.model.YearGroup;
import com.framgia.library.calendardayview.data.IEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deadsec on 11/8/17.
 * All the insertion and selection from the database
 * are listed below as methods calls
 */

public class DbHelper {

    public static void addToYearGroup(SQLiteDatabase db, YearGroup course) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.YearGroup.Cols.ID, course.getId());
        values.put(DbSchema.YearGroup.Cols.CREATED_AT, course.getCreatedAt());
        values.put(DbSchema.YearGroup.Cols.UPDATED_AT, course.getUpdatedAt());
        values.put(DbSchema.YearGroup.Cols.END_DAY, course.getEndDay());
        values.put(DbSchema.YearGroup.Cols.START_DAY, course.getStartDay());
        values.put(DbSchema.YearGroup.Cols.START_MONTH, course.getStartMonth());
        values.put(DbSchema.YearGroup.Cols.START_YEAR, course.getStartYear());
        values.put(DbSchema.YearGroup.Cols.END_MONTH, course.getEndMonth());
        values.put(DbSchema.YearGroup.Cols.END_YEAR, course.getEndYear());
        values.put(DbSchema.YearGroup.Cols.GROUP, course.getGroup());
        values.put(DbSchema.YearGroup.Cols.YEAR, course.getMyear());
        db.insertWithOnConflict(DbSchema.YearGroup.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static void addToRoom(SQLiteDatabase db, Room room) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Room.Cols.ID, room.getId());
        values.put(DbSchema.Room.Cols.CREATED_AT, room.getCreatedAt());
        values.put(DbSchema.Room.Cols.UPDATED_AT, room.getUpdatedAt());
        values.put(DbSchema.Room.Cols.BLOCK, room.getBlock());
        values.put(DbSchema.Room.Cols.CLASSROOM, room.getClassRoom());
        db.insertWithOnConflict(DbSchema.Room.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static void addToTeacher(SQLiteDatabase db, Teacher teacher) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Teacher.Cols.ID, teacher.getId());
        values.put(DbSchema.Teacher.Cols.CREATED_AT, teacher.getCreatedAt());
        values.put(DbSchema.Teacher.Cols.UPDATED_AT, teacher.getUpdatedAt());
        values.put(DbSchema.Teacher.Cols.EMAIL, teacher.getEmail());
        values.put(DbSchema.Teacher.Cols.EXPERIENCE, teacher.getExperience());
        values.put(DbSchema.Teacher.Cols.FIRST_NAME, teacher.getFirstName());
        values.put(DbSchema.Teacher.Cols.LAST_NAME, teacher.getLastName());
        values.put(DbSchema.Teacher.Cols.MISC, teacher.getMisc());
        values.put(DbSchema.Teacher.Cols.OFFICE_HOUR, teacher.getOfficeHour());
        values.put(DbSchema.Teacher.Cols.PHONE, teacher.getPhone());
        values.put(DbSchema.Teacher.Cols.QUALIFICATION, teacher.getQualification());
        values.put(DbSchema.Teacher.Cols.WEBSITE, teacher.getWebsite());
        db.insertWithOnConflict(DbSchema.Teacher.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static void addToTimeTable(SQLiteDatabase db, TimeTable timeTable) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.TimeTable.Cols.ID, timeTable.getId());
        values.put(DbSchema.TimeTable.Cols.CREATED_AT, timeTable.getCreatedAt());
        values.put(DbSchema.TimeTable.Cols.UPDATED_AT, timeTable.getUpdatedAt());
        values.put(DbSchema.TimeTable.Cols.COURSE_ID, timeTable.getCourseId());
        values.put(DbSchema.TimeTable.Cols.DAYS, timeTable.getDays());
        values.put(DbSchema.TimeTable.Cols.END_HOUR, timeTable.getEndHour());
        values.put(DbSchema.TimeTable.Cols.END_MINUTE, timeTable.getEndMinute());
        values.put(DbSchema.TimeTable.Cols.LESSION_ID, timeTable.getLessionId());
        values.put(DbSchema.TimeTable.Cols.ROOM_ID, timeTable.getRoomId());
        values.put(DbSchema.TimeTable.Cols.START_HOUR, timeTable.getStartHour());
        values.put(DbSchema.TimeTable.Cols.START_MINUTE, timeTable.getStartMinute());
        values.put(DbSchema.TimeTable.Cols.TEACHER_ID, timeTable.getTeacherId());
        db.insertWithOnConflict(DbSchema.TimeTable.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static void addToLession(SQLiteDatabase db, Lession lession) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Lession.Cols.ID, lession.getId());
        values.put(DbSchema.Lession.Cols.CREATED_AT, lession.getCreatedAt());
        values.put(DbSchema.Lession.Cols.UPDATED_AT, lession.getUpdatedAt());
        values.put(DbSchema.Lession.Cols.TYPE, lession.getType());
        db.insertWithOnConflict(DbSchema.Lession.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static void addToCourse(SQLiteDatabase db, Course course) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Course.Cols.ID, course.getId());
        values.put(DbSchema.Course.Cols.CREATED_AT, course.getCreatedAt());
        values.put(DbSchema.Course.Cols.UPDATED_AT, course.getUpdatedAt());
        values.put(DbSchema.Course.Cols.ABOUT, course.getAbout());
        values.put(DbSchema.Course.Cols.MODULE_ID, course.getModuleId());
        values.put(DbSchema.Course.Cols.MODULE_LEADER, course.getModuleLeader());
        values.put(DbSchema.Course.Cols.RESOURCES, course.getResources());
        values.put(DbSchema.Course.Cols.TITLE, course.getTitle());
        db.insertWithOnConflict(DbSchema.Course.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static List<Course> getCourse(SQLiteDatabase db) {
        List<Course> courses = new ArrayList<>();
        CustomCursorWrapper cursor = getCustomCursor(db, DbSchema.Course.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                courses.add(cursor.getCourse());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return courses;
    }

    public static List<Lession> getLession(SQLiteDatabase db) {
        List<Lession> lessions = new ArrayList<>();
        CustomCursorWrapper cursor = getCustomCursor(db, DbSchema.Lession.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                lessions.add(cursor.getLession());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return lessions;
    }

    public static List<Room> getRoom(SQLiteDatabase db) {
        List<Room> rooms = new ArrayList<>();
        CustomCursorWrapper cursor = getCustomCursor(db, DbSchema.Room.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                rooms.add(cursor.getRoom());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return rooms;
    }

    public static List<Teacher> getTeacher(SQLiteDatabase db) {
        List<Teacher> teachers = new ArrayList<>();
        CustomCursorWrapper cursor = getCustomCursor(db, DbSchema.Teacher.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                teachers.add(cursor.getTeacher());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return teachers;
    }

    public static List<TimeTable> getTimeTable(SQLiteDatabase db) {
        List<TimeTable> timeTables = new ArrayList<>();
        CustomCursorWrapper cursor = getCustomCursor(db, DbSchema.TimeTable.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                timeTables.add(cursor.getTimeTable());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return timeTables;
    }

    public static List<YearGroup> getYearGroup(SQLiteDatabase db) {
        List<YearGroup> yearGroups = new ArrayList<>();
        CustomCursorWrapper cursor = getCustomCursor(db, DbSchema.YearGroup.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                yearGroups.add(cursor.getYearGroup());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return yearGroups;
    }

    public static CustomCursorWrapper getCustomCursor(SQLiteDatabase db, String tableName, String whereClause, String[] whereArgs) {
        return new CustomCursorWrapper(db.query(tableName, null, whereClause, whereArgs, null, null, null));
    }

    public static List<IEvent> getEvents(Context context, SQLiteDatabase db, String day) {
        List<IEvent> events = new ArrayList<>();
        String query = "select " +
                "ti.uid, " +
                "ti.start_hour, " +
                "ti.end_hour, " +
                "ti.start_minute, " +
                "ti.end_minute, " +
                "le.type, " +
                "te.first_name, " +
                "te.last_name, " +
                "ro.block, " +
                "ro.class_room, " +
                "co.title, " +
                "co.module_id " +
                "from timetable ti " +
                "inner join lession le on le.uid=ti.lession_id " +
                "inner join teacher te on te.uid=ti.teacher_id " +
                "inner join room ro on ro.uid=ti.room_id " +
                "inner join all_course co on co.uid=ti.course_id where ti.days='"+day+"'";
        Log.d("QUERY:",query);
        Cursor cursor = db.rawQuery(query,null);
        DatabaseUtils.dumpCursorToString(cursor);
        try {
            Log.d("Inside", "try");
            while (cursor.moveToNext()) {
                int uuid = cursor.getInt(cursor.getColumnIndex("uid"));
                int start_hour = cursor.getInt(cursor.getColumnIndex("start_hour"));
                int end_hour = cursor.getInt(cursor.getColumnIndex("end_hour"));
                int start_minute = cursor.getInt(cursor.getColumnIndex("start_minute"));
                int end_minute = cursor.getInt(cursor.getColumnIndex("end_minute"));
                String type = cursor.getString(cursor.getColumnIndex("type"));
                String first_name = cursor.getString(cursor.getColumnIndex("first_name"));
                String last_name = cursor.getString(cursor.getColumnIndex("last_name"));
                String block = cursor.getString(cursor.getColumnIndex("block"));
                String class_room = cursor.getString(cursor.getColumnIndex("class_room"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String module_id = cursor.getString(cursor.getColumnIndex("module_id"));
                Log.d("Type", type);
                events.add(ClassModel.getClassModel(context, uuid, start_hour, start_minute, end_hour, end_minute, title, block + " " + class_room, first_name + " " + last_name, module_id,type));
            }
        } catch (Exception e) {
            Log.d("Exception", "occured",e);
            e.printStackTrace();
        }finally {
            cursor.close();
        }
        return events;
    }


}
