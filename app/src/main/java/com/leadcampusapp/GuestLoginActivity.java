package com.leadcampusapp;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class GuestLoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {




     
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);//app_bar_guest_login.xml
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        /*getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Stories");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            LeadStoryFragment firstFragment = new LeadStoryFragment();

            Bundle bundle = new Bundle();
            bundle.putString("story_possion" , "0");
            firstFragment.setArguments(bundle);
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
           // firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            /*getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();*/

                setTitle("Stories");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, firstFragment).commit();
        }









        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i = new Intent(GuestLoginActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.guest_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sidebar_story)
        {
            // Handle the camera action

            // Create a new Fragment to be placed in the activity layout
            LeadStoryFragment firstFragment = new LeadStoryFragment();

            Bundle bundle = new Bundle();
            bundle.putString("story_possion" , "0");
            firstFragment.setArguments(bundle);
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            //firstFragment.setArguments(getIntent().getExtras());

            setTitle("Stories");
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, firstFragment).commit();


        } else if (id == R.id.nav_events)
        {
            CurrentEventsFragment currenteventsFragment_Obj = new CurrentEventsFragment();

            setTitle("Current Events");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, currenteventsFragment_Obj).commit();
        }
        else if (id == R.id.nav_newsfeeds)
        {
            Fragment_GuestNewsFeeds fragment_guestNewsFeeds = new Fragment_GuestNewsFeeds();

            setTitle("News Feeds");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment_guestNewsFeeds).commit();
        }
        else if (id == R.id.nav_contact_us)
        {
             Fragment_ContactUs fragment_contactus_obj = new Fragment_ContactUs();

            setTitle("Contact Us");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment_contactus_obj).commit();

        }
        else if (id == R.id.nav_about_us)
        {
            Fragment_GuestAboutUs fragment_guestAboutUs_Obj = new Fragment_GuestAboutUs();
            setTitle("About Us");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment_guestAboutUs_Obj).commit();



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
