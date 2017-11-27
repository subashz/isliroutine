package tk.blankstudio.isliroutine.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deadsec on 11/8/17.
 * db mapping
 */

public class Course {
    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("module_id")
    private String mModuleId;
    @SerializedName("module_leader")
    private String mModuleLeader;
    @SerializedName("about")
    private String mAbout;
    @SerializedName("resources")
    private String mResources;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
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
