package com.app.sportzfever.models;

/**
 * Created by hemanta on 01-09-2017.
 */

public class Comments {

    private String id;

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private String userProfilePicture;

    private String status;

    private String userName;

    private String commentDateTime;

    private String comment;

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

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getCommentDateTime ()
    {
        return commentDateTime;
    }

    public void setCommentDateTime (String commentDateTime)
    {
        this.commentDateTime = commentDateTime;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
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
        return "ClassPojo [id = "+id+", userProfilePicture = "+userProfilePicture+", status = "+status+", userName = "+userName+", commentDateTime = "+commentDateTime+", comment = "+comment+", avatar = "+avatar+", user = "+user+"]";
    }
}
