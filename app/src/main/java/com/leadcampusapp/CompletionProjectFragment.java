package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatSpinner;
//import androidx.appcompat.widget.DefaultItemAnimator;
//import androidx.appcompat.widget.GridLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferListener;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static com.leadcampusapp.CompletionGalleryFolderSelectActivity.imageFilePath;

public class CompletionProjectFragment extends Fragment {
    private Context context;
    private AppCompatSpinner spin_completedProject;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 2, SELECT_FILE = 1;
    private View view4;



    private Button btn_submitCompletion;
    private HashMap<String,Integer> mapProjectIdProject;

    private LinearLayout lnrlyt_ProjectName;
    private LinearLayout lnrlyt_projectType;
    private LinearLayout lnrlyt_projObjective;
    private LinearLayout lnrlyt_placeOfimpl;
    private LinearLayout lnrlyt_leadFunded;
    private LinearLayout lnrlyt_challanges;
    //private LinearLayout lnrlyt_uploadImage;
    private Button lnrlyt_submit;

    private LinearLayout lnrlyt_txtUploadProj;
    private LinearLayout lnrlyt_uploadDocument;

    private String selectedProject;

    private TextView txt_projectName;
    private TextView txt_projectType;
    private TextView txt_beneficiaries;
    private TextView txt_objective;
    private TextView txt_leadFunded;
    private TextView txt_approvedAmt;

    private EditText edt_fundRaised;
    private EditText edt_challanges;
    private EditText edt_learning;
    private EditText edt_story;
    private EditText edt_resourcesUtilised;
    //private EditText edt_resourcesUtilisedAmt;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    private SharedPreferences shardpref_S_obj;
    private String str_leadId,str_RegistrationId;

   // public static String imageFilePath;

    static final String FTP_HOST= "54.169.178.228";

    /*********  FTP USERNAME ***********/
    static final String FTP_USER = "administrator";

    /*********  FTP PASSWORD ***********/
    static final String FTP_PASS  ="y7Kvso7;ZFz";

    private ArrayList<String> arrlstUploadedImageBitmap = null;

    private ArrayList<byte[]> arrlstUploadImageByteArray = null;

    private ProgressDialog progressDialog;
    private ImageView btn_uploadReport;
    private static final int PICKFILE_RESULT_CODE = 45;
    private byte[] docbyteArray = null;
    private String extensions = null;
    private ImageButton btn_additional;
    private Button btn_removeProjImag;
    private RelativeLayout rl_relativelayout;
    private TextView txt_placeOfImpl;


    private File actualImage,compressedImage;

    private TextView fileSelected;

    private RelativeLayout rowLinearLayout;

    private File img;
    private static Uri fileUri;
    File imageFile;
    String path;
    ImageView imgProj;

    private ImageAdapter imageAdapter;
    private ArrayList<String> imageUrls;
    private ArrayList<String> imgurlss;
    private HashMap<String,Bitmap> cameraImgMap;
    private ProgressDialog pd;
    private List<String> imagesEncodedList;
    private List<byte[]> imagesbyteEncodedList;
    private ArrayList<byte[]> imagesbyteEncodedListAll;
    private String imageEncoded;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        context = getActivity().getBaseContext();
        final View view = inflater.inflate(R.layout.completionproject_fragment, container, false);
        view4 = view;


        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.d("str_leadId:",str_leadId);
        //Toast.makeText(context,"LeadId: "+str_leadId,Toast.LENGTH_LONG).show();

        shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_RegistrationId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        Log.d("str_RegistrationId:",str_RegistrationId);



        initializeViews();

        rl_relativelayout = (RelativeLayout) view4.findViewById(R.id.relativelayout_RL);
        rl_relativelayout.setVisibility(View.INVISIBLE);

        LoadApprovedProject loadApprovedProject = new LoadApprovedProject(getActivity());
        loadApprovedProject.execute();


        btn_submitCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,"You clicked on submit please wait",Toast.LENGTH_SHORT).show();

           /*     pd = new ProgressDialog(context);
                pd.setMessage("Submitting please wait");
                pd.show();*/


                if (checkMandatory())
                {
                    btn_submitCompletion.setVisibility(View.GONE);
                    /*    imgurlss.clear();
                          imgurlss.addAll(imageAdapter.getImageList());*/

                    //Log.d("imgurlsssizeisss", String.valueOf(imageAdapter.getImageList().size()));

                  /*  for(String imgUrl : imgurlss)
                    {
                        Log.d("imgUrlissssss",imgUrl);
                        Bitmap bitmap;
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();

                        if(imgUrl.startsWith("content")){
                            bitmap = cameraImgMap.get(imgUrl);
                        }else {
                            bitmap = BitmapFactory.decodeFile(imgUrl, bmOptions);
                        }

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] imageInByte;
                        imageInByte = stream.toByteArray();
*/
                        //String encodedImageString = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                        //Log.d("encodeImageStringssssss",encodedImageString);

                      /*  if(!imgUrl.startsWith("content")) {
                            String where = MediaStore.MediaColumns.DATA + "=?";
                            String[] selectionArgs = new String[]{imgUrl};
                            ContentResolver contentResolver = context.getContentResolver();
                            Uri filesUri = MediaStore.Files.getContentUri("external");

                            int deleted = contentResolver.delete(filesUri, where, selectionArgs);
                            Log.d("deltedCountcontentissss", String.valueOf(deleted));
                        }else{
                            ContentResolver contentResolver = context.getContentResolver();
                            Uri filesUri = Uri.parse(imgUrl);
                            int deleted = contentResolver.delete(filesUri, null, null);
                            Log.d("deletedCountCameraissss", String.valueOf(deleted));
                        }*/

                        //arrlstUploadedImageBitmap.add(encodedImageString);

                       /* arrlstUploadImageByteArray.add(imageInByte);
                    }*/

                    //bitmap = Bitmap.createScaledBitmap(bitmap,parent.getWidth(),parent.getHeight(),true);
                    //imageView.setImageBitmap(bitmap);

                  //  ArrayList<byte[]> byteArrayImage = CompletionProjectStaticClass.getByteArrayImage();
                    byte[] byteImage = CompletionProjectStaticClass.getBytesImage();
                    ArrayList<byte[]> byteArrayImage;
                    byteArrayImage = new ArrayList<>();

                    byteArrayImage.addAll(imagesbyteEncodedListAll);

                 /*   if(byteArrayImage!=null){
                        for(int i=0;i<byteArrayImage.size();i++) {
                            arrlstUploadImageByteArray.add(byteArrayImage.get(i));
                        }
                    }*/
                    if(byteImage != null){
                        byteArrayImage.add(byteImage);
                    }
                    arrlstUploadImageByteArray.addAll(byteArrayImage);
                    Log.e("tag","arrlst imagesbyteEncodedListAll="+imagesbyteEncodedListAll.size());
                    Log.e("tag","arrlstUploadImageByteArray="+arrlstUploadImageByteArray.size());
                    Log.e("tag","arrlstUploadImageByteArray.toString="+arrlstUploadImageByteArray.toString());
                    if(imgurlss.size() > 10){
                        btn_submitCompletion.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(),"You can upload maximum of 10 images",Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (imgurlss.size() >= 4) {
                            btn_submitCompletion.setVisibility(View.VISIBLE);
                            Log.d("insidesizessssss", "greater than 4");
                            //Toast.makeText(getActivity(),"Inside size greater than 4",Toast.LENGTH_SHORT).show();
                            //pd.dismiss();
                            UploadProjectImages uploadProjectImages = new UploadProjectImages(getActivity());
                            uploadProjectImages.execute();
                        } else {
                            //pd.dismiss();
                            Toast.makeText(getActivity(), "Please Upload atleast 4 images", Toast.LENGTH_LONG).show();
                            //btn_submitCompletion.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });


        btn_removeProjImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageAdapter!=null) {
                    imageAdapter.clear();
                    imageUrls.clear();
                    imageAdapter = null;
                }else{
                    Toast.makeText(context,"No Images to remove",Toast.LENGTH_SHORT).show();
                }
            }
        });


        spin_completedProject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!spin_completedProject.getSelectedItem().toString().equals("List Of Projects") && !spin_completedProject.getSelectedItem().toString().equals("No Approved Projects")){
                    selectedProject = spin_completedProject.getSelectedItem().toString();

                    GetProjectDetails getProjctDtls = new GetProjectDetails(getActivity());
                    getProjctDtls.execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_additional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        btn_uploadReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        /*        String[] mimeTypes = {"application/pdf","application/msword","application/vnd.ms-excel"};

                String mimeTypesStr = "";
                for (String mimeType : mimeTypes) {
                    mimeTypesStr += mimeType + "|";
                }

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
                startActivityForResult(intent,PICKFILE_RESULT_CODE);*/
            Intent documentIntent = new Intent(getActivity().getBaseContext(),CompletionDocumentFolderSelectActivity.class);
            startActivityForResult(documentIntent,SELECT_FILE);



            }
        });

        return view;


    }// end of oncreate()

