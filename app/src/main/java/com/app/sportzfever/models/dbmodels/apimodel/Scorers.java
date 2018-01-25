package com.app.sportzfever.models.dbmodels.apimodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Scorers {

    private List<TeamScorer> team1Scorer = null;
    private List<TeamScorer> team2Scorer = null;

    public List<TeamScorer> getTeam1Scorer() {
        return team1Scorer;
    }

    public void setTeam1Scorer(List<TeamScorer> team1Scorer) {
        this.team1Scorer = team1Scorer;
    }

    public List<TeamScorer> getTeam2Scorer() {
        return team2Scorer;
    }

    public void setTeam2Scorer(List<TeamScorer> team2Scorer) {
        this.team2Scorer = team2Scorer;
    }

}