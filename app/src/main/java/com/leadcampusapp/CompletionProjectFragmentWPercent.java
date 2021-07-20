package com.leadcampusapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CompletionProjectFragmentWPercent extends Fragment
{


    private Context context;
    private String Str_loadprojectE1 ="no";
    private String Str_loadprojectE2 ="no";

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    private SharedPreferences shardpref_S_obj;

    private String str_leadId,str_RegistrationId;




    int int_responsecount;
    private int noOfobjects = 0;
    Class_ApprovedProjectList[] class_approvedProjectLists_arrayObj,class_approvedProjectLists_arrayObj1;
    private ListView listView__projectcompletionWpercent;

    Class_ApprovedProjectList class_approvedProjectLists_obj;
    private int progressStatus = 0;
    private Handler handler = new Handler();


    private int int_setvalue=0;
    String str_asyncTask_load_approvedproject_status;
    TextView currently_no_project_tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

       // context = getActivity().getBaseContext();

        final View view = inflater.inflate(R.layout.completionprojectwpercent_fragment, container, false);
        //view4 = view;

        listView__projectcompletionWpercent = (ListView) view.findViewById(R.id.listview_projectcompletionWpercent);

        currently_no_project_tv =(TextView)view.findViewById(R.id.currently_no_project_tv);

        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.d("str_leadId:",str_leadId);
        //Toast.makeText(context,"LeadId: "+str_leadId,Toast.LENGTH_LONG).show();

        str_RegistrationId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        Log.d("str_RegistrationId:",str_RegistrationId);

        context = getActivity();
        AsyncTask_LoadApprovedProject loadApprovedProject = new AsyncTask_LoadApprovedProject(context);
        loadApprovedProject.execute();



        listView__projectcompletionWpercent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String projectid = class_approvedProjectLists_arrayObj[position].getproject_id();
                String projectstatus=class_approvedProjectLists_arrayObj[position].getproject_status();
                String str_impactproject_status=class_approvedProjectLists_arrayObj[position].get_impactproject();

               // Toast.makeText(getActivity(),""+projectid.toString(),Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getActivity(), FinalProjectCompletion_Activity.class);
                i.putExtra("projectid_ValueKey", projectid.toString());
                i.putExtra("projectstatus_ValueKey", projectstatus.toString());
                i.putExtra("impact_projectstatus_ValueKey", str_impactproject_status.toString());
                startActivity(i);
                getActivity().finish();


            }
        });







        return view;


    }//end of oncreate();


    private class AsyncTask_LoadApprovedProject extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait,Approved Projects Loading...");
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

             LoadApprovedProject();  // get the state list
            return null;
        }

        public AsyncTask_LoadApprovedProject(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result)
        {

            dialog.dismiss();

            if (class_approvedProjectLists_arrayObj != null)
            {
                currently_no_project_tv.setVisibility(View.GONE);//currently_no_project_tv

                CustomAdapter adapter = new CustomAdapter();
                listView__projectcompletionWpercent.setAdapter(adapter);

                int x1 = class_approvedProjectLists_arrayObj.length;

                System.out.println("Inside the if list adapter" + x1);
            } else {
                Log.d("onPostExecute", "leavelist == null");
                currently_no_project_tv.setVisibility(View.VISIBLE);//currently_no_project_tv
            }

        }
    }//end of AsynTask_loadProject

 //CompletionGetApprovedProjectDetails
//CompletionGetApprovedProjectList

