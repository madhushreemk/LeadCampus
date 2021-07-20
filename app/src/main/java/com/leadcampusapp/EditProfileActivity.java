package com.leadcampusapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
//import android.support.v7.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatSpinner;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditProfileActivity extends AppCompatActivity {


    private AppCompatSpinner spin_gender;
    private AppCompatSpinner spin_bg;


    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;
    private Context context;

    //  private Button btn_save;
    private ImageView btn_save;
    private Button btn_addProfile;
    private ImageButton imgBtn_addProfile;


    AppCompatSpinner spin_state;
    Class_States[] arrayObj_Class_States, arrayObj_Class_States2;
    Class_States obj_Class_States;
    int statelistcount;
    String str_StateStatus="x";

    AppCompatSpinner spin_district;
    Class_District[] arrayObj_Class_District, arrayObj_Class_District2;
    Class_District obj_Class_District;
    int districtlistcount;
    String str_DistrictStatus="x";

    AppCompatSpinner spin_city;
    Class_Taluk[] arrayObj_Class_Taluk, arrayObj_Class_Taluk2;
    Class_Taluk obj_Class_Taluk;
    int taluklistcount;
    String str_TalukStatus="x";

    AppCompatSpinner spin_institution;
    Class_Colleges[] arrayObj_Class_Colleges, arrayObj_Class_Colleges2,arrayObj_Class_Colleges3;
    Class_Colleges obj_Class_Colleges;
    int collegeslistcount;
    String str_CollegesStatus;


    AppCompatSpinner spin_semester;
    Class_Sem[] arrayObj_Class_Sem, arrayObj_Class_Sem2;
    Class_Sem obj_Class_Sem;
    int semlistcount;
    String str_SemStatus;
    String str_semid,str_semname;




    AppCompatSpinner spin_course;
    Class_Course[] arrayObj_Class_Course, arrayObj_Class_Course2;
    Class_Course obj_Class_Course;
    int courselistcount;
    String str_CourseStatus;
    String str_courseid,str_coursename;



    AppCompatSpinner spin_program;
    Class_Program[] arrayObj_Class_Program, arrayObj_Class_Program2;
    Class_Program obj_Class_Program;
    int programlistcount;
    String str_ProgramStatus;
    String str_programid,str_programname="x";



    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private int REQUEST_CAMERA_BANK = 1, SELECT_FILE_BANK = 2;
    ImageView img_profilePick,img_bankPick;
    DatabaseHelper dbHelper;

    EditText edt_dob;
    Calendar myCalendar = Calendar.getInstance();
    String leadid, isprofileEdited;
    EditProfileModel edtPrfModel;

    HashMap<String, Integer> hashMapState = new HashMap<String, Integer>();


    Class_InternetDectector internetDectector, internetDectector2;
    Boolean isInternetPresent = false;
    Boolean isInternetPresent2 = false;


    String internet_issue = "false";
    String internet_issue1="Noerror";

    String str_Cityids,str_ColID,str_Sids,str_Dids;



    ArrayAdapter dataAdapter, dataAdapter_district, dataAdapter_city, dataAdapter_college,dataAdapter_semlist;
    ArrayAdapter dataAdapter_course,dataAdapter_program,dataAdapter_bloodgroup;

    Class_HintSpinnerAdapter class_hintspinneradapter_obj;

    AutoCompleteTextView txtSearch1;
    AutoCompleteTextView statesearch_ATV, districtsearch_ATV, citysearch_ATV, collegesearch_ATV;




    String str_editprofile_response,str_gender,str_bloodgroup,str_isProfileEdited,str_bankDoc;

    String str_MobileNo,str_RegID,str_LeadID,str_studenttype,str_academiccode;
    EditText edt_studName,alernativecell_et,emailid_et,aadhara_et,account_et,iifscode_et;


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
    SharedPreferences.Editor editor_S;


    Class_URL  config_obj= new Class_URL();
    public static final String  PREFBook_LoginTrack= "prefbook_logintrack";
    public static final String PrefID_WhereToGo = "prefid_wheretogo"; //
    SharedPreferences shardprefLoginTrack_obj;
    SharedPreferences.Editor editor_LoginTrack;








    byte[] imageinbytesArray={0};
    String studPickBase64 = null;
    String bankPicBase64 = null;


    RadioGroup  gender_radiogroup,bankdoc_radiogroup;

    String str_homeactivity="no";
    String O_statename,O_districtname,O_talukname,O_programmename,O_coursename,O_semname,O_collegename;
    String O_statecode,O_districtcode,O_talukacode,O_collegenameCode,O_coursecode,O_StreamprogramCode;
    String O_RegistrationId,O_StudentName,O_DOB,O_AadharNo,O_Account_No,O_IFSC_code,O_AlternativeMobileNo,O_altermailid,O_Gender,O_MailId,O_BloodGroup,O_leadID,O_MobileNo,O_Student_Image,Str_studentImgUrl,O_Student_Type,O_mytalent;
    TextView leadid_tv,mobileno_tv,student_tv;

    TextView stateerror_tv,districterror_tv,cityerror_tv,collegeerror_tv,courseerror_tv,semerror_tv;

    EditText dobseterror_tv;
    Bitmap Studentbmp;


    private File actualImage;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    String[] state_stringArray;


    Bitmap bmp_fromCamera;

    private String serverPath = Class_URL.ServerPath.toString().trim();


    private Button btn_submitQuery;
    private EditText edt_mailId_Query;
    private EditText edt_description_Query;
    private EditText request_statename_et,request_districtname_et,request_talukaname_et,request_Collegename_et,request_studentname_et;
    private AlertDialog dialog;

    ProgressDialog pd_getstudentdetails;

    TextView statelabel_tv,districtlabel_tv,citylabel_tv,courselabel_tv,institutionlabel_tv,streamlabel_tv;
    TextView semlabel_tv,whatsapplabel_tv,emailidlabel_tv,doblabel_tv;
    EditText mytalent_et;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edtPrfModel = new EditProfileModel();





        //  isprofileEdited = getIntent().getStringExtra("isProfileEdit");// for first time user

        // Toast.makeText(getApplicationContext(), "Profile" + isprofileEdited.toString(), Toast.LENGTH_SHORT).show();

        str_Cityids=str_ColID=str_Sids=str_Dids=str_courseid=str_programid=str_semid="x";
        str_gender="M";
        /*getSupportActionBar().setTitle("LeadMIS");*/

        /*leadid = getIntent().getStringExtra("leadid");

        Log.d("Lead Id is",leadid);*/

        str_homeactivity =getIntent().getStringExtra("KeyfromHome"); //fromhomeactivity


      /*  if(str_homeactivity.equals(null))
        {
            Toast.makeText(this,"Keyfromhome: "+str_homeactivity,Toast.LENGTH_SHORT).show();
        }*/

       /* if(TextUtils.isEmpty(str_homeactivity)) {
            Toast.makeText(this,"Keyfromhome: "+str_homeactivity,Toast.LENGTH_SHORT).show();
        }
        else
        {
            getStudentDetails();
        }*/

        context = EditProfileActivity.this;






        statelabel_tv=(TextView) findViewById(R.id.statelabel_TV);
        districtlabel_tv=(TextView) findViewById(R.id.districtlabel_TV);
        citylabel_tv=(TextView) findViewById(R.id.citylabel_TV);
        courselabel_tv=(TextView) findViewById(R.id.courselabel_TV);
        institutionlabel_tv=(TextView) findViewById(R.id.institutionlabel_TV);
        streamlabel_tv=(TextView) findViewById(R.id.streamlabel_TV);


        edt_studName = (EditText) findViewById(R.id.edt_studName);

        spin_state = (AppCompatSpinner) findViewById(R.id.spin_state);
        spin_city = (AppCompatSpinner) findViewById(R.id.spin_city);
        spin_institution = (AppCompatSpinner) findViewById(R.id.spin_institution);
        spin_semester = (AppCompatSpinner) findViewById(R.id.spin_sem);
        edt_dob = (EditText) findViewById(R.id.edt_dob);
        //spin_gender = (AppCompatSpinner) findViewById(R.id.spin_gender);
        spin_bg = (AppCompatSpinner) findViewById(R.id.spin_bg);

        spin_district = (AppCompatSpinner) findViewById(R.id.spin_district);

        spin_program = (AppCompatSpinner) findViewById(R.id.spin_program);
        spin_course = (AppCompatSpinner) findViewById(R.id.spin_course);

        alernativecell_et = (EditText) findViewById(R.id.alernativecell_ET);
        emailid_et= (EditText) findViewById(R.id.emailid_ET);
        aadhara_et= (EditText) findViewById(R.id.aadhara_ET);
        account_et= (EditText) findViewById(R.id.account_ET);
        iifscode_et= (EditText) findViewById(R.id.iifscode_ET);
        leadid_tv =(TextView)findViewById(R.id.leadid_TV);
        mobileno_tv =(TextView)findViewById(R.id.mobileno_TV);
        student_tv =(TextView)findViewById(R.id.studenttype_TV);
        stateerror_tv =(TextView)findViewById(R.id.stateerror_TV);
        districterror_tv =(TextView)findViewById(R.id.districterror_TV);
        cityerror_tv =(TextView)findViewById(R.id.cityerror_TV);
        collegeerror_tv = (TextView)findViewById(R.id.collegeerror_TV);
        courseerror_tv =(TextView)findViewById(R.id.courseerror_TV);
        semerror_tv = (TextView)findViewById(R.id.semerror_TV);
        dobseterror_tv =(EditText)findViewById(R.id.dobseterror_TV);
        mytalent_et= (EditText)findViewById(R.id.mytalent_et);

        gender_radiogroup =(RadioGroup)findViewById(R.id. gender_radiogroup);

        //   btn_save = (Button) findViewById(R.id.btn_save);

        btn_save = (ImageView) findViewById(R.id.btn_save);


        stateerror_tv.setVisibility(View.GONE);
        shardpref_S_obj=this.getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardprefLoginTrack_obj =this.getSharedPreferences(PREFBook_LoginTrack,Context.MODE_PRIVATE);

        shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_RegID = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_LeadID = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_MobileNo =shardpref_S_obj.getString(PrefID_SCellNo,"").trim();
       str_studenttype=shardpref_S_obj.getString(PrefID_SStudentType,"").trim();
        str_academiccode=shardpref_S_obj.getString(PrefId_S_AcademicId,"").trim();




        editor_LoginTrack = shardprefLoginTrack_obj.edit();
        editor_LoginTrack.putString(PrefID_WhereToGo,config_obj.packagename+"EditProfileActivity");
        editor_LoginTrack.commit();

        shardpref_S_obj.getString(PrefID_S_isprofileEdited, "").trim();
        str_isProfileEdited = shardpref_S_obj.getString(PrefID_S_isprofileEdited, "").trim();
        // str_LeadID = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.i("tag","str_isProfileEdited="+str_isProfileEdited);
        //btn_addProfile = (Button) findViewById(R.id.btn_addProfile);

        /*Toast.makeText(this,"str_isProfileEdited : "+str_isProfileEdited.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"LeadID : "+str_LeadID.toString(),Toast.LENGTH_SHORT).show();*/

        imgBtn_addProfile = (ImageButton) findViewById(R.id.imgBtn_addProfile);

        img_profilePick = (ImageView) findViewById(R.id.img_profilePick);
        img_bankPick = (ImageView) findViewById(R.id.img_bankPick);


        dbHelper = new DatabaseHelper(EditProfileActivity.this);


        txtSearch1 = (AutoCompleteTextView) findViewById(R.id.txtSearch1);
        statesearch_ATV = (AutoCompleteTextView) findViewById(R.id.statesearch);
        districtsearch_ATV = (AutoCompleteTextView) findViewById(R.id.districtsearch);
        citysearch_ATV = (AutoCompleteTextView) findViewById(R.id.citysearch);
        collegesearch_ATV = (AutoCompleteTextView) findViewById(R.id.collegesearch);










        /*btn_addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });*/

        imgBtn_addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        img_bankPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageBank();
            }
        });


