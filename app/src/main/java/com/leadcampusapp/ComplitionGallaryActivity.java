package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.net.URL;
import java.util.ArrayList;



public class ComplitionGallaryActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    ImageView selectedImage;
    ArrayList<Bitmap> bitmapList;
    ArrayList<URL> TempUrlList;
    ArrayList<String> doc_slno;
    ArrayList<String> doc_path;
    String LeadId,PDId;
    String Student_Image_Path,MobileNo;
    String Name;
    Integer ManagerId;

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    SharedPreferences shardprefPM_obj;
    String str_MangerID;
    Integer MDId;
    String str_projectid,str_projectstatus,str_impactproject_status;
    String status;
    int position_value;

    SharedPreferences shardpref_S_obj;
    SharedPreferences.Editor editor_S;
    public static final String PrefID_SGalleryIntent = "prefid_sgalleryintent"; //
    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complitiongallary);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
     //   getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Gallery");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        shardpref_S_obj=getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);

        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        ImageView img_remove = (ImageView) findViewById(R.id.img_delete_gallery);

        TempUrlList = GalleryImageBitmap.getAllImgURL();
        bitmapList = GalleryImageBitmap.getBitmapGalleryImage();
        doc_path = GalleryImageBitmap.getDocument_path();
        doc_slno = GalleryImageBitmap.getDocument_slno();

        selectedImage=(ImageView)findViewById(R.id.imageView);
        Log.e("tag","gallery bitmapList=="+bitmapList);

        if(bitmapList.size()!=0){
          //  GalleryImageBitmap.setBitmapGalleryImage(bitmapList);
            selectedImage.setImageBitmap(bitmapList.get(0));
            img_remove.setVisibility(View.VISIBLE);
        }else{
            selectedImage.setImageResource(R.drawable.no_img_available);
            img_remove.setVisibility(View.INVISIBLE);
        }


     /*   Intent intent = getIntent();
        if(intent!=null) {
            str_projectid = intent.getExtras().getString("projectid_ValueKey");
            str_projectstatus= intent.getExtras().getString("projectstatus_ValueKey");
            str_impactproject_status=intent.getExtras().getString("impact_projectstatus_ValueKey");
        }*/
    /*    ManagerId = getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);*/
        /*shardprefPM_obj=this.getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);

        Log.e("TAG","LeadId="+LeadId);
        Log.e("TAG","PDId="+PDId);*/

        gallery.setSpacing(5);
        gallery.setAnimationDuration(2000);
        final GalleryImageAdapter galleryImageAdapter= new GalleryImageAdapter(this,bitmapList);

        gallery.setAdapter(galleryImageAdapter);


        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // show the selected Image
                //selectedImage.setImageResource(galleryImageAdapter.mImageIds[position]);
                selectedImage.setImageBitmap(galleryImageAdapter.mBitmapList.get(position));
                position_value=position;
            }
        });


