package com.app.sportzfever.models.dbmodels;

public class OtherMatchDetail {

    private int id ;
    private String eventId ;
    private String team1Id ;
    private String team2Id ;
    private String startDate ;
    private String team1Name ;
    private String team2Name ;
    private String team1AvatarId ;
    private String team2AvatarId ;
    private String team1ProfilePicture ;
    private String team2ProfilePicture ;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getTeam1AvatarId() {
        return team1AvatarId;
    }

    public void setTeam1AvatarId(String team1AvatarId) {
        this.team1AvatarId = team1AvatarId;
    }

    public String getTeam2AvatarId() {
        return team2AvatarId;
    }

    public void setTeam2AvatarId(String team2AvatarId) {
        this.team2AvatarId = team2AvatarId;
    }

    public String getTeam1ProfilePicture() {
        return team1ProfilePicture;
    }

    public void setTeam1ProfilePicture(String team1ProfilePicture) {
        this.team1ProfilePicture = team1ProfilePicture;
    }

    public String getTeam2ProfilePicture() {
        return team2ProfilePicture;
    }

    public void setTeam2ProfilePicture(String team2ProfilePicture) {
        this.team2ProfilePicture = team2ProfilePicture;
    }
}
