package com.app.sportzfever.models;

/**
 * Created by hemanta on 01-09-2017.
 */

public class ModelSportTeamList {


    private String date;

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;

    private String teamName, userId, noOfGroup, noOfTeam, type;

    private String ownerName;

    private String teamAvatarId, wickets, best, economy, totalOvers, bowlingAvg;

    private String location, matchId, order, isInPlayingSquad = "0", isInPlayingBench = "0";

    private String ownerId;
    private int isAdded = 0;

    private String sportName;

    private String requestStatus, addedStatus = "Invitation not sent";

    private String jerseyNumber;

    private String avatarName;

    private String playerName = "";

    private String speciality = "";

    private String profilePicture = "";

    private String avatar, avtarId, isReservedPlayer = "0";

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(String jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getIsInPlayingSquad() {
        return isInPlayingSquad;
    }

    public void setIsInPlayingSquad(String isInPlayingSquad) {
        this.isInPlayingSquad = isInPlayingSquad;
    }

    public String getIsInPlayingBench() {
        return isInPlayingBench;
    }

    public void setIsInPlayingBench(String isInPlayingBench) {
        this.isInPlayingBench = isInPlayingBench;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }


    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String teamId = "";
    private boolean isNewPlayer;
    private String totalPlayersInTeam;

    private String ownerPic;

    private String isActive;

    private String captainName;

    private String captainId;

    private String description;

    private String captainPic;

    private String fansCount;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getTeamAvatarId() {
        return teamAvatarId;
    }

    public void setTeamAvatarId(String teamAvatarId) {
        this.teamAvatarId = teamAvatarId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }


    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTotalPlayersInTeam() {
        return totalPlayersInTeam;
    }

    public void setTotalPlayersInTeam(String totalPlayersInTeam) {
        this.totalPlayersInTeam = totalPlayersInTeam;
    }

    public String getOwnerPic() {
        return ownerPic;
    }

    public void setOwnerPic(String ownerPic) {
        this.ownerPic = ownerPic;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getCaptainId() {
        return captainId;
    }

    public void setCaptainId(String captainId) {
        this.captainId = captainId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaptainPic() {
        return captainPic;
    }

    public void setCaptainPic(String captainPic) {
        this.captainPic = captainPic;
    }

    public String getFansCount() {
        return fansCount;
    }

    public void setFansCount(String fansCount) {
        this.fansCount = fansCount;
    }

    public int getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(int isAdded) {
        this.isAdded = isAdded;
    }

    public String getAddedStatus() {
        return addedStatus;
    }

    public void setAddedStatus(String addedStatus) {
        this.addedStatus = addedStatus;
    }

    public String getAvtarId() {
        return avtarId;
    }

    public void setAvtarId(String avtarId) {
        this.avtarId = avtarId;
    }

    public String getIsReservedPlayer() {
        return isReservedPlayer;
    }

    public void setIsReservedPlayer(String isReservedPlayer) {
        this.isReservedPlayer = isReservedPlayer;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isNewPlayer() {
        return isNewPlayer;
    }

    public void setNewPlayer(boolean newPlayer) {
        isNewPlayer = newPlayer;
    }


    private String batsmanAvatarPic;

    private String battingAvg;

    private String totalMatch;

    private String totalInning;

    private String sixes;

    private String highestScore;

    private String bastsmanAvatarName;

    private String id;

    private String balls;

    private String strikeRate;

    private String fours;

    private String runs;

    public String getBatsmanAvatarPic() {
        return batsmanAvatarPic;
    }

    public void setBatsmanAvatarPic(String batsmanAvatarPic) {
        this.batsmanAvatarPic = batsmanAvatarPic;
    }

    public String getBattingAvg() {
        return battingAvg;
    }

    public void setBattingAvg(String battingAvg) {
        this.battingAvg = battingAvg;
    }

    public String getTotalMatch() {
        return totalMatch;
    }

    public void setTotalMatch(String totalMatch) {
        this.totalMatch = totalMatch;
    }

    public String getTotalInning() {
        return totalInning;
    }

    public void setTotalInning(String totalInning) {
        this.totalInning = totalInning;
    }

    public String getSixes() {
        return sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = sixes;
    }

    public String getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(String highestScore) {
        this.highestScore = highestScore;
    }

    public String getBastsmanAvatarName() {
        return bastsmanAvatarName;
    }

    public void setBastsmanAvatarName(String bastsmanAvatarName) {
        this.bastsmanAvatarName = bastsmanAvatarName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(String strikeRate) {
        this.strikeRate = strikeRate;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getNoOfGroup() {
        return noOfGroup;
    }

    public void setNoOfGroup(String noOfGroup) {
        this.noOfGroup = noOfGroup;
    }

    public String getNoOfTeam() {
        return noOfTeam;
    }

    public void setNoOfTeam(String noOfTeam) {
        this.noOfTeam = noOfTeam;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWickets() {
        return wickets;
    }

    public void setWickets(String wickets) {
        this.wickets = wickets;
    }

    public String getBest() {
        return best;
    }

    public void setBest(String best) {
        this.best = best;
    }

    public String getEconomy() {
        return economy;
    }

    public void setEconomy(String economy) {
        this.economy = economy;
    }

    public String getTotalOvers() {
        return totalOvers;
    }

    public void setTotalOvers(String totalOvers) {
        this.totalOvers = totalOvers;
    }

    public String getBowlingAvg() {
        return bowlingAvg;
    }

    public void setBowlingAvg(String bowlingAvg) {
        this.bowlingAvg = bowlingAvg;
    }
}
