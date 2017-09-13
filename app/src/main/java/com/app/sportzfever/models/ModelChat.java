package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 24-03-2017.
 */

public class ModelChat implements Serializable {
    private String senderid;



    private String id;

    private String sentTime;

    private String senderName;

    private String recieverId;

    private String senderProfilePic;

    private String recieverName;

    private String recieverProfilePic;

    private String sentOn;

    public String getSenderid ()
    {
        return senderid;
    }

    public void setSenderid (String senderid)
    {
        this.senderid = senderid;
    }

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

    public String getRecieverId ()
    {
        return recieverId;
    }

    public void setRecieverId (String recieverId)
    {
        this.recieverId = recieverId;
    }

    public String getSenderProfilePic ()
    {
        return senderProfilePic;
    }

    public void setSenderProfilePic (String senderProfilePic)
    {
        this.senderProfilePic = senderProfilePic;
    }

    public String getRecieverName ()
    {
        return recieverName;
    }

    public void setRecieverName (String recieverName)
    {
        this.recieverName = recieverName;
    }

    public String getRecieverProfilePic ()
    {
        return recieverProfilePic;
    }

    public void setRecieverProfilePic (String recieverProfilePic)
    {
        this.recieverProfilePic = recieverProfilePic;
    }

    public String getSentOn ()
    {
        return sentOn;
    }

    public void setSentOn (String sentOn)
    {
        this.sentOn = sentOn;
    }

    private String message;

    public int getRowType() {
        return RowType;
    }

    public void setRowType(int rowType) {
        RowType = rowType;
    }

    private int RowType=1;
    private String name;

    private String profilePic;

    private String ID;

    private String TYPE;



    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getProfilePic ()
    {
        return profilePic;
    }

    public void setProfilePic (String profilePic)
    {
        this.profilePic = profilePic;
    }

    public String getID ()
    {
        return ID;
    }

    public void setID (String ID)
    {
        this.ID = ID;
    }

    public String getTYPE ()
    {
        return TYPE;
    }

    public void setTYPE (String TYPE)
    {
        this.TYPE = TYPE;
    }

}
