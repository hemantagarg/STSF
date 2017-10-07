package com.app.sportzfever.models;

/**
 * Created by hemanta on 01-09-2017.
 */

public class ModelUpcomingMatches {

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private String id;

    private String team1Name;

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



    private String eventId;

    private String team2Name;

    private String location;

    private String team1AvatarID;

    private String matchStatus;

    private String team1profilePicture;

    private String tournamentName;

    private String numberOfOvers;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTeam1Name ()
    {
        return team1Name;
    }

    public void setTeam1Name (String team1Name)
    {
        this.team1Name = team1Name;
    }

    public String getTeam2profilePicture ()
    {
        return team2profilePicture;
    }

    public void setTeam2profilePicture (String team2profilePicture)
    {
        this.team2profilePicture = team2profilePicture;
    }

    public String getTeam2AvatarID ()
    {
        return team2AvatarID;
    }

    public void setTeam2AvatarID (String team2AvatarID)
    {
        this.team2AvatarID = team2AvatarID;
    }



    public String getEventId ()
    {
        return eventId;
    }

    public void setEventId (String eventId)
    {
        this.eventId = eventId;
    }

    public String getTeam2Name ()
    {
        return team2Name;
    }

    public void setTeam2Name (String team2Name)
    {
        this.team2Name = team2Name;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getTeam1AvatarID ()
    {
        return team1AvatarID;
    }

    public void setTeam1AvatarID (String team1AvatarID)
    {
        this.team1AvatarID = team1AvatarID;
    }

    public String getMatchStatus ()
    {
        return matchStatus;
    }

    public void setMatchStatus (String matchStatus)
    {
        this.matchStatus = matchStatus;
    }

    public String getTeam1profilePicture ()
    {
        return team1profilePicture;
    }

    public void setTeam1profilePicture (String team1profilePicture)
    {
        this.team1profilePicture = team1profilePicture;
    }

    public String getTournamentName ()
    {
        return tournamentName;
    }

    public void setTournamentName (String tournamentName)
    {
        this.tournamentName = tournamentName;
    }

    public String getNumberOfOvers ()
    {
        return numberOfOvers;
    }

    public void setNumberOfOvers (String numberOfOvers)
    {
        this.numberOfOvers = numberOfOvers;
    }


}
