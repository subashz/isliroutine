package com.example.deadsec.isliroutine.model;

/**
 * Created by deadsec on 11/8/17.
 */

public class Lession {
    private int mId;
    private String mType;
    private String mCreatedAt;
    private String mUpdatedAt;

    public Lession(int id, String type, String createdAt, String updatedAt) {
        mId = id;
        mType = type;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
    }

    public int getId() {
        return mId;
    }

    public String getType() {
        return mType;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }
}
