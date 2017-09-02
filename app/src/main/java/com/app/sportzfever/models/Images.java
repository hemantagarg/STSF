package com.app.sportzfever.models;

/**
 * Created by hemanta on 02-09-2017.
 */

public class Images {

    private String statusId;

    private String id;

    private String album;

    private String image;

    public String getStatusId ()
    {
        return statusId;
    }

    public void setStatusId (String statusId)
    {
        this.statusId = statusId;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAlbum ()
    {
        return album;
    }

    public void setAlbum (String album)
    {
        this.album = album;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [statusId = "+statusId+", id = "+id+", album = "+album+", image = "+image+"]";
    }
}
