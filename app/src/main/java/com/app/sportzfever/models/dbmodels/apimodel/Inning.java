package com.app.sportzfever.models.dbmodels.apimodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Inning {

    private int id;
    private String inningNumber;
    private String totalRunsScored;
    private String isDeclared;
    private String extras;
    private String playing;
    private String day;
    private String daySession;
    private String totalOvers;
    private String playedOvers;
    private String matchId;
    private String battingTeamId;
    private String bowlingTeamId;
    private String state;
    private String isScoredOnSF;
    private String wickets;
    private String battingTeamAvatarId;
    private String bowlingTeamAvatarId;
    private String battingTeamName;
    private String bowlingTeamName;
    private String battingTeamProfilePic;
    private String bowlingTeamIdProfilePic;
    private String currentOverId;
    private String drinksBreak;
    private List<OverBall> overBalls = null;
    private String batsmanOnStrike;
    private String batsmanOnNonStrike;
    private String previousBowlerId;
    private String currentBowlerId;
    private String inningScoreString;
    private String wicketFallNumber;
    private String overRate;
    private List<BattingStat> battingStats = null;
    private List<BowlingStat> bowlingStats = null;
    private List<String> fow = null;
    private ExtraRuns extraRuns;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInningNumber() {
        return inningNumber;
    }

    public void setInningNumber(String inningNumber) {
        this.inningNumber = inningNumber;
    }

    public String getTotalRunsScored() {
        return totalRunsScored;
    }

    public void setTotalRunsScored(String totalRunsScored) {
        this.totalRunsScored = totalRunsScored;
    }

    public String getIsDeclared() {
        return isDeclared;
    }

    public void setIsDeclared(String isDeclared) {
        this.isDeclared = isDeclared;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getPlaying() {
        return playing;
    }

    public void setPlaying(String playing) {
        this.playing = playing;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDaySession() {
        return daySession;
    }

    public void setDaySession(String daySession) {
        this.daySession = daySession;
    }

    public String getTotalOvers() {
        return totalOvers;
    }

    public void setTotalOvers(String totalOvers) {
        this.totalOvers = totalOvers;
    }

    public String getPlayedOvers() {
        return playedOvers;
    }

    public void setPlayedOvers(String playedOvers) {
        this.playedOvers = playedOvers;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getBattingTeamId() {
        return battingTeamId;
    }

    public void setBattingTeamId(String battingTeamId) {
        this.battingTeamId = battingTeamId;
    }

    public String getBowlingTeamId() {
        return bowlingTeamId;
    }

    public void setBowlingTeamId(String bowlingTeamId) {
        this.bowlingTeamId = bowlingTeamId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIsScoredOnSF() {
        return isScoredOnSF;
    }

    public void setIsScoredOnSF(String isScoredOnSF) {
        this.isScoredOnSF = isScoredOnSF;
    }

    public String getWickets() {
        return wickets;
    }

    public void setWickets(String wickets) {
        this.wickets = wickets;
    }

    public String getBattingTeamAvatarId() {
        return battingTeamAvatarId;
    }

    public void setBattingTeamAvatarId(String battingTeamAvatarId) {
        this.battingTeamAvatarId = battingTeamAvatarId;
    }

    public String getBowlingTeamAvatarId() {
        return bowlingTeamAvatarId;
    }

    public void setBowlingTeamAvatarId(String bowlingTeamAvatarId) {
        this.bowlingTeamAvatarId = bowlingTeamAvatarId;
    }

    public String getBattingTeamName() {
        return battingTeamName;
    }

    public void setBattingTeamName(String battingTeamName) {
        this.battingTeamName = battingTeamName;
    }

    public String getBowlingTeamName() {
        return bowlingTeamName;
    }

    public void setBowlingTeamName(String bowlingTeamName) {
        this.bowlingTeamName = bowlingTeamName;
    }

    public String getBattingTeamProfilePic() {
        return battingTeamProfilePic;
    }

    public void setBattingTeamProfilePic(String battingTeamProfilePic) {
        this.battingTeamProfilePic = battingTeamProfilePic;
    }

    public String getBowlingTeamIdProfilePic() {
        return bowlingTeamIdProfilePic;
    }

    public void setBowlingTeamIdProfilePic(String bowlingTeamIdProfilePic) {
        this.bowlingTeamIdProfilePic = bowlingTeamIdProfilePic;
    }

    public String getCurrentOverId() {
        return currentOverId;
    }

    public void setCurrentOverId(String currentOverId) {
        this.currentOverId = currentOverId;
    }

    public String getDrinksBreak() {
        return drinksBreak;
    }

    public void setDrinksBreak(String drinksBreak) {
        this.drinksBreak = drinksBreak;
    }

    public List<OverBall> getOverBalls() {
        return overBalls;
    }

    public void setOverBalls(List<OverBall> overBalls) {
        this.overBalls = overBalls;
    }

    public String getBatsmanOnStrike() {
        return batsmanOnStrike;
    }

    public void setBatsmanOnStrike(String batsmanOnStrike) {
        this.batsmanOnStrike = batsmanOnStrike;
    }

    public String getBatsmanOnNonStrike() {
        return batsmanOnNonStrike;
    }

    public void setBatsmanOnNonStrike(String batsmanOnNonStrike) {
        this.batsmanOnNonStrike = batsmanOnNonStrike;
    }

    public String getPreviousBowlerId() {
        return previousBowlerId;
    }

    public void setPreviousBowlerId(String previousBowlerId) {
        this.previousBowlerId = previousBowlerId;
    }

    public String getCurrentBowlerId() {
        return currentBowlerId;
    }

    public void setCurrentBowlerId(String currentBowlerId) {
        this.currentBowlerId = currentBowlerId;
    }

    public String getInningScoreString() {
        return inningScoreString;
    }

    public void setInningScoreString(String inningScoreString) {
        this.inningScoreString = inningScoreString;
    }

    public String getWicketFallNumber() {
        return wicketFallNumber;
    }

    public void setWicketFallNumber(String wicketFallNumber) {
        this.wicketFallNumber = wicketFallNumber;
    }

    public String getOverRate() {
        return overRate;
    }

    public void setOverRate(String overRate) {
        this.overRate = overRate;
    }

    public List<BattingStat> getBattingStats() {
        return battingStats;
    }

    public void setBattingStats(List<BattingStat> battingStats) {
        this.battingStats = battingStats;
    }

    public List<BowlingStat> getBowlingStats() {
        return bowlingStats;
    }

    public void setBowlingStats(List<BowlingStat> bowlingStats) {
        this.bowlingStats = bowlingStats;
    }

    public List<String> getFow() {
        return fow;
    }

    public void setFow(List<String> fow) {
        this.fow = fow;
    }

    public ExtraRuns getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(ExtraRuns extraRuns) {
        this.extraRuns = extraRuns;
    }

}