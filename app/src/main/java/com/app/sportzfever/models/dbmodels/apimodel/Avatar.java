package com.app.sportzfever.models.dbmodels.apimodel;

public class Avatar {

    private int  id;
    private String  profileBackgroundImage;
    private String  description;
    private String  avatarType;
    private String  coverImage;
    private String  userId;
    private String  alias;
    private String  createDate;
    private String  sportId;
    private String  profilePicture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfileBackgroundImage() {
        return profileBackgroundImage;
    }

    public void setProfileBackgroundImage(String profileBackgroundImage) {
        this.profileBackgroundImage = profileBackgroundImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarType() {
        return avatarType;
    }

    public void setAvatarType(String avatarType) {
        this.avatarType = avatarType;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSportId() {
        return sportId;
    }

    public void setSportId(String sportId) {
        this.sportId = sportId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String  name;




    public Avatar(String  profileBackgroundImage,String  description,String  avatarType,String  coverImage,String  userId,String  alias,String  createDate,String  sportId,String  profilePicture,String  name)
    {
        this.profileBackgroundImage=profileBackgroundImage;
        this.description=description;
        this.avatarType=avatarType;
        this.coverImage=coverImage;
        this.userId=userId;
        this.alias=alias;
        this.createDate=createDate;
        this.sportId=sportId;
        this.profilePicture=profilePicture;
        this.name=name;

    }

}
