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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.covid.R;

public class Activity_confirmoldpin extends AppCompatActivity {


    Button verfiy_oldpin_bt;
    TextView forgotpin_tv;
    EditText otp1_et,otp2_et,otp3_et,otp4_et;

    public static final String sharedpreference_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_userid = "KeyValue_userid";
    public static final String KeyValue_username = "KeyValue_username";
    public static final String KeyValue_user_mailid = "KeyValue_user_mailid";
    public static final String KeyValue_usercategory = "KeyValue_usercategory";
    public static final String KeyValue_usercellno = "KeyValue_usercellno";
    public static final String KeyValue_isuser_setpin = "KeyValue_isuser_setpin";
    public static final String KeyValue_isuser_changepin = "KeyValue_isuser_changepin";
    public static final String KeyValue_loggedfromgoogle = "KeyValue_loggedfromgoogle";


    SharedPreferences sharedpreference_usercredential_Obj;
    SharedPreferences.Editor editor_obj;

    public static final String sharedpreference_setpincredential = "sharedpreference_pincredential";
    public static final String KeyValue_setpin = "KeyValue_setpin";
    SharedPreferences sharedpreference_setpin_Obj;
    String str_username,str_userID;


    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    String str_confirmoldpin,str_isuser_loggedfromgoogle;
    EditText pin1masked_et,pin2masked_et,pin3masked_et,pin4masked_et;

    public static final String PREFBook_PM = "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_pm_username = "prefid_pm_username"; //

    SharedPreferences shardprefPM_obj;

    SharedPreferences shardpref_S_obj;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefId_S_Username = "prefid_susername";
    public static final String PrefID_Role = "prefid_role";

