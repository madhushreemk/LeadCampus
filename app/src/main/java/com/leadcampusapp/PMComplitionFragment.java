package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.leadcampusapp.module.ComplitedProjectList;
import com.leadcampusapp.module.ComplitionProjectListModule;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class PMComplitionFragment extends Fragment {

    ListView list,list_head;
    ArrayList<HashMap<String, String>> mylist, mylist_title;
    ListAdapter adapter_title,adapter,adapter2;
    HashMap<String, String> map1, map2;

    String[] LeadId= {"MG10441","MG10442","MG10443","MG10444"};
    String[] Names = {"Pramod. K","Akshatha. K","Shiva. S","Shripad. A"};
    String[] Colleges = {"BVB","KLE","BVB","KLE"};
    String[] ProjectTittle = {"Food","Books","Food","Books"};
    String[] Budget = {"100","200","300","400"};

    ComplitionProjectListModule complitedProjectListObj;
    ArrayList<ComplitionProjectListModule> complitedProjectListModules;
    private ProgressBar progressBar;
   // UnapprovedListAdapter adapter1 ;
    ComplitionProjectListModule[] complitedInfosarr;
    ArrayList<ComplitionProjectListModule> arrayList= new ArrayList<ComplitionProjectListModule>();

    private HashMap<String,String> mapPTitlePId;

    int responseCount=0;
    int i=0;
   // Integer ManagerId;

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    SharedPreferences shardprefPM_obj;
    String str_MangerID;
    Integer MDId;

    private EditText etSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.pmcomplitionproject_fragment, container, false);

        mapPTitlePId = new HashMap<String, String>();

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        list = (ListView) view.findViewById(R.id.lv_complitedlist);
        list_head = (ListView) view.findViewById(R.id.lv_complitedlistHead);

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        complitedProjectListObj = new ComplitionProjectListModule();

