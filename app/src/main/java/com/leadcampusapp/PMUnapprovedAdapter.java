package com.leadcampusapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

//import com.android.sripad.leadnew_22_6_2018.R;

/**
 * Created by Admin on 28-05-2018.
 */

public class PMUnapprovedAdapter extends BaseAdapter{

    public ArrayList<PMUnapprovedFragmentModel> projList;
    Activity activity;
    private ArrayList<PMUnapprovedFragmentModel> mDisplayedValues = null;

    public PMUnapprovedAdapter(Activity activity, ArrayList<PMUnapprovedFragmentModel> projList) {
        super();
        this.activity = activity;
        this.projList = projList;
        this.mDisplayedValues = projList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return mDisplayedValues.size();
    }

    @Override
    public PMUnapprovedFragmentModel getItem(int position) {

        //return projList.get(position);
        return mDisplayedValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView mproject_name;
        TextView mcollege;
        TextView mproject_title;
        TextView mbudget;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pmunapproved_listrow, null);
            holder = new ViewHolder();

            holder.mproject_name = (TextView) convertView.findViewById(R.id.txt_projName);

            holder.mproject_title = (TextView) convertView.findViewById(R.id.txt_projTitle);

            holder.mcollege = (TextView) convertView.findViewById(R.id.txt_college);
            holder.mbudget = (TextView) convertView.findViewById(R.id.txt_budget);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       /* Date date = new Date();
        String modifiedDate= new SimpleDateFormat("dd/MM/yyyy").format(date);
        Log.d("Modified date is xxxx",modifiedDate);*/

        PMUnapprovedFragmentModel item = projList.get(position);

        Log.d("StudeNamesssssssssssss",item.getName().toString());
        Log.d("ProjectTitlessssssss",item.getProject_title().toString());
        Log.d("CollegeNamesssssss",item.getCollege().toString());
        Log.d("Budgetissssssssssss",item.getBudget().toString());


        holder.mproject_name.setText(item.getName().toString());
        holder.mproject_title.setText(item.getProject_title().toString());
        holder.mcollege.setText(item.getCollege().toString());
        holder.mbudget.setText(item.getBudget().toString());

        return convertView;
    }

/*    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<ProjectStatusActivityModel2>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<ProjectStatusActivityModel2> FilteredArrList = new ArrayList<ProjectStatusActivityModel2>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<ProjectStatusActivityModel2>(mDisplayedValues); // saves the original data in mOriginalValues
                }

                *//********
                 *
                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                 *  else does the Filtering and returns FilteredArrList(Filtered)
                 *
                 ********//*
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    //Log.d("constraintisssss",constraint.toString());
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getProject_name();

                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            Log.d("dataisssss",data);
                            FilteredArrList.add(new ProjectStatusActivityModel2(mOriginalValues.get(i).getProject_name(),mOriginalValues.get(i).getApproved_amt(),mOriginalValues.get(i).getStatus(),mOriginalValues.get(i).getDispersed_amt(),mOriginalValues.get(i).getRating(),mOriginalValues.get(i).isCompletedFlag(),mOriginalValues.get(i).getProjectId()));
                        }
                    }
                    // set the Filtered result to return
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }*/


    public void filter(String charText,ArrayList<PMUnapprovedFragmentModel> projectList) {
        charText = charText.toLowerCase(Locale.getDefault());
        this.mDisplayedValues.clear();

        if(charText!=null) {
            if(projectList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.mDisplayedValues.addAll(projectList);
                } else {
                    for (PMUnapprovedFragmentModel wp : projectList) {
                        if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getProject_title().toLowerCase(Locale.getDefault()).contains(charText) || wp.getCollege().toLowerCase(Locale.getDefault()).contains(charText) || wp.getBudget().toLowerCase(Locale.getDefault()).contains(charText)) {
                            this.mDisplayedValues.add(wp);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }
    }



}
