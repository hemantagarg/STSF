package com.app.sportzfever.models.dbmodels.apimodel;

public class NewMatchLineUpViewModel {

    private String avatarId;
    private String  role ;
    private String  order ;
    private String isInPlayingSquad ;
    private String isInBench ;
    private String isReservedPlayer ;
    private String inviteStatus ;

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
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

    public String getIsReservedPlayer() {
        return isReservedPlayer;
    }

    public void setIsReservedPlayer(String isReservedPlayer) {
        this.isReservedPlayer = isReservedPlayer;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }
}