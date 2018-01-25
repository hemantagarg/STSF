package com.app.sportzfever.models.dbmodels;

public class Roster {
    private int  id;
    private String  readStatus;
    private String  requestRespondedAt;
    private String  requestSentAt;
    private String  avatar;
    private String  playerOrder;
    private String  team;

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

    public String getRequestRespondedAt() {
        return requestRespondedAt;
    }

    public void setRequestRespondedAt(String requestRespondedAt) {
        this.requestRespondedAt = requestRespondedAt;
    }

    public String getRequestSentAt() {
        return requestSentAt;
    }

    public void setRequestSentAt(String requestSentAt) {
        this.requestSentAt = requestSentAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPlayerOrder() {
        return playerOrder;
    }

    public void setPlayerOrder(String playerOrder) {
        this.playerOrder = playerOrder;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    private String  requestStatus;



    public Roster(String  readStatus,String  requestRespondedAt,String  requestSentAt,String  avatar,String  playerOrder,String  team,String  requestStatus)
    {
        this.readStatus=readStatus;
        this.requestRespondedAt=requestRespondedAt;
        this.requestSentAt=requestSentAt;
        this.avatar=avatar;
        this.playerOrder=playerOrder;
        this.team=team;
        this.requestStatus=requestStatus;
    }

}
