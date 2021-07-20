package com.leadcampusapp;

public class Class_Project_FundMain {

    String Slno;
    String Ticket_Status;
    String Manager_Alert_Days;
    String Account_Alert_Days;
    String Status;

    public String getSlno() {
        return Slno;
    }

    public void setSlno(String slno) {
        Slno = slno;
    }

    public String getTicket_Status() {
        return Ticket_Status;
    }

    public void setTicket_Status(String ticket_Status) {
        Ticket_Status = ticket_Status;
    }

    public String getManager_Alert_Days() {
        return Manager_Alert_Days;
    }

    public void setManager_Alert_Days(String manager_Alert_Days) {
        Manager_Alert_Days = manager_Alert_Days;
    }

    public String getAccount_Alert_Days() {
        return Account_Alert_Days;
    }

    public void setAccount_Alert_Days(String account_Alert_Days) {
        Account_Alert_Days = account_Alert_Days;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String toString()
    {
        return( this.Ticket_Status );
    }

}
