package com.leadcampusapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.leadcampusapp.remote.Class_ApiUtils;
import com.leadcampusapp.remote.Interface_userservice;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_studentUPI extends AppCompatActivity {
    EditText upi;
    Button payment_bt,summarydetails_bt;
    String upiId,str_amount,str_vendorname;
    TextView amount_tv,vendorname_tv;



    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_SCellNo = "prefid_scellno"; //
    public static final String PrefID_S_isprofileEdited = "prefid_isprofileEdited";
    public static final String PrefID_SStudentType = "prefid_sstudentType";
    public static final String PrefId_S_Gender = "prefid_sgender";
    public static final String PrefId_S_AcademicId = "prefid_sacademicid";

    SharedPreferences shardpref_S_obj;
    String str_sname,str_studentregistrationID,str_academicID;

    String str_detailspayment_response,str_feesmaster_response;
    private ListView summarylist_listview;

    Class_studentpayment_summary[] studentpayment_summary_arryObj;
    Class_studentpayment_summary studentpayment_summary_obj;

    Class_FeesCatMaster[] feesCatMasters_arryObj;
    Class_FeesCatMaster feesCatMasters_Obj;
    String str_feesID,str_feescatID;

    Spinner paymentfor_sp;
    String str_orderid,str_checksumtoken,str_transcation_token,str_savedpayment_response,str_yyyy_mm_dd;
    TransactionManager transactionManager;
    Integer ActivityRequestCode = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentupi);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Lead Payment");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        upi = findViewById(R.id.upi);
        vendorname_tv=findViewById(R.id.vendorname_tv);
        amount_tv=findViewById(R.id.amount_tv);
        payment_bt = findViewById(R.id.payment_bt);
        summarydetails_bt=findViewById(R.id.summarydetails_bt);
        summarylist_listview=findViewById(R.id.summarylist_listview);
        paymentfor_sp=findViewById(R.id.paymentfor_sp);


        shardpref_S_obj = getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        str_sname = shardpref_S_obj.getString(PrefID_SName, "").trim();
        vendorname_tv.setText(str_sname);

        str_studentregistrationID=shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_academicID=shardpref_S_obj.getString(PrefId_S_AcademicId, "").trim();

        Log.e("academic",shardpref_S_obj.getString(PrefId_S_AcademicId, "").trim());
        Log.e("studentregID",shardpref_S_obj.getString(PrefID_RegID, "").trim());
// 41275 7

      //  https://mis.leadcampus.org:8084/leadws/Login.asmx/GetstateList?stateid=0&Manager_Id=0
        //https://mis.leadcampus.org:8084/leadws/managerws.asmx/Get_Fees_Category_Master?Registration_Id=41275&College_ID=0
      //  https://mis.leadcampus.org:8084/leadws/Login.asmx/GetstateList?stateid=0&Manager_Id=0

        //https://mis.leadcampus.org:8084/leadws/managerws.asmx/Get_Fees_Category_Master?Registration_Id=41275&College_ID=0
        /*int int_feespaid= Integer.parseInt("1.0");
        Log.e("amount", String.valueOf(int_feespaid));*/


