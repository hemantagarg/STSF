package com.app.sportzfever.models;

public class TeamJoinRequest {

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private int teamType;

    private String requestStatus, notificationText;


    private String playerAvatarProfilePicture;

    private String playerAvatarId;

    public String getTeamProfilePic() {
        return teamProfilePic;
    }

    public void setTeamProfilePic(String teamProfilePic) {
        this.teamProfilePic = teamProfilePic;
    }

    private String teamProfilePic;


    private String playerAvatarName;

    private String requestSentAt;
    private String tournamentName;
    private String tournamentProfilePicture;

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentProfilePicture() {
        return tournamentProfilePicture;
    }

    public void setTournamentProfilePicture(String tournamentProfilePicture) {
        this.tournamentProfilePicture = tournamentProfilePicture;
    }

    public String getTournamentStartDate() {
        return tournamentStartDate;
    }

    public void setTournamentStartDate(String tournamentStartDate) {
        this.tournamentStartDate = tournamentStartDate;
    }

    public String getTournamentOrganizerId() {
        return tournamentOrganizerId;
    }

    public void setTournamentOrganizerId(String tournamentOrganizerId) {
        this.tournamentOrganizerId = tournamentOrganizerId;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    private String tournamentStartDate;
    private String tournamentOrganizerId;
    private String tournamentId;


    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamProfilePicture() {
        return teamProfilePicture;
    }

    public void setTeamProfilePicture(String teamProfilePicture) {
        this.teamProfilePicture = teamProfilePicture;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getTeamAvatarId() {
        return teamAvatarId;
    }

    public void setTeamAvatarId(String teamAvatarId) {
        this.teamAvatarId = teamAvatarId;
    }

    public String getPlayerAvatarProfilePicture() {
        return playerAvatarProfilePicture;
    }

    public void setPlayerAvatarProfilePicture(String playerAvatarProfilePicture) {
        this.playerAvatarProfilePicture = playerAvatarProfilePicture;
    }

    public String getPlayerAvatarId() {
        return playerAvatarId;
    }

    public void setPlayerAvatarId(String playerAvatarId) {
        this.playerAvatarId = playerAvatarId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getPlayerAvatarName() {
        return playerAvatarName;
    }

    public void setPlayerAvatarName(String playerAvatarName) {
        this.playerAvatarName = playerAvatarName;
    }

    public String getRequestSentAt() {
        return requestSentAt;
    }

    public void setRequestSentAt(String requestSentAt) {
        this.requestSentAt = requestSentAt;
    }


    private String inviteSentOn;


    private String oppositionTeamProfilePicture;


    public String getInviteSentOn() {
        return inviteSentOn;
    }

    public void setInviteSentOn(String inviteSentOn) {
        this.inviteSentOn = inviteSentOn;
    }


    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getOppositionTeamProfilePicture() {
        return oppositionTeamProfilePicture;
    }

    public void setOppositionTeamProfilePicture(String oppositionTeamProfilePicture) {
        this.oppositionTeamProfilePicture = oppositionTeamProfilePicture;
    }

    public String getOppositionTeamId() {
        return oppositionTeamId;
    }

    public void setOppositionTeamId(String oppositionTeamId) {
        this.oppositionTeamId = oppositionTeamId;
    }


    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getOppositionTeamName() {
        return oppositionTeamName;
    }

    public void setOppositionTeamName(String oppositionTeamName) {
        this.oppositionTeamName = oppositionTeamName;
    }

    public String getOppositionTeamAvatarId() {
        return oppositionTeamAvatarId;
    }

    public void setOppositionTeamAvatarId(String oppositionTeamAvatarId) {
        this.oppositionTeamAvatarId = oppositionTeamAvatarId;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }


    private String matchDate;

    private String oppositionTeamAvatarProfilePicture;

    private String location;

    private String oppositionTeamName;

    private String oppositionTeamAvatarId;

    private String oppositionTeamId;


    public String getOppositionTeamAvatarProfilePicture() {
        return oppositionTeamAvatarProfilePicture;
    }

    public void setOppositionTeamAvatarProfilePicture(String oppositionTeamAvatarProfilePicture) {
        this.oppositionTeamAvatarProfilePicture = oppositionTeamAvatarProfilePicture;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    private String teamName;

    private String id;

    private String teamProfilePicture;

    private String readStatus;

    private String teamAvatarId;

    private String eventId;

    private String avatarId;

    private String matchId;

    private String eventTitle;

    private String eventType;

    private String teamId;

    private String inviteStatus;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }


    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public int getTeamType() {
        return teamType;
    }

    public void setTeamType(int teamType) {
        this.teamType = teamType;
    }
}
