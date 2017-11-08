package com.example.deadsec.isliroutine.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.deadsec.isliroutine.model.Course;
import com.example.deadsec.isliroutine.model.Lession;
import com.example.deadsec.isliroutine.model.Room;
import com.example.deadsec.isliroutine.model.Teacher;
import com.example.deadsec.isliroutine.model.TimeTable;
import com.example.deadsec.isliroutine.model.YearGroup;


/**
 * Created by deadsec on 11/8/17.
 */

public class CustomCursorWrapper extends CursorWrapper {
    public CustomCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public YearGroup getYearGroup() {
        int id = getInt(getColumnIndex(DbSchema.YearGroup.Cols.ID));
        String mCreatedAt = getString(getColumnIndex(DbSchema.YearGroup.Cols.CREATED_AT));
        String mUpdatedAt = getString(getColumnIndex(DbSchema.YearGroup.Cols.UPDATED_AT));

        int mEndDay = getInt(getColumnIndex(DbSchema.YearGroup.Cols.END_DAY));
        int mStartDay = getInt(getColumnIndex(DbSchema.YearGroup.Cols.START_DAY));
        int mEndYear = getInt(getColumnIndex(DbSchema.YearGroup.Cols.END_YEAR));
        int mEndMonth = getInt(getColumnIndex(DbSchema.YearGroup.Cols.END_MONTH));
        int mStartYear = getInt(getColumnIndex(DbSchema.YearGroup.Cols.START_YEAR));
        int mStartMonth = getInt(getColumnIndex(DbSchema.YearGroup.Cols.START_MONTH));
        int mYear = getInt(getColumnIndex(DbSchema.YearGroup.Cols.YEAR));
        String mGroup = getString(getColumnIndex(DbSchema.YearGroup.Cols.GROUP));
        return new YearGroup(id, mYear, mStartDay, mEndDay, mStartYear, mEndYear, mStartMonth, mEndMonth, mGroup, mUpdatedAt, mCreatedAt);
    }

    public Room getRoom() {
        int id = getInt(getColumnIndex(DbSchema.Room.Cols.ID));
        String mCreatedAt = getString(getColumnIndex(DbSchema.Room.Cols.CREATED_AT));
        String mUpdatedAt = getString(getColumnIndex(DbSchema.Room.Cols.UPDATED_AT));
        String mBlock = getString(getColumnIndex(DbSchema.Room.Cols.BLOCK));
        String mClassRoom = getString(getColumnIndex(DbSchema.Room.Cols.CLASSROOM));

        return new Room(id, mBlock, mClassRoom, mCreatedAt, mUpdatedAt);
    }

    public Lession getLession() {
        int id = getInt(getColumnIndex(DbSchema.YearGroup.Cols.ID));
        String mCreatedAt = getString(getColumnIndex(DbSchema.Lession.Cols.CREATED_AT));
        String mUpdatedAt = getString(getColumnIndex(DbSchema.Lession.Cols.UPDATED_AT));
        String mType = getString(getColumnIndex(DbSchema.Lession.Cols.TYPE));
        return new Lession(id, mType, mCreatedAt, mUpdatedAt);
    }

    public Teacher getTeacher() {
        int id = getInt(getColumnIndex(DbSchema.Teacher.Cols.ID));
        String mCreatedAt = getString(getColumnIndex(DbSchema.Teacher.Cols.CREATED_AT));
        String mUpdatedAt = getString(getColumnIndex(DbSchema.Teacher.Cols.UPDATED_AT));
        String mFirstName = getString(getColumnIndex(DbSchema.Teacher.Cols.FIRST_NAME));
        String mLastName = getString(getColumnIndex(DbSchema.Teacher.Cols.LAST_NAME));
        String mEmail = getString(getColumnIndex(DbSchema.Teacher.Cols.EMAIL));
        String mExperience = getString(getColumnIndex(DbSchema.Teacher.Cols.EXPERIENCE));
        String mOfficeHour = getString(getColumnIndex(DbSchema.Teacher.Cols.OFFICE_HOUR));
        String mPhone = getString(getColumnIndex(DbSchema.Teacher.Cols.PHONE));
        String mQualification = getString(getColumnIndex(DbSchema.Teacher.Cols.QUALIFICATION));
        String mMisc = getString(getColumnIndex(DbSchema.Teacher.Cols.MISC));
        String mWebsite = getString(getColumnIndex(DbSchema.Teacher.Cols.WEBSITE));
        return new Teacher(id, mFirstName, mLastName, mOfficeHour, mPhone, mEmail, mWebsite, mQualification, mExperience, mMisc, mUpdatedAt, mCreatedAt);
    }

    public TimeTable getTimeTable() {
        int id = getInt(getColumnIndex(DbSchema.TimeTable.Cols.ID));
        String mCreatedAt = getString(getColumnIndex(DbSchema.TimeTable.Cols.CREATED_AT));
        String mUpdatedAt = getString(getColumnIndex(DbSchema.TimeTable.Cols.UPDATED_AT));
        String mDays = getString(getColumnIndex(DbSchema.TimeTable.Cols.DAYS));
        int mCourseId = getInt(getColumnIndex(DbSchema.TimeTable.Cols.COURSE_ID));
        int mEndHour = getInt(getColumnIndex(DbSchema.TimeTable.Cols.END_HOUR));
        int mEndMinute = getInt(getColumnIndex(DbSchema.TimeTable.Cols.END_MINUTE));
        int mStartHour = getInt(getColumnIndex(DbSchema.TimeTable.Cols.START_HOUR));
        int mStartMinute = getInt(getColumnIndex(DbSchema.TimeTable.Cols.START_MINUTE));
        int mRoomId = getInt(getColumnIndex(DbSchema.TimeTable.Cols.ROOM_ID));
        int mTeacherId = getInt(getColumnIndex(DbSchema.TimeTable.Cols.TEACHER_ID));
        int mLessionId = getInt(getColumnIndex(DbSchema.TimeTable.Cols.LESSION_ID));

        return new TimeTable(id, mRoomId, mLessionId, mTeacherId, mCourseId, mStartHour, mEndHour, mStartMinute, mEndMinute, mDays, mCreatedAt, mUpdatedAt);
    }

    public Course getCourse() {
        int id = getInt(getColumnIndex(DbSchema.Course.Cols.ID));
        String mCreatedAt = getString(getColumnIndex(DbSchema.Course.Cols.CREATED_AT));
        String mUpdatedAt = getString(getColumnIndex(DbSchema.Course.Cols.UPDATED_AT));
        String mModuleId = getString(getColumnIndex(DbSchema.Course.Cols.MODULE_ID));
        String mModuleLeader = getString(getColumnIndex(DbSchema.Course.Cols.MODULE_LEADER));
        String mAbout = getString(getColumnIndex(DbSchema.Course.Cols.ABOUT));
        String mResource = getString(getColumnIndex(DbSchema.Course.Cols.RESOURCES));
        String mTitle = getString(getColumnIndex(DbSchema.Course.Cols.TITLE));

        return new Course(id, mTitle, mModuleId, mModuleLeader, mAbout, mResource, mCreatedAt, mUpdatedAt);
    }
}
