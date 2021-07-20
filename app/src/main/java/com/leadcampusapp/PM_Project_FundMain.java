package com.leadcampusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PM_Project_FundMain extends AppCompatActivity {

    AppCompatSpinner spin_ticketStatus;
    Class_Project_FundMain obj_Class_Project_FundMain;
    String str_ticketSlno,str_ticketStatus,str_Status;
    int ticketStatusCount;
    Class_Project_FundMain[] arrayObj_Class_TicketStatus;
    ArrayAdapter adapter_ticketStatus;

    EditText edt_fromdate,edt_todate,fromdateseterror_TV,todateseterror_TV;
    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;
    Class_FromToDate[] arrayObj_Class_FromToDate;
    String FromDate,ToDate;
    String str_FromToDateStatus;
    int From_ToDateCount;
    String yearID,str_yearID;
    ListView lv_summary;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_fund_main);

        spin_ticketStatus= (AppCompatSpinner) findViewById(R.id.spin_ticketStatus);
        edt_fromdate = (EditText) findViewById(R.id.edt_fromdate);
        fromdateseterror_TV = (EditText) findViewById(R.id.fromdateseterror_TV);
        edt_todate = (EditText) findViewById(R.id.edt_todate);
        todateseterror_TV = (EditText) findViewById(R.id.todateseterror_TV);

        lv_summary = (ListView) findViewById(R.id.lv_summary);

      //  TicketStatusAsyncCallWS ticketStatusAsyncCallWS=new TicketStatusAsyncCallWS(getApplicationContext());
       // ticketStatusAsyncCallWS.execute();
        GetFromToDateAsyncCallWS getFromToDateAsyncCallWS=new GetFromToDateAsyncCallWS(getApplicationContext());
        getFromToDateAsyncCallWS.execute();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Pending");
        arrayList.add("Manager Reject");
        arrayList.add("Accontent Reject");


        adapter_ticketStatus = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayList);
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_ticketStatus.setDropDownViewResource(R.layout.spinnercustomstyle);
        //  districtsearch_ATV.setAdapter(dataAdapter_district);
        spin_ticketStatus.setAdapter(adapter_ticketStatus);
        spin_ticketStatus.setSupportBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorBlack));

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

        edt_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), R.style.DatePickerTheme,
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
        edt_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(), R.style.DatePickerTheme,
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
        });

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


                adapter_ticketStatus = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_TicketStatus);
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
}

