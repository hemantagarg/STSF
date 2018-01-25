package com.app.sportzfever.models.dbmodels;

public class MatchScorer {
    private int id;
    private String  readStatus;
    private String  scorerOrder;
    private String  inviteStatus;
    private String  scorerId;
    private String  matchId;
    private String  inviteSentOn;

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

    public String getScorerOrder() {
        return scorerOrder;
    }

    public void setScorerOrder(String scorerOrder) {
        this.scorerOrder = scorerOrder;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public String getScorerId() {
        return scorerId;
    }

    public void setScorerId(String scorerId) {
        this.scorerId = scorerId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getInviteSentOn() {
        return inviteSentOn;
    }

    public void setInviteSentOn(String inviteSentOn) {
        this.inviteSentOn = inviteSentOn;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    private String  team;



    public MatchScorer(String  readStatus,String  scorerOrder,String  inviteStatus,String  scorerId,String  matchId,String  inviteSentOn,String  team)
    {
        this.readStatus=readStatus;
        this.scorerOrder=scorerOrder;
        this.inviteStatus=inviteStatus;
        this.scorerId=scorerId;
        this.matchId=matchId;
        this.inviteSentOn=inviteSentOn;
        this.team=team;
    }

}
