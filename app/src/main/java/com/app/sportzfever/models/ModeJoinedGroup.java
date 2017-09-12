package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 24-03-2017.
 */

public class ModeJoinedGroup implements Serializable {

    private String chatRoomId;

    private String profilePicture;

    private String name;

    public String getChatRoomId ()
    {
        return chatRoomId;
    }

    public void setChatRoomId (String chatRoomId)
    {
        this.chatRoomId = chatRoomId;
    }

    public String getProfilePicture ()
    {
        return profilePicture;
    }

    public void setProfilePicture (String profilePicture)
    {
        this.profilePicture = profilePicture;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public int getRowType() {
        return RowType;
    }

    public void setRowType(int rowType) {
        RowType = rowType;
    }

    private int RowType=1;


}
