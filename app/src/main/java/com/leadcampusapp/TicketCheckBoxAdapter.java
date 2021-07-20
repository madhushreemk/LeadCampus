package com.leadcampusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

//import com.android.sripad.leadnew_22_6_2018.R;

/**
 * Created by Admin on 28-05-2018.
 */

public class TicketCheckBoxAdapter extends BaseAdapter{

    public ArrayList<Class_Fund_Details> feesUnPaidList;
    Activity activity;
    EditText userInputDialogEditText;
    String str_requestId,str_reson;

    public TicketCheckBoxAdapter(Activity activity, ArrayList<Class_Fund_Details> feesUnPaidList) {
        super();
        this.activity = activity;
        this.feesUnPaidList = feesUnPaidList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return feesUnPaidList.size();
    }

    @Override
    public Class_Fund_Details getItem(int position) {

        //return projList.get(position);
        return feesUnPaidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView ProjectTittle;
        TextView tv_Requested;
        TextView tv_Sanctioned;
        TextView tv_Released;
        TextView tv_Balance;
        CheckBox check_status;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final String[] userInput = new String[1];

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pmticket_fund_list, null);
            holder = new ViewHolder();

            holder.ProjectTittle = (TextView) convertView.findViewById(R.id.ProjectTittle);
            holder.tv_Requested = (TextView) convertView.findViewById(R.id.tv_Requested);
            holder.tv_Sanctioned = (TextView) convertView.findViewById(R.id.tv_Sanctioned);
            holder.tv_Released = (TextView) convertView.findViewById(R.id.tv_Released);
            holder.tv_Balance = (TextView) convertView.findViewById(R.id.tv_Balance);
           /* holder.mReject = (ImageView) convertView.findViewById(R.id.reject);
            holder.mCollegeName = (TextView) convertView.findViewById(R.id.txt_collegeName);
            holder.mPhoneNumber = (TextView) convertView.findViewById(R.id.txt_phoneNumber);*/
            holder.check_status = (CheckBox) convertView.findViewById(R.id.check_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Class_Fund_Details item = feesUnPaidList.get(position);

        if(item.getProject_Title()!=null) {
            holder.ProjectTittle.setText(item.getProject_Title().toString());
        }
        if(item.getRequested_Amount()!=null) {
            holder.tv_Requested.setText(item.getRequested_Amount().toString());
        }
        if(item.getSantioned_Amount()!=null) {
            holder.tv_Sanctioned.setText(item.getSantioned_Amount().toString());
        }
        if(item.getBalance_Amount()!=null) {
            holder.tv_Balance.setText(item.getBalance_Amount().toString());
        }
        if(item.getReleased_Amount()!=null) {
            holder.tv_Released.setText(item.getReleased_Amount().toString());
        }

  /*      if(item.getProj_count()!=null) {
            holder.mProjectCount.setText(item.getProj_count());
        }*/
        /*else{
            holder.mRegistrationDate.setText("");
        }*/

        if(item.getStatus().toString().equals("1")) {
            holder.check_status.setChecked(true);
            //holder.mStatus.setSelected(true);
        }
        if(item.getStatus().toString().equals("0")) {
            holder.check_status.setChecked(false);
            //holder.mStatus.setSelected(false);
        }
        final int pos = position;
        holder.check_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("insidelistview","listview clicked");

                if(isChecked) {
                    Log.d("ischhecked","true");
                    holder.check_status.setChecked(true);
                    //holder.mStatus.setEnabled(true);
                    feesUnPaidList.get(pos).setStatus("1");
                }else{
                    Log.d("ischhecked","false");
                    holder.check_status.setChecked(false);
                    feesUnPaidList.get(pos).setStatus("0");
                }
            }
        });



        return convertView;
    }
    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }
        @Override
        public void onClick(View v) {
            // put your code here
            //  String mValue = userInput.getText().toString();
            if(validation()) {
                //    dialog.dismiss();
                str_reson=userInputDialogEditText.getText().toString();
                TShirtUnpaidFragment.SubmitRejectDetails submitRejectDetails = new TShirtUnpaidFragment.SubmitRejectDetails(activity);
                submitRejectDetails.execute(str_requestId, str_reson);
                // Toast.makeText(activity, userInputDialogEditText.getText().toString()+","+str_requestId+","+str_reson , Toast.LENGTH_LONG).show();


            }
        }
    }
    public boolean validation(){

        Boolean bpmcomments=true,bamount=true;


        if( userInputDialogEditText.length() == 0 ){
            userInputDialogEditText.setError( "Reason field should not be empty!" );bpmcomments=false;}

        if(bpmcomments&&bamount) {
            return true;
        }else{return false;}
    }
/*
    public void filter(String charText,ArrayList<TShirtUnpaidModel> feesList,String selectedCollegeName) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.feesUnPaidList.clear();

     */
/*   for(FeesUnpaidModel feesUnpaidModel : feesList){
            String  stdName = feesUnpaidModel.getStudent_name().toString();
            //Log.d("StudeNameissssss",stdName);
        }*//*


        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.feesUnPaidList.addAll(feesList);
                } else {
                    for (TShirtUnpaidModel wp : feesList) {
                        //Log.d("GetCollegeNameissss",wp.getCollege_name().toLowerCase((Locale.getDefault())));
                       */
/* if (wp.getCollege_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getLead_id().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStudent_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText)) {
                            this.feesUnPaidList.add(wp);
                        }*//*

                        if(selectedCollegeName == null) {
                            if ((wp.getCollege_name()!=null && wp.getCollege_name().toLowerCase().contains(charText.toLowerCase())) || ( wp.getLead_id()!=null && wp.getLead_id().toLowerCase().contains(charText.toLowerCase())) || ( wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())) || ( wp.getStudent_name()!=null && wp.getStudent_name().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase()))) {
                                this.feesUnPaidList.add(wp);
                            }
                        }else{
                            if ((wp.getCollege_name()!=null && wp.getCollege_name().equals(selectedCollegeName)) && ((wp.getCollege_name()!=null && wp.getCollege_name().toLowerCase().contains(charText.toLowerCase())) || (wp.getLead_id()!=null && wp.getLead_id().toLowerCase().contains(charText.toLowerCase())) || (wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())) || (wp.getStudent_name()!=null && wp.getStudent_name().toLowerCase().contains(charText.toLowerCase())) || (wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())))) {
                                this.feesUnPaidList.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }
*/
}
