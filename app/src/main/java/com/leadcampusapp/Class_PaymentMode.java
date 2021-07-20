package com.leadcampusapp;

/**
 * Created by User on 11/10/2017.
 */

public class Class_PaymentMode {


    int id;

    String payment_mode_slno;
    String short_code;
    String description;
    String Status;


    public Class_PaymentMode(){

    }

    public String getPayment_mode_slno() {
        return payment_mode_slno;
    }

    public void setPayment_mode_slno(String payment_mode_slno) {
        this.payment_mode_slno = payment_mode_slno;
    }

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String toString()
    {
        return( this.description );
    }




}//end of class
