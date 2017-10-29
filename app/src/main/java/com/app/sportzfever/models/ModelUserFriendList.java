package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 10-09-2017.
 */

public class ModelUserFriendList implements Serializable{

    public int getRowType() {
        return RowType;
    }

    public void setRowType(int rowType) {
        RowType = rowType;
    }

    private int RowType=1;
    private String friendProfilePic;

    private String friendshipDate;

    private String requestStatus;

    private String friendName;

    private String friendId;

    public String getFriendProfilePic ()
    {
        return friendProfilePic;
    }

    public void setFriendProfilePic (String friendProfilePic)
    {
        this.friendProfilePic = friendProfilePic;
    }

    public String getFriendshipDate ()
    {
        return friendshipDate;
    }

    public void setFriendshipDate (String friendshipDate)
    {
        this.friendshipDate = friendshipDate;
    }

    public String getRequestStatus ()
    {
        return requestStatus;
    }

    public void setRequestStatus (String requestStatus)
    {
        this.requestStatus = requestStatus;
    }

    public String getFriendName ()
    {
        return friendName;
    }

    public void setFriendName (String friendName)
    {
        this.friendName = friendName;
    }

    public String getFriendId ()
    {
        return friendId;
    }

    public void setFriendId (String friendId)
    {
        this.friendId = friendId;
    }


}
