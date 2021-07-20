package com.leadcampusapp;

import android.app.ProgressDialog;
import android.content.Context;
//import android.graphics.Bitmap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//import com.android.sripad.leadnew_22_6_2018.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;

public class TestActivity2 extends AppCompatActivity {


    byte[][] imageBytesJagged;
    byte[] imageBytes={0};

	ByteArrayOutputStream baos;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);



        imageBytesJagged = new byte[1][1];
         Bitmap bitmap = BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_delete);

			   baos = new ByteArrayOutputStream();
			  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			 imageBytes = baos.toByteArray();


        /*for (int i = 0; i < imageBytesJagged.length; i++) {
            for (int j = 0; j < imageBytesJagged[i].length; j++) {
                imageBytesJagged[i][j] = ;
            }
*/

        /*for(int i=0;i<3;i++){
            byte[] data = baos.toByteArray();
            imageBytesJagged[i] = data;
        }*/


            Log.e("Imagebyte[]",""+imageBytes);


        AsyncCallWS_currentevents task = new AsyncCallWS_currentevents(TestActivity2.this);
        task.execute();

    }


	private class AsyncCallWS_currentevents extends AsyncTask<String, Void, Void> {
		ProgressDialog dialog;
		Context context;

		protected void onPreExecute() {
			//  Log.i(TAG, "onPreExecute---tab2");
			dialog.setMessage("Please wait,Updating Details loading...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();

		}

		@Override
		protected void onProgressUpdate(Void... values) {
			//Log.i(TAG, "onProgressUpdate---tab2");
		}


		@Override
		protected Void doInBackground(String... params) {
			Log.i("df", "doInBackground");

			CurrentEvents();  //
			return null;
		}

		public AsyncCallWS_currentevents(TestActivity2 activity) {
			context = activity;
			dialog = new ProgressDialog(activity);
		}

		@Override
		protected void onPostExecute(Void result) {

       /* if ((this.dialog != null) && this.dialog.isShowing()) {
            dialog.dismiss();

        }*/

			dialog.dismiss();
		}

	}


	public void CurrentEvents()
	{



		String URL=Class_URL.URL_Projects.toString().trim();
		String METHOD_NAME = "UpdatePorjectCompletionwithBytes";
		String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/UpdatePorjectCompletionwithBytes";


		//Toast.makeText(getActivity(),"test image",Toast.LENGTH_SHORT).show();


		for(int i=0;i<3;i++){
			byte[] data = baos.toByteArray();
			imageBytesJagged[i] = data;
		}


		try {
			// String  versioncode = this.getPackageManager()
			//        .getPackageInfo(this.getPackageName(), 0).versionName;
			SoapObject request = new SoapObject(Namespace, METHOD_NAME);

			request.addProperty("RegistrationId", 58);
			request.addProperty("leadId", "mg06");
			request.addProperty("ProjectId", 242);
			request.addProperty("Placeofimplement", "testing");
            request.addProperty("FundsRaised", 100);
            request.addProperty("Challenge", "testing");
            request.addProperty("Learning", "testing");
            request.addProperty("AsAStory", "testing");
            request.addProperty("imgs", imageBytesJagged);

            request.addProperty("docFile", null);
            request.addProperty("extension", null);


            /*request.addProperty("docFile", imageBytes);
			request.addProperty("doccount", 0);*/



            /*<RegistrationId>long</RegistrationId>
      <leadId>string</leadId>
      <ProjectId>long</ProjectId>
      <Placeofimplement>string</Placeofimplement>
      <FundsRaised>float</FundsRaised>
      <Challenge>string</Challenge>
      <Learning>string</Learning>
      <AsAStory>string</AsAStory>
      <imgs>
        <base64Binary>base64Binary</base64Binary>
        <base64Binary>base64Binary</base64Binary>
      </imgs>
      <docFile>base64Binary</docFile>
      <extension>string</extension>
            */




			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			new MarshalBase64().register(envelope);
			envelope.dotNet = true;
			//Set output SOAP object
			envelope.setOutputSoapObject(request);
			//Create HTTP call object
			HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

			try {
				androidHttpTransport.call(SOAPACTION, envelope);
				SoapObject response = (SoapObject) envelope.getResponse();
				//Log.e("currentevents response", response.toString());



			} catch (Throwable t) {
				//Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
				//    Toast.LENGTH_LONG).show();
				Log.e("request fail", "> " + t.getMessage());
			}
		} catch (Throwable t) {
			Log.e("UnRegister  Error", "> " + t.getMessage());

		}

	}//end of newsandEvents











}//end of class
