package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 01-09-2017.
 */

public class ModelAllTournament implements Serializable{

    private String lastEnrollmentDate;

    private String location;

    private String tournamentOrganizerId;

    private String isPublished;

    private String profilePicture;

    private String sportName;

    private String isDeleted;

    private String type;

    private String noOfTeam;

    private String aboutTournament;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    private String datetime;



    private String tournamentState;

    private String tournamentEndDate;

    private String isActive;

    private String id;

    private String noOfOvers;

    private String userId;

    private String name;

    private String tournamentResultId;

    private String roundOfPlay;

    private String noOfGroup;

    private String tournamentStateId;

    public String getLastEnrollmentDate ()
    {
        return lastEnrollmentDate;
    }

    public void setLastEnrollmentDate (String lastEnrollmentDate)
    {
        this.lastEnrollmentDate = lastEnrollmentDate;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getTournamentOrganizerId ()
    {
        return tournamentOrganizerId;
    }

    public void setTournamentOrganizerId (String tournamentOrganizerId)
    {
        this.tournamentOrganizerId = tournamentOrganizerId;
    }

    public String getIsPublished ()
    {
        return isPublished;
    }

    public void setIsPublished (String isPublished)
    {
        this.isPublished = isPublished;
    }

    public String getProfilePicture ()
    {
        return profilePicture;
    }

    public void setProfilePicture (String profilePicture)
    {
        this.profilePicture = profilePicture;
    }

    public String getSportName ()
    {
        return sportName;
    }

    public void setSportName (String sportName)
    {
        this.sportName = sportName;
    }

    public String getIsDeleted ()
    {
        return isDeleted;
    }

    public void setIsDeleted (String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getNoOfTeam ()
    {
        return noOfTeam;
    }

    public void setNoOfTeam (String noOfTeam)
    {
        this.noOfTeam = noOfTeam;
    }

    public String getAboutTournament ()
    {
        return aboutTournament;
    }

    public void setAboutTournament (String aboutTournament)
    {
        this.aboutTournament = aboutTournament;
    }



    public String getTournamentState ()
    {
        return tournamentState;
    }

    public void setTournamentState (String tournamentState)
    {
        this.tournamentState = tournamentState;
    }

    public String getTournamentEndDate ()
    {
        return tournamentEndDate;
    }

    public void setTournamentEndDate (String tournamentEndDate)
    {
        this.tournamentEndDate = tournamentEndDate;
    }

    public String getIsActive ()
    {
        return isActive;
    }

    public void setIsActive (String isActive)
    {
        this.isActive = isActive;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getNoOfOvers ()
    {
        return noOfOvers;
    }

    public void setNoOfOvers (String noOfOvers)
    {
        this.noOfOvers = noOfOvers;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getTournamentResultId ()
    {
        return tournamentResultId;
    }

    public void setTournamentResultId (String tournamentResultId)
    {
        this.tournamentResultId = tournamentResultId;
    }

    public String getRoundOfPlay ()
    {
        return roundOfPlay;
    }

    public void setRoundOfPlay (String roundOfPlay)
    {
        this.roundOfPlay = roundOfPlay;
    }

    public String getNoOfGroup ()
    {
        return noOfGroup;
    }

    public void setNoOfGroup (String noOfGroup)
    {
        this.noOfGroup = noOfGroup;
    }

    public String getTournamentStateId ()
    {
        return tournamentStateId;
    }

    public void setTournamentStateId (String tournamentStateId)
    {
        this.tournamentStateId = tournamentStateId;
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;

}
