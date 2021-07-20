package com.leadcampusapp;

/**
 * Created by User on 11/10/2017.
 */

public class Class_ApprovedProjectList {


    int id;

    String ProjectSlNo;
    String ProjectId;
    String ProjectName;
    String ProjectStatus;
    String ProjectprecentCount;
    String WebServiceStatus;
    String ImpactProject;

  /*<PDId>38393</PDId>
    <Title>title15</Title>
    <status>Success</status>
    <SanctionAmount>0</SanctionAmount>
    <Giventotal>0</Giventotal>*/

    public Class_ApprovedProjectList(){

    }


    public Class_ApprovedProjectList(int id,String slno ,String projectid, String projectname, String projectstatus,String projectprecentcount,String webservicestatus,String impactproject)
    {

        this.id = id;
        this.ProjectId = projectid;
        this.ProjectName = projectname;
        this.ProjectStatus=projectstatus;
        this.ProjectSlNo=slno;
        this.ProjectprecentCount=projectprecentcount;
        this.WebServiceStatus=webservicestatus;
        this.ImpactProject=impactproject;


    }


    public Class_ApprovedProjectList(String slno,String projectid, String projectname, String projectstatus,String projectprecentcount,String webservicestatus,String impactproject){

        this.ProjectId = projectid;
        this.ProjectName = projectname;
        this.ProjectStatus=projectstatus;
        this.ProjectSlNo=slno;
        this.ProjectprecentCount=projectprecentcount;
        this.WebServiceStatus=webservicestatus;
        this.ImpactProject=impactproject;
    }

    //get and set
    public String getproject_id(){
        return this.ProjectId;
    }
    public void setproject_id(String projectid){
        this.ProjectId = projectid;
    }

    //get and set
    public String getproject_name(){
        return this.ProjectName;
    }
    public void setproject_name(String projectname){
        this.ProjectName = projectname;
    }



    //get and set
    public String getproject_status(){
        return this.ProjectStatus;
    }
    public void setproject_status(String project_status){
        this.ProjectStatus = project_status;
    }


public String getproject_slno()
{ return this.ProjectSlNo;}

    public void setproject_slno(String project_slno){
        this.ProjectSlNo = project_slno;
    }


    public String getproject_precentCount()
    { return this.ProjectprecentCount;}

    public void setproject_precentcount(String projectprecentcount)
    {
        this.ProjectprecentCount = projectprecentcount;
    }


    public String get_webservicestatus()
    { return this.WebServiceStatus;}

    public void set_webservicestatus(String webservicestatus)
    {
        this.WebServiceStatus = webservicestatus;
    }

    public String get_impactproject() {
        return ImpactProject;
    }

    public void set_impactproject(String impactProject) {
        ImpactProject = impactProject;
    }
}//end of class
