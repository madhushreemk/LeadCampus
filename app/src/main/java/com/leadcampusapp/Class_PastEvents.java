package com.leadcampusapp;

/**
 * Created by User on 24/7/18.
 */

public class Class_PastEvents
{
    int _id;

    byte[] EventImage; //<Image_Path>string</Image_Path>
    String PastorCurrent; //<EventStatus>int</EventStatus>
    String Status; //<Status>string</Status>
    String PastEventURL;



    public Class_PastEvents() {
    }


    public Class_PastEvents(int id, byte[] eventimage, String pastorcurrent, String status,String pasteventurl) {
        this._id = id;
        this.EventImage = eventimage;
        this.PastorCurrent=pastorcurrent;
        this.Status=status;
        this.PastEventURL = pasteventurl;
    }



    public Class_PastEvents(byte[] eventimage, String pastorcurrent, String status,String pasteventurl)
    {
        this.EventImage = eventimage;
        this.PastorCurrent=pastorcurrent;
        this.Status=status;
        this.PastEventURL = pasteventurl;
    }


    public byte[] getEventImage(){
        return this.EventImage;
    }
    public byte[] setEventImage(byte[] eventimage){
        return this.EventImage=eventimage;
    }


    public String getPastorCurrent(){
        return this.PastorCurrent;
    }
    // setting id
    public void setPastorCurrent(String pastorcurrent){
        this.PastorCurrent = pastorcurrent;
    }


    public String getStatus(){
        return this.Status;
    }
    // setting id
    public void setStatus(String status){
        this.Status = status;
    }



    public String getPastEventURL(){
        return this.PastEventURL;
    }
    public void setPastEventURL(String pastEventURL)
    {
        this.PastEventURL=pastEventURL;
    }



}//end of class
