package com.app.sportzfever.models.dbmodels;

public class MatchTeamRoles {
    private int  id;
    private String  ViceCaptain;
    private String  matchId;
    private String  CaptainAvatar;
    private String  teamId;

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

    private String  WicketKeeperAvatar;


    public MatchTeamRoles(String  ViceCaptain,String  matchId,String  CaptainAvatar,String  teamId,String  WicketKeeperAvatar)
    {
        this.ViceCaptain=ViceCaptain;
        this.matchId=matchId;
        this.CaptainAvatar=CaptainAvatar;
        this.teamId=teamId;
        this.WicketKeeperAvatar=WicketKeeperAvatar;
    }

}
