package com.leadcampusapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;


public class StudentApplyRequest_fragment extends Fragment {


    private ScrollView lLayoutFrgEmpresas;
    Context context;
    Spinner requesttype_sp, priority_sp, projectTitle_sp;
    EditText msg_et;
    Button go_bt;
    String[] priority_spinnerdropdown = {"Select","Low", "Medium", "High"};
    String str_RequestStatus, selected_requestType, selected_requestStatus;
    int requestcount, requestcount_projectTitle;
    StudentRequestClass[] array_requestclasses;
    StudentRequestClass obj_requestclass;
    ArrayList<String> arrayList_reqName = new ArrayList<String>();
    ArrayList<String> arrayList_reqId = new ArrayList<String>();
    ArrayList<String> arrayList_reqStatus = new ArrayList<String>();
    ArrayList<StudentRequestClass> arrayList_reqClass;

    ArrayList<StudentRequestClass> arrayList_reqClass2;
    String reqid, reqtype, reqstatus, selected_requestTypeID = "";

    //ProjectTitle
    StudReqProject_TitleClass project_titleClass;
    StudReqProject_TitleClass[] ProjecTitlearray;
    ArrayList<StudReqProject_TitleClass> arrayList_proTitleClass;
    ArrayList<String> arrayList_protitle = new ArrayList<String>();
    ArrayList<String> arrayList_proId = new ArrayList<String>();
    ArrayList<String> arrayList_proStatus = new ArrayList<String>();
    String selected_priority="",internet_issue = "empty",str_pdid, str_protitle, str_prostatus, selected_protitle, selected_prodid;
    TextView projectTitleLabel_tv;

    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    SoapPrimitive response_submit;


    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_SCellNo = "prefid_scellno"; //

    private SharedPreferences shardpref_S_obj;

    private String str_leadId,str_RegistrationId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        lLayoutFrgEmpresas = (ScrollView) inflater.inflate(R.layout.fragment_applyrequest, container, false);

        context = lLayoutFrgEmpresas.getContext();
        arrayList_reqClass = new ArrayList<StudentRequestClass>();
        arrayList_proTitleClass = new ArrayList<StudReqProject_TitleClass>();
        requesttype_sp = (Spinner) lLayoutFrgEmpresas.findViewById(R.id.requesttype_SP);
        priority_sp = (Spinner) lLayoutFrgEmpresas.findViewById(R.id.priority_SP);
        projectTitle_sp = (Spinner) lLayoutFrgEmpresas.findViewById(R.id.projectTitle_SP);
        msg_et = (EditText) lLayoutFrgEmpresas.findViewById(R.id.msg_ET);
        go_bt = (Button) lLayoutFrgEmpresas.findViewById(R.id.go_BT);
        projectTitleLabel_tv = (TextView) lLayoutFrgEmpresas.findViewById(R.id.projectTitleLabel_TV);

        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();

