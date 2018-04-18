package com.app.sportzfever.models.dbmodels.apimodel;

public class ExistingPlayersToAddViewModel {

  private String  userId ;
  private String playerName ;
  private String role ;
  private String shouldAddInRoster;
  private String order ;
  private String avatarName;
  private String avatarId ;
  private String email;
  private String isInPlayingSquad ;
  private String isInBench ;
  private String profilePicture;
  private String inviteStatus ;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getShouldAddInRoster() {
        return shouldAddInRoster;
    }

    public void setShouldAddInRoster(String shouldAddInRoster) {
        this.shouldAddInRoster = shouldAddInRoster;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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