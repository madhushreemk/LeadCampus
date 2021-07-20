package com.leadcampusapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

import static android.view.View.GONE;

public class PMComplitionProjectActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    Context mContext=this;
    String count;
    RelativeLayout rl_gallery_images;

    private TextView projectname_tv;
    private TextView projecttype_tv;
    private TextView projectobjective_tv;
    private EditText edt_placeofimplementation;
    private EditText edt_fundraised;
    EditText edt_resources,edt_CurrentSituation,edt_HoursSpend,resourcesUtilised_amount_pm_et;
    private TextView leadfund_tv;
    private TextView approvedamt_tv;
    private EditText edt_challenges;
    private EditText edt_learning;
    private EditText edt_story;
    private EditText edt_pmcomment;

    private AppCompatSpinner spin_studLevel;

    private ImageView btn_saveImg,img_student,btn_reject;

    private Context context;

    private RatingBar ratingBar;

    private ArrayList<String> imagePathList;
    private ArrayList<Bitmap> bitmapList;
    private String serverPath = Class_URL.ServerPath.toString().trim();
    private URL url;
    private TextView txt_numOfImages;
    private ImageView img_mainGallery;
    private int count1;
    private int count2;
    private TextView txt_studentid,txt_mobileno,spin_error_TV;
    private String wordDocPath="null";
    private String wordDocumentName;
    private String docName;
    private URL downloadUrl;
    File outputFile = null;
    String LeadId,PDId;
    String Student_Image_Path,MobileNo;
    String Name;
    Integer ManagerId;
    ArrayList<URL> urllist = new ArrayList<URL>();
    ArrayList<Bitmap> bitmapLst=new ArrayList<Bitmap>();

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    SharedPreferences shardprefPM_obj;
    String str_MangerID;
    Integer MDId;

    String PMImgUrl,str_studLevel;
    private ProgressDialog progressDialog;

    ArrayAdapter dataAdapter_studLevel;

    private RatingBar innovaion_ratingbar_rb,leadership_ratingbar_rb,risktaken_ratingbar_rb,impact_ratingbar_rb,fundraised_ratingbar_rb,final_ratingbar_rb;

    int ratingbar1_intvalue,ratingbar2_intvalue,ratingbar3_intvalue,ratingbar4_intvalue,ratingbar5_intvalue;
    String ratingg_str,ratingg_str2,ratingg_str3,ratingg_str4,ratingg_str5,ratingg_str_final;
    //innovaion_ratingbar_rb
    TextView finalrating_tv;


    static TextView pmcomp_clickstartproject_editdate_tv;
    static TextView pmcomp_clickendproject_editdate_tv;

    private TextView pmcomplition_numberofdays_tv;

    LinearLayout calendarlabel_linearlayout,calendar_linearlayout,pmcomplition_studentdays_linearlayout;

    TextView pmcomp_collegename_tv,pmcomp_streamname_tv;
    String str_collegename="",str_streamname="",str_impactproject="";

    Button impactproject_bt;

    TextView pmcomplition_sdgs_display_tv,pmcomplition_sdgs_dropdownvalue_tv;

    CheckBox goal_1_nopoverty_cb,goal_2_zerohunger_cb,goal_3_goodhealth_cb,goal_4_quality_cb,goal_5_gender_cb,goal_6_clean_cb,
            goal_7_affordable_cb,goal_8_decent_cb,goal_9_industry_cb,goal_10_reduce_cb,goal_11_sustain_cb,goal_12_responsible_cb,
            goal_13_climate_cb,goal_14_lifewater_cb,goal_15_lifeland_cb,goal_16_peace_cb,goal_17_partnerships_cb,goal_18_none_cb;
    Dialog dialog = null;
    String str_alertshowhide;
    JSONObject goals_jsonobject;



    String str_collaboration_resp,str_permissionactivities_resp,str_exp_initiative_resp,str_lacking_initiative_resp
            ,str_againtstide_resp,str_cross_hurdles_resp,str_venture_resp,str_govern_award_resp,str_leadership_role_resp,str_sdgs_goals_resp;

    private ArrayList<String> arraylist_goalsln = new ArrayList<String>();
    String str_selected_goals_response="";
    private ArrayList<String> arraylist_goalsln_forcheckbox = new ArrayList<String>();



    CheckBox impactproject_collabaration_cb,impactproject_againtstide_cb,impactproject_crosshurdles_cb,impactproject_venture_cb,
            impactproject_governaward_cb,impactproject_leadershiprole_cb;

    EditText impactproject_collabaration_et,impactproject_againtstide_et,impactproject_crosshurdles_et,
            impactproject_venture_et,impactproject_governaward_et,impactproject_leadershiprole_et,permissionactivities_et,
            exp_initiative_et,lacking_initiative_et;

    String str_validation_leadershiprole,str_validation_governaward,str_validation_venture,str_validation_crosshurdles,str_validation_againtstide,
            str_validation_collabaration,str_sdgs_goals_verified;
    LinearLayout impactproject_linearlayout;

    TextView make_impactproject_tv;

    Button pm_chat_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmcomplition_project);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Completion Project");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        Name = intent.getStringExtra("name");
        Log.e("TAG","name="+Name);
       // count =intent.getStringExtra("count");
       // Log.e("TAG","count="+count);
        LeadId=intent.getStringExtra("LeadId");
        PDId = intent.getStringExtra("PDId");
        MobileNo=intent.getStringExtra("MobileNo");
        Student_Image_Path=intent.getStringExtra("Student_Image_Path");
        str_collegename=intent.getStringExtra("str_pmcomp_collegename");
        str_streamname=intent.getStringExtra("str_pmcomp_streamname");
        str_impactproject=intent.getStringExtra("str_pmcomp_is_impactproject");




        Log.e("TAG","LeadId="+LeadId);
        Log.e("TAG","PDId="+PDId);
        TextView txt_name=(TextView) findViewById(R.id.txt_name);
        txt_name.setText(Name);

        edt_CurrentSituation=(EditText) findViewById(R.id.edt_CurrentSituation);
        edt_resources=(EditText) findViewById(R.id.edt_resources);
        edt_HoursSpend=(EditText) findViewById(R.id.edt_hoursspend);
        resourcesUtilised_amount_pm_et=(EditText) findViewById(R.id.resourcesUtilised_amount_pm_et);

        spin_studLevel = (AppCompatSpinner) findViewById(R.id.spin_studLevel);
        spin_error_TV = (TextView) findViewById(R.id.spin_error_TV);

        spin_error_TV.setVisibility(View.GONE);
        txt_mobileno=(TextView) findViewById(R.id.txt_mobileno);
        txt_mobileno.setText(MobileNo);
        txt_mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_mobileno.getText().toString()));
                startActivity(intent);
            }
        });

        pm_chat_txt=(Button) findViewById(R.id.pm_chat_txt);

        innovaion_ratingbar_rb=(RatingBar)findViewById(R.id.innovaion_ratingbar_rb);
        leadership_ratingbar_rb= (RatingBar)findViewById(R.id.leadership_ratingbar_rb);
        risktaken_ratingbar_rb= (RatingBar)findViewById(R.id.risktaken_ratingbar_rb);
        impact_ratingbar_rb= (RatingBar)findViewById(R.id.impact_ratingbar_rb);
        fundraised_ratingbar_rb=(RatingBar)findViewById(R.id.fundraised_ratingbar_rb);

        final_ratingbar_rb=  (RatingBar)findViewById(R.id.final_ratingbar_rb);
        finalrating_tv=(TextView)findViewById(R.id.finalrating_tv);


        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        img_student=(ImageView) findViewById(R.id.img_student);


        pmcomp_clickstartproject_editdate_tv = (TextView) findViewById(R.id.pmcomp_clickstartproject_editdate_TV);
        pmcomp_clickendproject_editdate_tv = (TextView) findViewById(R.id.pmcomp_clickendproject_editdate_TV);

        pmcomplition_numberofdays_tv=(TextView) findViewById(R.id.pmcomplition_numberofdays_tv);

        impactproject_bt=(Button)findViewById(R.id.impactproject_bt);

        pmcomp_collegename_tv=(TextView) findViewById(R.id.pmcomp_collegename_tv);
                pmcomp_streamname_tv=(TextView) findViewById(R.id.pmcomp_streamname_tv);
        calendarlabel_linearlayout=(LinearLayout)findViewById(R.id.calendarlabel_linearlayout);
        calendar_linearlayout=(LinearLayout)findViewById(R.id.calendar_linearlayout);
        pmcomplition_studentdays_linearlayout=(LinearLayout)findViewById(R.id.pmcomplition_studentdays_linearlayout);


        pmcomplition_sdgs_display_tv=(TextView) findViewById(R.id.pmcomplition_sdgs_display_tv);
        pmcomplition_sdgs_dropdownvalue_tv=(TextView) findViewById(R.id.pmcomplition_sdgs_dropdownvalue_tv);



        impactproject_linearlayout =(LinearLayout)findViewById(R.id.impactproject_linearlayout);
        impactproject_collabaration_cb=(CheckBox)findViewById(R.id.impactproject_collabaration_cb);
        impactproject_againtstide_cb=(CheckBox)findViewById(R.id.impactproject_againtstide_cb);
        impactproject_crosshurdles_cb=(CheckBox)findViewById(R.id.impactproject_crosshurdles_cb);
        impactproject_venture_cb=(CheckBox)findViewById(R.id.impactproject_venture_cb);
        impactproject_governaward_cb=(CheckBox)findViewById(R.id.impactproject_governaward_cb);
        impactproject_leadershiprole_cb=(CheckBox)findViewById(R.id.impactproject_leadershiprole_cb);

        impactproject_collabaration_et=(EditText) findViewById(R.id.impactproject_collabaration_et);
        impactproject_againtstide_et=(EditText) findViewById(R.id.impactproject_againtstide_et);
        impactproject_crosshurdles_et=(EditText) findViewById(R.id.impactproject_crosshurdles_et);
        impactproject_venture_et=(EditText) findViewById(R.id.impactproject_venture_et);
        impactproject_governaward_et=(EditText) findViewById(R.id.impactproject_governaward_et);
        impactproject_leadershiprole_et=(EditText) findViewById(R.id.impactproject_leadershiprole_et);
        permissionactivities_et=(EditText) findViewById(R.id.permissionactivities_et);
        exp_initiative_et=(EditText) findViewById(R.id.exp_initiative_et);
        lacking_initiative_et=(EditText) findViewById(R.id.lacking_initiative_et);
        make_impactproject_tv=(TextView)findViewById(R.id.make_impactproject_tv);

        str_validation_leadershiprole=str_validation_governaward=str_validation_venture=str_validation_crosshurdles
                =str_validation_againtstide=str_validation_collabaration="yes";
        str_sdgs_goals_verified="no";










        shardprefPM_obj=this.getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);

       /* ManagerId = getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);*/

        rl_gallery_images=(RelativeLayout)findViewById(R.id.rl_gallery_images);

        if(str_impactproject.equals("0"))
        {
            make_impactproject_tv.setText("Make this an Impact project : ");
            impactproject_linearlayout.setVisibility(View.GONE);
        }
        else{
            make_impactproject_tv.setText("Make this an Non-Impact project : ");
            impactproject_linearlayout.setVisibility(View.VISIBLE);
        }

        setStudentLevelSpinner();

        String Imagestring = Student_Image_Path;
        if (Imagestring==null||Imagestring.equals("null") || Imagestring.equals("anyType{}")) {
            // PMImgUrl="null";
            img_student.setImageResource(R.drawable.devanand);
        } else {
            Log.e("studentImageStringis:",Imagestring);
            String arr[] = Imagestring.split("/", 2);

            String firstWord = arr[0];   //the
            String secondWord = arr[1];

            PMImgUrl = serverPath + secondWord;

            Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
            LoadGalleryImage_stud loadGalleryImage = new LoadGalleryImage_stud(PMComplitionProjectActivity.this);
            loadGalleryImage.execute();
        }



        pmcomp_collegename_tv.setText(str_collegename);
        pmcomp_streamname_tv.setText(str_streamname);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // TODO Auto-generated method stub
                ratingBar.setRating(rating);
                //   ratingBar_Indicator.setRating(rating);
                /*Toast.makeText(PMComplitionProjectActivity.this, "rating:"+String.valueOf(ratingBar.getRating()),
                        Toast.LENGTH_LONG).show();;*/
            }});

        ImageView doc_iv = (ImageView) findViewById(R.id.btn_doc);

        initializeViews();

        getProjectCompletionDtls();

        doc_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*File file= new File(String.valueOf(Environment.getRootDirectory()));
                Log.d("TAG","file="+file);
                //File file1=new File(Environment.getExternalStorageDirectory(),"Documents.docx");
                File file1=new File(Environment.getExternalStorageDirectory(),"MH00_Upload_Test file.docx");
                Log.d("TAG","file1="+file1);
                File myFile = new File(Environment.DIRECTORY_DOCUMENTS,"mmr1.docx");
                Log.d("TAG","myfile="+myFile);
                try {
                    //FileOpen.openFile(mContext, myFile);
                    FileOpen.openFile(mContext, file1);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                Log.d("wordDocPath",wordDocPath);
          //      Log.d("downloadUrl", String.valueOf(downloadUrl));

                if(wordDocPath.equals("null")){
                    Toast.makeText(PMComplitionProjectActivity.this, "File Not Exist",
                            Toast.LENGTH_SHORT).show();
                }else{
                    LoadDocument loadDocument = new LoadDocument(context);
                    loadDocument.execute();
                }



                //      Intent i=new Intent(PMComplitionProjectActivity.this,DocumentViewActivity.class);
                //    startActivity(i);
            /*    File file = new File(Environment.getExternalStorageDirectory(),
                        "Document.docx");

                try {
                    if (file.exists()) {
                        Uri path = Uri.fromFile(file);
                        Intent objIntent = new Intent(Intent.ACTION_VIEW);
                        // replace "application/msword" with
                        // "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                        // for docx files
                        // objIntent.setDataAndType(path,"application/msword");
                        objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(objIntent);
                    } else {
                        Toast.makeText(PMComplitionProjectActivity.this, "File NotFound",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(PMComplitionProjectActivity.this,
                            "No Viewer Application Found", Toast.LENGTH_SHORT)
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
        });

        //progressDialog = new ProgressDialog(context);

        spin_studLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view,
                                        int position, long id){
                // TODO Auto-generated method stub
                str_studLevel = spin_studLevel.getSelectedItem().toString();
                // Toast.makeText(getApplicationContext(),"Blood:"+str_bloodgroup,Toast.LENGTH_SHORT).show();
                if(!( (str_studLevel.equals("x"))|| (str_studLevel.equals("Select")) ))
                { spin_error_TV.setText("");
                    spin_error_TV.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });

        rl_gallery_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("lengthoflistsss", String.valueOf(bitmapList.size()));
                Log.d("lengthoflistsss", String.valueOf(bitmapLst.size()));

             //   Toast.makeText(getApplicationContext(),"On Click of relative layout submit",Toast.LENGTH_LONG).show();

               /* progressDialog.setMessage("Submitting");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();*/

                try {
                    if (bitmapLst.size() > 0) {

                        /*File projImageFolder = null;

                        if (new CheckForSDCard().isSDCardPresent()) {
                            projImageFolder = new File(
                                    Environment.getExternalStorageDirectory() + "/"
                                            + "ProjectImagesFiles");
                        } else {
                            Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
                        }

                        if (!projImageFolder.exists()) {
                            projImageFolder.mkdir();
                            Log.e("Main", "Directory Created.");
                        }

                        String mainFolder = projImageFolder.getAbsolutePath().toString();
                        File subFolder = new File(mainFolder,"Images_"+LeadId.toString());

                        if (!subFolder.exists()) {
                            subFolder.mkdir();
                            Log.e("Main", "SubDirectory Created.");
                        }

                        for(int k=0;k<bitmapLst.size();k++) {
                            File file = new File(subFolder, "Image_"+String.valueOf(k+1)+ ".png");
                            Bitmap bitmap = bitmapLst.get(k);

                            Log.d("Bitmapisss",bitmap.toString());

                            if (!file.exists()) {
                                try {
                                    file.createNewFile();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Log.e("Main", "File Created");
                            }

                            Log.d("FileNameiss",file.getAbsolutePath().toString());
                            try {
                                FileOutputStream out = new FileOutputStream(file);
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                out.flush();
                                out.close();
                                //stored = "success";
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }*/


                        try {
                            GalleryImageBitmap.setBitmapGalleryImage(bitmapLst);
                            Intent i = new Intent(PMComplitionProjectActivity.this, PMGallaryActivity.class);
                            i.putExtra("ManagerId", MDId);
                            i.putExtra("name", Name);
                            i.putExtra("PDId", PDId);
                            i.putExtra("LeadId", LeadId);
                            i.putExtra("MobileNo", MobileNo);
                            i.putExtra("Student_Image_Path", Student_Image_Path);
                            startActivity(i);
                            //progressDialog.dismiss();
                        }catch(OutOfMemoryError ex){
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    //progressDialog.dismiss();
                                    Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } else {

                    }
                }catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //progressDialog.dismiss();
                            Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        // if(count.equals("4")){
        //      init1();
        //  }


        pm_chat_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag","ProjectId chat=="+PDId);
                Intent i=new Intent(PMComplitionProjectActivity.this,ChatActivity.class);
                //i.putExtra("projectStatus","completed");
                i.putExtra("projectStatus","requested");
                i.putExtra("projectId",PDId);
                i.putExtra("userType","Manager");
                startActivity(i);
            }
        });


        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( edt_pmcomment.getText().toString().length() == 0 ){
                    edt_pmcomment.setError( "Manager comments required!" );}
                else{
                    SubmitRejectDetails submitRejectDetails = new SubmitRejectDetails(context);
                    submitRejectDetails.execute();
                }
            }
        });
        btn_saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   if(ratingBar.getRating()==0.0){
                    Toast.makeText(context,"Giving rating is mandatory",Toast.LENGTH_LONG).show();
                }
                else if(edt_pmcomment.getText().toString().isEmpty() || edt_pmcomment.getText().toString()=="" || edt_pmcomment.getText().toString()==null){
                    edt_pmcomment.setError("Enter comments mandatory");
                }*/


             if(str_sdgs_goals_verified.equalsIgnoreCase("no")) {
                 validation_sdg_confirm();
             }
             else {

                 if (validation_Save()) {
                     SubmitCompletionProject submitCompletion = new SubmitCompletionProject(context);
                     submitCompletion.execute();
                 }

             }

            }
        });





        innovaion_ratingbar_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingg_str=String.valueOf(innovaion_ratingbar_rb.getRating());

                ratingbar1_intvalue=Math.round(innovaion_ratingbar_rb.getRating());


                ratingBar.setRating(ratingbar1_intvalue);

                calculateFinalRating();
            }
        });

        leadership_ratingbar_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingg_str2=String.valueOf(leadership_ratingbar_rb.getRating());
                ratingbar2_intvalue=Math.round(leadership_ratingbar_rb.getRating());
                leadership_ratingbar_rb.setRating(ratingbar2_intvalue);

                // Toast.makeText(getApplicationContext(), ratingg_str2, Toast.LENGTH_SHORT).show();
                calculateFinalRating();

            }
        });

        risktaken_ratingbar_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingg_str3=String.valueOf(risktaken_ratingbar_rb.getRating());
                ratingbar3_intvalue=Math.round(risktaken_ratingbar_rb.getRating());
                System.out.println(" ratingbar3_intvalue value..."+ratingbar3_intvalue);
                risktaken_ratingbar_rb.setRating(ratingbar3_intvalue);
                //Toast.makeText(getApplicationContext(), ratingg_str3, Toast.LENGTH_SHORT).show();
                calculateFinalRating();

            }
        });
        impact_ratingbar_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingg_str4=String.valueOf(impact_ratingbar_rb.getRating());
                ratingbar4_intvalue=Math.round(impact_ratingbar_rb.getRating());
                System.out.println(" ratingbar4_intvalue value..."+ratingbar4_intvalue);
                impact_ratingbar_rb.setRating(ratingbar4_intvalue);

                // Toast.makeText(getApplicationContext(), ratingg_str4, Toast.LENGTH_SHORT).show();
                calculateFinalRating();

            }
        });
        fundraised_ratingbar_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingg_str5=String.valueOf(fundraised_ratingbar_rb.getRating());
                ratingbar5_intvalue=Math.round(fundraised_ratingbar_rb.getRating());
                System.out.println(" ratingbar5_intvalue value..."+ratingbar5_intvalue);

                fundraised_ratingbar_rb.setRating(ratingbar5_intvalue);

                // Toast.makeText(getApplicationContext(), ratingg_str5, Toast.LENGTH_SHORT).show();
                calculateFinalRating();

            }
        });




        impactproject_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                impactproject_alert_dialog();

            }
        });





        //sdgs goal

        pmcomplition_sdgs_dropdownvalue_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.sdgs_alert_layout, null);



                Button addgoals_bt = (Button) alertLayout.findViewById(R.id.addgoals_bt);

                Button cancel_goals_bt= (Button) alertLayout.findViewById(R.id.cancel_goals_bt);
                //goal_1_nopoverty_cb,goal_2_zerohunger_cb,goal_3_goodhealth_cb,goal_4_quality_cb
                //goal_5_gender_cb,goal_6_clean_cb,goal_7_affordable_cb,goal_8_decent_cb,goal_9_industry_cb
                //goal_10_reduce_cb,goal_11_sustain_cb,goal_12_responsible_cb,goal_13_climate_cb
                //goal_14_lifewater_cb,goal_15_lifeland_cb,goal_16_peace_cb,goal_17_partnerships_cb
                //goal_18_none_cb
                goal_1_nopoverty_cb = (CheckBox) alertLayout.findViewById(R.id.goal_1_nopoverty);

                goal_2_zerohunger_cb = (CheckBox) alertLayout.findViewById(R.id.goal_2_zerohunger);
                goal_3_goodhealth_cb=(CheckBox) alertLayout.findViewById(R.id.goal_3_goodhealth);
                goal_4_quality_cb=(CheckBox) alertLayout.findViewById(R.id.goal_4_quality);
                goal_5_gender_cb=(CheckBox) alertLayout.findViewById(R.id.goal_5_gender);

                goal_6_clean_cb=(CheckBox) alertLayout.findViewById(R.id.goal_6_clean);

                goal_7_affordable_cb=(CheckBox) alertLayout.findViewById(R.id.goal_7_affordable);
                goal_8_decent_cb=(CheckBox) alertLayout.findViewById(R.id.goal_8_decent);
                goal_9_industry_cb=(CheckBox) alertLayout.findViewById(R.id.goal_9_industry);
                goal_10_reduce_cb=(CheckBox) alertLayout.findViewById(R.id.goal_10_reduce);
                goal_11_sustain_cb=(CheckBox) alertLayout.findViewById(R.id.goal_11_sustain);
                goal_12_responsible_cb=(CheckBox) alertLayout.findViewById(R.id.goal_12_responsible);
                goal_13_climate_cb =(CheckBox) alertLayout.findViewById(R.id.goal_13_climate);
                goal_14_lifewater_cb=(CheckBox) alertLayout.findViewById(R.id.goal_14_lifewater);
                goal_15_lifeland_cb=(CheckBox) alertLayout.findViewById(R.id.goal_15_lifeland);
                goal_16_peace_cb=(CheckBox) alertLayout.findViewById(R.id.goal_16_peace);
                goal_17_partnerships_cb=(CheckBox) alertLayout.findViewById(R.id.goal_17_partnerships);
                goal_18_none_cb=(CheckBox) alertLayout.findViewById(R.id.goal_18_none);





                AlertDialog.Builder alert = new AlertDialog.Builder(PMComplitionProjectActivity.this);
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);

                dialog = alert.create();


                // for none count is zero
                if(pmcomplition_sdgs_display_tv.getText().toString().trim().length()>0)
                {
                    check_box_enable2();
                }else
                {
                }





                goal_18_none_cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        if(goal_18_none_cb.isChecked())
                        {


                            goal_1_nopoverty_cb.setChecked(false);
                            goal_2_zerohunger_cb.setChecked(false);
                            goal_3_goodhealth_cb.setChecked(false);
                            goal_4_quality_cb.setChecked(false);
                            goal_5_gender_cb.setChecked(false);
                            goal_6_clean_cb.setChecked(false);
                            goal_7_affordable_cb.setChecked(false);
                            goal_8_decent_cb.setChecked(false);
                            goal_9_industry_cb.setChecked(false);
                            goal_10_reduce_cb.setChecked(false);
                            goal_11_sustain_cb.setChecked(false);
                            goal_12_responsible_cb.setChecked(false);
                            goal_13_climate_cb.setChecked(false);
                            goal_14_lifewater_cb.setChecked(false);
                            goal_15_lifeland_cb.setChecked(false);
                            goal_16_peace_cb.setChecked(false);
                            goal_17_partnerships_cb.setChecked(false);

                            goal_1_nopoverty_cb.setEnabled(false);
                            goal_2_zerohunger_cb.setEnabled(false);
                            goal_3_goodhealth_cb.setEnabled(false);
                            goal_4_quality_cb.setEnabled(false);
                            goal_5_gender_cb.setEnabled(false);
                            goal_6_clean_cb.setEnabled(false);
                            goal_7_affordable_cb.setEnabled(false);
                            goal_8_decent_cb.setEnabled(false);
                            goal_9_industry_cb.setEnabled(false);
                            goal_10_reduce_cb.setEnabled(false);
                            goal_11_sustain_cb.setEnabled(false);
                            goal_12_responsible_cb.setEnabled(false);
                            goal_13_climate_cb.setEnabled(false);
                            goal_14_lifewater_cb.setEnabled(false);
                            goal_15_lifeland_cb.setEnabled(false);
                            goal_16_peace_cb.setEnabled(false);
                            goal_17_partnerships_cb.setEnabled(false);
                            //goalslist_linearlayout.setVisibility(View.INVISIBLE);

                        }else
                        {

                            goal_1_nopoverty_cb.setEnabled(true);
                            goal_2_zerohunger_cb.setEnabled(true);
                            goal_3_goodhealth_cb.setEnabled(true);
                            goal_4_quality_cb.setEnabled(true);
                            goal_5_gender_cb.setEnabled(true);
                            goal_6_clean_cb.setEnabled(true);
                            goal_7_affordable_cb.setEnabled(true);
                            goal_8_decent_cb.setEnabled(true);
                            goal_9_industry_cb.setEnabled(true);
                            goal_10_reduce_cb.setEnabled(true);
                            goal_11_sustain_cb.setEnabled(true);
                            goal_12_responsible_cb.setEnabled(true);
                            goal_13_climate_cb.setEnabled(true);
                            goal_14_lifewater_cb.setEnabled(true);
                            goal_15_lifeland_cb.setEnabled(true);
                            goal_16_peace_cb.setEnabled(true);
                            goal_17_partnerships_cb.setEnabled(true);

                            //goalslist_linearlayout.setVisibility(View.VISIBLE);

                        }

                    }
                });




                addgoals_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        // Toast.makeText(context, "Request Has Been Sent", Toast.LENGTH_LONG).show();
                        //if(validation_forgoals())


                        // goal_1_nopoverty_cb,goal_2_zerohunger_cb,goal_3_goodhealth_cb,goal_4_quality_cb
                        //goal_5_gender_cb,goal_6_clean_cb,goal_7_affordable_cb,goal_8_decent_cb,goal_9_industry_cb
                        //goal_10_reduce_cb,goal_11_sustain_cb,goal_12_responsible_cb,goal_13_climate_cb
                        //goal_14_lifewater_cb,goal_15_lifeland_cb,goal_16_peace_cb,goal_17_partnerships_cb
                        //goal_18_none_cb


                        if(goal_1_nopoverty_cb.isChecked()||goal_2_zerohunger_cb.isChecked()||goal_3_goodhealth_cb.isChecked()
                                ||goal_4_quality_cb.isChecked()||goal_5_gender_cb.isChecked()||goal_6_clean_cb.isChecked()
                                ||goal_7_affordable_cb.isChecked()||goal_8_decent_cb.isChecked()||goal_9_industry_cb.isChecked()
                                ||goal_10_reduce_cb.isChecked()||goal_11_sustain_cb.isChecked()||goal_12_responsible_cb.isChecked()
                                ||goal_13_climate_cb.isChecked()||goal_14_lifewater_cb.isChecked()||goal_15_lifeland_cb.isChecked()||
                                goal_16_peace_cb.isChecked()||goal_17_partnerships_cb.isChecked()||goal_18_none_cb.isChecked())
                        {
                            str_alertshowhide="yes";
                            check_box_selected();
                        }
                        else
                        {

                            str_alertshowhide="no";
                            Toast.makeText(getApplicationContext(),"Select atleast one option",Toast.LENGTH_SHORT).show();
                        }



                            /*EditProfileActivity.SubmitQuery submitQuery = new EditProfileActivity.SubmitQuery(context);
                            submitQuery.execute();*/

                        if(str_alertshowhide.equals("no"))
                        {

                        }
                        else
                        {


                            dialog.dismiss();
                        }


                    }
                });

                cancel_goals_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        goals_jsonobject = new JSONObject();
                        try {
                            goals_jsonobject.put("", "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pmcomplition_sdgs_display_tv.setText("");

                        dialog.dismiss();
                    }
                });


                dialog.show();


            }
        });

        //sdgs goal





        impactproject_collabaration_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(impactproject_collabaration_cb.isChecked())
                {
                    impactproject_collabaration_et.setVisibility(View.VISIBLE);
                    str_validation_collabaration="yes";
                }else{
                    impactproject_collabaration_et.setVisibility(View.GONE);
                    impactproject_collabaration_et.setText("");
                    str_validation_collabaration="no";
                }

            }
        });




        impactproject_againtstide_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(impactproject_againtstide_cb.isChecked())
                {
                    impactproject_againtstide_et.setVisibility(View.VISIBLE);
                    str_validation_againtstide="yes";
                }else{
                    impactproject_againtstide_et.setVisibility(View.GONE);
                    impactproject_againtstide_et.setText("");
                    str_validation_againtstide="no";
                }

            }
        });





        impactproject_crosshurdles_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(impactproject_crosshurdles_cb.isChecked())
                {
                    impactproject_crosshurdles_et.setVisibility(View.VISIBLE);
                    str_validation_crosshurdles="yes";
                }else{
                    impactproject_crosshurdles_et.setVisibility(View.GONE);
                    impactproject_crosshurdles_et.setText("");
                    str_validation_crosshurdles="no";
                }

            }
        });



        impactproject_venture_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(impactproject_venture_cb.isChecked())
                {
                    impactproject_venture_et.setVisibility(View.VISIBLE);
                    str_validation_venture="yes";
                }else{
                    impactproject_venture_et.setVisibility(View.GONE);
                    impactproject_venture_et.setText("");
                    str_validation_venture="no";
                }

            }
        });






        impactproject_governaward_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(impactproject_governaward_cb.isChecked())
                {

                    impactproject_governaward_et.setVisibility(View.VISIBLE);
                    str_validation_governaward="yes";
                }else{
                    impactproject_governaward_et.setVisibility(View.GONE);
                    impactproject_governaward_et.setText("");
                    str_validation_governaward="no";
                }

            }
        });




        impactproject_leadershiprole_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(impactproject_leadershiprole_cb.isChecked())
                {
                    impactproject_leadershiprole_et.setVisibility(View.VISIBLE);
                    str_validation_leadershiprole="yes";
                }else{
                    impactproject_leadershiprole_et.setVisibility(View.GONE);
                    impactproject_leadershiprole_et.setText("");
                    str_validation_leadershiprole="no";
                }

            }
        });




    }// end of oncreate()



    public void calculateFinalRating1()
    {
        float calculate = (float) (ratingbar1_intvalue
                + ratingbar2_intvalue + ratingbar3_intvalue + ratingbar4_intvalue + ratingbar5_intvalue) / 5;
        System.out.println(" calculate value..." + calculate);
        //ratingbar_final.setNumStars(calculate);
        // float val = (float) calculate;
        // System.out.println(" val value..." + val);
        final_ratingbar_rb.setRating(calculate);
        Toast.makeText(getApplicationContext(),""+calculate,Toast.LENGTH_SHORT).show();

       // finalvalue.setText(String.valueOf(calculate));
    }
    @SuppressLint("RestrictedApi")
    private void setStudentLevelSpinner() {
        final ArrayList listStudLevel = new ArrayList();
        listStudLevel.add("Select");
        listStudLevel.add("Initiator");
        listStudLevel.add("Change Maker");
        listStudLevel.add("LEADer");


        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        dataAdapter_studLevel = new ArrayAdapter(this, R.layout.simple_spinner_items, listStudLevel);

        // Drop down layout style - list view with radio button
        dataAdapter_studLevel.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        spin_studLevel.setAdapter(dataAdapter_studLevel);
        spin_studLevel.setSupportBackgroundTintList(ContextCompat.getColorStateList(this, R.color.colorBlack));

    }
    public void calculateFinalRating() {
        float calculate = (float) (ratingbar1_intvalue + ratingbar2_intvalue + ratingbar3_intvalue + ratingbar4_intvalue + ratingbar5_intvalue) / 5;
        System.out.println(" calculate value..." + calculate);

      //  Toast.makeText(getApplicationContext(),""+calculate,Toast.LENGTH_SHORT).show();


        //finalvalue.setText(calculate);
        float[] array1= new float[]{1.1f, 1.2f,1.3f,1.4f,1.6f,1.7f,1.8f,1.9f};
        float[] array2= new float[]{2.1f, 2.2f,2.3f,2.4f,2.6f,2.7f,2.8f,2.9f};
        float[] array3= new float[]{3.1f, 3.2f,3.3f,3.4f,3.6f,3.7f,3.8f,3.9f};
        float[] array4= new float[]{4.1f, 4.2f,4.3f,4.4f,4.6f,4.7f,4.8f,4.9f};



        /*if((calculate==array1[0])||(calculate==array1[1])){
            final_ratingbar_rb.setRating(1.0f);
            finalrating_tv.setText("1.0");

        }else if((calculate==array1[2])||(calculate==array1[3]) || (calculate==array1[4])||(calculate==array1[5])){
            final_ratingbar_rb.setRating(1.5f);
            finalrating_tv.setText("1.5");

        }else if((calculate==array1[6])||(calculate==array1[7]) || (calculate==array2[0])||(calculate==array2[1])){
            final_ratingbar_rb.setRating(2.0f);
            finalrating_tv.setText("2.0");

        }else if((calculate==array2[2])||(calculate==array2[3]) || (calculate==array2[4])||(calculate==array2[5])){
            final_ratingbar_rb.setRating(2.5f);
            finalrating_tv.setText("2.5");

        }else if((calculate==array2[6])||(calculate==array2[7]) || (calculate==array3[0])||(calculate==array3[1]) ){
            final_ratingbar_rb.setRating(3.0f);
            finalrating_tv.setText("3.0");

        }else if((calculate==array3[2])||(calculate==array3[3]) || (calculate==array3[4])||(calculate==array3[5])){
            final_ratingbar_rb.setRating(3.5f);
            finalrating_tv.setText("3.5");

        }else if((calculate==array3[6])||(calculate==array3[7]) || (calculate==array4[0])||(calculate==array4[1])){
            final_ratingbar_rb.setRating(4.0f);
            finalrating_tv.setText("4.0");

        }else if((calculate==array4[2])||(calculate==array4[3]) || (calculate==array4[4])||(calculate==array4[5])){
            final_ratingbar_rb.setRating(4.5f);
            finalrating_tv.setText("4.5");

        }else if((calculate==array4[6])||(calculate==array4[7])){
            final_ratingbar_rb.setRating(5.0f);
            finalrating_tv.setText("5.0");

        }*/



        if((calculate==array1[0])||(calculate==array1[1])||(calculate==1.0f)){
            final_ratingbar_rb.setRating(1.0f);
            finalrating_tv.setText("1.0");

        }else if((calculate==array1[2])||(calculate==array1[3]) || (calculate==array1[4])||(calculate==array1[5])){
            final_ratingbar_rb.setRating(1.5f);
            finalrating_tv.setText("1.5");

        }else if((calculate==array1[6])||(calculate==array1[7]) || (calculate==array2[0])||(calculate==array2[1])||(calculate==2.0f)){
            final_ratingbar_rb.setRating(2.0f);
            finalrating_tv.setText("2.0");

        }else if((calculate==array2[2])||(calculate==array2[3]) || (calculate==array2[4])||(calculate==array2[5])){
            final_ratingbar_rb.setRating(2.5f);
            finalrating_tv.setText("2.5");

        }else if((calculate==array2[6])||(calculate==array2[7]) || (calculate==array3[0])||(calculate==array3[1]) ||(calculate==3.0f)){
            final_ratingbar_rb.setRating(3.0f);
            finalrating_tv.setText("3.0");

        }else if((calculate==array3[2])||(calculate==array3[3]) || (calculate==array3[4])||(calculate==array3[5])){
            final_ratingbar_rb.setRating(3.5f);
            finalrating_tv.setText("3.5");

        }else if((calculate==array3[6])||(calculate==array3[7]) || (calculate==array4[0])||(calculate==array4[1])||(calculate==4.0f)){
            final_ratingbar_rb.setRating(4.0f);
            finalrating_tv.setText("4.0");

        }else if((calculate==array4[2])||(calculate==array4[3]) || (calculate==array4[4])||(calculate==array4[5])){
            final_ratingbar_rb.setRating(4.5f);
            finalrating_tv.setText("4.5");

        }else if((calculate==array4[6])||(calculate==array4[7])||(calculate==5.0f)){
            final_ratingbar_rb.setRating(5.0f);
            finalrating_tv.setText("5.0");

        }




    }


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

            AlertDialog.Builder adb = new AlertDialog.Builder(PMComplitionProjectActivity.this);
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

















    public boolean validation_Save()
    {

        Boolean bpmcomments=true,bamount=true,blevel=true,bsdgs_goals=true;
        Boolean bpmresource_amount1=true,bpmresource_amount2=true,bpmresource_amount3=true;
        Boolean bpmplaceofimplementation=true,bpm_challengefaced=true,bpm_learnings=true,bpm_story=true,
                bpm_currentsituation=true,bpm_sdgsgoals=true,bpm_impactproject_collabaration=true,bpm_impactproject_againtstide=true,bpm_impactproject_crosshurdles=true,bpm_impactproject_venture=true,
                bpm_impactproject_governaward=true,bpm_impactproject_leadershiprole=true,bpm_resources=true;



        String str_resource_amount=resourcesUtilised_amount_pm_et.getText().toString().trim();



        if(edt_placeofimplementation.getText().toString().trim().length()==0||
                edt_placeofimplementation.getText().toString().trim().length()<2)
        {
            if(edt_placeofimplementation.getText().toString().trim().length()<2)
            {
                edt_placeofimplementation.setError("Minimum 3 character");
                edt_placeofimplementation.requestFocus();
                bpmplaceofimplementation= false;
            }
            else {
                edt_placeofimplementation.setError("Enter the Place of Implementation");
                edt_placeofimplementation.requestFocus();
                bpmplaceofimplementation = false;
            }
        }


        if(edt_challenges.getText().toString().trim().length()<10){

            edt_challenges.setError("Minimum 10 characters");
            edt_challenges.requestFocus();
            bpm_challengefaced=false;
        }

        //edt_learning edt_story  edt_CurrentSituation
        //  Boolean bpmplaceofimplementation=true,bpm_challengefaced=true,bpm_learnings=true,bpm_story=true;

        if(edt_learning.getText().toString().trim().length()<20){
            edt_learning.setError("Minimum 20 characters");
            edt_learning.requestFocus();
            bpm_learnings=false;
        }



        if(edt_story.getText().toString().trim().length()<10){
            edt_story.setError("Minimum 10 characters");
            edt_story.requestFocus();
            bpm_story=false;
        }

        if(edt_CurrentSituation.getText().toString().trim().length()<3){
            edt_CurrentSituation.setError("Minimum 3 characters");
            edt_CurrentSituation.requestFocus();
            bpm_currentsituation=false;
        }




        if(edt_resources.getText().toString().trim().length()<5)
        {
            edt_resources.setError("Minimum 5 character");
            edt_resources.requestFocus();
            bpm_resources=false;
        }


        if(str_resource_amount.isEmpty()){

            resourcesUtilised_amount_pm_et.setError("Enter the Amount");
            resourcesUtilised_amount_pm_et.requestFocus();
            bpmresource_amount1= false;
        }

        if(resourcesUtilised_amount_pm_et.getText().toString().trim().length()==0){

            resourcesUtilised_amount_pm_et.setError("Enter the Amount");
            resourcesUtilised_amount_pm_et.requestFocus();
            bpmresource_amount2= false;
        }

       /* if(str_resource_amount.equalsIgnoreCase("0"))
        {
            resourcesUtilised_amount_pm_et.setError("Enter the Amount");
            resourcesUtilised_amount_pm_et.requestFocus();
            bpmresource_amount3= false;
        }
*/

        if(pmcomplition_sdgs_display_tv.getText().toString().trim().length()==0)
        {
            /*pmcomplition_sdgs_display_tv.setError("Select the goals");
            pmcomplition_sdgs_display_tv.requestFocus();*/
            Toast.makeText(getApplicationContext(),"Select the goals",Toast.LENGTH_SHORT).show();
            bpm_sdgsgoals=false;

        }

        if(str_impactproject.equals("1"))
        {
                if(impactproject_collabaration_cb.isChecked())
                {
                    if(impactproject_collabaration_et.getText().toString().trim().length()==0)
                    {
                        impactproject_collabaration_et.setError("Enter collabaration");
                        impactproject_collabaration_et.requestFocus();
                        bpm_impactproject_collabaration=false;
                    }
                    if(impactproject_collabaration_et.getText().toString().trim().length()<19)
                    {
                        impactproject_collabaration_et.setError("Minimum 20 character");
                        impactproject_collabaration_et.requestFocus();
                        bpm_impactproject_collabaration=false;
                    }
                }


            if(impactproject_againtstide_cb.isChecked())
            {
                if(impactproject_againtstide_et.getText().toString().trim().length()==0)
                {
                    impactproject_againtstide_et.setError("Empty not allowed");
                    impactproject_againtstide_et.requestFocus();
                    bpm_impactproject_againtstide=false;
                }
                if(impactproject_againtstide_et.getText().toString().trim().length()<19)
                {
                    impactproject_againtstide_et.setError("Minimum 20 character");
                    impactproject_againtstide_et.requestFocus();
                    bpm_impactproject_againtstide=false;
                }
            }


            if(impactproject_crosshurdles_cb.isChecked())
            {
                if(impactproject_crosshurdles_et.getText().toString().trim().length()==0)
                {
                    impactproject_crosshurdles_et.setError("Empty not allowed");
                    impactproject_crosshurdles_et.requestFocus();
                    bpm_impactproject_crosshurdles=false;
                }
                if(impactproject_crosshurdles_et.getText().toString().trim().length()<19)
                {
                    impactproject_crosshurdles_et.setError("Minimum 20 character");
                    impactproject_crosshurdles_et.requestFocus();
                    bpm_impactproject_crosshurdles=false;
                }
            }





            if(impactproject_venture_cb.isChecked())
            {
                if(impactproject_venture_et.getText().toString().trim().length()==0)
                {
                    impactproject_venture_et.setError("Empty not allowed");
                    impactproject_venture_et.requestFocus();
                    bpm_impactproject_venture=false;
                }
                if(impactproject_venture_et.getText().toString().trim().length()<19)
                {
                    impactproject_venture_et.setError("Minimum 20 character");
                    impactproject_venture_et.requestFocus();
                    bpm_impactproject_venture=false;
                }
            }





            if(impactproject_governaward_cb.isChecked())
            {
                if(impactproject_governaward_et.getText().toString().trim().length()==0)
                {
                    impactproject_governaward_et.setError("Empty not allowed");
                    impactproject_governaward_et.requestFocus();
                    bpm_impactproject_governaward=false;
                }
                if(impactproject_governaward_et.getText().toString().trim().length()<19)
                {
                    impactproject_governaward_et.setError("Minimum 20 character");
                    impactproject_governaward_et.requestFocus();
                    bpm_impactproject_governaward=false;
                }
            }




            if(impactproject_leadershiprole_cb.isChecked())
            {
                if(impactproject_leadershiprole_et.getText().toString().trim().length()==0)
                {
                    impactproject_leadershiprole_et.setError("Empty not allowed");
                    impactproject_leadershiprole_et.requestFocus();
                    bpm_impactproject_leadershiprole=false;
                }
                if(impactproject_leadershiprole_et.getText().toString().trim().length()<19)
                {
                    impactproject_leadershiprole_et.setError("Minimum 20 character");
                    impactproject_leadershiprole_et.requestFocus();
                    bpm_impactproject_leadershiprole=false;
                }
            }



            if(permissionactivities_et.getText().toString().trim().length()==0)
            {
                permissionactivities_et.setError("Empty not allowed");
                permissionactivities_et.requestFocus();
                return false;
            }
            if(permissionactivities_et.getText().toString().trim().length()<100)
            {
                permissionactivities_et.setError("Minimum 100 character");
                permissionactivities_et.requestFocus();
                return false;
            }



            if(exp_initiative_et.getText().toString().trim().length()==0)
            {
                exp_initiative_et.setError("Empty not allowed");
                exp_initiative_et.requestFocus();
                return false;
            }
            if(exp_initiative_et.getText().toString().trim().length()<19)
            {
                exp_initiative_et.setError("Minimum 20 character");
                exp_initiative_et.requestFocus();
                return false;
            }


            if(lacking_initiative_et.getText().toString().trim().length()==0)
            {
                lacking_initiative_et.setError("Empty not allowed");
                lacking_initiative_et.requestFocus();
                return false;
            }
            if(lacking_initiative_et.getText().toString().trim().length()<19)
            {
                lacking_initiative_et.setError("Minimum 20 character");
                lacking_initiative_et.requestFocus();
                return false;
            }


        }

        if(str_studLevel.equals("x")||spin_studLevel.getSelectedItem().toString().equals("Select"))
        { //Toast.makeText(getApplicationContext(),"select the state",Toast.LENGTH_SHORT).show();
            // ((TextView)spin_state.getSelectedView()).setError("Error message");
            spin_error_TV.setVisibility(View.VISIBLE);
            spin_error_TV.setError("Select Student level");
            blevel=false;
        }

        if( edt_pmcomment.getText().toString().length() == 0 ){
            edt_pmcomment.setError( "Manager comments required!" );bpmcomments=false;}



        innovaion_ratingbar_rb=(RatingBar)findViewById(R.id.innovaion_ratingbar_rb);
        leadership_ratingbar_rb= (RatingBar)findViewById(R.id.leadership_ratingbar_rb);
        risktaken_ratingbar_rb= (RatingBar)findViewById(R.id.risktaken_ratingbar_rb);
        impact_ratingbar_rb= (RatingBar)findViewById(R.id.impact_ratingbar_rb);
        fundraised_ratingbar_rb=(RatingBar)findViewById(R.id.fundraised_ratingbar_rb);



        if(innovaion_ratingbar_rb.getRating()==0.0
           ||leadership_ratingbar_rb.getRating()==0.0
           ||risktaken_ratingbar_rb.getRating()==0.0
           ||impact_ratingbar_rb.getRating()==0.0
           ||fundraised_ratingbar_rb.getRating()==0.0
           ||final_ratingbar_rb.getRating()==0.0)
        {
            Toast.makeText(context,"Rating is mandatory",Toast.LENGTH_LONG).show();
            bamount=false;}


        if(pmcomplition_sdgs_display_tv.getText().toString().trim().length()==0)
        {
            pmcomplition_sdgs_display_tv.setError("Select the goals");
            pmcomplition_sdgs_display_tv.requestFocus();
            bsdgs_goals= false;
        }


       /* if( final_ratingbar_rb.getRating()==0.0){
            Toast.makeText(context,"Rating is mandatory",Toast.LENGTH_LONG).show();bamount=false;}
*/




        if(bpmcomments&&bamount&&blevel&&bsdgs_goals&& bpmresource_amount1&&bpmresource_amount2&&bpmresource_amount3
        &&bpmplaceofimplementation&&bpm_challengefaced&&bpm_learnings&&bpm_story&&bpm_currentsituation&&bpm_sdgsgoals
        &&bpm_impactproject_collabaration&&bpm_impactproject_againtstide&&bpm_impactproject_crosshurdles&&bpm_impactproject_venture&&bpm_impactproject_governaward
            &&bpm_impactproject_leadershiprole&&bpm_resources) {
            return true;
        }else{return false;}

    }





    public class SubmitCompletionProject extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        private ProgressBar progressBar;

       // private ProgressDialog progressDialog;

        SubmitCompletionProject (Context ctx){
            context = ctx;
        //    progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = submitCompletionProject();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            /*progressDialog.setMessage("Submitting");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            //Toast.makeText(context,result.toString(),Toast.LENGTH_LONG).show();
            if(result!=null) {
                if (result.toString().equals("Error")) {
                    Toast.makeText(getApplicationContext(), "Error occured while saving to database", Toast.LENGTH_LONG).show();
                } else
                    {

                    if(result.toString().trim().equalsIgnoreCase("Project Updated Successfully")||
                            result.toString().trim().equalsIgnoreCase("Success")|| result.toString().trim().equalsIgnoreCase("Success.")     ) {
                        Toast.makeText(getApplicationContext(), "Project Updated Successfully", Toast.LENGTH_LONG).show();
                        finish();
                        Intent ittEditProjToProjStatus = new Intent(PMComplitionProjectActivity.this, PMProjectDetailActivity.class);
                        ittEditProjToProjStatus.putExtra("pageCount", 2);
                        startActivity(ittEditProjToProjStatus);
                    }else
                        {
                        Toast.makeText(getApplicationContext(), "Error:"+result.toString(), Toast.LENGTH_LONG).show();
                    }

                }
            }
            progressBar.setVisibility(GONE);
          //  progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive submitCompletionProject()
    {
        String METHOD_NAME = "UpdateComplitedprojectDetails1";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateComplitedprojectDetails1";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","submitCompletionProject");

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("Lead_Id",LeadId);
            int projId = Integer.valueOf(getIntent().getStringExtra("PDId"));
            int PDid=Integer.valueOf(PDId);
            Log.d("ProjectId", String.valueOf(projId));
            request.addProperty("PDId",PDid);
            request.addProperty("ProjectStatus","Completed");


            String str_placeOfImpl = edt_placeofimplementation.getText().toString();
            String str_fundRaised = edt_fundraised.getText().toString();
            String str_Challanges = edt_challenges.getText().toString();
            String str_learning = edt_learning.getText().toString();
            String str_story = edt_story.getText().toString();
            String str_CurrentSituation = edt_CurrentSituation.getText().toString();
            String str_Resource = edt_resources.getText().toString();
            String str_HoursSpend = edt_HoursSpend.getText().toString();
            String str_resource_amount=resourcesUtilised_amount_pm_et.getText().toString();

            int int_innovationrating=(int) innovaion_ratingbar_rb.getRating();
            int int_leadershiprating=(int)leadership_ratingbar_rb.getRating();//
            int int_risktakenrating=(int)risktaken_ratingbar_rb.getRating();
            int int_impactrating=(int)impact_ratingbar_rb.getRating();
            int int_fundraisedrating= (int)fundraised_ratingbar_rb.getRating();

           // Double double_finalrating = Double.valueOf(final_ratingbar_rb.getRating());

            String str_managerComments = edt_pmcomment.getText().toString();

            if(!str_placeOfImpl.isEmpty() && !str_placeOfImpl.equals("") && !str_placeOfImpl.equals(null)) {

                request.addProperty("Placeofimplement", str_placeOfImpl);
                Log.d("Placeofimplement", str_placeOfImpl);
            }
            else{
                request.addProperty("Placeofimplement", "");
            }
            if(!str_fundRaised.isEmpty() && !str_fundRaised.equals("") && !str_fundRaised.equals(null)) {
                request.addProperty("FundsRaised", Integer.valueOf(str_fundRaised));
                Log.d("FundsRaised", str_fundRaised);
            }else{
                request.addProperty("FundsRaised", "");
            }
            if(!str_Challanges.isEmpty() && !str_Challanges.equals("") && !str_Challanges.equals(null)){
                request.addProperty("Challenge",str_Challanges);
                Log.d("Challenge", str_Challanges);
            }else{
                request.addProperty("Challenge","");
            }

            if(!str_learning.isEmpty() && !str_learning.equals("") && !str_learning.equals(null)){
                request.addProperty("Learning",str_learning);
                Log.d("Learning", str_learning);
            }else{
                request.addProperty("Learning","");
            }

            if(!str_story.isEmpty() && !str_story.equals("") && !str_story.equals(null)){
                request.addProperty("AsAStory",str_story);
                Log.d("AsAStory", str_story);
            }else{
                request.addProperty("AsAStory","");
            }

//-----------------added by madhu 17/9/18
            if(!str_CurrentSituation.isEmpty() && !str_CurrentSituation.equals("") && !str_CurrentSituation.equals(null)){
                request.addProperty("CurrentSituation",str_CurrentSituation);
                Log.d("CurrentSituation", str_CurrentSituation);
            }else{
                request.addProperty("CurrentSituation","");
            }

            if(!str_Resource.isEmpty() && !str_Resource.equals("") && !str_Resource.equals(null)){
                request.addProperty("Resource",str_Resource);
                Log.d("Resource", str_Resource);
            }else{
                request.addProperty("Resource","");
            }



            if(!str_resource_amount.isEmpty() && !str_resource_amount.equals("") && !str_resource_amount.equals(null))
            {
               // int int_resourse_amt= Integer.parseInt(str_resource_amount);


                long long_resoursce_amount= Long.parseLong(str_resource_amount);
                request.addProperty("ResourceAmount",long_resoursce_amount);//<ResourceAmount>long</ResourceAmount>
                Log.d("str_resourceamount", str_resource_amount);
            }else{
                request.addProperty("ResourceAmount",0);//<ResourceAmount>long</ResourceAmount>
            }


            if(!str_HoursSpend.isEmpty() && !str_HoursSpend.equals("") && !str_HoursSpend.equals(null)){
                request.addProperty("HoursSpend",str_HoursSpend);
                Log.d("str_HoursSpend", str_HoursSpend);
            }else{
                request.addProperty("HoursSpend","");
            }

            if(!str_studLevel.isEmpty() && !str_studLevel.equals("") && !str_studLevel.equals(null)){
                request.addProperty("Level",str_studLevel);
                Log.d("str_studLevel", str_studLevel);
            }else{
                request.addProperty("Level",str_studLevel);
            }
//----------
           /* if(int_finalrating!=0){
                request.addProperty("Rating",int_finalrating);
                Log.d("Rating", String.valueOf(int_finalrating));
            }else{
                request.addProperty("Rating",0);
            }*/

           // request.addProperty("Rating",double_finalrating);

            String str_finalratingbar= String.valueOf(final_ratingbar_rb.getRating());
            request.addProperty("Rating",str_finalratingbar);


            if(!str_managerComments.isEmpty() && !str_managerComments.equals("") && !str_managerComments.equals(null)){
                request.addProperty("ManagerComments",str_managerComments);
                Log.d("ManagerComments", str_managerComments);
            }else{
                request.addProperty("ManagerComments","");
            }


            request.addProperty("InnovationRating",int_innovationrating);///<InnovationRating>int</InnovationRating>
            request.addProperty("LeadershipRating",int_leadershiprating);///<LeadershipRating>int</LeadershipRating>
            request.addProperty("RiskTakenRating",int_risktakenrating);///< <RiskTakenRating>int</RiskTakenRating>
            request.addProperty("ImpactRating",int_impactrating);///<ImpactRating>int</ImpactRating>
            request.addProperty("FundRaisedRating",int_fundraisedrating);///<FundRaisedRating>int</FundRaisedRating>




            request.addProperty("SDG_Goal",goals_jsonobject.toString());// <SDG_Goal>string</SDG_Goal>



            request.addProperty("Collaboration_Supported",impactproject_collabaration_et.getText().toString());// <Collaboration_Supported>string</Collaboration_Supported>
            request.addProperty("Permission_And_Activities",permissionactivities_et.getText().toString());//<Permission_And_Activities>string</Permission_And_Activities>
            request.addProperty("Experience_Of_Initiative",exp_initiative_et.getText().toString());//<Experience_Of_Initiative>string</Experience_Of_Initiative>
            request.addProperty("Lacking_initiative",lacking_initiative_et.getText().toString());//<Lacking_initiative>string</Lacking_initiative>
            request.addProperty("Against_Tide",impactproject_againtstide_et.getText().toString());//<Against_Tide>string</Against_Tide>
            request.addProperty("Cross_Hurdles",impactproject_crosshurdles_et.getText().toString());//<Cross_Hurdles>string</Cross_Hurdles>
            request.addProperty("Entrepreneurial_Venture",impactproject_venture_et.getText().toString());//<Entrepreneurial_Venture>string</Entrepreneurial_Venture>
            request.addProperty("Government_Awarded",impactproject_governaward_et.getText().toString());//<Government_Awarded>string</Government_Awarded>
            request.addProperty("Leadership_Roles",impactproject_leadershiprole_et.getText().toString());//<Leadership_Roles>string</Leadership_Roles>








            Log.e("request",request.toString());


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
                Log.d("soapresponse1xxxx",envelope.getResponse().toString());

                //Log.d("Requestsssss",request.toString());


                //SoapObject response = (SoapObject) envelope.getResponse();

                //return null;
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("submitResponse",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
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

    public class SubmitRejectDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        SubmitRejectDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = RejectProjectDetails();

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
                Toast.makeText(PMComplitionProjectActivity.this,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(PMComplitionProjectActivity.this,"Project Rejected Successfully",Toast.LENGTH_LONG).show();
                finish();
                /*Intent ittEditProjToProjStatus = new Intent(PMUnapproved_DetailsActivity.this,PMProjectDetailActivity.class);
                ittEditProjToProjStatus.putExtra("pageCount",0);
                startActivity(ittEditProjToProjStatus);*/
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private SoapPrimitive RejectProjectDetails() {
        String METHOD_NAME = "ManagerRejectProject";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ManagerRejectProject";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            // long projectIds = (long) Integer.valueOf(projectId);
           // request.addProperty("PDId",Project_Id);
            int projId = Integer.valueOf(getIntent().getStringExtra("PDId"));
            int PDid=Integer.valueOf(PDId);
            Log.d("ProjectId", String.valueOf(projId));
            request.addProperty("PDId",PDid);
            Log.d("PDIdssssssxxxx","hi");
            // Log.d("PDIdssssssxxxx", String.valueOf(projectId));
            //  request.addProperty("ProjectStatus","Rejected");
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

    private void getProjectCompletionDtls() {
        GetProjCompletionDtls getProjCompDtls = new GetProjCompletionDtls(mContext);
        getProjCompDtls.execute();
    }

    public class GetProjCompletionDtls extends AsyncTask<Void, Void, SoapObject> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;
     //   private ProgressDialog progressDialog;

        GetProjCompletionDtls (Context ctx){
            context = ctx;
          //  progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getCompletionProjectDtls();

            Log.d("Soap response issssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            /*progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/

        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if(result!=null){
            SoapPrimitive S_Status;
            Object O_Status;
            String str_status = null;


            O_Status = result.getProperty("Status");
            if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                S_Status = (SoapPrimitive) result.getProperty("Status");
                Log.d("Title", S_Status.toString());
                str_status = O_Status.toString();
            }

            if (!str_status.equals("Success")) {
                Toast.makeText(context, str_status, Toast.LENGTH_LONG).show();
                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.LinearLayout02);
                mainLayout.setVisibility(GONE);
            } else {
                SoapPrimitive S_ProjectName, S_ProjectType, S_PlaceOfImpl, S_Objectives, S_FundRaised, S_ApprovedAmount, S_FundReceived, S_Challenges, S_Learning, S_Story, S_CurrentSituation, S_Resource,S_HoursSpend,S_resource_amount;
                Object O_ProjectName, O_ProjectType, O_PlaceOfImpl, O_Objectives, O_FundRaised, O_ApprovedAmount, O_FundReceived, O_Challanges, O_Learning, O_Story, O_CurrentSituation, O_Resource,O_HoursSpend,O_resource_amount;

                Object O_projectstartdate,O_projectenddate;
                String str_ProjectName = null, str_ProjectType = null, str_PlaceOfImpl = null, str_Objectives = null, str_FundRaised = null, str_ApprovedAmount = null, str_FundReceived = null, str_Challanges = null, str_HoursSpend=null,str_Learning = null, str_Story = null, str_CurrentSituation = null, str_Resource = null;
                String str_pmstartdate=null,str_pmenddate=null,str_resource_amount=null;

                O_ProjectName = result.getProperty("Title");
                if (!O_ProjectName.toString().equals("anyType{}") && !O_ProjectName.toString().equals(null)) {
                    S_ProjectName = (SoapPrimitive) result.getProperty("Title");
                    Log.d("Title:", S_ProjectName.toString());
                    str_ProjectName = S_ProjectName.toString();
                    projectname_tv.setText(str_ProjectName);
                }

                O_ProjectType = result.getProperty("Theme");
                if (!O_ProjectType.toString().equals("anyType{}") && !O_ProjectType.toString().equals(null)) {
                    S_ProjectType = (SoapPrimitive) result.getProperty("Theme");
                    Log.d("Project Type", S_ProjectType.toString());
                    str_ProjectType = S_ProjectType.toString();
                    projecttype_tv.setText(str_ProjectType);
                }

            /*    O_Beneficiaries = result.getProperty("BeneficiaryNo");
                if(!O_Beneficiaries.toString().equals("anyType") && !O_Beneficiaries.toString().equals(null)){
                    S_Beneficiaries = (SoapPrimitive) result.getProperty("BeneficiaryNo");
                    Log.d("Beneficiaries:",S_Beneficiaries.toString());
                    str_Beneficiaries = S_Beneficiaries.toString();
                    txt_beneficiaries.setText(str_Beneficiaries);
                }*/

                O_Objectives = result.getProperty("Objectives");
                if (!O_Objectives.toString().equals("anyType{}") && !O_Objectives.toString().equals(null)) {
                    S_Objectives = (SoapPrimitive) result.getProperty("Objectives");
                    Log.d("Objectives:", S_Objectives.toString());
                    str_Objectives = S_Objectives.toString();
                    projectobjective_tv.setText(str_Objectives);
                }

                O_PlaceOfImpl = result.getProperty("Placeofimplement");
                if (!O_PlaceOfImpl.toString().equals("anyType{}") && !O_PlaceOfImpl.toString().equals(null)) {
                    S_PlaceOfImpl = (SoapPrimitive) result.getProperty("Placeofimplement");
                    Log.d("Placeofimplement:", S_PlaceOfImpl.toString());
                    str_PlaceOfImpl = S_PlaceOfImpl.toString();
                    edt_placeofimplementation.setText(str_PlaceOfImpl);
                }

                O_FundRaised = result.getProperty("FundsRaised");
                if (!O_FundRaised.toString().equals("anyType{}") && !O_FundRaised.toString().equals(null)) {
                    S_FundRaised = (SoapPrimitive) result.getProperty("FundsRaised");
                    Log.d("FundsRaised:", S_FundRaised.toString());
                    str_FundRaised = S_FundRaised.toString();
                    edt_fundraised.setText(str_FundRaised);
                }

                O_ApprovedAmount = result.getProperty("SanctionAmount");
                if (!O_ApprovedAmount.toString().equals("anyType{}") && !O_ApprovedAmount.toString().equals(null)) {
                    S_ApprovedAmount = (SoapPrimitive) result.getProperty("SanctionAmount");
                    Log.d("SanctionAmount:", S_ApprovedAmount.toString());
                    str_ApprovedAmount = S_ApprovedAmount.toString();
                    approvedamt_tv.setText(str_ApprovedAmount);
                }

                O_FundReceived = result.getProperty("Fund_Received");
                if (!O_FundReceived.toString().equals("anyType{}") && !O_FundReceived.toString().equals(null)) {
                    S_FundReceived = (SoapPrimitive) result.getProperty("Fund_Received");
                    Log.d("Fund_Received:", S_FundReceived.toString());
                    str_FundReceived = S_FundReceived.toString();
                    leadfund_tv.setText(str_FundReceived);
                }

                O_Challanges = result.getProperty("Challenge");
                if (!O_Challanges.toString().equals("anyType{}") && !O_Challanges.toString().equals(null)) {
                    S_Challenges = (SoapPrimitive) result.getProperty("Challenge");
                    Log.d("Challenge:", S_Challenges.toString());
                    str_Challanges = S_Challenges.toString();
                    edt_challenges.setText(str_Challanges);
                }


                O_Learning = result.getProperty("Learning");
                if (!O_Learning.toString().equals("anyType{}") && !O_Learning.toString().equals(null)) {
                    S_Learning = (SoapPrimitive) result.getProperty("Learning");
                    Log.d("Learning:", S_Learning.toString());
                    str_Learning = S_Learning.toString();
                    edt_learning.setText(str_Learning);
                }

                O_Story = result.getProperty("AsAStory");
                if (!O_Story.toString().equals("anyType{}") && !O_Story.toString().equals(null)) {
                    S_Story = (SoapPrimitive) result.getProperty("AsAStory");
                    Log.d("AsAStory:", S_Story.toString());
                    str_Story = S_Story.toString();
                    edt_story.setText(str_Story);
                }

                //-----------------added by madhu 17/9/18
                O_CurrentSituation = result.getProperty("CurrentSituation");
                if (!O_CurrentSituation.toString().equals("anyType{}") && !O_CurrentSituation.toString().equals(null)) {
                    S_CurrentSituation = (SoapPrimitive) result.getProperty("CurrentSituation");
                    Log.d("CurrentSituation:", S_CurrentSituation.toString());
                    str_CurrentSituation = S_CurrentSituation.toString();
                    edt_CurrentSituation.setText(str_CurrentSituation);
                }

                O_Resource = result.getProperty("Resource");
                if (!O_Resource.toString().equals("anyType{}") && !O_Resource.toString().equals(null)) {
                    S_Resource = (SoapPrimitive) result.getProperty("Resource");
                    Log.d("CurrentSituation:", S_Resource.toString());
                    str_Resource = S_Resource.toString();
                    edt_resources.setText(str_Resource);
                }
                //----------------------------
                //-----------------added by madhu 03/07/19-------
                O_HoursSpend = result.getProperty("HoursSpend");
                if (!O_HoursSpend.toString().equals("anyType{}") && !O_HoursSpend.toString().equals(null))
                {
                    S_HoursSpend = (SoapPrimitive) result.getProperty("HoursSpend");
                    Log.d("HoursSpend:", S_HoursSpend.toString());
                    str_HoursSpend = S_HoursSpend.toString();
                    edt_HoursSpend.setText(str_HoursSpend);
                }


                O_resource_amount=result.getProperty("TotalResourses");//<TotalResourses>0</TotalResourses>
                if (!O_resource_amount.toString().equals("anyType{}") && !O_resource_amount.toString().equals(null))
                {
                    S_resource_amount= (SoapPrimitive) result.getProperty("TotalResourses");
                    str_resource_amount=S_resource_amount.toString();
                    resourcesUtilised_amount_pm_et.setText(str_resource_amount);
                }

//pmcomp_clickstartproject_editdate_tv



                O_projectstartdate= result.getProperty("ProjectStartDate");//<ProjectStartDate>0000-00-00</ProjectStartDate>
                if(O_projectstartdate.toString().equals("anyType{}")
                        || O_projectstartdate.toString().equals(null)
                        ||O_projectstartdate.toString().equals("0000-00-00"))
                {
                    calendarlabel_linearlayout.setVisibility(View.GONE);
                    calendar_linearlayout.setVisibility(View.GONE);
                    str_pmstartdate=O_projectstartdate.toString();

                }
                else{

                    SoapPrimitive S_projectstartdate=(SoapPrimitive) result.getProperty("ProjectStartDate");
                    String str_projectstartdate=S_projectstartdate.toString();
                    str_pmstartdate=S_projectstartdate.toString();
                    pmcomp_clickstartproject_editdate_tv.setText(str_projectstartdate);

                }



                O_projectenddate= result.getProperty("ProjectEndDate");//<ProjectEndDate>0000-00-00</ProjectEndDate>

                if(O_projectenddate.toString().equals("anyType{}")
                        ||O_projectenddate.toString().equals(null)
                        ||O_projectenddate.toString().equals("0000-00-00"))
                {
                    str_pmenddate=O_projectenddate.toString();
                    calendarlabel_linearlayout.setVisibility(View.GONE);
                    calendar_linearlayout.setVisibility(View.GONE);

                }
                else{

                    SoapPrimitive S_projectenddate=(SoapPrimitive) result.getProperty("ProjectEndDate");
                    String str_projectenddate=S_projectenddate.toString();
                    str_pmenddate=S_projectenddate.toString();
                    pmcomp_clickendproject_editdate_tv.setText(str_projectenddate);
                }



                if(
                        str_pmstartdate.toString().equals(null)
                        || str_pmstartdate.toString().equals("anyType{}")
                        ||str_pmstartdate.toString().equals("0000-00-00")
                        ||str_pmstartdate.toString().equalsIgnoreCase("Click for Calendar")
                        ||str_pmenddate.toString().equals(null)
                        ||str_pmenddate.toString().equals("anyType{}")
                        ||str_pmenddate.toString().equals("0000-00-00")
                        ||str_pmenddate.toString().equalsIgnoreCase("Click for Calendar"))
                {
                    calendarlabel_linearlayout.setVisibility(View.GONE);
                    calendar_linearlayout.setVisibility(View.GONE);
                    pmcomplition_studentdays_linearlayout.setVisibility(View.GONE);

                }else
                {


                    try{
                        int days_count = Days.daysBetween(new LocalDate(str_pmstartdate), new LocalDate(str_pmenddate)).getDays();
                        int int_days=days_count+1;
                        Log.e("days",String.valueOf(int_days));
                        pmcomplition_numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
                    }catch(Throwable t)
                    {
                        pmcomplition_numberofdays_tv.setText(" ");
                    }
                }




                if (result.toString().contains("SDG_Status"))//<SDG_Status>No Goals</SDG_Status>
                {

                    goals_jsonobject = new JSONObject();
                    try {
                        goals_jsonobject.put("", "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    str_sdgs_goals_resp="";
                    pmcomplition_sdgs_display_tv.setText(str_sdgs_goals_resp);
                } else
                {




                    SoapObject sdgsList = (SoapObject) result.getProperty("SDG_List");
                    Object O_goalsln;
                    SoapPrimitive primitive_slno;
                    String str_slno = null;
                    arraylist_goalsln.clear();

                    for (int i = 0; i < sdgsList.getPropertyCount(); i++) {
                        SoapObject goal_sln = (SoapObject) sdgsList.getProperty(i);

                        O_goalsln = goal_sln.getProperty("Slno");

                        {
                            primitive_slno = (SoapPrimitive) goal_sln.getProperty("Slno");//Slno
                            Log.e("goalslnname", primitive_slno.toString());
                            str_slno = primitive_slno.toString();

                            arraylist_goalsln.add(str_slno);
                        }

                    }

                  arraylist_goalsln_forcheckbox=arraylist_goalsln;
                    set_sdgs_display_tv();

                }



                str_collaboration_resp = result.getProperty("Collaboration_Supported").toString();//<Collaboration_Supported/>
                str_permissionactivities_resp = result.getProperty("Permission_And_Activities").toString();// <Permission_And_Activities/>
                str_exp_initiative_resp = result.getProperty("Experience_Of_Initiative").toString();//<Experience_Of_Initiative/>
                str_lacking_initiative_resp = result.getProperty("Lacking_initiative").toString();//<Lacking_initiative/>
                str_againtstide_resp = result.getProperty("Against_Tide").toString();//<Against_Tide/>
                str_cross_hurdles_resp = result.getProperty("Cross_Hurdles").toString();//<Cross_Hurdles/>
                str_venture_resp = result.getProperty("Entrepreneurial_Venture").toString();//<Entrepreneurial_Venture/>
                str_govern_award_resp = result.getProperty("Government_Awarded").toString();//<Government_Awarded/>
                str_leadership_role_resp = result.getProperty("Leadership_Roles").toString();// <Leadership_Roles/>


                if(str_collaboration_resp.equals("anyType{}"))
                {
                    impactproject_collabaration_et.setText("");
                    impactproject_collabaration_cb.setChecked(false);
                    impactproject_collabaration_et.setVisibility(View.GONE);
                }else
                { impactproject_collabaration_et.setText(str_collaboration_resp.toString());
                    if(str_collaboration_resp.trim().length()>0)
                    { impactproject_collabaration_cb.setChecked(true);
                        impactproject_collabaration_et.setVisibility(View.VISIBLE);
                    }
                    else{impactproject_collabaration_cb.setChecked(false);
                        impactproject_collabaration_et.setVisibility(View.GONE);
                    }
                }


                if(str_permissionactivities_resp.equals("anyType{}"))
                {
                    permissionactivities_et.setText("");

                }else
                { permissionactivities_et.setText(str_permissionactivities_resp.toString());

                }

                if(str_exp_initiative_resp.equals("anyType{}"))
                {
                    exp_initiative_et.setText("");
                }else
                { exp_initiative_et.setText(str_exp_initiative_resp.toString());}






                if(str_lacking_initiative_resp.equals("anyType{}"))
                {
                    lacking_initiative_et.setText("");
                }else
                { lacking_initiative_et.setText(str_lacking_initiative_resp.toString());}


                if(str_againtstide_resp.equals("anyType{}"))
                {
                    impactproject_againtstide_et.setText("");
                    impactproject_againtstide_cb.setChecked(false);
                    impactproject_againtstide_et.setVisibility(View.GONE);
                }else
                { impactproject_againtstide_et.setText(str_againtstide_resp.toString());
                    if(str_againtstide_resp.toString().trim().length()>0)
                    { impactproject_againtstide_cb.setChecked(true); }
                    else{impactproject_againtstide_cb.setChecked(false);
                        impactproject_againtstide_et.setVisibility(View.GONE);}

                }

                if(str_cross_hurdles_resp.equals("anyType{}"))
                {
                    impactproject_crosshurdles_et.setText("");
                    impactproject_crosshurdles_cb.setChecked(false);
                    impactproject_crosshurdles_et.setVisibility(View.GONE);

                }else
                { impactproject_crosshurdles_et.setText(str_cross_hurdles_resp.toString());
                    if(str_cross_hurdles_resp.toString().trim().length()>0)
                    {  impactproject_crosshurdles_cb.setChecked(true); }
                    else{impactproject_crosshurdles_cb.setChecked(false);
                        impactproject_crosshurdles_et.setVisibility(View.GONE);
                    }
                    //
                }




                if(str_venture_resp.equals("anyType{}"))
                {
                    impactproject_venture_et.setText("");
                    impactproject_venture_cb.setChecked(false);
                    impactproject_venture_et.setVisibility(View.GONE);
                }else
                { impactproject_venture_et.setText(str_venture_resp.toString());
                    if(str_venture_resp.toString().length()>0)
                    { impactproject_venture_cb.setChecked(true); }
                    else{impactproject_venture_cb.setChecked(false);
                        impactproject_venture_et.setVisibility(View.GONE);
                    }

                }

                if(str_govern_award_resp.equals("anyType{}"))
                {
                    impactproject_governaward_et.setText("");
                    impactproject_governaward_cb.setChecked(false);
                    impactproject_governaward_et.setVisibility(View.GONE);
                }else
                { impactproject_governaward_et.setText(str_govern_award_resp.toString());
                    if(str_govern_award_resp.trim().length()>0)
                    {
                        impactproject_governaward_cb.setChecked(true);
                    }else{
                        impactproject_governaward_cb.setChecked(false);
                        impactproject_governaward_et.setVisibility(View.GONE);
                    }
                }

                if(str_leadership_role_resp.equals("anyType{}"))
                {
                    impactproject_leadershiprole_et.setText("");
                    impactproject_leadershiprole_cb.setChecked(false);
                    impactproject_leadershiprole_et.setVisibility(View.GONE);

                }else
                { impactproject_leadershiprole_et.setText(str_leadership_role_resp.toString());
                    if(str_leadership_role_resp.trim().length()>0)
                    { impactproject_leadershiprole_cb.setChecked(true);}
                    else{
                        impactproject_leadershiprole_cb.setChecked(false);
                        impactproject_leadershiprole_et.setVisibility(View.GONE);
                    }

                    //
                }






// add impact




                /*if(str_startdate.toString().equals("anyType{}")
                        || str_startdate.toString().equals(null)
                        ||str_startdate.toString().equals("0000-00-00")
                        ||str_startdate.toString().equalsIgnoreCase("Click for Calendar")
                        ||str_enddate.toString().equals("anyType{}")
                        ||str_enddate.toString().equals(null)
                        ||str_enddate.toString().equals("0000-00-00")
                        ||str_enddate.toString().equalsIgnoreCase("Click for Calendar"))
                {
                    editstudentdays_linearlayout.setVisibility(View.GONE);
                    editproject_date_linearlayout.setVisibility(View.GONE);
                    editproject_datelabel_linearlayout.setVisibility(View.GONE);


                }else
                {


                    try{
                        int days_count = Days.daysBetween(new LocalDate(str_startdate), new LocalDate(str_enddate)).getDays();
                        int int_days=days_count+1;
                        Log.e("days",String.valueOf(int_days));
                        editstudent_numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
                    }catch(Throwable t)
                    {
                        editstudent_numberofdays_tv.setText(" ");
                    }
                }


*/





                //-----------------------------------


      /*          O_FundReceived = result.getProperty("Fund_Received");
                if(!O_FundReceived.toString().equals("anyType") && !O_FundReceived.toString().equals(null)){
                    S_ApprovedAmount = (SoapPrimitive) result.getProperty("Fund_Received");
                    Log.d("Approved Amount:",S_ApprovedAmount.toString());
                    str_ApprovedAmount = S_ApprovedAmount.toString();
                    approvedamt_tv.setText(str_ApprovedAmount);
                }*/

                SoapObject soapObj = (SoapObject) result.getProperty("docs");

                if (!soapObj.toString().equals("anyType{}") && !soapObj.toString().equals(null)) {

                    SoapPrimitive S_DocumentPath;
                    Object O_DocumentPath;
                    String str_DocumentPath, str_actualdocPath;
                    //count2 = soapObj.getPropertyCount();

                    for (count1 = 0; count1 < soapObj.getPropertyCount(); count1++) {
                        SoapObject doclist = (SoapObject) soapObj.getProperty(count1);
                        Log.d("doclist", doclist.toString());

                        O_DocumentPath = doclist.getProperty("Document_Path");
                        if (!O_DocumentPath.toString().equals("anyType{}") && !O_DocumentPath.toString().equals(null)) {
                            S_DocumentPath = (SoapPrimitive) doclist.getProperty("Document_Path");
                            str_DocumentPath = S_DocumentPath.toString();


                            //Log.d("F")

                            str_actualdocPath = serverPath + str_DocumentPath.substring(2);
                            //Log.d("Documentsssssss",str_actualdocPath);


                            //       if(str_actualdocPath.endsWith("jpg") || str_actualdocPath.endsWith("jpeg") || str_actualdocPath.endsWith("png")){

                            if (str_actualdocPath.endsWith("jpg") || str_actualdocPath.endsWith("jpeg") || str_actualdocPath.endsWith("png") || str_actualdocPath.endsWith("gif") || str_actualdocPath.endsWith("psd")) {
                                count2++;

                                str_actualdocPath = str_actualdocPath.replace(" ", "%20");
                                //Log.d("str_actualdocPath", str_actualdocPath);

                                //if(!str_actualdocPath.contains("(1)")) {
                                try {
                                    url = new URL(str_actualdocPath);
                                    urllist.add(url);
                                    Log.d("Urlsssss", url.toString());
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }

                                imagePathList.add(str_actualdocPath);
/*
                                LoadGalleryImage loadGalleryImg = new LoadGalleryImage(context);
                                loadGalleryImg.execute();*/
                            }

                            if (str_actualdocPath.endsWith("docx") || str_actualdocPath.endsWith("doc") || str_actualdocPath.endsWith("pdf")) {
                                wordDocPath = str_actualdocPath;
                                wordDocPath = wordDocPath.replace(" ", "%20");

                                Log.d("wordDocPath", wordDocPath);
                                Log.d("downloadUrl", String.valueOf(downloadUrl));
                                String[] docNameArray = wordDocPath.split("/");
                                docName = docNameArray[4];
                                Log.d("doclengthisss", String.valueOf(docNameArray.length));
                                Log.d("Docnameesssss", docName);


                                try {
                                    downloadUrl = new URL(wordDocPath);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                               /* LoadDocument loadDocument = new LoadDocument(context);
                                loadDocument.execute();*/
                            }

                        }
                    }


                    LoadGalleryImage loadGalleryImg = new LoadGalleryImage(context);
                    loadGalleryImg.execute();





                /*    Log.d("bitmapsssssssizes", String.valueOf(bitmapList.size()));
                    if(bitmapList.size()>0){
                        img_mainGallery.setImageBitmap(bitmapList.get(0));
                        txt_numOfImages.setText("Images: "+bitmapList.size());
                    }else{
                        txt_numOfImages.setText("No images to show");
                    }*/
                }

            }


                //progressBar.setVisibility(GONE);

                //progressDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class LoadDocument extends AsyncTask<Void, Void, Void> {
    //    private ProgressDialog progressDialog;
        private Context context;

        private ProgressBar progressBar;

        private String downloadFileName = "";
        private static final String TAG = "Download Task";
        File apkStorage = null;



        LoadDocument (Context context){
            this.context = context;
      //      progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
//            progressDialog=new ProgressDialog(context);

    /*        progressDialog.setMessage("Downloading...");
            progressDialog.show();*/

           /* progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected Void doInBackground(Void... params) {
          /*  ArrayList<Bitmap> bitmapLst=null;
            Bitmap bitmaplogo=null;
            try {
                Log.d("Urlssssssssssss",url.toString());
                bitmaplogo = BitmapFactory.decodeStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            HttpURLConnection c = null;//Open Url Connection
            try {
                c = (HttpURLConnection) downloadUrl.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            try {
                c.connect();//connect the URL Connection
            } catch (IOException e) {
                e.printStackTrace();
            }

            //If Connection response is not OK then show Logs
            try {
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            //Get File if SD card is present
            if (new CheckForSDCard().isSDCardPresent()) {

                apkStorage = new File(
                        Environment.getExternalStorageDirectory() + "/"
                                + "ProjectDocumentFiles");
            } else
                Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

            //If File is not present create directory
            if (!apkStorage.exists()) {
                apkStorage.mkdir();
                Log.e(TAG, "Directory Created.");
            }

            docName = docName.replace("%20","");
            outputFile = new File(apkStorage, docName);//Create Output file in Main File

            //Create New File if not present
            if (!outputFile.exists()) {
                try {
                    outputFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "File Created");
            }

            FileOutputStream fos = null;//Get OutputStream for NewFile Location
            try {
                fos = new FileOutputStream(outputFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            InputStream is = null;//Get InputStream for connection
            try {
                is = c.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[1024];//Set buffer type
            int len1 = 0;//init length
            try {
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Close all connection after doing task
            try {
                fos.close();

                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;

            //return bitmaplogo;
        }

        @Override
        protected void onPostExecute(Void string) {
    /*        bitmapList.add(bitmap);

            Log.d("count1iss", String.valueOf(count1));
            Log.d("count2iss", String.valueOf(count2));
            Log.d("Bitmapsizess", String.valueOf(bitmapList.size()));
            if(bitmapList.size()==count2){
                img_mainGallery.setImageBitmap(bitmapList.get(0));
                txt_numOfImages.setText("Images: "+bitmapList.size());
            }*/

            try {
                if (outputFile != null) {
                 //   progressDialog.dismiss();
                    progressBar.setVisibility(GONE);
                    Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();

                    try {
                        //FileOpen.openFile(mContext, myFile);
                        FileOpen.openFile(mContext, outputFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    }, 3000);

                    Log.e(TAG, "Download Failed");

                }
            } catch (Exception e) {
                e.printStackTrace();

                //Change button text if exception occurs

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 3000);
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());

            }

         //   progressDialog.dismiss();

            progressBar.setVisibility(GONE);
        }
    }




    public class LoadGalleryImage_stud extends AsyncTask<Void, Object, Bitmap> {

        private Context context;
        private ProgressBar progressBar;

        LoadGalleryImage_stud (Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
              progressBar = (ProgressBar) findViewById(R.id.progressBar);
              progressBar.setVisibility(View.VISIBLE);

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
              progressBar.setVisibility(GONE);
        }
    }

    public class LoadGalleryImage extends AsyncTask<Void, Object, ArrayList<Bitmap>> {

        private Context context;
        private ProgressBar progressBar;
        private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
      //  private ProgressDialog progressDialog;

        LoadGalleryImage (Context context){
            this.context = context;
        //    progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
//            progressDialog.setMessage("Loading");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();

        }

        @Override
        protected ArrayList<Bitmap> doInBackground(Void... params) {

            Bitmap bitmaplogo=null;

            for(int k=0;k<urllist.size();k++) {


                try {
                    //Log.d("Urlssssssssssss",url.toString());
                    //bitmaplogo = BitmapFactory.decodeStream(url.openStream());
                    bitmaplogo = BitmapFactory.decodeStream(urllist.get(k).openStream());
                    bitmapLst.add(bitmaplogo);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
            return bitmapLst;
        }

        @Override
        protected void onPostExecute(ArrayList<Bitmap> bitmap) {
         /*   bitmapList.add(bitmap);

            Log.d("count1iss", String.valueOf(count1));
            Log.d("count2iss", String.valueOf(count2));
            Log.d("Bitmapsizess", String.valueOf(bitmapList.size()));
            if(bitmapList.size()==count2){
                img_mainGallery.setImageBitmap(bitmapList.get(0));
                txt_numOfImages.setText("Images: "+bitmapList.size());
            }*/

         try {
             if (bitmap != null) {
                 if (bitmap.size() > 0) {
                     img_mainGallery.setImageBitmap(bitmap.get(0));
                     txt_numOfImages.setText("Images: " + bitmap.size());
                 }
             }
         }catch(OutOfMemoryError ex){
             runOnUiThread(new Runnable() {
                 public void run() {
                     Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                 }
             });
         }
            //}




            progressBar.setVisibility(GONE);
           // progressDialog.dismiss();
        }
    }




    private SoapObject getCompletionProjectDtls() {
        String METHOD_NAME = "GetProjectCompletionDetails";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetProjectCompletionDetails";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            long projId = (long) Integer.valueOf(getIntent().getStringExtra("PDId"));

            int PDid=Integer.valueOf(PDId);
            request.addProperty("Lead_Id",LeadId);
            request.addProperty("projectId",projId);

            Log.e("tag","Lead_Id:"+LeadId);
            Log.e("tag","projectId:"+projId);
            /*request.addProperty("leadId","MH00");
            request.addProperty("PDId",mapProjectIdProject.get(selectedProject));*/




            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapcompletionxxxx",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
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



    private void initializeViews() {

        projectname_tv = (TextView) findViewById(R.id.projectname_tv);
        projecttype_tv = (TextView) findViewById(R.id.projecttype_tv);
        projectobjective_tv = (TextView) findViewById(R.id.projectobjective_tv);
        leadfund_tv = (TextView) findViewById(R.id.leadfund_tv);
        approvedamt_tv = (TextView) findViewById(R.id.approvedamt_tv);

        edt_placeofimplementation = (EditText) findViewById(R.id.edt_placeofimplementation);
        edt_fundraised = (EditText) findViewById(R.id.edt_fundraised);
        edt_challenges = (EditText) findViewById(R.id.edt_challenges);
        edt_learning = (EditText) findViewById(R.id.edt_learning);
        edt_story = (EditText) findViewById(R.id.edt_story);
        edt_pmcomment = (EditText) findViewById(R.id.edt_pmcomment);

        btn_saveImg = (ImageView) findViewById(R.id.btn_saveImg);
        btn_reject = (ImageView) findViewById(R.id.btn_reject);
        img_mainGallery = (ImageView) findViewById(R.id.img_mainGallery);
        txt_numOfImages = (TextView) findViewById(R.id.txt_numOfImages);

        txt_studentid = (TextView) findViewById(R.id.txt_studentid);
        txt_studentid.setText(LeadId);


        imagePathList = new ArrayList<String>();
        bitmapList = new ArrayList<Bitmap>();

    }
  /*  public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
            TableRow tbrow = new TableRow(this);
            ImageView iv1 = new ImageView(this);
            iv1.setImageResource(R.drawable.lead_logo);
            iv1.setLayoutParams(new TableRow.LayoutParams(100,100));
            tbrow.addView(iv1);
            //stk.addView(tbrow);
            ImageView iv2 = new ImageView(this);
            iv2.setImageResource(R.drawable.lead_logo);
            iv2.setLayoutParams(new TableRow.LayoutParams(100,100));
            iv2.setPadding(2,0,0,0);
            tbrow.addView(iv2);
            stk.addView(tbrow);
        TableRow tbrow1 = new TableRow(this);
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.lead_logo);
        iv3.setLayoutParams(new TableRow.LayoutParams(100,100));
        tbrow1.addView(iv3);
        stk.addView(tbrow1);

    }*/

    /*public void init1() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow = new TableRow(this);
        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.n1);
        iv1.setLayoutParams(new TableRow.LayoutParams(100,100));
        tbrow.addView(iv1);
        //stk.addView(tbrow);
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.n2);
        iv2.setLayoutParams(new TableRow.LayoutParams(100,100));
        iv2.setPadding(2,0,0,0);
        tbrow.addView(iv2);
        stk.addView(tbrow);
        TableRow tbrow1 = new TableRow(this);
        ImageView iv11 = new ImageView(this);
        iv11.setImageResource(R.drawable.n4);
        iv11.setLayoutParams(new TableRow.LayoutParams(100,100));
        tbrow1.addView(iv11);
        //stk.addView(tbrow);
        ImageView iv22 = new ImageView(this);
        iv22.setImageResource(R.drawable.n5);
        iv22.setLayoutParams(new TableRow.LayoutParams(100,100));
        iv22.setPadding(2,0,0,0);
        tbrow1.addView(iv22);
        stk.addView(tbrow1);

        stk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PMComplitionProjectActivity.this,PMGallaryActivity.class);
                startActivity(i);
            }});

    }*/
    public static class FileOpen {

        public static void openFile(Context context, File url) throws IOException {
            // Create URI
            File file=url;
           // Uri uri = Uri.fromFile(file);
            Uri uri = FileProvider.getUriForFile(context, "com.mydomain.fileprovider", file);


            Intent intent = new Intent(Intent.ACTION_VIEW);
            // Check what kind of file you are trying to open, by comparing the url with extensions.
            // When the if condition is matched, plugin sets the correct intent (mime) type,
            // so Android knew what application to use to open the file
            if (url.toString().contains(".doc") || url.toString().contains(".docx")) {
                // Word document
                intent.setDataAndType(uri, "application/msword");
            } else if(url.toString().contains(".pdf")) {
                // PDF file
                intent.setDataAndType(uri, "application/pdf");
            } else if(url.toString().contains(".ppt") || url.toString().contains(".pptx")) {
                // Powerpoint file
                intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
            } else if(url.toString().contains(".xls") || url.toString().contains(".xlsx")) {
                // Excel file
                intent.setDataAndType(uri, "application/vnd.ms-excel");
            } else if(url.toString().contains(".zip") || url.toString().contains(".rar")) {
                // WAV audio file
                intent.setDataAndType(uri, "application/x-wav");
            } else if(url.toString().contains(".rtf")) {
                // RTF file
                intent.setDataAndType(uri, "application/rtf");
            } else if(url.toString().contains(".wav") || url.toString().contains(".mp3")) {
                // WAV audio file
                intent.setDataAndType(uri, "audio/x-wav");
            } else if(url.toString().contains(".gif")) {
                // GIF file
                intent.setDataAndType(uri, "image/gif");
            } else if(url.toString().contains(".jpg") || url.toString().contains(".jpeg") || url.toString().contains(".png")) {
                // JPG file
                intent.setDataAndType(uri, "image/jpeg");
            } else if(url.toString().contains(".txt")) {
                // Text file
                intent.setDataAndType(uri, "text/plain");
            } else if(url.toString().contains(".3gp") || url.toString().contains(".mpg") || url.toString().contains(".mpeg") || url.toString().contains(".mpe") || url.toString().contains(".mp4") || url.toString().contains(".avi")) {
                // Video files
                intent.setDataAndType(uri, "video/*");
            } else {
                //if you want you can also define the intent type for any other file

                //additionally use else clause below, to manage other unknown extensions
                //in this case, Android will show all applications installed on the device
                //so you can choose which application to use
                intent.setDataAndType(uri, "*/*");
            }

            //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        }
    }



    void impactproject_alert_dialog()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(PMComplitionProjectActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("LEADCampus");

        if(str_impactproject.equals("0")) {
            dialog.setMessage("Are you sure want to mark this an Impact-Project?");
        }
        if(str_impactproject.equals("1"))
        {
            dialog.setMessage("Are you sure want to mark this an Non-Impact-Project?");
        }

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                Submit_ImpactProject_AsyncTask submit_impactproject = new Submit_ImpactProject_AsyncTask(mContext);
                submit_impactproject.execute();
                dialog.dismiss();
                //call web service
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


//sdgs goals


    public void check_box_selected()
    {
        String str_selected_goals="";
        String str_selected_goals_array="";

        if(goal_1_nopoverty_cb.isChecked())
        {
            str_selected_goals=str_selected_goals+goal_1_nopoverty_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_1_nopoverty_cb.getText().toString()+","+"1"+",";

        }
        if(goal_2_zerohunger_cb.isChecked())
        {
            str_selected_goals=str_selected_goals+goal_2_zerohunger_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_2_zerohunger_cb.getText().toString()+","+"2"+",";


        }
        if(goal_3_goodhealth_cb.isChecked())
        {
            str_selected_goals=str_selected_goals+goal_3_goodhealth_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_3_goodhealth_cb.getText().toString()+","+"3"+",";
        }
        if(goal_4_quality_cb.isChecked())
        {
            str_selected_goals=str_selected_goals+goal_4_quality_cb .getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_4_quality_cb .getText().toString()+","+"4"+",";
        }
        if(goal_5_gender_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_5_gender_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_5_gender_cb.getText().toString()+","+"5"+",";
        }
        if(goal_6_clean_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_6_clean_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_6_clean_cb.getText().toString()+","+"6"+",";
        }



        if(goal_7_affordable_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_7_affordable_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_7_affordable_cb.getText().toString()+","+"7"+",";
        }
        if(goal_8_decent_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_8_decent_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_8_decent_cb.getText().toString()+","+"8"+",";
        }
        if(goal_9_industry_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_9_industry_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_9_industry_cb.getText().toString()+","+"9"+",";
        }
        if(goal_10_reduce_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_10_reduce_cb.getText().toString()+",";

            str_selected_goals_array = str_selected_goals_array+goal_10_reduce_cb.getText().toString()+","+"10"+",";
        }
        if(goal_11_sustain_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_11_sustain_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_11_sustain_cb.getText().toString()+","+"11"+",";
        }
        if(goal_12_responsible_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_12_responsible_cb.getText().toString()+" ,";
            str_selected_goals_array = str_selected_goals_array+goal_12_responsible_cb.getText().toString()+","+"12"+",";
        }
        if(goal_13_climate_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_13_climate_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_13_climate_cb.getText().toString()+","+"13"+",";
        }

        if(goal_14_lifewater_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_14_lifewater_cb.getText().toString()+" ,";
            str_selected_goals_array = str_selected_goals_array+goal_14_lifewater_cb.getText().toString()+","+"14"+",";
        }
        if(goal_15_lifeland_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_15_lifeland_cb.getText().toString()+" ,";

            str_selected_goals_array = str_selected_goals_array+goal_15_lifeland_cb.getText().toString()+","+"15"+",";
        }
        if(goal_16_peace_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_16_peace_cb.getText().toString()+"  ,";

            str_selected_goals_array = str_selected_goals_array+goal_16_peace_cb.getText().toString()+","+"16"+",";
        }
        if(goal_17_partnerships_cb.isChecked())
        {
            str_selected_goals= str_selected_goals+goal_17_partnerships_cb.getText().toString();
            str_selected_goals_array = str_selected_goals_array+goal_17_partnerships_cb.getText().toString()+","+"17"+",";
        }


        if(goal_18_none_cb.isChecked())
        {
          /* goalslist_linearlayout.setVisibility(View.GONE);
           goal_1_nopoverty_cb.setVisibility(View.GONE);*/

            str_selected_goals="";
            str_selected_goals_array="";
            str_selected_goals= str_selected_goals+goal_18_none_cb.getText().toString();
            str_selected_goals_array = str_selected_goals_array+goal_18_none_cb.getText().toString()+","+"18"+",";
        }



        str_selected_goals_array = str_selected_goals_array.substring(0, str_selected_goals_array.length() - 1);

        Log.e("str",str_selected_goals_array);


        //String x="No Poverty ,1,No Poverty ,Zero Hunger ,2";

        String [] stringArray=str_selected_goals_array.split(",");

        Log.e("str",stringArray.toString());

        Log.e("length", String.valueOf(stringArray.length));

        //Log.e("str1",stringArray[3].toString());


        // Create JSONObject
        goals_jsonobject = new JSONObject();





        for(int i=0;i<stringArray.length;i=i+2)
        {
            int j=i;
            String goals=stringArray[j];
            String goals_number=stringArray[j+1];
            arraylist_goalsln_forcheckbox.add(goals_number);
            try {
                goals_jsonobject.put(goals, goals_number);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.e("jsonobject",goals_jsonobject.toString());

        pmcomplition_sdgs_display_tv.setText(str_selected_goals);
    }





    public void set_sdgs_display_tv()
    {

        String str_inner_goalsln;
        String final_goals="";


        for (int i = 0; i < arraylist_goalsln.size(); i++)
        {

            str_inner_goalsln=arraylist_goalsln.get(i).toString().trim();
            String str_goals_array="";

            if(str_inner_goalsln.equals("1"))
            {
                str_selected_goals_response=str_selected_goals_response+"No Poverty"+",";
                str_goals_array = str_goals_array+"No Poverty"+","+"1"+",";
            }
            if(str_inner_goalsln.equals("2"))
            {
                str_selected_goals_response=str_selected_goals_response+"Zero Hunger"+",";
                str_goals_array = str_goals_array+"Zero Hunger"+","+"2"+",";
            }


            if(str_inner_goalsln.equals("3"))
            {
                str_selected_goals_response=str_selected_goals_response+"Good Health and Well-being"+",";
                str_goals_array = str_goals_array+"Good Health and Well-being"+","+"3"+",";
            }

            if(str_inner_goalsln.equals("4"))
            {
                str_selected_goals_response=str_selected_goals_response+"Quality Education"+",";
                str_goals_array = str_goals_array+"Quality Education"+","+"4"+",";
            }

            if(str_inner_goalsln.equals("5"))
            {
                str_selected_goals_response=str_selected_goals_response+"Gender Equality"+",";
                str_goals_array = str_goals_array+"Gender Equality"+","+"5"+",";
            }

            if(str_inner_goalsln.equals("6"))
            {
                str_selected_goals_response=str_selected_goals_response+"Clean Water and Sanitation"+",";
                str_goals_array = str_goals_array+"Clean Water and Sanitation"+","+"6"+",";
            }

            if(str_inner_goalsln.equals("7"))
            {
                str_selected_goals_response=str_selected_goals_response+"Affordable and Clean Energy"+",";
                str_goals_array = str_goals_array+"Affordable and Clean Energy"+","+"7"+",";
            }

            if(str_inner_goalsln.equals("8"))
            {
                str_selected_goals_response=str_selected_goals_response+"Decent Work and Economic Growth"+",";
                str_goals_array = str_goals_array+"Decent Work and Economic Growth"+","+"8"+",";
            }

            if(str_inner_goalsln.equals("9"))
            {
                str_selected_goals_response=str_selected_goals_response+"Industry or Innovation and Infrastructure"+",";
                str_goals_array = str_goals_array+"Industry or Innovation and Infrastructure"+","+"9"+",";
            }

            if(str_inner_goalsln.equals("10"))
            {
                str_selected_goals_response=str_selected_goals_response+"Reduced Inequality"+",";
                str_goals_array = str_goals_array+"Reduced Inequality"+","+"10"+",";
            }



            if(str_inner_goalsln.equals("11"))
            {
                str_selected_goals_response=str_selected_goals_response+"Sustainable Cities and Communities"+",";
                str_goals_array = str_goals_array+"Sustainable Cities and Communities"+","+"11"+",";
            }

            if(str_inner_goalsln.equals("12"))
            {
                str_selected_goals_response=str_selected_goals_response+"Responsible Consumption and Production"+",";
                str_goals_array = str_goals_array+"Responsible Consumption and Production"+","+"12"+",";
            }

            if(str_inner_goalsln.equals("13"))
            {
                str_selected_goals_response=str_selected_goals_response+"Climate Action"+",";
                str_goals_array = str_goals_array+"Climate Action"+","+"13"+",";
            }

            if(str_inner_goalsln.equals("14"))
            {
                str_selected_goals_response=str_selected_goals_response+"Life Below Water"+",";
                str_goals_array = str_goals_array+"Life Below Water"+","+"14"+",";
            }

            if(str_inner_goalsln.equals("15"))
            {
                str_selected_goals_response=str_selected_goals_response+"Life on Land"+",";
                str_goals_array = str_goals_array+"Life on Land"+","+"15"+",";
            }

            if(str_inner_goalsln.equals("16"))
            {
                str_selected_goals_response=str_selected_goals_response+"Peace and Justice Strong Institutions"+",";
                str_goals_array = str_goals_array+"Peace and Justice Strong Institutions"+","+"16"+",";
            }

            if(str_inner_goalsln.equals("17"))
            {
                str_selected_goals_response=str_selected_goals_response+"Partnerships to achieve the Goal"+",";
                str_goals_array = str_goals_array+"Partnerships to achieve the Goal"+","+"17"+",";
            }

            if(str_inner_goalsln.equals("18"))
            {
                str_selected_goals_response=str_selected_goals_response+"None"+",";
                str_goals_array = str_goals_array+"None"+","+"18"+",";
            }

            final_goals=final_goals+str_goals_array;

        }

        str_sdgs_goals_resp=str_selected_goals_response;
        pmcomplition_sdgs_display_tv.setText(str_sdgs_goals_resp);

        final_goals = final_goals.substring(0, final_goals.length() - 1);
        String [] stringArray=final_goals.split(",");



        // Create JSONObject
        goals_jsonobject = new JSONObject();



        for(int i=0;i<stringArray.length;i=i+2)
        {
            int j=i;
            String goals=stringArray[j];
            String goals_number=stringArray[j+1];
            try {
                goals_jsonobject.put(goals, goals_number);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        Log.e("json_draftresponse",goals_jsonobject.toString());




    }



//sdgs goals





    public class Submit_ImpactProject_AsyncTask extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        private ProgressBar progressBar;

        // private ProgressDialog progressDialog;

        Submit_ImpactProject_AsyncTask (Context ctx){
            context = ctx;
            //    progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = submit_impactProject();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

            /*progressDialog.setMessage("Submitting");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            //Toast.makeText(context,result.toString(),Toast.LENGTH_LONG).show();
            if(result!=null)
            {
                Log.e("result",result.toString()); //Success


                if(result.toString().equalsIgnoreCase("Success"))
                {

                    if(str_impactproject.equals("0")) {
                        Intent intent_obj = new Intent(PMComplitionProjectActivity.this, PMProjectDetailActivity.class);
                        intent_obj.putExtra("pageCount", 2);
                        startActivity(intent_obj);
                    }
                    else
                        {


                            Intent i = new Intent(PMComplitionProjectActivity.this, PMComplitionProjectActivity.class);
                            i.putExtra("name",Name);
                            i.putExtra("projectId",PDId);
                            i.putExtra("LeadId",LeadId);
                            i.putExtra("PDId",PDId);
                            i.putExtra("MobileNo",MobileNo);
                            i.putExtra("Student_Image_Path",Student_Image_Path);
                            i.putExtra("str_pmcomp_collegename",str_collegename);
                            i.putExtra("str_pmcomp_streamname",str_streamname);
                            i.putExtra("str_pmcomp_is_impactproject","0");
                            startActivity(i);






                        }
                }
               /* if (result.toString().equals("Error")) {
                    Toast.makeText(getApplicationContext(), "Error occured while saving to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Project Updated Successfully", Toast.LENGTH_LONG).show();
                    finish();
                    Intent ittEditProjToProjStatus = new Intent(PMComplitionProjectActivity.this, PMProjectDetailActivity.class);
                    ittEditProjToProjStatus.putExtra("pageCount", 2);
                    startActivity(ittEditProjToProjStatus);
                }*/
            }
            progressBar.setVisibility(GONE);
            //  progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



    private SoapPrimitive submit_impactProject()
    {
        String METHOD_NAME = "UpdateProjectAsImpact";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectAsImpact";
        String NAMESPACE = "http://mis.leadcampus.org/";

        int int_isimpactproject= 2;//purposely assigned


        long long_pid= Long.parseLong(PDId);

        if(str_impactproject.equals("0"))
        {
            int_isimpactproject=1;
        }
        if(str_impactproject.equals("1"))
        {
            int_isimpactproject=0;
        }


        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);






            request.addProperty("PDId",long_pid);// <PDId>long</PDId>
            request.addProperty("isImpactProject",int_isimpactproject);//<isImpactProject>int</isImpactProject>


            Log.e("request",request.toString());



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);




            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("soapresponse1xxxx",envelope.getResponse().toString());


                //return null;
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("ImpactResponse",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
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
            Intent itthomeToLogin = new Intent(PMComplitionProjectActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(PMComplitionProjectActivity.this ,PMProjectDetailActivity.class);
            ittProjDtlsToHome.putExtra("pageCount",2);
            startActivity(ittProjDtlsToHome);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



   public void validation_sdg_confirm()
    {
        if((str_sdgs_goals_verified.equalsIgnoreCase("no")))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(PMComplitionProjectActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("LEADCampus");
            dialog.setMessage("Kindly verify SDGS Goal before Submitting" );

            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {

                    str_sdgs_goals_verified="yes";
                    dialog.dismiss();
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Action for "Cancel".
                            str_sdgs_goals_verified="no";
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
    }








    public void check_box_enable2()
    {


        String str_inner_goalsln;
        Log.e("size", String.valueOf(arraylist_goalsln_forcheckbox.size()));

        for (int i = 0; i < arraylist_goalsln_forcheckbox.size(); i++)
        {

            str_inner_goalsln=arraylist_goalsln_forcheckbox.get(i).toString().trim();
            if(str_inner_goalsln.equals("1"))
            {
                goal_1_nopoverty_cb.setChecked(true);
            }
            if(str_inner_goalsln.equals("2"))
            {
                goal_2_zerohunger_cb.setChecked(true);
            }


            if(str_inner_goalsln.equals("3"))
            {
                goal_3_goodhealth_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("4"))
            {
                goal_4_quality_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("5"))
            {
                goal_5_gender_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("6"))
            {
                goal_6_clean_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("7"))
            {
                goal_7_affordable_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("8"))
            {
                goal_8_decent_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("9"))
            {
                goal_9_industry_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("10"))
            {
                goal_10_reduce_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("11"))
            {
                goal_11_sustain_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("12"))
            {
                goal_12_responsible_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("13"))
            {
                goal_13_climate_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("14"))
            {
                goal_14_lifewater_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("15"))
            {
                goal_15_lifeland_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("16"))
            {
                goal_16_peace_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("17"))
            {
                goal_17_partnerships_cb.setChecked(true);
            }

            if(str_inner_goalsln.equals("18"))
            {
                goal_18_none_cb.setChecked(true);

                goal_1_nopoverty_cb.setEnabled(false);
                goal_2_zerohunger_cb.setEnabled(false);
                goal_3_goodhealth_cb.setEnabled(false);
                goal_4_quality_cb.setEnabled(false);
                goal_5_gender_cb.setEnabled(false);
                goal_6_clean_cb.setEnabled(false);
                goal_7_affordable_cb.setEnabled(false);
                goal_8_decent_cb.setEnabled(false);
                goal_9_industry_cb.setEnabled(false);
                goal_10_reduce_cb.setEnabled(false);
                goal_11_sustain_cb.setEnabled(false);
                goal_12_responsible_cb.setEnabled(false);
                goal_13_climate_cb.setEnabled(false);
                goal_14_lifewater_cb.setEnabled(false);
                goal_15_lifeland_cb.setEnabled(false);
                goal_16_peace_cb.setEnabled(false);
                goal_17_partnerships_cb.setEnabled(false);
            }

        }

        arraylist_goalsln_forcheckbox.clear();
    }














    @Override
    public void onBackPressed() {
        Intent ittProjDtlsToHome = new Intent(PMComplitionProjectActivity.this ,PMProjectDetailActivity.class);
        ittProjDtlsToHome.putExtra("pageCount",2);
        startActivity(ittProjDtlsToHome);
    }
}// end of class
