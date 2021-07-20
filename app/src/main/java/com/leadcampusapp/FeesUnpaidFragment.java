package com.leadcampusapp;

/**
 * Created by Admin on 20-07-2018.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

//import com.android.sripad.leadnew_22_6_2018.R;

public class FeesUnpaidFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    int count=0;

    private ArrayList<Class_UnpaidStudList> feesList;
    private View view;
    private boolean isCompletedflag=false;
    private int projectStatusIcon;
    private FeesUnpaidAdapter adapter;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_SCellNo = "prefid_scellno"; //

    private SharedPreferences shardpref_S_obj;
    private String str_leadId,str_RegistrationId;
    private ProgressDialog progressDialog;

    private EditText etSearch;

    private ListView lview;
    private ArrayList<Class_UnpaidStudList> originalList = null;

    private int countProposed=0;
    private int countReapply=0;
    private int countRequestForCompletion=0;
    private int countApproved=0;
    private int countRejected=0;
    private int countCompleted=0;

    private Button btn_generateReport;
    public static final String PrefID_PMID = "prefid_pmid"; //
    public static final String  PREFBook_PM= "prefbook_pm";
    SharedPreferences shardprefPM_obj;
    public String str_MangerID;
    private AppCompatSpinner spin_college;
    private TextView txt_selectAll;
    private LinkedHashSet<String> collegeNameLst;
    private ArrayList<String> collegeNameArrLst;

    private Button btn_selectAll,btn_selectNone,btn_submit,btn_submitnew;
    private CheckBox chk_selectAll;
    private Context context;
    private ArrayList<String> arrLstLeadIds;
    private ArrayList<String> arrLstMobileNo;
    private ArrayList<String> arrLststudName;
    private ArrayList<String> arrLstregisteredDate;
    private ArrayList<String> arrLstmailId;
    private ArrayList<String> arrLstFees;
    private ArrayList<String> arrLstPaymentMode;
    private ArrayList<String> arrLstregId;
    private ArrayList<String> arrLststateId;
    private ArrayList<String> arrLstdistrictId;
    private ArrayList<String> arrLstcityId;
    private ArrayList<String> arrLststreamId;
    private ArrayList<String> arrLstcollgId;
 //   private ArrayList<String> arrLstcityId;

    private String str_leadIdOverall="";

    //private String
    private int counts=0;
    private int counter=0;
    private ArrayList<String> arrayLeadId;
    private SwipeRefreshLayout swipeLayout;
    private TextView txt_actualTotalCounts;
    AppCompatSpinner spin_year,spin_feesCategory;

    int YearListCount,feesCatglistcount;
    String str_YearListStatus,yearID,str_FeesCatgStatus,str_feesCatSlno,str_feesCatName,str_yearID,str_yearCode,str_feesID;
    Class_YearList[] arrayObj_Class_YearList;
    Class_YearList obj_Class_YearList;
    ArrayAdapter dataAdapter_yearList,dataAdapter_feesCatg,dataAdapter_college;
    Class_FeesCatg[] arrayObj_Class_FeesCatg;
    Class_FeesCatg obj_Class_FeesCatg;

    Class_Colleges[] arrayObj_Class_Colleges, arrayObj_Class_Colleges2,arrayObj_Class_Colleges3;
    Class_Colleges obj_Class_Colleges;
    int collegeslistcount,MDId,studentlistcount;
    String str_CollegesStatus,str_ColID="x";
    String str_studListStatus;
    Class_UnpaidStudList[] arrayObj_Class_studList;
    Class_UnpaidStudList obj_Class_studList;
    String str_remark;
    JSONArray jsonArray_submit = new JSONArray();
    String str_paymentMode;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    EditText stfonlinetrans_no_tv,stfonlinetransAmount_tv,stfamountbycash_tv,stfonline_receiptno_tv,stfamountbycash_receiptno_tv;

  //  EditText userInput;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_fees_newunpaid, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);



        txt_actualTotalCounts = (TextView) view.findViewById(R.id.txt_actualTotalCount);

        initializeviews();

        return view;
    }

    private void initializeviews() {
        lview = (NonScrollListView) view.findViewById(R.id.listview_feesUnpaid);



        arrLstLeadIds = new ArrayList<String>();
        arrLststreamId = new ArrayList<String>();
        arrLstregId = new ArrayList<String>();
        arrLstcityId = new ArrayList<String>();
        arrLstcollgId = new ArrayList<String>();
        arrLstdistrictId = new ArrayList<String>();
        arrLstFees = new ArrayList<String>();
        arrLstmailId = new ArrayList<String>();
        arrLstMobileNo = new ArrayList<String>();
        arrLstPaymentMode = new ArrayList<String>();
        arrLstregisteredDate = new ArrayList<String>();
        arrLststateId = new ArrayList<String>();
        arrLststudName = new ArrayList<String>();


        feesList = new ArrayList<Class_UnpaidStudList>();
        adapter = new FeesUnpaidAdapter(getActivity(),feesList);
        lview.setAdapter(adapter);

        arrayLeadId = new ArrayList<String>();


        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MangerID:",str_MangerID);
        MDId=Integer.parseInt(str_MangerID);

        collegeNameLst = new LinkedHashSet<String>();
        collegeNameLst.add("Select College");
        collegeNameArrLst = new ArrayList<String>();

        spin_college = (AppCompatSpinner) view.findViewById(R.id.spin_college);

      /*  btn_selectAll = (Button) view.findViewById(R.id.btn_selectAll);

        btn_selectNone = (Button) view.findViewById(R.id.btn_selectNone);*/

        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        btn_submitnew = (Button) view.findViewById(R.id.btn_submitnew);

      //  chk_selectAll = (CheckBox) view.findViewById(R.id.chk_selectAll);

        txt_selectAll = (TextView) view.findViewById(R.id.txt_selectAll);
        spin_feesCategory = (AppCompatSpinner) view.findViewById(R.id.spin_feesCategory);
        spin_year = (AppCompatSpinner) view.findViewById(R.id.spin_year);

        //initCollegeSpinner();

       // populateList();

        GetFromToDateAsyncCallWS getFromToDateAsyncCallWS=new GetFromToDateAsyncCallWS(getContext());
        getFromToDateAsyncCallWS.execute();


        etSearch = (EditText) view.findViewById(R.id.etSearch);

        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
// Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text, originalList);

            }
        });

        spin_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String collegeName = spin_college.getSelectedItem().toString();
                //adapter.filter(collegeName, originalList);

                if(spin_college.getSelectedItem().toString().equals("Select Institution")||str_ColID.equals("x")||str_CollegesStatus.equals("Fail")||str_CollegesStatus.equals("There are no colleges"))
                {
                    Log.e("In strCollegesStatus","In str_CollegesStatus");
                    //   amount_ET.setText("0");
                }
                else {
                    obj_Class_Colleges = (Class_Colleges) spin_college.getSelectedItem();
                    str_ColID = obj_Class_Colleges.getcollege_id().toString();
                    str_feesID = obj_Class_Colleges.getFees_Id().toString();

                    GetFeesUnpaid getFeesUnpaid = new GetFeesUnpaid(getActivity());
                    getFeesUnpaid.execute();
                }
          //      chk_selectAll.setChecked(false);
                int size = feesList.size();
                for(int i=0;i<size;i++)
                {
                    Class_UnpaidStudList feesUnpaidModel = feesList.get(i);
                    feesUnpaidModel.setStatus("0");
                }
                adapter.notifyDataSetChanged();


                if(!collegeName.equals("Select Institution")) {
                    adapter.filter(collegeName, originalList);
                }else{

                    //collegeName = "";
                    adapter.filter(collegeName, originalList);

               /*     if(counter!=1){
                        collegeName = "";
                        adapter.filter(collegeName, originalList);
                    }*/
                }

                Log.d("Totalcountselectchange:", String.valueOf(lview.getCount()));
                txt_actualTotalCounts.setText(String.valueOf(lview.getCount()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* chk_selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Log.d("Checkedtruesssss","truesssss");
                    int size = feesList.size();
                    for(int i=0;i<size;i++)
                    {
                        Class_UnpaidStudList feesUnpaidModel = feesList.get(i);
                        feesUnpaidModel.setStatus("1");
                    }
                    adapter.notifyDataSetChanged();
                }

                else{
                    Log.d("Checkedfalsessssssss","falsessss");
                    int size = feesList.size();
                    for(int i=0;i<size;i++)
                    {
                        Class_UnpaidStudList feesUnpaidModel = feesList.get(i);
                        feesUnpaidModel.setStatus("0");
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
*/
        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_YearList = (Class_YearList) spin_year.getSelectedItem();

                str_yearID = obj_Class_YearList.getSlno().toString();
                str_yearCode = obj_Class_YearList.getAcademicCode().toString();
                FeesCatglistAsyncCallWS feesCatglistAsyncCallWS = new FeesCatglistAsyncCallWS(getContext());
                feesCatglistAsyncCallWS.execute();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });


        spin_feesCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_FeesCatg = (Class_FeesCatg) spin_feesCategory.getSelectedItem();

                str_feesCatSlno = obj_Class_FeesCatg.getFees_Category_Slno().toString();
                str_feesCatName = obj_Class_FeesCatg.getFees_category_description().toString();


                Log.e("tag","str_feesCatSlno="+str_feesCatSlno);
                Log.e("tag","str_feesCatName="+str_feesCatName);

                CollegelistAsyncCallWS collegelistAsyncCallWS =new CollegelistAsyncCallWS(getContext());
                collegelistAsyncCallWS.execute();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });
        btn_submitnew.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 //  if(isChecked){
                                                 counter=0;
                                                 arrLstLeadIds = new ArrayList<String>();
                                                 arrLststreamId = new ArrayList<String>();
                                                 arrLstregId = new ArrayList<String>();
                                                 arrLstcityId = new ArrayList<String>();
                                                 arrLstcollgId = new ArrayList<String>();
                                                 arrLstdistrictId = new ArrayList<String>();
                                                 arrLstFees = new ArrayList<String>();
                                                 arrLstmailId = new ArrayList<String>();
                                                 arrLstMobileNo = new ArrayList<String>();
                                                 arrLstPaymentMode = new ArrayList<String>();
                                                 arrLstregisteredDate = new ArrayList<String>();
                                                 arrLststateId = new ArrayList<String>();
                                                 arrLststudName = new ArrayList<String>();

                                                 int size = feesList.size();
                                                 Log.e("size", "size=" + String.valueOf(size));
                                                 for (int i = 0; i < size; i++) {
                                                     Class_UnpaidStudList feesUnpaidModel = feesList.get(i);
                                                     if (feesUnpaidModel.getStatus().equals("1")) {
                                                         Log.e("tag", "check box true");
                                                         Log.e("tag", "arrLstLeadIds="+arrLstLeadIds.size());
                                                         arrLstLeadIds.add(feesUnpaidModel.getLead_id().toString());
                                                         arrLstMobileNo.add(feesUnpaidModel.getMobileNo().toString());
                                                         arrLststudName.add(feesUnpaidModel.getStudentName().toString());
                                                         arrLstregisteredDate.add(feesUnpaidModel.getRegistrationDate().toString());
                                                         arrLstmailId.add(feesUnpaidModel.getMaidId().toString());
                                                         if (feesUnpaidModel.getFees().toString().equalsIgnoreCase("") || feesUnpaidModel.getFees().toString() == null) {
                                                             arrLstFees.add("0");
                                                         } else {
                                                             arrLstFees.add(feesUnpaidModel.getFees().toString());
                                                         }
                                                         arrLstPaymentMode.add(feesUnpaidModel.getPayment_Mode().toString());
                                                         arrLstregId.add(feesUnpaidModel.getRegistration_Id().toString());
                                                         arrLststateId.add(feesUnpaidModel.getStateCode().toString());
                                                         arrLstdistrictId.add(feesUnpaidModel.getDistrictCode().toString());
                                                         arrLstcityId.add(feesUnpaidModel.getTalukaCode().toString());
                                                         arrLststreamId.add(feesUnpaidModel.getStreamId().toString());
                                                         arrLstcollgId.add(feesUnpaidModel.getCollegeCode().toString());
                                                         counter++;
                                                     }
                                                    /*  Log.e("tag","arrLstLeadIds="+arrLstLeadIds.get(i).toString());
                                                      Log.e("tag","arrLstPaymentMode="+arrLstPaymentMode.get(i).toString());*/

                                                 }
                                                 Log.e("tag", "counter=" + String.valueOf(counter));
                                                 if (counter > 0) {
                                                     final Dialog dialog = new Dialog(getActivity());

                                                     dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                     dialog.setContentView(R.layout.dialog_tuitionfeessubmission);
                                                     dialog.setCancelable(false);
                                                     TextView header_tv = (TextView) dialog.findViewById(R.id.header_tv);
                                                     Button dialogcancelbutton = (Button) dialog.findViewById(R.id.stfeescanceldialog_BT);
                                                     Button dialogsubmitbutton = (Button) dialog.findViewById(R.id.stfeessubmitdialog_BT);
                                                     stfonlinetrans_no_tv = (EditText) dialog.findViewById(R.id.Stfonlinetrans_no_TV);
                                                     stfonlinetransAmount_tv = (EditText) dialog.findViewById(R.id.StfonlinetransAmount_TV);
                                                     stfamountbycash_tv = (EditText) dialog.findViewById(R.id.StfAmountbycash_TV);
                                                     stfonline_receiptno_tv = (EditText) dialog.findViewById(R.id.Stfonline_receiptno_TV);
                                                     stfamountbycash_receiptno_tv = (EditText) dialog.findViewById(R.id.StfAmountbycash_receiptno_TV);
                                                     RadioGroup submitfees_radiogroup;
                                                     submitfees_radiogroup = (RadioGroup) dialog.findViewById(R.id.submit_stfeesradiogroup);

                                                     header_tv.setText("Student Amount Collection");
                                                     submitfees_radiogroup.setVisibility(View.GONE);
                                                     stfonlinetransAmount_tv.setVisibility(View.GONE);

                                                     dialogcancelbutton.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) {

                                                             dialog.dismiss();

                                                         }
                                                     });

                                                     dialogsubmitbutton.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View v) {

                                                             internetDectector = new Class_InternetDectector(getActivity());
                                                             isInternetPresent = internetDectector.isConnectingToInternet();

                                                             if (isInternetPresent) {

                                                                 //   Toast.makeText(getApplicationContext(),"Outside value:"+forOnline,Toast.LENGTH_LONG).show();
                                                                 if (validationfor_dialog()) {
                                                                    // Toast.makeText(getContext(), "true1 : "+stfonline_receiptno_tv.getText().toString(), Toast.LENGTH_LONG).show();
                                                                     SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                                                                     submitAcknowledgements.execute();
                                                                 }
                                                             }
                                                         }
                                                     });
                                                     dialog.show();
                                                 } else {
                                                     Toast.makeText(getActivity(), "You have not selected any student", Toast.LENGTH_LONG).show();
                                                 }

                                             }
                                         });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lview.getCount()iss", String.valueOf(lview.getCount()));

            //    adapter.notifyDataSetChanged();

                for ( ; counts < lview.getCount() ; counts++)
                {
                    View vw = lview.getAdapter().getView(counts,null,null);
                    CheckBox checkBox = (CheckBox) vw.findViewById(R.id.check_status);
                    SwitchCompat sw_PaymentMode = (SwitchCompat) vw.findViewById(R.id.SwitchPaymentMode);
                    TextView paymentMode = (TextView) vw.findViewById(R.id.paymentModenew);

                    Log.e("tag","checkbox="+checkBox.isChecked());

                        TextView txt_leadId = (TextView) vw.findViewById(R.id.txt_leadId);
                        TextView txt_MobileNo = (TextView) vw.findViewById(R.id.txt_phoneNumber);
                        TextView txt_studName = (TextView) vw.findViewById(R.id.txt_studName);
                        TextView txt_registeredDate = (TextView) vw.findViewById(R.id.txt_registeredDateTxt);
                        TextView txt_mailId = (TextView) vw.findViewById(R.id.txt_mailId);
                        TextView txt_Fees = (TextView) vw.findViewById(R.id.txt_Fees);

                        TextView txt_regId = (TextView) vw.findViewById(R.id.regId);
                        TextView txt_stateId = (TextView) vw.findViewById(R.id.stateId);
                        TextView txt_districtId = (TextView) vw.findViewById(R.id.districtId);
                        TextView txt_cityId = (TextView) vw.findViewById(R.id.cityId);
                        TextView txt_streamId = (TextView) vw.findViewById(R.id.streamId);
                        TextView txt_collgId = (TextView) vw.findViewById(R.id.collgId);

                        RadioGroup paymentMode_radiogroup=(RadioGroup) vw.findViewById(R.id.paymentMode_radiogroup);
                   /* if(paymentMode_radiogroup.getCheckedRadioButtonId()==R.id.rdb_Cash)
                    {
                        txt_MobileNo.setText("1");
                        str_paymentMode="1";
                    }else{
                        txt_MobileNo.setText("2");
                        str_paymentMode="2";
                    }
*/

                        RadioButton rdb_Cash=(RadioButton) vw.findViewById(R.id.rdb_Cash);
                        RadioButton rdb_Online=(RadioButton)vw.findViewById(R.id.rdb_Online);

                        Log.e("TextLeadIdissss", txt_leadId.getText().toString());
                        Log.e("txt_MobileNo=", txt_MobileNo.getText().toString());


                   /* if(rdb_Cash.isChecked()){
                        paymentMode.setText("1");
                        str_paymentMode="1";
                    }else if(rdb_Online.isChecked()){
                        paymentMode.setText("2");
                        str_paymentMode="2";
                    }
*/
                    rdb_Online.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set Yes values in ArrayList if RadioButton is checked
                            if (isChecked)
                                //selectedAnswers.set(position, "2");
                               // feesUnPaidList.get(position).setPayment_Mode("2");
                                paymentMode.setText("2");
                        }
                    });