/*        ManagerId = getActivity().getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);
*/

        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);


        etSearch = (EditText) view.findViewById(R.id.etSearch);
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
                if(adapter!=null && text!=null) {
                    ((SimpleAdapter) adapter).getFilter().filter(text);
                }
            }
        });


        GetComplitedProjectDetails getComplitedProjectDetails = new GetComplitedProjectDetails(getActivity());
        getComplitedProjectDetails.execute(MDId);

        return view;
    }

    public void showActivity1() {

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/


        map1 = new HashMap<String, String>();

       // map1.put("slno", "Lead Id");
        map1.put("one", " Name");
        map1.put("two", " College");
        map1.put("three","Project Title");
        map1.put("four","Budget");
        mylist_title.add(map1);


        try {
            adapter_title = new SimpleAdapter(getContext(), mylist_title, R.layout.pmcomplited_list,
                    new String[]{ "one", "two","three","four"}, new int[]{
                    R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget});
            list_head.setAdapter(adapter_title);
        } catch (Exception e) {

        }

        /********************************************************/


        /**********Display the contents************/

      /*  for (int i = 0; i < Names.length; i++) {
            map2 = new HashMap<String, String>();

           // map2.put("slno", LeadId[i]);
            map2.put("one", Names[i]);
            map2.put("two", Colleges[i]);
            map2.put("three", ProjectTittle[i]);
            map2.put("four", Budget[i]);
            mylist.add(map2);
        }*/
        map2 = new HashMap<String, String>();

        map2.put("one", "Madhu");
        map2.put("two", "College");
        map2.put("three", "ProjectTittle");
        map2.put("four", "Budget");
        mylist.add(map2);

        try {
            adapter = new SimpleAdapter(getContext(), mylist, R.layout.pmcomplited_list,
                    new String[]{"one", "two","three","four"}, new int[]{
                    R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget});
            list.setAdapter(adapter);
        } catch (Exception e) {

        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //  String item = ((TextView)view).getText().toString();
             //   String selectedItem = (String) parent.getItemAtPosition(position);
                Object o = list.getItemAtPosition(position);
                String name = ((TextView) view.findViewById(R.id.Name)).getText().toString();

           //     Toast.makeText(getContext(), "item"+name, Toast.LENGTH_LONG).show();
                Intent i= new Intent(getContext(), PMComplitionProjectActivity.class);
                i.putExtra("name",name);
                i.putExtra("ManagerId",MDId);
                startActivity(i);
            }

        });
    }
    public void showActivity() {

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/

        map1 = new HashMap<String, String>();

        // map1.put("slno", "Lead Id");
        map1.put("one", " Name");
        map1.put("two", " College");
        map1.put("three","Project Title");
        map1.put("four","Approved Amount");
        mylist_title.add(map1);


        try {
            adapter_title = new SimpleAdapter(getContext(), mylist_title, R.layout.pmunapproved_listhead,
                    new String[]{ "one", "two","three","four"}, new int[]{
                    R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget});
            list_head.setAdapter(adapter_title);
        } catch (Exception e) {

        }














        /********************************************************/

        Log.i("Tag","complitedProjectListObj="+complitedProjectListObj.getStudent_name());
        Log.i("Tag","ComplitedProjectList.listview_arr.size="+ ComplitedProjectList.listview_arr.size());
        Log.i("Tag","ComplitedProjectListModule.listview_arr.size="+ ComplitionProjectListModule.listview_arr.size());

        /**********Display the contents************/
        for (int i = 0; i < ComplitedProjectList.listview_arr.size(); i++) {

            String Collegestring = ComplitedProjectList.listview_arr.get(i).getCollege_name();
            String firstWord = "";
            /*String arr[] = Collegestring.split(" ", 2);

            String firstWord = arr[0];   //the
            String secondWord = arr[1];

            Log.i("tag","firstWord="+firstWord+" secondWord="+secondWord);*/
            if(Collegestring.equals("anyType{}")){

            }else {

                String arr[] = Collegestring.split(" ");

                if (arr.length == 1) {
                    firstWord = arr[0];
                } else {
                    firstWord = arr[0];
                }
                Log.i("tag", "firstWord=" + firstWord);//+ " secondWord=" + secondWord);
            }
            map2 = new HashMap<String, String>();
            map2.put("one", ComplitedProjectList.listview_arr.get(i).getStudent_name());
            map2.put("two", firstWord);
            map2.put("three", ComplitedProjectList.listview_arr.get(i).getProject_tittle());
            map2.put("four", ComplitedProjectList.listview_arr.get(i).getAmount());

            map2.put("five", ComplitedProjectList.listview_arr.get(i).getLeadId());
            map2.put("six", ComplitedProjectList.listview_arr.get(i).getProjectId());
            map2.put("seven", ComplitedProjectList.listview_arr.get(i).getMobileNo());
            //Log.e("ImagePathissssssss",ComplitedProjectList.listview_arr.get(i).getStudent_Image_Path());
            map2.put("eight", ComplitedProjectList.listview_arr.get(i).getStudent_Image_Path());

            map2.put("nine", ComplitedProjectList.listview_arr.get(i).getpmcomp_collegename());
            map2.put("ten", ComplitedProjectList.listview_arr.get(i).getpmcomp_stream_name());
            map2.put("eleven", ComplitedProjectList.listview_arr.get(i).get_is_impactproject());

           /* Log.e("pmcom_collegename",ComplitedProjectList.listview_arr.get(i).getpmcomp_collegename());
            Log.e("pmcom_streamname",ComplitedProjectList.listview_arr.get(i).getpmcomp_stream_name());*/
            mylist.add(map2);
        }

        try {
            adapter = new SimpleAdapter(getContext(), mylist, R.layout.pmcomplited_list,
                    new String[]{"one", "two","three","four","five","six","seven","eight","nine","ten","eleven"}, new int[]{
                    R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget,R.id.tv_leadid,R.id.tv_projId,R.id.tv_mobileNo,R.id.tv_studImagePath,
            R.id.pmcomp_collegenamelist_tv,R.id.pmcomp_streamnamelist_tv,R.id.pmcomp_isimpactproject_tv});
            list.setAdapter(adapter);
        } catch (Exception e) {

        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  String item = ((TextView)view).getText().toString();
                //   String selectedItem = (String) parent.getItemAtPosition(position);
                Object o = list.getItemAtPosition(position);
                String name = ((TextView) view.findViewById(R.id.Name)).getText().toString();

                String project_Title = ((TextView) view.findViewById(R.id.ProjectTittle)).getText().toString();

/*                String LeadId = ComplitedProjectList.listview_arr.get(position).getLeadId();
                String PDId = ComplitedProjectList.listview_arr.get(position).getProjectId();
                String MobileNo= ComplitedProjectList.listview_arr.get(position).getMobileNo();
                String Student_Image_Path= ComplitedProjectList.listview_arr.get(position).getStudent_Image_Path();*/

                String LeadId = ((TextView) view.findViewById(R.id.tv_leadid)).getText().toString();
                String PDId = ((TextView) view.findViewById(R.id.tv_projId)).getText().toString();
                String MobileNo = ((TextView) view.findViewById(R.id.tv_mobileNo)).getText().toString();
                String Student_Image_Path = ((TextView) view.findViewById(R.id.tv_studImagePath)).getText().toString();
                String str_pmcomp_collegename = ((TextView) view.findViewById(R.id.pmcomp_collegenamelist_tv)).getText().toString();

                String str_pmcomp_streamname = ((TextView) view.findViewById(R.id.pmcomp_streamnamelist_tv)).getText().toString();

                String str_pmcomp_is_impactproject = ((TextView) view.findViewById(R.id.pmcomp_isimpactproject_tv)).getText().toString();

                String Project_Id = mapPTitlePId.get(project_Title);
                Log.d("Project Idxxxx",Project_Id);

              //  Toast.makeText(getContext(), "item"+name, Toast.LENGTH_LONG).show();
                Intent i= new Intent(getContext(), PMComplitionProjectActivity.class);
                i.putExtra("name",name);
                i.putExtra("projectId",Project_Id);
                i.putExtra("LeadId",LeadId);
                i.putExtra("PDId",PDId);
                i.putExtra("MobileNo",MobileNo);
                i.putExtra("Student_Image_Path",Student_Image_Path);
                i.putExtra("str_pmcomp_collegename",str_pmcomp_collegename);
                i.putExtra("str_pmcomp_streamname",str_pmcomp_streamname);
                i.putExtra("str_pmcomp_is_impactproject",str_pmcomp_is_impactproject);

                Log.i("tag","projectId="+Project_Id+"PDId="+PDId);


                startActivity(i);
            }

        });
    }

    public class GetComplitedProjectDetails extends AsyncTask<Integer, Void, SoapObject> {

        Context context;
     //   private ProgressDialog progressDialog;

        GetComplitedProjectDetails (Context ctx){
            context = ctx;
          //  progressDialog = new ProgressDialog(context);
        }

        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params [0];

            complitedProjectListModules = new ArrayList<ComplitionProjectListModule>();

            String METHOD_NAME = "GetcomplitedProjectList";
            String NamespaceMail="http://mis.leadcampus.org/", SOAP_ACTION1="http://mis.leadcampus.org/GetcomplitedProjectList";//namespace+methodname

            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

                request.addProperty("ManagerId", ManagerId);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                   // Log.d("soap responseyyyyyyy",response.toString());
                    //Log.d("Tag","soap response Complited"+response.toString());

                    responseCount = response.getPropertyCount();

                    Log.d("count", String.valueOf(response.getPropertyCount()));

                    ComplitionProjectListModule.listview_arr.clear();
                    ComplitedProjectList.listview_arr.clear();
                    ArrayList<String> innerObj_Class_centers = null;
                    if(responseCount>0) {
                        complitedInfosarr=new ComplitionProjectListModule[responseCount];
                        for (i = 0; i < responseCount; i++)
                        {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                            Log.d("response_soapobjxx",response_soapobj.toString());

                            complitedProjectListObj.setStudent_name(response_soapobj.getProperty("StudentName").toString());
                            complitedProjectListObj.setCollege_name(response_soapobj.getProperty("College_name").toString());
                            complitedProjectListObj.setProject_tittle(response_soapobj.getProperty("Title").toString());
                            complitedProjectListObj.setSanction_amount(response_soapobj.getProperty("SanctionAmount").toString());
                            complitedProjectListObj.setLeadId(response_soapobj.getProperty("Lead_Id").toString());
                            complitedProjectListObj.setProjectId(response_soapobj.getProperty("PDId").toString());

                            complitedProjectListObj.setpmcomp_collegename(response_soapobj.getProperty("College_name").toString());//<College_name>Lalbhai Dalpatbhai </College_name>
                            complitedProjectListObj.setpmcomp_stream_name(response_soapobj.getProperty("StreamCode").toString());//<StreamCode>Engineering</StreamCode>

                            complitedProjectListObj.set_is_impactproject(response_soapobj.getProperty("IsImpactProject").toString());//<IsImpactProject>0</IsImpactProject>

                            complitedProjectListModules.add(complitedProjectListObj);

                            Log.d("tag","complitedProjectListModules.get(i)="+complitedProjectListModules.get(i).getStudent_name());
                            Log.d("tag","complitedProjectListModules="+complitedProjectListModules.size());
                            Log.d("tag","complitedProjectListModules="+complitedProjectListObj.getStudent_name());

                            String Sname=response_soapobj.getProperty("StudentName").toString();
                            String College=response_soapobj.getProperty("College_name").toString();
                            String ProjectTittle=response_soapobj.getProperty("Title").toString();
                            String Amount=response_soapobj.getProperty("SanctionAmount").toString();
                            String Project_Id = response_soapobj.getProperty("PDId").toString();
                            String Lead_Id = response_soapobj.getProperty("Lead_Id").toString();
                            String MobileNo=response_soapobj.getProperty("MobileNo").toString();
                            String Student_Image_Path=response_soapobj.getProperty("Student_Image_Path").toString();

                            String str_collegename=response_soapobj.getProperty("College_name").toString();
                            String str_streamname=response_soapobj.getProperty("StreamCode").toString();

                            String str_impactproject=response_soapobj.getProperty("IsImpactProject").toString();

                            mapPTitlePId.put(ProjectTittle,Project_Id);

                            complitedInfosarr[i]=complitedProjectListObj;

                          ComplitedProjectList.listview_arr.add(new ComplitedProjectList(Sname, College,ProjectTittle, Amount,Lead_Id,Project_Id,MobileNo,Student_Image_Path,str_collegename,str_streamname,str_impactproject));

                            Log.i("Tag","ComplitedProjectListlistview_arr.size="+ ComplitedProjectList.listview_arr.size());

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

            //Log.d("Soap response is",response.toString());

            return null;
        }

        @Override
        protected void onPreExecute() {

           progressBar.setVisibility(View.VISIBLE);
            /*progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            progressBar.setVisibility(View.GONE);
           // progressDialog.dismiss();
            if(result!=null) {

                Log.i("Tag", "complitedInfosarr===" + complitedInfosarr);
                Log.i("Tag", "complitedInfosarr.length===" + complitedInfosarr.length);
                showActivity();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
