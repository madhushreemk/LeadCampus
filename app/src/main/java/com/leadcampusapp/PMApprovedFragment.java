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
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.leadcampusapp.module.ApprovedProjectList;
import com.leadcampusapp.module.ApprovedProjectListModule;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class PMApprovedFragment extends Fragment {

   // private ArrayList<HashMap<String, String>> list;
    public static final String FIRST_COLUMN="First";
    public static final String SECOND_COLUMN="Second";
    public static final String THIRD_COLUMN="Third";
    public static final String FOURTH_COLUMN="Fourth";

    ListView list,list_head;
    ArrayList<HashMap<String, String>> mylist, mylist_title;
    ListAdapter adapter_title, adapter;
    HashMap<String, String> map1, map2;

    String[] LeadId= {"MG10441","MG10442","MG10443","MG10444"};
    String[] Names = {"Pramod. K","Pooja. K","Shiva. S","Shripad. A"};
   // String[] Colleges = {"BVB","KLE","BVB","KLE"};
    String[] ProjectTittle = {"Food","Books","Food","Books"};
    String[] Approved_amt = {"100","200","300","400"};
    String[] Disperse_amt = {"100","200","100","200"};
    String[] Balance_amt = {"0","0","200","200"};


    ApprovedProjectListModule approvedProjectListObj;
    ArrayList<ApprovedProjectListModule> approvedProjectListModules;
    private ProgressBar progressBar;
    ApprovedProjectListModule[] approvedInfosarr;
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

        View view=inflater.inflate(R.layout.pmapproved_fragment, container, false);

        list = (ListView) view.findViewById(R.id.lv_PMapproved);
        list_head = (ListView) view.findViewById(R.id.lv_approvedlisthead);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        approvedProjectListObj = new ApprovedProjectListModule();

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


        GetApprovedProjectDetails getApprovedProjectDetails = new GetApprovedProjectDetails(getActivity());
        getApprovedProjectDetails.execute(MDId);

       // showActivity();
        return view;
    }

    public void showActivity() {

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/
        map1 = new HashMap<String, String>();

       // map1.put("slno", "Lead Id");
        map1.put("one", " Name");
      //  map1.put("two", " College");
        map1.put("two","Project Title");
        map1.put("three","Approved Amount");
        map1.put("four","Disperse Amount");
        map1.put("five","Balance Amount");
        mylist_title.add(map1);


        try {
            adapter_title = new SimpleAdapter(getContext(), mylist_title, R.layout.pmapproved_listhead,
                    new String[]{ "one", "two","three","four","five"}, new int[]{
                    R.id.Name, R.id.ProjectTittle, R.id.tv_approved, R.id.tv_disperse, R.id.tv_balnce});
            list_head.setAdapter(adapter_title);
        } catch (Exception e) {

        }

        /********************************************************/


        /**********Display the contents************/

  /*      for (int i = 0; i < Names.length; i++) {
            map2 = new HashMap<String, String>();

           // map2.put("slno", LeadId[i]);
            map2.put("one", Names[i]);
           // map2.put("two", Colleges[i]);
            map2.put("two", ProjectTittle[i]);
            map2.put("three", Approved_amt[i]);
            map2.put("four", Disperse_amt[i]);
            map2.put("five", Balance_amt[i]);
            mylist.add(map2);
        }
*/
        for (int i = 0; i < ApprovedProjectList.listview_arr.size(); i++) {

            map2 = new HashMap<String, String>();
            map2.put("one", ApprovedProjectList.listview_arr.get(i).getStudent_name());
            map2.put("two", ApprovedProjectList.listview_arr.get(i).getProject_tittle());
            map2.put("three", ApprovedProjectList.listview_arr.get(i).getApproved_amount());
            map2.put("four", ApprovedProjectList.listview_arr.get(i).getDisperse_amount());
            map2.put("five", ApprovedProjectList.listview_arr.get(i).getBalance_amount());
            map2.put("six", ApprovedProjectList.listview_arr.get(i).getLeadId());
            map2.put("seven", ApprovedProjectList.listview_arr.get(i).getPDId());
            map2.put("eight", ApprovedProjectList.listview_arr.get(i).getMobileNo());
            map2.put("nine", ApprovedProjectList.listview_arr.get(i).getStudent_Image_Path());
            map2.put("ten", ApprovedProjectList.listview_arr.get(i).getpmappr_collegename());
            map2.put("eleven", ApprovedProjectList.listview_arr.get(i).getpmappr_streamname());

           /* Log.e("pmcollegename",ApprovedProjectList.listview_arr.get(i).getpmappr_collegename());
            Log.e("pmstreamname",ApprovedProjectList.listview_arr.get(i).getpmappr_streamname());*/
            mylist.add(map2);

        /*    if(ApprovedProjectList.listview_arr.get(i).getApproved_amount().equals("0")){
                list.setBackgroundColor(Color.GREEN);
            }*/
        }

        try {
            adapter = new SimpleAdapter(getContext(), mylist, R.layout.pmapproved_list,
                    new String[]{"one", "two","three","four","five","six","seven","eight","nine","ten","eleven"}, new int[]{
                    R.id.Name, R.id.ProjectTittle, R.id.tv_approved, R.id.tv_disperse, R.id.tv_balnce,R.id.tv_leadid,R.id.tv_projId,R.id.tv_mobileNo,R.id.tv_studImagePath,
            R.id.pmapprov_collegenamelist_tv,R.id.pmapprov_streamnamelist_tv});
            list.setAdapter(adapter);
        } catch (Exception e) {

        }
      /*  if (ApprovedProjectList.listview_arr.get(i).getApproved_amount().equals("0")) {
            list.setBackgroundColor(Color.YELLOW);
        }*/

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //  String item = ((TextView)view).getText().toString();
             //   String selectedItem = (String) parent.getItemAtPosition(position);
                //Object o = list.getItemAtPosition(position);


              //  Toast.makeText(getContext(), "item"+name, Toast.LENGTH_LONG).show();
              /*  String approveamt=ApprovedProjectList.listview_arr.get(position).getApproved_amount();
                String desamt=ApprovedProjectList.listview_arr.get(position).getDisperse_amount();
                if(approveamt.equals(desamt)){
                 //   Toast.makeText(getActivity(),"approveamt: "+approveamt+" desamt="+desamt,Toast.LENGTH_LONG).show();
                    Log.i("Tag","approveamt: "+approveamt+" desamt="+desamt);
                }*/
                String approvedAmt = ((TextView) view.findViewById(R.id.tv_approved)).getText().toString();

                Log.d("ApprovedAmountisssss",approvedAmt);


               // if (approvedAmt.equals("0")) {

                    if (approvedAmt.equals("-200")) //condition has been changed
                    {
                    view.setEnabled(false);
                    view.setOnClickListener(null);

                    Toast.makeText(getActivity(),"Approved Amount is Zero",Toast.LENGTH_LONG).show();
                } else {
                    String name = ((TextView) view.findViewById(R.id.Name)).getText().toString();

                    String Lead_Id= ((TextView) view.findViewById(R.id.tv_leadid)).getText().toString();
                    String ProjectId= ((TextView) view.findViewById(R.id.tv_projId)).getText().toString();
                    String DisAmt = ((TextView) view.findViewById(R.id.tv_disperse)).getText().toString();
                    String MobileNo = ((TextView) view.findViewById(R.id.tv_mobileNo)).getText().toString();
                    String Student_Image_Path = ((TextView) view.findViewById(R.id.tv_studImagePath)).getText().toString();
                    String pmapprove_collegenamelist= ((TextView) view.findViewById(R.id.pmapprov_collegenamelist_tv)).getText().toString();
                            String pmapprove_streamnamelist=((TextView) view.findViewById(R.id.pmapprov_streamnamelist_tv)).getText().toString();

                            /*                    String Lead_Id= ApprovedProjectList.listview_arr.get(position).getLeadId();
                    String ProjectId= ApprovedProjectList.listview_arr.get(position).getPDId();
                    String DisAmt=ApprovedProjectList.listview_arr.get(position).getDisperse_amount();
                    String MobileNo=ApprovedProjectList.listview_arr.get(position).getMobileNo();
                    String Student_Image_Path= ApprovedProjectList.listview_arr.get(position).getStudent_Image_Path();*/
                    Intent i = new Intent(getContext(), PMApproved_DetailsActivity.class);
                    i.putExtra("name", name);
                    i.putExtra("Lead_Id", Lead_Id);
                    i.putExtra("ProjectId", ProjectId);
                    i.putExtra("DisAmt",DisAmt);
                    i.putExtra("ManagerId", MDId);
                    i.putExtra("MobileNo",MobileNo);
                    i.putExtra("Student_Image_Path",Student_Image_Path);
                        i.putExtra("pmapprove_collegenamelist",pmapprove_collegenamelist);
                        i.putExtra("pmapprove_streamnamelist",pmapprove_streamnamelist);
                    startActivity(i);
                }
            }

        });
    }
    public class GetApprovedProjectDetails extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        GetApprovedProjectDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params [0];
            //SoapObject response = getStudDtls(leadid);

            //unapprovedProjectListObj = new UnapprovedProjectListModule();
            approvedProjectListModules = new ArrayList<ApprovedProjectListModule>();

            String METHOD_NAME = "GetApprovedProjectList";
            String NamespaceMail="http://mis.leadcampus.org/", SOAP_ACTION1="http://mis.leadcampus.org/GetApprovedProjectList";//namespace+methodname

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
                    Log.d("Tag","soap response Approved"+response.toString());

                    responseCount = response.getPropertyCount();


                    Log.d("count", String.valueOf(response.getPropertyCount()));

                    ApprovedProjectListModule.listview_arr.clear();
                    ApprovedProjectList.listview_arr.clear();
                    ArrayList<String> innerObj_Class_centers = null;
                    if(responseCount>0) {
                        //complitionProjectListModules = new ArrayList<ComplitionProjectListModule>();
                        //      ArrayList<String> arrayList = new ArrayList<String>();
                        approvedInfosarr=new ApprovedProjectListModule[responseCount];
                        for (i = 0; i < responseCount; i++)
                        {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                            //approvedProjectListObj = new ApprovedProjectListModule();

                            approvedProjectListObj.setStudent_name(response_soapobj.getProperty("StudentName").toString());
                            approvedProjectListObj.setProject_tittle(response_soapobj.getProperty("Title").toString());
                            approvedProjectListObj.setApproved_amount(response_soapobj.getProperty("SanctionAmount").toString());
                            approvedProjectListObj.setDisperse_amount(response_soapobj.getProperty("Amount").toString());
                            approvedProjectListObj.setLeadId(response_soapobj.getProperty("Lead_Id").toString());
                            approvedProjectListObj.setPDId(response_soapobj.getProperty("PDId").toString());
                            approvedProjectListObj.setpmappr_collegename(response_soapobj.getProperty("College_name").toString());//<College_name>Lalbhai Dalpatbhai </College_name>
                            approvedProjectListObj.setpmappr_streamname(response_soapobj.getProperty("StreamCode").toString());//<StreamCode>Engineering</StreamCode>

                            //   approvedProjectListObj.setBalance_amount(response_soapobj.getProperty("Amount").toString());
                            approvedProjectListModules.add(approvedProjectListObj);

                            //            innerObj_Class_centers = new ArrayList();
                            //          innerObj_Class_centers.add(response_soapobj.getProperty("StudentName").toString()); //<Id>1</Id>
                            Log.d("tag","approvedProjectListModules.get(i)="+approvedProjectListModules.get(i).getStudent_name());
                            Log.d("tag","approvedProjectListModules="+approvedProjectListModules.size());
                            Log.d("tag","approvedProjectListModules="+approvedProjectListObj.getStudent_name());

                            String Sname=response_soapobj.getProperty("StudentName").toString();
                            String ProjectTittle=response_soapobj.getProperty("Title").toString();
                            String ApprovedAmount=response_soapobj.getProperty("SanctionAmount").toString();
                            String DisperseAmount=response_soapobj.getProperty("giventotal").toString();
                            String MobileNo=response_soapobj.getProperty("MobileNo").toString();
                            String Student_Image_Path=response_soapobj.getProperty("Student_Image_Path").toString();
                            String college_name=response_soapobj.getProperty("College_name").toString();
                            String stream_name=response_soapobj.getProperty("StreamCode").toString();
                        //    Double BalanceAmountdbl=Double.valueOf(ApprovedAmount).doubleValue()-Double.valueOf(DisperseAmount).doubleValue();
                            long BalanceAmountdbl=Integer.valueOf(ApprovedAmount).intValue()-Integer.valueOf(DisperseAmount).intValue();

                            String BalanceAmount=String.valueOf(BalanceAmountdbl);
                            Log.i("Tag","BalanceAmountdbl"+BalanceAmountdbl);
                            String Lead_Id=response_soapobj.getProperty("Lead_Id").toString();
                            String Project_Id=response_soapobj.getProperty("PDId").toString();
                            approvedInfosarr[i]=approvedProjectListObj;

                            ApprovedProjectList.listview_arr.add(new ApprovedProjectList(Sname,ProjectTittle, ApprovedAmount,DisperseAmount,BalanceAmount,Lead_Id,Project_Id,MobileNo,Student_Image_Path,
                                    college_name,stream_name));

                            Log.i("Tag","ApprovedProjectListModule.getAmt="+ ApprovedProjectList.listview_arr.size());
                        }
                    }
                    return response;

                }
                catch (Exception t) {
                    Log.e("requestfail PMapprov", "> " + t.getMessage().toString());

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

           /* Log.i("Tag","approvedInfosarr==="+approvedInfosarr);
            Log.i("Tag","approvedInfosarr.length==="+approvedInfosarr.length);*/
           if(result!=null) {
               showActivity();
           }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
