package com.leadcampusapp;

/**
 * Created by User on 12/7/18.
 */

public class Class_Program
{
    int id;

    String ProgramId;
    String ProgramName;
    String ProgramStatus;


    /* <programmeId>long</programmeId>
          <status>string</status>
          <programmeName>string</programmeName>*/




    public Class_Program(){

    }




    public Class_Program(int id, String programId, String programname, String programstatus){
        this.id = id;
        this.ProgramId = programId;
        this.ProgramName = programname;
        this.ProgramStatus=programstatus;


    }

    public Class_Program(String programId, String programname, String programstatus){

        this.ProgramId = programId;
        this.ProgramName = programname;
        this.ProgramStatus=programstatus;
    }


    //get and set
    public String getprogram_id(){
        return this.ProgramId;
    }
    public void setprogram_id(String programId){
        this.ProgramId = programId;
    }



    //get and set
    public String getprog_name(){
        return this.ProgramName;
    }
    public void setprog_name(String programname){
        this.ProgramName = programname;
    }




    //get and set
    public String getprog_status(){
        return this.ProgramStatus;
    }
    public void setprog_status(String programstatus){
        this.ProgramStatus = programstatus;
    }



    //set the string
    public String toString()
    {
        return( this.ProgramName );
    }



}
