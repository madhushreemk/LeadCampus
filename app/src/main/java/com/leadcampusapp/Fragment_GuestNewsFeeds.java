package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.design.widget.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class Fragment_GuestNewsFeeds extends Fragment implements ConnectivityReceiver.ConnectivityReceiverListener{

    ImageButton img_newsfeedsBack;

    public static final String  PREFBook_Stud= "prefbook_stud";
    private SharedPreferences shardpref_S_obj;
    public static final String PrefID_Role = "prefid_role";
    public String str_role = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {


        View view1 = inflater.inflate(R.layout.activity_news_feeds, container, false);
        shardpref_S_obj= getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_Role, "").trim();
        str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();

        /*getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);*/
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

     /*   View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("News Feeds");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
*/





       /* View view1;
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.leadstorytablayout, null);*/

        View view2;
        LayoutInflater inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.leadfbtablayout, null);

        View view3;
        LayoutInflater inflater3 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view3 = inflater3.inflate(R.layout.leadintablayout, null);

        View view4;
        LayoutInflater inflater4 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view4 = inflater4.inflate(R.layout.leadtwtablayout, null);




        TabLayout tabLayout = (TabLayout) view1.findViewById(R.id.tab_layout);
        //   tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view4));


        TabLayout.Tab selectedTab = tabLayout.getTabAt(0);
        View view = selectedTab.getCustomView();
        ImageView img1=(ImageView) view1.findViewById(R.id.img_newsfeedstabtitle);
        //img1.setImageResource(R.drawable.story_wht);
        //     img1.setImageResource(R.drawable.fb_001);

        // TextView selectedText = (TextView) view.findViewById(R.id.txt_newsfeedstabtitle);
        //selectedText.setTextColor(Color.parseColor("#FFFFFF"));


        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) view1.findViewById(R.id.pager);
        final NewsFeedsAdapter adapter = new NewsFeedsAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
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

        return view1;
    }



    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {

        if (!isConnected) {

            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
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

    }



}