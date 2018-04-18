package com.app.sportzfever.models.dbmodels.apimodel;

public class Lineup {

    private String  avatarId;
    private String  userId;
    private String  avatarName;
    private String  playerName;
    private String  playerProfilePicture;
    private String  inviteStatus;
    private String  teamId;
    private String  matchId;
    private String  order;
    private String  speciality;
    private String  isInPlayingSquad;
    private String  isInPlayingBench;
    public String getAvatarId() {
        return avatarId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getPlayerProfilePicture() {
        return playerProfilePicture;
    }

    public void setPlayerProfilePicture(String playerProfilePicture) {
        this.playerProfilePicture = playerProfilePicture;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
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
}
