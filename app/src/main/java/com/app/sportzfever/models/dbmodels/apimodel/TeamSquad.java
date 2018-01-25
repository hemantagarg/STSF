package com.app.sportzfever.models.dbmodels.apimodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamSquad {

    private String playerRole;
    private String playerAvatarId;
    private String profilePicture;
    private String name;

    public String getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(String playerRole) {
        this.playerRole = playerRole;
    }

    public String getPlayerAvatarId() {
        return playerAvatarId;
    }

    public void setPlayerAvatarId(String playerAvatarId) {
        this.playerAvatarId = playerAvatarId;
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

}