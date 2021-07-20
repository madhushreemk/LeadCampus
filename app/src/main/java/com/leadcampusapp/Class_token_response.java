package com.leadcampusapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Class_token_response
{

    @SerializedName("Status")
    @Expose
    private Boolean status;

    @SerializedName("Message")
    @Expose
    private String message;


    @SerializedName("obj")
    @Expose
    private Class_token tokenresp = null;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Class_token getTokenresp() {
        return tokenresp;
    }

    public void setTokenresp(Class_token tokenresp) {
        this.tokenresp = tokenresp;
    }
}
