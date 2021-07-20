package com.leadcampusapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
//import android.support.design.widget.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.android.sripad.leadnew_22_6_2018.R;

import com.google.android.material.tabs.TabLayout;

import java.io.File;

public class ProjectDetails extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    ImageButton img_proDtlsBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);

       /* getSupportActionBar().setTitle("Project Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));*/

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
      //  getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));


        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Project Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


         //ProjectStatusFragment.java
        //addProjectFragment.java
       // completionProjectFragmentWPercent.java

        //img_proDtlsBack = (ImageButton) findViewById(R.id.img_proDtlsBack);



        View view1;
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.projectstatustablayout, null);

        View view2;
        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.addprojecttablayout, null);

        View view3;
        LayoutInflater inflater3 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view3 = inflater3.inflate(R.layout.completionprojecttablayout, null);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));

        TabLayout.Tab selectedTab = tabLayout.getTabAt(0);

        View view = selectedTab.getCustomView();
        TextView selectedText = (TextView) view.findViewById(R.id.txt_prodtlstabtitle);
        selectedText.setTextColor(Color.parseColor("#000000"));


        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final ProjectDetailsAdapter adapter = new ProjectDetailsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);


        //viewPager.setCurrentItem(2);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_prodtlstabtitle);
                selectedText.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_prodtlstabtitle);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }// AddProjectFragment.java

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_prodtlstabtitle);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }
        });

        /*img_proDtlsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

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

            AlertDialog.Builder adb = new AlertDialog.Builder(ProjectDetails.this);
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


    @Override
    public void onBackPressed()
    {
        finish();
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
            Intent ittProjDtlsToEditProfile = new Intent(ProjectDetails.this ,EditProfileActivity.class);
            startActivity(ittProjDtlsToEditProfile);
            return true;
        }

        if (id == R.id.action_logout) {

            //deleteCache(ProjectDetails.this);

            Intent ittProjDtlsToLogin = new Intent(ProjectDetails.this ,LoginActivity.class);
            startActivity(ittProjDtlsToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(ProjectDetails.this ,HomeActivity.class);
            startActivity(ittProjDtlsToHome);
            finish();
            return true;
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


    /*public class GetStudentDetails extends AsyncTask<String, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        private ProgressBar progressBar;

        GetStudentDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(String... params) {
            String mobilenum = (String) params [0];
            String leadid = (String) params[1];
            //String versionCode = (String) params[2];

            SoapObject response = getLoginDetails(mobilenum,leadid);

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
            progressBar.setVisibility(View.GONE);

            String finalResult = result.toString();
            String finals = finalResult.replace("anyType","");
            Log.d("Finals is",finals);


            SoapPrimitive status = (SoapPrimitive) result.getProperty("Status");
            //Log.d("Status is:",status.toString());




            if(status.toString().equals("Success")) {
                SoapPrimitive role = (SoapPrimitive) result.getProperty("Role");
                Log.d("Role is:", role.toString());

                SoapPrimitive isProfileEdited =(SoapPrimitive)result.getProperty("isProfileEdit");



                if(role.toString().equals("Student"))
                {

                    str_regid = result.getProperty("RegistrationId").toString(); //1
                    str_sname = result.getProperty("Name").toString().toString();//name
                    str_s_pmname = result.getProperty("ManagerName").toString();//Abbhinandan
                    str_s_leadid = result.getProperty("Lead_Id").toString();//MHOO
                    str_s_emailID =result.getProperty("MailId").toString();// mailid@
                    str_s_cellno = result.getProperty("MobileNo").toString();

                    str_s_collegeName = result.getProperty("College_Name").toString();

                    Str_PMImageURL = result.getProperty("Manager_Image_Path").toString();


                    editor_S = shardpref_S_obj.edit();

                    editor_S.putString(PrefID_RegID,str_regid);
                    editor_S.putString(PrefID_SName,str_sname);

                    editor_S.putString(PrefID_SLeadID,str_s_leadid);



                    editor_S.putString(PrefID_SCollegeName,str_s_collegeName);

                    editor_S.putString(PrefID_Role,role.toString());



                    editor_S.putString(PrefID_PM_SName,str_s_pmname);
                    editor_S.putString(PrefID_PM_SEmailID,str_s_emailID);
                    editor_S.putString(PrefID_SCellNo,str_s_cellno);


                    String Imagestrings = Str_PMImageURL;
                    String Str_PMImgUrl = null;
                    if(Imagestrings.equals("null")||Imagestrings.equals("anyType{}")){
                        Str_PMImgUrl="null";
                    }else {
                        String arr[] = Imagestrings.split("/", 2);

                        String firstWord = arr[0];   //the
                        String secondWord = arr[1];

                        Str_PMImgUrl = serverPath + secondWord;
                        Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                        Log.i("tag", "PMImage1=" + Str_PMImageURL);
                        Log.i("tag", "PMImgUrl=" + Str_PMImgUrl);
                    }

                    editor_S.putString(PrefID_pmimageurl,Str_PMImgUrl);


                    if(isProfileEdited.toString().equals("0"))// first time user
                    {
                        Intent ittLoginToEditProfile = new Intent(LoginActivity.this,EditProfileActivity.class);
                        ittLoginToEditProfile.putExtra("leadid",leadid);
                        ittLoginToEditProfile.putExtra("isProfileEdit","0");
                        startActivity(ittLoginToEditProfile);
                    }
                    else
                    {
                        //Added by Sripad
                        Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(i);

                        str_studImgUrl = result.getProperty("UserImage").toString();

                        String Imagestring = str_studImgUrl;
                        String Str_StudImgUrl = null;
                        if(Imagestring.equals("null")||Imagestring.equals("anyType{}")){
                            Str_StudImgUrl="null";
                        }else {
                            String arr[] = Imagestring.split("/", 2);

                            String firstWord = arr[0];   //the
                            String secondWord = arr[1];

                            Str_StudImgUrl = serverPath + secondWord;
                            Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                            Log.i("tag", "StudentImage1=" + str_studImgUrl);
                            Log.i("tag", "StudentImage2=" + Str_StudImgUrl);
                        }





                        editor_S.putString(PrefID_SImageUrl,Str_StudImgUrl);

                    }

                    editor_S.commit();

                }
                if(role.toString().equals("Manager"))
                {


                    Str_Manager_Id=result.getProperty("ManagerId").toString();
                    Str_ManagerName=result.getProperty("Name").toString();
                    Str_PMEmailId=result.getProperty("MailId").toString();
                    Str_PM_Mobile=result.getProperty("MobileNo").toString();
                    Str_PMLocation=result.getProperty("Location").toString();
                    Str_PMImageURL=result.getProperty("UserImage").toString();
                    Log.i("tag","PMImageURL="+Str_PMImageURL);

                    String Imagestring = Str_PMImageURL;
                    String Str_PMImgUrl = null;
                    if(Imagestring.equals("null")||Imagestring.equals("anyType{}")){
                        Str_PMImgUrl="null";
                    }else {
                        String arr[] = Imagestring.split("/", 2);

                        String firstWord = arr[0];   //the
                        String secondWord = arr[1];

                        Str_PMImgUrl = serverPath + secondWord;
                        Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);

                        Log.i("tag", "PMImage1=" + Str_PMImageURL);
                        Log.i("tag", "PMImgUrl=" + Str_PMImgUrl);
                    }






                    ManagerId=Integer.parseInt(Str_Manager_Id);
                    Intent ittLoginToEditProfile = new Intent(LoginActivity.this,PMHomeActivity.class);
                    ittLoginToEditProfile.putExtra("ManagerId",ManagerId);
                    ittLoginToEditProfile.putExtra("ManagerName",Str_ManagerName);
                    ittLoginToEditProfile.putExtra("PMEmailId",Str_PMEmailId);
                    ittLoginToEditProfile.putExtra("PM_Mobile",Str_PM_Mobile);
                    ittLoginToEditProfile.putExtra("PMLocation",Str_PMLocation);
                    ittLoginToEditProfile.putExtra("PMImgUrl",Str_PMImgUrl);

                    editor_PM = shardprefPM_obj.edit();
                    editor_PM.putString(PrefID_PMID,Str_Manager_Id);
                    editor_PM.putString(PrefID_pmName,Str_ManagerName);
                    editor_PM.putString(PrefID_PMEMailID,Str_PMEmailId);
                    editor_PM.putString(PrefID_PMMobile,Str_PM_Mobile);
                    editor_PM.putString(PrefID_pmlocation,Str_PMLocation);
                    editor_PM.putString(PrefID_pmimageurl,Str_PMImgUrl);


                    Log.d("ManagerIds:",Str_Manager_Id);
                    Log.d("ManagerNames:",Str_ManagerName);
                    Log.d("ManagerEmailIds:",Str_PMEmailId);
                    Log.d("ManagerMobiles:",Str_PM_Mobile);
                    Log.d("Managerlocations:",Str_PMLocation);
                    Log.d("ManagerURLs:",Str_PMImgUrl);


                    editor_PM.commit();

                    editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefID_Role,role.toString());
                    editor_S.commit();





                    startActivity(ittLoginToEditProfile);
                }
            }else{
                Log.d("Status is: ",status.toString());
            }

       }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }*/
}
