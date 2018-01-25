package com.app.sportzfever.models.dbmodels;

public class CricketBall {
    private   int id;
    private   String inningOverCount;
    private   String ballCountInOver  ;
    private   String runScored ;
    private   String extraRuns ;
    private   String isFour;
    private   String isSix ;
    private   String runScoredOnNoBall ;
    private   String isNoBall ;
    private   String isWideBall ;
    private   String runScoredOnWideball ;
    private   String isBye;
    private   String runScoredOnBye ;
    private   String isLegBye ;
    private   String runScoredOnLegBye ;
    private   String isWicket ;
    private   String wicketType ;
    private   String comments ;
    private   String batsmanId ;
    private   String bowlerId ;
    private   String inningId ;
    private   String overId ;
    private   String matchId ;
    private   String caughtById ;
    private   String  runOutById ;
    private   String stumpedById ;
    private   String outBatsmanId;

    public CricketBall(String ballCountInOver , String inningOverCount , String runScored , String extraRuns , String isFour , String isSix, String runScoredOnNoBall, String isNoBall, String isWideBall, String runScoredOnWideball, String isBye, String runScoredOnBye , String isLegBye, String runScoredOnLegBye, String isWicket, String wicketType, String comments, String batsmanId, String bowlerId , String inningId, String overId, String matchId, String caughtById, String runOutById, String stumpedById, String outBatsmanId){this.inningOverCount =inningOverCount;
      this.ballCountInOver = ballCountInOver ;
      this.runScored =runScored;
      this.extraRuns =extraRuns;
      this.isFour=isFour;
      this. isSix =isSix;
      this. runScoredOnNoBall=runScoredOnNoBall ;
      this. isNoBall =isNoBall;
      this. isWideBall=isWideBall ;
      this. runScoredOnWideball =runScoredOnWideball;
      this. isBye=isBye;
      this. runScoredOnBye =runScoredOnBye;
      this. isLegBye =isLegBye;
      this. runScoredOnLegBye =runScoredOnLegBye;
      this. isWicket=isWicket ;
      this. wicketType=wicketType ;
      this. comments=comments ;
      this. batsmanId =batsmanId;
      this. bowlerId =bowlerId;
      this. inningId =inningId;
      this. overId=overId ;
      this. matchId =matchId;
      this. caughtById =caughtById;
      this. runOutById=runOutById ;
      this. stumpedById =stumpedById;
      this. outBatsmanId =outBatsmanId;
    }

    public String getInningOverCount() {
        return inningOverCount;
    }

    public void setInningOverCount(String inningOverCount) {
        this.inningOverCount = inningOverCount;
    }

    public String getBallCountInOver() {
        return ballCountInOver;
    }

    public void setBallCountInOver(String ballCountInOver) {
        this.ballCountInOver = ballCountInOver;
    }

    public String getRunScored() {
        return runScored;
    }

    public void setRunScored(String runScored) {
        this.runScored = runScored;
    }

    public String getExtraRuns() {
        return extraRuns;
    }

    public void setExtraRuns(String extraRuns) {
        this.extraRuns = extraRuns;
    }

    public String isFour() {
        return isFour;
    }

    public void setFour(String four) {
        isFour = four;
    }

    public String isSix() {
        return isSix;
    }

    public void setSix(String six) {
        isSix = six;
    }

    public String getRunScoredOnNoBall() {
        return runScoredOnNoBall;
    }

    public void setRunScoredOnNoBall(String runScoredOnNoBall) {
        this.runScoredOnNoBall = runScoredOnNoBall;
    }

    public String isNoBall() {
        return isNoBall;
    }

    public void setNoBall(String noBall) {
        isNoBall = noBall;
    }

    public String isWideBall() {
        return isWideBall;
    }

    public void setWideBall(String wideBall) {
        isWideBall = wideBall;
    }

    public String getRunScoredOnWideball() {
        return runScoredOnWideball;
    }

    public void setRunScoredOnWideball(String runScoredOnWideball) {
        this.runScoredOnWideball = runScoredOnWideball;
    }

    public String isBye() {
        return isBye;
    }

    public void setBye(String bye) {
        isBye = bye;
    }

    public String getRunScoredOnBye() {
        return runScoredOnBye;
    }

    public void setRunScoredOnBye(String runScoredOnBye) {
        this.runScoredOnBye = runScoredOnBye;
    }

    public String isLegBye() {
        return isLegBye;
    }

    public void setLegBye(String legBye) {
        isLegBye = legBye;
    }

    public String getRunScoredOnLegBye() {
        return runScoredOnLegBye;
    }

    public void setRunScoredOnLegBye(String runScoredOnLegBye) {
        this.runScoredOnLegBye = runScoredOnLegBye;
    }

    public String isWicket() {
        return isWicket;
    }

    public void setWicket(String wicket) {
        isWicket = wicket;
    }

    public String getWicketType() {
        return wicketType;
    }

    public void setWicketType(String wicketType) {
        this.wicketType = wicketType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getInningId() {
        return inningId;
    }

    public void setInningId(String inningId) {
        this.inningId = inningId;
    }

    public String getOverId() {
        return overId;
    }

    public void setOverId(String overId) {
        this.overId = overId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getCaughtById() {
        return caughtById;
    }

    public void setCaughtById(String caughtById) {
        this.caughtById = caughtById;
    }

    public String getRunOutById() {
        return runOutById;
    }

    public void setRunOutById(String runOutById) {
        this.runOutById = runOutById;
    }

    public String getStumpedById() {
        return stumpedById;
    }

    public void setStumpedById(String stumpedById) {
        this.stumpedById = stumpedById;
    }

    public String getOutBatsmanId() {
        return outBatsmanId;
    }

    public void setOutBatsmanId(String outBatsmanId) {
        this.outBatsmanId = outBatsmanId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
