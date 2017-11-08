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
                        YearGroup.Cols.END_YEAR + ")"
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
                        TimeTable.Cols.DAYS + ")"
        );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
