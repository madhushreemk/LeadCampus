package com.leadcampusapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
//import androidx.appcompat.widget.DefaultItemAnimator;
//import androidx.appcompat.widget.GridLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;
import static com.leadcampusapp.CompletionGalleryFolderSelectActivity.imageFilePath;

public class FinalProjectCompletion_Activity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{


    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    private SharedPreferences shardpref_S_obj;

    private String str_leadId,str_RegistrationId;
    private static ProgressDialog pd;

    String str_projectid,str_projectstatus,str_impactproject_status;
    String str_projectdraft_Status,str_response_approvedproject_status;
    private ArrayList<byte[]> arrlstUploadImageByteArray = null;

    TextView projectname_tv;

    TextView projectname_fpc_tv,projectype_fpc_tv,beneficiaries_fpc_tv,objectives_fpc_tv,placeofimpl_fpc_tv,approvedAmt_fpc_tv,challanges_fpc_et,learning_fpc_et,story_fpc_et;

    EditText resourcesUtilised_amount_fpc_et,resourcesUtilised_fpc_et;
    EditText placeofimpl_fpc_et,edt_hoursspent;

    String str_response_approved_title,str_response_approved_themetypename,str_response_approved_beneficinary,str_response_approved_objective,str_response_approved_approvedAmt,str_response_approved_placeofimplmnt;

    String str_response_SanctionAmount,str_response_Fund_Received,str_response_fundraised,str_response_challenge,str_response_learning,str_response_story,str_response_utilised,str_hoursspent,str_resource_amount_response;
    private ImageButton btn_add_image;


    int CompletionProgress,CompletionProgress1;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    File file_img;
    File file_imageFile;
    private static Uri uri_fileUri;
    private HashMap<String,Bitmap> cameraImgMap;
    private ImageAdapter imageAdapter;
    private ArrayList<String> imageUrls;
    private ArrayList<String> imgurlss;
    ArrayList<byte[]> mImageInByte;
    private static final int PICKFILE_RESULT_CODE = 45;
    private byte[] docbyteArray = null;
    private String extensions = null;
    private List<String> imagesEncodedList;
    private TextView fileSelected_fpc_tv,txt_leadFunded;

    private ArrayList<String> arrlstUploadedImageBitmap = null;
    private Button btn_submitCompletion,btn_saveDraft;

    private static TextView txt_numOfImages;
   // private TextView txt_noOfImg;
    private static ImageView img_mainGallery;
    private ImageView uploadReport_fpc_iv,btn_doc;
    private EditText edt_fundRaised;
    private int count1;
    private int count2;
    ArrayList<URL> urllist = new ArrayList<URL>();
    private URL url;
    private String wordDocPath="null";
    private ArrayList<String> imagePathList;
    private URL downloadUrl;
    private String docName;
    File outputFile = null;
    TextView txt_doc;
    static ArrayList<Bitmap> bitmapLst=new ArrayList<Bitmap>();
    RelativeLayout rl_gallery_images;
    LinearLayout ll_gallery_images;
    Dialog dialog = null;
    RadioGroup rg;
    RadioButton rb;


    Class_alert_msg obj_class_alert_msg;
    private Context contextpresentActivity;


    static TextView studentcomp_clickstartprojectdate_tv;
    static TextView studentcomp_clickendprojectdate_tv;
    static String yyyyMMdd_studentcomp_startdate = "";
    static String yyyyMMdd_studentcomp_enddate = "";
    private static TextView studentcomp_numberofdays_tv;

    String str_finalcomp_projectstartdate,str_finalcomp_projectenddate,str_is_impactproject;
    LinearLayout datelabel_linearlayout,date_linearlayout,days_linearlayout;


    Spinner sdgs_sp;
    TextView sdgs_dropdownvalue_tv,sdgs_display_tv;



    CheckBox goal_1_nopoverty_cb,goal_2_zerohunger_cb,goal_3_goodhealth_cb,goal_4_quality_cb,goal_5_gender_cb,goal_6_clean_cb,
            goal_7_affordable_cb,goal_8_decent_cb,goal_9_industry_cb,goal_10_reduce_cb,goal_11_sustain_cb,goal_12_responsible_cb,
            goal_13_climate_cb,goal_14_lifewater_cb,goal_15_lifeland_cb,goal_16_peace_cb,goal_17_partnerships_cb,goal_18_none_cb;


    JSONObject goals_jsonobject;

    String str_selected_goals_response="";

    String str_alertshowhide;

    String str_collaboration_resp,str_permissionactivities_resp,str_exp_initiative_resp,str_lacking_initiative_resp
            ,str_againtstide_resp,str_cross_hurdles_resp,str_venture_resp,str_govern_award_resp,str_leadership_role_resp,str_sdgs_goals_resp;

    private ArrayList<String> arraylist_goalsln = new ArrayList<String>();

    private ArrayList<String> arraylist_goalsln_forcheckbox = new ArrayList<String>();



    //impactproject

    CheckBox impactproject_collabaration_cb,impactproject_againtstide_cb,impactproject_crosshurdles_cb,impactproject_venture_cb,
            impactproject_governaward_cb,impactproject_leadershiprole_cb;

    EditText impactproject_collabaration_et,impactproject_againtstide_et,impactproject_crosshurdles_et,
            impactproject_venture_et,impactproject_governaward_et,impactproject_leadershiprole_et,permissionactivities_et,
    exp_initiative_et,lacking_initiative_et;

    TextView permissionactivities_wordscount_tv;

    String str_validation_leadershiprole,str_validation_governaward,str_validation_venture,str_validation_crosshurdles,str_validation_againtstide,
            str_validation_collabaration;
    LinearLayout impactproject_linearlayout,goalslist_linearlayout;



