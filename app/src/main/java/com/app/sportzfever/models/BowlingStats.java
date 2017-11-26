package com.app.sportzfever.models;

/**
 * Created by hemanta on 25-11-2017.
 */

public class BowlingStats {

    private String totalWideBall;

    private String totalNoBall;

    private String economy;

    private String bowlerId;

    private String numberOfOvers;

    private String totalDotBall;

    private String runs;

    private String maiden;

    private String name;
    private String wickets;

    private String extras;
    private int rowType = 1;

    public String getTotalWideBall() {
        return totalWideBall;
    }

    public void setTotalWideBall(String totalWideBall) {
        this.totalWideBall = totalWideBall;
    }

    public String getTotalNoBall() {
        return totalNoBall;
    }

    public void setTotalNoBall(String totalNoBall) {
        this.totalNoBall = totalNoBall;
    }

    public String getEconomy() {
        return economy;
    }

    public void setEconomy(String economy) {
        this.economy = economy;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

    public String getNumberOfOvers() {
        return numberOfOvers;
    }

    public void setNumberOfOvers(String numberOfOvers) {
        this.numberOfOvers = numberOfOvers;
    }

    public String getTotalDotBall() {
        return totalDotBall;
    }

    public void setTotalDotBall(String totalDotBall) {
        this.totalDotBall = totalDotBall;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getMaiden() {
        return maiden;
    }

    public void setMaiden(String maiden) {
        this.maiden = maiden;
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

    public int getRowType() {
        return rowType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
