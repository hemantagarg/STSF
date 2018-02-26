package com.app.sportzfever.models.dbmodels;

public class TossJson
{


    private   int id;
    private   String jsonData;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public int getServerinningId() {
        return serverinningId;
    }

    public void setServerinningId(int serverinningId) {
        this.serverinningId = serverinningId;
    }

    private   int serverinningId;

    public int getCricket_inning_id() {
        return cricket_inning_id;
    }

    public void setCricket_inning_id(int cricket_inning_id) {
        this.cricket_inning_id = cricket_inning_id;
    }

    private   int cricket_inning_id;

}
