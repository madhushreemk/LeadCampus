package com.leadcampusapp;

/**
 * Created by Admin on 28-05-2018.
 */

public class TshirtPaidModel {
    private String student_name;
    private String lead_id;
    private String college_name;
    private String phone_number;
    private String TshirtSize;
    private String projectcount;
    private String RequestedId;
    private String status;

    public String getTshirtSize() {
        return TshirtSize;
    }

    public void setTshirtSize(String tshirtSize) {
        TshirtSize = tshirtSize;
    }

    public String getProjectcount() {
        return projectcount;
    }

    public void setProjectcount(String projectcount) {
        this.projectcount = projectcount;
    }

    public String getRequestedId() {
        return RequestedId;
    }

    public void setRequestedId(String requestedId) {
        RequestedId = requestedId;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   /* public TshirtPaidModel(String student_name, String lead_id, String college_name, String phone_number, String status) {
        this.student_name = student_name;
        this.lead_id = lead_id;
        this.college_name = college_name;
        this.phone_number = phone_number;
        this.status = status;
    }*/

    public TshirtPaidModel(String student_name, String lead_id, String college_name, String phone_number, String tshirtSize, String projectcount, String requestedId, String status) {
        this.student_name = student_name;
        this.lead_id = lead_id;
        this.college_name = college_name;
        this.phone_number = phone_number;
        TshirtSize = tshirtSize;
        this.projectcount = projectcount;
        RequestedId = requestedId;
        this.status = status;
    }
}
