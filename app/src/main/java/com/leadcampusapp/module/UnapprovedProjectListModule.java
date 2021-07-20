package com.leadcampusapp.module;


import java.util.ArrayList;

/**
 * Created by User on 10/13/2017.
 */

public class UnapprovedProjectListModule {

    int id;

    String Student_name;
    String College_name;
    String Project_tittle;
    String Amount;

    String Lead_id;
    String Project_type;
    String Benficiaries;
    String Objectivies;
    String Action_plan;
    String ProjectId;
    String collegename;
    String streamname;

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }

    public String getLead_id() {
        return Lead_id;
    }

    public void setLead_id(String lead_id) {
        Lead_id = lead_id;
    }

    public String getProject_type() {
        return Project_type;
    }

    public void setProject_type(String project_type) {
        Project_type = project_type;
    }

    public String getBenficiaries() {
        return Benficiaries;
    }

    public void setBenficiaries(String benficiaries) {
        Benficiaries = benficiaries;
    }

    public String getObjectivies() {
        return Objectivies;
    }

    public void setObjectivies(String objectivies) {
        Objectivies = objectivies;
    }

    public String getAction_plan() {
        return Action_plan;
    }

    public void setAction_plan(String action_plan) {
        Action_plan = action_plan;
    }

    public UnapprovedProjectListModule(){

    }

    public UnapprovedProjectListModule(String student_name, String college_name, String project_tittle, String amount,String stream_name) {
        Student_name = student_name;
        College_name = college_name;
        Project_tittle = project_tittle;
        Amount = amount;
        streamname=stream_name;
    }

    public static ArrayList<UnapprovedProjectListModule> listview_arr=new ArrayList<UnapprovedProjectListModule>();


    public String getStudent_name() {
        return Student_name;
    }

    public void setStudent_name(String student_name) {
        Student_name = student_name;
    }

    public String getCollege_name() {
        return College_name;
    }

    public void setCollege_name(String college_name) {
        College_name = college_name;
    }

    public String getProject_tittle() {
        return Project_tittle;
    }

    public void setProject_tittle(String project_tittle) {
        Project_tittle = project_tittle;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }




    public String getcollegename() {
        return collegename;
    }

    public void setcollegename(String college_name) {
        collegename = college_name;
    }


    public String getstreamname() {
        return streamname;
    }

    public void setstreamname(String stream_name) {
        streamname = stream_name;
    }

}
