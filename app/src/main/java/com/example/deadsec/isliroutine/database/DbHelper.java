package com.example.deadsec.isliroutine.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.deadsec.isliroutine.loader.ClassDataLab;
import com.example.deadsec.isliroutine.model.Course;
import com.example.deadsec.isliroutine.model.Lession;
import com.example.deadsec.isliroutine.model.Room;
import com.example.deadsec.isliroutine.model.Teacher;
import com.example.deadsec.isliroutine.model.TimeTable;
import com.example.deadsec.isliroutine.model.YearGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deadsec on 11/8/17.
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
        db.insert(DbSchema.YearGroup.NAME, null, values);
    }

    public static void addToRoom(SQLiteDatabase db, Room room) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Room.Cols.ID, room.getId());
        values.put(DbSchema.Room.Cols.CREATED_AT, room.getCreatedAt());
        values.put(DbSchema.Room.Cols.UPDATED_AT, room.getUpdatedAt());
        values.put(DbSchema.Room.Cols.BLOCK, room.getBlock());
        values.put(DbSchema.Room.Cols.CLASSROOM, room.getClassRoom());
        db.insert(DbSchema.Room.NAME, null, values);
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
        db.insert(DbSchema.Teacher.NAME, null, values);
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
        db.insert(DbSchema.TimeTable.NAME, null, values);
    }

    public static void addToLession(SQLiteDatabase db, Lession lession) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Lession.Cols.ID, lession.getId());
        values.put(DbSchema.Lession.Cols.CREATED_AT, lession.getCreatedAt());
        values.put(DbSchema.Lession.Cols.UPDATED_AT, lession.getUpdatedAt());
        values.put(DbSchema.Lession.Cols.TYPE, lession.getType());
        db.insert(DbSchema.Lession.NAME, null, values);
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
        db.insert(DbSchema.Course.NAME, null, values);
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

}
