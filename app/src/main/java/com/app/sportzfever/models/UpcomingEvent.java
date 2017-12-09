package com.app.sportzfever.models;

/**
 * Created by hemanta on 01-09-2017.
 */

public class UpcomingEvent {

    private String team1Name;

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    private String matchStatus = "";

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;

    private String team2Id;

    private String locationUrl;

    private String team2Name;

    private String location;

    private String team2ProfilePicture;

    private String team1Id;


    private String eventType;

    private String team1ProfilePicture;

    private String id;

    private String title;

    private String description;

    private String userId;

    private String eventImage;

    private String team1;

    private String team2,playersCount;

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }


    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
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

    public String getTeam2ProfilePicture() {
        return team2ProfilePicture;
    }

    public void setTeam2ProfilePicture(String team2ProfilePicture) {
        this.team2ProfilePicture = team2ProfilePicture;
    }

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }


    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getTeam1ProfilePicture() {
        return team1ProfilePicture;
    }

    public void setTeam1ProfilePicture(String team1ProfilePicture) {
        this.team1ProfilePicture = team1ProfilePicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }


    public String getShortDayName() {
        return shortDayName;
    }

    public void setShortDayName(String shortDayName) {
        this.shortDayName = shortDayName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public String getShortMonthName() {
        return ShortMonthName;
    }

    public void setShortMonthName(String ShortMonthName) {
        this.ShortMonthName = ShortMonthName;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    private String shortDayName;

    private String time;

    private String month;

    private String monthName;

    private String year;

    private String ShortMonthName;

    private String dayName;

    private String date;

    private String datetime;


    public String getMatchStatus() {
        return matchStatus;
    }

    public String getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(String playersCount) {
        this.playersCount = playersCount;
    }
}
