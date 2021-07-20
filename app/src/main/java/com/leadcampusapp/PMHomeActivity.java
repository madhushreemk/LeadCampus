package com.leadcampusapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

//import com.android.sripad.leadnew_22_6_2018.R;

public class PMHomeActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    TextView about_TV, projectDetails_TV, events_TV, news_TV, contactus_TV, request_TV;
    ImageView about_IV, projectDetails_IV, events_IV, news_IV, contactus_IV, request_IV;
    TextView textCartItemCompliteCount, textCartItemApprovedCount, textCartItemUnApprovedCount;
    int mCartItemCount = 110;
    ProgressBar progressBar;
    private boolean doubleBackToExitPressedOnce = false;

    TextView compeleted_tv, pending_tv, approved_tv, tshirt_tv;

    //  String[] descriptionData = {"Student", "Leader", "Master", "Ambassador"};

    int ManagerId;
    String ManagerName, PMEmailId, PM_Mobile, PMLocation, PMImgUrl;
    String str_responsecount, str_responseStatus, str_studentcount, str_collegecount, str_TshirtStatus, str_TshirtRequestCount;
    String ApprovedCount = "0", ComplitedCount = "0", UnapprovedCount = "0", RequestForCompletion = "0", Rejected = "0", RequestForModification = "0", TshirtStatus = "0";
    TextView txt_Name, txt_mailid, txt_mobile;
    ImageView iv_profilepic;

    public static final String PREFBook_PM = "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    public static final String PrefID_pmName = "prefid_pmname"; //
    public static final String PrefID_PMEMailID = "prefid_pmemailid"; //
    public static final String PrefID_PMMobile = "prefid_pmmobile"; //
    public static final String PrefID_pmlocation = "prefid_pmlocation"; //
    public static final String PrefID_pmimageurl = "prefid_pmimgurl"; //
    public static final String PrefID_pm_username = "prefid_pm_username"; //
    public static final String PrefID_pm_password = "prefid_pm_password"; //

    public static final String PREFBook_Stud = "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_Role = "prefid_role";
    public static final String PrefID_RegID = "prefid_regid";
    SharedPreferences shardpref_S_obj;


    SharedPreferences shardprefPM_obj;
    SharedPreferences.Editor editor_PM;
    String str_MangerID,str_role,str_regId, str_MangerName, str_ManagerEmail, str_ManagerMaobile, str_ManagerLocation, str_MangerImg;
    Integer MDId;

    Class_URL config_obj = new Class_URL();
    public static final String PREFBook_LoginTrack = "prefbook_logintrack";
    public static final String PrefID_WhereToGo = "prefid_wheretogo"; //
    SharedPreferences shardprefLoginTrack_obj;
    SharedPreferences.Editor editor_LoginTrack;

    private Button themeWiseProjCount_BT;

    RecyclerView recyclerView;
    ArrayList<DataModel> arrayList;

    GridView gridview;

    public static final String PREFBook_PM_Count = "prefbook_pm_count";  //sharedpreference Book
    public static final String PrefID_UNCount = "prefid_UNCount"; //
    public static final String PrefID_ComCount = "prefid_ComCount"; //
    public static final String PrefID_AppCount = "prefid_AppCount"; //
    public static final String PrefID_Tshirt = "prefID_Tshirt";
    public static final String PrefID_ReqModCount = "prefid_ReqModCount"; //
    public static final String PrefID_RejCount = "prefid_RejCount"; //
    public static final String PrefID_ReqComCount = "prefid_ReqComCount"; //

    SharedPreferences shardprefPM_obj_count;
    SharedPreferences.Editor editor_PM_count;

    /* public static String[] osNameList = {
             "Android",
             "iOS",
             "Linux",
             "MacOS",
             "MS DOS",
             "Symbian",
             "Windows 10",
             "Windows XP",
     };
     public static int[] osImages = {
             R.drawable.about,
             R.drawable.project,
             R.drawable.event,
             R.drawable.news,
             R.drawable.analytical,
             R.drawable.masters,};*/


    String Str_ManagerName, Str_PMEmailId, Str_PM_Mobile, Str_PMLocation, Str_Manager_Id, Str_PMImageURL, str_pm_username, str_pm_password, Str_FCMName;


    private ProgressDialog progressDialog_pmswiperefresh;
    private String serverPath = Class_URL.ServerPath.toString().trim();
    SwipeRefreshLayout swipeToRefresh_pmhomeactivity;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    ImageView complited_img, pending_img, approved_img, tshirt_img;
    Integer complited_count = 0, pending_count = 0, approved_count = 0, tshirt_count = 0;
    String completed_str, pending_str, approved_str, tshirt_str = "0";


    TelephonyManager tm1 = null;
    String myVersion, deviceBRAND, deviceHARDWARE, devicePRODUCT, deviceUSER, deviceModelName, deviceId, tmDevice, tmSerial, androidId, simOperatorName, sdkver, mobileNumber;
    int sdkVersion, Measuredwidth = 0, Measuredheight = 0, update_flage = 0;
    AsyncTask<Void, Void, Void> mRegisterTask;
    String regId = "leadxz";
    private String versioncode;
    EditText username_et,password_et, dialognewpwd_et,dialogconfpwd_et;
    String str_newPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pm_activity_main);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        // getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Mentor Profile");


        swipeToRefresh_pmhomeactivity = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh_pmhomeactivity);


        shardprefLoginTrack_obj = this.getSharedPreferences(PREFBook_LoginTrack, Context.MODE_PRIVATE);
        editor_LoginTrack = shardprefLoginTrack_obj.edit();
        editor_LoginTrack.putString(PrefID_WhereToGo, config_obj.packagename + "PMHomeActivity");
        editor_LoginTrack.commit();


        shardprefPM_obj_count = getSharedPreferences(PREFBook_PM_Count, Context.MODE_PRIVATE);

        shardprefPM_obj = getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:", str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId = Integer.parseInt(str_MangerID);

        shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        str_ManagerEmail = shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        Log.d("str_ManagerEmail:", str_ManagerEmail);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();

        shardprefPM_obj.getString(PrefID_pmName, "").trim();
        str_MangerName = shardprefPM_obj.getString(PrefID_pmName, "").trim();
        Log.d("str_MangerName:", str_MangerName);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();

        shardprefPM_obj.getString(PrefID_PMMobile, "").trim();
        str_ManagerMaobile = shardprefPM_obj.getString(PrefID_PMMobile, "").trim();
        Log.d("str_ManagerMaobile:", str_ManagerMaobile);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        shardprefPM_obj.getString(PrefID_pmimageurl, "").trim();
        str_MangerImg = shardprefPM_obj.getString(PrefID_pmimageurl, "").trim();
        Log.d("str_MangerImg:", str_MangerImg);


        str_pm_username = shardprefPM_obj.getString(PrefID_pm_username, "").trim();
        Log.e("str_pm_username:", str_pm_username);
        str_pm_password = shardprefPM_obj.getString(PrefID_pm_password, "").trim();
        Log.e("str_pm_passwrd:", str_pm_password);

        shardpref_S_obj= getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_Role, "").trim();
        str_regId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();
        Log.e("str_role:", str_role);

     /*   shardprefPM_obj.getString(PrefID_pmlocation, "").trim();
        str_ManagerLocation = shardprefPM_obj.getString(PrefID_pmlocation, "").trim();
        Log.d("str_ManagerLocation:",str_ManagerLocation);
*/
        //   Intent intent = getIntent();
     /*   String MangerID=intent.getStringExtra("ManagerId");
        Log.i("tag","MangerID="+MangerID);
        if(MangerID.equals("null")){
            ManagerId=1;
        }else
        {
            ManagerId = Integer.parseInt(MangerID);
            Log.i("tag","ManagerId="+ManagerId);
        }*/
        //  String MangerID=intent.getStringExtra("ManagerId");