/********************** need to be in group *****************************************/
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));


        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Edit Profile");



/********************** need to be in group *****************************************/

        // getStudentDetails();
        //  setStateSpinner();
        // setCitySpinner();
        // setInstitutionSpinner();
        // setSemesterSpinner();
        // setDistrictSpinner();
        //setCourseSpinner();
        //setProgramSpinner();
        setDateOfBirth();
        //setGenderSpinner();
        setBloodGroupSpinner();

        // setSpinner();





        internetDectector = new Class_InternetDectector(getApplicationContext());
        isInternetPresent = internetDectector.isConnectingToInternet();


        if (isInternetPresent)
        {
            // getCollegelist_AsynTask();
            // Update_Semlist_spinner();

            // getSemlist_AsynTask();




            if (str_isProfileEdited.equals("0")) {
                // Toast.makeText(getApplicationContext(), "First Time", Toast.LENGTH_SHORT).show();

                SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
                db1.execSQL("CREATE TABLE IF NOT EXISTS Statelist(SID VARCHAR,SName VARCHAR);");
                Cursor cursor1 = db1.rawQuery("SELECT * FROM Statelist", null);
                int x = cursor1.getCount();
                Log.e("Total DB count", Integer.toString(x));


                if (x <= 0) // already values are their in database
                {

                    leadid_tv.setText(str_LeadID.toString().trim());
                    mobileno_tv.setText(str_MobileNo.toString().trim());
                    student_tv.setText(str_studenttype.toString().trim());
                    // Toast.makeText(getApplicationContext(), "If part", Toast.LENGTH_SHORT).show();
                    getstatelist_AsynTask();
                    // getProgramlist_AsynTask();
                    /*getdistrictlist_AsynTask();
                    getTaluklist_AsynTask();

                    getSemlist_AsynTask();
                    getCourselist_AsynTask();
                    getProgramlist_AsynTask();

                    getCollegelist_AsynTask();
*/

                } else //load from database
                {
                    // Toast.makeText(getApplicationContext(), "Else part", Toast.LENGTH_SHORT).show();


                    // uploadfromDB_Statelist();
                    // uploadfromDB_Districtlist();
                    Update_Semlist_spinner();
                    Update_Programlist_spinner();

                    // Update_Courselist_spinner();


                }

            }else if(str_isProfileEdited.equals("1")){
                getStudentDetails();
                getstatelist_AsynTask();
            }else if(str_isProfileEdited.equals("2")){
                getStudentDetails();
                getstatelist_AsynTask();
            }

        }



        txtSearch1.setText("Karnataka");












        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent ittEditToHome = new Intent(EditProfileActivity.this, HomeActivity.class);
                startActivity(ittEditToHome);