    // added by madhu for server img delete
    private static ProgressDialog pd1;
    SQLiteDatabase db1;
    Cursor cursor1;
    String status;
    static ArrayList<URL> TempUrlList = new ArrayList<>();
    ArrayList<String> documentlist_slno = new ArrayList<String>();
    ArrayList<String> documentlist_path = new ArrayList<>();
    ArrayList<String> Delete_doclist_slno = new ArrayList<String>();
    ArrayList<String> Delete_doclist_path = new ArrayList<>();


    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_project_completion);



        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        contextpresentActivity = FinalProjectCompletion_Activity.this;

        projectname_fpc_tv = (TextView) findViewById(R.id.projectname_fpc_tv);
        projectype_fpc_tv = (TextView) findViewById(R.id.projectype_fpc_tv);
        beneficiaries_fpc_tv = (TextView) findViewById(R.id.beneficiaries_fpc_tv);
        objectives_fpc_tv = (TextView) findViewById(R.id.objectives_fpc_tv);
        placeofimpl_fpc_et = (EditText) findViewById(R.id.placeofimpl_fpc_et);
        approvedAmt_fpc_tv = (TextView) findViewById(R.id.approvedAmt_fpc_tv);
        challanges_fpc_et = (TextView) findViewById(R.id.challanges_fpc_et);
        learning_fpc_et = (TextView) findViewById(R.id.learning_fpc_et);
        story_fpc_et = (TextView) findViewById(R.id.story_fpc_et);
        resourcesUtilised_fpc_et = (EditText) findViewById(R.id.resourcesUtilised_fpc_et);
        resourcesUtilised_amount_fpc_et= (EditText) findViewById(R.id.resourcesUtilised_amount_fpc_et);
        fileSelected_fpc_tv = (TextView) findViewById(R.id.fileSelected_fpc_TV);
        txt_leadFunded =  (TextView) findViewById(R.id.txt_leadFunded);
        edt_hoursspent=(EditText) findViewById(R.id.edt_hoursspent);

        img_mainGallery = (ImageView) findViewById(R.id.img_mainGallery);
        txt_numOfImages = (TextView) findViewById(R.id.txt_numOfImages);
        rl_gallery_images=(RelativeLayout)findViewById(R.id.rl_gallery_images);

     //   txt_noOfImg = (TextView) findViewById(R.id.txt_noOfImg);

        arrlstUploadedImageBitmap = new ArrayList<String>();
        btn_add_image = (ImageButton)findViewById(R.id.btn_add_image);
        btn_submitCompletion = (Button) findViewById(R.id.btn_submitCompletion);
        btn_saveDraft = (Button) findViewById(R.id.btn_saveDraft);
        uploadReport_fpc_iv=(ImageView)findViewById(R.id.uploadReport_fpc_iv);
        edt_fundRaised = (EditText) findViewById(R.id.edt_fundRaised);
        btn_doc =(ImageView) findViewById(R.id.btn_doc);
       // txt_doc = (TextView) findViewById(R.id.txt_doc);
        ll_gallery_images = (LinearLayout) findViewById(R.id.ll_gallery_images);


        studentcomp_clickstartprojectdate_tv = (TextView)findViewById(R.id.studentcomp_clickstartprojectdate_tv);
        studentcomp_clickendprojectdate_tv = (TextView) findViewById(R.id.studentcomp_clickendprojectdate_tv);
        studentcomp_numberofdays_tv=(TextView)findViewById(R.id.studentcomp_numberofdays_tv);


        datelabel_linearlayout=(LinearLayout)findViewById(R.id.datelabel_linearlayout);
        date_linearlayout=(LinearLayout)findViewById(R.id.date_linearlayout);
        days_linearlayout=(LinearLayout)findViewById(R.id.days_linearlayout);

        sdgs_sp =(Spinner)findViewById(R.id.sdgs_sp);
        sdgs_dropdownvalue_tv=(TextView)findViewById(R.id.sdgs_dropdownvalue_tv);
        sdgs_display_tv=(TextView)findViewById(R.id.sdgs_display_tv);



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


        permissionactivities_wordscount_tv=(TextView) findViewById(R.id.permissionactivities_wordscount_tv);

        str_validation_leadershiprole=str_validation_governaward=str_validation_venture=str_validation_crosshurdles
                =str_validation_againtstide=str_validation_collabaration="yes";

        Log.e("str_collabaration",str_validation_collabaration.toString());

        imageUrls = new ArrayList<String>();
        imgurlss = new ArrayList<String>();
        mImageInByte=new ArrayList<byte[]>();
        cameraImgMap = new HashMap<String,Bitmap>();
        imagePathList = new ArrayList<String>();


        arrlstUploadImageByteArray = new ArrayList<>();

        pd1 = new ProgressDialog(FinalProjectCompletion_Activity.this);

        //Log.e("tag","madhu urllist=="+urllist);
        GalleryImageBitmap.setAllImgURL(urllist);
        TempUrlList.clear();

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Completion Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        shardpref_S_obj=getApplicationContext().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();

        shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_RegistrationId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        Log.d("str_RegistrationId:",str_RegistrationId);

        final Intent intent = getIntent();
        if(intent!=null) {
            str_projectid = intent.getExtras().getString("projectid_ValueKey");
            str_projectstatus= intent.getExtras().getString("projectstatus_ValueKey");
            str_impactproject_status=intent.getExtras().getString("impact_projectstatus_ValueKey");


            Log.e("projectid",str_projectid);
            Log.e("leadID",str_leadId);
            Log.e("projectstatus",str_projectstatus);

        }

        /* if(str_GalleryIntent.equals("1")) {
			           // TempUrlList = GalleryImageBitmap.getAllImgURL();
			            LoadGalleryImage loadGalleryImg = new LoadGalleryImage(FinalProjectCompletion_Activity.this);
			            loadGalleryImg.execute();
			        }*/


        ll_gallery_images.setVisibility(View.GONE);


        if(str_impactproject_status.equals("0"))
        {
            impactproject_linearlayout.setVisibility(View.GONE);
        }


        /*studentcomp_clickstartprojectdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment fromdateFragment = new DatePickerFragmentFromDate();
                fromdateFragment.show(getFragmentManager(), "Date Picker");
            }
        });


        studentcomp_clickendprojectdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // settodate();
                DialogFragment dFragment = new DatePickerFragmentEndDate();
                // Show the date picker dialog fragment

                dFragment.show(getFragmentManager(), "Date Picker");


            }
        });
*/






        //goals_jsonobject = new JSONObject();

        if(str_projectstatus.equals("Draft"))
        {
            ll_gallery_images.setVisibility(View.VISIBLE);
            GetProjectDraftDetails_AsyncCallWS task = new GetProjectDraftDetails_AsyncCallWS(FinalProjectCompletion_Activity.this);
            task.execute();
        }
        else if(str_projectstatus.equals("Approved"))
        {
            ll_gallery_images.setVisibility(View.GONE);
           // CompletionGetApprovedProjectDetails
            CompletionGetApprovedProjectDetails_AsyncCallWS task = new CompletionGetApprovedProjectDetails_AsyncCallWS(FinalProjectCompletion_Activity.this);
            task.execute();
        }

        else if(str_projectstatus.equals("RequestForCompletion"))
        {
            ll_gallery_images.setVisibility(View.VISIBLE);
            btn_saveDraft.setVisibility(View.GONE);
            GetProjectDraftDetails_AsyncCallWS task = new GetProjectDraftDetails_AsyncCallWS(FinalProjectCompletion_Activity.this);
            task.execute();


            /*CompletionGetApprovedProjectDetails_AsyncCallWS task = new CompletionGetApprovedProjectDetails_AsyncCallWS(FinalProjectCompletion_Activity.this);
            task.execute();
*/

        }

        /*  int size=0;
			        size= GalleryImageBitmap.getAllImgURL().size();
			        Log.e("tag","size=="+size);
			        for(int k=0;k<size;k++) {*/
        //  }

        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        /*txt_noOfImg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                     //   private void showRadioButtonDialog() {

                            // custom dialog
                            dialog = new Dialog(FinalProjectCompletion_Activity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.radiobutton_dialog);
                            dialog.setCanceledOnTouchOutside(true);
                            List<String> stringList=new ArrayList<>();  // here is list
                        stringList.clear();
                            for(int i=0;i<urllist.size();i++) {
                                stringList.add("Image : " + (i + 1));
                            }
                        rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

                          //  rg.clearCheck();
                            for(int i=0;i<stringList.size();i++){
                                 rb=new RadioButton(FinalProjectCompletion_Activity.this); // dynamically creating RadioButton and adding to RadioGroup.

                                rb.setText(stringList.get(i));
                                rg.addView(rb);

                            }

                            dialog.show();

                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                int childCount = group.getChildCount();
                                Log.e("childCount->",String.valueOf(childCount));
                                ArrayList<Integer> btn_count=new ArrayList<>();
                                btn_count.clear();
                                for(int j=1;j<=childCount;j++) {
                                    btn_count.add(j);
                                    Log.e("tag","btn_count="+btn_count);
                                }
                                for (int x = 0; x < childCount; x++) {
                                    RadioButton btn = (RadioButton) group.getChildAt(x);
                                    if (btn.getId() == checkedId) {
                                        Log.e("selected RadioButton->",btn.getText().toString());
                                        Toast.makeText(FinalProjectCompletion_Activity.this, btn.getText().toString(), Toast.LENGTH_LONG).show();
                                        Log.e("tag","urllist.size()="+urllist.size()+"checkedId="+checkedId);
                                        int k = 0;
                                        for(int i=1;i<=urllist.size();i++){
//                                            Log.e("tag","I="+i+"checkedId="+checkedId+","+btn.getId()+btn_count.get(i));
                                            k=btn_count.get(i-1);
                                            Log.e("tag","I="+i+"k="+k);
                                                if (btn.getText().equals("Image : 1")) {
                                                    String ImgUrl = urllist.get(0).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(0));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }
                                                else if (btn.getText().equals("Image : 2")) {
                                                    String ImgUrl = urllist.get(1).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(1));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }
                                                else if (btn.getText().equals("Image : 3")) {
                                                    String ImgUrl = urllist.get(2).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(2));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }
                                                else if (btn.getText().equals("Image : 4")) {
                                                    String ImgUrl = urllist.get(3).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(3));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }
                                                else if (btn.getText().equals("Image : 5")) {
                                                    String ImgUrl = urllist.get(4).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(4));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }
                                                else if (btn.getText().equals("Image : 6")) {
                                                    String ImgUrl = urllist.get(5).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(5));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }
                                                else if (btn.getText().equals("Image : 7")) {
                                                    String ImgUrl = urllist.get(6).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(6));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }
                                                else if (btn.getText().equals("Image : 8")) {
                                                    String ImgUrl = urllist.get(7).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(7));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }
                                                else if (btn.getText().equals("Image : 9")) {
                                                    String ImgUrl = urllist.get(8).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(8));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }
                                                else if (btn.getText().equals("Image : 10")) {
                                                    String ImgUrl = urllist.get(9).toString();
                                                    Log.i("tag","ImgUrl="+urllist.get(9));
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                    startActivity(browserIntent);
                                                    Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                }

                                               *//* String ImgUrl = urllist.get(0).toString();
                                                Log.i("tag", "ImgUrl=" + urllist.get(0));
                                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(ImgUrl));
                                                startActivity(browserIntent);
                                                Toast.makeText(FinalProjectCompletion_Activity.this, checkedId + "," + btn.getId(), Toast.LENGTH_LONG).show();
                                                dialog.dismiss();
                                                rg.clearCheck();
                                                rg.removeAllViews();
                                                rg.removeAllViewsInLayout();*//*
                                         //   }

                                        }

                                    }
                                }
                            }
                        });

                    }
                });*/



        uploadReport_fpc_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent documentIntent = new Intent(getApplication(),CompletionDocumentFolderSelectActivity.class);
                    startActivityForResult(documentIntent,SELECT_FILE);

            }
        });

        btn_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(wordDocPath.equals("null")){
                    Toast.makeText(FinalProjectCompletion_Activity.this, "File Not Exist",
                            Toast.LENGTH_SHORT).show();
                }else{
                    LoadDocument loadDocument = new LoadDocument(getApplication());
                    loadDocument.execute();
                }
            }
        });


        btn_saveDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkSaveDraftFields())
                {
                    int count= getDeleted_ImgData_count();
                    if(count>0){
                        getDeleted_ImgData(count);
                        Async_DeleteImage async_deleteImage= new Async_DeleteImage(FinalProjectCompletion_Activity.this);
                        async_deleteImage.execute();
                    }



                    ArrayList<byte[]> byteArrayImage;
                    byteArrayImage = new ArrayList<byte[]>();
                    byteArrayImage.clear();

                    byte[] byteImage=null;

                    Log.e("tag","byteImage1="+byteImage);
                            byteImage   = CompletionProjectStaticClass.getBytesImage();
                    Log.e("tag","byteImage2="+byteImage);

                 /*   if(byteArrayImage!=null){
                        for(int i=0;i<byteArrayImage.size();i++) {
                            arrlstUploadImageByteArray.add(byteArrayImage.get(i));
                        }
                    }*/

                 Log.e("tag","mImageInByte=="+mImageInByte);


                    deleteimage_byposition(mImageInByte);
                    deletestatelistTable_B4insertion();





                    byteArrayImage.addAll(mImageInByte);
                    //  mImageInByte
                    /*if (byteImage != null) {
                        byteArrayImage.add(byteImage);
                    }*/
                    arrlstUploadImageByteArray.clear();
                    if (byteArrayImage != null) {
                        for (int i = 0; i < byteArrayImage.size(); i++)
                        {
                            arrlstUploadImageByteArray.add(byteArrayImage.get(i));


                        }

                        Log.e("B4r", String.valueOf(arrlstUploadImageByteArray.size()));
                    }


                    int comproress=0;
                    int imgs=imgurlss.size();

                    int saveDraftImgCount=urllist.size();
                    int complitionImgCount=imgurlss.size();

                  //  Toast.makeText(getApplicationContext(),"Size now")

                    int totalImgCount=saveDraftImgCount+complitionImgCount;
                    if (totalImgCount==0){
                        CompletionProgress1=CompletionProgress;
                    }
                    if(totalImgCount==1){
                       /// CompletionProgress1=CompletionProgress1+10;
                        comproress=10;
                    }
                    else if(totalImgCount==2){
                      //  CompletionProgress1=CompletionProgress1+20;
                        comproress=20;
                    }
                    else if(totalImgCount==3){
                      //  CompletionProgress1=CompletionProgress1+30;
                        comproress=30;
                    }
                    else if(totalImgCount>=4){
                      //  CompletionProgress1=CompletionProgress1+40;
                        comproress=40;
                    }

                    CompletionProgress1=CompletionProgress+comproress;

                    Log.e("tag", "CompletionProgress1==" + CompletionProgress1);
                    //arrlstUploadImageByteArray.addAll(byteArrayImage);
                    Log.e("tag", "byteArrayImage=" + byteArrayImage.size());
                    Log.e("tag", "arrlstUploadImageByteArray=" + arrlstUploadImageByteArray.size());
                    Log.e("tag", "arrlstUploadImageByteArray.toString=" + arrlstUploadImageByteArray.toString());

                    if (totalImgCount > 10)
                    {
                        btn_submitCompletion.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You can upload maximum of 10 images", Toast.LENGTH_LONG).show();
                    } else {
                       // if (imgurlss.size() >= 0) {

                        pd = new ProgressDialog(FinalProjectCompletion_Activity.this);
                        pd.setMax(100);
                        pd.setMessage("Loading...");
                        ///  pd.setTitle("ProgressDialog bar example");
                        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pd.setCanceledOnTouchOutside(false);
                        pd.show();

                        final Handler handle = new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                pd.incrementProgressBy(1);
                            }
                        };
                            Log.d("insidesizessssss", "CompletionProgress img="+CompletionProgress1);
                            btn_submitCompletion.setVisibility(View.VISIBLE);

                            Log.d("insidesizessssss", "greater than 1");
                            //Toast.makeText(getActivity(),"Inside size greater than 4",Toast.LENGTH_SHORT).show();
                            //pd.dismiss();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    while (pd.getProgress() <= pd.getMax()) {
                                        Thread.sleep(200);
                                        handle.sendMessage(handle.obtainMessage());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                            if (arrlstUploadImageByteArray != null)
                            {
                                UploadProjectImages_SaveDraft uploadProjectImages_saveDraft = new UploadProjectImages_SaveDraft(FinalProjectCompletion_Activity.this);
                                uploadProjectImages_saveDraft.execute();
                            }
                             if (docbyteArray != null) {
                                UploadProjectDocument_SaveDraft uploadProjectDocument_saveDraft = new UploadProjectDocument_SaveDraft(getApplication());
                                uploadProjectDocument_saveDraft.execute();
                            } else {
                                SubmitForCompletion_SaveDraft submitForCompletion_saveDraft = new SubmitForCompletion_SaveDraft(getApplicationContext());
                                submitForCompletion_saveDraft.execute();
                            }
                       /* } else {
                            //pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Please Upload atleast 1 images", Toast.LENGTH_LONG).show();
                            //btn_submitCompletion.setVisibility(View.VISIBLE);
                        }*/
                    }
                }
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
                    if (bitmapLst.size() > 0)
                    {

                        try {

                            Delete_AllImglistTable_B4Insertion();

                            GalleryImageBitmap.setBitmapGalleryImage(bitmapLst);

                            GalleryImageBitmap.setDocument_path(documentlist_path);
                            GalleryImageBitmap.setDocument_slno(documentlist_slno);

                            Intent i = new Intent(FinalProjectCompletion_Activity.this, ComplitionGallaryActivity.class);
                            i.putExtra("projectid_ValueKey", str_projectid);
                            i.putExtra("projectstatus_ValueKey", str_projectstatus);
                            i.putExtra("impact_projectstatus_ValueKey", str_impactproject_status.toString());
                            startActivity(i);

                            //progressDialog.dismiss();
                        }catch(final OutOfMemoryError ex){
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    //progressDialog.dismiss();
                                    Toast.makeText(getApplication(),"Slow Internet or Internet Dropped"+ex.toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    } else {

                    }
                }catch(final OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //progressDialog.dismiss();
                            Toast.makeText(getApplication(),"Slow Internet or Internet Dropped"+ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        btn_submitCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"You clicked on submit please wait",Toast.LENGTH_SHORT).show();

           /*     pd = new ProgressDialog(context);
                pd.setMessage("Submitting please wait");
                pd.show();*/


                if (checkMandatory())
                {

                /*if(1>2)
                {*/
                    btn_submitCompletion.setVisibility(View.GONE);
                    btn_saveDraft.setVisibility(View.GONE);

                    /*    imgurlss.clear();
                          imgurlss.addAll(imageAdapter.getImageList());*/

                    //Log.d("imgurlsssizeisss", String.valueOf(imageAdapter.getImageList().size()));

                  /*  for(String imgUrl : imgurlss)
                    {
                        Log.d("imgUrlissssss",imgUrl);
                        Bitmap bitmap;
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

                        if(imgUrl.startsWith("content")){
                            bitmap = cameraImgMap.get(imgUrl);
                        }else {
                            bitmap = BitmapFactory.decodeFile(imgUrl, bmOptions);
                        }

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imageInByte;
                        imageInByte = stream.toByteArray();
*/
                    //String encodedImageString = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                    //Log.d("encodeImageStringssssss",encodedImageString);

                      /*  if(!imgUrl.startsWith("content")) {
                            String where = MediaStore.MediaColumns.DATA + "=?";
                            String[] selectionArgs = new String[]{imgUrl};
                            ContentResolver contentResolver = context.getContentResolver();
                            Uri filesUri = MediaStore.Files.getContentUri("external");

                            int deleted = contentResolver.delete(filesUri, where, selectionArgs);
                            Log.d("deltedCountcontentissss", String.valueOf(deleted));
                        }else{
                            ContentResolver contentResolver = context.getContentResolver();
                            Uri filesUri = Uri.parse(imgUrl);
                            int deleted = contentResolver.delete(filesUri, null, null);
                            Log.d("deletedCountCameraissss", String.valueOf(deleted));
                        }*/

                    //arrlstUploadedImageBitmap.add(encodedImageString);

                       /* arrlstUploadImageByteArray.add(imageInByte);
                    }*/

                    //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
                    //imageView.setImageBitmap(bitmap);

                    //  ArrayList<byte[]> byteArrayImage = CompletionProjectStaticClass.getByteArrayImage();

                    //--------server img delete added by madhu 7/9/19
                   /* int count= getDeleted_ImgData_count();
                    if(count>0){
                        getDeleted_ImgData(count);
                        Async_DeleteImage async_deleteImage= new Async_DeleteImage(FinalProjectCompletion_Activity.this);
                        async_deleteImage.execute();
                    }*/
                    //----------------------------------------------------

                    ArrayList<byte[]> byteArrayImage;
                    byteArrayImage = new ArrayList<>();

                    byte[] byteImage = CompletionProjectStaticClass.getBytesImage();


                 /*   if(byteArrayImage!=null){
                        for(int i=0;i<byteArrayImage.size();i++) {
                            arrlstUploadImageByteArray.add(byteArrayImage.get(i));
                        }
                    }*/

                    deleteimage_byposition(mImageInByte);
                    deletestatelistTable_B4insertion();

                    //bvijay
                    byteArrayImage.addAll(mImageInByte);
                    //  mImageInByte
                    /*if(byteImage != null){
                        byteArrayImage.add(byteImage);
                    }*/
                    arrlstUploadImageByteArray.clear();
                    if (byteArrayImage != null) {
                        for (int i = 0; i < byteArrayImage.size(); i++) {
                            arrlstUploadImageByteArray.add(byteArrayImage.get(i));
                        }
                    }
                    //arrlstUploadImageByteArray.addAll(byteArrayImage);
                    Log.e("tag", "byteArrayImage=" + byteArrayImage.size());
                    Log.e("tag", "arrlstUploadImageByteArray=" + arrlstUploadImageByteArray.size());
                    Log.e("tag", "arrlstUploadImageByteArray.toString=" + arrlstUploadImageByteArray.toString());
                    int saveDraftImgCount = urllist.size();
                    int complitionImgCount = imgurlss.size();

                    int count= getDeleted_ImgData_count();
                    int deletedCount = 0,totalImgCount=0,uploadImgs=0;

                    uploadImgs=saveDraftImgCount+complitionImgCount;
                    Log.e("tag","uploadImgs="+uploadImgs);
                    if(uploadImgs>count){
                        deletedCount = uploadImgs - count;
                        Log.e("tag", " deletedCount =uploadImgs-count==" + String.valueOf(deletedCount) + "=" + String.valueOf(uploadImgs) + "-" + String.valueOf(count));
                    }else{
                        deletedCount = count - saveDraftImgCount;
                        Log.e("tag", " deletedCount =count-uploadImgs==" + String.valueOf(deletedCount) + "=" + String.valueOf(count) + "-" + String.valueOf(uploadImgs));
                    }
                    if(uploadImgs>=4){
                        totalImgCount=uploadImgs;
                    }else if(uploadImgs==0){
                        Toast.makeText(getApplicationContext(), "Please Upload atleast 4 images", Toast.LENGTH_LONG).show();
                    }/*else{
                        totalImgCount = deletedCount + uploadImgs;
                        Log.e("tag", "totalImgCount = deletedCount + uploadImgs==" + String.valueOf(totalImgCount) + "=" + String.valueOf(deletedCount) + "+" + String.valueOf(uploadImgs));

                    }*/
                   /* if(count>4) {
                        if (saveDraftImgCount > count) {
                            deletedCount = saveDraftImgCount - count;
                            Log.e("tag", " deletedCount =saveDraftImgCount-count==" + String.valueOf(deletedCount) + "=" + String.valueOf(saveDraftImgCount) + "-" + String.valueOf(count));
                        } else {
                            deletedCount = count - saveDraftImgCount;
                            Log.e("tag", " deletedCount =count-saveDraftImgCount==" + String.valueOf(deletedCount) + "=" + String.valueOf(count) + "-" + String.valueOf(saveDraftImgCount));

                        }
                    }



                    int totalImgCount = 0;
                    if(saveDraftImgCount>=4){
                         totalImgCount = saveDraftImgCount + complitionImgCount;
                        Log.e("tag", "saveDraftImgCount gr8 totalImgCount = saveDraftImgCount + complitionImgCount==" +  String.valueOf(totalImgCount) +"="+String.valueOf(saveDraftImgCount)+"-"+String.valueOf(complitionImgCount));

                    }else {
                        if(saveDraftImgCount==0&&complitionImgCount==0){
                            Toast.makeText(getApplicationContext(), "Please Upload atleast 4 images", Toast.LENGTH_LONG).show();
                        }else if(saveDraftImgCount==0&&complitionImgCount>=4) {
                            totalImgCount = saveDraftImgCount + complitionImgCount;
                            Log.e("tag", "complitionImgCount gr4 totalImgCount = saveDraftImgCount + complitionImgCount==" + String.valueOf(totalImgCount) + "=" + String.valueOf(saveDraftImgCount) + "-" + String.valueOf(complitionImgCount));
                        }else{
                            totalImgCount = deletedCount + complitionImgCount;
                            Log.e("tag", "totalImgCount = deletedCount + complitionImgCount==" + String.valueOf(totalImgCount) + "=" + String.valueOf(deletedCount) + "+" + String.valueOf(complitionImgCount));
                        }
                    }*/
                    Log.e("tag", "Deleted_ImgData_count=" + String.valueOf(count));
                    Log.e("tag", "saveDraftImgCount=" + String.valueOf(saveDraftImgCount));
                    Log.e("tag", "deletedCount=" + String.valueOf(deletedCount));
                    Log.e("tag", "complitionImgCount=" + String.valueOf(complitionImgCount));
                    Log.e("tag", "totalImgCount=" + String.valueOf(totalImgCount));

                    /*if(totalImgCount>4){
                        getDeleted_ImgData(count);
                        Async_DeleteImage async_deleteImage= new Async_DeleteImage(FinalProjectCompletion_Activity.this);
                        async_deleteImage.execute();
                    }*/

                    if (totalImgCount > 10) {
                        /*btn_submitCompletion.setVisibility(View.VISIBLE);
                        btn_saveDraft.setVisibility(View.VISIBLE);*/

                        if(str_projectstatus.equals("RequestForCompletion"))        // changes by 7/9/19
                        {
                            btn_submitCompletion.setVisibility(View.VISIBLE);
                            btn_saveDraft.setVisibility(View.GONE);
                        }else{
                            btn_submitCompletion.setVisibility(View.VISIBLE);
                            btn_saveDraft.setVisibility(View.VISIBLE);
                        }




                        Toast.makeText(getApplicationContext(), "You can upload maximum of 10 images", Toast.LENGTH_LONG).show();
                        arrlstUploadImageByteArray.clear();
                    } else {
                        if (totalImgCount >= 4) {
                            if(count>0) {
                                getDeleted_ImgData(count);
                                Async_DeleteImage async_deleteImage = new Async_DeleteImage(FinalProjectCompletion_Activity.this);
                                async_deleteImage.execute();
                            }
                            pd = new ProgressDialog(FinalProjectCompletion_Activity.this);
                            pd.setMax(100);
                            pd.setMessage("Loading...");
                            ///  pd.setTitle("ProgressDialog bar example");
                            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                            pd.setCanceledOnTouchOutside(false);
                            pd.show();

                            final Handler handle = new Handler() {
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    pd.incrementProgressBy(1);
                                }
                            };
                            // if (urllist.size() >= 4) {
                            //   btn_submitCompletion.setVisibility(View.VISIBLE);
                            Log.d("insidesizessssss", "greater than 4");
                            //Toast.makeText(getActivity(),"Inside size greater than 4",Toast.LENGTH_SHORT).show();
                            //pd.dismiss();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        while (pd.getProgress() <= pd.getMax()) {
                                            Thread.sleep(200);
                                            handle.sendMessage(handle.obtainMessage());
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                            if (arrlstUploadImageByteArray.size() == 0)
                            {
                                if (docbyteArray != null)
                                {
                                    UploadProjectDocument uploadProjectDocument = new UploadProjectDocument(getApplication());
                                    uploadProjectDocument.execute();
                                } else
                                    {
                                    /*pd.setProgress(100);
                                    if (pd.getProgress() == pd.getMax()) {
                                        pd.dismiss();
                                    }*/
                                    SubmitForCompletion submitForCompletion = new SubmitForCompletion(getApplicationContext());
                                    submitForCompletion.execute();
                                }
                            } else {

                                UploadProjectImages uploadProjectImages = new UploadProjectImages(FinalProjectCompletion_Activity.this);
                                uploadProjectImages.execute();


                            }



                           /* } else {
                                //pd.dismiss();
                                Toast.makeText(getApplicationContext(), "Please Upload atleast 4 images", Toast.LENGTH_LONG).show();
                                //btn_submitCompletion.setVisibility(View.VISIBLE);
                            }*/
                        } else {
                            //pd.dismiss();
                            Toast.makeText(getApplicationContext(), "Please Upload atleast 4 images", Toast.LENGTH_LONG).show();
                            /*btn_submitCompletion.setVisibility(View.VISIBLE);
                            btn_saveDraft.setVisibility(View.VISIBLE);*/

                            if(str_projectstatus.equals("RequestForCompletion"))
                            {
                                btn_submitCompletion.setVisibility(View.VISIBLE);
                                btn_saveDraft.setVisibility(View.GONE);
                            }
                            else{
                                btn_submitCompletion.setVisibility(View.VISIBLE);
                                btn_saveDraft.setVisibility(View.VISIBLE);
                            }

                        }
                    }
                //}

            }

            }
        });










                updatespinner();
        sdgs_sp.setVisibility(View.GONE);
                sdgs_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
                    {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });






        sdgs_dropdownvalue_tv.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.sdgs_alert_layout, null);


                goalslist_linearlayout=(LinearLayout)alertLayout.findViewById(R.id.goalslist_linearlayout);
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





                AlertDialog.Builder alert = new AlertDialog.Builder(FinalProjectCompletion_Activity.this);
                // this is set the view from XML inside AlertDialog
                alert.setView(alertLayout);
                // disallow cancel of AlertDialog on click of back button and outside touch
                alert.setCancelable(false);

                dialog = alert.create();


                // vijay goals


                // for none count is zero
                if(sdgs_display_tv.getText().toString().trim().length()>0)
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



