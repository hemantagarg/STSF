package com.app.sportzfever.models;

/**
 * Created by hemanta on 01-09-2017.
 */

public class ModelUpcomingMatches {

    public ModelUpcomingMatches() {
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private String id;

    private String team1Name, noOfOvers,team1ScorerName, team1BattingStatus,team2BattingStatus,team2ScorerName, isScorerForTeam1, isScorerForTeam2,isCurrentInningScorer;

    public String getShortMonthName() {
        return ShortMonthName;
    }

    public void setShortMonthName(String shortMonthName) {
        ShortMonthName = shortMonthName;
    }

    private String ShortMonthName;

    private String team2profilePicture;

    private String team2AvatarID;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String time;
    private String date;
    private String monthName;
    private String year;
    private String canEditLineup, isActiveScorer, activeScorerName, isTeam1ScoringOnSf, isTeam2ScoringOnSf;

    private String eventId, numberOfPlayers;

    private String team2Name;

    private String location, team2Id, team1Id;

    private String team1AvatarID;

    private String matchStatus, matchId;

    private String team1profilePicture;

    private String tournamentName;

    private String numberOfOvers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2profilePicture() {
        return team2profilePicture;
    }

    public void setTeam2profilePicture(String team2profilePicture) {
        this.team2profilePicture = team2profilePicture;
    }

    public String getTeam2AvatarID() {
        return team2AvatarID;
    }

    public void setTeam2AvatarID(String team2AvatarID) {
        this.team2AvatarID = team2AvatarID;
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeam1AvatarID() {
        return team1AvatarID;
    }

    public void setTeam1AvatarID(String team1AvatarID) {
        this.team1AvatarID = team1AvatarID;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getTeam1profilePicture() {
        return team1profilePicture;
    }

    public void setTeam1profilePicture(String team1profilePicture) {
        this.team1profilePicture = team1profilePicture;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getNumberOfOvers() {
        return numberOfOvers;
    }

    public void setNumberOfOvers(String numberOfOvers) {
        this.numberOfOvers = numberOfOvers;
    }


    public String getTeam1ScorerName() {
        return team1ScorerName;
    }

    public void setTeam1ScorerName(String team1ScorerName) {
        this.team1ScorerName = team1ScorerName;
    }

    public String getTeam2ScorerName() {
        return team2ScorerName;
    }

    public void setTeam2ScorerName(String team2ScorerName) {
        this.team2ScorerName = team2ScorerName;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getCanEditLineup() {
        return canEditLineup;
    }

    public void setCanEditLineup(String canEditLineup) {
        this.canEditLineup = canEditLineup;
    }

    public String getIsActiveScorer() {
        return isActiveScorer;
    }

    public void setIsActiveScorer(String isActiveScorer) {
        this.isActiveScorer = isActiveScorer;
    }

    public String getActiveScorerName() {
        return activeScorerName;
    }

    public void setActiveScorerName(String activeScorerName) {
        this.activeScorerName = activeScorerName;
    }

    public String getIsTeam1ScoringOnSf() {
        return isTeam1ScoringOnSf;
    }

    public void setIsTeam1ScoringOnSf(String isTeam1ScoringOnSf) {
        this.isTeam1ScoringOnSf = isTeam1ScoringOnSf;
    }

    public String getIsTeam2ScoringOnSf() {
        return isTeam2ScoringOnSf;
    }

    public void setIsTeam2ScoringOnSf(String isTeam2ScoringOnSf) {
        this.isTeam2ScoringOnSf = isTeam2ScoringOnSf;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getIsScorerForTeam1() {
        return isScorerForTeam1;
    }

    public void setIsScorerForTeam1(String isScorerForTeam1) {
        this.isScorerForTeam1 = isScorerForTeam1;
    }

    public String getIsScorerForTeam2() {
        return isScorerForTeam2;
    }

    public void setIsScorerForTeam2(String isScorerForTeam2) {
        this.isScorerForTeam2 = isScorerForTeam2;
    }

    public String getIsCurrentInningScorer() {
        return isCurrentInningScorer;
    }

    public void setIsCurrentInningScorer(String isCurrentInningScorer) {
        this.isCurrentInningScorer = isCurrentInningScorer;
    }

    public String getTeam1BattingStatus() {
        return team1BattingStatus;
    }

    public void setTeam1BattingStatus(String team1BattingStatus) {
        this.team1BattingStatus = team1BattingStatus;
    }

    public String getTeam2BattingStatus() {
        return team2BattingStatus;
    }

    public void setTeam2BattingStatus(String team2BattingStatus) {
        this.team2BattingStatus = team2BattingStatus;
    }

    public String getNoOfOvers() {
        return noOfOvers;
    }

    public void setNoOfOvers(String noOfOvers) {
        this.noOfOvers = noOfOvers;
    }
}
