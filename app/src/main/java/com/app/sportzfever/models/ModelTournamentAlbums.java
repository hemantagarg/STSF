package com.app.sportzfever.models;

/**
 * Created by hemanta on 30-08-2017.
 */

public class ModelTournamentAlbums {
    private String id;

    private String description;

    private String image;

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    private String albumName;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    private String albumId;

    private String uploadDate;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getUploadDate ()
    {
        return uploadDate;
    }

    public void setUploadDate (String uploadDate)
    {
        this.uploadDate = uploadDate;
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private Images[] images;

    private String totalImage;

    public Images[] getImages ()
    {
        return images;
    }

    public void setImages (Images[] images)
    {
        this.images = images;
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
