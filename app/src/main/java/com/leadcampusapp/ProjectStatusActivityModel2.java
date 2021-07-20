package com.leadcampusapp;

/**
 * Created by Admin on 28-05-2018.
 */

public class ProjectStatusActivityModel2 {
    private String project_name;
    private String approved_amt;
    private int status;
    private String dispersed_amt;
    private String rating;
    private boolean isCompletedFlag;
    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public ProjectStatusActivityModel2(String project_name, String approved_amt, int status, String dispersed_amt, String rating, boolean isCompletedFlag, String projectId) {
        this.project_name = project_name;
        this.approved_amt = approved_amt;
        this.status = status;
        this.dispersed_amt = dispersed_amt;
        this.rating = rating;
        this.isCompletedFlag = isCompletedFlag;
        this.projectId = projectId;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public boolean isCompletedFlag() {
        return isCompletedFlag;
    }

    public void setCompletedFlag(boolean completedFlag) {
        isCompletedFlag = completedFlag;
    }
}
