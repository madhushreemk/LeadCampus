package com.leadcampusapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {
    private Button btn_login;
    public static final int RequestPermissionCode = 7;
    public Button btn_call;
    public Button btn_guestlogin;
    public Button btn_smaple;
    private TextView txt_misscallnumber;

    private EditText edt_mobnumber, edt_leadid;
    private String mobnumber, leadid;
    private String versioncode;

    Class_InternetDectector cd;

    String Str_ManagerName, Str_PMEmailId, Str_PM_Mobile, Str_PMLocation, Str_Manager_Id, Str_PMImageURL;
    Integer ManagerId;

    String str_regid, str_sname, str_s_pmname, str_s_leadid, str_s_emailID, str_s_cellno, str_s_collegeName, str_studImgUrl, str_s_studentType, str_s_isStudentLEADer, str_s_gender, str_s_starCount, str_fb, str_tw, str_whatsapp, str_in, str_s_pmMobileNo;
    String str_isFeesPaid, Str_s_PMImageURL, str_tshirt_status, str_s_pm_mid, str_s_LLP, str_s_Prayana, str_s_Yuva, str_s_Valedicotry;

    String Str_StudImgUrl;

    String Str_PMImgUrl = null;
    String Str_PM_S_Full_ImgUrl = null;

    private String serverPath = Class_URL.ServerPath.toString().trim();

    public static final String PREFBook_PM = "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    public static final String PrefID_pmName = "prefid_pmname"; //
    public static final String PrefID_PMEMailID = "prefid_pmemailid"; //
    public static final String PrefID_PMMobile = "prefid_pmmobile"; //
    public static final String PrefID_pmlocation = "prefid_pmlocation"; //
    public static final String PrefID_pmimageurl = "prefid_pmimgurl"; //
    public static final String PrefID_pm_username = "prefid_pm_username"; //
    public static final String PrefID_pm_password = "prefid_pm_password"; //


    SharedPreferences shardprefPM_obj;
    SharedPreferences.Editor editor_PM;

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
    public static final String PrefID_PM_S_CellNo = "prefid_pm_s_cellno";//


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


    SharedPreferences shardprefPrinciple_info;
    SharedPreferences.Editor editor_Principle_info;
    public static final String PREBook_Principle_info = "prefbook_principle_info";
    public static final String PrefID_CollgeName = "prefid_CollgName";
    public static final String PrefID_AcademicId = "prefid_AcademicId";
    public static final String PrefID_PMailId = "prefid_PMailId";
    public static final String PrefID_PWhatsapp = "prefid_PWhatsapp";
    public static final String PrefID_PMobileNo = "prefid_PMobileNo";
    public static final String PrefID_PName = "prefid_PName";
    public static final String PrefID_PCollegeId = "prefid_PCollegeId";


    String str_loginresponse = "false";
    TelephonyManager tm1 = null;
    String myVersion, deviceBRAND, deviceHARDWARE, devicePRODUCT, deviceUSER, deviceModelName, deviceId, tmDevice, tmSerial, androidId, simOperatorName, sdkver, mobileNumber;
    int sdkVersion, Measuredwidth = 0, Measuredheight = 0, update_flage = 0;
    AsyncTask<Void, Void, Void> mRegisterTask;
    String regId = "leadxz", Str_FCMName;
    String Principal_Name, Principal_MailId, Principal_WhatsAppNo, Principal_MobileNo, Principal_College_Name, Principal_College_Id;

    private ProgressDialog progressDialog;

    Class_InternetDectector internetDectector, internetDectector2;
    Boolean isInternetPresent = false;


    public static final String PREFBook_generalbook = "prefbook_generalbook";  //sharedpreference Book
    SharedPreferences shardprefgeneralbook_obj;
    SharedPreferences.Editor editor_generalbook;
    String str_misscallno;

    SharedPreferences shardprefPM_obj_count;
    SharedPreferences.Editor editor_PM_count;
    public static final String PREFBook_PM_Count = "prefbook_pm_count";  //sharedpreference Book
    public static final String PrefID_UNCount = "prefid_UNCount"; //
    public static final String PrefID_ComCount = "prefid_ComCount"; //
    public static final String PrefID_AppCount = "prefid_AppCount"; //
    public static final String PrefID_ReqModCount = "prefid_ReqModCount"; //
    public static final String PrefID_RejCount = "prefid_RejCount"; //
    public static final String PrefID_ReqComCount = "prefid_ReqComCount"; //
    public static final String PrefID_Tshirt = "prefID_Tshirt";

    private FirebaseAnalytics firebaseAnalytics;
    SignInButton google_signin_bt;
    GoogleSignInClient googlesigninclient_obj;
    private static final int RC_SIGN_IN = 234;////a constant for detecting the login intent result
    GoogleSignInAccount account;
    String str_gmailid;
    FirebaseAuth firebaseauth_obj;

    String str_isuser_setpin,str_forgotusername,str_isuser_changepin,str_isuser_loggedfromgoogle;
    SharedPreferences sharedpreference_usercredential_Obj;
    public static final String sharedpreference_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_isuser_setpin = "KeyValue_isuser_setpin";
    public static final String KeyValue_isuser_changepin = "KeyValue_isuser_changepin";
    public static final String KeyValue_loggedfromgoogle = "KeyValue_loggedfromgoogle";

    String str_PMEmailId,str_PrincipleEmailId,str_S_EmailId;
    TextView forgotpassword_tv;
    EditText username_et,password_et, dialogusername_et;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        //val bundle = Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "my_item_id");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        shardprefPM_obj = getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardpref_S_obj = getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardprefgeneralbook_obj = getSharedPreferences(PREFBook_generalbook, Context.MODE_PRIVATE);

        shardprefPM_obj_count = getSharedPreferences(PREFBook_PM_Count, Context.MODE_PRIVATE);
        shardprefPrinciple_info = getSharedPreferences(PREBook_Principle_info, Context.MODE_PRIVATE);

        sharedpreference_usercredential_Obj=getSharedPreferences(sharedpreference_usercredential, Context.MODE_PRIVATE);
        //str_isuser_setpin = sharedpreference_usercredential_Obj.getString(KeyValue_isuser_setpin, "").trim();
        str_isuser_setpin="";
        str_isuser_changepin=sharedpreference_usercredential_Obj.getString(KeyValue_isuser_changepin, "").trim();
        str_isuser_loggedfromgoogle=sharedpreference_usercredential_Obj.getString(KeyValue_loggedfromgoogle, "").trim();

        shardpref_S_obj.getString(PrefID_PM_SEmailID, "").trim();
        str_S_EmailId = shardpref_S_obj.getString(PrefID_PM_SEmailID, "").trim();
        Log.d("str_S_EmailId:", str_S_EmailId);

        shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        str_PMEmailId = shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        Log.d("str_PMEmailId:", str_PMEmailId);

        shardprefPrinciple_info.getString(PrefID_PMailId, "").trim();
        str_PrincipleEmailId = shardprefPrinciple_info.getString(PrefID_PMailId, "").trim();
        Log.d("str_PrincipleEmailId:", str_PrincipleEmailId);

        edt_leadid = (EditText) findViewById(R.id.edt_leadid);
        edt_mobnumber = (EditText) findViewById(R.id.edt_mobnumber);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_call = (Button) findViewById(R.id.btn_call);
        btn_guestlogin = (Button) findViewById(R.id.btn_guestlogin);
        btn_smaple = (Button) findViewById(R.id.btn_smaple);
        txt_misscallnumber = (TextView) findViewById(R.id.txt_misscallnumber);
        forgotpassword_tv=(TextView)findViewById(R.id.forgotpassword_tv);

        google_signin_bt =(SignInButton)findViewById(R.id.google_signin_bt);
        google_signin_bt.setColorScheme(SignInButton.COLOR_DARK);
        setGooglePlusButtonText(google_signin_bt," Sign in with Google ");


        editor_PM_count = shardprefPM_obj_count.edit();
        editor_PM_count.putString(PrefID_AppCount, "0");
        Log.d("ApprovedCount", "0");
        editor_PM_count.putString(PrefID_ComCount, "0");
        editor_PM_count.putString(PrefID_RejCount, "0");
        editor_PM_count.putString(PrefID_UNCount, "0");
        editor_PM_count.putString(PrefID_ReqComCount, "0");
        editor_PM_count.putString(PrefID_ReqModCount, "0");
        editor_PM_count.putString(PrefID_Tshirt, "0");

        editor_PM_count.commit();

        str_misscallno = shardprefgeneralbook_obj.getString(PREFBook_generalbook, "").trim();
        txt_misscallnumber.setText(str_misscallno.toString());



        if (CheckingPermissionIsEnabledOrNot()) {
            // Toast.makeText(LoginActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();
        }
        // If, If permission is not enabled then else condition will execute.
        else {

            //Calling method to enable permission.
            //Toast.makeText(LoginActivity.this, "Requesting", Toast.LENGTH_LONG).show();
            RequestMultiplePermission();

        }


        try {
            versioncode = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        String MsgNotfication = "";

        Bundle extras = getIntent().getExtras();

        // if (extras != null){}
        if (extras != null) {
            MsgNotfication = extras.getString("2");
            if (MsgNotfication != null || MsgNotfication != "") {
                Toast.makeText(getApplicationContext(), " " + MsgNotfication.toString(), Toast.LENGTH_LONG).show();
                Log.e("notifMsg", "notifMsg: " + MsgNotfication.toString());
            }
        }

        //Google Sign initializing
        firebaseauth_obj = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_ID_type3))
                .requestEmail()
                .build();
        googlesigninclient_obj = GoogleSignIn.getClient(this, gso);
        //Google Sign initializing


        google_signin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                internetDectector = new Class_InternetDectector(getApplicationContext());
                isInternetPresent = internetDectector.isConnectingToInternet();

                if (2>1)
                {
                    if (isInternetPresent)
                    {
                        google_sign();
                    }else{
                        Toast.makeText(LoginActivity.this, "Kindly connect to internet", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        if(!str_S_EmailId.equalsIgnoreCase("")){
            signOut();
        }
        if(!str_PMEmailId.equalsIgnoreCase("")){
            signOut();
        }
        if(!str_PrincipleEmailId.equalsIgnoreCase("")){
            signOut();
        }

        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + txt_misscallnumber.getText().toString()));
                startActivity(intent);


            }
        });


        btn_guestlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(LoginActivity.this, GuestActivity.class);
                Intent i = new Intent(LoginActivity.this, GuestLoginActivity.class);
                startActivity(i);
                finish();
            }
        });


        forgotpassword_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Dialog dialog = new Dialog(LoginActivity.this);


                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.forgetdialog);
                dialog.setCancelable(false);


                Button forgotpasswordcancel_bt = (Button) dialog.findViewById(R.id.forgotpasswordcancel_bt);
                Button forgotpasswordsubmit_bt = (Button) dialog.findViewById(R.id.forgotpasswordsubmit_bt);
                dialogusername_et=(EditText)dialog.findViewById(R.id.dialogusername_et);

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

                        if(forgotpassword_validation())
                        {
                            if (isInternetPresent)
                            {

                                str_forgotusername=dialogusername_et.getText().toString();
                                dialog.dismiss();
                                ForgotPassword forgotPassword=new ForgotPassword(LoginActivity.this);
                                forgotPassword.execute();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Kindly connect to internet", Toast.LENGTH_SHORT).show();
                            }
                        }



                    }
                });


                dialog.show();



            }
        });

        btn_smaple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent i = new Intent(LoginActivity.this, GuestActivity.class);
                Intent i = new Intent(LoginActivity.this, ImageCompWebserviceActivity.class);
                startActivity(i);
                finish();
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                leadid = edt_leadid.getText().toString();
                mobnumber = edt_mobnumber.getText().toString();


                if (validation()) {

                    internetDectector = new Class_InternetDectector(getApplicationContext());
                    isInternetPresent = internetDectector.isConnectingToInternet();

                    if (isInternetPresent) {
                        GetLoginDetails getloginDetails = new GetLoginDetails(LoginActivity.this);
                        getloginDetails.execute(mobnumber, leadid);
                        //Toast.makeText(getApplicationContext(),"net"+isInternetPresent.toString(),Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "No Internet Connected", Toast.LENGTH_SHORT).show();
                    }
                }


 /*               Intent ittLoginToEditProfile = new Intent(LoginActivity.this,EditProfileActivity.class);
                startActivity(ittLoginToEditProfile);*/
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivity(intent);
            }
        }


    }// end of oncreate

    public boolean forgotpassword_validation()
    {
        boolean b_username, b_password;
        b_username=true;


        if (dialogusername_et.getText().toString().trim().length() == 0||
                dialogusername_et.getText().toString().trim().length()<=4) {
            dialogusername_et.setError("Enter Valid Username");
            username_et.requestFocus();
            b_username = false;
        }



        return (b_username);

    }

    private void google_sign() {
        //getting the google signin intent
        Intent signInIntent = googlesigninclient_obj.getSignInIntent();
        //starting the activity for result
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }
    private void signOut()
    {
        googlesigninclient_obj.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        // ...
                        Toast.makeText(LoginActivity.this,"Sigined Out Successfully", Toast.LENGTH_SHORT).show();

                        /*editor_obj = sharedpreference_usercredential_Obj.edit();
                        editor_obj.putString(KeyValue_loggedfromgoogle, "");
                        editor_obj.commit();*/
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        //if the requestCode is the Google Sign In code that we defined at starting
        if (requestCode == RC_SIGN_IN) {
            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                //Google Sign In was successful, authenticate with Firebase
                account = task.getResult(ApiException.class);
                //authenticating with firebase
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct)
    {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());

        //getting the auth credential
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        str_gmailid=acct.getEmail().toString();

        //Now using firebase we are signing in the user here
        firebaseauth_obj.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = firebaseauth_obj.getCurrentUser();


                            Toast.makeText(LoginActivity.this, "User Signed In:"+str_gmailid, Toast.LENGTH_SHORT).show();

                           // AsyncTask_Googleloginverify();
                            GetGoogleLoginDetails getloginDetails = new GetGoogleLoginDetails(LoginActivity.this);
                            getloginDetails.execute(str_gmailid);

                            /*try {
                                postRequest();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/

                        } else
                        {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public boolean validation() {

        Boolean bleadid = true, bpassword = true;

        if (edt_leadid.getText().toString().length() == 0) {
            edt_leadid.setError("LEAD ID is required!");
            bleadid = false;
        }

        if (edt_mobnumber.getText().toString().length() == 0) {
            edt_mobnumber.setError("Mobile Number is required!");
            bpassword = false;
        }

        if (bleadid && bpassword) {
            return true;
        } else {
            return false;
        }
    }


    public class GetLoginDetails extends AsyncTask<String, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;


        GetLoginDetails(Context ctx) {
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(String... params) {
            String mobilenum = (String) params[0];
            String leadid = (String) params[1];
            //String versionCode = (String) params[2];

            SoapObject response = getLoginDetails(mobilenum, leadid);


            /*if(str_loginresponse.equals("Success"))
            {
                Toast.makeText(getApplicationContext(),"Set GCM",Toast.LENGTH_SHORT).show();
            }*/

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
/*            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            //progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();

            if (result != null) {

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

                    SoapPrimitive isProfileEdited = (SoapPrimitive) result.getProperty("isProfileEdit");

                    SoapPrimitive S_academicId = (SoapPrimitive) result.getProperty("AcademicId");
                    String str_academicCode = S_academicId.toString();

                    editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefId_S_Username, leadid);
                    editor_S.putString(PrefId_S_Password, mobnumber);
                    editor_S.putString(PrefID_SLeadID, leadid);

                    if (!str_academicCode.equals(null) && !str_academicCode.equals("anyType{}") && !str_academicCode.equals("{}") && !str_academicCode.isEmpty()) {
                        Log.d("Academic_code:", str_academicCode);
                        editor_S.putString(PrefId_S_AcademicId, str_academicCode);
                    }


            /*    SoapPrimitive S_ManagerName = (SoapPrimitive) result.getProperty("ManagerName");
                String str_ManagerName = S_ManagerName.toString();

                SoapPrimitive S_MobileNo = (SoapPrimitive) result.getProperty("MobileNo");
                String str_MobileNo = S_MobileNo.toString();

                SoapPrimitive S_MailId = (SoapPrimitive) result.getProperty("MailId");
                String str_MailId = S_MailId.toString();

                SoapPrimitive S_Manager_Image_Path = (SoapPrimitive) result.getProperty("Manager_Image_Path");
                String str_Manager_Image_Path = S_Manager_Image_Path.toString();


                if(!str_ManagerName.equals(null) && !str_ManagerName.equals("anyType{}") && !str_ManagerName.equals("{}") && !str_ManagerName.isEmpty()){
                    Log.d("str_ManagerName:",str_ManagerName);
                    editor_S.putString(PrefID_PM_SName,str_ManagerName);
                }

                if(!str_MobileNo.equals(null) && !str_MobileNo.equals("anyType{}") && !str_MobileNo.equals("{}") && !str_MobileNo.isEmpty()){
                    Log.d("str_MobileNo:",str_MobileNo);
                    editor_S.putString(PrefID_PM_CellNo,str_MobileNo);
                }

                if(!str_MailId.equals(null) && !str_MailId.equals("anyType{}") && !str_MailId.equals("{}") && !str_MailId.isEmpty()){
                    Log.d("str_MailId:",str_MailId);
                    editor_S.putString(PrefID_PM_SEmailID,str_MailId);
                }

                if(!str_Manager_Image_Path.equals(null) && !str_Manager_Image_Path.equals("anyType{}") && !str_Manager_Image_Path.equals("{}") && !str_Manager_Image_Path.isEmpty()){
                    Log.d("str_Manager_Image_Path:",str_Manager_Image_Path);
                    editor_S.putString(PrefID_PM_ImagePath,str_Manager_Image_Path);
                }*/


                    editor_S.commit();


                    if (role.toString().equals("Student")) {
                        AsyncCallFCM task = new AsyncCallFCM(LoginActivity.this);
                        task.execute();

                        Str_FCMName = result.getProperty("Username").toString().toString();//name

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

                        Str_s_PMImageURL = result.getProperty("Manager_Image_Path").toString();

                        str_s_gender = result.getProperty("Gender").toString();

                        str_s_starCount = result.getProperty("StartCount").toString();

                        str_fb = result.getProperty("Facebook").toString();
                        str_tw = result.getProperty("Twitter").toString();
                        str_in = result.getProperty("InstaGram").toString();
                        str_whatsapp = result.getProperty("WhatsApp").toString();

                        str_isFeesPaid = result.getProperty("isFeePaid").toString();
                        str_tshirt_status = result.getProperty("isRequestForTShirt").toString();
                        //-----------------------------edited by madhu 9aug19----------------
                        str_s_LLP = result.getProperty("LLP_Badges").toString();
                        str_s_Prayana = result.getProperty("Prayana_Badges").toString();
                        str_s_Yuva = result.getProperty("Yuva_Badges").toString();
                        str_s_Valedicotry = result.getProperty("Valedicotry_Badges").toString();

                        Log.d("str_isFeesPaidissssss", str_isFeesPaid);
                        Log.e("str_s_LLP=", str_s_LLP + "str_s_Prayana=" + str_s_Prayana);


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


                        String Imagestrings = Str_s_PMImageURL;
                        Str_PM_S_Full_ImgUrl = null;
                        if (Imagestrings == null || Imagestrings.equals("null") || Imagestrings.equals("anyType{}")) {
                            Str_PM_S_Full_ImgUrl = "null";
                        } else {
                            String arr[] = Imagestrings.split("/", 2);

                            String firstWord = arr[0];   //the
                            String secondWord = arr[1];

                            Str_PM_S_Full_ImgUrl = serverPath + secondWord;
                            Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                            Log.i("tag", "PMImage1=" + Str_s_PMImageURL);
                            Log.i("tag", "PMImgUrl=" + Str_PM_S_Full_ImgUrl);
                        }

                        // editor_S.putString(PrefID_pmimageurl,Str_PMImgUrl);

                        editor_S.putString(PrefID_PM_S_ImagePath, Str_PM_S_Full_ImgUrl);


                        if (isProfileEdited.toString().equals("0"))// first time user
                        {
                            Intent ittLoginToEditProfile = new Intent(LoginActivity.this, EditProfileActivity.class);
                            ittLoginToEditProfile.putExtra("leadid", leadid);
                            // ittLoginToEditProfile.putExtra("isProfileEdit","0");
                            startActivity(ittLoginToEditProfile);
                            editor_S.putString(PrefID_S_isprofileEdited, "0");

/*                        LoadMgrProfilePick loadMgrProfilePick = new LoadMgrProfilePick(context);
                        loadMgrProfilePick.execute();*/

                        }else if (isProfileEdited.toString().equals("2"))// Manager edited profile time user
                        {
                            Intent ittLoginToEditProfile = new Intent(LoginActivity.this, EditProfileActivity.class);
                            ittLoginToEditProfile.putExtra("leadid", leadid);
                            // ittLoginToEditProfile.putExtra("isProfileEdit","0");
                            startActivity(ittLoginToEditProfile);
                            editor_S.putString(PrefID_S_isprofileEdited, "2");

/*                        LoadMgrProfilePick loadMgrProfilePick = new LoadMgrProfilePick(context);
                        loadMgrProfilePick.execute();*/

                        } else {


                            str_studImgUrl = result.getProperty("UserImage").toString();

                            String Imagestring = str_studImgUrl;
                            Str_StudImgUrl = null; //Str_fullStudImgUrl
                            if (Imagestring.equals("null") || Imagestring.equals("anyType{}")) {
                                Str_StudImgUrl = "null"; //Str_fullStudImgUrl
                            } else {
                                String arr[] = Imagestring.split("/", 2);

                                String firstWord = arr[0];   //the
                                String secondWord = arr[1];

                                Str_StudImgUrl = serverPath + secondWord; //Str_fullStudImgUrl
                                Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                                Log.i("tag", "StudentImage1=" + str_studImgUrl);
                                Log.i("tag", "StudentImage2=" + Str_StudImgUrl);//Str_fullStudImgUrl
                            }

                            editor_S.putString(PrefID_S_isprofileEdited, "1");


                            editor_S.putString(PrefID_SImageUrl, Str_StudImgUrl); //Str_fullStudImgUrl

                      /*  LoadStudentProfilePick loadStudentProfilePick = new LoadStudentProfilePick(context);
                        loadStudentProfilePick.execute();*/
                            /*Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);*/
                            if (str_isuser_setpin.isEmpty()) {

                                //  Toast.makeText(getApplicationContext(),"empty",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, Activity_setpin.class);
                                startActivity(i);
                                finish();

                            } else {
                                Intent i = new Intent(LoginActivity.this, Activity_pinlogin.class);
                                startActivity(i);
                                finish();
                            }

                        }


                        editor_S.commit();

                    }
                    if (role.toString().equals("Manager")) {

                        AsyncCallFCM task = new AsyncCallFCM(LoginActivity.this);
                        task.execute();

                        Str_FCMName = result.getProperty("Username").toString().toString();
                        Str_Manager_Id = result.getProperty("ManagerId").toString();
                        Str_ManagerName = result.getProperty("Name").toString();
                        Str_PMEmailId = result.getProperty("MailId").toString();
                        Str_PM_Mobile = result.getProperty("MobileNo").toString();
                        Str_PMLocation = result.getProperty("Location").toString();
                        Str_PMImageURL = result.getProperty("UserImage").toString();
                        str_regid=result.getProperty("RegistrationId").toString();
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
                        /*Intent ittLoginToEditProfile = new Intent(LoginActivity.this, PMHomeActivity.class);

                        ittLoginToEditProfile.putExtra("ManagerId", ManagerId);
                        ittLoginToEditProfile.putExtra("ManagerName", Str_ManagerName);
                        ittLoginToEditProfile.putExtra("PMEmailId", Str_PMEmailId);
                        ittLoginToEditProfile.putExtra("PM_Mobile", Str_PM_Mobile);
                        ittLoginToEditProfile.putExtra("PMLocation", Str_PMLocation);
                        ittLoginToEditProfile.putExtra("PMImgUrl", Str_PMImgUrl);*/

                        editor_PM = shardprefPM_obj.edit();
                        editor_PM.putString(PrefID_PMID, Str_Manager_Id);
                        Log.d("ManagerIdis", Str_Manager_Id);
                        editor_PM.putString(PrefID_pmName, Str_ManagerName);
                        editor_PM.putString(PrefID_PMEMailID, Str_PMEmailId);
                        editor_PM.putString(PrefID_PMMobile, Str_PM_Mobile);
                        editor_PM.putString(PrefID_pmlocation, Str_PMLocation);
                        editor_PM.putString(PrefID_pmimageurl, Str_PMImgUrl);

                        editor_PM.putString(PrefID_pm_username, leadid.toString());
                        editor_PM.putString(PrefID_pm_password, mobnumber.toString());


                        Log.d("ManagerIds:", Str_Manager_Id);
                        Log.d("ManagerNames:", Str_ManagerName);
                        Log.d("ManagerEmailIds:", Str_PMEmailId);
                        Log.d("ManagerMobiles:", Str_PM_Mobile);
                        Log.d("Managerlocations:", Str_PMLocation);
                        Log.d("ManagerURLs:", Str_PMImgUrl);


                        editor_PM.commit();

                        editor_S = shardpref_S_obj.edit();
                        editor_S.putString(PrefID_Role, role.toString());
                        editor_S.putString(PrefID_RegID, str_regid);
                        editor_S.commit();


                       // startActivity(ittLoginToEditProfile);
                        if (str_isuser_setpin.isEmpty()) {

                            //  Toast.makeText(getApplicationContext(),"empty",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, Activity_setpin.class);
                            startActivity(i);
                            finish();

                        } else {
                            Intent i = new Intent(LoginActivity.this, Activity_pinlogin.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    //-----------------------------edited by madhu 9aug19----------------

                    if (role.toString().equals("Principal")) {
                        AsyncCallFCM task = new AsyncCallFCM(LoginActivity.this);
                        task.execute();

                        Str_FCMName = result.getProperty("Username").toString().toString();
                        Principal_MailId = result.getProperty("Name").toString().toString();
                        Principal_WhatsAppNo = result.getProperty("Lead_Id").toString().toString();
                        Principal_MobileNo = result.getProperty("Student_Mobile_No").toString().toString();
                        Principal_College_Name = result.getProperty("College_Name").toString().toString();
                        Principal_College_Id = result.getProperty("LoginId").toString().toString();
                        str_regid=result.getProperty("RegistrationId").toString();

                        editor_Principle_info = shardprefPrinciple_info.edit();
                        editor_Principle_info.putString(PrefID_PName, Str_FCMName);
                        editor_Principle_info.putString(PrefID_AcademicId, str_academicCode);
                        editor_Principle_info.putString(PrefID_PMailId, Principal_MailId);
                        editor_Principle_info.putString(PrefID_PWhatsapp, Principal_WhatsAppNo);
                        editor_Principle_info.putString(PrefID_PMobileNo, Principal_MobileNo);
                        editor_Principle_info.putString(PrefID_CollgeName, Principal_College_Name);
                        editor_Principle_info.putString(PrefID_PCollegeId, Principal_College_Id);

                        editor_Principle_info.commit();

                        editor_S = shardpref_S_obj.edit();
                        editor_S.putString(PrefID_Role, role.toString());
                        editor_S.putString(PrefID_RegID, str_regid);
                        editor_S.commit();

                       /* Intent i = new Intent(LoginActivity.this, PrincipleHomeActivity.class);
                        startActivity(i);
                        finish();*/
                        if (str_isuser_setpin.isEmpty()) {

                            //  Toast.makeText(getApplicationContext(),"empty",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, Activity_setpin.class);
                            startActivity(i);
                            finish();

                        } else {
                            Intent i = new Intent(LoginActivity.this, Activity_pinlogin.class);
                            startActivity(i);
                            finish();
                        }

                    }
                } else {
                    Log.d("Status is: ", status.toString());
                }

                //progressDialog.dismiss();

/*            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject list = (SoapObject) result.getProperty(i);
                SoapPrimitive S_Name, email_id,S_Status;
                Object O_CohortDuration,O_name,O_mobileNumber,O_email_Id,O_program,O_cohortName,O_Address,O_BloodGroup,O_fellowshipLogo,O_student_photo;

                Log.d("Result of soap objectdd",list.toString());

                String results =

            *//*    if(list.toString().contains(getString(R.string.txtStatus))){
                    S_Status = (SoapPrimitive) list.getProperty("Status");
                    applicationId.setError(S_Status.toString());
                }

                else {

                    applicationId.setError(null);

                    O_name = list.getProperty(getString(R.string.txtSName));
                    if(O_name.toString().equals(getString(R.string.txtAnyType)) || O_name.toString().equals(null)){
                        name.setText("");
                        nameMain = name.getText().toString();
                    }
                    else{
                        S_Name = (SoapPrimitive) list.getProperty(getString(R.string.txtSName));
                        name.setText(S_Name.toString());
                        nameMain = name.getText().toString();
                    }

                    O_program = list.getProperty(getString(R.string.txtFellowshipName));
                    if(O_program.toString().equals(getString(R.string.txtAnyType)) || O_program.toString().equals(null) ){
                        program.setText("");
                    }
                    else{
                        program.setText(((SoapPrimitive) list.getProperty(getString(R.string.txtFellowshipName))).toString());
                    }

                    O_cohortName = list.getProperty(getString(R.string.txtCName));
                    if(O_cohortName.toString().equals(getString(R.string.txtAnyType)) || O_cohortName.toString().equals(null)){
                        cohortName="";
                    }
                    else{
                        cohortName = ((SoapPrimitive) list.getProperty(getString(R.string.txtCName))).toString();
                    }

                    O_mobileNumber = list.getProperty(getString(R.string.txtMobNo));
                    if(O_mobileNumber.toString().equals(getString(R.string.txtAnyType)) || O_mobileNumber.toString().equals(null)){
                        mobileNumber="";
                    }
                    else{
                        mobileNumber = ((SoapPrimitive) list.getProperty(getString(R.string.txtMobNo))).toString();
                    }

                    O_email_Id = list.getProperty(getString(R.string.txtemailId));
                    if(O_email_Id.toString().equals(getString(R.string.txtAnyType)) || O_email_Id.toString().equals(null)) {
                        emailid="";
                    }
                    else{
                        emailid = ((SoapPrimitive) list.getProperty(getString(R.string.txtemailId))).toString();
                    }

                    O_student_photo = list.getProperty(getString(R.string.txtOstudentPhoto));
                    if(O_student_photo.toString().equals(getString(R.string.txtAnyType)) || O_student_photo.toString().equals(null)) {
                        idCardGenerated = "";
                    }
                    else{
                        idCardGenerated = ((SoapPrimitive) list.getProperty(getString(R.string.txtOstudentPhoto))).toString();
                    }



                    O_CohortDuration = list.getProperty(getString(R.string.txtCohortDuration));
                    if(O_CohortDuration.toString().equals(getString(R.string.txtAnyType))){
                        cohortDurationFromTo = "";
                    }
                    else{
                        cohortDurationFromTo = ((SoapPrimitive) list.getProperty(getString(R.string.txtCohortDuration))).toString();
                    }

                    O_Address = list.getProperty(getString(R.string.txtAddress));
                    if(O_Address.toString().equals(getString(R.string.txtAnyType))){
                        studentAddress = "";
                    }
                    else{
                        studentAddress = ((SoapPrimitive) list.getProperty(getString(R.string.txtAddress))).toString();
                    }

                    O_BloodGroup = list.getProperty(getString(R.string.txtBloodGrp));
                    if(O_BloodGroup.toString().equals(getString(R.string.txtAnyType)) || O_BloodGroup.toString().equals(getString(R.string.txtzero))){
                        bloodGroup = "";
                    }
                    else{
                        bloodGroup = ((SoapPrimitive) list.getProperty(getString(R.string.txtBloodGrp))).toString();
                    }


                    //O_fellowshipLogo = (((SoapPrimitive) list.getProperty("FellowshipLogo")).toString());
                    O_fellowshipLogo = list.getProperty(getString(R.string.txtFellowshipLogo));
                    if(O_fellowshipLogo.toString().equals(getString(R.string.txtAnyType)) || O_fellowshipLogo.toString().equals(getString(R.string.txtzero))){
                        fellowshipimageLogo = "";
                    }
                    else{
                        fellowshipimageLogo = (((SoapPrimitive) list.getProperty(getString(R.string.txtFellowshipLogo))).toString());

                        String namespc = ((SoapPrimitive) list.getProperty(getString(R.string.txtFellowshipLogo))).getNamespace();
                        //Log.d("namespc", namespc);
                        String url1 = fellowshipimageLogo.substring(2);
                        //Log.d("url1", url1);

                        fellowshipimageLogo = getString(R.string.txtMainNamespace1) + url1;
                    }

                    RelativeLayout rltlytMain = (RelativeLayout) findViewById(R.id.rltlyt_details);
                    rltlytMain.setVisibility(View.VISIBLE);

                }*//*

            }*/
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class ForgotPassword extends AsyncTask<String, Void, SoapPrimitive> {

        Context context;
        //AlertDialog alertDialog;

        //private ProgressBar progressBar;

        private ProgressDialog progressDialog;

        ForgotPassword(Context ctx) {
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(String... params) {

            SoapPrimitive response = ForgotPassword();

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

                AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle(R.string.alert);


                dialog.setMessage(" Password is sent to your Mail \n Thank you.");

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
                Toast toast = Toast.makeText(getApplicationContext(), "unauthorized Email ID", Toast.LENGTH_LONG);
                TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.RED);
                toast.show();
            }

        }

    }
    private SoapPrimitive ForgotPassword()
    {

        String METHOD_NAME = "ForgetPassword";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ForgetPassword";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            request.addProperty("Email_Id", str_forgotusername);//<Lead_Id>string</Lead_Id>
            //request.addProperty("StudentName",edt_studName.getText().toString());
           // request.addProperty("PIN",str_loginpin);//<Email_id>string</Email_id>


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

            Log.e("tag","Request Forgotpwd="+request.toString());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                // Log.d("soapResponseyyyyyyy",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.d("tag","soapRespons ForgotPwd="+response.toString());

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

    public class GetGoogleLoginDetails extends AsyncTask<String, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;


        GetGoogleLoginDetails(Context ctx) {
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(String... params) {
            String gmail = (String) params[0];
          //  String leadid = (String) params[1];
            //String versionCode = (String) params[2];

            SoapObject response = getGoogleLoginDetails(gmail);


            /*if(str_loginresponse.equals("Success"))
            {
                Toast.makeText(getApplicationContext(),"Set GCM",Toast.LENGTH_SHORT).show();
            }*/

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
/*            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            //progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();

            if (result != null) {

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

                    SoapPrimitive isProfileEdited = (SoapPrimitive) result.getProperty("isProfileEdit");

                    SoapPrimitive S_academicId = (SoapPrimitive) result.getProperty("AcademicId");
                    String str_academicCode = S_academicId.toString();

                    editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefId_S_Username, leadid);
                    editor_S.putString(PrefId_S_Password, mobnumber);
                    editor_S.putString(PrefID_SLeadID, leadid);

                    if (!str_academicCode.equals(null) && !str_academicCode.equals("anyType{}") && !str_academicCode.equals("{}") && !str_academicCode.isEmpty()) {
                        Log.d("Academic_code:", str_academicCode);
                        editor_S.putString(PrefId_S_AcademicId, str_academicCode);
                    }


            /*    SoapPrimitive S_ManagerName = (SoapPrimitive) result.getProperty("ManagerName");
                String str_ManagerName = S_ManagerName.toString();

                SoapPrimitive S_MobileNo = (SoapPrimitive) result.getProperty("MobileNo");
                String str_MobileNo = S_MobileNo.toString();

                SoapPrimitive S_MailId = (SoapPrimitive) result.getProperty("MailId");
                String str_MailId = S_MailId.toString();

                SoapPrimitive S_Manager_Image_Path = (SoapPrimitive) result.getProperty("Manager_Image_Path");
                String str_Manager_Image_Path = S_Manager_Image_Path.toString();


                if(!str_ManagerName.equals(null) && !str_ManagerName.equals("anyType{}") && !str_ManagerName.equals("{}") && !str_ManagerName.isEmpty()){
                    Log.d("str_ManagerName:",str_ManagerName);
                    editor_S.putString(PrefID_PM_SName,str_ManagerName);
                }

                if(!str_MobileNo.equals(null) && !str_MobileNo.equals("anyType{}") && !str_MobileNo.equals("{}") && !str_MobileNo.isEmpty()){
                    Log.d("str_MobileNo:",str_MobileNo);
                    editor_S.putString(PrefID_PM_CellNo,str_MobileNo);
                }

                if(!str_MailId.equals(null) && !str_MailId.equals("anyType{}") && !str_MailId.equals("{}") && !str_MailId.isEmpty()){
                    Log.d("str_MailId:",str_MailId);
                    editor_S.putString(PrefID_PM_SEmailID,str_MailId);
                }

                if(!str_Manager_Image_Path.equals(null) && !str_Manager_Image_Path.equals("anyType{}") && !str_Manager_Image_Path.equals("{}") && !str_Manager_Image_Path.isEmpty()){
                    Log.d("str_Manager_Image_Path:",str_Manager_Image_Path);
                    editor_S.putString(PrefID_PM_ImagePath,str_Manager_Image_Path);
                }*/


                    editor_S.commit();


                    if (role.toString().equals("Student")) {
                        AsyncCallFCM task = new AsyncCallFCM(LoginActivity.this);
                        task.execute();

                        Str_FCMName = result.getProperty("Username").toString().toString();//name

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

                        Str_s_PMImageURL = result.getProperty("Manager_Image_Path").toString();

                        str_s_gender = result.getProperty("Gender").toString();

                        str_s_starCount = result.getProperty("StartCount").toString();

                        str_fb = result.getProperty("Facebook").toString();
                        str_tw = result.getProperty("Twitter").toString();
                        str_in = result.getProperty("InstaGram").toString();
                        str_whatsapp = result.getProperty("WhatsApp").toString();

                        str_isFeesPaid = result.getProperty("isFeePaid").toString();
                        str_tshirt_status = result.getProperty("isRequestForTShirt").toString();
                        //-----------------------------edited by madhu 9aug19----------------
                        str_s_LLP = result.getProperty("LLP_Badges").toString();
                        str_s_Prayana = result.getProperty("Prayana_Badges").toString();
                        str_s_Yuva = result.getProperty("Yuva_Badges").toString();
                        str_s_Valedicotry = result.getProperty("Valedicotry_Badges").toString();

                        Log.d("str_isFeesPaidissssss", str_isFeesPaid);
                        Log.e("str_s_LLP=", str_s_LLP + "str_s_Prayana=" + str_s_Prayana);


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


                        String Imagestrings = Str_s_PMImageURL;
                        Str_PM_S_Full_ImgUrl = null;
                        if (Imagestrings == null || Imagestrings.equals("null") || Imagestrings.equals("anyType{}")) {
                            Str_PM_S_Full_ImgUrl = "null";
                        } else {
                            String arr[] = Imagestrings.split("/", 2);

                            String firstWord = arr[0];   //the
                            String secondWord = arr[1];

                            Str_PM_S_Full_ImgUrl = serverPath + secondWord;
                            Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                            Log.i("tag", "PMImage1=" + Str_s_PMImageURL);
                            Log.i("tag", "PMImgUrl=" + Str_PM_S_Full_ImgUrl);
                        }

                        // editor_S.putString(PrefID_pmimageurl,Str_PMImgUrl);

                        editor_S.putString(PrefID_PM_S_ImagePath, Str_PM_S_Full_ImgUrl);


                        if (isProfileEdited.toString().equals("0"))// first time user
                        {
                            Intent ittLoginToEditProfile = new Intent(LoginActivity.this, EditProfileActivity.class);
                            ittLoginToEditProfile.putExtra("leadid", leadid);
                            // ittLoginToEditProfile.putExtra("isProfileEdit","0");
                            startActivity(ittLoginToEditProfile);
                            editor_S.putString(PrefID_S_isprofileEdited, "0");

/*                        LoadMgrProfilePick loadMgrProfilePick = new LoadMgrProfilePick(context);
                        loadMgrProfilePick.execute();*/

                        }else if (isProfileEdited.toString().equals("2"))// Manager edited profile time user
                        {
                            Intent ittLoginToEditProfile = new Intent(LoginActivity.this, EditProfileActivity.class);
                            ittLoginToEditProfile.putExtra("leadid", leadid);
                            // ittLoginToEditProfile.putExtra("isProfileEdit","0");
                            startActivity(ittLoginToEditProfile);
                            editor_S.putString(PrefID_S_isprofileEdited, "2");

/*                        LoadMgrProfilePick loadMgrProfilePick = new LoadMgrProfilePick(context);
                        loadMgrProfilePick.execute();*/

                        } else {


                            str_studImgUrl = result.getProperty("UserImage").toString();

                            String Imagestring = str_studImgUrl;
                            Str_StudImgUrl = null; //Str_fullStudImgUrl
                            if (Imagestring.equals("null") || Imagestring.equals("anyType{}")) {
                                Str_StudImgUrl = "null"; //Str_fullStudImgUrl
                            } else {
                                String arr[] = Imagestring.split("/", 2);

                                String firstWord = arr[0];   //the
                                String secondWord = arr[1];

                                Str_StudImgUrl = serverPath + secondWord; //Str_fullStudImgUrl
                                Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                                Log.i("tag", "StudentImage1=" + str_studImgUrl);
                                Log.i("tag", "StudentImage2=" + Str_StudImgUrl);//Str_fullStudImgUrl
                            }

                            editor_S.putString(PrefID_S_isprofileEdited, "1");


                            editor_S.putString(PrefID_SImageUrl, Str_StudImgUrl); //Str_fullStudImgUrl

                      /*  LoadStudentProfilePick loadStudentProfilePick = new LoadStudentProfilePick(context);
                        loadStudentProfilePick.execute();*/
                           /* Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(i);*/

                            if (str_isuser_setpin.isEmpty()) {

                                //  Toast.makeText(getApplicationContext(),"empty",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, Activity_setpin.class);
                                startActivity(i);
                                finish();

                            } else {
                                Intent i = new Intent(LoginActivity.this, Activity_pinlogin.class);
                                startActivity(i);
                                finish();
                            }

                        }


                        editor_S.commit();

                    }
                    if (role.toString().equals("Manager")) {

                        AsyncCallFCM task = new AsyncCallFCM(LoginActivity.this);
                        task.execute();

                        Str_FCMName = result.getProperty("Username").toString().toString();
                        Str_Manager_Id = result.getProperty("ManagerId").toString();
                        Str_ManagerName = result.getProperty("Name").toString();
                        Str_PMEmailId = result.getProperty("MailId").toString();
                        Str_PM_Mobile = result.getProperty("MobileNo").toString();
                        Str_PMLocation = result.getProperty("Location").toString();
                        Str_PMImageURL = result.getProperty("UserImage").toString();
                        str_regid=result.getProperty("RegistrationId").toString();
                        Log.e("tag", "PMImageURL=" + Str_PMImageURL);
                        Log.e("tag", "str_regid=" + str_regid);

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
                       /* Intent ittLoginToEditProfile = new Intent(LoginActivity.this, PMHomeActivity.class);

                        ittLoginToEditProfile.putExtra("ManagerId", ManagerId);
                        ittLoginToEditProfile.putExtra("ManagerName", Str_ManagerName);
                        ittLoginToEditProfile.putExtra("PMEmailId", Str_PMEmailId);
                        ittLoginToEditProfile.putExtra("PM_Mobile", Str_PM_Mobile);
                        ittLoginToEditProfile.putExtra("PMLocation", Str_PMLocation);
                        ittLoginToEditProfile.putExtra("PMImgUrl", Str_PMImgUrl);*/

                        editor_PM = shardprefPM_obj.edit();
                        editor_PM.putString(PrefID_PMID, Str_Manager_Id);
                        Log.d("ManagerIdis", Str_Manager_Id);
                        editor_PM.putString(PrefID_pmName, Str_ManagerName);
                        editor_PM.putString(PrefID_PMEMailID, Str_PMEmailId);
                        editor_PM.putString(PrefID_PMMobile, Str_PM_Mobile);
                        editor_PM.putString(PrefID_pmlocation, Str_PMLocation);
                        editor_PM.putString(PrefID_pmimageurl, Str_PMImgUrl);

                        editor_PM.putString(PrefID_pm_username, leadid.toString());
                        editor_PM.putString(PrefID_pm_password, mobnumber.toString());


                        Log.d("ManagerIds:", Str_Manager_Id);
                        Log.d("ManagerNames:", Str_ManagerName);
                        Log.d("ManagerEmailIds:", Str_PMEmailId);
                        Log.d("ManagerMobiles:", Str_PM_Mobile);
                        Log.d("Managerlocations:", Str_PMLocation);
                        Log.d("ManagerURLs:", Str_PMImgUrl);


                        editor_PM.commit();

                        editor_S = shardpref_S_obj.edit();
                        editor_S.putString(PrefID_Role, role.toString());
                        editor_S.putString(PrefID_RegID, str_regid);
                        editor_S.commit();


                     //   startActivity(ittLoginToEditProfile);

                        if (str_isuser_setpin.isEmpty()) {

                            //  Toast.makeText(getApplicationContext(),"empty",Toast.LENGTH_SHORT).show();
                           /* Intent i = new Intent(LoginActivity.this, Activity_setpin.class);
                            startActivity(i);
                            finish();*/
                            Intent ittLoginToEditProfile = new Intent(LoginActivity.this, Activity_setpin.class);

                           /* ittLoginToEditProfile.putExtra("ManagerId", ManagerId);
                            ittLoginToEditProfile.putExtra("ManagerName", Str_ManagerName);
                            ittLoginToEditProfile.putExtra("PMEmailId", Str_PMEmailId);
                            ittLoginToEditProfile.putExtra("PM_Mobile", Str_PM_Mobile);
                            ittLoginToEditProfile.putExtra("PMLocation", Str_PMLocation);
                            ittLoginToEditProfile.putExtra("PMImgUrl", Str_PMImgUrl);*/
                            startActivity(ittLoginToEditProfile);
                            finish();
                        } else {
                            /*Intent i = new Intent(LoginActivity.this, Activity_pinlogin.class);
                            startActivity(i);
                            finish();*/
                            Intent ittLoginToEditProfile = new Intent(LoginActivity.this, Activity_pinlogin.class);

                           /* ittLoginToEditProfile.putExtra("ManagerId", ManagerId);
                            ittLoginToEditProfile.putExtra("ManagerName", Str_ManagerName);
                            ittLoginToEditProfile.putExtra("PMEmailId", Str_PMEmailId);
                            ittLoginToEditProfile.putExtra("PM_Mobile", Str_PM_Mobile);
                            ittLoginToEditProfile.putExtra("PMLocation", Str_PMLocation);
                            ittLoginToEditProfile.putExtra("PMImgUrl", Str_PMImgUrl);*/
                            startActivity(ittLoginToEditProfile);
                            finish();
                        }
                    }

                    //-----------------------------edited by madhu 9aug19----------------

                    if (role.toString().equals("Principal")) {
                        AsyncCallFCM task = new AsyncCallFCM(LoginActivity.this);
                        task.execute();

                        Str_FCMName = result.getProperty("Username").toString().toString();
                        Principal_MailId = result.getProperty("Name").toString().toString();
                        Principal_WhatsAppNo = result.getProperty("Lead_Id").toString().toString();
                        Principal_MobileNo = result.getProperty("Student_Mobile_No").toString().toString();
                        Principal_College_Name = result.getProperty("College_Name").toString().toString();
                        Principal_College_Id = result.getProperty("LoginId").toString().toString();
                        str_regid=result.getProperty("RegistrationId").toString();

                        editor_Principle_info = shardprefPrinciple_info.edit();
                        editor_Principle_info.putString(PrefID_PName, Str_FCMName);
                        editor_Principle_info.putString(PrefID_AcademicId, str_academicCode);
                        editor_Principle_info.putString(PrefID_PMailId, Principal_MailId);
                        editor_Principle_info.putString(PrefID_PWhatsapp, Principal_WhatsAppNo);
                        editor_Principle_info.putString(PrefID_PMobileNo, Principal_MobileNo);
                        editor_Principle_info.putString(PrefID_CollgeName, Principal_College_Name);
                        editor_Principle_info.putString(PrefID_PCollegeId, Principal_College_Id);

                        editor_Principle_info.commit();

                        editor_S = shardpref_S_obj.edit();
                        editor_S.putString(PrefID_Role, role.toString());
                        editor_S.putString(PrefID_RegID, str_regid);
                        editor_S.commit();

                      /*  Intent i = new Intent(LoginActivity.this, PrincipleHomeActivity.class);
                        startActivity(i);
                        finish();*/
                        if (str_isuser_setpin.isEmpty()) {

                            //  Toast.makeText(getApplicationContext(),"empty",Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, Activity_setpin.class);
                            startActivity(i);
                            finish();

                        } else {
                            Intent i = new Intent(LoginActivity.this, Activity_pinlogin.class);
                            startActivity(i);
                            finish();
                        }

                    }
                } else {
                    Log.d("Status is: ", status.toString());
                }

                //progressDialog.dismiss();

/*            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject list = (SoapObject) result.getProperty(i);
                SoapPrimitive S_Name, email_id,S_Status;
                Object O_CohortDuration,O_name,O_mobileNumber,O_email_Id,O_program,O_cohortName,O_Address,O_BloodGroup,O_fellowshipLogo,O_student_photo;

                Log.d("Result of soap objectdd",list.toString());

                String results =

            *//*    if(list.toString().contains(getString(R.string.txtStatus))){
                    S_Status = (SoapPrimitive) list.getProperty("Status");
                    applicationId.setError(S_Status.toString());
                }

                else {

                    applicationId.setError(null);

                    O_name = list.getProperty(getString(R.string.txtSName));
                    if(O_name.toString().equals(getString(R.string.txtAnyType)) || O_name.toString().equals(null)){
                        name.setText("");
                        nameMain = name.getText().toString();
                    }
                    else{
                        S_Name = (SoapPrimitive) list.getProperty(getString(R.string.txtSName));
                        name.setText(S_Name.toString());
                        nameMain = name.getText().toString();
                    }

                    O_program = list.getProperty(getString(R.string.txtFellowshipName));
                    if(O_program.toString().equals(getString(R.string.txtAnyType)) || O_program.toString().equals(null) ){
                        program.setText("");
                    }
                    else{
                        program.setText(((SoapPrimitive) list.getProperty(getString(R.string.txtFellowshipName))).toString());
                    }

                    O_cohortName = list.getProperty(getString(R.string.txtCName));
                    if(O_cohortName.toString().equals(getString(R.string.txtAnyType)) || O_cohortName.toString().equals(null)){
                        cohortName="";
                    }
                    else{
                        cohortName = ((SoapPrimitive) list.getProperty(getString(R.string.txtCName))).toString();
                    }

                    O_mobileNumber = list.getProperty(getString(R.string.txtMobNo));
                    if(O_mobileNumber.toString().equals(getString(R.string.txtAnyType)) || O_mobileNumber.toString().equals(null)){
                        mobileNumber="";
                    }
                    else{
                        mobileNumber = ((SoapPrimitive) list.getProperty(getString(R.string.txtMobNo))).toString();
                    }

                    O_email_Id = list.getProperty(getString(R.string.txtemailId));
                    if(O_email_Id.toString().equals(getString(R.string.txtAnyType)) || O_email_Id.toString().equals(null)) {
                        emailid="";
                    }
                    else{
                        emailid = ((SoapPrimitive) list.getProperty(getString(R.string.txtemailId))).toString();
                    }

                    O_student_photo = list.getProperty(getString(R.string.txtOstudentPhoto));
                    if(O_student_photo.toString().equals(getString(R.string.txtAnyType)) || O_student_photo.toString().equals(null)) {
                        idCardGenerated = "";
                    }
                    else{
                        idCardGenerated = ((SoapPrimitive) list.getProperty(getString(R.string.txtOstudentPhoto))).toString();
                    }



                    O_CohortDuration = list.getProperty(getString(R.string.txtCohortDuration));
                    if(O_CohortDuration.toString().equals(getString(R.string.txtAnyType))){
                        cohortDurationFromTo = "";
                    }
                    else{
                        cohortDurationFromTo = ((SoapPrimitive) list.getProperty(getString(R.string.txtCohortDuration))).toString();
                    }

                    O_Address = list.getProperty(getString(R.string.txtAddress));
                    if(O_Address.toString().equals(getString(R.string.txtAnyType))){
                        studentAddress = "";
                    }
                    else{
                        studentAddress = ((SoapPrimitive) list.getProperty(getString(R.string.txtAddress))).toString();
                    }

                    O_BloodGroup = list.getProperty(getString(R.string.txtBloodGrp));
                    if(O_BloodGroup.toString().equals(getString(R.string.txtAnyType)) || O_BloodGroup.toString().equals(getString(R.string.txtzero))){
                        bloodGroup = "";
                    }
                    else{
                        bloodGroup = ((SoapPrimitive) list.getProperty(getString(R.string.txtBloodGrp))).toString();
                    }


                    //O_fellowshipLogo = (((SoapPrimitive) list.getProperty("FellowshipLogo")).toString());
                    O_fellowshipLogo = list.getProperty(getString(R.string.txtFellowshipLogo));
                    if(O_fellowshipLogo.toString().equals(getString(R.string.txtAnyType)) || O_fellowshipLogo.toString().equals(getString(R.string.txtzero))){
                        fellowshipimageLogo = "";
                    }
                    else{
                        fellowshipimageLogo = (((SoapPrimitive) list.getProperty(getString(R.string.txtFellowshipLogo))).toString());

                        String namespc = ((SoapPrimitive) list.getProperty(getString(R.string.txtFellowshipLogo))).getNamespace();
                        //Log.d("namespc", namespc);
                        String url1 = fellowshipimageLogo.substring(2);
                        //Log.d("url1", url1);

                        fellowshipimageLogo = getString(R.string.txtMainNamespace1) + url1;
                    }

                    RelativeLayout rltlytMain = (RelativeLayout) findViewById(R.id.rltlyt_details);
                    rltlytMain.setVisibility(View.VISIBLE);

                }*//*

            }*/
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    public class LoadMgrProfilePick extends AsyncTask<Void, Void, Bitmap> {

        private Context context;

        LoadMgrProfilePick(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap bitmaplogo = null;

            try {
                if (!Str_PMImgUrl.equals(null) && !Str_PMImgUrl.equalsIgnoreCase("null") && !Str_PMImgUrl.isEmpty() && Str_PMImgUrl != "" && !Str_PMImgUrl.equals("anyType{}") && !Str_PMImgUrl.equals("{}")) {
                    URL urlis = new URL(Str_PMImgUrl);
                    InputStream inputStream = urlis.openStream();
                    bitmaplogo = BitmapFactory.decodeStream(inputStream);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                final String exceptnMsg = e.getMessage().toString();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this, exceptnMsg, Toast.LENGTH_LONG).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                final String exceptnMsg = e.getMessage().toString();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this, exceptnMsg, Toast.LENGTH_LONG).show();
                    }
                });
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
        protected void onPostExecute(Bitmap pmBitmap) {
            if (pmBitmap != null) {
                Log.d("insidemgrbitmap", pmBitmap.toString());

                try {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    pmBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] imageinbytesArray = stream.toByteArray();
                    String encodedImage = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);

     /*           shardpref_S_obj.edit();
                editor_S.putString(PrefID_PM_ImgBase64,encodedImage);*/


                    editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefID_PM_ImgBase64, encodedImage);
                    editor_S.commit();


                    //StudentPMImageBitmap.setPmByteArray(imageinbytesArray);


                    //StudentPMImageBitmap.setPmBitmap(pmBitmap);

                    progressDialog.dismiss();

                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(i);
                } catch (OutOfMemoryError ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }
    }


    public class LoadStudentProfilePick extends AsyncTask<Void, Void, Bitmap> {

        private Context context;

        LoadStudentProfilePick(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            Log.d("InsideLoadStudent", "ProfilePick");
        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap bitmaplogo = null;

            try {
                if (!Str_StudImgUrl.equals(null) && !Str_StudImgUrl.equalsIgnoreCase("null") && !Str_StudImgUrl.isEmpty() && Str_StudImgUrl != "" && !Str_StudImgUrl.equals("anyType{}") && !Str_StudImgUrl.equals("{}")) {
                    Log.d("InsideStudImgUrl", "notnulls");
                    URL urlis = new URL(Str_StudImgUrl);
                    InputStream inputStream = urlis.openStream();
                    bitmaplogo = BitmapFactory.decodeStream(inputStream);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                final String exceptnMsg = e.getMessage().toString();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this, exceptnMsg, Toast.LENGTH_LONG).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
                final String exceptnMsg = e.getMessage().toString();

                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(LoginActivity.this, exceptnMsg, Toast.LENGTH_LONG).show();
                    }
                });
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
        protected void onPostExecute(Bitmap studBitmap) {
            if (studBitmap != null) {
                Log.d("insidestudbitmap", studBitmap.toString());

                try {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    studBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    //imageInByte;
                    byte[] imageinbytesArray = stream.toByteArray();


                    String encodedImage = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);

                    editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefID_S_ImgBase64, encodedImage);
                    editor_S.commit();
                } catch (OutOfMemoryError ex) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                //textEncode.setText(encodedImage);

                //StudentPMImageBitmap.setStudByteArray(imageinbytesArray);


                //StudentPMImageBitmap.setStudBitmap(studBitmap);
            }

            LoadMgrProfilePick loadMgrProfilePick = new LoadMgrProfilePick(context);
            loadMgrProfilePick.execute();
        }
    }


    private SoapObject getLoginDetails(String mobilenum, String leadid) {

        //changed from ValidateLogin to ValidateLogin1
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

            String username = edt_leadid.getText().toString();
            String password = edt_mobnumber.getText().toString();
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
                Log.d("soap LoginResponse", response.toString());

                return response;

            } catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
            }
        } catch (Exception t) {
            Log.d("exception outside", t.getMessage().toString());
        }
        return null;


    }
    private SoapObject getGoogleLoginDetails(String gmail) {

        //changed from ValidateLogin to ValidateLogin1
        String METHOD_NAME = "ValidateLogin1_Principle_Google";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ValidateLogin1_Principle_Google";

        try {
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            Log.d("gmail", gmail);

            /*request.addProperty("Username", mobilenum);
            request.addProperty("Password", leadid);*/

            /*request.addProperty("Username", leadid);
            request.addProperty("Password", mobilenum);*/

            String username = edt_leadid.getText().toString();
            String password = edt_mobnumber.getText().toString();
            request.addProperty("Mail_Id", "madhushree.kubsad@dfmail.org");
          //  request.addProperty("Password", password);

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
                Log.d("soap LoginResponse", response.toString());

                return response;

            } catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
            }
        } catch (Exception t) {
            Log.d("exception outside", t.getMessage().toString());
        }
        return null;


    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.INTERNET);
        int FourthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        int FifthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_NETWORK_STATE);
        int SixthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
        int SeventhPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WAKE_LOCK);
        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED && ThirdPermissionResult == PackageManager.PERMISSION_GRANTED && FourthPermissionResult == PackageManager.PERMISSION_GRANTED && FifthPermissionResult == PackageManager.PERMISSION_GRANTED && SixthPermissionResult == PackageManager.PERMISSION_GRANTED && SeventhPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    //Added by Sripad
    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]
                {
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CALL_PHONE
                }, RequestPermissionCode);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case RequestPermissionCode:

                if (grantResults.length > 0) {

                    boolean WriteExternalStoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadExternalStoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean InternetPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadPhonePermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean AccessNetworkPermission = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean CallPhonePermission = grantResults[5] == PackageManager.PERMISSION_GRANTED;

                    if (WriteExternalStoragePermission && ReadExternalStoragePermission && InternetPermission && ReadPhonePermission && AccessNetworkPermission && CallPhonePermission) {

                        //Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }

                break;
        }

    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText)
    {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                // tv.setBackgroundColor(Color.CYAN);
               /* tv.setBackgroundDrawable(
                        new ColorDrawable(Color.parseColor(COLOR)));
                tv.setTextColor(Color.WHITE);
                tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/laouibold.ttf"));*/
                return;
            }
        }
    }


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

        public AsyncCallFCM(LoginActivity activity) {
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


    /*public void setGCM1() {


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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
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


        Log.v("DeviceIMEI", "" + tmDevice);
        mobileNumber = "" + tm1.getLine1Number();
        Log.v("getLine1Number value", "" + mobileNumber);

        String mobileNumber1 = "" + tm1.getPhoneType();
        Log.v("getPhoneType value", "" + mobileNumber1);
        //tmSerial = "" + tm1.getSimSerialNumber();


        TelephonyManager tMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            tmSerial = "" + tMgr.getSimSerialNumber();
        }catch(Exception ex)
        {
            tmSerial="inaccessible";
        }

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
        //WindowManager w = getWindowManager();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            //   w.getDefaultDisplay().getSize(size);
            *//*Measuredwidth = w.getDefaultDisplay().getWidth();//size.x;
            Measuredheight = w.getDefaultDisplay().getHeight();//size.y;*//*

            Measuredwidth = displaymetrics.widthPixels;//size.x;
            Measuredheight = displaymetrics.heightPixels;//size.y;
        } else {
            *//*Display d = w.getDefaultDisplay();
            Measuredwidth = d.getWidth();
            Measuredheight = d.getHeight();*//*

            Measuredwidth = displaymetrics.widthPixels;//size.x;
            Measuredheight = displaymetrics.heightPixels;//size.y;
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


    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        }
        System.exit(0);
    }




























}// end of class
