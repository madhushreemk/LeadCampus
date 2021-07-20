package com.leadcampusapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

//import com.android.sripad.leadnew_22_6_2018.R;

/**
 * Created by Admin on 28-05-2018.
 */

public class PrincipleCollegeAdapter extends BaseAdapter{

    public ArrayList<PrincipleCollegeModule> principleCollegeList;
    Activity activity;
    EditText userInputDialogEditText;
    String str_requestId,str_reson;

    public PrincipleCollegeAdapter(Activity activity, ArrayList<PrincipleCollegeModule> principleCollegeList) {
        super();
        this.activity = activity;
        this.principleCollegeList = principleCollegeList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return principleCollegeList.size();
    }

    @Override
    public PrincipleCollegeModule getItem(int position) {

        //return projList.get(position);
        return principleCollegeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView mstudName;
        TextView mleadId;
        TextView mprojTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final String[] userInput = new String[1];

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.principle_college_listrow, null);
            holder = new ViewHolder();

            holder.mstudName = (TextView) convertView.findViewById(R.id.txt_studName);
            holder.mleadId = (TextView) convertView.findViewById(R.id.txt_leadId);
            holder.mprojTitle = (TextView) convertView.findViewById(R.id.txt_projTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PrincipleCollegeModule item = principleCollegeList.get(position);

        if(item.getStudentName()!=null) {
            holder.mstudName.setText(item.getStudentName().toString());
        }
        if(item.getLeadId()!=null) {
            holder.mleadId.setText(item.getLeadId().toString());
        }
        if(item.getProjectTitle()!=null) {
            holder.mprojTitle.setText(item.getProjectTitle().toString());
        }

        return convertView;
    }

}
