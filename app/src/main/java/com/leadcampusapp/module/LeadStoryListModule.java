package com.leadcampusapp.module;


import java.util.ArrayList;

/**
 * Created by User on 10/13/2017.
 */

public class LeadStoryListModule {

    String slno;
    String Story_Title;
    String Story_Description;
    String Image_Path;

    String Created_Date;
    String status;


    public LeadStoryListModule(String slno, String story_Title, String story_Description, String image_Path) {
        this.slno = slno;
        Story_Title = story_Title;
        Story_Description = story_Description;
        Image_Path = image_Path;
    }

    public static ArrayList<LeadStoryListModule> listview_arr=new ArrayList<LeadStoryListModule>();

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
