package com.leadcampusapp; /**
 * Created by Admin on 20-07-2018.
 */

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class FeesPaidFragment extends Fragment
{
    private ListView lview;
    private ArrayList<Class_PaidStudList> feesList;
    private FeesPaidAcknowledgedAdapter adapter;
    private SharedPreferences shardprefPM_obj;
    private String str_MangerID;
    private static final String  PREFBook_PM= "prefbook_pm";
    private static final String PrefID_PMID = "prefid_pmid";
    private LinkedHashSet<String> collegeNameLst;
    private ArrayList<String> collegeNameArrLst;
    private AppCompatSpinner spin_college;
    private ArrayList<Class_PaidStudList> originalList = null;
    private ProgressDialog progressDialog;
    private EditText etSearch;
    private TextView txt_studRegistered,txt_feesPaidStudents;
    private int counter=0;

    Class_Colleges[] arrayObj_Class_Colleges, arrayObj_Class_Colleges2,arrayObj_Class_Colleges3;
    Class_Colleges obj_Class_Colleges;
    int collegeslistcount,MDId,studentlistcount;
    String str_CollegesStatus,str_ColID="x";
    String str_studListStatus;
    ArrayAdapter dataAdapter_yearList,dataAdapter_feesCatg,dataAdapter_college;
    Class_FeesCatg[] arrayObj_Class_FeesCatg;
    Class_FeesCatg obj_Class_FeesCatg;
    int YearListCount,feesCatglistcount,From_ToDateCount;
    String str_YearListStatus,yearID,str_FeesCatgStatus,str_feesCatSlno,str_feesCatName,str_yearID,str_yearCode;
    AppCompatSpinner spin_year,spin_feesCategory;
    private TextView txt_actualTotalCounts;


    EditText edt_fromdate,edt_todate,fromdateseterror_TV,todateseterror_TV;
    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;
    Class_FromToDate[] arrayObj_Class_FromToDate;
    String FromDate,ToDate;
    String str_FromToDateStatus;
    Class_PaidStudList[] arrayObj_Class_studList;
    Class_PaidStudList obj_Class_studList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.frag_fees_paid_acknowledged, container, false);

        lview = (NonScrollListView) view.findViewById(R.id.listview_fees_paid_acknowledged);

        feesList = new ArrayList<Class_PaidStudList>();
        adapter = new FeesPaidAcknowledgedAdapter(getActivity(),feesList);
        lview.setAdapter(adapter);



        shardprefPM_obj = getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MangerID:",str_MangerID);
        MDId=Integer.parseInt(str_MangerID);

        collegeNameLst = new LinkedHashSet<String>();
        collegeNameLst.add("Select Institution");
        collegeNameArrLst = new ArrayList<String>();

        spin_college = (AppCompatSpinner) view.findViewById(R.id.spin_college);

        txt_studRegistered = (TextView) view.findViewById(R.id.txt_studRegistered);
        txt_feesPaidStudents = (TextView) view.findViewById(R.id.txt_feesPaidStudents);
        edt_fromdate = (EditText) view.findViewById(R.id.edt_fromdate);
        fromdateseterror_TV = (EditText) view.findViewById(R.id.fromdateseterror_TV);
        edt_todate = (EditText) view.findViewById(R.id.edt_todate);
        todateseterror_TV = (EditText) view.findViewById(R.id.todateseterror_TV);
        spin_feesCategory = (AppCompatSpinner) view.findViewById(R.id.spin_feesCategory);
        txt_actualTotalCounts = (TextView) view.findViewById(R.id.txt_actualTotalCount);

        //populateGetFeesRegistered();


        GetFromToDateAsyncCallWS getFromToDateAsyncCallWS=new GetFromToDateAsyncCallWS(getActivity());
        getFromToDateAsyncCallWS.execute();
        FeesCatglistAsyncCallWS feesCatglistAsyncCallWS=new FeesCatglistAsyncCallWS(getActivity());
        feesCatglistAsyncCallWS.execute();
        //populateList();

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
                originalList.clear();
                spin_college.setSelection(0);
                adapter.notifyDataSetChanged();
            }
        });
        edt_todate.setOnClickListener(new View.OnClickListener() {
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
                originalList.clear();
                spin_college.setSelection(0);
                adapter.notifyDataSetChanged();
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

                Log.e("tag","str_feesCatName="+str_feesCatName);

                CollegelistAsyncCallWS collegelistAsyncCallWS=new CollegelistAsyncCallWS(getContext());
                collegelistAsyncCallWS.execute();
                // Toast.makeText(getApplicationContext(),"str_Programsid: "+str_programid,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });


        spin_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
   /*             String collegeName = spin_college.getSelectedItem().toString();
                adapter.filter(collegeName,originalList);*/

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

                    GetFeespaid getFeesUnpaid = new GetFeespaid(getActivity());
                    getFeesUnpaid.execute();
                }

                if(!collegeName.equals("Select Institution")) {
                    adapter.filter(collegeName, originalList);
                }else{
                    counter++;
                    collegeName = "";
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



        // showActivity();
        return view;
    }

    private void populateGetFeesRegistered() {
        GetFeesRegistered getFeesRegistered = new GetFeesRegistered(getActivity());
        getFeesRegistered.execute();
    }

 /*   private void populateList() {

        GetFeesPaid getFeesPaid = new GetFeesPaid(getActivity());
        getFeesPaid.execute();

    }*/

    /*public class GetFeesPaid extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetFeesPaid (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getFeesPaid();

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

            if(result != null){
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
                    if (O_CollegeName.toString().equals("anyType{}") || O_CollegeName.toString().equals(null)) {
                        str_collegeName = "";
                        //collegeNameLst.add(str_collegeName);
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

                    FeesUnpaidModel item = null;
                    if (!str_collegeName.isEmpty()) {
                        item = new FeesUnpaidModel(str_studentName, str_leadid, str_registrationDate, str_collegeName, str_MobileNo, str_IsFeesPaid);
                        feesList.add(item);
                    }


                }

            }

            originalList = new ArrayList<FeesUnpaidModel>();
            originalList.addAll(feesList);

            adapter.notifyDataSetChanged();
            //initCollegeSpinner();
        }
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getFeesPaid() {
        String METHOD_NAME = "GetStudentFeepaid";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetStudentFeepaid";
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
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
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
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }*/


    private void initCollegeSpinner() {
        collegeNameArrLst.addAll(collegeNameLst);
        ArrayAdapter dataAdapter2 = new ArrayAdapter(getActivity(), R.layout.simple_spinner_items, collegeNameArrLst);
        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);
        // attaching data adapter to spinner
        spin_college.setAdapter(dataAdapter2);
        //spin_college.setSupportBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.colorWhite));

    }

    public class GetFeesRegistered extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialgs;

        //private ProgressBar progressBar;

        GetFeesRegistered (Context ctx){
            context = ctx;
            progressDialgs = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getFeesRegistered();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
      /*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
            progressDialgs.setMessage("Loading");
            progressDialgs.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialgs.setCanceledOnTouchOutside(false);
            progressDialgs.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if(result!=null){

            SoapPrimitive S_TotalRegistrn, S_FeePaiedStudent, S_Status;
            Object O_TotalRegistrn, O_FeePaiedStudent, O_Status;
            String str_TotalRegistrn = null, str_FeePaiedStudent = null, str_Status = null;

            Log.d("Resultssssiss", result.toString());


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
                    O_TotalRegistrn = list.getProperty("TotalRegistration");
                    if (!O_TotalRegistrn.toString().equals("anyType{}") && !O_TotalRegistrn.toString().equals(null)) {
                        S_TotalRegistrn = (SoapPrimitive) list.getProperty("TotalRegistration");
                        Log.d("TotalRegistration:", S_TotalRegistrn.toString());
                        str_TotalRegistrn = S_TotalRegistrn.toString();
                        txt_studRegistered.setText(str_TotalRegistrn);
                    }

                    O_FeePaiedStudent = list.getProperty("FeePaiedStudent");
                    if (!O_FeePaiedStudent.toString().equals("anyType{}") && !O_FeePaiedStudent.toString().equals(null)) {
                        S_FeePaiedStudent = (SoapPrimitive) list.getProperty("FeePaiedStudent");
                        Log.d("SFeePaiedStudent:", S_FeePaiedStudent.toString());
                        str_FeePaiedStudent = S_FeePaiedStudent.toString();
                        txt_feesPaidStudents.setText(str_FeePaiedStudent);
                    }


                }
            }
        }

            progressDialgs.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getFeesRegistered() {
        String METHOD_NAME = "GetStudntRegAndPaiedStudentconts";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetStudntRegAndPaiedStudentconts";
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
                //Log.d("soapresponse1xxxxFees",envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                //Log.e("soapresponseyyyyyyyFees",response.toString());

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
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
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
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

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
                    edt_todate.setText(ToDate);
                    yearID=arrayObj_Class_FromToDate[j].getAcademic_Id();


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

    public class GetFeespaid extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetFeespaid (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getFeespaid();

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
                originalList = new ArrayList<Class_PaidStudList>();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);

                Log.d("Totalcountssss:", String.valueOf(lview.getCount()));
                txt_actualTotalCounts.setText(String.valueOf(lview.getCount()));

            }else{
                Toast.makeText(getActivity(),str_studListStatus, Toast.LENGTH_LONG).show();

            }

            progressDialog.dismiss();



        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getFeespaid() {
        String METHOD_NAME = "Get_Manager_Payment_History";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Get_Manager_Payment_History";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            FromDate=edt_fromdate.getText().toString();
            ToDate=edt_todate.getText().toString();
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
            request.addProperty("Fees_Category_Id",str_feesCatSlno);
            request.addProperty("From_Date",strFrom);
            request.addProperty("To_Date",strTo);
            request.addProperty("College_Id",str_ColID);
            //users.ad
            //request.add

            Log.e("tag","request studpaidlist="+request.toString());
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
                Log.d("tag","soapresponse studPaid"+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse studPaid"+response.toString());

                studentlistcount = response.getPropertyCount();
                SoapObject summaryresponse=(SoapObject)response.getProperty(0);
                str_studListStatus=summaryresponse.getProperty("Status").toString();
                Log.d("summarylistcount", String.valueOf(response.getPropertyCount()));

                if(str_studListStatus.equalsIgnoreCase("Success")) {
                    if (studentlistcount > 0) {
                        arrayObj_Class_studList = new Class_PaidStudList[studentlistcount];
                        //    ViewSummaryList_arraylist.clear();
                        for (int i = 0; i < studentlistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                            Class_PaidStudList innerObj_Class_manager = new Class_PaidStudList();
                            innerObj_Class_manager.setPayment_Id(response_soapobj.getProperty("Payment_Id").toString()); //<Id>1</Id>
                            innerObj_Class_manager.setLead_Id(response_soapobj.getProperty("Lead_Id").toString()); //<Centre_Code>Hubballi</Centre_Code>
                            innerObj_Class_manager.setStudentName(response_soapobj.getProperty("StudentName").toString());// <Centre_Name>HB</Centre_Name>
                            innerObj_Class_manager.setRegistration_Id(response_soapobj.getProperty("Registration_Id").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setPaid_Fees(response_soapobj.getProperty("Paid_Fees").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setPaid_date(response_soapobj.getProperty("paid_date").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setCreated_Date(response_soapobj.getProperty("Created_Date").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setAuto_Receipt_No(response_soapobj.getProperty("Auto_Receipt_No").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setTransanction_Id(response_soapobj.getProperty("transanction_Id").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setReference_id(response_soapobj.getProperty("reference_id").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setFees_Category_description(response_soapobj.getProperty("Fees_Category_description").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setTransactionStatus(response_soapobj.getProperty("transactionStatus").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setYearCode(response_soapobj.getProperty("YearCode").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setPayment_Type(response_soapobj.getProperty("Payment_Type").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setPayeer_Id(response_soapobj.getProperty("Payeer_Id").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setCreated_User_Type(response_soapobj.getProperty("Created_User_Type").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setPayment_Mode(response_soapobj.getProperty("Payment_Mode").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setPayment_Remark(response_soapobj.getProperty("Payment_Remark").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setManager_Submission_Status(response_soapobj.getProperty("Manager_Submission_Status").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setPayment_Receipt_Path(response_soapobj.getProperty("Payment_Receipt_Path").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRec_Date(response_soapobj.getProperty("Rec_Date").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRec_Remark(response_soapobj.getProperty("Rec_Remark").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRec_By(response_soapobj.getProperty("Rec_By").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRec_Status(response_soapobj.getProperty("Rec_Status").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setStatus(response_soapobj.getProperty("Status").toString());// <<Stateid>1</Stateid>

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

}//end of fragment class
