package com.leadcampusapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

//import com.android.sripad.leadnew_22_6_2018.R;

/**
 * Created by Admin on 28-05-2018.
 */

public class ReqOpenAdapter extends BaseAdapter{

    public ArrayList<ReqOpenModule> ReqOpenList;
    Activity activity;
    EditText userInputDialogEditText;
    String str_requestId,str_reson;

    public ReqOpenAdapter(Activity activity, ArrayList<ReqOpenModule> ReqOpenList) {
        super();
        this.activity = activity;
        this.ReqOpenList = ReqOpenList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return ReqOpenList.size();
    }

    @Override
    public ReqOpenModule getItem(int position) {

        //return projList.get(position);
        return ReqOpenList.get(position);
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
        TextView txt_collgName;
        TextView txt_reqMsg;

        Button priority_btn;

        TextView txt_projectName;
        TextView txt_mailId;
        TextView txt_manager_MailId;
        TextView txt_reqHeadId;
        TextView txt_mobNo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final String[] userInput = new String[1];

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.reqopen_listrow, null);
            holder = new ViewHolder();

            holder.txt_ticketno = (TextView) convertView.findViewById(R.id.txt_ticketno);
            holder.txt_reqDate = (TextView) convertView.findViewById(R.id.txt_reqDate);
            holder.txt_lead_id = (TextView) convertView.findViewById(R.id.txt_lead_id);
            holder.txt_studName = (TextView) convertView.findViewById(R.id.txt_studName);
            holder.txt_mobileno = (TextView) convertView.findViewById(R.id.txt_mobileno);
            holder.txt_reqType = (TextView) convertView.findViewById(R.id.txt_reqType);
            holder.txt_reqMsg = (TextView) convertView.findViewById(R.id.txt_reqMsg);
            holder.txt_collgName = (TextView) convertView.findViewById(R.id.txt_collgName);
            holder.priority_btn = (Button) convertView.findViewById(R.id.priority_btn);

            holder.txt_projectName = (TextView) convertView.findViewById(R.id.txt_projectName);
            holder.txt_mailId = (TextView) convertView.findViewById(R.id.txt_mailid);
            holder.txt_manager_MailId = (TextView) convertView.findViewById(R.id.txt_managerMailId);
            holder.txt_reqHeadId = (TextView) convertView.findViewById(R.id.txt_reqHeadId);
            holder.txt_mobNo = (TextView) convertView.findViewById(R.id.txt_mobNo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReqOpenModule item = ReqOpenList.get(position);

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
        if(item.getCollege_Name()!=null) {
            holder.txt_collgName.setText(item.getCollege_Name().toString());
        }
        if(item.getRequest_Priority()!=null) {
            holder.priority_btn.setText(item.getRequest_Priority().toString());
        }

        if(item.getProject_Name()!=null) {
            holder.txt_projectName.setText(item.getProject_Name().toString());
        }
        if(item.getMailId()!=null) {
            holder.txt_mailId.setText(item.getMailId().toString());
        }
        if(item.getProg_MailID()!=null) {
            holder.txt_manager_MailId.setText(item.getProg_MailID().toString());
        }
        if(item.getRequestHead_Id()!=null) {
            holder.txt_reqHeadId.setText(item.getRequestHead_Id().toString());
        }
        if(item.getMobileNo()!=null) {
            holder.txt_mobileno.setText(item.getMobileNo().toString());
        }

        if(item.getRequest_Priority().equals("Medium")) {
            holder.priority_btn.setBackgroundColor( holder.priority_btn.getContext().getResources().getColor(R.color.priority_medium));
        }else if(item.getRequest_Priority().equals("High")) {
            holder.priority_btn.setBackgroundColor( holder.priority_btn.getContext().getResources().getColor(R.color.priority_high));
        }else if(item.getRequest_Priority().equals("Low")) {
            holder.priority_btn.setBackgroundColor( holder.priority_btn.getContext().getResources().getColor(R.color.priority_low));
        }


        holder.txt_projectName.setVisibility(View.GONE);
        holder.priority_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_ticketId=holder.txt_ticketno.getText().toString();
                String str_leadId=holder.txt_lead_id.getText().toString();
                String str_reqMsg=holder.txt_reqMsg.getText().toString();
                String str_studName=holder.txt_studName.getText().toString();
                String str_collgName=holder.txt_collgName.getText().toString();
                String str_projectName=holder.txt_projectName.getText().toString();
                String str_mailId=holder.txt_mailId.getText().toString();
                String str_manager_mailID=holder.txt_manager_MailId.getText().toString();
                String str_reqHeadId=holder.txt_reqHeadId.getText().toString();
                String str_mobNo=holder.txt_mobileno.getText().toString();
                String str_reqDate=holder.txt_reqDate.getText().toString();
                String str_reqType=holder.txt_reqType.getText().toString();



                Intent intent = new Intent(activity,ReqOpenDetailActivity.class);
                intent.putExtra("str_ticketId",str_ticketId);
                intent.putExtra("str_leadId",str_leadId);
                intent.putExtra("str_reqMsg",str_reqMsg);
                intent.putExtra("str_studName",str_studName);
                intent.putExtra("str_collgName",str_collgName);
                intent.putExtra("str_projectName",str_projectName);
                intent.putExtra("str_mailId",str_mailId);
                intent.putExtra("str_manager_mailID",str_manager_mailID);
                intent.putExtra("str_reqHeadId",str_reqHeadId);
                intent.putExtra("str_mobNo",str_mobNo);
                intent.putExtra("str_reqDate",str_reqDate);
                intent.putExtra("str_reqType",str_reqType);

                activity.startActivity(intent);



            }
        });

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