//last end goals
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

                        sdgs_display_tv.setText("");

                        dialog.dismiss();
                    }
                });


                dialog.show();


            }
        });




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



        permissionactivities_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               // permissionactivities_wordscount_tv.setText(80 - s.toString().length() + "/80");
                permissionactivities_wordscount_tv.setText("Min 100 Words: "+(100 - s.toString().trim().length()) + "/100");
                //Min 100 Words

            }
        });






    }// end of oncreate();

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

            AlertDialog.Builder adb = new AlertDialog.Builder(FinalProjectCompletion_Activity.this);
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
            yyyyMMdd_studentcomp_startdate = mdyFormat.format(calendar.getTime());


            studentcomp_clickstartprojectdate_tv.setText(mdyFormat.format(calendar.getTime()));
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

            yyyyMMdd_studentcomp_enddate = mdyFormat.format(calendar.getTime());


            studentcomp_clickendprojectdate_tv.setText(mdyFormat.format(calendar.getTime()));

            //  System.out.println("To Date:"+ yyyyMMdd_todate);

            /*int days_count = Days.daysBetween(new LocalDate(yyyyMMdd_pmstartdate), new LocalDate(yyyyMMdd_pmenddate)).getDays();
            int int_days=days_count+1;
            System.out.println ("Days: "+int_days);

            if((int_days<=6))
            {
            }
            else{
                //alerts_dialog_fortodate();
            }
*/


        }

    }


//----------------------------date------------------------------------





    //----------------------------------Delete image in sarver added by madhu 7/9/19------------------------
    //for server image, delete image list
    public int getDeleted_ImgData_count() {

        db1 = this.openOrCreateDatabase("Leaddb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS AllImageList(img BLOB NOT NULL,documentlist_path VARCHAR,documentlist_slno VARCHAR,position VARCHAR);");

        cursor1 = db1.rawQuery("SELECT * FROM AllImageList", null);
        //  db1.close();
        int x = cursor1.getCount();
        Log.d("tag","cursor count delete img="+ Integer.toString(x));
        return x;

    }
    public void getDeleted_ImgData(int x){
        int i=0;
        Delete_doclist_slno.clear();
        Delete_doclist_path.clear();
        if(x>0) {
            if (cursor1.moveToFirst()) {
                do {

                    Delete_doclist_path.add(cursor1.getString(cursor1.getColumnIndex("documentlist_path")));
                    Delete_doclist_slno.add(cursor1.getString(cursor1.getColumnIndex("documentlist_slno")));

                } while (cursor1.moveToNext());
            }//if ends

        }

        db1.close();

    }
    public void Delete_AllImglistTable_B4Insertion() {

        SQLiteDatabase db6 = openOrCreateDatabase("Leaddb", Context.MODE_PRIVATE, null);

        db6.execSQL("CREATE TABLE IF NOT EXISTS AllImageList(img BLOB NOT NULL,documentlist_path VARCHAR,documentlist_slno VARCHAR,position VARCHAR);");
        Cursor cursor1 = db6.rawQuery("SELECT * FROM AllImageList", null);
        int x = cursor1.getCount();
        if (x > 0) {
            db6.delete("AllImageList", null, null);
        }
        db6.close();
    }
    //------------------------------------------------------------------------------------------------















//database for image
    public void deleteimage_byposition(ArrayList<byte[]> mImageInByte)
    {

        SQLiteDatabase db1 = this.openOrCreateDatabase("Leaddb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS ImageDeletedlist(PID VARCHAR);");

        Cursor cursor1 = db1.rawQuery("SELECT * FROM ImageDeletedlist", null);
        int x = cursor1.getCount();
        Log.d("cursor count", Integer.toString(x));

        int i=0;

        if(x>0)
        {
            try {
                if (cursor1.moveToFirst()) {
                    do {

                        i = Integer.parseInt(cursor1.getString(cursor1.getColumnIndex("PID")));
                        mImageInByte.remove(i);

                    } while (cursor1.moveToNext());
                }//if ends

            }
            catch(Exception ex)
            {
                db1.close();
            }

        }

        db1.close();

    }


    public void deletestatelistTable_B4insertion() {

        SQLiteDatabase db6 = openOrCreateDatabase("Leaddb", Context.MODE_PRIVATE, null);

        db6.execSQL("CREATE TABLE IF NOT EXISTS ImageDeletedlist(PID VARCHAR);");
        Cursor cursor1 = db6.rawQuery("SELECT * FROM ImageDeletedlist", null);
        int x = cursor1.getCount();
        if (x > 0) {
            db6.delete("ImageDeletedlist", null, null);
        }
        db6.close();
    }