/*    @Override
    public void onDetach() {
        Log.d("insideexitsss","exitsssss");

        for(String imgUrl : imageUrls){
            if(!imgUrl.startsWith("content")) {
                String where = MediaStore.MediaColumns.DATA + "=?";
                String[] selectionArgs = new String[]{imgUrl};
                ContentResolver contentResolver = context.getContentResolver();
                Uri filesUri = MediaStore.Files.getContentUri("external");

                int deleted = contentResolver.delete(filesUri, where, selectionArgs);
                Log.d("deltedCountcontentissss", String.valueOf(deleted));
            }else{
                ContentResolver contentResolver = context.getContentResolver();
                Uri filesUri = Uri.parse(imgUrl);
                int deleted = contentResolver.delete(filesUri, null, null);
                Log.d("deletedCountCameraissss", String.valueOf(deleted));
            }
        }
        super.onDetach();
    }*/

    private boolean checkMandatory() {

        String str_challanges = edt_challanges.getText().toString();
        String str_learning = edt_learning.getText().toString();
        String str_story = edt_story.getText().toString();


        if(str_challanges.isEmpty()){
            //pd.dismiss();
            edt_challanges.setError("Enter the challenges");
            edt_challanges.requestFocus();
            return false;
        }

        if(str_learning.isEmpty()){
            //pd.dismiss();
            edt_learning.setError("Enter the learnings");
            edt_learning.requestFocus();
            return false;
        }

        if(str_story.isEmpty()){
            //pd.dismiss();
            edt_story.setError("Enter the story");
            edt_story.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }


    public class UploadProjectImages extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        /*private ProgressBar progressBar;*/

        UploadProjectImages (Context ctx)
        {
            context = ctx;
            pd = new ProgressDialog(context);
            pd.setMessage("Submitting your request please wait. If you press back button your request will be uploaded in the backend");
            pd.show();
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            SoapPrimitive response =null;

           /* for(int k=0;k<arrlstUploadedImageBitmap.size();k++) {
                response = uploadProjectImage(arrlstUploadedImageBitmap.get(k),k+1);
                if(k==arrlstUploadedImageBitmap.size()-1){
                    return response;
                }
            }*/

            for(int k=0;k<arrlstUploadImageByteArray.size();k++)
            {
                response = uploadProjectImage(arrlstUploadImageByteArray.get(k),k+1);
                if(k==arrlstUploadImageByteArray.size()-1)
                {
                    return response;
                }
            }


            //Log.d("Soap response is",response.toString());
            return null;

        }

        @Override
        protected void onPreExecute() {
   /*         progressBar = (ProgressBar) view4.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {

            if(result != null) {
                if (result.toString().equalsIgnoreCase("success")) {

           /*     for(String imgePath : imageAdapter.getImageList()) {
                    final String where = MediaStore.MediaColumns.DATA + "=?";
                    final String[] selectionArgs = new String[]{imgePath};
                    final ContentResolver contentResolver = context.getContentResolver();
                    final Uri filesUri = MediaStore.Files.getContentUri("external");

                    contentResolver.delete(filesUri, where, selectionArgs);
                }*/

                    //imgurlss.clear();
                    imageUrls.clear();
                    arrlstUploadedImageBitmap.clear();


                    if (docbyteArray != null) {
                        UploadProjectDocument uploadProjectDocument = new UploadProjectDocument(getActivity());
                        uploadProjectDocument.execute();
                    } else {
                        SubmitForCompletion submitForCompletion = new SubmitForCompletion(getActivity());
                        submitForCompletion.execute();
                    }
                } else {
                    Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG).show();
                    pd.dismiss();
                    //progressDialog.dismiss();
                }
            }else{
                //Toast.makeText(getActivity(), "", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive uploadProjectImage(byte[] uploadImgString,int count) {
        String METHOD_NAME = "UpdateProjectCompletionImg1";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletionImg1";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","uploadProjectImage");

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",str_leadId);
            request.addProperty("RegistrationId",str_RegistrationId);

            Log.d("Projectssssssss", String.valueOf(mapProjectIdProject.get(selectedProject)));
            request.addProperty("ProjectId",mapProjectIdProject.get(selectedProject));

            request.addProperty("ProfileImage",uploadImgString);
            request.addProperty("doccount",count);



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);


            envelope.dotNet = true;

            Log.d("Requestlogcat",request.toString());

            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //Log.d("soaprespUploadPrjctImg",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soaprespUploadPrjctImg",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();

                }
            });
           /* getActivity().finish();*/
        }
        return null;
    }

    public class SubmitForCompletion extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        SubmitForCompletion (Context ctx){
            context = ctx;
        }

        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            SoapPrimitive response = submitCompletionProject();
            return response;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            Toast.makeText(getActivity(),result.toString(),Toast.LENGTH_LONG).show();
            pd.dismiss();
            //progressDialog.dismiss();
            if(result!=null) {
                if (result.toString().equalsIgnoreCase("success")) {
                    //pd.dismiss();
                    getActivity().finish();
                }
            }else{
                //getActivity().finish();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive submitCompletionProject() {
        String METHOD_NAME = "UpdateProjectCompletions";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletions";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","submitCompletionProject");

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",str_leadId);
            request.addProperty("RegistrationId",str_RegistrationId);

            Log.d("Projectssssssss", String.valueOf(mapProjectIdProject.get(selectedProject)));

            request.addProperty("ProjectId",mapProjectIdProject.get(selectedProject));

            if(edt_fundRaised != null){
                int length = edt_fundRaised.getText().length();
                Log.d("Lengthisss", String.valueOf(length));
                if(length > 0) {
                    Log.d("beforelengthgreaterthan","zero");
                    String str_fundRaised = edt_fundRaised.getText().toString();
                    Log.d("str_fundraisedafter", str_fundRaised);
                    if (!str_fundRaised.equals(null) && !str_fundRaised.isEmpty() && !str_fundRaised.equals("")) {
                        request.addProperty("FundsRaised", str_fundRaised);
                    } else {
                        request.addProperty("FundsRaised", "0");
                    }
                }else{
                    request.addProperty("FundsRaised", "0");
                }
            }else{
                request.addProperty("FundsRaised", "0");
            }

            if(edt_challanges.getText()!=null) {
                String str_Challanges = edt_challanges.getText().toString();
                if (!str_Challanges.isEmpty() && !str_Challanges.equals("") && !str_Challanges.equals(null)) {
                    request.addProperty("Challenge", str_Challanges);
                } else {
                    request.addProperty("Challenge", "");
                }
            }else{
                request.addProperty("Challenge", "");
            }

            if(edt_learning.getText()!=null) {
                String str_learning = edt_learning.getText().toString();
                if (!str_learning.isEmpty() && !str_learning.equals("") && !str_learning.equals(null)) {
                    request.addProperty("Learning", str_learning);
                } else {
                    request.addProperty("Learning", "");
                }
            }else{
                request.addProperty("Learning", "");
            }

            if(edt_story.getText()!=null) {
                String str_story = edt_story.getText().toString();
                if (!str_story.isEmpty() && !str_story.equals("") && !str_story.equals(null)) {
                    request.addProperty("AsAStory", str_story);
                } else {
                    request.addProperty("AsAStory", "");
                }
            }else{
                request.addProperty("AsAStory", "");
            }

            if(edt_resourcesUtilised.getText() != null){
                String str_resourceUtilised = edt_resourcesUtilised.getText().toString();
                Log.d("StringResourcesUtilised",str_resourceUtilised);

                if (!str_resourceUtilised.isEmpty() && !str_resourceUtilised.equals("") && !str_resourceUtilised.equals(null)) {
                    request.addProperty("Resource", str_resourceUtilised);
                } else {
                    request.addProperty("Resource", "");
                }
            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //new MarshalBase64().register(envelope);
            //new Marshal.register(envelope);

            envelope.dotNet = true;




            //Set output SOAP object
            envelope.setOutputSoapObject(request);



            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("soaprespsubmitComplnPrj",envelope.getResponse().toString());

                //Log.d("Requestsssss",request.toString());


                //SoapObject response = (SoapObject) envelope.getResponse();

                //return null;
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soaprespsubmitComplnPrj",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

    public class UploadProjectDocument extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        UploadProjectDocument (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            SoapPrimitive response =null;

            response = uploadProjectDocument();

            //Log.d("Soap response is",response.toString());
            return response;

        }

        @Override
        protected void onPreExecute() {
   /*         progressBar = (ProgressBar) view4.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
         /*    Toast.makeText(getActivity(),result.toString(),Toast.LENGTH_LONG).show();
             progressDialog.dismiss();*/


            if(result != null) {
                if (result.toString().equalsIgnoreCase("success")) {
                    //Log.d("FinalSoapResult",result.toString());
/*                LoadApprovedProject loadApprovedProject = new LoadApprovedProject(getActivity());
                loadApprovedProject.execute();*/

                    SubmitForCompletion submitForCompletion = new SubmitForCompletion(getActivity());
                    submitForCompletion.execute();
                } else {
                    Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

                Log.d("Resultisssss", result.toString());
            }else{
                progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive uploadProjectDocument() {
        String METHOD_NAME = "UpdateProjectCompletionDocument";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletionDocument";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","uploadProjectDocumentssss");

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId",str_leadId);
            request.addProperty("RegistrationId",str_RegistrationId);

            Log.d("Projectssssssss", String.valueOf(mapProjectIdProject.get(selectedProject)));
            request.addProperty("ProjectId",mapProjectIdProject.get(selectedProject));

            request.addProperty("docFile",docbyteArray);
            request.addProperty("extension",extensions);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            new MarshalBase64().register(envelope);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //Log.d("soaprespUpldPrjctDoc",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soaprespUpldPrjctDoc",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }


    private void initializeViews() {
        mapProjectIdProject = new HashMap<String,Integer>();

        lnrlyt_ProjectName = (LinearLayout) view4.findViewById(R.id.lnrlyt_ProjectName);
        lnrlyt_projectType = (LinearLayout) view4.findViewById(R.id.lnrlyt_projectType);
        lnrlyt_projObjective = (LinearLayout) view4.findViewById(R.id.lnrlyt_projObjective);
        lnrlyt_placeOfimpl = (LinearLayout) view4.findViewById(R.id.lnrlyt_placeOfimpl);
        lnrlyt_leadFunded = (LinearLayout) view4.findViewById(R.id.lnrlyt_leadFunded);
        lnrlyt_challanges = (LinearLayout) view4.findViewById(R.id.lnrlyt_challanges);
        //lnrlyt_uploadImage = (LinearLayout) view4.findViewById(R.id.lnrlyt_uploadImage);
        //lnrlyt_submit = (Button) view4.findViewById(R.id.lnrlyt_submit);

        lnrlyt_txtUploadProj = (LinearLayout) view4.findViewById(R.id.lnrlyt_txtUploadProj);
        lnrlyt_uploadDocument = (LinearLayout) view4.findViewById(R.id.lnrlyt_uploadDocument);

        txt_projectName = (TextView) view4.findViewById(R.id.txt_projectName);
        txt_projectType = (TextView) view4.findViewById(R.id.txt_projectType);
        txt_beneficiaries = (TextView) view4.findViewById(R.id.txt_beneficiaries);
        txt_objective = (TextView) view4.findViewById(R.id.txt_objective);
        txt_leadFunded = (TextView) view4.findViewById(R.id.txt_leadFunded);
        txt_approvedAmt = (TextView) view4.findViewById(R.id.txt_approvedAmt);

        //edt_placeOfImpl = (EditText) view4.findViewById(R.id.edt_placeOfImpl);
        edt_fundRaised = (EditText) view4.findViewById(R.id.edt_fundRaised);
        edt_challanges = (EditText) view4.findViewById(R.id.edt_challanges);
        edt_learning = (EditText) view4.findViewById(R.id.edt_learning);
        edt_story = (EditText) view4.findViewById(R.id.edt_story);

        edt_resourcesUtilised = (EditText) view4.findViewById(R.id.edt_resourcesUtilised);
        //edt_resourcesUtilisedAmt = (EditText) view4.findViewById(R.id.edt_resourcesUtilisedAmt);

        spin_completedProject = (AppCompatSpinner) view4.findViewById(R.id.spin_completedProject);//
        //btn_uploadReport = (Button) view4.findViewById(R.id.btn_uploadReport);

        btn_uploadReport = (ImageView) view4.findViewById(R.id.btn_uploadReport);

        btn_additional = (ImageButton) view4.findViewById(R.id.btn_addProject);

        fileSelected = (TextView) view4.findViewById(R.id.txt_fileSelected);

        txt_placeOfImpl = (TextView) view4.findViewById(R.id.txt_placeOfImpl);

        btn_submitCompletion = (Button) view4.findViewById(R.id.btn_submitCompletion);

        btn_removeProjImag = (Button) view4.findViewById(R.id.btn_removeProjImag);

        //coordinatorLayout = (CoordinatorLayout) view4.findViewById(R.id.coordinatorLayout);

        imageUrls = new ArrayList<String>();

        imgurlss = new ArrayList<String>();

        arrlstUploadedImageBitmap = new ArrayList<String>();

        cameraImgMap = new HashMap<String,Bitmap>();

        arrlstUploadImageByteArray = new ArrayList<byte[]>();
    }

    public class LoadApprovedProject extends AsyncTask<Void, Void, SoapObject> {

        AlertDialog alertDialog;

        LoadApprovedProject (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);

        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            SoapObject response = loadApprovedProjects();

            //Log.d("LoadApprovedProjectRes:",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
    /*        progressBar = (ProgressBar) view4.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            if(arrlstUploadedImageBitmap!=null && !arrlstUploadedImageBitmap.isEmpty()) {
                arrlstUploadedImageBitmap.clear();
            }

        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if(result != null){
            mapProjectIdProject.clear();

            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject list = (SoapObject) result.getProperty(i);
                SoapPrimitive S_ProjectTitle, S_ProjectId, S_Status;
                Object O_ProjectTitle, O_ProjectId, O_Status;
                String str_ProjectTitle = null, str_ProjectId = null, str_Status = null;

                O_Status = list.getProperty("status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) list.getProperty("status");
                    Log.d("Status", S_Status.toString());
                    str_Status = S_Status.toString();
                }
                if (str_Status.equalsIgnoreCase("success")) {
                    Log.d("Inside", "Successs");

                    O_ProjectTitle = list.getProperty("Title");
                    if (!O_ProjectTitle.toString().equals("anyType{}") && !O_ProjectTitle.toString().equals(null)) {
                        S_ProjectTitle = (SoapPrimitive) list.getProperty("Title");
                        Log.d("Title", S_ProjectTitle.toString());
                        str_ProjectTitle = O_ProjectTitle.toString();
                    }

                    O_ProjectId = list.getProperty("PDId");
                    if (!O_ProjectId.toString().equals("anyType{}") && !O_ProjectId.toString().equals(null)) {
                        S_ProjectId = (SoapPrimitive) list.getProperty("PDId");
                        Log.d("PDId", S_ProjectId.toString());
                        str_ProjectId = O_ProjectId.toString();
                    }

                    mapProjectIdProject.put(str_ProjectTitle, Integer.valueOf(str_ProjectId));
                }
            }

            initListOfCompletedProjectSpinner();

            if (spin_completedProject.getSelectedItem().toString().equals("List Of Projects") || spin_completedProject.getSelectedItem().toString().contains("No Approved")) {

                rl_relativelayout.setVisibility(View.VISIBLE);
                lnrlyt_ProjectName.setVisibility(View.GONE);
                lnrlyt_projectType.setVisibility(View.GONE);
                lnrlyt_projObjective.setVisibility(View.GONE);
                lnrlyt_placeOfimpl.setVisibility(View.GONE);
                lnrlyt_leadFunded.setVisibility(View.GONE);
                lnrlyt_challanges.setVisibility(View.GONE);
                //lnrlyt_uploadImage.setVisibility(View.GONE);
                //lnrlyt_submit.setVisibility(View.GONE);
                btn_submitCompletion.setVisibility(View.GONE);

                lnrlyt_txtUploadProj.setVisibility(View.GONE);
                lnrlyt_uploadDocument.setVisibility(View.GONE);


            } else {
                rl_relativelayout.setVisibility(View.VISIBLE);
                lnrlyt_ProjectName.setVisibility(View.VISIBLE);
                lnrlyt_projectType.setVisibility(View.VISIBLE);
                lnrlyt_projObjective.setVisibility(View.VISIBLE);
                lnrlyt_placeOfimpl.setVisibility(View.VISIBLE);
                lnrlyt_leadFunded.setVisibility(View.VISIBLE);
                lnrlyt_challanges.setVisibility(View.VISIBLE);
                //lnrlyt_uploadImage.setVisibility(View.VISIBLE);
                //lnrlyt_submit.setVisibility(View.VISIBLE);
                btn_submitCompletion.setVisibility(View.VISIBLE);

                lnrlyt_txtUploadProj.setVisibility(View.VISIBLE);
                lnrlyt_uploadDocument.setVisibility(View.VISIBLE);
            }

        }

            //progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public class GetProjectDetails extends AsyncTask<Void, Void, SoapObject> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        GetProjectDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getProjectDtls();

            //Log.d("Soapresponseissssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) view4.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if(result!=null) {

                SoapPrimitive S_Status;
                Object O_Status;
                String str_status = null;


                O_Status = result.getProperty("status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) result.getProperty("status");
                    Log.d("Title", S_Status.toString());
                    str_status = O_Status.toString();
                }

                if (!str_status.equals("Success")) {
                    lnrlyt_ProjectName.setVisibility(View.GONE);
                    lnrlyt_projectType.setVisibility(View.GONE);
                    lnrlyt_projObjective.setVisibility(View.GONE);
                    lnrlyt_placeOfimpl.setVisibility(View.GONE);
                    lnrlyt_leadFunded.setVisibility(View.GONE);
                    lnrlyt_challanges.setVisibility(View.GONE);
                    //lnrlyt_uploadImage.setVisibility(View.GONE);
                    //lnrlyt_submit.setVisibility(View.GONE);
                    btn_submitCompletion.setVisibility(View.GONE);
                    lnrlyt_txtUploadProj.setVisibility(View.GONE);
                    lnrlyt_uploadDocument.setVisibility(View.GONE);


                    Toast.makeText(getActivity(), str_status, Toast.LENGTH_LONG).show();
                } else {
                    lnrlyt_ProjectName.setVisibility(View.VISIBLE);
                    lnrlyt_projectType.setVisibility(View.VISIBLE);
                    lnrlyt_projObjective.setVisibility(View.VISIBLE);
                    lnrlyt_placeOfimpl.setVisibility(View.VISIBLE);
                    lnrlyt_leadFunded.setVisibility(View.VISIBLE);
                    lnrlyt_challanges.setVisibility(View.VISIBLE);
                    //lnrlyt_uploadImage.setVisibility(View.VISIBLE);
                    //lnrlyt_submit.setVisibility(View.VISIBLE);
                    btn_submitCompletion.setVisibility(View.VISIBLE);

                    lnrlyt_txtUploadProj.setVisibility(View.VISIBLE);
                    lnrlyt_uploadDocument.setVisibility(View.VISIBLE);


                    SoapPrimitive S_ProjectName, S_ProjectType, S_Beneficiaries, S_Objectives, S_LeadFunded, S_ApprovedAmount, S_Placeofimplement;
                    Object O_ProjectName, O_ProjectType, O_Beneficiaries, O_Objectives, O_LeadFunded, O_ApprovedAmount, O_Placeofimplement;
                    String str_ProjectName = null, str_ProjectType = null, str_Beneficiaries = null, str_Objectives, str_LeadFunded, str_ApprovedAmount, str_Placeofimplement;


                    O_ProjectName = result.getProperty("Title");
                    if (!O_ProjectName.toString().equals("anyType{}") && !O_ProjectName.toString().equals(null)) {
                        S_ProjectName = (SoapPrimitive) result.getProperty("Title");
                        Log.d("Title:", S_ProjectName.toString());
                        str_ProjectName = S_ProjectName.toString();
                        txt_projectName.setText(str_ProjectName);
                    }

                    O_ProjectType = result.getProperty("ThemeName");
                    if (!O_ProjectType.toString().equals("anyType{}") && !O_ProjectType.toString().equals(null)) {
                        S_ProjectType = (SoapPrimitive) result.getProperty("ThemeName");
                        Log.d("Project Type", S_ProjectType.toString());
                        str_ProjectType = S_ProjectType.toString();
                        txt_projectType.setText(str_ProjectType);
                    }

                    O_Beneficiaries = result.getProperty("BeneficiaryNo");
                    if (!O_Beneficiaries.toString().equals("anyType{}") && !O_Beneficiaries.toString().equals(null)) {
                        S_Beneficiaries = (SoapPrimitive) result.getProperty("BeneficiaryNo");
                        Log.d("Beneficiaries:", S_Beneficiaries.toString());
                        str_Beneficiaries = S_Beneficiaries.toString();
                        txt_beneficiaries.setText(str_Beneficiaries);
                    }

                    O_Objectives = result.getProperty("Objectives");
                    if (!O_Objectives.toString().equals("anyType{}") && !O_Objectives.toString().equals(null)) {
                        S_Objectives = (SoapPrimitive) result.getProperty("Objectives");
                        Log.d("Objectives:", S_Objectives.toString());
                        str_Objectives = S_Objectives.toString();
                        txt_objective.setText(str_Objectives);
                    }

                    O_LeadFunded = result.getProperty("giventotal");
                    if (!O_LeadFunded.toString().equals("anyType{}") && !O_LeadFunded.toString().equals(null)) {
                        S_LeadFunded = (SoapPrimitive) result.getProperty("giventotal");
                        Log.d("Lead Funded:", S_LeadFunded.toString());
                        str_LeadFunded = S_LeadFunded.toString();
                        txt_leadFunded.setText(str_LeadFunded);
                    }

                    O_ApprovedAmount = result.getProperty("SanctionAmount");
                    if (!O_ApprovedAmount.toString().equals("anyType{}") && !O_ApprovedAmount.toString().equals(null)) {
                        S_ApprovedAmount = (SoapPrimitive) result.getProperty("SanctionAmount");
                        Log.d("Approved Amount:", S_ApprovedAmount.toString());
                        str_ApprovedAmount = S_ApprovedAmount.toString();
                        txt_approvedAmt.setText(str_ApprovedAmount);
                    }

                    O_Placeofimplement = result.getProperty("Placeofimplement");
                    if (!O_Placeofimplement.toString().equals("anyType{}") && !O_Placeofimplement.toString().equals(null)) {
                        S_Placeofimplement = (SoapPrimitive) result.getProperty("Placeofimplement");
                        Log.d("Placeofimplement:", S_Placeofimplement.toString());
                        str_Placeofimplement = S_Placeofimplement.toString();
                        txt_placeOfImpl.setText(str_Placeofimplement);
                    }


                }
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getProjectDtls() {
        String METHOD_NAME = "CompletionGetApprovedProjectDetails";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/CompletionGetApprovedProjectDetails";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("leadId",str_leadId);
            request.addProperty("PDId",mapProjectIdProject.get(selectedProject));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapcompletionxxxx",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }


        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

    private SoapObject loadApprovedProjects()
    {
        String METHOD_NAME = "CompletionGetApprovedProjectList";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/CompletionGetApprovedProjectList";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("Lead_Id",str_leadId);

            //users.ad
            //request.add

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                //Log.e("ResponseGetApproved:",envelope.getResponse().toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
         //   if (requestCode == SELECT_FILE) {
                //onSelectFromGalleryResult(data);


        /*        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                imagesEncodedList = new ArrayList<String>();
                if(data.getData()!=null){

                    Uri mImageUri=data.getData();

                    // Get the cursor
                    Cursor cursor = getActivity().getBaseContext().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded  = cursor.getString(columnIndex);
                    cursor.close();

                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    mArrayUri.add(mImageUri);

                    Log.d("ImageEncodedissssssssif",imageEncoded);



      *//*              galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                    gvGallery.setAdapter(galleryAdapter);
                    gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                            .getLayoutParams();
                    mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);*//*

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getActivity().getBaseContext().getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded  = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);

                            Log.d("ImageEncodedisssssssselse",imageEncoded);

                            cursor.close();

                    *//*        galleryAdapter = new GalleryAdapter(getApplicationContext(),mArrayUri);
                            gvGallery.setAdapter(galleryAdapter);
                            gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
                            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                                    .getLayoutParams();
                            mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);*//*

                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }*/


              /*  Log.d("InsideActivity","Resultsssssssssssss");


            }

            else*/ if (requestCode == REQUEST_CAMERA) {
              String path1=  CommonUtils.compressImage(imageFilePath);
              Log.e("tag","path1"+path1);
             /*   GetImageThumbnail getImageThumbnail = new GetImageThumbnail();

                Bitmap bitmap = null;
                try {
                    bitmap = getImageThumbnail.getThumbnail(fileUri, getActivity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
*/
                Bitmap bitmap=BitmapFactory.decodeFile(new File(imageFilePath).getAbsolutePath());
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageInByte;
                imageInByte = stream.toByteArray();
                Bitmap bm = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
              //  String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bm, "Titledddd", null);

                cameraImgMap.put(path1,bm);

                CompletionProjectStaticClass.setBytesImage(imageInByte); ///Image(imageInByte);
              //  arrlstUploadImageByteArray.add(imageInByte);
             //   Log.e("tag","cameraImgMap="+cameraImgMap);
                //imageUrls.add(path);
               /* if(imageAdapter!=null) {
                    //imageUrls.clear();
                    imageUrls.addAll(imageAdapter.getImageList());
                    imageUrls.add(path);
                }else{
                    imageUrls.add(path);
                }*/




            /*    if(imageAdapter!=null) {

                    Log.d("imageAdapterStringsss1",imageAdapter.getImageList().toString());
                    Log.d("imageUrlsStringsss1",imageUrls.toString());

                    ArrayList<String> sample = new ArrayList<String>();
                    for(String imgUrl : imageUrls){
                        for (int j = 0; j < imageAdapter.getImageList().size(); j++) {
                            if ((imgUrl.equals(imageAdapter.getImageList().get(j)))){
                                Log.d("ImageURLtobedeletedisss",imgUrl);
                                String imgiss = imgUrl;
                                //imageUrls.remove(imgiss);

                                sample.add(imgiss);
                            }
                        }
                    }
                    imageUrls.removeAll(sample);

                    Log.d("imageAdapterStringsss2",imageAdapter.getImageList().toString());
                    Log.d("imageUrlsStringsss2",imageUrls.toString());

                }*/

        /*        ArrayList<String> dummyimageUrls = new ArrayList<String>();
                dummyimageUrls.addAll(imageUrls);

                if(imageAdapter!=null){
                    ArrayList<String> currentList = imageAdapter.getImageList();

                    Iterator<String> it = dummyimageUrls.iterator();

                    ArrayList<String> sample = new ArrayList<String>();
                    while(it.hasNext()){
                        String simple = it.next();
                        System.out.println(simple);
                        if(!currentList.contains(simple)){
                            //dummyimageUrls.remove(simple);
                            sample.add(simple);
                        }
                    }
                    dummyimageUrls.removeAll(sample);
                }

                imageUrls.clear();
                imageUrls.addAll(dummyimageUrls);*/


                if(imageAdapter!=null) {
                    imageUrls.clear();
                    Log.d("imageAdapterss",imageAdapter.getImageList().toString());
                    imageUrls.addAll(imageAdapter.getImageList());
                }

                imageUrls.add(path1);

                /*ArrayList<Uri> mArrayUri = new ArrayList<Uri>();

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.upload_project_image, null);

                ImageView img_proj = (ImageView) rowView.findViewById(R.id.img_projImage);

                Log.d("Childcountisss", String.valueOf(parentLinearLayout.getChildCount()));

                btnUploadId = (parentLinearLayout.getChildCount()+1)*1000;

                projImageId = btnUploadId + 1;

                Log.d("btnUploadId", String.valueOf(btnUploadId));

                Log.d("projImageId", String.valueOf(projImageId));

                //btnUploadImage.setId(btnUploadId);
                //img_proj.setId(projImageId);

                hashMapBtnImg.put(btnUploadId,projImageId);

                if(parentLinearLayout.getChildCount()<=9) {
                    parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount());
                    rowView.setId(projImageId+1);
                    rowviewId = rowView.getId();

                    rowView.setVisibility(View.GONE);
                }

                if((parentLinearLayout.getChildCount() % 2) == 0){
                    rowView.setPadding(160,0,0,0);
                }else{
                    rowView.setPadding(0,0,0,0);
                }

                imageidis = projImageId;
                flag = 1;

                Log.d("LOG_TAG", "Selected Images" + mArrayUri.size());

                int id1 = getResources().getIdentifier(String.valueOf(rowviewId), "id", getActivity().getPackageName());
                rowLinearLayout = (RelativeLayout) view4.findViewById(id1);
                rowLinearLayout.setVisibility(View.VISIBLE);

                */

    /*            try {
                    actualImage = FileUtil.from(getActivity(), Uri.parse(path));

                    Log.d("ActualImageSize", "ActualImageSizeissss " + getReadableFileSize(actualImage.length()));
                    String fileSize = getReadableFileSize(actualImage.length());
                    String fileSizeArray[] = fileSize.split("\\s");

                    if(fileSizeArray[1].equals("GB") || fileSizeArray[1].equals("TB") || (fileSizeArray[1].equals("MB") && (Double.valueOf(fileSizeArray[0]) > 5.0))) {
                        compressedImage = new Compressor.Builder(getActivity())
                                .setMaxWidth(740)
                                .setMaxHeight(480)
                                .setQuality(100)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
                                .build()
                                .compressToFile(actualImage);
                        //img_proj.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
                    }

                    else{
                        //imgProj.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                        //img_proj.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                //imgurlss.clear();
                //imgurlss.addAll(imageUrls);
                initializeRecyclerView();
                //imgurlss.removeAll(imageUrls);
                //imgurlss.addAll(imageAdapter.getImageList());

            }

            else if(requestCode == PICKFILE_RESULT_CODE){
                Uri selectedUri = data.getData();

                String filePath;

                Log.e("uri",selectedUri.toString());

                if (selectedUri.toString().startsWith("file:")) {
                    filePath = selectedUri.getPath();
                }

                else {
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getBaseContext().getContentResolver().query(selectedUri,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    filePath = cursor.getString(columnIndex);
                    cursor.close();
                }

                Log.d("filePathissssss",filePath);

                //if(selectedImage.toString(""))

                File file = new File(filePath);

                docbyteArray = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);

                    int offset = 0;
                    int numRead = 0;
                    while (offset < docbyteArray.length && (numRead=fileInputStream.read(docbyteArray, offset, docbyteArray.length-offset)) >= 0) {
                        offset += numRead;
                    }

                    // Ensure all the bytes have been read in
                    if (offset < docbyteArray.length) {
                        throw new IOException("Could not completely read file " + file.getName());
                    }


                    Log.d("bytearrayisss",docbyteArray.toString());

                    String[] fileArray = filePath.split("/");
                    for(int k=0;k<fileArray.length;k++){
                        String filePart = fileArray[k];
                        Log.d("Kvalueis", String.valueOf(k));
                        Log.d("filePartisss",filePart);
                    }

                    //String extArray = fil
                    extensions = fileArray[fileArray.length-1].split("\\.")[1];

                    Log.d("extensionisss",extensions);



                    // Close the input stream and return bytes
                    fileInputStream.close();

                    Toast.makeText(getActivity(),"File is Selected",Toast.LENGTH_LONG).show();

                    fileSelected.setText(filePath);
                    fileSelected.setVisibility(View.VISIBLE);

              /*      Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "File is selected", Snackbar.LENGTH_LONG);
                    snackbar1.show();*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(resultCode == RESULT_CANCELED){
            Log.d("Inside","RESULT_Cancelledddddd");

            Intent itt = getActivity().getIntent();

            ArrayList<String> fileArrayString;
            ArrayList<byte[]> fileArrayByte;
            ArrayList<String> fileDocumentString;

            if( CompletionProjectStaticClass.getCountCompletion() == 1 ){
                Log.d("Inside","RESULT_CancelleddddddfromPhotosActivityIntent");
                CompletionProjectStaticClass.setCountCompletion(0);

                fileArrayString = CompletionProjectStaticClass.getFileArrayString();
                fileArrayByte = CompletionProjectStaticClass.getByteArrayImage();
                imagesEncodedList = new ArrayList<String>();
                imagesbyteEncodedList = new ArrayList<byte[]>();
                imagesbyteEncodedListAll = new ArrayList<>();
                for(int i=0;i<fileArrayString.size();i++){
                    //imageUrls.add(fileArrayString.get(i));

                    if(imageAdapter!=null) {
                        imageUrls.clear();
                        imagesbyteEncodedList.clear();
                     //   imagesbyteEncodedList.addAll(imageAdapter.getImageListByte());
                        Log.d("imageAdapter ",imageAdapter.getImageList().toString());
                        imageUrls.addAll(imageAdapter.getImageList());
                    }
                    imagesbyteEncodedList.add(fileArrayByte.get(i));
                    imagesEncodedList.add(fileArrayString.get(i));
                    //imageUrls.add(fileArrayString.get(i));
                }

                imageUrls.addAll(imagesEncodedList);
             //   imagesbyteEncodedListAll.addAll(imagesbyteEncodedList);
                initializeRecyclerView();
            }


            if( CompletionProjectStaticClass.getCountDocumentCompletion() == 1 ){
                CompletionProjectStaticClass.setCountDocumentCompletion(0);

                fileDocumentString = CompletionProjectStaticClass.getFileDocString();

                Log.d("fileDocumentStringsss:",fileDocumentString.toString());

                for(int i=0;i<fileDocumentString.size();i++) {
                    String filePath = fileDocumentString.get(i);
                    File file = new File(filePath);

                    Log.d("fileDocumentString2:", fileDocumentString.get(i).toString());

                    docbyteArray = new byte[(int) file.length()];
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);

                        int offset = 0;
                        int numRead = 0;
                        while (offset < docbyteArray.length && (numRead = fileInputStream.read(docbyteArray, offset, docbyteArray.length - offset)) >= 0) {
                            offset += numRead;
                        }

                        // Ensure all the bytes have been read in
                        if (offset < docbyteArray.length) {
                            throw new IOException("Could not completely read file " + file.getName());
                        }

                        Log.d("bytearrayisss", docbyteArray.toString());

                        String[] fileArray = filePath.split("/");
                        for (int k = 0; k < fileArray.length; k++) {
                            String filePart = fileArray[k];
                            Log.d("Kvalueis", String.valueOf(k));
                            Log.d("filePartisss", filePart);
                        }

                        //String extArray = fil
                        extensions = fileArray[fileArray.length - 1].split("\\.")[1];

                        Log.d("extensionisss", extensions);


                        // Close the input stream and return bytes
                        fileInputStream.close();

                        Toast.makeText(getActivity(), "File is Selected", Toast.LENGTH_LONG).show();

                        fileSelected.setText(filePath);
                        fileSelected.setVisibility(View.VISIBLE);

                    } catch (Exception e) {

                    }
                }




            }



  /*          int id1 = getResources().getIdentifier(String.valueOf(rowviewId), "id", getActivity().getPackageName());
            rowLinearLayout = (RelativeLayout) view4.findViewById(id1);
            parentLinearLayout.removeView(rowLinearLayout);*/
        }
    }// end of onActivityResult

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    private void initListOfCompletedProjectSpinner()
    {
        final ArrayList listCompletedProj = new ArrayList();
        if(mapProjectIdProject.size()>0)
        {
            rl_relativelayout.setVisibility(View.VISIBLE);
            listCompletedProj.add("List Of Projects");

            Set<String> setCompleted = mapProjectIdProject.keySet();

            for (String key : setCompleted) {
                listCompletedProj.add(key);
            }
        }else{
            rl_relativelayout.setVisibility(View.INVISIBLE);
            listCompletedProj.add("No Approved Projects");
        }
        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);

        ArrayAdapter dataAdapterListCompleted = new ArrayAdapter(context, R.layout.simple_spinner_items, listCompletedProj);

        //ArrayAdapter dataAdapterListCompleted = ArrayAdapter.createFromResource(context, R.array.types, R.layout.simple_spinner_items);

        // Drop down layout style - list view with radio button
        // dataAdapterListCompleted.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterListCompleted.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        spin_completedProject.setAdapter(dataAdapterListCompleted);
        //spin_completedProject.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
    }


    private void selectImage()
    {
        final CharSequence[] items = { "Take Photo", "Choose From Library",
                "Cancel" };

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    cameraIntent();
                } else if (items[item].equals("Choose From Library")) {
                    userChoosenTask ="Choose from Library";
                    galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    /*int id1 = getResources().getIdentifier(String.valueOf(rowviewId), "id", getActivity().getPackageName());
                    rowLinearLayout = (RelativeLayout) view4.findViewById(id1);
                    parentLinearLayout.removeView(rowLinearLayout);*/

                    dialog.dismiss();
                }
            }
        });


        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                }
                return true;
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        img=getOutputFromCamera();
        imageFilePath = CommonUtils.getFilename();
        File imageFile = new File(imageFilePath);
        Uri imageFileUri = Uri.fromFile(imageFile); // convert path to Uri
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);   // set the image file name
        startActivityForResult(intent, REQUEST_CAMERA);
        /*fileUri= null;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String filePath = img.getAbsolutePath();

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, filePath);
            fileUri = context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }

        else{
            fileUri = Uri.fromFile(img);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUEST_CAMERA);*/
    }

    private File getOutputFromCamera() {
        String timeStamp = new SimpleDateFormat(getString(R.string.txtdateformat), Locale.getDefault()).format(new Date());
        imageFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getString(R.string.txtFileDocumentUploadImg) + timeStamp + getString(R.string.txtFileExtension));
        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Log.d("absolute path is",imageFile.getAbsolutePath());
        return imageFile;
    }






    private void galleryIntent()
    {
/*        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_FILE);*/

        Intent photoSelectActivity = new Intent(getActivity().getBaseContext(),CompletionGallerySelectActivity.class);
        //photoSelectActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //photoSelectActivity.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY );
        //startActivity(photoSelectActivity);
        startActivityForResult(photoSelectActivity,SELECT_FILE);

    }



    public Bitmap compressImage(String filePaths,int type)
    {

     /*   String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;*/

        String filePath = null;

        if(type==1){
            filePath = filePaths;
        }

        if(type==2){
            filePath = getRealPathFromURI(filePaths);
        }

        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//       by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//       you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//       max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;


        /*float maxHeight = 216.0f;
        float maxWidth = 212.0f;*/


        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//       width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//       setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//       inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//       this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//           load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//       check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

/*        FileOutputStream out = null;
        String filename = getFilename();
        compressedfilepaths=filename;*/

        /*      try {*/
        //out = new FileOutputStream(filename);

//           write the compressed bitmap at the destination specified by filename.
        //scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

      /*  } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        return scaledBitmap;

    }//compression Ends


    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = getActivity().getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    private void initializeRecyclerView()
    {

        imgurlss = new ArrayList<String>();

        imgurlss.addAll(imageUrls);

        imagesbyteEncodedListAll.addAll(imagesbyteEncodedList);
        imageAdapter = new ImageAdapter(getActivity().getApplicationContext(), imgurlss);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);

        //RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL, false);

        //layoutManager.setAutoMeasureEnabled(true);
        //layoutManager.
        RecyclerView recyclerView = (RecyclerView) view4.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(getActivity().getApplicationContext(), R.dimen.item_offset));
        recyclerView.setAdapter(imageAdapter);


    }








    private void doFtp() {

        MyFTPClientFunctions ftpclient = null;
        ftpclient = new MyFTPClientFunctions();


        final String host = "54.169.178.228";
        final String username = "administrator";
        final String password = "y7Kvso7;ZFz";

        boolean status = false;
        status = ftpclient.ftpConnect(host, username, password, 21);
        if (status == true) {
            Log.d("Connection Successsssss", "Connection Success");
        } else {
            Log.d("Connection faileddddddd", "Connection failed");
        }
    }


    public void uploadFile(File fileName){

        FTPClient client = new FTPClient();
        String imagePhotofilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "CameraContentDemo.jpeg";
        File imagePhotoFile = new File(imagePhotofilePath);

        try {

            client.connect(FTP_HOST,21);

            client.login(FTP_USER, FTP_PASS);

            if(client.isConnected()){
                Log.d("Connectedssssss","Yessss"+client.isConnected());
                client.setType(FTPClient.TYPE_BINARY);
                client.changeDirectory("/");

                client.upload(imagePhotoFile, new MyTransferListener());
            }
            else{
                Log.d("Connectedssssss","Noooo"+client.isConnected());
            }
            if(client.isAuthenticated()){
                Log.d("Authenticatedsss","Yessss"+client.isAuthenticated());
            }
            else{
                Log.d("Authenticatedsss","Noooo"+client.isAuthenticated());
            }

        } catch (Exception e) {
            Log.d("Exceptionsssssss",e.toString());
            e.printStackTrace();
            try {
                client.disconnect(true);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public class MyTransferListener implements FTPDataTransferListener {

        public void started() {
        }

        public void transferred(int length) {

            // Yet other length bytes has been transferred since the last time this
            // method was called
            Toast.makeText(getActivity(), " transferred ..." + length, Toast.LENGTH_SHORT).show();
            //System.out.println(" transferred ..." + length);
        }


        public void completed() {

            //btn.setVisibility(View.VISIBLE);
            // Transfer completed

            Toast.makeText(getActivity(), " completed ...", Toast.LENGTH_SHORT).show();
            //System.out.println(" completed ..." );
        }

        public void aborted() {

            //btn.setVisibility(View.VISIBLE);
            // Transfer aborted
            Toast.makeText(getActivity()," transfer aborted , please try again...", Toast.LENGTH_SHORT).show();
            //System.out.println(" aborted ..." );
        }

        public void failed() {

            //btn.setVisibility(View.VISIBLE);
            // Transfer failed
            System.out.println(" failed ..." );
        }

    }



    public class FTPConnectTask extends AsyncTask<Void, Void, Void> {

        private Context context;
        private ProgressBar progressBar;

        FTPConnectTask (Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            progressBar = (ProgressBar) view4.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... params) {
            File fileName = new File("sample");
            uploadFile(fileName);
            return null;
        }

        @Override
        protected void onPostExecute(Void string) {
            progressBar.setVisibility(GONE);
        }
    }


/*    private void createButtonClick(int projImageId) {
        int id1 = getResources().getIdentifier(String.valueOf(projImageId), "id", getActivity().getPackageName());
        img_projImage1 = (ImageView) view4.findViewById(id1);

        img_projImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"Button id: "+String.valueOf(v.getId()),Toast.LENGTH_LONG).show();
         *//*       imageidis = v.getId();
                flag = 1;*//*
                selectImage();
            }
        });
    }*/

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bao);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);

        //Log.e(TAG,"Path="+path);
        return Uri.parse(path);
    }


    /**
     * Showing Dialog
     * */
}
