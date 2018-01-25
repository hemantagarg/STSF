package com.app.sportzfever.models.dbmodels;

public class CricketScoreCard {
   private  int id;
   private  String status ;
   private String sixes ;
   private String runs;
   private String balls ;
   private String fours ;
   private String matchId ;
   private String strikeRate ;
   private String playOrder;
   private String inningId ;
   private String onStrike;
   private String batsmanId;
    public CricketScoreCard(String status ,String sixes ,String runs,String balls ,String fours ,String matchId ,
                            String strikeRate ,String playOrder,String inningId ,String onStrike,String batsmanId){
        this.status=status;
        this.sixes=sixes;
        this.runs=runs;
        this.balls=balls;
        this.fours=fours;
        this.matchId=matchId;
        this.strikeRate=strikeRate;
        this.playOrder=playOrder;
        this.inningId=inningId;
        this.onStrike=onStrike;
        this.batsmanId=batsmanId;

    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSixes() {
        return sixes;
    }

    public void setSixes(String sixes) {
        this.sixes = sixes;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
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

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(String strikeRate) {
        this.strikeRate = strikeRate;
    }

    public String getPlayOrder() {
        return playOrder;
    }

    public void setPlayOrder(String playOrder) {
        this.playOrder = playOrder;
    }

    public String getInningId() {
        return inningId;
    }

    public void setInningId(String inningId) {
        this.inningId = inningId;
    }

    public String isOnStrike() {
        return onStrike;
    }

    public void setOnStrike(String onStrike) {
        this.onStrike = onStrike;
    }

    public String getBatsmanId() {
        return batsmanId;
    }

    public void setBatsmanId(String batsmanId) {
        this.batsmanId = batsmanId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
