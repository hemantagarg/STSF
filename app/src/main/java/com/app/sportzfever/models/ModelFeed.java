package com.app.sportzfever.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hemanta on 30-08-2017.
 */

public class ModelFeed implements Serializable {

    private int rowType;
    private String message, date, feedId,teamId;

    private String originalUserProfilePicture;

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    private String dateString;

    private String dateTime;

    private String statusType;

    private String originalUserName;

    private String originalAvatarSportName;

    private String originalStatusId;

    private String id;

    private String avatarName;

    private String originalAvatarName;

    private String originalAvatarProfilePicture;

    private String avatarType;

    private String dateTimeUpdated;

    private String event,eventTitle;

    private String description;

    private String userName;
    private int commentsCount = 0, likeCount = 0, shareCount = 0;

    private String originalAvatarType;

    private String originalAvatar;

    private String originalUser, isLiked="0";

    private String userProfilePicture;

    private String avatarSportName;

    private String avatarProfilePicture;

    private String avatar;

    private String shares;

    private ArrayList<Images> images = new ArrayList<>();

    private ArrayList<Likes> likes = new ArrayList<>();

    private String statusVisiblity;

    private String isShared;

    private String user;

    private ArrayList<Comments> comments = new ArrayList<>();

    public String getOriginalUserProfilePicture() {
        return originalUserProfilePicture;
    }

    public void setOriginalUserProfilePicture(String originalUserProfilePicture) {
        this.originalUserProfilePicture = originalUserProfilePicture;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getOriginalUserName() {
        return originalUserName;
    }

    public void setOriginalUserName(String originalUserName) {
        this.originalUserName = originalUserName;
    }

    public String getOriginalAvatarSportName() {
        return originalAvatarSportName;
    }

    public void setOriginalAvatarSportName(String originalAvatarSportName) {
        this.originalAvatarSportName = originalAvatarSportName;
    }

    public String getOriginalStatusId() {
        return originalStatusId;
    }

    public void setOriginalStatusId(String originalStatusId) {
        this.originalStatusId = originalStatusId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    public String getOriginalAvatarName() {
        return originalAvatarName;
    }

    public void setOriginalAvatarName(String originalAvatarName) {
        this.originalAvatarName = originalAvatarName;
    }

    public ArrayList<Likes> getLikes() {
        return likes;
    }

    public String getOriginalAvatarProfilePicture() {
        return originalAvatarProfilePicture;
    }

    public void setOriginalAvatarProfilePicture(String originalAvatarProfilePicture) {
        this.originalAvatarProfilePicture = originalAvatarProfilePicture;
    }

    public String getAvatarType() {
        return avatarType;
    }

    public void setAvatarType(String avatarType) {
        this.avatarType = avatarType;
    }

    public String getDateTimeUpdated() {
        return dateTimeUpdated;
    }

    public void setDateTimeUpdated(String dateTimeUpdated) {
        this.dateTimeUpdated = dateTimeUpdated;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOriginalAvatarType() {
        return originalAvatarType;
    }

    public void setOriginalAvatarType(String originalAvatarType) {
        this.originalAvatarType = originalAvatarType;
    }

    public String getOriginalAvatar() {
        return originalAvatar;
    }

    public void setOriginalAvatar(String originalAvatar) {
        this.originalAvatar = originalAvatar;
    }

    public String getOriginalUser() {
        return originalUser;
    }

    public void setOriginalUser(String originalUser) {
        this.originalUser = originalUser;
    }

    public String getUserProfilePicture() {
        return userProfilePicture;
    }

    public void setUserProfilePicture(String userProfilePicture) {
        this.userProfilePicture = userProfilePicture;
    }

    public String getAvatarSportName() {
        return avatarSportName;
    }

    public void setAvatarSportName(String avatarSportName) {
        this.avatarSportName = avatarSportName;
    }

    public String getAvatarProfilePicture() {
        return avatarProfilePicture;
    }

    public void setAvatarProfilePicture(String avatarProfilePicture) {
        this.avatarProfilePicture = avatarProfilePicture;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public String getStatusVisiblity() {
        return statusVisiblity;
    }

    public void setStatusVisiblity(String statusVisiblity) {
        this.statusVisiblity = statusVisiblity;
    }

    public String getIsShared() {
        return isShared;
    }

    public void setIsShared(String isShared) {
        this.isShared = isShared;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<Comments> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comments> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "ClassPojo [originalUserProfilePicture = " + originalUserProfilePicture + ", dateTime = " + dateTime + ", statusType = " + statusType + ", originalUserName = " + originalUserName + ", originalAvatarSportName = " + originalAvatarSportName + ", originalStatusId = " + originalStatusId + ", id = " + id + ", avatarName = " + avatarName + ", originalAvatarName = " + originalAvatarName + ", originalAvatarProfilePicture = " + originalAvatarProfilePicture + ", avatarType = " + avatarType + ", dateTimeUpdated = " + dateTimeUpdated + ", event = " + event + ", description = " + description + ", userName = " + userName + ", originalAvatarType = " + originalAvatarType + ", originalAvatar = " + originalAvatar + ", originalUser = " + originalUser + ", userProfilePicture = " + userProfilePicture + ", avatarSportName = " + avatarSportName + ", avatarProfilePicture = " + avatarProfilePicture + ", avatar = " + avatar + ", shares = " + shares + ", likes = " + likes + ", statusVisiblity = " + statusVisiblity + ", isShared = " + isShared + ", user = " + user + ", comments = " + comments + "]";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public void setLikes(ArrayList<Likes> likes) {
        this.likes = likes;
    }

    public ArrayList<Images> getImages() {
        return images;
    }

    public void setImages(ArrayList<Images> images) {
        this.images = images;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public String getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(String isLiked) {
        this.isLiked = isLiked;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }
}
