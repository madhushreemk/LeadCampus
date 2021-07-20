package com.leadcampusapp;

public class Class_YearList {

    String slno;
    String AcademicCode;
    String Status;

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getAcademicCode() {
        return AcademicCode;
    }

    public void setAcademicCode(String academicCode) {
        AcademicCode = academicCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
    public String toString()
    {
        return( this.AcademicCode );
    }

}