*/

                if(validation())
                {
                    internetDectector2 = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent2 = internetDectector2.isConnectingToInternet();

                    if (isInternetPresent2)
                    {
                        SaveStudentDetails_AsynTask();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No Internet",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Kindly complete the profile",Toast.LENGTH_LONG).show();
                }



                /*Intent ittEditToHome = new Intent(EditProfileActivity.this, TestActivity.class);
                startActivity(ittEditToHome);*/

            }
        });





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
                spin_course.setAdapter(null);
                spin_semester.setAdapter(null);

                str_Cityids=str_ColID=str_Dids=str_courseid=str_programid=str_semid="x";
                //Toast.makeText(getApplicationContext(),"State"+spin_state.getSelectedItem().toString()+"ID:"+str_Sids,Toast.LENGTH_SHORT).show();

                //  Toast.makeText(getApplicationContext(),"State:"+spin_state.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                if(!( (str_Sids.equals("x"))|| (str_Sids.equals("0")) ))
                { stateerror_tv.setText("");
                    stateerror_tv.setVisibility(View.GONE);
                    getdistrictlist_AsynTask();
                }

              /* String str_spinstate=spin_state.getSelectedItem().toString();
              if(arrayObj_Class_States!=null)
              {
                  for (int i = 0; i < arrayObj_Class_States.length; i ++) {
                      if (arrayObj_Class_States[i] != null) {
                          if (arrayObj_Class_States[i].getstate_name().equals(str_spinstate)) {
                              Toast.makeText(getApplicationContext(), "ID:" + arrayObj_Class_States[i].getstate_id().toString(), Toast.LENGTH_SHORT).show();

                              str_Sids=arrayObj_Class_States[i].getstate_id().toString();
                               getdistrictlist_AsynTask();
                              break;
                          }
                          i++;
                      }
                  }
              }*/


               /* if(spin_state.getSelectedItem().toString().equals("Select State"))
                {                            }
                else
                {
                    obj_Class_States = (Class_States) spin_state.getSelectedItem();
                    str_Sids = obj_Class_States.getstate_id().toString();
                    spin_city.setAdapter(null);
                    spin_program.setAdapter(null);
                    spin_institution.setAdapter(null);
                    spin_course.setAdapter(null);
                    spin_semester.setAdapter(null);
                    Toast.makeText(getApplicationContext(),"State"+spin_state.getSelectedItem().toString()+"ID:"+str_Sids,Toast.LENGTH_SHORT).show();

                    if(!(str_Sids.equals("x")||str_Sids.equals("0")))
                    { getdistrictlist_AsynTask();
                    }

                }*/







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
                spin_course.setAdapter(null);
                spin_semester.setAdapter(null);

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

                if(!(str_programname.equals("x"))|| !(spin_program.getSelectedItem().toString().equals("Select Course")))
                { getCollegelist_AsynTask();
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
                }
                else {
                    obj_Class_Colleges = (Class_Colleges) spin_institution.getSelectedItem();
                    str_ColID = obj_Class_Colleges.getcollege_id().toString();
                }


                if(!(str_ColID.equals("x")) || !(spin_institution.getSelectedItem().toString().equals("Select Institution"))||!(spin_institution.getSelectedItem().toString().equals("There are no colleges")))
                {
                    Log.e("str_ColID",str_ColID.toString());
                    Log.e("Institution",spin_institution.getSelectedItem().toString());
                    collegeerror_tv.setVisibility(View.GONE);
                    getCourselist_AsynTask();
                }
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });






        spin_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_Course = (Class_Course) spin_course.getSelectedItem();

                str_courseid = obj_Class_Course.getcourse_id().toString();
                str_coursename = obj_Class_Course.getcourse_name().toString();


                if( !(str_courseid.equals("x"))|| !(spin_course.getSelectedItem().toString().equals("Select Stream"))  )
                { courseerror_tv.setVisibility(View.GONE);
                    getSemlist_AsynTask(); }
                // Toast.makeText(getApplicationContext(),"str_courseid: "+str_courseid+ " " +"str_coursename: "+str_coursename,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });

        spin_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_Sem = (Class_Sem) spin_semester.getSelectedItem();

                str_semid = obj_Class_Sem.getsem_id().toString(); //semID
                str_semname = obj_Class_Sem.getsem_name().toString();  //1

                if( !(str_Sids.equals("x"))|| !(spin_course.getSelectedItem().toString().equals("Select Semester"))  )
                {
                    semerror_tv.setVisibility(View.GONE);
                }

                // Toast.makeText(getApplicationContext(),"str_semid: "+str_semid+"str_semname: "+str_semname,Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub
            }
        });


        spin_bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                str_bloodgroup = spin_bg.getSelectedItem().toString();
                // Toast.makeText(getApplicationContext(),"Blood:"+str_bloodgroup,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });






        //if (str_isProfileEdited.equals("0"))
        if(2>1)
        {

            btn_submitQuery = (Button) findViewById(R.id.btn_submitQuery);
            //btn_submitQuery.setSelected(true);



            btn_submitQuery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* if(checkMandatoryNew()){

                    }*/

                    LayoutInflater inflater = getLayoutInflater();
                    View alertLayout = inflater.inflate(R.layout.custom_student_query, null);




                    Button btn_sendRequest = (Button) alertLayout.findViewById(R.id.btn_sendRequest);

                    request_statename_et = (EditText) alertLayout.findViewById(R.id.request_statename_et);
                    request_districtname_et = (EditText) alertLayout.findViewById(R.id.request_districtname_et);
                    request_talukaname_et=(EditText) alertLayout.findViewById(R.id.request_talukaname_et);
                    request_Collegename_et=(EditText) alertLayout.findViewById(R.id.request_Collegename_et);
                    request_studentname_et=(EditText) alertLayout.findViewById(R.id.request_studentname_et);


                    edt_mailId_Query = (EditText) alertLayout.findViewById(R.id.edt_mailId);
                    edt_description_Query = (EditText) alertLayout.findViewById(R.id.edt_description);




                    AlertDialog.Builder alert = new AlertDialog.Builder(EditProfileActivity.this);
                    // this is set the view from XML inside AlertDialog
                    alert.setView(alertLayout);
                    // disallow cancel of AlertDialog on click of back button and outside touch
                    alert.setCancelable(true);

        /*        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
        *//*                String user = etUsername.getText().toString();
                        String pass = etEmail.getText().toString();*//*
                        //Toast.makeText(getBaseContext(), "Username: " + user + " Email: " + pass, Toast.LENGTH_SHORT).show();
                    }
                });*/

                    dialog = alert.create();

                    btn_sendRequest.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Toast.makeText(context, "Request Has Been Sent", Toast.LENGTH_LONG).show();
                            if(checkMandatoryQuery()){
                                SubmitQuery submitQuery = new SubmitQuery(context);
                                submitQuery.execute();
                            }


                        }
                    });


                    dialog.show();

                }
            });
        }



    } //end of oncreate()

    private boolean checkMandatoryQuery()
    {


        boolean b_edtmailquery,b_vaildemail,b_description,b_request_statename,b_request_districtname;
        boolean b_request_talukaname,b_request_Collegename,b_request_studentname;

        b_edtmailquery=b_vaildemail=b_description=b_request_statename=b_request_districtname=true;
        b_request_talukaname=b_request_Collegename=b_request_studentname=true;




        if(edt_mailId_Query.getText().toString().isEmpty() || edt_mailId_Query.getText().equals(null)) {
            edt_mailId_Query.setError("Enter Email ID");
            b_edtmailquery=false;
            //return false;
        }


        if(!isValidEmail(edt_mailId_Query.getText().toString().trim())) {
            edt_mailId_Query.setError("Invalid Email ID");
            b_vaildemail=false;
            //return false;
        }



        if(edt_description_Query.getText().toString().isEmpty() || edt_description_Query.getText().equals(null)){
            edt_description_Query.setError("Enter Query Description");
            b_description=false;
        }

        //request_statename_et,request_districtname_et,request_talukaname_et,request_Collegename_et,request_studentname_et;
        if(request_statename_et.getText().toString().length()<3)
        {
            request_statename_et.setError("Minimum 3 character");
            b_request_statename=false;
        }

        if(request_districtname_et.getText().toString().length()<3)
        {
            request_districtname_et.setError("Minimum 3 character");
            b_request_districtname=false;
        }

        if(request_talukaname_et.getText().toString().length()<3)
        {
            request_talukaname_et.setError("Minimum 3 character");
            b_request_talukaname=false;
        }

        if(request_Collegename_et.getText().toString().length()<5)
        {
            request_Collegename_et.setError("Minimum 5 character");
            b_request_Collegename=false;
        }

        if(request_studentname_et.getText().toString().length()<4)
        {
            request_studentname_et.setError("Minimum 4 character");
            b_request_studentname=false;
        }


        return (b_edtmailquery&&b_vaildemail&&b_description&&b_request_statename&&b_request_districtname&&
        b_request_talukaname&&b_request_Collegename&&b_request_studentname);

    }






    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }





 /*   private boolean checkMandatoryNew() {

    }
*/

    public class SubmitQuery extends AsyncTask<String, Void, SoapPrimitive> {

        Context context;
        //AlertDialog alertDialog;

        //private ProgressBar progressBar;

        private ProgressDialog progressDialog;

        SubmitQuery(Context ctx) {
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(String... params) {

            SoapPrimitive response = submitQuery();

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
        protected void onPostExecute(SoapPrimitive result) {



            if(result.toString().equals("success")){
                dialog.dismiss();
                Toast.makeText(context,"Your Request sent to the manager",Toast.LENGTH_LONG).show();
            }

            progressDialog.dismiss();
        }

    }


    private SoapPrimitive submitQuery()
    {

        String METHOD_NAME = "SendStudentRequestforManager";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/SendStudentRequestforManager";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);



            /*







      */

            request.addProperty("Lead_Id", str_LeadID);//<Lead_Id>string</Lead_Id>
            //request.addProperty("StudentName",edt_studName.getText().toString());
            request.addProperty("Email_id",edt_mailId_Query.getText().toString());//<Email_id>string</Email_id>
            request.addProperty("Student_MobileNo",mobileno_tv.getText().toString());//<Student_MobileNo>string</Student_MobileNo>
            request.addProperty("Message",edt_description_Query.getText().toString());// <Message>string</Message>

            request.addProperty("StudentName",request_studentname_et.getText().toString());//<StudentName>string</StudentName>
            request.addProperty("StateName",request_statename_et.getText().toString());//<StateName>string</StateName>
            request.addProperty("DistrictName",request_districtname_et.getText().toString());//<DistrictName>string</DistrictName>
            request.addProperty("TalukaName",request_talukaname_et.getText().toString());//<TalukaName>string</TalukaName>
            request.addProperty("CollegeName",request_Collegename_et.getText().toString());//<CollegeName>string</CollegeName>

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            //  SendStudentRequestforManager{Lead_Id=MF00993; Email_id=testing; Student_MobileNo=9689240475; Message=testing; }

            Log.d("Requestisssss",request.toString());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                // Log.d("soapResponseyyyyyyy",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.d("soapResponseyyyyyyy",response.toString());

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































    private void selectAllMasterData() {
        //getStateDetails();
        //setStateSpinner();
        //getDistrictDetails();
        // setDistrictSpinner();
        //setStateSpinner();
    }







    public void onRadioButtonGenderClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_male:
                if (checked) {
                    str_gender="M";
                }
                break;
            case R.id.rdb_female:
                if (checked) {
                    str_gender="F";
                }
                break;
        }
    }

    public void onRadioButtonBankClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.rdb_passbook:
                if (checked) {
                    str_bankDoc="1";
                }
                break;
            case R.id.rdb_check:
                if (checked) {
                    str_bankDoc="2";
                }
                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                //onSelectFromGalleryResult(data);

                /*if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                {*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {

                    Log.d("Insidexxxxxx","greater than M");

                    //if (android.os.Build.VERSION.SDK_INT > 24) {

                    /*if(1>2)
                    {*/
                    Uri selectedImage = data.getData();
                    Log.e("uri", selectedImage.toString());

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();


                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);


                    //studPickBase64 = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);

                  /*  ImageView imgProj = (ImageView) view2.findViewById(projImageId);
                    imgProj.setImageBitmap(bitmap);*/

//In marshmallow version camara image rotation code

                    ExifInterface exif= null;
                    try {
                        exif = new ExifInterface(picturePath.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("EXIF value1", exif.getAttribute(ExifInterface.TAG_ORIENTATION));
                    if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")){
                        bitmap= rotate(bitmap, 90);
                    } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")){
                        bitmap= rotate(bitmap, 270);
                    } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")){
                        bitmap= rotate(bitmap, 180);
                    }/* else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")){
                        bitmap= rotate(bitmap, 0);
                    }*/
