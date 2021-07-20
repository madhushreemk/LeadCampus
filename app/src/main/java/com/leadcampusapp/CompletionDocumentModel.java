package com.leadcampusapp;

import java.util.ArrayList;

/**
 * Created by deepshikha on 3/3/17.
 */

public class CompletionDocumentModel {
    ArrayList<String> al_documentpath;
    String str_folder;
    ArrayList<String> al_documentName;

    public String getStr_folder() {
        return str_folder;
    }

    public void setStr_folder(String str_folder) {
        this.str_folder = str_folder;
    }

    public ArrayList<String> getAl_documentpath() {
        return al_documentpath;
    }

    public void setAl_documentpath(ArrayList<String> al_documentpath) {
        this.al_documentpath = al_documentpath;
    }

    public ArrayList<String> getAl_documentName() {
        return al_documentName;
    }

    public void setAl_documentName(ArrayList<String> al_documentName) {
        this.al_documentName = al_documentName;
    }
}
