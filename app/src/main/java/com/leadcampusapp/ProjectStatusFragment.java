package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import java.util.ArrayList;
import java.util.Locale;

//public class ProjectStatusFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
public class ProjectStatusFragment extends Fragment{

    private ArrayList<ProjectStatusActivityModel2> projList;
    private View view;
    private boolean isCompletedflag=false;
    private int projectStatusIcon;
    private ProjectStatusAdapter adapter;

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

    private EditText etSearch;

    private ListView lview;
    private ArrayList<ProjectStatusActivityModel2> originalList = null;

    private int countProposed=0;
    private int countReapply=0;
    private int countRequestForCompletion=0;
    private int countApproved=0;
    private int countRejected=0;
    private int countCompleted=0;

    private Button btn_generateReport;

    private SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.projectstatus_fragment, container, false);

        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.d("str_leadId:",str_leadId);
        //Toast.makeText(getActivity(),"LeadId: "+str_leadId,Toast.LENGTH_LONG).show();

        shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_RegistrationId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        Log.d("str_RegistrationId:",str_RegistrationId);

     /*   swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/




        initializeViews();



       /* btn_generateReport = (Button) view.findViewById(R.id.btn_generateReport);

        btn_generateReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ittPSToPSPieChart = new Intent(getActivity(),ProjectStatusReport.class);
                ittPSToPSPieChart.putExtra("Pending",countProposed);
                ittPSToPSPieChart.putExtra("Reapply",countReapply);
                ittPSToPSPieChart.putExtra("Approved",countApproved);
                ittPSToPSPieChart.putExtra("Rejected",countRejected);
                ittPSToPSPieChart.putExtra("RequestForCompletion",countRequestForCompletion);
                ittPSToPSPieChart.putExtra("Completed",countCompleted);
                startActivity(ittPSToPSPieChart);
            }
        });*/






        return view;
    }

    private void initializeViews() {
     /*   shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.d("str_leadId:",str_leadId);
        //Toast.makeText(getActivity(),"LeadId: "+str_leadId,Toast.LENGTH_LONG).show();

        shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_RegistrationId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        Log.d("str_RegistrationId:",str_RegistrationId);*/
        //Toast.makeText(getActivity(),"RegistrationId: "+str_RegistrationId,Toast.LENGTH_LONG).show();



        projList = new ArrayList<ProjectStatusActivityModel2>();

        etSearch = (EditText) view.findViewById(R.id.etSearch);


        lview = (ListView) view.findViewById(R.id.listview);
        adapter = new ProjectStatusAdapter(getActivity(),projList);
        lview.setAdapter(adapter);
        //lview.setTextFilterEnabled(true);










        populateList();



        //adapter.notifyDataSetChanged();

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),"Item clickedsss" ,Toast.LENGTH_LONG).show();

                if(projList.get(position).getStatus()==R.drawable.pendinghome_new){
                    Intent ittPrjStatToEdtProj = new Intent(getActivity(), EditProjectActivity.class);
                    ittPrjStatToEdtProj.putExtra("projstatus","pending");
                    ittPrjStatToEdtProj.putExtra("projectId",projList.get(position).getProjectId());
                    startActivity(ittPrjStatToEdtProj);
                }

                if(projList.get(position).getStatus()==R.drawable.reapply){
                    Intent ittPrjStatToEdtProj = new Intent(getActivity(), EditProjectActivity.class);
                    ittPrjStatToEdtProj.putExtra("projstatus","reapply");
                    ittPrjStatToEdtProj.putExtra("projectId",projList.get(position).getProjectId());
                    startActivity(ittPrjStatToEdtProj);
                }
                if(projList.get(position).getStatus()==R.drawable.completed){
                    Intent ittPrjStatToEdtProj = new Intent(getActivity(), EditProjectActivity.class);
                    ittPrjStatToEdtProj.putExtra("projstatus","completed");
                    ittPrjStatToEdtProj.putExtra("projectId",projList.get(position).getProjectId());
                    startActivity(ittPrjStatToEdtProj);

                }

                if(projList.get(position).getStatus()==R.drawable.approveneww){
                    Intent ittPrjStatToEdtProj = new Intent(getActivity(), EditProjectActivity.class);
                    ittPrjStatToEdtProj.putExtra("projstatus","approved");
                    ittPrjStatToEdtProj.putExtra("projectId",projList.get(position).getProjectId());
                    startActivity(ittPrjStatToEdtProj);
                }

                if(projList.get(position).getStatus()==R.drawable.rejectnew){
                    Intent ittPrjStatToEdtProj = new Intent(getActivity(), EditProjectActivity.class);
                    ittPrjStatToEdtProj.putExtra("projstatus","rejected");
                    ittPrjStatToEdtProj.putExtra("projectId",projList.get(position).getProjectId());
                    startActivity(ittPrjStatToEdtProj);

                }

                if(projList.get(position).getStatus()==R.drawable.requestedneww){
                    Intent ittPrjStatToEdtProj = new Intent(getActivity(), EditProjectActivity.class);
                    ittPrjStatToEdtProj.putExtra("projstatus","requested");
                    ittPrjStatToEdtProj.putExtra("projectId",projList.get(position).getProjectId());
                    startActivity(ittPrjStatToEdtProj);
                }


            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }
            ;
            @Override
            public void afterTextChanged(Editable s) {
                //adapter.getFilter().filter(s.toString());
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text,originalList);
            }
        });
    }

    private void populateList() {

        ProjectStatusTask projStatTask = new ProjectStatusTask(getActivity());
        projStatTask.execute();

        /* ProjectStatusActivityModel item1, item2, item3, item4, item5;

       item1 = new ProjectStatusActivityModel("Student Awareness", "Education", "1000","800",R.drawable.approve, "200");
        projList.add(item1);

        item2 = new ProjectStatusActivityModel("Health Camp", "Medical", "1000","800" ,R.drawable.completed,"200");
        projList.add(item2);

        item3 = new ProjectStatusActivityModel("Shirts Distribution", "Social", "5000","1000",R.drawable.pending,"4000");
        projList.add(item3);

        item4 = new ProjectStatusActivityModel("Food Distribution", "Social", "6000", "4000" , R.drawable.reapply, "2000");
        projList.add(item4);*/

    }