//database for image




    private boolean checkSaveDraftFields()
    {

        String str_challanges = challanges_fpc_et.getText().toString();
        String str_learning = learning_fpc_et.getText().toString();
        String str_story = story_fpc_et.getText().toString();
        String str_place = placeofimpl_fpc_et.getText().toString();
        String str_fundRaised = edt_fundRaised.getText().toString();
        String str_Resource = resourcesUtilised_fpc_et.getText().toString();
        String str_hoursspent = edt_hoursspent.getText().toString();
        String str_resource_amount= resourcesUtilised_amount_fpc_et.getText().toString();

        if(str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() &&str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 0;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }

        if(!str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() &&!str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 60;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }

        if(!str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 10;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 10;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 10;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 10;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 10;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 10;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }

        if(!str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 20;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }

        if(!str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 30;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 40;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 40;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 40;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 40;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 40;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 40;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 40;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 40;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 40;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() && str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 50;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 50;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && str_story.isEmpty() && !str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 50;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && !str_challanges.isEmpty() && str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 50;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(!str_place.isEmpty() && str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 50;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        if(str_place.isEmpty() && !str_challanges.isEmpty() && !str_learning.isEmpty() && !str_story.isEmpty() && !str_fundRaised.isEmpty() && !str_Resource.isEmpty()){
            //pd.dismiss();
            CompletionProgress = 50;
            Log.e("tag","CompletionProgress="+CompletionProgress);
        }
        return true;
    }
    private boolean checkMandatory() {

        String str_challanges = challanges_fpc_et.getText().toString().trim();
        String str_learning = learning_fpc_et.getText().toString();
        String str_story = story_fpc_et.getText().toString();
        String str_place = placeofimpl_fpc_et.getText().toString();
        String str_hoursSpent = edt_hoursspent.getText().toString().trim();
        String str_sdgs_validation=sdgs_display_tv.getText().toString().trim();
        String str_resource_amount=resourcesUtilised_amount_fpc_et.getText().toString().trim();
        String str_resouce_utilized=resourcesUtilised_fpc_et.getText().toString().trim();


        if(str_place.isEmpty())
        {
            //pd.dismiss();
            placeofimpl_fpc_et.setError("Enter the place of implementation");
            placeofimpl_fpc_et.requestFocus();
            return false;
        }

        if(str_challanges.isEmpty()){
            //pd.dismiss();
            challanges_fpc_et.setError("Enter the challenges");
            challanges_fpc_et.requestFocus();
            return false;
        }

        if(str_challanges.length()<9){

            challanges_fpc_et.setError("Minimum 10 characters");
            challanges_fpc_et.requestFocus();
            return false;
        }

        if(str_learning.isEmpty()){
            //pd.dismiss();
            learning_fpc_et.setError("Enter the learnings");
            learning_fpc_et.requestFocus();
            return false;
        }
        if(str_learning.length()<20){
            learning_fpc_et.setError("Minimum 20 characters");
            learning_fpc_et.requestFocus();
            return false;
        }

        if(str_story.isEmpty()){

            story_fpc_et.setError("Enter the story");
            story_fpc_et.requestFocus();
            return false;
        }



        if(resourcesUtilised_fpc_et.getText().toString().trim().length()==0)
        {
            resourcesUtilised_fpc_et.setError("minimum 5 character");
            resourcesUtilised_fpc_et.requestFocus();
            return false;
        }



        if(str_resource_amount.isEmpty()){

            resourcesUtilised_amount_fpc_et.setError("Enter the Amount");
            resourcesUtilised_amount_fpc_et.requestFocus();
            return false;
        }

        if(resourcesUtilised_amount_fpc_et.getText().toString().trim().length()==0){

            resourcesUtilised_amount_fpc_et.setError("Enter the Amount");
            resourcesUtilised_amount_fpc_et.requestFocus();
            return false;
        }

       /* if(str_resource_amount.equalsIgnoreCase("0"))
        {
            resourcesUtilised_amount_fpc_et.setError("Enter the Amount");
            resourcesUtilised_amount_fpc_et.requestFocus();
            return false;
        }*/


        if(str_hoursSpent.isEmpty()){

            edt_hoursspent.setError("Enter the SpentHours");
            edt_hoursspent.requestFocus();
            return false;
        }


        if(edt_hoursspent.getText().toString().trim().length()==0){

            edt_hoursspent.setError("Enter the SpentHours");
            edt_hoursspent.requestFocus();
            return false;
        }




        if(str_hoursSpent.equalsIgnoreCase("0"))
        {
            edt_hoursspent.setError("Enter the SpentHours");
            edt_hoursspent.requestFocus();
            return false;
        }


        if(sdgs_display_tv.getText().toString().trim().length()==0)
        {
            sdgs_display_tv.setError("Select the goals");
            Toast.makeText(getApplicationContext(),"Select the goals",Toast.LENGTH_SHORT).show();
            sdgs_display_tv.requestFocus();
            return false;
        }




        if(str_impactproject_status.equals("1"))
        {


            if(str_validation_collabaration.equalsIgnoreCase("yes"))
            {

                if(impactproject_collabaration_et.getText().toString().trim().length()==0)
                {
                    impactproject_collabaration_et.setError("Enter collabaration");
                    impactproject_collabaration_et.requestFocus();
                    return false;
                }
                if(impactproject_collabaration_et.getText().toString().trim().length()<19)
                {
                    impactproject_collabaration_et.setError("Minimum 20 character");
                    impactproject_collabaration_et.requestFocus();
                    return false;
                }
            }



            if(str_validation_againtstide.equalsIgnoreCase("yes"))
            {
                if(impactproject_againtstide_et.getText().toString().trim().length()==0)
                {
                    impactproject_againtstide_et.setError("Empty not allowed");
                    impactproject_againtstide_et.requestFocus();
                    return false;
                }
                if(impactproject_againtstide_et.getText().toString().trim().length()<19)
                {
                    impactproject_againtstide_et.setError("Minimum 20 character");
                    impactproject_againtstide_et.requestFocus();
                    return false;
                }
            }


            if(str_validation_crosshurdles.equalsIgnoreCase("yes"))
            {
                if(impactproject_crosshurdles_et.getText().toString().trim().length()==0)
                {
                    impactproject_crosshurdles_et.setError("Empty not allowed");
                    impactproject_crosshurdles_et.requestFocus();
                    return false;
                }
                if(impactproject_crosshurdles_et.getText().toString().trim().length()<19)
                {
                    impactproject_crosshurdles_et.setError("Minimum 20 character");
                    impactproject_crosshurdles_et.requestFocus();
                    return false;
                }
            }


            if(str_validation_venture.equalsIgnoreCase("yes"))
            {
                if(impactproject_venture_et.getText().toString().trim().length()==0)
                {
                    impactproject_venture_et.setError("Empty not allowed");
                    impactproject_venture_et.requestFocus();
                    return false;
                }
                if(impactproject_venture_et.getText().toString().trim().length()<19)
                {
                    impactproject_venture_et.setError("Minimum 20 character");
                    impactproject_venture_et.requestFocus();
                    return false;
                }
            }


            if(str_validation_governaward.equalsIgnoreCase("yes"))
            {
                if(impactproject_governaward_et.getText().toString().trim().length()==0)
                {
                    impactproject_governaward_et.setError("Empty not allowed");
                    impactproject_governaward_et.requestFocus();
                    return false;
                }
                if(impactproject_governaward_et.getText().toString().trim().length()<19)
                {
                    impactproject_governaward_et.setError("Minimum 20 character");
                    impactproject_governaward_et.requestFocus();
                    return false;
                }
            }




            if(str_validation_leadershiprole.equalsIgnoreCase("yes"))
            {
                if(impactproject_leadershiprole_et.getText().toString().trim().length()==0)
                {
                    impactproject_leadershiprole_et.setError("Empty not allowed");
                    impactproject_leadershiprole_et.requestFocus();
                    return false;
                }
                if(impactproject_leadershiprole_et.getText().toString().trim().length()<19)
                {
                    impactproject_leadershiprole_et.setError("Minimum 20 character");
                    impactproject_leadershiprole_et.requestFocus();
                    return false;
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


        if(str_story.length()<100){
            story_fpc_et.setError("Minimum 100 characters");
            story_fpc_et.requestFocus();
            return false;
        }
        else{
            return true;
        }


    }

    public class UploadProjectImages_SaveDraft extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        /*private ProgressBar progressBar;*/

        UploadProjectImages_SaveDraft (FinalProjectCompletion_Activity activity)
        {
            context = activity;
          /*  pd = new ProgressDialog(activity);
            //     pd = new ProgressDialog(getApplication());
            pd.setMessage("Submitting your request please wait. If you press back button your request will be uploaded in the backend");
            pd.setCanceledOnTouchOutside(false);
            pd.show();*/
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            SoapPrimitive response =null;

           /* for(int k=0;k<arrlstUploadedImageBitmap.size();k++) {
                response = uploadProjectImage(arrlstUploadedImageBitmap.get(k),k+1);
                if(k==arrlstUploadedImageBitmap.size()-1){
                    return response;
                }
            }*/


            Log.e("After", String.valueOf(arrlstUploadImageByteArray.size()));
            for(int k=0;k<arrlstUploadImageByteArray.size();k++)
            {



                response = UploadProjectImages_SaveDraft(arrlstUploadImageByteArray.get(k),k+1);

                if(k==arrlstUploadImageByteArray.size()-1)
                {
                    return response;
                }
            }


            //Log.d("Soap response is",response.toString());
            return null;

        }

        @Override
        protected void onPreExecute() {
   /*         progressBar = (ProgressBar) view4.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {

            if(result != null) {
                if (result.toString().equalsIgnoreCase("success")) {

           /*     for(String imgePath : imageAdapter.getImageList()) {
                    final String where = MediaStore.MediaColumns.DATA + "=?";
                    final String[] selectionArgs = new String[]{imgePath};
                    final ContentResolver contentResolver = context.getContentResolver();
                    final Uri filesUri = MediaStore.Files.getContentUri("external");

                    contentResolver.delete(filesUri, where, selectionArgs);
                }*/

                    //imgurlss.clear();
                    imageUrls.clear();
                    arrlstUploadedImageBitmap.clear();

                    pd.setProgress(100);
                    if (pd.getProgress() == pd.getMax()) {
                        pd.dismiss();
                    }
                   /* if (docbyteArray != null) {
                        UploadProjectDocument_SaveDraft uploadProjectDocument_saveDraft = new UploadProjectDocument_SaveDraft(getApplication());
                        uploadProjectDocument_saveDraft.execute();
                    } else {
                        SubmitForCompletion_SaveDraft submitForCompletion_saveDraft = new SubmitForCompletion_SaveDraft(getApplicationContext());
                        submitForCompletion_saveDraft.execute();
                    }*/
                } else {
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                    pd.dismiss();
                    //progressDialog.dismiss();
                }
            }else{
                //Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive UploadProjectImages_SaveDraft(byte[] uploadImgString,int count) {
        String METHOD_NAME = "UpdateProjectCompletionImg1";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletionImg1";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","uploadProjectImage");

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",str_leadId);
            request.addProperty("RegistrationId",str_RegistrationId);

            //  Log.d("Projectssssssss", String.valueOf(mapProjectIdProject.get(selectedProject)));
            request.addProperty("ProjectId",str_projectid);

            request.addProperty("ProfileImage",uploadImgString);
            request.addProperty("doccount",count);



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);


            envelope.dotNet = true;

            Log.d("Requestlogcat",request.toString());

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //Log.d("soaprespUploadPrjctImg",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soaprespUploadPrjctImg",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();

                }
            });
           /* getActivity().finish();*/
        }
        return null;
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
                        PMComplitionProjectActivity.FileOpen.openFile(getApplicationContext(), outputFile);
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

    public class UploadProjectDocument_SaveDraft extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        UploadProjectDocument_SaveDraft (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            SoapPrimitive response =null;

            response = UploadProjectDocument_SaveDraft();

            //Log.d("Soap response is",response.toString());
            return response;

        }

        @Override
        protected void onPreExecute() {
   /*         progressBar = (ProgressBar) view4.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
         /*    Toast.makeText(getActivity(),result.toString(),Toast.LENGTH_LONG).show();
             progressDialog.dismiss();*/


            if(result != null) {
                if (result.toString().equalsIgnoreCase("success")) {
                    //Log.d("FinalSoapResult",result.toString());
/*                LoadApprovedProject loadApprovedProject = new LoadApprovedProject(getActivity());
                loadApprovedProject.execute();*/

                    SubmitForCompletion_SaveDraft submitForCompletion_saveDraft = new SubmitForCompletion_SaveDraft(getApplication());
                    submitForCompletion_saveDraft.execute();
                } else {
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                    //   progressDialog.dismiss();
                }

                Log.d("Resultisssss", result.toString());
            }else{
                //progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive UploadProjectDocument_SaveDraft() {
        String METHOD_NAME = "UpdateProjectCompletionDocumentDraft";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletionDocumentDraft";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","UpdateProjectCompletionDocumentDraft");

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",str_leadId);
            request.addProperty("RegistrationId",str_RegistrationId);

            //  Log.d("Projectssssssss", String.valueOf(mapProjectIdProject.get(selectedProject)));
            request.addProperty("ProjectId",str_projectid);

            request.addProperty("docFile",docbyteArray);
            request.addProperty("extension",extensions);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //Log.d("soaprespUpldPrjctDoc",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soaprespUpldPrjctDoc",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }

    public class SubmitForCompletion_SaveDraft extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        SubmitForCompletion_SaveDraft (Context ctx){
            context = ctx;
        }

        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            SoapPrimitive response = SubmitForCompletion_SaveDraft();
            return response;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
         //   pd.dismiss();
            //progressDialog.dismiss();
            if(result!=null) {
                if (result.toString().equalsIgnoreCase("success")) //success
                {
                    //pd.dismiss();
                    pd.setProgress(100);
                    if (pd.getProgress() == pd.getMax()) {
                        pd.dismiss();
                    }
                    Toast.makeText(getApplicationContext(),"Data saved successfully",Toast.LENGTH_LONG).show();

                    Intent ittEditProjToProjStatus = new Intent(FinalProjectCompletion_Activity.this, ProjectDetails.class);
                    startActivity(ittEditProjToProjStatus);
                    finish();
                    /*FragmentTransaction fragmenttransaction = getSupportFragmentManager().beginTransaction();
                    HomeFragment regcomplainfragment = new HomeFragment();
                    fragmenttransaction.replace(R.id.content_frame, regcomplainfragment).addToBackStack("HomeFragment");
                    fragmenttransaction.commit();*/
                }
            }else{
                //getActivity().finish();
                Toast.makeText(getApplicationContext(),"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive SubmitForCompletion_SaveDraft() {
        String METHOD_NAME = "UpdateProjectCompletionsDraft";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletionsDraft";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","UpdateProjectCompletionsDraft");

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",str_leadId);

            if(projectname_fpc_tv.getText()!=null) {
                String str_projectname = projectname_fpc_tv.getText().toString();
                if (!str_projectname.isEmpty() && !str_projectname.equals("") && !str_projectname.equals(null)) {
                    request.addProperty("Title", str_projectname);
                } else {
                    request.addProperty("Title", "");
                }
            }else{
                request.addProperty("Title", "");
            }

          //  request.addProperty("RegistrationId",str_RegistrationId);
            request.addProperty("ProjectId",str_projectid);

            if(edt_fundRaised != null){
                int length = edt_fundRaised.getText().length();
                Log.d("Lengthisss", String.valueOf(length));
                if(length > 0) {
                    Log.d("beforelengthgreaterthan","zero");
                    String str_fundRaised = edt_fundRaised.getText().toString();
                    Log.d("str_fundraisedafter", str_fundRaised);

                    if (!str_fundRaised.equals(null) && !str_fundRaised.isEmpty() && !str_fundRaised.equals("")) {
                        request.addProperty("FundsRaised", str_fundRaised);
                    } else {
                        request.addProperty("FundsRaised", "0");
                    }
                }else{
                    request.addProperty("FundsRaised", "0");
                }
            }else{
                request.addProperty("FundsRaised", "0");
            }


            if(challanges_fpc_et.getText()!=null) {
                String str_Challanges = challanges_fpc_et.getText().toString();
                if (!str_Challanges.isEmpty() && !str_Challanges.equals("") && !str_Challanges.equals(null)) {
                    request.addProperty("Challenge", str_Challanges);
                } else {
                    request.addProperty("Challenge", "");
                }
            }else{
                request.addProperty("Challenge", "");
            }

            if(learning_fpc_et.getText()!=null) {
                String str_learning = learning_fpc_et.getText().toString();
                if (!str_learning.isEmpty() && !str_learning.equals("") && !str_learning.equals(null)) {
                    request.addProperty("Learning", str_learning);
                } else {
                    request.addProperty("Learning", "");
                }
            }else{
                request.addProperty("Learning", "");
            }

            if(story_fpc_et.getText()!=null) {
                String str_story = story_fpc_et.getText().toString();
                if (!str_story.isEmpty() && !str_story.equals("") && !str_story.equals(null)) {
                    request.addProperty("AsAStory", str_story);
                } else {
                    request.addProperty("AsAStory", "");
                }
            }else{
                request.addProperty("AsAStory", "");
            }

            if(resourcesUtilised_fpc_et.getText() != null){
                String str_resourceUtilised = resourcesUtilised_fpc_et.getText().toString();
                Log.d("StringResourcesUtilised",str_resourceUtilised);

                if (!str_resourceUtilised.isEmpty() && !str_resourceUtilised.equals("") && !str_resourceUtilised.equals(null)) {
                    request.addProperty("Resource", str_resourceUtilised);
                } else {
                    request.addProperty("Resource", "");
                }
            }

            request.addProperty("CompletionProgress",CompletionProgress1);

            if(placeofimpl_fpc_et.getText()!=null) {
                String str_placeofImp = placeofimpl_fpc_et.getText().toString();
                if (!str_placeofImp.isEmpty() && !str_placeofImp.equals("") && !str_placeofImp.equals(null)) {
                    request.addProperty("Placeofimplement", str_placeofImp);
                } else {
                    request.addProperty("Placeofimplement", "");
                }
            }else{
                request.addProperty("Placeofimplement", "");
            }


            String str_resource_amount= resourcesUtilised_amount_fpc_et.getText().toString();
            if(str_resource_amount.isEmpty()||str_resource_amount.equals("")||str_resource_amount.equals(null)
            ||str_resource_amount.equals("0"))
            {
                request.addProperty("TotalResources",0);//<TotalResources>long</TotalResources>
            }
            else{
                int int_resource_amt= Integer.parseInt(resourcesUtilised_amount_fpc_et.getText().toString());
                if(int_resource_amt>0) {
                    long long_resource_amount = Long.parseLong(str_resource_amount);
                    request.addProperty("TotalResources", long_resource_amount);//<TotalResources>long</TotalResources>
                }
                else{
                    request.addProperty("TotalResources",0);//<TotalResources>long</TotalResources>
                }
            }


            String str_hoursspent = edt_hoursspent.getText().toString();

            if(!str_hoursspent.isEmpty() && !str_hoursspent.equals("") && !str_hoursspent.equals(null)){
                request.addProperty("HoursSpend",str_hoursspent);
                Log.d("str_hoursspent", str_hoursspent);
            }else{
                request.addProperty("HoursSpend",0);
                //Zero added

            }


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

            Log.e("tag","CompletionProgress="+CompletionProgress1);
            Log.e("Request savedraft",request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //new MarshalBase64().register(envelope);
            //new Marshal.register(envelope);

            envelope.dotNet = true;




            //Set output SOAP object
            envelope.setOutputSoapObject(request);



            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.e("savedraft request",envelope.getResponse().toString());

                //Log.d("Requestsssss",request.toString());


                //SoapObject response = (SoapObject) envelope.getResponse();

                //return null;
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soaprespsubmitComplnPrj",response.toString());
                //Log.e("savedraft response",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplication(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplication(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplication(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplication(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

    public class UploadProjectImages extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        /*private ProgressBar progressBar;*/

        UploadProjectImages (FinalProjectCompletion_Activity activity)
        {
            context = activity;
         //   pd = new ProgressDialog(activity);
       //     pd = new ProgressDialog(getApplication());
           /* pd.setMessage("Submitting your request please wait. If you press back button your request will be uploaded in the backend");
            pd.setCanceledOnTouchOutside(false);
            pd.show();*/
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            SoapPrimitive response =null;

           /* for(int k=0;k<arrlstUploadedImageBitmap.size();k++) {
                response = uploadProjectImage(arrlstUploadedImageBitmap.get(k),k+1);
                if(k==arrlstUploadedImageBitmap.size()-1){
                    return response;
                }
            }*/

            for(int k=0;k<arrlstUploadImageByteArray.size();k++)
            {
                response = uploadProjectImage(arrlstUploadImageByteArray.get(k),k+1);
                if(k==arrlstUploadImageByteArray.size()-1)
                {
                    return response;
                }
            }


            //Log.d("Soap response is",response.toString());
            return null;

        }

        @Override
        protected void onPreExecute() {
   /*         progressBar = (ProgressBar) view4.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {

            if(result != null) {
                if (result.toString().equalsIgnoreCase("success")) {

           /*     for(String imgePath : imageAdapter.getImageList()) {
                    final String where = MediaStore.MediaColumns.DATA + "=?";
                    final String[] selectionArgs = new String[]{imgePath};
                    final ContentResolver contentResolver = context.getContentResolver();
                    final Uri filesUri = MediaStore.Files.getContentUri("external");

                    contentResolver.delete(filesUri, where, selectionArgs);
                }*/

                    //imgurlss.clear();
                    imageUrls.clear();
                    arrlstUploadedImageBitmap.clear();




                    if (docbyteArray != null) {
                        UploadProjectDocument uploadProjectDocument = new UploadProjectDocument(getApplication());
                        uploadProjectDocument.execute();
                    } else {
                        SubmitForCompletion submitForCompletion = new SubmitForCompletion(getApplicationContext());
                        submitForCompletion.execute();
                    }
                    /*pd.setProgress(100);
                    if (pd.getProgress() == pd.getMax()) {
                        pd.dismiss();
                    }*/
                } else {
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                    Log.e("tag","result="+result.toString());
                    pd.dismiss();
                    //progressDialog.dismiss();
                }
            }else{
                //Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive uploadProjectImage(byte[] uploadImgString,int count) {
        String METHOD_NAME = "UpdateProjectCompletionImg1";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletionImg1";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","uploadProjectImage");

        try
        {
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",str_leadId);
            request.addProperty("RegistrationId",str_RegistrationId);

          //  Log.d("Projectssssssss", String.valueOf(mapProjectIdProject.get(selectedProject)));
            request.addProperty("ProjectId",str_projectid);

            request.addProperty("ProfileImage",uploadImgString);
            request.addProperty("doccount",count);



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);


            envelope.dotNet = true;

            Log.d("Requestlogcat",request.toString());

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //Log.d("soaprespUploadPrjctImg",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soaprespUploadPrjctImg",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();

                }
            });
           /* getActivity().finish();*/
        }
        return null;
    }

    public class UploadProjectDocument extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        UploadProjectDocument (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            SoapPrimitive response =null;

            response = uploadProjectDocument();

            //Log.d("Soap response is",response.toString());
            return response;

        }

        @Override
        protected void onPreExecute() {
   /*         progressBar = (ProgressBar) view4.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
         /*    Toast.makeText(getActivity(),result.toString(),Toast.LENGTH_LONG).show();
             progressDialog.dismiss();*/


            if(result != null) {
                if (result.toString().equalsIgnoreCase("success"))
                {
                    //Log.d("FinalSoapResult",result.toString());
/*                LoadApprovedProject loadApprovedProject = new LoadApprovedProject(getActivity());
                loadApprovedProject.execute();*/

                    SubmitForCompletion submitForCompletion = new SubmitForCompletion(getApplication());
                    submitForCompletion.execute();
                } else {
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                 //   progressDialog.dismiss();
                }

                Log.d("Resultisssss", result.toString());
            }else{
                //progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive uploadProjectDocument() {
        String METHOD_NAME = "UpdateProjectCompletionDocument";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletionDocument";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","uploadProjectDocumentssss");

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",str_leadId);
            request.addProperty("RegistrationId",str_RegistrationId);

          //  Log.d("Projectssssssss", String.valueOf(mapProjectIdProject.get(selectedProject)));
            request.addProperty("ProjectId",str_projectid);

            request.addProperty("docFile",docbyteArray);
            request.addProperty("extension",extensions);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //Log.d("soaprespUpldPrjctDoc",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soaprespUpldPrjctDoc",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }

    public class SubmitForCompletion extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        SubmitForCompletion (Context ctx){
            context = ctx;
        }

        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            SoapPrimitive response = submitCompletionProject();
            return response;
        }

        @Override
        protected void onPreExecute() {
          /*  if (pd != null && pd.isShowing()){
                pd = new ProgressDialog(context);
            //     pd = new ProgressDialog(getApplication());
            pd.setMessage("Submitting your request please wait. If you press back button your request will be uploaded in the backend");
            pd.setCanceledOnTouchOutside(false);
            pd.show();}*/
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {

         //   pd.dismiss();
            //progressDialog.dismiss();
            if(result!=null) {
                if (result.toString().equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(),"Project Completed Successfully",Toast.LENGTH_LONG).show();
                    //pd.dismiss();
                    finish();
                    Intent ittEditProjToProjStatus = new Intent(FinalProjectCompletion_Activity.this, ProjectDetails.class);
                    startActivity(ittEditProjToProjStatus);
                    pd.setProgress(100);
                    if (pd.getProgress() == pd.getMax()) {
                        pd.dismiss();
                    }
                }
            }else{
                //getActivity().finish();
                Toast.makeText(context,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive submitCompletionProject()
    {
        String METHOD_NAME = "UpdateProjectCompletions";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletions";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","submitCompletionProject");

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",str_leadId);
            //request.addProperty("RegistrationId",str_RegistrationId);

         //   Log.d("Projectssssssss", String.valueOf(mapProjectIdProject.get(selectedProject)));

            request.addProperty("ProjectId",str_projectid);

            if(edt_fundRaised != null){
                int length = edt_fundRaised.getText().length();
                Log.d("Lengthisss", String.valueOf(length));
                if(length > 0) {
                    Log.d("beforelengthgreaterthan","zero");
                    String str_fundRaised = edt_fundRaised.getText().toString();
                    Log.d("str_fundraisedafter", str_fundRaised);
                    if (!str_fundRaised.equals(null) && !str_fundRaised.isEmpty() && !str_fundRaised.equals("")) {
                        request.addProperty("FundsRaised", str_fundRaised);
                    } else {
                        request.addProperty("FundsRaised", "0");
                    }
                }else{
                    request.addProperty("FundsRaised", "0");
                }
            }else{
                request.addProperty("FundsRaised", "0");
            }

            if(placeofimpl_fpc_et.getText()!=null) {
                String str_placeofImp = placeofimpl_fpc_et.getText().toString();
                if (!str_placeofImp.isEmpty() && !str_placeofImp.equals("") && !str_placeofImp.equals(null)) {
                    request.addProperty("Placeofimplement", str_placeofImp);
                } else {
                    request.addProperty("Placeofimplement", "");
                }
            }else{
                request.addProperty("Placeofimplement", "");
            }

            if(challanges_fpc_et.getText()!=null) {
                String str_Challanges = challanges_fpc_et.getText().toString();
                if (!str_Challanges.isEmpty() && !str_Challanges.equals("") && !str_Challanges.equals(null)) {
                    request.addProperty("Challenge", str_Challanges);
                } else {
                    request.addProperty("Challenge", "");
                }
            }else{
                request.addProperty("Challenge", "");
            }

            if(learning_fpc_et.getText()!=null) {
                String str_learning = learning_fpc_et.getText().toString();
                if (!str_learning.isEmpty() && !str_learning.equals("") && !str_learning.equals(null)) {
                    request.addProperty("Learning", str_learning);
                } else {
                    request.addProperty("Learning", "");
                }
            }else{
                request.addProperty("Learning", "");
            }

            if(story_fpc_et.getText()!=null) {
                String str_story = story_fpc_et.getText().toString();
                if (!str_story.isEmpty() && !str_story.equals("") && !str_story.equals(null)) {
                    request.addProperty("AsAStory", str_story);
                } else {
                    request.addProperty("AsAStory", "");
                }
            }else{
                request.addProperty("AsAStory", "");
            }

            if(resourcesUtilised_fpc_et.getText() != null){
                String str_resourceUtilised = resourcesUtilised_fpc_et.getText().toString();
                Log.d("StringResourcesUtilised",str_resourceUtilised);

                if (!str_resourceUtilised.isEmpty() && !str_resourceUtilised.equals("") && !str_resourceUtilised.equals(null)) {
                    request.addProperty("Resource", str_resourceUtilised);
                } else {
                    request.addProperty("Resource", "");
                }
            }


            String str_resource_amount=resourcesUtilised_amount_fpc_et.getText().toString();
            if(!str_resource_amount.isEmpty() && !str_resource_amount.equals("") && !str_resource_amount.equals(null)){

                long long_resource_amount= Long.parseLong(str_resource_amount);
                request.addProperty("TotalResources",long_resource_amount);//<TotalResources>long</TotalResources>
                Log.d("str_totalresource", String.valueOf(long_resource_amount));
            }else{
                request.addProperty("TotalResources",0);
                //zero has been added on 7Sept2019
            }



            String str_hoursspent = edt_hoursspent.getText().toString();

            if(!str_hoursspent.isEmpty() && !str_hoursspent.equals("") && !str_hoursspent.equals(null)){
                request.addProperty("HoursSpend",str_hoursspent);
                Log.d("str_HoursSpend", str_hoursspent);
            }else{
                request.addProperty("HoursSpend",0);
                //zero has been added on 7Sept2019
            }



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





            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //new MarshalBase64().register(envelope);
            //new Marshal.register(envelope);

            envelope.dotNet = true;




            //Set output SOAP object
            envelope.setOutputSoapObject(request);



            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("soaprespsubmitComplnPrj",envelope.getResponse().toString());

                //Log.d("Requestsssss",request.toString());


                //SoapObject response = (SoapObject) envelope.getResponse();

                //return null;
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soaprespsubmitComplnPrj",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplication(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
              runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplication(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplication(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplication(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

    private class GetProjectDraftDetails_AsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            /*dialog.setMessage("Please wait,State Loading...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
*/

            pd = new ProgressDialog(FinalProjectCompletion_Activity.this);
            pd.setMax(100);
            pd.setMessage("Loading...");
            ///  pd.setTitle("ProgressDialog bar example");
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCanceledOnTouchOutside(false);
            pd.show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("df", "doInBackground");

            new Thread(new Runnable()
            {
                @Override
                public void run() {
                    try {
                        while (pd.getProgress() <= pd.getMax()) {
                            Thread.sleep(200);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            }).start();

            Get_ProjectDraftdetails();

            return null;
        }

        public GetProjectDraftDetails_AsyncCallWS(FinalProjectCompletion_Activity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/
            //dialog.dismiss();

            //uploadfromDB_Statelist();


            if(str_projectdraft_Status.toString().trim().equals("Success"))
            {
                projectname_fpc_tv.setText(str_response_approved_title);
                projectype_fpc_tv.setText(str_response_approved_themetypename);
                beneficiaries_fpc_tv.setText(str_response_approved_beneficinary);
                objectives_fpc_tv.setText(str_response_approved_objective);
                if(str_response_approved_placeofimplmnt.equals("anyType{}"))
                { placeofimpl_fpc_et.setText(""); }

                else
                { placeofimpl_fpc_et.setText(str_response_approved_placeofimplmnt); }

                if(str_response_approved_approvedAmt.equals("anyType{}")){
                    approvedAmt_fpc_tv.setText("");
                }else {
                approvedAmt_fpc_tv.setText(str_response_approved_approvedAmt); }



                if(str_resource_amount_response.equals("anyType{}")||
                        str_resource_amount_response.equals(null)||
                        str_resource_amount_response.isEmpty())
                {
                    resourcesUtilised_amount_fpc_et.setText("");
                }else
                {
                    resourcesUtilised_amount_fpc_et.setText(str_resource_amount_response);
                }

                if(str_hoursspent.equals("anyType{}")){
                    edt_hoursspent.setText("");
                }else {
                    edt_hoursspent.setText(str_hoursspent);
                }

                if(str_response_fundraised.equals("anyType{}")){
                    edt_fundRaised.setText("");
                }else{
                    edt_fundRaised.setText(str_response_fundraised);
                }
                if(str_response_challenge.equals("anyType{}")){
                    challanges_fpc_et.setText("");
                }else{
                    challanges_fpc_et.setText(str_response_challenge);
                }
                if(str_response_learning.equals("anyType{}")){
                    learning_fpc_et.setText("");
                }else {
                    learning_fpc_et.setText(str_response_learning);
                }
                if(str_response_story.equals("anyType{}")){
                    story_fpc_et.setText("");
                }else{
                story_fpc_et.setText(str_response_story); }

                if(str_response_utilised.equals("anyType{}")){
                    resourcesUtilised_fpc_et.setText("");
                }else{
                resourcesUtilised_fpc_et.setText(str_response_utilised); }

                if(str_response_Fund_Received.equals("anyType{}")){
                    txt_leadFunded.setText("");
                }else{
                txt_leadFunded.setText(str_response_Fund_Received); }





                if(str_finalcomp_projectstartdate.toString().equals("anyType{}")
                        ||str_finalcomp_projectstartdate.toString().equals(null)
                        ||str_finalcomp_projectstartdate.toString().equals("0000-00-00"))
                {
                    datelabel_linearlayout.setVisibility(View.GONE);
                    date_linearlayout.setVisibility(View.GONE);

                }
                else{
                    studentcomp_clickstartprojectdate_tv.setText(str_finalcomp_projectstartdate);
                }





                if(str_finalcomp_projectenddate.toString().equals("anyType{}")
                   ||str_finalcomp_projectenddate.toString().equals(null)
                        ||str_finalcomp_projectenddate.toString().equals("0000-00-00"))
                {
                    datelabel_linearlayout.setVisibility(View.GONE);
                    date_linearlayout.setVisibility(View.GONE);

                }else
                {
                    studentcomp_clickendprojectdate_tv.setText(str_finalcomp_projectenddate);
                }



                if(!str_finalcomp_projectstartdate.toString().equals("anyType{}")
                        && !str_finalcomp_projectstartdate.toString().equals(null) &&
                        !str_finalcomp_projectstartdate.toString().equals("0000-00-00")
                    &&!str_finalcomp_projectenddate.toString().equals("anyType{}")
                        && !str_finalcomp_projectenddate.toString().equals(null) &&
                        !str_finalcomp_projectenddate.toString().equals("0000-00-00"))
                {
                    try{
                        int days_count = Days.daysBetween(new LocalDate(str_finalcomp_projectstartdate), new LocalDate(str_finalcomp_projectenddate)).getDays();
                        int int_days=days_count+1;
                        Log.e("days count", String.valueOf(int_days));
                        studentcomp_numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
                    }catch(Throwable t)
                    {
                        studentcomp_numberofdays_tv.setText("Date Format not in YYYY_MM_DD");
                    }

                }else
                { days_linearlayout.setVisibility(View.GONE);

                }




                sdgs_display_tv.setText(str_sdgs_goals_resp);





                if(str_collaboration_resp.equals("anyType{}"))
                {
                    impactproject_collabaration_et.setText("");
                }else
                { impactproject_collabaration_et.setText(str_collaboration_resp.toString());
                        if(str_collaboration_resp.trim().length()>0)
                        { impactproject_collabaration_cb.setChecked(true);}
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

                //  TempUrlList.clear();
                // for(int k=0;k<GalleryImageBitmap.getAllImgURL().size();k++) {
                // }
                //  TempUrlList=GalleryImageBitmap.getAllImgURL();



                LoadGalleryImage loadGalleryImg = new LoadGalleryImage(FinalProjectCompletion_Activity.this);
               loadGalleryImg.execute();
              //  txt_noOfImg.setText("No of Images : "+urllist.size());


              /*  String imgurl = null;
                for(int k=0;k<urllist.size();k++) {
                    imgurl = String.valueOf(urllist.get(0));

                    try {
                        Glide.with(getApplicationContext())
                               // .load("http://testbed.dfindia.org/Documents/MH08103_41256_5212c179-2691-4403-8ccb-62799c0413a3.jpg")
                                .load(imgurl)
                                .asBitmap()
                                .into(img_mainGallery);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                txt_numOfImages.setText("Images: " + urllist.size());*/
            }

        }//end of onPostExecute
    }// end Async task




    public void Get_ProjectDraftdetails()
    {


        String URL="",METHOD_NAME="",Namespace="",SOAPACTION="";

        if(str_projectstatus.equals("Draft"))
        {
            URL =Class_URL.URL_Projects.toString().trim();
            METHOD_NAME = "GetProjectDraftDetails";
            Namespace="http://mis.leadcampus.org/";
            SOAPACTION="http://mis.leadcampus.org/GetProjectDraftDetails";

        }
        else{
            if(str_projectstatus.equals("RequestForCompletion"))
            {
                URL =Class_URL.URL_Manager.toString().trim();
                METHOD_NAME = "GetProjectCompletionDetails";
                Namespace="http://mis.leadcampus.org/";
                SOAPACTION="http://mis.leadcampus.org/GetProjectCompletionDetails";

            }
        }




       /* <projectId>long</projectId>
      <Lead_Id>string</Lead_Id>*/

       Long projectid_long= Long.valueOf(str_projectid);

        try {


            //vijay check
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

               // Log.e("")
            request.addProperty("projectId", projectid_long);//<projectId>long</projectId>
            request.addProperty("Lead_Id", str_leadId);//<Lead_Id>string</Lead_Id>*/


        //    request.addProperty("projectId", 40910);//<projectId>long</projectId>
          //  request.addProperty("Lead_Id", "MH08103");//<Lead_Id>string</Lead_Id>*/

            /*<PDId>40910</PDId>
            <Title>Save as draft test</Title>
            <Theme>Awareness</Theme>
            <BeneficiaryNo>10</BeneficiaryNo>
            <Objectives>save as draft test</Objectives>
            <Placeofimplement>test</Placeofimplement>
            <FundsRaised>0</FundsRaised>
            <SanctionAmount>0</SanctionAmount>
            <Fund_Received>0</Fund_Received>
            <Challenge>save as draft test</Challenge>
            <Learning>save as draft test</Learning>
            <AsAStory>save as draft test</AsAStory>
            <docs>
                <vmProjectDocs>
                <PDId>0</PDId>
                  <SlNo>23795</SlNo>
                    <Document_Id>1</Document_Id>
                     <Document_Path>~/Documents/MH08103_40910_33a96c6a-59a0-4ea6-8685-c9633ee1e998.jpg</Document_Path>
                   </vmProjectDocs><vmProjectDocs>
                  <PDId>0</PDId>
                 <SlNo>23835</SlNo>
                  <Document_Id>2</Document_Id>
                <Document_Path>~/Documents/MH08103_40910_3d80fb0a-d7d6-4257-b148-656f61350573.pdf</Document_Path>
            </vmProjectDocs><vmProjectDocs>
               <PDId>0</PDId>
                <SlNo>23836</SlNo>
               <Document_Id>2</Document_Id>
               <Document_Path>~/Documents/MH08103_40910_be5acfd7-241d-4ffc-9a31-d24ec35a603f.pdf</Document_Path>
               </vmProjectDocs>
                </docs><Status>Success</Status>
               <CurrentSituation>save as draft test</CurrentSituation>
                <Resource>save as draft test</Resource>
                <CompletionProgress>70</CompletionProgress>
                <ProjectStatus>Draft</ProjectStatus>
              </vmProjectCompletion>*/


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

                SoapObject response = (SoapObject) envelope.getResponse();
               // SoapObject o_stateresponse = (SoapObject) response.getProperty(0);
                str_projectdraft_Status = response.getProperty("Status").toString();

                Log.e("requestsavedraft",response.toString());

                //response_str_projectid=

              //  str_response_approvedproject_status = response.getProperty("status").toString();// <status>Success</status>

                if(str_projectdraft_Status.toString().trim().equals("Success"))
                {
                    str_response_approved_title = response.getProperty("Title").toString();// <Title>Independence Day with soilders, police ,Forest officer & orphanage childrens</Title>
                    str_response_approved_themetypename = response.getProperty("Theme").toString();//<ThemeName>Social</ThemeName>
                    str_response_approved_beneficinary = response.getProperty("BeneficiaryNo").toString();//<BeneficiaryNo>1000</BeneficiaryNo>
                    str_response_approved_objective = response.getProperty("Objectives").toString();//<Objectives>to appreciate the soilders and police for there wonderful work</Objectives>
                    str_response_approved_approvedAmt = response.getProperty("SanctionAmount").toString();//<AskedAmount>320</AskedAmount>
                    str_response_approved_placeofimplmnt = response.getProperty("Placeofimplement").toString();//<Placeofimplement>bealgum </Placeofimplement>
                    str_response_fundraised = response.getProperty("FundsRaised").toString();
                    str_response_SanctionAmount = response.getProperty("SanctionAmount").toString();
                    str_response_Fund_Received = response.getProperty("Fund_Received").toString();
                    str_response_challenge = response.getProperty("Challenge").toString();
                    str_response_learning = response.getProperty("Learning").toString();
                    str_response_story = response.getProperty("AsAStory").toString();
                    str_response_utilised = response.getProperty("Resource").toString();
                    str_hoursspent = response.getProperty("HoursSpend").toString();
                    str_finalcomp_projectstartdate = response.getProperty("ProjectStartDate").toString();//<ProjectStartDate/>
                    str_finalcomp_projectenddate = response.getProperty("ProjectEndDate").toString();//<ProjectEndDate/>


                    str_collaboration_resp = response.getProperty("Collaboration_Supported").toString();//<Collaboration_Supported/>
                    str_permissionactivities_resp = response.getProperty("Permission_And_Activities").toString();// <Permission_And_Activities/>
                    str_exp_initiative_resp = response.getProperty("Experience_Of_Initiative").toString();//<Experience_Of_Initiative/>
                    str_lacking_initiative_resp = response.getProperty("Lacking_initiative").toString();//<Lacking_initiative/>
                    str_againtstide_resp = response.getProperty("Against_Tide").toString();//<Against_Tide/>
                    str_cross_hurdles_resp = response.getProperty("Cross_Hurdles").toString();//<Cross_Hurdles/>
                    str_venture_resp = response.getProperty("Entrepreneurial_Venture").toString();//<Entrepreneurial_Venture/>
                    str_govern_award_resp = response.getProperty("Government_Awarded").toString();//<Government_Awarded/>
                    str_leadership_role_resp = response.getProperty("Leadership_Roles").toString();// <Leadership_Roles/>

                    str_resource_amount_response=response.getProperty("TotalResourses").toString();// //<TotalResourses>0</TotalResourses>


                    /*str_is_impactproject=response.getProperty("isImpact_Project").toString();//<IsImpactProject>1</IsImpactProject>

                    Log.e("str_impact",response.getProperty("isImpact_Project").toString());//isImpact_Project
*/

                   /* if(response.toString().contains("IsImpactProject"))
                    {
                        str_is_impactproject=response.getProperty("isImpact_Project").toString();//<IsImpactProject>1</IsImpactProject>
                    }
                    else{ str_is_impactproject="0";
                    }*/


                    //<SDG_Status>No Goals</SDG_Status> //when goals has not been selected
                    //<SDG_List> // when goals list has been selected


                    if (response.toString().contains("SDG_Status"))//<SDG_Status>No Goals</SDG_Status>
                    {

                        goals_jsonobject = new JSONObject();
                        goals_jsonobject.put("", "");
                        str_sdgs_goals_resp="";
                       // sdgs_display_tv.setText("");
                    } else
                        {




                    SoapObject sdgsList = (SoapObject) response.getProperty("SDG_List");
                    Object O_goalsln;
                    SoapPrimitive primitive_slno;
                    String str_slno = null;
                    arraylist_goalsln.clear();

                    for (int i = 0; i < sdgsList.getPropertyCount(); i++)
                    {
                        SoapObject goal_sln = (SoapObject) sdgsList.getProperty(i);

                        O_goalsln = goal_sln.getProperty("Slno");

                        {
                            primitive_slno = (SoapPrimitive) goal_sln.getProperty("Slno");//Slno
                            Log.e("goalslnname", primitive_slno.toString());
                            str_slno = primitive_slno.toString();

                            arraylist_goalsln.add(str_slno);
                        }

                    }
                          //  ArrayList<String> al2 = (ArrayList<String>)arraylist_goalsln.clone();

                            arraylist_goalsln_forcheckbox=arraylist_goalsln;
                    set_sdgs_display_tv();

                }

                    //Log.e("sdgsList", String.valueOf(sdgsList.getPropertyCount()));


                    SoapObject soapObj = (SoapObject) response.getProperty("docs");

                    if (!soapObj.toString().equals("anyType{}") && !soapObj.toString().equals(null)) {

                        SoapPrimitive S_DocumentPath,S_DocumentSlno,S_DocumentPathImg;
                        Object O_DocumentPath,O_DocumentSlno;;
                        String str_DocumentPath, str_actualdocPath,str_DocumentSlno;
                        int count_img;
                        //count2 = soapObj.getPropertyCount();

                        for (count1 = 0; count1 < soapObj.getPropertyCount(); count1++) {
                            SoapObject doclist = (SoapObject) soapObj.getProperty(count1);
                            Log.d("doclist", doclist.toString());

                            O_DocumentPath = doclist.getProperty("Document_Path");
                            if (!O_DocumentPath.toString().equals("anyType{}") && !O_DocumentPath.toString().equals(null)) {
                                S_DocumentPath = (SoapPrimitive) doclist.getProperty("Document_Path");
                                str_DocumentPath = S_DocumentPath.toString();
                                Log.e("tag","count_img1="+count1);




                                //Log.d("F")

                                str_actualdocPath = Class_URL.ServerPath.toString().trim() + str_DocumentPath.substring(2);
                                //Log.d("Documentsssssss",str_actualdocPath);


                                //       if(str_actualdocPath.endsWith("jpg") || str_actualdocPath.endsWith("jpeg") || str_actualdocPath.endsWith("png")){

                                if (str_actualdocPath.endsWith("jpg") || str_actualdocPath.endsWith("jpeg") || str_actualdocPath.endsWith("png") || str_actualdocPath.endsWith("gif") || str_actualdocPath.endsWith("psd")) {
                                    count2++;

                                    count_img=count1;
                                    S_DocumentSlno = (SoapPrimitive) doclist.getProperty("SlNo");
                                    S_DocumentPathImg = (SoapPrimitive) doclist.getProperty("Document_Path");
                                    Log.e("tag","count_img2="+count_img+"S_DocumentSlno="+S_DocumentSlno);
                                    Log.e("tag","S_DocumentPathImg="+S_DocumentPathImg);

                                    str_actualdocPath = str_actualdocPath.replace(" ", "%20");
                                    //Log.d("str_actualdocPath", str_actualdocPath);

                                    //if(!str_actualdocPath.contains("(1)")) {
                                    try {
                                        url = new URL(str_actualdocPath);
                                        urllist.add(url);


                                        //TempUrlList.add(url);

                                        //added by madhu on 7/9/19
                                        documentlist_path.add(S_DocumentPathImg.toString().trim());
                                        documentlist_slno.add(S_DocumentSlno.toString().trim());

                                        Log.d("Urlsssss", url.toString());
                                    } catch (MalformedURLException e) {
                                        e.printStackTrace();
                                    }

                                    imagePathList.add(str_actualdocPath);

                                /*LoadGalleryImage loadGalleryImg = new LoadGalleryImage(FinalProjectCompletion_Activity.this);
                                loadGalleryImg.execute();*/
                                }

                                GalleryImageBitmap.setAllImgURL(urllist);       // added by madhu on 7/9/19

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
                      /*  for(int k=0;k<=urllist.size();k++) {
                            String imgurl= String.valueOf(urllist.get(0));
                            try {
                                Glide.with(getApplicationContext())
                                        .load(imgurl)
                                        .asBitmap()
                                        .into(img_mainGallery);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }*/


                    }



                }

                //Log.e("response projectdraft", str_projectdraft_Status.toString());

            } catch (Throwable t)
        {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
               // internet_issue = "slow internet";
            }
        } catch (Throwable t)
    {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }


    }//End of uploaddetails







    private class CompletionGetApprovedProjectDetails_AsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait,Loading");
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

            Get_ApprovedProjectdetails();
            return null;
        }

        public CompletionGetApprovedProjectDetails_AsyncCallWS(FinalProjectCompletion_Activity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {



            dialog.dismiss();

            if(str_response_approvedproject_status.toString().trim().equals("Success"))
            {
                projectname_fpc_tv.setText(str_response_approved_title);
                projectype_fpc_tv.setText(str_response_approved_themetypename);
                beneficiaries_fpc_tv.setText(str_response_approved_beneficinary);
                objectives_fpc_tv.setText(str_response_approved_objective);

                if(str_response_approved_placeofimplmnt.equals("anyType{}"))
                { placeofimpl_fpc_et.setText(""); }

                else
                { placeofimpl_fpc_et.setText(str_response_approved_placeofimplmnt); }



                approvedAmt_fpc_tv.setText(str_response_approved_approvedAmt);

                txt_leadFunded.setText(str_response_Fund_Received);



                if((str_hoursspent.equals("anyType{}"))||(str_hoursspent.equals(" "))
                        ||(str_hoursspent.equals(null)) )
                {
                    edt_hoursspent.setText("");
                }else {
                    edt_hoursspent.setText(str_hoursspent);
                }




                if(str_finalcomp_projectstartdate.toString().equals("anyType{}")
                        || str_finalcomp_projectstartdate.toString().equals(null)
                        || str_finalcomp_projectstartdate.toString().equals("0000-00-00"))
                {

                    datelabel_linearlayout.setVisibility(View.GONE);
                    date_linearlayout.setVisibility(View.GONE);

                }
                else{

                    studentcomp_clickstartprojectdate_tv.setText(str_finalcomp_projectstartdate);
                }





                if(str_finalcomp_projectenddate.toString().equals("anyType{}")
                        ||str_finalcomp_projectenddate.toString().equals(null)
                        ||str_finalcomp_projectenddate.toString().equals("0000-00-00"))
                {
                    datelabel_linearlayout.setVisibility(View.GONE);
                    date_linearlayout.setVisibility(View.GONE);
                }else
                { studentcomp_clickendprojectdate_tv.setText(str_finalcomp_projectenddate);
                }



                if(!str_finalcomp_projectstartdate.toString().equals("anyType{}")
                        && !str_finalcomp_projectstartdate.toString().equals(null) &&
                        !str_finalcomp_projectstartdate.toString().equals("0000-00-00")
                        &&!str_finalcomp_projectenddate.toString().equals("anyType{}")
                        && !str_finalcomp_projectenddate.toString().equals(null) &&
                        !str_finalcomp_projectenddate.toString().equals("0000-00-00"))
                {
                    try{
                        int days_count = Days.daysBetween(new LocalDate(str_finalcomp_projectstartdate), new LocalDate(str_finalcomp_projectenddate)).getDays();
                        int int_days=days_count+1;
                        studentcomp_numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
                    }catch(Throwable t)
                    {
                        studentcomp_numberofdays_tv.setText("Date Format not in YYYY_MM_DD");
                    }

                }else
                { days_linearlayout.setVisibility(View.GONE);

                }

               /* if(str_is_impactproject.equals(null)||str_is_impactproject.equalsIgnoreCase("anyType{}")||
                        str_is_impactproject.equals("0"))
                {

                }*/


                if(str_collaboration_resp.equals("anyType{}"))
                {
                    impactproject_collabaration_et.setText("");
                }else
                { impactproject_collabaration_et.setText(str_collaboration_resp.toString());}


                if(str_permissionactivities_resp.equals("anyType{}"))
                {
                    permissionactivities_et.setText("");
                }else
                { permissionactivities_et.setText(str_permissionactivities_resp.toString());}

                if(str_exp_initiative_resp.equals("anyType{}"))
                {
                    exp_initiative_et.setText("");
                }else
                { exp_initiative_et.setText(str_exp_initiative_resp.toString());}



                    sdgs_display_tv.setText(str_sdgs_goals_resp);


                if(str_lacking_initiative_resp.equals("anyType{}"))
                {
                    lacking_initiative_et.setText("");
                }else
                { lacking_initiative_et.setText(str_lacking_initiative_resp.toString());}

                if(str_againtstide_resp.equals("anyType{}"))
                {
                    impactproject_againtstide_et.setText("");
                }else
                { impactproject_againtstide_et.setText(str_againtstide_resp.toString());}

                if(str_cross_hurdles_resp.equals("anyType{}"))
                {
                    impactproject_crosshurdles_et.setText("");
                }else
                { impactproject_crosshurdles_et.setText(str_cross_hurdles_resp.toString());}




                if(str_venture_resp.equals("anyType{}"))
                {
                    impactproject_venture_et.setText("");
                }else
                { impactproject_venture_et.setText(str_venture_resp.toString());}

                if(str_govern_award_resp.equals("anyType{}"))
                {
                    impactproject_governaward_et.setText("");
                }else
                { impactproject_governaward_et.setText(str_govern_award_resp.toString());}

                if(str_leadership_role_resp.equals("anyType{}"))
                {
                    impactproject_leadershiprole_et.setText("");
                }else
                { impactproject_leadershiprole_et.setText(str_leadership_role_resp.toString());}




            }


        }//end of onPostExecute
    }// end Async task



    public void Get_ApprovedProjectdetails()
    {
        String URL =Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "CompletionGetApprovedProjectDetails";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/CompletionGetApprovedProjectDetails";



      /* <leadId>string</leadId>
      <PDId>int</PDId>*/

        int projectid_in= Integer.parseInt(str_projectid);

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            request.addProperty("leadId", str_leadId);//<leadId>string</leadId>
            request.addProperty("PDId", projectid_in);//<PDId>int</PDId>*/



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);



           /* <Title>Independence Day with soilders, police ,Forest officer & orphanage childrens</Title>
            <ThemeName>Social</ThemeName>
            <BeneficiaryNo>1000</BeneficiaryNo>
            <Objectives>to appreciate the soilders and police for there wonderful work</Objectives>
            <AskedAmount>320</AskedAmount>
            <SanctionAmount>320</SanctionAmount>
            <giventotal>0</giventotal>
            <Placeofimplement>bealgum </Placeofimplement>
            <Challenge/>
            <Learning/>
            <AsAStory/>
            <CurrentSituation/>
            <Resource/>
            <status>Success</status>*/

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                //  Log.i(TAG, "GetAllLoginDetails is running");
                //    result1 = (Vector<SoapObject>) envelope.getResponse();

                // response = (SoapPrimitive) envelope.getResponse();

                // SoapPrimitive res =(SoapPrimitive)envelope.getResponse();

                SoapObject response = (SoapObject) envelope.getResponse();
                // SoapObject o_stateresponse = (SoapObject) response.getProperty(0);

                Log.e("responseApproved",response.toString());


                str_response_approvedproject_status = response.getProperty("status").toString();// <status>Success</status>

                if(str_response_approvedproject_status.toString().trim().equals("Success"))
                {
                   str_response_approved_title=response.getProperty("Title").toString();// <Title>Independence Day with soilders, police ,Forest officer & orphanage childrens</Title>
                    str_response_approved_themetypename=response.getProperty("ThemeName").toString();//<ThemeName>Social</ThemeName>
                    str_response_approved_beneficinary=response.getProperty("BeneficiaryNo").toString();//<BeneficiaryNo>1000</BeneficiaryNo>
                    str_response_approved_objective=response.getProperty("Objectives").toString();//<Objectives>to appreciate the soilders and police for there wonderful work</Objectives>
                    str_response_approved_approvedAmt=response.getProperty("SanctionAmount").toString();//<AskedAmount>320</AskedAmount>
                    str_response_approved_placeofimplmnt=response.getProperty("Placeofimplement").toString();//<Placeofimplement>bealgum </Placeofimplement>
                    str_response_Fund_Received=response.getProperty("giventotal").toString();
                    str_hoursspent=response.getProperty("HoursSpend").toString();
                    str_finalcomp_projectstartdate=response.getProperty("ProjectStartDate").toString();//<ProjectStartDate/>
                    str_finalcomp_projectenddate=response.getProperty("ProjectEndDate").toString();//<ProjectEndDate/>

                   // str_is_impactproject=response.getProperty("IsImpactProject").toString();//<IsImpactProject>1</IsImpactProject>

                    str_collaboration_resp=response.getProperty("Collaboration_Supported").toString();//<Collaboration_Supported/>
                    str_permissionactivities_resp=response.getProperty("Permission_And_Activities").toString();// <Permission_And_Activities/>
                    str_exp_initiative_resp=response.getProperty("Experience_Of_Initiative").toString();//<Experience_Of_Initiative/>
                    str_lacking_initiative_resp=response.getProperty("Lacking_initiative").toString();//<Lacking_initiative/>
                    str_againtstide_resp=response.getProperty("Against_Tide").toString();//<Against_Tide/>
                    str_cross_hurdles_resp=response.getProperty("Cross_Hurdles").toString();//<Cross_Hurdles/>
                    str_venture_resp=response.getProperty("Entrepreneurial_Venture").toString();//<Entrepreneurial_Venture/>
                    str_govern_award_resp=response.getProperty("Government_Awarded").toString();//<Government_Awarded/>
                    str_leadership_role_resp=response.getProperty("Leadership_Roles").toString();// <Leadership_Roles/>

                    //approved details

                    if (response.toString().contains("SDG_Status"))//<SDG_Status>No Goals</SDG_Status>
                    {


                        goals_jsonobject = new JSONObject();
                        goals_jsonobject.put("", "0");
                        str_sdgs_goals_resp="";
                    }

                }




                Log.e("res_approvedstatus ", str_response_approvedproject_status.toString());

            } catch (Throwable t)
            {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());
                // internet_issue = "slow internet";
            }
        } catch (Throwable t)
        {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }


    }//End of uploaddetails






//camera features

    private void selectImage()
    {
        final CharSequence[] items = { "Take Photo", "Choose From Library",
                "Cancel" };

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    cameraIntent();
                } else if (items[item].equals("Choose From Library")) {
                    userChoosenTask ="Choose from Library";
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    /*int id1 = getResources().getIdentifier(String.valueOf(rowviewId), "id", getActivity().getPackageName());
                    rowLinearLayout = (RelativeLayout) view4.findViewById(id1);
                    parentLinearLayout.removeView(rowLinearLayout);*/

                    dialog.dismiss();
                }
            }
        });


        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });
        builder.show();
    }


    private void cameraIntent()
    {
        file_img=getOutputFromCamera();
        imageFilePath = CommonUtils.getFilename();
        File imageFile = new File(imageFilePath);
      //  Uri imageFileUri = Uri.fromFile(imageFile); // convert path to Uri
        Uri imageFileUri = FileProvider.getUriForFile(this, "com.mydomain.fileprovider", imageFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);   // set the image file name
        startActivityForResult(intent, REQUEST_CAMERA);
        /*
        file_img=getOutputFromCamera();
        uri_fileUri= null;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String filePath = file_img.getAbsolutePath();

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            uri_fileUri = getApplicationContext().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }

        else{
            uri_fileUri = Uri.fromFile(file_img);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri_fileUri);
        startActivityForResult(intent, REQUEST_CAMERA);*/
    }


    private void galleryIntent()
    {
/*        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_FILE);*/

        Intent photoSelectActivity = new Intent(this,CompletionGallerySelectActivity.class);
        //photoSelectActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //photoSelectActivity.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY );
        //startActivity(photoSelectActivity);
        startActivityForResult(photoSelectActivity,SELECT_FILE);

    }




    private File getOutputFromCamera() {
        String timeStamp = new SimpleDateFormat(getString(R.string.txtdateformat), Locale.getDefault()).format(new Date());
        file_imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getString(R.string.txtFileDocumentUploadImg) + timeStamp + getString(R.string.txtFileExtension));
        try {
            file_imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Log.d("absolute path is",imageFile.getAbsolutePath());
        return file_imageFile;
    }


