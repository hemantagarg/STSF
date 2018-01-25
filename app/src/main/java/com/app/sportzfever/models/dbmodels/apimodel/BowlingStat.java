package com.app.sportzfever.models.dbmodels.apimodel;

/**
 * Created by Admin on 1/23/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BowlingStat {

    private String maiden;
    private String numberOfOvers;
    private String bowlerId;
    private String name;
    private String runs;
    private String wickets;
    private String extras;
    private String economy;
    private String totalNoBall;
    private String totalWideBall;
    private String totalDotBall;

    public String getMaiden() {
        return maiden;
    }

    public void setMaiden(String maiden) {
        this.maiden = maiden;
    }

    public String getNumberOfOvers() {
        return numberOfOvers;
    }

    public void setNumberOfOvers(String numberOfOvers) {
        this.numberOfOvers = numberOfOvers;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getWickets() {
        return wickets;
    }

    public void setWickets(String wickets) {
        this.wickets = wickets;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    public String getEconomy() {
        return economy;
    }

    public void setEconomy(String economy) {
        this.economy = economy;
    }

    public String getTotalNoBall() {
        return totalNoBall;
    }

    public void setTotalNoBall(String totalNoBall) {
        this.totalNoBall = totalNoBall;
    }

    public String getTotalWideBall() {
        return totalWideBall;
    }

    public void setTotalWideBall(String totalWideBall) {
        this.totalWideBall = totalWideBall;
    }

    public String getTotalDotBall() {
        return totalDotBall;
    }

    public void setTotalDotBall(String totalDotBall) {
        this.totalDotBall = totalDotBall;
    }

}