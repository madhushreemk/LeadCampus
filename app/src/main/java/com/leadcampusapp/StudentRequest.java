package com.leadcampusapp;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
//import android.support.design.widget.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

public class StudentRequest extends AppCompatActivity {
    TabLayout tablayout_compoff;



    public final static String COLOR = "#3F51B5";

    // Titles of the individual pages (displayed in tabs)
    private final String[] PAGE_TITLES = new String[]{
            "APPLY REQUEST",
            "HISTORY"
    };

    // The fragments that are used as the individual pages
    private final Fragment[] PAGES = new Fragment[]{
            new StudentApplyRequest_fragment(),
            new Student_history()
                };


    // The ViewPager is responsible for sliding pages (fragments) in and out upon user input
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_request);

        mViewPager = (ViewPager) findViewById(R.id.compoff_viewpager);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));


        tablayout_compoff = (TabLayout) findViewById(R.id.compoff_tablayout);
        tablayout_compoff.setupWithViewPager(mViewPager);

               getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor(COLOR)));

        getSupportActionBar().setDisplayUseLogoEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
       // getSupportActionBar().setHomeAsUpIndicator(
        //        R.drawable.lef);

    }// end of Oncreate


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }


        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:

                    return new StudentApplyRequest_fragment();
                case 1:

                    return new Student_history();

            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {


            return PAGE_TITLES[position];
        }

    }// end of MyPagerAdapter


    @Override
    public void onBackPressed()
    {
        Intent i = new Intent(this,HomeActivity.class);
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
            Intent itthomeToLogin = new Intent(StudentRequest.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(StudentRequest.this ,HomeActivity.class);
            startActivity(ittProjDtlsToHome);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
