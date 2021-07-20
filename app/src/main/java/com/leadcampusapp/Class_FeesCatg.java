package com.leadcampusapp;

/**
 * Created by User on 10/02/2021.
 */

public class Class_FeesCatg {

    int id;

    String Fees_Category_Slno;
    String fees_category_code;
    String Fees_category_description;
    String academic_year;
    String Fees;
    String Fees_ID;
    String Status;

     /*<DistrictId>1</DistrictId>
           <DistrictName>Nicobar</DistrictName>
            <Status>Success</Status>
            <Stateid>1</Stateid> */

    public Class_FeesCatg(){

    }

    public Class_FeesCatg(int id, String fees_Category_Slno, String fees_category_code, String fees_category_description, String academic_year, String status) {
        this.id = id;
        Fees_Category_Slno = fees_Category_Slno;
        this.fees_category_code = fees_category_code;
        Fees_category_description = fees_category_description;
        this.academic_year = academic_year;
        Status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFees_Category_Slno() {
        return Fees_Category_Slno;
    }

    public void setFees_Category_Slno(String fees_Category_Slno) {
        Fees_Category_Slno = fees_Category_Slno;
    }

    public String getFees_category_code() {
        return fees_category_code;
    }

    public void setFees_category_code(String fees_category_code) {
        this.fees_category_code = fees_category_code;
    }

    public String getFees_category_description() {
        return Fees_category_description;
    }

    public void setFees_category_description(String fees_category_description) {
        Fees_category_description = fees_category_description;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFees() {
        return Fees;
    }

    public void setFees(String fees) {
        Fees = fees;
    }

    public String getFees_ID() {
        return Fees_ID;
    }

    public void setFees_ID(String fees_ID) {
        Fees_ID = fees_ID;
    }

    public String toString()
    {
        return( this.Fees_category_description );
    }








}//end of class
