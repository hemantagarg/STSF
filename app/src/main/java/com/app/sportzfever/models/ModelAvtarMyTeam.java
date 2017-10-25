package com.app.sportzfever.models;

public class ModelAvtarMyTeam
{

    private String teamName;

    private String teamAvatarId;

    private String location;

    private String owner;

    private String teamId,sportName;

    private String captain;

    public String getTeamProfilePicture() {
        return teamProfilePicture;
    }

    public void setTeamProfilePicture(String teamProfilePicture) {
        this.teamProfilePicture = teamProfilePicture;
    }

    private String teamProfilePicture;

    public String getTeamName ()
    {
        return teamName;
    }

    public void setTeamName (String teamName)
    {
        this.teamName = teamName;
    }

    public String getTeamAvatarId ()
    {
        return teamAvatarId;
    }

    public void setTeamAvatarId (String teamAvatarId)
    {
        this.teamAvatarId = teamAvatarId;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getOwner ()
    {
        return owner;
    }

    public void setOwner (String owner)
    {
        this.owner = owner;
    }

    public String getTeamId ()
    {
        return teamId;
    }

    public void setTeamId (String teamId)
    {
        this.teamId = teamId;
    }

    public String getCaptain ()
    {
        return captain;
    }

    public void setCaptain (String captain)
    {
        this.captain = captain;
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }
}
