package com.leadcampusapp;

/**
 * Created by Admin on 28-05-2018.
 */

public class ProjectStatusActivityModel {
    private String project_name;
    private String project_type;
    private String approved_amt;
    private String dispersed_amt;
    //private String amt_app;
    private int status;
    private String completion_amt;

    public ProjectStatusActivityModel(String project_name, String project_type, String approved_amt, String dispersed_amt, int status, String completion_amt) {
        this.project_name = project_name;
        this.project_type = project_type;
        this.approved_amt = approved_amt;
        this.dispersed_amt = dispersed_amt;
        this.status = status;
        this.completion_amt = completion_amt;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getApproved_amt() {
        return approved_amt;
    }

    public void setApproved_amt(String approved_amt) {
        this.approved_amt = approved_amt;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDispersed_amt() {
        return dispersed_amt;
    }

    public void setDispersed_amt(String dispersed_amt) {
        this.dispersed_amt = dispersed_amt;
    }

    public String getCompletion_amt() {
        return completion_amt;
    }

    public void setCompletion_amt(String completion_amt) {
        this.completion_amt = completion_amt;
    }
    //private String leadFundedStatus;

/*    public ProjectStatusActivityModel(String sNo, String typeOfSeasonal, String documentation, String assurance, String duedate) {
        this.sNo = sNo;
        this.typeOfSeasonal = typeOfSeasonal;
        this.documentation = documentation;
        this.assurance = assurance;
        this.duedate = duedate;
    }

    public String getsNo() {
        return sNo;
    }

    public String getTypeOfSeasonal() {
        return typeOfSeasonal;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getAssurance() {
        return assurance;
    }

    public String getDuedate() {
        return duedate;
    }*/



 /*   public ProjectStatusActivityModel(String project_name, String project_type, String amt_app, int status, String leadFundedStatus) {
        this.project_name = project_name;
        this.project_type = project_type;
        this.amt_app = amt_app;
        this.status = status;
        this.leadFundedStatus = leadFundedStatus;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getAmt_app() {
        return amt_app;
    }

    public void setAmt_app(String amt_app) {
        this.amt_app = amt_app;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLeadFundedStatus() {
        return leadFundedStatus;
    }

    public void setLeadFundedStatus(String leadFundedStatus) {
        this.leadFundedStatus = leadFundedStatus;
    }*/
}
