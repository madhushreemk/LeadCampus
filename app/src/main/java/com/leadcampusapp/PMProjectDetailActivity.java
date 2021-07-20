package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.design.widget.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

//import com.android.sripad.leadnew_22_6_2018.R;


public class PMProjectDetailActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

   // Integer ManagerId;
   // String ManagerName,PMEmailId,PM_Mobile,PMLocation,PMImgUrl;

    public int pageCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmproject_detail);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Project Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       /*  Intent intent = getIntent();
       String ManagerIdstr=intent.getStringExtra("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerIdstr);
        if(ManagerId.equals("null")){
            ManagerId=1;
        }else
        {
            ManagerId = Integer.parseInt(ManagerIdstr);

        }
        ManagerId = getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);

        Intent intent = getIntent();
        ManagerName = intent.getStringExtra("ManagerName");
        PMEmailId= intent.getStringExtra("PMEmailId");
        PM_Mobile= intent.getStringExtra("PM_Mobile");
        PMLocation= intent.getStringExtra("PMLocation");
        PMImgUrl=intent.getStringExtra("PMImgUrl");


        Log.i("Tag","PMImgUrl=="+PMImgUrl);*/


       //PMUnapproved_DetailsActivity.java

        //PMUnapprovedFragment.java
        //PMApprovedFragments.java
        //PMComplitionFragment.java
        //PMFundAmountFragment.java
        //
        View view1;
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.unapprovedtablayout, null);

        View view4;
        LayoutInflater inflater4 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view4 = inflater4.inflate(R.layout.approvedtablayout, null);

        View view3;
        LayoutInflater inflater3 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view3 = inflater3.inflate(R.layout.pmcomplitionprojecttablayout, null);

        View view2;
        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.fundamounttablayout, null);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layoutPD);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view4));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));

        pageCount = getIntent().getExtras().getInt("pageCount");
        Log.i("tag","pageCount PMProjectDetails="+pageCount);

        TabLayout.Tab tab = null;
        if(pageCount==0) {
            TabLayout.Tab selectedTab = tabLayout.getTabAt(0);
            View view = selectedTab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitleunapproved);
            selectedText.setTextColor(Color.parseColor("#000000"));
        }else if(pageCount==1){
            TabLayout.Tab selectedTab = tabLayout.getTabAt(1);
            View view = selectedTab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitleApproved);
            selectedText.setTextColor(Color.parseColor("#000000"));
        }else if(pageCount==2){
            TabLayout.Tab selectedTab = tabLayout.getTabAt(2);
            View view = selectedTab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitlecomplition);
            selectedText.setTextColor(Color.parseColor("#000000"));
        }else if(pageCount==3){
            TabLayout.Tab selectedTab = tabLayout.getTabAt(3);
            View view = selectedTab.getCustomView();
            TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitlefund);
            selectedText.setTextColor(Color.parseColor("#000000"));
        }
     /*   float myTabLayoutSize = 360;
        if (DeviceInfo.getWidthDP(this) >= myTabLayoutSize ){
            tabLayout.setTabMode(TabLayout.MODE_FIXED);
        } else {*/
            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //}
        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager1);

        final PMProjectDetailsAdapter adapter = new PMProjectDetailsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(pageCount);
      //  Fragment.SavedState savedState = getFragmentManager().saveFragmentInstanceState();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if(tab.getPosition()==0) {
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitleunapproved);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }else if(tab.getPosition()==1){
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitleApproved);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }else if(tab.getPosition()==2){
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitlecomplition);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }else if(tab.getPosition()==3){
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitlefund);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if(tab.getPosition()==0) {
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitleunapproved);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }else if(tab.getPosition()==1){
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitleApproved);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }else if(tab.getPosition()==2){
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitlecomplition);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }else if(tab.getPosition()==3){
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitlefund);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }
           //     TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitle);
             //   selectedText.setTextColor(Color.parseColor("#000000"));
               // viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                if(tab.getPosition()==0) {
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitleunapproved);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }else if(tab.getPosition()==1){
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitleApproved);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }else if(tab.getPosition()==2){
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitlecomplition);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }else if(tab.getPosition()==3){
                    TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitlefund);
                    selectedText.setTextColor(Color.parseColor("#000000"));
                }
              //  TextView selectedText = (TextView) view.findViewById(R.id.PMtexttitle);
                //selectedText.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

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

            AlertDialog.Builder adb = new AlertDialog.Builder(PMProjectDetailActivity.this);
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


      /*  if (id == R.id.action_editProfile) {
            Intent itthomeToEditProfile = new Intent(PMProjectDetailActivity.this ,PMEditProfileActivity.class);
            itthomeToEditProfile.putExtra("ManagerId",ManagerId);
            startActivity(itthomeToEditProfile);
            return true;
        }*/


        if (id == R.id.action_logout) {
            Intent itthomeToLogin = new Intent(PMProjectDetailActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(PMProjectDetailActivity.this ,PMHomeActivity.class);
          /*  ittProjDtlsToHome.putExtra("ManagerId",ManagerId);
            ittProjDtlsToHome.putExtra("ManagerName",ManagerName);
            ittProjDtlsToHome.putExtra("PMEmailId",PMEmailId);
            ittProjDtlsToHome.putExtra("PM_Mobile",PM_Mobile);
            ittProjDtlsToHome.putExtra("PMLocation",PMLocation);
            ittProjDtlsToHome.putExtra("PMImgUrl",PMImgUrl);*/
            startActivity(ittProjDtlsToHome);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent ittProjDtlsToHome = new Intent(PMProjectDetailActivity.this ,PMHomeActivity.class);
        startActivity(ittProjDtlsToHome);
    }
}
