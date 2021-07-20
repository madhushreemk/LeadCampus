package com.leadcampusapp.module;


import java.util.ArrayList;

/**
 * Created by User on 10/13/2017.
 */

public class ApprovedProjectListModule {

    String Student_name;
    String Project_tittle;
    String Approved_amount;
    String Disperse_amount;
    String Balance_amount;
    String LeadId;
    String PDId;
    String PMAppr_collegename;
    String PMAppr_streamname;
    public static ArrayList<ApprovedProjectListModule> listview_arr=new ArrayList<ApprovedProjectListModule>();

    public ApprovedProjectListModule(){

    }

    public ApprovedProjectListModule(String student_name, String project_tittle, String approved_amount, String disperse_amount, String balance_amount, String leadId, String PDId,
                                     String pmappr_college_name,String pmappr_stream_name ) {
        Student_name = student_name;
        Project_tittle = project_tittle;
        Approved_amount = approved_amount;
        Disperse_amount = disperse_amount;
        Balance_amount = balance_amount;
        LeadId = leadId;
        PMAppr_collegename=pmappr_college_name;
        PMAppr_streamname=pmappr_stream_name;
        this.PDId = PDId;
    }

    public String getLeadId() {
        return LeadId;
    }

    public void setLeadId(String leadId) {
        LeadId = leadId;
    }

    public String getPDId() {
        return PDId;
    }

    public void setPDId(String PDId) {
        this.PDId = PDId;
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

    public String getApproved_amount() {
        return Approved_amount;
    }

    public void setApproved_amount(String approved_amount) {
        Approved_amount = approved_amount;
    }

    public String getDisperse_amount() {
        return Disperse_amount;
    }

    public void setDisperse_amount(String disperse_amount) {
        Disperse_amount = disperse_amount;
    }

    public String getBalance_amount() {
        return Balance_amount;
    }

    public void setBalance_amount(String balance_amount) {
        Balance_amount = balance_amount;
    }



    public String getpmappr_collegename() {
        return PMAppr_collegename;
    }
    public void setpmappr_collegename(String pmappr_college_name) {
        PMAppr_collegename = pmappr_college_name;
    }


    public String getpmappr_streamname() {
        return PMAppr_streamname;
    }

    public void setpmappr_streamname(String pmappr_stream_name) {
        this.PMAppr_streamname = pmappr_stream_name;
    }
}
