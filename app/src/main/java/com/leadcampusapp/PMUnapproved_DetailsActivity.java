package com.leadcampusapp;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Set;


public class PMUnapproved_DetailsActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    private static final String TAG = "Tag";

    String material[]={"Books","Digital camera","Pen","Mouse","xyz"};
    String cost[]={"100","200","300","400"};
    String LeadId,ProjectId;
    Integer Lead_Id,Project_Id;
   // private AppCompatSpinner spin_projtype;

    private ArrayList<String> lstMaterialName = new ArrayList<String>();
    private ArrayList<String> lstMaterialCost = new ArrayList<String>();

    private ArrayList<String> lstMemberName = new ArrayList<String>();
    private ArrayList<String> lstMemberEmail = new ArrayList<>();

    private String projectId = null;
    private String str_totalCost = null;
    private HashMap<String,Integer> hashProjTypeId = new HashMap<String,Integer>();

    private int flag=1;

    Context context;
    AppCompatSpinner spin_projectType;
    EditText edt_projectType;
    EditText edt_title;
    EditText edt_noOfBeneficiaries,edt_whoareBeneficiaries;
    EditText edt_objectives;
    EditText edt_actionPlan;
    EditText edt_pmcomment;
    EditText edt_amount,edt_resources,edt_CurrentSituation;
    EditText edt_placeofImp;
    TextView txt_PMComments;
    TextView txt_TotalCost,txt_mobileno;
    ImageView btn_save,btn_approve,btn_reapply,btn_cancle,btn_reject,img_student;
    Integer ManagerId;
    String str_ProjectTitle = null,str_ThemeName = null,str_whoareBeneficiaries=null,str_BeneficiaryNo = null,str_Objective = null,str_actionPlan = null,str_imageUrl=null,str_MobileNo=null,str_ManagerComments=null,str_materialName=null,str_memberName=null,str_materialCost=null,str_memberEmail=null,str_placeofImp=null,str_CurrentSituation=null;
   String str_collegename="", str_streamname="";
    String str_Resource=null;
    String PMImgUrl;
    private String serverPath = Class_URL.ServerPath.toString().trim();





    static TextView clickstart_pmunapproved_editdate_tv;
    static TextView clickend_pmunapproved_editdate_tv;
    static String yyyyMMdd_pmstartdate = "";
    static String yyyyMMdd_pmenddate = "";

    private static TextView pm_numberofdays_tv;
    LinearLayout pm_datelabel_linerlayout,pm_date_linerlayout;

     static LinearLayout pm_days_linearlayout;

     TextView pmunapproved_streamname_tv,pmunapproved_collegename_tv;
     CheckBox pmunapprov_impactproject_cb;
    public Button pm_chat_txt;
     int int_impactproject=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pmactivity_unapproved__details);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Pending Approval");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String Name = intent.getStringExtra("name");
        LeadId = intent.getStringExtra("lead_id");
        ProjectId= intent.getStringExtra("Project_id");
        str_MobileNo=intent.getStringExtra("MobileNo");
        str_collegename=intent.getStringExtra("collegename");
        str_streamname=intent.getStringExtra("streamname");
        Log.e(TAG,"name="+Name);
        Log.e(TAG,"lead_id="+LeadId);
        Log.e(TAG,"Project_id="+ProjectId);
       Log.e(TAG,"streamname="+intent.getStringExtra("streamname"));

      /*  ManagerId = getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);
*/
        //   Lead_Id=Integer.parseInt(LeadId);
        Project_Id=Integer.parseInt(ProjectId);
        //spin_projtype = (AppCompatSpinner) findViewById(R.id.spin_projectType);

        pmunapproved_collegename_tv=(TextView)findViewById(R.id.pmunapproved_collegename_tv);
        pmunapproved_streamname_tv=(TextView)findViewById(R.id.pmunapproved_streamname_tv);
        spin_projectType = (AppCompatSpinner) findViewById(R.id.spin_projectType);
        //   edt_projectType = (EditText) findViewById(R.id.edt_projectType);
        edt_noOfBeneficiaries = (EditText) findViewById(R.id.edt_noOfBeneficiaries);
        edt_objectives = (EditText) findViewById(R.id.edt_objective);
        edt_actionPlan = (EditText) findViewById(R.id.edt_actionplan);
        edt_title = (EditText) findViewById(R.id.edt_title);
        edt_whoareBeneficiaries =(EditText) findViewById(R.id.edt_whoareBeneficiaries);
      //  txt_TotalCost=(TextView) findViewById(R.id.totalCost);
        edt_pmcomment=(EditText) findViewById(R.id.edt_pmcomment);
        edt_amount=(EditText) findViewById(R.id.edt_amount);
        btn_save=(ImageView) findViewById(R.id.btn_save);
        btn_approve=(ImageView) findViewById(R.id.btn_approval);
        btn_reapply=(ImageView)findViewById(R.id.btn_reapply);
        btn_cancle=(ImageView)findViewById(R.id.btn_cancle);
        btn_reject=(ImageView) findViewById(R.id.btn_reject);
        img_student=(ImageView) findViewById(R.id.img_student);
        edt_placeofImp=(EditText) findViewById(R.id.edt_placeOfImpl);
        edt_CurrentSituation=(EditText) findViewById(R.id.edt_CurrentSituation);
        clickstart_pmunapproved_editdate_tv = (TextView) findViewById(R.id.clickstart_pmunapproved_editdate_TV);
        clickend_pmunapproved_editdate_tv = (TextView) findViewById(R.id.clickend_pmunapproved_editdate_TV);


        pm_numberofdays_tv=(TextView) findViewById(R.id.pm_numberofdays_tv);
        pm_datelabel_linerlayout=(LinearLayout) findViewById(R.id.pm_datelabel_linerlayout);
        pm_date_linerlayout=(LinearLayout) findViewById(R.id.pm_date_linerlayout);
        pm_days_linearlayout=(LinearLayout) findViewById(R.id.pm_days_linearlayout);
        pmunapprov_impactproject_cb=(CheckBox)findViewById(R.id.pmunapprov_impactproject_cb);

        pm_chat_txt=(Button) findViewById(R.id.pm_chat_txt);


        //   edt_resources=(EditText) findViewById(R.id.edt_resources);

        txt_mobileno =(TextView) findViewById(R.id.txt_mobileno);
        TextView name_tv= (TextView) findViewById(R.id.txt_name);
        name_tv.setText(Name);

        TextView leadId_tv=(TextView) findViewById(R.id.txt_lead_id);
        leadId_tv.setText(LeadId);
        txt_mobileno.setText(str_MobileNo);

        txt_mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_mobileno.getText().toString()));
                startActivity(intent);
            }
        });
        //setProjectTypeSpinner();

        pmunapproved_collegename_tv.setText(str_collegename);
        pmunapproved_streamname_tv.setText(str_streamname);




        getProjectType();

        btn_save.setVisibility(View.GONE);
        btn_cancle.setVisibility(View.GONE);
       // btn_cancle.setEnabled(false);

        Log.i("tag","edt_title.hasFocus()=="+edt_title.hasFocus());
        Log.i("tag","edt_title.hasFocusable()=="+edt_title.hasFocusable());

   /*     edt_title.addTextChangedListener(loginTextWatcher);
        edt_objectives.addTextChangedListener(loginTextWatcher);
        edt_noOfBeneficiaries.addTextChangedListener(loginTextWatcher);
        edt_actionPlan.addTextChangedListener(loginTextWatcher);
*/
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation_Save()) {
                    SubmitSaveDetails submitSaveDetails = new SubmitSaveDetails(context);
                    submitSaveDetails.execute();
                }
            }
        });
        btn_approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation_Approval()) {
                    SubmitApproveDetails submitApproveDetails = new SubmitApproveDetails(context);
                    submitApproveDetails.execute();
                }
            }
        });
        btn_reapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation_ReApply()){
                    SubmitReApplyDetails reApplyDetails = new SubmitReApplyDetails(context);
                    reApplyDetails.execute();
                }
            }
        });
        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation_ReApply()) {
                    SubmitCancelDetails submitCancelDetails = new SubmitCancelDetails(context);
                    submitCancelDetails.execute();
                }
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProjectType_cancel();
                btn_save.setVisibility(View.GONE);
                btn_cancle.setVisibility(View.GONE);
            }
        });
        pm_chat_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag","ProjectId chat=="+ProjectId);
                Intent i=new Intent(PMUnapproved_DetailsActivity.this,ChatActivity.class);
                i.putExtra("projectStatus","pending");
                i.putExtra("projectId",ProjectId);
                i.putExtra("userType","Manager");
                startActivity(i);
            }
        });

        edt_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(edt_title.getText().toString().equals(str_ProjectTitle)){

                }
                else {
                    btn_save.setVisibility(View.VISIBLE);
                    btn_cancle.setVisibility(View.VISIBLE);
                }
            }
        });

        edt_noOfBeneficiaries.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(edt_noOfBeneficiaries.getText().toString().equals(str_BeneficiaryNo)){

                }
                else {
                    btn_save.setVisibility(View.VISIBLE);
                    btn_cancle.setVisibility(View.VISIBLE);
                }
            }
        });

        edt_objectives.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(edt_objectives.getText().toString().equals(str_Objective)){

                }
                else {
                    btn_save.setVisibility(View.VISIBLE);
                    btn_cancle.setVisibility(View.VISIBLE);
                }
            }
        });

        edt_actionPlan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(edt_actionPlan.getText().toString().equals(str_actionPlan)){

                }
                else {
                    btn_save.setVisibility(View.VISIBLE);
                    btn_cancle.setVisibility(View.VISIBLE);
                }
            }
        });

        edt_whoareBeneficiaries.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(edt_whoareBeneficiaries.getText().toString().equals(str_whoareBeneficiaries)){

                }
                else {
                    btn_save.setVisibility(View.VISIBLE);
                    btn_cancle.setVisibility(View.VISIBLE);
                }
            }
        });

        edt_placeofImp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(edt_placeofImp.getText().toString().equals(str_placeofImp)){

                }
                else {
                    btn_save.setVisibility(View.VISIBLE);
                    btn_cancle.setVisibility(View.VISIBLE);
                }
            }
        });

        //-----------------added by madhu 17/9/18
       edt_CurrentSituation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(edt_CurrentSituation.getText().toString().equals(str_CurrentSituation)){

                }
                else {
                    btn_save.setVisibility(View.VISIBLE);
                    btn_cancle.setVisibility(View.VISIBLE);
                }
            }
        });

        Log.i("tag","pin_projectType.getSelectedItem()="+spin_projectType.getSelectedItem());
        spin_projectType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                String msupplier=spin_projectType.getSelectedItem().toString();
                Log.e("Selected item : ",msupplier+"str_ThemeName="+str_ThemeName);
                if(spin_projectType.getSelectedItem().equals(str_ThemeName)){
                    Log.e("str_ThemeName : ",str_ThemeName);
                }else{
                    btn_save.setVisibility(View.VISIBLE);
                    btn_cancle.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
    /*    if (spin_projectType.getSelectedItem().equals(str_ThemeName)) {
            Log.i("tag","str_ThemeName equal="+str_ThemeName);
        }else{
            Log.i("tag","str_ThemeName not equal="+str_ThemeName);
        }*/





        clickstart_pmunapproved_editdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_save.setVisibility(View.VISIBLE);
                btn_cancle.setVisibility(View.VISIBLE);
                DialogFragment fromdateFragment = new DatePickerFragmentFromDate();
                fromdateFragment.show(getFragmentManager(), "Date Picker");
            }
        });


        clickend_pmunapproved_editdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_save.setVisibility(View.VISIBLE);
                btn_cancle.setVisibility(View.VISIBLE);
                // settodate();
                DialogFragment dFragment = new DatePickerFragmentEndDate();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });




        pmunapprov_impactproject_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

               if(pmunapprov_impactproject_cb.isChecked())
               {
                   int_impactproject=1;
                   //Toast.makeText(getApplicationContext(),"Yes",Toast.LENGTH_SHORT).show();
               }
               else{
                   int_impactproject=0;
                  // Toast.makeText(getApplicationContext(),"No",Toast.LENGTH_SHORT).show();
               }
            }
        });






    }// end of Oncreate()


    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {

        if (!isConnected) {

            AlertDialog.Builder adb = new AlertDialog.Builder(PMUnapproved_DetailsActivity.this);
            //adb.setView(alertDialogView);

            adb.setTitle("Sorry! Not connected to the internet");

            adb.setIcon(android.R.drawable.ic_dialog_alert);

            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });


            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });

            adb.setCancelable(true);
            adb.show();
        }

