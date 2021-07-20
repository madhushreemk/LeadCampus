package com.leadcampusapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

//import com.android.sripad.leadnew_22_6_2018.R;

/**
 * Created by Admin on 28-05-2018.
 */

public class FeesPaidAcknowledgedAdapter extends BaseAdapter{

    public ArrayList<Class_PaidStudList> feesPaidList;
    Activity activity;

    public FeesPaidAcknowledgedAdapter(Activity activity, ArrayList<Class_PaidStudList> feesPaidList) {
        super();
        this.activity = activity;
        this.feesPaidList = feesPaidList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return feesPaidList.size();
    }

    @Override
    public Class_PaidStudList getItem(int position) {

        //return projList.get(position);
        return feesPaidList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView mstudName;
        TextView mleadId;
        TextView mRegistrationDate;
        TextView txt_feesCat;
        TextView txt_recptNo;
        TextView txt_Fees;
        TextView Created_User_Type;
        TextView OnlineorCash;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fees_paid_listrow, null);
            holder = new ViewHolder();

            holder.mstudName = (TextView) convertView.findViewById(R.id.txt_studName);
            holder.mleadId = (TextView) convertView.findViewById(R.id.txt_leadId);
            holder.mRegistrationDate = (TextView) convertView.findViewById(R.id.txt_registeredDateTxt);
            holder.txt_feesCat = (TextView) convertView.findViewById(R.id.txt_feesCat);
            holder.txt_recptNo = (TextView) convertView.findViewById(R.id.txt_recptNo);
            holder.txt_Fees = (TextView) convertView.findViewById(R.id.txt_Fees);
            holder.Created_User_Type = (TextView) convertView.findViewById(R.id.Created_User_Type);
            holder.OnlineorCash = (TextView) convertView.findViewById(R.id.OnlineorCash);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Class_PaidStudList item = feesPaidList.get(position);

        if(item.getStudentName()!=null) {
            holder.mstudName.setText(item.getStudentName().toString());
        }
        /*if(item.getPhone_number()!=null) {
            holder.mPhoneNumber.setText(item.getPhone_number().toString());
        }*/
       /* if(!item.getCollege_name().equals(null)) {
            holder.mCollegeName.setText(item.getCollege_name().toString());
        }*/
        if(item.getLead_Id()!=null) {
            holder.mleadId.setText(item.getLead_Id().toString());
        }
        if(item.getCreated_Date()!=null) {
            holder.mRegistrationDate.setText(item.getCreated_Date().toString());
        }
        if(item.getCreated_User_Type()!=null) {
            holder.Created_User_Type.setText(item.getCreated_User_Type().toString());
        }
        if(item.getPaid_Fees()!=null) {
            holder.txt_Fees.setText(item.getPaid_Fees().toString());
        }
        if(item.getPayment_Mode()!=null) {
            holder.OnlineorCash.setText(item.getPayment_Mode().toString());
        }
        if(item.getFees_Category_description()!=null) {
            holder.txt_feesCat.setText(item.getFees_Category_description().toString());
        }
        if(item.getAuto_Receipt_No()!=null) {
            holder.txt_recptNo.setText(item.getAuto_Receipt_No().toString());
        }

      /*  if(item.getPhone_number()!=null) {
            holder.mPhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + holder.mPhoneNumber.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            });
        }
*/
        return convertView;
    }


    public void filter(String charText,ArrayList<Class_PaidStudList> feesList) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.feesPaidList.clear();

     /*   for(FeesUnpaidModel feesUnpaidModel : feesList){
            String  stdName = feesUnpaidModel.getStudent_name().toString();
            //Log.d("StudeNameissssss",stdName);
        }*/

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.feesPaidList.addAll(feesList);
                } else {
                    for (Class_PaidStudList wp : feesList) {
                        //Log.d("GetCollegeNameissss",wp.getCollege_name().toLowerCase((Locale.getDefault())));
                       /* if (wp.getCollege_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getLead_id().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStudent_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText)) {
                            this.feesUnPaidList.add(wp);
                        }*/
                        if  ((wp.getStudentName()!=null && wp.getStudentName().toLowerCase().contains(charText.toLowerCase())) || ( wp.getLead_Id()!=null && wp.getLead_Id().toLowerCase().contains(charText.toLowerCase())) || ( wp.getCreated_User_Type()!=null && wp.getCreated_User_Type().toLowerCase().contains(charText.toLowerCase())) || ( wp.getPayment_Mode()!=null && wp.getPayment_Mode().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getAuto_Receipt_No()!=null && wp.getAuto_Receipt_No().toLowerCase().contains(charText.toLowerCase()))) {
                            this.feesPaidList.add(wp);
                        }

                     /*   if(selectedCollegeName == null) {
                            if (wp.getCollege_name().contains(charText) || wp.getLead_id().contains(charText) || wp.getPhone_number().contains(charText) || wp.getStudent_name().contains(charText) || wp.getPhone_number().contains(charText)) {
                                this.feesPaidList.add(wp);
                            }
                        }else{
                            if (wp.getCollege_name().equals(selectedCollegeName) && (wp.getCollege_name().contains(charText) || wp.getLead_id().contains(charText) || wp.getPhone_number().contains(charText) || wp.getStudent_name().contains(charText) || wp.getPhone_number().contains(charText))) {
                                this.feesPaidList.add(wp);
                            }
                        }*/


                    }
                }
                notifyDataSetChanged();
            }
        }
    }
}
