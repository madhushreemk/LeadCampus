package com.leadcampusapp; /**
 * Created by Admin on 20-07-2018.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.LinkedHashSet;

//import com.android.sripad.leadnew_22_6_2018.R;

public class TShirtPaidFragment extends Fragment
{
    private ListView lview;
    private ArrayList<TshirtPaidModel> paidList;
    private TShirtPaidAckAdapter adapter;
    private SharedPreferences shardprefPM_obj;
    private String str_MangerID;
    private static final String  PREFBook_PM= "prefbook_pm";
    private static final String PrefID_PMID = "prefid_pmid";
    private LinkedHashSet<String> collegeNameLst;
    private ArrayList<String> collegeNameArrLst;
    private AppCompatSpinner spin_college;
    private ArrayList<TshirtPaidModel> originalList = null;
    private ProgressDialog progressDialog;
    private EditText etSearch;
    private TextView txt_studRegistered,txt_feesPaidStudents;
    private TextView txt_actualTotalCounts;
    private int counter=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.frag_tshirt_paid_acknowledged, container, false);

        lview = (NonScrollListView) view.findViewById(R.id.listview_fees_paid_acknowledged);
        txt_actualTotalCounts = (TextView) view.findViewById(R.id.txt_actualTotalCount);

        paidList = new ArrayList<TshirtPaidModel>();
        adapter = new TShirtPaidAckAdapter(getActivity(),paidList);
        lview.setAdapter(adapter);



        shardprefPM_obj = getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MangerID:",str_MangerID);

        collegeNameLst = new LinkedHashSet<String>();
        collegeNameLst.add("Select College");
        collegeNameArrLst = new ArrayList<String>();

        spin_college = (AppCompatSpinner) view.findViewById(R.id.spin_college);

        txt_studRegistered = (TextView) view.findViewById(R.id.txt_studRegistered);
        txt_feesPaidStudents = (TextView) view.findViewById(R.id.txt_feesPaidStudents);

      //  populateGetFeesRegistered();

        populateList();

        etSearch = (EditText) view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                String selectedCollege = spin_college.getSelectedItem().toString();

                if(!selectedCollege.equals("Select College")) {
                    String text = etSearch.getText().toString();
                    adapter.filter(text, originalList, selectedCollege);
                }else{
                    String text = etSearch.getText().toString();
                    adapter.filter(text, originalList, null);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //adapter.getFilter().filter(s.toString());
           /*     String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text,originalList);*/

                String text2 = s.toString();
                if(text2.equals("")) {
                    String text = spin_college.getSelectedItem().toString();
                    if(text!="Select College") {
                        adapter.filter(text, originalList, null);
                    }
                }
            }
        });

        spin_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
   /*             String collegeName = spin_college.getSelectedItem().toString();
                adapter.filter(collegeName,originalList);*/

                String collegeName = spin_college.getSelectedItem().toString();
                //adapter.filter(collegeName, originalList);
                if(!collegeName.equals("Select College")) {
                    adapter.filter(collegeName, originalList,null);
                }else{
                    counter++;
                    collegeName = "";
                    adapter.filter(collegeName, originalList,null);

               /*     if(counter!=1){
                        collegeName = "";
                        adapter.filter(collegeName, originalList);
                    }*/
                }
                Log.d("tag","Totalcountssssonselectchange:"+ String.valueOf(lview.getCount()));
                txt_actualTotalCounts.setText(String.valueOf(lview.getCount()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // showActivity();
        return view;
    }

    private void populateGetFeesRegistered() {
        GetFeesRegistered getFeesRegistered = new GetFeesRegistered(getActivity());
        getFeesRegistered.execute();
    }

    private void populateList() {

        GetSanctionTshirtList getSanctionTshirtList = new GetSanctionTshirtList(getActivity());
        getSanctionTshirtList.execute();

    }

    public class GetSanctionTshirtList extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetSanctionTshirtList (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = GetSanctionTshirtList();

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

            if(result != null){
            SoapPrimitive S_LeadId, S_StudentName, S_TshirtSize, S_CollegeName, S_MobileNo, S_IsFeesPaid, S_Status,S_projectcount, S_RequestedId;
            Object O_LeadId, O_StudentName, O_TshirtSize, O_CollegeName, O_MobileNo, O_IsFeesPaid, O_Status, O_projectcount, O_RequestedId;
            String str_leadid = null, str_studentName = null, str_TshirtSize = null, str_collegeName = null, str_MobileNo = null, str_IsFeesPaid = null, str_Status = null, str_projectcount = null,str_RequestedId = null;

            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject list = (SoapObject) result.getProperty(i);

                //Log.d("DistrictResult", list.toString());

                O_Status = list.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) list.getProperty("Status");
                    Log.d("Status:", S_Status.toString());
                    str_Status = S_Status.toString();
                }

                if (str_Status.equalsIgnoreCase("Success")) {

                    O_StudentName = list.getProperty("StudentName");
                    if (!O_StudentName.toString().equals("anyType{}") && !O_StudentName.toString().equals(null)) {
                        S_StudentName = (SoapPrimitive) list.getProperty("StudentName");
                        str_studentName = S_StudentName.toString();
                        Log.d("StudentNameissss", str_studentName);
                    }

                    O_LeadId = list.getProperty("Lead_id");
                    if (!O_LeadId.toString().equals("anyType{}") && !O_LeadId.toString().equals(null)) {
                        S_LeadId = (SoapPrimitive) list.getProperty("Lead_id");
                        str_leadid = S_LeadId.toString();
                        Log.d("str_leadidisss", str_leadid);
                    }

                    O_TshirtSize = list.getProperty("TshirtSize");
                    if (!O_TshirtSize.toString().equals("anyType{}") && !O_TshirtSize.toString().equals(null)) {
                        S_TshirtSize = (SoapPrimitive) list.getProperty("TshirtSize");
                        Log.d("S_RegistrationDate", S_TshirtSize.toString());
                        str_TshirtSize = S_TshirtSize.toString();
                        Log.d("str_TshirtSize", str_TshirtSize);
                    }

                    O_CollegeName = list.getProperty("CollegeName");
                    if (!O_CollegeName.toString().equals("anyType{}") && !O_CollegeName.toString().equals(null)) {
                        S_CollegeName = (SoapPrimitive) list.getProperty("CollegeName");
                        Log.d("S_CollegeName", S_CollegeName.toString());
                        str_collegeName = S_CollegeName.toString();
                        Log.d("str_collegeName", str_collegeName);

                        collegeNameLst.add(str_collegeName);

                    }
                    if (O_CollegeName.toString().equals("anyType{}") || O_CollegeName.toString().equals(null)) {
                        str_collegeName = "";
                        //collegeNameLst.add(str_collegeName);
                    }

                    O_MobileNo = list.getProperty("MobileNo");
                    if (!O_MobileNo.toString().equals("anyType{}") && !O_MobileNo.toString().equals(null)) {
                        S_MobileNo = (SoapPrimitive) list.getProperty("MobileNo");
                        Log.d("S_MobileNo", S_MobileNo.toString());
                        str_MobileNo = S_MobileNo.toString();
                    }

                    O_IsFeesPaid = list.getProperty("isFeePaid");
                    if (!O_IsFeesPaid.toString().equals("anyType{}") && !O_IsFeesPaid.toString().equals(null)) {
                        S_IsFeesPaid = (SoapPrimitive) list.getProperty("isFeePaid");
                        Log.d("S_IsFeesPaid", S_IsFeesPaid.toString());
                        str_IsFeesPaid = S_IsFeesPaid.toString();
                    }
                    O_projectcount = list.getProperty("projectcount");
                    if (!O_projectcount.toString().equals("anyType{}") && !O_projectcount.toString().equals(null)) {
                        S_projectcount = (SoapPrimitive) list.getProperty("projectcount");
                        Log.d("S_projectcount", S_projectcount.toString());
                        str_projectcount = S_projectcount.toString();
                    }
                    O_RequestedId = list.getProperty("RequestedId");
                    if (!O_RequestedId.toString().equals("anyType{}") && !O_RequestedId.toString().equals(null)) {
                        S_RequestedId = (SoapPrimitive) list.getProperty("RequestedId");
                        Log.d("S_RequestedId", S_RequestedId.toString());
                        str_RequestedId = S_RequestedId.toString();
                    }

                    TshirtPaidModel item = null;
                    if (!str_collegeName.isEmpty()) {
                            item = new TshirtPaidModel(str_studentName, str_leadid, str_collegeName, str_MobileNo,str_TshirtSize , str_projectcount, str_RequestedId, str_Status);
                        paidList.add(item);
                    }


                }

            }

            originalList = new ArrayList<TshirtPaidModel>();
            originalList.addAll(paidList);

            adapter.notifyDataSetChanged();

                Log.d("Totalcountssss:", String.valueOf(lview.getCount()));
                txt_actualTotalCounts.setText(String.valueOf(lview.getCount()));

                initCollegeSpinner();
        }
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject GetSanctionTshirtList() {
        String METHOD_NAME = "GetSanctionTshirtList";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetSanctionTshirtList";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
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
          //  HttpTransportSE androidHttpTransport = new HttpTransportSE("http://testbed.dfindia.org/leadws/Managerws.asmx?WSDL");

            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                Log.d("tag","soapresponse1xxxx GetSanctionTshirtList"+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponse GetSanctionTshirtList"+response.toString());

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


    private void initCollegeSpinner() {
        collegeNameArrLst.addAll(collegeNameLst);
        ArrayAdapter dataAdapter2 = new ArrayAdapter(getActivity(), R.layout.simple_spinner_items, collegeNameArrLst);
        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);
        // attaching data adapter to spinner
        spin_college.setAdapter(dataAdapter2);
        //spin_college.setSupportBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.colorWhite));

    }

    public class GetFeesRegistered extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;
        ProgressDialog progressDialgs;

        //private ProgressBar progressBar;

        GetFeesRegistered (Context ctx){
            context = ctx;
            progressDialgs = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getFeesRegistered();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
      /*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
            progressDialgs.setMessage("Loading");
            progressDialgs.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialgs.setCanceledOnTouchOutside(false);
            progressDialgs.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if(result!=null){

            SoapPrimitive S_TotalRegistrn, S_FeePaiedStudent, S_Status;
            Object O_TotalRegistrn, O_FeePaiedStudent, O_Status;
            String str_TotalRegistrn = null, str_FeePaiedStudent = null, str_Status = null;

            Log.d("Resultssssiss", result.toString());


            for (int i = 0; i < result.getPropertyCount(); i++) {
                SoapObject list = (SoapObject) result.getProperty(i);

                //Log.d("DistrictResult", list.toString());

                O_Status = list.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) list.getProperty("Status");
                    Log.d("Status:", S_Status.toString());
                    str_Status = S_Status.toString();
                }

                if (str_Status.equalsIgnoreCase("Success")) {
                    O_TotalRegistrn = list.getProperty("TotalRegistration");
                    if (!O_TotalRegistrn.toString().equals("anyType{}") && !O_TotalRegistrn.toString().equals(null)) {
                        S_TotalRegistrn = (SoapPrimitive) list.getProperty("TotalRegistration");
                        Log.d("TotalRegistration:", S_TotalRegistrn.toString());
                        str_TotalRegistrn = S_TotalRegistrn.toString();
                        txt_studRegistered.setText(str_TotalRegistrn);
                    }

                    O_FeePaiedStudent = list.getProperty("FeePaiedStudent");
                    if (!O_FeePaiedStudent.toString().equals("anyType{}") && !O_FeePaiedStudent.toString().equals(null)) {
                        S_FeePaiedStudent = (SoapPrimitive) list.getProperty("FeePaiedStudent");
                        Log.d("SFeePaiedStudent:", S_FeePaiedStudent.toString());
                        str_FeePaiedStudent = S_FeePaiedStudent.toString();
                        txt_feesPaidStudents.setText(str_FeePaiedStudent);
                    }


                }
            }
        }

            progressDialgs.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getFeesRegistered() {
        String METHOD_NAME = "GetStudntRegAndPaiedStudentconts";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetStudntRegAndPaiedStudentconts";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
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
                //Log.d("soapresponse1xxxxFees",envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                //Log.e("soapresponseyyyyyyyFees",response.toString());

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

}//end of fragment class
