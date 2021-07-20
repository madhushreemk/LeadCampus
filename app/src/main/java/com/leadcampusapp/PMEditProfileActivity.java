package com.leadcampusapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.leadcampusapp.module.ManagerDetails;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PMEditProfileActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    private AppCompatSpinner spin_state;
    private AppCompatSpinner spin_city;
    private AppCompatSpinner spin_institution;
    private AppCompatSpinner spin_semester;
    private AppCompatSpinner spin_gender;
    private AppCompatSpinner spin_bg;
    private EditText edt_dob;

    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;
    private Context context;

    private ImageView btn_save;
    private ImageView btn_addProfile;

    int responseCount=0;
    int i=0;
    private ProgressBar progressBar;
    ProgressDialog dialog;

    EditText Mname_et,mailid_et,address_et,facebook_et,tw_et,inst_et;
    EditText Mobile_tv;
    RadioButton rdb_male,rdb_female;
    EditText whatsapp_tv;

    ManagerDetails managerDetailsObj;
    ArrayList<ManagerDetails> managerDetails=new ArrayList<>();
    int ManagerId;
    String result_of_submit;
    String status;
    String PMImgUrl;
    ImageView img_profilePick;
    private ImageButton imgBtn_addProfile;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String encodedImageString;

    private String serverPath = Class_URL.ServerPath.toString().trim();

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    public static final String PrefID_pmName = "prefid_pmname"; //
    public static final String PrefID_PMEMailID = "prefid_pmemailid"; //
    public static final String PrefID_PMMobile = "prefid_pmmobile"; //
   // public static final String PrefID_pmlocation = "prefid_pmlocation"; //
    public static final String PrefID_pmimageurl = "prefid_pmimgurl"; //

    SharedPreferences shardprefPM_obj;

    SharedPreferences.Editor editor_PM;
    String str_MangerID,str_MangerName,str_ManagerEmail,str_ManagerMaobile,str_MangerImg;
    Integer MDId;
    byte[] byteArray;
    Bitmap bitmap;
    byte[] imageinbytesArray={0};

    String Image_Path,Mname,mailid,bloodGroup,mobileno,address,gender,InstaGram,Facebook,Twitter,Whatsapp;


    Class_URL  config_obj= new Class_URL();
    public static final String  PREFBook_LoginTrack= "prefbook_logintrack";
    public static final String PrefID_WhereToGo = "prefid_wheretogo"; //
    SharedPreferences shardprefLoginTrack_obj;
    SharedPreferences.Editor editor_LoginTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmedit_profilenew);

        /*getSupportActionBar().setTitle("LeadMIS");*/
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        shardprefLoginTrack_obj =this.getSharedPreferences(PREFBook_LoginTrack,Context.MODE_PRIVATE);
        editor_LoginTrack = shardprefLoginTrack_obj.edit();
        editor_LoginTrack.putString(PrefID_WhereToGo,config_obj.packagename+"PMEditProfileActivity");
        editor_LoginTrack.commit();




        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Edit Profile");
       /* ManagerId = getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);
*/
        context = PMEditProfileActivity.this;
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
      //  dialog= new ProgressDialog(this);
        managerDetailsObj=new ManagerDetails();
        spin_bg = (AppCompatSpinner) findViewById(R.id.spin_bg);
        btn_save = (ImageView) findViewById(R.id.btn_save);
        imgBtn_addProfile = (ImageButton) findViewById(R.id.imgBtn_addProfile);
        Mname_et = (EditText) findViewById(R.id.edt_mentorName);
        mailid_et = (EditText) findViewById(R.id.edt_emailid);
        address_et = (EditText) findViewById(R.id.edt_address);
        Mobile_tv = (EditText) findViewById(R.id.mobile_tv);
        rdb_male = (RadioButton) findViewById(R.id.rdb_male);
        rdb_female = (RadioButton) findViewById(R.id.rdb_female);
        img_profilePick = (ImageView) findViewById(R.id.img_profilePick);

        facebook_et= (EditText) findViewById(R.id.edt_fb);
        tw_et=(EditText) findViewById(R.id.edt_twitter);
        inst_et=(EditText)findViewById(R.id.edt_inst);
        whatsapp_tv=(EditText) findViewById(R.id.whatsapp_tv);


        shardprefPM_obj= getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);

        shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        str_ManagerEmail = shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        Log.d("str_ManagerEmail:",str_ManagerEmail);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();

        shardprefPM_obj.getString(PrefID_pmName, "").trim();
        str_MangerName = shardprefPM_obj.getString(PrefID_pmName, "").trim();
        Log.d("str_MangerName:",str_MangerName);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();

        shardprefPM_obj.getString(PrefID_PMMobile, "").trim();
        str_ManagerMaobile = shardprefPM_obj.getString(PrefID_PMMobile, "").trim();
        Log.d("str_ManagerMaobile:",str_ManagerMaobile);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        shardprefPM_obj.getString(PrefID_pmimageurl, "").trim();
        str_MangerImg = shardprefPM_obj.getString(PrefID_pmimageurl, "").trim();
        Log.d("str_MangerImg:",str_MangerImg);

     /*   shardprefPM_obj.getString(PrefID_pmlocation, "").trim();
        str_ManagerLocation = shardprefPM_obj.getString(PrefID_pmlocation, "").trim();
        Log.d("str_ManagerLocation:",str_ManagerLocation);
*/
    /*    Intent intent = getIntent();
        String MangerID=intent.getStringExtra("ManagerId");
        Log.i("tag","MangerID"+MangerID);
        if(MangerID.equals("null")){
            ManagerId=1;
        }else
        {
            ManagerId = Integer.parseInt(MangerID);

        }
*/
        GetManagerDetails getManagerDetails = new GetManagerDetails(this);
        getManagerDetails.execute(MDId);


        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    UpdateManagerDetails updateManagerDetails = new UpdateManagerDetails(PMEditProfileActivity.this);
                    updateManagerDetails.execute();
                }

            }
        });

        imgBtn_addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

      //  setGenderSpinner();
        setBloodGroupSpinner();
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

            AlertDialog.Builder adb = new AlertDialog.Builder(PMEditProfileActivity.this);
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



    private void selectImage()
    {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    //userChoosenTask ="Take Photo";
                    cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    //userChoosenTask ="Choose from Library";
                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

 /*   private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    //userChoosenTask ="Take Photo";
                    cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    //userChoosenTask ="Choose from Library";
                    galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        //intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }
*/
    public void onRadioButtonGenderClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.rdb_male: if(checked){} break;
            case R.id.rdb_female: if (checked){} break;
        }
    }

    public boolean validation()
    {

        Boolean bfarmername=true,baddress=true,blocation=true,bimageview=true,bnetwork=true,bfees=true,bcluster=true;


        if( Mname_et.getText().toString().length() == 0 ){
            Mname_et.setError( "Manager name is required!" );bfarmername=false;}

        if( address_et.getText().toString().length() == 0 ){
            address_et.setError( "Address is required!" );baddress=false;}

        if( mailid_et.getText().toString().length() == 0 ){
            mailid_et.setError( "Mail ID is required!" );blocation=false;}

        /*if(imageview.getDrawable() == null){
            Toast.makeText(MainActivity.this, "Image is not taken", Toast.LENGTH_LONG).show();bimageview= false;}
*/
        /*if(spin_bg.getSelectedItemPosition()==0){
            TextView errorText = (TextView) spin_bg.getSelectedView();
            errorText.setError("Select Fees");
            bfees=false;
        }
        if(cluster.getSelectedItemPosition() ==0){
            TextView errorText = (TextView) cluster.getSelectedView();
            errorText.setError("Select Cluster");
            bcluster=false;
        }*/


        if(bfarmername&&baddress&&blocation&&bimageview&&bnetwork&&bfees&&bcluster) {
            return true;
        }else{return false;}
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                //onSelectFromGalleryResult(data);

                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //if (android.os.Build.VERSION.SDK_INT > 24) {

                    Uri selectedImage = data.getData();
                    Log.e("uri", selectedImage.toString());

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                     bitmap = BitmapFactory.decodeFile(picturePath);

                  /*  ImageView imgProj = (ImageView) view2.findViewById(projImageId);
                    imgProj.setImageBitmap(bitmap);*/

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byteArray = byteArrayOutputStream .toByteArray();

                    encodedImageString = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    Log.i("tag","encodedImageString1="+encodedImageString);

                    //In marshmallow version camara image rotation code

                    ExifInterface exif= null;
                    try {
                        exif = new ExifInterface(picturePath.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("EXIF value1", exif.getAttribute(ExifInterface.TAG_ORIENTATION));
                    if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")){
                        bitmap= rotate(bitmap, 90);
                    } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")){
                        bitmap= rotate(bitmap, 270);
                    } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")){
                        bitmap= rotate(bitmap, 180);
                    }/* else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")){
                        bitmap= rotate(bitmap, 0);
                    }*/
