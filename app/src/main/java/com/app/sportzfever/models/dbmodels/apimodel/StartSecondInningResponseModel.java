package com.app.sportzfever.models.dbmodels.apimodel;


public class StartSecondInningResponseModel {
    private String inningId;
    private String battingTeamId;

    public String getInningId() {
        return inningId;
    }

    public void setInningId(String inningId) {
        this.inningId = inningId;
    }

    public String getBattingTeamId() {
        return battingTeamId;
    }

    public void setBattingTeamId(String battingTeamId) {
        this.battingTeamId = battingTeamId;
    }

    public String getBowlingTeamId() {
        return bowlingTeamId;
    }

    public void setBowlingTeamId(String bowlingTeamId) {
        this.bowlingTeamId = bowlingTeamId;
    }

    public String getActiveScorerId() {
        return activeScorerId;
    }

    public void setActiveScorerId(String activeScorerId) {
        this.activeScorerId = activeScorerId;
    }

    private String bowlingTeamId;
    private String activeScorerId;

}
