package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 10-09-2017.
 */

public class ModelStats implements Serializable{

    public int getRowType() {
        return RowType;
    }

    public void setRowType(int rowType) {
        RowType = rowType;
    }

    private int RowType=1;
    private String dateOfBirth;

    private String userProfilePicture;

    private String avatarId;



    private String totalMatch;

    private String totalCatches;


    private String batsmanId;
    private String highScorMatchId;

    private String totalSixes;



    private String total100;

    private String totalInnings;

    private String highestScoreAgainstTeamId;

    private String total50;


    private String highestScore;

    private String totalFours;

    private String totalBalls;

    private String highestScoreLocation;

    private String highestScoreAgainstTeamName;

    private String highestScoreAgainstPrfile;

    private String strikeRate;



    private String highestScoreAgainstAvatarId;

    public String getHighScorMatchId ()
    {
        return highScorMatchId;
    }

    public void setHighScorMatchId (String highScorMatchId)
    {
        this.highScorMatchId = highScorMatchId;
    }

    public String getTotalSixes ()
    {
        return totalSixes;
    }

    public void setTotalSixes (String totalSixes)
    {
        this.totalSixes = totalSixes;
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

    public String getTotal100 ()
    {
        return total100;
    }

    public void setTotal100 (String total100)
    {
        this.total100 = total100;
    }

    public String getTotalInnings ()
    {
        return totalInnings;
    }

    public void setTotalInnings (String totalInnings)
    {
        this.totalInnings = totalInnings;
    }

    public String getHighestScoreAgainstTeamId ()
    {
        return highestScoreAgainstTeamId;
    }

    public void setHighestScoreAgainstTeamId (String highestScoreAgainstTeamId)
    {
        this.highestScoreAgainstTeamId = highestScoreAgainstTeamId;
    }

    public String getTotal50 ()
    {
        return total50;
    }

    public void setTotal50 (String total50)
    {
        this.total50 = total50;
    }

    public String getTeamProfile ()
    {
        return teamProfile;
    }

    public void setTeamProfile (String teamProfile)
    {
        this.teamProfile = teamProfile;
    }

    public String getTeamId ()
    {
        return teamId;
    }

    public void setTeamId (String teamId)
    {
        this.teamId = teamId;
    }

    public String getHighestScore ()
    {
        return highestScore;
    }

    public void setHighestScore (String highestScore)
    {
        this.highestScore = highestScore;
    }

    public String getTotalFours ()
    {
        return totalFours;
    }

    public void setTotalFours (String totalFours)
    {
        this.totalFours = totalFours;
    }

    public String getTotalBalls ()
    {
        return totalBalls;
    }

    public void setTotalBalls (String totalBalls)
    {
        this.totalBalls = totalBalls;
    }

    public String getHighestScoreLocation ()
    {
        return highestScoreLocation;
    }

    public void setHighestScoreLocation (String highestScoreLocation)
    {
        this.highestScoreLocation = highestScoreLocation;
    }

    public String getHighestScoreAgainstTeamName ()
    {
        return highestScoreAgainstTeamName;
    }

    public void setHighestScoreAgainstTeamName (String highestScoreAgainstTeamName)
    {
        this.highestScoreAgainstTeamName = highestScoreAgainstTeamName;
    }

    public String getHighestScoreAgainstPrfile ()
    {
        return highestScoreAgainstPrfile;
    }

    public void setHighestScoreAgainstPrfile (String highestScoreAgainstPrfile)
    {
        this.highestScoreAgainstPrfile = highestScoreAgainstPrfile;
    }

    public String getStrikeRate ()
    {
        return strikeRate;
    }

    public void setStrikeRate (String strikeRate)
    {
        this.strikeRate = strikeRate;
    }





    public String getHighestScoreAgainstAvatarId ()
    {
        return highestScoreAgainstAvatarId;
    }

    public void setHighestScoreAgainstAvatarId (String highestScoreAgainstAvatarId)
    {
        this.highestScoreAgainstAvatarId = highestScoreAgainstAvatarId;
    }


    private String status;

    private String location;

    private String runConceded;


    private String oppositionTeamId;

    private String wicketTaken;

    private String eventID;

    private String matchDate;

    private String ballPlayed;

    private String TeamId;

    private String matchId;

    private String oppositionTeamName;

    private String oppositionTeamProfile;

    private String oppositionTeamAvatarId;

    private String runScored;



    public String getBatsmanId ()
    {
        return batsmanId;
    }

    public void setBatsmanId (String batsmanId)
    {
        this.batsmanId = batsmanId;
    }



    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getRunConceded ()
    {
        return runConceded;
    }

    public void setRunConceded (String runConceded)
    {
        this.runConceded = runConceded;
    }



    public String getOppositionTeamId ()
    {
        return oppositionTeamId;
    }

    public void setOppositionTeamId (String oppositionTeamId)
    {
        this.oppositionTeamId = oppositionTeamId;
    }

    public String getWicketTaken ()
    {
        return wicketTaken;
    }

    public void setWicketTaken (String wicketTaken)
    {
        this.wicketTaken = wicketTaken;
    }

    public String getEventID ()
    {
        return eventID;
    }

    public void setEventID (String eventID)
    {
        this.eventID = eventID;
    }

    public String getMatchDate ()
    {
        return matchDate;
    }

    public void setMatchDate (String matchDate)
    {
        this.matchDate = matchDate;
    }

    public String getBallPlayed ()
    {
        return ballPlayed;
    }

    public void setBallPlayed (String ballPlayed)
    {
        this.ballPlayed = ballPlayed;
    }



    public String getMatchId ()
    {
        return matchId;
    }

    public void setMatchId (String matchId)
    {
        this.matchId = matchId;
    }

    public String getOppositionTeamName ()
    {
        return oppositionTeamName;
    }

    public void setOppositionTeamName (String oppositionTeamName)
    {
        this.oppositionTeamName = oppositionTeamName;
    }

    public String getOppositionTeamProfile ()
    {
        return oppositionTeamProfile;
    }

    public void setOppositionTeamProfile (String oppositionTeamProfile)
    {
        this.oppositionTeamProfile = oppositionTeamProfile;
    }

    public String getOppositionTeamAvatarId ()
    {
        return oppositionTeamAvatarId;
    }

    public void setOppositionTeamAvatarId (String oppositionTeamAvatarId)
    {
        this.oppositionTeamAvatarId = oppositionTeamAvatarId;
    }

    public String getRunScored ()
    {
        return runScored;
    }

    public void setRunScored (String runScored)
    {
        this.runScored = runScored;
    }

    private String avatarName;

    private String avatarprofilePicture;

    private String age;

    private String userId;

    private String gender;

    private String batsmanFullName;

    private String totalStumping;

    public String getDateOfBirth ()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth (String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserProfilePicture ()
    {
        return userProfilePicture;
    }

    public void setUserProfilePicture (String userProfilePicture)
    {
        this.userProfilePicture = userProfilePicture;
    }

    public String getAvatarId ()
    {
        return avatarId;
    }

    public void setAvatarId (String avatarId)
    {
        this.avatarId = avatarId;
    }



    public String getTotalMatch ()
    {
        return totalMatch;
    }

    public void setTotalMatch (String totalMatch)
    {
        this.totalMatch = totalMatch;
    }

    public String getTotalCatches ()
    {
        return totalCatches;
    }

    public void setTotalCatches (String totalCatches)
    {
        this.totalCatches = totalCatches;
    }



    public String getAvatarName ()
    {
        return avatarName;
    }

    public void setAvatarName (String avatarName)
    {
        this.avatarName = avatarName;
    }

    public String getAvatarprofilePicture ()
    {
        return avatarprofilePicture;
    }

    public void setAvatarprofilePicture (String avatarprofilePicture)
    {
        this.avatarprofilePicture = avatarprofilePicture;
    }

    public String getAge ()
    {
        return age;
    }

    public void setAge (String age)
    {
        this.age = age;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getBatsmanFullName ()
    {
        return batsmanFullName;
    }

    public void setBatsmanFullName (String batsmanFullName)
    {
        this.batsmanFullName = batsmanFullName;
    }

    public String getTotalStumping ()
    {
        return totalStumping;
    }

    public void setTotalStumping (String totalStumping)
    {
        this.totalStumping = totalStumping;
    }
    private String teamName;

    private String teamAvatarId;

    private String totalOvers;

    private String bestAgainstLocation;

    private String strikrate;

    private String bestAgainstTeamName;

    private String totalWickets;

    private String totalInning;

    private String teamProfile;

    private String teamId;

    private String bestAgainstTeamProfile;

    private String fiveWickets;

    private String bestAgainstTeamId;

    private String totalExtraRuns;

    private String best;

    private String totalMaidenOver;

    private String economyRate;

    private String avg;

    private String bestAgainstAvatarId;

    private String totalRuns;


    public String getTotalOvers ()
    {
        return totalOvers;
    }

    public void setTotalOvers (String totalOvers)
    {
        this.totalOvers = totalOvers;
    }

    public String getBestAgainstLocation ()
    {
        return bestAgainstLocation;
    }

    public void setBestAgainstLocation (String bestAgainstLocation)
    {
        this.bestAgainstLocation = bestAgainstLocation;
    }

    public String getStrikrate ()
    {
        return strikrate;
    }

    public void setStrikrate (String strikrate)
    {
        this.strikrate = strikrate;
    }

    public String getBestAgainstTeamName ()
    {
        return bestAgainstTeamName;
    }

    public void setBestAgainstTeamName (String bestAgainstTeamName)
    {
        this.bestAgainstTeamName = bestAgainstTeamName;
    }

    public String getTotalWickets ()
    {
        return totalWickets;
    }

    public void setTotalWickets (String totalWickets)
    {
        this.totalWickets = totalWickets;
    }

    public String getTotalInning ()
    {
        return totalInning;
    }

    public void setTotalInning (String totalInning)
    {
        this.totalInning = totalInning;
    }


    public String getBestAgainstTeamProfile ()
    {
        return bestAgainstTeamProfile;
    }

    public void setBestAgainstTeamProfile (String bestAgainstTeamProfile)
    {
        this.bestAgainstTeamProfile = bestAgainstTeamProfile;
    }

    public String getFiveWickets ()
    {
        return fiveWickets;
    }

    public void setFiveWickets (String fiveWickets)
    {
        this.fiveWickets = fiveWickets;
    }

    public String getBestAgainstTeamId ()
    {
        return bestAgainstTeamId;
    }

    public void setBestAgainstTeamId (String bestAgainstTeamId)
    {
        this.bestAgainstTeamId = bestAgainstTeamId;
    }

    public String getTotalExtraRuns ()
    {
        return totalExtraRuns;
    }

    public void setTotalExtraRuns (String totalExtraRuns)
    {
        this.totalExtraRuns = totalExtraRuns;
    }

    public String getBest ()
    {
        return best;
    }

    public void setBest (String best)
    {
        this.best = best;
    }

    public String getTotalMaidenOver ()
    {
        return totalMaidenOver;
    }

    public void setTotalMaidenOver (String totalMaidenOver)
    {
        this.totalMaidenOver = totalMaidenOver;
    }

    public String getEconomyRate ()
    {
        return economyRate;
    }

    public void setEconomyRate (String economyRate)
    {
        this.economyRate = economyRate;
    }

    public String getAvg ()
    {
        return avg;
    }

    public void setAvg (String avg)
    {
        this.avg = avg;
    }

    public String getBestAgainstAvatarId ()
    {
        return bestAgainstAvatarId;
    }

    public void setBestAgainstAvatarId (String bestAgainstAvatarId)
    {
        this.bestAgainstAvatarId = bestAgainstAvatarId;
    }

    public String getTotalRuns ()
    {
        return totalRuns;
    }

    public void setTotalRuns (String totalRuns)
    {
        this.totalRuns = totalRuns;
    }

}
