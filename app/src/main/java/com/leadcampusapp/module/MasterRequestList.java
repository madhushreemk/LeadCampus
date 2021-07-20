package com.leadcampusapp.module;


import java.util.ArrayList;

/**
 * Created by User on 10/13/2017.
 */

public class MasterRequestList {

    String Lead_Id;
    String StudentName;
    String isApply_MasterLeader;
    String isApply_LeadAmbassador;
    String Student_Type;
    String Status;

    public static ArrayList<MasterRequestList> listview_arr=new ArrayList<MasterRequestList>();

    public MasterRequestList(String lead_Id, String studentName, String isApply_MasterLeader, String isApply_LeadAmbassador, String student_Type) {
        Lead_Id = lead_Id;
        StudentName = studentName;
        this.isApply_MasterLeader = isApply_MasterLeader;
        this.isApply_LeadAmbassador = isApply_LeadAmbassador;
        Student_Type = student_Type;
    }

    public String getLead_Id() {
        return Lead_Id;
    }

    public void setLead_Id(String lead_Id) {
        Lead_Id = lead_Id;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getIsApply_MasterLeader() {
        return isApply_MasterLeader;
    }

    public void setIsApply_MasterLeader(String isApply_MasterLeader) {
        this.isApply_MasterLeader = isApply_MasterLeader;
    }

    public String getIsApply_LeadAmbassador() {
        return isApply_LeadAmbassador;
    }

    public void setIsApply_LeadAmbassador(String isApply_LeadAmbassador) {
        this.isApply_LeadAmbassador = isApply_LeadAmbassador;
    }

    public String getStudent_Type() {
        return Student_Type;
    }

    public void setStudent_Type(String student_Type) {
        Student_Type = student_Type;
    }
}
