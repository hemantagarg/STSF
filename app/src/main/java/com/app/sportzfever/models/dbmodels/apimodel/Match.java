package com.app.sportzfever.models.dbmodels.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match {

    private int id;
    private String description;
    private String location;
    private String matchDate;
    private MatchDate matchDate1;
    private String tossResultId;
    private String tie;
    private String tossSelection;
    private String matchType;
    private String matchStatus;
    private String numberOfInnings;
    private String inviteStatus;
    private String team1Id;
    private String team1InningId;
    private String team1Name;
    private String team1ProfilePic;
    private String team2Id;
    private String team2InningId;
    private String team2Name;
    private String team2ProfilePic;
    private String tournamentId;
    private String matchResultId;
    private String eventId;
    private String activeScorerId;
    private String numberOfPlayers;
    private String numberOfOvers;
    private String isTeam1ScoringOnSf;
    private String isTeam2ScoringOnSf;
    private String team1AvatarId;
    private String team2AvatarId;
    private String tournamentName;
    private String matchTile;
    private String wonString;
    private String tossString;
    private String team1Scorer;
    private String team2Scorer;
    private String team1ScoreString;
    private String team2ScoreString;
    private String matchScheduleString;
    private String socialSharingString;
    private String inningsPlayStatusString;
    private String requiredRunRate;
    private String runInBall;
    private String runInOver;
    private String team1StatusPic;
    private String team2StatusPic;
    private String runsRequired;
    private String ballsRemaining;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public MatchDate getMatchDate1() {
        return matchDate1;
    }

    public void setMatchDate1(MatchDate matchDate1) {
        this.matchDate1 = matchDate1;
    }

    public String getTie() {
        return tie;
    }

    public void setTie(String tie) {
        this.tie = tie;
    }

    public String getTossSelection() {
        return tossSelection;
    }

    public void setTossSelection(String tossSelection) {
        this.tossSelection = tossSelection;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getNumberOfInnings() {
        return numberOfInnings;
    }

    public void setNumberOfInnings(String numberOfInnings) {
        this.numberOfInnings = numberOfInnings;
    }

    public String getInviteStatus() {
        return inviteStatus;
    }

    public void setInviteStatus(String inviteStatus) {
        this.inviteStatus = inviteStatus;
    }

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    public String getTeam1InningId() {
        return team1InningId;
    }

    public void setTeam1InningId(String team1InningId) {
        this.team1InningId = team1InningId;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam1ProfilePic() {
        return team1ProfilePic;
    }

    public void setTeam1ProfilePic(String team1ProfilePic) {
        this.team1ProfilePic = team1ProfilePic;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    public String getTeam2InningId() {
        return team2InningId;
    }

    public void setTeam2InningId(String team2InningId) {
        this.team2InningId = team2InningId;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getTeam2ProfilePic() {
        return team2ProfilePic;
    }

    public void setTeam2ProfilePic(String team2ProfilePic) {
        this.team2ProfilePic = team2ProfilePic;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getMatchResultId() {
        return matchResultId;
    }

    public void setMatchResultId(String matchResultId) {
        this.matchResultId = matchResultId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getActiveScorerId() {
        return activeScorerId;
    }

    public void setActiveScorerId(String activeScorerId) {
        this.activeScorerId = activeScorerId;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getNumberOfOvers() {
        return numberOfOvers;
    }

    public void setNumberOfOvers(String numberOfOvers) {
        this.numberOfOvers = numberOfOvers;
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

    public String getTeam1AvatarId() {
        return team1AvatarId;
    }

    public void setTeam1AvatarId(String team1AvatarId) {
        this.team1AvatarId = team1AvatarId;
    }

    public String getTeam2AvatarId() {
        return team2AvatarId;
    }

    public void setTeam2AvatarId(String team2AvatarId) {
        this.team2AvatarId = team2AvatarId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getMatchTile() {
        return matchTile;
    }

    public void setMatchTile(String matchTile) {
        this.matchTile = matchTile;
    }

    public String getWonString() {
        return wonString;
    }

    public void setWonString(String wonString) {
        this.wonString = wonString;
    }

    public String getTossString() {
        return tossString;
    }

    public void setTossString(String tossString) {
        this.tossString = tossString;
    }

    public String getTeam1Scorer() {
        return team1Scorer;
    }

    public void setTeam1Scorer(String team1Scorer) {
        this.team1Scorer = team1Scorer;
    }

    public String getTeam2Scorer() {
        return team2Scorer;
    }

    public void setTeam2Scorer(String team2Scorer) {
        this.team2Scorer = team2Scorer;
    }

    public String getTeam1ScoreString() {
        return team1ScoreString;
    }

    public void setTeam1ScoreString(String team1ScoreString) {
        this.team1ScoreString = team1ScoreString;
    }

    public String getTeam2ScoreString() {
        return team2ScoreString;
    }

    public void setTeam2ScoreString(String team2ScoreString) {
        this.team2ScoreString = team2ScoreString;
    }

    public String getMatchScheduleString() {
        return matchScheduleString;
    }

    public void setMatchScheduleString(String matchScheduleString) {
        this.matchScheduleString = matchScheduleString;
    }

    public String getSocialSharingString() {
        return socialSharingString;
    }

    public void setSocialSharingString(String socialSharingString) {
        this.socialSharingString = socialSharingString;
    }

    public String getInningsPlayStatusString() {
        return inningsPlayStatusString;
    }

    public void setInningsPlayStatusString(String inningsPlayStatusString) {
        this.inningsPlayStatusString = inningsPlayStatusString;
    }

    public String getRequiredRunRate() {
        return requiredRunRate;
    }

    public void setRequiredRunRate(String requiredRunRate) {
        this.requiredRunRate = requiredRunRate;
    }

    public String getRunInBall() {
        return runInBall;
    }

    public void setRunInBall(String runInBall) {
        this.runInBall = runInBall;
    }

    public String getRunInOver() {
        return runInOver;
    }

    public void setRunInOver(String runInOver) {
        this.runInOver = runInOver;
    }

    public String getTeam1StatusPic() {
        return team1StatusPic;
    }

    public void setTeam1StatusPic(String team1StatusPic) {
        this.team1StatusPic = team1StatusPic;
    }

    public String getTeam2StatusPic() {
        return team2StatusPic;
    }

    public void setTeam2StatusPic(String team2StatusPic) {
        this.team2StatusPic = team2StatusPic;
    }

    public String getRunsRequired() {
        return runsRequired;
    }

    public void setRunsRequired(String runsRequired) {
        this.runsRequired = runsRequired;
    }

    public String getBallsRemaining() {
        return ballsRemaining;
    }

    public void setBallsRemaining(String ballsRemaining) {
        this.ballsRemaining = ballsRemaining;
    }

    public String getTossResultId() {
        return tossResultId;
    }

    public void setTossResultId(String tossResultId) {
        this.tossResultId = tossResultId;
    }
}