package com.app.sportzfever.models;

/**
 * Created by hemanta on 26-11-2017.
 */

public class ModelRecentBall {

    private String batsmanId;

    private String id;

    private String inningOverCount;

    private String bowlerId;

    private String bowlingString;

    private String isWicket;

    private String runScored;

    private String overString;
    private int rowType = 1;

    public String getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(String batsmanId) {
        this.batsmanId = batsmanId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInningOverCount() {
        return inningOverCount;
    }

    public void setInningOverCount(String inningOverCount) {
        this.inningOverCount = inningOverCount;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

    public String getBowlingString() {
        return bowlingString;
    }

    public void setBowlingString(String bowlingString) {
        this.bowlingString = bowlingString;
    }

    public String getIsWicket() {
        return isWicket;
    }

    public void setIsWicket(String isWicket) {
        this.isWicket = isWicket;
    }

    public String getRunScored() {
        return runScored;
    }

    public void setRunScored(String runScored) {
        this.runScored = runScored;
    }

    public String getOverString() {
        return overString;
    }

    public void setOverString(String overString) {
        this.overString = overString;
    }

    public int getRowType() {
        return rowType;
    }
}
