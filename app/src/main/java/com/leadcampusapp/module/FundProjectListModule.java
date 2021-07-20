package com.leadcampusapp.module;


import java.util.ArrayList;

/**
 * Created by User on 10/13/2017.
 */

public class FundProjectListModule {

    int id;
    String Lead_id;
    String Project_id;
    String Student_name;
    String Project_tittle;
    String SanctionAmount;
    String giventotal;
    String balanceAmount;

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getLead_id() {
        return Lead_id;
    }

    public void setLead_id(String lead_id) {
        Lead_id = lead_id;
    }
    public FundProjectListModule(){

    }

    public String getGiventotal() {
        return giventotal;
    }

    public void setGiventotal(String giventotal) {
        this.giventotal = giventotal;
    }

    public FundProjectListModule(String lead_id, String project_id, String student_name, String project_tittle, String sanctionAmount, String giventotal, String balanceAmount) {
        Lead_id = lead_id;
        Project_id = project_id;
        Student_name = student_name;
        Project_tittle = project_tittle;
        SanctionAmount = sanctionAmount;
        this.giventotal = giventotal;
        this.balanceAmount = balanceAmount;
    }

    public String getSanctionAmount() {
        return SanctionAmount;
    }

    public void setSanctionAmount(String sanctionAmount) {
        SanctionAmount = sanctionAmount;
    }

    public static ArrayList<FundProjectListModule> listview_arr=new ArrayList<FundProjectListModule>();

    public String getProject_id() {
        return Project_id;
    }

    public void setProject_id(String project_id) {
        Project_id = project_id;
    }

    public String getStudent_name() {
        return Student_name;
    }

    public void setStudent_name(String student_name) {
        Student_name = student_name;
    }

    public String getProject_tittle() {
        return Project_tittle;
    }

    public void setProject_tittle(String project_tittle) {
        Project_tittle = project_tittle;
    }

  }
