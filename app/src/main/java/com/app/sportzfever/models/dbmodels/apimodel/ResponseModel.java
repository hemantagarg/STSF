package com.app.sportzfever.models.dbmodels.apimodel;

/**
 * Created by Admin on 1/23/2018.
 */

public class ResponseModel {

    private String result;
    private String allowedToEdit;
    private MatchStaticsData data;
    private String message;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getAllowedToEdit() {
        return allowedToEdit;
    }

    public void setAllowedToEdit(String allowedToEdit) {
        this.allowedToEdit = allowedToEdit;
    }

    public MatchStaticsData getData() {
        return data;
    }

    public void setData(MatchStaticsData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}