/*        ManagerId = getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);
*/
 /*       ManagerName = intent.getStringExtra("ManagerName");
        PMEmailId= intent.getStringExtra("PMEmailId");
        PM_Mobile= intent.getStringExtra("PM_Mobile");
        PMLocation= intent.getStringExtra("PMLocation");
        PMImgUrl=intent.getStringExtra("PMImgUrl");**/

        Log.i("str_MangerImg=", str_MangerImg);
        txt_Name = (TextView) findViewById(R.id.txt_Name);
        txt_mailid = (TextView) findViewById(R.id.txt_mailid);
        txt_mobile = (TextView) findViewById(R.id.txt_mobile);
        //  txt_location=(TextView)findViewById(R.id.txt_location);
        iv_profilepic = (ImageView) findViewById(R.id.iv_profilepic);

        approved_tv = (TextView) findViewById(R.id.approved_tv);
        compeleted_tv = (TextView) findViewById(R.id.compeleted_tv);
        pending_tv = (TextView) findViewById(R.id.pending_tv);
        tshirt_tv = (TextView) findViewById(R.id.tshirt_tv);

        approved_img = (ImageView) findViewById(R.id.approved_img);
        pending_img = (ImageView) findViewById(R.id.pending_img);
        complited_img = (ImageView) findViewById(R.id.complited_img);
        tshirt_img = (ImageView) findViewById(R.id.tshirt_img);

        txt_Name.setText(str_MangerName);
        txt_mailid.setText(str_ManagerEmail);
        ;

        txt_mobile.setText(str_ManagerMaobile);

      /*  TextView chart_TV=(TextView) findViewById(R.id.chart_TV);
        ImageView chart_IV = (ImageView) findViewById(R.id.chart_IV);*/
   /*     if(str_ManagerLocation.equals("anyType{}") || str_ManagerLocation.equals(null)){
            txt_location.setText("");
        }else{
            txt_location.setText(str_ManagerLocation);
        }*/

        GetProjectCount getProjectCount = new GetProjectCount(this);
        getProjectCount.execute(MDId);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new GridAdapterPMFirst(this));

        GridView gridViewSecond = (GridView) findViewById(R.id.gridview2);
        gridViewSecond.setAdapter(new GridAdapterPMSecond(this));
        // gridViewSecond.setAdapter(new GridAdapterPMSecond(this, UnapprovedCount, ComplitedCount, ApprovedCount, RequestForCompletion, RequestForModification, Rejected, str_studentcount, str_collegecount));
        GridView gridViewTrid = (GridView) findViewById(R.id.gridview1);
        gridViewTrid.setAdapter(new GridAdapterPMThrid(this));

        GridView gridViewFour = (GridView) findViewById(R.id.gridview3);
        gridViewFour.setAdapter(new GridAdapterPMFour(this));


        pending_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ittProjDtlsToHome = new Intent(PMHomeActivity.this, PMProjectDetailActivity.class);
                ittProjDtlsToHome.putExtra("pageCount", 0);
                startActivity(ittProjDtlsToHome);
            }
        });

        complited_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ittProjDtlsToHome = new Intent(PMHomeActivity.this, PMProjectDetailActivity.class);
                ittProjDtlsToHome.putExtra("pageCount", 3);
                startActivity(ittProjDtlsToHome);
            }
        });

        approved_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ittProjDtlsToHome = new Intent(PMHomeActivity.this, PMProjectDetailActivity.class);
                ittProjDtlsToHome.putExtra("pageCount", 1);
                startActivity(ittProjDtlsToHome);
            }
        });

        tshirt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToFeesPaid = new Intent(PMHomeActivity.this, TShirtPaidActivity.class);
                itthomeToFeesPaid.putExtra("From", "Manager");
                v.getContext().startActivity(itthomeToFeesPaid);
            }
        });


        if (str_MangerImg.equals("null") || str_MangerImg.equals("anyType{}")) {
            iv_profilepic.setImageResource(R.drawable.devanand);
        } else {
            LoadGalleryImage loadGalleryImage = new LoadGalleryImage(this);
            loadGalleryImage.execute();
        }
        /*textCartItemCompliteCount = (TextView) findViewById(R.id.cart_badge);
        textCartItemApprovedCount = (TextView) findViewById(R.id.cart_badge2);
        textCartItemUnApprovedCount = (TextView) findViewById(R.id.cart_badge1);*/
        LinearLayout pending_count = (LinearLayout) findViewById(R.id.pending_count);

      /*  themeWiseProjCount_BT = (Button) findViewById(R.id.themeWiseProjCount_BT);
        themeWiseProjCount_BT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ittThemeWiseProjCount = new Intent(PMHomeActivity.this,PMGetThemeWiseProjectCount.class);
                ittThemeWiseProjCount.putExtra("MDId",MDId);
                startActivity(ittThemeWiseProjCount);
            }
        });*/

     /*   int widthsize1=500;
        int widthsize2=300;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;
        if (density == DisplayMetrics.DENSITY_HIGH) {
            //    Toast.makeText(getContext(), "DENSITY_HIGH... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
            if (density >= 230 || density <= 250) {
                widthsize1=300;
                widthsize2=200;
            }
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView recyclerView1 =(RecyclerView) findViewById(R.id.recyclerView_contact);

        ArrayList<DataModel> arrayList1=new ArrayList<>();
        arrayList1=new ArrayList<>();
        arrayList1.add(new DataModel("Fees", R.drawable.fees_new, "#ffbc00"));
        arrayList1.add(new DataModel("ML & LA", R.drawable.mlal_new, "#d94343"));
        arrayList1.add(new DataModel("Reports", R.drawable.report_new, "#1e7145"));
        arrayList1.add(new DataModel("News Feeds", R.drawable.newsfeeds_new, "#0a64c5"));
        arrayList1.add(new DataModel("Events", R.drawable.events_new, "#84795d"));
        arrayList1.add(new DataModel("Contact Us",R.drawable.contact_new,"#f42a2a"));*/
     /*   ArrayList<DataModel> arrayList2=new ArrayList<>();
        arrayList2=new ArrayList<>();
        arrayList2.add(new DataModel("Pending",R.drawable.pending_home,"#3E51B1"));
        arrayList2.add(new DataModel("Approved",R.drawable.approve_home,"#3E51B1"));
        arrayList2.add(new DataModel("Completed",R.drawable.completed_home,"#3E51B1"));*/

      /*  arrayList = new ArrayList<>();
        arrayList.add(new DataModel("About LEAD", R.drawable.about_new, "#97c024"));
        arrayList.add(new DataModel("Projects", R.drawable.project_new, "#f58d00"));


        RecyclerViewAdapter adapter = new RecyclerViewAdapter(PMHomeActivity.this, arrayList, this);
        recyclerView.setAdapter(adapter);

        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, widthsize1);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter adapter1 = new RecyclerViewAdapter(PMHomeActivity.this, arrayList1, this);
        recyclerView1.setAdapter(adapter1);

        AutoFitGridLayoutManager layoutManager1 = new AutoFitGridLayoutManager(this, widthsize2);
        recyclerView1.setLayoutManager(layoutManager1);*/

    /*    RecyclerViewCountAdapter adapter2 = new RecyclerViewCountAdapter(PMHomeActivityNew.this, arrayList2,this);
        recyclerView2.setAdapter(adapter2);

        AutoFitGridLayoutManager layoutManager2 = new AutoFitGridLayoutManager(this, 300);
        recyclerView2.setLayoutManager(layoutManager2);*/

       /* gridview = (GridView) findViewById(R.id.customgrid);
        gridview.setAdapter(new ImageAdapter(this, osNameList, osImages));

*/


        //currently commented

       /* GetStudentCount getStudentCount = new GetStudentCount(this);
        getStudentCount.execute(MDId);

        GetCollegeCount getCollegeCount = new GetCollegeCount(this);
        getCollegeCount.execute(MDId);*/
        // currently commented




      /*  chart_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PMHomeActivityNew.this,AnalyticChartActivity.class);
                i.putExtra("UnapprovedCount",UnapprovedCount);
                i.putExtra("ComplitedCount",ComplitedCount);
                i.putExtra("ApprovedCount",ApprovedCount);
                i.putExtra("RequestForCompletion",RequestForCompletion);
                i.putExtra("RequestForModification",RequestForModification);
                i.putExtra("Rejected",Rejected);
                i.putExtra("str_studentcount",str_studentcount);
                i.putExtra("str_collegecount",str_collegecount);
                startActivity(i);
            }
        });


        chart_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PMHomeActivityNew.this,AnalyticChartActivity.class);
                i.putExtra("UnapprovedCount",UnapprovedCount);
                i.putExtra("ComplitedCount",ComplitedCount);
                i.putExtra("ApprovedCount",ApprovedCount);
                i.putExtra("RequestForCompletion",RequestForCompletion);
                i.putExtra("RequestForModification",RequestForModification);
                i.putExtra("Rejected",Rejected);
                i.putExtra("str_studentcount",str_studentcount);
                i.putExtra("str_collegecount",str_collegecount);
                startActivity(i);
            }
        });
        projectDetails_TV = (TextView) findViewById(R.id.projectDetails_TV);
        projectDetails_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToProject = new Intent(PMHomeActivityNew.this,PMProjectDetailActivity.class);
                itthomeToProject.putExtra("pageCount",0);
                Log.i("Tag","ManagerId homeintent="+MDId);
             *//*   itthomeToProject.putExtra("ManagerId",MDId);
                itthomeToProject.putExtra("ManagerName",str_MangerName);
                itthomeToProject.putExtra("PMEmailId",str_ManagerEmail);
                itthomeToProject.putExtra("PM_Mobile",str_ManagerMaobile);
                itthomeToProject.putExtra("PMLocation",str_ManagerLocation);
                itthomeToProject.putExtra("PMImgUrl",str_MangerImg);*//*
                startActivity(itthomeToProject);
            }
        });

        projectDetails_IV = (ImageView) findViewById(R.id.projectDetails_IV);
        projectDetails_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToProject = new Intent(PMHomeActivityNew.this,PMProjectDetailActivity.class);
                itthomeToProject.putExtra("pageCount",0);
                Log.i("Tag","ManagerId homeintent="+MDId);
              *//*  itthomeToProject.putExtra("ManagerId",MDId);
                itthomeToProject.putExtra("ManagerName",str_MangerName);
                itthomeToProject.putExtra("PMEmailId",str_ManagerEmail);
                itthomeToProject.putExtra("PM_Mobile",str_ManagerMaobile);
                itthomeToProject.putExtra("PMLocation",str_ManagerLocation);
                itthomeToProject.putExtra("PMImgUrl",str_MangerImg);*//*
                startActivity(itthomeToProject);
            }
        });

        events_TV = (TextView) findViewById(R.id.events_TV);
        events_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToEvent = new Intent(PMHomeActivityNew.this,EventsActivity.class);
                startActivity(itthomeToEvent);
            }
        });
        events_IV = (ImageView) findViewById(R.id.events_IV);
        events_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToEvent = new Intent(PMHomeActivityNew.this,EventsActivity.class);
                startActivity(itthomeToEvent);
            }
        });
        news_TV = (TextView) findViewById(R.id.news_TV);
        news_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToNewsFeeds = new Intent(PMHomeActivityNew.this,NewsFeedsActivity.class);
                startActivity(itthomeToNewsFeeds);
            }
        });
        news_IV = (ImageView) findViewById(R.id.news_IV);
        news_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToNewsFeeds = new Intent(PMHomeActivityNew.this,NewsFeedsActivity.class);
                startActivity(itthomeToNewsFeeds);
            }
        });

        about_TV = (TextView) findViewById(R.id.about_TV);
        about_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(PMHomeActivityNew.this,AboutUsActivity.class);
                itthomeToAboutUs.putExtra("From","Manager");
                startActivity(itthomeToAboutUs);
            }
        });

        about_IV = (ImageView) findViewById(R.id.about_IV);
        about_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(PMHomeActivityNew.this,AboutUsActivity.class);
                itthomeToAboutUs.putExtra("From","Manager");
                startActivity(itthomeToAboutUs);
            }
        });

        contactus_TV = (TextView) findViewById(R.id.contactus_TV);
        contactus_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(PMHomeActivityNew.this,ContactUsActivity.class);
                itthomeToAboutUs.putExtra("From","Manager");
                startActivity(itthomeToAboutUs);
            }
        });

        contactus_IV = (ImageView) findViewById(R.id.contactus_IV);
        contactus_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(PMHomeActivityNew.this,ContactUsActivity.class);
                itthomeToAboutUs.putExtra("From","Manager");
                startActivity(itthomeToAboutUs);
            }
        });

        request_TV = (TextView) findViewById(R.id.request_TV);
        request_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(PMHomeActivityNew.this,RequestActivity.class);
                itthomeToAboutUs.putExtra("From","Manager");
                startActivity(itthomeToAboutUs);
            }
        });

        request_IV = (ImageView) findViewById(R.id.request_IV);
        request_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(PMHomeActivityNew.this,RequestActivity.class);
                itthomeToAboutUs.putExtra("From","Manager");
                startActivity(itthomeToAboutUs);
            }
        });
*/

       /* StateProgressBar stateProgressBar = (StateProgressBar) findViewById(R.id.usage_stateprogressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
*/


        swipeToRefresh_pmhomeactivity.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {


            @Override
            public void onRefresh() {

                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (isInternetPresent) {

                    // implement Handler to wait for 2 seconds and then update UI
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // cancle the Visual indication of a refresh
                        /*try {
                            wait(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                            swipeToRefresh_pmhomeactivity.setRefreshing(false);

                            GetLoginDetails getloginDetails = new GetLoginDetails(PMHomeActivity.this);
                            getloginDetails.execute();
                            //Toast.makeText(getApplicationContext(),"net"+isInternetPresent.toString(),Toast.LENGTH_SHORT).show();


                            AsyncCallFCM task = new AsyncCallFCM(PMHomeActivity.this);
                            task.execute();


                        }
                    }, 500);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                }

            }


        });


    }// end of OnCreate()

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

            AlertDialog.Builder adb = new AlertDialog.Builder(PMHomeActivity.this);
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

            //finish();
        }
    }


    public class LoadGalleryImage extends AsyncTask<Void, Object, Bitmap> {

        private Context context;
        //   private ProgressBar progressBar;

        LoadGalleryImage(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            //  progressBar = (ProgressBar) findViewById(R.id.progressBar);
            //  progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            ArrayList<Bitmap> bitmapLst = null;
            Bitmap bitmaplogo = null;

            try {
                URL url = new URL(str_MangerImg);
                Log.d("Urlssssssssssss", url.toString());
                bitmaplogo = BitmapFactory.decodeStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (OutOfMemoryError ex) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return bitmaplogo;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // bitmapList.add(bitmap);
            iv_profilepic.setImageBitmap(bitmap);
            //  progressBar.setVisibility(GONE);
        }
    }

    private void setupComplited() {

        if (textCartItemCompliteCount != null) {
            if (Integer.parseInt(ComplitedCount) == 0) {
                if (textCartItemCompliteCount.getVisibility() != View.GONE) {
                    textCartItemCompliteCount.setVisibility(View.GONE);
                }
            } else {
                //  textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 110)));
                textCartItemCompliteCount.setText(ComplitedCount);
                if (textCartItemCompliteCount.getVisibility() != View.VISIBLE) {
                    textCartItemCompliteCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setupUnapproved() {

        if (textCartItemUnApprovedCount != null) {
            if (Integer.parseInt(UnapprovedCount) == 0) {
                if (textCartItemUnApprovedCount.getVisibility() != View.GONE) {
                    textCartItemUnApprovedCount.setVisibility(View.GONE);
                }
            } else {
                //  textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 110)));
                textCartItemUnApprovedCount.setText(UnapprovedCount);
                if (textCartItemUnApprovedCount.getVisibility() != View.VISIBLE) {
                    textCartItemUnApprovedCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void setupApproved() {

        if (textCartItemApprovedCount != null) {
            if (Integer.parseInt(ApprovedCount) == 0) {
                if (textCartItemApprovedCount.getVisibility() != View.GONE) {
                    textCartItemApprovedCount.setVisibility(View.GONE);
                }
            } else {
                //  textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 110)));
                textCartItemApprovedCount.setText(ApprovedCount);
                if (textCartItemApprovedCount.getVisibility() != View.VISIBLE) {
                    textCartItemApprovedCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }




    /*@Override
    public void onItemClick(DataModel item) {

        String itemName=item.text;
      //  Toast.makeText(getApplicationContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
        if(itemName.equals("About LEAD")){
            Intent itthomeToAboutUs = new Intent(PMHomeActivity.this,AboutUsActivity.class);
            itthomeToAboutUs.putExtra("From","Manager");
            startActivity(itthomeToAboutUs);
        }
        if(itemName.equals("Projects")){
            Intent itthomeToProject = new Intent(PMHomeActivity.this,PMProjectDetailActivity.class);
            itthomeToProject.putExtra("pageCount",0);
            startActivity(itthomeToProject);
        }
        if(itemName.equals("Fees")){
            Intent itthomeToFeesPaid = new Intent(PMHomeActivity.this,FeesPaidActivity.class);
            itthomeToFeesPaid.putExtra("From","Manager");
            startActivity(itthomeToFeesPaid);
        }
        if(itemName.equals("ML & LA")){
            Intent itthomeToAboutUs = new Intent(PMHomeActivity.this,RequestActivity.class);
            itthomeToAboutUs.putExtra("From","Manager");
            startActivity(itthomeToAboutUs);
        }
        if(itemName.equals("Reports")){
            Intent i = new Intent(PMHomeActivity.this,AnalyticChartActivity.class);
            i.putExtra("UnapprovedCount",UnapprovedCount);
            i.putExtra("ComplitedCount",ComplitedCount);
            i.putExtra("ApprovedCount",ApprovedCount);
            i.putExtra("RequestForCompletion",RequestForCompletion);
            i.putExtra("RequestForModification",RequestForModification);
            i.putExtra("Rejected",Rejected);
            i.putExtra("str_studentcount",str_studentcount);
            i.putExtra("str_collegecount",str_collegecount);
            startActivity(i);
        }
        if(itemName.equals("News Feeds")){
            Intent itthomeToNewsFeeds = new Intent(PMHomeActivity.this,NewsFeedsActivity.class);
            startActivity(itthomeToNewsFeeds);
        }
        if(itemName.equals("Events")){
            Intent itthomeToEvent = new Intent(PMHomeActivity.this,EventsActivity.class);
            startActivity(itthomeToEvent);
        }
        if(itemName.equals("Contact Us")){
            Intent itthomeToAboutUs = new Intent(PMHomeActivity.this,ContactUsActivity.class);
            itthomeToAboutUs.putExtra("From","Manager");
            startActivity(itthomeToAboutUs);
        }


    }*/


    public class GetProjectCount extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        //ProgressBar progressBar;

        GetProjectCount(Context ctx) {
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params[0];
            //SoapObject response = getStudDtls(leadid);

            String METHOD_NAME = "GetProjectCount";
            String NamespaceMail = "http://mis.leadcampus.org/", SOAP_ACTION1 = "http://mis.leadcampus.org/GetProjectCount";//namespace+methodname

            try {
                //mis.leadcampus.org

                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

                request.addProperty("ManagerId", ManagerId);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");


                //anyType{Counts=1; ProjectStatus=Approved; Status=Success; };

                try {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("tag", "soap response project count" + response.toString());

                    int int_projectcount = response.getPropertyCount();

                    Log.e("projectcount", Integer.toString(int_projectcount));

                    for (int i = 0; i < int_projectcount; i++) {
                        SoapObject innerresponse = (SoapObject) response.getProperty(i);
                        str_responsecount = innerresponse.getProperty("Counts").toString();
                        str_responseStatus = innerresponse.getProperty("ProjectStatus").toString();
                        str_TshirtStatus = innerresponse.getProperty("TshirtStatus").toString();
                        str_TshirtRequestCount = innerresponse.getProperty("TshirtRequestCount").toString();

                        Log.e("tag", "str_TshirtRequestCount:" + str_TshirtRequestCount);
                        if (str_responseStatus.equals("Approved")) {
                            ApprovedCount = str_responsecount;
                        } else if (str_responseStatus.equals("Completed")) {
                            ComplitedCount = str_responsecount;
                        } else if (str_responseStatus.equals("Proposed")) {
                            UnapprovedCount = str_responsecount;
                        } else if (str_responseStatus.equals("Rejected")) {
                            Rejected = str_responsecount;
                        } else if (str_responseStatus.equals("RequestForCompletion")) {
                            RequestForCompletion = str_responsecount;
                        } else if (str_responseStatus.equals("RequestForModification")) {
                            RequestForModification = str_responsecount;
                        }

                        Log.e("innerresponse", str_responsecount + str_responseStatus);
                    }
                    Log.e("innerresponse", str_responsecount + str_responseStatus);

                    String Counts = response.getProperty(0).toString();
                    Log.e("Counts", Counts);
                    Log.e("ApprovedCount", ApprovedCount);
                    Log.e("ComplitedCount", ComplitedCount);
                    Log.e("UnapprovedCount", UnapprovedCount);



                   /* String Counts=response.getProperty("Counts").toString();
                       String ProjectStatus=response.getProperty("ProjectStatus").toString();

                      Log.d("Counts",Counts);
                      Log.d("ProjectStatus",ProjectStatus);*/

                    return response;

                } catch (Exception t) {
                    Log.e("request fail PCount", "> " + t.getMessage().toString());
                }
            } catch (Exception t) {
                Log.d("exception outside", t.getMessage().toString());
            }

            //Log.d("Soap response is",response.toString());

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if (result != null) {
                progressBar.setVisibility(View.GONE);
                compeleted_tv.setText(ComplitedCount);
                pending_tv.setText(UnapprovedCount);
                approved_tv.setText(ApprovedCount);
                tshirt_tv.setText(str_TshirtRequestCount);

                shardprefPM_obj_count.getString(PrefID_ComCount, "").trim();
                completed_str = shardprefPM_obj_count.getString(PrefID_ComCount, "").trim();
                Log.d("completed_str:", completed_str);
                //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
                complited_count = Integer.parseInt(completed_str);

                shardprefPM_obj_count.getString(PrefID_AppCount, "").trim();
                approved_str = shardprefPM_obj_count.getString(PrefID_AppCount, "").trim();
                Log.d("approved_str:", approved_str);
                //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
                approved_count = Integer.parseInt(approved_str);

                shardprefPM_obj_count.getString(PrefID_UNCount, "").trim();
                pending_str = shardprefPM_obj_count.getString(PrefID_UNCount, "").trim();
                Log.d("pending_str:", pending_str);
                pending_count = Integer.parseInt(pending_str);

                shardprefPM_obj_count.getString(PrefID_Tshirt, "").trim();
                tshirt_str = shardprefPM_obj_count.getString(PrefID_Tshirt, "").trim();
                Log.d("tshirt_str:", tshirt_str);
                tshirt_count = Integer.parseInt(tshirt_str);

                if (approved_count < Integer.valueOf(ApprovedCount)) {
                    Animation startAnimation = AnimationUtils.loadAnimation(PMHomeActivity.this, R.anim.blinking_animation);
                    approved_tv.startAnimation(startAnimation);
                    approved_img.startAnimation(startAnimation);

                    editor_PM_count = shardprefPM_obj_count.edit();
                    editor_PM_count.putString(PrefID_AppCount, ApprovedCount);
                    Log.d("ApprovedCount", ApprovedCount);
                   /* editor_PM_count.putString(PrefID_ComCount,ComplitedCount);
                    editor_PM_count.putString(PrefID_RejCount,Rejected);
                    editor_PM_count.putString(PrefID_UNCount,UnapprovedCount);
                    editor_PM_count.putString(PrefID_ReqComCount,RequestForCompletion);
                    editor_PM_count.putString(PrefID_ReqModCount,RequestForModification);*/
                    editor_PM_count.commit();

                } else if (approved_count == Integer.valueOf(ApprovedCount)) {
                    approved_tv.clearAnimation();
                    approved_img.clearAnimation();
                }

                if (complited_count < Integer.valueOf(ComplitedCount)) {
                    Animation startAnimation = AnimationUtils.loadAnimation(PMHomeActivity.this, R.anim.blinking_animation);
                    compeleted_tv.startAnimation(startAnimation);
                    complited_img.startAnimation(startAnimation);

                    editor_PM_count = shardprefPM_obj_count.edit();
                    editor_PM_count.putString(PrefID_ComCount, ComplitedCount);
                    editor_PM_count.commit();
                } else if (complited_count == Integer.valueOf(ComplitedCount)) {
                    compeleted_tv.clearAnimation();
                    complited_img.clearAnimation();
                }

                if (pending_count < Integer.valueOf(UnapprovedCount)) {
                    Animation startAnimation = AnimationUtils.loadAnimation(PMHomeActivity.this, R.anim.blinking_animation);
                    pending_tv.startAnimation(startAnimation);
                    pending_img.startAnimation(startAnimation);

                    editor_PM_count = shardprefPM_obj_count.edit();
                    editor_PM_count.putString(PrefID_UNCount, UnapprovedCount);
                    //    editor_PM_count.putString(PrefID_AppCount,ApprovedCount);
                    // editor_PM_count.putString(PrefID_ComCount,ComplitedCount);
                   /* editor_PM_count.putString(PrefID_RejCount,Rejected);
                    editor_PM_count.putString(PrefID_UNCount,UnapprovedCount);
                    editor_PM_count.putString(PrefID_ReqComCount,RequestForCompletion);
                    editor_PM_count.putString(PrefID_ReqModCount,RequestForModification);*/
                    editor_PM_count.commit();
                } else if (pending_count == Integer.valueOf(UnapprovedCount)) {
                    pending_tv.clearAnimation();
                    pending_img.clearAnimation();
                }

                if (tshirt_count < Integer.valueOf(str_TshirtRequestCount)) {
                    Animation startAnimation = AnimationUtils.loadAnimation(PMHomeActivity.this, R.anim.blinking_animation);
                    tshirt_tv.startAnimation(startAnimation);
                    tshirt_img.startAnimation(startAnimation);

                    editor_PM_count = shardprefPM_obj_count.edit();
                    editor_PM_count.putString(PrefID_Tshirt, str_TshirtRequestCount);
                    editor_PM_count.commit();
                } else if (tshirt_count == Integer.valueOf(str_TshirtRequestCount)) {
                    tshirt_tv.clearAnimation();
                    tshirt_img.clearAnimation();
                }

                Log.e("ApprovedCount1", ApprovedCount);
                Log.e("ComplitedCount1", ComplitedCount);
                Log.e("UnapprovedCount1", UnapprovedCount);
                Log.e("approved_count", String.valueOf(approved_count));
                Log.e("pending_count", String.valueOf(pending_count));
                Log.e("complited_count", String.valueOf(complited_count));
            }


           /* setupComplited();
            setupApproved();
            setupUnapproved();*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    public class GetStudentCount extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        //ProgressBar progressBar;

        GetStudentCount(Context ctx) {
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params[0];
            //SoapObject response = getStudDtls(leadid);

            String METHOD_NAME = "GetStudentcount";
            String NamespaceMail = "http://mis.leadcampus.org/", SOAP_ACTION1 = "http://mis.leadcampus.org/GetStudentcount";//namespace+methodname

            try {
                //mis.leadcampus.org

                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

                request.addProperty("ManagerId", ManagerId);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");


                //anyType{Counts=1; ProjectStatus=Approved; Status=Success; };

                try {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("tag", "soap response GetStudentcount" + response.toString());

                    int int_projectcount = response.getPropertyCount();

                    Log.e("projectcount", Integer.toString(int_projectcount));

                    //  String str_Status=response.getProperty("Status").toString();

                    for (int i = 0; i < int_projectcount; i++) {
                        SoapObject innerresponse = (SoapObject) response.getProperty(i);

                        str_studentcount = innerresponse.getProperty("Counts").toString();
                        Log.i("str_studentcount=", str_studentcount);

                   /*     if(str_responseStatus.equals("Approved")){
                            ApprovedCount=str_responsecount;
                        }
                        else if(str_responseStatus.equals("Completed")){
                            ComplitedCount=str_responsecount;
                        }
                        else if(str_responseStatus.equals("Proposed")){
                            UnapprovedCount=str_responsecount;
                        }
                        else if(str_responseStatus.equals("Rejected")){
                            Rejected=str_responsecount;
                        }
                        else if(str_responseStatus.equals("RequestForCompletion")){
                            RequestForCompletion=str_responsecount;
                        }
                        else if(str_responseStatus.equals("RequestForModification")){
                            RequestForModification=str_responsecount;
                        }*/

                        Log.e("innerresponse", str_responsecount);
                    }
                    Log.e("innerresponse", str_responsecount);

                    String Counts = response.getProperty(0).toString();
                    Log.e("Counts", Counts);


                   /* String Counts=response.getProperty("Counts").toString();
                       String ProjectStatus=response.getProperty("ProjectStatus").toString();

                      Log.d("Counts",Counts);
                      Log.d("ProjectStatus",ProjectStatus);*/

                    // return response;

                } catch (Exception t) {
                    Log.e("request fail PCount", "> " + t.getMessage().toString());
                }
            } catch (Exception t) {
                Log.d("exception outside", t.getMessage().toString());
            }

            //Log.d("Soap response is",response.toString());

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            progressBar.setVisibility(View.GONE);
            /*if(result==null){
                Toast.makeText(PMHomeActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            }*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class GetCollegeCount extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        //ProgressBar progressBar;

        GetCollegeCount(Context ctx) {
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params[0];
            //SoapObject response = getStudDtls(leadid);

            String METHOD_NAME = "GetManagerCollegeCount";
            String NamespaceMail = "http://mis.leadcampus.org/", SOAP_ACTION1 = "http://mis.leadcampus.org/GetManagerCollegeCount";//namespace+methodname

            try {
                //mis.leadcampus.org

                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

                request.addProperty("ManagerId", ManagerId);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");


                //anyType{Counts=1; ProjectStatus=Approved; Status=Success; };

                try {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("tag", "soap response GetManagerCollegeCount" + response.toString());

                    int int_projectcount = response.getPropertyCount();

                    Log.e("projectcount", Integer.toString(int_projectcount));

                    //  String str_Status=response.getProperty("Status").toString();

                    for (int i = 0; i < int_projectcount; i++) {
                        SoapObject innerresponse = (SoapObject) response.getProperty(i);

                        str_collegecount = innerresponse.getProperty("Counts").toString();
                        Log.i("str_collegecount=", str_collegecount);

                   /*     if(str_responseStatus.equals("Approved")){
                            ApprovedCount=str_responsecount;
                        }
                        else if(str_responseStatus.equals("Completed")){
                            ComplitedCount=str_responsecount;
                        }
                        else if(str_responseStatus.equals("Proposed")){
                            UnapprovedCount=str_responsecount;
                        }
                        else if(str_responseStatus.equals("Rejected")){
                            Rejected=str_responsecount;
                        }
                        else if(str_responseStatus.equals("RequestForCompletion")){
                            RequestForCompletion=str_responsecount;
                        }
                        else if(str_responseStatus.equals("RequestForModification")){
                            RequestForModification=str_responsecount;
                        }*/

                        Log.e("innerresponse", str_responsecount);
                    }
                    Log.e("innerresponse", str_responsecount);

                    String Counts = response.getProperty(0).toString();
                    Log.e("Counts", Counts);


                   /* String Counts=response.getProperty("Counts").toString();
                       String ProjectStatus=response.getProperty("ProjectStatus").toString();

                      Log.d("Counts",Counts);
                      Log.d("ProjectStatus",ProjectStatus);*/

                    // return response;

                } catch (Exception t) {
                    Log.e("request fail PCount", "> " + t.getMessage().toString());
                }
            } catch (Exception t) {
                Log.d("exception outside", t.getMessage().toString());
            }

            //Log.d("Soap response is",response.toString());

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            progressBar.setVisibility(View.GONE);
            /*if(result==null){
                Toast.makeText(PMHomeActivity.this, "Web Service Error", Toast.LENGTH_SHORT).show();
            }*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.findItem(R.id.action_feedback)
                .setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_editProfile) {
            Intent itthomeToEditProfile = new Intent(PMHomeActivity.this, PMEditProfileActivity.class);
            //  itthomeToEditProfile.putExtra("ManagerId",MDId);
            startActivity(itthomeToEditProfile);
            return true;
        }

        if( id == R.id.changepwd){
            final Dialog dialog = new Dialog(PMHomeActivity.this);


            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.change_pwd);
            dialog.setCancelable(false);


            Button forgotpasswordcancel_bt = (Button) dialog.findViewById(R.id.forgotpasswordcancel_bt);
            Button forgotpasswordsubmit_bt = (Button) dialog.findViewById(R.id.forgotpasswordsubmit_bt);
            dialognewpwd_et=(EditText) dialog.findViewById(R.id.dialognewpwd_et);
            dialogconfpwd_et=(EditText)dialog.findViewById(R.id.dialogconfpwd_et);

            forgotpasswordcancel_bt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    dialog.dismiss();

                }
            });

            forgotpasswordsubmit_bt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0)
                {

                    internetDectector = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector.isConnectingToInternet();

                    if (isInternetPresent)
                    {

                        str_newPwd=dialognewpwd_et.getText().toString();
                        String str_confPwd=dialogconfpwd_et.getText().toString();
                        if(str_newPwd.equalsIgnoreCase(str_confPwd)){
                            ChangePassword changePassword=new ChangePassword(PMHomeActivity.this);
                            changePassword.execute();
                        }

                        dialog.dismiss();

                    }
                    else{
                        Toast.makeText(PMHomeActivity.this, "Kindly connect to internet", Toast.LENGTH_SHORT).show();
                    }




                }
            });


            dialog.show();
        }

        if (id == R.id.action_logout) {
            editor_LoginTrack = shardprefLoginTrack_obj.edit();
            editor_LoginTrack.putString(PrefID_WhereToGo, config_obj.packagename + "LoginActivity");
            editor_LoginTrack.commit();

            Intent itthomeToLogin = new Intent(PMHomeActivity.this, LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == R.id.notification) {
            Intent itthomeToNotification = new Intent(PMHomeActivity.this, PMNotificationHistoryActivity.class);
            startActivity(itthomeToNotification);
            return true;
        }

        if (id == R.id.action_request) {
            Intent itthomeTorequest = new Intent(PMHomeActivity.this, PMRequestActivity.class);
            startActivity(itthomeTorequest);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        //finish();

        //   finish();
        /*if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            //return;
            finishAffinity();
            System.exit(0);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please press back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);*/

        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


    public class GetLoginDetails extends AsyncTask<String, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;


        GetLoginDetails(Context ctx) {
            context = ctx;
            progressDialog_pmswiperefresh = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(String... params) {
            //String mobilenum = (String) params [0];
            // String leadid = (String) params[1];
            //String versionCode = (String) params[2];

            SoapObject response = getLoginDetails();


            return response;
        }

        @Override
        protected void onPreExecute() {
/*            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog_pmswiperefresh.setMessage("Loading");
            progressDialog_pmswiperefresh.setCanceledOnTouchOutside(false);
            progressDialog_pmswiperefresh.show();

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            //progressBar.setVisibility(View.GONE);
            progressDialog_pmswiperefresh.dismiss();


            String finalResult = result.toString();
            String finals = finalResult.replace("anyType{}", "");
            Log.d("Finals is", finals);


            SoapPrimitive status = (SoapPrimitive) result.getProperty("Status");
            //Log.d("Status is:",status.toString());


            if (status.toString().equals("Invalid Username or Password")) {
                Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                //progressDialog.dismiss();
            }

            if (status.toString().equals("Success")) {
                SoapPrimitive role = (SoapPrimitive) result.getProperty("Role");
                Log.d("Role is:", role.toString());

                if (role.toString().equals("Manager")) {

                    /*AsyncCallFCM task = new AsyncCallFCM(LoginActivity.this);
                    task.execute();*/

                    Str_FCMName = result.getProperty("Username").toString().toString();
                    Str_Manager_Id = result.getProperty("ManagerId").toString();
                    Str_ManagerName = result.getProperty("Name").toString();
                    Str_PMEmailId = result.getProperty("MailId").toString();
                    Str_PM_Mobile = result.getProperty("MobileNo").toString();
                    Str_PMLocation = result.getProperty("Location").toString();
                    Str_PMImageURL = result.getProperty("UserImage").toString();
                    Log.i("tag", "PMImageURL=" + Str_PMImageURL);

                    String Imagestring = Str_PMImageURL;
                    String Str_PMImgUrl = null;
                    if (Imagestring.equals("null") || Imagestring.equals("anyType{}")) {
                        Str_PMImgUrl = "null";
                    } else {
                        String arr[] = Imagestring.split("/", 2);

                        String firstWord = arr[0];   //the
                        String secondWord = arr[1];

                        Str_PMImgUrl = serverPath + secondWord;
                        Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                        Log.i("tag", "PMImage1=" + Str_PMImageURL);
                        Log.i("tag", "PMImgUrl=" + Str_PMImgUrl);
                    }


                    ManagerId = Integer.parseInt(Str_Manager_Id);


                    editor_PM = shardprefPM_obj.edit();
                    editor_PM.putString(PrefID_PMID, Str_Manager_Id);
                    Log.d("ManagerIdis", Str_Manager_Id);
                    editor_PM.putString(PrefID_pmName, Str_ManagerName);
                    editor_PM.putString(PrefID_PMEMailID, Str_PMEmailId);
                    editor_PM.putString(PrefID_PMMobile, Str_PM_Mobile);
                    editor_PM.putString(PrefID_pmlocation, Str_PMLocation);
                    editor_PM.putString(PrefID_pmimageurl, Str_PMImgUrl);

                    editor_PM.putString(PrefID_pm_username, str_pm_username.toString());
                    editor_PM.putString(PrefID_pm_password, str_pm_password.toString());


                    Log.d("ManagerIds:", Str_Manager_Id);
                    Log.d("ManagerNames:", Str_ManagerName);
                    Log.d("ManagerEmailIds:", Str_PMEmailId);
                    Log.d("ManagerMobiles:", Str_PM_Mobile);
                    Log.d("Managerlocations:", Str_PMLocation);
                    Log.d("ManagerURLs:", Str_PMImgUrl);


                    editor_PM.commit();

                    /*editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefID_Role,role.toString());
                    editor_S.commit();*/

                    Intent ittLoginToEditProfile = new Intent(PMHomeActivity.this, PMHomeActivity.class);
                    startActivity(ittLoginToEditProfile);

                    // add
                }
            } else {
                Log.d("Status is: ", status.toString());
            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    private SoapObject getLoginDetails() {

        String METHOD_NAME = "ValidateLogin";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ValidateLogin";

        try {

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            String username = str_pm_username.toString();
            String password = str_pm_password.toString();
            request.addProperty("Username", username);
            request.addProperty("Password", password);

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
                // str_loginresponse = (String)response.getProperty("Status");
                Log.d("soap responseyyyyyyy", response.toString());

                return response;

            } catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
            }
        } catch (Exception t) {
            Log.d("exception outside", t.getMessage().toString());
        }
        return null;


    }


    public class ChangePassword extends AsyncTask<String, Void, SoapPrimitive> {

        Context context;
        //AlertDialog alertDialog;

        //private ProgressBar progressBar;

        private ProgressDialog progressDialog;

        ChangePassword(Context ctx) {
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(String... params) {

            SoapPrimitive response = ChangePassword();

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



            //   String str_result=result.getProperty("Status").toString();
            if(result.toString().equalsIgnoreCase("Success")){
                //    dialog.dismiss();
                progressDialog.dismiss();

                AlertDialog.Builder dialog = new AlertDialog.Builder(PMHomeActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.alert);


                dialog.setMessage(" Password is updated \n Thank you.");

                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


                final AlertDialog alert = dialog.create();
                alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                    }
                });
                alert.show();

            }
            else{

                progressDialog.dismiss();
                Toast toast = Toast.makeText(getApplicationContext(), "Unable to change password", Toast.LENGTH_LONG);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }

        }

    }
    private SoapPrimitive ChangePassword()
    {

        String METHOD_NAME = "Change_Password";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Change_Password";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            request.addProperty("User_Id", str_regId);//<Lead_Id>string</Lead_Id>
            request.addProperty("User_Type",str_role);
            request.addProperty("New_Password",str_newPwd);//<Email_id>string</Email_id>


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

            Log.e("tag","Request Change_Password="+request.toString());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                // Log.d("soapResponseyyyyyyy",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.d("tag","soapRespons Change_Password="+response.toString());

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


    private class AsyncCallFCM extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute() {
            Log.i("Leadmis", "onPreExecute---tab2");
           /* dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("Leadmis", "onProgressUpdate---tab2");
        }

        public AsyncCallFCM(PMHomeActivity activity) {
            context = activity;
            // dialog = new ProgressDialog(activity);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("Leadmis", "doInBackground");


            setGCM1();
            setGCM1();


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            /*if (dialog.isShowing()) {
                dialog.dismiss();

            }*/
            // Log.i(TAG, "onPostExecute");

           /* if(versionval)
            {	}else{alerts();}*/


        }
    }//end of AsynTask


   /* public void setGCM1() {


//

        // Fetch Device info

       *//* final TelephonyManager tm = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);*//*

        tm1 = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        //   final String tmDevice, tmSerial, androidId;
        String NetworkType;
        //TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        simOperatorName = tm1.getSimOperatorName();
        Log.v("Operator", "" + simOperatorName);
        NetworkType = "GPRS";


        int simSpeed = tm1.getNetworkType();
        if (simSpeed == 1)
            NetworkType = "Gprs";
        else if (simSpeed == 4)
            NetworkType = "Edge";
        else if (simSpeed == 8)
            NetworkType = "HSDPA";
        else if (simSpeed == 13)
            NetworkType = "LTE";
        else if (simSpeed == 3)
            NetworkType = "UMTS";
        else
            NetworkType = "Unknown";

        Log.v("SIM_INTERNET_SPEED", "" + NetworkType);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            tmDevice = UUID.randomUUID().toString();
        }else {
            tmDevice = "" + tm1.getDeviceId();
        }
        Log.v("DeviceIMEI", "" + tmDevice);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mobileNumber = "" + tm1.getLine1Number();
        Log.v("getLine1Number value", "" + mobileNumber);

        String mobileNumber1 = "" + tm1.getPhoneType();
        Log.v("getPhoneType value", "" + mobileNumber1);
        tmSerial = "" + tm1.getSimSerialNumber();
        //  Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        Log.v("androidId CDMA devices", "" + androidId);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        deviceId = deviceUuid.toString();
        //  Log.v("deviceIdUUID universally unique identifier", "" + deviceId);


        deviceModelName = Build.MODEL;
        Log.v("Model Name", "" + deviceModelName);
        deviceUSER = Build.USER;
        Log.v("Name USER", "" + deviceUSER);
        devicePRODUCT = Build.PRODUCT;
        Log.v("PRODUCT", "" + devicePRODUCT);
        deviceHARDWARE = Build.HARDWARE;
        Log.v("HARDWARE", "" + deviceHARDWARE);
        deviceBRAND = Build.BRAND;
        Log.v("BRAND", "" + deviceBRAND);
        myVersion = Build.VERSION.RELEASE;
        Log.v("VERSION.RELEASE", "" + myVersion);
        sdkVersion = Build.VERSION.SDK_INT;
        Log.v("VERSION.SDK_INT", "" + sdkVersion);
        sdkver = Integer.toString(sdkVersion);
        // Get display details

        Measuredwidth = 0;
        Measuredheight = 0;
        Point size = new Point();
        WindowManager w = getWindowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //   w.getDefaultDisplay().getSize(size);
            Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
            Measuredheight = w.getDefaultDisplay().getHeight();//size.y;
        } else {
            Display d = w.getDefaultDisplay();
            Measuredwidth = d.getWidth();
            Measuredheight = d.getHeight();
        }

        Log.v("SCREEN_Width", "" + Measuredwidth);
        Log.v("SCREEN_Height", "" + Measuredheight);


        regId = FirebaseInstanceId.getInstance().getToken();



        Log.e("regId_DeviceID", "" + regId);

*//*<username>string</username>
      <DeviceId>string</DeviceId>
      <OSVersion>string</OSVersion>
      <Manufacturer>string</Manufacturer>
      <ModelNo>string</ModelNo>
      <SDKVersion>string</SDKVersion>
      <DeviceSrlNo>string</DeviceSrlNo>
      <ServiceProvider>string</ServiceProvider>
      <SIMSrlNo>string</SIMSrlNo>
      <DeviceWidth>string</DeviceWidth>
      <DeviceHeight>string</DeviceHeight>
      <AppVersion>string</AppVersion>*//*

        //if (!regId.equals("")){
        if (2>1){
            String SOAP_ACTION1 = "http://mis.leadcampus.org/SaveDeviceDetails";
            String METHOD_NAME1 = "SaveDeviceDetails";
            String MAIN_NAMESPACE = "http://mis.leadcampus.org/";
            String URI = Class_URL.URL_Login.toString().trim();


            SoapObject request = new SoapObject(MAIN_NAMESPACE, METHOD_NAME1);

            //	request.addProperty("LeadId", Password1);
            request.addProperty("username",Str_FCMName );

            request.addProperty("DeviceId", regId);
            request.addProperty("OSVersion", myVersion);
            request.addProperty("Manufacturer", deviceBRAND);
            request.addProperty("ModelNo", deviceModelName);
            request.addProperty("SDKVersion", sdkver);
            request.addProperty("DeviceSrlNo", tmDevice);
            request.addProperty("ServiceProvider", simOperatorName);
            request.addProperty("SIMSrlNo", tmSerial);
            request.addProperty("DeviceWidth", Measuredwidth);
            request.addProperty("DeviceHeight", Measuredheight);
            request.addProperty("AppVersion", versioncode);
            //request.addProperty("AppVersion","4.0");


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            Log.e("deviceDetails Request","deviceDetail"+request.toString());
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URI);

            try {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                System.out.println("Device Res"+response);

                Log.i("sending device detail", response.toString());

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("err",e.toString());
            }
        }






    }//end of GCM()*/



    public void setGCM1() {


//

        // Fetch Device info

       /* final TelephonyManager tm = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);*/

        tm1 = (TelephonyManager) getBaseContext()
                .getSystemService(Context.TELEPHONY_SERVICE);

        //   final String tmDevice, tmSerial, androidId;
        String NetworkType;
        //TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
        simOperatorName = tm1.getSimOperatorName();
        Log.v("Operator", "" + simOperatorName);
        NetworkType = "GPRS";


        int simSpeed = tm1.getNetworkType();
        if (simSpeed == 1)
            NetworkType = "Gprs";
        else if (simSpeed == 4)
            NetworkType = "Edge";
        else if (simSpeed == 8)
            NetworkType = "HSDPA";
        else if (simSpeed == 13)
            NetworkType = "LTE";
        else if (simSpeed == 3)
            NetworkType = "UMTS";
        else
            NetworkType = "Unknown";

        Log.v("SIM_INTERNET_SPEED", "" + NetworkType);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //tmDevice = "" + tm1.getDeviceId();
        String tmDevice = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.v("DeviceIMEI", "" + tmDevice);
        mobileNumber = "" + tm1.getLine1Number();
        Log.v("getLine1Number value", "" + mobileNumber);

        String mobileNumber1 = "" + tm1.getPhoneType();
        Log.v("getPhoneType value", "" + mobileNumber1);
        // tmSerial = "" + tm1.getSimSerialNumber();
        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            tmSerial = "" + tMgr.getSimSerialNumber();
        }catch(Exception ex)
        {
            tmSerial="inaccessible";
        }



        //  Log.v("GSM devices Serial Number[simcard] ", "" + tmSerial);
        androidId = "" + Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.v("androidId CDMA devices", "" + androidId);
        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        deviceId = deviceUuid.toString();
        Log.v("deviceIdUUID", "" + deviceId);


        deviceModelName = Build.MODEL;
        Log.v("Model Name", "" + deviceModelName);
        deviceUSER = Build.USER;
        Log.v("Name USER", "" + deviceUSER);
        devicePRODUCT = Build.PRODUCT;
        Log.v("PRODUCT", "" + devicePRODUCT);
        deviceHARDWARE = Build.HARDWARE;
        Log.v("HARDWARE", "" + deviceHARDWARE);
        deviceBRAND = Build.BRAND;
        Log.v("BRAND", "" + deviceBRAND);
        myVersion = Build.VERSION.RELEASE;
        Log.v("VERSION.RELEASE", "" + myVersion);
        sdkVersion = Build.VERSION.SDK_INT;
        Log.v("VERSION.SDK_INT", "" + sdkVersion);
        sdkver = Integer.toString(sdkVersion);
        // Get display details

        Measuredwidth = 0;
        Measuredheight = 0;
        Point size = new Point();
        // WindowManager w = getWindowManager();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        /*int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //   w.getDefaultDisplay().getSize(size);
           /* Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
            Measuredheight = w.getDefaultDisplay().getHeight();//size.y;*/

            Measuredwidth = displaymetrics.widthPixels;//size.x;
            Measuredheight = displaymetrics.heightPixels;//size.y;
        } else {
            // Display d = w.getDefaultDisplay();
            /*Measuredwidth = d.getWidth();
            Measuredheight = d.getHeight();*/
            Measuredwidth = displaymetrics.widthPixels;//size.x;
            Measuredheight = displaymetrics.heightPixels;//size.y;
        }

        Log.v("SCREEN_Width", "" + Measuredwidth);
        Log.v("SCREEN_Height", "" + Measuredheight);


        regId = FirebaseInstanceId.getInstance().getToken();



        Log.e("regId_DeviceID", "" + regId);

