package com.leadcampusapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;
import java.util.Locale;

//import com.android.sripad.leadnew_22_6_2018.R;

/**
 * Created by Admin on 28-05-2018.
 */

public class FeesUnpaidAdapter extends BaseAdapter{

    public ArrayList<Class_UnpaidStudList> feesUnPaidList;
    Activity activity;
    private ArrayList<ProjectStatusActivityModel2> mDisplayedValues = null;

    public FeesUnpaidAdapter(Activity activity, ArrayList<Class_UnpaidStudList> feesUnPaidList) {
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
    public Class_UnpaidStudList getItem(int position) {

        //return projList.get(position);
        return feesUnPaidList.get(position);
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
        TextView mMailId;
        TextView regId;
        TextView stateId;
        TextView districtId;
        TextView cityId;
        TextView streamId;
        TextView mcollgId;
        TextView txt_Fees;
        TextView paymentMode;
        CheckBox mStatus;
        SwitchCompat simpleSwitch;
        RadioGroup paymentMode_radiogroup;
        RadioButton rdb_Online,rdb_Cash;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fees_unpaid_listrow, null);
            holder = new ViewHolder();

            holder.mstudName = (TextView) convertView.findViewById(R.id.txt_studName);
            holder.mleadId = (TextView) convertView.findViewById(R.id.txt_leadId);
            holder.mRegistrationDate = (TextView) convertView.findViewById(R.id.txt_registeredDateTxt);
            holder.mCollegeName = (TextView) convertView.findViewById(R.id.txt_collegeName);
            holder.mPhoneNumber = (TextView) convertView.findViewById(R.id.txt_phoneNumber);
            holder.mMailId = (TextView)convertView.findViewById(R.id.txt_mailId);
            holder.regId = (TextView) convertView.findViewById(R.id.regId);
            holder.stateId = (TextView) convertView.findViewById(R.id.stateId);
            holder.districtId = (TextView) convertView.findViewById(R.id.districtId);
            holder.cityId = (TextView) convertView.findViewById(R.id.cityId);
            holder.streamId = (TextView) convertView.findViewById(R.id.streamId);
            holder.mcollgId = (TextView) convertView.findViewById(R.id.collgId);
            holder.txt_Fees = (TextView) convertView.findViewById(R.id.txt_Fees);

            holder.mStatus = (CheckBox) convertView.findViewById(R.id.check_status);
            holder.simpleSwitch = (SwitchCompat) convertView.findViewById(R.id.SwitchPaymentMode); // initiate Switch
            holder.paymentMode =(TextView) convertView.findViewById(R.id.paymentModenew);
            holder.paymentMode_radiogroup=(RadioGroup) convertView.findViewById(R.id.paymentMode_radiogroup);
            holder.rdb_Cash=(RadioButton) convertView.findViewById(R.id.rdb_Cash);
            holder.rdb_Online=(RadioButton)convertView.findViewById(R.id.rdb_Online);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Class_UnpaidStudList item = feesUnPaidList.get(position);

        if(item.getStudentName()!=null) {
            holder.mstudName.setText(item.getStudentName().toString());
        }
        if(item.getMobileNo()!=null) {
            holder.mPhoneNumber.setText(item.getMobileNo().toString());
        }
        if(item.getCollegeName()!=null) {
            holder.mCollegeName.setText(item.getCollegeName().toString());
        }
        if(item.getLead_id()!=null) {
            holder.mleadId.setText(item.getLead_id().toString());
        }
        if(item.getRegistrationDate()!=null) {
            holder.mRegistrationDate.setText(item.getRegistrationDate().toString());
        }
        if(item.getMaidId()!=null) {
            holder.mMailId.setText(item.getMaidId().toString());
        }
        if(item.getRegistration_Id()!=null) {
            holder.regId.setText(item.getRegistration_Id().toString());
        }
        if(item.getStateCode()!=null) {
            holder.stateId.setText(item.getStateCode().toString());
        }
        if(item.getDistrictCode()!=null) {
            holder.districtId.setText(item.getDistrictCode().toString());
        }
        if(item.getTalukaCode()!=null) {
            holder.cityId.setText(item.getTalukaCode().toString());
        }
        if(item.getStreamId()!=null) {
            holder.streamId.setText(item.getStreamId().toString());
        }
        if(item.getCollegeCode()!=null) {
            holder.mcollgId.setText(item.getCollegeCode().toString());
        }
        if(item.getFees()!=null) {
            holder.txt_Fees.setText(item.getFees().toString());
        }
        /*else{
            holder.mRegistrationDate.setText("");
        }*/

        if(item.getStatus().toString().equals("1")) {
            holder.mStatus.setChecked(true);
            //holder.mStatus.setSelected(true);
        }
        if(item.getStatus().toString().equals("0")) {
            holder.mStatus.setChecked(false);
            //holder.mStatus.setSelected(false);
        }

        holder.mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+holder.mPhoneNumber.getText().toString()));
                v.getContext().startActivity(intent);
            }
        });
   /*     holder.mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("insidelistview","listview clicked");
                notifyDataSetChanged();
            }
        });*/
        holder.rdb_Online.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set Yes values in ArrayList if RadioButton is checked
                if (isChecked)
                    //selectedAnswers.set(position, "2");
                    feesUnPaidList.get(position).setPayment_Mode("2");

            }
        });
