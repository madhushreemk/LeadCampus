package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by deepshikha on 20/3/17.
 */

public class CompletionGalleryFolderSelectActivity extends AppCompatActivity{ //implements ConnectivityReceiver.ConnectivityReceiverListener{
    int int_position;
    private GridView gridView;
    CompletionGalleryGridViewAdapter adapter;
    ArrayList<String> mTempArry;
    ArrayList<String> mCompressArry;
    byte[] imageInByte;
    ArrayList<byte[]> mImageInByte;
    ArrayList<byte[]> mImageInByteAll;
    public static String imageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion_photo_select);

        Log.d("Insidexxx","photosActivitysss");

        //Toast.makeText(getApplicationContext(),"Inside CompletionGalleryFolderSelectActivity",Toast.LENGTH_LONG).show();


        gridView = (GridView)findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new CompletionGalleryGridViewAdapter(this, CompletionGallerySelectActivity.al_images,int_position);



        //adapter.getCheckedItems();

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.activity_completion_actionbar_layouts);

        View custView = getSupportActionBar().getCustomView();
        Button btn_selectAll = (Button) custView.findViewById(R.id.btn_selectAll);
        btn_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempArry = adapter.getCheckedItems();
                Toast.makeText(getApplicationContext(),"Selected Items: "+mTempArry.size(),Toast.LENGTH_SHORT).show();

                Log.d("ImageUrl gallery", String.valueOf(mTempArry.size()));
                //
            //    Log.d("tag","ImageUrl name ="+ String.valueOf(mTempArry.toString()));


                mCompressArry=new ArrayList<String>();
                mImageInByte=new ArrayList<>();
                mImageInByteAll=new ArrayList<>();

                for(int i=0;i<mTempArry.size();i++){
                    Log.d("FileNameisss:",mTempArry.get(i));
                    imageFilePath = CommonUtils.getFilename();
                    String img_path= CommonUtils.compressImage(mTempArry.get(i));
                    Log.i("tag","img_path=="+img_path);
                    mCompressArry.add(img_path);
                    Log.d("FileName mCompressArry:",mCompressArry.get(i));

                 /*   Bitmap bitmap= BitmapFactory.decodeFile(new File(imageFilePath).getAbsolutePath());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                    imageInByte = stream.toByteArray();
                    mImageInByte.add(imageInByte);*/
                }

             //   mImageInByteAll.addAll(mImageInByte);
               // CompletionProjectStaticClass.setByteArrayImage(mImageInByte);
                Log.d("tag","ImageUrl mImageInByteAll size"+ String.valueOf(mImageInByteAll.size()));
                Log.d("tag","ImageUrl bytearray size"+ String.valueOf(mImageInByte.size()));
                Log.d("tag","ImageUrl bytearray =="+ mImageInByte.toString());
                Log.d("tag","ImageUrl gallery compress"+ String.valueOf(mCompressArry.size()));
                CompletionProjectStaticClass.setFileArrayString(mCompressArry);
             //   CompletionProjectStaticClass.setFileArrayString(mTempArry);

               //Intent photosToCompletion = new Intent(getApplicationContext(),ProjectDetails.class);

               Intent photosToCompletion = new Intent(getApplicationContext(),FinalProjectCompletion_Activity.class);
                //photosToCompletion.putExtra("fromTeam","CompletionGalleryFolderSelectActivity");
                photosToCompletion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                //photosToCompletion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                CompletionProjectStaticClass.setCountCompletion(1);

                startActivity(photosToCompletion);
                //finish();
            }
        });

        gridView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

   /* @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
*/
    private void showSnack(boolean isConnected) {

        if (!isConnected) {

            AlertDialog.Builder adb = new AlertDialog.Builder(CompletionGalleryFolderSelectActivity.this);
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




    /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_editProfile) {
            Intent ittProjDtlsToEditProfile = new Intent(ProjectDetails.this ,EditProfileActivity.class);
            startActivity(ittProjDtlsToEditProfile);
            return true;
        }

        if (id == R.id.action_logout) {

            //deleteCache(ProjectDetails.this);

            Intent ittProjDtlsToLogin = new Intent(ProjectDetails.this ,LoginActivity.class);
            startActivity(ittProjDtlsToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(ProjectDetails.this ,HomeActivity.class);
            startActivity(ittProjDtlsToHome);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }*/
}