//  start click


        Get_Fees_Category_Master_AsynTask task_fees=new Get_Fees_Category_Master_AsynTask(Activity_studentUPI.this);
        task_fees.execute();


        StudentSummaryDetails_AsynTask task = new StudentSummaryDetails_AsynTask(Activity_studentUPI.this);
        task.execute();


        payment_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                upiId = upi.getText().toString();
                str_vendorname=vendorname_tv.getText().toString();
                str_amount=amount_tv.getText().toString();
                if (TextUtils.isEmpty(upiId)) {
                    upi.setError("Required");
                    return;
                }

                //Intent intent = new Intent(getApplicationContext(), StudentUPIPayment.class);

                /*Intent intent = new Intent(getApplicationContext(), StudentUPIPayment.class);
                intent.putExtra("upi", upiId);
                intent.putExtra("amount", str_amount);
                intent.putExtra("vendorname",str_vendorname);
                intent.putExtra("studentregistrationID",str_studentregistrationID);
                intent.putExtra("studentfeesID",str_feesID);
                intent.putExtra("studentfeescatID",str_feescatID);
                startActivity(intent);*/

                str_orderid=str_checksumtoken=str_transcation_token=str_yyyy_mm_dd="0";
                fetch_token();

            }
        });





        paymentfor_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                feesCatMasters_Obj = (Class_FeesCatMaster) paymentfor_sp.getSelectedItem();
                str_feesID = feesCatMasters_Obj.getFees_ID().toString();
                str_feescatID=feesCatMasters_Obj.getFees_Category_Slno().toString();




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        summarydetails_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });


    }


    @Override
    public void onBackPressed() {

        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_studentUPI.this);
            dialog.setCancelable(false);
            dialog.setTitle("LEADCampus");
            dialog.setMessage("Are you sure want to Exit Payment Module");

            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Intent i = new Intent(Activity_studentUPI.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Action for "Cancel".
                            dialog.dismiss();
                        }
                    });

            final AlertDialog alert = dialog.create();
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                }
            });
            alert.show();

            //alert.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
        }
    }




    private class Get_Fees_Category_Master_AsynTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait,Fetching Fees Details..");
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

           Get_Fees_Category_Master();  //
            return null;
        }

        public Get_Fees_Category_Master_AsynTask(Activity_studentUPI activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            dialog.dismiss();




            if(str_feesmaster_response.equalsIgnoreCase("Success"))
            {


                ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle,feesCatMasters_arryObj);
                dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
                paymentfor_sp.setAdapter(dataAdapter);



            }else{
                //alert();
            }

        }

    }





    public void Get_Fees_Category_Master()
    {

        String URL = Class_URL.URL_Manager.toString().trim();


        String METHOD_NAME = "Get_Fees_Category_Master";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Get_Fees_Category_Master";

//<Registration_Id>int</Registration_Id>
//      <College_ID>int</College_ID>


        int int_regID= Integer.parseInt(str_studentregistrationID);

        try {
            str_feesmaster_response="x";

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Registration_Id",int_regID );//<Registration_Id>int</Registration_Id>
            request.addProperty("College_ID",0 );//<College_ID>int</College_ID>

//https://mis.leadcampus.org:8084/leadws/managerws.asmx/Get_Fees_Category_Master?Registration_Id=41275&College_ID=0
            //
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);


                //SoapPrimitive response = (SoapPrimitive ) envelope.getResponse();
                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject response_SO = (SoapObject) response.getProperty(0);
                Log.e("Feestatus",response_SO.getProperty("Status").toString());

                str_feesmaster_response=response_SO.getProperty("Status").toString();
                //Log.e("fees", String.valueOf(response.getPropertyCount()));
                int count =response.getPropertyCount();

               feesCatMasters_arryObj=new Class_FeesCatMaster[count];

                /*anyType{vmGET_FeeCategory_Master=anyType{Fees_Category_Slno=1; fees_category_code=RG;
Fees_category_description=Registration; Fees=1; Fees_ID=486; academic_year=0; Status=Success; }; }*/

                //Action means my doing

                for(int i=0;i<count;i++)
                {


                    SoapObject responses_SO = (SoapObject) response.getProperty(i);

                    Class_FeesCatMaster FeesCatMaster_innerObj=new Class_FeesCatMaster();

                    FeesCatMaster_innerObj.setFees_Category_Slno(responses_SO.getProperty("Fees_Category_Slno").toString());
                    FeesCatMaster_innerObj.setFees_category_code(responses_SO.getProperty("fees_category_code").toString());
                    FeesCatMaster_innerObj.setFees_category_description(responses_SO.getProperty("Fees_category_description").toString());
                   FeesCatMaster_innerObj.setFees(responses_SO.getProperty("Fees").toString());
                            FeesCatMaster_innerObj.setFees_ID(responses_SO.getProperty("Fees_ID").toString());
                            FeesCatMaster_innerObj.setAcademic_year(responses_SO.getProperty("academic_year").toString());
                            FeesCatMaster_innerObj.setStatus(responses_SO.getProperty("Status").toString());


                    Log.e("payresp",responses_SO.getProperty("Fees_category_description").toString());

                    feesCatMasters_arryObj[i]=FeesCatMaster_innerObj;

                }


                Log.e("fees_details",response.toString());



            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("feesdetails fail", "> " + t.getMessage());
                //internet_issue1 = "savepayment";
                // Toast.makeText(getApplicationContext(), "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();

            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
            Toast.makeText(getApplicationContext(), "Fees Details: " + t.toString(),Toast.LENGTH_LONG).show();
        }









    }













    private class StudentSummaryDetails_AsynTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait,Fetching Payment Details..");
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

            Get_Payment_Details();  //
            return null;
        }

        public StudentSummaryDetails_AsynTask(Activity_studentUPI activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            dialog.dismiss();

            if(str_detailspayment_response.equalsIgnoreCase("Success"))
            {


                if (studentpayment_summary_arryObj != null)
                {
                    CustomAdapter_paymentdetails adapter = new CustomAdapter_paymentdetails();
                    summarylist_listview.setAdapter(adapter);

                    int y = studentpayment_summary_arryObj.length;

                    Log.e("length adapter", "" + y);
                } else {
                    Log.d("length adapter", "zero");
                }

            }else{
                //alert();
            }

        }

    }






    public void Get_Payment_Details()
    {
//https://mis.leadcampus.org:8084/leadws/managerws.asmx/Get_Payment_Details?Registration_ID=41275&Manager_Id=0&Academic_Id=7
        /*

      */
        String URL = Class_URL.URL_Manager.toString().trim();


        String METHOD_NAME = "Get_Payment_Details";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Get_Payment_Details";


      int int_regID= Integer.parseInt(str_studentregistrationID);
      int int_mangerID=0;
      int int_academicID= Integer.parseInt(str_academicID);
        try {
            str_detailspayment_response="x";

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Registration_ID",int_regID );//<Registration_ID>int</Registration_ID>
            request.addProperty("Manager_Id", int_mangerID);//<Manager_Id>int</Manager_Id>
            request.addProperty("Academic_Id",int_academicID );//<Academic_Id>int</Academic_Id>

//
            //
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);


                //SoapPrimitive response = (SoapPrimitive ) envelope.getResponse();
                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject response_SO = (SoapObject) response.getProperty(0);
                Log.e("status",response_SO.getProperty("Status").toString());

                str_detailspayment_response=response_SO.getProperty("Status").toString();
              //  Log.e("status", String.valueOf(response.getPropertyCount()));
                int count =response.getPropertyCount();

                studentpayment_summary_arryObj=new Class_studentpayment_summary[count];

                for(int i=0;i<count;i++)
                {

                    SoapObject responses_SO = (SoapObject) response.getProperty(i);
                    Class_studentpayment_summary studentpayment_summary_innerObj=new Class_studentpayment_summary();

                    studentpayment_summary_innerObj.setStr_Status(responses_SO.getProperty("Status").toString());
                   studentpayment_summary_innerObj.setStr_reference_id(responses_SO.getProperty("reference_id").toString());//<reference_id>1612960865</reference_id>
                    studentpayment_summary_innerObj.setStr_Auto_Receipt_No(responses_SO.getProperty("Auto_Receipt_No").toString());//<Auto_Receipt_No>19</Auto_Receipt_No>
                    studentpayment_summary_innerObj.setStr_Created_Date(responses_SO.getProperty("Created_Date").toString());//<Created_Date>10-02-2021 06:02;30 06:11:30 PM</Created_Date>

                    Log.e("payresp",responses_SO.getProperty("reference_id").toString());



                    studentpayment_summary_arryObj[i]=studentpayment_summary_innerObj;

                }




                Log.e("resp_details",response.toString());



            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("saved payment fail", "> " + t.getMessage());
                //internet_issue1 = "savepayment";
                // Toast.makeText(getApplicationContext(), "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();

            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
            Toast.makeText(getApplicationContext(), "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();
        }

    }//End of uploaddetails













    private class Holder_offline {
        TextView holder_paymentstatus_tv;
        TextView holder_payment_tranID_tv;
        TextView holder_date_tv;
        TextView holder_receipt_no_tv;

    }



    public class CustomAdapter_paymentdetails extends BaseAdapter
    {


        public CustomAdapter_paymentdetails() {

            super();
            Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(studentpayment_summary_arryObj.length);
            System.out.println("studentpayment_summary_arryObj.length" + x);
            return studentpayment_summary_arryObj.length;
        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            Log.d("getItem position", "x");
            return studentpayment_summary_arryObj[position];
        }


        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);

            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView1, ViewGroup parent)
        {

            final Holder_offline holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView1 == null) {
                holder = new Holder_offline();
                convertView1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_row_item_student_paymentdetails, parent, false);



              //  holder.holder_paymentstatus_tv = (TextView) convertView1.findViewById(R.id.payment_status_tv);
                holder.holder_payment_tranID_tv = (TextView) convertView1.findViewById(R.id.payment_tranID_tv);
                holder.holder_receipt_no_tv = (TextView) convertView1.findViewById(R.id.receipt_no_tv);
                holder.holder_date_tv= (TextView) convertView1.findViewById(R.id.date_tv);

                Log.d("Inside If convertView1", "Inside If convertView1");

                convertView1.setTag(holder);

            } else {
                holder = (Holder_offline) convertView1.getTag();
                Log.d("else convertView1", "else convertView1");
            }

            // Class_farmponddetails_offline farmponddetails_obj;
            studentpayment_summary_obj = (Class_studentpayment_summary) getItem(position);


            if (studentpayment_summary_obj != null) {
                if ((studentpayment_summary_obj.getStr_Status().equalsIgnoreCase("Success"))) {


                    Log.e("payment",studentpayment_summary_obj.getStr_reference_id().toString());
                    //holder.holder_paymentstatus_tv.setText(studentpayment_summary_obj.getStr_Status().toString());
                    holder.holder_payment_tranID_tv.setText(studentpayment_summary_obj.getStr_reference_id().toString());
                    holder.holder_date_tv.setText(studentpayment_summary_obj.getStr_Created_Date().toString());
                    holder.holder_receipt_no_tv.setText(studentpayment_summary_obj.getStr_Auto_Receipt_No().toString());


                }


            }



            return convertView1;

        }//End of custom getView
    }//End of CustomAdapter



