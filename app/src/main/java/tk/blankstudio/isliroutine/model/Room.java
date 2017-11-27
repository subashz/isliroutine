package tk.blankstudio.isliroutine.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deadsec on 11/8/17.
 *
 * mapping to the retrofit responze and the internal database
 */

public class Room {
    @SerializedName("id")
    private int mId;
    @SerializedName("block")
    private String mBlock;
    @SerializedName("class_room")
    private String mClassRoom;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("updated_at")
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
