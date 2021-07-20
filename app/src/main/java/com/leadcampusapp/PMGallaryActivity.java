package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

//import com.android.sripad.leadnew_22_6_2018.R;

import java.util.ArrayList;

public class PMGallaryActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    ImageView selectedImage;
    ArrayList<Bitmap> bitmapList;
    String LeadId,PDId;
    String Student_Image_Path,MobileNo;
    String Name;
    Integer ManagerId;

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    SharedPreferences shardprefPM_obj;
    String str_MangerID;
    Integer MDId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmgallary);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
     //   getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Gallery");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Gallery gallery = (Gallery) findViewById(R.id.gallery);

        bitmapList = GalleryImageBitmap.getBitmapGalleryImage();

        selectedImage=(ImageView)findViewById(R.id.imageView);
        selectedImage.setImageBitmap(bitmapList.get(0));

        Intent intent = getIntent();
        Name = intent.getStringExtra("name");
        Log.e("TAG","name="+Name);

        LeadId=intent.getStringExtra("LeadId");
        PDId = intent.getStringExtra("PDId");
        MobileNo=intent.getStringExtra("MobileNo");
        Student_Image_Path=intent.getStringExtra("Student_Image_Path");

    /*    ManagerId = getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);*/
        shardprefPM_obj=this.getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);

        Log.e("TAG","LeadId="+LeadId);
        Log.e("TAG","PDId="+PDId);

        gallery.setSpacing(5);
        gallery.setAnimationDuration(2000);
        final GalleryImageAdapter galleryImageAdapter= new GalleryImageAdapter(this,bitmapList);

        gallery.setAdapter(galleryImageAdapter);


        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // show the selected Image
                //selectedImage.setImageResource(galleryImageAdapter.mImageIds[position]);
                selectedImage.setImageBitmap(galleryImageAdapter.mBitmapList.get(position));
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

            AlertDialog.Builder adb = new AlertDialog.Builder(PMGallaryActivity.this);
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


       /* if (id == R.id.action_editProfile) {
            Intent itthomeToEditProfile = new Intent(PMComplitionProjectActivity.this ,PMEditProfileActivity.class);
            startActivity(itthomeToEditProfile);
            return true;
        }*/


        if (id == R.id.action_logout) {
            Intent itthomeToLogin = new Intent(PMGallaryActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == android.R.id.home) {
        /*    Intent ittProjDtlsToHome = new Intent(PMGallaryActivity.this ,PMComplitionProjectActivity.class);
            ittProjDtlsToHome.putExtra("ManagerId",MDId);
            ittProjDtlsToHome.putExtra("name",Name);
            ittProjDtlsToHome.putExtra("projectId",PDId);
            ittProjDtlsToHome.putExtra("LeadId",LeadId);
            ittProjDtlsToHome.putExtra("MobileNo",MobileNo);
            ittProjDtlsToHome.putExtra("Student_Image_Path",Student_Image_Path);
            startActivity(ittProjDtlsToHome);*/

            Intent ittProjDtlsToHome = new Intent(PMGallaryActivity.this ,PMComplitionProjectActivity.class);
      /*      ittProjDtlsToHome.putExtra("ManagerId",MDId);
            ittProjDtlsToHome.putExtra("name",Name);
            ittProjDtlsToHome.putExtra("projectId",PDId);
            ittProjDtlsToHome.putExtra("LeadId",LeadId);
            ittProjDtlsToHome.putExtra("MobileNo",MobileNo);
            ittProjDtlsToHome.putExtra("Student_Image_Path",Student_Image_Path);*/

            ittProjDtlsToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(ittProjDtlsToHome);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
/*        Intent ittProjDtlsToHome = new Intent(PMGallaryActivity.this ,PMComplitionProjectActivity.class);
        ittProjDtlsToHome.putExtra("ManagerId",MDId);
        ittProjDtlsToHome.putExtra("name",Name);
        ittProjDtlsToHome.putExtra("projectId",PDId);
        ittProjDtlsToHome.putExtra("LeadId",LeadId);
        ittProjDtlsToHome.putExtra("MobileNo",MobileNo);
        ittProjDtlsToHome.putExtra("Student_Image_Path",Student_Image_Path);
        startActivity(ittProjDtlsToHome);*/
    }
}
