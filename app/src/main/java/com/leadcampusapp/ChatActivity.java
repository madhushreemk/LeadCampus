package com.leadcampusapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ChatActivity extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    Class_ChatModule[] class_chatModules_arrayobj;
    ArrayList<Class_ChatModule> class_chatModulesobj=new ArrayList<>();
    String str_chat_status;
    String comments, userType, managerName, studentName,replyTime,projectStatus;

    SharedPreferences shardprefPM_obj;
    SharedPreferences.Editor editor_PM;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //


    String userId,intent_projectId,intent_projectStatus,final_projStatus,intent_userType;
    String messageText;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        context=getApplicationContext();

        intent_projectStatus = getIntent().getStringExtra("projectStatus");
        intent_projectId = getIntent().getStringExtra("projectId");
        intent_userType = getIntent().getStringExtra("userType");


        if(intent_projectStatus.equals("completed")){
           /* messageET.setVisibility(View.GONE);
            sendBtn.setVisibility(View.GONE);*/
            final_projStatus="Completed";
        }
        else if(intent_projectStatus.equals("reapply")){
            final_projStatus="Proposed";
        }
        else if(intent_projectStatus.equals("approved")){
            final_projStatus="Approved";
        }
        else if(intent_projectStatus.equals("rejected")){
            final_projStatus="Rejected";
        }
        else if(intent_projectStatus.equals("requested")){
            final_projStatus="RequestForCompletion";
        }
        else if(intent_projectStatus.equals("pending")){
            final_projStatus="Proposed";
        }

        initControls();





        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                boolean stud_manag_send;
                //if(intent_userType.equals("Student")) {
                    if (intent_userType.equals("Student")) {
                        stud_manag_send = false;
                    } else {
                        stud_manag_send = true;
                    }
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setId(Long.parseLong(userId));//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setProjectStatus(final_projStatus);
                chatMessage.setMe(stud_manag_send);
                Log.e("tag","txtProjStatus 1="+final_projStatus+"==="+chatMessage.getProjectStatus());

              //  messageET.setText("");

                displayMessage(chatMessage);

                SaveMentorMentee saveMentorMentee = new SaveMentorMentee(ChatActivity.this);
                saveMentorMentee.execute();
                messageET.setText("");

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("Me");
        //meLabel.setText("Mentor");

        if(intent_userType.equals("Student")){
            meLabel.setText("Manager");
            shardprefPM_obj=getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
            shardprefPM_obj.getString(PrefID_RegID, "").trim();
            userId = shardprefPM_obj.getString(PrefID_RegID, "").trim();
        }else {
            meLabel.setText("Student");
            shardprefPM_obj=getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
            shardprefPM_obj.getString(PrefID_PMID, "").trim();
            userId = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        }
        AsyncCallWS_GetMentorMentee asyncCallWS_getMentorMentee = new AsyncCallWS_GetMentorMentee(ChatActivity.this);
        asyncCallWS_getMentorMentee.execute();

    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ArrayList<Class_ChatModule> msg_final=new ArrayList<>();

        boolean stud_manag;
       /* for (int j=1;j<msg_final.size();j++) {
            comments = msg_final.get(j).getComments();
            userType = msg_final.get(j).getUserType();
            managerName = msg_final.get(j).getManagerName();
            studentName = msg_final.get(j).getStudentName();
            replyTime = msg_final.get(j).getReplyTime();
            projectStatus = msg_final.get(j).getProjectStatus();*/

       for (int j=0;j<class_chatModulesobj.size();j++){
           comments = class_chatModulesobj.get(j).getComments();
           userType = class_chatModulesobj.get(j).getUserType();
           managerName = class_chatModulesobj.get(j).getManagerName();
           studentName = class_chatModulesobj.get(j).getStudentName();
           replyTime = class_chatModulesobj.get(j).getReplyTime();
           projectStatus = class_chatModulesobj.get(j).getProjectStatus();

            Log.e("tag","comments="+comments+"userType="+userType);
            if(intent_userType.equals("Student")) {
                if (userType.equals("Student")) {
                    stud_manag = false;
                } else {
                    stud_manag = true;
                }
            }else{
                if (userType.equals("Student")) {
                    stud_manag = true;
                } else {
                    stud_manag = false;
                }
            }
            ChatMessage msg = new ChatMessage();
            msg.setId(j);
            msg.setMe(stud_manag);
            msg.setMessage(comments);
            msg.setDate(replyTime);
            msg.setProjectStatus(projectStatus);
            chatHistory.add(msg);
        }
