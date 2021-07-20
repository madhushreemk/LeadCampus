package com.leadcampusapp;

/**
 * Created by Admin on 20-07-2018.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;

//import com.android.sripad.leadnew_22_6_2018.R;

public class Funding_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    int count=0;

    private ArrayList<Class_FundStudList> feesList;
    private View view;
    private boolean isCompletedflag=false;
    private int projectStatusIcon;
    private FundStudListAdapter adapter;

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
    private ArrayList<Class_FundStudList> originalList = null;

    private int countProposed=0;
    private int countReapply=0;
    private int countRequestForCompletion=0;
    private int countApproved=0;
    private int countRejected=0;
    private int countCompleted=0;

    private Button btn_generateReport;
    public static final String PrefID_PMID = "prefid_pmid"; //
    public static final String  PREFBook_PM= "prefbook_pm";
    public static final String PrefID_PMEMailID = "prefid_pmemailid"; //

    SharedPreferences shardprefPM_obj;
    public String str_MangerID,str_MangerEmailID;
    private AppCompatSpinner spin_college;
    private TextView txt_selectAll;
    private LinkedHashSet<String> collegeNameLst;
    private ArrayList<String> collegeNameArrLst;

    private Button btn_selectAll,btn_selectNone,btn_submit,btn_submitnew;
    private CheckBox chk_selectAll;
    private Context context;

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
    int collegeslistcount,MDId,studentlistcount=0;
    String str_CollegesStatus,str_ColID="x";
    String str_studFundListStatus;
    Class_FundStudList[] arrayObj_Class_studList;
    Class_UnpaidStudList obj_Class_studList;
    String str_remark;
    JSONArray jsonArray_submit = new JSONArray();
    String str_paymentMode;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    EditText stfonlinetrans_no_tv,stfonlinetransAmount_tv,stfamountbycash_tv,stfonline_receiptno_tv,stfamountbycash_receiptno_tv;

    AppCompatSpinner spin_ticketStatus,priority_SP;
    Class_Project_FundMain obj_Class_Project_FundMain;
    String str_ticketSlno,str_ticketStatus,str_Status;
    int ticketStatusCount;
    Class_Project_FundMain[] arrayObj_Class_TicketStatus;
    ArrayAdapter adapter_ticketStatus;

    EditText edt_fromdate,edt_todate_fund,fromdateseterror_TV,todateseterror_TV;
    TextView txt_actualTotalCount;
    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;
    Class_FromToDate[] arrayObj_Class_FromToDate;
    String FromDate,ToDate;
    String str_FromToDateStatus;
    int From_ToDateCount;
    //  String yearID,str_yearID;
    ListView lv_summary;

    Class_Fund_Details[] class_fund_details;
    Class_Fund_Details classFundDetails;

    private ArrayList<String> arrLstLead_Id;
    private ArrayList<String> arrLstMobile_No;
    private ArrayList<String> arrLstStudent_Name;
    private ArrayList<String> arrLstRegistration_Id;
    private ArrayList<String> arrLstEmail_Id;
    private ArrayList<String> arrLstSem_Name;
    private ArrayList<String> arrLstCollege_Name;
    private ArrayList<String> arrLstPDID;
    private ArrayList<String> arrLstProject_Title;
    private ArrayList<String> arrLstRequested_Amount;
    private ArrayList<String> arrLstSantioned_Amount;
    private ArrayList<String> arrLstReleased_Amount;
    private ArrayList<String> arrLstTotal_Released_Amount;
    private ArrayList<String> arrLstBalance_Amount;
    private ArrayList<String> arrLstStatus;
    String ticketPriority="MEDIUM";
    ImageView FromToDate_btn;

    //  EditText userInput;
    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.activity_project_fund_main, container, false);

      /*  swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/



        //  txt_actualTotalCounts = (TextView) view.findViewById(R.id.txt_actualTotalCount);

        initializeviews();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Pending");
        arrayList.add("Manager Reject");
        arrayList.add("Accontent Reject");


        adapter_ticketStatus = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, arrayList);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_ticketStatus.setDropDownViewResource(R.layout.spinnercustomstyle);
        //  districtsearch_ATV.setAdapter(dataAdapter_district);
        spin_ticketStatus.setAdapter(adapter_ticketStatus);
        spin_ticketStatus.setSupportBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorBlack));

        spin_ticketStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                //   obj_Class_Project_FundMain = (Class_Project_FundMain) spin_ticketStatus.getSelectedItem();

                String ticketStatus = parent.getItemAtPosition(position).toString();

                if(ticketStatus.equalsIgnoreCase("Manager Reject")){
                    str_ticketStatus="MR";
                }else if(ticketStatus.equalsIgnoreCase("Accontent Reject")){
                    str_ticketStatus="AR";
                }else if(ticketStatus.equalsIgnoreCase("Pending")){
                    str_ticketStatus="Pending";
                }
//                str_ticketSlno = obj_Class_Project_FundMain.getSlno().toString();
                //              str_ticketStatus = obj_Class_Project_FundMain.getTicket_Status().toString();

                Log.e("tag","str_ticketStatus="+str_ticketStatus);

                // Toast.makeText(getApplicationContext(),"str_Programsid: "+str_programid,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });



        return view;
    }

    private void initializeviews() {
        lview = (ListView) view.findViewById(R.id.lv_summary);


        feesList = new ArrayList<Class_FundStudList>();
        adapter = new FundStudListAdapter(getActivity(),feesList);
        lview.setAdapter(adapter);

        arrayLeadId = new ArrayList<String>();


        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MangerID:",str_MangerID);
        MDId=Integer.parseInt(str_MangerID);

        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        str_MangerEmailID= shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        Log.d("str_MangerEmailID:",str_MangerEmailID);
        spin_ticketStatus= (AppCompatSpinner) view.findViewById(R.id.spin_ticketStatus);
        edt_fromdate = (EditText) view.findViewById(R.id.edt_fromdate);
        fromdateseterror_TV = (EditText) view.findViewById(R.id.fromdateseterror_TV);
        edt_todate_fund = (EditText) view.findViewById(R.id.edt_todate_fund);
        txt_actualTotalCount = (TextView) view.findViewById(R.id.txt_actualTotalCount);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        FromToDate_btn = (ImageView) view.findViewById(R.id.FromToDate_btn);

        //initCollegeSpinner();

        // populateList();
        GetFromToDateAsyncCallWS getFromToDateAsyncCallWS=new GetFromToDateAsyncCallWS(getContext());
        getFromToDateAsyncCallWS.execute();

        FromToDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetStudentFundReleased getStudentFundReleased=new GetStudentFundReleased(getContext());
                getStudentFundReleased.execute();
            }
        });
        edt_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                cDay = dayOfMonth;
                                cMonth = monthOfYear;
                                cYear = year;

                                // String date =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                //  String date =year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                String date =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;


                                SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
                                // SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

                                try {
                                    Date d=dateFormat.parse(date);
                                    System.out.println("Formated from"+dateFormat.format(d));
                                    fromdateseterror_TV.setVisibility(View.GONE);
                                    edt_fromdate.setText(dateFormat.format(d).toString());

                                }
                                catch(Exception e) {
                                    //java.text.ParseException: Unparseable date: Geting error
                                    System.out.println("Excep"+e);
                                }
                                //TextView txtExactDate = (TextView) findViewById(R.id.txt_exactDate);


                                //txtDate.edita
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-(1000 * 60 * 60 * 24 * 365 * 14));
                // - (1000 * 60 * 60 * 24 * 365.25 * 14)
//------

                datePickerDialog.show();
                //  originalList.clear();
                spin_ticketStatus.setSelection(0);
                //adapter.notifyDataSetChanged();
            }
        });
        /*edt_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                cDay = dayOfMonth;
                                cMonth = monthOfYear;
                                cYear = year;

                                // String date =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                // String date =year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                String date =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;


                                SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
                                // SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

                                try {
                                    Date d=dateFormat.parse(date);
                                    System.out.println("Formated to"+dateFormat.format(d));
                                    todateseterror_TV.setVisibility(View.GONE);
                                    edt_todate.setText(dateFormat.format(d).toString());

                                }
                                catch(Exception e) {
                                    //java.text.ParseException: Unparseable date: Geting error
                                    System.out.println("Excep"+e);
                                }
                                //TextView txtExactDate = (TextView) findViewById(R.id.txt_exactDate);


                                //txtDate.edita
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-(1000 * 60 * 60 * 24 * 365 * 14));
                // - (1000 * 60 * 60 * 24 * 365.25 * 14)
//------

                datePickerDialog.show();
                //originalList.clear();
                spin_ticketStatus.setSelection(0);
                //adapter.notifyDataSetChanged();
            }
        });*/
