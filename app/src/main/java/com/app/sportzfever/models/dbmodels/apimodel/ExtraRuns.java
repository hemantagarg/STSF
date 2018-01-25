package com.app.sportzfever.models.dbmodels.apimodel;

/**
 * Created by Admin on 1/23/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtraRuns {

    private Integer nb;
    private Integer wd;
    private Integer b;
    private Integer lb;

    public Integer getNb() {
        return nb;
    }

    public void setNb(Integer nb) {
        this.nb = nb;
    }

    public Integer getWd() {
        return wd;
    }

    public void setWd(Integer wd) {
        this.wd = wd;
    }

    public Integer getB() {
        return b;
    }

    public void setB(Integer b) {
        this.b = b;
    }

    public Integer getLb() {
        return lb;
    }

    public void setLb(Integer lb) {
        this.lb = lb;
    }

}