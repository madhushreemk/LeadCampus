package com.leadcampusapp;

import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GuestActivity extends AppCompatActivity
{

    DrawerLayout mDrawerLayout;
    // ListView represents Navigation Drawer
    ListView mDrawerList;

    // ActionBarDrawerToggle indicates the presence of Navigation Drawer in the action bar
    ActionBarDrawerToggle mDrawerToggle;
    // Title of the action bar
    String mTitle="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);


        /*getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Stories");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);

        mTitle = (String) getTitle();

        // Getting reference to the ActionBarDrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle( this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close){

            /** Called when drawer is closed */
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            /** Called when a drawer is opened */
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle("Guest Login");
                invalidateOptionsMenu();
            }
        };

        // Setting DrawerToggle on DrawerLayout
        mDrawerLayout.setDrawerListener(mDrawerToggle);

// Creating an ArrayAdapter to add items to the listview mDrawerList
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getBaseContext(),
                R.layout.sidebardrawer_list_item ,
                getResources().getStringArray(R.array.guestsidebar)
        );


        // Setting the adapter on mDrawerList
        mDrawerList.setAdapter(adapter);

        // Enabling Home button
       // getActionBar().setHomeButtonEnabled(true);

        // Enabling Up navigation
       // getActionBar().setDisplayHomeAsUpEnabled(true);





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
          //  firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }
}
