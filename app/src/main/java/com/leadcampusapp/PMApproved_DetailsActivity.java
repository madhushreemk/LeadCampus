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
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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


public class PMApproved_DetailsActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    private static final String TAG = "Tag";

    String material[]={"Books","Digital camera","Pen","Mouse","xyz"};
    String cost[]={"100","200","300","400"};

  //  private AppCompatSpinner spin_projtype;
    EditText edt_title,edt_noOfBeneficiaries,edt_fundamount,edt_objectives,edt_actionPlan,edt_amount,edt_balance_amount,edt_disperseamount,edt_pmcomment,edt_placeofImt,edt_whoareBeneficiaries;
    TextView txt_TotalCost,txt_projectType,txt_studentid,txt_mobileno;
    ImageView btn_submit,img_student,btn_reject;

    String PMImgUrl;
    private String serverPath = Class_URL.ServerPath.toString().trim();
    Context context;
    String Lead_Id,ProjectId;
    int Project_Id,ManagerId;
    private String str_totalCost = null;
    private ArrayList<String> lstMaterialName = new ArrayList<String>();
    private ArrayList<String> lstMaterialCost = new ArrayList<String>();

    private ArrayList<String> lstMemberName = new ArrayList<String>();
    private ArrayList<String> lstMemberEmail = new ArrayList<>();
    String Student_Image_Path;

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    SharedPreferences shardprefPM_obj;
    String str_MangerID;
    Integer MDId;

    Integer DisAmtint;



    static TextView pmapproved_clickstartproject_editdate_tv;
    static TextView pmapproved_clickendproject_editdate_tv;
    static String yyyyMMdd_pmapproved_startdate = "";
    static String yyyyMMdd_pmapproved_enddate = "";

    private static TextView pmapproved_numberofdays_tv;
    static LinearLayout pmapproved_studentdays_linearlayout;

    static Class_alert_msg obj_class_alert_msg;
    static Context static_context;

    TextView pmapprov_collegename_tv,pmapprov_streamname_tv;
    String str_pmapprov_collegename="",str_pmapprov_streamname="";


    CheckBox pmapprov_impactproject_cb;
    int int_approved_impactproject=0;

    public Button pm_chat_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved__details);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Dispersement Amount");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        obj_class_alert_msg = new Class_alert_msg(getApplicationContext());
        static_context=getApplicationContext();

        Intent intent = getIntent();
        String Name = intent.getStringExtra("name");
         Lead_Id = intent.getStringExtra("Lead_Id");
         ProjectId = intent.getStringExtra("ProjectId");
        String DisAmt=intent.getStringExtra("DisAmt");
        String MobileNo=intent.getStringExtra("MobileNo");
        Student_Image_Path=intent.getStringExtra("Student_Image_Path");

        str_pmapprov_collegename=intent.getStringExtra("pmapprove_collegenamelist");
       str_pmapprov_streamname=intent.getStringExtra("pmapprove_streamnamelist");

        DisAmtint=Integer.parseInt(DisAmt);
        Project_Id=Integer.parseInt(ProjectId);

        shardprefPM_obj= getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);

      /*  ManagerId = getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMApproved="+ManagerId);
*/
        Log.e(TAG,"name="+Name);
        Log.e(TAG,"ProjectId="+ProjectId);
        Log.e(TAG,"Lead_Id="+Lead_Id);

     //   spin_projtype = (AppCompatSpinner) findViewById(R.id.spin_projectType);

        TextView name_tv= (TextView) findViewById(R.id.txt_name);
        name_tv.setText(Name);
        txt_studentid=(TextView) findViewById(R.id.txt_studentid);
        txt_studentid.setText(Lead_Id);

        txt_mobileno=(TextView) findViewById(R.id.txt_mobileno);
        txt_mobileno.setText(MobileNo);
        //setProjectTypeSpinner();

        img_student=(ImageView) findViewById(R.id.img_student);
        edt_actionPlan=(EditText) findViewById(R.id.edt_actionplan);
        edt_amount=(EditText)findViewById(R.id.edt_amount);
        edt_noOfBeneficiaries=(EditText)findViewById(R.id.edt_noOfBeneficiaries);
        edt_objectives=(EditText)findViewById(R.id.edt_objective);
        edt_title=(EditText)findViewById(R.id.edt_title);
        edt_balance_amount=(EditText)findViewById(R.id.edt_balance_amount);
        edt_disperseamount=(EditText)findViewById(R.id.edt_disperseamount);
        edt_pmcomment=(EditText)findViewById(R.id.edt_pmcomment);
        edt_fundamount=(EditText)findViewById(R.id.edt_fundamount);
        edt_placeofImt=(EditText) findViewById(R.id.edt_placeOfImpl);
        edt_whoareBeneficiaries=(EditText) findViewById(R.id.edt_whoareBeneficiaries);
       // txt_TotalCost=(TextView) findViewById(R.id.totalCost);
        txt_projectType=(TextView) findViewById(R.id.txt_projectType);

        pmapprov_collegename_tv=(TextView) findViewById(R.id.pmapprov_collegename_tv);
                pmapprov_streamname_tv=(TextView) findViewById(R.id.pmapprov_streamname_tv);



        pmapproved_clickstartproject_editdate_tv = (TextView) findViewById(R.id.pmapproved_clickstartproject_editdate_tv);
        pmapproved_clickendproject_editdate_tv = (TextView) findViewById(R.id.pmapproved_clickendproject_editdate_tv);

        pmapproved_numberofdays_tv=(TextView)findViewById(R.id.pmapproved_numberofdays_tv);
        pmapproved_studentdays_linearlayout=(LinearLayout)findViewById(R.id.pmapproved_studentdays_linearlayout);

        pmapprov_impactproject_cb=(CheckBox)findViewById(R.id.pmapprov_impactproject_cb);

        pm_chat_txt=(Button) findViewById(R.id.pm_chat_txt);


        btn_submit=(ImageView) findViewById(R.id.btn_submit);
        btn_reject=(ImageView) findViewById(R.id.btn_reject);
        edt_title.setEnabled(false);
        edt_objectives.setEnabled(false);
        edt_noOfBeneficiaries.setEnabled(false);
        edt_amount.setEnabled(false);
        edt_actionPlan.setEnabled(false);
        edt_balance_amount.setEnabled(false);
        edt_fundamount.setEnabled(false);
        edt_whoareBeneficiaries.setEnabled(false);
        edt_placeofImt.setEnabled(false);

        edt_fundamount.setText(DisAmt);

        String Imagestring = Student_Image_Path;
        if (Imagestring==null||Imagestring.equals("null") || Imagestring.equals("anyType{}")) {
            // PMImgUrl="null";
            img_student.setImageResource(R.drawable.devanand);
        } else {
            String arr[] = Imagestring.split("/", 2);

            String firstWord = arr[0];   //the
            String secondWord = arr[1];

            PMImgUrl = serverPath + secondWord;

            Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
            LoadGalleryImage loadGalleryImage = new LoadGalleryImage(PMApproved_DetailsActivity.this);
            loadGalleryImage.execute();
        }
        txt_mobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_mobileno.getText().toString()));
                startActivity(intent);
            }
        });



        pmapprov_collegename_tv.setText(str_pmapprov_collegename);
        pmapprov_streamname_tv.setText(str_pmapprov_streamname);


     //   Integer approved_amt= Integer.parseInt(edt_amount.getText().toString());
       // Log.i("tag","approved_amt="+approved_amt);
     //   String ApprovedAmt=edt_amount.getText().toString();

     /*   edt_disperseamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer v1 = Integer.parseInt(!edt_amount.getText().toString().isEmpty() ?
                        edt_amount.getText().toString() : "0");
                Integer v2 = Integer.parseInt(!edt_disperseamount.getText().toString().isEmpty() ?
                        edt_disperseamount.getText().toString() : "0");
                Integer value = v1 - v2;
                edt_balance_amount.setText(value.toString());
            }
        });
*/
        ViewProject viewProject = new ViewProject(context);
        viewProject.execute();




  //-----------------date------------------

        pmapproved_clickstartproject_editdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment fromdateFragment = new DatePickerFragmentFromDate();
                fromdateFragment.show(getFragmentManager(), "Date Picker");

            }
        });


        pmapproved_clickendproject_editdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // settodate();
                DialogFragment dFragment = new DatePickerFragmentEndDate();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");


            }
        });






        //--------------date------------------------

        pm_chat_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("tag","ProjectId chat=="+ProjectId);
                Intent i=new Intent(PMApproved_DetailsActivity.this,ChatActivity.class);
                i.putExtra("projectStatus","approved");
                i.putExtra("projectId",ProjectId);
                i.putExtra("userType","Manager");
                startActivity(i);
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validation_ReApply())
                {
                    String ApprovedAmt=edt_amount.getText().toString();
                    String DisperseAmt=edt_disperseamount.getText().toString();
                    String BalanceAmt=edt_balance_amount.getText().toString();
                    Integer BalanceAmtInt=Integer.parseInt(BalanceAmt);
                    Integer DisperseAmtInt=Integer.parseInt(DisperseAmt);
                    Integer ApprovedAmtInt=Integer.parseInt(ApprovedAmt);
                    Log.e("tag","ApprovedAmt="+ApprovedAmt+"DisperseAmt="+DisperseAmt);
                    Log.e("tag","DisperseAmtInt="+DisperseAmtInt+"ApprovedAmtInt="+ApprovedAmtInt);

                    if(DisperseAmtInt>BalanceAmtInt)
                    {
                    Toast.makeText(getApplicationContext()," Dispersed amount cannot be more than balance amount",Toast.LENGTH_LONG).show();
                }
                else {
                        btn_submit.setVisibility(View.GONE);
                    SubmitApprovedDetails submitApprovedDetails = new SubmitApprovedDetails(context);
                    submitApprovedDetails.execute();
                }

                }

            }
        });

        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( edt_pmcomment.getText().toString().length() == 0 ){
                    edt_pmcomment.setError( "Manager comments required!" );}
                else {
                    SubmitRejectDetails submitRejectDetails = new SubmitRejectDetails(context);
                    submitRejectDetails.execute();
                }

            }
        });



        pmapprov_impactproject_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pmapprov_impactproject_cb.isChecked())
                {
                    int_approved_impactproject=1;
                }else{
                    int_approved_impactproject=0;
                }
            }
        });







    /*    final LinearLayout lm = (LinearLayout) findViewById(R.id.ll_materialCost);

        // create the layout params that will be used to define how your
        // button will be displayed
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        //Create four
        for(int j=0;j<=4;j++)
        {
            // Create LinearLayout
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            params.weight=2;

            // Create TextView
          TextView product = new TextView(this);
            product.setText(material[j]+j+"    ");
            product.setTextColor(Color.parseColor("#FFFFFF"));
            product.setPadding(20,0,50,10);

            product.setMaxWidth(500);
            ll.addView(product);

            // Create TextView
            TextView price = new TextView(this);
            price.setText("  $"+j+"     ");
            price.setTextColor(Color.parseColor("#FFFFFF"));
          //  price.setPadding(10,0,100,10);
          //  price.setMaxWidth(100);
            ll.addView(price);

            final int index = j;
            // Set click listener for button
            //Add button to LinearLayout defined in XML
            lm.addView(ll);
        }*/

       // init();



    }// end of oncreate()

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

            AlertDialog.Builder adb = new AlertDialog.Builder(PMApproved_DetailsActivity.this);
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




    public boolean validation_ReApply()
    {

        Boolean bpmcomments=true,bamount=true,b_datevalidation2=true,b_datevalidation3=true;


        if( edt_pmcomment.getText().toString().length() == 0 ){
            edt_pmcomment.setError( "Manager comments required!" );bpmcomments=false;}

        if( edt_disperseamount.getText().toString().length() == 0 ){
            edt_disperseamount.setError( "Disperse Amount required!" );bamount=false;}


        if(pmapproved_clickstartproject_editdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar")
                ||pmapproved_clickendproject_editdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar"))
        {
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            b_datevalidation3=false;
        }


        if((!pmapproved_clickstartproject_editdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar"))
                &&(!pmapproved_clickendproject_editdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar")))

        {

            if ((pmapproved_clickstartproject_editdate_tv.getText().toString().length() != 0)
                    && (pmapproved_clickendproject_editdate_tv.getText().toString().length() != 0)) {
        /*if(date1.compareTo(date2)<0){ //0 comes when two date are same,
            //1 comes when date1 is higher then date2
            //-1 comes when date1 is lower then date2 }*/


                SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

                try {
                    Date fromdate = mdyFormat.parse(yyyyMMdd_pmapproved_startdate);
                    Date todate = mdyFormat.parse(yyyyMMdd_pmapproved_enddate);

                    if (fromdate.compareTo(todate) <= 0)
                    {
                        //return true;
                    } else {
                        Toast.makeText(context, "Kindly enter valid date", Toast.LENGTH_SHORT).show();
                        b_datevalidation2 = false;
                        //return false;
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }//end of if

        }





        if(bpmcomments&&bamount&&b_datevalidation2&&b_datevalidation3) {
            return true;
        }else{return false;}
    }
    private SoapObject getProjectDetails() {
        String METHOD_NAME = "GetApprovedProjectDetails";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetApprovedProjectDetails";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",Lead_Id);
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
                //Log.e("Tag","soap response ApprovedDetails"+response.toString());

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
            Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            if(result!=null)
            {

            SoapPrimitive S_ProjectTitle, S_ThemeName, S_BeneficiaryNo, S_Objective, S_actionPlan, S_ManagerComments, S_MaterialName, S_MaterialCost, S_TotalCost, S_ApprovalAmt, S_PlaceofImt, S_WhoareBeneficiary, S_MembersName, S_MemberEmail;
            Object O_ProjectTitle, O_ThemeName, O_BeneficiaryNo, O_Objective, O_actionPlan, O_ManagerComments, O_MaterialName, O_MaterialCost, O_TotalCost, O_ApprovalAmt, O_Status, O_PlaceofImt, O_WhoareBeneficiary, O_MembersName, O_MemberEmail;
            String str_ApprovalAmt = null, str_ProjectTitle = null, str_ThemeName = null, str_BeneficiaryNo = null, str_Objective = null, str_actionPlan = null, str_ManagerComments = null, str_materialName = null, str_materialCost = null, str_placeofImt = null, str_whoareBeneficiary = null, str_memberName = null, str_memberEmail = null;
            SoapObject SO_ThemeName;

            Object O_impactproject;
           SoapPrimitive S_impactproject;

                SoapPrimitive S_startdate,S_enddate;
                Object O_startdate,O_enddate = null;
                String str_startdate,str_enddate;

            //   if(flag==0) {
            O_Status = result.getProperty("status");
            if (O_Status.toString().equals("Invalid project details"))
            {
                Toast.makeText(getApplicationContext(), "Invalid project details", Toast.LENGTH_LONG).show();
            } else
            {
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

                O_ApprovalAmt = result.getProperty("SanctionAmount");
                if (!O_ApprovalAmt.toString().equals("anyType") && !O_ApprovalAmt.toString().equals("anyType{}") && !O_ApprovalAmt.toString().equals(null)) {
                    S_ApprovalAmt = (SoapPrimitive) result.getProperty("SanctionAmount");
                    Log.d("BeneficiaryNo", S_ApprovalAmt.toString());
                    str_ApprovalAmt = S_ApprovalAmt.toString();
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

                O_ManagerComments = result.getProperty("ManagerComments");
                if (!O_ManagerComments.toString().equals("anyType") && !O_ManagerComments.toString().equals("anyType{}") && !O_ManagerComments.toString().equals(null)) {
                    S_ManagerComments = (SoapPrimitive) result.getProperty("ManagerComments");
                    Log.d("Manager Comments", S_ManagerComments.toString());
                    str_ManagerComments = S_ManagerComments.toString();
                }

                O_TotalCost = result.getProperty("Amount");
                if (!O_TotalCost.toString().equals("anyType") && !O_TotalCost.toString().equals("anyType{}") && !O_TotalCost.toString().equals(null)) {
                    S_TotalCost = (SoapPrimitive) result.getProperty("Amount");
                    Log.d("Total Cost", S_TotalCost.toString());
                    str_totalCost = S_TotalCost.toString();
                }

                O_PlaceofImt = result.getProperty("Placeofimplement");
                if (!O_PlaceofImt.toString().equals("anyType") && !O_PlaceofImt.toString().equals("anyType{}") && !O_PlaceofImt.toString().equals(null)) {
                    S_PlaceofImt = (SoapPrimitive) result.getProperty("Placeofimplement");
                    Log.d("Placeofimplement", S_PlaceofImt.toString());
                    str_placeofImt = S_PlaceofImt.toString();
                }

                O_WhoareBeneficiary = result.getProperty("BeneficiariesList");
                if (!O_WhoareBeneficiary.toString().equals("anyType") && !O_WhoareBeneficiary.toString().equals("anyType{}") && !O_WhoareBeneficiary.toString().equals(null))
                {
                    S_WhoareBeneficiary = (SoapPrimitive) result.getProperty("BeneficiariesList");
                    Log.d("WhoareBeneficiary", S_WhoareBeneficiary.toString());
                    str_whoareBeneficiary = S_WhoareBeneficiary.toString();
                }


                O_startdate= result.getProperty("ProjectStartDate");//<ProjectStartDate>2019-07-14</ProjectStartDate>
                Log.e("startDate",O_startdate.toString());
                if (O_startdate.toString().equalsIgnoreCase("anyType{}")
                        ||O_startdate.toString().equals("0000-00-00"))
                {
                    //  S_startdate = O_startdate;

                    // Log.e("startDate",S_startdate.toString());

                    str_startdate = "Click for Calendar";
                }else
               {
                    str_startdate = O_startdate.toString();
                    yyyyMMdd_pmapproved_startdate=str_startdate;
                }

                // Toast.makeText(context,""+O_startdate.toString(),Toast.LENGTH_LONG).show();
                pmapproved_clickstartproject_editdate_tv.setText(str_startdate.toString());


                O_enddate= result.getProperty("ProjectEndDate");//<ProjectEndDate>2019-07-16</ProjectEndDate>
                if (O_enddate.toString().equalsIgnoreCase("anyType{}")
                        ||O_enddate.toString().equals("0000-00-00")) {
                       /* S_enddate = (SoapPrimitive) result.getProperty("ProjectEndDate");

                        Log.e("endDate",S_enddate.toString());*/
                    str_enddate = "Click for Calendar";
                }else{
                    str_enddate = O_enddate.toString();
                    yyyyMMdd_pmapproved_enddate=str_enddate;
                }
                pmapproved_clickendproject_editdate_tv.setText(str_enddate.toString());


                if(str_startdate.toString().equalsIgnoreCase("anyType{}")
                        || str_startdate.toString().equals(null)
                        ||str_startdate.toString().equals("0000-00-00")
                        ||str_startdate.toString().equalsIgnoreCase("Click for Calendar")
                        ||str_enddate.toString().equals("anyType{}")
                        ||str_enddate.toString().equals(null)
                        ||str_enddate.toString().equals("0000-00-00")
                        ||str_enddate.toString().equalsIgnoreCase("Click for Calendar"))
                {

                            //alert();

                }else
                {


                    try{
                        int days_count = Days.daysBetween(new LocalDate(str_startdate), new LocalDate(str_enddate)).getDays();
                        int int_days=days_count+1;
                        Log.e("days",String.valueOf(int_days));
                        pmapproved_numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
                    }catch(Throwable t)
                    {
                        pmapproved_numberofdays_tv.setText(" ");
                    }
                }



                O_impactproject= result.getProperty("IsImpactProject");//<IsImpactProject>0</IsImpactProject>

                if (O_impactproject.toString().equals("anyType")||
                        O_impactproject.toString().equals("anyType{}") || O_impactproject.toString().equals(null))
                {
                    int_approved_impactproject=0;
                    pmapprov_impactproject_cb.setChecked(false);
                }
                else if(O_impactproject.toString().equals("0"))
                {
                    int_approved_impactproject=0;
                    pmapprov_impactproject_cb.setChecked(false);
                }
                else{
                    int_approved_impactproject=1;
                    pmapprov_impactproject_cb.setChecked(true);
                }




            }// end of else

            txt_projectType.setText(str_ThemeName);
            //txt_projectType.setFocusable(false);
            //txt_projectType.setInputType(0);
            txt_projectType.setTextSize(15);

            edt_amount.setText(str_ApprovalAmt);
            edt_amount.setTextSize(15);

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
            edt_objectives.setInputType(0);
            edt_objectives.setTextSize(15);

            edt_actionPlan.setText(str_actionPlan);
            //     edt_actionPlan.setFocusable(true);
            //   edt_actionPlan.setInputType(0);
            edt_actionPlan.setTextSize(15);

          //  edt_pmcomment.setText(str_ManagerComments);
           // edt_pmcomment.setTextSize(15);

            edt_placeofImt.setText(str_placeofImt);
            edt_placeofImt.setTextSize(15);

            edt_whoareBeneficiaries.setText(str_whoareBeneficiary);
            edt_whoareBeneficiaries.setTextSize(15);

            Log.i("Tag", "edt_pmcomment=" + str_ManagerComments);

            Integer ApprovedAmtInt = Integer.parseInt(str_ApprovalAmt);
            Integer Balance = ApprovedAmtInt - DisAmtint;
            edt_balance_amount.setText(Balance.toString());
            Log.e("tag", "Balance=" + Balance);
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
            initMaterials();
            initMembers();

            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class SubmitApprovedDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        SubmitApprovedDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = SubmitProjectDetails();

            //   SoapObject response = ReApplyProjectDetails();
            Log.d("tag","Soap response approvedDetails"+response.toString());

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
                    btn_submit.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Error occured while saving to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Amount funded successfully", Toast.LENGTH_LONG).show();
                    finish();
                    Intent ittEditProjToProjStatus = new Intent(PMApproved_DetailsActivity.this, PMProjectDetailActivity.class);
                    ittEditProjToProjStatus.putExtra("pageCount", 1);
                    startActivity(ittEditProjToProjStatus);
                }
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private SoapPrimitive SubmitProjectDetails() {
        String METHOD_NAME = "AddFundAmountwithPMcomments";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/AddFundAmountwithPMcomments";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            // long projectIds = (long) Integer.valueOf(projectId);
            Log.d("PDIdssssssxxxx","hi");
            Log.d("PDIdssssssxxxx", String.valueOf(Project_Id));
            request.addProperty("PDId",Project_Id);
            request.addProperty("ManagerId",MDId);
            request.addProperty("LeadId",Lead_Id);
            request.addProperty("Amount",Integer.parseInt(edt_disperseamount.getText().toString()));
            request.addProperty("ManagerRemark",edt_pmcomment.getText().toString());

            request.addProperty("ProjectStartDate",yyyyMMdd_pmapproved_startdate.toString());//<ProjectStartDate>string</ProjectStartDate>
            request.addProperty("ProjectEndDate",yyyyMMdd_pmapproved_enddate.toString());// <ProjectEndDate>string</ProjectEndDate>

            request.addProperty("IsImpactProject",int_approved_impactproject);// <IsImpactProject>int</IsImpactProject>
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
                Toast.makeText(PMApproved_DetailsActivity.this,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(PMApproved_DetailsActivity.this,"Project Rejected Successfully",Toast.LENGTH_LONG).show();
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
            request.addProperty("PDId",Project_Id);
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


    /* public void initMaterials() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        String matrl_name = "<u> Material Name </u>";
        tv0.setText(Html.fromHtml(matrl_name));
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.LEFT);
        tv0.setWidth(500);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(5, 10, 5, 5);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        String matrl_cost = "<u> Cost </u>";
        tv1.setText(Html.fromHtml(matrl_cost));
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setWidth(200);
        tv1.setPadding(50, 10, 0, 5);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        for (int i = 0; i < lstMaterialName.size() && i < lstMaterialCost.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(lstMaterialName.get(i));
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.LEFT);
            t1v.setWidth(500);
            t1v.setPadding(5, 5, 5, 5);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(lstMaterialCost.get(i));
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            t2v.setWidth(100);
            t2v.setPadding(50, 5, 0, 5);
            tbrow.addView(t2v);

            stk.addView(tbrow);
        }

       TableRow tbrow = new TableRow(this);
        TextView t1v = new TextView(this);
        t1v.setText("Total");
        t1v.setTypeface(null, Typeface.BOLD);
        t1v.setTextColor(Color.WHITE);
        t1v.setGravity(Gravity.LEFT);
        t1v.setWidth(500);
        t1v.setPadding(5, 5, 5, 5);
        tbrow.addView(t1v);

        TextView t2v = new TextView(this);
        t2v.setText(str_totalCost);
        t2v.setTextColor(Color.WHITE);
        t2v.setTypeface(null, Typeface.BOLD);
        t2v.setGravity(Gravity.CENTER);
        t2v.setWidth(100);
        t2v.setPadding(50, 5, 0, 5);
        tbrow.addView(t2v);
        stk.addView(tbrow);

    //    txt_TotalCost.setText(str_totalCost);
    }
*/
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


    @SuppressLint("RestrictedApi")
    private void setProjectTypeSpinner() {
        final ArrayList liststate = new ArrayList();
        liststate.add("Education");
        liststate.add("Health Care");
        liststate.add("Sports");

        ArrayAdapter dataAdapterState = new ArrayAdapter(getApplicationContext(), R.layout.simple_spinner_items, liststate);

        // Drop down layout style - list view with radio button
        dataAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
      /*  spin_projtype.setAdapter(dataAdapterState);
        spin_projtype.setSupportBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorWhite));
        spin_projtype.setSelection(1);
        spin_projtype.setEnabled(false);
        spin_projtype.setClickable(false);*/
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




    //-------------------------------date--------------------------------

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


            /*return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);*/





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


            // clickstartproject_editdate_tv.setText(dateFormat.format(calendar.getTime()));

            // SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");//2017-06-22

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
            yyyyMMdd_pmapproved_startdate = mdyFormat.format(calendar.getTime());


            pmapproved_clickstartproject_editdate_tv.setText(yyyyMMdd_pmapproved_startdate);

            pmapproved_clickendproject_editdate_tv.setText("Click for Calendar");
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


            /*return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);*/





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

            // clickendproject_editdate_tv.setText(dateFormat.format(calendar.getTime()));

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

            yyyyMMdd_pmapproved_enddate = mdyFormat.format(calendar.getTime());
            pmapproved_clickendproject_editdate_tv.setText(yyyyMMdd_pmapproved_enddate);

            //  System.out.println("To Date:"+ yyyyMMdd_todate);


            int days_count = Days.daysBetween(new LocalDate(yyyyMMdd_pmapproved_startdate), new LocalDate(yyyyMMdd_pmapproved_enddate)).getDays();
            int int_days=days_count+1;


            pmapproved_numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
            System.out.println ("Days: "+int_days);


            if((int_days<0))
            {

                obj_class_alert_msg.alerts_dialog("error","error",getActivity());
                pmapproved_clickendproject_editdate_tv.setText("Click for Calendar");
                pmapproved_numberofdays_tv.setText("Number of Days: ");

            }



           /* int days_count = Days.daysBetween(new LocalDate(yyyyMMdd_startdate), new LocalDate(yyyyMMdd_enddate)).getDays();
            int int_days=days_count+1;
            System.out.println ("Days: "+int_days);

            if((int_days<=6))
            {

            }
            else{
                //alerts_dialog_fortodate();
            }*/



        }

    }


//----------------------------date------------------------------------
















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
            Intent itthomeToEditProfile = new Intent(PMApproved_DetailsActivity.this ,PMEditProfileActivity.class);
            itthomeToEditProfile.putExtra("ManagerId",ManagerId);
            startActivity(itthomeToEditProfile);
            return true;
        }*/


        if (id == R.id.action_logout) {
            Intent itthomeToLogin = new Intent(PMApproved_DetailsActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(PMApproved_DetailsActivity.this ,PMProjectDetailActivity.class);
            ittProjDtlsToHome.putExtra("pageCount",1);
            startActivity(ittProjDtlsToHome);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}//end of class