/*
        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);*/

        adapter = new ChatAdapter(ChatActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

                for(int i=0; i<chatHistory.size(); i++) {
                    ChatMessage message = chatHistory.get(i);
                    displayMessage(message);
                }

    }

    private class AsyncCallWS_GetMentorMentee extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("DFTech", "doInBackground");
            //  GetAllEvents();
            GetMentorMentee();  // call of details
            return null;
        }

        public AsyncCallWS_GetMentorMentee(ChatActivity context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result)
        {

           /* if ((this.dialog != null) && this.dialog.isShowing()) {
                dialog.dismiss();

            }*/

            dialog.dismiss();
            loadDummyHistory();
          /*  if (class_contactus_arrayObj != null)
            {
                ContactUsActivity.CustomAdapter adapter = new ContactUsActivity.CustomAdapter();
                listView.setAdapter(adapter);

                int x = class_contactus_arrayObj.length;

                System.out.println("Inside the if list adapter" + x);
            } else {
                Log.d("onPostExecute", "ondutyhistoryclass_arrayObj == null");
            }
*/

            // Toast.makeText(getApplicationContext(),""+class_contactus_arrayObj[0].getEmailid1().toString(),Toast.LENGTH_LONG).show();



            // System.out.println("Reached the onPostExecute");

        }//end of onPostExecute
    }// end Async task

    public void GetMentorMentee() {

        String URL = Class_URL.URL_Manager.toString();
        String METHOD_NAME = "GetMentorMentee";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/GetMentorMentee";

        long PDId= Long.parseLong(intent_projectId);
        try {
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("PDId", PDId);

            Log.e("request", request.toString());
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
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("get mentor response", response.toString());
                int int_count = response.getPropertyCount();

                Log.e("number of rows", "" + int_count);
//Response
// anyType{vmGetMentorMentee=anyType{Comments=testing; UserType=Manager; ManagerName=Anisha Cardoza; StudentName=Mallikarjun kumbar; ReplyTime=14-08-19 02:52:24 PM; ProjectStatus=anyType{}; Status=Success; }; }

                class_chatModules_arrayobj = new Class_ChatModule[int_count];
                class_chatModulesobj.clear();
             //   class_chatModulesobj=new ArrayList<>(int_count);
                    // listitems_arraylist.clear();
                    for (int i = 0; i < int_count; i++) {
                        SoapObject list = (SoapObject) response.getProperty(i);
                        str_chat_status = (String) list.getProperty("Status").toString();
                        // <Status>Holidays not found</Status>

                        if (str_chat_status.equalsIgnoreCase("Success")){
                        SoapPrimitive Comments, UserType, ManagerName, StudentName,ReplyTime,ProjectStatus;
                        String str_ProjectStatus;
                        String approved = "";
                            Comments = (SoapPrimitive) list.getProperty("Comments");
                            UserType = (SoapPrimitive) list.getProperty("UserType");
                            ManagerName = (SoapPrimitive) list.getProperty("ManagerName");
                            StudentName = (SoapPrimitive) list.getProperty("StudentName");
                            ReplyTime = (SoapPrimitive) list.getProperty("ReplyTime");
                           // ProjectStatus=(SoapPrimitive) list.getProperty("ProjectStatus");
                        if(list.getProperty("ProjectStatus").toString().equals("anyType{}"))
                        {
                            str_ProjectStatus="anyType{}";
                        }
                        else
                        { str_ProjectStatus=(String)list.getProperty("ProjectStatus").toString(); }



                        Class_ChatModule innerObj = new Class_ChatModule();
                        innerObj.setComments(Comments.toString());
                        innerObj.setUserType(UserType.toString());
                        innerObj.setManagerName(ManagerName.toString());
                        innerObj.setStudentName(StudentName.toString());
                        innerObj.setReplyTime(ReplyTime.toString());
                        innerObj.setProjectStatus(str_ProjectStatus);


                        class_chatModules_arrayobj[i] = innerObj;
                            class_chatModulesobj.add(class_chatModules_arrayobj[i]);
                        //listitems_arraylist.add(innerObj);

                    }//End of for loop
                }
            }
            catch(Throwable t){
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("Chat fail", "> " + t.getMessage());


            }
        } catch (Throwable t) {
            Log.e("Chat Error", "> " + t.getMessage());

        }



    }//End of contactus

    public class SaveMentorMentee extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
      //  private ProgressBar progressBar;
        ProgressDialog dialog;

        SaveMentorMentee (Context ctx){
            context = ctx;
            dialog = new ProgressDialog(context);

        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = SaveMentorMentee();

            //   SoapObject response = ReApplyProjectDetails();
            Log.d("tag","Soap response approvedDetails"+response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result!=null) {
                if (result.toString().equals("Error")) {
                    Toast.makeText(getApplicationContext(), "Error occured while saving to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Comment sent successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
           dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private SoapPrimitive SaveMentorMentee() {
        String METHOD_NAME = "SaveMentorMentee";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/SaveMentorMentee";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

             long projectIds = (long) Integer.valueOf(intent_projectId);

            Log.d("PDIdssssssxxxx", intent_projectId);
            Log.d("messageText=", messageText);
            request.addProperty("PDId",projectIds);
            request.addProperty("Comments",messageText);
            request.addProperty("UserType",intent_userType);
            request.addProperty("UserId",userId);
            request.addProperty("ProjectStatus",final_projStatus);

            Log.d("Requestisxxxxx chat==",request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                return response;

            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
            }
        }catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
        }
        return null;

    }

}
