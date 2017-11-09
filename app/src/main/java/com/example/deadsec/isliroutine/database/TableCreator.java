package com.example.deadsec.isliroutine.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.deadsec.isliroutine.database.DbSchema.*;

/**
 * Created by deadsec on 11/8/17.
 */

public class TableCreator extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "Routine";

    public TableCreator(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createYearGroupTable(db);
        createTimeTable(db);
        createCourseTable(db);
        createLessionTable(db);
        createRoomTable(db);
        createTeacherTable(db);

    }

    public void createYearGroupTable(SQLiteDatabase db) {
        db.execSQL(
                "create table " + YearGroup.NAME + "(" +
                        "_id integer primary key autoincrement, " +
                        YearGroup.Cols.ID + "," +
                        YearGroup.Cols.END_DAY + "," +
                        YearGroup.Cols.END_MONTH + "," +
                        YearGroup.Cols.GROUP + "," +
                        YearGroup.Cols.START_DAY + "," +
                        YearGroup.Cols.START_MONTH + "," +
                        YearGroup.Cols.START_YEAR + "," +
                        YearGroup.Cols.YEAR + "," +
                        YearGroup.Cols.UPDATED_AT + "," +
                        YearGroup.Cols.CREATED_AT + "," +
                        YearGroup.Cols.END_YEAR + "," +
                        " UNIQUE(" + YearGroup.Cols.ID + "))"
        );
    }

    public void createTimeTable(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TimeTable.NAME + "(" +
                        "_id integer primary key autoincrement, " +
                        TimeTable.Cols.ID + "," +
                        TimeTable.Cols.UPDATED_AT + "," +
                        TimeTable.Cols.CREATED_AT + "," +
                        TimeTable.Cols.END_HOUR + "," +
                        TimeTable.Cols.START_HOUR + "," +
                        TimeTable.Cols.START_MINUTE + "," +
                        TimeTable.Cols.END_MINUTE + "," +
                        TimeTable.Cols.COURSE_ID + "," +
                        TimeTable.Cols.LESSION_ID + "," +
                        TimeTable.Cols.TEACHER_ID + "," +
                        TimeTable.Cols.ROOM_ID + "," +
                        TimeTable.Cols.DAYS + ", " +
                        " UNIQUE(" + TimeTable.Cols.ID + "))"
        );
    }

    public void createLessionTable(SQLiteDatabase db) {
        db.execSQL(
                "create table " + Lession.NAME + "(" +
                        "_id integer primary key autoincrement, " +
                        Lession.Cols.ID + "," +
                        Lession.Cols.CREATED_AT + "," +
                        Lession.Cols.UPDATED_AT + "," +
                        Lession.Cols.TYPE + "," +
                        " UNIQUE(" + Lession.Cols.ID + "))"
        );
    }

    public void createCourseTable(SQLiteDatabase db) {
        db.execSQL(
                "create table " + Course.NAME + "(" +
                        "_id integer primary key autoincrement, " +
                        Course.Cols.ID + "," +
                        Course.Cols.CREATED_AT + "," +
                        Course.Cols.UPDATED_AT + "," +
                        Course.Cols.ABOUT + "," +
                        Course.Cols.MODULE_ID + "," +
                        Course.Cols.MODULE_LEADER + "," +
                        Course.Cols.RESOURCES + "," +
                        Course.Cols.TITLE + "," +
                        " UNIQUE(" + Course.Cols.ID + "))"
        );
    }

    public void createRoomTable(SQLiteDatabase db) {
        db.execSQL(
                "create table " + Room.NAME + "(" +
                        "_id integer primary key autoincrement, " +
                        Room.Cols.ID + "," +
                        Room.Cols.CREATED_AT + "," +
                        Room.Cols.UPDATED_AT + "," +
                        Room.Cols.BLOCK + "," +
                        Room.Cols.CLASSROOM + "," +
                        " UNIQUE(" + Room.Cols.ID + "))"
        );
    }

    public void createTeacherTable(SQLiteDatabase db) {
        db.execSQL(
                "create table " + Teacher.NAME + "(" +
                        "_id integer primary key autoincrement, " +
                        Teacher.Cols.ID + "," +
                        Teacher.Cols.CREATED_AT + "," +
                        Teacher.Cols.UPDATED_AT + "," +
                        Teacher.Cols.EMAIL + "," +
                        Teacher.Cols.EXPERIENCE + "," +
                        Teacher.Cols.FIRST_NAME + "," +
                        Teacher.Cols.LAST_NAME + "," +
                        Teacher.Cols.MISC + "," +
                        Teacher.Cols.OFFICE_HOUR + "," +
                        Teacher.Cols.PHONE + "," +
                        Teacher.Cols.QUALIFICATION + "," +
                        Teacher.Cols.WEBSITE + "," +
                        " UNIQUE(" + Teacher.Cols.ID + "))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
