package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 01-09-2017.
 */

public class Likes implements Serializable{

    private String id;

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private String avatarName;

    private String userProfilePicture;

    private String avatarProfilePicture;

    private String avatarId;

    private String status;

    private String likeDateTime;

    private String userName;

    private String user;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAvatarName ()
    {
        return avatarName;
    }

    public void setAvatarName (String avatarName)
    {
        this.avatarName = avatarName;
    }

    public String getUserProfilePicture ()
    {
        return userProfilePicture;
    }

    public void setUserProfilePicture (String userProfilePicture)
    {
        this.userProfilePicture = userProfilePicture;
    }

    public String getAvatarProfilePicture ()
    {
        return avatarProfilePicture;
    }

    public void setAvatarProfilePicture (String avatarProfilePicture)
    {
        this.avatarProfilePicture = avatarProfilePicture;
    }

    public String getAvatarId ()
    {
        return avatarId;
    }

    public void setAvatarId (String avatarId)
    {
        this.avatarId = avatarId;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getLikeDateTime ()
    {
        return likeDateTime;
    }

    public void setLikeDateTime (String likeDateTime)
    {
        this.likeDateTime = likeDateTime;
    }

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getUser ()
    {
        return user;
    }

    public void setUser (String user)
    {
        this.user = user;
    }

}
