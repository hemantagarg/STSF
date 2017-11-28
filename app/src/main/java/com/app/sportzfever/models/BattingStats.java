package com.app.sportzfever.models;

/**
 * Created by hemanta on 25-11-2017.
 */

public class BattingStats {

    private String batsmanId;

    private String runoutByUserFullName;

    private String batsmanAvatarName;

    private String inningId;

    private String balls;

    private String playOrder;

    private String batsmanAvatarUrl;

    private String strikeRate;

    private String fours;

    private String onStrike;

    private String status;

    private String dotball;

    private String sixes;

    private String outString;

    private String matchId;

    private String runs;

    private String runoutByAvatarPic;
    private int rowType = 1;

    public String getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(String batsmanId) {
        this.batsmanId = batsmanId;
    }


    public String getRunoutByUserFullName() {
        return runoutByUserFullName;
    }

    public void setRunoutByUserFullName(String runoutByUserFullName) {
        this.runoutByUserFullName = runoutByUserFullName;
    }

    public String getBatsmanAvatarName() {
        return batsmanAvatarName;
    }

    public void setBatsmanAvatarName(String batsmanAvatarName) {
        this.batsmanAvatarName = batsmanAvatarName;
    }


    public String getInningId() {
        return inningId;
    }

    public void setInningId(String inningId) {
        this.inningId = inningId;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getPlayOrder() {
        return playOrder;
    }

    public void setPlayOrder(String playOrder) {
        this.playOrder = playOrder;
    }

    public String getBatsmanAvatarUrl() {
        return batsmanAvatarUrl;
    }

    public void setBatsmanAvatarUrl(String batsmanAvatarUrl) {
        this.batsmanAvatarUrl = batsmanAvatarUrl;
    }

    public String getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(String strikeRate) {
        this.strikeRate = strikeRate;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getOnStrike() {
        return onStrike;
    }

    public void setOnStrike(String onStrike) {
        this.onStrike = onStrike;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDotball() {
        return dotball;
    }

    public void setDotball(String dotball) {
        this.dotball = dotball;
    }

    public String getSixes() {
        return sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = sixes;
    }

    public String getOutString() {
        return outString;
    }

    public void setOutString(String outString) {
        this.outString = outString;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getRunoutByAvatarPic() {
        return runoutByAvatarPic;
    }

    public void setRunoutByAvatarPic(String runoutByAvatarPic) {
        this.runoutByAvatarPic = runoutByAvatarPic;
    }

    public int getRowType() {
        return rowType;
    }
}
