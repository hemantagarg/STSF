package com.app.sportzfever.models.dbmodels.apimodel;

public class MatchScorerModel {

    private String scorerId;
    private String scorerOrder;
    private String matchId;
    private String inviteStatus;
    private String team;
    private String scorerName;
    private String userProfilePicture;

    public String getScorerId() {
        return scorerId;
    }

    public void setScorerId(String scorerId) {
        this.scorerId = scorerId;
    }

    public String getScorerOrder() {
        return scorerOrder;
    }

    public void setScorerOrder(String scorerOrder) {
        this.scorerOrder = scorerOrder;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getScorerName() {
        return scorerName;
    }

    public void setScorerName(String scorerName) {
        this.scorerName = scorerName;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }
}