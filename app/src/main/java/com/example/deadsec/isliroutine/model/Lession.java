package com.example.deadsec.isliroutine.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deadsec on 11/8/17.
 */

public class Lession {
    @SerializedName("id")
    private int mId;
    @SerializedName("type")
    private String mType;
    @SerializedName("updated_at")
    private String mCreatedAt;
    @SerializedName("created_at")
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
