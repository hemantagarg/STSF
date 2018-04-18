package com.app.sportzfever.models.dbmodels.apimodel;

public class MatchTeamRolesModel
{
    private int  id;
    private String  ViceCaptain;
    private String  matchId;
    private String  CaptainAvatar;
    private String  teamId;
    private String  WicketKeeperAvatar;
    private String  captainName;
    private String  captainProfilePicture;
    private String  wicketKeeperName;
    private String  wicketKeeperProfilePicture;
    private String  viceCaptainName;
    private String  viceCaptainProfilePicture;

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public String getCaptainProfilePicture() {
        return captainProfilePicture;
    }

    public void setCaptainProfilePicture(String captainProfilePicture) {
        this.captainProfilePicture = captainProfilePicture;
    }

    public String getWicketKeeperName() {
        return wicketKeeperName;
    }

    public void setWicketKeeperName(String wicketKeeperName) {
        this.wicketKeeperName = wicketKeeperName;
    }

    public String getWicketKeeperProfilePicture() {
        return wicketKeeperProfilePicture;
    }

    public void setWicketKeeperProfilePicture(String wicketKeeperProfilePicture) {
        this.wicketKeeperProfilePicture = wicketKeeperProfilePicture;
    }

    public String getViceCaptainName() {
        return viceCaptainName;
    }

    public void setViceCaptainName(String viceCaptainName) {
        this.viceCaptainName = viceCaptainName;
    }

    public String getViceCaptainProfilePicture() {
        return viceCaptainProfilePicture;
    }

    public void setViceCaptainProfilePicture(String viceCaptainProfilePicture) {
        this.viceCaptainProfilePicture = viceCaptainProfilePicture;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getViceCaptain() {
        return ViceCaptain;
    }

    public void setViceCaptain(String viceCaptain) {
        ViceCaptain = viceCaptain;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getCaptainAvatar() {
        return CaptainAvatar;
    }

    public void setCaptainAvatar(String captainAvatar) {
        CaptainAvatar = captainAvatar;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getWicketKeeperAvatar() {
        return WicketKeeperAvatar;
    }

    public void setWicketKeeperAvatar(String wicketKeeperAvatar) {
        WicketKeeperAvatar = wicketKeeperAvatar;
    }

}
