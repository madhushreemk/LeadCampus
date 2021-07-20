package com.leadcampusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//import com.android.sripad.leadnew_22_6_2018.R;

/**
 * Created by Admin on 28-05-2018.
 */

public class TShirtPaidAckAdapter extends BaseAdapter{

    public ArrayList<TshirtPaidModel> tshirtPaidList;
    Activity activity;

    public TShirtPaidAckAdapter(Activity activity, ArrayList<TshirtPaidModel> tshirtPaidList) {
        super();
        this.activity = activity;
        this.tshirtPaidList = tshirtPaidList;
    }

    @Override
    public int getCount() {
        //return projList.size();
        return tshirtPaidList.size();
    }

    @Override
    public TshirtPaidModel getItem(int position) {

        //return projList.get(position);
        return tshirtPaidList.get(position);
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
        Button mUnapprove;
        Button mExchange;
      //  CheckBox mStatus;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final Spinner[] reson_spin = new Spinner[1];
        final Spinner[] resize_spin = new Spinner[1];
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.tshirt_paid_listrow, null);
            holder = new ViewHolder();

            holder.mstudName = (TextView) convertView.findViewById(R.id.txt_studName);
            holder.mleadId = (TextView) convertView.findViewById(R.id.txt_leadId);
            holder.mTShirtSize = (TextView) convertView.findViewById(R.id.txt_tShirtSize);
            holder.mProjectCount = (TextView) convertView.findViewById(R.id.txt_projCount);
            holder.mRequestedId = (TextView) convertView.findViewById(R.id.txt_requestedId);
            //holder.mRegistrationDate = (TextView) convertView.findViewById(R.id.txt_registeredDateTxt);
            holder.mCollegeName = (TextView) convertView.findViewById(R.id.txt_collegeName);
            holder.mPhoneNumber = (TextView) convertView.findViewById(R.id.txt_phoneNumber);
            holder.mUnapprove = (Button) convertView.findViewById(R.id.unapprove);
            holder.mExchange = (Button) convertView.findViewById(R.id.exchange);
         //   holder.mStatus = (CheckBox) convertView.findViewById(R.id.check_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TshirtPaidModel item = tshirtPaidList.get(position);

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
        if(item.getRequestedId()!=null) {
            holder.mRequestedId.setText(item.getRequestedId().toString());
        }
        if(item.getTshirtSize()!=null) {
            holder.mTShirtSize.setText(item.getTshirtSize().toString());
        }

        holder.mProjectCount.setText(item.getProjectcount());
        holder.mRequestedId.setVisibility(View.INVISIBLE);


  /*      if(item.getProj_count()!=null) {
            holder.mProjectCount.setText(item.getProj_count());
        }*/
        /*else{
            holder.mRegistrationDate.setText("");
        }*/

      /*  if(item.getStatus().toString().equals("1")) {
            holder.mStatus.setChecked(true);
            //holder.mStatus.setSelected(true);
        }
        if(item.getStatus().toString().equals("0")) {
            holder.mStatus.setChecked(false);
            //holder.mStatus.setSelected(false);
        }*/

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

        //final CheckBox checkboxSelected = holder.mStatus;
      /*  final int pos = position;
        holder.mStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("insidelistview","listview clicked");

                if(isChecked) {
                    Log.d("ischhecked","true");
                    holder.mStatus.setChecked(true);
                    //holder.mStatus.setEnabled(true);
                    tshirtPaidList.get(pos).setStatus("1");
                }else{
                    Log.d("ischhecked","false");
                    holder.mStatus.setChecked(false);
                    tshirtPaidList.get(pos).setStatus("0");
                }
                //notifyDataSetChanged();
                //holder.mStatus.setChecked(true);
                //holder.mStatus.setSelected(true);

            }
        });
*/



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

