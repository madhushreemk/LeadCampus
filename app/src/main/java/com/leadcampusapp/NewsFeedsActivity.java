package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.support.design.widget.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//import com.android.sripad.leadnew_22_6_2018.R;

import com.google.android.material.tabs.TabLayout;

import java.io.File;

public class NewsFeedsActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    ImageButton img_newsfeedsBack;

    public static final String  PREFBook_Stud= "prefbook_stud";
    private SharedPreferences shardpref_S_obj;
    public static final String PrefID_Role = "prefid_role";
    public String str_role = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feeds);

        //img_newsfeedsBack = (ImageButton) findViewById(R.id.img_newsfeedsBack);

        shardpref_S_obj=NewsFeedsActivity.this.getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_Role, "").trim();
        str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("News Feeds");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);






       /* View view1;
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.leadstorytablayout, null);*/

        View view2;
        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.leadfbtablayout, null);

        View view3;
        LayoutInflater inflater3 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view3 = inflater3.inflate(R.layout.leadintablayout, null);

        View view4;
        LayoutInflater inflater4 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view4 = inflater4.inflate(R.layout.leadtwtablayout, null);




        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
     //   tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view4));


        TabLayout.Tab selectedTab = tabLayout.getTabAt(0);
        View view = selectedTab.getCustomView();
        ImageView img1=(ImageView) view.findViewById(R.id.img_newsfeedstabtitle);
        //img1.setImageResource(R.drawable.story_wht);
   //     img1.setImageResource(R.drawable.fb_001);

       // TextView selectedText = (TextView) view.findViewById(R.id.txt_newsfeedstabtitle);
        //selectedText.setTextColor(Color.parseColor("#FFFFFF"));


        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final NewsFeedsAdapter adapter = new NewsFeedsAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
               /* if(tab.getPosition()==0) {
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_newsfeedstabtitle);
                    //img1.setImageResource(R.drawable.story_wht);
                    img1.setImageResource(R.drawable.story);
                }else */
               if(tab.getPosition()==0){
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_fbfeedstabtitle);
                    //img1.setImageResource(R.drawable.fb_whit_001);
                    img1.setImageResource(R.drawable.fb_001);
                }else if(tab.getPosition()==1){
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_infeedstabtitle);
                    //img1.setImageResource(R.drawable.instwht_001);
                    img1.setImageResource(R.drawable.inst_001);
                }else if(tab.getPosition()==2){
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_twfeedstabtitle);
                    //img1.setImageResource(R.drawable.tw_wht001);
                    img1.setImageResource(R.drawable.tw_001);
                }
          //      TextView selectedText = (TextView) view.findViewById(R.id.txt_newsfeedstabtitle);
            //    selectedText.setTextColor(Color.parseColor("#FFFFFF"));

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
               /* if(tab.getPosition()==0) {
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_newsfeedstabtitle);
                    img1.setImageResource(R.drawable.story);
                }else*/
               if(tab.getPosition()==0){
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_fbfeedstabtitle);
                    img1.setImageResource(R.drawable.fb_001);
                }else if(tab.getPosition()==1){
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_infeedstabtitle);
                    img1.setImageResource(R.drawable.inst_001);
                }else if(tab.getPosition()==2){
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_twfeedstabtitle);
                    img1.setImageResource(R.drawable.tw_001);
                }
            //    TextView selectedText = (TextView) view.findViewById(R.id.txt_newsfeedstabtitle);
              //  selectedText.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
               /* if(tab.getPosition()==0) {
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_newsfeedstabtitle);
                    //img1.setImageResource(R.drawable.story_wht);
                    img1.setImageResource(R.drawable.story);
                }else */
               if(tab.getPosition()==0){
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_fbfeedstabtitle);
                    //img1.setImageResource(R.drawable.fb_whit_001);
                    img1.setImageResource(R.drawable.fb_001);
                }else if(tab.getPosition()==1){
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_infeedstabtitle);
                    //img1.setImageResource(R.drawable.instwht_001);
                    img1.setImageResource(R.drawable.inst_001);
                }else if(tab.getPosition()==2){
                    ImageView img1 = (ImageView) view.findViewById(R.id.img_twfeedstabtitle);
                    //img1.setImageResource(R.drawable.tw_wht001);
                    img1.setImageResource(R.drawable.tw_001);
                }
              //  TextView selectedText = (TextView) view.findViewById(R.id.txt_newsfeedstabtitle);
                //selectedText.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });

        /*img_newsfeedsBack.setOnClickListener(new View.OnClickListener() {
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


     /*   if (id == R.id.action_editProfile) {
            Intent ittNewsFeedsToEditProfile = new Intent(NewsFeedsActivity.this ,EditProfileActivity.class);
            startActivity(ittNewsFeedsToEditProfile);
            return true;
        }*/

        if (id == R.id.action_logout) {

            //deleteCache(NewsFeedsActivity.this);

            Intent ittNewsFeedsToLogin = new Intent(NewsFeedsActivity.this ,LoginActivity.class);
            startActivity(ittNewsFeedsToLogin);
            return true;
        }

        if (id == android.R.id.home) {
  /*          Intent ittNewsFeedsToHome = new Intent(NewsFeedsActivity.this ,HomeActivity.class);
            startActivity(ittNewsFeedsToHome);*/

            if(str_role.equals("Student")) {
                Intent ittEventsToStudHome = new Intent(NewsFeedsActivity.this, HomeActivity.class);
                startActivity(ittEventsToStudHome);
            }else if(str_role.equalsIgnoreCase("Principal")){
                Intent ittEventsToStudHome = new Intent(NewsFeedsActivity.this, PrincipleHomeActivity.class);
                startActivity(ittEventsToStudHome);
            }
            else{
                Intent ittEventsToPMHome = new Intent(NewsFeedsActivity.this, PMHomeActivity.class);
                startActivity(ittEventsToPMHome);
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
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

            AlertDialog.Builder adb = new AlertDialog.Builder(NewsFeedsActivity.this);
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

}
