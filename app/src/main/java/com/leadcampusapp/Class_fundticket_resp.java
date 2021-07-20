package com.leadcampusapp;


/*
anyType{vmGet_Funding_Status=anyType{Ticket_Id=1; Requeted_By=Abhinandan;
        Requested_Date=04-05-2021; Approved_By=Pending; Approved_Date= ;
        Approved_Remark= ; Approval_Status=Pending; Requested_Project=2;
        Manager_Approved_Project=0; Account_Approved_Project=0; Requested_Amount=200;
        Manager_Approved_Amount=0; Account_Approved_Amount=0; status=success; };
*/

public class Class_fundticket_resp {


    String str_ticketid;
    String str_requestedby;
    String str_requesteddate;
    String str_approvedby;
    String str_approveddate;
    String str_approvedremarks;
    String str_approvalstatus;
    String str_requestedproject;
    String str_managerapprovedproject;
    String str_accountapprovedprojects;
    String str_requestedamount;
    String str_managerapprovedamount;
    String str_accountapprovedamount;
    String str_status;


    public String getStr_ticketid() {
        return str_ticketid;
    }

    public void setStr_ticketid(String str_ticketid) {
        this.str_ticketid = str_ticketid;
    }

    public String getStr_requestedby() {
        return str_requestedby;
    }

    public void setStr_requestedby(String str_requestedby) {
        this.str_requestedby = str_requestedby;
    }

    public String getStr_requesteddate() {
        return str_requesteddate;
    }

    public void setStr_requesteddate(String str_requesteddate) {
        this.str_requesteddate = str_requesteddate;
    }

    public String getStr_approvedby() {
        return str_approvedby;
    }

    public void setStr_approvedby(String str_approvedby) {
        this.str_approvedby = str_approvedby;
    }

    public String getStr_approveddate() {
        return str_approveddate;
    }

    public void setStr_approveddate(String str_approveddate) {
        this.str_approveddate = str_approveddate;
    }

    public String getStr_approvedremarks() {
        return str_approvedremarks;
    }

    public void setStr_approvedremarks(String str_approvedremarks) {
        this.str_approvedremarks = str_approvedremarks;
    }

    public String getStr_approvalstatus() {
        return str_approvalstatus;
    }

    public void setStr_approvalstatus(String str_approvalstatus) {
        this.str_approvalstatus = str_approvalstatus;
    }

    public String getStr_requestedproject() {
        return str_requestedproject;
    }

    public void setStr_requestedproject(String str_requestedproject) {
        this.str_requestedproject = str_requestedproject;
    }

    public String getStr_managerapprovedproject() {
        return str_managerapprovedproject;
    }

    public void setStr_managerapprovedproject(String str_managerapprovedproject) {
        this.str_managerapprovedproject = str_managerapprovedproject;
    }

    public String getStr_accountapprovedprojects() {
        return str_accountapprovedprojects;
    }

    public void setStr_accountapprovedprojects(String str_accountapprovedprojects) {
        this.str_accountapprovedprojects = str_accountapprovedprojects;
    }

    public String getStr_requestedamount() {
        return str_requestedamount;
    }

    public void setStr_requestedamount(String str_requestedamount) {
        this.str_requestedamount = str_requestedamount;
    }

    public String getStr_managerapprovedamount() {
        return str_managerapprovedamount;
    }

    public void setStr_managerapprovedamount(String str_managerapprovedamount) {
        this.str_managerapprovedamount = str_managerapprovedamount;
    }

    public String getStr_accountapprovedamount() {
        return str_accountapprovedamount;
    }

    public void setStr_accountapprovedamount(String str_accountapprovedamount) {
        this.str_accountapprovedamount = str_accountapprovedamount;
    }

    public String getStr_status() {
        return str_status;
    }

    public void setStr_status(String str_status) {
        this.str_status = str_status;
    }
}
