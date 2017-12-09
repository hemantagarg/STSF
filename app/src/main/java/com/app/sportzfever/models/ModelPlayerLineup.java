package com.app.sportzfever.models;

/**
 * Created by hemanta on 09-12-2017.
 */

public class ModelPlayerLineup {

    private String avatarId, playerName, playerProfilePicture, inviteStatus, matchId, teamId, order, isInPlayingSquad, isInPlayingBench;

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
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

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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
}
