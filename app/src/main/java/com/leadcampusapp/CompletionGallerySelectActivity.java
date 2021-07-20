package com.leadcampusapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class CompletionGallerySelectActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    public static ArrayList<CompletionModelImages> al_images = new ArrayList<>();
    boolean boolean_folder;
    CompletionAdapter_PhotosFolder obj_adapter;
    GridView gv_folder;
    private static final int REQUEST_PERMISSIONS = 100;
    String path;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completion_photo_select);


        context = CompletionGallerySelectActivity.this;




        gv_folder = (GridView)findViewById(R.id.gv_folder);

        gv_folder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("Insidexxx","gv_folderssss");
                Intent intent = new Intent(getApplicationContext(), CompletionGalleryFolderSelectActivity.class);
                intent.putExtra("value",i);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,100);
                //startActivity(intent);
            }
        });


        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(CompletionGallerySelectActivity.this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(CompletionGallerySelectActivity.this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(CompletionGallerySelectActivity.this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            fn_imagespath();
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

            AlertDialog.Builder adb = new AlertDialog.Builder(CompletionGallerySelectActivity.this);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                Log.d("Inside","Resultreturned");
                //finish();
            }
        }
    }












    public ArrayList<CompletionModelImages> fn_imagespath() {
        al_images.clear();

        int int_position = 0;
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name,column_mime_type;


        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;
        cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, orderBy + " DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

        /*Log.e("cursor", String.valueOf(cursor));
        Log.e("cursorindex", String.valueOf(column_index_data));
        Log.e("cursorindexfoldername", String.valueOf(column_index_folder_name));*/



        while (cursor.moveToNext())
        {
            absolutePathOfImage = cursor.getString(column_index_data);
         //   Log.e("Column", absolutePathOfImage);
           // Log.e("Folder", cursor.getString(column_index_folder_name));

            for (int i = 0; i < al_images.size(); i++) {
                // Log.e("al_images", al_images.get(i).getStr_folder());
                //Log.e("cursorname", cursor.getString(column_index_folder_name));
                try{
                if (al_images.get(i).getStr_folder().equals(cursor.getString(column_index_folder_name))) {
                    boolean_folder = true;
                    int_position = i;
                    break;
                } else {
                    boolean_folder = false;
                }

            }catch(Exception ex)
                {
                    String exp= String.valueOf(ex);

                    Log.e("exp",""+exp);
                }
            }


            if (boolean_folder) {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.addAll(al_images.get(int_position).getAl_imagepath());
                al_path.add(absolutePathOfImage);
                al_images.get(int_position).setAl_imagepath(al_path);
            } else {
                ArrayList<String> al_path = new ArrayList<>();
                al_path.add(absolutePathOfImage);
                CompletionModelImages obj_model = new CompletionModelImages();
                obj_model.setStr_folder(cursor.getString(column_index_folder_name));
                obj_model.setAl_imagepath(al_path);

                al_images.add(obj_model);
            }

        }



 /*       ArrayList<String> filenames = new ArrayList<String>();
        path = Environment.getExternalStorageDirectory()
                + File.separator;

        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            String file_name = files[i].getName();

            if(file_name.endsWith(".pdf")) {
                Log.d("file_nameissss", files[i].getAbsolutePath());
            }
            // you can store name to arraylist and use it later
            filenames.add(file_name);
        }*/



        /*ContentResolver cr = context.getContentResolver();
        Uri uriExternal = MediaStore.Files.getContentUri("internal");

        String[] projections = null;
        String sortOrder = null; // unordered

        String selectionMimeType = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");
        String[] selectionArgsPdf = new String[]{ mimeType };
        Cursor allPdfFiles = cr.query(uri, projections, selectionMimeType, selectionArgsPdf, sortOrder);

        column_index_data = allPdfFiles.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = allPdfFiles.getColumnIndexOrThrow(MediaStore..Media.BUCKET_DISPLAY_NAME);

        while (allPdfFiles.moveToNext()){
            Log.d("FileNameis,",allPdfFiles.getString(column_index_data));
            Log.d("FolderNameiss,",allPdfFiles.getString(column_index_folder_name));
        }*/









     /*   for (int i = 0; i < al_images.size(); i++) {
            Log.e("FOLDER", al_images.get(i).getStr_folder());
            for (int j = 0; j < al_images.get(i).getAl_imagepath().size(); j++) {
                Log.e("FILE", al_images.get(i).getAl_imagepath().get(j));
            }
        }*/

        obj_adapter = new CompletionAdapter_PhotosFolder(getApplicationContext(),al_images);
        gv_folder.setAdapter(obj_adapter);
        return al_images;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults.length > 0 && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        fn_imagespath();
                    } else {
                        Toast.makeText(CompletionGallerySelectActivity.this, "The app was not allowed to read or write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}