//-----------------------------------
                    img_profilePick.setImageBitmap(bitmap);

                 /*   ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bitmapPicture.compress(Bitmap.CompressFormat.PNG, 50, stream);
                    // bmp.recycle();
                    byte[] byteArray = stream.toByteArray();
                    img_data = Base64.encodeToString(byteArray, Base64.DEFAULT);*/


                } else {
                    Bundle extras2 = data.getExtras();
                    if (extras2 != null) {
                        Bitmap yourImage = extras2.getParcelable("data");
                        // convert bitmap to byte
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imageInByte;
                        imageInByte = stream.toByteArray();
                        //	Log.e("output before conversion", imageInByte.toString());
                        //	Log.d("Insert: ", "Inserting ..image");
                        bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
                        //	Log.d("Insert image report: ",bm.toString() );

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byteArray = byteArrayOutputStream .toByteArray();

                        encodedImageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        Log.i("tag","encodedImageString2="+encodedImageString);
                        img_profilePick.setImageBitmap(bitmap);
                        // Inserting Contacts

                        //	db.addPhoto(new Photo(lead_id, imageInByte));
                        //	addimage();

                    }
                }


            } else if (requestCode == REQUEST_CAMERA) {
                //onCaptureImageResult(data);
                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageInByte;
                    imageInByte = stream.toByteArray();
                    //	Log.e("output before conversion", imageInByte.toString());
                    // Inserting Contacts
                    bitmap = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                     byteArray = byteArrayOutputStream .toByteArray();

                    encodedImageString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    Log.i("tag","encodedImageString3="+encodedImageString);
                    //report_photo.setImageBitmap(bm);

                    /*Log.d("projectImageidxxxx",String.valueOf(projImageId));

                    ImageView imgProj = (ImageView) view2.findViewById(projImageId);
                    imgProj.setImageBitmap(bm);*/


                /*    Bitmap circleBitmap = Bitmap.createBitmap(yourImage.getWidth(), yourImage.getHeight(), Bitmap.Config.ARGB_8888);

                    BitmapShader shader = new BitmapShader (yourImage,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                    Paint paint = new Paint();
                    paint.setShader(shader);

                    Canvas c = new Canvas(circleBitmap);
                    c.drawCircle(yourImage.getWidth()/2, yourImage.getHeight()/2, yourImage.getWidth()/2, paint);

                    img_profilePick.setImageBitmap(circleBitmap);*/

                    img_profilePick.setImageBitmap(bitmap);
                }
            }
        }
    }

    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        //       mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }


    public class GetManagerDetailsNew extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        private ProgressBar progressBar;

        GetManagerDetailsNew (Context ctx){
            context = ctx;
        }
        Boolean res=false;
        String str_Response;

        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params [0];
            //SoapObject response = getStudDtls(leadid);

            String METHOD_NAME = "GetManagerDetails";
            String NamespaceMail="http://mis.leadcampus.org/", SOAP_ACTION1="http://mis.leadcampus.org/GetManagerDetails";//namespace+methodname
            SoapObject response = null;

            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

                request.addProperty("ManagerId", ManagerId);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
               
                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    response = (SoapObject) envelope.getResponse();
                    Log.d("tag","soap response getresult="+response.toString());

                    str_Response=response.getProperty("Status").toString();
                    Mname=response.getProperty("ManagerName").toString();
                    mailid=response.getProperty("MailId").toString();
                    bloodGroup=response.getProperty("BloodGroup").toString();
                    gender=response.getProperty("Gender").toString();
                    mobileno=response.getProperty("MobileNo").toString();
                    address=response.getProperty("Address").toString();
                    Image_Path=response.getProperty("Image_Path").toString();
                    Facebook = response.getProperty("Facebook").toString();
                    Twitter = response.getProperty("Twitter").toString();
                    InstaGram = response.getProperty("InstaGram").toString();
                    Whatsapp = response.getProperty("WhatsApp").toString();
                    Log.d("Image_Path=",Image_Path);

                    String Imagestring = Image_Path;
                    if(Imagestring.equals("null")||Imagestring.equals("anyType{}")){
                        // PMImgUrl="null";
                        img_profilePick.setImageResource(R.drawable.devanand);
                    }else {
                        String arr[] = Imagestring.split("/", 2);

                        String firstWord = arr[0];   //the
                        String secondWord = arr[1];

                        PMImgUrl = serverPath + secondWord;

                        Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                        LoadGalleryImage loadGalleryImage=new LoadGalleryImage(PMEditProfileActivity.this);
                        loadGalleryImage.execute();
                    }

                    editor_PM = shardprefPM_obj.edit();
                    editor_PM.clear();
                    editor_PM.commit();


                    editor_PM = shardprefPM_obj.edit();
                    editor_PM.putString(PrefID_PMID, String.valueOf(MDId));
                    editor_PM.putString(PrefID_pmName,Mname);
                    editor_PM.putString(PrefID_PMEMailID,mailid);
                    editor_PM.putString(PrefID_PMMobile,mobileno);
                   // editor_PM.putString(Pre,mobileno);
                    editor_PM.putString(PrefID_pmimageurl,PMImgUrl);
                    editor_PM.commit();


                    Log.d("ManagerIds:", String.valueOf(MDId));
                    Log.d("ManagerNames:",Mname);
                    Log.d("ManagerEmailIds:",mailid);
                    Log.d("ManagerMobiles:",mobileno);
                    //  Log.d("Managerlocations:",Str_PMLocation);
                    Log.d("ManagerURLs:",PMImgUrl);
                    Log.d("mailid",mailid);
                    /*if(response.getProperty("Status").toString().equals("Success")){
                        Log.i("tag","Status="+response.getProperty("Status").toString());
                        res=true;
                    }
                    else {
                        res=false;
                    }*/
                    return response;

                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());
                }




                Mname_et.setText(Mname);
                mailid_et.setText(mailid);
                Mobile_tv.setText(mobileno);
                address_et.setText(address);
                if(gender.equals("M")){
                    rdb_male.setChecked(true);
                }
                else{
                    rdb_female.setChecked(true);
                }
                if(bloodGroup.equals("A+")){
                    spin_bg.setSelection(1);
                }else if(bloodGroup.equals("B+")){
                    spin_bg.setSelection(2);
                }else if(bloodGroup.equals("A-")){
                    spin_bg.setSelection(3);
                }else if(bloodGroup.equals("B-")){
                    spin_bg.setSelection(4);
                }else if(bloodGroup.equals("AB+")){
                    spin_bg.setSelection(5);
                }else if(bloodGroup.equals("AB-")){
                    spin_bg.setSelection(6);
                }else if(bloodGroup.equals("O+")){
                    spin_bg.setSelection(7);
                }else if(bloodGroup.equals("O-")){
                    spin_bg.setSelection(8);
                }else if(bloodGroup.equals("Select")||bloodGroup.equals("null")||bloodGroup.equals("anyType{}")){
                    spin_bg.setSelection(0);
                }



            }catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
            }

            //Log.d("Soap response is",response.toString());
          //  SoapObject response = (SoapObject) .getResponse();

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            progressBar.setVisibility(View.GONE);

        //    Log.d("GetManagerDetailsNewre",result.toString());

           // if(result.getProperty("Status").toString().equals("Success")) {
            //if(res==true){
            if(result!=null) {
                if (str_Response.equals("Success")) {
                    Intent ittEditToHome = new Intent(PMEditProfileActivity.this, PMHomeActivity.class);
                    startActivity(ittEditToHome);
                }
            }

            //     String finalResult = result.toString();
            //   Log.d("finalResultssssss",finalResult);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    public class GetManagerDetails extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        private ProgressBar progressBar;

        GetManagerDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params [0];
            //SoapObject response = getStudDtls(leadid);

            String METHOD_NAME = "GetManagerDetails";
            String NamespaceMail="http://mis.leadcampus.org/", SOAP_ACTION1="http://mis.leadcampus.org/GetManagerDetails";//namespace+methodname

            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

                request.addProperty("ManagerId", ManagerId);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("soap responseyyyyyyy",response.toString());

                    return response;

                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());
                }
            }catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
            }

            //Log.d("Soap response is",response.toString());

            return null;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            progressBar.setVisibility(View.GONE);

            if(result!=null) {
                String finalResult = result.toString();
                Log.d("finalResultssssss", finalResult);
                Mname = result.getProperty("ManagerName").toString();
                mailid = result.getProperty("MailId").toString();
                bloodGroup = result.getProperty("BloodGroup").toString();
                gender = result.getProperty("Gender").toString();
                mobileno = result.getProperty("MobileNo").toString();
                address = result.getProperty("Address").toString();
                Image_Path = result.getProperty("Image_Path").toString();
                Facebook = result.getProperty("Facebook").toString();
                Twitter = result.getProperty("Twitter").toString();
                InstaGram = result.getProperty("InstaGram").toString();
                Whatsapp = result.getProperty("WhatsApp").toString();
                Log.d("Image_Path=", Image_Path);

                String Imagestring = Image_Path;
                if (Imagestring.equals("null") || Imagestring.equals("anyType{}")) {
                    // PMImgUrl="null";
                    img_profilePick.setImageResource(R.drawable.devanand);
                } else {
                    String arr[] = Imagestring.split("/", 2);

                    String firstWord = arr[0];   //the
                    String secondWord = arr[1];

                    PMImgUrl = serverPath + secondWord;

                    Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                    LoadGalleryImage loadGalleryImage = new LoadGalleryImage(PMEditProfileActivity.this);
                    loadGalleryImage.execute();
                }




          /*  editor_PM.putString(PrefID_PMID, String.valueOf(MDId));
            editor_PM.putString(PrefID_pmName,Mname);
            editor_PM.putString(PrefID_PMEMailID,mailid);
            editor_PM.putString(PrefID_PMMobile,mobileno);
            editor_PM.putString(PrefID_pmimageurl,PMImgUrl);
            editor_PM.commit();//();*/

                if (Facebook.equals("null") || Facebook.equals("anyType{}")) {
                    facebook_et.setText("");
                } else {
                    facebook_et.setText(Facebook);
                }
                if (Twitter.equals("null") || Twitter.equals("anyType{}")) {
                    tw_et.setText("");
                } else {
                    tw_et.setText(Twitter);
                }
                if (InstaGram.equals("null") || InstaGram.equals("anyType{}")) {
                    inst_et.setText("");
                } else {
                    inst_et.setText(InstaGram);
                }
                if (address.equals("null") || address.equals("anyType{}")) {
                    address_et.setText("");
                } else {
                    address_et.setText(address);
                }
                if (Whatsapp.equals("null") || Whatsapp.equals("anyType{}")) {
                    whatsapp_tv.setText("");
                } else {
                    whatsapp_tv.setText(Whatsapp);
                }
                Mname_et.setText(Mname);
                mailid_et.setText(mailid);
                Mobile_tv.setText(mobileno);

                if (gender.equals("M")) {
                    rdb_male.setChecked(true);
                } else {
                    rdb_female.setChecked(true);
                }


                if (bloodGroup.equals("A+")) {
                    spin_bg.setSelection(1);
                } else if (bloodGroup.equals("B+")) {
                    spin_bg.setSelection(2);
                } else if (bloodGroup.equals("A-")) {
                    spin_bg.setSelection(3);
                } else if (bloodGroup.equals("B-")) {
                    spin_bg.setSelection(4);
                } else if (bloodGroup.equals("AB+")) {
                    spin_bg.setSelection(5);
                } else if (bloodGroup.equals("AB-")) {
                    spin_bg.setSelection(6);
                } else if (bloodGroup.equals("O+")) {
                    spin_bg.setSelection(7);
                } else if (bloodGroup.equals("O-")) {
                    spin_bg.setSelection(8);
                } else if (bloodGroup.equals("Bombay Blood")) {
                    spin_bg.setSelection(9);
                } else if (bloodGroup.equals("Select") || bloodGroup.equals("null") || bloodGroup.equals("anyType{}")) {
                    spin_bg.setSelection(0);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class LoadGalleryImage extends AsyncTask<Void, Object, Bitmap> {

        private Context context;
        //   private ProgressBar progressBar;

        LoadGalleryImage (Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            //  progressBar = (ProgressBar) findViewById(R.id.progressBar);
            //  progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            ArrayList<Bitmap> bitmapLst=null;
            Bitmap bitmaplogo=null;

            try {
                URL url= new URL(PMImgUrl);
                Log.d("Urlssssssssssss",url.toString());
                bitmaplogo = BitmapFactory.decodeStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmaplogo;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // bitmapList.add(bitmap);
            img_profilePick.setImageBitmap(bitmap);
            //  progressBar.setVisibility(GONE);
        }
    }

    private class UpdateManagerDetails extends AsyncTask<String, Void, Void>
    {
       // ProgressDialog dialog;

        Context context;

      //  public UpdateManagerDetails(GetManagerDetails getManagerDetails) {
      //  }

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

        public UpdateManagerDetails(PMEditProfileActivity activity) {
            context = activity;
            dialog = new ProgressDialog(activity);
        }
        @Override
        protected Void doInBackground(String... params)
        {
            Log.i("TAG", "doInBackground");
            //submit_PMProfile(ManagerId);
            Submit();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i("TAG", "onPostExecute");

            Log.d("ServiceResponseisss: ",status.toString());

            dialog.dismiss();
        /*    if(status.equals("Error")){
                Toast.makeText(PMEditProfileActivity.this, "Update Request Failed " ,
                        		Toast.LENGTH_LONG).show();
            }*/
            if(status.equals("Success")) {
                Toast.makeText(PMEditProfileActivity.this, "Profile Updated Successfully " ,
                        Toast.LENGTH_LONG).show();
                Log.i("MDId","MDId="+MDId);
                GetManagerDetailsNew getManagerDetails1 = new GetManagerDetailsNew(PMEditProfileActivity.this);
                getManagerDetails1.execute(MDId);

             /*   editor_PM=shardprefPM_obj.edit();
                editor_PM.putString(PrefID_PMID, String.valueOf(MDId));
                editor_PM.putString(PrefID_pmName,Mname);
                editor_PM.putString(PrefID_PMEMailID,mailid);
                editor_PM.putString(PrefID_PMMobile,mobileno);
                editor_PM.putString(PrefID_pmimageurl,PMImgUrl);
                Log.d("ManagerIds:", String.valueOf(MDId));
                Log.d("ManagerNames:",Mname);
                Log.d("ManagerEmailIds:",mailid);
                Log.d("ManagerMobiles:",mobileno);
                //  Log.d("Managerlocations:",Str_PMLocation);
                Log.d("ManagerURLs:",PMImgUrl);
                Log.d("mailid",mailid);
                editor_PM.commit();*/

               /* ittEditToHome.putExtra("ManagerId",MDId);
                ittEditToHome.putExtra("ManagerName",Mname_et.getText().toString());
                ittEditToHome.putExtra("PMEmailId",mailid_et.getText().toString());
                ittEditToHome.putExtra("PM_Mobile",Mobile_tv.getText().toString());
                ittEditToHome.putExtra("PMLocation",address_et.getText().toString());
                ittEditToHome.putExtra("PMImgUrl",str_MangerImg);
                startActivity(ittEditToHome);*/

            }
            else{
                Toast.makeText(PMEditProfileActivity.this, "Update Request Failed " ,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    @SuppressLint("RestrictedApi")
    private void setGenderSpinner() {
        final ArrayList listgender = new ArrayList();
        listgender.add("Male");
        listgender.add("Female");

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        ArrayAdapter dataAdapterGender = new ArrayAdapter(context, R.layout.simple_spinner_items, listgender);

        // Drop down layout style - list view with radio button
        dataAdapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_gender.setAdapter(dataAdapterGender);
        spin_gender.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
    }

    @SuppressLint("RestrictedApi")
    private void setBloodGroupSpinner() {
        final ArrayList listBg = new ArrayList();
        listBg.add("Select");
        listBg.add("A+");
        listBg.add("B+");
        listBg.add("A-");
        listBg.add("B-");
        listBg.add("AB+");
        listBg.add("AB-");
        listBg.add("O+");
        listBg.add("O-");
        listBg.add("Bombay Blood");

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        ArrayAdapter dataAdapterBg = new ArrayAdapter(context, R.layout.simple_spinner_items, listBg);

        // Drop down layout style - list view with radio button
        dataAdapterBg.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        spin_bg.setAdapter(dataAdapterBg);
        //spin_bg.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
    }


    public void submit_PMProfile(Integer managerId)
    {

//	String	engage_class1= status.getTag().toString();
        //	Vector<SoapObject> result1 = null;
        String URL = Class_URL.URL_Manager.toString().trim();//"Login.asmx?WSDL";
        // String METHOD_NAME = "intCount";//"NewAppReleseDetails";
        // String Namespace="http://www.example.com", SOAPACTION="http://www.example.com/intCount";
        // String URL = "http://192.168.1.196:8080/deterp_ws/server4.php?wsdl";//"Login.asmx?WSDL";
        String METHOD_NAME = "UpdateManagerDetails";//"NewAppReleseDetails";
        String Namespace="http://mis.leadcampus.org/", SOAPACTION="http://mis.leadcampus.org/UpdateManagerDetails";
        try{
            String Gender = null;
            String MnameNew=Mname_et.getText().toString();
            String EmailIdNew= mailid_et.getText().toString();
            String AddressNew=address_et.getText().toString();
            String MobileNo=Mobile_tv.getText().toString();
            if(rdb_female.isChecked()){
                 Gender="F";
            }else if(rdb_male.isChecked()){
                 Gender="M";
            }
            
           

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("ManagerId",MDId);
            request.addProperty("ManagerName", MnameNew);
            request.addProperty("MailId", EmailIdNew);
            request.addProperty("Address", AddressNew);
            request.addProperty("MobileNo", MobileNo);
            request.addProperty("Gender", Gender);
            request.addProperty("BloodGroup", AddressNew);

            //	request.addProperty("to", 9);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try
            {

                androidHttpTransport.call(SOAPACTION, envelope);
                //	Log.i(TAG, "GetAllLoginDetails is running");
                //	result1 = (Vector<SoapObject>) envelope.getResponse();
                SoapObject response = (SoapObject) envelope.getResponse();
               // result_of_submit=response.getProperty(0).toString();
                Log.i("Tag","string value at response >"+response.toString());

            }
            catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //		Toast.LENGTH_LONG).show();
                Log.e("request fail", "> " + t.getMessage());

            }
        }catch (Throwable t) {
            Log.e("Tag","UnRegister Receiver Error >" + t.getMessage());

        }

    }

    public void Submit() {

        String URL = Class_URL.URL_Manager.toString().trim();
        String METHOD_NAME = "UpdateManagerDetailswithstring";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/UpdateManagerDetailswithstring";

        //for final
        byte[] imageinbytesArray1;
        imageinbytesArray1=imageinbytesArray;

            try {

                String Gender = null;
                String MnameNew = Mname_et.getText().toString();
                String EmailIdNew = mailid_et.getText().toString();
                String AddressNew = address_et.getText().toString();
                String MobileNo = Mobile_tv.getText().toString();
                String Facebook = facebook_et.getText().toString();
                String Twitter = tw_et.getText().toString();
                String Inst = inst_et.getText().toString();
                String Whatsapp=whatsapp_tv.getText().toString();

                if (rdb_female.isChecked()) {
                    Gender = "F";
                } else if (rdb_male.isChecked()) {
                    Gender = "M";
                }
                String BloodGroop = spin_bg.getSelectedItem().toString();

                String encodestr="data:image/jpg;base64/"+encodedImageString;

                SoapObject request = new SoapObject(Namespace, METHOD_NAME);
                // request.addProperty("distid", "0");//short
                request.addProperty("ManagerId", MDId);
                request.addProperty("ManagerName", MnameNew);
                request.addProperty("MailId", EmailIdNew);
                request.addProperty("Address", AddressNew);
                request.addProperty("MobileNo", MobileNo);
                request.addProperty("Gender", Gender);
                request.addProperty("BloodGroup", BloodGroop);
                request.addProperty("ProfileImage",encodedImageString);
                request.addProperty("Facebook",Facebook);
                request.addProperty("Twitter",Twitter);
                request.addProperty("InstaGram",Inst);
                request.addProperty("WhatsApp",Whatsapp);

                Log.e("tag","imageinbytesArray=="+imageinbytesArray);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

                try {

                    androidHttpTransport.call(SOAPACTION, envelope);
                    //  Log.i(TAG, "GetAllLoginDetails is running");
                    //    result1 = (Vector<SoapObject>) envelope.getResponse();

                    ///SoapObject response = (SoapObject) envelope.getResponse();
                    SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                    //Log.e("tag","soap response Updateresponse"+ response.toString());

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
        }

    @Override
    public void onBackPressed()
    {
        // Do Here what ever you want do on back press;

        AlertDialog.Builder dialog = new AlertDialog.Builder(PMEditProfileActivity.this);
        dialog.setCancelable(false);
        dialog.setTitle("LEADCampus");
        dialog.setMessage("Are you sure want to Exit from Edit Profile" );

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                Intent i = new Intent(PMEditProfileActivity.this,PMHomeActivity.class);
                startActivity(i);
                finish();
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                        dialog.dismiss();
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.setOnShowListener( new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();




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

        /*  if (id == R.id.action_settings) {
            return true;
        }*/

        if (id == R.id.action_logout) {

            editor_LoginTrack = shardprefLoginTrack_obj.edit();
            editor_LoginTrack.putString(PrefID_WhereToGo, config_obj.packagename+"LoginActivity");
            editor_LoginTrack.commit();
            Intent itthomeToLogin = new Intent(PMEditProfileActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        if (id == android.R.id.home)
        {


            AlertDialog.Builder dialog = new AlertDialog.Builder(PMEditProfileActivity.this);
            dialog.setCancelable(false);
            dialog.setTitle("LEADCampus");
            dialog.setMessage("Are you sure want to Exit from Edit Profile" );

            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id)
                {
                    Intent i = new Intent(PMEditProfileActivity.this,PMHomeActivity.class);
                    startActivity(i);
                    finish();
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Action for "Cancel".
                            dialog.dismiss();

                        }
                    });

            final AlertDialog alert = dialog.create();
            alert.setOnShowListener( new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                }
            });
            alert.show();


            /*Intent ittProjDtlsToHome = new Intent(PMEditProfileActivity.this ,PMHomeActivity.class);
            //  ittProjDtlsToHome.putExtra("ManagerId",MDId);
            startActivity(ittProjDtlsToHome);*/
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


}
