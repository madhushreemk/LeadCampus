package com.leadcampusapp;

import java.util.List;

public class Class_FundStudList {
    /* <Registration_Id>37003</Registration_Id>
            <Lead_Id>MH03842</Lead_Id>
            <Student_Name>One Test edit</Student_Name>
            <Mobile_No>7619298575</Mobile_No>
            <Email_Id>mallikarjun.tech@dfmail.com</Email_Id>
            <College_Name>Malnad College</College_Name>
            <Sem_Name>3rd year (6th sem)</Sem_Name>
            <Status>Success</Status>
            <Fund_Details>
                <vmGet_Released_Fund_Details>
                    <PDID>53433</PDID>
                    <Project_Title>First project</Project_Title>
                    <Requested_Amount>25</Requested_Amount>
                    <Santioned_Amount>12</Santioned_Amount>
                    <Released_Amount>12</Released_Amount>
                    <Total_Released_Amount>0</Total_Released_Amount>
                    <Balance_Amount>12</Balance_Amount>
                    <Status>success</Status>
                </vmGet_Released_Fund_Details>
                <vmGet_Released_Fund_Details>
                    <PDID>53435</PDID>
                    <Project_Title>Web Project modify in mobile</Project_Title>
                    <Requested_Amount>30</Requested_Amount>
                    <Santioned_Amount>10</Santioned_Amount>
                    <Released_Amount>10</Released_Amount>
                    <Total_Released_Amount>0</Total_Released_Amount>
                    <Balance_Amount>10</Balance_Amount>
                    <Status>success</Status>
                </vmGet_Released_Fund_Details>
            </Fund_Details>
        </vmGet_Student_List> */
    String Registration_Id;
    String Student_Name;
    String Mobile_No;
    String Email_Id;
    String Lead_Id;
    String College_Name;
    String Sem_Name;
    String Status;
    private Class_Fund_Details[] Fund_details;

    List<Class_Fund_Details> Fund_Details;

    public String getRegistration_Id() {
        return Registration_Id;
    }

    public void setRegistration_Id(String registration_Id) {
        Registration_Id = registration_Id;
    }

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getMobile_No() {
        return Mobile_No;
    }

    public void setMobile_No(String mobile_No) {
        Mobile_No = mobile_No;
    }

    public String getEmail_Id() {
        return Email_Id;
    }

    public void setEmail_Id(String email_Id) {
        Email_Id = email_Id;
    }

    public String getLead_Id() {
        return Lead_Id;
    }

    public void setLead_Id(String lead_Id) {
        Lead_Id = lead_Id;
    }

    public String getCollege_Name() {
        return College_Name;
    }

    public void setCollege_Name(String college_Name) {
        College_Name = college_Name;
    }

    public String getSem_Name() {
        return Sem_Name;
    }

    public void setSem_Name(String sem_Name) {
        Sem_Name = sem_Name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

   /* public List<Class_Fund_Details> getFund_Details() {
        return Fund_Details;
    }*/

    public void setFund_Details(List<Class_Fund_Details> fund_Details) {
        Fund_Details = fund_Details;
    }

    public Class_Fund_Details[] getFund_details() {
        return Fund_details;
    }

    public void setFund_details(Class_Fund_Details[] fund_details) {
        Fund_details = fund_details;
    }
}