//        edt_todate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c = Calendar.getInstance();
//
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DatePickerTheme,
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//
//                                cDay = dayOfMonth;
//                                cMonth = monthOfYear;
//                                cYear = year;
//
//                                // String date =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//                                //  String date =year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//                                String date =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
//
//
//                                SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
//                                // SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
//
//                                try {
//                                    Date d=dateFormat.parse(date);
//                                    System.out.println("Formated To"+dateFormat.format(d));
//                                    todateseterror_TV.setVisibility(View.GONE);
//                                    edt_todate.setText(dateFormat.format(d).toString());
//
//                                }
//                                catch(Exception e) {
//                                    //java.text.ParseException: Unparseable date: Geting error
//                                    System.out.println("Excep"+e);
//                                }
//                                //TextView txtExactDate = (TextView) findViewById(R.id.txt_exactDate);
//
//
//                                //txtDate.edita
//                            }
//                        }, mYear, mMonth, mDay);
//
//                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-(1000 * 60 * 60 * 24 * 365 * 14));
//                // - (1000 * 60 * 60 * 24 * 365.25 * 14)
////------
//
//                datePickerDialog.show();
//                //  originalList.clear();
//                spin_ticketStatus.setSelection(0);
//                //adapter.notifyDataSetChanged();
//            }
//        });


        edt_todate_fund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                cDay = dayOfMonth;
                                cMonth = monthOfYear;
                                cYear = year;

                                // String date =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                //  String date =year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                String date =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;


                                SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
                                // SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

                                try {
                                    Date d=dateFormat.parse(date);
                                    System.out.println("Formated from"+dateFormat.format(d));
                                  //  todateseterror_TV.setVisibility(View.GONE);
                                    edt_todate_fund.setText(dateFormat.format(d).toString());

                                }
                                catch(Exception e) {
                                    //java.text.ParseException: Unparseable date: Geting error
                                    System.out.println("Excep"+e);
                                }
                                //TextView txtExactDate = (TextView) findViewById(R.id.txt_exactDate);


                                //txtDate.edita
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()-(1000 * 60 * 60 * 24 * 365 * 14));
                // - (1000 * 60 * 60 * 24 * 365.25 * 14)
