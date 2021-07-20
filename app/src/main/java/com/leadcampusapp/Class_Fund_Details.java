package com.leadcampusapp;

public class Class_Fund_Details {
    /*
     <PDID>53433</PDID>
                    <Project_Title>First project</Project_Title>
                    <Requested_Amount>25</Requested_Amount>
                    <Santioned_Amount>12</Santioned_Amount>
                    <Released_Amount>12</Released_Amount>
                    <Total_Released_Amount>0</Total_Released_Amount>
                    <Balance_Amount>12</Balance_Amount>
                    <Status>success</Status>
     */

    String PDID;
    String Project_Title;
    String Requested_Amount;
    String Santioned_Amount;
    String Released_Amount;
    String Total_Released_Amount;
    String Balance_Amount;
    String Status;
    String Registration_Id;


    public String getPDID() {
        return PDID;
    }

    public void setPDID(String PDID) {
        this.PDID = PDID;
    }

    public String getProject_Title() {
        return Project_Title;
    }

    public void setProject_Title(String project_Title) {
        Project_Title = project_Title;
    }

    public String getRequested_Amount() {
        return Requested_Amount;
    }

    public void setRequested_Amount(String requested_Amount) {
        Requested_Amount = requested_Amount;
    }

    public String getSantioned_Amount() {
        return Santioned_Amount;
    }

    public void setSantioned_Amount(String santioned_Amount) {
        Santioned_Amount = santioned_Amount;
    }

    public String getReleased_Amount() {
        return Released_Amount;
    }

    public void setReleased_Amount(String released_Amount) {
        Released_Amount = released_Amount;
    }

    public String getTotal_Released_Amount() {
        return Total_Released_Amount;
    }

    public void setTotal_Released_Amount(String total_Released_Amount) {
        Total_Released_Amount = total_Released_Amount;
    }

    public String getBalance_Amount() {
        return Balance_Amount;
    }

    public void setBalance_Amount(String balance_Amount) {
        Balance_Amount = balance_Amount;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRegistration_Id() {
        return Registration_Id;
    }

    public void setRegistration_Id(String registration_Id) {
        Registration_Id = registration_Id;
    }

    public Class_Fund_Details(String PDID, String project_Title, String requested_Amount, String santioned_Amount, String released_Amount, String total_Released_Amount, String balance_Amount, String status, String registration_Id) {
        this.PDID = PDID;
        Project_Title = project_Title;
        Requested_Amount = requested_Amount;
        Santioned_Amount = santioned_Amount;
        Released_Amount = released_Amount;
        Total_Released_Amount = total_Released_Amount;
        Balance_Amount = balance_Amount;
        Status = status;
        Registration_Id =registration_Id;
    }

    public Class_Fund_Details() {
    }
}
