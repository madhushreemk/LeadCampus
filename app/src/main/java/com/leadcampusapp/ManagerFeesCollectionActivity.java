package com.leadcampusapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManagerFeesCollectionActivity extends AppCompatActivity {

    EditText etName,etLeadId,etMobile,emailid_ET,edt_paymentDate,amount_ET,remark_ET,dobseterror_TV;
    AppCompatSpinner spin_feesCategory,spin_state,spin_district,spin_city,spin_program,spin_institution,spin_paymentmode;
    Class_States[] arrayObj_Class_States, arrayObj_Class_States2;
    Class_States obj_Class_States;
    int statelistcount;
    String str_StateStatus="x";
    String[] state_stringArray;

    Class_District[] arrayObj_Class_District, arrayObj_Class_District2;
    Class_District obj_Class_District;
    int districtlistcount;
    String str_DistrictStatus="x";

    Class_Taluk[] arrayObj_Class_Taluk, arrayObj_Class_Taluk2;
    Class_Taluk obj_Class_Taluk;
    int taluklistcount;
    String str_TalukStatus="x";

    Class_Colleges[] arrayObj_Class_Colleges, arrayObj_Class_Colleges2,arrayObj_Class_Colleges3;
    Class_Colleges obj_Class_Colleges;
    int collegeslistcount;
    String str_CollegesStatus;

    Class_FeesCatg[] arrayObj_Class_FeesCatg, arrayObj_Class_FeesCatg2;
    Class_FeesCatg obj_Class_FeesCatg;
    int feesCatglistcount;
    String str_FeesCatgStatus="x";

    Class_PaymentMode[] arrayObj_Class_PaymentMode, arrayObj_Class_PaymentMode2;
    Class_PaymentMode obj_Class_PaymentMode;
    int PaymentModelistcount;
    String str_PaymentModeStatus="x";

    Class_Course[] arrayObj_Class_Course, arrayObj_Class_Course2;
    Class_Course obj_Class_Course;
    int courselistcount;
    String str_CourseStatus;
    String str_courseid,str_coursename;

    Class_Program[] arrayObj_Class_Program, arrayObj_Class_Program2;
    Class_Program obj_Class_Program;
    int programlistcount;
    String str_ProgramStatus;
    String str_programid,str_programname="x";

    String O_statename,O_districtname,O_talukname,O_programmename,O_coursename,O_semname,O_collegename;
    String O_statecode,O_districtcode,O_talukacode,O_collegenameCode,O_coursecode,O_StreamprogramCode,O_MailId,O_leadID;
   static String O_StudentName = null;
    static String O_RegistrationId="0";
    static String O_MobileNo=null;
    TextView stateerror_tv,districterror_tv,cityerror_tv,collegeerror_tv,courseerror_tv,semerror_tv,paymentmodeerror_TV;
    String str_Cityids,str_ColID,str_Sids,str_Dids;
    String str_MobileNo,str_RegID,str_LeadID="0",str_studenttype,str_academiccode;


    ImageView search_bt;
    Button btn_submit;
    RadioGroup registration_radiogroup;
    RadioButton rdb_register,rdb_newRegister;
    String str_Reg,str_newReg,str_feesCatSlno,str_feesCatName,str_feesId,str_feesAmount="0",str_PaymentModeID,str_PaymentModeName;
    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;
    private Context context;

    String str_isProfileEdited,str_academicId;

    SharedPreferences shardpref_S_obj;
    SharedPreferences.Editor editor_S;

    public static final String  PREFBook_Stud= "prefbook_stud";
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_S_isprofileEdited = "prefid_isprofileEdited";
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefId_S_AcademicId = "prefid_sacademicid";

    SharedPreferences shardprefPM_obj;
    SharedPreferences.Editor editor_PM;
    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //

    String str_MangerID,str_RegistrationId;
    Integer MDId;

    ArrayAdapter dataAdapter, dataAdapter_district, dataAdapter_city, dataAdapter_college,dataAdapter_feesCatg,dataAdapter_semlist;
    ArrayAdapter dataAdapter_course,dataAdapter_program,dataAdapter_bloodgroup;

    Class_InternetDectector internetDectector, internetDectector2;
    Boolean isInternetPresent = false;
    Boolean isInternetPresent2 = false;


    String internet_issue = "false";
    String internet_issue1="Noerror";
    int isNewRegistration;
    RelativeLayout leadId_rl;
    String searchStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_fees_collection);

        context = ManagerFeesCollectionActivity.this;
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));


        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Manager Fees Collection");

        spin_feesCategory = (AppCompatSpinner) findViewById(R.id.spin_feesCategory);
        spin_state = (AppCompatSpinner) findViewById(R.id.spin_state);
        stateerror_tv = (TextView) findViewById(R.id.stateerror_TV);
        spin_district = (AppCompatSpinner) findViewById(R.id.spin_district);
        districterror_tv = (TextView) findViewById(R.id.districterror_TV);
        spin_city = (AppCompatSpinner) findViewById(R.id.spin_city);
        cityerror_tv = (TextView) findViewById(R.id.cityerror_TV);
        spin_program = (AppCompatSpinner) findViewById(R.id.spin_program);
        courseerror_tv = (TextView) findViewById(R.id.courselabel_TV);
        spin_institution = (AppCompatSpinner) findViewById(R.id.spin_institution);
        collegeerror_tv = (TextView) findViewById(R.id.collegeerror_TV);
        etLeadId = (EditText) findViewById(R.id.etLeadId);
        search_bt = (ImageView) findViewById(R.id.search_bt);
        etName = (EditText) findViewById(R.id.etName);
        etMobile = (EditText) findViewById(R.id.etMobile);
        emailid_ET = (EditText) findViewById(R.id.emailid_ET);
        edt_paymentDate = (EditText) findViewById(R.id.edt_paymentDate);
        dobseterror_TV = (EditText) findViewById(R.id.dobseterror_TV);
        amount_ET = (EditText) findViewById(R.id.amount_ET);
        spin_paymentmode =(AppCompatSpinner) findViewById(R.id.spin_paymentmode);
        paymentmodeerror_TV = (TextView) findViewById(R.id.paymentmodeerror_TV);
        remark_ET =(EditText)findViewById(R.id.remark_ET);
        registration_radiogroup = (RadioGroup) findViewById(R.id.registration_radiogroup);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        rdb_register=(RadioButton) findViewById(R.id.rdb_register);
        rdb_newRegister=(RadioButton)findViewById(R.id.rdb_newRegister);
        leadId_rl=(RelativeLayout) findViewById(R.id.leadId_rl);

        shardpref_S_obj=this.getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);

        shardpref_S_obj.getString(PrefID_S_isprofileEdited, "").trim();
        str_isProfileEdited = shardpref_S_obj.getString(PrefID_S_isprofileEdited, "").trim();
        shardpref_S_obj.getString(PrefId_S_AcademicId, "").trim();
        str_academicId = shardpref_S_obj.getString(PrefId_S_AcademicId, "").trim();
        Log.d("str_academicId:",str_academicId);

        shardprefPM_obj= getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MangerID:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);
        /*str_LeadID = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.e("tag","str_LeadID="+str_LeadID);*/

        shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_RegistrationId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        Log.d("str_RegistrationId:",str_RegistrationId);

        etMobile.setEnabled(false);
        etMobile.setClickable(false);
        amount_ET.setClickable(false);
        amount_ET.setEnabled(false);

        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();
        if(isInternetPresent){
            getFeesCatglist_AsynTask();
            getPaymentModelist_AsynTask();
            getstatelist_AsynTask();

        }
        if(rdb_newRegister.isChecked()){
            Log.e("tag","rdb_newRegister="+rdb_newRegister.isChecked());
            leadId_rl.setVisibility(View.GONE);
            etMobile.setEnabled(true);
            etMobile.setClickable(true);
        }else{
            Log.e("tag","rdb_newRegister="+rdb_newRegister.isChecked());
            leadId_rl.setVisibility(View.VISIBLE);
            etMobile.setEnabled(false);
            etMobile.setClickable(false);
        }
        setPaymentDate();
        spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub

                obj_Class_States = (Class_States) spin_state.getSelectedItem();
                str_Sids = obj_Class_States.getstate_id().toString();
                spin_city.setAdapter(null);
                spin_program.setAdapter(null);
                spin_institution.setAdapter(null);
              //  spin_course.setAdapter(null);
                //spin_semester.setAdapter(null);

                str_Cityids=str_ColID=str_Dids=str_courseid=str_programid="x";
                //Toast.makeText(getApplicationContext(),"State"+spin_state.getSelectedItem().toString()+"ID:"+str_Sids,Toast.LENGTH_SHORT).show();

                //  Toast.makeText(getApplicationContext(),"State:"+spin_state.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                if(!( (str_Sids.equals("x"))|| (str_Sids.equals("0")) ))
                { stateerror_tv.setText("");
                    stateerror_tv.setVisibility(View.GONE);
                    getdistrictlist_AsynTask();
                }

            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });

        spin_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_District = (Class_District) spin_district.getSelectedItem();
                str_Dids = obj_Class_District.getdistrict_id().toString();
                // Toast.makeText(getApplicationContext(),"District ID"+str_Dids+"State:"+spin_district.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

                //Toast.makeText(getApplicationContext(),"District ID:"+spin_district.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

                spin_city.setAdapter(null);
                spin_program.setAdapter(null);
                spin_institution.setAdapter(null);
            //    spin_course.setAdapter(null);
              //  spin_semester.setAdapter(null);

                // str_Cityids="x";
                if(!(str_Dids.equals("x")))
                { districterror_tv.setVisibility(View.GONE);
                    getTaluklist_AsynTask();
                }
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });

        spin_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_Taluk = (Class_Taluk) spin_city.getSelectedItem();
                str_Cityids = obj_Class_Taluk.gettaluk_id().toString();
                //  Toast.makeText(getApplicationContext(),"City ID"+str_Cityids+"City:"+spin_city.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

                if(!(str_Cityids.equals("x"))|| !(spin_district.getSelectedItem().toString().equals("Select City")))
                { cityerror_tv.setVisibility(View.GONE); }
                getProgramlist_AsynTask();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });

        //Course
        spin_program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_Program = (Class_Program) spin_program.getSelectedItem();

                str_programid = obj_Class_Program.getprogram_id().toString();
                str_programname = obj_Class_Program.getprog_name().toString();

                if(!(str_programname.equals("x")))
                {
                    if(!(spin_program.getSelectedItem().toString().equals("Select Course"))) {
                        getCollegelist_AsynTask();
                    }
                }
                // Toast.makeText(getApplicationContext(),"str_Programsid: "+str_programid,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });

        spin_institution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub

                if(str_CollegesStatus.equals("Fail")||str_CollegesStatus.equals("There are no colleges")||str_ColID.equals("x"))
                {
                    Log.e("In strCollegesStatus","In str_CollegesStatus");
                    amount_ET.setText("0");
                }
                else {
                    obj_Class_Colleges = (Class_Colleges) spin_institution.getSelectedItem();
                    str_ColID = obj_Class_Colleges.getcollege_id().toString();
                    amount_ET.setText(obj_Class_Colleges.getFees().toString());
                    str_feesId = obj_Class_Colleges.getFees_Id().toString();
                    str_feesAmount = obj_Class_Colleges.getFees().toString();
                }


                if(!(str_ColID.equals("x")) || !(spin_institution.getSelectedItem().toString().equals("Select Institution"))||!(spin_institution.getSelectedItem().toString().equals("There are no colleges")))
                {
                    Log.e("str_ColID",str_ColID.toString());
                    Log.e("Institution",spin_institution.getSelectedItem().toString());
                    collegeerror_tv.setVisibility(View.GONE);
                //    getCourselist_AsynTask();
                }
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

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
                if(str_feesCatName.equalsIgnoreCase("Registration")){
                    registration_radiogroup.setVisibility(View.VISIBLE);
                }else{
                    registration_radiogroup.setVisibility(View.GONE);
                }
                // Toast.makeText(getApplicationContext(),"str_Programsid: "+str_programid,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });
        /*if(rdb_newRegister.isChecked()){
            Log.e("tag","rdb_newRegister="+rdb_newRegister.isChecked());
            leadId_rl.setVisibility(View.GONE);
        }else{
            Log.e("tag","rdb_newRegister="+rdb_newRegister.isChecked());
            leadId_rl.setVisibility(View.VISIBLE);
        }*/


        spin_paymentmode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_PaymentMode = (Class_PaymentMode) spin_paymentmode.getSelectedItem();

                str_PaymentModeID = obj_Class_PaymentMode.getPayment_mode_slno().toString();
                str_PaymentModeName = obj_Class_PaymentMode.getDescription().toString();

                Log.e("tag","str_PaymentModeName="+str_PaymentModeName);
                // Toast.makeText(getApplicationContext(),"str_Programsid: "+str_programid,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });


        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_LeadID=etLeadId.getText().toString();
                Log.e("tag","str_LeadID="+str_LeadID);
                if(etLeadId.getText().length()==0||str_LeadID==null){
                    etLeadId.setError("Enter Lead ID");
                    etLeadId.requestFocus();
                  //  Toast.makeText(getApplicationContext(),"Enter Lead ID",Toast.LENGTH_LONG).show();
                }else {
                    getStudentDetails();
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation())
                {
                    internetDectector2 = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent2 = internetDectector2.isConnectingToInternet();

                    if (isInternetPresent2)
                    {
                        SubmitAcknowledgements submitAcknowledgements=new SubmitAcknowledgements(ManagerFeesCollectionActivity.this);
                        submitAcknowledgements.execute();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
    } // End onCreate

    public void onRadioButtonNewRegClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rdb_register: if(checked){
                leadId_rl.setVisibility(View.VISIBLE);
                etMobile.setEnabled(false);
                etMobile.setClickable(false);
            } break;
            case R.id.rdb_newRegister: if (checked){
                leadId_rl.setVisibility(View.GONE);
                etMobile.setEnabled(true);
                etMobile.setClickable(true);

            } break;
        }
    }

    public boolean validation()
    {
        boolean validationresult = true;

        boolean b_studenname,b_alernativecell,b_emailid,b_dob,b_aadhara1,b_aadhara2,b_account1,b_account2,b_iifscode1,b_iifscode2;
        boolean b_state,b_district,b_city,b_college,b_course,b_sem,b_programid,b_mytalent;

        b_studenname=b_alernativecell=b_emailid=b_dob=b_aadhara1=b_aadhara2=b_account1=b_account2=b_iifscode1=b_iifscode2=true;
        b_state=b_district=b_city=b_college=b_course=b_sem=b_programid=b_mytalent=true;

        if (etName.getText().toString().length() == 0 || etName.getText().toString().length() <= 2 || etName.getText().toString().trim().length() == 0) {
            etName.setError("InValid Student name");
            etName.requestFocus();
            b_studenname=false;
            validationresult = false;
        }

        if (etMobile.getText().toString().length() == 0 || etMobile.getText().toString().length() <= 9) {
            etMobile.setError("InValid Mobile number");
            //alernativecell_et.requestFocus();
            b_alernativecell=false;
            validationresult = false;
        }

        if (emailid_ET.getText().toString().length() == 0 ||emailid_ET.getText().toString().length()<=5) {
            emailid_ET.setError("Enter Valid EmailID!");
            //emailid_et.requestFocus();
            b_emailid=false;
            validationresult = false;
        }

/*

        if (edt_paymentDate.getText().toString().length() == 0 ||edt_paymentDate.getText().toString().length()<=5)
        {
            dobseterror_TV.setVisibility(View.VISIBLE);
            dobseterror_TV.setError("Enter the Payment Date!");
            b_dob=false;
            validationresult = false;
        }
*/




        if(str_Sids.equals("x")||spin_state.getSelectedItem().toString().equals("Select State"))
        { //Toast.makeText(getApplicationContext(),"select the state",Toast.LENGTH_SHORT).show();
            // ((TextView)spin_state.getSelectedView()).setError("Error message");
            stateerror_tv.setVisibility(View.VISIBLE);
            stateerror_tv.setError("Select State");
            b_state=false;
            validationresult = false;
        }
        if(str_Dids.equals("x")||spin_district.getSelectedItem().toString().equals("Select District"))
        { districterror_tv.setVisibility(View.VISIBLE);
            districterror_tv.setError("Select District");
            //Toast.makeText(getApplicationContext(),"select the District",Toast.LENGTH_SHORT).show();
            b_district=false;
            validationresult = false;}

        if(str_Cityids.equals("x")||spin_city.getSelectedItem().toString().equals("Select City"))
        {   cityerror_tv.setVisibility(View.VISIBLE);
            cityerror_tv.setError("Select City");
            // Toast.makeText(getApplicationContext(),"select the City",Toast.LENGTH_SHORT).show();
            b_city=false;
            validationresult = false;
        }
        if(str_programid.equals("x"))
        { b_programid=false;
            //Toast.makeText(getApplicationContext(),"select the Program",Toast.LENGTH_SHORT).show();
            validationresult = false;}

        if(str_ColID.equals("x")||spin_institution.getSelectedItem().toString().equals("Select Institution")||spin_institution.getSelectedItem().toString().equals("There are no colleges")||spin_institution.getSelectedItem().toString().equals("WS Exception"))
        // if(spin_institution.getSelectedItem().toString().equals("Select Institution")||spin_institution.getSelectedItem().toString().equals("There are no colleges")||spin_institution.getSelectedItem().toString().equals("WS Exception"))
        {     b_college=false;
            collegeerror_tv.setVisibility(View.VISIBLE);
            collegeerror_tv.setError("Select Institution");
            //Toast.makeText(getApplicationContext(),"select the College",Toast.LENGTH_SHORT).show();
            validationresult = false;
        }

        if(str_programid.equals("x")||spin_program.getSelectedItem().toString().equals("Select Course"))
        {   courseerror_tv.setVisibility(View.VISIBLE);
            courseerror_tv.setError("Select Course");
            //Toast.makeText(getApplicationContext(),"select the Course",Toast.LENGTH_SHORT).show();
            b_college=false;
            validationresult = false;}

        if(str_PaymentModeID.equals("x")||spin_paymentmode.getSelectedItem().toString().equalsIgnoreCase("[SELECT]"))
        {   paymentmodeerror_TV.setVisibility(View.VISIBLE);
            paymentmodeerror_TV.setError("Select Payment Mode");
            //Toast.makeText(getApplicationContext(),"select the Course",Toast.LENGTH_SHORT).show();
            b_college=false;
            validationresult = false;}

        if(spin_paymentmode.getSelectedItem().toString().equalsIgnoreCase("Online")){
            if(remark_ET.getText().length()==0) {
                remark_ET.setVisibility(View.VISIBLE);
                remark_ET.setError("Empty not allowed");
                b_college=false;
                validationresult = false;
            }
            //Toast.makeText(getApplicationContext(),"select the Course",Toast.LENGTH_SHORT).show();

        }
        //return validationresult;
        return (b_studenname&&b_alernativecell&&b_emailid&&b_dob&&b_aadhara1&&b_aadhara2&&b_account1&&b_account2&&b_iifscode1
                &&b_iifscode2&&b_state&&b_district&&b_city&&b_college&&b_course&&b_sem&&b_programid&&b_mytalent);
    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void onRadioButtonGenderClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_male:
                if (checked) {
                    str_Reg="M";
                }
                break;
            case R.id.rdb_female:
                if (checked) {
                    str_newReg="F";
                }
                break;
        }
    }

    private void setPaymentDate() {

        final Calendar c = Calendar.getInstance();

        edt_paymentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context, R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                cDay = dayOfMonth;
                                cMonth = monthOfYear;
                                cYear = year;

                                // String date =dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                String date =year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;


                                // SimpleDateFormat dateFormat= new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");

                                try {
                                    Date d=dateFormat.parse(date);
                                    System.out.println("Formated"+dateFormat.format(d));
                                    dobseterror_TV.setVisibility(View.GONE);
                                    edt_paymentDate.setText(dateFormat.format(d).toString());

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
            }
        });


    }

    // ============================All AsynTask Call

    public void getFeesCatglist_AsynTask()
    {
        FeesCatglistAsyncCallWS task = new FeesCatglistAsyncCallWS(ManagerFeesCollectionActivity.this);
        task.execute();
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

        public FeesCatglistAsyncCallWS(ManagerFeesCollectionActivity activity) {
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

//                if(str_isProfileEdited.equals("1"))
//                {
//                    dataAdapter_feesCatg = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_FeesCatg);
//                    dataAdapter_feesCatg.setDropDownViewResource(R.layout.spinnercustomstyle);
//                    spin_district.setAdapter(dataAdapter_feesCatg);
//                    spin_district.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
//
//                   // if(O_districtname!="" || O_districtname!=null) {
//                        for (int i = 0; i < spin_feesCategory.getCount(); i++) {
//                            Log.e("spin District",spin_feesCategory.getItemAtPosition(i).toString());
//                         //   if (spin_feesCategory.getItemAtPosition(i).toString().equals(O_districtname))
//                           // {
//                                spin_feesCategory.setSelection(i);
//                              //  break;
//                            //}
//                       // }
//                    }
//
//
//                }
//                else
//                {
                    dataAdapter_feesCatg = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_FeesCatg);
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
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails

    public void getstatelist_AsynTask()
    {
        StatelistAsyncCallWS task = new StatelistAsyncCallWS(ManagerFeesCollectionActivity.this);
        task.execute();
    }

    private class StatelistAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            /*dialog.setMessage("Please wait,State Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            GetStatelist();  // get the state list
            return null;
        }

        public StatelistAsyncCallWS(ManagerFeesCollectionActivity activity) {
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

            //uploadfromDB_Statelist();


            if(str_StateStatus.equals("Success"))
            {

                // if(str_isProfileEdited.equals("1"))
                Log.e("tag","str_LeadID=="+str_LeadID);
                if(!str_LeadID.equals("0"))
                {
                    Log.e("tag","1O_statecode="+O_statecode);

                    dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_States);
                    dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_state.setAdapter(dataAdapter);
                    spin_state.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                    int spinnerposition =1;
                    //spinnerposition = dataAdapter.getPosition(O_statecode);
                   // spinnerposition= Integer.parseInt(O_statecode);
                    //                        spin_state.setSelection(spinnerposition);
                    if(!O_statename.equals("")||!O_statename.equalsIgnoreCase(null)) {
                        spin_state.setSelection(getIndex(spin_state, O_statename));
                        /*for (int i = 0; i < spin_state.getCount(); i++) {
                            Log.e(" spin_state=", spin_state.getItemAtPosition(i).toString());
                            if (spin_state.getItemAtPosition(i).toString().equals(O_statecode)) {
                                spin_state.setSelection(i);
                                break;
                            }
                        }*/
                    }
                   // spin_state.setSelection(getIndex(spin_state, O_statecode));

                    Log.e("tag","O_statecode="+O_statecode);
                    // spin_state.setSelection(spinnerposition-1);
                }
                else
                {
                    dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_States);
                    dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
        /*class_hintspinneradapter_obj = new Class_HintSpinnerAdapter(getApplicationContext(), R.layout.spinnercustomstyle);
        class_hintspinneradapter_obj.addAll(state_stringArray);
        */
                    //class_hintspinneradapter_obj
                    //dataAdapter.add("Select State");
                    //spin_state.setPrompt("Select State");

                    spin_state.setAdapter(dataAdapter); //working

       /*class_hintspinneradapter_obj.add("Select State");
        spin_state.setAdapter(class_hintspinneradapter_obj);
        spin_state.setSelection(class_hintspinneradapter_obj.getCount());*/

                    spin_state.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                }

            }

        }//end of onPostExecute
    }// end Async task

    public void GetStatelist()
    {
        String URL =Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "GetstateList";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/GetstateList";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("stateid", "0");//<stateid>long</stateid>
            request.addProperty("Manager_Id",MDId);

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
                SoapObject o_stateresponse =(SoapObject)response.getProperty(0);
                str_StateStatus = o_stateresponse.getProperty("Status").toString();

                Log.e("response state list",response.toString());
                statelistcount = response.getPropertyCount();

                Log.d("statecount", String.valueOf(response.getPropertyCount()));


           /*<Id>1</Id>
           <State>Andaman and Nicobar Islands</State>
           <Status>Success</Status>

           */
                if(str_StateStatus.equals("Success")) {
                    if (statelistcount > 0) {
                        // arrayObj_Class_States = new Class_States[statelistcount];


                        arrayObj_Class_States = new Class_States[statelistcount];
                        state_stringArray = new String[statelistcount];

                        /*Class_States innerObj_Class_states1 = new Class_States();
                        innerObj_Class_states1.setstate_id("0"); //<code>long</code>
                        innerObj_Class_states1.setstate_name("Select State Name"); //<StateName>Andaman and Nicobar Islands</StateName>
                        innerObj_Class_states1.setstate_status("Success Status");// <Status>Success</Status>
                        arrayObj_Class_States[0]=innerObj_Class_states1;*/

                        for (int i = 0; i < statelistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_States innerObj_Class_states = new Class_States();
                            innerObj_Class_states.setstate_id(response_soapobj.getProperty("code").toString()); //<code>long</code>
                            innerObj_Class_states.setstate_name(response_soapobj.getProperty("StateName").toString()); //<StateName>Andaman and Nicobar Islands</StateName>
                            innerObj_Class_states.setstate_status(response_soapobj.getProperty("Status").toString());// <Status>Success</Status>

                            arrayObj_Class_States[i] = innerObj_Class_states;


                            String str_stateID = response_soapobj.getProperty("code").toString();
                            String str_statename = response_soapobj.getProperty("StateName").toString();
                            //String str_Status=response_soapobj.getProperty("Status").toString();

                            // DBCreate_Statedetails_insert_2SQLiteDB(str_stateID,str_statename);

                            state_stringArray[i]=response_soapobj.getProperty("StateName").toString();

                        }//end for loop

                    }//end of if
                }

            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails

    public void getPaymentModelist_AsynTask()
    {
        PaymentModelistAsyncCallWS task = new PaymentModelistAsyncCallWS(ManagerFeesCollectionActivity.this);
        task.execute();
    }

    private class PaymentModelistAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            /*dialog.setMessage("Please wait,State Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            GetPaymentModelist();  // get the state list
            return null;
        }

        public PaymentModelistAsyncCallWS(ManagerFeesCollectionActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result) {


            if(str_PaymentModeStatus.equals("Success"))
            {


                    dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_PaymentMode);
                    dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);

                    spin_paymentmode.setAdapter(dataAdapter); //working
                    spin_paymentmode.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

            }

        }//end of onPostExecute
    }// end Async task

    public void GetPaymentModelist()
    {
        String URL =Class_URL.URL_Manager.toString().trim();
        String METHOD_NAME = "Get_Payment_Mode";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/Get_Payment_Mode";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

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
                SoapObject o_PaymentModeresponse =(SoapObject)response.getProperty(0);
                str_PaymentModeStatus = o_PaymentModeresponse.getProperty("Status").toString();

                Log.e("tag","response payment mode list"+response.toString());
                PaymentModelistcount = response.getPropertyCount();

                Log.d("paymentmode count", String.valueOf(response.getPropertyCount()));


           /*<Id>1</Id>
           <State>Andaman and Nicobar Islands</State>
           <Status>Success</Status>

           */
                if(str_PaymentModeStatus.equals("Success")) {
                    if (PaymentModelistcount > 0) {
                        // arrayObj_Class_States = new Class_States[statelistcount];


                        arrayObj_Class_PaymentMode = new Class_PaymentMode[PaymentModelistcount];
                      //  state_stringArray = new String[statelistcount];

                        /*Class_States innerObj_Class_states1 = new Class_States();
                        innerObj_Class_states1.setstate_id("0"); //<code>long</code>
                        innerObj_Class_states1.setstate_name("Select State Name"); //<StateName>Andaman and Nicobar Islands</StateName>
                        innerObj_Class_states1.setstate_status("Success Status");// <Status>Success</Status>
                        arrayObj_Class_States[0]=innerObj_Class_states1;*/

                        for (int i = 0; i < PaymentModelistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_PaymentMode innerObj_Class_paymentmode = new Class_PaymentMode();
                            innerObj_Class_paymentmode.setDescription(response_soapobj.getProperty("description").toString()); //<code>long</code>
                            innerObj_Class_paymentmode.setPayment_mode_slno(response_soapobj.getProperty("payment_mode_slno").toString()); //<StateName>Andaman and Nicobar Islands</StateName>
                            innerObj_Class_paymentmode.setShort_code(response_soapobj.getProperty("short_code").toString());// <Status>Success</Status>
                            innerObj_Class_paymentmode.setStatus(response_soapobj.getProperty("Status").toString()); //<StateName>Andaman and Nicobar Islands</StateName>

                            arrayObj_Class_PaymentMode[i] = innerObj_Class_paymentmode;


                           /* String str_stateID = response_soapobj.getProperty("code").toString();
                            String str_statename = response_soapobj.getProperty("StateName").toString();*/
                            //String str_Status=response_soapobj.getProperty("Status").toString();

                            // DBCreate_Statedetails_insert_2SQLiteDB(str_stateID,str_statename);

                           // state_stringArray[i]=response_soapobj.getProperty("StateName").toString();

                        }//end for loop

                    }//end of if
                }

            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails

    public void getdistrictlist_AsynTask()
    {
        DistrictlistAsyncCallWS task = new DistrictlistAsyncCallWS(ManagerFeesCollectionActivity.this);
        task.execute();
    }

    private class DistrictlistAsyncCallWS extends AsyncTask<String, Void, Void> {
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

            GetDistrictlist();  // get the District list
            return null;
        }

        public DistrictlistAsyncCallWS(ManagerFeesCollectionActivity activity) {
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


            if(str_DistrictStatus.equals("Success"))
            {

              //  if(str_isProfileEdited.equals("1"))
                if(!str_LeadID.equals("0"))
                {
                    dataAdapter_district = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_District);
                    dataAdapter_district.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_district.setAdapter(dataAdapter_district);
                    spin_district.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                    if(O_districtname!="" || O_districtname!=null) {
                        for (int i = 0; i < spin_district.getCount(); i++) {
                            Log.e("spin District",spin_district.getItemAtPosition(i).toString());
                            if (spin_district.getItemAtPosition(i).toString().equals(O_districtname))
                            {
                                spin_district.setSelection(i);
                                break;
                            }
                        }
                    }


                }
                else
                {
                    dataAdapter_district = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_District);
                    //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_district.setDropDownViewResource(R.layout.spinnercustomstyle);
                    //  districtsearch_ATV.setAdapter(dataAdapter_district);
                    spin_district.setAdapter(dataAdapter_district);
                    spin_district.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                }
            }


        }//end of onPostExecute
    }// end Async task

    public void GetDistrictlist()
    {

        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "GetDistricts";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/GetDistricts";



        //for final


        try {
//vijay district
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            //  request.addProperty("distid", Long.parseLong(str_Sids));//Long

            // request.addProperty("distid", 17);//Long

            request.addProperty("stateId", Long.parseLong(str_Sids));//Long
            request.addProperty("Manager_Id",MDId);
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
                Log.e("response districtlist",response.toString());
                districtlistcount = response.getPropertyCount();
                SoapObject districtresponse=(SoapObject)response.getProperty(0);
                str_DistrictStatus=districtresponse.getProperty("status").toString();
                Log.d("districtcount", String.valueOf(response.getPropertyCount()));



                //anyType{vmdist=anyType{DistrictId=1; DistrictName=Nicobar; Stateid=1; status=Success;

          /*
           <DistrictId>1</DistrictId>
           <DistrictName>Nicobar</DistrictName>
            <Status>Success</Status>
            <Stateid>1</Stateid>*/
                if(str_DistrictStatus.equals("Success")) {
                    if (districtlistcount > 0) {
                        arrayObj_Class_District = new Class_District[districtlistcount];
                        for (int i = 0; i < districtlistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_District innerObj_Class_district = new Class_District();
                            innerObj_Class_district.setdistrict_id(response_soapobj.getProperty("DistrictId").toString()); //<Id>1</Id>
                            innerObj_Class_district.setdistrict_name(response_soapobj.getProperty("DistrictName").toString()); //<Centre_Code>Hubballi</Centre_Code>
                            innerObj_Class_district.setdistrict_status(response_soapobj.getProperty("status").toString());// <Centre_Name>HB</Centre_Name>
                            innerObj_Class_district.setdstateid_id(response_soapobj.getProperty("Stateid").toString());// <<Stateid>1</Stateid>

                            str_DistrictStatus = response_soapobj.getProperty("status").toString();

                            arrayObj_Class_District[i] = innerObj_Class_district;


                            String str_districtID = response_soapobj.getProperty("DistrictId").toString();
                            String str_districtname = response_soapobj.getProperty("DistrictName").toString();
                            String str_districtstateid = response_soapobj.getProperty("Stateid").toString();

                            //DBCreate_Districtdetails_insert_2SQLiteDB(str_districtID,str_districtname,str_districtstateid);


                        }//end for loop

                    }//end of if

                }

            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails

    public void getTaluklist_AsynTask()
    {
        TaluklistAsyncCallWS task = new TaluklistAsyncCallWS(ManagerFeesCollectionActivity.this);
        task.execute();
    }

    private class TaluklistAsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            /*dialog.setMessage("Please wait,Taluk loading...");
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

            GetTaluklist();  // get the District list
            return null;
        }

        public TaluklistAsyncCallWS(ManagerFeesCollectionActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            // dialog.dismiss();

            /*uploadfromDB_Statelist();
            uploadfromDB_Districtlist();*/

            // setSpinner();


            //  uploadfromDB_Districtlist();


            //arrayObj_Class_Taluk


            if(str_TalukStatus.equals("Success")) //status=Success;
            {
                //if (str_isProfileEdited.equals("1"))
                if(!str_LeadID.equals("0"))
                {
                    dataAdapter_city = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Taluk);
                    dataAdapter_city.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_city.setAdapter(dataAdapter_city);
                    spin_city.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                    if(O_talukname!="" || O_talukname!=null) {
                        for (int i = 0; i < spin_city.getCount(); i++) {
                            if (spin_city.getItemAtPosition(i).toString().equals(O_talukname))
                            {
                                spin_city.setSelection(i);
                                break;
                            }
                        }
                    }
                }
                else {


                    dataAdapter_city = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Taluk);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_city.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_city.setAdapter(dataAdapter_city);
                    spin_city.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                }
            }

        }//end of onPostExecute
    }// end Async task

    public void GetTaluklist()
    {



        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "GetCities";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/GetCities";



        //for final


        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            //request.addProperty("cityid", "0");//short

            request.addProperty("district", Long.parseLong(str_Dids));//<district>long</district>
            request.addProperty("Manager_Id",MDId);

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
                Log.e("response taluklist",response.toString());
                taluklistcount = response.getPropertyCount();
                // str_TalukStatus =(String)response.getProperty(0).toString();
                SoapObject  TalukSoapresponse = (SoapObject)response.getProperty(0);
                str_TalukStatus=TalukSoapresponse.getProperty("status").toString();
                Log.d("talukcount", String.valueOf(response.getPropertyCount()));

                /* anyType{vmcity=anyType{Id=0; District_Id=0; status=There are no cities; };*/
          /*
    <Id>70</Id>
    <Distict_Id>266</Distict_Id>
    <Taluk_Name>Hubli</Taluk_Name>
    <Status>Success</Status>

    */
                //status=Success;
                if(str_TalukStatus.equals("Success")) {

                    if (taluklistcount > 0) {
                        arrayObj_Class_Taluk = new Class_Taluk[taluklistcount];


                        for (int i = 0; i < taluklistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero


                            Class_Taluk innerObj_Class_taluk = new Class_Taluk();
                            innerObj_Class_taluk.settaluk_id(response_soapobj.getProperty("Id").toString()); //<Id>1</Id>
                            innerObj_Class_taluk.settaluk_did(response_soapobj.getProperty("District_Id").toString()); //<Distict_Id>266</Distict_Id>
                            innerObj_Class_taluk.settalukname(response_soapobj.getProperty("Taluk_Name").toString());// <Taluk_Name>Hubli</Taluk_Name>
                            innerObj_Class_taluk.settalukstatus(response_soapobj.getProperty("status").toString());// <Status>Success</Status>

                            str_TalukStatus = response_soapobj.getProperty("status").toString();

                            arrayObj_Class_Taluk[i] = innerObj_Class_taluk;


                            String str_talukID = response_soapobj.getProperty("Id").toString();
                            String str_taluk_did = response_soapobj.getProperty("District_Id").toString();
                            String str_talukname = response_soapobj.getProperty("Taluk_Name").toString();


                            //   DBCreate_Talukdetails_insert_2SQLiteDB(str_talukID,str_taluk_did,str_talukname);


                        }//end for loop

                    }//end of if

                }
            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails

    public void getCollegelist_AsynTask()
    {
        CollegelistAsyncCallWS task = new CollegelistAsyncCallWS(ManagerFeesCollectionActivity.this);
        task.execute();
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

        public CollegelistAsyncCallWS(ManagerFeesCollectionActivity activity) {
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
               // if (str_isProfileEdited.equals("1"))
                if(!str_LeadID.equals("0"))
                {
                    dataAdapter_college = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Colleges);
                    dataAdapter_college.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_institution.setAdapter(dataAdapter_college);
                    spin_institution.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                    str_ColID="y";
                    if(O_collegename!="" || O_collegename!=null)
                    {
                        for (int i = 0; i < spin_institution.getCount(); i++) {
                            if (spin_institution.getItemAtPosition(i).toString().equals(O_collegename))
                            {
                                spin_institution.setSelection(i);
                                break;
                            }
                        }
                    }

                }
                else {

                    str_ColID="y";
                    dataAdapter_college = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Colleges);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_college.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_institution.setAdapter(dataAdapter_college);
                    spin_institution.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                }
            }
            else {
                //There are no colleges
                if (str_CollegesStatus.equals("There are no colleges"))
                {
                    /*List<String> list = new ArrayList<String>();
                    list.add("No Colleges");*/
                    dataAdapter_college = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Colleges3);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_college.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_institution.setAdapter(dataAdapter_college);
                    spin_institution.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                }
                else
                {
                    List<String> list = new ArrayList<String>();
                    list.add("WS Exception");
                    dataAdapter_college = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, list);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_college.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_institution.setAdapter(dataAdapter_college);
                    spin_institution.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

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

            request.addProperty("city", Long.parseLong(str_Cityids));//  <city>long</city>
            request.addProperty("type", str_programname);// <type>string</type>
            request.addProperty("Manager_Id",MDId);
            request.addProperty("Fees_Category_Id",Integer.valueOf(str_feesCatSlno));
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
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails



    private void getStudentDetails() {
        GetStudentDetails getStudentDetails = new GetStudentDetails(ManagerFeesCollectionActivity.this);
        getStudentDetails.execute(str_LeadID);
    }


    public class GetStudentDetails extends AsyncTask<String, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        // private ProgressBar progressBar;

        ProgressDialog dialog;

        GetStudentDetails(Context ctx)
        {
            context = ctx;
            dialog = new ProgressDialog(ctx);
        }


        @Override
        protected SoapObject doInBackground(String... params) {
            String leadid = (String) params[0];
            //String versionCode = (String) params[2];


            SoapObject response = getStudDtls(leadid);

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
            /*pd_getstudentdetails.setMessage("Please wait,Downloading Details loading...");
            pd_getstudentdetails.setCanceledOnTouchOutside(false);
            pd_getstudentdetails.show();
*/

            dialog.setMessage("Please wait,Updating Details loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            //  progressBar.setVisibility(View.GONE);

            dialog.dismiss();
            // pd_getstudentdetails.dismiss();



          //  Log.e("tag","result =="+result.getProperty("Status").toString());
            if(searchStatus.equals("Success")) {
                if (O_StudentName.equalsIgnoreCase("anyType{}") || O_StudentName.equalsIgnoreCase("") || O_StudentName == null) {
                    etName.setText("");
                } else {
                    etName.setText(O_StudentName.toString().trim());
                }
           /* if(O_AlternativeMobileNo.toString().length()==0)
            {alernativecell_et.setText(" "); }
            else { alernativecell_et.setText(O_AlternativeMobileNo.toString().trim()); }
*/

                if (O_MailId.equalsIgnoreCase("anyType{}") || O_MailId.equalsIgnoreCase("") || O_MailId == null) {
                    emailid_ET.setText("");

                } else {
                    emailid_ET.setText(O_MailId.toString().trim());
                }
                etMobile.setText(O_MobileNo.toString().trim());

                getstatelist_AsynTask();
            }else{
                Toast.makeText(context, result.getProperty("Status").toString(), Toast.LENGTH_LONG).show();
            }
           /* if(O_AadharNo.equals("anyType{}")||O_AadharNo.equals("anytype{}")||O_AadharNo.equals("0"))
            { aadhara_et.setText(""); }
            else
            {   aadhara_et.setText(O_AadharNo.toString().trim()); }

            if(O_Account_No.equals("anyType{}")||O_Account_No.equals("anytype{}")||O_Account_No.equals("0"))
            { account_et.setText(""); }
            else
            {   account_et.setText(O_Account_No.toString().trim());  }
*/


            /*if(O_IFSC_code.equals("anyType{}")||O_IFSC_code.equals("anytype{}")||O_IFSC_code.equals("0"))
            {   iifscode_et.setText("");}
            else{ iifscode_et.setText(O_IFSC_code.toString().trim()); }



            if(O_mytalent.equalsIgnoreCase("anyType{}")||O_mytalent.isEmpty())
            { mytalent_et.setText(""); }
            else{ mytalent_et.setText(O_mytalent.toString().trim()); }
*/
         //   edt_dob.setText(O_DOB.toString().trim());
           // leadid_tv.setText(O_leadID.toString().trim());
          //  student_tv.setText(O_Student_Type.toString().trim());

            /*if(O_Gender.equals("M"))
            {
                gender_radiogroup.check(R.id.rdb_male); }
            else
            { gender_radiogroup.check(R.id.rdb_female); }


            if(O_BloodGroup!="" || O_BloodGroup!=null)
            {
                for (int i = 0; i < spin_bg.getCount(); i++) {
                    if (spin_bg.getItemAtPosition(i).toString().equals(O_BloodGroup))
                    {
                        spin_bg.setSelection(i);
                        break;
                    }
                }
            }*/

         //   img_profilePick.setImageBitmap(Studentbmp);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getStudDtls(String leadid) {
        String METHOD_NAME = "GetStudentDetailss";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetStudentDetailss";

        try {
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            /*            Log.d("Mobile Number",mobilenum);*/
            Log.d("Lead Id", leadid);

            /*request.addProperty("Username", mobilenum);
            request.addProperty("Password", leadid);*/

            request.addProperty("leadId", leadid);
            /*            request.addProperty("Password", mobilenum);*/

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("GetStudent","soap response"+ response.toString());
                searchStatus=response.getProperty("Status").toString();
                if(searchStatus.equals("Success")) {
                    O_RegistrationId = response.getProperty("RegistrationId").toString();
                    O_StudentName = response.getProperty("StudentName").toString();
                    //  O_DOB=response.getProperty("DOB").toString();
                    String RegistrationDate = response.getProperty("RegistrationDate").toString();
                    String isProfileEdit = response.getProperty("isProfileEdit").toString();
                    O_statecode = response.getProperty("StateCode").toString();
                    O_districtcode = response.getProperty("DistrictCode").toString();
                    O_talukacode = response.getProperty("TalukaCode").toString();
                    O_collegenameCode = response.getProperty("CollegeCode").toString();
                    O_coursecode = response.getProperty("CourseCode").toString();
                    O_StreamprogramCode = response.getProperty("StreamCode").toString();
                    String O_semcode = response.getProperty("SemName").toString();
                    // O_AadharNo=response.getProperty("AadharNo").toString();
                    String O_Address = response.getProperty("Address").toString();
                    O_MailId = response.getProperty("MailId").toString();
              /*  O_AlternativeMobileNo=response.getProperty("AlternativeMobileNo").toString();
                O_Gender=response.getProperty("Gender").toString();
                O_BloodGroup=response.getProperty("BloodGroup").toString();
              */
                    String O_FacebookId = response.getProperty("FacebookId").toString();
                    String O_LinkedInId = response.getProperty("LinkedInId").toString();
                    String O_AcademicCode = response.getProperty("AcademicCode").toString();
                    // O_Student_Type=response.getProperty("Student_Type").toString();
                    String O_Bank_Name = response.getProperty("Bank_Name").toString();
                    String O_Branch_Name = response.getProperty("Branch_Name").toString();
                    //  O_Account_No=response.getProperty("Account_No").toString();
                    // O_IFSC_code=response.getProperty("IFSC_code").toString();
                    String O_Status = response.getProperty("Status").toString();
                    //  O_Student_Image=response.getProperty("Student_Image").toString();
                    String O_ManagerMobileNumber = response.getProperty("ManagerMobileNumber").toString();
                    O_MobileNo = response.getProperty("MobileNo").toString();
                    //   O_mytalent=response.getProperty("MyTalent").toString();//<MyTalent/>
                    //   Log.e("tag","RegistrationId="+O_RegistrationId+"StudentName="+O_StudentName+"DOB="+O_DOB);
                    Log.e("tag", "RegistrationDate=" + RegistrationDate + "isProfileEdit=" + isProfileEdit + "StateCode=" + O_statecode);
                    Log.e("tag", "DistrictCode=" + O_districtcode + "TalukaCode=" + O_talukacode + "CollegeCode=" + O_collegenameCode);

                    O_leadID = response.getProperty("Lead_Id").toString();
                    //    O_altermailid= response .getProperty("AlternateMailId").toString();
                    O_collegename = response.getProperty("College_Name").toString();
                    O_statename = response.getProperty("StateName").toString();
                    O_districtname = response.getProperty("DistrictName").toString();
                    O_talukname = response.getProperty("Taluk_Name").toString();
                    O_programmename = response.getProperty("programmeName").toString();
                    O_coursename = response.getProperty("CourseName").toString();
                    O_semname = response.getProperty("SemName").toString();


                }

         /*       String arr[] = O_Student_Image.split("/", 2);

                String firstWord = arr[0];   //the
                String secondWord = arr[1];

                Str_studentImgUrl = serverPath + secondWord;
                Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                Log.e("student URL", ""+Str_studentImgUrl);

                URL url = new URL(Str_studentImgUrl.toString().trim());
                Studentbmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
*/
                // img_profilePick.setImageBitmap(Studentbmp);



                return response;

            } catch (Exception t) {

                Log.e("request fail", "> " + t.getMessage().toString());


            }
        } catch (Exception t) {
            Log.d("exception outside", t.getMessage().toString());
        }
        return null;
    }

    public void getProgramlist_AsynTask()
    {
        getProgramlist_AsynTask task = new getProgramlist_AsynTask(ManagerFeesCollectionActivity.this);
        task.execute();
    }

    private class getProgramlist_AsynTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
           /* dialog.setMessage("Please wait,ProgramList loading...");
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

            GetProgramlist();  // get the Course list
            return null;
        }

        public getProgramlist_AsynTask(ManagerFeesCollectionActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            // dialog.dismiss();

            //Update_Programlist_spinner();


            if(str_ProgramStatus.equals("Success"))
            {
               // if(str_isProfileEdited.equals("1"))
                if(!str_LeadID.equals("0"))
                {


                    dataAdapter_program = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Program);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_program.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_program.setAdapter(dataAdapter_program);
                    spin_program.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                    if(O_programmename!="" || O_programmename!=null)
                    {
                        for (int i = 0; i < spin_program.getCount(); i++) {
                            if (spin_program.getItemAtPosition(i).toString().equals(O_programmename))
                            {
                                spin_program.setSelection(i);
                                break;
                            }
                        }
                    }

                }
                else
                {
                    dataAdapter_program = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Program);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_program.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_program.setAdapter(dataAdapter_program);
                    spin_program.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                }

            }

        }//end of onPostExecute
    }// end Async task

    public void GetProgramlist()
    {



        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "GetprogramList";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/GetprogramList";



        //for final


        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            // request.addProperty("courseid", 0);//<courseid>long</courseid>

            //
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);


                SoapObject  response = (SoapObject ) envelope.getResponse();
                SoapObject o_programresponse = (SoapObject ) response.getProperty(0);
                str_ProgramStatus = o_programresponse.getProperty("status").toString();
                Log.e("resp Programlist",response.toString());
                programlistcount = response.getPropertyCount();

                Log.d("Programcount", String.valueOf(response.getPropertyCount()));



                if(str_ProgramStatus.equals("Success"))
                {
                    if (programlistcount > 0) {
                        arrayObj_Class_Program = new Class_Program[programlistcount];


                        for (int i = 0; i < programlistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                      /* <programmeId>long</programmeId>
          <status>string</status>
          <programmeName>string</programmeName>*/


                            Class_Program innerObj_Class_program = new Class_Program();
                            innerObj_Class_program.setprogram_id(response_soapobj.getProperty("programmeId").toString()); //<programmeId>long</programmeId>
                            innerObj_Class_program.setprog_name(response_soapobj.getProperty("programmeName").toString()); //  <programmeName>string</programmeName>
                            innerObj_Class_program.setprog_status(response_soapobj.getProperty("status").toString()); // <status>string</status>








                            str_ProgramStatus = response_soapobj.getProperty("status").toString();

                            arrayObj_Class_Program[i] = innerObj_Class_program;


                            String str_progID = response_soapobj.getProperty("programmeId").toString();
                            String str_progname = response_soapobj.getProperty("programmeName").toString();




                            // DBCreate_Programdetails_insert_2SQLiteDB(str_progID, str_progname);


                        }//end for loop

                    }//end of if

                }


            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("getCollege fail", "> " + t.getMessage());
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails

    public class SubmitAcknowledgements extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //private ProgressBar progressBar;
        private ProgressDialog progressDialog;

        SubmitAcknowledgements (Context ctx){
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
                    finish();
                    startActivity(getIntent());
                }else if(result.toString().equalsIgnoreCase("EXISTS")){
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
        String METHOD_NAME = "Save_Manager_PaymentDetails";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Save_Manager_PaymentDetails";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            JSONObject jsonObject = new JSONObject();

            if(rdb_register.isChecked()){
                isNewRegistration=0;
            }else if(rdb_newRegister.isChecked()){
                isNewRegistration=1;
            }

            Log.e("tag","isNewRegistration="+isNewRegistration);
            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("Fees_Category_ID",Integer.valueOf(str_feesCatSlno));
            request.addProperty("Fees_Category_Name",str_feesCatName);
            request.addProperty("Fees_ID",Integer.valueOf(str_feesId));
            request.addProperty("isNewRegistration",isNewRegistration);    // new reg==1, reg==0
            request.addProperty("Payment_Remark",remark_ET.getText().toString());
            request.addProperty("Manager_Id",MDId);
           /* request.addProperty("Fees_Category_ID",Integer.valueOf(str_feesCatSlno));
            request.addProperty("Fees_Category_Name",str_feesCatName);
            request.addProperty("Fees_ID",Integer.valueOf(str_feesId));
            request.addProperty("isNewRegistration",isNewRegistration);
            request.addProperty("Payment_Remark",remark_ET.getText().toString());
            request.addProperty("Manager_Id",MDId);*/
//[{"Registration_Id":38267,"Student_Name":"Madhu","MobileNo":"8904674048","State_Id":17,"District_Id":266,"City_Id":68,"stream_Id":3,"College_ID":178,"Email_Id":"madhushree.kubsad@dfmail.org","Paid_date":"0","Paid_fees":100,"Payment_Mode":1,"Payment_Remark":"fees paid"}]
          /*  jsonObject.put("Registration_Id",38267);
            Log.e("tag","str_RegistrationId="+str_RegistrationId);
            jsonObject.put("Student_Name",etName.getText().toString());
            jsonObject.put("MobileNo","8904674048");
            jsonObject.put("State_Id",17);
            jsonObject.put("District_Id",266);
            jsonObject.put("City_Id",68);
            jsonObject.put("stream_Id",3);
            jsonObject.put("College_ID",178);
            jsonObject.put("Email_Id","madhushree.kubsad@dfmail.org");
            jsonObject.put("Paid_date","2021-02-22");
            Log.e("tag","paid Date="+edt_paymentDate.getText().toString());
            jsonObject.put("Paid_fees",1);
            jsonObject.put("Payment_Mode",1);
            jsonObject.put("Payment_Remark","test test");*/
          //  [{"Registration_Id":0,"Student_Name":"test a","MobileNo":"8904674048","State_Id":"17","District_Id":"256","City_Id":"14","stream":"5","College_ID":"37","Email_Id":"madhushree.kubsad@dfmail.org","Paid_date":"2021-02-22","Paid_fees":"1","Payment_Mode":"1","Payment_Remark":"test test"}]
            jsonObject.put("Registration_Id",Integer.valueOf(O_RegistrationId));
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

            request.addProperty("PaymentDetails","["+jsonObject+"]");


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


                //SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("Responseissss",envelope.getResponse().toString());

                //return null;

                return response;

            }catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

    public int getIndex(Spinner spinner, String myString) {
        int index = 0;
        String item = null;
        for (int i = 0; i < spinner.getCount(); i++) {
            //   Log.e("entered getIndex", "entered getIndex");

            item = spinner.getItemAtPosition(i).toString();
            if (item.equals(myString)) {
                index = i;
                 Log.e("entered myString i=", String.valueOf(index));

            }
        }
        return index;
    }

}