package com.leadcampusapp.module;

/**
 * Created by Admin on 28-05-2018.
 */

public class NotificationActivityModel {
    private String Notification_Type;
    private String Notification_Message;
    private String Notification_Date;
    private String Status;

    public NotificationActivityModel(String notification_Type, String notification_Message, String notification_Date) {
        Notification_Type = notification_Type;
        Notification_Message = notification_Message;
        Notification_Date = notification_Date;
    }

    public String getNotification_Type() {
        return Notification_Type;
    }

    public void setNotification_Type(String notification_Type) {
        Notification_Type = notification_Type;
    }

    public String getNotification_Message() {
        return Notification_Message;
    }

    public void setNotification_Message(String notification_Message) {
        Notification_Message = notification_Message;
    }

    public String getNotification_Date() {
        return Notification_Date;
    }

    public void setNotification_Date(String notification_Date) {
        Notification_Date = notification_Date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
