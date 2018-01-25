package com.app.sportzfever.models.dbmodels;

import java.io.StringReader;

public class CricketInning {
   private int id;
   private String totalOvers;
   private String wickets;
   private String isDeclared;
   private String bowlingTeamId;
   private String isScoredOnSF;
   private String matchId;
   private String inningNumber;
   private String playing;
   private String daySession;
   private String totalRunsScored;
   private String state;
   private String extras;
   private String playedOvers;
   private String battingTeamId;
   private String day;
    public CricketInning(String totalOvers , String wickets, String isDeclared, String bowlingTeamId, String isScoredOnSF , String matchId,
                         String inningNumber , String playing, String daySession , String totalRunsScored ,
                         String state, String extras, String playedOvers , String battingTeamId ,String day)
    {
        this.totalOvers =totalOvers;
        this.wickets=wickets;
        this.isDeclared=isDeclared;
        this.bowlingTeamId =bowlingTeamId;
        this.isScoredOnSF =isScoredOnSF;
        this.matchId =matchId;
        this.inningNumber =inningNumber;
        this.playing =playing;
        this.daySession =daySession;
        this.totalRunsScored =totalRunsScored;
        this.state =state;
        this.extras =extras;
        this.playedOvers=playedOvers;
        this.battingTeamId =battingTeamId;
        this.day =day;
    }
    public String getTotalOvers() {
        return totalOvers;
    }

    public void setTotalOvers(String totalOvers) {
        this.totalOvers = totalOvers;
    }

    public String getWickets() {
        return wickets;
    }

    public void setWickets(String wickets) {
        this.wickets = wickets;
    }

    public String isDeclared() {
        return isDeclared;
    }

    public void setDeclared(String declared) {
        isDeclared = declared;
    }

    public String getBowlingTeamId() {
        return bowlingTeamId;
    }

    public void setBowlingTeamId(String bowlingTeamId) {
        this.bowlingTeamId = bowlingTeamId;
    }

    public String isScoredOnSF() {
        return isScoredOnSF;
    }

    public void setScoredOnSF(String scoredOnSF) {
        isScoredOnSF = scoredOnSF;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getInningNumber() {
        return inningNumber;
    }

    public void setInningNumber(String inningNumber) {
        this.inningNumber = inningNumber;
    }

    public String isPlaying() {
        return playing;
    }

    public void setPlaying(String playing) {
        this.playing = playing;
    }

    public String getDaySession() {
        return daySession;
    }

    public void setDaySession(String daySession) {
        this.daySession = daySession;
    }

    public String getTotalRunsScored() {
        return totalRunsScored;
    }

    public void setTotalRunsScored(String totalRunsScored) {
        this.totalRunsScored = totalRunsScored;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getPlayedOvers() {
        return playedOvers;
    }

    public void setPlayedOvers(String playedOvers) {
        this.playedOvers = playedOvers;
    }

    public String getBattingTeamId() {
        return battingTeamId;
    }

    public void setBattingTeamId(String battingTeamId) {
        this.battingTeamId = battingTeamId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
