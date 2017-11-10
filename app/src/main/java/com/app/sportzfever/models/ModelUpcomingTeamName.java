package com.app.sportzfever.models;

import java.io.File;
import java.io.Serializable;

/**
 * Created by hemanta on 10-09-2017.
 */

public class ModelUpcomingTeamName implements Serializable {

    public int getRowType() {
        return RowType;
    }

    public void setRowType(int rowType) {
        RowType = rowType;
    }

    private int RowType = 1;


    private String team2Id;



    private String location;

    private String ballsRemaining;

    private String team1Id;

    private String id;

    private String matchResultId;


    private String readStatus;

    private String eventId;

    private String description;


    private String numberOfPlayers;

    private String tossSelection;

    private String numberOfOvers;



    private String tossResultId;


    private String team1AvatarId;

    private String matchType;

    private String runsRequired;

    private String team2AvatarId;

    private String requiredRunRate;

    private String matchDate;

    private String matchStatus;



    public String getTeam2Id ()
    {
        return team2Id;
    }

    public void setTeam2Id (String team2Id)
    {
        this.team2Id = team2Id;
    }


    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getBallsRemaining ()
    {
        return ballsRemaining;
    }

    public void setBallsRemaining (String ballsRemaining)
    {
        this.ballsRemaining = ballsRemaining;
    }

    public String getTeam1Id ()
    {
        return team1Id;
    }

    public void setTeam1Id (String team1Id)
    {
        this.team1Id = team1Id;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getMatchResultId ()
    {
        return matchResultId;
    }

    public void setMatchResultId (String matchResultId)
    {
        this.matchResultId = matchResultId;
    }


    public String getReadStatus ()
    {
        return readStatus;
    }

    public void setReadStatus (String readStatus)
    {
        this.readStatus = readStatus;
    }

    public String getEventId ()
    {
        return eventId;
    }

    public void setEventId (String eventId)
    {
        this.eventId = eventId;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }



    public String getNumberOfPlayers ()
    {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers (String numberOfPlayers)
    {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getTossSelection ()
    {
        return tossSelection;
    }

    public void setTossSelection (String tossSelection)
    {
        this.tossSelection = tossSelection;
    }

    public String getNumberOfOvers ()
    {
        return numberOfOvers;
    }

    public void setNumberOfOvers (String numberOfOvers)
    {
        this.numberOfOvers = numberOfOvers;
    }



    public String getTossResultId ()
    {
        return tossResultId;
    }

    public void setTossResultId (String tossResultId)
    {
        this.tossResultId = tossResultId;
    }



    public String getTeam1AvatarId ()
    {
        return team1AvatarId;
    }

    public void setTeam1AvatarId (String team1AvatarId)
    {
        this.team1AvatarId = team1AvatarId;
    }

    public String getMatchType ()
    {
        return matchType;
    }

    public void setMatchType (String matchType)
    {
        this.matchType = matchType;
    }

    public String getRunsRequired ()
    {
        return runsRequired;
    }

    public void setRunsRequired (String runsRequired)
    {
        this.runsRequired = runsRequired;
    }

    public String getTeam2AvatarId ()
    {
        return team2AvatarId;
    }

    public void setTeam2AvatarId (String team2AvatarId)
    {
        this.team2AvatarId = team2AvatarId;
    }

    public String getRequiredRunRate ()
    {
        return requiredRunRate;
    }

    public void setRequiredRunRate (String requiredRunRate)
    {
        this.requiredRunRate = requiredRunRate;
    }

    public String getMatchDate ()
    {
        return matchDate;
    }

    public void setMatchDate (String matchDate)
    {
        this.matchDate = matchDate;
    }

    public String getMatchStatus ()
    {
        return matchStatus;
    }

    public void setMatchStatus (String matchStatus)
    {
        this.matchStatus = matchStatus;
    }





    public String getTeamAvatarId ()
    {
        return teamAvatarId;
    }

    public void setTeamAvatarId (String teamAvatarId)
    {
        this.teamAvatarId = teamAvatarId;
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

    public String getTeamId ()
    {
        return teamId;
    }

    public void setTeamId (String teamId)
    {
        this.teamId = teamId;
    }
    private String teamAvatarId;




    private String teamId;





    public String getPlayerTeamId ()
    {
        return playerTeamId;
    }

    public void setPlayerTeamId (String playerTeamId)
    {
        this.playerTeamId = playerTeamId;
    }

    public String getPlayPosition ()
    {
        return playPosition;
    }

    public void setPlayPosition (String playPosition)
    {
        this.playPosition = playPosition;
    }

    public String getPlaySquadId ()
    {
        return playSquadId;
    }

    public void setPlaySquadId (String playSquadId)
    {
        this.playSquadId = playSquadId;
    }

    public String getPlayerAvatarId ()
    {
        return playerAvatarId;
    }

    public void setPlayerAvatarId (String playerAvatarId)
    {
        this.playerAvatarId = playerAvatarId;
    }

    public String getPlayerRole ()
    {
        return playerRole;
    }

    public void setPlayerRole (String playerRole)
    {
        this.playerRole = playerRole;
    }

    private String name;

    private String profilePicture;

    private String playerTeamId;

    private String playPosition;

    private String playSquadId;

    private String playerAvatarId;

    private String playerRole;





}
