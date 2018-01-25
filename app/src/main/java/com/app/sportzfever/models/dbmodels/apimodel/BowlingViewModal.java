package com.app.sportzfever.models.dbmodels.apimodel;

/**
 * Created by Admin on 1/23/2018.
 */

public class BowlingViewModal {

    private String maiden;
    private String overNumber;
    private String numberOfOvers;
    private String bowlerId;
    private String bowlerAvatarName;
    private String runs;
    private String wickets;
    private String extras;
    private String economy;

    public String getMaiden()
    {
        return maiden;
    }

    public void setMaiden(String maiden) {
        this.maiden = maiden;
    }

    public String getOverNumber() {
        return overNumber;
    }

    public void setOverNumber(String overNumber) {
        this.overNumber = overNumber;
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

    public String getBowlerAvatarName() {
        return bowlerAvatarName;
    }

    public void setBowlerAvatarName(String bowlerAvatarName) {
        this.bowlerAvatarName = bowlerAvatarName;
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
}