//--------------------Paytm-------------------------------------------------

    public void fetch_token()
    {


        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        String date = df.format(c.getTime());
        Random rand = new Random();
        int min =1000, max= 9999;
// nextInt as provided by Random is exclusive of the top value so you need to add 1
        int randomNum = rand.nextInt((max - min) + 1) + min;
        str_orderid =  date+String.valueOf(randomNum);
        Log.e("orderid",str_orderid);



        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(Activity_studentUPI.this);
        progressDialog.setMessage("Fetching CheckSum Token....");
        progressDialog.setTitle("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();


       // String MID = "Deshpa57179173206691";//Staging
        String MID="DesFou64628105401332";//Production
        String order_id = str_orderid;
        String Website = "WEBSTAGING";


        String Amount = "1";

        //String callback="https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID="+order_id;
        String callback="https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+order_id;


        String Str_checksum1 = "{\"requestType\":\"Payment\",\"mid\":\"" + MID + "\",\"orderId\":\"" + order_id + "\"," +
                "\"websiteName\":\"" + Website + "\",\"txnAmount\":{\"value\":\"" + Amount + "\",\"currency\":\"INR\"}," +
                "\"userInfo\":{\"custId\":\"WE25622\"},\"enablePaymentMode\":[{\"mode\":\"UPI\", \"channels\":[\"UPIPUSH\",\"UPIPUSHEXPRESS\"]}]," +
                "\"callbackUrl\":\"" + callback + "\"}";

        String Str_checksum = "{\"requestType\":\"Payment\",\"mid\":\""+MID+"\",\"orderId\":\""+order_id+"\"," +
                "\"websiteName\":\""+Website+"\",\"txnAmount\":{\"value\":\""+Amount+"\",\"currency\":\"INR\"}," +
                "\"userInfo\":{\"custId\":\"WE25622\"},\"callbackUrl\":\""+callback+"\"}";

        //  Call<Class_token_response> call = userService.getpaytmchecksumkey(orderIdString);
        Call<Class_token_response> call = userService.getpaytmchecksumkey(Str_checksum);
        //  Call<Class_token_response> call = userService.getpaytmchecksumkey("jkk12345678912345612123");
        //jkk12345678912345612123

        Log.e("checksum_url","https://www.dfindia.org:82/api/Authentication/getpaytmchecksumkey?OrderId="+str_orderid);


        call.enqueue(new Callback<Class_token_response>() {
            @Override
            public void onResponse(Call<Class_token_response> call, Response<Class_token_response> response)
            {
                //    Log.e("token_response", "token_response: " + new Gson().toJson(response));



                try{



                    if(response.isSuccessful())
                    {

                        Class_token_response loc_obj = response.body();
                        Log.e("response.body", response.body().getMessage().toString());

                        Log.e("checksum_token",loc_obj.getTokenresp().getPaytmChecksum().toString());

                        Log.e("checksum :","Validation");
                        Log.e("checksum_validateion",loc_obj.getTokenresp().getVerifySignature());

                        str_checksumtoken=loc_obj.getTokenresp().getPaytmChecksum().toString().trim();

                       // checksumtoken_et.setText(loc_obj.getTokenresp().getPaytmChecksum().toString());
                        //   txnToken.setText(loc_obj.getTokenresp().getPaytmChecksumNew().toString());

                        // Log.e("response.body", response.body().size);
                        progressDialog.dismiss();

                        Log.e("call", "PayTM Transcation API");
                        Transactiontoken_AsynTask task= new Transactiontoken_AsynTask(Activity_studentUPI.this);
                        task.execute();


                    }

                }catch(Exception ex)
                {
                    Log.e("error", String.valueOf(ex));
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                progressDialog.dismiss();
                Log.e("WS", "error" + t.getMessage());
                Toast.makeText(Activity_studentUPI.this, "WS:" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



    }


    private class Transactiontoken_AsynTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait,fetching transcation token...");
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

            get_transcationtoken();  //
            return null;
        }

        public Transactiontoken_AsynTask(Activity_studentUPI activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();
            if(str_transcation_token.equalsIgnoreCase("0"))
            {

            }else{
                do_paytm_transcation();
            }
        }

    }




    public void get_transcationtoken()
    {

        //String MID = "Deshpa57179173206691";//Staging
        String MID="DesFou64628105401332";//Production
        String order_id = str_orderid;
        String Website = "WEBSTAGING";
        String Merchant_key = "kYCILDoti4a8hb5f";


        String Amount = "1";
        //String value = "https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction?mid=Deshpa57179173206691&orderId="+order_id;
        String value = "https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=DesFou64628105401332&orderId="+order_id; //Production

        String callback="https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+order_id;


        String json_for_checksum1 = "{\"requestType\":\"Payment\",\"mid\":\"" + MID + "\",\"orderId\":\"" + order_id + "\"," +
                "\"websiteName\":\"" + Website + "\",\"txnAmount\":{\"value\":\"" + Amount + "\",\"currency\":\"INR\"}," +
                "\"userInfo\":{\"custId\":\"WE25622\"},\"enablePaymentMode\":[{\"mode\":\"UPI\", \"channels\":[\"UPIPUSH\",\"UPIPUSHEXPRESS\"]}]," +
                "\"callbackUrl\":\"" + callback + "\"}";


        //"enablePaymentMode" : [{ "mode":"UPI", "channels":["UPIPUSH","UPIPUSHEXPRESS"] }]

        String json_for_checksum = "{\"requestType\":\"Payment\",\"mid\":\""+MID+"\",\"orderId\":\""+order_id+"\"," +
                "\"websiteName\":\""+Website+"\",\"txnAmount\":{\"value\":\""+Amount+"\",\"currency\":\"INR\"}," +
                "\"userInfo\":{\"custId\":\"WE25622\"},\"callbackUrl\":\""+callback+"\"}";

        Log.e("json_for_checksum",json_for_checksum);



        //String checksum = PaytmChecksum.generateSignature(body.toString(), "YOUR_MERCHANT_KEY");


        //String Second_jason = "{\"head\":{\"signature\":\"" + str_checksumtoken + "\"},\"body\":" + json_for_checksum + "}";

        String Second_jason = "{\"head\":{\"signature\":\"" + str_checksumtoken + "\"},\"body\":"+json_for_checksum+"}";


        String post_data = Second_jason.toString();



        /* for Staging */
        URL url = null;
        try {
            //url = new URL("https://securegw-stage.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");
            String str_staging_url=value;
            Log.e("str_staging_url",str_staging_url);
            url = new URL(str_staging_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        /* for Production */
// URL url = new URL("https://securegw.paytm.in/theia/api/v1/initiateTransaction?mid=YOUR_MID_HERE&orderId=ORDERID_98765");

        Log.e("parameters","of Head & Body");
        Log.e("parameter_passed",post_data);

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            DataOutputStream requestWriter = new DataOutputStream(connection.getOutputStream());
            requestWriter.writeBytes(post_data);
            requestWriter.close();
            String responseData = "";
            InputStream is = connection.getInputStream();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(is));
            if ((responseData = responseReader.readLine()) != null)
            {
                System.out.append("TransResponse: " + responseData);
                Log.e("response", String.valueOf(responseData));

                /*JSONObject jsonObject = new JSONObject(responseData);

                JSONObject myResponse = jsonObject.getJSONObject("body");
                JSONArray tsmresponse = (JSONArray) myResponse.get("body");

                Log.e("length", String.valueOf(tsmresponse.length()));*/


                // tsmresponse.getJSONObject(0).getString("name");

                JSONObject jsonObject = new JSONObject(responseData);
                JSONObject myResponse = jsonObject.getJSONObject("body");
                str_transcation_token=myResponse.getString("txnToken").toString();
                //txnToken_et.setText(str_transcation_token);
                Log.e("transcationToken",myResponse.getString("txnToken").toString());

            }
            responseReader.close();
        } catch (Exception exception)
        {
            exception.printStackTrace();
            Log.e("error", String.valueOf(exception));
        }


    }




    public void do_paytm_transcation()
    {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(Activity_studentUPI.this);
        progressDialog.setMessage("PayTM Transcation....");
        progressDialog.setTitle("Please wait....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();




        String str_txnAmount = "1";
        //txnAmountString = "1.00";
       // String str_txnmid ="Deshpa57179173206691";//Staging
        String str_txnmid="DesFou64628105401332";// production
        String str_txnorderId =str_orderid;
       String str_txnToken= str_transcation_token;

        String host = "https://securegw-stage.paytm.in/";
        /*if(environment.isChecked()){
            host = "https://securegw.paytm.in/";
        }*/

        progressDialog.dismiss();
        if(2>1)
        {
            String orderDetails = "MID: " + str_txnmid + ", OrderId: " + str_txnorderId + ", TxnToken: " + str_txnToken + ", Amount: " + str_txnAmount;
            Toast.makeText(this, orderDetails, Toast.LENGTH_SHORT).show();



            //  String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderIdString;

            String callBackUrl="http://localhost:50559/callbackRes.aspx";

            //String callBackUrl="https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=PYTM_ORDR_"+orderIdString;


            // https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=PYTM_ORDR_1618228030"
            //https://securegw-stage.paytm.in/theia/paytmCallback?

            Log.e("callbackurl : ",callBackUrl);


            PaytmOrder paytmOrder = new PaytmOrder(str_txnorderId, str_txnmid, str_txnToken, str_txnAmount, callBackUrl);

            //Log.e("callbackurl : ", String.valueOf(paytmOrder));
            Log.e("paytmOrder : ", new Gson().toJson(paytmOrder));

            transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback()
            {

                @Override
                public void onTransactionResponse(Bundle bundle)
                {
                    Log.e("transactionManager : ", new Gson().toJson(transactionManager));

                    Log.e("onTransactionResp : ", bundle.toString());

                    String str_bundle=bundle.toString();
                    Log.e("onTransactionResp : ", str_bundle);

                  //  Toast.makeText(Activity_studentUPI.this, "Response (onTransactionResponse) : "+bundle.toString(), Toast.LENGTH_SHORT).show();
                   /* SaveStudentPaymentDetails_AsynTask task = new SaveStudentPaymentDetails_AsynTask(Activity_studentUPI.this);
                    task.execute();*/


                    savepaymentdetails();
                }

                @Override
                public void networkNotAvailable()
                {

                }

                @Override
                public void onErrorProceed(String s) {
                    Log.e("onErrorProceed : ", s.toString());
                }

                @Override
                public void clientAuthenticationFailed(String s) {
                    Log.e("clientAuthenFailed: ", s.toString());
                }

                @Override
                public void someUIErrorOccurred(String s) {
                    Log.e("someUIErrorOccur: ", s.toString());
                }

                @Override
                public void onErrorLoadingWebPage(int i, String s, String s1) {
                    Log.e("ErrorLoadingWebPage: ", s.toString());
                }

                @Override
                public void onBackPressedCancelTransaction() {

                }

                @Override
                public void onTransactionCancel(String s, Bundle bundle) {

                }
            });

            transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
            Log.e("ShowPaymentUrl: ", host + "theia/api/v1/showPaymentPage");
            transactionManager.startTransaction(this, ActivityRequestCode);

            Log.e("transcation","completed1");
        }


        Log.e("transcation","completed2");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null)
        {

            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.e("tag", key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }

            Log.e("nativeSdkForMerchMsg:",data.getStringExtra("nativeSdkForMerchantMessage"));
            Log.e("onActivityRes:", data.getStringExtra("response"));
            Toast.makeText(this, "Response: "+data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();

            Log.e("transcation","completed3");
        }
    }




    public void savepaymentdetails()
    {
         SaveStudentPaymentDetails_AsynTask task = new SaveStudentPaymentDetails_AsynTask(Activity_studentUPI.this);
                    task.execute();
    }



    private class SaveStudentPaymentDetails_AsynTask extends AsyncTask<String, Void, Void>
    {
        ProgressDialog dialog_savepayment;
        Context context;

        protected void onPreExecute()
        {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog_savepayment.setMessage("Please wait,Updating Payment Details..");
            dialog_savepayment.setCanceledOnTouchOutside(false);
            dialog_savepayment.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            SaveStudentPaymentDetails();  //
            return null;
        }

        public SaveStudentPaymentDetails_AsynTask(Activity_studentUPI activity) {
            context = activity;
            dialog_savepayment = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            dialog_savepayment.dismiss();

            if(str_savedpayment_response.equalsIgnoreCase("Success"))
            {
                // finish();
                Payment_alertdialog("Success");

            }else{
                //alert();
            }

        }

    }




    public void SaveStudentPaymentDetails()
    {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        str_yyyy_mm_dd = df.format(c.getTime());
        // formattedDate have current date/time
        Log.e("date", str_yyyy_mm_dd);

        String URL = Class_URL.URL_Projects.toString().trim();

        String METHOD_NAME = "Save_Student_PaymentDetails";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Save_Student_PaymentDetails";



        try {
            str_savedpayment_response="false";

            int int_studentregID= Integer.parseInt(str_studentregistrationID);

            int int_feesID= Integer.parseInt(str_feesID);
            //  int int_feespaid= Integer.parseInt(str_amount);
           // int int_referenceID= Integer.parseInt("12345678");
            String str_referenceID="123456";

           // BigInteger int_referenceID=new BigInteger(str_orderid);


            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Fees_Category_ID",str_feescatID );//<Fees_Category_ID>string</Fees_Category_ID>
            request.addProperty("Programe_ID", 1);//<Programe_ID>int</Programe_ID>
            request.addProperty("Registration_ID",int_studentregID );//<Registration_ID>int</Registration_ID>
            request.addProperty("Paid_fees", 1);//<Paid_fees>int</Paid_fees>
            request.addProperty("Paid_date", str_yyyy_mm_dd);//<Paid_date>string</Paid_date>
            request.addProperty("Payment_Type", "UPI");//<Payment_Type>string</Payment_Type>
            request.addProperty("Payeer_Id", " ");//<Payeer_Id>string</Payeer_Id>
            request.addProperty("Transaction_ID", "0");//<Transaction_ID>int</Transaction_ID>
            request.addProperty("Reference_ID", str_referenceID);//<Reference_ID>int</Reference_ID>
            request.addProperty("Transaction_Status", "Success");//<Transaction_Status>string</Transaction_Status>
            request.addProperty("Created_User_Type", "Student");// <Created_User_Type>string</Created_User_Type>
            request.addProperty("User_Id", int_studentregID);//<User_Id>int</User_Id>
            request.addProperty("Fees_ID", int_feesID);// <Fees_ID>int</Fees_ID>



//
            //
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);


                //SoapPrimitive response = (SoapPrimitive ) envelope.getResponse();
                SoapObject response = (SoapObject) envelope.getResponse();

                SoapObject payment_response = (SoapObject) response.getProperty(0);
                Log.e("status",payment_response.getProperty("Status").toString());

                str_savedpayment_response = payment_response.getProperty("Status").toString();
                Log.e("resp_savepayment",response.toString());





            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("saved payment fail", "> " + t.getMessage());
                //internet_issue1 = "savepayment";
                // Toast.makeText(getApplicationContext(), "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();

            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
            Toast.makeText(getApplicationContext(), "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();
        }

    }//End of uploaddetails






    public void Payment_alertdialog(String Success)
    {

        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_studentUPI.this);
            dialog.setCancelable(false);
            dialog.setTitle("Alert");
            if(Success.equalsIgnoreCase("Success")) {
                dialog.setMessage("Congrants Payment is Successfull");
            }
            else if (Success.equalsIgnoreCase("cancelled"))
            {
                dialog.setMessage("Payment is cancelled!");
            }else{
                dialog.setMessage("Kindy install digital wallet application");
            }

            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {

                   /* SaveStudentPaymentDetails_AsynTask task = new SaveStudentPaymentDetails_AsynTask(StudentUPIPayment.this);
                    task.execute();*/
                    Intent i = new Intent(Activity_studentUPI.this, Activity_studentUPI.class);
                    startActivity(i);
                    finish();
                }
            });

            final AlertDialog alert = dialog.create();
            alert.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                }
            });
            alert.show();

            //alert.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
        }
    }







}//end of class


























    /*public void SaveStudentPaymentDetails()
    {

        String URL = Class_URL.URL_Manager.toString().trim();
        *//*String METHOD_NAME = "UpdateStudentProfilewithstring";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/UpdateStudentProfilewithstring";
*//*

        String METHOD_NAME = "Save_Student_PaymentDetails";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Save_Student_PaymentDetails";



        try {
            str_savedpayment_response="x";

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Fees_Category_ID","1" );//<Fees_Category_ID>string</Fees_Category_ID>
            request.addProperty("Programe_ID", 1);//<Programe_ID>int</Programe_ID>
            request.addProperty("Registration_ID",43676 );//<Registration_ID>int</Registration_ID>
            request.addProperty("Paid_fees", 100);//<Paid_fees>int</Paid_fees>
            request.addProperty("Paid_date", "2020-01-21");//<Paid_date>string</Paid_date>
            request.addProperty("Payment_Type", "UPI");//<Payment_Type>string</Payment_Type>
            request.addProperty("Payeer_Id", "empty");//<Payeer_Id>string</Payeer_Id>
            request.addProperty("Transaction_ID", 123567);//<Transaction_ID>int</Transaction_ID>
            request.addProperty("Reference_ID", 1234567);//<Reference_ID>int</Reference_ID>
            request.addProperty("Transaction_Status", "Success");//<Transaction_Status>string</Transaction_Status>
            request.addProperty("Created_User_Type", "Student");// <Created_User_Type>string</Created_User_Type>
            request.addProperty("User_Id", 43676);//<User_Id>int</User_Id>



//
            //
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);


                //SoapPrimitive response = (SoapPrimitive ) envelope.getResponse();
                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject root = (SoapObject) response.getProperty(0);
                Log.e("status",root.getProperty("Status").toString());

                //str_savedpayment_response = response.toString();
                Log.e("resp editprofile",response.toString());



            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("saved payment fail", "> " + t.getMessage());
                //internet_issue1 = "savepayment";
                // Toast.makeText(getApplicationContext(), "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();

            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
            Toast.makeText(getApplicationContext(), "Request failed: " + t.toString(),Toast.LENGTH_LONG).show();
        }

    }//End of uploaddetails*/


  /*  anyType{vmPayment_Details=anyType{Payment_Id=12; Lead_Id=MI00001;
    StudentName=Sharad Noolvi; Registration_Id=43676; Paid_Fees=100;
    paid_date=21-January-20; Created_Date=10-02-2021 04:02;13 04:34:13 PM;
    Auto_Receipt_No=12; transanction_Id=123567; reference_id=1234567;
    Fees_Category_description=Registration; transactionStatus=success;
    YearCode=2020-21; Payment_Type=UPI; Payeer_Id=empty; Status=Success; }; }*/

