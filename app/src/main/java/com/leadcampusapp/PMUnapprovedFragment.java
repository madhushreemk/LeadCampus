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
import com.leadcampusapp.module.UnapprovedProjectList;
import com.leadcampusapp.module.UnapprovedProjectListModule;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class PMUnapprovedFragment extends Fragment {

    // private ArrayList<HashMap<String, String>> list;
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN="Fourth";

    ListView list,list_head;
    ArrayList<HashMap<String, String>> mylist, mylist_title;
    ListAdapter adapter_title,adapter,adapter2;
    HashMap<String, String> map1, map2;

    String[] LeadId= {"MG10441","MG10442","MG10443","MG10444"};
    String[] Names = {"Pramod. K","Akshatha. K","Shiva. S","Shripad. A"};
    String[] Colleges = {"BVB","KLE","BVB","KLE"};
    String[] ProjectTittle = {"Food","Books","Food","Books"};
    String[] Budget = {"100","200","300","400"};

    UnapprovedProjectListModule unapprovedProjectListObj;
    ArrayList<UnapprovedProjectListModule> unapprovedProjectListModules;
    private ProgressBar progressBar;
    //  UnapprovedListAdapter adapter1 ;
    UnapprovedProjectListModule[] unapprovedInfosarr;
    ArrayList<UnapprovedProjectListModule> arrayList= new ArrayList<UnapprovedProjectListModule>();

    int responseCount=0;
    int i=0;
    Integer ManagerId;

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    SharedPreferences shardprefPM_obj;
    String str_MangerID;
    Integer MDId;

    private EditText etSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.pmunapproved_fragment, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        list = (ListView) view.findViewById(R.id.lv_PMunapproved);
        list_head = (ListView) view.findViewById(R.id.listView1);

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);

        unapprovedProjectListObj = new UnapprovedProjectListModule();

        etSearch = (EditText) view.findViewById(R.id.etSearch);
       /* etSearch.addTextChangedListener(new TextWatcher() {

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
                ((SimpleAdapter) adapter).getFilter().filter(text);
                ((SimpleAdapter) adapter).notifyDataSetChanged();

               *//* if(adapter!=null && text!=null) {
                    ((SimpleAdapter) adapter).getFilter().filter(text);
                }*//*
            }
        });*/




     /*   Intent intent = getActivity().getIntent();
        String MangerID=intent.getStringExtra("ManagerId");
        Log.i("tag","MangerID unapproved="+MangerID);
        if(MangerID.equals("null")){
            ManagerId=1;
        }else
        {
            ManagerId = Integer.parseInt(MangerID);

        }*/
