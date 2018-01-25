package com.app.sportzfever.models.dbmodels.apimodel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MatchStaticsData {

    private Match match;
    private Scorers scorers;
    private Team team1;
    private Team team2;
    private List<TeamSquad> team1Squad = null;
    private List<TeamSquad> team2Squad = null;
    private List<Inning> innings = null;

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Scorers getScorers() {
        return scorers;
    }

    public void setScorers(Scorers scorers) {
        this.scorers = scorers;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public List<TeamSquad> getTeam1Squad() {
        return team1Squad;
    }

    public void setTeam1Squad(List<TeamSquad> team1Squad) {
        this.team1Squad = team1Squad;
    }

    public List<TeamSquad> getTeam2Squad() {
        return team2Squad;
    }

    public void setTeam2Squad(List<TeamSquad> team2Squad) {
        this.team2Squad = team2Squad;
    }

    public List<Inning> getInnings() {
        return innings;
    }

    public void setInnings(List<Inning> innings) {
        this.innings = innings;
    }

}