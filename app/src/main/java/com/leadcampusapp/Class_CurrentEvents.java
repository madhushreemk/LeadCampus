package com.leadcampusapp;

/**
 * Created by User on 20/7/18.
 */

/*<EventId>long</EventId>
<EventName>string</EventName>
<EventFromDate>string</EventFromDate>
<EventToDate>string</EventToDate>
<EventDescription>string</EventDescription>
<EventApplyURL>string</EventApplyURL>
<EventURL>string</EventURL>
<Image_Path>string</Image_Path>
 <EventStatus>int</EventStatus>
<Status>string</Status>*/


public class Class_CurrentEvents {

    int _id;
    //String EventName;
    //String EventDesc;
    // String EventImageURL;
    String EventApplyURL; //<EventApplyURL>string</EventApplyURL>
    String EventURL; //<EventURL>string</EventURL>
    byte[] EventImage; //<Image_Path>string</Image_Path>
    String PastorCurrent; //<EventStatus>int</EventStatus>
    String Status; //<Status>string</Status>
    String FromDate;//<EventFromDate>string</EventFromDate>
    String Todate;//<EventToDate>string</EventToDate>





   /* int _id;
    String EventApplyURL;
    String EventURL;
    byte[] EventImage;*/


    public Class_CurrentEvents() {
    }


    public Class_CurrentEvents(int id, String eventapplyurl, String eventurl,byte[] eventimage,String pastorcurrent,String status,String fromdate,String todate) {
        this._id = id;
        this.EventApplyURL = eventapplyurl;
        this.EventURL = eventurl;
        this.EventImage = eventimage;
        this.PastorCurrent=pastorcurrent;
        this.Status=status;
        this.FromDate=fromdate;
        this.Todate=todate;
    }



    public Class_CurrentEvents(String eventapplyurl, String eventurl,byte[] eventimage,String pastorcurrent,String status,String fromdate,String todate) {

        this.EventApplyURL = eventapplyurl;
        this.EventURL = eventurl;
        this.EventImage = eventimage;
        this.PastorCurrent=pastorcurrent;
        this.Status=status;
        this.FromDate=fromdate;
        this.Todate=todate;

    }


    public String getEventApplyURL(){
        return this.EventApplyURL;
    }
    // setting id
    public void setEventApplyURL(String eventapplyurl){
        this.EventApplyURL = eventapplyurl;
    }

    public String getEventURL(){
        return this.EventURL;
    }
    // setting id
    public void setEventURL(String eventurl){
        this.EventURL = eventurl;
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

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getTodate() {
        return Todate;
    }

    public void setTodate(String todate) {
        Todate = todate;
    }
}