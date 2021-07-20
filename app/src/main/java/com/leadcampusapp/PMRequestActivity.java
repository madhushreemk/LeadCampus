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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class PMRequestActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmrequest);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //  getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));


        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Request Module");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        View view1;
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.tablayoutopen_req, null);

        View view2;
        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.tablayoutclose_req, null);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));

        TabLayout.Tab selectedTab = tabLayout.getTabAt(0);
        View view = selectedTab.getCustomView();
        TextView selectedText = (TextView) view.findViewById(R.id.txt_req);
        selectedText.setTextColor(Color.parseColor("#000000"));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        final PMRequestAdapter adapter = new PMRequestAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_req);
                selectedText.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_req);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_req);
                selectedText.setTextColor(Color.parseColor("#000000"));
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

            AlertDialog.Builder adb = new AlertDialog.Builder(PMRequestActivity.this);
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
    public void onBackPressed()
    {
        Intent i = new Intent(this,PMHomeActivity.class);
        startActivity(i);
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
            Intent itthomeToLogin = new Intent(PMRequestActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(PMRequestActivity.this ,PMHomeActivity.class);
            startActivity(ittProjDtlsToHome);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
