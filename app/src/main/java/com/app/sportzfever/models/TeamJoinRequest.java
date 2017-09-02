package com.app.sportzfever.models;

public class TeamJoinRequest
{

    private String teamName;

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private String id;

    private String teamProfilePicture;

    private String readStatus;

    private String requestStatus;

    private String teamAvatarId;

    private String playerAvatarProfilePicture;

    private String playerAvatarId;

    private String teamId;

    private String playerAvatarName;

    public String getTeamName ()
    {
        return teamName;
    }

    public void setTeamName (String teamName)
    {
        this.teamName = teamName;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTeamProfilePicture ()
    {
        return teamProfilePicture;
    }

    public void setTeamProfilePicture (String teamProfilePicture)
    {
        this.teamProfilePicture = teamProfilePicture;
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

    public String getTeamAvatarId ()
    {
        return teamAvatarId;
    }

    public void setTeamAvatarId (String teamAvatarId)
    {
        this.teamAvatarId = teamAvatarId;
    }

    public String getPlayerAvatarProfilePicture ()
    {
        return playerAvatarProfilePicture;
    }

    public void setPlayerAvatarProfilePicture (String playerAvatarProfilePicture)
    {
        this.playerAvatarProfilePicture = playerAvatarProfilePicture;
    }

    public String getPlayerAvatarId ()
    {
        return playerAvatarId;
    }

    public void setPlayerAvatarId (String playerAvatarId)
    {
        this.playerAvatarId = playerAvatarId;
    }

    public String getTeamId ()
    {
        return teamId;
    }

    public void setTeamId (String teamId)
    {
        this.teamId = teamId;
    }

    public String getPlayerAvatarName ()
    {
        return playerAvatarName;
    }

    public void setPlayerAvatarName (String playerAvatarName)
    {
        this.playerAvatarName = playerAvatarName;
    }



}