// perform setOnCheckedChangeListener event on no button
        holder.rdb_Cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set No values in ArrayList if RadioButton is checked
                if (isChecked)
                    //  selectedAnswers.set(position, "1");
                    feesUnPaidList.get(position).setPayment_Mode("1");
                //   holder.paymentMode.setText(selectedAnswers.get(position).toString());
            }
        });
        //final CheckBox checkboxSelected = holder.mStatus;
        final int pos = position;
        if(holder.rdb_Cash.isChecked())
        { holder.paymentMode.setText("1");
            feesUnPaidList.get(pos).setPayment_Mode("1");
        }else if(holder.rdb_Online.isChecked()){
            holder.paymentMode.setText("2");
            feesUnPaidList.get(pos).setPayment_Mode("2");
        }
        holder.mStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("insidelistview","listview clicked");

                if(isChecked) {
                    Log.d("ischhecked","true");
                    holder.mStatus.setChecked(true);
                    //holder.mStatus.setEnabled(true);
                    feesUnPaidList.get(pos).setStatus("1");
                }else{
                    Log.d("ischhecked","false");
                    holder.mStatus.setChecked(false);
                    feesUnPaidList.get(pos).setStatus("0");
                }
                //notifyDataSetChanged();
                //holder.mStatus.setChecked(true);
                //holder.mStatus.setSelected(true);

            }
        });




  /*      Button btn_selectAll;
        btn_selectAll = (Button)
        btn_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lview.getCount()iss", String.valueOf(lview.getCount()));
                for (int i = 0; i < lview.getCount(); i++) {
                    View vw = lview.getAdapter().getView(i,null,null);
                    CheckBox checkBox = (CheckBox) vw.findViewById(R.id.check_status);
                    Log.d("checkboxstatusissss", String.valueOf(checkBox.isChecked()));
                    checkBox.setChecked(true);
                    Log.d("checkboxstatusissss2", String.valueOf(checkBox.isChecked()));
                    lview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });*/


        return convertView;
    }


    public void filter(String charText,ArrayList<Class_UnpaidStudList> feesList) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.feesUnPaidList.clear();

     /*   for(FeesUnpaidModel feesUnpaidModel : feesList){
            String  stdName = feesUnpaidModel.getStudent_name().toString();
            //Log.d("StudeNameissssss",stdName);
        }*/

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.feesUnPaidList.addAll(feesList);
                } else {
                    for (Class_UnpaidStudList wp : feesList) {
                        //Log.d("GetCollegeNameissss",wp.getCollege_name().toLowerCase((Locale.getDefault())));
                       /* if (wp.getCollege_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getLead_id().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStudent_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText)) {
                            this.feesUnPaidList.add(wp);
                        }*/
                        if  ((wp.getCollegeName()!=null && wp.getCollegeName().toLowerCase().contains(charText.toLowerCase())) || ( wp.getLead_id()!=null && wp.getLead_id().toLowerCase().contains(charText.toLowerCase())) || ( wp.getMobileNo()!=null && wp.getMobileNo().toLowerCase().contains(charText.toLowerCase())) || ( wp.getStudentName()!=null && wp.getStudentName().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getMobileNo()!=null && wp.getMobileNo().toLowerCase().contains(charText.toLowerCase()))) {
                            this.feesUnPaidList.add(wp);
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }
}
