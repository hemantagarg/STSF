package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 24-03-2017.
 */

public class ModelGroupChat implements Serializable {

    private String message;

    private String id;

    private String sentTime;

    public int getRowType() {
        return RowType;
    }

    public void setRowType(int rowType) {
        RowType = rowType;
    }

    private int RowType=1;

    private String senderName;

    private String senderProfilePic;

    private String senderId;

    private String sentOn;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getSentTime ()
    {
        return sentTime;
    }

    public void setSentTime (String sentTime)
    {
        this.sentTime = sentTime;
    }

    public String getSenderName ()
    {
        return senderName;
    }

    public void setSenderName (String senderName)
    {
        this.senderName = senderName;
    }

    public String getSenderProfilePic ()
    {
        return senderProfilePic;
    }

    public void setSenderProfilePic (String senderProfilePic)
    {
        this.senderProfilePic = senderProfilePic;
    }

    public String getSenderId ()
    {
        return senderId;
    }

    public void setSenderId (String senderId)
    {
        this.senderId = senderId;
    }

    public String getSentOn ()
    {
        return sentOn;
    }

    public void setSentOn (String sentOn)
    {
        this.sentOn = sentOn;
    }

}