/*        ManagerId = getActivity().getIntent().getExtras().getInt("ManagerId");
        Log.i("tag","MangerID PMProjectDetails="+ManagerId);
*/
        GetUnapprovedProjectDetails getUnapprovedProjectDetails = new GetUnapprovedProjectDetails(getActivity());
        getUnapprovedProjectDetails.execute(MDId);

        return view;
    }



    public boolean unapprovedproject(int MDId) {
        String mailresponse="flag";
        int verInt=0;
        String URL=Class_URL.URL_Manager.toString().trim();  //xml code
        String METHOD_NAMEMail = "GetUnaprrovedProjectList";
        String NamespaceMail="http://mis.leadcampus.org/", SOAPACTIONMail="http://mis.leadcampus.org/GetUnaprrovedProjectList";//namespace+methodname

        /*try{*/

        SoapObject request = new SoapObject(NamespaceMail, METHOD_NAMEMail);

        request.addProperty("ManagerId", ManagerId);//S



        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        //Set output SOAP object
        envelope.setOutputSoapObject(request);
        //Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        try
        {
            androidHttpTransport.call(SOAPACTIONMail, envelope);

            SoapObject response = (SoapObject) envelope.getResponse();
            Log.i("Tag","string value at response="+response.toString());


            String object2string = response.getProperty(0).toString();

            mailresponse=object2string.toString();
            verInt=Integer.valueOf(object2string);

            //Toast.makeText(getApplicationContext(), "hi"+object2string, Toast.LENGTH_LONG).show();
            Log.i("Tag","string value at response"+object2string.toString());
        }
        catch (Throwable t) {

            Log.e("request fail", "> " + t.getMessage());
        }

					/*}catch (Throwable t) {
		          Log.e("UnRegister Receiver Error", "> " + t.getMessage());

		        }*/
        return true;
    }
    public void showActivity1() {

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/


        map1 = new HashMap<String, String>();

        // map1.put("slno", "Lead Id");
        map1.put("one", " Name");
        map1.put("two", " College");
        map1.put("three","Project Tittle");
        map1.put("four","Budget");
        mylist_title.add(map1);


        try {
            adapter_title = new SimpleAdapter(getContext(), mylist_title, R.layout.pmunapproved_list,
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
            adapter = new SimpleAdapter(getContext(), mylist, R.layout.pmunapproved_list,
                    new String[]{"one", "two","three","four"}, new int[]{
                    R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget});
            list.setDividerHeight(1);
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

                //  Toast.makeText(getContext(), "item"+name, Toast.LENGTH_LONG).show();
                Intent i= new Intent(getContext(), PMUnapproved_DetailsActivity.class);
                i.putExtra("name",name);
                startActivity(i);
            }

        });
    }





    public void showActivity()
    {

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
            adapter_title = new SimpleAdapter(getContext(), mylist_title, R.layout.pmunapproved_listhead,
                    new String[]{ "one", "two","three","four"}, new int[]{
                    R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget});
            list_head.setAdapter(adapter_title);
        } catch (Exception e) {

        }

        /********************************************************/

        Log.i("Tag","unapprovedProjectListObj="+unapprovedProjectListObj.getStudent_name());
        Log.i("Tag","UnapprovedProjectList.listview_arr.size="+ UnapprovedProjectList.listview_arr.size());
        Log.i("Tag","UnapprovedProjectListModule.listview_arr.size="+ UnapprovedProjectListModule.listview_arr.size());


        //Log.e("Tag","Streamname="+ UnapprovedProjectListModule.listview_arr.get(0).getstreamname().toString());
        /**********Display the contents************/
        for (int i = 0; i < UnapprovedProjectList.listview_arr.size(); i++)
        {
            String Collegestring = UnapprovedProjectList.listview_arr.get(i).getCollege_name();
            String firstWord="";
            String secondWord;

            if(Collegestring.equals("anyType{}")){
                //Toast.makeText(getContext(), "College Name Not Found", Toast.LENGTH_SHORT).show();
            }else {
                String arr[] = Collegestring.split(" ");

                //the
                //  secondWord = arr[1];
                if(arr.length==1){
                    firstWord = arr[0];
                }
                else{
                    firstWord = arr[0];
                }
                Log.i("tag", "firstWord=" + firstWord );//+ " secondWord=" + secondWord);

         /*       map2 = new HashMap<String, String>();
                map2.put("one", UnapprovedProjectList.listview_arr.get(i).getStudent_name());
                map2.put("two", firstWord);
                map2.put("three", UnapprovedProjectList.listview_arr.get(i).getProject_tittle());
                map2.put("four", UnapprovedProjectList.listview_arr.get(i).getAmount());
                mylist.add(map2);*/
            }


            map2 = new HashMap<String, String>();
            map2.put("one", UnapprovedProjectList.listview_arr.get(i).getStudent_name());
            map2.put("two", firstWord);
            map2.put("three", UnapprovedProjectList.listview_arr.get(i).getProject_tittle());
            map2.put("four", UnapprovedProjectList.listview_arr.get(i).getAmount());

            map2.put("five",UnapprovedProjectList.listview_arr.get(i).getLead_id());
            map2.put("six",UnapprovedProjectList.listview_arr.get(i).getProject_Id());
            map2.put("seven",UnapprovedProjectList.listview_arr.get(i).getMobileNo());
            map2.put("eight",UnapprovedProjectList.listview_arr.get(i).getstream_name());
            Log.e("streamname",UnapprovedProjectList.listview_arr.get(i).getstream_name().toString());
           /* map2.put("eight",UnapprovedProjectList.listview_arr.get(i).getcollegename());
            map2.put("nine",UnapprovedProjectList.listview_arr.get(i).getstreamname());*/
            mylist.add(map2);
        }


        try {
            adapter = new SimpleAdapter(getContext(), mylist, R.layout.pmunapproved_list,
                    new String[]{"one", "two","three","four","five","six","seven","eight"}, new int[]
                    {
                    R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget, R.id.lead_id,R.id.proj_id,R.id.mobile_no,
                    R.id.streamname_list_tv});

            //((SimpleAdapter) adapter).getFilter().filter("title35");
            list.setAdapter(adapter);
        }

        catch (Exception e) {
            Log.e("Exceptioniss:",e.toString());
        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //  String item = ((TextView)view).getText().toString();
                //   String selectedItem = (String) parent.getItemAtPosition(position);
                Object o = list.getItemAtPosition(position);
                Log.i("Tag","o=="+o.toString());
                Log.i("Tag","position=="+position);

                String name = ((TextView) view.findViewById(R.id.Name)).getText().toString();
                String Lead_id = ((TextView) view.findViewById(R.id.lead_id)).getText().toString();
                String Project_id = ((TextView) view.findViewById(R.id.proj_id)).getText().toString();
                String MobileNo = ((TextView) view.findViewById(R.id.mobile_no)).getText().toString();
                String collegename=((TextView) view.findViewById(R.id.College)).getText().toString();
                String streamname=((TextView) view.findViewById(R.id.streamname_list_tv)).getText().toString();

                Log.e("stream_name",streamname);

               /* String collegename=((TextView) view.findViewById(R.id.collegename_list_tv)).getText().toString();
                String streamname=((TextView) view.findViewById(R.id.streamname_list_tv)).getText().toString();
*/

                /*String Lead_id= UnapprovedProjectList.listview_arr.get(position).getLead_id();
                String Project_id= UnapprovedProjectList.listview_arr.get(position).getProject_Id();
                String MobileNo= UnapprovedProjectList.listview_arr.get(position).getMobileNo();*/
                //   Toast.makeText(getContext(), "item="+name, Toast.LENGTH_LONG).show();
                Log.e("Tag","Lead_idUnApproved="+position+"=="+Lead_id);
                Log.e("Tag","Project_idUnApproved="+position+"=="+Project_id);
               /* Toast.makeText(getContext(), "Lead_id="+Lead_id, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Project_type="+Project_type+"Action_plan="+Action_plan, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Benficiaries="+Benficiaries+"Objectivies="+Objectivies, Toast.LENGTH_LONG).show();*/
                Intent i= new Intent(getContext(), PMUnapproved_DetailsActivity.class);
                i.putExtra("name",name);
                i.putExtra("lead_id",Lead_id);
                i.putExtra("Project_id",Project_id);
                i.putExtra("MobileNo",MobileNo);
                i.putExtra("collegename",collegename);
                i.putExtra("streamname",streamname);
                /*i.putExtra("collegename",collegename);
                i.putExtra("streamname",streamname);*/
                startActivity(i);
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
                ((SimpleAdapter) adapter).getFilter().filter(text);
                //((SimpleAdapter) adapter).notifyDataSetChanged();
            }
        });

    }

    public class GetUnapprovedProjectDetails extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        GetUnapprovedProjectDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params [0];
            //SoapObject response = getStudDtls(leadid);

            //unapprovedProjectListObj = new UnapprovedProjectListModule();
            unapprovedProjectListModules = new ArrayList<UnapprovedProjectListModule>();

            String METHOD_NAME = "GetUnaprrovedProjectList";
            String NamespaceMail="http://mis.leadcampus.org/", SOAP_ACTION1="http://mis.leadcampus.org/GetUnaprrovedProjectList";//namespace+methodname

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
                    Log.d("Tag","soap response Unapproved"+response.toString());

                    responseCount = response.getPropertyCount();


                    Log.d("count", String.valueOf(response.getPropertyCount()));

                    UnapprovedProjectListModule.listview_arr.clear();
                    UnapprovedProjectList.listview_arr.clear();
                    ArrayList<String> innerObj_Class_centers = null;
                    if(responseCount>0) {
                        //complitionProjectListModules = new ArrayList<ComplitionProjectListModule>();
                        //      ArrayList<String> arrayList = new ArrayList<String>();
                        unapprovedInfosarr=new UnapprovedProjectListModule[responseCount];
                        for (i = 0; i < responseCount; i++)
                        {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                            unapprovedProjectListObj.setStudent_name(response_soapobj.getProperty("StudentName").toString());//StudentName
                            unapprovedProjectListObj.setCollege_name(response_soapobj.getProperty("College_name").toString());
                            unapprovedProjectListObj.setProject_tittle(response_soapobj.getProperty("Title").toString());
                            unapprovedProjectListObj.setAmount(response_soapobj.getProperty("Amount").toString());
                            unapprovedProjectListObj.setLead_id(response_soapobj.getProperty("Lead_Id").toString());
                            unapprovedProjectListObj.setProjectId(response_soapobj.getProperty("PDId").toString());

                            unapprovedProjectListObj.setcollegename(response_soapobj.getProperty("College_name").toString());//<College_name>Lalbhai Dalpatbhai </College_name>
                            unapprovedProjectListObj.setstreamname(response_soapobj.getProperty("StreamCode").toString());//<StreamCode>Engineering</StreamCode>
                            /* unapprovedProjectListObj.setProject_type(response_soapobj.getProperty("Theme").toString());
                            unapprovedProjectListObj.setAction_plan(response_soapobj.getProperty("ActionPlan").toString());
                            unapprovedProjectListObj.setBenficiaries(response_soapobj.getProperty("BeneficiaryNo").toString());
                            unapprovedProjectListObj.setObjectivies(response_soapobj.getProperty("Objectives").toString());
                           */
                            unapprovedProjectListModules.add(unapprovedProjectListObj);

                            //            innerObj_Class_centers = new ArrayList();
                            //          innerObj_Class_centers.add(response_soapobj.getProperty("StudentName").toString()); //<Id>1</Id>
                            Log.d("tag","unapprovedProjectListModules.get(i)="+unapprovedProjectListModules.get(i).getStudent_name());
                            Log.d("tag","unapprovedProjectListModules="+unapprovedProjectListModules.size());
                            Log.d("tag","unapprovedProjectListModules="+unapprovedProjectListObj.getStudent_name());

                            String Sname=response_soapobj.getProperty("StudentName").toString();
                            String College=response_soapobj.getProperty("College_name").toString();
                            String ProjectTittle=response_soapobj.getProperty("Title").toString();
                            String Amount=response_soapobj.getProperty("Amount").toString();
                            String ProjectId=response_soapobj.getProperty("PDId").toString();
                            String Lead_id=response_soapobj.getProperty("Lead_Id").toString();
                            String MobileNo=response_soapobj.getProperty("MobileNo").toString();
                            String collegename=response_soapobj.getProperty("College_name").toString();
                            String streamname=response_soapobj.getProperty("StreamCode").toString();
                          /*  String Project_type=response_soapobj.getProperty("ThemeName").toString();
                            String ActionPlan=response_soapobj.getProperty("ActionPlan").toString();
                            String BeneficiaryNo=response_soapobj.getProperty("BeneficiaryNo").toString();
                            String Objectives=response_soapobj.getProperty("Objectives").toString();
*/
                            unapprovedInfosarr[i]=unapprovedProjectListObj;

                          //  UnapprovedProjectList.listview_arr.add(new UnapprovedProjectList(Sname, College,ProjectTittle, Amount,Lead_id,ProjectId,MobileNo,collegename,streamname));

                            UnapprovedProjectList.listview_arr.add(new UnapprovedProjectList(Sname, College,ProjectTittle,Amount,Lead_id,ProjectId,MobileNo,streamname));

                            Log.i("Tag","UnapprovedProjectListModule.getAmt="+ UnapprovedProjectList.listview_arr.size());
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


            /*Log.i("Tag","unapprovedInfosarr==="+unapprovedInfosarr);
            Log.i("Tag","unapprovedInfosarr.length==="+unapprovedInfosarr.length);*/
            if(result!=null)
            {
                showActivity();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
