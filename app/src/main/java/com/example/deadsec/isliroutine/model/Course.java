package com.example.deadsec.isliroutine.model;

/**
 * Created by deadsec on 11/8/17.
 */

public class Course {
    private int mId;
    private String mTitle;
    private String mModuleId;
    private String mModuleLeader;
    private String mAbout;
    private String mResources;
    private String mCreatedAt;
    private String mUpdatedAt;

    public Course(int id, String title, String moduleId, String moduleLeader, String about, String resources, String createdAt, String updatedAt) {
        mId = id;
        mTitle = title;
        mModuleId = moduleId;
        mModuleLeader = moduleLeader;
        mAbout = about;
        mResources = resources;
        mCreatedAt = createdAt;
        mUpdatedAt = updatedAt;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getModuleId() {
        return mModuleId;
    }

    public String getModuleLeader() {
        return mModuleLeader;
    }

    public String getAbout() {
        return mAbout;
    }

    public String getResources() {
        return mResources;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }
}