/*        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();*/
    }

  /*  private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
       /*     if (edt_title.getText().toString().equals(str_ProjectTitle)) {
            } else {
                btn_save.setVisibility(View.VISIBLE);
                btn_cancle.setVisibility(View.VISIBLE);
            }*/
     /*  Log.i("tag","edt_objectives.getText()=="+edt_objectives.getText().toString());
       Log.i("tag","str_Objective=="+str_Objective);

            if (edt_objectives.getText().toString().equals(str_Objective)) {

            } else {
                btn_save.setVisibility(View.VISIBLE);
                btn_cancle.setVisibility(View.VISIBLE);
            }
        }
    };*/

    private void getProjectType_cancel() {
        ProjectTypeCancel projectTypeCancel = new ProjectTypeCancel(getApplicationContext());
        projectTypeCancel.execute();
    }

    public class ProjectTypeCancel extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        private ProgressBar progressBar;

        ProjectTypeCancel (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "GetThemeList";
            String SOAP_ACTION1 = "http://mis.leadcampus.org/GetThemeList";

            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);


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
                    //Log.d("soap responseyyyyyyy",response.toString());

                    return response;

                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());
                }
            }catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            progressBar.setVisibility(View.GONE);

            if(result!=null) {

                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);

                ArrayList<String> projectTypeList = new ArrayList<String>();

                Log.d("finalResultsssss", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_ThemeId, S_ThemeName, S_Status;
                    Object O_ThemeId, O_ThemeName, O_Status;

                    int projectTypeId = 0;

                    String str_ProjectTypeId = null, str_ProjectType = null, str_status = null;

                    O_ThemeId = list.getProperty("ThemeId");
                    if (!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals("anyType{}") && !O_ThemeId.toString().equals(null)) {
                        S_ThemeId = (SoapPrimitive) list.getProperty("ThemeId");
                        Log.d("ProjectId", S_ThemeId.toString());
                        str_ProjectTypeId = O_ThemeId.toString();
                        projectTypeId = Integer.valueOf(str_ProjectTypeId);
                    }

                    O_ThemeName = list.getProperty("ThemeName");
                    if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals("anyType{}") && !O_ThemeName.toString().equals(null)) {
                        S_ThemeName = (SoapPrimitive) list.getProperty("ThemeName");
                        Log.d("ProjectType Name", S_ThemeName.toString());
                        str_ProjectType = O_ThemeName.toString();

                        projectTypeList.add(str_ProjectType);

                        hashProjTypeId.put(str_ProjectType, projectTypeId);
                    }

                }

                ViewProjectCancel viewProjectCancel = new ViewProjectCancel(context);
                viewProjectCancel.execute();
            }

            //setProjectTypeSpinner(projectTypeList);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private void getProjectType() {
        ProjectType projType = new ProjectType(getApplicationContext());
        projType.execute();
    }

    public class ViewProjectCancel extends AsyncTask<Void, Void, SoapObject> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        ViewProjectCancel (Context ctx){
            context = ctx;
        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getProjectDetails();

            //SoapPrimitive response =getProjectDetails();
            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            SoapPrimitive S_ProjectTitle, S_ThemeName, S_BeneficiaryNo, S_Placeofimplement, S_Objective, S_actionPlan, S_MobileNo, S_ManagerComments, S_MemberEmail, S_MembersName, S_MaterialName, S_MaterialCost, S_TotalCost, S_StudImage,S_BeneficiariesList,S_CurrentSituation,S_Resource;
            Object O_ProjectTitle, O_ThemeName, O_BeneficiaryNo, O_Placeofimplement, O_Objective, O_actionPlan,O_MobileNo, O_ManagerComments, O_MemberEmail, O_MembersName, O_MaterialName, O_MaterialCost, O_TotalCost, O_StudImage,O_BeneficiariesList,O_CurrentSituation,O_Resource;
            SoapObject SO_ThemeName;
            //   if(flag==0) {
            O_ProjectTitle = result.getProperty("Title");
            if (!O_ProjectTitle.toString().equals("anyType") && !O_ProjectTitle.toString().equals("anyType{}") && !O_ProjectTitle.toString().equals(null)) {
                S_ProjectTitle = (SoapPrimitive) result.getProperty("Title");
                Log.d("Titlesssssss", S_ProjectTitle.toString());
                str_ProjectTitle = S_ProjectTitle.toString();
            }

            O_ThemeName = result.getProperty("ThemeName");
            if (O_ThemeName.toString().equals("anyType") && O_ThemeName.toString().equals("anyType{}") && O_ThemeName.toString().equals(null)) {
                SO_ThemeName = (SoapObject) result.getProperty("ThemeName");
                Log.d("ThemeNamessss", SO_ThemeName.toString());
                str_ThemeName = SO_ThemeName.toString();
                //   spin_projectType.setSelection(0);
            }
            if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals("anyType{}") && !O_ThemeName.toString().equals(null)) {
                S_ThemeName = (SoapPrimitive) result.getProperty("ThemeName");
                Log.d("ThemeNamessss", S_ThemeName.toString());
                str_ThemeName = S_ThemeName.toString();
                //    spin_projectType.setSelection(0);
            }

            O_BeneficiaryNo = result.getProperty("BeneficiaryNo");
            if (!O_BeneficiaryNo.toString().equals("anyType") && !O_BeneficiaryNo.toString().equals("anyType{}") && !O_BeneficiaryNo.toString().equals(null)) {
                S_BeneficiaryNo = (SoapPrimitive) result.getProperty("BeneficiaryNo");
                Log.d("BeneficiaryNo", S_BeneficiaryNo.toString());
                str_BeneficiaryNo = S_BeneficiaryNo.toString();
            }

            O_Placeofimplement= result.getProperty("Placeofimplement");
            if (!O_Placeofimplement.toString().equals("anyType") && !O_Placeofimplement.toString().equals("anyType{}") && !O_Placeofimplement.toString().equals(null)) {
                S_Placeofimplement = (SoapPrimitive) result.getProperty("Placeofimplement");
                Log.d("Placeofimplement", S_Placeofimplement.toString());
                str_placeofImp = S_Placeofimplement.toString();
            }

            O_Objective = result.getProperty("Objectives");
            if (!O_Objective.toString().equals("anyType") && !O_Objective.toString().equals("anyType{}") && !O_Objective.toString().equals(null)) {
                S_Objective = (SoapPrimitive) result.getProperty("Objectives");
                Log.d("Objectivess", S_Objective.toString());
                str_Objective = S_Objective.toString();
            }

            O_actionPlan = result.getProperty("ActionPlan");
            if (!O_actionPlan.toString().equals("anyType") && !O_actionPlan.toString().equals("anyType{}") && !O_actionPlan.toString().equals(null)) {
                S_actionPlan = (SoapPrimitive) result.getProperty("ActionPlan");
                Log.d("ActionPlansss", S_actionPlan.toString());
                str_actionPlan = S_actionPlan.toString();
            }

            O_StudImage = result.getProperty("Student_Image_Path");
            if (!O_StudImage.toString().equals("anyType") && !O_StudImage.toString().equals(null) && !O_StudImage.toString().equals("anyType{}")) {
                S_StudImage = (SoapPrimitive) result.getProperty("Student_Image_Path");
                Log.d("Image Url", S_StudImage.toString());
                str_imageUrl = S_StudImage.toString();
            }
