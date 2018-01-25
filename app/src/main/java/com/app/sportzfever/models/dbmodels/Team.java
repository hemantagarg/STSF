package com.app.sportzfever.models.dbmodels;

public class Team {
    private int id;
    private String  teamCreatedDate;
    private String  club;
    private String  longitude;
    private String  location;
    private String  owner;
    private String  lattitude;
    private String  captain;
    private String  isActive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamCreatedDate() {
        return teamCreatedDate;
    }

    public void setTeamCreatedDate(String teamCreatedDate) {
        this.teamCreatedDate = teamCreatedDate;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String  avatar;

    public Team(String  teamCreatedDate,String  club,String  longitude,String  location,String  owner,String  lattitude,String  captain,String  isActive,String  avatar)
    {
        this.teamCreatedDate=teamCreatedDate;
        this.club=club;
        this.longitude=longitude;
        this.location=location;
        this.owner=owner;
        this.lattitude=lattitude;
        this.captain=captain;
        this.isActive=isActive;
        this.avatar=avatar;
    }

}
