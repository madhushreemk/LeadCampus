package com.leadcampusapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Vector;

public class Student_history extends Fragment {
    LinearLayout Request_history_LL, noRequesthistory_LL;
    LinearLayout linearLayoutmain;
    private LinearLayout linearLayout;
    ListView listView;
    Context context;
    Resources resource;
    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;
    StudentRequestHisoryDetails[] studentRequestHisoryDetailslist;
    StudentRequestHisoryDetails studentRequestHisoryDetails;
    int count1;
    SoapPrimitive response_delete;
    String internet_issue = "empty",str_requestedid,str_pdid;
    String str_requestid, str_requestdate,str_requestmsg,str_requestpriority,
            str_requeststatus,str_requestheadid,str_requestType,str_projid,
            str_projTitle,str_resdate,str_resmsg,str_status;

    CustomAdapter adapter;

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
                             Bundle savedInstanceState) {


        linearLayout = (LinearLayout) inflater.inflate(R.layout.stud_req_history, container, false);

        linearLayoutmain = (LinearLayout) linearLayout.findViewById(R.id.RequestHistorymain_LL);

        adapter = new CustomAdapter();
        listView = (ListView) linearLayout.findViewById(R.id.customlistview_requesthistory);
        context = linearLayout.getContext();
        resource = context.getResources();


        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();

        internetDectector = new Class_InternetDectector(getActivity());
        isInternetPresent = internetDectector.isConnectingToInternet();

        if (isInternetPresent) {

            AsyncCall_history task = new AsyncCall_history(context);
            task.execute();
        } else {
            Toast.makeText(getActivity(), "No internet", Toast.LENGTH_LONG).show();

        }

        return linearLayout;

    }

    private class AsyncCall_history extends AsyncTask<String, Void, Void> {
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
            Log.i("Ashraf", "doInBackground");
            Reqesthistory_details();
            return null;
        }

        public AsyncCall_history(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result) {


            dialog.dismiss();

            if (studentRequestHisoryDetailslist != null) {
               // CustomAdapter adapter = new CustomAdapter();
                listView.setAdapter(adapter);

                int x = studentRequestHisoryDetailslist.length;


            } else {
                Log.d("onPostExecute", "list == null");
            }

            System.out.println("Reached the onPostExecute");

        }//end of onPostExecute
    }// end Async task



    public void Reqesthistory_details() {
        Vector<SoapObject> result1 = null;

        String URL = Class_URL.URL_Projects.toString();
        String METHOD_NAME = "Get_Student_Request_History";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/Get_Student_Request_History";

        try {
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            request.addProperty("Lead_Id", str_leadId);//MH08103,MI00001

            Log.i("getHistoryDetails", request.toString());


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {



                androidHttpTransport.call(SOAPACTION, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.i("value at response", response.toString());
                count1 = response.getPropertyCount();  // number of count in array in response 6,0-5,5

                Log.i("number of rows", "" + count1);
                //studentRequestHisoryDetailslist=new StudentRequestHisoryDetails[0];
               // studentRequestHisoryDetailslist=null;

                studentRequestHisoryDetailslist = new StudentRequestHisoryDetails[count1];

                for (int i = 0; i < count1; i++) {
                    System.out.println("abcd");


                    SoapObject list = (SoapObject) response.getProperty(i);
//                    String str_requestid, str_requestdate,str_requestmsg,str_requestpriority,
//                            str_requeststatus,str_requestheadid,str_requestType,str_projid,
//                            str_projTitle,str_resdate,str_resmsg,str_status;

                    System.out.println("list..........." + list);

                    str_requestid = list.getProperty("request_Id").toString();
                    str_requestdate = list.getProperty("Request_Date").toString();
                    str_requestmsg = list.getProperty("Request_Message").toString();
                    str_requestpriority = list.getProperty("Request_Priority").toString();
                    str_requeststatus = list.getProperty("Request_Status").toString();
                    str_requestheadid = list.getProperty("HeadId").toString();
                    str_requestType = list.getProperty("HeadName").toString();
                    str_projid = list.getProperty("Project_Id").toString();
                    str_projTitle = list.getProperty("Project_Title").toString();
                    str_resdate = list.getProperty("Response_Date").toString();
                    str_resmsg = list.getProperty("Response_Message").toString();
                    str_status = list.getProperty("Status").toString();



                    StudentRequestHisoryDetails innercompoffobj = new StudentRequestHisoryDetails();
                    Log.i("value at name premitive", str_status);
                    innercompoffobj.setPro_id(str_projid);
                    innercompoffobj.setPro_title(str_projTitle);
                    innercompoffobj.setReq_date(str_requestdate);
                    innercompoffobj.setReq_msg(str_requestmsg);
                    innercompoffobj.setReq_priority(str_requestpriority);
                    innercompoffobj.setReq_status(str_requeststatus);
                    innercompoffobj.setReq_type(str_requestType);
                    innercompoffobj.setResp_date(str_resdate);
                    innercompoffobj.setResp_msg(str_resmsg);
                    innercompoffobj.setStatus(str_status);
                    innercompoffobj.setHeadid(str_requestheadid);
                    innercompoffobj.setReq_id(str_requestid);

                    studentRequestHisoryDetailslist[i] = innercompoffobj;



                }// End of for loop


            } catch (Throwable t) {
                Log.e("request fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }

    }//End of  method



    //Holder for customAdapter
    private class Holder {
        TextView holder_headid_TV,holder_reqmsg_TV, holder_reqtype_TV,holder_resmsg_TV,holder_reqdate_TV,
                 holder_reqid_TV,holder_reqstatus_TV,holder_respdate_TV,holder_projID_TV,holder_projTitle_TV,holder_status_TV;
        Button delete_bt;
        LinearLayout protitle_LL,respdate_LL,respmsg_LL;

    }
    //End of Holder

    public class CustomAdapter extends BaseAdapter {






        public CustomAdapter() {

            super();
            Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(studentRequestHisoryDetailslist.length);
            Log.d("Student_history.length", x);
            return studentRequestHisoryDetailslist.length;

        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            System.out.println("getItem position" + x);
            Log.d("getItem position", "x");
            return studentRequestHisoryDetailslist[position];
        }

        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);
            System.out.println("getItemId position" + x);
            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Holder holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_childlayout, parent, false);


                Request_history_LL = (LinearLayout) convertView.findViewById(R.id.req_history_LL);
                noRequesthistory_LL = (LinearLayout) convertView.findViewById(R.id.nohistory_LL);
                holder.protitle_LL = (LinearLayout) convertView.findViewById(R.id.protitle_ll);
                holder.respdate_LL = (LinearLayout) convertView.findViewById(R.id.respdate_ll);
                holder.respmsg_LL = (LinearLayout) convertView.findViewById(R.id.respmsg_ll);



                holder.holder_reqmsg_TV = (TextView) convertView.findViewById(R.id.requestmsg_TV);
                holder.holder_reqtype_TV = (TextView) convertView.findViewById(R.id.requestType_TV);
                holder.holder_resmsg_TV = (TextView) convertView.findViewById(R.id.ResponseMsg_TV);
                holder.holder_reqdate_TV = (TextView) convertView.findViewById(R.id.request_dateaction_TV);
                holder.holder_respdate_TV = (TextView) convertView.findViewById(R.id.resp_dateaction_TV);
                holder.holder_projID_TV = (TextView) convertView.findViewById(R.id.projectid_TV);
                holder.holder_projTitle_TV = (TextView) convertView.findViewById(R.id.projectTitle_TV);
                holder.holder_status_TV = (TextView) convertView.findViewById(R.id.Status_TV);
                holder.holder_reqid_TV = (TextView) convertView.findViewById(R.id.reqid_TV);
                holder.holder_reqstatus_TV = (TextView) convertView.findViewById(R.id.reqstatus_TV);
                holder.holder_headid_TV = (TextView) convertView.findViewById(R.id.headid_TV);



                holder.delete_bt = (Button) convertView.findViewById(R.id.delete_BT);
                holder.delete_bt.setVisibility(View.GONE);
//                respdate_LL.setVisibility(View.GONE);
//                respmsg_LL.setVisibility(View.GONE);
//                protitle_LL.setVisibility(View.GONE);

                //font_change_row_item_new();





                Log.d("Inside If convertView", "Inside If convertView");

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
                Log.d("Inside else convertView", "Inside else convertView");
            }

            studentRequestHisoryDetails = (StudentRequestHisoryDetails) getItem(position);
            if (count1 == 0) {
                Request_history_LL.setVisibility(View.GONE);
                noRequesthistory_LL.setVisibility(View.VISIBLE);
            } else {

                if (studentRequestHisoryDetails != null) {
                    holder.holder_reqmsg_TV.setText(studentRequestHisoryDetails.getReq_msg());
                    holder.holder_reqtype_TV.setText(studentRequestHisoryDetails.getReq_type());
                    holder.holder_reqdate_TV.setText(studentRequestHisoryDetails.getReq_date());
                    holder.holder_reqid_TV.setText(studentRequestHisoryDetails.getReq_id());
                    holder.holder_status_TV.setText(studentRequestHisoryDetails.getStatus());
                    holder.holder_reqstatus_TV.setText(studentRequestHisoryDetails.getReq_status());
                    holder.holder_headid_TV.setText(studentRequestHisoryDetails.getHeadid());






                    String headid=holder.holder_headid_TV.getText().toString();
                    System.out.println("studentRequestHisoryDetails.headid.().."+ studentRequestHisoryDetails.getHeadid());

                    if (studentRequestHisoryDetails.getHeadid().equals("1")) {
                        holder.protitle_LL.setVisibility(View.VISIBLE);
                        holder.holder_projID_TV.setText(studentRequestHisoryDetails.getPro_id());
                        holder.holder_projTitle_TV.setText(studentRequestHisoryDetails.getPro_title());
                    } else {
                        holder.protitle_LL.setVisibility(View.GONE);
                    }



                    if (studentRequestHisoryDetails.getReq_status().equals("2")) {
                        holder.delete_bt.setVisibility(View.GONE);
                        holder.respmsg_LL.setVisibility(View.VISIBLE);
                        holder.respdate_LL.setVisibility(View.VISIBLE);
                        holder.holder_resmsg_TV.setText(studentRequestHisoryDetails.getResp_msg());
                        holder.holder_respdate_TV.setText(studentRequestHisoryDetails.getResp_date());

                    } else {
                        holder.delete_bt.setVisibility(View.VISIBLE);
                        holder.respdate_LL.setVisibility(View.GONE);
                        holder.respmsg_LL.setVisibility(View.GONE);
                    }



                    holder.delete_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            str_requestedid=holder.holder_reqid_TV.getText().toString();
                            System.out.println("str_requestedid..onclick."+str_requestedid);
                            str_pdid=holder.holder_projID_TV.getText().toString();
                            System.out.println("str_pdid ..onclick..."+str_pdid);

                            internetDectector = new Class_InternetDectector(getActivity());
                            isInternetPresent = internetDectector.isConnectingToInternet();

                            if (isInternetPresent) {
                                DeleteHistory_AsyncTask task1 = new DeleteHistory_AsyncTask(context);
                                task1.execute();
                            } else {
                                Toast.makeText(getActivity(), "No internet", Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }// end of if



            }





            return convertView;


        }


    }//End of CustomAdapter

    // Submit Async  request
    private class DeleteHistory_AsyncTask extends AsyncTask<String, Void, Void> {

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


            deleteHistory();


            return null;
        }

        public DeleteHistory_AsyncTask(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                if (response_delete != null) {
                    System.out.println("response_delete.." + response_delete);
                    if (response_delete.toString().equals("success")) {

                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG).show();


                        Intent i=new Intent(getActivity(),StudentRequest.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);

                    } else {
                        Toast.makeText(context, "Sorry,could not be Deleted", Toast.LENGTH_LONG).show();

                    }
                }
            }
        }
    }// End of AsyncCall_submit

    public void deleteHistory() {

        String URL = Class_URL.URL_Projects.toString();
        String METHOD_NAME = "Student_Submit_Request_ToManager";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/Student_Submit_Request_ToManager";

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);


               System.out.println("str_requestedid...." + str_requestedid);

                if(str_requestedid.equals("1")) {

                    request.addProperty("Lead_Id", "");
                    request.addProperty("Request_type","");
                    request.addProperty("Priority","");
                    request.addProperty("PDID",str_pdid);
                    request.addProperty("Request_message","");
                    request.addProperty("RequestedId", str_requestedid);
                }else{
                    request.addProperty("Lead_Id", "");
                    request.addProperty("Request_type","");
                    request.addProperty("Priority","");
                    request.addProperty("PDID","");
                    request.addProperty("Request_message", "");
                    request.addProperty("RequestedId",str_requestedid);
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
                response_delete = (SoapPrimitive) envelope.getResponse();
                Log.i("string value atsponse", response_delete.toString());

            } catch (Throwable t) {

                Log.e("request fail", "> " + t.getMessage());
                internet_issue = "slow internet";
            }
        } catch (Throwable t) {

            Log.e("UnRegister Recei Error", "> " + t.getMessage());

        }

    }//End



}
