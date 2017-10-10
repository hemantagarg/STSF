package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 10-09-2017.
 */

public class ModelGallery implements Serializable{

    public int getRowType() {
        return RowType;
    }

    public void setRowType(int rowType) {
        RowType = rowType;
    }

    private int RowType=1;
    private String albumName;

    private String albumId;

    private String image;

    private String user;

    private String totalImage;

    public String getAlbumName ()
    {
        return albumName;
    }

    public void setAlbumName (String albumName)
    {
        this.albumName = albumName;
    }

    public String getAlbumId ()
    {
        return albumId;
    }

    public void setAlbumId (String albumId)
    {
        this.albumId = albumId;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getUser ()
    {
        return user;
    }

    public void setUser (String user)
    {
        this.user = user;
    }

    public String getTotalImage ()
    {
        return totalImage;
    }

    public void setTotalImage (String totalImage)
    {
        this.totalImage = totalImage;
    }

}
