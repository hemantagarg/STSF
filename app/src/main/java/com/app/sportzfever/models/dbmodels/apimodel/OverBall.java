package com.app.sportzfever.models.dbmodels.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OverBall {

    private int id;
    private String inningOverCount;
    private String isWicket;
    private String batsmanId;
    private String bowlerId;
    private String bowlingString;
    private String runScored;
    private String overString;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInningOverCount() {
        return inningOverCount;
    }

    public void setInningOverCount(String inningOverCount) {
        this.inningOverCount = inningOverCount;
    }

    public String getIsWicket() {
        return isWicket;
    }

    public void setIsWicket(String isWicket) {
        this.isWicket = isWicket;
    }

    public String getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(String batsmanId) {
        this.batsmanId = batsmanId;
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

}