    String str_role;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmoldpin);

        verfiy_oldpin_bt =(Button) findViewById(R.id.verfiy_oldpin_bt);
        forgotpin_tv=(TextView)findViewById(R.id.forgotpin_tv);

        otp1_et=(EditText) findViewById(R.id.otp1_et);
        otp2_et=(EditText) findViewById(R.id.otp2_et);
        otp3_et=(EditText) findViewById(R.id.otp3_et);
        otp4_et=(EditText) findViewById(R.id.otp4_et);

        pin1masked_et=(EditText)findViewById(R.id.pin1masked_et);
        pin2masked_et=(EditText)findViewById(R.id.pin2masked_et);
        pin3masked_et=(EditText)findViewById(R.id.pin3masked_et);
        pin4masked_et=(EditText)findViewById(R.id.pin4masked_et);

        otp1_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        otp2_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        otp3_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        otp4_et.setInputType(InputType.TYPE_CLASS_NUMBER);

        sharedpreference_setpin_Obj=getSharedPreferences(sharedpreference_setpincredential, Context.MODE_PRIVATE);
       // str_setpin = sharedpreference_setpin_Obj.getString(KeyValue_setpin, "").trim();

       /* sharedpreference_usercredential_Obj=getSharedPreferences(sharedpreference_usercredential, Context.MODE_PRIVATE);
        str_username= sharedpreference_usercredential_Obj.getString(KeyValue_username, "").trim();
        str_isuser_loggedfromgoogle=sharedpreference_usercredential_Obj.getString(KeyValue_loggedfromgoogle, "").trim();


        sharedpreference_usercredential_Obj=getSharedPreferences(sharedpreference_usercredential, Context.MODE_PRIVATE);
        str_userID= sharedpreference_usercredential_Obj.getString(KeyValue_userid, "").trim();
        Log.e("confirmpinuserid",str_userID);*/
        shardpref_S_obj= getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);

        shardpref_S_obj.getString(PrefID_Role, "").trim();
        str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();
        Log.e("str_role:", str_role);

        if(str_role.equalsIgnoreCase("Student")) {

            //otp4_et
            shardpref_S_obj = getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);

            shardpref_S_obj.getString(PrefId_S_Username, "").trim();
            str_username = shardpref_S_obj.getString(PrefId_S_Username, "").trim();
            Log.d("str_username", str_username);
        }else {
            shardprefPM_obj = getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
            str_username = shardprefPM_obj.getString(PrefID_pm_username, "").trim();
            Log.e("str_pm_username:", str_username);

        }

        otp1_et.requestFocus();

        otp1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if(otp1_et.getText().toString().length()>=1)
                {
                    pin1masked_et.setVisibility(View.VISIBLE);
                    otp1_et.setVisibility(View.GONE);
                    pin1masked_et.setText("*");

                    otp2_et.requestFocus();

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        pin1masked_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(pin1masked_et.getText().toString().length()==0)
                {
                    pin1masked_et.setVisibility(View.GONE);
                    otp1_et.setVisibility(View.VISIBLE);
                    otp1_et.setText("");
                    otp1_et.requestFocus();
                }
            }
        });



        otp2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if(otp2_et.getText().toString().length()>=1)
                {
                    pin2masked_et.setVisibility(View.VISIBLE);
                    otp2_et.setVisibility(View.GONE);
                    pin2masked_et.setText("*");
                    otp3_et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        pin2masked_et.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(pin2masked_et.getText().toString().length()==0)
                {
                    pin2masked_et.setVisibility(View.GONE);
                    otp2_et.setVisibility(View.VISIBLE);
                    otp2_et.setText("");
                    otp2_et.requestFocus();
                }
            }
        });


        otp3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if(otp3_et.getText().toString().length()>=1)
                {
                    pin3masked_et.setVisibility(View.VISIBLE);
                    otp3_et.setVisibility(View.GONE);
                    pin3masked_et.setText("*");
                    otp4_et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        pin3masked_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(pin3masked_et.getText().toString().length()==0)
                {
                    pin3masked_et.setVisibility(View.GONE);
                    otp3_et.setVisibility(View.VISIBLE);
                    otp3_et.setText("");
                    otp3_et.requestFocus();
                }
            }
        });



        otp4_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if(otp4_et.getText().toString().length()>=1)
                {

                    pin4masked_et.setVisibility(View.VISIBLE);
                    otp4_et.setVisibility(View.GONE);
                    pin4masked_et.setText("*");

                    otp4_et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });



        pin4masked_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {

                if(pin4masked_et.getText().toString().length()==0)
                {
                    pin4masked_et.setVisibility(View.GONE);
                    otp4_et.setVisibility(View.VISIBLE);
                    otp4_et.setText("");
                    otp4_et.requestFocus();
                }
            }
        });



        verfiy_oldpin_bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(validation()) {

                    str_confirmoldpin = otp1_et.getText().toString() +
                            otp2_et.getText().toString() +
                            otp3_et.getText().toString() +
                            otp4_et.getText().toString();

                    if (2>1)
                    {

                        internetDectector = new Class_InternetDectector(getApplicationContext());
                        isInternetPresent = internetDectector.isConnectingToInternet();
                        if (isInternetPresent) {

                            UpdatePinPassword updatePinPassword=new UpdatePinPassword(Activity_confirmoldpin.this);
                            updatePinPassword.execute();
                          //  AsyncTask_ValidateUserPIN();
                        }


                }   else{
                        Toast.makeText(getApplicationContext(),"PIN doesn't match", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });




        forgotpin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_confirmoldpin.this);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.forgotpin);
                dialog.setMessage("You need to relogin to the application for set new PIN.\n" +
                        "Do you want to proceed");

                dialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        internetDectector = new Class_InternetDectector(getApplicationContext());
                        isInternetPresent = internetDectector.isConnectingToInternet();
                        if (isInternetPresent) {
                            editor_obj = sharedpreference_usercredential_Obj.edit();
                            editor_obj.putString(KeyValue_isuser_setpin, "");
                            editor_obj.commit();

                            editor_obj = sharedpreference_usercredential_Obj.edit();
                            editor_obj.putString(KeyValue_isuser_changepin, "");
                            editor_obj.commit();


                            if(str_isuser_loggedfromgoogle.isEmpty())
                            {

                            }else{
                                editor_obj = sharedpreference_usercredential_Obj.edit();
                                editor_obj.putString(KeyValue_loggedfromgoogle, "yes");
                                editor_obj.commit();
                            }


                            Intent i = new Intent(Activity_confirmoldpin.this, MainActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else
                        {
                            Toast.makeText(Activity_confirmoldpin.this, "Kindly connect to internet", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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

            }
        });




    }//On create()




    public boolean validation()
    {
        boolean b_otp1, b_otp2, b_otp3, b_otp4;
        b_otp1=b_otp2=b_otp3=b_otp4=true;



        if (otp1_et.getText().toString().trim().length() == 0) {
            otp1_et.setError("Enter PIN");
            otp1_et.requestFocus();
            b_otp1 = false;
        }

        if (otp2_et.getText().toString().trim().length() == 0) {
            otp2_et.setError("Enter PIN");
            otp2_et.requestFocus();
            b_otp2 = false;
        }

        if (otp3_et.getText().toString().trim().length() == 0) {
            otp3_et.setError("Enter PIN");
            otp3_et.requestFocus();
            b_otp3 = false;
        }
        if(otp4_et.getText().toString().trim().length()==0)
        {
            otp4_et.setError("Enter PIN");
            otp4_et.requestFocus();
            b_otp4=false;
        }

        return (b_otp1 && b_otp2 && b_otp3&& b_otp4);
    }



    @Override
    public void onBackPressed()
    {

        /*AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_confirmoldpin.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.alert);
        dialog.setMessage("Are you sure want to go back");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

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
        alert.show();*/
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }






    public class UpdatePinPassword extends AsyncTask<String, Void, SoapObject> {

        Context context;
        //AlertDialog alertDialog;

        //private ProgressBar progressBar;

        private ProgressDialog progressDialog;

        UpdatePinPassword(Context ctx) {
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(String... params) {

            SoapObject response = UpdatePinPassword();

            //Log.d("ResponseCommitsss", response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {


            String str_result=result.getProperty("Status").toString();

            if(str_result.equals("Success")){
                //    dialog.dismiss();
                //  Toast.makeText(context,"Your Request sent to the manager",Toast.LENGTH_LONG).show();
                Intent i = new Intent(Activity_confirmoldpin.this, Activity_setpin.class);
                i.putExtra("Key_confirmoldpin", "yes");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }else{

                Toast toast = Toast.makeText(getApplicationContext(), "Wrong PIN", Toast.LENGTH_LONG);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }

            progressDialog.dismiss();
        }

    }


    private SoapObject UpdatePinPassword()
    {

        String METHOD_NAME = "ValidateLogin_PIN";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ValidateLogin_PIN";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            request.addProperty("Username", str_username);//<Lead_Id>string</Lead_Id>
            //request.addProperty("StudentName",edt_studName.getText().toString());
            request.addProperty("PIN",str_confirmoldpin);//<Email_id>string</Email_id>


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            //  SendStudentRequestforManager{Lead_Id=MF00993; Email_id=testing; Student_MobileNo=9689240475; Message=testing; }

            Log.d("tag","Request UpdatePinPassword="+request.toString());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                // Log.d("soapResponseyyyyyyy",envelope.getResponse().toString());

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.d("tag","soapRespons UpdatePinPassword="+response.toString());

                return response;

            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
            }
        }catch (Exception t) {
            Log.d("exception outside",t.getMessage().toString());
        }
        return null;


    }

   /* public void AsyncTask_ValidateUserPIN()
    {

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(com.dfcovid.Activity_confirmoldpin.this);
        progressDialog.setMessage("Loading....");
        progressDialog.setTitle("Please wait fetching Details....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();



        Class_loginPinrequest request = new Class_loginPinrequest();
        request.setUsername(str_username);
        request.setPIN(str_confirmoldpin);


        Interface_userservice userService;
        userService = Class_ApiUtils.getUserService();
        Call<Class_normalloginresponse> call = userService.Post_ValidateUserPIN(request);

        Log.e("TAG", "pinreq : " + new Gson().toJson(request));

        call.enqueue(new Callback<Class_normalloginresponse>() {
            @Override
            public void onResponse(Call<Class_normalloginresponse> call, Response<Class_normalloginresponse> response)
            {
                Log.e("response", response.toString());

                Log.e("TAG", "pinRes: " + new Gson().toJson(response));
                Log.e("tag","PinResponse body"+ String.valueOf(response.body()));
                //   DefaultResponse error1 = ErrorUtils.parseError(response);
               *//* Log.e("response new:",error1.getMsg());
                Log.e("response new status:", String.valueOf(error1.getstatus()));*//*

                Class_normalloginresponse user_object;
                user_object = (Class_normalloginresponse) response.body();

                if (response.isSuccessful())
                {

                    progressDialog.dismiss();

                    String str_userstatus=user_object.getMessage().trim().toString();
                    if(str_userstatus.equalsIgnoreCase("Success"))
                    {

                        Intent i = new Intent(com.dfcovid.Activity_confirmoldpin.this, com.dfcovid.Activity_setpin.class);
                        i.putExtra("Key_confirmoldpin", "yes");
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();


                    }
                    else{

                        Toast toast = Toast.makeText(getApplicationContext(), "Wrong PIN", Toast.LENGTH_LONG);
                        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                        toastMessage.setTextColor(Color.RED);
                        toast.show();
                    }



                } else {


                    DefaultResponse error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("responseerror", error.getMsg());

                    Toast.makeText(com.dfcovid.Activity_confirmoldpin.this, "Wrong PIN", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t)
            {

                Log.d("retrofiteerror", t.toString());
                Toast.makeText(com.dfcovid.Activity_confirmoldpin.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });// end of call

    }*/





}