/*<username>string</username>
      <DeviceId>string</DeviceId>
      <OSVersion>string</OSVersion>
      <Manufacturer>string</Manufacturer>
      <ModelNo>string</ModelNo>
      <SDKVersion>string</SDKVersion>
      <DeviceSrlNo>string</DeviceSrlNo>
      <ServiceProvider>string</ServiceProvider>
      <SIMSrlNo>string</SIMSrlNo>
      <DeviceWidth>string</DeviceWidth>
      <DeviceHeight>string</DeviceHeight>
      <AppVersion>string</AppVersion>*/

        //if (!regId.equals("")){
        if (2>1){
            // String WEBSERVICE_NAME = "http://dfhrms.cloudapp.net/PMSservice.asmx?WSDL";
            String SOAP_ACTION1 = "http://mis.leadcampus.org/SaveDeviceDetails";
            String METHOD_NAME1 = "SaveDeviceDetails";
            String MAIN_NAMESPACE = "http://mis.leadcampus.org/";
            String URI = Class_URL.URL_Login.toString().trim();


            SoapObject request = new SoapObject(MAIN_NAMESPACE, METHOD_NAME1);

            //	request.addProperty("LeadId", Password1);
            request.addProperty("username",Str_FCMName );

            request.addProperty("DeviceId", regId);
            request.addProperty("OSVersion", myVersion);
            request.addProperty("Manufacturer", deviceBRAND);
            request.addProperty("ModelNo", deviceModelName);
            request.addProperty("SDKVersion", sdkver);
            request.addProperty("DeviceSrlNo", tmDevice);
            request.addProperty("ServiceProvider", simOperatorName);
            request.addProperty("SIMSrlNo", tmSerial);
            request.addProperty("DeviceWidth", Measuredwidth);
            request.addProperty("DeviceHeight", Measuredheight);
            request.addProperty("AppVersion", versioncode);
            //request.addProperty("AppVersion","4.0");


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            // Set output SOAP object
            envelope.setOutputSoapObject(request);
            Log.e("deviceDetails Request","deviceDetail"+request.toString());
            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URI);

            try {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                System.out.println("Device Res"+response);

                Log.i("sending device detail", response.toString());

            } catch (Exception e) {
                e.printStackTrace();
                Log.i("err",e.toString());
            }
        }






    }//end of GCM()



}// end of class