        holder.mUnapprove.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    String str_requestId=holder.mRequestedId.getText().toString();
                                                    String str_size=holder.mTShirtSize.getText().toString();
                                                    String str_leadId=holder.mleadId.getText().toString();
                                                    TShirtUnpaidFragment.SubmitUnapprove submitUnapprove = new TShirtUnpaidFragment.SubmitUnapprove(activity);
                                                    submitUnapprove.execute(str_requestId,str_size,str_leadId);

                                                    /*LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
                                                    View mView = layoutInflaterAndroid.inflate(R.layout.pmfund_prompt, null);
                                                    AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
                                                    alertDialogBuilderUserInput.setView(mView);

                                                    final TextView headerText = (TextView) mView.findViewById(R.id.textView1);
                                                    final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.fundamtPromt_et);
                                                    headerText.setText("Enter Reson for T-shirt Unapproved");
                                                    headerText.setTextSize(14);
                                                    userInputDialogEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                                                    alertDialogBuilderUserInput
                                                            .setCancelable(false)
                                                            .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialogBox, int id) {
                                                                    // ToDo get user input here
                                                                    String str_requestId=holder.mRequestedId.getText().toString();
                                                                    String str_size=holder.mTShirtSize.getText().toString();
                                                                    String str_leadId=holder.mleadId.getText().toString();
                                                                    TShirtUnpaidFragment.SubmitUnapprove submitUnapprove = new TShirtUnpaidFragment.SubmitUnapprove(activity);
                                                                    submitUnapprove.execute(str_requestId,str_size,str_leadId);
                                                                    Toast.makeText(activity,userInputDialogEditText.getText().toString()+",,"+holder.mleadId.getText().toString(),Toast.LENGTH_LONG).show();
                                                                }
                                                            })

                                                            .setNegativeButton("Cancel",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialogBox, int id) {
                                                                            dialogBox.cancel();
                                                                        }
                                                                    });

                                                    AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                                                    alertDialogAndroid.show();*/
                                                }
                                            });

        holder.mExchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(activity,"Exchange: ",Toast.LENGTH_LONG).show();
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
                View mView = layoutInflaterAndroid.inflate(R.layout.pmtshirtexchange_prompt, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(activity);
                alertDialogBuilderUserInput.setView(mView);

                reson_spin[0] = (Spinner) mView.findViewById(R.id.reson_spin);
                resize_spin[0] = (Spinner) mView.findViewById(R.id.resize_spin);
                setResonSpinner();
                setResizeSpinner();
             /*   final TextView headerText = (TextView) mView.findViewById(R.id.textView1);
                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.fundamtPromt_et);
                headerText.setText("Enter Reson for T-shirt Exchange");
                headerText.setTextSize(14);
                userInputDialogEditText.setInputType(InputType.TYPE_CLASS_TEXT);*/
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                               String str_reson = reson_spin[0].getSelectedItem().toString();
                               String str_resize = resize_spin[0].getSelectedItem().toString();
                                String str_requestId=holder.mRequestedId.getText().toString();
                                String str_size=holder.mTShirtSize.getText().toString();
                                String str_leadId=holder.mleadId.getText().toString();
                                String str_resizenew = null;
                                if(str_resize.equals("S - Small")){
                                    str_resizenew="S";
                                }
                                if(str_resize.equals("M - Medium")){
                                    str_resizenew="M";
                                }
                                if(str_resize.equals("L - Large")){
                                    str_resizenew="L";
                                }
                                if(str_resize.equals("XL - Extra Large")){
                                    str_resizenew="XL";
                                }
                                if(str_resize.equals("XXL - Double Extra Large")){
                                    str_resizenew="XXL";
                                }
                                Log.i("tag","str_resizenew=="+str_resizenew+" str_resize=="+str_resize);
                                TShirtUnpaidFragment.SubmitExchangeDetails submitExchangeDetails = new TShirtUnpaidFragment.SubmitExchangeDetails(activity);
                                submitExchangeDetails.execute(str_leadId,str_requestId,str_size,str_resizenew,str_reson);

                                //   Toast.makeText(activity,userInputDialogEditText.getText().toString(),Toast.LENGTH_LONG).show();
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
            private void setResonSpinner() {
                final ArrayList listBg = new ArrayList();
                listBg.add("Size Variation");
                listBg.add("Low Printing Quality");
                listBg.add("Teared");
                listBg.add("Dusty");
                listBg.add("Old Stock");

                //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
                ArrayAdapter dataAdapterBg = new ArrayAdapter(activity, R.layout.simple_spinner_items, listBg);

                // Drop down layout style - list view with radio button
                dataAdapterBg.setDropDownViewResource(R.layout.spinnercustomstyle);

                // attaching data adapter to spinner
                reson_spin[0].setAdapter(dataAdapterBg);
                //spin_bg.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
            }
            private void setResizeSpinner() {
                final ArrayList listBg = new ArrayList();
                listBg.add("S - Small");
                listBg.add("M - Medium");
                listBg.add("L - Large");
                listBg.add("XL - Extra Large");
                listBg.add("XXL - Double Extra Large");

                //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
                ArrayAdapter dataAdapterBg = new ArrayAdapter(activity, R.layout.simple_spinner_items, listBg);

                // Drop down layout style - list view with radio button
                dataAdapterBg.setDropDownViewResource(R.layout.spinnercustomstyle);

                // attaching data adapter to spinner
                resize_spin[0].setAdapter(dataAdapterBg);
                //spin_bg.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
            }

        });



        return convertView;
    }



    public void filter(String charText,ArrayList<TshirtPaidModel> feesList,String selectedCollegeName) {
        //charText = charText.toLowerCase(Locale.getDefault());
        //Log.d("charTextissss",charText);
        this.tshirtPaidList.clear();

     /*   for(FeesUnpaidModel feesUnpaidModel : feesList){
            String  stdName = feesUnpaidModel.getStudent_name().toString();
            //Log.d("StudeNameissssss",stdName);
        }*/

        if(charText!=null) {
            if(feesList!=null) {
                if (charText.isEmpty() || charText.length() == 0) {
                    this.tshirtPaidList.addAll(feesList);
                } else {
                    for (TshirtPaidModel wp : feesList) {
                        //Log.d("GetCollegeNameissss",wp.getCollege_name().toLowerCase((Locale.getDefault())));
                       /* if (wp.getCollege_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getLead_id().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText) || wp.getStudent_name().toLowerCase(Locale.getDefault()).contains(charText) || wp.getPhone_number().toLowerCase(Locale.getDefault()).contains(charText)) {
                            this.feesUnPaidList.add(wp);
                        }*/
                        if(selectedCollegeName == null) {
                            if ((wp.getCollege_name()!=null && wp.getCollege_name().toLowerCase().contains(charText.toLowerCase())) || ( wp.getLead_id()!=null && wp.getLead_id().toLowerCase().contains(charText.toLowerCase())) || ( wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())) || ( wp.getStudent_name()!=null && wp.getStudent_name().toLowerCase().contains(charText.toLowerCase()) ) || ( wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase()))) {
                                this.tshirtPaidList.add(wp);
                            }
                        }else{
                            if ((wp.getCollege_name()!=null && wp.getCollege_name().equals(selectedCollegeName)) && ((wp.getCollege_name()!=null && wp.getCollege_name().toLowerCase().contains(charText.toLowerCase())) || (wp.getLead_id()!=null && wp.getLead_id().toLowerCase().contains(charText.toLowerCase())) || (wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())) || (wp.getStudent_name()!=null && wp.getStudent_name().toLowerCase().contains(charText.toLowerCase())) || (wp.getPhone_number()!=null && wp.getPhone_number().toLowerCase().contains(charText.toLowerCase())))) {
                                this.tshirtPaidList.add(wp);
                            }
                        }

                    }
                }
                notifyDataSetChanged();
            }
        }
    }
}
