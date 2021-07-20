package com.leadcampusapp;

/**
 * Created by User on 14-Jun-19.
 */

public class ReqOpenModule {
    private String Ticket_No;
    private String Request_Date;
    private String Lead_Id;
    private String Student_Name;
    private String MobileNo;
    private String RequestHead_Id;
    private String Project_Id;
    private String Request_type;
    private String Request_Message;
    private String Response_Message;
    private String Request_Priority;
    private String College_Name;
    private String Project_Name;
    private String MailId;
    private String Prog_MailID;
    private String Status;

    public ReqOpenModule(String ticket_No, String request_Date, String lead_Id, String student_Name, String mobileNo, String requestHead_Id, String project_Id, String request_type, String request_Message, String request_Priority, String college_Name,String project_Name,String mailId,String prog_MailId) {
        Ticket_No = ticket_No;
        Request_Date = request_Date;
        Lead_Id = lead_Id;
        Student_Name = student_Name;
        MobileNo = mobileNo;
        RequestHead_Id = requestHead_Id;
        Project_Id = project_Id;
        Request_type = request_type;
        Request_Message = request_Message;
        Request_Priority = request_Priority;
        College_Name = college_Name;
        Project_Name = project_Name;
        MailId = mailId;
        Prog_MailID = prog_MailId;
    }

    public String getTicket_No() {
        return Ticket_No;
    }

    public void setTicket_No(String ticket_No) {
        Ticket_No = ticket_No;
    }

    public String getRequest_Date() {
        return Request_Date;
    }

    public void setRequest_Date(String request_Date) {
        Request_Date = request_Date;
    }

    public String getLead_Id() {
        return Lead_Id;
    }

    public void setLead_Id(String lead_Id) {
        Lead_Id = lead_Id;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getRequestHead_Id() {
        return RequestHead_Id;
    }

    public void setRequestHead_Id(String requestHead_Id) {
        RequestHead_Id = requestHead_Id;
    }

    public String getProject_Id() {
        return Project_Id;
    }

    public void setProject_Id(String project_Id) {
        Project_Id = project_Id;
    }

    public String getRequest_type() {
        return Request_type;
    }

    public void setRequest_type(String request_type) {
        Request_type = request_type;
    }

    public String getRequest_Message() {
        return Request_Message;
    }

    public void setRequest_Message(String request_Message) {
        Request_Message = request_Message;
    }

    public String getResponse_Message() {
        return Response_Message;
    }

    public void setResponse_Message(String response_Message) {
        Response_Message = response_Message;
    }

    public String getRequest_Priority() {
        return Request_Priority;
    }

    public void setRequest_Priority(String request_Priority) {
        Request_Priority = request_Priority;
    }

    public String getCollege_Name() {
        return College_Name;
    }

    public void setCollege_Name(String college_Name) {
        College_Name = college_Name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
    /*  <Ticket_No>string</Ticket_No>
          <Request_Date>string</Request_Date>
          <Lead_Id>string</Lead_Id>
          <Student_Name>string</Student_Name>
          <MobileNo>string</MobileNo>
          <RequestHead_Id>string</RequestHead_Id>
          <Project_Id>string</Project_Id>
          <Request_type>string</Request_type>
          <Request_Message>string</Request_Message>
          <Response_Message>string</Response_Message>
          <Request_Priority>string</Request_Priority>
          <College_Name>string</College_Name>
          <Status>string</Status>*/


    public String getProject_Name() {
        return Project_Name;
    }

    public void setProject_Name(String project_Name) {
        Project_Name = project_Name;
    }

    public String getMailId() {
        return MailId;
    }

    public void setMailId(String mailId) {
        MailId = mailId;
    }

    public String getProg_MailID() {
        return Prog_MailID;
    }

    public void setProg_MailID(String prog_MailID) {
        Prog_MailID = prog_MailID;
    }
}
