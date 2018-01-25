package com.app.sportzfever.models.dbmodels;

public class MatchSfPlayerName {
    private int  id;
    private String  avatarName;
    private String  teamId;
    private String  matchId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    private String  avatarId;






    public MatchSfPlayerName(String  avatarName,String  teamId,String  matchId,String  avatarId)
    {
        this.avatarName=avatarName;
        this.teamId=teamId;
        this.matchId=matchId;
        this.avatarId=avatarId;

    }

}