        internetDectector = new Class_InternetDectector(getActivity());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent) {
            Requestdropdown_AsyncTask task1 = new Requestdropdown_AsyncTask(context);
            task1.execute();
        } else {
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_LONG).show();
        }

        ArrayAdapter<String> priority_adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.simple_spinner_item, priority_spinnerdropdown);
        priority_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priority_sp.setAdapter(priority_adapter);

        priority_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_priority = priority_sp.getSelectedItem().toString();
                Log.i("selected_priority", " : " + selected_priority);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        requesttype_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                // TODO Auto-generated method stub


                selected_requestType = requesttype_sp.getSelectedItem().toString();

               // selected_requestTypeID = arrayList_reqClass.get(position).getRequestid();

                selected_requestTypeID = arrayList_reqId.get(position);



                Log.i("selected_requestType", " : " + selected_requestType);
                Log.i("selected_requestTypeID", " : " + selected_requestTypeID);

                if (selected_requestTypeID.equals("") || selected_requestTypeID.equals("null"))
                {
                    System.out.println("selected_requestTypeID..null.." + selected_requestTypeID);

                } else {
                    if (selected_requestTypeID.equals("1"))
                    {
                        System.out.println("Entered selected_requestTypeID=1");
                        projectTitle_sp.setVisibility(View.VISIBLE);
                        projectTitleLabel_tv.setVisibility(View.VISIBLE);
                    } else {
                        projectTitle_sp.setVisibility(View.GONE);
                        projectTitleLabel_tv.setVisibility(View.GONE);
                    }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


        projectTitle_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub



                selected_protitle = projectTitle_sp.getSelectedItem().toString();
                selected_prodid = arrayList_proTitleClass.get(position).getPdid();



                Log.i("selected_protitle", " : " + selected_protitle);
                Log.i("selected_prodid", " : " + selected_prodid);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });


        go_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selected_priority = priority_sp.getSelectedItem().toString();
                selected_requestType = requesttype_sp.getSelectedItem().toString();

                if (Validation()) {

                    internetDectector = new Class_InternetDectector(getActivity());
                    isInternetPresent = internetDectector.isConnectingToInternet();

                    if (isInternetPresent) {
                        AsyncCall_submit task = new AsyncCall_submit(context);
                        task.execute();
                    } else {
                        Toast.makeText(getActivity(), "No internet", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
        return lLayoutFrgEmpresas;


    }


    private class Requestdropdown_AsyncTask extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        @Override
        protected void onPreExecute() {
            Log.i("TAG", "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.i("", "onProgressUpdate---tab2");
        }

        public Requestdropdown_AsyncTask(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("TAG", "doInBackground---tab5");

            RequestSummary();


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("TAG", "onPostExecute---tab5");

            if (dialog.isShowing()) {
                dialog.dismiss();


                if (requestcount <= 0)
                {
                    Toast.makeText(context, "No Request Types", Toast.LENGTH_SHORT).show();
                } else
                    {
                    System.out.println("selected_requestTypeID..null.." + selected_requestTypeID);

                    Log.e("tag", "arrayList_reqClass.size: " + arrayList_reqClass.size());








                    for (int k = 0; k < arrayList_reqClass.size(); k++)
                    {
                        if(k==0)
                        {
                            arrayList_reqId.add("-1");
                            arrayList_reqName.add("Select");
                            arrayList_reqStatus.add("Sucess");

                            arrayList_reqId.add(array_requestclasses[k].requestid);
                            arrayList_reqName.add(array_requestclasses[k].requestname);
                            arrayList_reqStatus.add(array_requestclasses[k].requeststatus);

                        }
                        else {
                            arrayList_reqId.add(array_requestclasses[k].requestid);
                            arrayList_reqName.add(array_requestclasses[k].requestname);
                            arrayList_reqStatus.add(array_requestclasses[k].requeststatus);

                            Log.e("tag", "arrayList_reqClass=" + arrayList_reqClass.get(k).getRequestname());

                        }




                    }
                    ArrayAdapter dataAdapter = new ArrayAdapter(context, R.layout.simple_spinner_item, arrayList_reqName);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    requesttype_sp.setAdapter(dataAdapter);


                    if (requestcount_projectTitle > 0)
                    {

                        // projectTitle_sp.setVisibility(View.VISIBLE);
                        for (int k = 0; k < arrayList_proTitleClass.size(); k++) {
                            arrayList_proId.add(ProjecTitlearray[k].pdid);
                            arrayList_protitle.add(ProjecTitlearray[k].proTitle);
                            arrayList_proStatus.add(ProjecTitlearray[k].proStatus);
                            Log.e("tag", "arrayList_proTitleClass=" + arrayList_proTitleClass.get(k).getProTitle());

                        }
                        ArrayAdapter dataAdapter1 = new ArrayAdapter(context, R.layout.simple_spinner_item, arrayList_protitle);
                        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        projectTitle_sp.setAdapter(dataAdapter1);

                    }
                }
            }


//                if (requestcount <= 0) {
//                    Toast.makeText(context, "No Request Types", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    if(selected_requestTypeID.equals("") || selected_requestTypeID.equals("null")) {
//                        System.out.println("selected_requestTypeID..null.." + selected_requestTypeID);
//
//                        Log.e("tag", "arrayList_reqClass.size" + arrayList_reqClass.size());
//
//                        for (int k = 0; k < arrayList_reqClass.size(); k++) {
//                            arrayList_reqId.add(array_requestclasses[k].requestid);
//                            arrayList_reqName.add(array_requestclasses[k].requestname);
//                            arrayList_reqStatus.add(array_requestclasses[k].requeststatus);
//                            Log.e("tag", "arrayList_reqClass=" + arrayList_reqClass.get(k).getRequestname());
//
//                        }
//                        ArrayAdapter dataAdapter = new ArrayAdapter(context, R.layout.simple_spinner_item, arrayList_reqName);
//                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        request_sp.setAdapter(dataAdapter);
//
//
//                    }else {
//                        System.out.println("selected_requestTypeID.." + selected_requestTypeID + requestcount_projectTitle);
//                        if (selected_requestTypeID.equals("1")) {
//                            if (requestcount_projectTitle>0) {
//
//                                // projectTitle_sp.setVisibility(View.VISIBLE);
//                                for (int k = 0; k < arrayList_proTitleClass.size(); k++) {
//                                    arrayList_proId.add(ProjecTitlearray[k].pdid);
//                                    arrayList_protitle.add(ProjecTitlearray[k].proTitle);
//                                    arrayList_proStatus.add(ProjecTitlearray[k].proStatus);
//                                    Log.e("tag", "arrayList_proTitleClass=" + arrayList_proTitleClass.get(k).getProTitle());
//
//                                }
//                                ArrayAdapter dataAdapter1 = new ArrayAdapter(context, R.layout.simple_spinner_item, arrayList_protitle);
//                                dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                                projectTitle_sp.setAdapter(dataAdapter1);
//
//                            }
//                        }
//
//
//
//                    }
//                }
//


        }

    }//End

    public void RequestSummary() {


        String URL = Class_URL.URL_Projects.toString();
        String METHOD_NAME = "Get_Student_Request_Head";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/Get_Student_Request_Head";


        try {
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Lead_Id", str_leadId);//MH05102,MH08103


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.i("resp ", response.toString());

                SoapObject soapObjectReqHead = (SoapObject) response.getProperty("RequestHead");
                Log.e("soapobj", soapObjectReqHead.toString());

                requestcount = soapObjectReqHead.getPropertyCount();
                System.out.println("soapObjectReqHead.getPropertyCount().." + soapObjectReqHead.getPropertyCount());

                if (requestcount > 0)
                {
                    array_requestclasses = new StudentRequestClass[requestcount];


                    arrayList_reqClass.clear();



                    for (int j = 0; j < soapObjectReqHead.getPropertyCount(); j++)
                    {
                        SoapObject soapObject = (SoapObject) soapObjectReqHead.getProperty(j);
                        Log.e("soapobj iner", soapObject.toString());

                        StudentRequestClass inner_requestSummaryobject = new StudentRequestClass();
                        inner_requestSummaryobject.setRequestid(soapObject.getProperty("slno").toString());
                        inner_requestSummaryobject.setRequestname(soapObject.getProperty("Head_Name").toString());
                        inner_requestSummaryobject.setRequeststatus(soapObject.getProperty("Status").toString());

                       array_requestclasses[j] = inner_requestSummaryobject;

                        reqid = soapObject.getProperty("slno").toString();
                        reqtype = soapObject.getProperty("Head_Name").toString();
                        reqstatus = soapObject.getProperty("Status").toString();

                        Log.e("tag", "reqid==" + reqid + reqtype + reqstatus);
                        Log.e("class", array_requestclasses[j].getRequestid().toString());

                        obj_requestclass = new StudentRequestClass(reqid, reqtype, reqstatus);

                        arrayList_reqClass.add(obj_requestclass);

                        System.out.println("arrayList_reqClass..inside forloop." + arrayList_reqClass.get(j));

                    }
                    //Log.e("arrayList_reqClass", arrayList_reqClass.toString());

                    Log.e("array size", String.valueOf(arrayList_reqClass.size()));

                }

                //*--------------------------------------------------//
                // ProjectTitle
                SoapObject soapObjectProjectTitle = (SoapObject) response.getProperty("Projects");
                Log.e("soapObjectProjectTitle", soapObjectProjectTitle.toString());

                requestcount_projectTitle = soapObjectProjectTitle.getPropertyCount();
                System.out.println("soapObjectProjectTitle.getPropertyCount().." + soapObjectProjectTitle.getPropertyCount());


                if (requestcount_projectTitle > 0) {
                    ProjecTitlearray = new StudReqProject_TitleClass[requestcount_projectTitle];

                    for (int j = 0; j < soapObjectProjectTitle.getPropertyCount(); j++) {
                        SoapObject soapObject = (SoapObject) soapObjectProjectTitle.getProperty(j);
                        Log.e("soapobj iner1", soapObject.toString());
                        StudReqProject_TitleClass inner_requestSummaryobject = new StudReqProject_TitleClass();
                        inner_requestSummaryobject.setPdid(soapObject.getProperty("PDID").toString());
                        inner_requestSummaryobject.setProTitle(soapObject.getProperty("ProjectTitle").toString());
                        inner_requestSummaryobject.setProStatus(soapObject.getProperty("Status").toString());
                        ProjecTitlearray[j] = inner_requestSummaryobject;

                        str_pdid = soapObject.getProperty("PDID").toString();
                        str_protitle = soapObject.getProperty("ProjectTitle").toString();
                        str_prostatus = soapObject.getProperty("Status").toString();
                        Log.e("tag", "str_pdid==" + str_pdid + str_protitle + str_prostatus);
                        Log.e("class", ProjecTitlearray[j].getPdid().toString());
                        project_titleClass = new StudReqProject_TitleClass(str_pdid, str_protitle, str_prostatus);
                        arrayList_proTitleClass.add(project_titleClass);
                        System.out.println("arrayList_proTitleClass..inside forloop." + arrayList_proTitleClass.get(j));

                    }


                }
            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());
                //str_RequestStatus = t.getMessage().toString();
            }
        } catch (Throwable t) {
            Log.e("UnRegister  Error", "> " + t.getMessage());
            // str_RequestStatus = t.getMessage().toString();

        }

    }


    // Submit Async  request
    private class AsyncCall_submit  extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {

            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        @Override
        protected Void doInBackground(String... params) {
            Log.i("DF", "doInBackground");



            applyRequest();


            return null;
        }

        public AsyncCall_submit(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                System.out.println("response_submit.."+response_submit);
                if (response_submit.toString().equals("success")) {

                    Toast.makeText(context, "Applied", Toast.LENGTH_LONG).show();

                    Intent i=new Intent(getActivity(),StudentRequest.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }else{
                    Toast.makeText(context, " "+response_submit.toString(), Toast.LENGTH_LONG).show();

                }
            }

        }
    }// End of AsyncCall_submit



    public void applyRequest() {

        String URL = Class_URL.URL_Projects.toString();
        String METHOD_NAME = "Student_Submit_Request_ToManager";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/Student_Submit_Request_ToManager";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);


            if (selected_requestTypeID.equals("") || selected_requestTypeID.equals("null")) {
                System.out.println("selected_requestTypeID..null.." + selected_requestTypeID);

            } else {
                System.out.println("selected_requestTypeID...." + selected_requestTypeID);

                if(selected_requestTypeID.equals("1")) {

                    request.addProperty("Lead_Id", str_leadId);
                    request.addProperty("Request_type",selected_requestTypeID);
                    request.addProperty("Priority",selected_priority);
                    request.addProperty("PDID",selected_prodid);
                    request.addProperty("Request_message",msg_et.getText().toString());
                    request.addProperty("RequestedId", "");
                }else{
                    request.addProperty("Lead_Id", str_leadId);
                    request.addProperty("Request_type",selected_requestTypeID);
                    request.addProperty("Priority",selected_priority);
                    request.addProperty("PDID","");
                    request.addProperty("Request_message", msg_et.getText().toString());
                    request.addProperty("RequestedId","");
                }
            }

            Log.i("string value request", request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                response_submit = (SoapPrimitive) envelope.getResponse();
                Log.i("string value atsponse", response_submit.toString());

            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {

            Log.e("UnRegister Recei Error", "> " + t.getMessage());

        }

    }//End



    public boolean Validation()
    {
        boolean b_validationmessage1, b_validationmessage2,b_validation_priorityspinner,b_validation_requesttypespinner;

        b_validationmessage1=b_validationmessage2=b_validation_priorityspinner=b_validation_requesttypespinner=true;


        if (msg_et.getText().toString().length()== 0)
        {
            msg_et.setError("Empty not allowed!");
            msg_et.requestFocus();
            b_validationmessage1 = false;
        }

        if(msg_et.getText().toString().length()<=5)
        {
            msg_et.setError("Minimum character is 10");
            msg_et.requestFocus();
            b_validationmessage2 = false;
        }



        if(priority_sp.getSelectedItem().toString().equalsIgnoreCase("Select"))
        {
            Toast.makeText(getActivity(), "Select the Priority", Toast.LENGTH_LONG).show();
            b_validation_priorityspinner=false;
        }


       if( requesttype_sp.getSelectedItem().toString().equalsIgnoreCase("Select"))
        {

            Toast.makeText(getActivity(), "Select the RequestType", Toast.LENGTH_LONG).show();
            b_validation_requesttypespinner=false;
        }

        //vijay


        if (b_validationmessage1&&b_validationmessage2&&b_validation_priorityspinner
                &&b_validation_requesttypespinner) {
            return true;
        } else {
            return false;
        }

    }//end of validation



}
