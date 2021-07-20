package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class LeadStoryActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    public static final String  PREFBook_Stud= "prefbook_stud";
    private SharedPreferences shardpref_S_obj;
    public static final String PrefID_Role = "prefid_role";
    public String str_role = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_story);

        shardpref_S_obj=LeadStoryActivity.this.getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_Role, "").trim();
        str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Stories");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            Intent intent = getIntent();
        String story_possion=intent.getStringExtra("stroy_position");
        Log.e("tag","story_possion="+story_possion);
//           Log.e("string2",getIntent().getStringExtra("stroy_position").toString());
            // Create a new Fragment to be placed in the activity layout
            LeadStoryFragment firstFragment = new LeadStoryFragment();

            Bundle bundle = new Bundle();
            bundle.putString("story_possion" , story_possion);
            firstFragment.setArguments(bundle);

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
          //  firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
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

            AlertDialog.Builder adb = new AlertDialog.Builder(LeadStoryActivity.this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action_editProfile = menu.findItem(R.id.action_editProfile);
        action_editProfile.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


     /*   if (id == R.id.action_editProfile) {
            Intent ittNewsFeedsToEditProfile = new Intent(NewsFeedsActivity.this ,EditProfileActivity.class);
            startActivity(ittNewsFeedsToEditProfile);
            return true;
        }*/

        if (id == R.id.action_logout) {

            //deleteCache(NewsFeedsActivity.this);

            Intent ittNewsFeedsToLogin = new Intent(LeadStoryActivity.this ,LoginActivity.class);
            startActivity(ittNewsFeedsToLogin);
            return true;
        }

        if (id == android.R.id.home) {
  /*          Intent ittNewsFeedsToHome = new Intent(NewsFeedsActivity.this ,HomeActivity.class);
            startActivity(ittNewsFeedsToHome);*/

            if(str_role.equals("Student")) {
                Intent ittEventsToStudHome = new Intent(LeadStoryActivity.this, HomeActivity.class);
                startActivity(ittEventsToStudHome);
            }else if(str_role.equalsIgnoreCase("Principal")){
                Intent ittEventsToStudHome = new Intent(LeadStoryActivity.this, PrincipleHomeActivity.class);
                startActivity(ittEventsToStudHome);
            }
            else{
                Intent ittEventsToPMHome = new Intent(LeadStoryActivity.this, PMHomeActivity.class);
                startActivity(ittEventsToPMHome);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
