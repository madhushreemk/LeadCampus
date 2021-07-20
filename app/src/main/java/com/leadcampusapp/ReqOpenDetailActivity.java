package com.leadcampusapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReqOpenDetailActivity extends AppCompatActivity {

    TextView ticketNo_TV,lead_id_TV,StudName_TV,MobNo_TV,MailId_TV,CollgName_TV,ReqDate_TV,ReqType_TV,projTitle_TV;
    EditText req_msg_ET,Res_msg_ET;
    CheckBox generate_doc_cb;
    String str_ticketId,str_leadId,str_reqMsg,str_studName,str_collgName,str_projectName,str_mailId,str_reqDate,str_reqType,str_manager_mailID,str_reqHeadId,str_mobNo;
    static TextView clickfromdate_tv, clicktodate_tv;
    Button close_ticket_btn;
    LinearLayout proj_letter_lv1,proj_letter_lv2,proj_letter_lv3,proj_letter_lv4;

    static String yyyyMMdd_fromdate = "";
    static String yyyyMMdd_todate = "";

    Context context;

    public static final String PrefID_PMID = "prefid_pmid"; //
    public static final String  PREFBook_PM= "prefbook_pm";
    SharedPreferences shardprefPM_obj;
    public String str_MangerID;

    boolean generate_doc=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_req_open_detail);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //  getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));


        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Request Module");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ticketNo_TV = (TextView) findViewById(R.id.ticketNo_TV);
        lead_id_TV = (TextView) findViewById(R.id.lead_id_TV);
        StudName_TV = (TextView) findViewById(R.id.StudName_TV);
        MobNo_TV = (TextView) findViewById(R.id.MobNo_TV);
        MailId_TV = (TextView) findViewById(R.id.MailId_TV);
        CollgName_TV  = (TextView) findViewById(R.id.CollgName_TV);
        ReqDate_TV = (TextView) findViewById(R.id.ReqDate_TV);
        ReqType_TV = (TextView) findViewById(R.id.ReqType_TV);
        projTitle_TV = (TextView) findViewById(R.id.projTitle_TV);

        req_msg_ET = (EditText) findViewById(R.id.req_msg_ET);
        Res_msg_ET = (EditText)findViewById(R.id.Res_msg_ET);

        clickfromdate_tv=(TextView) findViewById(R.id.clickfromdate_TV);
        clicktodate_tv=(TextView) findViewById(R.id.clicktodate_TV);

        generate_doc_cb = (CheckBox) findViewById(R.id.generate_doc_cb);

        close_ticket_btn = (Button) findViewById(R.id.close_ticket_btn);

        proj_letter_lv1 = (LinearLayout) findViewById(R.id.proj_letter_lv1);
        proj_letter_lv2 = (LinearLayout) findViewById(R.id.proj_letter_lv2);
        proj_letter_lv3 = (LinearLayout) findViewById(R.id.proj_letter_lv3);
        proj_letter_lv4 = (LinearLayout) findViewById(R.id.proj_letter_lv4);


        shardprefPM_obj= getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();

        Log.d("str_MangerID:",str_MangerID);

        Intent intent = getIntent();
        str_ticketId = intent.getStringExtra("str_ticketId");
        str_leadId = intent.getStringExtra("str_leadId");
        str_reqMsg = intent.getStringExtra("str_reqMsg");
        str_studName =intent.getStringExtra("str_studName");
        str_collgName =intent.getStringExtra("str_collgName");
        str_projectName =intent.getStringExtra("str_projectName");
        str_mailId =intent.getStringExtra("str_mailId");
        str_manager_mailID =intent.getStringExtra("str_manager_mailID");
        str_reqHeadId =intent.getStringExtra("str_reqHeadId");
        str_mobNo=intent.getStringExtra("str_mobNo");
        str_reqDate=intent.getStringExtra("str_reqDate");
        str_reqType=intent.getStringExtra("str_reqType");

        ticketNo_TV.setText(str_ticketId);
        lead_id_TV.setText(str_leadId);
        req_msg_ET.setText(str_reqMsg);
        StudName_TV.setText(str_studName);
        MobNo_TV.setText(str_mobNo);
        MailId_TV.setText(str_mailId);
        CollgName_TV.setText(str_collgName);
        projTitle_TV.setText(str_projectName);
        ReqDate_TV.setText(str_reqDate);
        ReqType_TV.setText(str_reqType);

        req_msg_ET.setEnabled(false);
        req_msg_ET.setFocusable(false);
       // Res_msg_ET.setFocusable(false);

        if(str_reqHeadId.equals("1")){
            proj_letter_lv1.setVisibility(View.VISIBLE);
            proj_letter_lv2.setVisibility(View.VISIBLE);
            if(generate_doc_cb.isChecked())
            {
                // true,do the task
                generate_doc=true;
                proj_letter_lv3.setVisibility(View.VISIBLE);
                proj_letter_lv4.setVisibility(View.VISIBLE);

            }else{
                generate_doc=false;
                proj_letter_lv3.setVisibility(View.GONE);
                proj_letter_lv4.setVisibility(View.GONE);
            }
           // proj_letter_lv3.setVisibility(View.VISIBLE);
            //proj_letter_lv4.setVisibility(View.VISIBLE);

        }

        clickfromdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment fromdateFragment = new DatePickerFragmentFromDate();
                fromdateFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        clicktodate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // settodate();
                DialogFragment dFragment = new DatePickerFragment();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");


            }
        });

        generate_doc_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkbox_clicked(v);
            }

        });

        close_ticket_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(checkValidation()) {
                    SubmitReqData submitReqData = new SubmitReqData(ReqOpenDetailActivity.this);
                    submitReqData.execute();
                }
            }
        });
    }

    public void checkbox_clicked(View v)
    {

        if(generate_doc_cb.isChecked())
        {
            // true,do the task
            generate_doc=true;
            proj_letter_lv3.setVisibility(View.VISIBLE);
            proj_letter_lv4.setVisibility(View.VISIBLE);

        }
        else
        {
            generate_doc=false;
            proj_letter_lv3.setVisibility(View.GONE);
            proj_letter_lv4.setVisibility(View.GONE);
        }

    }

    public class SubmitReqData extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        SubmitReqData (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = SubmitReqData();

            //   SoapObject response = ReApplyProjectDetails();
            Log.d("tag","Soap response="+response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result!=null) {
                if (result.toString().equals("Error")) {
                    Toast.makeText(getApplicationContext(), "Error occured while saving to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "successfully closed ticket", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(ReqOpenDetailActivity.this, PMRequestActivity.class);
                    startActivity(intent);
                }
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private SoapPrimitive SubmitReqData() {
        String METHOD_NAME = "Update_Manager_CloseTicket";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Update_Manager_CloseTicket";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            // long projectIds = (long) Integer.valueOf(projectId);

            Log.e("tag","from date:"+clickfromdate_tv.getText().toString());
            Log.e("tag","To date:"+clicktodate_tv.getText().toString());

            request.addProperty("Ticket_Id",str_ticketId);
            request.addProperty("Lead_Id",str_leadId);
            request.addProperty("Request_Message",str_reqMsg);
            request.addProperty("Response_Message",Res_msg_ET.getText().toString());
            request.addProperty("ManagerId",str_MangerID);
            request.addProperty("Head_Id",str_reqHeadId);
            request.addProperty("IsDocCreate",generate_doc);
            request.addProperty("ValidFromDate",yyyyMMdd_fromdate);
            request.addProperty("ValidToDate",yyyyMMdd_todate);
            request.addProperty("StudentName",str_studName);
            request.addProperty("CollegeName",str_collgName);
            request.addProperty("MailId",str_mailId);
            request.addProperty("ProjectTitle",str_projectName);
            request.addProperty("Manager_Mailid",str_manager_mailID);
            Log.d("Requestisxxxxx",request.toString());

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
                // Log.d("soap response1xxxx",envelope.getResponse().toString());

                //  SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("soap response: ",envelope.getResponse().toString());

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

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            /*return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);*/


            DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme,
                    this, year, month, day);


            return dialog;


        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar cal = new GregorianCalendar(year, month, day);
            setDate(cal);
        }

        public void setDate(final Calendar calendar) {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            //((TextView) findViewById(R.id.showDate)).setText(dateFormat.format(calendar.getTime()));
            clicktodate_tv.setText(dateFormat.format(calendar.getTime()));

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

            yyyyMMdd_todate = mdyFormat.format(calendar.getTime());

            // System.out.println("To Date:"+ yyyyMMdd_todate);



        }

    }


    public static class DatePickerFragmentFromDate extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            /*return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);*/


            DatePickerDialog dialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme,
                    this, year, month, day);


       /* dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
*/

       /* dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        c.add(Calendar.DAY_OF_MONTH,150);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
*/
            return dialog;


        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar cal = new GregorianCalendar(year, month, day);
            setDate(cal);
        }

        public void setDate(final Calendar calendar) {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            //((TextView) findViewById(R.id.showDate)).setText(dateFormat.format(calendar.getTime()));


            clickfromdate_tv.setText(dateFormat.format(calendar.getTime()));

            // SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");//2017-06-22

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
            yyyyMMdd_fromdate = mdyFormat.format(calendar.getTime());

            Calendar c = Calendar.getInstance();
            DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy");

            clicktodate_tv.setText("Click for Calendar");
            // paternityerror_tv.setVisibility(View.GONE);


            // System.out.println("From date:"+ yyyyMMdd_fromdate);
        }

    }

    public boolean checkValidation() {
        Log.e("tag","clickfromdate_tv ="+clickfromdate_tv.getText().toString());
        Log.e("tag","clicktodate_tv ="+clicktodate_tv.getText().toString());

        boolean validationresult = true;
        boolean b_resMes, datevalidationresult1, datevalidationresult2, datevalidationresult3, datevalidationresult4, b_account1, b_account2, b_iifscode1, b_iifscode2;
        boolean b_state, b_district, b_city, b_college, b_course, b_sem, b_programid, b_mytalent;

        b_resMes = datevalidationresult1 = datevalidationresult2 = datevalidationresult3 = datevalidationresult4 = true;
        b_state = b_district = b_city = b_college = b_course = b_sem = b_programid = b_mytalent = true;

        if (Res_msg_ET.getText().toString().length() == 0 || Res_msg_ET.getText().toString().trim().length() == 0) {
            Res_msg_ET.setError("Empty not allowed");
            Res_msg_ET.requestFocus();
            b_resMes = false;
        }

      //  if (str_reqType.equals("Project Approval Letter")) {
        if( generate_doc==true){
            if (clickfromdate_tv.getText().toString().length() == 0 || clicktodate_tv.toString().length() == 0
                    || clickfromdate_tv.getText().toString().equalsIgnoreCase("0000-00-00")
                    || clicktodate_tv.getText().toString().equalsIgnoreCase("0000-00-00")) {
                Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
                datevalidationresult1 = false;
            }

        if (yyyyMMdd_todate.toString().length() == 0 || yyyyMMdd_todate.equalsIgnoreCase("0000-00-00")) {
            clicktodate_tv.setError("Kindly enter the date");
            clicktodate_tv.requestFocus();
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            datevalidationresult3 = false;
        }

        if (yyyyMMdd_fromdate.toString().length() == 0 || yyyyMMdd_fromdate.equalsIgnoreCase("0000-00-00")) {
            clickfromdate_tv.setError("Kindly enter the date");
            clickfromdate_tv.requestFocus();
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            datevalidationresult4 = false;

        }


        if ((yyyyMMdd_fromdate.toString().length() != 0) && (yyyyMMdd_todate.toString().length() != 0)) {
        /*if(date1.compareTo(date2)<0){ //0 comes when two date are same,
            //1 comes when date1 is higher then date2
            //-1 comes when date1 is lower then date2 }*/

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

            try {
                Date fromdate = mdyFormat.parse(yyyyMMdd_fromdate);
                Date todate = mdyFormat.parse(yyyyMMdd_todate);

                if (fromdate.compareTo(todate) <= 0) {
                    datevalidationresult2 = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Kindly enter valid date", Toast.LENGTH_SHORT).show();
                    datevalidationresult2 = false;
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }//end of if

    }
        return (b_resMes&&datevalidationresult1&&datevalidationresult2&&datevalidationresult3&&datevalidationresult4);
    }

    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this,PMRequestActivity.class);
        startActivity(i);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action_editProfile = menu.findItem(R.id.action_editProfile);
        action_editProfile.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


       /* if (id == R.id.action_editProfile) {
            Intent itthomeToEditProfile = new Intent(PMComplitionProjectActivity.this ,PMEditProfileActivity.class);
            startActivity(itthomeToEditProfile);
            return true;
        }*/


        if (id == R.id.action_logout) {
            Intent itthomeToLogin = new Intent(ReqOpenDetailActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(ReqOpenDetailActivity.this ,PMRequestActivity.class);
            startActivity(ittProjDtlsToHome);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
