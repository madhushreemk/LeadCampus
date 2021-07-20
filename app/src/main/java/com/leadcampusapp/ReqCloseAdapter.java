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

public class ReqCloseAdapter extends BaseAdapter{

    public ArrayList<ReqCloseModule> ReqCloseList;
    Activity activity;
    EditText userInputDialogEditText;
    String str_requestId,str_reson;

    public ReqCloseAdapter(Activity activity, ArrayList<ReqCloseModule> ReqCloseList) {
        super();
        this.activity = activity;
        this.ReqCloseList = ReqCloseList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return ReqCloseList.size();
    }

    @Override
    public ReqCloseModule getItem(int position) {

        //return projList.get(position);
        return ReqCloseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView txt_ticketno;
        TextView txt_reqDate;
        TextView txt_lead_id;
        TextView txt_studName;
        TextView txt_mobileno;
        TextView txt_reqType;
        TextView txt_reqMsg;
        TextView txt_resMsg;
        TextView txt_priority;
        TextView txt_resDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final String[] userInput = new String[1];

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.reqclose_listrow, null);
            holder = new ViewHolder();

            holder.txt_ticketno = (TextView) convertView.findViewById(R.id.txt_ticketno);
            holder.txt_reqDate = (TextView) convertView.findViewById(R.id.txt_reqDate);
            holder.txt_lead_id = (TextView) convertView.findViewById(R.id.txt_lead_id);
            holder.txt_studName = (TextView) convertView.findViewById(R.id.txt_studName);
            holder.txt_mobileno = (TextView) convertView.findViewById(R.id.txt_mobileno);
            holder.txt_reqType = (TextView) convertView.findViewById(R.id.txt_reqType);
            holder.txt_reqMsg = (TextView) convertView.findViewById(R.id.txt_reqMsg);
            holder.txt_resMsg = (TextView) convertView.findViewById(R.id.txt_resMsg);
            holder.txt_priority = (TextView) convertView.findViewById(R.id.txt_priority);
            holder.txt_resDate = (TextView) convertView.findViewById(R.id.txt_resDate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReqCloseModule item = ReqCloseList.get(position);

        if(item.getTicket_No()!=null) {
            holder.txt_ticketno.setText(item.getTicket_No().toString());
        }
        if(item.getRequest_Date()!=null) {
            holder.txt_reqDate.setText(item.getRequest_Date().toString());
        }
        if(item.getLead_Id()!=null) {
            holder.txt_lead_id.setText(item.getLead_Id().toString());
        }
        if(item.getStudent_Name()!=null) {
            holder.txt_studName.setText(item.getStudent_Name().toString());
        }
        if(item.getMobileNo()!=null) {
            holder.txt_mobileno.setText(item.getMobileNo().toString());
        }
        if(item.getRequest_type()!=null) {
            holder.txt_reqType.setText(item.getRequest_type().toString());
        }
        if(item.getRequest_Message()!=null) {
            holder.txt_reqMsg.setText(item.getRequest_Message().toString());
        }
        if(item.getResponse_Message()!=null) {
            holder.txt_resMsg.setText(item.getResponse_Message().toString());
        }
        if(item.getRequest_Priority()!=null) {
            holder.txt_priority.setText(item.getRequest_Priority().toString());
        }
        if(item.getRespond_Date()!=null) {
            holder.txt_resDate.setText(item.getRespond_Date().toString());
        }


        return convertView;
    }

/*    public void filter(String charText,ArrayList<ReqOpenModule> reqOpenList,String selectedCollegeName) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.ReqOpenList.clear();

     *//*   for(FeesUnpaidModel feesUnpaidModel : feesList){
            String  stdName = feesUnpaidModel.getStudent_name().toString();
            //Log.d("StudeNameissssss",stdName);
        }*//*

        if(charText!=null) {
            if(reqOpenList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.ReqOpenList.addAll(reqOpenList);
                } else {
                    for (ReqOpenModule wp : reqOpenList) {
                        //Log.d("GetCollegeNameissss",wp.getCollege_name().toLowerCase((Locale.getDefault())));
                       *//* if (wp.getCollege_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getLead_id().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStudent_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText)) {
                            this.feesUnPaidList.add(wp);
                        }*//*
                        if(selectedCollegeName == null) {
                            if ((wp.getCollege_name()!=null && wp.getCollege_name().toLowerCase().contains(charText.toLowerCase())) || ( wp.getLead_id()!=null && wp.getLead_id().toLowerCase().contains(charText.toLowerCase())) || ( wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())) || ( wp.getStudent_name()!=null && wp.getStudent_name().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase()))) {
                                this.ReqOpenList.add(wp);
                            }
                        }else{
                            if ((wp.getCollege_name()!=null && wp.getCollege_name().equals(selectedCollegeName)) && ((wp.getCollege_name()!=null && wp.getCollege_name().toLowerCase().contains(charText.toLowerCase())) || (wp.getLead_id()!=null && wp.getLead_id().toLowerCase().contains(charText.toLowerCase())) || (wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())) || (wp.getStudent_name()!=null && wp.getStudent_name().toLowerCase().contains(charText.toLowerCase())) || (wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())))) {
                                this.ReqOpenList.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }*/
}
