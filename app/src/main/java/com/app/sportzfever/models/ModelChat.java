package com.app.sportzfever.models;

import java.io.Serializable;

/**
 * Created by hemanta on 24-03-2017.
 */

public class ModelChat implements Serializable {

    private String username;
    private String userId;
    private String date;

    private String unreadCount;
    private String is_read;
    private String senderName;
    private String userImage = "", searchId;
    private String requestId;
    private int rowType = 1;
    private String receiverImage;
    private String date_time;
    private String sender_name = "";
    private String sender_id;
    private String reciever_id;
    private String receiverName;

    private String senderid;

    private String message;

    private String sentTime;

    private String receiverId;

    private String recieverName;

    private String recieverProfilePic;

    private String sentDay;

    private String senderPic;

    private String senderUsername;

    private String sentOn;

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }


    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public String getRecieverProfilePic() {
        return recieverProfilePic;
    }

    public void setRecieverProfilePic(String recieverProfilePic) {
        this.recieverProfilePic = recieverProfilePic;
    }

    public String getSentDay() {
        return sentDay;
    }

    public void setSentDay(String sentDay) {
        this.sentDay = sentDay;
    }

    public String getSenderPic() {
        return senderPic;
    }

    public void setSenderPic(String senderPic) {
        this.senderPic = senderPic;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSentOn() {
        return sentOn;
    }

    public void setSentOn(String sentOn) {
        this.sentOn = sentOn;
    }

    @Override
    public String toString() {
        return "ClassPojo [senderid = " + senderid + ", message = " + message + ", sentTime = " + sentTime + ", receiverId = " + receiverId + ", recieverName = " + recieverName + ", recieverProfilePic = " + recieverProfilePic + ", sentDay = " + sentDay + ", senderPic = " + senderPic + ", senderUsername = " + senderUsername + ", sentOn = " + sentOn + "]";
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReciever_id() {
        return reciever_id;
    }

    public void setReciever_id(String reciever_id) {
        this.reciever_id = reciever_id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }


    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }


    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(String unreadCount) {
        this.unreadCount = unreadCount;
    }

    public String getIs_read() {
        return is_read;
    }

    public void setIs_read(String is_read) {
        this.is_read = is_read;
    }


    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }


    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }


    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