//-----------------------------------
                    img_profilePick.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    studPickBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Log.d("studPickBase64sssssss:",studPickBase64);


                } else {
                    Log.d("Insidexxxxxx","elsexxxx");
                    Bundle extras2 = data.getExtras();
                    if (extras2 != null)
                    {
                        Log.d("Insidexxxxxx","extras2 not null");
                        //vijay camera

                        /*
                        Bitmap yourImage = extras2.getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        //imageInByte;
                        imageinbytesArray = stream.toByteArray();
                        //	Log.e("output before conversion", imageInByte.toString());
                        //	Log.d("Insert: ", "Inserting ..image");
                        Bitmap bm = BitmapFactory.decodeByteArray(imageinbytesArray, 0, imageinbytesArray.length);
                        //	Log.d("Insert image report: ",bm.toString() );
                        studPickBase64 = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);
                        img_profilePick.setImageBitmap(bm);
                       */
                        if (data == null)
                        {
                            Toast.makeText(getApplicationContext(),"Failed to read picture data!",Toast.LENGTH_SHORT).show();
                            return;
                        }


                        try {
                            actualImage = Class_FileUtil_forImage.from(this, data.getData());
                            img_profilePick.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));

                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(),"Failed to read picture data!",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }



                    }
                }


            } else if (requestCode == REQUEST_CAMERA) {
                //onCaptureImageResult(data);
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    // byte[] imageInByte;
                    imageinbytesArray = stream.toByteArray();

                    studPickBase64 = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);
                    //	Log.e("output before conversion", imageInByte.toString());
                    // Inserting Contacts
                    bmp_fromCamera = BitmapFactory.decodeByteArray(imageinbytesArray, 0, imageinbytesArray.length);
                    //report_photo.setImageBitmap(bm);

                    /*Log.d("projectImageidxxxx",String.valueOf(projImageId));

                    ImageView imgProj = (ImageView) view2.findViewById(projImageId);
                    imgProj.setImageBitmap(bm);*/


                /*    Bitmap circleBitmap = Bitmap.createBitmap(yourImage.getWidth(), yourImage.getHeight(), Bitmap.Config.ARGB_8888);

                    BitmapShader shader = new BitmapShader (yourImage,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    Paint paint = new Paint();
                    paint.setShader(shader);

                    Canvas c = new Canvas(circleBitmap);
                    c.drawCircle(yourImage.getWidth()/2, yourImage.getHeight()/2, yourImage.getWidth()/2, paint);

                    img_profilePick.setImageBitmap(circleBitmap);*/


                    img_profilePick.setImageBitmap(bmp_fromCamera);


                }
            }
            else if (requestCode == SELECT_FILE_BANK) {
                //onSelectFromGalleryResult(data);

                /*if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
                {*/
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {

                    Log.d("Insidexxxxxx","greater than M");

                    //if (android.os.Build.VERSION.SDK_INT > 24) {

                    /*if(1>2)
                    {*/
                    Uri selectedImage = data.getData();
                    Log.e("uri", selectedImage.toString());

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();


                    Bitmap bitmap = BitmapFactory.decodeFile(picturePath);


                    //studPickBase64 = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);

                  /*  ImageView imgProj = (ImageView) view2.findViewById(projImageId);
                    imgProj.setImageBitmap(bitmap);*/

//In marshmallow version camara image rotation code

                    ExifInterface exif= null;
                    try {
                        exif = new ExifInterface(picturePath.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("EXIF value1", exif.getAttribute(ExifInterface.TAG_ORIENTATION));
                    if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")){
                        bitmap= rotate(bitmap, 90);
                    } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")){
                        bitmap= rotate(bitmap, 270);
                    } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")){
                        bitmap= rotate(bitmap, 180);
                    }/* else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")){
                        bitmap= rotate(bitmap, 0);
                    }*/
//-----------------------------------
                    img_bankPick.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream .toByteArray();
                    bankPicBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Log.d("bank pic:",bankPicBase64);


                } else {
                    Log.d("Insidexxxxxx","elsexxxx");
                    Bundle extras2 = data.getExtras();
                    if (extras2 != null)
                    {
                        Log.d("Insidexxxxxx","extras2 not null");
                        //vijay camera

                        /*
                        Bitmap yourImage = extras2.getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        //imageInByte;
                        imageinbytesArray = stream.toByteArray();
                        //	Log.e("output before conversion", imageInByte.toString());
                        //	Log.d("Insert: ", "Inserting ..image");
                        Bitmap bm = BitmapFactory.decodeByteArray(imageinbytesArray, 0, imageinbytesArray.length);
                        //	Log.d("Insert image report: ",bm.toString() );
                        studPickBase64 = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);
                        img_profilePick.setImageBitmap(bm);
                       */
                        if (data == null)
                        {
                            Toast.makeText(getApplicationContext(),"Failed to read picture data!",Toast.LENGTH_SHORT).show();
                            return;
                        }


                        try {
                            actualImage = Class_FileUtil_forImage.from(this, data.getData());
                            img_bankPick.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));

                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(),"Failed to read picture data!",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }



                    }
                }


            } else if (requestCode == REQUEST_CAMERA_BANK) {
                //onCaptureImageResult(data);
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    // byte[] imageInByte;
                    imageinbytesArray = stream.toByteArray();

                    bankPicBase64 = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);
                    //	Log.e("output before conversion", imageInByte.toString());
                    // Inserting Contacts
                    bmp_fromCamera = BitmapFactory.decodeByteArray(imageinbytesArray, 0, imageinbytesArray.length);

                    img_bankPick.setImageBitmap(bmp_fromCamera);


                }
            }
        }
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        //       mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    //userChoosenTask ="Take Photo";
                    cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    //userChoosenTask ="Choose from Library";
                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void selectImageBank() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    //userChoosenTask ="Take Photo";
                    cameraIntentBank();

                } else if (items[item].equals("Choose from Library")) {
                    //userChoosenTask ="Choose from Library";
                    galleryIntentBank();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }
    private void cameraIntentBank() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA_BANK);
    }

    private void galleryIntentBank() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE_BANK);
    }






    private void setDateOfBirth() {

        final Calendar c = Calendar.getInstance();

        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mYear = c.get(Calendar.YEAR)-15;
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
                                    dobseterror_tv.setVisibility(View.GONE);
                                    edt_dob.setText(dateFormat.format(d).toString());

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

    @SuppressLint("RestrictedApi")
    private void setGenderSpinner() {
        final ArrayList listgender = new ArrayList();
        listgender.add("Male");
        listgender.add("Female");

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        ArrayAdapter dataAdapterGender = new ArrayAdapter(context, R.layout.simple_spinner_items, listgender);

        // Drop down layout style - list view with radio button
        dataAdapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_gender.setAdapter(dataAdapterGender);
        spin_gender.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
    }

    @SuppressLint("RestrictedApi")
    private void setBloodGroupSpinner() {
        final ArrayList listBg = new ArrayList();
        listBg.add("A+");
        listBg.add("B+");
        listBg.add("A-");
        listBg.add("B-");
        listBg.add("AB+");
        listBg.add("AB-");
        listBg.add("O+");
        listBg.add("O-");
        listBg.add("Bombay");

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        dataAdapter_bloodgroup = new ArrayAdapter(context, R.layout.simple_spinner_items, listBg);

        // Drop down layout style - list view with radio button
        dataAdapter_bloodgroup.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        spin_bg.setAdapter(dataAdapter_bloodgroup);
        spin_bg.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

    }

    @Override
    public void onBackPressed()
    {
        if((str_isProfileEdited.equals("1")))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(EditProfileActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("LEADCampus");
            dialog.setMessage("Are you sure want to Exit from Edit Profile" );

            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Intent i = new Intent(EditProfileActivity.this,HomeActivity.class);
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
            alert.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                }
            });
            alert.show();

            //alert.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
        }

        if((str_isProfileEdited.equals("0"))||(str_isProfileEdited.equals("2")))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(EditProfileActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("LEADCampus");
            dialog.setMessage("Are you sure want to Exit from Edit Profile" );

            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Intent i = new Intent(EditProfileActivity.this,LoginActivity.class);
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
            alert.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                }
            });
            alert.show();
        }



    }// On back Press







// set spinner


    public void setSpinner()
    {
        spin_semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_Sem = (Class_Sem) spin_semester.getSelectedItem();

           /* String x=obj_Class_Centers.getcenter_name().toString();
            String y=obj_Class_Centers.getCenterCode().toString();
            Toast.makeText(getApplicationContext(),"name: "+x+"ID: "+y,Toast.LENGTH_SHORT).show();
*/
                str_semid = obj_Class_Sem.getsem_id().toString(); //semID
                str_semname = obj_Class_Sem.getsem_name().toString();  //1

                // Toast.makeText(getApplicationContext(),"str_semid: "+str_semid+"str_semname: "+str_semname,Toast.LENGTH_SHORT).show();

                //MentorID= obj_Class_Centers.getMem_id().toString();
            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });







        spin_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_Course = (Class_Course) spin_course.getSelectedItem();

                str_courseid = obj_Class_Course.getcourse_id().toString();
                str_coursename = obj_Class_Course.getcourse_name().toString();


                // Toast.makeText(getApplicationContext(),"str_courseid: "+str_courseid+ " " +"str_coursename: "+str_coursename,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });


        spin_program.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                obj_Class_Program = (Class_Program) spin_program.getSelectedItem();

                str_programid = obj_Class_Program.getprogram_id().toString();
                str_programname = obj_Class_Program.getprog_name().toString();


                // Toast.makeText(getApplicationContext(),"str_programid: "+str_programid,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });






        spin_bg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                str_bloodgroup = spin_bg.getSelectedItem().toString();



                // Toast.makeText(getApplicationContext(),"Blood:"+str_bloodgroup,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });


    }

//set spinner