//camera features




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECT_FILE)
            {
                //onSelectFromGalleryResult(data);


        /*        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getBaseContext().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);

                    Log.d("ImageEncodedissssssssif",imageEncoded);



      *//*              galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);*//*

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getBaseContext().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);

                            Log.d("ImageEncodedisssssssselse",imageEncoded);

                            cursor.close();

                    *//*        galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);*//*

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }*/


                Log.d("InsideActivity","Resultsssssssssssss");


            }

            else if (requestCode == REQUEST_CAMERA)
            {

             //   if (requestCode == REQUEST_CAMERA) {
                    String path1=  CommonUtils.compressImage(imageFilePath);
                    Log.e("tag","path1"+path1);
             /*   GetImageThumbnail getImageThumbnail = new GetImageThumbnail();

                Bitmap bitmap = null;
                try {
                    bitmap = getImageThumbnail.getThumbnail(fileUri, getActivity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/
                    Bitmap bitmap=BitmapFactory.decodeFile(new File(imageFilePath).getAbsolutePath());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte=null;
                    imageInByte = stream.toByteArray();
                    CompletionProjectStaticClass.setBytesImage(imageInByte);
                    Bitmap bm = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                    //  String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bm, "Titledddd", null);

                    cameraImgMap.put(path1,bm);

                    CompletionProjectStaticClass.setBytesImage(imageInByte); ///Image(imageInByte);

              /*  GetImageThumbnail getImageThumbnail = new GetImageThumbnail();

                Bitmap bitmap = null;
                try {
                    bitmap = getImageThumbnail.getThumbnail(uri_fileUri,this);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte;
                imageInByte = stream.toByteArray();
                Bitmap bm = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bm, "Titledddd", null);

                cameraImgMap.put(path,bm);*/

                //imageUrls.add(path);
               /* if(imageAdapter!=null) {
                    //imageUrls.clear();
                    imageUrls.addAll(imageAdapter.getImageList());
                    imageUrls.add(path);
                }else{
                    imageUrls.add(path);
                }*/




            /*    if(imageAdapter!=null) {

                    Log.d("imageAdapterStringsss1",imageAdapter.getImageList().toString());
                    Log.d("imageUrlsStringsss1",imageUrls.toString());

                    ArrayList<String> sample = new ArrayList<String>();
                    for(String imgUrl : imageUrls){
                        for (int j = 0; j < imageAdapter.getImageList().size(); j++) {
                            if ((imgUrl.equals(imageAdapter.getImageList().get(j)))){
                                Log.d("ImageURLtobedeletedisss",imgUrl);
                                String imgiss = imgUrl;
                                //imageUrls.remove(imgiss);

                                sample.add(imgiss);
                            }
                        }
                    }
                    imageUrls.removeAll(sample);

                    Log.d("imageAdapterStringsss2",imageAdapter.getImageList().toString());
                    Log.d("imageUrlsStringsss2",imageUrls.toString());

                }*/

        /*        ArrayList<String> dummyimageUrls = new ArrayList<String>();
                dummyimageUrls.addAll(imageUrls);

                if(imageAdapter!=null){
                    ArrayList<String> currentList = imageAdapter.getImageList();

                    Iterator<String> it = dummyimageUrls.iterator();

                    ArrayList<String> sample = new ArrayList<String>();
                    while(it.hasNext()){
                        String simple = it.next();
                        System.out.println(simple);
                        if(!currentList.contains(simple)){
                            //dummyimageUrls.remove(simple);
                            sample.add(simple);
                        }
                    }
                    dummyimageUrls.removeAll(sample);
                }

                imageUrls.clear();
                imageUrls.addAll(dummyimageUrls);*/


                if(imageAdapter!=null)
                {
                    imageUrls.clear();
                    Log.d("imageAdapterss",imageAdapter.getImageList().toString());
                    imageUrls.addAll(imageAdapter.getImageList());
                }

                imageUrls.add(path1);

                /*ArrayList<Uri> mArrayUri = new ArrayList<Uri>();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.upload_project_image, null);

                ImageView img_proj = (ImageView) rowView.findViewById(R.id.img_projImage);

                Log.d("Childcountisss", String.valueOf(parentLinearLayout.getChildCount()));

                btnUploadId = (parentLinearLayout.getChildCount()+1)*1000;

                projImageId = btnUploadId + 1;

                Log.d("btnUploadId", String.valueOf(btnUploadId));

                Log.d("projImageId", String.valueOf(projImageId));

                //btnUploadImage.setId(btnUploadId);
                //img_proj.setId(projImageId);

                hashMapBtnImg.put(btnUploadId,projImageId);

                if(parentLinearLayout.getChildCount()<=9) {
                    parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
                    rowView.setId(projImageId+1);
                    rowviewId = rowView.getId();

                    rowView.setVisibility(View.GONE);
                }

                if((parentLinearLayout.getChildCount() % 2) == 0){
                    rowView.setPadding(160,0,0,0);
                }else{
                    rowView.setPadding(0,0,0,0);
                }

                imageidis = projImageId;
                flag = 1;

                Log.d("LOG_TAG", "Selected Images" + mArrayUri.size());

                int id1 = getResources().getIdentifier(String.valueOf(rowviewId), "id", getActivity().getPackageName());
                rowLinearLayout = (RelativeLayout) view4.findViewById(id1);
                rowLinearLayout.setVisibility(View.VISIBLE);

                */

    /*            try {
                    actualImage = FileUtil.from(getActivity(), Uri.parse(path));

                    Log.d("ActualImageSize", "ActualImageSizeissss " + getReadableFileSize(actualImage.length()));
                    String fileSize = getReadableFileSize(actualImage.length());
                    String fileSizeArray[] = fileSize.split("\\s");

                    if(fileSizeArray[1].equals("GB") || fileSizeArray[1].equals("TB") || (fileSizeArray[1].equals("MB") && (Double.valueOf(fileSizeArray[0]) > 5.0))) {
                        compressedImage = new Compressor.Builder(getActivity())
                                .setMaxWidth(740)
                                .setMaxHeight(480)
                                .setQuality(100)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .build()
                                .compressToFile(actualImage);
                        //img_proj.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
                    }

                    else{
                        //imgProj.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                        //img_proj.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                //imgurlss.clear();
                //imgurlss.addAll(imageUrls);
                initializeRecyclerView();
                //imgurlss.removeAll(imageUrls);
                //imgurlss.addAll(imageAdapter.getImageList());

            }

            else if(requestCode == PICKFILE_RESULT_CODE){
                Uri selectedUri = data.getData();

                String filePath;

                Log.e("uri",selectedUri.toString());

                if (selectedUri.toString().startsWith("file:")) {
                    filePath = selectedUri.getPath();
                }

                else {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = this.getContentResolver().query(selectedUri,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();
                }

                Log.d("filePathissssss",filePath);

                //if(selectedImage.toString(""))

                File file = new File(filePath);

                docbyteArray = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);

                    int offset = 0;
                    int numRead = 0;
                    while (offset < docbyteArray.length && (numRead=fileInputStream.read(docbyteArray, offset, docbyteArray.length-offset)) >= 0) {
                        offset += numRead;
                    }

                    // Ensure all the bytes have been read in
                    if (offset < docbyteArray.length) {
                        throw new IOException("Could not completely read file " + file.getName());
                    }


                    Log.d("bytearrayisss",docbyteArray.toString());

                    String[] fileArray = filePath.split("/");
                    for(int k=0;k<fileArray.length;k++){
                        String filePart = fileArray[k];
                        Log.d("Kvalueis", String.valueOf(k));
                        Log.d("filePartisss",filePart);
                    }

                    //String extArray = fil
                    extensions = fileArray[fileArray.length-1].split("\\.")[1];

                    Log.d("extensionisss",extensions);



                    // Close the input stream and return bytes
                    fileInputStream.close();

                    Toast.makeText(this,"File is Selected",Toast.LENGTH_LONG).show();

                    fileSelected_fpc_tv.setText(filePath);
                    fileSelected_fpc_tv.setVisibility(View.VISIBLE);

              /*      Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "File is selected", Snackbar.LENGTH_LONG);
                    snackbar1.show();*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(resultCode == RESULT_CANCELED)
        {
            Log.d("Inside","RESULT_Cancelledddddd");

            Intent itt = this.getIntent();

            ArrayList<String> fileArrayString;
            ArrayList<String> fileDocumentString;

            if( CompletionProjectStaticClass.getCountCompletion() == 1 ){
                Log.d("Inside","RESULT_CancelleddddddfromPhotosActivityIntent");
                CompletionProjectStaticClass.setCountCompletion(0);

                fileArrayString = CompletionProjectStaticClass.getFileArrayString();
                imagesEncodedList = new ArrayList<String>();

                for(int i=0;i<fileArrayString.size();i++){
                    //imageUrls.add(fileArrayString.get(i));

                    if(imageAdapter!=null)
                    {
                        imageUrls.clear();
                        Log.d("imageAdapter ",imageAdapter.getImageList().toString());
                        imageUrls.addAll(imageAdapter.getImageList());
                    }
                    imagesEncodedList.add(fileArrayString.get(i));
                    //imageUrls.add(fileArrayString.get(i));
                }
                imageUrls.addAll(imagesEncodedList);

                initializeRecyclerView();
            }


            if( CompletionProjectStaticClass.getCountDocumentCompletion() == 1 )
            {
                CompletionProjectStaticClass.setCountDocumentCompletion(0);

                fileDocumentString = CompletionProjectStaticClass.getFileDocString();

                Log.d("fileDocumentStringsss:",fileDocumentString.toString());

                for(int i=0;i<fileDocumentString.size();i++) {
                    String filePath = fileDocumentString.get(i);
                    File file = new File(filePath);

                    Log.d("fileDocumentString2:", fileDocumentString.get(i).toString());

                    docbyteArray = new byte[(int) file.length()];
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);

                        int offset = 0;
                        int numRead = 0;
                        while (offset < docbyteArray.length && (numRead = fileInputStream.read(docbyteArray, offset, docbyteArray.length - offset)) >= 0) {
                            offset += numRead;
                        }

                        // Ensure all the bytes have been read in
                        if (offset < docbyteArray.length) {
                            throw new IOException("Could not completely read file " + file.getName());
                        }

                        Log.d("bytearrayisss", docbyteArray.toString());

                        String[] fileArray = filePath.split("/");
                        for (int k = 0; k < fileArray.length; k++) {
                            String filePart = fileArray[k];
                            Log.d("Kvalueis", String.valueOf(k));
                            Log.d("filePartisss", filePart);
                        }

                        //String extArray = fil
                        extensions = fileArray[fileArray.length - 1].split("\\.")[1];

                        Log.d("extensionisss", extensions);


                        // Close the input stream and return bytes
                        fileInputStream.close();

                        Toast.makeText(this, "File is Selected", Toast.LENGTH_LONG).show();

                        fileSelected_fpc_tv.setText(filePath);
                        fileSelected_fpc_tv.setVisibility(View.VISIBLE);

                    } catch (Exception e) {

                    }
                }




            }



  /*          int id1 = getResources().getIdentifier(String.valueOf(rowviewId), "id", getActivity().getPackageName());
            rowLinearLayout = (RelativeLayout) view4.findViewById(id1);
            parentLinearLayout.removeView(rowLinearLayout);*/
        }
    }// end of onActivityResult







    private void initializeRecyclerView()
    {

        imgurlss = new ArrayList<String>();

        Log.d("ImageUrlsizeissss", String.valueOf(imageUrls.size()));
        //
        imgurlss.addAll(imageUrls);

        Log.d("imgurlss==", String.valueOf(imgurlss.size()));
        mImageInByte.clear();
       /* for(String imgUrl : imgurlss) {

            Bitmap bitmap;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();

            bitmap = BitmapFactory.decodeFile(imgUrl, bmOptions);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageInByte;
           // imageInByte = stream.toByteArray();

            imageInByte = stream.toByteArray();
            mImageInByte.add(imageInByte);
    }*/
       for(int p=0;p<imgurlss.size();p++)
       {
           Bitmap bitmap= BitmapFactory.decodeFile(new File(imgurlss.get(p)).getAbsolutePath());
           ByteArrayOutputStream stream = new ByteArrayOutputStream();
           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
           byte[] imageInByte;
           imageInByte = stream.toByteArray();
           mImageInByte.add(imageInByte);
       }

    Log.e("tag","mImageInByte="+mImageInByte.size());
        Log.e("tag","mImageInByte="+mImageInByte.toString());
    //   mImageInByteAll.addAll(mImageInByte);
    // CompletionProjectStaticClass.setByteArrayImage(mImageInByte);
        imageAdapter = new ImageAdapter(this, imgurlss);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);

        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL, false);

        //layoutManager.setAutoMeasureEnabled(true);
        //layoutManager.
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.item_offset));
        recyclerView.setAdapter(imageAdapter);


    }



    @Override
    public void onBackPressed()
    {

       // obj_class_alert_msg.alerts_dialog2(contextpresentActivity,HomeActivity.class);
        AlertDialog.Builder dialog = new AlertDialog.Builder(FinalProjectCompletion_Activity.this);
        dialog.setCancelable(false);
        dialog.setTitle("LEADCampus");
        dialog.setMessage("Are you sure want to Exit from Completion Details" );

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Intent i = new Intent(FinalProjectCompletion_Activity.this,ProjectDetails.class);
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



    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent ittEditProjToProjDtls = new Intent(FinalProjectCompletion_Activity.this ,ProjectDetails.class);
            startActivity(ittEditProjToProjDtls);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }*/








    public static class LoadGalleryImage extends AsyncTask<Void, Object, ArrayList<Bitmap>> {

        private Context context;
        //    private ProgressBar progressBar;
        private ArrayList<Bitmap> bitmapList = new ArrayList<Bitmap>();
        //  private ProgressDialog progressDialog;

        LoadGalleryImage(Context context) {
            this.context = context;
            TempUrlList = GalleryImageBitmap.getAllImgURL();
            //    progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            //   progressBar = (ProgressBar) findViewById(R.id.progressBar);
            // progressBar.setVisibility(View.VISIBLE);
//            progressDialog.setMessage("Loading");
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();



            if(!pd.isShowing()){

			   /* if (pd1.getProgress() == pd
			            .getMax()) {
			        pd.setProgress(0);
			    }*/
                pd1.setMax(100);
                pd1.setMessage("Loading...");
                ///  pd.setTitle("ProgressDialog bar example");
                pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pd1.setCanceledOnTouchOutside(false);
                pd1.show();

            }

        }

        @Override
        protected ArrayList<Bitmap> doInBackground(Void... params) {
          //  pd.setProgress(50);


            Bitmap bitmaplogo = null;
            bitmapLst.clear();

           // for (int k = 0; k < urllist.size(); k++)
                for (int k = 0; k < TempUrlList.size(); k++)
            {

                handle.sendMessage(handle.obtainMessage());
                try {
                    //Log.d("Urlssssssssssss",url.toString());
                    //bitmaplogo = BitmapFactory.decodeStream(url.openStream());
                   // bitmaplogo = BitmapFactory.decodeStream(urllist.get(k).openStream());
                    bitmaplogo = BitmapFactory.decodeStream(TempUrlList.get(k).openStream());
                    bitmapLst.add(bitmaplogo);
                    Log.e("tag","activity bitmapList=="+bitmapList);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (OutOfMemoryError ex) {
                    /*runOnUiThread(new Runnable() {
                        public void run() {*/
                            Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                     /*   }
                    });*/
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

                /*if (bitmap != null)
                {
                    if (bitmap.size() > 0) {
                        img_mainGallery.setImageBitmap(bitmap.get(0));
                        txt_numOfImages.setText("Images: " + bitmap.size());
                    }
                }*/

                if (TempUrlList != null) {
                    if (TempUrlList.size() > 0) {

                        img_mainGallery.setImageBitmap(bitmap.get(0));
                        //  txt_numOfImages.setText("Images: " + bitmap.size());
                        txt_numOfImages.setText("Images: " + TempUrlList.size());
                    } else {
                        img_mainGallery.setImageResource(0);//bitmap.get(0));
                        txt_numOfImages.setText("No Images");
                    }
                }
            } catch (OutOfMemoryError ex) {
                /*runOnUiThread(new Runnable() {
                    public void run() {*/
                        Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                /*    }
                });*/
            }
            //}
            if(pd.isShowing()) {
                pd.setProgress(100);
                if (pd.getProgress() == pd
                        .getMax()) {
                    pd.dismiss();
                }
            }

                else if(pd1.isShowing()) {
                    pd1.setProgress(100);
                    if (pd1.getProgress() == pd1
                            .getMax()) {
                        pd1.dismiss();

                    }
                }

        }


        Handler handle = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
               // pd.incrementProgressBy(1);
                if(pd.isShowing()) {
                    pd.incrementProgressBy(1);
                }
                else if(pd1.isShowing()){
                    pd1.incrementProgressBy(1);
                }

            }
        };

        //    progressBar.setProgress(100);
          //  progressBar.setVisibility(GONE);
            // progressDialog.dismiss();

    }






    //-------------------------- server img delete added by madhu 7/9/19 ------------------------------------------
    private class Async_DeleteImage extends AsyncTask<String, Void, Void>
    {
        ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public Async_DeleteImage(FinalProjectCompletion_Activity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //submit_PMProfile(ManagerId);
            DeleteImage();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            dialog.dismiss();

            if(status.equalsIgnoreCase("Image Deleted")) {
                //  Toast.makeText(FinalProjectCompletion_Activity.this, "Image Deleted Successfully " ,
                //         Toast.LENGTH_LONG).show();
                Delete_AllImglistTable_B4Insertion();

            }
            else{
                Toast.makeText(FinalProjectCompletion_Activity.this, status ,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void DeleteImage() {

        String URL = Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "Delete_ProjectImg";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/Delete_ProjectImg";

        try {


            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

			        /*    request.addProperty("Img_Slno", Delete_doclist_slno.get());
			            request.addProperty("img_Path", doc_path.get(position_value));*/
            JSONObject jsonObject = new JSONObject();
            for(int k=0;k<Delete_doclist_slno.size() && k<Delete_doclist_path.size();k++){
                jsonObject.put(Delete_doclist_path.get(k),Delete_doclist_slno.get(k));
            }
            request.addProperty("ImgDelete",jsonObject.toString());
            Log.e("tag","soap request delete image="+ request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("tag","soap response delete image"+ response.toString());

                status = response.toString();

            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }
    }























    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_project, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_editProfile) {
            Intent ittProjDtlsToEditProfile = new Intent(FinalProjectCompletion_Activity.this ,EditProfileActivity.class);
            startActivity(ittProjDtlsToEditProfile);
            return true;
        }

        if (id == R.id.action_logout) {

            //deleteCache(ProjectDetails.this);

            Intent ittProjDtlsToLogin = new Intent(FinalProjectCompletion_Activity.this ,LoginActivity.class);
            startActivity(ittProjDtlsToLogin);
            return true;
        }

        /*if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(FinalProjectCompletion_Activity.this ,ProjectDetails.class);
            startActivity(ittProjDtlsToHome);
            finish();
            return true;
        }*/
        if (id == android.R.id.home)
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(FinalProjectCompletion_Activity.this);
            dialog.setCancelable(false);
            dialog.setTitle("LEADCampus");
            dialog.setMessage("Are you sure want to Exit from Completion Details" );

            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Intent i = new Intent(FinalProjectCompletion_Activity.this,ProjectDetails.class);
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

           /* Intent ittProjDtlsToHome = new Intent(FinalProjectCompletion_Activity.this ,ProjectDetails.class);
            startActivity(ittProjDtlsToHome);
            finish();*/
          //  obj_class_alert_msg.alerts_dialog2(contextpresentActivity,ProjectDetails.class);

           // return true;
        }

        if(id == R.id.action_information){
            TextView txtclose;
            Button btnFollow;
            final Dialog myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.custompopup);
            txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
            //txtclose.setText("M");
            //  btnFollow = (Button) myDialog.findViewById(R.id.btnfollow);
            txtclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        }


        return super.onOptionsItemSelected(item);

    }





    void updatespinner()
    {


        final ArrayList<String> strDeviceArr = new ArrayList<>();
        strDeviceArr.add("Select device");

        ArrayAdapter<String> default_sdgs_goal = new ArrayAdapter<String>(getApplicationContext(), R.layout.leavetypespinneritems, strDeviceArr);
        default_sdgs_goal.setDropDownViewResource(R.layout.spinnercustomstyle);
        sdgs_sp.setAdapter(default_sdgs_goal);
    }




    //sdgs_goals




    // goal_1_nopoverty_cb,goal_2_zerohunger_cb,goal_3_goodhealth_cb,goal_4_quality_cb
    //goal_5_gender_cb,goal_6_clean_cb,goal_7_affordable_cb,goal_8_decent_cb,goal_9_industry_cb
    //goal_10_reduce_cb,goal_11_sustain_cb,goal_12_responsible_cb,goal_13_climate_cb
    //goal_14_lifewater_cb,goal_15_lifeland_cb,goal_16_peace_cb,goal_17_partnerships_cb
    //goal_18_none_cb


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

       /* goal_18_none_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();
            }
        });*/


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

       // String[] str=new String[]{str_selected_goals_array.split(",")};

       /* try {
            JSONObject obj1 = new JSONObject(String.valueOf(stringArray));
            Log.e("jsonobject",obj1.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/


        sdgs_display_tv.setText(str_selected_goals);
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


//sdgs_goals








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











}//end of class
