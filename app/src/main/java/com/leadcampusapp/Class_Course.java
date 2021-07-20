package com.leadcampusapp;

/**
 * Created by User on 12/7/18.
 */

public class Class_Course
{

    int id;

    String CourseId;
    String CourseName;
    String CourseProgCode;
    String CourseStatus;


     /*<CourseId>long</CourseId>
          <CourseName>string</CourseName>
          <ProgrammeCode>long</ProgrammeCode>
          <Status>string</Status>*/




    public Class_Course(){

    }


    public Class_Course(int id, String courseId, String coursename, String courseprogcode, String coursestatus){
        this.id = id;
        this.CourseId = courseId;
        this.CourseName = coursename;
        this.CourseProgCode=courseprogcode;
        this.CourseStatus=coursestatus;


    }

    public Class_Course(String courseId, String coursename, String courseprogcode, String coursestatus){

        this.CourseId = courseId;
        this.CourseName = coursename;
        this.CourseProgCode=courseprogcode;
        this.CourseStatus=coursestatus;
    }


    //get and set
    public String getcourse_id(){
        return this.CourseId;
    }
    public void setcourse_id(String courseId){
        this.CourseId = courseId;
    }




    //courseprogcode

    //get and set
    public String getcourse_progcode(){
        return this.CourseProgCode;
    }
    public void setcourse_progcode(String courseprogcode){
        this.CourseProgCode = courseprogcode;
    }





    //get and set
    public String getcourse_name(){
        return this.CourseName;
    }
    public void setcourse_name(String coursename){
        this.CourseName = coursename;
    }











    //get and set
    public String getcourse_status(){
        return this.CourseStatus;
    }
    public void setcourse_status(String coursestatus){
        this.CourseStatus = coursestatus;
    }








    //set the string
    public String toString()
    {
        return( this.CourseName );
    }



}// End of course
