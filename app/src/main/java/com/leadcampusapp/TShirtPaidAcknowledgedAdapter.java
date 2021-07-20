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

public class TShirtPaidAcknowledgedAdapter extends BaseAdapter{

    public ArrayList<FeesUnpaidModel> feesPaidList;
    Activity activity;

    public TShirtPaidAcknowledgedAdapter(Activity activity, ArrayList<FeesUnpaidModel> feesPaidList) {
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
    public FeesUnpaidModel getItem(int position) {

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
        TextView mCollegeName;
        TextView mPhoneNumber;
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
            holder.mCollegeName = (TextView) convertView.findViewById(R.id.txt_collegeName);
            holder.mPhoneNumber = (TextView) convertView.findViewById(R.id.txt_phoneNumber);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FeesUnpaidModel item = feesPaidList.get(position);

        if(item.getStudent_name()!=null) {
            holder.mstudName.setText(item.getStudent_name().toString());
        }
        if(item.getPhone_number()!=null) {
            holder.mPhoneNumber.setText(item.getPhone_number().toString());
        }
        if(!item.getCollege_name().equals(null)) {
            holder.mCollegeName.setText(item.getCollege_name().toString());
        }
        if(item.getLead_id()!=null) {
            holder.mleadId.setText(item.getLead_id().toString());
        }
        if(item.getRegistration_date()!=null) {
            holder.mRegistrationDate.setText(item.getRegistration_date().toString());
        }

        if(item.getPhone_number()!=null) {
            holder.mPhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + holder.mPhoneNumber.getText().toString()));
                    v.getContext().startActivity(intent);
                }
            });
        }

        return convertView;
    }


    public void filter(String charText,ArrayList<FeesUnpaidModel> feesList,String selectedCollegeName) {
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
                    for (FeesUnpaidModel wp : feesList) {
                        //Log.d("GetCollegeNameissss",wp.getCollege_name().toLowerCase((Locale.getDefault())));
                       /* if (wp.getCollege_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getLead_id().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStudent_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText)) {
                            this.feesUnPaidList.add(wp);
                        }*/
                     /*   if(selectedCollegeName == null) {
                            if (wp.getCollege_name().contains(charText) || wp.getLead_id().contains(charText) || wp.getPhone_number().contains(charText) || wp.getStudent_name().contains(charText) || wp.getPhone_number().contains(charText)) {
                                this.feesPaidList.add(wp);
                            }
                        }else{
                            if (wp.getCollege_name().equals(selectedCollegeName) && (wp.getCollege_name().contains(charText) || wp.getLead_id().contains(charText) || wp.getPhone_number().contains(charText) || wp.getStudent_name().contains(charText) || wp.getPhone_number().contains(charText))) {
                                this.feesPaidList.add(wp);
                            }
                        }*/

                        if(selectedCollegeName == null) {
                            if ((wp.getCollege_name()!=null && wp.getCollege_name().toLowerCase().contains(charText.toLowerCase())) || ( wp.getLead_id()!=null && wp.getLead_id().toLowerCase().contains(charText.toLowerCase())) || ( wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())) || ( wp.getStudent_name()!=null && wp.getStudent_name().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase()))) {
                                this.feesPaidList.add(wp);
                            }
                        }else{
                            if ((wp.getCollege_name()!=null && wp.getCollege_name().equals(selectedCollegeName)) && ((wp.getCollege_name()!=null && wp.getCollege_name().toLowerCase().contains(charText.toLowerCase())) || (wp.getLead_id()!=null && wp.getLead_id().toLowerCase().contains(charText.toLowerCase())) || (wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())) || (wp.getStudent_name()!=null && wp.getStudent_name().toLowerCase().contains(charText.toLowerCase())) || (wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())))) {
                                this.feesPaidList.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }
}
