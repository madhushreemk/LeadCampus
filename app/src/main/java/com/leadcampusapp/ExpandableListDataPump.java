package com.leadcampusapp;

import android.content.Context;

//import com.android.sripad.leadnew_22_6_2018.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(Context context) {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> leadProgram = new ArrayList<String>();
        leadProgram.add(context.getResources().getString(R.string.leadProgram));

        List<String> leadPrayana = new ArrayList<String>();
        leadPrayana.add(context.getResources().getString(R.string.leadPrayana));

        List<String> leadSummit = new ArrayList<String>();
        leadSummit.add(context.getResources().getString(R.string.leadYuvaSummit));

        List<String> leadValedictory = new ArrayList<String>();
        leadValedictory.add(context.getResources().getString(R.string.leadValedictory));

        //List<String> leadTalaash = new ArrayList<String>();
        //leadTalaash.add(context.getResources().getString(R.string.leadTalaash));


        expandableListDetail.put("LEAD LEADERSHIP PROGRAM", leadProgram);
        expandableListDetail.put("LEAD PRAYANA", leadPrayana);
        expandableListDetail.put("YUVA SUMMIT", leadSummit);
        expandableListDetail.put("LEAD VALEDICTORY", leadValedictory);
      //  expandableListDetail.put("LEAD TALAASH", leadTalaash);



        return expandableListDetail;
    }
}
