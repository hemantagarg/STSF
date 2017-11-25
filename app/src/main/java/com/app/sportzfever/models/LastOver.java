package com.app.sportzfever.models;

/**
 * Created by hemanta on 25-11-2017.
 */

public class LastOver {

    private String id;

    private String overNumber;

    private String bowlerId;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getOverNumber ()
    {
        return overNumber;
    }

    public void setOverNumber (String overNumber)
    {
        this.overNumber = overNumber;
    }

    public String getBowlerId ()
    {
        return bowlerId;
    }

    public void setBowlerId (String bowlerId)
    {
        this.bowlerId = bowlerId;
    }

}
