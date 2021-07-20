package com.leadcampusapp;

import com.leadcampusapp.module.UnapprovedProjectListModule;

import java.util.ArrayList;

public class PMUnapprovedFragmentModel {
    private String Name;
    private String college;
    private String project_title;
    private String budget;
    private String lead_id;
    private String proj_id;
    private String MobileNo;

    public static ArrayList<PMUnapprovedFragmentModel> listview_arrs=new ArrayList<PMUnapprovedFragmentModel>();


    public PMUnapprovedFragmentModel(String name, String college, String project_title, String budget,String lead_id,String proj_id,String mobileNo) {
        Name = name;
        this.college = college;
        this.project_title = project_title;
        this.budget = budget;
        this.lead_id = lead_id;
        this.proj_id = proj_id;
        this.MobileNo = mobileNo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getProject_title() {
        return project_title;
    }

    public void setProject_title(String project_title) {
        this.project_title = project_title;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getLead_id() {
        return lead_id;
    }

    public void setLead_id(String lead_id) {
        this.lead_id = lead_id;
    }

    public String getProj_id() {
        return proj_id;
    }

    public void setProj_id(String proj_id) {
        this.proj_id = proj_id;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }
}
