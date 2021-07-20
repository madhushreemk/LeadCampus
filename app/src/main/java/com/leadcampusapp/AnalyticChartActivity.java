package com.leadcampusapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.design.widget.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

//import com.android.sripad.leadnew_22_6_2018.R;

public class AnalyticChartActivity extends AppCompatActivity {

    ImageButton img_eventsBack;

    public static final String  PREFBook_Stud= "prefbook_stud";
    private SharedPreferences shardpref_S_obj;
    public static final String PrefID_Role = "prefid_role";
    public String str_role = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyticchart);

        shardpref_S_obj=AnalyticChartActivity.this.getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_Role, "").trim();
        str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();


       /* img_eventsBack = (ImageButton) findViewById(R.id.img_eventsBack);*/

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Analytical Charts");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        View view1;
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.pm_projectcountchart_tablayout, null);

       /* View view2;
        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.pm_stu_collchart_tablayout, null);
*/
        View view3;
        LayoutInflater inflater3 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view3 = inflater3.inflate(R.layout.pm_themeproject_tablayout, null);

        View view4;
        LayoutInflater inflater4 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view4 = inflater4.inflate(R.layout.pm_fundamt_tablayout, null);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
      //  tabLayout.addTab(tabLayout.newTab().setCustomView(view2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view4));

        TabLayout.Tab selectedTab = tabLayout.getTabAt(0);
        View view = selectedTab.getCustomView();
        TextView selectedText = (TextView) view.findViewById(R.id.txt_analytictabtitle);
        selectedText.setTextColor(Color.parseColor("#000000"));


        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final AnalyticAdapter adapter = new AnalyticAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_analytictabtitle);
                selectedText.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_analytictabtitle);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_analytictabtitle);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }
        });

        /*img_eventsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
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


    /*    if (id == R.id.action_editProfile) {
            Intent ittEventsToEditProfile = new Intent(EventsActivity.this ,EditProfileActivity.class);
            startActivity(ittEventsToEditProfile);
            return true;
        }*/

        if (id == R.id.action_logout) {
            Intent ittEventsToLogin = new Intent(AnalyticChartActivity.this ,LoginActivity.class);
            startActivity(ittEventsToLogin);
            return true;
        }

        if (id == android.R.id.home) {

            if(str_role.equals("Student")) {
                Intent ittEventsToStudHome = new Intent(AnalyticChartActivity.this, HomeActivity.class);
                startActivity(ittEventsToStudHome);
            }else{
                Intent ittEventsToPMHome = new Intent(AnalyticChartActivity.this, PMHomeActivity.class);
                startActivity(ittEventsToPMHome);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
