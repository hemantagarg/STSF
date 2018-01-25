package com.app.sportzfever.models.dbmodels.apimodel;

/**
 * Created by Admin on 1/23/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BattingStat {

    private String playOrder;
    private String batsmanAvatarName;
    private String status;
    private String onStrike;
    private String runs;
    private String dotball;
    private String balls;
    private String fours;
    private String sixes;
    private String strikeRate;
    private String batsmanId;
    private String outString;

    public String getPlayOrder() {
        return playOrder;
    }

    public void setPlayOrder(String playOrder) {
        this.playOrder = playOrder;
    }

    public String getBatsmanAvatarName() {
        return batsmanAvatarName;
    }

    public void setBatsmanAvatarName(String batsmanAvatarName) {
        this.batsmanAvatarName = batsmanAvatarName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOnStrike() {
        return onStrike;
    }

    public void setOnStrike(String onStrike) {
        this.onStrike = onStrike;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getDotball() {
        return dotball;
    }

    public void setDotball(String dotball) {
        this.dotball = dotball;
    }

    public String getBalls() {
        return balls;
    }

    public void setBalls(String balls) {
        this.balls = balls;
    }

    public String getFours() {
        return fours;
    }

    public void setFours(String fours) {
        this.fours = fours;
    }

    public String getSixes() {
        return sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = sixes;
    }

    public String getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(String strikeRate) {
        this.strikeRate = strikeRate;
    }

    public String getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(String batsmanId) {
        this.batsmanId = batsmanId;
    }

    public String getOutString() {
        return outString;
    }

    public void setOutString(String outString) {
        this.outString = outString;
    }

}
