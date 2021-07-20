package com.leadcampusapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {

    Spinner sp_feedback;
    EditText et_feedback,et_suggestion;
    Button btn_submit;
    private HashMap<String,Integer> hashProjTypeId = new HashMap<String,Integer>();
    ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private SharedPreferences shardpref_S_obj;
    private String str_leadId,str_RegistrationId,str_academicCode;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_SLeadID = "prefid_sleadid";
    public static final String PrefId_S_AcademicId = "prefid_sacademicid";

    SharedPreferences shardprefPM_obj;
    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book

    public static final String PrefID_PMID = "prefid_pmid"; //
    private String str_PMId;

    String str_feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sp_feedback = (Spinner) findViewById(R.id.feedback_sp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        et_feedback = (EditText) findViewById(R.id.et_feedback);
        et_suggestion  = (EditText) findViewById(R.id.et_suggestion);
        btn_submit = (Button) findViewById(R.id.submit);
        // Spinner click listener
       // sp_feedback.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        GetFeedbackList getFeedbackList = new GetFeedbackList(this);
        getFeedbackList.execute();

        shardpref_S_obj=getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardprefPM_obj=getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefId_S_AcademicId, "").trim();
        str_academicCode = shardpref_S_obj.getString(PrefId_S_AcademicId, "").trim();
        Log.d("str_academicCode:",str_academicCode);

        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.d("str_leadId:",str_leadId);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_PMId = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_PMId:", str_PMId);

        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               if(checkValidation()) {
                   SubmitFeedbackAsynck submitFeedbackAsynck = new SubmitFeedbackAsynck(FeedbackActivity.this);
                   submitFeedbackAsynck.execute();
               }
            }
        });

    }

    public boolean checkValidation()
    {
        boolean validationresult = true;

        boolean b_studenname,b_alernativecell,b_emailid,b_dob,b_aadhara1,b_aadhara2,b_account1,b_account2,b_iifscode1,b_iifscode2;
        boolean b_state,b_district,b_city,b_college,b_course,b_sem,b_programid,b_mytalent;

        b_studenname=b_alernativecell=b_emailid=b_dob=b_aadhara1=b_aadhara2=b_account1=b_account2=b_iifscode1=b_iifscode2=true;
        b_state=b_district=b_city=b_college=b_course=b_sem=b_programid=b_mytalent=true;

        if (et_feedback.getText().toString().length() == 0  || et_feedback.getText().toString().trim().length() == 0) {
            et_feedback.setError("Empty not allowed");
            et_feedback.requestFocus();
            b_studenname=false;
            validationresult = false;
        }

        if (et_suggestion.getText().toString().length() == 0 ) {
            et_suggestion.setError("Empty not allowed");
            b_alernativecell=false;
            validationresult = false;
        }
        if(sp_feedback.getSelectedItem().equals("Select Feedback")||sp_feedback.getSelectedItemPosition()==0) {
            TextView errorText = (TextView) sp_feedback.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Select Feedback");
            b_emailid = false;
            validationresult = false;
        }
        return (b_studenname&&b_alernativecell&&b_emailid);
    }

    public class GetFeedbackList extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetFeedbackList (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "SuggestionFeedbackHeadsMaster";
            String SOAP_ACTION1 = "http://mis.leadcampus.org/SuggestionFeedbackHeadsMaster";
            String NAMESPACE = "http://mis.leadcampus.org/";

            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("Feedback list:",response.toString());

                    return response;

                }

                catch(OutOfMemoryError ex){
                   runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(FeedbackActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(FeedbackActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
               runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(FeedbackActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(FeedbackActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {
           /* progressBar.setVisibility(View.GONE);*/
           progressDialog.dismiss();

            if(result!=null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);

                ArrayList<String> feedbackList = new ArrayList<String>();
                feedbackList.add("Select Feedback");

                Log.d("finalResultsssss", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_Slno, S_Head_Name, S_Status;
                    Object O_Slno, O_Head_Name, O_Status;

                    int int_Slno = 0;

                    String str_Slno = null, str_Head_Name = null, str_status = null;

                    O_Slno = list.getProperty("Slno");
                    if (!O_Slno.toString().equals("anyType") && !O_Slno.toString().equals(null) && !O_Slno.toString().equals("anyType{}")) {
                        S_Slno = (SoapPrimitive) list.getProperty("Slno");
                        Log.d("Slno", S_Slno.toString());
                        str_Slno = O_Slno.toString();
                        int_Slno = Integer.valueOf(str_Slno);
                    }

                    O_Head_Name = list.getProperty("Head_Name");
                    if (!O_Head_Name.toString().equals("anyType") && !O_Head_Name.toString().equals(null) && !O_Head_Name.toString().equals("anyType{}")) {
                        S_Head_Name = (SoapPrimitive) list.getProperty("Head_Name");
                        Log.d("Head_Name", S_Head_Name.toString());
                        str_Head_Name = O_Head_Name.toString();

                        feedbackList.add(str_Head_Name);

                        hashProjTypeId.put(str_Head_Name, int_Slno);
                    }
                    setFeedbackListSpinner(feedbackList);

                }

            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private void setFeedbackListSpinner(ArrayList<String> feedbackList) {


        ArrayAdapter dataAdapter2 = new ArrayAdapter(this, R.layout.simple_spinner_items, feedbackList);

        // Drop down layout style - list view with radio button
        //dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        sp_feedback.setAdapter(dataAdapter2);
        //spin_projectType.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));

    }


    public class SubmitFeedbackAsynck extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

   /*     private ProgressBar progressBar;*/

        SubmitFeedbackAsynck (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = feedbackDetails();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
/*            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Submitting");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            /*progressBar.setVisibility(View.GONE);*/

            progressDialog.dismiss();

            if(result!=null) {
                if (result.toString().equalsIgnoreCase("success")) {
                    Toast.makeText(FeedbackActivity.this, "Feedback sent Successfully", Toast.LENGTH_LONG).show();
                  //  clearAllFields();
                    finish();
                    Intent ittEditProjToProjDtls = new Intent(FeedbackActivity.this, HomeActivity.class);
                    startActivity(ittEditProjToProjDtls);
                } else {
                    Toast.makeText(FeedbackActivity.this, result.toString(), Toast.LENGTH_LONG).show();
                }
            }else{

            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private SoapPrimitive feedbackDetails()
    {

        String METHOD_NAME = "SaveSuggestionFeedback";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/SaveSuggestionFeedback";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");

            ArrayList<vmmaterial> matModel = new ArrayList<vmmaterial>();

           /* vmmaterial material = new vmmaterial();
            material.setSlno(1);
            material.setMeterialCost(50);
            material.setMeterialName("Material 1");
            matModel.add(material);*/
            str_feedback = sp_feedback.getSelectedItem().toString();

            Integer HeadId = (Integer) hashProjTypeId.get(str_feedback);

            request.addProperty("Lead_Id",str_leadId);
            request.addProperty("ManagerId",str_PMId);
            request.addProperty("HeadId",String.valueOf(HeadId));
            request.addProperty("Feedback", et_feedback.getText().toString());
            request.addProperty("Suggestion", et_suggestion.getText().toString());
            request.addProperty("AcademicCode",str_academicCode);


            Log.d("Requestisss",request.toString());


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            ///envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                // Log.d("soap response1xxxx",envelope.getResponse().toString());


                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("soap responseyyyyyyy",response.toString());

                return response;

                //return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(FeedbackActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(FeedbackActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }catch(OutOfMemoryError ex){
           runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(FeedbackActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            t.printStackTrace();
            final String exceptionStr = t.getMessage().toString();
         runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(FeedbackActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action_editProfile = menu.findItem(R.id.action_editProfile);
        action_editProfile.setVisible(false);
        MenuItem action_logout = menu.findItem(R.id.action_logout);
        action_logout.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


      /*  if (id == R.id.action_editProfile) {
            Intent ittProjDtlsToEditProfile = new Intent(FeedbackActivity.this ,EditProfileActivity.class);
            startActivity(ittProjDtlsToEditProfile);
            return true;
        }

        if (id == R.id.action_logout) {

            Intent ittProjDtlsToLogin = new Intent(FeedbackActivity.this ,LoginActivity.class);
            startActivity(ittProjDtlsToLogin);
            return true;
        }
*/
        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(FeedbackActivity.this ,HomeActivity.class);
            startActivity(ittProjDtlsToHome);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
