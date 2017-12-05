package com.app.sportzfever.models;

/**
 * Created by hemanta on 30-08-2017.
 */

public class ModelNotification {


    private int rowType;


    public int getRowType() {
        return rowType;
    }

    public void setRowType(int rowType) {
        this.rowType = rowType;
    }
    private String id;

    private String readStatus;

    private String isTeamNotification;

    private String notificationText;

    private String actionId;


    private String userPorfile;

    private String datetime;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getReadStatus ()
    {
        return readStatus;
    }

    public void setReadStatus (String readStatus)
    {
        this.readStatus = readStatus;
    }

    public String getIsTeamNotification ()
    {
        return isTeamNotification;
    }

    public void setIsTeamNotification (String isTeamNotification)
    {
        this.isTeamNotification = isTeamNotification;
    }

    public String getNotificationText ()
    {
        return notificationText;
    }

    public void setNotificationText (String notificationText)
    {
        this.notificationText = notificationText;
    }

    public String getActionId ()
    {
        return actionId;
    }

    public void setActionId (String actionId)
    {
        this.actionId = actionId;
    }



    public String getUserPorfile ()
    {
        return userPorfile;
    }

    public void setUserPorfile (String userPorfile)
    {
        this.userPorfile = userPorfile;
    }

    public String getDatetime ()
    {
        return datetime;
    }

    public void setDatetime (String datetime)
    {
        this.datetime = datetime;
    }

    private String shortDayName;

    private String time;

    private String month;

    private String monthName;

    private String year;

    private String ShortMonthName;

    private String dayName;

    private String date;

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
}
