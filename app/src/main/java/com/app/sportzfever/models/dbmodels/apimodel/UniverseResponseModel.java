package com.app.sportzfever.models.dbmodels.apimodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 1/23/2018.
 */

public class UniverseResponseModel<T> {

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String result;
    private T data ;
    private String message;
}