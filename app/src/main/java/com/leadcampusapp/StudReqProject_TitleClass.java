package com.leadcampusapp;

public class StudReqProject_TitleClass {

    int ID;
    String pdid;
    String proTitle;
    String proStatus;

    public StudReqProject_TitleClass(){

    }
    public StudReqProject_TitleClass(int ID, String pdid, String proTitle, String proStatus) {
        this.ID = ID;
        this.pdid = pdid;
        this.proTitle = proTitle;
        this.proStatus = proStatus;
    }

    public StudReqProject_TitleClass(String pdid, String proTitle, String proStatus) {
        this.pdid = pdid;
        this.proTitle = proTitle;
        this.proStatus = proStatus;
    }

    public String getPdid() {
        return pdid;
    }

    public void setPdid(String pdid) {
        this.pdid = pdid;
    }

    public String getProTitle() {
        return proTitle;
    }

    public void setProTitle(String proTitle) {
        this.proTitle = proTitle;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }
}
