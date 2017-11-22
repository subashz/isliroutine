package tk.blankstudio.isliroutine.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deadsec on 11/8/17.
 */

public class TimeTable {
    @SerializedName("id")
    private int mId;
    @SerializedName("room_id")
    private int mRoomId;
    @SerializedName("lession_id")
    private int mLessionId;

    @SerializedName("teacher_id")
    private int mTeacherId;

    @SerializedName("course_id")
    private int mCourseId;
    @SerializedName("start_hour")
    private int mStartHour;
    @SerializedName("end_hour")
    private int mEndHour;
    @SerializedName("start_minute")
    private int mStartMinute;
    @SerializedName("end_minute")
    private int mEndMinute;
    @SerializedName("days")
    private String mDays;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
    private String mUpdatedAt;

    public int getId() {
        return mId;
    }

    public int getRoomId() {
        return mRoomId;
    }

    public int getLessionId() {
        return mLessionId;
    }

    public int getTeacherId() {
        return mTeacherId;
    }

    public int getCourseId() {
        return mCourseId;
    }

    public int getStartHour() {
        return mStartHour;
    }

    public int getEndHour() {
        return mEndHour;
    }

    public int getStartMinute() {
        return mStartMinute;
    }

    public int getEndMinute() {
        return mEndMinute;
    }

    public String getDays() {
        return mDays;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public TimeTable(int id, int roomId, int lessionId, int teacherId, int courseId, int startHour, int endHour, int startMinute, int endMinute, String days, String createdAt, String updatedAt) {
        mId = id;
        mRoomId = roomId;
        mLessionId = lessionId;
        mTeacherId = teacherId;
        mCourseId = courseId;
        mStartHour = startHour;
        mEndHour = endHour;
        mStartMinute = startMinute;
        mEndMinute = endMinute;
        mDays = days;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
    }
}
