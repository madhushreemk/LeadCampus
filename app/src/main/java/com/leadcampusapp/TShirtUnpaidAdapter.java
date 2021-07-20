package com.leadcampusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

//import com.android.sripad.leadnew_22_6_2018.R;

/**
 * Created by Admin on 28-05-2018.
 */

public class TShirtUnpaidAdapter extends BaseAdapter{

    public ArrayList<TShirtUnpaidModel> feesUnPaidList;
    Activity activity;
    EditText userInputDialogEditText;
    String str_requestId,str_reson;

    public TShirtUnpaidAdapter(Activity activity, ArrayList<TShirtUnpaidModel> feesUnPaidList) {
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
    public TShirtUnpaidModel getItem(int position) {

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
        TextView mTShirtSize;
        TextView mCollegeName;
        TextView mPhoneNumber;
        TextView mProjectCount;
        TextView mRequestedId;
        ImageView mReject;
        CheckBox mStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final String[] userInput = new String[1];

        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tshirt_unpaid_listrow, null);
            holder = new ViewHolder();

            holder.mstudName = (TextView) convertView.findViewById(R.id.txt_studName);
            holder.mleadId = (TextView) convertView.findViewById(R.id.txt_leadId);
            holder.mTShirtSize = (TextView) convertView.findViewById(R.id.txt_tShirtSize);
            holder.mProjectCount = (TextView) convertView.findViewById(R.id.txt_projCount);
            holder.mRequestedId = (TextView) convertView.findViewById(R.id.txt_requestedId);
            holder.mReject = (ImageView) convertView.findViewById(R.id.reject);
            holder.mCollegeName = (TextView) convertView.findViewById(R.id.txt_collegeName);
            holder.mPhoneNumber = (TextView) convertView.findViewById(R.id.txt_phoneNumber);
            holder.mStatus = (CheckBox) convertView.findViewById(R.id.check_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TShirtUnpaidModel item = feesUnPaidList.get(position);

        if(item.getStudent_name()!=null) {
            holder.mstudName.setText(item.getStudent_name().toString());
        }
        if(item.getPhone_number()!=null) {
            holder.mPhoneNumber.setText(item.getPhone_number().toString());
        }
        if(item.getCollege_name()!=null) {
            holder.mCollegeName.setText(item.getCollege_name().toString());
        }
        if(item.getLead_id()!=null) {
            holder.mleadId.setText(item.getLead_id().toString());
        }
        if(item.getRequested_id()!=null) {
            holder.mRequestedId.setText(item.getRequested_id().toString());
        }
        if(item.getTshirt_size()!=null) {
            holder.mTShirtSize.setText(item.getTshirt_size().toString());
        }

        holder.mProjectCount.setText(item.getProj_count());

        holder.mRequestedId.setVisibility(View.INVISIBLE);

  /*      if(item.getProj_count()!=null) {
            holder.mProjectCount.setText(item.getProj_count());
        }*/
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
        holder.mReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
                View mView = layoutInflaterAndroid.inflate(R.layout.pmfund_prompt, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
                alertDialogBuilderUserInput.setView(mView);

                final TextView headerText = (TextView) mView.findViewById(R.id.textView1);
                userInputDialogEditText = (EditText) mView.findViewById(R.id.fundamtPromt_et);
                headerText.setText("Enter Reson for T-shirt Reject");
                headerText.setTextSize(14);
                str_requestId = holder.mRequestedId.getText().toString();
                //  str_reson = userInputDialogEditText.getText().toString();
                userInputDialogEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                                //    userInput[0] =userInputDialogEditText.getText().toString();

                                //  TShirtUnpaidFragment.SubmitRejectDetails submitRejectDetails = new TShirtUnpaidFragment.SubmitRejectDetails(activity);
                                // submitRejectDetails.execute(str_requestId, str_reson);
                                //   Toast.makeText(activity, userInputDialogEditText.getText().toString() + ",," + holder.mleadId.getText().toString(), Toast.LENGTH_LONG).show();
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                // AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                //alertDialogAndroid.show();
                AlertDialog alertDialog = alertDialogBuilderUserInput.create();
                alertDialog.show();

                Button theButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                theButton.setOnClickListener(new CustomListener(alertDialog));
            }
        });


   /*     holder.mStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("insidelistview","listview clicked");
                notifyDataSetChanged();
            }
        });*/

        //final CheckBox checkboxSelected = holder.mStatus;
        final int pos = position;
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
    public void filter(String charText,ArrayList<TShirtUnpaidModel> feesList,String selectedCollegeName) {
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
                    for (TShirtUnpaidModel wp : feesList) {
                        //Log.d("GetCollegeNameissss",wp.getCollege_name().toLowerCase((Locale.getDefault())));
                       /* if (wp.getCollege_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getLead_id().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStudent_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText)) {
                            this.feesUnPaidList.add(wp);
                        }*/
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
}