//-----------------------------------Delete image in sarver added by madhu------------------------------------------------------
        img_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.e("tag","position_value=="+position_value);
                Log.e("tag","position_value doc_path=="+doc_path.get(position_value));
                for(int i=0;i<doc_path.size();i++){
                    Log.e("tag","doc_path=="+doc_path.get(i));
                }
                insert_AllImageList();
                insert_positionImage();
                DeleteServerImage_byposition(bitmapList);
              //  Async_DeleteImage async_deleteImage= new Async_DeleteImage(ComplitionGallaryActivity.this);
               // async_deleteImage.execute();
                Intent i = new Intent(ComplitionGallaryActivity.this, ComplitionGallaryActivity.class);
                startActivity(i);

            }
        });

    }

    public void insert_AllImageList()
    {
        SQLiteDatabase db1 = openOrCreateDatabase("Leaddb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS AllImageList(img BLOB NOT NULL,documentlist_path VARCHAR,documentlist_slno VARCHAR,position VARCHAR);");


     //   for(int k=0;k<doc_path.size();k++) {
            Bitmap bitmap_img=bitmapList.get(position_value);
            String documentlist_path_str=doc_path.get(position_value);
            String documentlist_slno_str=doc_slno.get(position_value);
            String SQLiteQuery = "INSERT INTO AllImageList (img,documentlist_path,documentlist_slno,position)" +
                    " VALUES ('" + bitmap_img +"','" +documentlist_path_str+"','"+documentlist_slno_str+"','"+position_value+"');";
            db1.execSQL(SQLiteQuery);
      //  }


        Cursor cursor1 = db1.rawQuery("SELECT * FROM AllImageList", null);
        int x = cursor1.getCount();
        Log.e("tag","x=="+x);
        Log.e("tag","getdoc_slno=="+cursor1.getColumnIndex("documentlist_slno_str"));
    }
    public void insert_positionImage()
    {
        SQLiteDatabase db1 = openOrCreateDatabase("Leaddb", Context.MODE_PRIVATE, null);
        db1.execSQL("CREATE TABLE IF NOT EXISTS PositionImageList(img BLOB NOT NULL,documentlist_path VARCHAR,documentlist_slno VARCHAR,position VARCHAR);");


        //   for(int k=0;k<doc_path.size();k++) {
        Bitmap bitmap_img=bitmapList.get(position_value);
        String documentlist_path_str=doc_path.get(position_value);
        String documentlist_slno_str=doc_slno.get(position_value);
        Log.e("tag","documentlist_slno_str="+documentlist_slno_str);
        String SQLiteQuery = "INSERT INTO PositionImageList (img,documentlist_path,documentlist_slno,position)" +
                " VALUES ('" + bitmap_img +"','" +documentlist_path_str+"','"+documentlist_slno_str+"','"+position_value+"');";
        db1.execSQL(SQLiteQuery);
        //  }


        Cursor cursor1 = db1.rawQuery("SELECT * FROM PositionImageList", null);
        int x = cursor1.getCount();
        Log.e("tag","x=="+x);
        Log.e("tag","getdoc_slno=="+cursor1.getColumnIndex("documentlist_slno_str"));
    }
    public void DeleteServerImage_byposition(ArrayList<Bitmap> bitmapList)
    {

        SQLiteDatabase db6 = openOrCreateDatabase("Leaddb", Context.MODE_PRIVATE, null);

        db6.execSQL("CREATE TABLE IF NOT EXISTS PositionImageList(img BLOB NOT NULL,documentlist_path VARCHAR,documentlist_slno VARCHAR,position VARCHAR);");
        Cursor cursor1 = db6.rawQuery("SELECT * FROM PositionImageList", null);

        int x = cursor1.getCount();
        Log.d("cursor count==", Integer.toString(x));

        int i=0;

        if(x>0) {
            if (cursor1.moveToFirst()) {
                do {

                    i = Integer.parseInt(cursor1.getString(cursor1.getColumnIndex("position")));
                    bitmapList.remove(i);
                    TempUrlList.remove(i);
                    doc_slno.remove(i);
                    doc_path.remove(i);
                    Delete_PositionImg_B4Insertion();
                } while (cursor1.moveToNext());
            }//if ends

        }

        db6.close();

    }
    public void Delete_PositionImg_B4Insertion() {

        SQLiteDatabase db6 = openOrCreateDatabase("Leaddb", Context.MODE_PRIVATE, null);

        db6.execSQL("CREATE TABLE IF NOT EXISTS PositionImageList(img BLOB NOT NULL,documentlist_path VARCHAR,documentlist_slno VARCHAR,position VARCHAR);");
        Cursor cursor1 = db6.rawQuery("SELECT * FROM PositionImageList", null);
        int x = cursor1.getCount();
        if (x > 0) {
            db6.delete("PositionImageList", null, null);
        }
        db6.close();
    }