//-----------------added by madhu 17/9/18
            O_CurrentSituation= result.getProperty("CurrentSituation");
            if (!O_CurrentSituation.toString().equals("anyType") && !O_CurrentSituation.toString().equals("anyType{}") && !O_CurrentSituation.toString().equals(null)) {
                S_CurrentSituation = (SoapPrimitive) result.getProperty("CurrentSituation");
                Log.d("CurrentSituation", S_CurrentSituation.toString());
                str_CurrentSituation = S_CurrentSituation.toString();
            }

           /* O_Resource= result.getProperty("Resource");
            if (!O_Resource.toString().equals("anyType") && !O_Resource.toString().equals("anyType{}") && !O_Resource.toString().equals(null)) {
                S_Resource = (SoapPrimitive) result.getProperty("Resource");
                Log.d("Resource", S_Resource.toString());
                str_Resource = S_Resource.toString();
            }
*/
           /* O_MobileNo = result.getProperty("MobileNo");
            if (!O_MobileNo.toString().equals("anyType") && !O_MobileNo.toString().equals(null) && !O_MobileNo.toString().equals("anyType{}")) {
                S_MobileNo = (SoapPrimitive) result.getProperty("MobileNo");
                Log.d("MobileNo", S_MobileNo.toString());
                str_MobileNo = S_MobileNo.toString();
            }*/

            O_BeneficiariesList = result.getProperty("BeneficiariesList");
            if (!O_BeneficiariesList.toString().equals("anyType") && !O_BeneficiariesList.toString().equals("anyType{}") && !O_BeneficiariesList.toString().equals(null)) {
                S_BeneficiariesList = (SoapPrimitive) result.getProperty("BeneficiariesList");
                Log.d("BeneficiariesList", S_BeneficiariesList.toString());
                str_whoareBeneficiaries = S_BeneficiariesList.toString();
            }

            String Imagestring = str_imageUrl;
            if (Imagestring.equals("null") || Imagestring.equals("anyType{}")) {
                // PMImgUrl="null";
                img_student.setImageResource(R.drawable.devanand);
            } else {
                String arr[] = Imagestring.split("/", 2);

                String firstWord = arr[0];   //the
                String secondWord = arr[1];

                PMImgUrl = serverPath + secondWord;

                Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                LoadGalleryImage loadGalleryImage = new LoadGalleryImage(PMUnapproved_DetailsActivity.this);
                loadGalleryImage.execute();
            }
            /*    O_ManagerComments = result.getProperty("ManagerComments");
                if (!O_ManagerComments.toString().equals("anyType") && !O_ManagerComments.toString().equals(null)) {
                    S_ManagerComments = (SoapPrimitive) result.getProperty("ManagerComments");
                    Log.d("Manager Comments", S_ManagerComments.toString());
                    str_ManagerComments = S_ManagerComments.toString();
                }*/

            O_TotalCost = result.getProperty("Amount");
            if (!O_TotalCost.toString().equals("anyType") && !O_TotalCost.toString().equals("anyType{}") && !O_TotalCost.toString().equals(null)) {
                S_TotalCost = (SoapPrimitive) result.getProperty("Amount");
                Log.d("Total Cost", S_TotalCost.toString());
                str_totalCost = S_TotalCost.toString();
            }

            if (str_totalCost.equals("0")) {
                edt_amount.setText("0");
                edt_amount.setEnabled(false);
            }

         /*       edt_projectType.setText(str_ThemeName);
                edt_projectType.setFocusable(false);
                edt_projectType.setInputType(0);
                edt_projectType.setTextSize(15);
*/








            edt_title.setText(str_ProjectTitle);
            //    edt_title.setFocusable(true);
            //edt_title.setInputType(0);
            edt_title.setTextSize(15);

            edt_noOfBeneficiaries.setText(str_BeneficiaryNo);
            //  edt_noOfBeneficiaries.setFocusable(true);
            //  edt_noOfBeneficiaries.setInputType(0);
            edt_noOfBeneficiaries.setTextSize(15);

            edt_objectives.setText(str_Objective);
            //      edt_objectives.setFocusable(true);
            //  edt_objectives.setInputType(0);
            edt_objectives.setTextSize(15);

            edt_actionPlan.setText(str_actionPlan);
            //     edt_actionPlan.setFocusable(true);
            //   edt_actionPlan.setInputType(0);
            edt_actionPlan.setTextSize(15);
            edt_whoareBeneficiaries.setText(str_whoareBeneficiaries);
            edt_whoareBeneficiaries.setTextSize(15);

            edt_placeofImp.setText(str_placeofImp);
            edt_placeofImp.setTextSize(15);

//-----------------added by madhu 17/9/18
            edt_CurrentSituation.setText(str_CurrentSituation);
            edt_CurrentSituation.setTextSize(15);

           /* edt_resources.setText(str_Resource);
            edt_resources.setTextSize(15);*/
