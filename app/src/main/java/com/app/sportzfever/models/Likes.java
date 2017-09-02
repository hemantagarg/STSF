package com.app.sportzfever.models;

/**
 * Created by hemanta on 01-09-2017.
 */

public class Likes {

    private String id;

    private String userProfilePicture;

    private String status;

    private String likeDateTime;

    private String userName;

    private String avatar;

    private String user;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUserProfilePicture ()
    {
        return userProfilePicture;
    }

    public void setUserProfilePicture (String userProfilePicture)
    {
        this.userProfilePicture = userProfilePicture;
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

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
    }

    public String getUser ()
    {
        return user;
    }

    public void setUser (String user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", userProfilePicture = "+userProfilePicture+", status = "+status+", likeDateTime = "+likeDateTime+", userName = "+userName+", avatar = "+avatar+", user = "+user+"]";
    }
}
