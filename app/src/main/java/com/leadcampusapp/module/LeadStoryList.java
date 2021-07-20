package com.leadcampusapp.module;


import java.util.ArrayList;

/**
 * Created by User on 10/13/2017.
 */

public class LeadStoryList {

    String slno;
    String Story_Title;
    String Story_Description;
    String Image_Path;
    String Story_Type;
    String Card_Image_Path;
    String URL_Link;

    String Created_Date;
    String status;
    String Video_Story_URL;

    public String getVideo_Story_URL() {
        return Video_Story_URL;
    }

    public LeadStoryList(String slno, String story_Title, String story_Description, String image_Path, String story_Type, String card_Image_Path, String URL_Link, String Video_Story_URL) {
        this.slno = slno;
        Story_Title = story_Title;
        Story_Description = story_Description;
        Image_Path = image_Path;
        Story_Type = story_Type;
        Card_Image_Path = card_Image_Path;
        this.URL_Link = URL_Link;
        this.Video_Story_URL=Video_Story_URL;
    }

    public static ArrayList<LeadStoryList> listview_arr=new ArrayList<LeadStoryList>();

    public String getStory_Type() {
        return Story_Type;
    }

    public void setStory_Type(String story_Type) {
        Story_Type = story_Type;
    }

    public String getCard_Image_Path() {
        return Card_Image_Path;
    }

    public void setCard_Image_Path(String card_Image_Path) {
        Card_Image_Path = card_Image_Path;
    }

    public String getURL_Link() {
        return URL_Link;
    }

    public void setURL_Link(String URL_Link) {
        this.URL_Link = URL_Link;
    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getStory_Title() {
        return Story_Title;
    }

    public void setStory_Title(String story_Title) {
        Story_Title = story_Title;
    }

    public String getStory_Description() {
        return Story_Description;
    }

    public void setStory_Description(String story_Description) {
        Story_Description = story_Description;
    }

    public String getImage_Path() {
        return Image_Path;
    }

    public void setImage_Path(String image_Path) {
        Image_Path = image_Path;
    }

    public String getCreated_Date() {
        return Created_Date;
    }

    public void setCreated_Date(String created_Date) {
        Created_Date = created_Date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
