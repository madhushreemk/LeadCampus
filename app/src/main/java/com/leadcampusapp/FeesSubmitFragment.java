package com.leadcampusapp; /**
 * Created by Admin on 20-07-2018.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Locale;

import static androidx.recyclerview.widget.RecyclerView.*;

//import com.android.sripad.leadnew_22_6_2018.R;

public class FeesSubmitFragment extends Fragment
{
    private ListView lview;
    private ArrayList<FeesUnpaidModel> feesList;
    private FeesPaidAcknowledgedAdapter adapter;
    private SharedPreferences shardprefPM_obj;
    private String str_MangerID;
    private static final String  PREFBook_PM= "prefbook_pm";
    private static final String PrefID_PMID = "prefid_pmid";
    private LinkedHashSet<String> collegeNameLst;
    private ArrayList<String> collegeNameArrLst;
    private AppCompatSpinner spin_college;
    private ArrayList<FeesUnpaidModel> originalList = null;
    private ProgressDialog progressDialog;
    private EditText etSearch;
    private TextView txt_studRegistered,txt_feesPaidStudents;
    private int counter=0;
    Integer MDId;
    int summarylistcount,From_ToDateCount,summaryDetailslistcount,summaryDetailsCount;
    String str_SummaryStatus,str_FromToDateStatus,str_SummaryDetailsStatus="null",getStr_SummaryDetailsStatus;
    Class_ManagerSummary[] arrayObj_Class_Summary, arrayObj_Class_Summary2;
    Class_ManagerSummary obj_Class_Summary;
    Class_ManagerSummaryDetails[] arrayObj_Class_SummaryDetails;
    Class_FromToDate[] arrayObj_Class_FromToDate;
    String FromDate,ToDate;
    Class_FromToDate obj_Class_FromToDate;

    ImageView FromToDate_btn;
    ListView lv_summary,lv_paidlist;
    EditText edt_fromdate,edt_todate,fromdateseterror_TV,todateseterror_TV;
    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;
    SummaryListViewAdapter summaryListViewAdapter;
    SummaryDetailsViewAdapter summaryDetailsViewAdapter;
    private ArrayList<Class_ManagerSummary> ViewSummaryList_arraylist;
    private ArrayList<Class_ManagerSummaryDetails> ViewSummaryDetailsList_arraylist;

    EditText stfonlinetrans_no_tv,stfonlinetransAmount_tv,stfamountbycash_tv,stfonline_receiptno_tv,stfamountbycash_receiptno_tv;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    Boolean bo_onlinechecked=false,bo_foronline=false,bo_cashchecked=true;
    Boolean bo_dialogdismiss=false;
    Button submitfeesstf_bt;
    String forOnline="No";

    String str_categoryID,str_Amount,str_remarks,str_paymentMode="1",str_categoryName;
    String str_response_onlinefeesNEFT="fail";
    String str_response_cash="fail";

    Class_FeesCatg[] arrayObj_Class_FeesCatg;
    Class_FeesCatg obj_Class_FeesCatg;
    int feesCatglistcount;
    String str_FeesCatgStatus="x";
    ArrayAdapter dataAdapter_feesCatg;
    AppCompatSpinner spin_feesCategory;
    SharedPreferences shardpref_S_obj;
    SharedPreferences.Editor editor_S;

    public static final String  PREFBook_Stud= "prefbook_stud";
    public static final String PrefId_S_AcademicId = "prefid_sacademicid";
    String str_academicId,str_feesCatSlno,str_feesCatName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.frag_fees_submit, container, false);



        shardprefPM_obj = getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MangerID:",str_MangerID);
        MDId=Integer.parseInt(str_MangerID);

        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);

        str_academicId = shardpref_S_obj.getString(PrefId_S_AcademicId, "").trim();
        Log.d("str_academicId:",str_academicId);

        ViewSummaryList_arraylist = new ArrayList<Class_ManagerSummary>();
        ViewSummaryDetailsList_arraylist = new ArrayList<Class_ManagerSummaryDetails>();
        edt_fromdate = (EditText) view.findViewById(R.id.edt_fromdate);
        fromdateseterror_TV = (EditText) view.findViewById(R.id.fromdateseterror_TV);
        edt_todate = (EditText) view.findViewById(R.id.edt_todate);
        todateseterror_TV = (EditText) view.findViewById(R.id.todateseterror_TV);
        FromToDate_btn = (ImageView) view.findViewById(R.id.FromToDate_btn);
        lv_summary = (ListView) view.findViewById(R.id.lv_summary);
        lv_paidlist = (ListView) view.findViewById(R.id.lv_paidlist);
        spin_feesCategory = (AppCompatSpinner) view.findViewById(R.id.spin_feesCategory);

        summaryListViewAdapter = new SummaryListViewAdapter(getActivity(), ViewSummaryList_arraylist);
        summaryDetailsViewAdapter = new SummaryDetailsViewAdapter(getActivity(), ViewSummaryDetailsList_arraylist);

        GetFromToDateAsyncCallWS getFromToDateAsyncCallWS=new GetFromToDateAsyncCallWS(getActivity());
        getFromToDateAsyncCallWS.execute();

        FeesCatglistAsyncCallWS feesCatglistAsyncCallWS=new FeesCatglistAsyncCallWS(getActivity());
        feesCatglistAsyncCallWS.execute();

        FromToDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSummarylistAsyncCallWS getSummarylistAsyncCallWS=new GetSummarylistAsyncCallWS(getActivity());
                getSummarylistAsyncCallWS.execute();

                GetSummaryDetailslistAsyncCallWS getSummaryDetailslistAsyncCallWS=new GetSummaryDetailslistAsyncCallWS(getActivity());
                getSummaryDetailslistAsyncCallWS.execute();
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

                // Toast.makeText(getApplicationContext(),"str_Programsid: "+str_programid,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
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
                summaryDetailsViewAdapter.notifyDataSetChanged();
                lv_paidlist.setAdapter(summaryDetailsViewAdapter);
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
                summaryDetailsViewAdapter.notifyDataSetChanged();
                lv_paidlist.setAdapter(summaryDetailsViewAdapter);
            }

        });

        // showActivity();
        return view;
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

                    GetSummarylistAsyncCallWS getSummarylistAsyncCallWS=new GetSummarylistAsyncCallWS(getActivity());
                    getSummarylistAsyncCallWS.execute();
                    GetSummaryDetailslistAsyncCallWS getSummaryDetailslistAsyncCallWS=new GetSummaryDetailslistAsyncCallWS(getActivity());
                    getSummaryDetailslistAsyncCallWS.execute();
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

    private class GetSummarylistAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait Loading...");
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

            GetSummarylist();  // get the District list
            return null;
        }

        public GetSummarylistAsyncCallWS(Context activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result) {

            Log.e("tag","arrayObj_Class_Summary[j].length=="+arrayObj_Class_Summary.length);

            dialog.dismiss();
            if(str_SummaryStatus.equals("Success")) {
                for (int j=0;j<arrayObj_Class_Summary.length;j++) {
                    Log.e("tag","arrayObj_Class_Summary[j].getBalance()=="+arrayObj_Class_Summary[j].getBalance());
                    Log.e("tag","arrayObj_Class_Summary[j].getSubmitted()=="+arrayObj_Class_Summary[j].getSubmitted());

                   /* category_tv.setText(arrayObj_Class_Summary[j].getFees_Category_Name());
                    submit_tv.setText(arrayObj_Class_Summary[j].getSubmitted());
                    balance_tv.setText(arrayObj_Class_Summary[j].getBalance());
                    recevied_tv.setText(arrayObj_Class_Summary[j].getCollected());*/
                    if (ViewSummaryList_arraylist != null) {
                        summaryListViewAdapter.notifyDataSetChanged();
                        lv_summary.setAdapter(summaryListViewAdapter);

                    }
                }
            }


        }//end of onPostExecute
    }// end Async task

    public void GetSummarylist()
    {

        String URL = Class_URL.URL_Manager.toString().trim();
        String METHOD_NAME = "Get_Manager_Summary";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Get_Manager_Summary";

        try {
//vijay district
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

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
            Log.e("tag","Formated FromDate="+strFrom);
            Log.e("tag","Formated Todate="+strTo);
            request.addProperty("Manager_Id", MDId);//Long
            request.addProperty("From_Date",strFrom);
            request.addProperty("To_Date", strTo);//Long
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
                Log.e("response summarylist",response.toString());
                summarylistcount = response.getPropertyCount();
                SoapObject summaryresponse=(SoapObject)response.getProperty(0);
                str_SummaryStatus=summaryresponse.getProperty("Status").toString();
                Log.d("summarylistcount", String.valueOf(response.getPropertyCount()));


                if(str_SummaryStatus.equals("Success")) {
                    if (summarylistcount > 0) {
                        arrayObj_Class_Summary = new Class_ManagerSummary[summarylistcount];
                        ViewSummaryList_arraylist.clear();
                        for (int i = 0; i < summarylistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_ManagerSummary innerObj_Class_manager = new Class_ManagerSummary();
                            innerObj_Class_manager.setFees_Category_Id(response_soapobj.getProperty("Fees_Category_Id").toString()); //<Id>1</Id>
                            innerObj_Class_manager.setFees_Category_Name(response_soapobj.getProperty("Fees_Category_Name").toString()); //<Centre_Code>Hubballi</Centre_Code>
                            innerObj_Class_manager.setSubmitted(response_soapobj.getProperty("Submitted").toString());// <Centre_Name>HB</Centre_Name>
                            innerObj_Class_manager.setCollected(response_soapobj.getProperty("Collected").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setStatus(response_soapobj.getProperty("Status").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setBalance(response_soapobj.getProperty("Balance").toString());// <<Stateid>1</Stateid>

                            str_SummaryStatus = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_Summary[i] = innerObj_Class_manager;


                            ViewSummaryList_arraylist.add(innerObj_Class_manager);
                         /*   String str_districtID = response_soapobj.getProperty("DistrictId").toString();
                            String str_districtname = response_soapobj.getProperty("DistrictName").toString();
                            String str_districtstateid = response_soapobj.getProperty("Stateid").toString();
*/
                            //DBCreate_Districtdetails_insert_2SQLiteDB(str_districtID,str_districtname,str_districtstateid);


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

    private class GetSummaryDetailslistAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait Loading...");
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

            GetSummaryDetailslist();  // get the District list
            return null;
        }

        public GetSummaryDetailslistAsyncCallWS(Context activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result) {

            Log.e("tag","arrayObj_Class_Summary[j].length=="+arrayObj_Class_Summary.length);

            dialog.dismiss();
            if(str_SummaryDetailsStatus.equals("Success")) {
                for (int j=0;j<arrayObj_Class_SummaryDetails.length;j++) {
                    Log.e("tag","arrayObj_Class_Summary[j].getCat()=="+arrayObj_Class_SummaryDetails[j].getFees_Category_description());
                    Log.e("tag","arrayObj_Class_Summary[j].getSubmitted()=="+arrayObj_Class_SummaryDetails[j].getSubmitted_By());

                   /* category_tv.setText(arrayObj_Class_Summary[j].getFees_Category_Name());
                    submit_tv.setText(arrayObj_Class_Summary[j].getSubmitted());
                    balance_tv.setText(arrayObj_Class_Summary[j].getBalance());
                    recevied_tv.setText(arrayObj_Class_Summary[j].getCollected());*/
                    if (ViewSummaryDetailsList_arraylist != null) {
                        summaryDetailsViewAdapter.notifyDataSetChanged();
                        lv_paidlist.setAdapter(summaryDetailsViewAdapter);
                    }
                }
            }


        }//end of onPostExecute
    }// end Async task

    public void GetSummaryDetailslist()
    {

        String URL = Class_URL.URL_Manager.toString().trim();
        String METHOD_NAME = "Get_Manager_Submission_Details";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Get_Manager_Submission_Details";

        try {
//vijay district
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

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
            request.addProperty("Manager_Id", MDId);//Long
            request.addProperty("p_Fees_Category_Id",Integer.valueOf(str_feesCatSlno));
            request.addProperty("From_Date",strFrom);
            request.addProperty("To_Date", strTo);//Long
            // <stateId>long</stateId>
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            Log.e("tag","request summDetails="+request);
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
                Log.e("response summaryDetails",response.toString());
                summaryDetailslistcount = response.getPropertyCount();
                SoapObject summaryresponse=(SoapObject)response.getProperty(0);
                str_SummaryDetailsStatus=summaryresponse.getProperty("Status").toString();
                Log.d("summarylistcount", String.valueOf(response.getPropertyCount()));


                if(str_SummaryDetailsStatus.equals("Success")) {
                    if (summaryDetailslistcount > 0) {
                        arrayObj_Class_SummaryDetails = new Class_ManagerSummaryDetails[summaryDetailslistcount];
                        ViewSummaryDetailsList_arraylist.clear();
                        for (int i = 0; i < summaryDetailslistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                            Class_ManagerSummaryDetails innerObj_Class_manager = new Class_ManagerSummaryDetails();
                            innerObj_Class_manager.setSubmission_slno(response_soapobj.getProperty("Submission_slno").toString()); //<Id>1</Id>
                            innerObj_Class_manager.setFees_Category_Id(response_soapobj.getProperty("Fees_Category_Id").toString()); //<Centre_Code>Hubballi</Centre_Code>
                            innerObj_Class_manager.setFees_Category_description(response_soapobj.getProperty("Fees_Category_description").toString());// <Centre_Name>HB</Centre_Name>
                            innerObj_Class_manager.setSubmission_Amount(response_soapobj.getProperty("Submission_Amount").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setSubmitted_Date(response_soapobj.getProperty("Submitted_Date").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setSubmitted_Mode(response_soapobj.getProperty("Submitted_Mode").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setSubmitted_Remark(response_soapobj.getProperty("Submitted_Remark").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setSubmitted_By(response_soapobj.getProperty("Submitted_By").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRec_Status(response_soapobj.getProperty("Rec_Status").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setSubmitter_Name(response_soapobj.getProperty("Submitter_Name").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRec_Date(response_soapobj.getProperty("Rec_Date").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRec_By(response_soapobj.getProperty("Rec_By").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRec_Mail_id(response_soapobj.getProperty("Rec_Mail_id").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setRec_Remark(response_soapobj.getProperty("Rec_Remark").toString());// <<Stateid>1</Stateid>
                            innerObj_Class_manager.setStatus(response_soapobj.getProperty("Status").toString());// <<Stateid>1</Stateid>

                            str_SummaryDetailsStatus = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_SummaryDetails[i] = innerObj_Class_manager;


                            ViewSummaryDetailsList_arraylist.add(innerObj_Class_manager);
                         /*   String str_districtID = response_soapobj.getProperty("DistrictId").toString();
                            String str_districtname = response_soapobj.getProperty("DistrictName").toString();
                            String str_districtstateid = response_soapobj.getProperty("Stateid").toString();
*/
                            //DBCreate_Districtdetails_insert_2SQLiteDB(str_districtID,str_districtname,str_districtstateid);


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

    public class SummaryListViewAdapter extends BaseAdapter {


        public ArrayList<Class_ManagerSummary> projList;
        Activity activity;
        private ArrayList<Class_ManagerSummary> mDisplayedValues = null;

        public SummaryListViewAdapter(Activity activity, ArrayList<Class_ManagerSummary> projList) {
            super();
            this.activity = activity;
            this.projList = projList;
            this.mDisplayedValues = projList;
        }

        @Override
        public int getCount() {
            //return projList.size();
            Log.e("size", String.valueOf(mDisplayedValues.size()));
            return mDisplayedValues.size();

        }

        @Override
        public Class_ManagerSummary getItem(int position) {

            //return projList.get(position);
            return mDisplayedValues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;
            LayoutInflater inflater = activity.getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.child_summarylist_item, null);
                holder = new ViewHolder();

                holder.category_tv = (TextView) convertView.findViewById(R.id.category_tv);
             //   holder.total_tv = (TextView) convertView.findViewById(R.id.total_tv);
                holder.recevied_tv = (TextView) convertView.findViewById(R.id.recevied_tv);
                holder.submit_tv = (TextView) convertView.findViewById(R.id.submit_tv);
                holder.balance_tv = (TextView) convertView.findViewById(R.id.balance_tv);
                holder.pay_iv = (ImageView) convertView.findViewById(R.id.pay_iv);
                holder.categoryID_tv = (TextView) convertView.findViewById(R.id.categoryID_tv);

                // holder.status_tv=(TextView)convertView.findViewById(R.id.status_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Class_ManagerSummary Obj_Class_farmerlistdetails = (Class_ManagerSummary) getItem(position);


            if (Obj_Class_farmerlistdetails != null) {
                {
                  //  String FullName = Obj_Class_farmerlistdetails.getStudentName();
                    holder.category_tv.setText(Obj_Class_farmerlistdetails.getFees_Category_Name());
                    holder.categoryID_tv.setText(Obj_Class_farmerlistdetails.getFees_Category_Id());
                    holder.recevied_tv.setText(Obj_Class_farmerlistdetails.getCollected());
                    holder.submit_tv.setText(Obj_Class_farmerlistdetails.getSubmitted());
                    holder.balance_tv.setText(Obj_Class_farmerlistdetails.getBalance());
                   // holder.total_tv.setText(Obj_Class_farmerlistdetails.getCollected());

                }

            }

            if(holder.balance_tv.getText().toString().equalsIgnoreCase("0")){
                holder.pay_iv.setVisibility(GONE);
            }else{
                holder.pay_iv.setVisibility(VISIBLE);
            }
            holder.pay_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(getActivity());

                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_tuitionfeessubmission);
                    dialog.setCancelable(false);
                    TextView header_tv = (TextView) dialog.findViewById(R.id.header_tv);
                    Button dialogcancelbutton = (Button) dialog.findViewById(R.id.stfeescanceldialog_BT);
                    Button dialogsubmitbutton = (Button) dialog.findViewById(R.id.stfeessubmitdialog_BT);
                    stfonlinetrans_no_tv = (EditText) dialog.findViewById(R.id.Stfonlinetrans_no_TV);
                    stfonlinetransAmount_tv=(EditText)dialog.findViewById(R.id.StfonlinetransAmount_TV);
                    stfamountbycash_tv=(EditText)dialog.findViewById(R.id.StfAmountbycash_TV);
                    stfonline_receiptno_tv=(EditText)dialog.findViewById(R.id.Stfonline_receiptno_TV);
                    stfamountbycash_receiptno_tv=(EditText)dialog.findViewById(R.id.StfAmountbycash_receiptno_TV);

                    stfonlinetransAmount_tv.setText(holder.balance_tv.getText().toString());
                    header_tv.setText("Submit to Account");


                    dialogcancelbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.dismiss();
                            bo_cashchecked=true;
                            bo_onlinechecked=false;
                        }
                    });

                    dialogsubmitbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            internetDectector = new Class_InternetDectector(getActivity());
                            isInternetPresent = internetDectector.isConnectingToInternet();

                            if (bo_onlinechecked ||bo_cashchecked) {
                                if (isInternetPresent)
                                {


                                    Log.e("tag","str_categoryName="+str_categoryName+"str_Amount="+str_Amount);

                                    //   Toast.makeText(getApplicationContext(),"Outside value:"+forOnline,Toast.LENGTH_LONG).show();
                                    if(validationfor_dialog())
                                    {

                                        if(forOnline.equals("yes"))
                                        {
                                         //   Toast.makeText(getActivity(), "Online", Toast.LENGTH_SHORT).show();
                                            str_paymentMode="2";
                                            bo_dialogdismiss = true;
                                        }
                                        else
                                        {
                                          //  Toast.makeText(getActivity(), "Cash", Toast.LENGTH_SHORT).show();
                                            str_paymentMode="1";
                                            bo_dialogdismiss = true;
                                        }
                                        String balanceAmt=holder.balance_tv.getText().toString();
                                        int balanceAmtInt=Integer.valueOf(balanceAmt);

                                        stfonlinetransAmount_tv.setFilters(new InputFilter[]{new InputFilterMinMax("1", balanceAmt)});
                                        str_categoryID= holder.categoryID_tv.getText().toString();
                                        str_Amount=stfonlinetransAmount_tv.getText().toString();
                                        str_categoryName=holder.category_tv.getText().toString();
                                        str_remarks=stfonline_receiptno_tv.getText().toString();
                                        int enterdAmtInt=Integer.valueOf(str_Amount);
                                        if(balanceAmtInt<enterdAmtInt){
                                            Toast.makeText(getActivity(), "Entered Amount is Grater then balance amount", Toast.LENGTH_SHORT).show();
                                        }else{
                                          //  Toast.makeText(getActivity(), "Entered Amount is Less then balance amount", Toast.LENGTH_SHORT).show();
                                           /* if(forOnline.equals("yes")) {
                                                if(validate_remark()) {
                                                    SubmitAmountDetails submitAmountDetails = new SubmitAmountDetails(getActivity());
                                                    submitAmountDetails.execute();
                                                }
                                            }else{*/
                                                SubmitAmountDetails submitAmountDetails = new SubmitAmountDetails(getActivity());
                                                submitAmountDetails.execute();
                                           // }
                                        }
                                       // SubmitAmountDetails submitAmountDetails=new SubmitAmountDetails(getActivity());
                                        //submitAmountDetails.execute();
                                    }
                                } else
                                {
                                    Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_SHORT).show();
                                    bo_dialogdismiss=false;

                                }
                            }//end of bo_onlinechecked
                            else {
                                Toast.makeText(getActivity(), "Select Option", Toast.LENGTH_SHORT).show();
                            }

                            if(bo_dialogdismiss){
                                dialog.dismiss();
                                bo_cashchecked=true;
                                bo_onlinechecked=false;
                                bo_dialogdismiss=false;
                            }


                        }
                    });

                    RadioGroup submitfees_radiogroup;
                    submitfees_radiogroup = (RadioGroup) dialog.findViewById(R.id.submit_stfeesradiogroup);

                    dialog.show();


                    submitfees_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            // TODO Auto-generated method stub

                            if (checkedId == R.id.Stfonlinetrans_RB) {
                                bo_onlinechecked=true;
                                bo_cashchecked=false;
                                bo_foronline=true;
                                forOnline="yes";
                            }

                            if (checkedId == R.id.Stfbycash_RB) {
                                bo_cashchecked=true;
                                bo_onlinechecked=false;
                                bo_foronline=false;
                                forOnline="No";
                            }

                        }
                    }); // end of radioGroup.setOnCheckedChangeListener


                }
            });

            return convertView;
        }

    }
    public boolean validate_remark(){
        boolean stfonlinereceipt_no_tv_VR5=true;
        boolean bo_return=false;

        if(stfonline_receiptno_tv.getText().toString().trim().length()==0||stfonline_receiptno_tv.getText().toString().trim().length()<1)
        {
            stfonline_receiptno_tv.setError("Empty is not allowed");
            stfonlinereceipt_no_tv_VR5=false;
        }
        if(stfonlinereceipt_no_tv_VR5)
        { bo_return=true; }
        else { bo_return=false; }

        return bo_return;
    }
    public boolean validationfor_dialog()
    {
        boolean stfonlinetrans_no_tv_VR1=true,stfonlinetrans_no_tv_VR2=true,stfonlinetransAmount_tv_VR3=true,stfonline_receiptno_tv_VR4=true,stfonline_receiptno_tv_VR5=true;
        boolean stfonlinetransAmount_tv_VR4=true,stfamountbycash_tv_VR5=true,stfamountbycash_tv_VR6=true;
        boolean stfonlinereceipt_no_tv_VR5=true,stfonlinereceipt_no_tv_VR6=true,stfonlinereceipt_no_tv_VR7=true;
        boolean stfamountbycash_receipt_no_tv_VR7=true,stfamountbycash_receipt_no_tv_VR8=true,stfamountbycash_receipt_no_tv_VR9=true;

        boolean bo_return=false;

        /*if(bo_onlinechecked)
        {*/
            /*if (stfonlinetrans_no_tv.getText().toString().trim().length() == 0) {
                stfonlinetrans_no_tv.setError("Empty not allowed");
                stfonlinetrans_no_tv.requestFocus();
                stfonlinetrans_no_tv_VR1 = false;
            } else if (stfonlinetrans_no_tv.getText().toString().trim().length() < 5)
            //if(sonlinetrans_no_tv.getText().toString().trim().length()<5)
            {
                stfonlinetrans_no_tv.setError("Minimum 5 character is required");
                stfonlinetrans_no_tv.requestFocus();
                stfonlinetrans_no_tv_VR2 = false;
            } else {
            }*/


            if ((stfonlinetransAmount_tv.getText().toString().trim().length() == 0))
            {
                stfonlinetransAmount_tv.setError("Please Enter Transaction ID");
                stfonlinetransAmount_tv.requestFocus();
                stfonlinetransAmount_tv_VR3 = false;
            } else if (stfonlinetransAmount_tv.getText().toString().equalsIgnoreCase("0")||stfonlinetransAmount_tv.getText().toString().equalsIgnoreCase("00")||stfonlinetransAmount_tv.getText().toString().equalsIgnoreCase("000")) {
                stfonlinetransAmount_tv.setError("Amount Should not be zero");
                stfonlinetransAmount_tv.requestFocus();
                stfonlinetransAmount_tv_VR4 = false;
            }

        if(forOnline.equals("yes")) {
            Log.e("tag","forOnline="+forOnline);
            if (stfonline_receiptno_tv.getText().toString().trim().length() == 0 || stfonline_receiptno_tv.getText().toString().trim().length() < 1) {
                stfonline_receiptno_tv.setError("Please Enter Transaction ID");
                stfonlinereceipt_no_tv_VR5 = false;
            }

        }else{
            Log.e("tag","forOnline="+forOnline);
            stfonlinereceipt_no_tv_VR5 = true;

        }


            /*if(stfonline_receiptno_tv.getText().toString().trim().length()==0||stfonline_receiptno_tv.getText().toString().trim().length()<1)
            {
                stfonline_receiptno_tv.setError("Empty is not allowed");
                stfonlinereceipt_no_tv_VR5=false;
            }*/

            /*if(stfonline_receiptno_tv.getText().toString().trim().length()<7||stfonline_receiptno_tv.getText().toString().trim().length()==1)
            {
                if(stfonline_receiptno_tv.getText().toString().trim().equals("0")||stfonline_receiptno_tv.getText().toString().trim().equals("00")||
                        stfonline_receiptno_tv.getText().toString().trim().equals("000")||stfonline_receiptno_tv.getText().toString().trim().equals("0000")||
                        stfonline_receiptno_tv.getText().toString().trim().equals("00000")||stfonline_receiptno_tv.getText().toString().trim().equals("000000"))
                {
                    stfonline_receiptno_tv.setError("Zero not allowed");
                    stfonlinereceipt_no_tv_VR6=false;
                }

            }*/

            /*if(stfonline_receiptno_tv.getText().toString().trim().length()>7)
            {
                stfonline_receiptno_tv.setError("Maximum 6 digit");
                stfonlinereceipt_no_tv_VR7=false;
            }*/

      //  }

        /*if(bo_cashchecked) {
            if (stfamountbycash_tv.getText().toString().trim().length() == 0) {
                stfamountbycash_tv.setError("Empty Not Allowed");
                stfamountbycash_tv.requestFocus();
                stfamountbycash_tv_VR5 = false;
            } else if (stfamountbycash_tv.getText().toString().trim().length() < 2) {
                stfamountbycash_tv.setError("Minimum Rs100");
                stfamountbycash_tv.requestFocus();
                stfamountbycash_tv_VR6 = false;
            }




            if(stfamountbycash_receiptno_tv.getText().toString().trim().length()==0||stfamountbycash_receiptno_tv.getText().toString().trim().length()<1)
            {
                stfamountbycash_receiptno_tv.setError("Empty is not allowed");
                stfamountbycash_receipt_no_tv_VR7=false;
            }

            if(stfamountbycash_receiptno_tv.getText().toString().trim().length()<7||stfamountbycash_receiptno_tv.getText().toString().trim().length()==1)
            {
                if(stfamountbycash_receiptno_tv.getText().toString().trim().equals("0")||stfamountbycash_receiptno_tv.getText().toString().trim().equals("00")||
                        stfamountbycash_receiptno_tv.getText().toString().trim().equals("000")||stfamountbycash_receiptno_tv.getText().toString().trim().equals("0000")||
                        stfamountbycash_receiptno_tv.getText().toString().trim().equals("00000")||stfamountbycash_receiptno_tv.getText().toString().trim().equals("000000"))
                {
                    stfamountbycash_receiptno_tv.setError("Zero not allowed");
                    stfamountbycash_receipt_no_tv_VR8=false;
                }

            }

            if(stfamountbycash_receiptno_tv.getText().toString().trim().length()>7)
            {
                stfamountbycash_receiptno_tv.setError("Maximum 6 digit");
                stfamountbycash_receipt_no_tv_VR9=false;
            }




        }*/


      //  if(bo_onlinechecked)
        //{
            if(stfonlinetransAmount_tv_VR3&&stfonlinetransAmount_tv_VR4
                    &&stfonlinereceipt_no_tv_VR5)
            { bo_return=true; }
            else { bo_return=false; }
       /* }
        if(bo_cashchecked)
        {
            if(stfamountbycash_tv_VR5&&stfamountbycash_tv_VR6&&stfamountbycash_receipt_no_tv_VR7&&stfamountbycash_receipt_no_tv_VR8
                    &&stfamountbycash_receipt_no_tv_VR9)
            { bo_return=true;}
            else {bo_return=false;}

        }*/

       /* if(stfonlinetrans_no_tv_VR1&&stfonlinetrans_no_tv_VR2&&stfonlinetransAmount_tv_VR3&&stfonlinetransAmount_tv_VR4&&stfamountbycash_tv_VR5&&stfamountbycash_tv_VR6)
        { return true;}else{return false;}
*/

        if(bo_onlinechecked||bo_onlinechecked){return bo_return; }
        else { return bo_return; }

    }

    public static class ViewHolder {
        TextView category_tv,total_tv,recevied_tv,submit_tv,balance_tv,categoryID_tv;
        ImageView pay_iv;
    }



    public class SubmitAmountDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //      private ProgressBar progressBar;
        ProgressDialog dialog;

        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = SubmitAmount();

            //   SoapObject response = ReApplyProjectDetails();
            Log.d("Soap response issssss",response.toString());

            return response;
        }

        public SubmitAmountDetails(Context activity) {
           // context = activity;
            dialog = new ProgressDialog(activity);
        }
        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result!=null) {
                if (result.toString().equals("Error")) {
                    Toast.makeText(getContext(), "Error occured while saving to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Sucessfully Updated", Toast.LENGTH_LONG).show();
                    GetSummarylistAsyncCallWS getSummarylistAsyncCallWS=new GetSummarylistAsyncCallWS(getActivity());
                    getSummarylistAsyncCallWS.execute();

                    GetSummaryDetailslistAsyncCallWS getSummaryDetailslistAsyncCallWS=new GetSummaryDetailslistAsyncCallWS(getActivity());
                    getSummaryDetailslistAsyncCallWS.execute();                }
            }
            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private SoapPrimitive SubmitAmount() {
        String METHOD_NAME = "Manager_Submit_Fees";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Manager_Submit_Fees";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            request.addProperty("Fees_Category_Id",Integer.valueOf(str_categoryID));
            request.addProperty("Fees_Category_Name",str_categoryName);
            request.addProperty("Submission_Amount",Integer.valueOf(str_Amount));
            request.addProperty("Payment_Mode",Integer.valueOf(str_paymentMode));
            request.addProperty("Remark",str_remarks);
            request.addProperty("Submitted_By",MDId);

            Log.d("PDIdssssssxxxx","hi");

            Log.d("Request submit fees=",request.toString());
            Log.e("tag","2 str_categoryName="+str_categoryName+"2 str_Amount="+str_Amount);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("tag","soap masterrequest update"+envelope.getResponse().toString());

                //return null;

                return response;

            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
            }
        }catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
        }
        return null;

    }

    public static class ViewHolderDetails {

        TextView amount,paidDate,payMode,paymentStatus,recBy,remark,feesCategory,subName;
    }
    public class SummaryDetailsViewAdapter extends BaseAdapter {


        public ArrayList<Class_ManagerSummaryDetails> projList;
        Activity activity;
        private ArrayList<Class_ManagerSummaryDetails> mDisplayedValues = null;

        public SummaryDetailsViewAdapter(Activity activity, ArrayList<Class_ManagerSummaryDetails> projList) {
            super();
            this.activity = activity;
            this.projList = projList;
            this.mDisplayedValues = projList;
        }

        @Override
        public int getCount() {
            //return projList.size();
            Log.e("size", String.valueOf(mDisplayedValues.size()));
            return mDisplayedValues.size();

        }

        @Override
        public Class_ManagerSummaryDetails getItem(int position) {

            //return projList.get(position);
            return mDisplayedValues.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolderDetails holder;
            LayoutInflater inflater = activity.getLayoutInflater();

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.manager_submitted_feeslist, null);
                holder = new ViewHolderDetails();

                holder.amount = (TextView) convertView.findViewById(R.id.amount);
                holder.paidDate = (TextView) convertView.findViewById(R.id.paidDate);
                holder.payMode = (TextView) convertView.findViewById(R.id.payMode);
                holder.paymentStatus = (TextView) convertView.findViewById(R.id.paymentStatus);
                holder.recBy = (TextView) convertView.findViewById(R.id.recBy);
                holder.remark = (TextView) convertView.findViewById(R.id.remark);
                holder.subName = (TextView) convertView.findViewById(R.id.subName);
                holder.feesCategory = (TextView) convertView.findViewById(R.id.feesCategory);

//                holder.categoryID_tv = (TextView) convertView.findViewById(R.id.categoryID_tv);

                // holder.status_tv=(TextView)convertView.findViewById(R.id.status_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolderDetails) convertView.getTag();
            }
            Class_ManagerSummaryDetails Obj_Class_farmerlistdetails = (Class_ManagerSummaryDetails) getItem(position);


            if (Obj_Class_farmerlistdetails != null) {
                {
                    //  String FullName = Obj_Class_farmerlistdetails.getStudentName();
                    holder.amount.setText(Obj_Class_farmerlistdetails.getSubmission_Amount());
                    holder.paidDate.setText(Obj_Class_farmerlistdetails.getSubmitted_Date());
                    holder.payMode.setText(Obj_Class_farmerlistdetails.getSubmitted_Mode());
                    holder.paymentStatus.setText(Obj_Class_farmerlistdetails.getRec_Status());
                    holder.recBy.setText(Obj_Class_farmerlistdetails.getRec_By());
                    holder.remark.setText(Obj_Class_farmerlistdetails.getRec_Remark());
                    holder.subName.setText(Obj_Class_farmerlistdetails.getSubmitter_Name());
                    holder.feesCategory.setText(Obj_Class_farmerlistdetails.getFees_Category_description());

                }

            }

            return convertView;
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

            request.addProperty("Academic_Id", str_academicId);//Long
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

}//end of fragment class