//------

                datePickerDialog.show();
                //  originalList.clear();
               // spin_ticketStatus.setSelection(0);
                //adapter.notifyDataSetChanged();
            }
        });



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

        Log.d("countisssss", String.valueOf(lview.getCount()));



        Log.d("ListviewCountissssss", String.valueOf(lview.getCount()));
      //  txt_actualTotalCount.setText(lview.getCount());

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //  if(isChecked){
                counter=0;
                arrLstPDID = new ArrayList<String>();
                arrLstProject_Title = new ArrayList<String>();
                arrLstRequested_Amount = new ArrayList<String>();
                arrLstSantioned_Amount = new ArrayList<String>();
                arrLstReleased_Amount = new ArrayList<String>();
                arrLstTotal_Released_Amount = new ArrayList<String>();
                arrLstBalance_Amount = new ArrayList<String>();
                arrLstStatus = new ArrayList<String>();

                arrLstRegistration_Id = new ArrayList<String>();
                arrLstLead_Id = new ArrayList<String>();
                arrLstMobile_No = new ArrayList<String>();
                arrLstStudent_Name= new ArrayList<String>();
                arrLstEmail_Id = new ArrayList<String>();
                arrLstCollege_Name= new ArrayList<String>();
                arrLstSem_Name = new ArrayList<String>();

                int size = feesList.size();
                Log.e("size", "size=" + String.valueOf(size));
                for (int i = 0; i < size; i++) {
                    Class_FundStudList feesUnpaidModel = feesList.get(i);
                    Class_Fund_Details[] classFundDetails;
                    classFundDetails=feesList.get(i).getFund_details();

                    Log.e("tag", "arrLstLeadIds="+arrLstLead_Id.size());
                    arrLstLead_Id.add(feesUnpaidModel.getLead_Id().toString());
                    arrLstMobile_No.add(feesUnpaidModel.getMobile_No().toString());
                    arrLstStudent_Name.add(feesUnpaidModel.getStudent_Name().toString());
                    // arrLstRegistration_Id.add(feesUnpaidModel.getRegistration_Id().toString());
                    arrLstEmail_Id.add(feesUnpaidModel.getEmail_Id().toString());
                    arrLstCollege_Name.add(feesUnpaidModel.getCollege_Name().toString());
                    arrLstSem_Name.add(feesUnpaidModel.getSem_Name().toString());
                        /*if (feesUnpaidModel.getFees().toString().equalsIgnoreCase("") || feesUnpaidModel.getFees().toString() == null) {
                            arrLstFees.add("0");
                        } else {
                            arrLstFees.add(feesUnpaidModel.getFees().toString());
                        }*/

                    for(int j=0;j<classFundDetails.length;j++) {
                        if (classFundDetails[j].getStatus().equals("1")) {
                            Log.e("tag", "check box true");

                            arrLstPDID.add(classFundDetails[j].getPDID().toString());
                            arrLstProject_Title.add(classFundDetails[j].getProject_Title().toString());
                            arrLstReleased_Amount.add(classFundDetails[j].getReleased_Amount().toString());
                            arrLstRequested_Amount.add(classFundDetails[j].getRequested_Amount().toString());
                            arrLstTotal_Released_Amount.add(classFundDetails[j].getTotal_Released_Amount().toString());
                            arrLstBalance_Amount.add(classFundDetails[j].getBalance_Amount().toString());
                            arrLstSantioned_Amount.add(classFundDetails[j].getSantioned_Amount().toString());
                            arrLstStatus.add(classFundDetails[j].getStatus().toString());
                            arrLstRegistration_Id.add(classFundDetails[j].getRegistration_Id().toString());

                            counter++;

                        }


                    }
                    Log.e("tag", "counter1=" + String.valueOf(counter));

                                                    /*  Log.e("tag","arrLstLeadIds="+arrLstLeadIds.get(i).toString());
                                                      Log.e("tag","arrLstPaymentMode="+arrLstPaymentMode.get(i).toString());*/

                }
                for(int i9=0;i9<arrLstPDID.size();i9++){
                    Log.e("tag","arrLstPDID="+arrLstPDID.get(i9).toString());
                    Log.e("tag","arrLstRegistration_Id="+arrLstRegistration_Id.get(i9).toString());
                }
                Log.e("tag", "counter=" + String.valueOf(counter));
                if (counter > 0) {
                    final Dialog dialog = new Dialog(getActivity());

                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_ticketpriority);
                    dialog.setCancelable(false);
                    // TextView header_tv = (TextView) dialog.findViewById(R.id.header_tv);
                    Button dialogcancelbutton = (Button) dialog.findViewById(R.id.stfeescanceldialog_BT);
                    Button dialogsubmitbutton = (Button) dialog.findViewById(R.id.stfeessubmitdialog_BT);
                    priority_SP = dialog.findViewById(R.id.priority_SP);
                    TicketStatusAsyncCallWS ticketStatusAsyncCallWS=new TicketStatusAsyncCallWS(getActivity());
                    ticketStatusAsyncCallWS.execute();
                    priority_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected (AdapterView < ? > parent, View view,
                                                    int position, long id){
                            // TODO Auto-generated method stub
                            //   obj_Class_Project_FundMain = (Class_Project_FundMain) spin_ticketStatus.getSelectedItem();

                            ticketPriority = parent.getItemAtPosition(position).toString();

                            Log.e("tag","ticketPriority=="+ticketPriority);
//                str_ticketSlno = obj_Class_Project_FundMain.getSlno().toString();
                            //              str_ticketStatus = obj_Class_Project_FundMain.getTicket_Status().toString();

                            Log.e("tag","str_ticketStatus="+str_ticketStatus);

                            // Toast.makeText(getApplicationContext(),"str_Programsid: "+str_programid,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected (AdapterView < ? > parent){
                            // TODO Auto-generated method stub
                        }
                    });

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
                                // if (validationfor_dialog()) {
                                // Toast.makeText(getContext(), "true1 : "+stfonline_receiptno_tv.getText().toString(), Toast.LENGTH_LONG).show();
                                SubmitTicketDetails submitTicketDetails = new SubmitTicketDetails(getActivity());
                                submitTicketDetails.execute();
                                // }
                            }
                        }
                    });
                    dialog.show();
                } else {
                    Toast.makeText(getActivity(), "You have not selected any student", Toast.LENGTH_LONG).show();
                }

            }
        });



    }

    @Override
    public void onRefresh() {
        initializeviews();
        swipeLayout.setRefreshing(false);
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


            if(str_FromToDateStatus.equals("Success")) {
                Log.e("tag","arrayObj_Class_FromToDate="+arrayObj_Class_FromToDate.length);
                for (int j=0;j<arrayObj_Class_FromToDate.length;j++) {
//                    Log.e("tag","arrayObj_Class_FromToDate[j].getFrom_Date()=="+arrayObj_Class_FromToDate[j].getFrom_Date());
                    //                  Log.e("tag","arrayObj_Class_FromToDate[j].getTo_Date()=="+arrayObj_Class_FromToDate[j].getTo_Date());

                    FromDate=arrayObj_Class_FromToDate[j].getFrom_Date();
                    ToDate=arrayObj_Class_FromToDate[j].getTo_Date();
                    edt_fromdate.setText(FromDate);
                    edt_todate_fund.setText(ToDate);
                    yearID=arrayObj_Class_FromToDate[j].getAcademic_Id();

                    GetStudentFundReleased getStudentFundReleased=new GetStudentFundReleased(getContext());
                    getStudentFundReleased.execute();
                }
            }


        }//end of onPostExecute
    }// end Async task

    public void GetFromToDate()
    {

        String URL = Class_URL.URL_Manager.toString().trim();
        String METHOD_NAME = "Get_Academic_Detail";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Get_Academic_Detail";



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
                Log.e("response datelist",response.toString());
                From_ToDateCount = response.getPropertyCount();
                SoapObject summaryresponse=(SoapObject)response.getProperty(0);
                str_FromToDateStatus=summaryresponse.getProperty("Status").toString();
                Log.d("datelistcount", String.valueOf(response.getPropertyCount()));


                if(str_FromToDateStatus.equals("Success")) {
                    if (From_ToDateCount > 0) {
                        arrayObj_Class_FromToDate = new Class_FromToDate[From_ToDateCount];
                        for (int i = 0; i < From_ToDateCount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_FromToDate innerObj_Class_fromtodate = new Class_FromToDate();
                            innerObj_Class_fromtodate.setAcademic_Code(response_soapobj.getProperty("Academic_Code").toString()); //<Id>1</Id>
                            innerObj_Class_fromtodate.setAcademic_Id(response_soapobj.getProperty("Academic_Id").toString()); //<Centre_Code>Hubballi</Centre_Code>
                            innerObj_Class_fromtodate.setFrom_Date(response_soapobj.getProperty("From_Date").toString());// <Centre_Name>HB</Centre_Name>
                            innerObj_Class_fromtodate.setTo_Date(response_soapobj.getProperty("To_Date").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_fromtodate.setStatus(response_soapobj.getProperty("Status").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_fromtodate.setYear_Code(response_soapobj.getProperty("Year_Code").toString());// <<Stateid>1</Stateid>

                            str_FromToDateStatus = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_FromToDate[i] = innerObj_Class_fromtodate;

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

    private class TicketStatusAsyncCallWS extends AsyncTask<String, Void, Void> {
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

        public TicketStatusAsyncCallWS(Context activity) {
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


            if(str_Status.equals("Success"))
            {


                adapter_ticketStatus = new ArrayAdapter(getContext(), R.layout.spinnercustomstyle, arrayObj_Class_TicketStatus);
                //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter_ticketStatus.setDropDownViewResource(R.layout.spinnercustomstyle);
                //  districtsearch_ATV.setAdapter(dataAdapter_district);
                priority_SP.setAdapter(adapter_ticketStatus);
                priority_SP.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                //   }
            }


        }//end of onPostExecute
    }// end Async task

    public void GetFeesCatglist()
    {

        String URL = Class_URL.URL_Manager.toString().trim();
        String METHOD_NAME = "Get_Master_Ticket_Status";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Get_Master_Ticket_Status";



        //for final


        try {
//vijay district
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            //  request.addProperty("distid", Long.parseLong(str_Sids));//Long

            // request.addProperty("distid", 17);//Long

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
                Log.e("response TicketStatus",response.toString());
                ticketStatusCount = response.getPropertyCount();
                SoapObject districtresponse=(SoapObject)response.getProperty(0);
                str_Status=districtresponse.getProperty("Status").toString();
                Log.d("ticketStatusCount", String.valueOf(response.getPropertyCount()));


                if(str_Status.equals("Success")) {
                    if (ticketStatusCount > 0) {
                        arrayObj_Class_TicketStatus = new Class_Project_FundMain[ticketStatusCount];
                        for (int i = 0; i < ticketStatusCount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_Project_FundMain innerObj_Class_Project_FundMain = new Class_Project_FundMain();
                            innerObj_Class_Project_FundMain.setSlno(response_soapobj.getProperty("Slno").toString()); //<Id>1</Id>
                            innerObj_Class_Project_FundMain.setTicket_Status(response_soapobj.getProperty("Ticket_Status").toString()); //<Centre_Code>Hubballi</Centre_Code>
                            innerObj_Class_Project_FundMain.setAccount_Alert_Days(response_soapobj.getProperty("Manager_Alert_Days").toString());// <Centre_Name>HB</Centre_Name>
                            innerObj_Class_Project_FundMain.setManager_Alert_Days(response_soapobj.getProperty("Account_Alert_Days").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_Project_FundMain.setStatus(response_soapobj.getProperty("Status").toString());// <<Stateid>1</Stateid>

                            str_Status = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_TicketStatus[i] = innerObj_Class_Project_FundMain;

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

    public class GetStudentFundReleased extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetStudentFundReleased (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getStudentFundReleased();

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


            if(str_studFundListStatus.equalsIgnoreCase("Success")){
                if(feesList.size()==0||feesList.isEmpty()){
                    Toast.makeText(getActivity(),str_studFundListStatus, Toast.LENGTH_LONG).show();
                    originalList = new ArrayList<Class_FundStudList>();
                    originalList.addAll(feesList);

                    adapter.notifyDataSetChanged();
                }else {
                    originalList = new ArrayList<Class_FundStudList>();
                    originalList.addAll(feesList);

                    adapter.notifyDataSetChanged();
                    //progressBar.setVisibility(View.GONE);


              //      Log.d("Totalcountssss:", String.valueOf(lview.getCount()));
//               txt_actualTotalCounts.setText(String.valueOf(lview.getCount()));
                }
            }else{
                Toast.makeText(getActivity(),str_studFundListStatus, Toast.LENGTH_LONG).show();
                originalList = new ArrayList<Class_FundStudList>();
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

    private SoapObject getStudentFundReleased() {
        String METHOD_NAME = "Get_Release_Fund_List";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Get_Release_Fund_List";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


            FromDate=edt_fromdate.getText().toString();
            ToDate=edt_todate_fund.getText().toString();
            Log.e("tag","FromDate="+FromDate);
            Log.e("tag","Todate="+ToDate);
            String outputPattern = "yyyy-MM-dd";
            String inputPattern = "dd-MM-yyyy";
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

            Date date1 = null,date2=null;
            String strFrom = null,strTo=null;
            try {
                date1 = inputFormat.parse(FromDate);
                strFrom = outputFormat.format(date1);
                date2 = inputFormat.parse(ToDate);
                strTo = outputFormat.format(date2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.e("tag","DFormated FromDate="+strFrom);
            Log.e("tag","DFormated Todate="+strTo);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("Manager_Id",MDId);
            request.addProperty("From_Date",strFrom);
            request.addProperty("To_Date",strTo);
            request.addProperty("Ticket_Status","Pending");
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
                Log.d("soapresponseFund",envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapresponseyyyyyyyFund",response.toString());

                /*if(response.getProperty("Status").toString().equalsIgnoreCase("No Record")){
                str_studFundListStatus="No Record";
            }else {*/
                    SoapObject Fundlistresponse = (SoapObject) response.getProperty("Student_Details");
                    studentlistcount = 0;
                    SoapObject vmGet_Student_List = (SoapObject) Fundlistresponse.getProperty("vmGet_Student_List");
                    studentlistcount = Fundlistresponse.getPropertyCount();

                    Log.d("studentlistcount", String.valueOf(Fundlistresponse.getPropertyCount()));

                    str_studFundListStatus = vmGet_Student_List.getProperty("Status").toString();
                    feesList.clear();

                    if (str_studFundListStatus.equalsIgnoreCase("Success")) {
                        if (studentlistcount > 0) {
                            arrayObj_Class_studList = new Class_FundStudList[studentlistcount];

                            //    ViewSummaryList_arraylist.clear();
                            for (int i = 0; i < studentlistcount; i++) {

                                SoapObject response_soapobj = (SoapObject) Fundlistresponse.getProperty(i); //resp starts from zero

//Log.e("tag","response_soapobj="+response_soapobj.getProperty("Registration_Id"));
                                Class_FundStudList innerObj_Class_manager = new Class_FundStudList();
                                innerObj_Class_manager.setRegistration_Id(response_soapobj.getProperty("Registration_Id").toString()); //<Id>1</Id>
                                innerObj_Class_manager.setStudent_Name(response_soapobj.getProperty("Student_Name").toString()); //<Centre_Code>Hubballi</Centre_Code>
                                innerObj_Class_manager.setCollege_Name(response_soapobj.getProperty("College_Name").toString());// <Centre_Name>HB</Centre_Name>
                                innerObj_Class_manager.setEmail_Id(response_soapobj.getProperty("Email_Id").toString());// <<Stateid>1</Stateid>
                                innerObj_Class_manager.setLead_Id(response_soapobj.getProperty("Lead_Id").toString());// <<Stateid>1</Stateid>
                                innerObj_Class_manager.setSem_Name(response_soapobj.getProperty("Sem_Name").toString());// <<Stateid>1</Stateid>
                                innerObj_Class_manager.setMobile_No(response_soapobj.getProperty("Mobile_No").toString());// <<Stateid>1</Stateid>
                                innerObj_Class_manager.setStatus(response_soapobj.getProperty("Status").toString());// <<Stateid>1</Stateid>


                                SoapObject Fund_Details = (SoapObject) response_soapobj.getProperty("Fund_Details");
                                Log.e("tag", "Fund_Details==" + Fund_Details.getPropertyCount());
                                class_fund_details = new Class_Fund_Details[Fund_Details.getPropertyCount()];

                                for (int j = 0; j < Fund_Details.getPropertyCount(); j++) {
                                    SoapObject response_Fund_Details = (SoapObject) Fund_Details.getProperty(j); //resp starts from zero

                                    Log.e("tag", "response_Fund_Details.getPropertyCount()==" + response_Fund_Details.getPropertyCount());

                                    Log.e("tag", "Fund_Details==" + response_Fund_Details.getProperty("PDID").toString());
//                                str_studFundListStatus = response_Fund_Details.getProperty("Status").toString();
                                    //   classFundDetails=new Class_Fund_Details();

                                /*classFundDetails.setPDID(response_Fund_Details.getProperty("PDID").toString());
                                classFundDetails.setProject_Title(response_Fund_Details.getProperty("Project_Title").toString());
                                classFundDetails.setRequested_Amount(response_Fund_Details.getProperty("Requested_Amount").toString());
                                classFundDetails.setSantioned_Amount(response_Fund_Details.getProperty("Santioned_Amount").toString());
                                classFundDetails.setReleased_Amount(response_Fund_Details.getProperty("Released_Amount").toString());
                                classFundDetails.setTotal_Released_Amount(response_Fund_Details.getProperty("Total_Released_Amount").toString());
                                classFundDetails.setBalance_Amount(response_Fund_Details.getProperty("Balance_Amount").toString());
                                classFundDetails.setStatus(response_Fund_Details.getProperty("Status").toString());*/
                                    String PDID = response_Fund_Details.getProperty("PDID").toString();
                                    String Project_Title = response_Fund_Details.getProperty("Project_Title").toString();
                                    String Requested_Amount = response_Fund_Details.getProperty("Requested_Amount").toString();
                                    String Santioned_Amount = response_Fund_Details.getProperty("Santioned_Amount").toString();
                                    String Released_Amount = response_Fund_Details.getProperty("Released_Amount").toString();
                                    String Total_Released_Amount = response_Fund_Details.getProperty("Total_Released_Amount").toString();
                                    String Balance_Amount = response_Fund_Details.getProperty("Balance_Amount").toString();
                                    String regID = response_soapobj.getProperty("Registration_Id").toString();
                                    String Status = "0";
                                    class_fund_details[j] = new Class_Fund_Details(PDID, Project_Title, Requested_Amount, Santioned_Amount, Released_Amount, Total_Released_Amount, Balance_Amount, Status, regID);
                                }
                                innerObj_Class_manager.setFund_details(class_fund_details);


                                // innerObj_Class_manager.setPayment_Mode("1");

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
              //  }
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

    public class SubmitTicketDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //private ProgressBar progressBar;
        private ProgressDialog progressDialog;

        SubmitTicketDetails (Context ctx){
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

    private SoapPrimitive submitArrLystLeadIds() {
        String METHOD_NAME = "Create_Ticketing_System";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Create_Ticketing_System";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            //   Log.e("tag","isNewRegistration="+isNewRegistration);
            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");

            request.addProperty("Ticket_Priority",ticketPriority);
            request.addProperty("Manager_MailId",str_MangerEmailID);
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
                for(int k=0;k<arrLstRegistration_Id.size();k++) {

                    //  jsonObject=null;
                    jsonObject.put("PDID", Integer.valueOf(arrLstPDID.get(k)));
                    Log.e("tag", "arrLstPDID=" + arrLstPDID.get(k));
                    jsonObject.put("Student_Id", arrLstRegistration_Id.get(k));
                    Log.e("tag", "Student_Id.get(k)=" + arrLstRegistration_Id.get(k));
                   /* jsonObject.put("MobileNo", arrLstMobileNo.get(k));
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
                    Log.e("tag", "arrLstPaymentMode=" + Integer.valueOf(arrLstPaymentMode.get(k)));*/
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

            request.addProperty("Ticket_Details",jsonObjectList.toString());

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

}//end of fragment classxx