//GetProjectDraftDetails
//UpdateProjectCompletionImgCompressDraft
//UpdateProjectCompletionDocumentDraft



    public void LoadApprovedProject()
    {
        String URL=Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "CompletionGetApprovedProjectList";
        String SOAPACTION = "http://mis.leadcampus.org/CompletionGetApprovedProjectList";
        String Namespace = "http://mis.leadcampus.org/";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            // request.addProperty("currentevents", "0");

            request.addProperty("Lead_Id",str_leadId);

           // request.addProperty("Lead_Id","mh08245");
            //request.addProperty("Lead_Id","MH04606");//mh08245

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            /*<PDId>38393</PDId>
    <Title>title15</Title>
    <status>Success</status>
    <SanctionAmount>0</SanctionAmount>
    <Giventotal>0</Giventotal>*/


            try {
                androidHttpTransport.call(SOAPACTION, envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("LoadApprovedPr response", response.toString());
                int_responsecount = response.getPropertyCount();

                noOfobjects = int_responsecount;

               // <status>Success</status>

                SoapObject soapobject_response1 = (SoapObject) response.getProperty(0);
                str_asyncTask_load_approvedproject_status= (String) soapobject_response1.getProperty("status").toString(); //<status>Success</status>

                if(str_asyncTask_load_approvedproject_status.equalsIgnoreCase("Success")) {


                    class_approvedProjectLists_arrayObj = new Class_ApprovedProjectList[noOfobjects];
                    for (int i = 0; i < int_responsecount; i++)
                    {
                        SoapObject soapobject_response = (SoapObject) response.getProperty(i);
                        SoapPrimitive soapprimitive_projectid, soapprimitive_projectname, soapprimitive_status, soapprimitive_projectstatus, soapprimitive_completionprogress,soapprimitive_impactproject_status;

                        soapprimitive_projectid = (SoapPrimitive) soapobject_response.getProperty("PDId");
                        soapprimitive_projectname = (SoapPrimitive) soapobject_response.getProperty("Title");
                        soapprimitive_completionprogress = (SoapPrimitive) soapobject_response.getProperty("CompletionProgress");
                        soapprimitive_status = (SoapPrimitive) soapobject_response.getProperty("status");

                        soapprimitive_projectstatus = (SoapPrimitive) soapobject_response.getProperty("ProjectStatus");
                        //<ProjectStatus>Approved</ProjectStatus> // <ProjectStatus>Draft</ProjectStatus>

                        soapprimitive_impactproject_status = (SoapPrimitive) soapobject_response.getProperty("isImpact_Project");//<isImpact_Project>0</isImpact_Project>

                        Class_ApprovedProjectList innerObj = new Class_ApprovedProjectList();
                        if (soapprimitive_status.toString().equals("Success"))
                        {
                            //Class_ApprovedProjectList innerObj = new Class_ApprovedProjectList();
                            innerObj.setproject_id(soapprimitive_projectid.toString());
                            innerObj.setproject_name(soapprimitive_projectname.toString());
                            innerObj.setproject_status(soapprimitive_projectstatus.toString());
                            innerObj.setproject_precentcount(soapprimitive_completionprogress.toString());
                            innerObj.set_webservicestatus(soapprimitive_status.toString());
                            innerObj.set_impactproject(soapprimitive_impactproject_status.toString());

                            class_approvedProjectLists_arrayObj[i] = innerObj;
                        } else {
                            innerObj = null;
                        }
                    }

                }

        } catch (Throwable t) {
            //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
            //    Toast.LENGTH_LONG).show();
            Str_loadprojectE1 ="yes";
            Log.e("request fail", "> " + t.getMessage());
        }

        } catch (Throwable t) {
            Str_loadprojectE2="yes";
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }



    }// end of class

  // List View calling

        //Holder for customAdapter
        private class Holder
        {
            TextView holder_projecttitle_tv;
            TextView holder_project_id_tv;
            TextView holder_nopastevents_tv;
            TextView holder_pastevent_imageurl_tv;
            ProgressBar holder_progressbar_pg;
            TextView holder_no_tv;
            TextView holder_project_completion_count_tv;
            ImageView holder_projectcompletion_iv;
            TextView holder_projectstatus_completion_tv;
        }
        //End of Holder




        public class CustomAdapter extends BaseAdapter
        {


            public CustomAdapter() {

                super();
                Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
            }

            @Override
            public int getCount() {

                String x = Integer.toString(class_approvedProjectLists_arrayObj.length);
                Log.d("class_arrayObj.length", x);
                return class_approvedProjectLists_arrayObj.length;

            }

            @Override
            public Object getItem(int position) {
                String x = Integer.toString(position);
                System.out.println("getItem position" + x);
                Log.d("getItem position", "x");
                return class_approvedProjectLists_arrayObj[position];
            }

            @Override
            public long getItemId(int position) {
                String x = Integer.toString(position);
                System.out.println("getItemId position" + x);
                Log.d("getItemId position", x);
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {

                final Holder holder;

                Log.d("CustomAdapter", "position: " + position);
                // holder = new Holder();
                if (convertView == null) {
                    holder = new Holder();
                    convertView = LayoutInflater.from(getActivity()).inflate(R.layout.row_item_cprojectwpercent, parent, false);


                    //Typeface facebold = Typeface.createFromAsset(getResources().getAssets(), "fonts/centurygothic.ttf");
                    //durationlabel_TV

                    holder.holder_projecttitle_tv=(TextView) convertView.findViewById(R.id.projecttitle_name_holder_TV);
                    holder.holder_progressbar_pg =(ProgressBar)convertView.findViewById(R.id.projectcompletion_progressbar_holder_pg);
                    holder.holder_project_id_tv=(TextView)convertView.findViewById(R.id.project_id_holder_TV);
                    holder.holder_no_tv =(TextView)convertView.findViewById(R.id.projectcompletion_holder_no_tv);

                    holder.holder_project_completion_count_tv =(TextView)convertView.findViewById(R.id.project_completion_count_holder_TV);
                    //project_completion_count_holder_TV

                    holder.holder_projectcompletion_iv=(ImageView)convertView.findViewById(R.id.projstatus_completion_holder_iv);
                    holder.holder_projectstatus_completion_tv=(TextView)convertView.findViewById(R.id.projectstatus_completion_holder_tv);


                    holder.holder_progressbar_pg.setMax(100);

                    Log.d("Inside If convertView", "Inside If convertView");

                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                    Log.d("Inside else convertView", "Inside else convertView");
                }



                class_approvedProjectLists_obj =(Class_ApprovedProjectList) getItem(position);

                // Class_CurrentEvents[] details =(Class_CurrentEvents) getItem(position);



                if(class_approvedProjectLists_arrayObj!=null)
                {

                    holder.holder_projecttitle_tv.setText(class_approvedProjectLists_obj.getproject_name());
                    holder.holder_no_tv.setText("");
                    holder.holder_project_id_tv.setText(class_approvedProjectLists_obj.getproject_id());
                    holder.holder_project_completion_count_tv.setText(class_approvedProjectLists_obj.getproject_precentCount());

                    holder.holder_projectstatus_completion_tv.setText(class_approvedProjectLists_obj.getproject_status().toString());
                    String str_projectstatus_completion=holder.holder_projectstatus_completion_tv.getText().toString();


                    String str_setvalue=holder.holder_project_completion_count_tv.getText().toString();
                    Log.e("value",str_setvalue);

                    int_setvalue= Integer.parseInt(str_setvalue);
                    Log.e("value of Int", String.valueOf(int_setvalue));



                    if(str_projectstatus_completion.trim().length()==0||
                            str_projectstatus_completion.trim().equals(null)||
                            str_projectstatus_completion.isEmpty())
                    {
                        str_projectstatus_completion="empty";
                    }


                    if(str_projectstatus_completion.equalsIgnoreCase("RequestForCompletion"))
                    {

                        holder.holder_no_tv.setVisibility(View.GONE);
                        holder.holder_progressbar_pg.setVisibility(View.GONE);
                        holder.holder_projectcompletion_iv.setVisibility(View.VISIBLE);

                    }

                    else
                        {

                        if (int_setvalue > 0)
                        {

                            holder.holder_no_tv.setVisibility(View.VISIBLE);
                            holder.holder_progressbar_pg.setVisibility(View.VISIBLE);
                            holder.holder_projectcompletion_iv.setVisibility(View.GONE);

                            holder.holder_no_tv.setText(holder.holder_project_completion_count_tv.getText().toString() + "");

                            holder.holder_progressbar_pg.setProgress(Integer.parseInt(holder.holder_project_completion_count_tv.getText().toString()));



                      /*  //progressStatus = 0;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (progressStatus < int_setvalue) {
                                    // Update the progress status
                                    progressStatus += 1;

                                    // Try to sleep the thread for 20 milliseconds
                                    try {
                                        Thread.sleep(80);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    // Update the progress bar
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Show the progress on TextView
                                            holder.holder_no_tv.setText(progressStatus + "");
                                            holder.holder_progressbar_pg.setProgress(progressStatus); // 50 default progress value for the progress bar
                                        }
                                    });
                                }
                            }
                        }).start(); // Start the operation

                        *//*progressStatus = 0;
                        int_setvalue = 0;*/

                        } else
                       {
                           holder.holder_no_tv.setVisibility(View.VISIBLE);
                           holder.holder_progressbar_pg.setVisibility(View.VISIBLE);
                           holder.holder_projectcompletion_iv.setVisibility(View.GONE);

                            holder.holder_no_tv.setText(0 + "");
                            holder.holder_progressbar_pg.setProgress(0);
                        }

                    }


                }

                return convertView;
            }

        }//End of CustomAdapter










    }//end of class