package com.app.sportzfever.models;

/**
 * Created by hemanta on 25-11-2017.
 */

public class ModelLiveInnings {
    private String totalRunsScored;

    private String isScoredOnSF;

    private String[] fow;

    private String totalOvers;

    private String bowlingTeamName;

    private String isDeclared;

    private String state;

    private BattingStats[] battingStats;

    private String daySession;

    private LastOver lastOver;

    private String battingTeamName;

    private BowlingStats[] bowlingStats;

    private String playing;

    private String id;

    private String battingTeamProfilePic;

    private CurrentOver currentOver;

    private String bowlingTeamAvatarId;

    private ExtraRuns extraRuns;

    private String wickets;

    private String bowlingTeamIdProfilePic;

    private String battingTeamAvatarId;

    private String bowlingTeamId;

    private String battingTeamId;

    private String inningNumber;

    private String extras;

    private String wicketFallNumber;

    private String matchId;

    private String playedOvers;

    private String day;

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType = 1;

    public String getTotalRunsScored() {
        return totalRunsScored;
    }

    public void setTotalRunsScored(String totalRunsScored) {
        this.totalRunsScored = totalRunsScored;
    }

    public String getIsScoredOnSF() {
        return isScoredOnSF;
    }

    public void setIsScoredOnSF(String isScoredOnSF) {
        this.isScoredOnSF = isScoredOnSF;
    }

    public String getTotalOvers() {
        return totalOvers;
    }

    public void setTotalOvers(String totalOvers) {
        this.totalOvers = totalOvers;
    }

    public String getBowlingTeamName() {
        return bowlingTeamName;
    }

    public void setBowlingTeamName(String bowlingTeamName) {
        this.bowlingTeamName = bowlingTeamName;
    }

    public String getIsDeclared() {
        return isDeclared;
    }

    public void setIsDeclared(String isDeclared) {
        this.isDeclared = isDeclared;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getDaySession() {
        return daySession;
    }

    public void setDaySession(String daySession) {
        this.daySession = daySession;
    }

    public String getBattingTeamName() {
        return battingTeamName;
    }

    public void setBattingTeamName(String battingTeamName) {
        this.battingTeamName = battingTeamName;
    }

    public String getPlaying() {
        return playing;
    }

    public void setPlaying(String playing) {
        this.playing = playing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBattingTeamProfilePic() {
        return battingTeamProfilePic;
    }

    public void setBattingTeamProfilePic(String battingTeamProfilePic) {
        this.battingTeamProfilePic = battingTeamProfilePic;
    }

    public String getBowlingTeamAvatarId() {
        return bowlingTeamAvatarId;
    }

    public void setBowlingTeamAvatarId(String bowlingTeamAvatarId) {
        this.bowlingTeamAvatarId = bowlingTeamAvatarId;
    }

    public String getWickets() {
        return wickets;
    }

    public void setWickets(String wickets) {
        this.wickets = wickets;
    }

    public String getBowlingTeamIdProfilePic() {
        return bowlingTeamIdProfilePic;
    }

    public void setBowlingTeamIdProfilePic(String bowlingTeamIdProfilePic) {
        this.bowlingTeamIdProfilePic = bowlingTeamIdProfilePic;
    }

    public String getBattingTeamAvatarId() {
        return battingTeamAvatarId;
    }

    public void setBattingTeamAvatarId(String battingTeamAvatarId) {
        this.battingTeamAvatarId = battingTeamAvatarId;
    }

    public String getBowlingTeamId() {
        return bowlingTeamId;
    }

    public void setBowlingTeamId(String bowlingTeamId) {
        this.bowlingTeamId = bowlingTeamId;
    }

    public String getBattingTeamId() {
        return battingTeamId;
    }

    public void setBattingTeamId(String battingTeamId) {
        this.battingTeamId = battingTeamId;
    }

    public String getInningNumber() {
        return inningNumber;
    }

    public void setInningNumber(String inningNumber) {
        this.inningNumber = inningNumber;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getWicketFallNumber() {
        return wicketFallNumber;
    }

    public void setWicketFallNumber(String wicketFallNumber) {
        this.wicketFallNumber = wicketFallNumber;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getPlayedOvers() {
        return playedOvers;
    }

    public void setPlayedOvers(String playedOvers) {
        this.playedOvers = playedOvers;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public int getRowType() {
        return rowType;
    }

    public BowlingStats[] getBowlingStats() {
        return bowlingStats;
    }

    public void setBowlingStats(BowlingStats[] bowlingStats) {
        this.bowlingStats = bowlingStats;
    }

    public BattingStats[] getBattingStats() {
        return battingStats;
    }

    public void setBattingStats(BattingStats[] battingStats) {
        this.battingStats = battingStats;
    }

    public CurrentOver getCurrentOver() {
        return currentOver;
    }

    public void setCurrentOver(CurrentOver currentOver) {
        this.currentOver = currentOver;
    }

    public ExtraRuns getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(ExtraRuns extraRuns) {
        this.extraRuns = extraRuns;
    }

    public LastOver getLastOver() {
        return lastOver;
    }

    public void setLastOver(LastOver lastOver) {
        this.lastOver = lastOver;
    }

    public String[] getFow() {
        return fow;
    }

    public void setFow(String[] fow) {
        this.fow = fow;
    }
}
