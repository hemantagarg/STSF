package com.app.sportzfever.models.dbmodels;

public class CricketSelectedTeamPlayers {

    private int id;
    private String readStatus;
    private String invitationSendOn;
    private String inviteStatus;
    private String matchId;
    private String isInPlayingBench;
    private String teamId;
    private String role;
    private String isInPlayingSquad;
    private String position;
    private String avatarId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getInvitationSendOn() {
        return invitationSendOn;
    }

    public void setInvitationSendOn(String invitationSendOn) {
        this.invitationSendOn = invitationSendOn;
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

    public String getIsInPlayingBench() {
        return isInPlayingBench;
    }

    public void setIsInPlayingBench(String isInPlayingBench) {
        this.isInPlayingBench = isInPlayingBench;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIsInPlayingSquad() {
        return isInPlayingSquad;
    }

    public void setIsInPlayingSquad(String isInPlayingSquad) {
        this.isInPlayingSquad = isInPlayingSquad;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getInvitationAnsweredOn() {
        return invitationAnsweredOn;
    }

    public void setInvitationAnsweredOn(String invitationAnsweredOn) {
        this.invitationAnsweredOn = invitationAnsweredOn;
    }

    private String invitationAnsweredOn;

    public CricketSelectedTeamPlayers (String readStatus,String invitationSendOn,String inviteStatus,String matchId,String isInPlayingBench,String teamId,String role,String isInPlayingSquad,String position,String avatarId,String id,String invitationAnsweredOn)
    {
        this.readStatus=readStatus;
        this.invitationSendOn=invitationSendOn;
                this.inviteStatus=inviteStatus;
        this.matchId=matchId;
        this.isInPlayingBench=isInPlayingBench;
        this.teamId=teamId;
        this.role=role;
        this.isInPlayingSquad=isInPlayingSquad;
        this.position=position;
        this.avatarId=avatarId;
        this.invitationAnsweredOn=invitationAnsweredOn;
    }
}
