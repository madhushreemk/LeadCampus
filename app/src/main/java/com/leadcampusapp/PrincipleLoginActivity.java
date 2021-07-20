package com.leadcampusapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class PrincipleLoginActivity extends AppCompatActivity {


    Spinner year_spin;
    String[] arraySpin_year = new String[] {
            "2019", "2018", "2017", "2016"
    };

    private ProgressDialog progressDialog;
    TextView txt_registration,txt_projCount,txt_FundedAmount,collg_name;
    ArrayList<PrincipleCollegeModule> collegeList;
    PrincipleCollegeModule collegeListObj;
    ListView list,list_head;
    private ProgressBar progressBar;

    private PrincipleCollegeAdapter adapter_collg;
    PrincipleCollegeModule item;
    ArrayList<String> arr_leadId=new ArrayList<>();
    ArrayList<String> arr_studName=new ArrayList<>();
    ArrayList<String> arr_projectTitle=new ArrayList<>();

    SharedPreferences shardprefPrinciple_info;
    SharedPreferences.Editor editor_Principle_info;
    public static final String PREBook_Principle_info="prefbook_principle_info";
    public static final String PrefID_CollgeName="prefid_CollgName";
    public static final String PrefID_AcademicId="prefid_AcademicId";
    public static final String PrefID_PCollegeId="prefid_PCollegeId";
    public static final String PrefID_PMailId="prefid_PMailId";
    public static final String PrefID_PWhatsapp="prefid_PWhatsapp";
    public static final String PrefID_PMobileNo="prefid_PMobileNo";
    public static final String PrefID_PName="prefid_PName";

    String CollegeName,AcademicId,CollegeId;
    String selected_year="2019",AcademicId_new;
    LinearLayout main_layout_principle;
    private HashMap<String,Integer> mapYearCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principle_login);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        main_layout_principle=(LinearLayout) findViewById(R.id.main_layout_principle);
        year_spin = (Spinner) findViewById(R.id.year_spin);
        txt_registration = (TextView) findViewById(R.id.txt_registration);
        txt_projCount = (TextView) findViewById(R.id.txt_projCount);
        txt_FundedAmount =(TextView) findViewById(R.id.txt_FundedAmount);
        list = (ListView) findViewById(R.id.lv_stud_list);
        collg_name=(TextView) findViewById(R.id.collg_name);

        shardprefPrinciple_info=getSharedPreferences(PREBook_Principle_info, Context.MODE_PRIVATE);

        shardprefPrinciple_info.getString(PrefID_CollgeName, "").trim();
        CollegeName = shardprefPrinciple_info.getString(PrefID_CollgeName, "").trim();
        Log.e("tag","CollegeName:"+CollegeName);

        shardprefPrinciple_info.getString(PrefID_AcademicId, "").trim();
        AcademicId = shardprefPrinciple_info.getString(PrefID_AcademicId, "").trim();
        Log.e("tag","AcademicId:"+AcademicId);

        shardprefPrinciple_info.getString(PrefID_PCollegeId, "").trim();
        CollegeId = shardprefPrinciple_info.getString(PrefID_PCollegeId, "").trim();
        Log.e("tag","CollegeId:"+CollegeId);

        collegeList = new ArrayList<PrincipleCollegeModule>();
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpin_year);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spin.setAdapter(adapter);*/

        collg_name.setText(CollegeName);
        mapYearCode = new HashMap<String,Integer>();
       /* adapter_collg = new PrincipleCollegeAdapter(this,collegeList);
        list.setAdapter(adapter_collg);*/
        initializeSpinnerYear();

        year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected (AdapterView < ? > parent, View view, int position, long id){
                // TODO Auto-generated method stub
                selected_year = year_spin.getSelectedItem().toString();
                if(selected_year!="Select Year"){
                    GetPrincipleDetails getPrincipleDetails = new GetPrincipleDetails(PrincipleLoginActivity.this);
                    getPrincipleDetails.execute();
                }
                // Toast.makeText(getApplicationContext(),"Blood:"+str_bloodgroup,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected (AdapterView < ? > parent){
                // TODO Auto-generated method stub

            }
        });
        /*if(selected_year.equalsIgnoreCase("2019")){
            AcademicId_new="6";
        }else if(selected_year.equalsIgnoreCase("2018")){
            AcademicId_new="5";
        }else if(selected_year.equalsIgnoreCase("2017")){
            AcademicId_new="4";
        }else if(selected_year.equalsIgnoreCase("2016")){
            AcademicId_new="3";
        }else if(selected_year.equalsIgnoreCase("2015")){
            AcademicId_new="2";
        }else if(selected_year.equalsIgnoreCase("2014")){
            AcademicId_new="1";
        }

        GetPrincipleDetails getPrincipleDetails = new GetPrincipleDetails(PrincipleLoginActivity.this);
        getPrincipleDetails.execute();*/

    }

    private void initializeSpinnerYear() {
        final ArrayList<String> listYear = new ArrayList<String>();

        // listYear.add("Select Year");
        listYear.add("2019-2020");
        listYear.add("2018-2019");
        listYear.add("2017-2018");
        listYear.add("2016-2017");
        listYear.add("2015-2016");
        listYear.add("2014-2015");
        listYear.add("Below 2014");

        mapYearCode.put("Below 2014",0);
        mapYearCode.put("2014-2015",1);
        mapYearCode.put("2015-2016",2);
        mapYearCode.put("2016-2017",3);
        mapYearCode.put("2017-2018",4);
        mapYearCode.put("2018-2019",5);
        mapYearCode.put("2019-2020",6);

        ArrayAdapter dataAdapterListYear = new ArrayAdapter(getApplicationContext(), R.layout.simple_spinner_items, listYear);
        dataAdapterListYear.setDropDownViewResource(R.layout.spinnercustomstyle);

        year_spin.setAdapter(dataAdapterListYear);
      //  year_spin.setSupportBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.colorBlack));

    }



    public class GetPrincipleDetails extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetPrincipleDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "GetCollegeCountSummary";
            String SOAP_ACTION1 = "http://mis.leadcampus.org/GetCollegeCountSummary";
            String NAMESPACE = "http://mis.leadcampus.org/";

            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

               // String collgId="302",AcadId="5";
                Log.e("tag","principle CollegeId="+CollegeId+"AcademicId="+AcademicId_new);
                int yearId = mapYearCode.get(selected_year);

                request.addProperty("CollegeId",CollegeId);
                request.addProperty("AcademicId",String.valueOf(yearId));

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                Log.e("tag","request principle="+request);
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("Principle list:",response.toString());

                    SoapObject soapObj = (SoapObject) response.getProperty("ProjectList");

                    if (!soapObj.toString().equals("anyType{}") && !soapObj.toString().equals(null)) {

                        SoapPrimitive S_Lead_Id,S_StudentName,S_ProjectTitle;
                        Object O_Lead_Id,O_StudentName,O_ProjectTitle;
                        String str_Lead_Id = null, str_StudentName = null,str_ProjectTitle = null;
                        //count2 = soapObj.getPropertyCount();
                        collegeList.clear();
                        Log.e("tag","soapObj.getPropertyCount()=="+soapObj.getPropertyCount());
                       // Log.e("tag","soapObj.getPropertyCount()-1=="+soapObj.getPropertyCount()-1);

                        for (int count1 = 0; count1 < soapObj.getPropertyCount(); count1++) {
                            SoapObject ProjectList = (SoapObject) soapObj.getProperty(count1);
                            Log.d("doclist", ProjectList.toString());

                            if(count1!=0) {
                                O_Lead_Id = ProjectList.getProperty("Lead_Id");
                                Log.e("tag", "Lead_Id==" + O_Lead_Id);
                                if (!O_Lead_Id.toString().equals("anyType{}") && !O_Lead_Id.toString().equals(null)) {
                                    S_Lead_Id = (SoapPrimitive) ProjectList.getProperty("Lead_Id");
                                    str_Lead_Id = S_Lead_Id.toString();
                                    Log.e("tag", "str_Lead_Id==" + str_Lead_Id);
                                }
                                O_StudentName = ProjectList.getProperty("StudentName");
                                Log.e("tag", "StudentName==" + O_StudentName);
                                if (!O_StudentName.toString().equals("anyType{}") && !O_StudentName.toString().equals(null)) {
                                    S_StudentName = (SoapPrimitive) ProjectList.getProperty("StudentName");
                                    str_StudentName = S_StudentName.toString();
                                    Log.e("tag", "str_StudentName==" + str_StudentName);
                                }
                                O_ProjectTitle = ProjectList.getProperty("ProjectTitle");
                                Log.e("tag", "ProjectTitle==" + O_ProjectTitle);
                                if (!O_ProjectTitle.toString().equals("anyType{}") && !O_ProjectTitle.toString().equals(null)) {
                                    S_ProjectTitle = (SoapPrimitive) ProjectList.getProperty("ProjectTitle");
                                    str_ProjectTitle = S_ProjectTitle.toString();
                                    Log.e("tag", "str_ProjectTitle==" + str_ProjectTitle);
                                }

                                arr_leadId.add(str_Lead_Id);
                                arr_studName.add(str_StudentName);
                                arr_projectTitle.add(str_ProjectTitle);
                                Log.e("tag", "arr_leadId=" + arr_leadId);


                                item = new PrincipleCollegeModule(str_Lead_Id, str_StudentName, str_ProjectTitle);
                                collegeList.add(item);
                            }
                        }


                     //   adapter_collg.notifyDataSetChanged();
                    }

                    Log.e("tag","collegeList="+collegeList);

                    return response;

                }

                catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(PrincipleLoginActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    t.printStackTrace();
                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(PrincipleLoginActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(PrincipleLoginActivity.this,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(PrincipleLoginActivity.this,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {
           /* progressBar.setVisibility(View.GONE);*/
            progressDialog.dismiss();

            if(result!=null) {
                String finalResult = result.toString();
                String finals = finalResult.replace("anyType", "");
                Log.d("Finals is", finals);
                String regCount = String.valueOf(result.getProperty("Registration_Count"));
                Log.d("regCount is=", regCount);
                txt_registration.setText(regCount);
                String prjCount = String.valueOf(result.getProperty("Project_Count"));
                Log.d("prjCount is=", prjCount);
                txt_projCount.setText(prjCount);
                String fundCount = String.valueOf(result.getProperty("Funded_Amount"));
                txt_FundedAmount.setText(fundCount);

            }

           /* for(int i=0;i<collegeList.size();i++) {
                adapter_collg = new PrincipleCollegeAdapter(PrincipleLoginActivity.this, collegeList);

            }
            list.setAdapter(adapter_collg);*/

          // Log.e("tag","collegeList="+collegeList.get(0).getStudentName());
            adapter_collg = new PrincipleCollegeAdapter(PrincipleLoginActivity.this, collegeList);
            list.setAdapter(adapter_collg);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
