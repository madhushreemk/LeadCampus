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
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
//import android.support.design.widget.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.iid.FirebaseInstanceId;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {

    Context context;

    private boolean doubleBackToExitPressedOnce = false;


    Class_URL config_obj = new Class_URL();
    public static final String PREFBook_LoginTrack = "prefbook_logintrack";
    public static final String PrefID_WhereToGo = "prefid_wheretogo"; //
    SharedPreferences shardprefLoginTrack_obj;
    SharedPreferences.Editor editor_LoginTrack;

    SwipeRefreshLayout swipeRefreshLayout;


    public static final String PREFBook_Stud = "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_PM_S_MID = "prefid_pm_s_mid"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_SCellNo = "prefid_scellno"; //
    public static final String PrefID_SCollegeName = "prefid_scollegename"; //
    public static final String PrefID_SImageUrl = "prefid_simgUrl"; //
    public static final String PrefId_S_Password = "prefid_spassword";
    public static final String PrefId_S_Username = "prefid_susername";

    public static final String PrefId_S_Gender = "prefid_sgender";
    public static final String PrefId_S_StarCount = "prefid_sstarCount";

    public static final String PrefId_S_FB = "prefid_sfb";
    public static final String PrefId_S_TW = "prefid_stw";
    public static final String PrefId_S_IN = "prefid_sin";
    public static final String PrefId_S_Whatsapp = "prefid_swhatsapp";
    public static final String PrefId_S_IsFeesPaid = "prefid_sfeesPaid";

    public static final String PrefId_S_TshirtStatus = "prefid_s_tshirtstatus";

    public static final String PrefId_S_AcademicId = "prefid_sacademicid";

    public static final String PrefID_SStudentType = "prefid_sstudentType"; //
    public static final String PrefID_SSisStudentLEADer = "prefid_ssisStudentLEADer";

    public static final String PrefID_PM_S_ImagePath = "prefid_pm_imageUrl"; //
    public static final String PrefID_PM_S_CellNo = "prefid_pm_cellno";//


    public static final String PrefID_S_ImgBase64 = "prefid_s_imgBase64"; //
    public static final String PrefID_PM_ImgBase64 = "prefid_pm_imgBase64";

    public static final String PrefID_Role = "prefid_role";
    public static final String PrefID_S_isprofileEdited = "prefid_isprofileEdited";

    public static final String PrefID_SLLP = "prefID_SLLP";
    public static final String PrefID_SPrayana = "prefID_SPrayana";
    public static final String PrefID_SYuva = "prefID_SYuva";
    public static final String PrefID_SValedicotry = "prefID_SValedicotry";

    SharedPreferences shardpref_S_obj;
    SharedPreferences.Editor editor_S;


    String str_regid, str_sname,str_role, str_s_pmname, str_s_leadid, str_s_emailID, str_s_cellno, str_s_collegeName, str_studImgUrl, str_s_studentType, str_s_isStudentLEADer, str_s_gender, str_s_starCount, str_fb, str_tw, str_whatsapp, str_in, str_s_pmMobileNo;
    String str_isFeesPaid, Str_s_PMImageURL, str_tshirt_status;
    String str_s_LLP, str_s_Prayana, str_s_Yuva, str_s_Valedicotry;


    String Str_PM_S_Full_ImgUrl = null;
    String Str_fullStudImgUrl = null;

    private String serverPath = Class_URL.ServerPath.toString().trim();
    private ProgressDialog progressDialog_refreshSwipe;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;


    private Menu thismenu;


    ArrayAdapter dataAdapter_tshrit_reapply;
    private AppCompatSpinner spin_tshirtreapply_reason;
    String str_tshirt_reapply_reason;

    RadioGroup rg_tshirt_reapply_radiogroup;
    private RadioButton rb_tshirt_reapply_radiobutton;
    String str_tshir_reapply_tsize;
    String Str_tshirt_reapply_error1, Str_tshirt_reapply_error2, str_tshirt_reapply_response, str_regId_reapplytshirt, str_s_pm_mid, Str_tshirterror4, Str_tshirterror3;


    String str_getTshirtstatus_is_approved;

    String str_gettshirtlistresponse1, str_getTshirtStatus1, str_getTshirt_requestedID1, str_getTshirt_tshirtSize1;


    TelephonyManager tm1 = null;
    String myVersion, deviceBRAND, deviceHARDWARE, devicePRODUCT, deviceUSER, deviceModelName, deviceId, tmDevice, tmSerial, androidId, simOperatorName, sdkver, mobileNumber;
    int sdkVersion, Measuredwidth = 0, Measuredheight = 0, update_flage = 0;
    AsyncTask<Void, Void, Void> mRegisterTask;
    String regId = "leadxz", Str_FCMName;
    private String versioncode;
    EditText username_et,password_et, dialognewpwd_et,dialogconfpwd_et;
    String str_newPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        context = HomeActivity.this;


        Str_tshirt_reapply_error1 = Str_tshirt_reapply_error2 = Str_tshirterror4 = Str_tshirterror3 = str_getTshirtstatus_is_approved = "no";

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //getSupportActionBar().setTitle("LeadMIS");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        // getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

    /*    toolbar.setTitle("LeadMIS");
        setSupportActionBar(toolbar);*/

        shardprefLoginTrack_obj = this.getSharedPreferences(PREFBook_LoginTrack, Context.MODE_PRIVATE);
        editor_LoginTrack = shardprefLoginTrack_obj.edit();
        editor_LoginTrack.putString(PrefID_WhereToGo, config_obj.packagename + "HomeActivity");
        editor_LoginTrack.commit();


        shardpref_S_obj = getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);

        str_s_leadid = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_s_cellno = shardpref_S_obj.getString(PrefID_SCellNo, "").trim();
        Str_FCMName = str_s_leadid.toString();

        str_tshirt_status = shardpref_S_obj.getString(PrefId_S_TshirtStatus, "").trim();

        str_regId_reapplytshirt = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_s_pm_mid = shardpref_S_obj.getString(PrefID_PM_S_MID, "").trim();
        str_sname = shardpref_S_obj.getString(PrefID_SName, "").trim();
        str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();
        Log.e("str_role:", str_role);

        Log.e("leadID ", str_s_leadid.toString());
        Log.e("Cellnumber", str_s_cellno.toString());

        try {
            versioncode = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        View view1;
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.myprofiletablayout, null);

        TextView txt_personProfile = (TextView) view1.findViewById(R.id.txt_personProfile);
        txt_personProfile.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));

        View view2;
        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.leaderprofiletablayout, null);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ProfileAdapter adapter = new ProfileAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                View view = tab.getCustomView();
                TextView txt_personProfile = (TextView) view.findViewById(R.id.txt_personProfile);
                txt_personProfile.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView txt_personProfile = (TextView) view.findViewById(R.id.txt_personProfile);//MyprofileFragment.java
                //MyprofileFragmentNew.java
                txt_personProfile.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView txt_personProfile = (TextView) view.findViewById(R.id.txt_personProfile);//LeaderProfileFragment.java
                txt_personProfile.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
            }
        });

   /*     getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                            swipeRefreshLayout.setRefreshing(false);


                            Log.e("leadID ", str_s_leadid.toString());
                            Log.e("Cellnumber", str_s_cellno.toString());

                            GetLoginDetails getloginDetails = new GetLoginDetails(HomeActivity.this);
                            getloginDetails.execute(str_s_cellno.toString(), str_s_leadid.toString());
                            //Toast.makeText(getApplicationContext(),"net"+isInternetPresent.toString(),Toast.LENGTH_SHORT).show();


                            AsyncCallFCM task = new AsyncCallFCM(HomeActivity.this);
                            task.execute();


                        }
                    }, 500);

                } else {
                    Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                }

            }


        });





       /* if (str_tshirt_status.equals("3"))
        {
           // Toast.makeText(getApplicationContext(),)
            if(thismenu != null)
            {
                thismenu.findItem(R.id.action_tshirtreapply).setVisible(true);

            }else
            {
                thismenu.findItem(R.id.action_tshirtreapply).setVisible(false);
            }
        }*/


        if (str_tshirt_status.equals("3")) {


            internetDectector = new Class_InternetDectector(getApplicationContext());
            isInternetPresent = internetDectector.isConnectingToInternet();

            if (isInternetPresent) {
                GetTshirtlist_AsyncCallWS task = new GetTshirtlist_AsyncCallWS(HomeActivity.this);
                task.execute();
            }

        }


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

            AlertDialog.Builder adb = new AlertDialog.Builder(HomeActivity.this);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    //
    //public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    {


        getMenuInflater().inflate(R.menu.menu, menu);


        if (str_tshirt_status.equals("3")) {


            MenuItem shareItem = menu.findItem(R.id.action_tshirtreapply);
            shareItem.setVisible(true);
        }
        return true;

        // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        //action_tshirtreapply

        if (id == R.id.action_editProfile) {
            Intent itthomeToEditProfile = new Intent(HomeActivity.this, EditProfileActivity.class);
            startActivity(itthomeToEditProfile);
            return true;
        }


        if( id == R.id.changepwd){
            final Dialog dialog = new Dialog(HomeActivity.this);


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
                                    ChangePassword changePassword=new ChangePassword(HomeActivity.this);
                                    changePassword.execute();
                                }

                                dialog.dismiss();

                            }
                            else{
                                Toast.makeText(HomeActivity.this, "Kindly connect to internet", Toast.LENGTH_SHORT).show();
                            }




                    }
                });


                dialog.show();
        }
        if (id == R.id.action_logout) {

            editor_LoginTrack = shardprefLoginTrack_obj.edit();
            editor_LoginTrack.putString(PrefID_WhereToGo, config_obj.packagename + "LoginActivity");
            editor_LoginTrack.commit();

            //deleteCache(context);

            Intent itthomeToLogin = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }
        if (id == R.id.action_feedback) {
            Intent itthomeTofeeedback = new Intent(HomeActivity.this, FeedbackActivity.class);
            startActivity(itthomeTofeeedback);
            return true;
        }
        if (id == R.id.action_request) {
            Intent itthomeTorequest = new Intent(HomeActivity.this, StudentRequest.class);
            startActivity(itthomeTorequest);
            return true;
        }
        if (id == R.id.action_tshirtreapply) {

            /*TextView txtclose;
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
            myDialog.show();*/


            final Dialog myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.tshirt_reapply_customdialog);
            myDialog.setCancelable(false);


            // rg_tshirtradiogroup =(RadioGroup)myDialog.findViewById(R.id. tshirtradiogroup_RG);


            spin_tshirtreapply_reason = (AppCompatSpinner) myDialog.findViewById(R.id.spin_tshirtreapply_reason);

            rg_tshirt_reapply_radiogroup = (RadioGroup) myDialog.findViewById(R.id.tshirt_reapply_radiogroup_RG);
            Button dialog_bt_tshirt_reapply = (Button) myDialog.findViewById(R.id.tshirt_reapply_BT);
            Button dialog_bt_tshirtcancel = (Button) myDialog.findViewById(R.id.tshirt_reapply_cancel_BT);
            // TextView previous_request_tv =(TextView)myDialog.findViewById(R.id.previous_request_tv);

            settshirt_reapply_spinner();


            spin_tshirtreapply_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // TODO Auto-generated method stub
                    str_tshirt_reapply_reason = spin_tshirtreapply_reason.getSelectedItem().toString();
                    //Toast.makeText(getApplicationContext(),"Reason: "+str_tshirt_reapply_reason,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO Auto-generated method stub

                }
            });


            dialog_bt_tshirt_reapply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedId = rg_tshirt_reapply_radiogroup.getCheckedRadioButtonId();

                    rb_tshirt_reapply_radiobutton = (RadioButton) myDialog.findViewById(selectedId);

                    String string = rb_tshirt_reapply_radiobutton.getText().toString().trim();
                    String[] vals = string.split(" ");
                    if (vals.length > 1) {
                        str_tshir_reapply_tsize = vals[0];
                    }


                    internetDectector = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector.isConnectingToInternet();

                    if (isInternetPresent) {
                        Tshirt_reapply_AsyncCallWS task = new Tshirt_reapply_AsyncCallWS(HomeActivity.this);
                        task.execute();
                        myDialog.cancel();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet", Toast.LENGTH_LONG).show();
                    }

                }
            });


            dialog_bt_tshirtcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myDialog.cancel();
                }
            });


            myDialog.show();


            return true;
        }


        if (id == R.id.notification) {
            Intent itthomeToNotification = new Intent(HomeActivity.this, NotificationHistoryActivity.class);
            startActivity(itthomeToNotification);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
        }
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
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        //finish();
 /*       if (doubleBackToExitPressedOnce) {
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


//------------------------------------------------------------------------------------------------------------

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

                AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
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

            request.addProperty("User_Id", str_regId_reapplytshirt);//<Lead_Id>string</Lead_Id>
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

    public class GetLoginDetails extends AsyncTask<String, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;


        GetLoginDetails(Context ctx) {
            context = ctx;
            progressDialog_refreshSwipe = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(String... params) {
            String mobilenum = (String) params[0];
            String leadid = (String) params[1];
            //String versionCode = (String) params[2];

            SoapObject response = getLoginDetails(mobilenum, leadid);


            return response;
        }

        @Override
        protected void onPreExecute() {
/*            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog_refreshSwipe.setMessage("Loading");
            progressDialog_refreshSwipe.setCanceledOnTouchOutside(false);
            progressDialog_refreshSwipe.show();

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            //progressBar.setVisibility(View.GONE);
            progressDialog_refreshSwipe.dismiss();

            if (result != null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType{}", "");
                Log.d("Finals is", finals);


                SoapPrimitive status = (SoapPrimitive) result.getProperty("Status");


                if (status.toString().equals("Success")) {
                    SoapPrimitive role = (SoapPrimitive) result.getProperty("Role");
                    Log.d("Role is:", role.toString());

                    SoapPrimitive isProfileEdited = (SoapPrimitive) result.getProperty("isProfileEdit");

                    SoapPrimitive S_academicId = (SoapPrimitive) result.getProperty("AcademicId");
                    String str_academicCode = S_academicId.toString();


                    editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefId_S_Username, str_s_leadid);
                    editor_S.putString(PrefId_S_Password, str_s_cellno);
                    editor_S.putString(PrefID_SLeadID, str_s_leadid);

                    if (!str_academicCode.equals(null) && !str_academicCode.equals("anyType{}") && !str_academicCode.equals("{}") && !str_academicCode.isEmpty()) {
                        Log.d("Academic_code:", str_academicCode);
                        editor_S.putString(PrefId_S_AcademicId, str_academicCode);
                    }

                    editor_S.commit();


                    if (role.toString().equals("Student")) {

                        str_regid = result.getProperty("RegistrationId").toString(); //1
                        str_sname = result.getProperty("Name").toString().toString();//name
                        str_s_pmname = result.getProperty("ManagerName").toString();//Abbhinandan
                        str_s_pm_mid = result.getProperty("ManagerId").toString();//1
                        str_s_leadid = result.getProperty("Lead_Id").toString();//MHOO
                        str_s_emailID = result.getProperty("MailId").toString();// mailid@
                        str_s_cellno = result.getProperty("Student_Mobile_No").toString();// <Student_Mobile_No>long</Student_Mobile_No>

                        str_s_pmMobileNo = result.getProperty("MobileNo").toString();
                        //mobile change

                        str_s_studentType = result.getProperty("Student_Type").toString();
                        str_s_isStudentLEADer = result.getProperty("isStudentLEADer").toString();
                        str_s_collegeName = result.getProperty("College_Name").toString();

                        // Str_PMImageURL = result.getProperty("Manager_Image_Path").toString();


                        Str_s_PMImageURL = result.getProperty("Manager_Image_Path").toString();////Str_s_PMImageURL;

                        str_s_gender = result.getProperty("Gender").toString();

                        str_s_starCount = result.getProperty("StartCount").toString();

                        str_fb = result.getProperty("Facebook").toString();
                        str_tw = result.getProperty("Twitter").toString();
                        str_in = result.getProperty("InstaGram").toString();
                        str_whatsapp = result.getProperty("WhatsApp").toString();

                        str_isFeesPaid = result.getProperty("isFeePaid").toString();
                        str_tshirt_status = result.getProperty("isRequestForTShirt").toString();

                        str_s_LLP = result.getProperty("LLP_Badges").toString();
                        str_s_Prayana = result.getProperty("Prayana_Badges").toString();
                        str_s_Yuva = result.getProperty("Yuva_Badges").toString();
                        str_s_Valedicotry = result.getProperty("Valedicotry_Badges").toString();

                        Log.d("str_isFeesPaidissssss", str_isFeesPaid);


                        editor_S = shardpref_S_obj.edit();
                        editor_S.putString(PrefID_RegID, str_regid);
                        editor_S.putString(PrefID_SName, str_sname);
                        editor_S.putString(PrefID_SLeadID, str_s_leadid);
                        editor_S.putString(PrefID_SCollegeName, str_s_collegeName);
                        editor_S.putString(PrefID_Role, role.toString());
                        editor_S.putString(PrefID_PM_SName, str_s_pmname);
                        editor_S.putString(PrefID_PM_S_MID, str_s_pm_mid);
                        editor_S.putString(PrefID_PM_SEmailID, str_s_emailID);
                        editor_S.putString(PrefID_SCellNo, str_s_cellno);
                        editor_S.putString(PrefID_SStudentType, str_s_studentType);
                        editor_S.putString(PrefID_SSisStudentLEADer, str_s_isStudentLEADer);

                        editor_S.putString(PrefID_SLLP, str_s_LLP);
                        editor_S.putString(PrefID_SPrayana, str_s_Prayana);
                        editor_S.putString(PrefID_SYuva, str_s_Yuva);
                        editor_S.putString(PrefID_SValedicotry, str_s_Valedicotry);

                        // editor_S.putString(PrefID_PMMobile,str_s_pmMobileNo);

                        editor_S.putString(PrefID_PM_S_CellNo, str_s_pmMobileNo);   //PrefID_PM_S_CellNo
                        editor_S.putString(PrefId_S_Gender, str_s_gender);
                        editor_S.putString(PrefId_S_StarCount, str_s_starCount);

                        editor_S.putString(PrefId_S_FB, str_fb);
                        editor_S.putString(PrefId_S_TW, str_tw);
                        editor_S.putString(PrefId_S_IN, str_in);
                        editor_S.putString(PrefId_S_Whatsapp, str_whatsapp);

                        editor_S.putString(PrefId_S_IsFeesPaid, str_isFeesPaid);
                        editor_S.putString(PrefId_S_TshirtStatus, str_tshirt_status); //0 not eligible, 1 eligible, 2 already sent request

                        // String Imagestrings = Str_PMImageURL;

                        String Imagestrings = Str_s_PMImageURL;

                        //Str_PMImgUrl = null;

                        Str_PM_S_Full_ImgUrl = null;

                        if (Imagestrings == null || Imagestrings.equals("null") || Imagestrings.equals("anyType{}")) {
                            //Str_PMImgUrl="null";
                            Str_PM_S_Full_ImgUrl = "null";

                        } else {
                            String arr[] = Imagestrings.split("/", 2);

                            String firstWord = arr[0];   //the
                            String secondWord = arr[1];

                            //Str_PMImgUrl = serverPath + secondWord;

                            Str_PM_S_Full_ImgUrl = serverPath + secondWord;

                            Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                            Log.i("tag", "PMImage1=" + Str_s_PMImageURL);
                            Log.i("tag", "PMImgUrl=" + Str_PM_S_Full_ImgUrl);
                        }

                        editor_S.putString(PrefID_PM_S_ImagePath, Str_PM_S_Full_ImgUrl);


                        if (isProfileEdited.toString().equals("0"))// first time user
                        {
                        /*Intent ittLoginToEditProfile = new Intent(LoginActivity.this,EditProfileActivity.class);
                        ittLoginToEditProfile.putExtra("leadid",leadid);
                        // ittLoginToEditProfile.putExtra("isProfileEdit","0");
                        startActivity(ittLoginToEditProfile);
                        editor_S.putString(PrefID_S_isprofileEdited,"0");*/

                        } else {

                            str_studImgUrl = result.getProperty("UserImage").toString();

                            String Imagestring = str_studImgUrl;
                            Str_fullStudImgUrl = null;// Str_fullStudImgUrl
                            if (Imagestring.equals("null") || Imagestring.equals("anyType{}")) {
                                Str_fullStudImgUrl = "null"; //Str_fullStudImgUrl
                            } else {
                                String arr[] = Imagestring.split("/", 2);

                                String firstWord = arr[0];   //the
                                String secondWord = arr[1];

                                Str_fullStudImgUrl = serverPath + secondWord; //Str_fullStudImgUrl
                                Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                                Log.i("tag", "StudentImage1=" + str_studImgUrl);
                                Log.i("tag", "StudentImage2=" + Str_fullStudImgUrl);//Str_fullStudImgUrl
                            }

                            editor_S.putString(PrefID_S_isprofileEdited, "1");


                            editor_S.putString(PrefID_SImageUrl, Str_fullStudImgUrl);//Str_fullStudImgUrl

                      /*  LoadStudentProfilePick loadStudentProfilePick = new LoadStudentProfilePick(context);
                        loadStudentProfilePick.execute();*/
                            Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                            startActivity(i);
                        }
                        editor_S.commit();

                    }

                } else {
                    Log.d("Status is: ", status.toString());
                }
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


