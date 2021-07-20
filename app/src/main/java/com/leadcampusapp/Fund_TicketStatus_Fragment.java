package com.leadcampusapp;

/**
 * Created by Admin on 20-07-2018.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;

//import com.android.sripad.leadnew_22_6_2018.R;

public class Fund_TicketStatus_Fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    int count=0;

    private ArrayList<Class_UnpaidStudList> feesList;
    private View view;
    private boolean isCompletedflag=false;
    private int projectStatusIcon;
   // private FeesUnpaidAdapter adapter;

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

    private ListView lview_fundticketstatus;
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

    AppCompatSpinner spin_ticketStatus;
    Class_Project_FundMain obj_Class_Project_FundMain;
    String str_ticketSlno,str_ticketStatus,str_Status;
    int ticketStatusCount;
    Class_Project_FundMain[] arrayObj_Class_TicketStatus;

    ArrayAdapter adapter_ticketStatus;

    EditText edt_fromdate,edt_todate_ticket,fromdateseterror_TV,todateseterror_TV;
    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;
    Class_FromToDate[] arrayObj_Class_FromToDate;
    String FromDate,ToDate;
    String str_FromToDateStatus;
    int From_ToDateCount;
  //  String yearID,str_yearID;
    ListView lv_summary;
    ImageView FromToDate_btn;

    Class_fundticket_resp[] class_fundticketresp_arrayobj;
    Class_fundticket_resp class_fundticketresp_obj;
    String str_fundticketstatus;

  //  EditText userInput;
    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.activity_project_fundticket_main, container, false);

        /*swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


*/
        txt_actualTotalCounts = (TextView) view.findViewById(R.id.txt_actualTotalCount);

        initializeviews();

          ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Pending");
        arrayList.add("Manager Reject");
        arrayList.add("Accontent Reject");


        FromToDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncCall_GetFundStatus getfundstatus=new AsyncCall_GetFundStatus(getContext());
                getfundstatus.execute();
            }
        });

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

        /*edt_fromdate.setOnClickListener(new View.OnClickListener() {
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
        edt_todate_ticket.setOnClickListener(new View.OnClickListener() {
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
                                    edt_todate_ticket.setText(dateFormat.format(d).toString());

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
        edt_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme,
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
//                originalList.clear();
//                spin_college.setSelection(0);
//                adapter.notifyDataSetChanged();
            }
        });





        edt_todate_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme,
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
                                   // todateseterror_TV.setVisibility(View.GONE);
                                    edt_todate_ticket.setText(dateFormat.format(d).toString());

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
//                originalList.clear();
//                spin_college.setSelection(0);
//                adapter.notifyDataSetChanged();
            }
        });

//        edt_todate_ticket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Calendar c = Calendar.getInstance();
//
//                mYear = c.get(Calendar.YEAR);
//                mMonth = c.get(Calendar.MONTH);
//                mDay = c.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme,
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
//                                // String date =year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
//                                String date =dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
//
//
//                                SimpleDateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
//                                // SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
//
//                                try {
//                                    Date d=dateFormat.parse(date);
//                                    System.out.println("Formated to"+dateFormat.format(d));
//                                    Log.e("tag","todate==="+dateFormat.format(d).toString());
//                                    todateseterror_TV.setVisibility(View.GONE);
//                                    edt_fromdate.setText(dateFormat.format(d).toString());
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
//               // originalList.clear();
//            //    spin_college.setSelection(0);
//           //     adapter.notifyDataSetChanged();
//             //   originalList.clear();
//              //  spin_college.setSelection(0);
//               // adapter.notifyDataSetChanged();
//            }
//        });

        return view;
    }

    public void initializeviews()
    {
        lview_fundticketstatus = (ListView) view.findViewById(R.id.lv_summary);


        feesList = new ArrayList<Class_UnpaidStudList>();
       // adapter = new FeesUnpaidAdapter(getActivity(),feesList);
        //lview_fundticketstatus.setAdapter(adapter);

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

        spin_ticketStatus= (AppCompatSpinner) view.findViewById(R.id.spin_ticketStatus);
        edt_fromdate = (EditText) view.findViewById(R.id.edt_fromdate);
        fromdateseterror_TV = (EditText) view.findViewById(R.id.fromdateseterror_TV);
        edt_todate_ticket = (EditText) view.findViewById(R.id.edt_todate_ticket);
        FromToDate_btn = (ImageView) view.findViewById(R.id.FromToDate_btn);

        //initCollegeSpinner();

       // populateList();

        str_fundticketstatus="false";
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
               // adapter.filter(text, originalList);

            }
        });

       // Log.d("countisssss", String.valueOf(lview_fundticketstatus.getCount()));



        Log.d("ListviewCountissssss", String.valueOf(lview_fundticketstatus.getCount()));
        //txt_actualTotalCount.setText(lview.getCount());


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


            if(str_FromToDateStatus.equals("Success"))
            {
                Log.e("tag","arrayObj_Class_FromToDate="+arrayObj_Class_FromToDate.length);
                for (int j=0;j<arrayObj_Class_FromToDate.length;j++) {
//                    Log.e("tag","arrayObj_Class_FromToDate[j].getFrom_Date()=="+arrayObj_Class_FromToDate[j].getFrom_Date());
                    //                  Log.e("tag","arrayObj_Class_FromToDate[j].getTo_Date()=="+arrayObj_Class_FromToDate[j].getTo_Date());

                    FromDate=arrayObj_Class_FromToDate[j].getFrom_Date();
                    ToDate=arrayObj_Class_FromToDate[j].getTo_Date();
                    edt_fromdate.setText(FromDate);
                    edt_todate_ticket.setText(ToDate);
                    yearID=arrayObj_Class_FromToDate[j].getAcademic_Id();


                }
            }

            if(str_FromToDateStatus.equals("Success"))
            {

                AsyncCall_GetFundStatus getfundstatus=new AsyncCall_GetFundStatus(getContext());
                getfundstatus.execute();
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


                if(str_FromToDateStatus.equals("Success"))
                {
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



    //https://mis.leadcampus.org:8084/leadws/Managerws.asmx/Get_Fund_Status?Manager_Id=1&From_Date=2021-04-01&To_Date=2022-04-31
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
                spin_ticketStatus.setAdapter(adapter_ticketStatus);
                spin_ticketStatus.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
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




    private class AsyncCall_GetFundStatus extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait,Fund status loading...");
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

            getfundstatus();  //
            return null;
        }

        public AsyncCall_GetFundStatus(Context activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result)
        {

            dialog.dismiss();
            if (class_fundticketresp_arrayobj != null)
            {
                CustomAdapter_fundticketstatus adapter = new CustomAdapter_fundticketstatus();
                lview_fundticketstatus.setAdapter(adapter);

                int y = class_fundticketresp_arrayobj.length;

                Log.e("tag","lview_fundticketstatus=="+lview_fundticketstatus.getCount());
             //   txt_actualTotalCounts.setText(lview_fundticketstatus.getCount());
                Log.e("length adapter", "" + y);
            } else {
                Log.d("length adapter", "zero");
            }




        }//end of onPostExecute
    }// end Async task





    public void getfundstatus()
    {
//https://mis.leadcampus.org:8084/leadws/Managerws.asmx/Get_Fund_Status?Manager_Id=1&From_Date=2021-04-01&To_Date=2022-04-31
        String URL = Class_URL.URL_Manager.toString().trim();
        String METHOD_NAME = "Get_Fund_Status";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Get_Fund_Status";






        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);





            request.addProperty("Manager_Id", 1);//<Manager_Id>int</Manager_Id>
            request.addProperty("From_Date", "2021-04-01");//<From_Date>string</From_Date>
            request.addProperty("To_Date", "2022-04-31");//<To_Date>string</To_Date>
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

                /*E/resp_getticket: anyType{vmGet_Funding_Status=anyType{Ticket_Id=1; Requeted_By=Abhinandan;
                Requested_Date=04-05-2021; Approved_By=Pending; Approved_Date= ;
                Approved_Remark= ; Approval_Status=Pending; Requested_Project=2;
                Manager_Approved_Project=0; Account_Approved_Project=0; Requested_Amount=200;
                Manager_Approved_Amount=0; Account_Approved_Amount=0; status=success; };

                vmGet_Funding_Status=anyType{Ticket_Id=2; Requeted_By=Abhinandan;
                Requested_Date=05-05-2021; Approved_By=Pending; Approved_Date= ; Approved_Remark= ;
                Approval_Status=Pending; Requested_Project=3; Manager_Approved_Project=0;
                Account_Approved_Project=0; Requested_Amount=203; Manager_Approved_Amount=0;
                Account_Approved_Amount=0; status=success; };
                vmGet_Funding_Status=anyType{Ticket_Id=3; Requeted_By=Abhinandan; Requested_Date=06-05-2021;
                Approved_By=Pending; Approved_Date= ; Approved_Remark= ; Approval_Status=Pending;
                Requested_Project=7; Manager_Approved_Project=0; Account_Approved_Project=0;
                Requested_Amount=11; Manager_Approved_Amount=0; Account_Approved_Amount=0; status=success; }; }

                2021-05-17 10:09:07.100 18299-18655/com.leadcampusapp E/ticketStatusCount1: 3*/

                SoapObject  response = (SoapObject ) envelope.getResponse();
                Log.e("resp_getfundticket",response.toString());
                int fundticketStatusCount1 = response.getPropertyCount();

                Log.e("fundticketStatusCount1", String.valueOf(fundticketStatusCount1));

                if(fundticketStatusCount1>0) {

                    SoapObject fundticketresponse = (SoapObject) response.getProperty(0);
                    str_fundticketstatus = fundticketresponse.getProperty("status").toString();
                    Log.d("fundticketStatusCount", String.valueOf(response.getPropertyCount()));



                    class_fundticketresp_arrayobj = new Class_fundticket_resp[fundticketStatusCount1];

                    for (int i = 0; i < fundticketStatusCount1; i++) {

                        SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                        Class_fundticket_resp innerObj_class_fundtickekresp = new Class_fundticket_resp();
                        innerObj_class_fundtickekresp.setStr_ticketid(response_soapobj.getProperty("Ticket_Id").toString()); //<Id>1</Id>
                        innerObj_class_fundtickekresp.setStr_requestedby(response_soapobj.getProperty("Requeted_By").toString());//Requeted_By
                        innerObj_class_fundtickekresp.setStr_requesteddate(response_soapobj.getProperty("Requested_Date").toString());//Requested_Date
                        innerObj_class_fundtickekresp.setStr_approvedby(response_soapobj.getProperty("Approved_By").toString());//Approved_By
                        innerObj_class_fundtickekresp.setStr_approveddate(response_soapobj.getProperty("Approved_Date").toString());//Approved_Date
                        innerObj_class_fundtickekresp.setStr_approvedremarks(response_soapobj.getProperty("Approved_Remark").toString());//Approved_Remark
                        innerObj_class_fundtickekresp.setStr_approvalstatus(response_soapobj.getProperty("Approval_Status").toString());//Approval_Status
                        innerObj_class_fundtickekresp.setStr_requestedproject(response_soapobj.getProperty("Requested_Project").toString());//Requested_Project
                        innerObj_class_fundtickekresp.setStr_managerapprovedproject(response_soapobj.getProperty("Manager_Approved_Project").toString());//Manager_Approved_Project
                        innerObj_class_fundtickekresp.setStr_accountapprovedprojects(response_soapobj.getProperty("Account_Approved_Project").toString());//Account_Approved_Project
                        innerObj_class_fundtickekresp.setStr_requestedamount(response_soapobj.getProperty("Requested_Amount").toString());//Requested_Amount
                        innerObj_class_fundtickekresp.setStr_managerapprovedamount(response_soapobj.getProperty("Manager_Approved_Amount").toString());//Manager_Approved_Amount
                        innerObj_class_fundtickekresp.setStr_accountapprovedamount(response_soapobj.getProperty("Account_Approved_Amount").toString());//
                        innerObj_class_fundtickekresp.setStr_status(response_soapobj.getProperty("status").toString());//status


                        str_Status = response_soapobj.getProperty("status").toString();

                        class_fundticketresp_arrayobj[i] = innerObj_class_fundtickekresp;
                        class_fundticketresp_arrayobj[i].getStr_ticketid().toString();


                    }

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


    private class Holder_offline {

        TextView holder_ticketid_tv;
        TextView holder_reqesteddate_tv;
        TextView holder_approveddate_tv;
        TextView holder_approvedby_tv;
        TextView holder_approvedstatus_tv;
        TextView holder_reqestedamount_tv;
        TextView holder_approvedamount_tv;
        TextView holder_openclose_tv;
        ImageView holder_fund_ticketstatusinfo_iv;
        LinearLayout holder_listview_ll;
        LinearLayout holder_listview_noitems_ll;
    }



    public class CustomAdapter_fundticketstatus extends BaseAdapter
    {


        public CustomAdapter_fundticketstatus() {

            super();
            Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(class_fundticketresp_arrayobj.length);
            System.out.println("class_fundticketresp_arrayobj.length" + x);
            return class_fundticketresp_arrayobj.length;
        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            Log.d("getItem position", "x");
            return class_fundticketresp_arrayobj[position];
        }


        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);

            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView1, ViewGroup parent) {

            final Holder_offline holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView1 == null)
            {
                holder = new Holder_offline();
                convertView1 = LayoutInflater.from(getActivity()).inflate(R.layout.listview_rowitem_fundticketstatus, parent, false);


                holder.holder_listview_ll= (LinearLayout) convertView1.findViewById(R.id.listview_ll);
                holder.holder_listview_noitems_ll= (LinearLayout) convertView1.findViewById(R.id.listview_noitems_ll);
                holder.holder_ticketid_tv= (TextView) convertView1.findViewById(R.id.ticketid_tv);
                holder.holder_openclose_tv= (TextView) convertView1.findViewById(R.id.openclose_tv);
                holder.holder_fund_ticketstatusinfo_iv= (ImageView) convertView1.findViewById(R.id.fund_ticketstatusinfo_iv);

                holder.holder_reqesteddate_tv = (TextView) convertView1.findViewById(R.id.reqesteddate_tv);
                holder.holder_approveddate_tv = (TextView) convertView1.findViewById(R.id.approveddate_tv);
                holder.holder_approvedstatus_tv = (TextView) convertView1.findViewById(R.id.approvedby_tv);

                holder.holder_approvedby_tv = (TextView) convertView1.findViewById(R.id.approvedstatus_tv);
                holder.holder_reqestedamount_tv = (TextView) convertView1.findViewById(R.id.reqestedamount_tv);
                holder.holder_approvedamount_tv = (TextView) convertView1.findViewById(R.id.approvedamount_tv);


                Log.d("Inside If convertView1", "Inside If convertView1");

                convertView1.setTag(holder);

            } else {
                holder = (Holder_offline) convertView1.getTag();
                Log.d("else convertView1", "else convertView1");
            }

            // Class_farmponddetails_offline farmponddetails_obj;
            class_fundticketresp_obj = (Class_fundticket_resp) getItem(position);


            if (class_fundticketresp_obj != null)
            {
                holder.holder_ticketid_tv.setText(class_fundticketresp_obj.getStr_ticketid());
                holder.holder_reqesteddate_tv.setText(class_fundticketresp_obj.getStr_requesteddate());
                holder.holder_approveddate_tv.setText(class_fundticketresp_obj.getStr_approveddate());
                holder.holder_approvedby_tv.setText(class_fundticketresp_obj.getStr_approvedby());
                holder.holder_approvedstatus_tv.setText(class_fundticketresp_obj.getStr_approvalstatus());
                holder.holder_reqestedamount_tv.setText(class_fundticketresp_obj.getStr_requestedamount());
                holder.holder_approvedamount_tv.setText(class_fundticketresp_obj.getStr_accountapprovedamount());

            }else{
                holder.holder_listview_ll.setVisibility(View.GONE);
                holder.holder_listview_noitems_ll.setVisibility(View.VISIBLE);
            }

            holder.holder_fund_ticketstatusinfo_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater li = LayoutInflater.from(getContext());
                    View promptsView = li.inflate(R.layout.alert_dialog_fundticketdetails, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getContext());

                    // set alert_dialog.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final TextView ReqBy_tv = (TextView) promptsView.findViewById(R.id.ReqBy_tv);
                    final TextView approvedremark_tv = (TextView) promptsView.findViewById(R.id.approvedremark_tv);
                    final TextView reqProject_tv = (TextView) promptsView.findViewById(R.id.reqProject_tv);
                    final TextView approvedProject_tv = (TextView) promptsView.findViewById(R.id.approvedProject_tv);
                    final TextView accStatus_tv = (TextView) promptsView.findViewById(R.id.accStatus_tv);
                    final TextView accRemark_tv = (TextView) promptsView.findViewById(R.id.accRemark_tv);
                    final TextView accAppProj_tv = (TextView) promptsView.findViewById(R.id.accAppProj_tv);

                    // set dialog message

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    ReqBy_tv.setText(class_fundticketresp_obj.getStr_requestedby());
                    approvedremark_tv.setText(class_fundticketresp_obj.getStr_approvedremarks());
                    reqProject_tv.setText(class_fundticketresp_obj.getStr_requestedproject());
                    approvedProject_tv.setText(class_fundticketresp_obj.getStr_managerapprovedproject());
                    accAppProj_tv.setText(class_fundticketresp_obj.getStr_accountapprovedprojects());
                    accStatus_tv.setText(class_fundticketresp_obj.getStr_approvalstatus());
                    accRemark_tv.setText(class_fundticketresp_obj.getStr_approvedremarks());
                    // show it
                    alertDialog.show();
                }
            });

            return convertView1;

        }//End of custom getView
    }//End of CustomAdapter




}//end of fragment classxx
