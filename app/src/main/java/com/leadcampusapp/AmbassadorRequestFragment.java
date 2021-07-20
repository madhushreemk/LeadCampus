package com.leadcampusapp;

/**
 * Created by Admin on 20-07-2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.leadcampusapp.module.AmbassadorRequestList;
import com.leadcampusapp.module.AmbassadorRequestListModule;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class AmbassadorRequestFragment extends Fragment
{

    ListView list,list_head;
    ArrayList<HashMap<String, String>> mylist, mylist_title;
    ListAdapter adapter_title, adapter;
    HashMap<String, String> map1, map2;

    AmbassadorRequestListModule ambassadorRequestListObj;
    ArrayList<AmbassadorRequestListModule> ambassadorRequestListModules;
    private ProgressBar progressBar;
    AmbassadorRequestListModule[] ambassadorRequestInfosarr;
    int responseCount=0;
    int i=0;
    String str_Lead_Id;

    // Integer ManagerId;

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    SharedPreferences shardprefPM_obj;
    String str_MangerID;
    Integer MDId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.ambassadorrequest_fragment, container, false);

        list = (ListView) view.findViewById(R.id.lv_ambassadorlist);
        list_head = (ListView) view.findViewById(R.id.lv_ambassadorlisthead);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        ambassadorRequestListObj = new AmbassadorRequestListModule();

/*        ManagerId = getActivity().getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);
*/

        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);

        GetAmbassadorRequestDetails getAmbassadorRequestDetails = new GetAmbassadorRequestDetails(getActivity());
        getAmbassadorRequestDetails.execute(MDId);

        // showActivity();
        return view;
    }

    public void showActivity() {

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/
        map1 = new HashMap<String, String>();

        // map1.put("slno", "Lead Id");
        map1.put("one", " LEAD Id");
        map1.put("two", "Student Name");
        map1.put("three","Student Type");
      /*  map1.put("three","Applied");
        map1.put("four","Disperse Amount");
        map1.put("five","Balance Amount");*/
        mylist_title.add(map1);


        try {
            adapter_title = new SimpleAdapter(getContext(), mylist_title, R.layout.pmmasterrequest_list,
                    new String[]{ "one", "two","three"}, new int[]{
                    R.id.LeadId, R.id.StudentName, R.id.StudentType});
            list_head.setAdapter(adapter_title);
        } catch (Exception e) {

        }

        /********************************************************/


        for (int i = 0; i < AmbassadorRequestList.listview_arr.size(); i++) {

            map2 = new HashMap<String, String>();
            map2.put("one", AmbassadorRequestList.listview_arr.get(i).getLead_Id());
            map2.put("two", AmbassadorRequestList.listview_arr.get(i).getStudentName());
            map2.put("three", AmbassadorRequestList.listview_arr.get(i).getStudent_Type());
            // map2.put("four", ApprovedProjectList.listview_arr.get(i).getDisperse_amount());
            //map2.put("five", ApprovedProjectList.listview_arr.get(i).getBalance_amount());
            mylist.add(map2);

        /*    if(ApprovedProjectList.listview_arr.get(i).getApproved_amount().equals("0")){
                list.setBackgroundColor(Color.GREEN);
            }*/
        }

        try {
            adapter = new SimpleAdapter(getContext(), mylist, R.layout.pmmasterrequest_list,
                    new String[]{"one", "two","three"}, new int[]{
                    R.id.LeadId, R.id.StudentName, R.id.StudentType});
            list.setAdapter(adapter);
        } catch (Exception e) {

        }
      /*  if (ApprovedProjectList.listview_arr.get(i).getApproved_amount().equals("0")) {
            list.setBackgroundColor(Color.YELLOW);
        }*/

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Object o = list.getItemAtPosition(position);

                str_Lead_Id=AmbassadorRequestList.listview_arr.get(position).getLead_Id();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage("Are you sure, You wanted to make LEAD Ambassador");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //   Toast.makeText(getActivity(), "You clicked yes button", Toast.LENGTH_LONG).show();
                                SubmitDetails submitDetails = new SubmitDetails();
                                submitDetails.execute();
                            }
                        });
                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });

                final AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                    }
                });
                alertDialog.show();
            }

        });
    }

    public class SubmitDetails extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //      private ProgressBar progressBar;


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = AmbassadorRequest();

            //   SoapObject response = ReApplyProjectDetails();
            Log.d("Soap response issssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result != null) {
                if (result.toString().equals("Error")) {
                    Toast.makeText(getContext(), "Error occured while saving to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Sucessfully updated to Lead Ambassador", Toast.LENGTH_LONG).show();
                /*Intent ittEditProjToProjStatus = new Intent(getContext(),PMProjectDetailActivity.class);
                ittEditProjToProjStatus.putExtra("pageCount",0);
                startActivity(ittEditProjToProjStatus);*/
                    GetAmbassadorRequestDetails getAmbassadorRequestDetails = new GetAmbassadorRequestDetails(getActivity());
                    getAmbassadorRequestDetails.execute(MDId);
                }
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive AmbassadorRequest() {
        String METHOD_NAME = "Applyleadmasterandambassador";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Applyleadmasterandambassador";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            request.addProperty("Lead_Id",str_Lead_Id);
            Log.d("PDIdssssssxxxx","hi");

            request.addProperty("Student_Type","Lead Ambassador");

            Log.d("Requestisxxxxx",request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("tag","soap masterrequest update"+envelope.getResponse().toString());

                //return null;

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


    public class GetAmbassadorRequestDetails extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        GetAmbassadorRequestDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params [0];
            //SoapObject response = getStudDtls(leadid);

            //unapprovedProjectListObj = new UnapprovedProjectListModule();
            ambassadorRequestListModules = new ArrayList<AmbassadorRequestListModule>();

            String METHOD_NAME = "ApplayedLeadAmbassador";
            String NamespaceMail="http://mis.leadcampus.org/", SOAP_ACTION1="http://mis.leadcampus.org/ApplayedLeadAmbassador";//namespace+methodname

            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

                request.addProperty("ManagerId", ManagerId);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();

                    Log.d("Tag", "soap response ApplayedLeadAmbassador=" + response.toString());


                    responseCount = response.getPropertyCount();


                    Log.d("count ambassador", String.valueOf(response.getPropertyCount()));

                    AmbassadorRequestList.listview_arr.clear();
                    AmbassadorRequestListModule.listview_arr.clear();

                    if (responseCount > 0) {

                        ambassadorRequestInfosarr = new AmbassadorRequestListModule[responseCount];
                        for (i = 0; i < responseCount; i++) {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                            String Status=response_soapobj.getProperty("Status").toString();
                            Log.i("tag","Status"+Status);

                            if( Status.equals("Success")) {
                                ambassadorRequestListObj.setStudentName(response_soapobj.getProperty("StudentName").toString());
                                ambassadorRequestListObj.setLead_Id(response_soapobj.getProperty("Lead_Id").toString());
                                ambassadorRequestListObj.setIsApply_MasterLeader(response_soapobj.getProperty("isApply_MasterLeader").toString());
                                ambassadorRequestListObj.setIsApply_LeadAmbassador(response_soapobj.getProperty("isApply_LeadAmbassador").toString());
                                ambassadorRequestListObj.setStudent_Type(response_soapobj.getProperty("Student_Type").toString());

                                //   approvedProjectListObj.setBalance_amount(response_soapobj.getProperty("Amount").toString());
                                ambassadorRequestListModules.add(ambassadorRequestListObj);

                                Log.d("tag", "ambassadorRequestListModules.get(i)=" + ambassadorRequestListModules.get(i).getStudentName());
                                Log.d("tag", "ambassadorRequestListModules=" + ambassadorRequestListModules.size());
                                Log.d("tag", "ambassadorRequestListModules=" + ambassadorRequestListObj.getStudentName());

                                String StudentName = response_soapobj.getProperty("StudentName").toString();
                                String Lead_Id = response_soapobj.getProperty("Lead_Id").toString();
                                String isApply_MasterLeader = response_soapobj.getProperty("isApply_MasterLeader").toString();
                                String isApply_LeadAmbassador = response_soapobj.getProperty("isApply_LeadAmbassador").toString();
                                String Student_Type = response_soapobj.getProperty("Student_Type").toString();


                                ambassadorRequestInfosarr[i] = ambassadorRequestListObj;

                                AmbassadorRequestList.listview_arr.add(new AmbassadorRequestList(Lead_Id, StudentName, isApply_MasterLeader, isApply_LeadAmbassador, Student_Type));

                                Log.i("Tag", "AmbassadorRequestList.size=" + AmbassadorRequestList.listview_arr.size());
                            }else{
                                return null;
                            }
                        }
                    }
                    return response;

                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());
                }
            }catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
            }

            return null;
        }

        @Override
        protected void onPreExecute() {

            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            progressBar.setVisibility(View.GONE);

        /*    if(result==null){
                Toast.makeText(getActivity(),"There is no student applied for LEAD Ambassador ",Toast.LENGTH_LONG).show();
            }*/
            if(result!= null) {
                showActivity();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}//end of fragment class
