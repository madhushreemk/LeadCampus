package com.leadcampusapp;

/**
 * Created by User on 14-Jun-19.
 */

public class PrincipleCollegeModule {
    /*private ArrayList<String> LeadId;
    private ArrayList<String> StudentName;
    private ArrayList<String> ProjectTitle;

    public PrincipleCollegeModule(ArrayList<String> leadId, ArrayList<String> studentName, ArrayList<String> projectTitle) {
        LeadId = leadId;
        StudentName = studentName;
        ProjectTitle = projectTitle;
    }

    public ArrayList<String> getLeadId() {
        return LeadId;
    }

    public void setLeadId(ArrayList<String> leadId) {
        LeadId = leadId;
    }

    public ArrayList<String> getStudentName() {
        return StudentName;
    }

    public void setStudentName(ArrayList<String> studentName) {
        StudentName = studentName;
    }

    public ArrayList<String> getProjectTitle() {
        return ProjectTitle;
    }

    public void setProjectTitle(ArrayList<String> projectTitle) {
        ProjectTitle = projectTitle;
    }*/

    private String LeadId;
    private String StudentName;
    private String ProjectTitle;

    public PrincipleCollegeModule(String leadId, String studentName, String projectTitle) {
        LeadId = leadId;
        StudentName = studentName;
        ProjectTitle = projectTitle;
    }

    public String getLeadId() {
        return LeadId;
    }

    public void setLeadId(String leadId) {
        LeadId = leadId;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getProjectTitle() {
        return ProjectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        ProjectTitle = projectTitle;
    }
}
