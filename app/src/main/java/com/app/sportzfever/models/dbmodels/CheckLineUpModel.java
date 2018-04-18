package com.app.sportzfever.models.dbmodels;

public class CheckLineUpModel
{
    private int id;
    private String eventId;
    private String team1Id;
    private String team2Id;
    private String isTeam1ScoringOnSf;
    private String isTeam2ScoringOnSf;
    private String team1Name;
    private String team2Name;
    private String isLineupCompleteTeam1;
    private String isLineupCompleteTeam2;

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

    public String getIsTeam1ScoringOnSf() {
        return isTeam1ScoringOnSf;
    }

    public void setIsTeam1ScoringOnSf(String isTeam1ScoringOnSf) {
        this.isTeam1ScoringOnSf = isTeam1ScoringOnSf;
    }

    public String getIsTeam2ScoringOnSf() {
        return isTeam2ScoringOnSf;
    }

    public void setIsTeam2ScoringOnSf(String isTeam2ScoringOnSf) {
        this.isTeam2ScoringOnSf = isTeam2ScoringOnSf;
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

    public String getIsLineupCompleteTeam1() {
        return isLineupCompleteTeam1;
    }

    public void setIsLineupCompleteTeam1(String isLineupCompleteTeam1) {
        this.isLineupCompleteTeam1 = isLineupCompleteTeam1;
    }

    public String getIsLineupCompleteTeam2() {
        return isLineupCompleteTeam2;
    }

    public void setIsLineupCompleteTeam2(String isLineupCompleteTeam2) {
        this.isLineupCompleteTeam2 = isLineupCompleteTeam2;
    }
}
