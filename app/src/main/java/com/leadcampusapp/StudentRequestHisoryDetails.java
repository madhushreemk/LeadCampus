package com.leadcampusapp;

class StudentRequestHisoryDetails {

    int id;
    String req_id;
    String req_date;
    String req_msg;
    String req_priority;
    String req_status;
    String headid;
    String req_type;
    String pro_id;
    String pro_title;
    String resp_date;
    String resp_msg;
    String status;

    public StudentRequestHisoryDetails(){}
    public StudentRequestHisoryDetails(int id, String req_id, String req_date, String req_msg, String req_priority, String req_status, String headid, String req_type, String pro_id, String pro_title, String resp_date, String resp_msg, String status) {
        this.id = id;
        this.req_id = req_id;
        this.req_date = req_date;
        this.req_msg = req_msg;
        this.req_priority = req_priority;
        this.req_status = req_status;
        this.headid = headid;
        this.req_type = req_type;
        this.pro_id = pro_id;
        this.pro_title = pro_title;
        this.resp_date = resp_date;
        this.resp_msg = resp_msg;
        this.status = status;
    }

    public StudentRequestHisoryDetails(String req_date, String req_msg, String req_priority, String headid, String req_type, String pro_id, String pro_title, String resp_date, String resp_msg, String status) {
        this.req_date = req_date;
        this.req_msg = req_msg;
        this.req_priority = req_priority;
        this.headid = headid;
        this.req_type = req_type;
        this.pro_id = pro_id;
        this.pro_title = pro_title;
        this.resp_date = resp_date;
        this.resp_msg = resp_msg;
        this.status = status;
    }

    public String getReq_id() {
        return req_id;
    }

    public void setReq_id(String req_id) {
        this.req_id = req_id;
    }

    public String getReq_date() {
        return req_date;
    }

    public void setReq_date(String req_date) {
        this.req_date = req_date;
    }

    public String getReq_msg() {
        return req_msg;
    }

    public void setReq_msg(String req_msg) {
        this.req_msg = req_msg;
    }

    public String getReq_priority() {
        return req_priority;
    }

    public void setReq_priority(String req_priority) {
        this.req_priority = req_priority;
    }

    public String getReq_status() {
        return req_status;
    }

    public void setReq_status(String req_status) {
        this.req_status = req_status;
    }

    public String getHeadid() {
        return headid;
    }

    public void setHeadid(String headid) {
        this.headid = headid;
    }

    public String getReq_type() {
        return req_type;
    }

    public void setReq_type(String req_type) {
        this.req_type = req_type;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getPro_title() {
        return pro_title;
    }

    public void setPro_title(String pro_title) {
        this.pro_title = pro_title;
    }

    public String getResp_date() {
        return resp_date;
    }

    public void setResp_date(String resp_date) {
        this.resp_date = resp_date;
    }

    public String getResp_msg() {
        return resp_msg;
    }

    public void setResp_msg(String resp_msg) {
        this.resp_msg = resp_msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