//------------------------------------------------------------------------------------------------------------------------
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

            AlertDialog.Builder adb = new AlertDialog.Builder(ComplitionGallaryActivity.this);
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


        if (id == R.id.action_logout) {
            Intent itthomeToLogin = new Intent(ComplitionGallaryActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == android.R.id.home) {

          /*  GalleryImageBitmap.setAllImgURL(TempUrlList);
            editor_S = shardpref_S_obj.edit();
            editor_S.putString(PrefID_SGalleryIntent, "1");
            editor_S.commit();*/

            GalleryImageBitmap.setAllImgURL(TempUrlList);               //added by madhu
            FinalProjectCompletion_Activity.LoadGalleryImage loadGalleryImg = new FinalProjectCompletion_Activity.LoadGalleryImage(getApplicationContext());
            loadGalleryImg.execute();
            Intent ittProjDtlsToHome = new Intent(ComplitionGallaryActivity.this ,FinalProjectCompletion_Activity.class);

            ittProjDtlsToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            startActivity(ittProjDtlsToHome);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed()
    {
        GalleryImageBitmap.setAllImgURL(TempUrlList);       //added by madhu
        FinalProjectCompletion_Activity.LoadGalleryImage loadGalleryImg = new FinalProjectCompletion_Activity.LoadGalleryImage(getApplicationContext());
        loadGalleryImg.execute();
        Intent ittProjDtlsToHome = new Intent(ComplitionGallaryActivity.this ,FinalProjectCompletion_Activity.class);
        ittProjDtlsToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(ittProjDtlsToHome);
     //   super.onBackPressed();
/*        Intent ittProjDtlsToHome = new Intent(PMGallaryActivity.this ,PMComplitionProjectActivity.class);
        ittProjDtlsToHome.putExtra("ManagerId",MDId);
        ittProjDtlsToHome.putExtra("name",Name);
        ittProjDtlsToHome.putExtra("projectId",PDId);
        ittProjDtlsToHome.putExtra("LeadId",LeadId);
        ittProjDtlsToHome.putExtra("MobileNo",MobileNo);
        ittProjDtlsToHome.putExtra("Student_Image_Path",Student_Image_Path);
        startActivity(ittProjDtlsToHome);*/
    }

 /*   private class Async_DeleteImage extends AsyncTask<String, Void, Void>
    {
        ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute()
        {
            Log.i("TAG", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            Log.i("TAG", "onProgressUpdate---tab2");
        }

        public Async_DeleteImage(ComplitionGallaryActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //submit_PMProfile(ManagerId);
            DeleteImage();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            dialog.dismiss();

            if(status.equalsIgnoreCase("Image Deleted")) {
                Toast.makeText(ComplitionGallaryActivity.this, "Image Deleted Successfully " ,
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent(ComplitionGallaryActivity.this, FinalProjectCompletion_Activity.class);
                i.putExtra("projectid_ValueKey", str_projectid);
                i.putExtra("projectstatus_ValueKey", str_projectstatus);
                i.putExtra("impact_projectstatus_ValueKey", str_impactproject_status);
                startActivity(i);

            }
            else{
                Toast.makeText(ComplitionGallaryActivity.this, status ,
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void DeleteImage() {

        String URL = Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "Delete_ProjectImg";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/Delete_ProjectImg";

        try {


            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            request.addProperty("Img_Slno", doc_slno.get(position_value));
            request.addProperty("img_Path", doc_path.get(position_value));

            Log.e("tag","soap request delete image="+ request.toString());
            Log.e("tag","soap request position_value="+ position_value);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("tag","soap response delete image"+ response.toString());

                status = response.toString();

            } catch (Throwable t) {
                //Toast.makeText(context, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());

            }
        } catch (Throwable t) {
            //Toast.makeText(context, "UnRegister Receiver Error " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Log.e("UnRegister Error", "> " + t.getMessage());
        }
    }*/


}
