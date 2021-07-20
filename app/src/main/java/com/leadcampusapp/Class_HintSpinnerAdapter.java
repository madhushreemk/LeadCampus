package com.leadcampusapp;

/**
 * Created by User on 23/7/18.
 */

 import android.content.Context;
 import android.widget.ArrayAdapter;

public class Class_HintSpinnerAdapter extends ArrayAdapter<String> {

    public Class_HintSpinnerAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        // TODO Auto-generated constructor stub

    }

    @Override
    public int getCount() {

        // TODO Auto-generated method stub
        int count = super.getCount();

        return count>0 ? count-1 : count ;


    }


}