/*    @Override
    public void onRefresh() {
        initializeViews();
        swipeLayout.setRefreshing(false);
    }*/


    public class ProjectStatusTask extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        ProjectStatusTask (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getProjectStatus();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
      /*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {



         /*   String finalResult = result.toString();
            String finals = finalResult.replace("anyType","");
            Log.d("Finals is",finals);


            SoapPrimitive status = (SoapPrimitive) result.getProperty("Status");
            //Log.d("Status is:",status.toString());

            if(status.toString().equals("Success")) {
                SoapPrimitive role = (SoapPrimitive) result.getProperty("Role");
                Log.d("Role is:", role.toString());

                if(role.toString().equals("Student")){
          *//*          Intent ittLoginToEditProfile = new Intent(LoginActivity.this,EditProfileActivity.class);
                    ittLoginToEditProfile.putExtra("leadid",leadid);
                    startActivity(ittLoginToEditProfile);*//*
                }
            }else{
                Log.d("Status is: ",status.toString());
            }*/

            if(result!=null){

            SoapPrimitive S_ApprovedAmount, S_ReleasedAmount, S_ProjectTitle, S_ProjectStatus, S_Rating,S_ProjectId,S_Status;
            Object O_ApprovedAmount, O_ReleasedAmount, O_ProjectTitle, O_ProjectStatus, O_Rating,O_ProjectId,O_Status;
            String str_ApprovedAmount = null, str_ReleasedAmount = null, str_ProjectTitle = null, str_ProjectStatus = null, str_Rating=null,str_projectId=null,str_Status=null;

            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject list = (SoapObject) result.getProperty(i);

                Log.d("DistrictResult", list.toString());

                O_Status = list.getProperty("status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) list.getProperty("status");
                    Log.d("Status:", S_Status.toString());
                    str_Status = S_Status.toString();
                }

                if (str_Status.equalsIgnoreCase("success")) {

                    O_ProjectTitle = list.getProperty("Title");
                    if (!O_ProjectTitle.toString().equals("anyType{}") && !O_ProjectTitle.toString().equals(null)) {
                        S_ProjectTitle = (SoapPrimitive) list.getProperty("Title");
                        Log.d("Titlesssssss", S_ProjectTitle.toString());
                        str_ProjectTitle = S_ProjectTitle.toString();
                    }

                    O_ApprovedAmount = list.getProperty("SanctionAmount");
                    if (!O_ApprovedAmount.toString().equals("anyType{}") && !O_ApprovedAmount.toString().equals(null)) {
                        S_ApprovedAmount = (SoapPrimitive) list.getProperty("SanctionAmount");
                        Log.d("Approved Amountssss", S_ApprovedAmount.toString());
                        str_ApprovedAmount = S_ApprovedAmount.toString();
                    }

                    O_ProjectStatus = list.getProperty("ProjectStatus");
                    if (!O_ProjectStatus.toString().equals("anyType{}") && !O_ProjectStatus.toString().equals(null)) {
                        S_ProjectStatus = (SoapPrimitive) list.getProperty("ProjectStatus");
                        Log.d("ProjectStatusss", S_ProjectStatus.toString());
                        str_ProjectStatus = S_ProjectStatus.toString();
                    }

                    O_ReleasedAmount = list.getProperty("giventotal");
                    if (!O_ReleasedAmount.toString().equals("anyType{}") && !O_ReleasedAmount.toString().equals(null)) {
                        S_ReleasedAmount = (SoapPrimitive) list.getProperty("giventotal");
                        Log.d("ReleasedAmountssss", S_ReleasedAmount.toString());
                        str_ReleasedAmount = S_ReleasedAmount.toString();
                    }

                    O_ProjectId = list.getProperty("PDId");
                    if (!O_ProjectId.toString().equals("anyType{}") && !O_ProjectId.toString().equals(null)) {
                        S_ProjectId = (SoapPrimitive) list.getProperty("PDId");
                        Log.d("ReleasedAmountssss", S_ProjectId.toString());
                        str_projectId = S_ProjectId.toString();
                    }


                    if (str_ProjectStatus.equals("Completed")) {
                        O_Rating = list.getProperty("Rating");
                        if (!O_Rating.toString().equals("anyType{}") && !O_Rating.toString().equals(null)) {
                            S_Rating = (SoapPrimitive) list.getProperty("Rating");
                            Log.d("Ratingsssss", S_Rating.toString());
                            str_Rating = S_Rating.toString();
                        }

                        isCompletedflag = true;

                        projectStatusIcon = R.drawable.completed;
                        countCompleted++;


                    } else {

                        if (str_ProjectStatus.equals("Proposed")) {
                            projectStatusIcon = R.drawable.pendinghome_new;
                            countProposed++;
                        }
                        if (str_ProjectStatus.equals("RequestForModification")) {
                            projectStatusIcon = R.drawable.reapply;
                            countReapply++;
                        }
                        if (str_ProjectStatus.equals("Approved")) {
                            projectStatusIcon = R.drawable.approveneww;
                            countApproved++;
                        }
                        if (str_ProjectStatus.equals("Draft")){
                            projectStatusIcon = R.drawable.approveneww;
                            countApproved++;
                        }
                        if (str_ProjectStatus.equals("Rejected")) {
                            projectStatusIcon = R.drawable.rejectnew;
                            countRejected++;
                        }
                        if (str_ProjectStatus.equals("RequestForCompletion")) {
                            projectStatusIcon = R.drawable.requestedneww;
                            countRequestForCompletion++;
                        }


                        isCompletedflag = false;
                        str_Rating = "0";
                    }

                    ProjectStatusActivityModel2 item;

                    item = new ProjectStatusActivityModel2(str_ProjectTitle, str_ApprovedAmount, projectStatusIcon, str_ReleasedAmount, str_Rating, isCompletedflag, str_projectId);
                    projList.add(item);
                }
                }



            originalList = new ArrayList<ProjectStatusActivityModel2>();
            originalList.addAll(projList);

            adapter.notifyDataSetChanged();
            }
            //progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getProjectStatus() {
        String METHOD_NAME = "GetprojectstatusList";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetprojectstatusList";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            request.addProperty("Lead_id",str_leadId);
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

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                // Log.d("soap response1xxxx",envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soap responseyyyyyyy",response.toString());

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
