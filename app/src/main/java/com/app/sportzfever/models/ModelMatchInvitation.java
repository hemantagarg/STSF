package com.app.sportzfever.models;

/**
 * Created by hemanta on 30-08-2017.
 */

public class ModelMatchInvitation {


    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private String teamName;

    private String id;

    private String teamProfilePicture;

    private String readStatus;

    private String teamAvatarId;

    private String eventId;

    private String avatarId;

    private String matchId;

    private String eventTitle;

    private String eventType;

    private String teamId;

    private String inviteStatus;

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

    public String getTeamAvatarId ()
    {
        return teamAvatarId;
    }

    public void setTeamAvatarId (String teamAvatarId)
    {
        this.teamAvatarId = teamAvatarId;
    }

    public String getEventId ()
    {
        return eventId;
    }

    public void setEventId (String eventId)
    {
        this.eventId = eventId;
    }

    public String getAvatarId ()
    {
        return avatarId;
    }

    public void setAvatarId (String avatarId)
    {
        this.avatarId = avatarId;
    }

    public String getMatchId ()
    {
        return matchId;
    }

    public void setMatchId (String matchId)
    {
        this.matchId = matchId;
    }

    public String getEventTitle ()
    {
        return eventTitle;
    }

    public void setEventTitle (String eventTitle)
    {
        this.eventTitle = eventTitle;
    }

    public String getEventType ()
    {
        return eventType;
    }

    public void setEventType (String eventType)
    {
        this.eventType = eventType;
    }

    public String getTeamId ()
    {
        return teamId;
    }

    public void setTeamId (String teamId)
    {
        this.teamId = teamId;
    }

    public String getInviteStatus ()
    {
        return inviteStatus;
    }

    public void setInviteStatus (String inviteStatus)
    {
        this.inviteStatus = inviteStatus;
    }


}