//----------------------------------------------------------------------

    private SoapObject getLoginDetails(String mobilenum, String leadid) {

        String METHOD_NAME = "ValidateLogin1";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ValidateLogin1";

        try {
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            Log.d("Mobile Number", mobilenum);
            Log.d("Lead Id", leadid);

            /*request.addProperty("Username", mobilenum);
            request.addProperty("Password", leadid);*/

            /*request.addProperty("Username", leadid);
            request.addProperty("Password", mobilenum);*/


            String username = str_s_leadid.toString();
            String password = str_s_cellno.toString();

            Log.e("LeadID", str_s_leadid.toString());
            Log.e("password", str_s_cellno.toString());

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


    private void settshirt_reapply_spinner() {
        final ArrayList list_reason = new ArrayList();
        list_reason.add("Damage");
        list_reason.add("Lost");
        list_reason.add("Size Mismatch");
        list_reason.add("Emergency T-shirt");


        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        dataAdapter_tshrit_reapply = new ArrayAdapter(context, R.layout.simple_spinner_items_white, list_reason);

        // Drop down layout style - list view with radio button
        dataAdapter_tshrit_reapply.setDropDownViewResource(R.layout.spinnerdropdownstylewhite);

        // attaching data adapter to spinner
        spin_tshirtreapply_reason.setAdapter(dataAdapter_tshrit_reapply);
        //  spin_tshirtreapply_reason.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));

    }


    private class Tshirt_reapply_AsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("LeadMIS", "doInBackground");
            //  GetAllEvents();

            Tshirt_reapply();  //apply T-shirt request


            return null;
        }

        public Tshirt_reapply_AsyncCallWS(HomeActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {
            dialog.dismiss();


            if (Str_tshirt_reapply_error1.equals("no") || Str_tshirt_reapply_error2.equals("no")) {
                if (str_tshirt_reapply_response.equals("Success") || str_tshirt_reapply_response.equals("success")) {

                    /*editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefId_S_TshirtStatus,"1");
                    editor_S.commit();*/
                    Toast.makeText(getApplicationContext(), "T-Shirt request sent Successfully", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Error: WS" + str_tshirt_reapply_response, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Error: WS" + str_tshirt_reapply_response, Toast.LENGTH_SHORT).show();
            }

        }//end of OnPostExecute

    }// end Async task


    public void Tshirt_reapply() {

        //URL
        String URL = Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "ApplyTshirtRequested";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/ApplyTshirtRequested";
        //URL


        Integer int_registrationId = Integer.parseInt(str_regId_reapplytshirt.toString().trim());
        Integer int_managerid = Integer.parseInt(str_s_pm_mid.toString().trim());


        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            //for re-request of t-shirt
            request.addProperty("RequestedId", str_getTshirt_requestedID1);//<RequestedId>string</RequestedId>
            request.addProperty("TshirtModSize", str_tshir_reapply_tsize.trim());//<TshirtModSize>string</TshirtModSize>

            //for re-request of t-shirt

            request.addProperty("RegistrationId", int_registrationId);//<RegistrationId>int</RegistrationId>
            request.addProperty("Lead_Id", str_s_leadid.toString().trim());//<Lead_Id>string</Lead_Id>
            request.addProperty("ManagerId", int_managerid);//<ManagerId>int</ManagerId>
            request.addProperty("MemberName", str_sname);//<MemberName>string</MemberName>
            request.addProperty("TshirtSize", str_tshir_reapply_tsize.trim());//<TshirtSize>string</TshirtSize>
            request.addProperty("RequestedCount", 1);//<RequestedCount>int</RequestedCount>
            request.addProperty("ReapplyReson", str_tshirt_reapply_reason);//<ReapplyReson>string</ReapplyReson>


/*<RegistrationId>int</RegistrationId>
      <Lead_Id>string</Lead_Id>
      <ManagerId>int</ManagerId>
      <MemberName>string</MemberName>
      <TshirtSize>string</TshirtSize>
      <RequestedCount>int</RequestedCount>*/
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            Log.e("requested", request.toString());

            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAPACTION, envelope);

                SoapPrimitive tshirt_response = (SoapPrimitive) envelope.getResponse();

                //   SoapObject tshirt_response = (SoapObject) envelope.getResponse();

                Log.e("tshirtreapply response", tshirt_response.toString());

                str_tshirt_reapply_response = tshirt_response.toString().trim();

            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Str_tshirt_reapply_error1 = "yes";
                Log.e("request fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Str_tshirt_reapply_error2 = "yes";
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }


    }//end of TshirtApply


    private class GetTshirtlist_AsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");

            /*dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("LeadMIS", "doInBackground");
            //  GetAllEvents();

            GetTshirtlist_list();  // call of GetTshirtlist


            return null;
        }

        public GetTshirtlist_AsyncCallWS(HomeActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPostExecute(Void result) {
            // dialog.dismiss();

            if (str_getTshirtStatus1.equals("Success")) {

            }


        }//end of OnPostExecute

    }// end Async task


    public void GetTshirtlist_list() {

        //URL
        String URL = Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "GetTshirtlist";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/GetTshirtlist";
        //URL

        /*<RequestedId>16</RequestedId>
        <TshirtSize>S</TshirtSize>
        <RequestedDate>10/13/2018 1:41:44 PM</RequestedDate>
        <SanctionDate>10/13/2018 1:43:46 PM</SanctionDate>
        <TshirtStatus>Exchange</TshirtStatus>
        <Status>Success</Status>*/

        /*<vmtshirtlist>
        <RequestedId>0</RequestedId>
        <Status>There is no Tshirt request here</Status>
        </vmtshirtlist>*/

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);


            request.addProperty("Lead_id", str_s_leadid.toString().trim());//<Lead_Id>string</Lead_Id>


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAPACTION, envelope);
                SoapObject gettshirtlist_response = (SoapObject) envelope.getResponse();

                Log.e("gettshirt list", gettshirtlist_response.toString());

                gettshirtlist_response = (SoapObject) gettshirtlist_response.getProperty(0);


                str_gettshirtlistresponse1 = gettshirtlist_response.getProperty("Status").toString();

                str_getTshirtStatus1 = gettshirtlist_response.getProperty("Status").toString();
                str_getTshirtstatus_is_approved = gettshirtlist_response.getProperty("TshirtStatus").toString();//<TshirtStatus>Requested</TshirtStatus>


                if (str_getTshirtStatus1.equalsIgnoreCase("Success") &&
                        (str_getTshirtstatus_is_approved.equalsIgnoreCase("Requested")
                                || str_getTshirtstatus_is_approved.equalsIgnoreCase("Reapply") || str_getTshirtstatus_is_approved.equalsIgnoreCase("ApproveRollBacked"))) {

                    str_getTshirt_requestedID1 = (String) gettshirtlist_response.getProperty("RequestedId").toString();//<RequestedId>16</RequestedId>
                    str_getTshirt_tshirtSize1 = (String) gettshirtlist_response.getProperty("TshirtSize").toString();//<TshirtSize>S</TshirtSize>


                }
                if ((str_getTshirtstatus_is_approved.equalsIgnoreCase("Approved"))) {
                    str_getTshirt_requestedID1 = "";
                }


            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Str_tshirterror3 = "yes";
                Log.e("request fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Str_tshirterror4 = "yes";
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }


    }//end of TshirtApply


    private class AsyncCallFCM extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog;

        Context context;
        boolean versionval;

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

        public AsyncCallFCM(HomeActivity activity) {
            context = activity;
            // dialog = new ProgressDialog(activity);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("Leadmis", "doInBackground");


            setGCM1();
            setGCM1();


            //  versionval =	appversioncheck(versioncodeInString);


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











}//end of class
