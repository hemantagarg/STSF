package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 10-09-2017.
 */

public class ModelFriendList implements Serializable{

    private String message;
    private int RowType=1;
    private String lastseen;

    private String friendProfilePic;

    private String lastseensetting;

    private String isdevice;

    private String status;

    private String readreceiptsetting;

    private String lastactivity;

    private String friendName;

    private String friendId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLastseen() {
        return lastseen;
    }

    public void setLastseen(String lastseen) {
        this.lastseen = lastseen;
    }

    public String getFriendProfilePic() {
        return friendProfilePic;
    }

    public void setFriendProfilePic(String friendProfilePic) {
        this.friendProfilePic = friendProfilePic;
    }

    public String getLastseensetting() {
        return lastseensetting;
    }

    public void setLastseensetting(String lastseensetting) {
        this.lastseensetting = lastseensetting;
    }

    public String getIsdevice() {
        return isdevice;
    }

    public void setIsdevice(String isdevice) {
        this.isdevice = isdevice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReadreceiptsetting() {
        return readreceiptsetting;
    }

    public void setReadreceiptsetting(String readreceiptsetting) {
        this.readreceiptsetting = readreceiptsetting;
    }

    public String getLastactivity() {
        return lastactivity;
    }

    public void setLastactivity(String lastactivity) {
        this.lastactivity = lastactivity;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", lastseen = " + lastseen + ", friendProfilePic = " + friendProfilePic + ", lastseensetting = " + lastseensetting + ", isdevice = " + isdevice + ", status = " + status + ", readreceiptsetting = " + readreceiptsetting + ", lastactivity = " + lastactivity + ", friendName = " + friendName + ", friendId = " + friendId + "]";
    }

    public int getRowType() {
        return RowType;
    }

    public void setRowType(int rowType) {
        RowType = rowType;
    }
}
