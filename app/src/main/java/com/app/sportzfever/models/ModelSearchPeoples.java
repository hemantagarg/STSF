package com.app.sportzfever.models;

/**
 * Created by hemanta on 31-10-2017.
 */

public class ModelSearchPeoples {
    private String dateOfBirth;

    private String hometown;

    private String email;

    private String profilePicture;

    private String name;

    private String userId;

    private String about;

    private String totalPost;

    private String profileBackgroundImage;

    private String totalTeam;

    private String totalFriend;

    private String coverImage;

    private String currentLocation;
    private int rowType;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(String totalPost) {
        this.totalPost = totalPost;
    }

    public String getProfileBackgroundImage() {
        return profileBackgroundImage;
    }

    public void setProfileBackgroundImage(String profileBackgroundImage) {
        this.profileBackgroundImage = profileBackgroundImage;
    }

    public String getTotalTeam() {
        return totalTeam;
    }

    public void setTotalTeam(String totalTeam) {
        this.totalTeam = totalTeam;
    }

    public String getTotalFriend() {
        return totalFriend;
    }

    public void setTotalFriend(String totalFriend) {
        this.totalFriend = totalFriend;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    public int getRowType() {
        return rowType;
    }
}
