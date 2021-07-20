package com.leadcampusapp;

/**
 * Created by Admin on 28-05-2018.
 */

public class FeesUnpaidModel {
    private String student_name;
    private String lead_id;
    private String registration_date;
    private String college_name;
    private String phone_number;
    private String status;

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

    public String getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(String registration_date) {
        this.registration_date = registration_date;
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

    public FeesUnpaidModel(String student_name, String lead_id, String registration_date, String college_name, String phone_number, String status) {
        this.student_name = student_name;
        this.lead_id = lead_id;
        this.registration_date = registration_date;
        this.college_name = college_name;
        this.phone_number = phone_number;
        this.status = status;
    }
}
