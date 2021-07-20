package com.leadcampusapp;

/**
 * Created by User on 12/7/18.
 */






public class Class_Sem
{

    int id;

    String SemId;
    String SemName;
    String SemStatus;


    /*<SemId>long</SemId>
<SemName>string</SemName>
<Status>string</Status>*/



    public Class_Sem(){

    }


    public Class_Sem(int id, String semId, String semname, String semstatus){
        this.id = id;
        this.SemId = semId;
        this.SemName = semname;
        this.SemStatus=semstatus;

    }

    public Class_Sem(String semId, String semname, String semstatus){

        this.SemId = semId;
        this.SemName = semname;
        this.SemStatus=semstatus;
    }



    //get and set
    public String getsem_id(){
        return this.SemId;
    }
    public void setsem_id(String semId){
        this.SemId = semId;
    }




    //get and set
    public String getsem_name(){
        return this.SemName;
    }
    public void setsem_name(String semname){
        this.SemName = semname;
    }


    //get and set
    public String getsem_status(){
        return this.SemStatus;
    }
    public void setsem_status(String semstatus){
        this.SemStatus = semstatus;
    }


    //set the string
    public String toString()
    {
        return( this.SemName );
    }



}// end of class
