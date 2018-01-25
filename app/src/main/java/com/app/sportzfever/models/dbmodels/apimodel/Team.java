package com.app.sportzfever.models.dbmodels.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Team {

    private String teamAvatarId;
    private String name;
    private String isTeamScoringOnSf;
    private String teamId;
    private String profilePicture;

    public String getTeamAvatarId() {
        return teamAvatarId;
    }

    public void setTeamAvatarId(String teamAvatarId) {
        this.teamAvatarId = teamAvatarId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsTeamScoringOnSf() {
        return isTeamScoringOnSf;
    }

    public void setIsTeamScoringOnSf(String isTeamScoringOnSf) {
        this.isTeamScoringOnSf = isTeamScoringOnSf;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

}