// ============================All AsynTask Call

    public void getstatelist_AsynTask()
    {
        StatelistAsyncCallWS task = new StatelistAsyncCallWS(EditProfileActivity.this);
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

        public StatelistAsyncCallWS(EditProfileActivity activity) {
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

                if(str_isProfileEdited.equals("1")||str_isProfileEdited.equals("2"))
                {
                    dataAdapter = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_States);
                    dataAdapter.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_state.setAdapter(dataAdapter);
                    spin_state.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                    int spinnerposition =1;
                    //spinnerposition = dataAdapter.getPosition(O_statecode);
                    spinnerposition= Integer.parseInt(O_statecode);
                    spin_state.setSelection(spinnerposition);
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
            request.addProperty("Manager_Id", 0);//<stateid>long</stateid>


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




    public void getdistrictlist_AsynTask()
    {
        DistrictlistAsyncCallWS task = new DistrictlistAsyncCallWS(EditProfileActivity.this);
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

        public DistrictlistAsyncCallWS(EditProfileActivity activity) {
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

                if(str_isProfileEdited.equals("1")||str_isProfileEdited.equals("2"))
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

//https://mis.leadcampus.org:8084/leadws/Login.asmx?op=GetDistricts
        try {
//vijay district
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            //  request.addProperty("distid", Long.parseLong(str_Sids));//Long

            // request.addProperty("distid", 17);//Long

            request.addProperty("stateId", Long.parseLong(str_Sids));//Long
            request.addProperty("Manager_Id", 0);//<Manager_Id>int</Manager_Id>

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
        TaluklistAsyncCallWS task = new TaluklistAsyncCallWS(EditProfileActivity.this);
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

        public TaluklistAsyncCallWS(EditProfileActivity activity) {
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
                if (str_isProfileEdited.equals("1")||str_isProfileEdited.equals("2"))
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
            request.addProperty("Manager_Id", 0);//<Manager_Id>int</Manager_Id>


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
        CollegelistAsyncCallWS task = new CollegelistAsyncCallWS(EditProfileActivity.this);
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

        public CollegelistAsyncCallWS(EditProfileActivity activity) {
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
                if (str_isProfileEdited.equals("1")||str_isProfileEdited.equals("2"))
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

           /* <city>long</city>
      <type>string</type>


      */
            request.addProperty("city", Long.parseLong(str_Cityids));//  <city>long</city>
            request.addProperty("type", str_programname);// <type>string</type
            request.addProperty("Manager_Id", 0);//  <Manager_Id>int</Manager_Id>
            request.addProperty("Fees_Category_Id", 0);// <Fees_Category_Id>int</Fees_Category_Id>
            request.addProperty("User_Type", "student");//<User_Type>string</User_Type>


            //
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




    public void getSemlist_AsynTask()
    {
        getSemlist_AsynTask task = new getSemlist_AsynTask(EditProfileActivity.this);
        task.execute();
    }

    private class getSemlist_AsynTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
           /* dialog.setMessage("Please wait,SemList loading...");
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

            GetSemlist();  // get the Sem list
            return null;
        }

        public getSemlist_AsynTask(EditProfileActivity activity) {
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

//            Update_Semlist_spinner();
            if(str_SemStatus.equals("Success"))
            {
                if(str_isProfileEdited.equals("1")||str_isProfileEdited.equals("2"))
                {

                    dataAdapter_semlist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Sem);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_semlist.setDropDownViewResource(R.layout.spinnercustomstyle);

                    spin_semester.setAdapter(dataAdapter_semlist);
                    spin_semester.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                    if(O_semname!="" || O_semname!=null)
                    {
                        for (int i = 0; i < spin_semester.getCount(); i++) {
                            if (spin_semester.getItemAtPosition(i).toString().equals(O_semname))
                            {
                                spin_semester.setSelection(i);
                                break;
                            }
                        }
                    }
                }
                else
                {
                    dataAdapter_semlist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Sem);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_semlist.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_semester.setAdapter(dataAdapter_semlist);
                    spin_semester.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));

                }
            }

        }//end of onPostExecute
    }// end Async task


    public void GetSemlist()
    {
        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "GetSemesteronCourse";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/GetSemesteronCourse";
        //for final
        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            //request.addProperty("courseId",Long.parseLong(str_programid));//<courseId>long</courseId>

            request.addProperty("courseId",Long.parseLong(str_courseid));//<courseId>long</courseId>
            request.addProperty("stream",Long.parseLong(str_programid));

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
                SoapObject  semresponse = (SoapObject ) response.getProperty(0);
                str_SemStatus=semresponse.getProperty("Status").toString();

                Log.e("respsemlist",response.toString());
                semlistcount = response.getPropertyCount();

                Log.d("Semcount", String.valueOf(response.getPropertyCount()));



                if(str_SemStatus.equals("Success"))
                {
                    if (semlistcount > 0) {
                        arrayObj_Class_Sem = new Class_Sem[semlistcount];


                        for (int i = 0; i < semlistcount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                         /*<SemId>long</SemId>
                        <SemName>string</SemName>
                         <Status>string</Status>*/


                            Class_Sem innerObj_Class_sem = new Class_Sem();
                            innerObj_Class_sem.setsem_id(response_soapobj.getProperty("SemId").toString()); //<SemId>long</SemId>
                            innerObj_Class_sem.setsem_name(response_soapobj.getProperty("SemName").toString()); //<SemName>string</SemName>
                            innerObj_Class_sem.setsem_status(response_soapobj.getProperty("Status").toString());// <Status>string</Status>







                            str_SemStatus = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_Sem[i] = innerObj_Class_sem;


                            String str_semID = response_soapobj.getProperty("SemId").toString();
                            String str_semname = response_soapobj.getProperty("SemName").toString();



                            //        DBCreate_semdetails_insert_2SQLiteDB(str_semID, str_semname);


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




    public void getCourselist_AsynTask()
    {
        getCourselist_AsynTask task = new getCourselist_AsynTask(EditProfileActivity.this);
        task.execute();
    }



    private class getCourselist_AsynTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
           /* dialog.setMessage("Please wait,CourseList loading...");
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

            GetCourselist();  // get the Course list
            return null;
        }

        public getCourselist_AsynTask(EditProfileActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @SuppressLint("RestrictedApi")
        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            //    dialog.dismiss();

            //   Update_Courselist_spinner();


            if(str_CourseStatus.equals("Success"))
            {

                if(str_isProfileEdited.equals("1")||str_isProfileEdited.equals("2"))
                {

                    dataAdapter_course = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Course);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_course.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_course.setAdapter(dataAdapter_course);
                    spin_course.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));



                    Log.e("spin_course","spin_course:"+O_coursename.toString());

                    if(O_coursename!="" || O_coursename!=null)
                    {

                        for (int i = 0; i < spin_course.getCount(); i++) {
                            if (spin_course.getItemAtPosition(i).toString().equals(O_coursename))
                            {

                                spin_course.setSelection(i);
                                break;
                            }
                        }
                    }

                }
                else
                {

                    dataAdapter_course = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Course);
                    // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    dataAdapter_course.setDropDownViewResource(R.layout.spinnercustomstyle);
                    spin_course.setAdapter(dataAdapter_course);
                    spin_course.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
                }
            }

        }//end of onPostExecute
    }// end Async task



    public void GetCourselist()
    {

        String URL = Class_URL.URL_Login.toString().trim();
        String METHOD_NAME = "GetCoursesOnProgram";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/GetCoursesOnProgram";


//"http://mis.leadcampus.org/GetCoursesOnProgram
        //for final


        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("program", Long.parseLong(str_programid));//<courseid>long</courseid> str_ColID str_programid
            //<program>long</program>
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
                SoapObject courseResponse =(SoapObject) response.getProperty(0);
                str_CourseStatus =courseResponse.getProperty("Status").toString();
                Log.e("resp Courselist",response.toString());
                courselistcount = response.getPropertyCount();

                Log.d("Coursecount", String.valueOf(response.getPropertyCount()));


                //Status=Success;
                if(str_CourseStatus.equals("Success"))
                {
                    if (courselistcount > 0) {
                        arrayObj_Class_Course = new Class_Course[courselistcount];


                        for (int i = 0; i < courselistcount; i++)
                        {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                       /* *<CourseId>long</CourseId>
          <CourseName>string</CourseName>
          <ProgrammeCode>long</ProgrammeCode>
          <Status>string</Status>*/


                            Class_Course innerObj_Class_course = new Class_Course();
                            innerObj_Class_course.setcourse_id(response_soapobj.getProperty("CourseId").toString()); //<CourseId>long</CourseId>
                            innerObj_Class_course.setcourse_name(response_soapobj.getProperty("CourseName").toString()); // <CourseName>string</CourseName>
                            innerObj_Class_course.setcourse_progcode(response_soapobj.getProperty("ProgrammeCode").toString()); // <ProgrammeCode>long</ProgrammeCode>
                            innerObj_Class_course.setcourse_status(response_soapobj.getProperty("Status").toString());// <Status>string</Status>


                            str_CourseStatus = response_soapobj.getProperty("Status").toString();

                            arrayObj_Class_Course[i] = innerObj_Class_course;


                            String str_coursID = response_soapobj.getProperty("CourseId").toString();
                            String str_coursname = response_soapobj.getProperty("CourseName").toString();
                            String str_courseProgCode= response_soapobj.getProperty("ProgrammeCode").toString();



                            //DBCreate_Coursedetails_insert_2SQLiteDB(str_coursID, str_coursname,str_courseProgCode);


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








    public void getProgramlist_AsynTask()
    {
        getProgramlist_AsynTask task = new getProgramlist_AsynTask(EditProfileActivity.this);
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

        public getProgramlist_AsynTask(EditProfileActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            // dialog.dismiss();

            //Update_Programlist_spinner();


            if(str_ProgramStatus.equals("Success"))
            {
                if(str_isProfileEdited.equals("1")||str_isProfileEdited.equals("2"))
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

            request.addProperty("Manager_Id", 0);// <Manager_Id>int</Manager_Id>
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







    public void SaveStudentDetails_AsynTask()
    {
        SaveStudentDetails_AsynTask task = new SaveStudentDetails_AsynTask(EditProfileActivity.this);
        task.execute();
    }



    private class SaveStudentDetails_AsynTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;
        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait,Updating Details loading...");
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

            SendStudentDetails();  //
            return null;
        }

        public SaveStudentDetails_AsynTask(EditProfileActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

            dialog.dismiss();



            if(internet_issue1.equals("Noerror")) {

                if (str_editprofile_response.equals("Profile Updated.")||str_editprofile_response.equals("Profile Updated but imaeg not saved.")) {
                   /* Toast.makeText(getApplicationContext(), "Profile Updated Successfully", Toast.LENGTH_LONG).show();

                    editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefID_S_isprofileEdited,"1");
                    editor_S.putString(PrefId_S_Gender,str_gender.toString());
                    Log.e("gender","gender"+str_gender.toString());
                    editor_S.commit();

                    Intent i = new Intent(EditProfileActivity.this,HomeActivity.class);
                    i.putExtra("fromeditProfile","yes");
                    startActivity(i);*/


                    AlertDialog.Builder dialog = new AlertDialog.Builder(EditProfileActivity.this);
                    dialog.setCancelable(false);
                    dialog.setTitle("LEADCampus");
                    dialog.setMessage("Thank you for updating the profile");

                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {
                            editor_S = shardpref_S_obj.edit();
                            editor_S.putString(PrefID_S_isprofileEdited,"1");
                            editor_S.putString(PrefId_S_Gender,str_gender.toString());
                            Log.e("gender","gender"+str_gender.toString());
                            editor_S.commit();

                            Intent i = new Intent(EditProfileActivity.this,HomeActivity.class);
                            i.putExtra("fromeditProfile","yes");
                            startActivity(i);
                        }
                    });

                    final AlertDialog alert = dialog.create();
                    alert.setOnShowListener( new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0)
                        {
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                        }
                    });
                    alert.show();

                } else {
                    Toast.makeText(getApplicationContext(), "W:Error"+str_editprofile_response, Toast.LENGTH_LONG).show();
                }

            }else
            {
                Toast.makeText(getApplicationContext(), "Error:"+str_editprofile_response, Toast.LENGTH_LONG).show();
            }


        }//end of onPostExecute
    }// end Async task






    public void SendStudentDetails()
    {

        String URL = Class_URL.URL_Login.toString().trim();
        /*String METHOD_NAME = "UpdateStudentProfilewithstring";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/UpdateStudentProfilewithstring";
*/

        String METHOD_NAME = "UpdateStudentProfilewithCompress_Testbed";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/UpdateStudentProfilewithCompress_Testbed";


        //UpdateStudentProfilewithCompress



        try {
            str_editprofile_response="x";


           /* DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String inputDateStr=edt_dob.getText().toString();
            Date date = inputFormat.parse(inputDateStr);
            String str_DateddMMyyyy = outputFormat.format(date);
            Log.e("dateformat",str_DateddMMyyyy.toString());*/

            long long_aadhara =0;
            //long long_account =0;
            String  str_iifscode = " ";
            if(imageinbytesArray.length!=0){}else{ imageinbytesArray=null;}


            if(aadhara_et.getText().toString().trim().length()==0)
            { long_aadhara=Long.parseLong("0");}
            else {long_aadhara=Long.parseLong(aadhara_et.getText().toString().trim());  }

           /* if(account_et.getText().toString().trim().length()==0)
            { long_account=0; }
            else
            {  long_account =Long.parseLong(account_et.getText().toString().trim()); }
*/
            if(iifscode_et.getText().toString().trim().length()==0)
            { str_iifscode= " "; }
            else { str_iifscode =iifscode_et.getText().toString().trim(); }


            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("RegistrationId", Long.parseLong(str_RegID));//<RegistrationId>long</RegistrationId>
            request.addProperty("Lead_Id", str_LeadID);//<Lead_Id>string</Lead_Id>
            request.addProperty("StudentName", edt_studName.getText().toString().trim());//<StudentName>string</StudentName>
            request.addProperty("DOB", edt_dob.getText().toString());//<DOB>string</DOB>
            request.addProperty("StateCode", str_Sids);// <StateCode>int</StateCode>
            request.addProperty("DistrictCode", Integer.parseInt(str_Dids));//<DistrictCode>int</DistrictCode>
            request.addProperty("TalukaCode", Integer.parseInt(str_Cityids));//<TalukaCode>int</TalukaCode>
            request.addProperty("CollegeCode", Integer.parseInt(str_ColID));//<CollegeCode>int</CollegeCode>
            request.addProperty("StreamCode", Integer.parseInt(str_programid));//<StreamCode>int</StreamCode>
            request.addProperty("CourseCode", Integer.parseInt(str_courseid));//<CourseCode>int</CourseCode>
            request.addProperty("SemCode",Integer.parseInt(str_semid));//<SemCode>int</SemCode>
            request.addProperty("AadharNo",long_aadhara);//<AadharNo>long</AadharNo>
            request.addProperty("Address", " ");// <Address>string</Address>
            request.addProperty("MailId", emailid_et.getText().toString());//<MailId>string</MailId>
            request.addProperty("AlternativeMobileNo", Long.parseLong(alernativecell_et.getText().toString()));//<AlternativeMobileNo>long</AlternativeMobileNo>
            request.addProperty("AlternateMailId", " ");//<AlternateMailId>string</AlternateMailId>
            request.addProperty("Gender",str_gender.toString() );//<Gender>string</Gender>
            request.addProperty("BloodGroup", str_bloodgroup.toString());//<BloodGroup>string</BloodGroup>
            request.addProperty("FacebookId", " ");//<FacebookId>string</FacebookId>
            request.addProperty("LinkedInId", " ");//<LinkedInId>string</LinkedInId>
            request.addProperty("AcademicCode", str_academiccode);//<AcademicCode>int</AcademicCode>
            request.addProperty("Bank_Name", " ");//<Bank_Name>string</Bank_Name>
            request.addProperty("Branch_Name", " ");//<Branch_Name>string</Branch_Name>
            request.addProperty("Account_No", account_et.getText().toString().trim());// <Account_No>string</Account_No>
            request.addProperty("IFSC_code", str_iifscode);//<IFSC_code>string</IFSC_code>
            request.addProperty("MyTalent", mytalent_et.getText().toString().trim());//<MyTalent>string</MyTalent>
            //request.addProperty("ProfileImage", null);/// <ProfileImage>base64Binary</ProfileImage>
            request.addProperty("ProfileImage", studPickBase64);
            request.addProperty("BankDocument", bankPicBase64);
            request.addProperty("Document_Type", str_bankDoc);



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


                SoapPrimitive  response = (SoapPrimitive ) envelope.getResponse();

                str_editprofile_response = response.toString();
                Log.e("resp editprofile",response.toString());
                //E/resp editprofile: Profile Updated.ed


            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("editprofile fail", "> " + t.getMessage());
                internet_issue1 = "Editprofilerror";
            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }

    }//End of uploaddetails


    private void getStudentDetails() {
        GetStudentDetails getStudentDetails = new GetStudentDetails(EditProfileActivity.this);
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




            edt_studName.setText(O_StudentName.toString().trim());

            if(O_AlternativeMobileNo.toString().length()==0)
            {alernativecell_et.setText(" "); }
            else { alernativecell_et.setText(O_AlternativeMobileNo.toString().trim()); }


            emailid_et.setText(O_MailId.toString().trim());

            if(O_AadharNo.equals("anyType{}")||O_AadharNo.equals("anytype{}")||O_AadharNo.equals("0"))
            { aadhara_et.setText(""); }
            else
            {   aadhara_et.setText(O_AadharNo.toString().trim()); }

            if(O_Account_No.equals("anyType{}")||O_Account_No.equals("anytype{}")||O_Account_No.equals("0"))
            { account_et.setText(""); }
            else
            {   account_et.setText(O_Account_No.toString().trim());  }



            if(O_IFSC_code.equals("anyType{}")||O_IFSC_code.equals("anytype{}")||O_IFSC_code.equals("0"))
            {   iifscode_et.setText("");}
            else{ iifscode_et.setText(O_IFSC_code.toString().trim()); }



            if(O_mytalent.equalsIgnoreCase("anyType{}")||O_mytalent.isEmpty())
            { mytalent_et.setText(""); }
            else{ mytalent_et.setText(O_mytalent.toString().trim()); }

            edt_dob.setText(O_DOB.toString().trim());
            leadid_tv.setText(O_leadID.toString().trim());
            mobileno_tv.setText(O_MobileNo.toString().trim());
            student_tv.setText(O_Student_Type.toString().trim());

            if(O_Gender.equals("M"))
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
            }

            img_profilePick.setImageBitmap(Studentbmp);

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


                O_RegistrationId=response.getProperty("RegistrationId").toString();
                O_StudentName=response.getProperty("StudentName").toString();
                O_DOB=response.getProperty("DOB").toString();
                String RegistrationDate=response.getProperty("RegistrationDate").toString();
                String isProfileEdit=response.getProperty("isProfileEdit").toString();
                O_statecode=response.getProperty("StateCode").toString();
                O_districtcode=response.getProperty("DistrictCode").toString();
                O_talukacode=response.getProperty("TalukaCode").toString();
                O_collegenameCode=response.getProperty("CollegeCode").toString();
                O_coursecode=response.getProperty("CourseCode").toString();
                O_StreamprogramCode=response.getProperty("StreamCode").toString();
                String O_semcode=response.getProperty("SemName").toString();
                O_AadharNo=response.getProperty("AadharNo").toString();
                String O_Address=response.getProperty("Address").toString();
                O_MailId=response.getProperty("MailId").toString();
                O_AlternativeMobileNo=response.getProperty("AlternativeMobileNo").toString();
                O_Gender=response.getProperty("Gender").toString();
                O_BloodGroup=response.getProperty("BloodGroup").toString();
                String O_FacebookId=response.getProperty("FacebookId").toString();
                String O_LinkedInId=response.getProperty("LinkedInId").toString();
                String O_AcademicCode=response.getProperty("AcademicCode").toString();
                O_Student_Type=response.getProperty("Student_Type").toString();
                String O_Bank_Name=response.getProperty("Bank_Name").toString();
                String O_Branch_Name=response.getProperty("Branch_Name").toString();
                O_Account_No=response.getProperty("Account_No").toString();
                O_IFSC_code=response.getProperty("IFSC_code").toString();
                String O_Status=response.getProperty("Status").toString();
                O_Student_Image=response.getProperty("Student_Image").toString();
                String O_ManagerMobileNumber=response.getProperty("ManagerMobileNumber").toString();
                O_MobileNo=response.getProperty("MobileNo").toString();
                O_mytalent=response.getProperty("MyTalent").toString();//<MyTalent/>
                Log.e("tag","RegistrationId="+O_RegistrationId+"StudentName="+O_StudentName+"DOB="+O_DOB);
                Log.e("tag","RegistrationDate="+RegistrationDate+"isProfileEdit="+isProfileEdit+"StateCode="+O_statecode);
                Log.e("tag","DistrictCode="+O_districtcode+"TalukaCode="+O_talukacode+"CollegeCode="+O_collegenameCode);

                O_leadID =response.getProperty("Lead_Id").toString();
                O_altermailid= response .getProperty("AlternateMailId").toString();
                O_collegename = response .getProperty("College_Name").toString();
                O_statename = response .getProperty("StateName").toString();
                O_districtname = response .getProperty("DistrictName").toString();
                O_talukname = response .getProperty("Taluk_Name").toString();
                O_programmename = response .getProperty("programmeName").toString();
                O_coursename = response .getProperty("CourseName").toString();
                O_semname = response .getProperty("SemName").toString();




                String arr[] = O_Student_Image.split("/", 2);

                String firstWord = arr[0];   //the
                String secondWord = arr[1];

                Str_studentImgUrl = serverPath + secondWord;
                Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                Log.e("student URL", ""+Str_studentImgUrl);

                URL url = new URL(Str_studentImgUrl.toString().trim());
                Studentbmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

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













// Validation


    public boolean validation()
    {
        boolean validationresult = true;

        boolean b_studenname,b_alernativecell,b_emailid,b_dob,b_aadhara1,b_aadhara2,b_account1,b_account2,b_iifscode1,b_iifscode2;
        boolean b_state,b_district,b_city,b_college,b_course,b_sem,b_programid,b_mytalent;

        b_studenname=b_alernativecell=b_emailid=b_dob=b_aadhara1=b_aadhara2=b_account1=b_account2=b_iifscode1=b_iifscode2=true;
        b_state=b_district=b_city=b_college=b_course=b_sem=b_programid=b_mytalent=true;

        if (edt_studName.getText().toString().length() == 0 || edt_studName.getText().toString().length() <= 2 || edt_studName.getText().toString().trim().length() == 0) {
            edt_studName.setError("InValid Student name");
            edt_studName.requestFocus();
            b_studenname=false;
            validationresult = false;
        }

        if (alernativecell_et.getText().toString().length() == 0 || alernativecell_et.getText().toString().length() <= 9) {
            alernativecell_et.setError("InValid Mobile number");
            //alernativecell_et.requestFocus();
            b_alernativecell=false;
            validationresult = false;
        }

        if (emailid_et.getText().toString().length() == 0 ||emailid_et.getText().toString().length()<=5) {
            emailid_et.setError("Enter Valid EmailID!");
            //emailid_et.requestFocus();
            b_emailid=false;
            validationresult = false;
        }


        if (edt_dob.getText().toString().length() == 0 ||edt_dob.getText().toString().length()<=5)
        {
            // edt_dob.setError("Enter the DOB!");
            //Toast.makeText(getApplicationContext(),"Enter the Date",Toast.LENGTH_SHORT).show();
            //edt_dob.requestFocus();
            dobseterror_tv.setVisibility(View.VISIBLE);
            dobseterror_tv.setError("Enter the DOB!");
            b_dob=false;
            validationresult = false;
        }


        if(aadhara_et.getText().toString().length() == 0)
        {
            b_aadhara1=true;
            validationresult= true;
        }else
        {
            if (aadhara_et.getText().toString().length() <= 11) {
                aadhara_et.setError("12 digit required");
                //aadhara_et.requestFocus();
                b_aadhara2=false;
                validationresult = false;
            }
        }



        if(account_et.getText().toString().length() == 0)
        { b_account1=true;
            validationresult = true;
        }
        else
        {
            if (account_et.getText().toString().length() < 10 && account_et.getText().toString().length() <21)
            {
                account_et.setError("Min 10,Max 20 digit required");
                // account_et.requestFocus();
                b_account2=false;
                validationresult = false;
            }
        }


        if (iifscode_et.getText().toString().length() == 0 )
        {
            b_iifscode1=true;
            validationresult = true;
        }
        else
        {
            if (iifscode_et.getText().toString().length() <=5) {
                iifscode_et.setError("Enter Valid IFSC Code");
                // account_et.requestFocus();
                b_iifscode2=false;
                validationresult = false;
            }
        }





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

        if(str_courseid.equals("x")||spin_course.getSelectedItem().toString().equals("Select Stream"))
        {   courseerror_tv.setVisibility(View.VISIBLE);
            courseerror_tv.setError("Select Stream");
            //Toast.makeText(getApplicationContext(),"select the Course",Toast.LENGTH_SHORT).show();
            b_college=false;
            validationresult = false;}


        if(str_semid.equals("x")||spin_semester.getSelectedItem().toString().equals("Select Semester"))
        { semerror_tv.setVisibility(View.VISIBLE);
            semerror_tv.setError("Select Semester");
            b_sem=false;
            validationresult = false;
        }


       /* if(mytalent_et.getText().toString().trim().length()<5)
        {
            mytalent_et.setError("Minimum 5 character");
            b_mytalent=false;

        }*/
           // (iifscode_et.getText().toString().length() <=5

        //return validationresult;
        return (b_studenname&&b_alernativecell&&b_emailid&&b_dob&&b_aadhara1&&b_aadhara2&&b_account1&&b_account2&&b_iifscode1
                &&b_iifscode2&&b_state&&b_district&&b_city&&b_college&&b_course&&b_sem&&b_programid&&b_mytalent);
    }














//validation

//----- DB Create

    public void DBCreate_Statedetails_insert_2SQLiteDB(String str_stateID,String str_statename)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Statelist(SID VARCHAR,SName VARCHAR);");


        String SQLiteQuery = "INSERT INTO Statelist (SID, SName)" +
                " VALUES ('"+str_stateID+"','"+str_statename+"');";
        db1.execSQL(SQLiteQuery);

    }

    public void DBCreate_Districtdetails_insert_2SQLiteDB(String str_districtID,String str_districtname,String str_districtstateid)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Districtlist(DID VARCHAR,DName VARCHAR,DStateID VARCHAR);");


        String SQLiteQuery = "INSERT INTO Districtlist (DID,DName,DStateID)" +
                " VALUES ('"+str_districtID+"','"+str_districtname+"','"+str_districtstateid+"');";
        db1.execSQL(SQLiteQuery);

        db1.close();
    }


    public void DBCreate_Talukdetails_insert_2SQLiteDB(String str_talukID,String str_taluk_did,String str_talukname)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Taluklist(TID VARCHAR,TName VARCHAR,TDistrictID VARCHAR);");


        String SQLiteQuery = "INSERT INTO Taluklist(TID,TName,TDistrictID)" +
                " VALUES ('"+str_talukID+"','"+str_talukname+"','"+str_taluk_did+"');";
        db1.execSQL(SQLiteQuery);
        db1.close();
    }




    public void DBCreate_Collegedetails_insert_2SQLiteDB
            (String str_collegeID,String str_college_tid,String str_collegename)

    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Collegelist(CollegeID VARCHAR,CollegeName VARCHAR,CTalukID VARCHAR);");

        // db1.execSQL("CREATE TABLE IF NOT EXISTS Taluklist(TID VARCHAR,TName VARCHAR,TDistrictID VARCHAR);");

      /*  db1.execSQL("CREATE TABLE IF NOT EXISTS Collegelist(CollegeID VARCHAR,CollegeName VARCHAR,CTalukID VARCHAR," +
                "CurrentapplcountDB VARCHAR,CurrentadmissioncountDB VARCHAR,PreviousapplcountDB VARCHAR,PreviousadmissioncountDB VARCHAR);");
*/

        //Prev2prevapplcountDB
        /*String SQLiteQuery = "INSERT INTO Taluklist(TID,TName,TDistrictID)" +
                " VALUES ('"+str_talukID+"','"+str_talukname+"','"+str_taluk_did+"');";*/

        String SQLiteQuery = "INSERT INTO Collegelist(CollegeID,CollegeName,CTalukID)" +
                " VALUES ('"+str_collegeID+"','"+str_collegename+"','"+str_college_tid+"');";



        /*String SQLiteQuery = "INSERT INTO Collegelist(CollegeID,CollegeName,CTalukID," +
                "CurrentapplcountDB,CurrentadmissioncountDB,PreviousapplcountDB,PreviousadmissioncountDB)" +
                " VALUES ('"+str_collegeID+"','"+str_collegename+"','"+str_college_tid+"'," +
                "'"+str_currentapplcount+"','"+str_currentadmissioncount+"','"+str_previousapplcount+"','"+str_previousadmissioncount+"');";
*/
        db1.execSQL(SQLiteQuery);
        db1.close();
    }







    public void DBCreate_semdetails_insert_2SQLiteDB(String str_semID,String str_semname)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Semlist(SID VARCHAR,SName VARCHAR);");


        String SQLiteQuery = "INSERT INTO Semlist(SID,SName)" +
                " VALUES ('"+str_semID+"','"+str_semname+"');";
        db1.execSQL(SQLiteQuery);
        db1.close();
    }



    public void DBCreate_Coursedetails_insert_2SQLiteDB(String str_courseID,  String str_coursename ,String str_courseProgCode)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Courselist(CID VARCHAR,CName VARCHAR,CPCode VARCHAR);");


        String SQLiteQuery = "INSERT INTO Courselist(CID,CName,CPCode)" +
                " VALUES ('"+str_courseID+"','"+str_coursename+"','"+str_courseProgCode+"');";
        db1.execSQL(SQLiteQuery);
        db1.close();
    }



    public void DBCreate_Programdetails_insert_2SQLiteDB(String str_progID, String str_progname)
    {
        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Programlist(PID VARCHAR,PName VARCHAR);");

        String SQLiteQuery = "INSERT INTO Programlist(PID,PName)" +
                " VALUES ('"+str_progID+"','"+str_progname+"');";
        db1.execSQL(SQLiteQuery);
        db1.close();

    }














    public void uploadfromDB_Statelist()
    {

        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Statelist(SID VARCHAR,SName VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM Statelist", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i=0;
        arrayObj_Class_States2 = new Class_States[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_States innerObj_Class_StatesList = new Class_States();
                innerObj_Class_StatesList.setstate_id(cursor1.getString(cursor1.getColumnIndex("SID")));
                innerObj_Class_StatesList.setstate_name(cursor1.getString(cursor1.getColumnIndex("SName")));
                // innerObj_Class_StatesList.setCenterCode(cursor1.getString(cursor1.getColumnIndex("CCode")));

                arrayObj_Class_States2[i]=innerObj_Class_StatesList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends


        if(x>0) {
            // ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayObj_Class_Centers2);

            dataAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayObj_Class_States2);
            //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            //spin_state.setAdapter(dataAdapter);
            // Toast.makeText(getApplicationContext(),"In database",Toast.LENGTH_LONG).show();
            //  txtSearch1.setAdapter(dataAdapter);
            statesearch_ATV.setAdapter(dataAdapter);

        }

    }





    public void uploadfromDB_Districtlist()
    {

        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Districtlist(DID VARCHAR,DName VARCHAR,DStateID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM Districtlist", null);
        int x = cursor1.getCount();
        Log.d("cursor Dcount", Integer.toString(x));

        int i=0;
        arrayObj_Class_District2 = new Class_District[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_District innerObj_Class_DistrictList = new Class_District();
                innerObj_Class_DistrictList.setdistrict_id(cursor1.getString(cursor1.getColumnIndex("DID")));
                innerObj_Class_DistrictList.setdistrict_name(cursor1.getString(cursor1.getColumnIndex("DName")));
                innerObj_Class_DistrictList.setdstateid_id(cursor1.getString(cursor1.getColumnIndex("DStateID")));

                arrayObj_Class_District2[i]=innerObj_Class_DistrictList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends

        db1.close();
        if(x>0) {
            // ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayObj_Class_Centers2);

            dataAdapter_district = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_District2);
            //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter_district.setDropDownViewResource(R.layout.spinnercustomstyle);
            districtsearch_ATV.setAdapter(dataAdapter_district);
        }

    }


    public void Update_taluk_spinner(String str_Dids)
    {

        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Taluklist(TID VARCHAR,TName VARCHAR,TDistrictID VARCHAR);");
        Cursor cursor1 = db1.rawQuery("SELECT * FROM Taluklist WHERE TDistrictID='" + str_Dids + "'", null);
        int x = cursor1.getCount();
        Log.d("cursor Tcount", Integer.toString(x));

        int i=0;
        arrayObj_Class_Taluk2 = new Class_Taluk[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Taluk innerObj_Class_TalukList = new Class_Taluk();
                innerObj_Class_TalukList.settaluk_id(cursor1.getString(cursor1.getColumnIndex("TID")));
                innerObj_Class_TalukList.settalukname(cursor1.getString(cursor1.getColumnIndex("TName")));
                innerObj_Class_TalukList.settaluk_did(cursor1.getString(cursor1.getColumnIndex("TDistrictID")));


                arrayObj_Class_Taluk2[i]=innerObj_Class_TalukList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends



        db1.close();

        if(x>0) {
            // ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayObj_Class_Fellowship2);

            dataAdapter_city = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Taluk2);
            // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter_city.setDropDownViewResource(R.layout.spinnercustomstyle);
            citysearch_ATV.setAdapter(dataAdapter_city);
        }

    }


    // public void Update_college_spinner(String str_Tids,String str_collegetype)
    public void Update_college_spinner(String str_Tids)
    {

        //SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        // db1.execSQL("CREATE TABLE IF NOT EXISTS Collegelist(CollegeID VARCHAR,CollegeName VARCHAR,CTalukID VARCHAR);");

        /*db1.execSQL("CREATE TABLE IF NOT EXISTS Collegelist(CollegeID VARCHAR,CollegeName VARCHAR,CTalukID VARCHAR," +
                "CurrentapplcountDB VARCHAR,CurrentadmissioncountDB VARCHAR,PreviousapplcountDB VARCHAR,PreviousadmissioncountDB VARCHAR," +
                "Prev2prevapplcountDB VARCHAR,Prev2prevadmissioncountDB VARCHAR,CollegeTypeDB VARCHAR);");
*/


        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Collegelist(CollegeID VARCHAR,CollegeName VARCHAR,CTalukID VARCHAR);");


       /* db1.execSQL("CREATE TABLE IF NOT EXISTS Collegelist(CollegeID VARCHAR,CollegeName VARCHAR,CTalukID VARCHAR," +
                "CurrentapplcountDB VARCHAR,CurrentadmissioncountDB VARCHAR);");
*/

      /*  db1.execSQL("CREATE TABLE IF NOT EXISTS Collegelist(CollegeID VARCHAR,CollegeName VARCHAR,CTalukID VARCHAR," +
                "CurrentapplcountDB VARCHAR,CurrentadmissioncountDB VARCHAR,PreviousapplcountDB VARCHAR,PreviousadmissioncountDB VARCHAR);");
*/


        // Cursor cursor1 = db1.rawQuery("SELECT * FROM Collegelist WHERE CTalukID='" + str_Tids + "'", null);
        //Cursor cursor1 = db1.rawQuery("SELECT * FROM Collegelist WHERE CTalukID='"+str_Tids+"' AND CollegeTypeDB='"+str_collegetype+"'", null);
        Cursor cursor1 = db1.rawQuery("SELECT * FROM Collegelist WHERE CTalukID='"+str_Tids+"'", null);
        int x = cursor1.getCount();
        Log.e("College Ccount", Integer.toString(x));

        int i=0;
        arrayObj_Class_Colleges2 = new Class_Colleges[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Colleges innerObj_Class_CollegeList = new Class_Colleges();
                innerObj_Class_CollegeList.setcollege_id(cursor1.getString(cursor1.getColumnIndex("CollegeID")));
                innerObj_Class_CollegeList.setcollegename(cursor1.getString(cursor1.getColumnIndex("CollegeName")));
                innerObj_Class_CollegeList.setcollege_tid(cursor1.getString(cursor1.getColumnIndex("CTalukID")));
                Log.e("college",(cursor1.getString(cursor1.getColumnIndex("CollegeID"))));

                arrayObj_Class_Colleges2[i]=innerObj_Class_CollegeList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends



        db1.close();
        if(x>0) {
            // ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayObj_Class_Fellowship2);

            dataAdapter_college = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Colleges2);
            // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter_college.setDropDownViewResource(R.layout.spinnercustomstyle);
            collegesearch_ATV.setAdapter(dataAdapter_college);
        }

    }




    public void Update_Semlist_spinner()
    {

        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Semlist(SID VARCHAR,SName VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT * FROM Semlist", null);
        int x = cursor1.getCount();


        Log.e("Sem Ccount", Integer.toString(x));

        int i=0;
        arrayObj_Class_Sem2 = new Class_Sem[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Sem innerObj_Class_SemList = new Class_Sem();
                innerObj_Class_SemList.setsem_id(cursor1.getString(cursor1.getColumnIndex("SID")));
                innerObj_Class_SemList.setsem_name(cursor1.getString(cursor1.getColumnIndex("SName")));
                Log.e("Semlis",(cursor1.getString(cursor1.getColumnIndex("SID"))));

                arrayObj_Class_Sem2[i]=innerObj_Class_SemList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends



        db1.close();
        if(x>0) {
            // ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayObj_Class_Fellowship2);

            dataAdapter_semlist = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Sem2);
            // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter_semlist.setDropDownViewResource(R.layout.spinnercustomstyle);
            spin_semester.setAdapter(dataAdapter_semlist);
            spin_semester.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorBlack));
        }

    }




    public void Update_Courselist_spinner()
    {

        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Courselist(CID VARCHAR,CName VARCHAR,CPCode VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT * FROM Courselist", null);
        int x = cursor1.getCount();


        Log.e("Course count", Integer.toString(x));

        int i=0;
        arrayObj_Class_Course2 = new Class_Course[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Course innerObj_Class_CourseList = new Class_Course();
                innerObj_Class_CourseList.setcourse_id(cursor1.getString(cursor1.getColumnIndex("CID")));
                innerObj_Class_CourseList.setcourse_name(cursor1.getString(cursor1.getColumnIndex("CName")));
                Log.e("Courselist",(cursor1.getString(cursor1.getColumnIndex("CID"))));

                arrayObj_Class_Course2[i]=innerObj_Class_CourseList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends



        db1.close();
        if(x>0) {
            // ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayObj_Class_Fellowship2);

            dataAdapter_course = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Course2);
            // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter_course.setDropDownViewResource(R.layout.spinnercustomstyle);
            spin_course.setAdapter(dataAdapter_course);
        }

    }


    public void Update_Programlist_spinner()
    {

        SQLiteDatabase db1 = this.openOrCreateDatabase("LeadEditProfileDB", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS Programlist(PID VARCHAR,PName VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT * FROM Programlist", null);
        int x = cursor1.getCount();


        Log.e("Program count", Integer.toString(x));

        int i=0;
        arrayObj_Class_Program2 = new Class_Program[x];
        if (cursor1.moveToFirst()) {

            do {
                Class_Program innerObj_Class_ProgramList = new Class_Program();
                innerObj_Class_ProgramList.setprogram_id(cursor1.getString(cursor1.getColumnIndex("PID")));
                innerObj_Class_ProgramList.setprog_name(cursor1.getString(cursor1.getColumnIndex("PName")));
                Log.e("Courselist",(cursor1.getString(cursor1.getColumnIndex("PID"))));

                arrayObj_Class_Program2[i]=innerObj_Class_ProgramList;
                i++;
            } while (cursor1.moveToNext());
        }//if ends



        db1.close();
        if(x>0) {
            // ArrayAdapter dataAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, arrayObj_Class_Fellowship2);

            dataAdapter_program = new ArrayAdapter(getApplicationContext(), R.layout.spinnercustomstyle, arrayObj_Class_Program2);
            // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dataAdapter_program.setDropDownViewResource(R.layout.spinnercustomstyle);
            spin_program.setAdapter(dataAdapter_program);
        }

    }




//-----DB Create




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action_editProfile = menu.findItem(R.id.action_editProfile);
        action_editProfile.setVisible(false);


        //action_editProfile.

        /*MenuItem action_editProfile = menu.findItem(R.id.action_editProfile);
        action_editProfile*/

        //View view = (View) getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*  if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.action_logout)
        {

            editor_LoginTrack = shardprefLoginTrack_obj.edit();
            editor_LoginTrack.putString(PrefID_WhereToGo,config_obj.packagename+"LoginActivity");
            editor_LoginTrack.commit();

            deleteCache(context);


            Intent itthomeToLogin = new Intent(EditProfileActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }















    /*private void initProjectTypeSpinner(String projectTypeName) {
        final ArrayList projType = new ArrayList();

        Set<String> projTypeList = hashProjTypeId.keySet();


        for(String key: projTypeList){
            projType.add(key);
        }

*//* projType.add("Education");
projType.add("Health Care");
projType.add("Sports");*//*

        ArrayAdapter dataAdapter2 = new ArrayAdapter(context, R.layout.simple_spinner_items, projType);
*//**/


// Drop down layout style - list view with radio button
    //   dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// attaching data adapter to spinner
    //spin_projectType.setAdapter(dataAdapter2);
//spin_projectType.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

    //   spin_projectType.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));

      /*  if(projectTypeName!="" || projectTypeName!=null) {
            for (int i = 0; i < spin_projectType.getCount(); i++) {
                if (spin_projectType.getItemAtPosition(i).equals(projectTypeName)) {
                    spin_projectType.setSelection(i);
                    break;
                }
            }
        }

    }

    *//*

     */


    //Compress the image










}// end of Class
