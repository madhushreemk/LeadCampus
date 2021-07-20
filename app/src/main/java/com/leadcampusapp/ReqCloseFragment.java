package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

//import com.android.sripad.leadnew_22_6_2018.R;

public class ReqCloseFragment extends Fragment{

    private ArrayList<ProjectStatusActivityModel2> projList;
    private View view;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_SCellNo = "prefid_scellno"; //

    private SharedPreferences shardpref_S_obj;
    private String str_leadId,str_RegistrationId;
    private ProgressDialog progressDialog;

    ReqCloseModule reqCloseModule_item;
    ArrayList<ReqCloseModule> reqCloseModulesList;
    private ReqCloseAdapter adapter_reqClose;

    private ListView lview;

    public static final String PrefID_PMID = "prefid_pmid"; //
    public static final String  PREFBook_PM= "prefbook_pm";
    SharedPreferences shardprefPM_obj;
    public String str_MangerID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_req_open, container, false);

        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.d("str_leadId:",str_leadId);

        shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_RegistrationId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        Log.d("str_RegistrationId:",str_RegistrationId);

        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();

        Log.d("str_MangerID:",str_MangerID);
        reqCloseModulesList = new ArrayList<ReqCloseModule>();

        lview = (ListView) view.findViewById(R.id.lv_openreq);

        GetReqCloselist getReqqOpenlist = new GetReqCloselist(getActivity());
        getReqqOpenlist.execute();

        return view;
    }

    public class GetReqCloselist extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetReqCloselist (Context ctx){
            context = ctx;
            //   progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getReqCloselist();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
      /*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
           /* progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapObject result1) {

            Log.d("Resultisssss:",result1.toString());
           /* anyType{vmManagerOpenRequest=anyType{Ticket_No=13; Request_Date=26-06-2019 12:54:08 PM; Lead_Id=MH05102;
            Student_Name=Madhu Tech; MobileNo=8904674048; RequestHead_Id=4; Project_Id=0; Request_type=Funding;
            Request_Message=testing; Request_Priority=High; College_Name=Lalbhai Dalpatbhai ; Status=Success; };*/
            reqCloseModulesList.clear();
            if(result1 != null) {

                SoapPrimitive S_Ticket_No,S_Request_Date, S_Lead_Id, S_Student_Name, S_MobileNo,S_RequestHead_Id,S_Project_Id,S_Request_type,S_Request_Message,S_Request_Priority,S_Response_Message,S_College_Name,S_Status,S_Respond_Date;
                Object O_Ticket_No, O_Request_Date, O_Lead_Id, O_Student_Name, O_MobileNo, O_RequestHead_Id, O_Project_Id, O_Request_type, O_Request_Message, O_Request_Priority,O_Response_Message,O_College_Name,O_Status,O_Respond_Date;
                String str_Ticket_No = null, str_Request_Date = null, str_Lead_Id = null, str_Student_Name = null, str_MobileNo = null, str_RequestHead_Id = null,str_Project_Id = null,str_Request_type = null,str_Request_Message = null,str_Response_Message=null,str_Request_Priority = null,str_College_Name = null,str_status = null,str_Respond_Date=null;

                for (int i = 0; i < result1.getPropertyCount(); i++) {
                    SoapObject result = (SoapObject) result1.getProperty(i);

                    Log.d("soap O_Status list:",result.toString());

                    O_Status = result.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) result.getProperty("Status");
                        Log.d("S_Status:", S_Status.toString());
                        str_status = S_Status.toString();
                    }
                    if (str_status.equalsIgnoreCase("Success")) {
                        O_Ticket_No = result.getProperty("Ticket_No");
                        if (!O_Ticket_No.toString().equals("anyType{}") && !O_Ticket_No.toString().equals(null)) {
                            S_Ticket_No = (SoapPrimitive) result.getProperty("Ticket_No");
                            Log.d("S_Ticket_No:", S_Ticket_No.toString());
                            str_Ticket_No = S_Ticket_No.toString();

                            //txt_assignedSmall.setText(str_allotedS);
                        }

                        O_Request_Date = result.getProperty("Request_Date");
                        if (!O_Request_Date.toString().equals("anyType{}") && !O_Request_Date.toString().equals(null)) {
                            S_Request_Date = (SoapPrimitive) result.getProperty("Request_Date");
                            Log.d("S_AllotedM:", S_Request_Date.toString());
                            str_Request_Date = S_Request_Date.toString();

                            //txt_assignedMedium.setText(str_allotedM);
                        }

                        O_Lead_Id = result.getProperty("Lead_Id");
                        if (!O_Lead_Id.toString().equals("anyType{}") && !O_Lead_Id.toString().equals(null)) {
                            S_Lead_Id = (SoapPrimitive) result.getProperty("Lead_Id");
                            str_Lead_Id = S_Lead_Id.toString();
                            Log.d("S_Lead_Id:", str_Lead_Id);

                            //txt_assignedLarge.setText(str_allotedL);
                        }

                        O_Student_Name = result.getProperty("Student_Name");
                        if (!O_Student_Name.toString().equals("anyType{}") && !O_Student_Name.toString().equals(null)) {
                            S_Student_Name = (SoapPrimitive) result.getProperty("Student_Name");
                            Log.d("S_AllotedXL", S_Student_Name.toString());
                            str_Student_Name = S_Student_Name.toString();
                            Log.d("S_Student_Name", str_Student_Name);

                            //txt_assignedXL.setText(str_allotedXL);
                        }

                        O_MobileNo = result.getProperty("MobileNo");
                        if (!O_MobileNo.toString().equals("anyType{}") && !O_MobileNo.toString().equals(null)) {
                            S_MobileNo = (SoapPrimitive) result.getProperty("MobileNo");
                            Log.d("S_MobileNo", S_MobileNo.toString());
                            str_MobileNo = S_MobileNo.toString();
                            Log.d("str_allotedXXL", str_MobileNo);

                            //   txt_assignedXXL.setText(str_allotedXXL);
                        }


                        O_RequestHead_Id = result.getProperty("RequestHead_Id");
                        if (!O_RequestHead_Id.toString().equals("anyType{}") && !O_RequestHead_Id.toString().equals(null)) {
                            S_RequestHead_Id = (SoapPrimitive) result.getProperty("RequestHead_Id");
                            Log.d("S_RequestHead_Id:", S_RequestHead_Id.toString());
                            str_RequestHead_Id = S_RequestHead_Id.toString();

                            // txt_balSmall.setText(str_usedS);
                        }

                        O_Project_Id = result.getProperty("Project_Id");
                        if (!O_Project_Id.toString().equals("anyType{}") && !O_Project_Id.toString().equals(null)) {
                            S_Project_Id = (SoapPrimitive) result.getProperty("Project_Id");
                            Log.d("S_Project_Id:", S_Project_Id.toString());
                            str_Project_Id = S_Project_Id.toString();

                            //    txt_balMedium.setText(str_usedM);
                        }

                        O_Request_type = result.getProperty("Request_type");
                        if (!O_Request_type.toString().equals("anyType{}") && !O_Request_type.toString().equals(null)) {
                            S_Request_type = (SoapPrimitive) result.getProperty("Request_type");
                            Log.d("S_Request_type:", S_Request_type.toString());
                            str_Request_type = S_Request_type.toString();

                            //  txt_balLarge.setText(str_usedL);
                        }

                        O_Request_Message = result.getProperty("Request_Message");
                        if (!O_Request_Message.toString().equals("anyType{}") && !O_Request_Message.toString().equals(null)) {
                            S_Request_Message = (SoapPrimitive) result.getProperty("Request_Message");
                            Log.d("S_Request_Message:", S_Request_Message.toString());
                            str_Request_Message = S_Request_Message.toString();
                        }

                        O_Response_Message = result.getProperty("Response_Message");
                        if (!O_Response_Message.toString().equals("anyType{}") && !O_Response_Message.toString().equals(null)) {
                            S_Response_Message = (SoapPrimitive) result.getProperty("Response_Message");
                            Log.d("S_Response_Message:", S_Response_Message.toString());
                            str_Response_Message = S_Response_Message.toString();
                        }

                        O_Request_Priority = result.getProperty("Request_Priority");
                        if (!O_Request_Priority.toString().equals("anyType{}") && !O_Request_Priority.toString().equals(null)) {
                            S_Request_Priority = (SoapPrimitive) result.getProperty("Request_Priority");
                            Log.d("O_Request_Priority:", S_Request_Priority.toString());
                            str_Request_Priority = S_Request_Priority.toString();

                        }

                        O_College_Name = result.getProperty("College_Name");
                        if (!O_College_Name.toString().equals("anyType{}") && !O_College_Name.toString().equals(null)) {
                            S_College_Name = (SoapPrimitive) result.getProperty("College_Name");
                            Log.d("O_College_Name:", S_College_Name.toString());
                            str_College_Name = S_College_Name.toString();

                        }
                        O_Respond_Date = result.getProperty("Respond_Date");
                        if (!O_Respond_Date.toString().equals("anyType{}") && !O_Respond_Date.toString().equals(null)) {
                            S_Respond_Date = (SoapPrimitive) result.getProperty("Respond_Date");
                            Log.d("O_Respond_Date:", S_Respond_Date.toString());
                            str_Respond_Date = S_Respond_Date.toString();

                        }


                        reqCloseModule_item = new ReqCloseModule(str_Ticket_No, str_Request_Date,str_Respond_Date, str_Lead_Id, str_Student_Name, str_MobileNo, str_RequestHead_Id, str_Project_Id, str_Request_type, str_Request_Message, str_Response_Message, str_Request_Priority, str_College_Name);
                        reqCloseModulesList.add(reqCloseModule_item);

                    } else {
                        Toast.makeText(getActivity(), "There is no tshirt", Toast.LENGTH_LONG).show();
                    }
                }


            }

            adapter_reqClose = new ReqCloseAdapter(getActivity(), reqCloseModulesList);
            lview.setAdapter(adapter_reqClose);

//            progressDialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getReqCloselist() {
        String METHOD_NAME = "GetClosedRequest";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetClosedRequest";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


            request.addProperty("ManagerId",str_MangerID);
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
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("soapresponse1xxxx:",envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapresponseyyyyyyy:",response.toString());

                //return null;

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
}
