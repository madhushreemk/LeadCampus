package com.leadcampusapp;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

//import com.android.sripad.leadnew_22_6_2018.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Admin on 28-05-2018.
 */

public class ProjectStatusAdapter extends BaseAdapter{

    public ArrayList<ProjectStatusActivityModel2> projList;
    Activity activity;
    private ArrayList<ProjectStatusActivityModel2> mDisplayedValues = null;

    public ProjectStatusAdapter(Activity activity, ArrayList<ProjectStatusActivityModel2> projList) {
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
    public ProjectStatusActivityModel2 getItem(int position) {

        //return projList.get(position);
        return mDisplayedValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView mproject_name;
        TextView mapproved_amt;
        ImageView mstatus;
        TextView mdispersed_amt;
        RatingBar mrating_bar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.projectstatus_listrow, null);
            holder = new ViewHolder();

            holder.mproject_name = (TextView) convertView.findViewById(R.id.txt_projName);

            holder.mrating_bar = (RatingBar) convertView.findViewById(R.id.ratingBar1);
   /*         holder.mproject_type = (TextView) convertView.findViewById(R.id.txt_projType);*/
            //holder.mamt_app = (TextView) convertView.findViewById(R.id.txt_amountApp);
            //holder.mapproved_amt =

            holder.mapproved_amt = (TextView) convertView.findViewById(R.id.txt_approvedamt);
            holder.mdispersed_amt = (TextView) convertView.findViewById(R.id.txt_dispersedamt);
            holder.mstatus = (ImageView) convertView.findViewById(R.id.img_projStatus);
  /*          holder.mcompletion_amt = (TextView) convertView.findViewById(R.id.txt_completionamt);*/
            //holder.mleadFundedStatus = (TextView) convertView.findViewById(R.id.txt_leadFundedStatus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

       /* Date date = new Date();
        String modifiedDate= new SimpleDateFormat("dd/MM/yyyy").format(date);
        Log.d("Modified date is xxxx",modifiedDate);*/

        ProjectStatusActivityModel2 item = projList.get(position);

        holder.mproject_name.setText(item.getProject_name().toString());
/*        holder.mproject_type.setText(item.getProject_type().toString());*/
        //holder.mamt_app.setText(item.getAmt_app().toString());
        holder.mapproved_amt.setText(item.getApproved_amt().toString());
        holder.mdispersed_amt.setText(item.getDispersed_amt().toString());

        if(item.isCompletedFlag()==true){
            holder.mstatus.setVisibility(View.GONE);
            holder.mrating_bar.setVisibility(View.VISIBLE);
            holder.mrating_bar.setRating(Float.parseFloat(item.getRating()));
            Drawable drawable = holder.mrating_bar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FFC300"), PorterDuff.Mode.SRC_ATOP);
        }else {
            holder.mstatus.setVisibility(View.VISIBLE);
            holder.mrating_bar.setVisibility(View.GONE);
            holder.mstatus.setImageResource(item.getStatus());
        }
        /*holder.mcompletion_amt.setText(item.getCompletion_amt().toString());*/
        //holder.mleadFundedStatus.setText(item.getLeadFundedStatus().toString());

     /*   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1=null;
        Date date2=null;

        try {
            date1 = sdf.parse(item.getDuedate().toString());
            date2 = sdf.parse(modifiedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        //date2 = sdf.parse("2010-01-31");

/*        if(date1.compareTo(date2) > 0 ){
            holder.mSNo.setText(item.getsNo().toString());
            holder.mtypeOfSeason.setText(item.getTypeOfSeasonal().toString());
            holder.mdocumentation.setText(item.getDocumentation().toString());
            holder.massurance.setText(item.getAssurance().toString());
            holder.mduedate.setText(item.getDuedate().toString());
            //holder.mduedate.setTextColor(Color.RED);
        }
        if(date1.compareTo(date2) < 0){
            holder.mSNo.setText(item.getsNo().toString());
            holder.mtypeOfSeason.setText(item.getTypeOfSeasonal().toString());
            holder.mdocumentation.setText(item.getDocumentation().toString());
            holder.massurance.setText(item.getAssurance().toString());
            holder.mduedate.setText(item.getDuedate().toString());
            holder.mduedate.setTextColor(Color.RED);
        }
        if(date1.compareTo(date2) == 0){
            holder.mSNo.setText(item.getsNo().toString());
            holder.mtypeOfSeason.setText(item.getTypeOfSeasonal().toString());
            holder.mdocumentation.setText(item.getDocumentation().toString());
            holder.massurance.setText(item.getAssurance().toString());
            holder.mduedate.setText(item.getDuedate().toString());
            holder.mduedate.setTypeface(Typeface.DEFAULT_BOLD);
            // holder.mduedate.setTextColor(Color.YELLOW);
            holder.mduedate.setTextColor(Color.parseColor("#FFD12A"));
        }

        int diffInDays = (int) ((date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24));
        if(diffInDays==1 || diffInDays==2 || diffInDays==3 || diffInDays==4 || diffInDays==5 || diffInDays==6 || diffInDays==7){
            holder.mSNo.setText(item.getsNo().toString());
            holder.mtypeOfSeason.setText(item.getTypeOfSeasonal().toString());
            holder.mdocumentation.setText(item.getDocumentation().toString());
            holder.massurance.setText(item.getAssurance().toString());
            holder.mduedate.setText(item.getDuedate().toString());
            holder.mduedate.setTypeface(Typeface.DEFAULT_BOLD);
           // holder.mduedate.setTextColor(Color.YELLOW);
            holder.mduedate.setTextColor(Color.parseColor("#FFD12A"));
        }*/

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


    public void filter(String charText,ArrayList<ProjectStatusActivityModel2> projectList) {
        charText = charText.toLowerCase(Locale.getDefault());
        this.mDisplayedValues.clear();

        if(charText!=null) {
            if(projectList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.mDisplayedValues.addAll(projectList);
                } else {
                    for (ProjectStatusActivityModel2 wp : projectList) {
                        if (wp.getProject_name().toLowerCase(Locale.getDefault()).contains(charText.toLowerCase())) {
                            this.mDisplayedValues.add(wp);
                        }
                    }
                }
                notifyDataSetChanged();
            }
        }
    }



}
