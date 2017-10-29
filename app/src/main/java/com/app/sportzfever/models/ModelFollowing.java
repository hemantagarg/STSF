package com.app.sportzfever.models;

public class ModelFollowing
{

    private String avatarType;

    private String status;

    private String fan_date_time;

    private String profilePicture;

    private String name;

    private String avatar;

    public String getAvatarType ()
    {
        return avatarType;
    }

    public void setAvatarType (String avatarType)
    {
        this.avatarType = avatarType;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getFan_date_time ()
    {
        return fan_date_time;
    }

    public void setFan_date_time (String fan_date_time)
    {
        this.fan_date_time = fan_date_time;
    }

    public String getProfilePicture ()
    {
        return profilePicture;
    }

    public void setProfilePicture (String profilePicture)
    {
        this.profilePicture = profilePicture;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;

}
