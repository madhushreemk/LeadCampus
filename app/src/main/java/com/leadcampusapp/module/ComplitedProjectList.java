package com.leadcampusapp.module;


import java.util.ArrayList;

/**
 * Created by User on 10/13/2017.
 */

public class ComplitedProjectList {

    int id;

    String Student_name;
    String College_name;
    String Project_tittle;
    String Sanction_amount;
    String LeadId;
    String ProjectId;
    String MobileNo;
    String Student_Image_Path;
    String PMcomp_collegename;
    String PMcomp_stream_name;

    String Is_ImpactProject;

    public String getLeadId() {
        return LeadId;
    }

    public void setLeadId(String leadId) {
        LeadId = leadId;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String projectId) {
        ProjectId = projectId;
    }


    public String get_is_impactproject() {
        return Is_ImpactProject;
    }

    public void set_is_impactproject(String impactproject) {
        Is_ImpactProject = impactproject;
    }


    public ComplitedProjectList(){

    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getStudent_Image_Path() {
        return Student_Image_Path;
    }

    public ComplitedProjectList(String student_name, String college_name, String project_tittle, String sanction_amount, String leadId, String projectId, String MobileNo, String Student_Image_Path,
                                String pmcomp_collegename,String pmcomp_streamname,String impactproject) {
        Student_name = student_name;
        College_name = college_name;
        Project_tittle = project_tittle;
        Sanction_amount = sanction_amount;
        LeadId = leadId;
        ProjectId = projectId;
        PMcomp_collegename=pmcomp_collegename;
        PMcomp_stream_name=pmcomp_streamname;
        this.MobileNo=MobileNo;
        this.Student_Image_Path=Student_Image_Path;
        this.Is_ImpactProject=impactproject;
    }

    public static ArrayList<ComplitedProjectList> listview_arr=new ArrayList<ComplitedProjectList>();


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
        return Sanction_amount;
    }

    public void setAmount(String amount) {
        Sanction_amount = amount;
    }



    public String getpmcomp_collegename() {
        return PMcomp_collegename;
    }

    public void setpmcomp_collegename(String pmcomp_collegename) {
        this.PMcomp_collegename = pmcomp_collegename;
    }

    public String getpmcomp_stream_name() {
        return PMcomp_stream_name;
    }

    public void setpmcomp_stream_name(String pmcomp_stream_name) {
        this.PMcomp_stream_name = pmcomp_stream_name;
    }
}
