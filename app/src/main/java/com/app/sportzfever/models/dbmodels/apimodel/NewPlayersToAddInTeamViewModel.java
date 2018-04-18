package com.app.sportzfever.models.dbmodels.apimodel;

public class NewPlayersToAddInTeamViewModel {

    private String  playerName;
    private String avatarId;
    private String  avatarName;
    private String  email ;
    private String  shouldAddInRoster;
    private String  isInPlayingSquad ;
    private String isInBench ;
    private String  role;
    private String  order ;
    private String profilePicture;
    private String inviteStatus ;


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShouldAddInRoster() {
        return shouldAddInRoster;
    }

    public void setShouldAddInRoster(String shouldAddInRoster) {
        this.shouldAddInRoster = shouldAddInRoster;
    }

    public String getIsInPlayingSquad() {
        return isInPlayingSquad;
    }

    public void setIsInPlayingSquad(String isInPlayingSquad) {
        this.isInPlayingSquad = isInPlayingSquad;
    }

    public String getIsInBench() {
        return isInBench;
    }

    public void setIsInBench(String isInBench) {
        this.isInBench = isInBench;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }
}