//                txt_PMComments.setText(str_ManagerComments);

            SoapObject materialList = (SoapObject) result.getProperty("materials");
            for (int i = 0; i < materialList.getPropertyCount(); i++) {
                SoapObject vmmateriallist = (SoapObject) materialList.getProperty(i);

                O_MaterialName = vmmateriallist.getProperty("MeterialName");
                if (!O_MaterialName.toString().equals("anyType") && !O_MaterialName.toString().equals("anyType{}") && !O_MaterialName.toString().equals(null)) {
                    S_MaterialName = (SoapPrimitive) vmmateriallist.getProperty("MeterialName");
                    Log.d("MaterialNamesssss", S_MaterialName.toString());
                    str_materialName = S_MaterialName.toString();

                    lstMaterialName.add(str_materialName);
                }

                O_MaterialCost = vmmateriallist.getProperty("MeterialCost");
                if (!O_MaterialCost.toString().equals("anyType") && !O_MaterialCost.toString().equals("anyType{}") && !O_MaterialCost.toString().equals(null)) {
                    S_MaterialCost = (SoapPrimitive) vmmateriallist.getProperty("MeterialCost");
                    Log.d("MaterialCostsssss", S_MaterialCost.toString());
                    str_materialCost = S_MaterialCost.toString();

                    lstMaterialCost.add(str_materialCost);
                }
            }

            SoapObject MembersList = (SoapObject) result.getProperty("Members");
            for (int i = 0; i < MembersList.getPropertyCount(); i++) {
                SoapObject vmmemberlist = (SoapObject) MembersList.getProperty(i);

                O_MembersName = vmmemberlist.getProperty("MemberName");
                if (!O_MembersName.toString().equals("anyType") && !O_MembersName.toString().equals("anyType{}") && !O_MembersName.toString().equals(null)) {
                    S_MembersName = (SoapPrimitive) vmmemberlist.getProperty("MemberName");
                    Log.d("MemberNamesssss", S_MembersName.toString());
                    str_memberName = S_MembersName.toString();

                    lstMemberName.add(str_memberName);
                }

                O_MemberEmail = vmmemberlist.getProperty("MemberEmail");
                if (!O_MemberEmail.toString().equals("anyType") && !O_MemberEmail.toString().equals("anyType{}") && !O_MemberEmail.toString().equals(null)) {
                    S_MemberEmail = (SoapPrimitive) vmmemberlist.getProperty("MemberEmail");
                    Log.d("MemberEmail", S_MemberEmail.toString());
                    str_memberEmail = S_MemberEmail.toString();

                    lstMemberEmail.add(str_memberEmail);
                }
            }
          //  inintMembers();
            initProjectTypeSpinner(str_ThemeName);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public boolean validation_Save(){

        Boolean bfarmername=true,baddress=true,blocation=true,bimageview=true,bnetwork=true,bfees=true,bcluster=true;
        Boolean datevalidationresult1=true,datevalidationresult2=true;

     /*   if( edt_pmcomment.getText().toString().length() == 0 ){
            edt_pmcomment.setError( "Manager comments required!" );bfarmername=false;}

        if( edt_amount.getText().toString().length() == 0 ){
            edt_amount.setError( "Approved Amount required!" );baddress=false;}*/

        if( edt_title.getText().toString().length() == 0 ){
            edt_title.setError( "Title required!" );blocation=false;}

        if( edt_actionPlan.getText().toString().length() == 0 ){
            edt_actionPlan.setError( "Action Plan required!" );blocation=false;}

        if( edt_noOfBeneficiaries.getText().toString().length() == 0 ){
            edt_noOfBeneficiaries.setError( "No of Beneficiaries required!" );blocation=false;}

        if( edt_whoareBeneficiaries.getText().toString().length() == 0){
            edt_whoareBeneficiaries.setError("Who are the beneficiaries required!");blocation=false;
        }
        if( edt_objectives.getText().toString().length() == 0 ){
            edt_objectives.setError( "Objectives required!" );blocation=false;}

        /*if(imageview.getDrawable() == null){
            Toast.makeText(MainActivity.this, "Image is not taken", Toast.LENGTH_LONG).show();bimageview= false;}
*/
        /*if(spin_bg.getSelectedItemPosition()==0){
            TextView errorText = (TextView) spin_bg.getSelectedView();
            errorText.setError("Select Fees");
            bfees=false;
        }
        if(cluster.getSelectedItemPosition() ==0){
            TextView errorText = (TextView) cluster.getSelectedView();
            errorText.setError("Select Cluster");
            bcluster=false;
        }*/


        if(clickstart_pmunapproved_editdate_tv.getText().toString().length()==0||clickend_pmunapproved_editdate_tv.toString().length()==0
                ||clickstart_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00")
                ||clickend_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00"))
        {
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            datevalidationresult1=false;
        }




        if(clickstart_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("Click for calendar")||
                clickend_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("Click for calendar"))
        {
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            datevalidationresult1=false;
        }


       /* if((yyyyMMdd_pmstartdate.toString().length()!=0)&&(yyyyMMdd_pmenddate.toString().length()!=0) )
        {*/
        /*if(date1.compareTo(date2)<0){ //0 comes when two date are same,
            //1 comes when date1 is higher then date2
            //-1 comes when date1 is lower then date2 }*/


            if( !(clickstart_pmunapproved_editdate_tv.getText().toString().length()==0)
                    &&!(clickend_pmunapproved_editdate_tv.getText().toString().length()==0)
                    &&!(clickstart_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00"))
                    &&!(clickend_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00")))
            {



            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

            try {
                Date fromdate = mdyFormat.parse(clickstart_pmunapproved_editdate_tv.getText().toString());
                Date todate = mdyFormat.parse(clickend_pmunapproved_editdate_tv.getText().toString());

                if(fromdate.compareTo(todate)<=0)
                {
                    datevalidationresult2=true;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Kindly enter valid date", Toast.LENGTH_SHORT).show();
                    datevalidationresult2=false;
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }//end of if


        if(bfarmername&&baddress&&blocation&&bimageview&&bnetwork&&bfees&&bcluster
                &&datevalidationresult1&&datevalidationresult2) {
            return true;
        }else{return false;}
    }

    public boolean validation_Approval(){

        Boolean bfarmername=true,baddress=true,blocation=true,bimageview=true,bnetwork=true,bfees=true,bcluster=true;
        Boolean datevalidationresult1=true,datevalidationresult2=true;

        if( edt_pmcomment.getText().toString().length() == 0 ){
            edt_pmcomment.setError( "Manager comments required!" );bfarmername=false;}

        if( edt_amount.getText().toString().length() == 0 ){
            edt_amount.setError( "Approved Amount required!" );baddress=false;}

        if( edt_title.getText().toString().length() == 0 ){
            edt_title.setError( "Title required!" );blocation=false;}

        if( edt_actionPlan.getText().toString().length() == 0 ){
            edt_actionPlan.setError( "Action Plan required!" );blocation=false;}

        if( edt_noOfBeneficiaries.getText().toString().length() == 0 ){
            edt_noOfBeneficiaries.setError( "No of Beneficiaries required!" );blocation=false;}

        if( edt_objectives.getText().toString().length() == 0 ){
            edt_objectives.setError( "Objectives required!" );blocation=false;}

        if( edt_whoareBeneficiaries.getText().toString().length() == 0){
            edt_whoareBeneficiaries.setError("Who are the beneficiaries required!");blocation=false;
        }
        /*if(imageview.getDrawable() == null){
            Toast.makeText(MainActivity.this, "Image is not taken", Toast.LENGTH_LONG).show();bimageview= false;}
*/
        /*if(spin_bg.getSelectedItemPosition()==0){
            TextView errorText = (TextView) spin_bg.getSelectedView();
            errorText.setError("Select Fees");
            bfees=false;
        }
        if(cluster.getSelectedItemPosition() ==0){
            TextView errorText = (TextView) cluster.getSelectedView();
            errorText.setError("Select Cluster");
            bcluster=false;
        }*/


       /* if(yyyyMMdd_pmstartdate.toString().length()==0||yyyyMMdd_pmenddate.toString().length()==0
          ||yyyyMMdd_pmstartdate.equalsIgnoreCase("0000-00-00")
                ||yyyyMMdd_pmenddate.equalsIgnoreCase("0000-00-00"))
        {
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            datevalidationresult1=false;
        }*/


        if(clickstart_pmunapproved_editdate_tv.getText().toString().length()==0||clickend_pmunapproved_editdate_tv.getText().toString().length()==0
                ||clickstart_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00")
                ||clickend_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00"))
        {
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            datevalidationresult1=false;
        }

        if(clickstart_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("Click for calendar")||
                clickend_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("Click for calendar"))
        {
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            datevalidationresult1=false;
        }

        //if((yyyyMMdd_pmstartdate.toString().length()!=0)&&(yyyyMMdd_pmenddate.toString().length()!=0) )
        if( !(clickstart_pmunapproved_editdate_tv.getText().toString().length()==0)
                &&!(clickend_pmunapproved_editdate_tv.getText().toString().length()==0)
                &&!(clickstart_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00"))
                &&!(clickend_pmunapproved_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00")))
        {
        /*if(date1.compareTo(date2)<0){ //0 comes when two date are same,
            //1 comes when date1 is higher then date2
            //-1 comes when date1 is lower then date2 }*/

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

            try {
                Date fromdate = mdyFormat.parse(clickstart_pmunapproved_editdate_tv.getText().toString());
                Date todate = mdyFormat.parse(clickend_pmunapproved_editdate_tv.getText().toString());

                if(fromdate.compareTo(todate)<=0)
                {
                    datevalidationresult2=true;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Kindly enter valid date", Toast.LENGTH_SHORT).show();
                    datevalidationresult2=false;
                }


            } catch (ParseException e) {
                e.printStackTrace();
            }
        }//end of if


        if(bfarmername&&baddress&&blocation&&bimageview&&bnetwork&&bfees&&bcluster&&datevalidationresult1
                &&datevalidationresult2) {
            return true;
        }else{return false;}
    }

    public boolean validation_ReApply()
    {

        Boolean bpmcomments=true;


        if( edt_pmcomment.getText().toString().length() == 0 ){
            edt_pmcomment.setError( "Manager comments required!" );bpmcomments=false;}

        /*if( edt_amount.getText().toString().length() == 0 ){
            edt_amount.setError( "Approved Amount required!" );bamount=false;}
*/
        if(bpmcomments) {
            return true;
        }else{return false;}
    }

    public class ProjectType extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        private ProgressBar progressBar;

        ProjectType (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Void... params) {

            String METHOD_NAME = "GetThemeList";
            String SOAP_ACTION1 = "http://mis.leadcampus.org/GetThemeList";

            try{


                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);


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
                    //Log.d("soap responseyyyyyyy",response.toString());

                    return response;

                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());
                }
            }catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            progressBar.setVisibility(View.GONE);


            String finalResult = result.toString();
            String finals = finalResult.replace("anyType","");
            Log.d("Finals is",finals);

            ArrayList<String> projectTypeList = new ArrayList<String>();

            Log.d("finalResultsssss",finalResult);

            for(int i=0;i < result.getPropertyCount();i++){
                SoapObject list = (SoapObject) result.getProperty(i);
                SoapPrimitive S_ThemeId,S_ThemeName,S_Status;
                Object O_ThemeId,O_ThemeName,O_Status;

                int projectTypeId=0;

                String str_ProjectTypeId=null,str_ProjectType=null,str_status=null;

                O_ThemeId = list.getProperty("ThemeId");
                if(!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals("anyType{}") && !O_ThemeId.toString().equals(null)){
                    S_ThemeId = (SoapPrimitive) list.getProperty("ThemeId");
                    Log.d("ProjectId",S_ThemeId.toString());
                    str_ProjectTypeId = O_ThemeId.toString();
                    projectTypeId = Integer.valueOf(str_ProjectTypeId);
                }

                O_ThemeName = list.getProperty("ThemeName");
                if(!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals("anyType{}") && !O_ThemeName.toString().equals(null)){
                    S_ThemeName = (SoapPrimitive) list.getProperty("ThemeName");
                    Log.d("ProjectType Name",S_ThemeName.toString());
                    str_ProjectType = O_ThemeName.toString();

                    projectTypeList.add(str_ProjectType);

                    hashProjTypeId.put(str_ProjectType,projectTypeId);
                }

            }

            ViewProject viewProject = new ViewProject(context);
            viewProject.execute();

            //setProjectTypeSpinner(projectTypeList);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getProjectDetails() {
        String METHOD_NAME = "GetUnapprovedProjectDetails";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetUnapprovedProjectDetails";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",LeadId);
            request.addProperty("PDId",Project_Id);

            Log.d("projectIdsssss",ProjectId);
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
                // Log.d("soap response1xxxx",envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                //SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("soap responseyyyyyyy",response.toString());

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

    public class ViewProject extends AsyncTask<Void, Void, SoapObject> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        ViewProject (Context ctx){
            context = ctx;
        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getProjectDetails();

            //SoapPrimitive response =getProjectDetails();
            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            SoapPrimitive S_ProjectTitle,S_ThemeName,S_BeneficiaryNo, S_Placeofimplement,S_Objective,S_actionPlan,S_ManagerComments,S_MembersName,S_MemberEmail,S_MaterialName,S_MaterialCost,S_TotalCost,S_StudImage,S_BeneficiariesList,S_CurrentSituation;
            Object O_ProjectTitle,O_ThemeName,O_BeneficiaryNo, O_Placeofimplement,O_Objective,O_actionPlan,O_ManagerComments,O_MembersName,O_MemberEmail,O_MaterialName,O_MaterialCost,O_TotalCost,O_StudImage,O_BeneficiariesList,O_CurrentSituation;
            SoapObject SO_ThemeName;

            SoapPrimitive S_startdate_pm,S_enddate_pm;
            Object O_startdate_pm,O_enddate_pm = null;
            String str_startdate_pm,str_enddate_pm;


            //   if(flag==0) {
            O_ProjectTitle = result.getProperty("Title");
            if (!O_ProjectTitle.toString().equals("anyType{}") && !O_ProjectTitle.toString().equals(null)) {
                S_ProjectTitle = (SoapPrimitive) result.getProperty("Title");
                Log.d("Titlesssssss", S_ProjectTitle.toString());
                str_ProjectTitle = S_ProjectTitle.toString();
            }

            O_ThemeName = result.getProperty("ThemeName");
            if (O_ThemeName.toString().equals("anyType{}") && O_ThemeName.toString().equals(null)) {
                SO_ThemeName = (SoapObject) result.getProperty("ThemeName");
                Log.d("ThemeNamessss", SO_ThemeName.toString());
                str_ThemeName = SO_ThemeName.toString();
                //   spin_projectType.setSelection(0);
            }
            if (!O_ThemeName.toString().equals("anyType{}") && !O_ThemeName.toString().equals(null)) {
                S_ThemeName = (SoapPrimitive) result.getProperty("ThemeName");
                Log.d("ThemeNamessss", S_ThemeName.toString());
                str_ThemeName = S_ThemeName.toString();
                //    spin_projectType.setSelection(0);
            }

            O_BeneficiaryNo = result.getProperty("BeneficiaryNo");
            if (!O_BeneficiaryNo.toString().equals("anyType{}") && !O_BeneficiaryNo.toString().equals(null)) {
                S_BeneficiaryNo = (SoapPrimitive) result.getProperty("BeneficiaryNo");
                Log.d("BeneficiaryNo", S_BeneficiaryNo.toString());
                str_BeneficiaryNo = S_BeneficiaryNo.toString();
            }

            O_Placeofimplement= result.getProperty("Placeofimplement");
            if (!O_Placeofimplement.toString().equals("anyType") && !O_Placeofimplement.toString().equals("anyType{}") && !O_Placeofimplement.toString().equals(null)) {
                S_Placeofimplement = (SoapPrimitive) result.getProperty("Placeofimplement");
                Log.d("Placeofimplement", S_Placeofimplement.toString());
                str_placeofImp = S_Placeofimplement.toString();
            }

            O_Objective = result.getProperty("Objectives");
            if (!O_Objective.toString().equals("anyType{}") && !O_Objective.toString().equals(null)) {
                S_Objective = (SoapPrimitive) result.getProperty("Objectives");
                Log.d("Objectivess", S_Objective.toString());
                str_Objective = S_Objective.toString();
            }

            O_actionPlan = result.getProperty("ActionPlan");
            if (!O_actionPlan.toString().equals("anyType{}") && !O_actionPlan.toString().equals(null)) {
                S_actionPlan = (SoapPrimitive) result.getProperty("ActionPlan");
                Log.d("ActionPlansss", S_actionPlan.toString());
                str_actionPlan = S_actionPlan.toString();
            }

//-----------------added by madhu 17/9/18
            O_CurrentSituation= result.getProperty("CurrentSituation");
            if (!O_CurrentSituation.toString().equals("anyType") && !O_CurrentSituation.toString().equals("anyType{}") && !O_CurrentSituation.toString().equals(null)) {
                S_CurrentSituation = (SoapPrimitive) result.getProperty("CurrentSituation");
                Log.d("CurrentSituation", S_CurrentSituation.toString());
                str_CurrentSituation = S_CurrentSituation.toString();
            }
//-------
            O_StudImage = result.getProperty("Student_Image_Path");
            if (!O_StudImage.toString().equals("anyType{}") && !O_StudImage.toString().equals(null)) {
                S_StudImage = (SoapPrimitive) result.getProperty("Student_Image_Path");
                Log.d("Image Url", S_StudImage.toString());
                str_imageUrl = S_StudImage.toString();
            }

            O_BeneficiariesList = result.getProperty("BeneficiariesList");
            if (!O_BeneficiariesList.toString().equalsIgnoreCase("anyType") && !O_BeneficiariesList.toString().equals(null) && !O_BeneficiariesList.toString().equalsIgnoreCase("anyType{}")) {
                S_BeneficiariesList = (SoapPrimitive) result.getProperty("BeneficiariesList");
                Log.d("BeneficiariesList", S_BeneficiariesList.toString());
                str_whoareBeneficiaries = S_BeneficiariesList.toString();
            }


            O_startdate_pm= result.getProperty("ProjectStartDate");
            Log.e("startDate",O_startdate_pm.toString());

            if (O_startdate_pm.toString().equalsIgnoreCase("anyType")
                    ||O_startdate_pm.toString().equals("0000-00-00")
                    ||O_startdate_pm.toString().equalsIgnoreCase("anyType{}"))
            {
                str_startdate_pm = "Click for Calendar";
            }else{

                S_startdate_pm = (SoapPrimitive) result.getProperty("ProjectStartDate");

                Log.e("startDate",S_startdate_pm.toString());
                str_startdate_pm = S_startdate_pm.toString();
                yyyyMMdd_pmstartdate=str_startdate_pm.toString();


            }

            // Toast.makeText(context,""+O_startdate.toString(),Toast.LENGTH_LONG).show();
            clickstart_pmunapproved_editdate_tv.setText(str_startdate_pm.toString());


            O_enddate_pm= result.getProperty("ProjectEndDate");
            if (O_enddate_pm.toString().equalsIgnoreCase("anyType")
                    ||O_enddate_pm.toString().equals("0000-00-00")
                    ||O_enddate_pm.toString().equalsIgnoreCase("anyType{}"))
            {
                str_enddate_pm = "Click for Calendar";

            }else{

                S_enddate_pm = (SoapPrimitive) result.getProperty("ProjectEndDate");

                Log.e("endDate",S_enddate_pm.toString());
                str_enddate_pm = S_enddate_pm.toString();
                yyyyMMdd_pmenddate=str_enddate_pm.toString();


            }
            clickend_pmunapproved_editdate_tv.setText(str_enddate_pm.toString());

//vijay



            if(str_startdate_pm.toString().equals("anyType{}")
                    || str_startdate_pm.toString().equals(null)
                    ||str_startdate_pm.toString().equals("0000-00-00")
                    ||str_startdate_pm.toString().equalsIgnoreCase("Click for Calendar")
                    ||str_enddate_pm.toString().equals("anyType{}")
                    ||str_enddate_pm.toString().equals(null)
                    ||str_enddate_pm.toString().equals("0000-00-00")
                    ||str_enddate_pm.toString().equalsIgnoreCase("Click for Calendar"))
            {
                pm_days_linearlayout.setVisibility(View.GONE);

            }else
        {


            try{
                int days_count = Days.daysBetween(new LocalDate(str_startdate_pm), new LocalDate(str_enddate_pm)).getDays();
                int int_days=days_count+1;
                Log.e("days",String.valueOf(int_days));
                pm_numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
            }catch(Throwable t)
            {
                pm_numberofdays_tv.setText(" ");
            }
        }









            Log.i("tag","str_imageUrl="+str_imageUrl);
            String Imagestring = str_imageUrl;
            if(Imagestring==null||Imagestring.equals("anyType{}")||Imagestring.equals("null")){
                // PMImgUrl="null";
                img_student.setImageResource(R.drawable.devanand);
            }else {
                String arr[] = Imagestring.split("/", 2);

                String firstWord = arr[0];   //the
                String secondWord = arr[1];

                PMImgUrl = serverPath + secondWord;

                Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                LoadGalleryImage loadGalleryImage=new LoadGalleryImage(PMUnapproved_DetailsActivity.this);
                loadGalleryImage.execute();
            }
            /*    O_ManagerComments = result.getProperty("ManagerComments");
                if (!O_ManagerComments.toString().equals("anyType") && !O_ManagerComments.toString().equals(null)) {
                    S_ManagerComments = (SoapPrimitive) result.getProperty("ManagerComments");
                    Log.d("Manager Comments", S_ManagerComments.toString());
                    str_ManagerComments = S_ManagerComments.toString();
                }*/

            O_TotalCost = result.getProperty("Amount");
            if (!O_TotalCost.toString().equals("anyType{}") && !O_TotalCost.toString().equals(null)) {
                S_TotalCost = (SoapPrimitive) result.getProperty("Amount");
                Log.d("Total Cost", S_TotalCost.toString());
                str_totalCost = S_TotalCost.toString();
            }

            if(str_totalCost.equals("0")){
                edt_amount.setText("0");
                edt_amount.setEnabled(false);
            }

         /*       edt_projectType.setText(str_ThemeName);
                edt_projectType.setFocusable(false);
                edt_projectType.setInputType(0);
                edt_projectType.setTextSize(15);
*/
            edt_title.setText(str_ProjectTitle);
            //    edt_title.setFocusable(true);
            //edt_title.setInputType(0);
            edt_title.setTextSize(15);

            edt_noOfBeneficiaries.setText(str_BeneficiaryNo);
            //  edt_noOfBeneficiaries.setFocusable(true);
            //  edt_noOfBeneficiaries.setInputType(0);
            edt_noOfBeneficiaries.setTextSize(15);

            edt_objectives.setText(str_Objective);
            //      edt_objectives.setFocusable(true);
            //  edt_objectives.setInputType(0);
            edt_objectives.setTextSize(15);

            edt_actionPlan.setText(str_actionPlan);
            //     edt_actionPlan.setFocusable(true);
            //   edt_actionPlan.setInputType(0);
            edt_actionPlan.setTextSize(15);

            edt_whoareBeneficiaries.setText(str_whoareBeneficiaries);
            edt_whoareBeneficiaries.setTextSize(15);

            edt_placeofImp.setText(str_placeofImp);
            edt_placeofImp.setTextSize(15);

            //-----------------added by madhu 17/9/18
            edt_CurrentSituation.setText(str_CurrentSituation);
            edt_CurrentSituation.setTextSize(15);
//                txt_PMComments.setText(str_ManagerComments);

            SoapObject materialList = (SoapObject) result.getProperty("materials");
            for (int i = 0; i < materialList.getPropertyCount(); i++) {
                SoapObject vmmateriallist = (SoapObject) materialList.getProperty(i);

                O_MaterialName = vmmateriallist.getProperty("MeterialName");
                if (!O_MaterialName.toString().equals("anyType") && !O_MaterialName.toString().equals("anyType{}") && !O_MaterialName.toString().equals(null)) {
                    S_MaterialName = (SoapPrimitive) vmmateriallist.getProperty("MeterialName");
                    Log.d("MaterialNamesssss", S_MaterialName.toString());
                    str_materialName = S_MaterialName.toString();

                    lstMaterialName.add(str_materialName);
                }
                O_MaterialCost = vmmateriallist.getProperty("MeterialCost");
                if (!O_MaterialCost.toString().equals("anyType") && !O_MaterialCost.toString().equals("anyType{}") && !O_MaterialCost.toString().equals(null)) {
                    S_MaterialCost = (SoapPrimitive) vmmateriallist.getProperty("MeterialCost");
                    Log.d("MaterialCostsssss", S_MaterialCost.toString());
                    str_materialCost = S_MaterialCost.toString();

                    lstMaterialCost.add(str_materialCost);
                }


            }

            SoapObject MembersList = (SoapObject) result.getProperty("Members");
            for (int i = 0; i < MembersList.getPropertyCount(); i++) {
                SoapObject vmmemberlist = (SoapObject) MembersList.getProperty(i);

                O_MembersName = vmmemberlist.getProperty("MemberName");
                if (!O_MembersName.toString().equals("anyType") && !O_MembersName.toString().equals(null) && !O_MembersName.toString().equals("anyType{}")) {
                    S_MembersName = (SoapPrimitive) vmmemberlist.getProperty("MemberName");
                    Log.d("MemberNamesssss", S_MembersName.toString());
                    str_memberName = S_MembersName.toString();

                    lstMemberName.add(str_memberName);
                }

                O_MemberEmail = vmmemberlist.getProperty("MemberEmail");
                if (!O_MemberEmail.toString().equals("anyType") && !O_MemberEmail.toString().equals("anyType{}") && !O_MemberEmail.toString().equals(null)) {
                    S_MemberEmail = (SoapPrimitive) vmmemberlist.getProperty("MemberEmail");
                    Log.d("MemberEmail", S_MemberEmail.toString());
                    str_memberEmail = S_MemberEmail.toString();

                    lstMemberEmail.add(str_memberEmail);
                }

            }
            initMaterials();
            initMembers();
            initProjectTypeSpinner(str_ThemeName);

            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class LoadGalleryImage extends AsyncTask<Void, Object, Bitmap> {

        private Context context;
        //   private ProgressBar progressBar;

        LoadGalleryImage (Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            //  progressBar = (ProgressBar) findViewById(R.id.progressBar);
            //  progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            ArrayList<Bitmap> bitmapLst=null;
            Bitmap bitmaplogo=null;

            try {
                URL url= new URL(PMImgUrl);
                Log.d("Urlssssssssssss",url.toString());
                bitmaplogo = BitmapFactory.decodeStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmaplogo;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // bitmapList.add(bitmap);
            img_student.setImageBitmap(bitmap);
            //  progressBar.setVisibility(GONE);
        }
    }

    public class SubmitSaveDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        SubmitSaveDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = updateProjectDetails();

            //Log.d("Soap response issssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result.toString().equals("Error")){
                Toast.makeText(context,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"Project Updated Successfully",Toast.LENGTH_LONG).show();
                finish();
                Intent ittEditProjToProjStatus = new Intent(PMUnapproved_DetailsActivity.this,PMProjectDetailActivity.class);
                ittEditProjToProjStatus.putExtra("pageCount",0);
                startActivity(ittEditProjToProjStatus);
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive updateProjectDetails()
    {
        String METHOD_NAME = "SaveUnapprovedprojectDetails1";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/SaveUnapprovedprojectDetails1";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            // long projectIds = (long) Integer.valueOf(projectId);
            request.addProperty("PDId",Project_Id);
            Log.d("PDIdssssssxxxx","hi");
            Log.d("PDIdssssssxxxx", String.valueOf(projectId));
            request.addProperty("Lead_Id",LeadId);
            request.addProperty("Title",edt_title.getText().toString());
            Log.d("Titlesssssxxxx",edt_title.getText().toString());
            int themeId = hashProjTypeId.get(spin_projectType.getSelectedItem().toString());
            Log.d("themeidsssssxxxx", String.valueOf(themeId));
            request.addProperty("Theme",String.valueOf(themeId));
            long beneficiaryNo = (long) Integer.valueOf(edt_noOfBeneficiaries.getText().toString());
            Log.d("beneficiaryNosssxxxx", String.valueOf(beneficiaryNo));
            request.addProperty("BeneficiaryNo",beneficiaryNo);
            Log.d("objectivesxxxxxx",edt_objectives.getText().toString());
            request.addProperty("Objectives",edt_objectives.getText().toString());
            Log.d("actionplansxxxxxx",edt_actionPlan.getText().toString());
            request.addProperty("ActionPlan",edt_actionPlan.getText().toString());
            request.addProperty("Beneficiaries",edt_whoareBeneficiaries.getText().toString());
            request.addProperty("Placeofimplement",edt_placeofImp.getText().toString());
            request.addProperty("CurrentSituation",edt_CurrentSituation.getText().toString());


            request.addProperty("ProjectStartDate",yyyyMMdd_pmstartdate.toString());
            request.addProperty("ProjectEndDate",yyyyMMdd_pmenddate.toString());

            request.addProperty("ProjectEndDate",yyyyMMdd_pmenddate.toString());

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

                //SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soapupdateProjectDtlsss",envelope.getResponse().toString());

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

    public class SubmitApproveDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        SubmitApproveDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = updateApproveProjectDetails();

            //Log.d("Soap response issssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result.toString().equals("Error")){
                Toast.makeText(context,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"Project Approved Successfully",Toast.LENGTH_LONG).show();
                finish();
                Intent ittEditProjToProjStatus = new Intent(PMUnapproved_DetailsActivity.this,PMProjectDetailActivity.class);
                ittEditProjToProjStatus.putExtra("pageCount",0);
                startActivity(ittEditProjToProjStatus);
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive updateApproveProjectDetails()
    {
        String METHOD_NAME = "UpdateUnApprovedprojectDetails1";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateUnApprovedprojectDetails1";


        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            // long projectIds = (long) Integer.valueOf(projectId);
            request.addProperty("PDId",Project_Id);
            Log.d("PDIdssssssxxxx","hi");
            Log.d("PDIdssssssxxxx", String.valueOf(projectId));
            request.addProperty("Lead_Id",LeadId);
            request.addProperty("Title",edt_title.getText().toString());
            Log.d("Titlesssssxxxx",edt_title.getText().toString());
            int themeId = hashProjTypeId.get(spin_projectType.getSelectedItem().toString());
            Log.d("themeidsssssxxxx", String.valueOf(themeId));
            request.addProperty("Theme",String.valueOf(themeId));
            long beneficiaryNo = (long) Integer.valueOf(edt_noOfBeneficiaries.getText().toString());
            Log.d("beneficiaryNosssxxxx", String.valueOf(beneficiaryNo));
            request.addProperty("BeneficiaryNo",beneficiaryNo);
            Log.d("objectivesxxxxxx",edt_objectives.getText().toString());
            request.addProperty("Objectives",edt_objectives.getText().toString());
            Log.d("actionplansxxxxxx",edt_actionPlan.getText().toString());
            request.addProperty("ActionPlan",edt_actionPlan.getText().toString());
            Log.d("actionplansxxxxxx",edt_amount.getText().toString());
            request.addProperty("SanctionAmount",edt_amount.getText().toString());
            Log.d("actionplansxxxxxx",edt_pmcomment.getText().toString());
            request.addProperty("ManagerComments",edt_pmcomment.getText().toString());
            request.addProperty("ProjectStatus","Approved");
            request.addProperty("Beneficiaries",edt_whoareBeneficiaries.getText().toString());
            request.addProperty("Placeofimplement",edt_placeofImp.getText().toString());
            request.addProperty("CurrentSituation",edt_CurrentSituation.getText().toString());



            request.addProperty("ProjectStartDate",yyyyMMdd_pmstartdate.toString());
            request.addProperty("ProjectEndDate",yyyyMMdd_pmenddate.toString());
            request.addProperty("IsImpactProject",int_impactproject);//<IsImpactProject>int</IsImpactProject>

            Log.d("ReqPMunapprove",request.toString());

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


                //SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soapupdateProjectDtlsss",envelope.getResponse().toString());

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

    public class SubmitReApplyDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        SubmitReApplyDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = ReApplyProjectDetails();

            //   SoapObject response = ReApplyProjectDetails();
            //Log.d("Soap response issssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result.toString().equals("Error")){
                Toast.makeText(context,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"Project Sent for Re-Apply",Toast.LENGTH_LONG).show();
                finish();
                Intent ittEditProjToProjStatus = new Intent(PMUnapproved_DetailsActivity.this,PMProjectDetailActivity.class);
                ittEditProjToProjStatus.putExtra("pageCount",0);
                startActivity(ittEditProjToProjStatus);
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private SoapPrimitive ReApplyProjectDetails() {
        String METHOD_NAME = "ReApplayProject";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ReApplayProject";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            // long projectIds = (long) Integer.valueOf(projectId);
            request.addProperty("PDId",Project_Id);
            Log.d("PDIdssssssxxxx","hi");
            Log.d("PDIdssssssxxxx", String.valueOf(projectId));

            request.addProperty("ProjectStatus","RequestForModification");
            Log.d("actionplansxxxxxx",edt_pmcomment.getText().toString());
            request.addProperty("ManagerComments",edt_pmcomment.getText().toString());
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
                //Log.e("soapupdateProjectDtlsss",envelope.getResponse().toString());

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

    public class SubmitCancelDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        SubmitCancelDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = CancelProjectDetails();

            //   SoapObject response = ReApplyProjectDetails();
            //Log.d("Soap response issssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result.toString().equals("Error")){
                Toast.makeText(context,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(context,"Project Rejected Successfully",Toast.LENGTH_LONG).show();
                finish();
                Intent ittEditProjToProjStatus = new Intent(PMUnapproved_DetailsActivity.this,PMProjectDetailActivity.class);
                ittEditProjToProjStatus.putExtra("pageCount",0);
                startActivity(ittEditProjToProjStatus);
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private SoapPrimitive CancelProjectDetails() {
        String METHOD_NAME = "RejectProject";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/RejectProject";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            // long projectIds = (long) Integer.valueOf(projectId);
            request.addProperty("PDId",Project_Id);
            Log.d("PDIdssssssxxxx","hi");
            Log.d("PDIdssssssxxxx", String.valueOf(projectId));
            request.addProperty("ProjectStatus","Rejected");
            Log.d("actionplansxxxxxx",edt_pmcomment.getText().toString());
            request.addProperty("ManagerComments",edt_pmcomment.getText().toString());
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
                //Log.e("soapupdateProjectDtlsss",envelope.getResponse().toString());

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




    // start of from date
    @SuppressLint("ValidFragment")
    public static class DatePickerFragmentFromDate extends  DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);



            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
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




            // SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");//2017-06-22

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
            yyyyMMdd_pmstartdate = mdyFormat.format(calendar.getTime());


            clickstart_pmunapproved_editdate_tv.setText(mdyFormat.format(calendar.getTime()));
            //  System.out.println("From date:"+ yyyyMMdd_fromdate);
        }

    }





    public static class DatePickerFragmentEndDate extends  DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                    this, year, month, day);

            // dialog.getDatePicker().setMaxDate(c.getTimeInMillis()); // this will set maximum date validation
            //dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            //dialog.getDatePicker().setMaxDate(c.getTimeInMillis());

            // dialog.getDatePicker().setMaxDate(c.getTimeInMillis()+((1000*60*60*24*90)));//Error:fromDate: Sun Jun 18 12:50:44 GMT+05:30 2017 does not precede toDate: Fri Jun 09 02:45:11 GMT+05:30 2017


           /* dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            c.add(Calendar.DAY_OF_MONTH,150);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());*/

            return dialog;


        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar cal = new GregorianCalendar(year, month, day);
            setDate(cal);
        }

        public void setDate(final Calendar calendar) {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            //((TextView) findViewById(R.id.showDate)).setText(dateFormat.format(calendar.getTime()));



            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

            yyyyMMdd_pmenddate = mdyFormat.format(calendar.getTime());


            clickend_pmunapproved_editdate_tv.setText(mdyFormat.format(calendar.getTime()));

            //  System.out.println("To Date:"+ yyyyMMdd_todate);

            int days_count = Days.daysBetween(new LocalDate(yyyyMMdd_pmstartdate), new LocalDate(yyyyMMdd_pmenddate)).getDays();
            int int_days=days_count+1;
            System.out.println ("Days: "+int_days);
            pm_days_linearlayout.setVisibility(View.VISIBLE);

            pm_numberofdays_tv.setText("Number of days:"+String.valueOf(int_days));

           /* if((int_days<=6))
            {
            }
            else{
                //alerts_dialog_fortodate();
            }*/


        }

    }


//----------------------------date------------------------------------



























    @SuppressLint("RestrictedApi")
    private void setProjectTypeSpinner() {
        final ArrayList liststate = new ArrayList();
        liststate.add("Select");
        liststate.add("Education");
        liststate.add("Health Care");
        liststate.add("Sports");

        ArrayAdapter dataAdapterState = new ArrayAdapter(getApplicationContext(), R.layout.simple_spinner_items, liststate);

        // Drop down layout style - list view with radio button
        dataAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
    //    spin_projtype.setAdapter(dataAdapterState);
      //  spin_projtype.setSupportBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorWhite));
    }

    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setPadding(40, 1, 1, 1);

        TextView tv0 = new TextView(this);
        tv0.setText(" Material Name ");
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.LEFT);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(5, 10, 5, 5);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Cost ");
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setPadding(50, 10, 0, 5);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        for (int i = 0; i < 4; i++) {
            TableRow tbrow = new TableRow(this);
            tbrow.setPadding(40, 1, 1, 1);

            tbrow.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            TextView t1v = new TextView(this);
            //  t1v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            t1v.setWidth(700);
            t1v.setText(material[i]);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.LEFT);
            t1v.setPadding(5, 5, 5, 5);

            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(cost[i]);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            t2v.setWidth(100);
            t2v.setPadding(50, 5, 0, 5);
            tbrow.addView(t2v);
            stk.addView(tbrow);
        }

    }

    @SuppressLint("RestrictedApi")
    private void initProjectTypeSpinner(String projectTypeName) {
        ArrayList projType = new ArrayList();

        Set<String> projTypeList = hashProjTypeId.keySet();

        Log.i("tag","projectTypeName"+projectTypeName);

        for(String key: projTypeList){
            Log.d("Key is",key);
            projType.add(key);
        }

/*        projType.add("Education");
        projType.add("Health Care");
        projType.add("Sports");*/

        ArrayAdapter dataAdapter2 = new ArrayAdapter(getApplicationContext(), R.layout.simple_spinner_items, projType);

        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);
        // Drop down layout style - list view with radio button
      //  dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_projectType.setAdapter(dataAdapter2);
        //spin_projectType.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        //spin_projectType.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
     //   spin_projectType.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
        if(projectTypeName!="" || projectTypeName!=null) {
            for (int i = 0; i < spin_projectType.getCount(); i++) {
                if (spin_projectType.getItemAtPosition(i).equals(projectTypeName)) {
                    spin_projectType.setSelection(i);
                    break;
                }
            }
        }


    }

   /* public void initMaterials() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_material);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        String matrl_name = "<u> Material Name </u>";
        tv0.setText(Html.fromHtml(matrl_name));
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.CENTER);
       // tv0.setWidth(500);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(5, 10, 5, 5);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        String matrl_cost = "<u> Cost </u>";
        tv1.setText(Html.fromHtml(matrl_cost));
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTypeface(null, Typeface.BOLD);
    //    tv1.setWidth(300);
        tv1.setPadding(70, 10, 0, 5);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        for (int i = 0; i < lstMaterialName.size() && i < lstMaterialCost.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(lstMaterialName.get(i));
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
           // t1v.setWidth(500);
            t1v.setPadding(5, 5, 5, 5);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(lstMaterialCost.get(i));
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
          //  t2v.setWidth(100);
            t2v.setPadding(70, 5, 0, 5);
            tbrow.addView(t2v);

            stk.addView(tbrow);
        }

      TableRow tbrow = new TableRow(this);
        TextView t1v = new TextView(this);
        t1v.setText("Total");
        t1v.setTypeface(null, Typeface.BOLD);
        t1v.setTextColor(Color.WHITE);
        t1v.setGravity(Gravity.CENTER);
      //  t1v.setWidth(700);
        t1v.setPadding(5, 5, 5, 5);
        tbrow.addView(t1v);

        TextView t2v = new TextView(this);
        t2v.setText(str_totalCost);
        t2v.setTextColor(Color.WHITE);
        t2v.setTypeface(null, Typeface.BOLD);
        t2v.setGravity(Gravity.CENTER);
        //t2v.setWidth(100);
        t2v.setPadding(70, 5, 0, 5);
        tbrow.addView(t2v);
        stk.addView(tbrow);

      //  txt_TotalCost.setText(str_totalCost);
    }*/

    public void initMaterials() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_material);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        String matrl_name = "<u> Material Name </u>";
        tv0.setText(Html.fromHtml(matrl_name));
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(5, 10, 5, 5);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        String matrl_cost = "<u> Cost </u>";
        tv1.setText(Html.fromHtml(matrl_cost));
        tv1.setTextColor(Color.BLACK);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setPadding(50, 10, 0, 5);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        for (int i = 0; i < lstMaterialName.size() && i < lstMaterialCost.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(lstMaterialName.get(i));
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.LEFT);
            t1v.setPadding(5, 5, 5, 5);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(lstMaterialCost.get(i));
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setPadding(50, 5, 0, 5);
            tbrow.addView(t2v);

            stk.addView(tbrow);
        }

        TableRow tbrow = new TableRow(this);
        TextView t1v = new TextView(this);
        t1v.setText("Total");
        t1v.setTypeface(null, Typeface.BOLD);
        t1v.setTextColor(Color.BLACK);
        t1v.setGravity(Gravity.LEFT);
        t1v.setPadding(5, 5, 5, 5);
        tbrow.addView(t1v);

        TextView t2v = new TextView(this);
        t2v.setText(str_totalCost);
        t2v.setTextColor(Color.BLACK);
        t2v.setTypeface(null, Typeface.BOLD);
        t2v.setGravity(Gravity.CENTER);
        t2v.setPadding(50, 5, 0, 5);
        tbrow.addView(t2v);

        stk.addView(tbrow);
    }

    public void initMembers() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        String member_name = "<u> Member Name </u>";
        tv0.setText(Html.fromHtml(member_name));
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(5, 10, 5, 5);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        String member_MailId = "<u> Mail Id </u>";
        tv1.setText(Html.fromHtml(member_MailId));
        tv1.setTextColor(Color.BLACK);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setPadding(50, 10, 0, 5);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        for (int i = 0; i < lstMemberName.size() && i < lstMemberEmail.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(lstMemberName.get(i));
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.LEFT);
            t1v.setPadding(5, 5, 5, 5);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(lstMemberEmail.get(i));
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setPadding(50, 5, 0, 5);
            tbrow.addView(t2v);

            stk.addView(tbrow);
        }

    }



    //Date fragment










    //Date Fragment




    /* public void inintMembers() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        String matrl_name = "<u> Member Names </u>";
        tv0.setText(Html.fromHtml(matrl_name));
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.CENTER);
        // tv0.setWidth(500);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(5, 10, 5, 5);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        String matrl_cost = "<u> Email </u>";
        tv1.setText(Html.fromHtml(matrl_cost));
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTypeface(null, Typeface.BOLD);
        //    tv1.setWidth(300);
        tv1.setPadding(70, 10, 0, 5);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        for (int i = 0; i < lstMemberName.size() && i < lstMemberEmail.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(lstMemberName.get(i));
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);
            // t1v.setWidth(500);
            t1v.setPadding(5, 5, 5, 5);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(lstMemberEmail.get(i));
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            //  t2v.setWidth(100);
            t2v.setPadding(70, 5, 0, 5);
            tbrow.addView(t2v);

            stk.addView(tbrow);
        }

     *//*   TableRow tbrow = new TableRow(this);
        TextView t1v = new TextView(this);
        t1v.setText("Total");
        t1v.setTypeface(null, Typeface.BOLD);
        t1v.setTextColor(Color.WHITE);
        t1v.setGravity(Gravity.CENTER);
        //  t1v.setWidth(700);
        t1v.setPadding(5, 5, 5, 5);
        tbrow.addView(t1v);

        TextView t2v = new TextView(this);
        t2v.setText(str_totalCost);
        t2v.setTextColor(Color.WHITE);
        t2v.setTypeface(null, Typeface.BOLD);
        t2v.setGravity(Gravity.CENTER);
        //t2v.setWidth(100);
        t2v.setPadding(70, 5, 0, 5);
        tbrow.addView(t2v);
        stk.addView(tbrow);
*//*
        //  txt_TotalCost.setText(str_totalCost);
    }
*/
    /*    @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();


            if (id == R.id.action_editProfile) {
                Intent itthomeToEditProfile = new Intent(PMUnapproved_DetailsActivity.this ,PMEditProfileActivity.class);
                startActivity(itthomeToEditProfile);
                return true;
            }

            if (id == R.id.action_logout) {
                Intent itthomeToLogin = new Intent(PMUnapproved_DetailsActivity.this ,LoginActivity.class);
                startActivity(itthomeToLogin);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    */
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


    /*    if (id == R.id.action_editProfile) {
            Intent ittProjDtlsToEditProfile = new Intent(PMUnapproved_DetailsActivity.this ,PMEditProfileActivity.class);
            ittProjDtlsToEditProfile.putExtra("ManagerId",ManagerId);
            startActivity(ittProjDtlsToEditProfile);
            return true;
        }
        */

        if (id == R.id.action_logout) {
            Intent ittProjDtlsToLogin = new Intent(PMUnapproved_DetailsActivity.this ,LoginActivity.class);
            startActivity(ittProjDtlsToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(PMUnapproved_DetailsActivity.this ,PMProjectDetailActivity.class);
            ittProjDtlsToHome.putExtra("pageCount",0);
            startActivity(ittProjDtlsToHome);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }
}
