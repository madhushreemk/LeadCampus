package com.leadcampusapp;

/**
 * Created by User on 16-Aug-19.
 */

public class Class_ChatModule {
    //anyType{vmGetMentorMentee=anyType{Comments=testing; UserType=Manager; ManagerName=Anisha Cardoza; StudentName=Mallikarjun kumbar; ReplyTime=14-08-19 02:52:24 PM; ProjectStatus=anyType{}; Status=Success; }; }
    String Comments;
    String UserType;
    String ManagerName;
    String StudentName;
    String ReplyTime;
    String ProjectStatus;
    String Status;

    public Class_ChatModule(String comments, String userType, String managerName, String studentName, String replyTime, String projectStatus, String status) {
        Comments = comments;
        UserType = userType;
        ManagerName = managerName;
        StudentName = studentName;
        ReplyTime = replyTime;
        ProjectStatus = projectStatus;
        Status = status;
    }

    public Class_ChatModule() {

    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getReplyTime() {
        return ReplyTime;
    }

    public void setReplyTime(String replyTime) {
        ReplyTime = replyTime;
    }

    public String getProjectStatus() {
        return ProjectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        ProjectStatus = projectStatus;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
