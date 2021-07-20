package com.leadcampusapp.module;


import java.util.ArrayList;

/**
 * Created by User on 10/13/2017.
 */

public class UnapprovedProjectList {

    int id;

    String Student_name;
    String College_name;
    String Project_tittle;
    String Amount;

    String Lead_id;
    String Project_Id;
    String MobileNo;
    String collegename;
    String streamname;

   /* String Project_type;
    String Benficiaries;
    String Objectivies;
    String Action_plan;*/

    public UnapprovedProjectList(){

    }

    //public UnapprovedProjectList(String student_name, String college_name, String project_tittle, String amount, String lead_id, String project_Id, String MobileNo,String collegenames, String stream_name) {

    public UnapprovedProjectList(String student_name, String college_name, String project_tittle, String amount, String lead_id, String project_Id, String MobileNo,String stream_name) {
        Student_name = student_name;
        College_name = college_name;
        Project_tittle = project_tittle;
        Amount = amount;
        Lead_id = lead_id;
        Project_Id = project_Id;
        streamname=stream_name;
        this.MobileNo=MobileNo;
       /* collegename=collegenames;
        streamname=stream_name;*/

    }
/*   public UnapprovedProjectList(String student_name, String college_name, String project_tittle, String amount, String lead_id,String Project_Id) {
        Student_name = student_name;
        College_name = college_name;
        Project_tittle = project_tittle;
        Amount = amount;
        Lead_id = lead_id;
        Project_Id= Project_Id;
    }*/

    /*  public UnapprovedProjectList(String student_name, String college_name, String project_tittle, String amount) {
        Student_name = student_name;
        College_name = college_name;
        Project_tittle = project_tittle;
        Amount = amount;
    }*/

    public static ArrayList<UnapprovedProjectList> listview_arr=new ArrayList<UnapprovedProjectList>();



    public String getstream_name() {
        return streamname;
    }

    public void setstream_name(String stream_name) {
        streamname = stream_name;
    }

    public String getProject_Id() {
        return Project_Id;
    }

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

    public String getLead_id() {
        return Lead_id;
    }

    public String getMobileNo() {
        return MobileNo;
    }



/*
    public String getcollegename() {
        return collegename;
    }

    public void setCollegename(String college_name) {
        collegename = college_name;
    }


    public String getstreamname() {
        return collegename;
    }

    public void setstreamname(String stream_name) {
        streamname = stream_name;
    }*/

}
