package com.app.sportzfever.models.dbmodels.apimodel;

public class Sport {

    private int  id;

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    private String  sportName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
