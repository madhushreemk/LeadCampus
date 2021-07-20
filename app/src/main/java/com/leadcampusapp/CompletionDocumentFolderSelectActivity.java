package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by deepshikha on 20/3/17.
 */

public class CompletionDocumentFolderSelectActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    int int_position;
    private GridView gridView;
    private CompletionDocumentGridViewAdapter adapter;
    private ArrayList<String> mTempArry;
    private String path;
    private ArrayList<CompletionDocumentModel> completionDocumentModelArrLst;
    private ArrayList<String> filePaths,filenames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion_document_photo_select);

        Log.d("Insidexxx","photosActivitysss");

        //Toast.makeText(getApplicationContext(),"Inside CompletionGalleryFolderSelectActivity",Toast.LENGTH_LONG).show();

        completionDocumentModelArrLst = new ArrayList<CompletionDocumentModel>();
        filePaths = new ArrayList<String>();
        filenames = new ArrayList<String>();


        //ArrayList<String> filenames = new ArrayList<String>();
        path = Environment.getExternalStorageDirectory() + File.separator;

        File directory = new File(path);
        File[] files = directory.listFiles();



        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
        {
            ///mounted
            File dir= android.os.Environment.getExternalStorageDirectory();
            walkdir(dir);
        }



/*        for (int i = 0; i < files.length; i++) {
            String file_name = files[i].getName();

            if(file_name.endsWith(".pdf") || file_name.endsWith(".doc")) {
                Log.d("file_nameissss", files[i].getAbsolutePath());
                filePaths.add(files[i].getAbsolutePath());
                filenames.add(file_name);
            }
            // you can store name to arraylist and use it later

        }*/

        CompletionDocumentModel completionDocumentModel = new CompletionDocumentModel();
        completionDocumentModel.setAl_documentpath(filePaths);
        completionDocumentModel.setAl_documentName(filenames);

        completionDocumentModelArrLst.add(completionDocumentModel);



        gridView = (GridView)findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new CompletionDocumentGridViewAdapter(this, completionDocumentModelArrLst,0);



        //adapter.getCheckedItems();

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.activity_completion_document_actionbar_layouts);

        View custView = getSupportActionBar().getCustomView();
        Button btn_selectAll = (Button) custView.findViewById(R.id.btn_selectAll);
        btn_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTempArry = adapter.getCheckedItems();
                Toast.makeText(getApplicationContext(),"Selected Items: "+mTempArry.size(),Toast.LENGTH_SHORT).show();

                if(mTempArry.size()==1) {

                    for (int i = 0; i < mTempArry.size(); i++) {
                        Log.d("FileNameisss:", mTempArry.get(i));
                    }

                    CompletionProjectStaticClass.setFileDocString(mTempArry);

                 //   Intent documentToCompletion = new Intent(getApplicationContext(), ProjectDetails.class);// for fragment

                    Intent documentToCompletion = new Intent(getApplicationContext(), FinalProjectCompletion_Activity.class);// for Activity
                    //photosToCompletion.putExtra("fromTeam","CompletionGalleryFolderSelectActivity");
                    documentToCompletion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    //photosToCompletion.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    CompletionProjectStaticClass.setCountDocumentCompletion(1);// for fragment
                    startActivity(documentToCompletion);
                }else{
                    Toast.makeText(getApplicationContext(),"You can upload only one document",Toast.LENGTH_SHORT).show();
                }

                //finish();
            }
        });

        gridView.setAdapter(adapter);
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

            AlertDialog.Builder adb = new AlertDialog.Builder(CompletionDocumentFolderSelectActivity.this);
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





    private void walkdir(File dir) {
        File listFile[] = dir.listFiles();

        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {// if its a directory need to get the files under that directory
                    walkdir(listFile[i]);
                } else {// add path of  files to your arraylist for later use

                    //Do what ever u want
                    //filepath.add(listFile[i].getAbsolutePath());
                    String file_name = listFile[i].getName();

                    if(file_name.endsWith(".pdf") || file_name.endsWith(".doc") || file_name.endsWith(".docx") || file_name.endsWith(".xls") || file_name.endsWith(".xlsx")) {
                        Log.d("file_nameissss", listFile[i].getAbsolutePath());
                        filePaths.add(listFile[i].getAbsolutePath());
                        filenames.add(file_name);
                    }

                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
