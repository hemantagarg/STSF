package com.app.sportzfever.models;

public class ModelFollower
{

    private String teamName;

    private String teamProfilePicture;

    private String teamAvatarId;

    private String avatarType;

    private String status;

    private String followerUserId;

    private String followerName;

    private String followerPicture;

    public String getTeamName ()
    {
        return teamName;
    }

    public void setTeamName (String teamName)
    {
        this.teamName = teamName;
    }

    public String getTeamProfilePicture ()
    {
        return teamProfilePicture;
    }

    public void setTeamProfilePicture (String teamProfilePicture)
    {
        this.teamProfilePicture = teamProfilePicture;
    }

    public String getTeamAvatarId ()
    {
        return teamAvatarId;
    }

    public void setTeamAvatarId (String teamAvatarId)
    {
        this.teamAvatarId = teamAvatarId;
    }

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

    public String getFollowerUserId ()
    {
        return followerUserId;
    }

    public void setFollowerUserId (String followerUserId)
    {
        this.followerUserId = followerUserId;
    }

    public String getFollowerName ()
    {
        return followerName;
    }

    public void setFollowerName (String followerName)
    {
        this.followerName = followerName;
    }

    public String getFollowerPicture ()
    {
        return followerPicture;
    }

    public void setFollowerPicture (String followerPicture)
    {
        this.followerPicture = followerPicture;
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;

}
