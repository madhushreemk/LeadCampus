package com.leadcampusapp;

import android.graphics.Bitmap;

/**
 * Created by Shripad on 16-07-2018.
 */

public class Result {
    String studresult=null;
    Bitmap studbitmap=null;
    String mgrresult=null;
    Bitmap mgrbitmap=null;

    public String getStudresult() {
        return studresult;
    }

    public void setStudresult(String studresult) {
        this.studresult = studresult;
    }

    public Bitmap getStudbitmap() {
        return studbitmap;
    }

    public void setStudbitmap(Bitmap studbitmap) {
        this.studbitmap = studbitmap;
    }

    public String getMgrresult() {
        return mgrresult;
    }

    public void setMgrresult(String mgrresult) {
        this.mgrresult = mgrresult;
    }

    public Bitmap getMgrbitmap() {
        return mgrbitmap;
    }

    public void setMgrbitmap(Bitmap mgrbitmap) {
        this.mgrbitmap = mgrbitmap;
    }
}
