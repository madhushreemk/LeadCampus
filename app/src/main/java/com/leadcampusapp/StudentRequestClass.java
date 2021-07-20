package com.leadcampusapp;

public class StudentRequestClass {
    int id;
    String requestid;
    String requestname;
    String requeststatus;

    public StudentRequestClass(){

    }

    public StudentRequestClass(int id, String requestid, String requestname, String requeststatus) {
        this.id = id;
        this.requestid = requestid;
        this.requestname = requestname;
        this.requeststatus = requeststatus;
    }

    public StudentRequestClass(String requestid, String requestname, String requeststatus) {
        this.requestid = requestid;
        this.requestname = requestname;
        this.requeststatus = requeststatus;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public String getRequestname() {
        return requestname;
    }

    public void setRequestname(String requestname) {
        this.requestname = requestname;
    }

    public String getRequeststatus() {
        return requeststatus;
    }

    public void setRequeststatus(String requeststatus) {
        this.requeststatus = requeststatus;
    }
}
