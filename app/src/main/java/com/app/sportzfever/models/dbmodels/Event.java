package com.app.sportzfever.models.dbmodels;

public class Event {
    private int  id;
    private String  status;
    private String  startDate;
    private String  endDate;
    private String  description;
    private String  title;
    private String  eventType;
    private String  isDeleted;
    private String  userId;
    private String  team1;
    private String  team2;
    private String  eventImage;
    private String  calendarId;
    private String  longitude;
    private String  location;
    private String  locationUrl;
    private String  isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(String calendarId) {
        this.calendarId = calendarId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    private String  lattitude;


    public Event(String  status,String  startDate,String  endDate,String  description,String  title,String  eventType,String  isDeleted,String  userId,String  team1,String  team2,String  eventImage,String  calendarId,String  longitude,String  location,String  locationUrl,String  isActive,String  lattitude)
    {
        this.status=status;
        this.startDate=startDate;
        this.endDate=endDate;
        this.description=description;
        this.title=title;
        this.eventType=eventType;
        this.isDeleted=isDeleted;
        this.userId=userId;
        this.team1=team1;
        this.team2=team2;
        this.eventImage=eventImage;
        this.calendarId=calendarId;
        this.longitude=longitude;
        this.location=location;
        this.locationUrl=locationUrl;
        this.isActive=isActive;
        this.lattitude=lattitude;
    }

}
