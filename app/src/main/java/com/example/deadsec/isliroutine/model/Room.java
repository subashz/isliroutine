package com.example.deadsec.isliroutine.model;

/**
 * Created by deadsec on 11/8/17.
 */

public class Room {
    private int mId;
    private String mBlock;
    private String mClassRoom;
    private String mCreatedAt;
    private String mUpdatedAt;

    public Room(int id, String block, String classRoom, String createdAt, String updatedAt) {
        mId = id;
        mBlock = block;
        mClassRoom = classRoom;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
    }

    public int getId() {
        return mId;
    }

    public String getBlock() {
        return mBlock;
    }

    public String getClassRoom() {
        return mClassRoom;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }
}
