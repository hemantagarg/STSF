package com.app.sportzfever.models.dbmodels;

public class Matches {
    private int id;
    private String  inviteStatus;
    private String  matchDate;
    private String  numberOfOvers;
    private String  matchStatus;
    private String  tossSelection;
    private String  eventId;
    private String  team2CheckAvailibility;
    private String  team1Id;
    private String  team2Id;
    private String  matchResultId;
    private String  location;
    private String  tie;
    private String  matchType;
    private String  numberOfInnings;
    private String  readStatus;
    private String  dl;
    private String  description;
    private String  activeScorerId;
    private String  isTeam2ScoringOnSf;
    private String  tossResultId;
    private String  calendarId;
    private String  leagueId;
    private String  isTeam1ScoringOnSf;
    private String  team1CheckAvailibility;
    private String  points;
    private String  numberOfPlayers;
    private String  tournamentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getNumberOfOvers() {
        return numberOfOvers;
    }

    public void setNumberOfOvers(String numberOfOvers) {
        this.numberOfOvers = numberOfOvers;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getTossSelection() {
        return tossSelection;
    }

    public void setTossSelection(String tossSelection) {
        this.tossSelection = tossSelection;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTeam2CheckAvailibility() {
        return team2CheckAvailibility;
    }

    public void setTeam2CheckAvailibility(String team2CheckAvailibility) {
        this.team2CheckAvailibility = team2CheckAvailibility;
    }

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    public String getMatchResultId() {
        return matchResultId;
    }

    public void setMatchResultId(String matchResultId) {
        this.matchResultId = matchResultId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTie() {
        return tie;
    }

    public void setTie(String tie) {
        this.tie = tie;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getNumberOfInnings() {
        return numberOfInnings;
    }

    public void setNumberOfInnings(String numberOfInnings) {
        this.numberOfInnings = numberOfInnings;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public String getDl() {
        return dl;
    }

    public void setDl(String dl) {
        this.dl = dl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActiveScorerId() {
        return activeScorerId;
    }

    public void setActiveScorerId(String activeScorerId) {
        this.activeScorerId = activeScorerId;
    }

    public String getIsTeam2ScoringOnSf() {
        return isTeam2ScoringOnSf;
    }

    public void setIsTeam2ScoringOnSf(String isTeam2ScoringOnSf) {
        this.isTeam2ScoringOnSf = isTeam2ScoringOnSf;
    }

    public String getTossResultId() {
        return tossResultId;
    }

    public void setTossResultId(String tossResultId) {
        this.tossResultId = tossResultId;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getIsTeam1ScoringOnSf() {
        return isTeam1ScoringOnSf;
    }

    public void setIsTeam1ScoringOnSf(String isTeam1ScoringOnSf) {
        this.isTeam1ScoringOnSf = isTeam1ScoringOnSf;
    }

    public String getTeam1CheckAvailibility() {
        return team1CheckAvailibility;
    }

    public void setTeam1CheckAvailibility(String team1CheckAvailibility) {
        this.team1CheckAvailibility = team1CheckAvailibility;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }




    public Matches(String  inviteStatus,String  matchDate,String  numberOfOvers,String  matchStatus,String  tossSelection,String  eventId,String  team2CheckAvailibility,String  team1Id,String  matchResultId,String  location,String  tie,String  matchType,String  numberOfInnings,String  readStatus,String  dl,String  description,String  activeScorerId,String  isTeam2ScoringOnSf,String  tossResultId,String  calendarId,String  leagueId,String  isTeam1ScoringOnSf,String  team1CheckAvailibility,String  points,String  numberOfPlayers,String  tournamentId,String  team2Id)
    {
        this.inviteStatus=inviteStatus;
        this.matchDate=matchDate;
        this.numberOfOvers=numberOfOvers;
        this.matchStatus=matchStatus;
        this.tossSelection=tossSelection;
        this.eventId=eventId;
        this.team2CheckAvailibility=team2CheckAvailibility;
        this.team1Id=team1Id;
        this.matchResultId=matchResultId;
        this.location=location;
        this.tie=tie;
        this.matchType=matchType;
        this.numberOfInnings=numberOfInnings;
        this.readStatus=readStatus;
        this.dl=dl;
        this.description=description;
        this.activeScorerId=activeScorerId;
        this.isTeam2ScoringOnSf=isTeam2ScoringOnSf;
        this.tossResultId=tossResultId;
        this.calendarId=calendarId;
        this.leagueId=leagueId;
        this.isTeam1ScoringOnSf=isTeam1ScoringOnSf;
        this.team1CheckAvailibility=team1CheckAvailibility;
        this.points=points;
        this.numberOfPlayers=numberOfPlayers;
        this.tournamentId=tournamentId;
        this.team2Id=team2Id;
    }

}
