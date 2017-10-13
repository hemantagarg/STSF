package com.app.sportzfever.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hemanta on 30-08-2017.
 */

public class ModelAvtarFeed implements Serializable {

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    private int rowType;
    private String originalUserProfilePicture;


    private String imageAvatarSportName;

    private String imageAvatarSportId;

    private String album;

    private String image;

    private String imageAvatarType;




    public String getImageAvatarSportName ()
    {
        return imageAvatarSportName;
    }

    public void setImageAvatarSportName (String imageAvatarSportName)
    {
        this.imageAvatarSportName = imageAvatarSportName;
    }

    public String getImageAvatarSportId ()
    {
        return imageAvatarSportId;
    }

    public void setImageAvatarSportId (String imageAvatarSportId)
    {
        this.imageAvatarSportId = imageAvatarSportId;
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

    public String getImageAvatarType ()
    {
        return imageAvatarType;
    }

    public void setImageAvatarType (String imageAvatarType)
    {
        this.imageAvatarType = imageAvatarType;
    }
    private String dateTime;

    private String statusType;

    private String[] commentsData;

    private String originalUserName;

    private String originalAvatarSportName;

    private String originalStatusId;

    private String dateString;



    private String avatarName;

    private String originalAvatarName;

    private String originalAvatarProfilePicture;

    private String avatarType;

    private String dateTimeUpdated;


    public String getShortDayName ()
    {
        return shortDayName;
    }

    public void setShortDayName (String shortDayName)
    {
        this.shortDayName = shortDayName;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getMonth ()
    {
        return month;
    }

    public void setMonth (String month)
    {
        this.month = month;
    }

    public String getMonthName ()
    {
        return monthName;
    }

    public void setMonthName (String monthName)
    {
        this.monthName = monthName;
    }

    public String getYear ()
    {
        return year;
    }

    public void setYear (String year)
    {
        this.year = year;
    }

    public String getShortMonthName ()
    {
        return ShortMonthName;
    }

    public void setShortMonthName (String ShortMonthName)
    {
        this.ShortMonthName = ShortMonthName;
    }

    public String getDayName ()
    {
        return dayName;
    }

    public void setDayName (String dayName)
    {
        this.dayName = dayName;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getDatetime ()
    {
        return datetime;
    }

    public void setDatetime (String datetime)
    {
        this.datetime = datetime;
    }






    private String userName;



    private String originalAvatarType;

    private String originalAvatar;

    private String originalUser;

    private String userProfilePicture;

    private String avatarSportName;

    private String avatarProfilePicture;

    private String isUserLiked;

    private String avatar;

    private String shares;

    private String likes;

    private String[] images;

    private String statusVisiblity;

    private String isShared;

    private String user;

    private String comments;

    public String getOriginalUserProfilePicture ()
    {
        return originalUserProfilePicture;
    }

    public void setOriginalUserProfilePicture (String originalUserProfilePicture)
    {
        this.originalUserProfilePicture = originalUserProfilePicture;
    }

    public String getDateTime ()
    {
        return dateTime;
    }

    public void setDateTime (String dateTime)
    {
        this.dateTime = dateTime;
    }

    public String getStatusType ()
    {
        return statusType;
    }

    public void setStatusType (String statusType)
    {
        this.statusType = statusType;
    }

    public String[] getCommentsData ()
    {
        return commentsData;
    }

    public void setCommentsData (String[] commentsData)
    {
        this.commentsData = commentsData;
    }

    public String getOriginalUserName ()
    {
        return originalUserName;
    }

    public void setOriginalUserName (String originalUserName)
    {
        this.originalUserName = originalUserName;
    }

    public String getOriginalAvatarSportName ()
    {
        return originalAvatarSportName;
    }

    public void setOriginalAvatarSportName (String originalAvatarSportName)
    {
        this.originalAvatarSportName = originalAvatarSportName;
    }

    public String getOriginalStatusId ()
    {
        return originalStatusId;
    }

    public void setOriginalStatusId (String originalStatusId)
    {
        this.originalStatusId = originalStatusId;
    }

    public String getDateString ()
    {
        return dateString;
    }

    public void setDateString (String dateString)
    {
        this.dateString = dateString;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getAvatarName ()
    {
        return avatarName;
    }

    public void setAvatarName (String avatarName)
    {
        this.avatarName = avatarName;
    }

    public String getOriginalAvatarName ()
    {
        return originalAvatarName;
    }

    public void setOriginalAvatarName (String originalAvatarName)
    {
        this.originalAvatarName = originalAvatarName;
    }

    public String getOriginalAvatarProfilePicture ()
    {
        return originalAvatarProfilePicture;
    }

    public void setOriginalAvatarProfilePicture (String originalAvatarProfilePicture)
    {
        this.originalAvatarProfilePicture = originalAvatarProfilePicture;
    }

    public String getAvatarType ()
    {
        return avatarType;
    }

    public void setAvatarType (String avatarType)
    {
        this.avatarType = avatarType;
    }

    public String getDateTimeUpdated ()
    {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated (String dateTimeUpdated)
    {
        this.dateTimeUpdated = dateTimeUpdated;
    }


    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }



    public String getOriginalAvatarType ()
    {
        return originalAvatarType;
    }

    public void setOriginalAvatarType (String originalAvatarType)
    {
        this.originalAvatarType = originalAvatarType;
    }

    public String getOriginalAvatar ()
    {
        return originalAvatar;
    }

    public void setOriginalAvatar (String originalAvatar)
    {
        this.originalAvatar = originalAvatar;
    }

    public String getOriginalUser ()
    {
        return originalUser;
    }

    public void setOriginalUser (String originalUser)
    {
        this.originalUser = originalUser;
    }

    public String getUserProfilePicture ()
    {
        return userProfilePicture;
    }

    public void setUserProfilePicture (String userProfilePicture)
    {
        this.userProfilePicture = userProfilePicture;
    }

    public String getAvatarSportName ()
    {
        return avatarSportName;
    }

    public void setAvatarSportName (String avatarSportName)
    {
        this.avatarSportName = avatarSportName;
    }

    public String getAvatarProfilePicture ()
    {
        return avatarProfilePicture;
    }

    public void setAvatarProfilePicture (String avatarProfilePicture)
    {
        this.avatarProfilePicture = avatarProfilePicture;
    }

    public String getIsUserLiked ()
    {
        return isUserLiked;
    }

    public void setIsUserLiked (String isUserLiked)
    {
        this.isUserLiked = isUserLiked;
    }

    public String getAvatar ()
    {
        return avatar;
    }

    public void setAvatar (String avatar)
    {
        this.avatar = avatar;
    }

    public String getShares ()
    {
        return shares;
    }

    public void setShares (String shares)
    {
        this.shares = shares;
    }

    public String getLikes ()
    {
        return likes;
    }

    public void setLikes (String likes)
    {
        this.likes = likes;
    }

    public String[] getImages ()
    {
        return images;
    }

    public void setImages (String[] images)
    {
        this.images = images;
    }

    public String getStatusVisiblity ()
    {
        return statusVisiblity;
    }

    public void setStatusVisiblity (String statusVisiblity)
    {
        this.statusVisiblity = statusVisiblity;
    }

    public String getIsShared ()
    {
        return isShared;
    }

    public void setIsShared (String isShared)
    {
        this.isShared = isShared;
    }

    public String getUser ()
    {
        return user;
    }

    public void setUser (String user)
    {
        this.user = user;
    }

    public String getComments ()
    {
        return comments;
    }

    public void setComments (String comments)
    {
        this.comments = comments;
    }

    private String team1Name;



    private String team2Id;

    private String locationUrl;

    private String team2Name;

    private String location;

    private String team2ProfilePicture;

    private String team1Id;



    private String eventType;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    private String event;

    private String team1ProfilePicture;

    private String id;

    private String statusId;

    private String title;

    private String description;

    private String userId;

    private String eventImage;

    private String team1;

    private String team2;

    public String getTeam1Name ()
    {
        return team1Name;
    }

    public void setTeam1Name (String team1Name)
    {
        this.team1Name = team1Name;
    }



    public String getTeam2Id ()
    {
        return team2Id;
    }

    public void setTeam2Id (String team2Id)
    {
        this.team2Id = team2Id;
    }

    public String getLocationUrl ()
    {
        return locationUrl;
    }

    public void setLocationUrl (String locationUrl)
    {
        this.locationUrl = locationUrl;
    }

    public String getTeam2Name ()
    {
        return team2Name;
    }

    public void setTeam2Name (String team2Name)
    {
        this.team2Name = team2Name;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getTeam2ProfilePicture ()
    {
        return team2ProfilePicture;
    }

    public void setTeam2ProfilePicture (String team2ProfilePicture)
    {
        this.team2ProfilePicture = team2ProfilePicture;
    }

    public String getTeam1Id ()
    {
        return team1Id;
    }

    public void setTeam1Id (String team1Id)
    {
        this.team1Id = team1Id;
    }


    public String getEventType ()
    {
        return eventType;
    }

    public void setEventType (String eventType)
    {
        this.eventType = eventType;
    }

    public String getTeam1ProfilePicture ()
    {
        return team1ProfilePicture;
    }

    public void setTeam1ProfilePicture (String team1ProfilePicture)
    {
        this.team1ProfilePicture = team1ProfilePicture;
    }




    public String getStatusId ()
    {
        return statusId;
    }

    public void setStatusId (String statusId)
    {
        this.statusId = statusId;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    private String shortDayName;

    private String time;

    private String month;

    private String monthName;

    private String year;

    private String ShortMonthName;

    private String dayName;

    private String date;

    private String datetime;








    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getEventImage ()
    {
        return eventImage;
    }

    public void setEventImage (String eventImage)
    {
        this.eventImage = eventImage;
    }

    public String getTeam1 ()
    {
        return team1;
    }

    public void setTeam1 (String team1)
    {
        this.team1 = team1;
    }

    public String getTeam2 ()
    {
        return team2;
    }

    public void setTeam2 (String team2)
    {
        this.team2 = team2;
    }
}
