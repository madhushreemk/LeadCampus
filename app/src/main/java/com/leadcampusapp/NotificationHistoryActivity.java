package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.leadcampusapp.module.NotificationActivityModel;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class NotificationHistoryActivity extends AppCompatActivity {

    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};


    Class_URL  config_obj= new Class_URL();
    public static final String  PREFBook_LoginTrack= "prefbook_logintrack";
    public static final String PrefID_WhereToGo = "prefid_wheretogo"; //
    SharedPreferences shardprefLoginTrack_obj;
    SharedPreferences.Editor editor_LoginTrack;

    private Context context;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    private SharedPreferences shardpref_S_obj;
    private String str_leadId;
    private ArrayList<NotificationActivityModel> originalList = null;
    private ArrayList<NotificationActivityModel> notifList;
    private NotificationCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_history);

        context = this;
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);

        notifList = new ArrayList<NotificationActivityModel>();

        shardpref_S_obj=getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.d("str_leadId:",str_leadId);

       // ArrayAdapter adapter = new ArrayAdapter<String>(this,
         //       R.layout.notification_list, mobileArray);
       // AnimateBell();
        ListView listView = (ListView) findViewById(R.id.mobile_list);
       // listView.setAdapter(adapter);
       // listView.setAdapter(new NotificationCustomAdapter(this, prgmNameList,prgmImages));
        adapter = new NotificationCustomAdapter(this,notifList);
        listView.setAdapter(adapter);

       GetNotificationDetails getNotificationDetails = new GetNotificationDetails(this);
        getNotificationDetails.execute();
    }

   /* public void AnimateBell() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shakeanimation);
        ImageView imgBell= (ImageView) findViewById(R.id.notification);
        imgBell.setImageResource(R.drawable.baseline_notifications_white_24);
        imgBell.setAnimation(shake);
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem action_request = menu.findItem(R.id.action_request);
        action_request.setVisible(false);
        MenuItem action_feedback = menu.findItem(R.id.action_feedback);
        action_feedback.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_editProfile) {
            Intent itthomeToEditProfile = new Intent(NotificationHistoryActivity.this ,EditProfileActivity.class);
            startActivity(itthomeToEditProfile);
            return true;
        }


        if (id == R.id.action_logout) {

          /*  editor_LoginTrack = shardprefLoginTrack_obj.edit();
            editor_LoginTrack.putString(PrefID_WhereToGo, config_obj.packagename+"LoginActivity");
            editor_LoginTrack.commit();*/

            //deleteCache(context);

            Intent itthomeToLogin = new Intent(NotificationHistoryActivity.this ,LoginActivity.class);
            startActivity(itthomeToLogin);
            return true;
        }

        /*if (id == R.id.notification) {
            Intent itthomeToNotification = new Intent(NotificationHistoryActivity.this ,NotificationHistoryActivity.class);
            startActivity(itthomeToNotification);
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public class GetNotificationDetails extends AsyncTask<Void, Void, SoapObject> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;

        GetNotificationDetails (Context ctx){
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
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            notifList.clear();
            if (result != null) {

                SoapPrimitive S_Notification_Type, S_Notification_Message, S_Notification_Date, S_ProjectStatus, S_Rating, S_ProjectId, S_Status;
                Object O_Notification_Type, O_Notification_Message, O_Notification_Date, O_ProjectStatus, O_Rating, O_ProjectId, O_Status;
                String str_Notification_Type = null, str_Notification_Message = null, str_Notification_Date = null, str_ProjectStatus = null, str_Rating = null, str_projectId = null, str_Status = null;

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    Log.d("Result", list.toString());

                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {

                        O_Notification_Type = list.getProperty("Notification_Type");
                        if (!O_Notification_Type.toString().equals("anyType{}") && !O_Notification_Type.toString().equals(null)) {
                            S_Notification_Type = (SoapPrimitive) list.getProperty("Notification_Type");
                            Log.d("Notification_Type", S_Notification_Type.toString());
                            str_Notification_Type = S_Notification_Type.toString();
                        }

                        O_Notification_Message = list.getProperty("Notification_Message");
                        if (!O_Notification_Message.toString().equals("anyType{}") && !O_Notification_Message.toString().equals(null)) {
                            S_Notification_Message = (SoapPrimitive) list.getProperty("Notification_Message");
                            Log.d("Notification_Message", S_Notification_Message.toString());
                            str_Notification_Message = S_Notification_Message.toString();
                        }

                        O_Notification_Date = list.getProperty("Notification_Date");
                        if (!O_Notification_Date.toString().equals("anyType{}") && !O_Notification_Date.toString().equals(null)) {
                            S_Notification_Date = (SoapPrimitive) list.getProperty("Notification_Date");
                            Log.d("Notification_Date", S_Notification_Date.toString());
                            str_Notification_Date = S_Notification_Date.toString();
                        }

                        NotificationActivityModel item;

                        item = new NotificationActivityModel(str_Notification_Type, str_Notification_Message, str_Notification_Date);
                        notifList.add(item);
                    }
                }




                originalList = new ArrayList<NotificationActivityModel>();
                originalList.clear();
                originalList.addAll(notifList);

                adapter.notifyDataSetChanged();

            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getProjectDtls() {
        String METHOD_NAME = "GetTopTenStudentNotificationList";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetTopTenStudentNotificationList";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("Lead_Id",str_leadId);
           // request.addProperty("PDId",mapProjectIdProject.get(selectedProject));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapcompletionxxxx",response.toString());
                return response;

            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(NotificationHistoryActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(NotificationHistoryActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
                //getActivity().finish();
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(NotificationHistoryActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }


        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(NotificationHistoryActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }
}
