package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 01-09-2017.
 */

public class ModelAllTournamentPointTables implements Serializable{

    private String teamName;

    private String teamAvatarId;

    private String groupName;

    private String lost;

    private String teamId;

    private String id;

    private String groupId;

    private String teamProfilePicture="";

    private String matches;

    private String dummyTeamName;

    private String won;

    private String points;

    private String netRunRate;

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

    public String getGroupName ()
    {
        return groupName;
    }

    public void setGroupName (String groupName)
    {
        this.groupName = groupName;
    }

    public String getLost ()
    {
        return lost;
    }

    public void setLost (String lost)
    {
        this.lost = lost;
    }

    public String getTeamId ()
    {
        return teamId;
    }

    public void setTeamId (String teamId)
    {
        this.teamId = teamId;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getGroupId ()
    {
        return groupId;
    }

    public void setGroupId (String groupId)
    {
        this.groupId = groupId;
    }

    public String getTeamProfilePicture ()
    {
        return teamProfilePicture;
    }

    public void setTeamProfilePicture (String teamProfilePicture)
    {
        this.teamProfilePicture = teamProfilePicture;
    }

    public String getMatches ()
    {
        return matches;
    }

    public void setMatches (String matches)
    {
        this.matches = matches;
    }

    public String getDummyTeamName ()
    {
        return dummyTeamName;
    }

    public void setDummyTeamName (String dummyTeamName)
    {
        this.dummyTeamName = dummyTeamName;
    }

    public String getWon ()
    {
        return won;
    }

    public void setWon (String won)
    {
        this.won = won;
    }

    public String getPoints ()
    {
        return points;
    }

    public void setPoints (String points)
    {
        this.points = points;
    }

    public String getNetRunRate ()
    {
        return netRunRate;
    }

    public void setNetRunRate (String netRunRate)
    {
        this.netRunRate = netRunRate;
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;

}
