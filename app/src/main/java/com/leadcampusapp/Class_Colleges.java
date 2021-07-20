package com.leadcampusapp;

/**
 * Created by User on 11/13/2017.
 */

public class Class_Colleges {

    int id;


    String CollegeId;
    String College_TID;
    String CollegeName;
    String CollegeStatus;
    String CollegeType;

    String CurrentApplCount;
    String CurrentAdmissionCount;
    String PreviousApplCount;
    String PreviousAdmissionCount;
    String Prev2prevApplCount;
    String Prev2prevAdmissionCount;

    String Fees;
    String Fees_Id;
    String Status;

    public Class_Colleges(){

    }

    public Class_Colleges(int id, String collegeId, String college_tid, String collegename, String collegestatus, String collegetype,
                          String currentapplcount, String currentadmissioncount, String previousapplcount, String previousadmissioncount, String prev2prevapplcount, String prev2prevadmissioncount)
    {
        this.id = id;
        this.CollegeId = collegeId;
        this.College_TID=college_tid;
        this.CollegeName = collegename;
        this.CollegeStatus=collegestatus;
        this.CollegeType=collegetype;

        this.CurrentApplCount=currentapplcount;
        this.CurrentAdmissionCount=currentadmissioncount;
        this.PreviousApplCount=previousapplcount;
        this.PreviousAdmissionCount=previousadmissioncount;
        this.Prev2prevApplCount=prev2prevapplcount;
        this.Prev2prevAdmissionCount= prev2prevadmissioncount;
    }

    public Class_Colleges(String collegeId, String college_tid, String collegename, String collegestatus, String collegetype,
                          String currentapplcount, String currentadmissioncount, String previousapplcount, String previousadmissioncount, String prev2prevapplcount, String prev2prevadmissioncount)
    {

        this.CollegeId = collegeId;
        this.College_TID=college_tid;
        this.CollegeName = collegename;
        this.CollegeStatus=collegestatus;
        this.CollegeType=collegetype;

        this.CurrentApplCount=currentapplcount;
        this.CurrentAdmissionCount=currentadmissioncount;
        this.PreviousApplCount=previousapplcount;
        this.PreviousAdmissionCount=previousadmissioncount;
        this.Prev2prevApplCount=prev2prevapplcount;
        this.Prev2prevAdmissionCount= prev2prevadmissioncount;
    }


    //get and set
    public String getcollege_id(){
        return this.CollegeId;
    }
    public void setcollege_id(String collegeId){
        this.CollegeId = collegeId;
    }


    //get and set
    public String getcollege_tid(){
        return this.College_TID;
    }
    public void setcollege_tid(String college_tid){
        this.College_TID = college_tid;
    }

    //get and set
    public String getcollegename(){
        return this.CollegeName;
    }
    public void setcollegename(String collegename){
        this.CollegeName = collegename;
    }

    //get and set
    public String getcollegestatus(){
        return this.CollegeStatus;
    }
    public void setcollegestatus(String collegestatus){
        this.CollegeStatus = collegestatus;
    }



    //get and set
    public String getcollegetype(){
        return this.CollegeType;
    }
    public void setcollegetype(String collegetype){
        this.CollegeType = collegetype;
    }











    //get and set
    public String getcurrentapplcount(){
        return this.CurrentApplCount;
    }
    public void setcurrentapplcount(String currentapplcount){
        this.CurrentApplCount = currentapplcount;
    }



    //get and set
    public String getcurrentadmissioncount(){
        return this.CurrentAdmissionCount;
    }
    public void setcurrentadmissioncount(String currentadmissioncount){
        this.CurrentAdmissionCount = currentadmissioncount;
    }

    //get and set
    public String getpreviousapplcount(){
        return this.PreviousApplCount;
    }
    public void setpreviousapplcount(String previousapplcount){
        this.PreviousApplCount = previousapplcount;
    }






    //get and set
    public String getpreviousadmissioncount(){
        return this.PreviousAdmissionCount;
    }
    public void setpreviousadmissioncount(String previousadmissioncount){
        this.PreviousAdmissionCount = previousadmissioncount;
    }


    //get and set
    public String getprev2prevapplcount(){
        return this.Prev2prevApplCount;
    }
    public void setprev2prevapplcount(String prev2prevapplcount){
        this.Prev2prevApplCount = prev2prevapplcount;
    }


    //get and set
    public String getprev2prevadmissioncount(){
        return this.Prev2prevAdmissionCount;
    }
    public void setprev2prevadmissioncount(String prev2prevadmissioncount){
        this.Prev2prevAdmissionCount = prev2prevadmissioncount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCollegeId() {
        return CollegeId;
    }

    public void setCollegeId(String collegeId) {
        CollegeId = collegeId;
    }

    public String getCollegeName() {
        return CollegeName;
    }

    public void setCollegeName(String collegeName) {
        CollegeName = collegeName;
    }

    public String getFees() {
        return Fees;
    }

    public void setFees(String fees) {
        Fees = fees;
    }

    public String getFees_Id() {
        return Fees_Id;
    }

    public void setFees_Id(String fees_Id) {
        Fees_Id = fees_Id;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    //set the string
    public String toString()
    {
        return( this.CollegeName );
    }


}//end of class
