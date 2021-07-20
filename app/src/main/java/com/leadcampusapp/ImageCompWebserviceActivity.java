package com.leadcampusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Capture image from camera and set in imageview by reducing its size without affecting quality.
 */
public class ImageCompWebserviceActivity extends AppCompatActivity {

    private Button mbtnCapture;
    private ImageView mivImage;
    private Context mContext;
    private final int REQUEST_CODE_CLICK_IMAGE = 1002, REQUEST_CODE_GALLERY_IMAGE = 1003;
    public static String imageFilePath;//Made it static as need to override the original image with compressed image.


    private BottomSheet mBottomSheetDialog;


    byte[] signimageinbytesArray={0};
    byte[] signimageinbytesArray2={0};
    ArrayList<byte[]> listbteArray;
    ArrayList<byte[]> listbteArray1;
    SerializeModule serializeModule;//=new SerializeModule();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagecomwebservice);
        mContext = ImageCompWebserviceActivity.this;
        mbtnCapture = (Button) findViewById(R.id.button);
        mivImage = (ImageView) findViewById(R.id.imageView);
        imageFilePath = CommonUtils.getFilename();

        listbteArray=new ArrayList<byte[]>();
        serializeModule=new SerializeModule();
        Log.d("Image Path===", imageFilePath);
        mbtnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionBottomSheetDialog();
            }
        });
        createBottomSheetDialog();
    }

    /**
     * Request for camera app to open and capture image.
     * @param isFromGallery-if true then launch gallery app else camera app.
     */
    public void startIntent(boolean isFromGallery) {
        if (!isFromGallery) {
            File imageFile = new File(imageFilePath);
            Uri imageFileUri = Uri.fromFile(imageFile); // convert path to Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);   // set the image file name
            startActivityForResult(intent, REQUEST_CODE_CLICK_IMAGE);
        } else if (isFromGallery) {
            File imageFile = new File(imageFilePath);
            Uri imageFileUri = Uri.fromFile(imageFile); // convert path to Uri
            Intent intent = new Intent(
                      Intent.ACTION_PICK,
                      MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.setType("image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);   // set the image file name
            startActivityForResult(
                      Intent.createChooser(intent, "Select File"),
                      REQUEST_CODE_GALLERY_IMAGE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CLICK_IMAGE) {
            Log.e("tag","imageFilePath1="+imageFilePath);
            new ImageCompression().execute(imageFilePath);

        } else if (requestCode == REQUEST_CODE_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            final String picturePath = cursor.getString(columnIndex); // returns null
            cursor.close();
            //copy the selected file of gallery into app's sdcard folder and perform the compression operations on it.
            //And override the original image with the newly resized image.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        CommonUtils.copyFile(picturePath, imageFilePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            Log.e("tag","imageFilePath2="+imageFilePath);
            new ImageCompression().execute(imageFilePath);

        }
    }


    /**
     * Show online options with the bottom sheet dialog with hangout,viber,skype calling options
     */
    private void showOptionBottomSheetDialog() {
        mBottomSheetDialog.show();
    }

    /**
     * Create a bottomsheet dialog.
     */
    public void createBottomSheetDialog()
    {
        BottomSheet.Builder builder = new BottomSheet.Builder(ImageCompWebserviceActivity.this).title("Choose Option").sheet(R
                  .menu
                  .image_selection_option).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.it_camera:
                        startIntent(false);
                        break;
                    case R.id.it_gallery:
                        startIntent(true);

                        break;
                    case R.id.it_cancel:

                        break;
                }
                CommonUtils.hideKeyboard(ImageCompWebserviceActivity.this);
            }
        });
        mBottomSheetDialog = builder.build();
    }

    /**
     * Asynchronos task to reduce an image size without affecting its quality and set in imageview.
     */
    public class ImageCompression extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (strings.length == 0 || strings[0] == null)
                return null;

            return CommonUtils.compressImage(strings[0]);
        }

        protected void onPostExecute(String imagePath) {
            // imagePath is path of new compressed image.
            Bitmap bmp =BitmapFactory.decodeFile(new File(imagePath).getAbsolutePath());

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            signimageinbytesArray = stream.toByteArray();
            serializeModule.setFileData(signimageinbytesArray);

            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            signimageinbytesArray2 = stream1.toByteArray();
            serializeModule.setFileData(signimageinbytesArray2);

            listbteArray1=new ArrayList<>();
            listbteArray.add(signimageinbytesArray);
            listbteArray.add(signimageinbytesArray2);
            listbteArray1.addAll(listbteArray);

            Log.e("listbteArray=", String.valueOf(listbteArray));
            Log.e("listbteArray1=", String.valueOf(listbteArray1));
           /* for(int i=0;i<=listbteArray.size();i++) {
                Log.e("listbteArray=", String.valueOf(listbteArray.get(i)));
            }*/
            mivImage.setImageBitmap(bmp);

            UploadProjectImage uploadProjectImage = new UploadProjectImage(getApplicationContext());
            uploadProjectImage.execute();
        }
    }

    public class UploadProjectImage extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

        UploadProjectImage (Context ctx){
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
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                  /*  SubmitForCompletion submitForCompletion = new SubmitForCompletion(getApplicationContext());
                    submitForCompletion.execute();*/
                } else {

                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                   // progressDialog.dismiss();
                }

                Log.d("Resultisssss", result.toString());
            }else{
               // progressDialog.dismiss();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive uploadProjectDocument() {
        String METHOD_NAME = "UpdateProjectCompletionImgCompressList";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/UpdateProjectCompletionImgCompressList";
        String NAMESPACE = "http://mis.leadcampus.org/";

        Log.d("Insidexxxxx","uploadProjectDocumentssss");

        try{
            //mis.leadcampus.org

            ArrayList<byte[]> newlist=new ArrayList<>();
            newlist.addAll(listbteArray);
            int count=newlist.size();
            ArrayList<Integer> intList=new ArrayList<>();
            intList.add(11);
            intList.add(22);
            intList.add(33);

            List<Integer> companies =  new ArrayList<Integer>();
            companies.add(65);
            companies.add(66);
            companies.add(67);

       //    serializeModule.setFileInt(intList);
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("leadId","mh05102");
            request.addProperty("ProjectId","40913");
         //   request.addProperty("ProfileImage", intList);

            JSONArray jsonArray=new JSONArray();
            jsonArray.put(listbteArray);
            JSONObject jsonObject = new JSONObject();
            for(int k=0;k<jsonArray.length();k++){
                jsonObject.put("ProfileImage",jsonArray.get(k));
            }

            request.addProperty("ProfileImage",jsonObject.toString());


          /*  SoapObject soapCompanies = new SoapObject(NAMESPACE, "companies");
            for (Integer i : companies){
                soapCompanies.addProperty("int", i);
            }

            request.addProperty("ProfileImage",soapCompanies);*/

         /*   PropertyInfo profileImg = new PropertyInfo();
            for(int i=0;i<=3;i++) {

                profileImg.setName("ProfileImage");
                profileImg.setValue(intList.get(i));
              ///  profileImg.setValue(serializeModule.getFileInt(i));
              //  profileImg.setValue(i);
                profileImg.setType(int.class);
                request.addProperty(profileImg);
            }*/


            request.addProperty("doccount",1);
           /* for(int i=0;i<=listbteArray.size();i++) {
                request.addProperty("ProfileImage", listbteArray.get(i));
            }*/
            /*PropertyInfo profileImg = new PropertyInfo();
            for(int i=0;i<=count;i++) {

                profileImg.setName("ProfileImage");
                profileImg.setValue(newlist.get(i));
                profileImg.setType(byte.class);
            }
            request.addProperty(profileImg);*/

            Log.d("Requestlogcat",request.toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
         //   new MarshalBase64().register(envelope);

           // MarshalBase64 marshal = new MarshalBase64();
            //marshal.register(envelope);

            /*new MarshalBase64().register(envelope); //serialization
            envelope.encodingStyle = SoapEnvelope.ENC;
*/
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //Log.d("soaprespUpldPrjctDoc",envelope.getResponse().toString());

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("soaprespUpldPrjctDoc",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }

        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }


    @Override
    protected void onStop()
    {
        if (mBottomSheetDialog != null) {
            if (mBottomSheetDialog.isShowing()) {
                mBottomSheetDialog.hide();
            }
        }
        super.onStop();
    }


}
