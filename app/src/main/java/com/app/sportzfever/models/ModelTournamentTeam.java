package com.app.sportzfever.models;

/**
 * Created by hemanta on 30-08-2017.
 */

public class ModelTournamentTeam {


    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private String tournamentId;


    private String teamName;

    private String teamAvatarId;

    private String stamp_created;

    private String isDeleted;

    private String stamp_updated;

    private String teamId;

    private String invitationSentOn;

    private String id;

    private String isActive;

    private String teamProfilePicture;

    private String readStatus;

    private String inviteStatus;

    public String getTournamentId ()
    {
        return tournamentId;
    }

    public void setTournamentId (String tournamentId)
    {
        this.tournamentId = tournamentId;
    }



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

    public String getStamp_created ()
    {
        return stamp_created;
    }

    public void setStamp_created (String stamp_created)
    {
        this.stamp_created = stamp_created;
    }

    public String getIsDeleted ()
    {
        return isDeleted;
    }

    public void setIsDeleted (String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public String getStamp_updated ()
    {
        return stamp_updated;
    }

    public void setStamp_updated (String stamp_updated)
    {
        this.stamp_updated = stamp_updated;
    }

    public String getTeamId ()
    {
        return teamId;
    }

    public void setTeamId (String teamId)
    {
        this.teamId = teamId;
    }

    public String getInvitationSentOn ()
    {
        return invitationSentOn;
    }

    public void setInvitationSentOn (String invitationSentOn)
    {
        this.invitationSentOn = invitationSentOn;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIsActive ()
    {
        return isActive;
    }

    public void setIsActive (String isActive)
    {
        this.isActive = isActive;
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

    public String getInviteStatus ()
    {
        return inviteStatus;
    }

    public void setInviteStatus (String inviteStatus)
    {
        this.inviteStatus = inviteStatus;
    }
}
