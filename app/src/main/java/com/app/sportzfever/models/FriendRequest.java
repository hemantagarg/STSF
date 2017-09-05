package com.app.sportzfever.models;

public class FriendRequest
{

    private String friendUserId;

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private String id;

    private String readStatus;

    public String getRequestSentAt() {
        return requestSentAt;
    }

    public void setRequestSentAt(String requestSentAt) {
        this.requestSentAt = requestSentAt;
    }

    private String requestSentAt;

    private String requestStatus;

    private String userId;

    private String friendUserName;

    private String userName;
    private String userProfilePicture;

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getFriendUserProfilePicture() {
        return friendUserProfilePicture;
    }

    public void setFriendUserProfilePicture(String friendUserProfilePicture) {
        this.friendUserProfilePicture = friendUserProfilePicture;
    }

    private String friendUserProfilePicture;

    public String getFriendUserId ()
    {
        return friendUserId;
    }

    public void setFriendUserId (String friendUserId)
    {
        this.friendUserId = friendUserId;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getReadStatus ()
    {
        return readStatus;
    }

    public void setReadStatus (String readStatus)
    {
        this.readStatus = readStatus;
    }

    public String getRequestStatus ()
    {
        return requestStatus;
    }

    public void setRequestStatus (String requestStatus)
    {
        this.requestStatus = requestStatus;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getFriendUserName ()
    {
        return friendUserName;
    }

    public void setFriendUserName (String friendUserName)
    {
        this.friendUserName = friendUserName;
    }

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }


}
