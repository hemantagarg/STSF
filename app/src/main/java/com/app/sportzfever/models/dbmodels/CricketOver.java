package com.app.sportzfever.models.dbmodels;

public class CricketOver {
    int id ;
    String current ;
    String wicketsInOver ;
    String overNumber ;
    String matchId ;
    String extraRunsInOver ;
    String isMaiden ;
    String runsScoredInOver ;
    String inningId ;
    String bowlerId ;
    public  CricketOver(String current ,String wicketsInOver ,String overNumber ,String matchId ,String extraRunsInOver ,String isMaiden ,String runsScoredInOver ,String inningId ,String bowlerId ){
      this.current =current;
      this.wicketsInOver=wicketsInOver;
      this.overNumber=overNumber;
      this.matchId=matchId;
      this.extraRunsInOver=extraRunsInOver;
      this.isMaiden=isMaiden;
      this.runsScoredInOver=runsScoredInOver;
      this.inningId=inningId;
      this.bowlerId=bowlerId;
    }
    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getWicketsInOver() {
        return wicketsInOver;
    }

    public void setWicketsInOver(String wicketsInOver) {
        this.wicketsInOver = wicketsInOver;
    }

    public String getOverNumber() {
        return overNumber;
    }

    public void setOverNumber(String overNumber) {
        this.overNumber = overNumber;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getExtraRunsInOver() {
        return extraRunsInOver;
    }

    public void setExtraRunsInOver(String extraRunsInOver) {
        this.extraRunsInOver = extraRunsInOver;
    }

    public String getIsMaiden() {
        return isMaiden;
    }

    public void setIsMaiden(String isMaiden) {
        this.isMaiden = isMaiden;
    }

    public String getRunsScoredInOver() {
        return runsScoredInOver;
    }

    public void setRunsScoredInOver(String runsScoredInOver) {
        this.runsScoredInOver = runsScoredInOver;
    }

    public String getInningId() {
        return inningId;
    }

    public void setInningId(String inningId) {
        this.inningId = inningId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(String bowlerId) {
        this.bowlerId = bowlerId;
    }

}
