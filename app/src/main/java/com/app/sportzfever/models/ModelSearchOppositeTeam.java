package com.app.sportzfever.models;

/**
 * Created by hemanta on 21-12-2017.
 */

public class ModelSearchOppositeTeam {

    private String isActive;

    private String teamName;

    private String requestStatus;

    private String teamProfilePicture;

    private String sportId;

    private String teamAvatarId;

    private boolean added;

    private String sportName;

    private String teamLocation;

    private String teamId;
    private int rowType;

    public String getIsActive ()
    {
        return isActive;
    }

    public void setIsActive (String isActive)
    {
        this.isActive = isActive;
    }

    public String getTeamName ()
    {
        return teamName;
    }

    public void setTeamName (String teamName)
    {
        this.teamName = teamName;
    }

    public String getRequestStatus ()
    {
        return requestStatus;
    }

    public void setRequestStatus (String requestStatus)
    {
        this.requestStatus = requestStatus;
    }

    public String getTeamProfilePicture ()
    {
        return teamProfilePicture;
    }

    public void setTeamProfilePicture (String teamProfilePicture)
    {
        this.teamProfilePicture = teamProfilePicture;
    }

    public String getSportId ()
    {
        return sportId;
    }

    public void setSportId (String sportId)
    {
        this.sportId = sportId;
    }

    public String getTeamAvatarId ()
    {
        return teamAvatarId;
    }

    public void setTeamAvatarId (String teamAvatarId)
    {
        this.teamAvatarId = teamAvatarId;
    }

    public String getSportName ()
    {
        return sportName;
    }

    public void setSportName (String sportName)
    {
        this.sportName = sportName;
    }

    public String getTeamLocation ()
    {
        return teamLocation;
    }

    public void setTeamLocation (String teamLocation)
    {
        this.teamLocation = teamLocation;
    }

    public String getTeamId ()
    {
        return teamId;
    }

    public void setTeamId (String teamId)
    {
        this.teamId = teamId;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    public int getRowType() {
        return rowType;
    }
}
