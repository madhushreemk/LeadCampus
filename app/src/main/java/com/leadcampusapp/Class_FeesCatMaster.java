package com.leadcampusapp;



/*anyType{vmGET_FeeCategory_Master=anyType{Fees_Category_Slno=1; fees_category_code=RG;
Fees_category_description=Registration; Fees=1; Fees_ID=486; academic_year=0; Status=Success; }; }*/

public class Class_FeesCatMaster
{

    private String Fees_Category_Slno;
   private String fees_category_code;
    private String Fees_category_description;

    private String Fees;
    private String Fees_ID;
    private String academic_year;
    private String Status;

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


    public String toString() {
        return Fees_category_description;
    }
}
