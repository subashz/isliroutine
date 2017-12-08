package tk.blankstudio.isliroutine.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import tk.blankstudio.isliroutine.model.ClassModel;
import tk.blankstudio.isliroutine.model.ClassRoomCourse;
import tk.blankstudio.isliroutine.model.RoutineCourse;
import tk.blankstudio.isliroutine.model.Lession;
import tk.blankstudio.isliroutine.model.Room;
import tk.blankstudio.isliroutine.model.Teacher;
import tk.blankstudio.isliroutine.model.TimeTable;
import tk.blankstudio.isliroutine.model.YearGroup;
import com.framgia.library.calendardayview.data.IEvent;
import com.google.api.services.classroom.model.Course;

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
        values.put(DbSchema.Teacher.Cols.NAME, teacher.getName());
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
        values.put(DbSchema.TimeTable.Cols.YEAR_GROUP_ID, timeTable.getYearGroupId());
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

    public static void addToCourse(SQLiteDatabase db, RoutineCourse routineCourse) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.Course.Cols.ID, routineCourse.getId());
        values.put(DbSchema.Course.Cols.CREATED_AT, routineCourse.getCreatedAt());
        values.put(DbSchema.Course.Cols.UPDATED_AT, routineCourse.getUpdatedAt());
        values.put(DbSchema.Course.Cols.ABOUT, routineCourse.getAbout());
        values.put(DbSchema.Course.Cols.MODULE_ID, routineCourse.getModuleId());
        values.put(DbSchema.Course.Cols.MODULE_LEADER, routineCourse.getModuleLeader());
        values.put(DbSchema.Course.Cols.RESOURCES, routineCourse.getResources());
        values.put(DbSchema.Course.Cols.TITLE, routineCourse.getTitle());
        db.insertWithOnConflict(DbSchema.Course.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static void addToClassRoomCourse(SQLiteDatabase db, ClassRoomCourse classRoomCourse) {
      ContentValues values = new ContentValues();
        values.put(DbSchema.ClassRoomCourse.Cols.ID, classRoomCourse.getId());
        values.put(DbSchema.ClassRoomCourse.Cols.NAME, classRoomCourse.getName());
        values.put(DbSchema.ClassRoomCourse.Cols.DESCRIPTION, classRoomCourse.getDescription());
        values.put(DbSchema.ClassRoomCourse.Cols.ENROLLMENT_CODE, classRoomCourse.getEnrollmentCode());
        values.put(DbSchema.ClassRoomCourse.Cols.COURSE_STATE, classRoomCourse.getCourseState());
        values.put(DbSchema.ClassRoomCourse.Cols.ALTERNATE_LINK, classRoomCourse.getAlternateLink());
        values.put(DbSchema.ClassRoomCourse.Cols.DESCRIPTIONHEADING, classRoomCourse.getDescriptionHeading());
        values.put(DbSchema.ClassRoomCourse.Cols.GOOGLE_DRIVE_LINK, classRoomCourse.getGoogleDriveLink());
        values.put(DbSchema.ClassRoomCourse.Cols.SECTION, classRoomCourse.getSection());
        db.insertWithOnConflict(DbSchema.ClassRoomCourse.NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static List<RoutineCourse> getRoutineCourse(SQLiteDatabase db) {
        List<RoutineCourse> cours = new ArrayList<>();
        CustomCursorWrapper cursor = getCustomCursor(db, DbSchema.Course.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                cours.add(cursor.getCourse());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return cours;
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

    public static List<TimeTable> getTimeTable(SQLiteDatabase db,int index) {
        List<TimeTable> timeTables = new ArrayList<>();
        CustomCursorWrapper cursor = getCustomCursor(db, DbSchema.TimeTable.NAME, "year_group_id="+index, null);
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

    public static List<YearGroup> getYearGroup(SQLiteDatabase db,String uid) {
        List<YearGroup> yearGroups = new ArrayList<>();
        CustomCursorWrapper cursor;
        if(uid==null) {
            cursor= getCustomCursor(db, DbSchema.YearGroup.NAME,null , null);
        }else {
             cursor= getCustomCursor(db, DbSchema.YearGroup.NAME, "uid="+uid, null);
        }
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

      public static List<ClassRoomCourse> getClassRoomCourse(SQLiteDatabase db) {
        List<ClassRoomCourse> classRoomCourse = new ArrayList<>();
        CustomCursorWrapper cursor = getCustomCursor(db, DbSchema.ClassRoomCourse.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                classRoomCourse.add(cursor.getClassRoomCourse());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return classRoomCourse;
    }

    public static CustomCursorWrapper getCustomCursor(SQLiteDatabase db, String tableName, String whereClause, String[] whereArgs) {
        return new CustomCursorWrapper(db.query(tableName, null, whereClause, whereArgs, null, null, null));
    }

    public static List<IEvent> getEvents(Context context, SQLiteDatabase db, String day,int group_id) {
        List<IEvent> events = new ArrayList<>();
        String query = "select " +
                "ti.uid, " +
                "ti.start_hour, " +
                "ti.end_hour, " +
                "ti.start_minute, " +
                "ti.end_minute, " +
                "le.type, " +
                "te.name, " +
                "ro.block, " +
                "ro.class_room, " +
                "co.title, " +
                "co.module_id " +
                "from timetable ti " +
                "inner join lession le on le.uid=ti.lession_id " +
                "inner join teacher te on te.uid=ti.teacher_id " +
                "inner join room ro on ro.uid=ti.room_id " +
                "inner join all_course co on co.uid=ti.course_id where ti.days='"+day+"' and ti.year_group_id="+group_id;
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
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String block = cursor.getString(cursor.getColumnIndex("block"));
                String class_room = cursor.getString(cursor.getColumnIndex("class_room"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String module_id = cursor.getString(cursor.getColumnIndex("module_id"));
                Log.d("Type", type);
                events.add(ClassModel.getClassModel(context, uuid, start_hour, start_minute, end_hour, end_minute, title, class_room, name, module_id,type));
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