// perform setOnCheckedChangeListener event on no button
                    rdb_Cash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
// set No values in ArrayList if RadioButton is checked
                            if (isChecked)
                                //  selectedAnswers.set(position, "1");
                               // feesUnPaidList.get(position).setPayment_Mode("1");
                            //   holder.paymentMode.setText(selectedAnswers.get(position).toString());
                                paymentMode.setText("1");
                        }
                    });
                   // paymentMode.setText(feesUnPaidList.get(position).getPayment_Mode());



                    if(checkBox.isChecked()) {

                        arrLstLeadIds.add(txt_leadId.getText().toString());
                        arrLstMobileNo.add(txt_MobileNo.getText().toString());
                        arrLststudName.add(txt_studName.getText().toString());
                        arrLstregisteredDate.add(txt_registeredDate.getText().toString());
                        arrLstmailId.add(txt_mailId.getText().toString());
                        if(txt_Fees.getText().toString().equalsIgnoreCase("")||txt_Fees.getText().toString()==null){
                            arrLstFees.add("0");
                        }else {
                            arrLstFees.add(txt_Fees.getText().toString());
                        }

                       /* if(sw_PaymentMode.isChecked())
                        { paymentMode.setText("1");
                            Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();

                        }else{
                            paymentMode.setText("2");
                            Toast.makeText(getContext(),"unclicked",Toast.LENGTH_SHORT).show();
                        }*/


/*



                        if(sw_PaymentMode.isChecked()){
                        //    paymentMode="1";
                            Log.e("tag","sw_PaymentMode.getTextOn().toString()="+sw_PaymentMode.getTextOn().toString());
                            Log.e("tag","paymentMode1="+paymentMode);
                        }else{
                         //   paymentMode="2";
                            Log.e("tag","sw_PaymentMode.getTextOff().toString()="+sw_PaymentMode.getTextOff().toString());

                            Log.e("tag","paymentMode2="+paymentMode);
                        }*/

                      //  ToggleButton toggle = (ToggleButton) vw.findViewById(R.id.togglebutton);
                      /*  sw_PaymentMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    paymentMode="1";
                                    Log.e("tag","sw_PaymentMode.getTextOn().toString()="+sw_PaymentMode.getTextOn().toString());
                                    Log.e("tag","paymentMode1="+paymentMode);
                                    // The toggle is enabled
                                } else {
                                    paymentMode="2";
                                    Log.e("tag","sw_PaymentMode.getTextOff().toString()="+sw_PaymentMode.getTextOff().toString());

                                    Log.e("tag","paymentMode2="+paymentMode);
                                    // The toggle is disabled
                                }
                            } });*/

                        Log.e("tag","paymentMode ="+paymentMode.getText().toString());
//                       Log.e("tag","str_paymentMode ="+str_paymentMode.toString());
                        arrLstPaymentMode.add(paymentMode.getText().toString());
                        arrLstregId.add(txt_regId.getText().toString());
                        arrLststateId.add(txt_stateId.getText().toString());
                        arrLstdistrictId.add(txt_districtId.getText().toString());
                        arrLstcityId.add(txt_cityId.getText().toString());
                        arrLststreamId.add(txt_streamId.getText().toString());
                        arrLstcollgId.add(txt_collgId.getText().toString());

                        //str_leadIdOverall = "'" + txt_leadId.getText().toString() + "'";

                        //arrayLeadId.add(str_leadIdOverall);

                        Log.d("str_leadIdOverallis",str_leadIdOverall);

                        //

                        counter++;

                    }

                }

                /*if(counter!=0){
                    str_leadIdOverall = "";
                    for(int i=0;i<arrayLeadId.size();i++){
                        str_leadIdOverall = str_leadIdOverall + arrayLeadId.get(i) + ",";
                        Log.d("stringleadidssssssxxxxx",str_leadIdOverall);

                        if(i == arrayLeadId.size()-1){
                            str_leadIdOverall = str_leadIdOverall.substring(0,str_leadIdOverall.length()-1);
                        }

                    }
                }*/


                if(counter!=0) {
                    //Log.d("stringfinalisssddddd:",str_leadIdOverall);
        /*            getActivity().finish();
                    startActivity(getActivity().getIntent());*/


                    LayoutInflater li = LayoutInflater.from(getContext());
                    View promptsView = li.inflate(R.layout.alert_dialog_remark, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getContext());

                    // set alert_dialog.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView.findViewById(R.id.etUserInput);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
                                    // edit text
                                    str_remark=userInput.getText().toString();
                                    boolean remarkEmpt=false;

                                    for(int i=0;i<arrLstPaymentMode.size();i++){
                                        Log.e("tag","arrLstPaymentMode="+arrLstPaymentMode.get(i));
                                        Log.e("tag","list="+feesList.get(i).getPayment_Mode());
                                    }
                                    Log.e("paymentMode=", String.valueOf(arrLstPaymentMode.contains("1")));
                                    if(arrLstPaymentMode.contains("1")){
                                        str_paymentMode="1";
                                    }else{
                                        str_paymentMode="2";
                                    }
                                    if(str_paymentMode.equalsIgnoreCase("1")) {
                                        Log.e("paymentMode=",str_paymentMode);
                                        if(userInput.getText().length()==0||str_remark==null){
                                            userInput.setError("Enter Remarks");
                                            userInput.requestFocus();
                                            remarkEmpt=false;
                                        }else{
                                            remarkEmpt=true;
                                            Toast.makeText(getContext(), "true1 : "+userInput.getText().toString(), Toast.LENGTH_LONG).show();

                                            /*SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                                            submitAcknowledgements.execute();*/
                                        }
                                    }
                                    if(remarkEmpt){
                                        Toast.makeText(getContext(), "true : "+userInput.getText().toString(), Toast.LENGTH_LONG).show();
                                       /* SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                                        submitAcknowledgements.execute();*/
                                    }
                                    // Toast.makeText(getContext(), "Entered: "+userInput.getText().toString(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //dialog.cancel();
                                            adapter.notifyDataSetChanged();
                                            dialog.dismiss();

                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();


                    //Toast.makeText(getActivity(),"Selected one student",Toast.LENGTH_LONG).show();
          /*          counter = 0;
                    getActivity().finish();
                    startActivity(getActivity().getIntent());*/

                }else{
                    Toast.makeText(getActivity(),"You have not selected any student",Toast.LENGTH_LONG).show();
                }


             /*   if(lview.getCount()==0 && collegeNameLst.size()==1){
                    txt_selectAll.setVisibility(View.GONE);
                    chk_selectAll.setVisibility(View.GONE);
                }else{
                    txt_selectAll.setVisibility(View.VISIBLE);
                    chk_selectAll.setVisibility(View.VISIBLE);
                }*/
            }
        });




        Log.d("countisssss", String.valueOf(lview.getCount()));



        Log.d("ListviewCountissssss", String.valueOf(lview.getCount()));
        //txt_actualTotalCount.setText(lview.getCount());


    }

    public boolean validationfor_dialog()
    {
        boolean stfonlinetrans_no_tv_VR1=true,stfonlinetrans_no_tv_VR2=true,stfonlinetransAmount_tv_VR3=true,stfonline_receiptno_tv_VR4=true,stfonline_receiptno_tv_VR5=true;
        boolean stfonlinetransAmount_tv_VR4=true,stfamountbycash_tv_VR5=true,stfamountbycash_tv_VR6=true;
        boolean stfonlinereceipt_no_tv_VR5=true,stfonlinereceipt_no_tv_VR6=true,stfonlinereceipt_no_tv_VR7=true;
        boolean stfamountbycash_receipt_no_tv_VR7=true,stfamountbycash_receipt_no_tv_VR8=true,stfamountbycash_receipt_no_tv_VR9=true;

        boolean bo_return=false;

    /*    if ((stfonline_receiptno_tv.getText().toString().trim().length() == 0))
        {
            stfonline_receiptno_tv.setError("Empty Not Allowed");
            stfonline_receiptno_tv.requestFocus();
            stfonlinetransAmount_tv_VR3 = false;
        }*/

        if(arrLstPaymentMode.contains("2")){
            str_paymentMode="2";
            Log.e("tag","arrLstPaymentMode=true");
            if(stfonline_receiptno_tv.getText().toString().trim().length()==0){
                stfonline_receiptno_tv.setError("Please Enter Transaction ID");
                stfonline_receiptno_tv.requestFocus();
                stfonlinetrans_no_tv_VR1=false;
            }

        }else{
                Log.e("tag","arrLstPaymentMode=false");

            }



        if(stfonlinetrans_no_tv_VR1)
        { bo_return=true; }
        else { bo_return=false; }


        /*if(bo_onlinechecked||bo_onlinechecked){return bo_return; }
        else { return bo_return; }*/
        if(arrLstPaymentMode.contains("2")){return bo_return; }
        else { return bo_return; }
       // return bo_return;
    }

    public boolean validation_Remarks()
    {
        boolean bo_return=false;
        boolean remark_txt=true;

        if(arrLstPaymentMode.contains("2")){
            str_paymentMode="2";
            if(stfonline_receiptno_tv.getText().toString().trim().length()==0){
                stfonline_receiptno_tv.setError("Empty Not Allowed");
                stfonline_receiptno_tv.requestFocus();
                remark_txt=false;
            }
        }else{
            str_paymentMode="1";
            remark_txt=true;
        }


        if(remark_txt)
        { bo_return=true; }
        else { bo_return=false; }

        if(arrLstPaymentMode.contains("2")){return bo_return; }
        else { return bo_return; }
        //return  bo_return;
    }
 /*   private void loadCollegeSpinner() {

    }*/

    public void getAdapterData(){
        for ( ; counts < lview.getCount() ; counts++) {
            View vw = lview.getAdapter().getView(counts,null,null);
            CheckBox checkBox = (CheckBox) vw.findViewById(R.id.check_status);
            SwitchCompat sw_PaymentMode = (SwitchCompat) vw.findViewById(R.id.SwitchPaymentMode);
            TextView paymentMode = (TextView) vw.findViewById(R.id.paymentModenew);

            Log.e("tag","checkbox="+checkBox.isChecked());



            TextView txt_leadId = (TextView) vw.findViewById(R.id.txt_leadId);
            TextView txt_MobileNo = (TextView) vw.findViewById(R.id.txt_phoneNumber);
            TextView txt_studName = (TextView) vw.findViewById(R.id.txt_studName);
            TextView txt_registeredDate = (TextView) vw.findViewById(R.id.txt_registeredDateTxt);
            TextView txt_mailId = (TextView) vw.findViewById(R.id.txt_mailId);
            TextView txt_Fees = (TextView) vw.findViewById(R.id.txt_Fees);

            TextView txt_regId = (TextView) vw.findViewById(R.id.regId);
            TextView txt_stateId = (TextView) vw.findViewById(R.id.stateId);
            TextView txt_districtId = (TextView) vw.findViewById(R.id.districtId);
            TextView txt_cityId = (TextView) vw.findViewById(R.id.cityId);
            TextView txt_streamId = (TextView) vw.findViewById(R.id.streamId);
            TextView txt_collgId = (TextView) vw.findViewById(R.id.collgId);

            RadioGroup paymentMode_radiogroup=(RadioGroup) vw.findViewById(R.id.paymentMode_radiogroup);
            RadioButton rdb_Cash=(RadioButton) vw.findViewById(R.id.rdb_Cash);
            RadioButton rdb_Online=(RadioButton)vw.findViewById(R.id.rdb_Online);
            Log.e("TextLeadIdissss", txt_leadId.getText().toString());
            Log.e("txt_MobileNo=", txt_MobileNo.getText().toString());
            if(rdb_Cash.isChecked()){
                paymentMode.setText("1");
                str_paymentMode="1";
            }else{
                paymentMode.setText("2");
                str_paymentMode="2";
            }

            if(checkBox.isChecked()) {

                arrLstLeadIds.add(txt_leadId.getText().toString());
                arrLstMobileNo.add(txt_MobileNo.getText().toString());
                arrLststudName.add(txt_studName.getText().toString());
                arrLstregisteredDate.add(txt_registeredDate.getText().toString());
                arrLstmailId.add(txt_mailId.getText().toString());
                if(txt_Fees.getText().toString().equalsIgnoreCase("")||txt_Fees.getText().toString()==null){
                    arrLstFees.add("0");
                }else {
                    arrLstFees.add(txt_Fees.getText().toString());
                }

                       /* if(sw_PaymentMode.isChecked())
                        { paymentMode.setText("1");
                            Toast.makeText(getContext(),"clicked",Toast.LENGTH_SHORT).show();

                        }else{
                            paymentMode.setText("2");
                            Toast.makeText(getContext(),"unclicked",Toast.LENGTH_SHORT).show();
                        }*/


/*



                        if(sw_PaymentMode.isChecked()){
                        //    paymentMode="1";
                            Log.e("tag","sw_PaymentMode.getTextOn().toString()="+sw_PaymentMode.getTextOn().toString());
                            Log.e("tag","paymentMode1="+paymentMode);
                        }else{
                         //   paymentMode="2";
                            Log.e("tag","sw_PaymentMode.getTextOff().toString()="+sw_PaymentMode.getTextOff().toString());

                            Log.e("tag","paymentMode2="+paymentMode);
                        }*/

                //  ToggleButton toggle = (ToggleButton) vw.findViewById(R.id.togglebutton);
                      /*  sw_PaymentMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (isChecked) {
                                    paymentMode="1";
                                    Log.e("tag","sw_PaymentMode.getTextOn().toString()="+sw_PaymentMode.getTextOn().toString());
                                    Log.e("tag","paymentMode1="+paymentMode);
                                    // The toggle is enabled
                                } else {
                                    paymentMode="2";
                                    Log.e("tag","sw_PaymentMode.getTextOff().toString()="+sw_PaymentMode.getTextOff().toString());

                                    Log.e("tag","paymentMode2="+paymentMode);
                                    // The toggle is disabled
                                }
                            } });*/

                Log.e("tag","paymentMode ="+paymentMode.getText().toString());

                arrLstPaymentMode.add(paymentMode.getText().toString());
                arrLstregId.add(txt_regId.getText().toString());
                arrLststateId.add(txt_stateId.getText().toString());
                arrLstdistrictId.add(txt_districtId.getText().toString());
                arrLstcityId.add(txt_cityId.getText().toString());
                arrLststreamId.add(txt_streamId.getText().toString());
                arrLstcollgId.add(txt_collgId.getText().toString());

                //str_leadIdOverall = "'" + txt_leadId.getText().toString() + "'";

                //arrayLeadId.add(str_leadIdOverall);

                Log.d("str_leadIdOverallis",str_leadIdOverall);

                //

                counter++;

            }

        }
    }
    private void populateList() {

        GetFeesUnpaid getFeesUnpaid = new GetFeesUnpaid(getActivity());
        getFeesUnpaid.execute();

    }

    @Override
    public void onRefresh() {
        initializeviews();
        swipeLayout.setRefreshing(false);
    }

    public class SubmitAcknowledgements extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //private ProgressBar progressBar;
        private ProgressDialog progressDialog;

        SubmitAcknowledgements (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = submitArrLystLeadIds();

            //Log.d("Soap response issssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
       /*     progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Submitting");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            progressDialog.dismiss();
            if(result!=null) {
                if (result.toString().equalsIgnoreCase("success")) {
                    Toast.makeText(context, "Student data saved successfully", Toast.LENGTH_LONG).show();
                    //counter = 0;
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                } else if(result.toString().equalsIgnoreCase("EXISTS")){
                    Toast.makeText(context, "Student mobile number already exists in LEAD", Toast.LENGTH_LONG).show();
                    //counter = 0;
                    //finish();
                    // startActivity(getIntent());
                } else {
                    Toast.makeText(context, result.toString(), Toast.LENGTH_LONG).show();
                }
            }
            //progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

  /*  private SoapPrimitive submitArrLystLeadIds() {
        String METHOD_NAME = "Save_Manager_PaymentDetails";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Save_Manager_PaymentDetails";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            JSONObject jsonObject = new JSONObject();

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("Fees_Category_ID",Integer.valueOf(str_feesCatSlno));
            request.addProperty("Fees_Category_Name",str_feesCatName);
            request.addProperty("Fees_ID",Integer.valueOf(str_feesID));
            request.addProperty("isNewRegistration",0);
            request.addProperty("Payment_Remark","testing");
            request.addProperty("Manager_Id",MDId);
//[{"Registration_Id":38267,"Student_Name":"Madhu","MobileNo":"8904674048","State_Id":17,"District_Id":266,"City_Id":68,"stream_Id":3,"College_ID":178,"Email_Id":"madhushree.kubsad@dfmail.org","Paid_date":"0","Paid_fees":100,"Payment_Mode":1,"Payment_Remark":"fees paid"}]
          *//*  jsonObject.put("Registration_Id",38267);
            Log.e("tag","str_RegistrationId="+str_RegistrationId);
            jsonObject.put("Student_Name",etName.getText().toString());
            jsonObject.put("MobileNo","8904674048");
            jsonObject.put("State_Id",17);
            jsonObject.put("District_Id",266);
            jsonObject.put("City_Id",68);
            jsonObject.put("stream_Id",3);
            jsonObject.put("College_ID",178);
            jsonObject.put("Email_Id","madhushree.kubsad@dfmail.org");
            jsonObject.put("Paid_date","2021-02-22");
            Log.e("tag","paid Date="+edt_paymentDate.getText().toString());
            jsonObject.put("Paid_fees",1);
            jsonObject.put("Payment_Mode",1);
            jsonObject.put("Payment_Remark","test test");*//*
            //  [{"Registration_Id":0,"Student_Name":"test a","MobileNo":"8904674048","State_Id":"17","District_Id":"256","City_Id":"14","stream":"5","College_ID":"37","Email_Id":"madhushree.kubsad@dfmail.org","Paid_date":"2021-02-22","Paid_fees":"1","Payment_Mode":"1","Payment_Remark":"test test"}]

            Log.e("tag","arrLstregId="+arrLstregId.size());
            *//*for(int k1=0;k1<arrLstregId.size();k1++) {
                Log.e("tag", "str_RegistrationId=" + arrLstregId.get(k1));
            }*//*
            for(int k=0;k<arrLstregId.size();k++) {

                jsonObject.put("Registration_Id", Integer.valueOf(arrLstregId.get(k)));
                Log.e("tag", "str_RegistrationId=" + arrLstregId.get(k));
                jsonObject.put("Student_Name", arrLststudName.get(k));
                jsonObject.put("MobileNo", arrLstMobileNo.get(k));
                jsonObject.put("State_Id", Integer.valueOf(arrLststateId.get(k)));
                jsonObject.put("District_Id", Integer.valueOf(arrLstdistrictId.get(k)));
                jsonObject.put("City_Id", Integer.valueOf(arrLstcityId.get(k)));
                jsonObject.put("stream_Id", Integer.valueOf(arrLststreamId.get(k)));
                jsonObject.put("College_ID", Integer.valueOf(arrLstcollgId.get(k)));
                jsonObject.put("Email_Id", arrLstmailId.get(k));
               // jsonObject.put("Paid_date", arrLstp);
              //  Log.e("tag", "paid Date=" + edt_paymentDate.getText().toString());
                jsonObject.put("Paid_fees", Integer.valueOf(arrLstFees.get(k)));
                jsonObject.put("Payment_Mode", Integer.valueOf(arrLstPaymentMode.get(k)));
              //  jsonObject.put("Payment_Remark", remark_ET.getText().toString());
            }
            request.addProperty("PaymentDetails","["+jsonObject+"]");


            Log.d("Requestisxxxxx",request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                // Log.d("soap response1xxxx",envelope.getResponse().toString());


                //SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("Responseissss",envelope.getResponse().toString());

                //return null;

                return response;

            }catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }
*/
  private SoapPrimitive submitArrLystLeadIds() {
      String METHOD_NAME = "Save_Manager_PaymentDetails";
      String SOAP_ACTION1 = "http://mis.leadcampus.org/Save_Manager_PaymentDetails";
      String NAMESPACE = "http://mis.leadcampus.org/";

      try{
          //mis.leadcampus.org

          SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

          JSONObject jsonObject = new JSONObject();
          JSONArray jsonArray = new JSONArray();

       //   Log.e("tag","isNewRegistration="+isNewRegistration);
          //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
          request.addProperty("Fees_Category_ID",Integer.valueOf(str_feesCatSlno));
          request.addProperty("Fees_Category_Name",str_feesCatName);
          request.addProperty("Fees_ID",Integer.valueOf(str_feesID));
          request.addProperty("isNewRegistration",0);
          request.addProperty("Payment_Remark",str_remark);
          request.addProperty("Manager_Id",MDId);

//[{"Registration_Id":38267,"Student_Name":"Madhu","MobileNo":"8904674048","State_Id":17,"District_Id":266,"City_Id":68,"stream_Id":3,"College_ID":178,"Email_Id":"madhushree.kubsad@dfmail.org","Paid_date":"0","Paid_fees":100,"Payment_Mode":1,"Payment_Remark":"fees paid"}]

          //  [{"Registration_Id":0,"Student_Name":"test a","MobileNo":"8904674048","State_Id":"17","District_Id":"256","City_Id":"14","stream":"5","College_ID":"37","Email_Id":"madhushree.kubsad@dfmail.org","Paid_date":"2021-02-22","Paid_fees":"1","Payment_Mode":"1","Payment_Remark":"test test"}]
         /* jsonObject.put("Registration_Id",Integer.valueOf(O_RegistrationId));
          Log.e("tag","str_RegistrationId="+O_RegistrationId);
          jsonObject.put("Student_Name",etName.getText().toString());
          jsonObject.put("MobileNo",etMobile.getText().toString());
          jsonObject.put("State_Id",Integer.valueOf(str_Sids));
          jsonObject.put("District_Id",Integer.valueOf(str_Dids));
          jsonObject.put("City_Id",Integer.valueOf(str_Cityids));
          jsonObject.put("stream_Id",Integer.valueOf(str_programid));
          jsonObject.put("College_ID",Integer.valueOf(str_ColID));
          jsonObject.put("Email_Id",emailid_ET.getText().toString());
          // jsonObject.put("Paid_date",edt_paymentDate.getText().toString());
          // Log.e("tag","paid Date="+edt_paymentDate.getText().toString());
          jsonObject.put("Paid_fees",Integer.valueOf(str_feesAmount));
          jsonObject.put("Payment_Mode",Integer.valueOf(str_PaymentModeID));
          //   jsonObject.put("Payment_Remark",remark_ET.getText().toString());

          request.addProperty("PaymentDetails","["+jsonObject+"]");*/
          ArrayList<String> jsonObjectList=new ArrayList<String>();

          try{
          for(int k=0;k<arrLstregId.size();k++) {

            //  jsonObject=null;
              jsonObject.put("Registration_Id", Integer.valueOf(arrLstregId.get(k)));
              Log.e("tag", "str_RegistrationId=" + arrLstregId.get(k));
              jsonObject.put("Student_Name", arrLststudName.get(k));
              Log.e("tag", "arrLststudName.get(k)=" + arrLststudName.get(k));
              jsonObject.put("MobileNo", arrLstMobileNo.get(k));
              Log.e("tag", "arrLstMobileNo=" + arrLstMobileNo.get(k));
              jsonObject.put("State_Id", Integer.valueOf(arrLststateId.get(k)));
              Log.e("tag", "arrLststateId=" + Integer.valueOf(arrLststateId.get(k)));
              jsonObject.put("District_Id", Integer.valueOf(arrLstdistrictId.get(k)));
              Log.e("tag", "arrLstdistrictId=" + Integer.valueOf(arrLstdistrictId.get(k)));
              jsonObject.put("City_Id", Integer.valueOf(arrLstcityId.get(k)));
              Log.e("tag", "arrLstcityId=" + Integer.valueOf(arrLstcityId.get(k)));
              jsonObject.put("stream_Id", Integer.valueOf(arrLststreamId.get(k)));
              Log.e("tag", "arrLststreamId=" + Integer.valueOf(arrLststreamId.get(k)));
              jsonObject.put("College_ID", Integer.valueOf(arrLstcollgId.get(k)));
              Log.e("tag", "arrLstcollgId=" + Integer.valueOf(arrLstcollgId.get(k)));
              jsonObject.put("Email_Id", arrLstmailId.get(k));
              Log.e("tag", "arrLstmailId=" + arrLstmailId.get(k));
              // jsonObject.put("Paid_date", arrLstp);
              //  Log.e("tag", "paid Date=" + edt_paymentDate.getText().toString());
              jsonObject.put("Paid_fees", Integer.valueOf(arrLstFees.get(k)));
              Log.e("tag", "arrLstFees=" + Integer.valueOf(arrLstFees.get(k)));
              jsonObject.put("Payment_Mode", Integer.valueOf(arrLstPaymentMode.get(k)));
              Log.e("tag", "arrLstPaymentMode=" + Integer.valueOf(arrLstPaymentMode.get(k)));
              //  jsonObject.put("Payment_Remark", remark_ET.getText().toString());
              Log.e("tag","jsonObject1="+jsonObject.toString());
              jsonObjectList.add(jsonObject.toString());
            //  for(int i=0;i<jsonObjectList.size();i++) {
             //     funjson(jsonObjectList.get(k));
              //}

              // jsonObjectList.put("",);
           //   jsonArray.put(jsonObject);
              Log.e("tag","jsonArray1="+jsonArray.toString());
              Log.e("tag","jsonObjectList1="+jsonObjectList.toString());

          }
          } catch (JSONException e) {
              e.printStackTrace();
          } catch (NumberFormatException e) {
              e.printStackTrace();
          }
         /* for(int j=0;j<jsonObjectList.size();j++){
              Log.e("tag","jsonObjectList="+jsonObjectList.get(j).toString());
              jsonArray_submit.put(jsonObjectList.get(j));
          }*/
          Log.e("tag","jsonObject2="+jsonObject.toString());
        //  Log.e("tag","jsonArray2="+jsonArray.toString());
      //    Log.e("tag","jsonArray_submit="+jsonArray_submit.toString());
          Log.e("tag","jsonObjectList2="+jsonObjectList.toString());

          JSONObject jsonObject1=new JSONObject();
          jsonObject1.put("",jsonArray);
          Log.e("tag","final jsonObj="+jsonObject1.toString());

          //  for(int jo=1;jo<jsonObject.length();jo++){

              request.addProperty("PaymentDetails",jsonObjectList.toString());

        //  }

          Log.d("Requestisxxxxx",request.toString());

          SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
          envelope.dotNet = true;
          //Set output SOAP object
          envelope.setOutputSoapObject(request);

          //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
          //Create HTTP call object
          //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
          HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
          //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

          try
          {
              androidHttpTransport.call(SOAP_ACTION1, envelope);

              //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
              // Log.d("soap response1xxxx",envelope.getResponse().toString());


             // SoapObject response = (SoapObject) envelope.getResponse();
              SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
              Log.e("Responseissss",envelope.getResponse().toString());

              //return null;

              return response;

          }catch(OutOfMemoryError ex){
              getActivity().runOnUiThread(new Runnable() {
                  public void run() {
                      Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                  }
              });
          }
          catch (Exception t) {
              Log.e("request fail", "> " + t.getMessage().toString());

              final String exceptionStr = t.getMessage().toString();
              getActivity().runOnUiThread(new Runnable() {
                  public void run() {
                      Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                  }
              });
          }
      }
      catch(OutOfMemoryError ex){
          getActivity().runOnUiThread(new Runnable() {
              public void run() {
                  Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
              }
          });
      }
      catch (Exception t) {
          Log.e("exception outside",t.getMessage().toString());

          final String exceptionStr = t.getMessage().toString();
          getActivity().runOnUiThread(new Runnable() {
              public void run() {
                  Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
              }
          });
      }
      return null;

  }


void funjson(String jsonObject){
   //   JSONArray jsonArray=new JSONArray();
    jsonObject.replace("\\","");

   // JSONObject jsonObj=new JSONObject();
 //   jsonObj.put(jsonObject);
    Log.e("tag","removed jsonObject:"+jsonObject);
    jsonArray_submit.put(jsonObject);
   //   return jsonArray;
}


   /* public class GetFeesUnpaid extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetFeesUnpaid (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getFeesUnpaid();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
      *//*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*//*
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {



            if(result != null) {

                SoapPrimitive S_LeadId, S_StudentName, S_RegistrationDate, S_CollegeName, S_MobileNo, S_IsFeesPaid, S_Status;
                Object O_LeadId, O_StudentName, O_RegistrationDate, O_CollegeName, O_MobileNo, O_IsFeesPaid, O_Status;
                String str_leadid = null, str_studentName = null, str_registrationDate = null, str_collegeName = null, str_MobileNo = null, str_IsFeesPaid = null, str_Status = null;

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    //Log.d("DistrictResult", list.toString());

                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {

                        O_StudentName = list.getProperty("StudentName");
                        if (!O_StudentName.toString().equals("anyType{}") && !O_StudentName.toString().equals(null)) {
                            S_StudentName = (SoapPrimitive) list.getProperty("StudentName");
                            str_studentName = S_StudentName.toString();
                            Log.d("StudentNameissss", str_studentName);
                        }

                        O_LeadId = list.getProperty("Lead_id");
                        if (!O_LeadId.toString().equals("anyType{}") && !O_LeadId.toString().equals(null)) {
                            S_LeadId = (SoapPrimitive) list.getProperty("Lead_id");
                            str_leadid = S_LeadId.toString();
                            Log.d("str_leadidisss", str_leadid);
                        }

                        O_RegistrationDate = list.getProperty("RegistrationDate");
                        if (!O_RegistrationDate.toString().equals("anyType{}") && !O_RegistrationDate.toString().equals(null)) {
                            S_RegistrationDate = (SoapPrimitive) list.getProperty("RegistrationDate");
                            Log.d("S_RegistrationDate", S_RegistrationDate.toString());
                            str_registrationDate = S_RegistrationDate.toString();
                            Log.d("str_registrationDate", str_registrationDate);
                        }

                        O_CollegeName = list.getProperty("CollegeName");
                        if (!O_CollegeName.toString().equals("anyType{}") && !O_CollegeName.toString().equals(null)) {
                            S_CollegeName = (SoapPrimitive) list.getProperty("CollegeName");
                            Log.d("S_CollegeName", S_CollegeName.toString());
                            str_collegeName = S_CollegeName.toString();
                            Log.d("str_collegeName", str_collegeName);

                            collegeNameLst.add(str_collegeName);

                        }

                        O_MobileNo = list.getProperty("MobileNo");
                        if (!O_MobileNo.toString().equals("anyType{}") && !O_MobileNo.toString().equals(null)) {
                            S_MobileNo = (SoapPrimitive) list.getProperty("MobileNo");
                            Log.d("S_MobileNo", S_MobileNo.toString());
                            str_MobileNo = S_MobileNo.toString();
                        }

                        O_IsFeesPaid = list.getProperty("isFeePaid");
                        if (!O_IsFeesPaid.toString().equals("anyType{}") && !O_IsFeesPaid.toString().equals(null)) {
                            S_IsFeesPaid = (SoapPrimitive) list.getProperty("isFeePaid");
                            Log.d("S_IsFeesPaid", S_IsFeesPaid.toString());
                            str_IsFeesPaid = S_IsFeesPaid.toString();
                        }

                        FeesUnpaidModel item;

                        item = new FeesUnpaidModel(str_studentName, str_leadid, str_registrationDate, str_collegeName, str_MobileNo, str_IsFeesPaid);
                        feesList.add(item);
                    }

                }

                originalList = new ArrayList<FeesUnpaidModel>();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);

                Log.d("Totalcountssss:", String.valueOf(lview.getCount()));
                txt_actualTotalCounts.setText(String.valueOf(lview.getCount()));

                initCollegeSpinner();
            }

            progressDialog.dismiss();



        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getFeesUnpaid() {
        String METHOD_NAME = "GetStudentRegistration";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetStudentRegistration";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("ManagerId",str_MangerID);
            //users.ad
            //request.add

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("soapresponse1xxxxFees",envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapresponseyyyyyyyFees",response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String str_exceptionerror = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),str_exceptionerror, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String str_exceptionerror = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),str_exceptionerror, Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }*/

    public class GetFeesUnpaid extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetFeesUnpaid (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getFeesUnpaid();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
      /*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {


            if(str_studListStatus.equalsIgnoreCase("Success")){
                originalList = new ArrayList<Class_UnpaidStudList>();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);

                Log.d("Totalcountssss:", String.valueOf(lview.getCount()));
                txt_actualTotalCounts.setText(String.valueOf(lview.getCount()));

            }else{
                Toast.makeText(getActivity(),str_studListStatus, Toast.LENGTH_LONG).show();
                originalList = new ArrayList<Class_UnpaidStudList>();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();
            }

            progressDialog.dismiss();



        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getFeesUnpaid() {
        String METHOD_NAME = "GetStudentRegistration";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetStudentRegistration";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("ManagerId",MDId);
            request.addProperty("College_Id",str_ColID);
            request.addProperty("Fees_Category_Id",str_feesCatSlno);
            request.addProperty("Academic_Id",str_yearID);
            //users.ad
            //request.add

            Log.e("tag","request studentlist="+request.toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("soapresponseFees",envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapresponseyyyyyyyFees",response.toString());

                studentlistcount = response.getPropertyCount();
                SoapObject summaryresponse=(SoapObject)response.getProperty(0);
                str_studListStatus=summaryresponse.getProperty("Status").toString();
                Log.d("summarylistcount", String.valueOf(response.getPropertyCount()));
                feesList.clear();

                if(str_studListStatus.equalsIgnoreCase("Success")) {
                    if (studentlistcount > 0) {
                        arrayObj_Class_studList = new Class_UnpaidStudList[studentlistcount];

                    //    ViewSummaryList_arraylist.clear();
                        for (int i = 0; i < studentlistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_UnpaidStudList innerObj_Class_manager = new Class_UnpaidStudList();
                            innerObj_Class_manager.setRegistration_Id(response_soapobj.getProperty("Registration_Id").toString()); //<Id>1</Id>
                            innerObj_Class_manager.setStudentName(response_soapobj.getProperty("StudentName").toString()); //<Centre_Code>Hubballi</Centre_Code>
                            innerObj_Class_manager.setMobileNo(response_soapobj.getProperty("MobileNo").toString());// <Centre_Name>HB</Centre_Name>
                            innerObj_Class_manager.setMaidId(response_soapobj.getProperty("MaidId").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setLead_id(response_soapobj.getProperty("Lead_id").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setStateCode(response_soapobj.getProperty("StateCode").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setDistrictCode(response_soapobj.getProperty("DistrictCode").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setTalukaCode(response_soapobj.getProperty("TalukaCode").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setCollegeCode(response_soapobj.getProperty("CollegeCode").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setCollegeName(response_soapobj.getProperty("CollegeName").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setStreamId(response_soapobj.getProperty("StreamId").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRegistrationDate(response_soapobj.getProperty("RegistrationDate").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setFees(response_soapobj.getProperty("Fees").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setIsFeePaid(response_soapobj.getProperty("isFeePaid").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setProjectcount(response_soapobj.getProperty("projectcount").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRequestedId(response_soapobj.getProperty("RequestedId").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setStatus(response_soapobj.getProperty("Status").toString());// <<Stateid>1</Stateid>

                           // innerObj_Class_manager.setPayment_Mode("1");
                            str_studListStatus = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_studList[i] = innerObj_Class_manager;

                            feesList.add(arrayObj_Class_studList[i]);

                         //   ViewSummaryList_arraylist.add(innerObj_Class_manager);
                         /*   String str_districtID = response_soapobj.getProperty("DistrictId").toString();
                            String str_districtname = response_soapobj.getProperty("DistrictName").toString();
                            String str_districtstateid = response_soapobj.getProperty("Stateid").toString();
*/
                            //DBCreate_Districtdetails_insert_2SQLiteDB(str_districtID,str_districtname,str_districtstateid);


                        }//end for loop

                    }//end of if

                }
                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String str_exceptionerror = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),str_exceptionerror, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String str_exceptionerror = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),str_exceptionerror, Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

    private class GetFromToDateAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
           /* dialog.setMessage("Please wait,District Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            GetFromToDate();  // get the District list
            return null;
        }

        public GetFromToDateAsyncCallWS(Context activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result) {


            if(str_YearListStatus.equalsIgnoreCase("Success"))
            {


                dataAdapter_yearList = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, arrayObj_Class_YearList);
                //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter_yearList.setDropDownViewResource(R.layout.spinnercustomstyle);
                //  districtsearch_ATV.setAdapter(dataAdapter_district);
                spin_year.setAdapter(dataAdapter_yearList);
                spin_year.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                //   }
            }


        }//end of onPostExecute
    }// end Async task

    public void GetFromToDate()
    {

        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "GetAcademicYearList";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/GetAcademicYearList";

        //for final

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);


            // <stateId>long</stateId>
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject  response = (SoapObject ) envelope.getResponse();
                Log.e("response acdamiclist",response.toString());
                YearListCount = response.getPropertyCount();
                SoapObject summaryresponse=(SoapObject)response.getProperty(0);
                str_YearListStatus=summaryresponse.getProperty("Status").toString();
                Log.d("YearListcount", String.valueOf(response.getPropertyCount()));


                if(str_YearListStatus.equals("Success")) {
                    if (YearListCount > 0) {
                        arrayObj_Class_YearList = new Class_YearList[YearListCount];
                        for (int i = 0; i < YearListCount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_YearList innerObj_Class_YearList = new Class_YearList();
                            innerObj_Class_YearList.setAcademicCode(response_soapobj.getProperty("AcademicCode").toString()); //<Id>1</Id>
                            innerObj_Class_YearList.setSlno(response_soapobj.getProperty("slno").toString()); //<Centre_Code>Hubballi</Centre_Code>
                            innerObj_Class_YearList.setStatus(response_soapobj.getProperty("Status").toString());// <Centre_Name>HB</Centre_Name>

                            str_YearListStatus = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_YearList[i] = innerObj_Class_YearList;

                        }//end for loop

                    }//end of if

                }

            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
                //  internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }

    private class FeesCatglistAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
           /* dialog.setMessage("Please wait,District Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            GetFeesCatglist();  // get the District list
            return null;
        }

        public FeesCatglistAsyncCallWS(Context activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            //dialog.dismiss();

            /*uploadfromDB_Statelist();
            uploadfromDB_Districtlist();
            setSpinner();*/
            //  uploadfromDB_Districtlist();


            if(str_FeesCatgStatus.equals("Success"))
            {


                dataAdapter_feesCatg = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, arrayObj_Class_FeesCatg);
                //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dataAdapter_feesCatg.setDropDownViewResource(R.layout.spinnercustomstyle);
                //  districtsearch_ATV.setAdapter(dataAdapter_district);
                spin_feesCategory.setAdapter(dataAdapter_feesCatg);
                spin_feesCategory.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                //   }
            }


        }//end of onPostExecute
    }// end Async task

    public void GetFeesCatglist()
    {

        String URL = Class_URL.URL_Manager.toString().trim();
        String METHOD_NAME = "Get_Fees_Category_Master";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Get_Fees_Category_Master";



        //for final


        try {
//vijay district
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            //  request.addProperty("distid", Long.parseLong(str_Sids));//Long

            // request.addProperty("distid", 17);//Long

            request.addProperty("Academic_Id", str_yearID);//Long
            request.addProperty("Registration_Id", 0);//Long
            request.addProperty("College_ID", 0);//Long

            //request.addProperty("stateId", 17);//Long
            // <stateId>long</stateId>
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                //  Log.i(TAG, "GetAllLoginDetails is running");
                //    result1 = (Vector<SoapObject>) envelope.getResponse();

                // response = (SoapPrimitive) envelope.getResponse();

                // SoapPrimitive res =(SoapPrimitive)envelope.getResponse();

                SoapObject  response = (SoapObject ) envelope.getResponse();
                Log.e("response feesCatglist",response.toString());
                feesCatglistcount = response.getPropertyCount();
                SoapObject districtresponse=(SoapObject)response.getProperty(0);
                str_FeesCatgStatus=districtresponse.getProperty("Status").toString();
                Log.d("feesCatgcount", String.valueOf(response.getPropertyCount()));


                if(str_FeesCatgStatus.equals("Success")) {
                    if (feesCatglistcount > 0) {
                        arrayObj_Class_FeesCatg = new Class_FeesCatg[feesCatglistcount];
                        for (int i = 0; i < feesCatglistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_FeesCatg innerObj_Class_FeesCatg = new Class_FeesCatg();
                            innerObj_Class_FeesCatg.setFees_category_code(response_soapobj.getProperty("fees_category_code").toString()); //<Id>1</Id>
                            innerObj_Class_FeesCatg.setFees_category_description(response_soapobj.getProperty("Fees_category_description").toString()); //<Centre_Code>Hubballi</Centre_Code>
                            innerObj_Class_FeesCatg.setFees_Category_Slno(response_soapobj.getProperty("Fees_Category_Slno").toString());// <Centre_Name>HB</Centre_Name>
                            innerObj_Class_FeesCatg.setAcademic_year(response_soapobj.getProperty("academic_year").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_FeesCatg.setStatus(response_soapobj.getProperty("Status").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_FeesCatg.setFees(response_soapobj.getProperty("Fees").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_FeesCatg.setFees_ID(response_soapobj.getProperty("Fees_ID").toString());// <<Stateid>1</Stateid>

                            str_FeesCatgStatus = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_FeesCatg[i] = innerObj_Class_FeesCatg;

                        }//end for loop

                    }//end of if

                }

            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
                //  internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }

    private void initCollegeSpinner() {
        //final ArrayList projType = new ArrayList();
   /*     Log.d("collegeNameLstisss",collegeNameLst.toString());

        for(String collegeName : collegeNameLst){
            Log.d("collegeNameissss",collegeName);
        }*/

        collegeNameArrLst.addAll(collegeNameLst);

        ArrayAdapter dataAdapter2 = new ArrayAdapter(getActivity(), R.layout.simple_spinner_items, collegeNameArrLst);

        // Drop down layout style - list view with radio button
        //dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        spin_college.setAdapter(dataAdapter2);
        //spin_projectType.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        //spin_college.setSupportBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.colorWhite));

        Log.d("countisssss", String.valueOf(lview.getCount()));

       /* if(lview.getCount()==0){
            txt_selectAll.setVisibility(View.GONE);
            chk_selectAll.setVisibility(View.GONE);
        }else{
            txt_selectAll.setVisibility(View.VISIBLE);
            chk_selectAll.setVisibility(View.VISIBLE);
        }*/

    }

    private class CollegelistAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait,College loading...");

            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            GetCollegelist();  // get the College list
            return null;
        }

        public CollegelistAsyncCallWS(Context activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            dialog.dismiss();


           /* uploadfromDB_Statelist();
            uploadfromDB_Districtlist();
*/
            /*uploadfromDB_Statelist();
            uploadfromDB_Districtlist();

            uploadfromDB_collegeTypeSpinner();*/ //vijay

            if(str_CollegesStatus.equals("Success"))
            {
                /*if (str_isProfileEdited.equals("1"))
                {
                    dataAdapter_college = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, arrayObj_Class_Colleges);
                    dataAdapter_college.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_college.setAdapter(dataAdapter_college);
                    spin_college.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                    str_ColID="y";
                    if(O_collegename!="" || O_collegename!=null)
                    {
                        for (int i = 0; i < spin_institution.getCount(); i++) {
                            if (spin_college.getItemAtPosition(i).toString().equals(O_collegename))
                            {
                                spin_college.setSelection(i);
                                break;
                            }
                        }
                    }

                }
                else {*/

                    str_ColID="y";
                    dataAdapter_college = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, arrayObj_Class_Colleges);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_college.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_college.setAdapter(dataAdapter_college);
                    spin_college.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                //}
            }
            else {
                //There are no colleges
                if (str_CollegesStatus.equals("There are no colleges"))
                {
                    /*List<String> list = new ArrayList<String>();
                    list.add("No Colleges");*/
                    dataAdapter_college = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, arrayObj_Class_Colleges3);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_college.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_college.setAdapter(dataAdapter_college);
                    spin_college.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                }
                else
                {
                    List<String> list = new ArrayList<String>();
                    list.add("WS Exception");
                    dataAdapter_college = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, list);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_college.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_college.setAdapter(dataAdapter_college);
                    spin_college.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                }

            }

        }//end of onPostExecute
    }// end Async task

    public void GetCollegelist()
    {

        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "GetCollegesOnCity";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/GetCollegesOnCity";



        //for final


        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            // request.addProperty("clgid", 0);//<mailid>string</mailid>  shubham.dsep@detedu.org  str_loginemailid


          //  int feesid =spin_feesCategory.getSelectedItemPosition();
          //  Log.e("tag","feesID="+feesid);
           // str_feesCatSlno= arrayObj_Class_FeesCatg[feesid].getFees_Category_Slno();
            Log.e("tag","Manager_Id="+MDId+"Fees_Category_Id="+str_feesCatSlno);
            request.addProperty("city", 0);//  <city>long</city>
            request.addProperty("type", "");// <type>string</type>
            request.addProperty("Manager_Id",MDId);
            request.addProperty("Fees_Category_Id",str_feesCatSlno);
            request.addProperty("User_Type","Manager");

            //
            Log.e("request collegelist",request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                //  Log.i(TAG, "GetAllLoginDetails is running");
                //    result1 = (Vector<SoapObject>) envelope.getResponse();

                // response = (SoapPrimitive) envelope.getResponse();

                // SoapPrimitive res =(SoapPrimitive)envelope.getResponse();

                SoapObject  response = (SoapObject ) envelope.getResponse();
                Log.e("resp collegelist",response.toString());
                collegeslistcount = response.getPropertyCount();
                SoapObject response_soapobj1 = (SoapObject) response.getProperty(0);
                str_CollegesStatus=response_soapobj1.getProperty("Status").toString();//Status=There are no colleges; //Status=Success;
                Log.d("Ccount", String.valueOf(response.getPropertyCount()));



                //anyType{CollegeId=0; TalukId=0; Status=There are no colleges; };


                /*SoapObject response_soapobj1 = (SoapObject) response.getProperty(0);

                nocollegestatus=response_soapobj1.getProperty("Status").toString();//<Status>Success</Status>

                Log.e("nocollege",nocollegestatus);


                (str_CollegesStatus.equals("Success")) {*/
                if(str_CollegesStatus.equals("Success"))
                {
                    if (collegeslistcount > 0) {
                        arrayObj_Class_Colleges = new Class_Colleges[collegeslistcount];


                        for (int i = 0; i < collegeslistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                         /*<CollegeId>long</CollegeId>
          <TalukId>long</TalukId>
          <College_Name>string</College_Name>
          <Status>string</Status>*/

                            Class_Colleges innerObj_Class_colleges = new Class_Colleges();
                            innerObj_Class_colleges.setcollege_id(response_soapobj.getProperty("CollegeId").toString()); //<CollegeId>long</CollegeId>
                            innerObj_Class_colleges.setcollege_tid(response_soapobj.getProperty("TalukId").toString()); //<TalukId>long</TalukId>
                            innerObj_Class_colleges.setcollegename(response_soapobj.getProperty("College_Name").toString());// <College_Name>string</College_Name>
                            innerObj_Class_colleges.setFees(response_soapobj.getProperty("Fees").toString());
                            innerObj_Class_colleges.setFees_Id(response_soapobj.getProperty("Fees_Id").toString());

                            innerObj_Class_colleges.setcollegestatus(response_soapobj.getProperty("Status").toString());// <Status>Success</Status>


                            str_CollegesStatus = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_Colleges[i] = innerObj_Class_colleges;


                            String str_collegeID = response_soapobj.getProperty("CollegeId").toString();
                            String str_college_tid = response_soapobj.getProperty("TalukId").toString();
                            String str_collegename = response_soapobj.getProperty("College_Name").toString();


                            // DBCreate_Collegedetails_insert_2SQLiteDB(str_collegeID,str_college_tid,str_collegename);

                            // DBCreate_Collegedetails_insert_2SQLiteDB(str_collegeID, str_college_tid, str_collegename);


                        }//end for loop

                    }//end of if


                }// end of if
                else
                {
                    arrayObj_Class_Colleges3 = new Class_Colleges[1];
                    Class_Colleges innerObj_Class_colleges = new Class_Colleges();
                    innerObj_Class_colleges.setcollege_id("0"); //<CollegeId>long</CollegeId>
                    innerObj_Class_colleges.setcollege_tid("0"); //<TalukId>long</TalukId>
                    innerObj_Class_colleges.setcollegename("There are no colleges");// <College_Name>string</College_Name>

                    str_CollegesStatus ="There are no colleges";
                    arrayObj_Class_Colleges3[0] = innerObj_Class_colleges;

                }


            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("getCollege fail", "> " + t.getMessage());
               // internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }
}//end of fragment classxx
