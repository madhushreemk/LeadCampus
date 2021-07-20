package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.leadcampusapp.module.FundProjectList;
import com.leadcampusapp.module.FundProjectListModule;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PMFundAmountFragment extends Fragment {

    ListView list,list_head;
    ArrayList<HashMap<String, String>> mylist, mylist_title;
    ListAdapter adapter_title, adapter;
    HashMap<String, String> map1, map2;

    String[] Names = {"Pramod. K","Uma. K","Shiva. S","Shripad. A"};
    String[] Colleges = {"BVB","KLE","BVB","KLE"};
    String[] ProjectTittle = {"Food","Books","Food","Books"};
    String[] Budget = {"100","0","200","400"};
    String[] Fund = {"100","0","200","400"};
    ArrayList<String> array_image = new ArrayList<String>();

    FundProjectListModule fundProjectListObj;
    ArrayList<FundProjectListModule> fundProjectListModules;
    private ProgressBar progressBar;
   // UnapprovedListAdapter adapter1 ;
    FundProjectListModule[] fundInfosarr;
    //ArrayList<UnapprovedProjectListModule> arrayList= new ArrayList<UnapprovedProjectListModule>();

    String LeadId,ProjectId;
    int Project_id,Lead_id;
    int responseCount=0;
    int i=0;
    EditText userInput;
    Integer ManagerId;

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    SharedPreferences shardprefPM_obj;
    String str_MangerID;
    Integer MDId;
    String giventotal,balanceAmount,approvedAmount;
    private EditText etSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.pmfundamount_fragment, container, false);

        list = (ListView) view.findViewById(R.id.lv_fundamt);
        list_head = (ListView) view.findViewById(R.id.lv_fundamtHead);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        array_image.add(String.valueOf(R.drawable.cash_bag));
        array_image.add(String.valueOf(R.drawable.cash_bag));
        array_image.add(String.valueOf(R.drawable.cash_bag));
        array_image.add(String.valueOf(R.drawable.cash_bag));

        //showActivity();
      /*  ManagerId = getActivity().getIntent().getExtras().getInt("ManagerId");
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













        GetFundProjectDetails getFundProjectDetails = new GetFundProjectDetails(getActivity());
        getFundProjectDetails.execute(MDId);
        return view;
    }

  /*  public void showActivity1() {

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/

     /*   map1 = new HashMap<String, String>();

        map1.put("slno", "SlNo");
        map1.put("one", " Name");
        map1.put("two", " College");
        map1.put("three","Project Tittle");
        map1.put("four","Approved Amount");
        map1.put("five","Fund");
        mylist_title.add(map1);


        try {
            adapter_title = new SimpleAdapter(getContext(), mylist_title, R.layout.pmfundamount_listhead,
                    new String[]{ "one", "two","three","four","five"}, new int[]{
                    R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget, R.id.tv_balnce});
            list_head.setAdapter(adapter_title);
        } catch (Exception e) {

        }

        /********************************************************/


        /**********Display the contents************/

      /*  for (int i = 0; i < Names.length; i++) {
            map2 = new HashMap<String, String>();

            map2.put("slno", String.valueOf(i + 1));
            map2.put("one", Names[i]);
            map2.put("two", Colleges[i]);
            map2.put("three", ProjectTittle[i]);
            map2.put("four", Budget[i]);
            map2.put("five", Fund[i]);
            Log.i("tag","Fund[i]="+Fund[i]);
            Log.i("tag","Fund[i]="+Fund[i].toString());
            if(Fund[i].toString().equals("0")){
                map2.put("six","00");
            }else {
                map2.put("six", array_image.get(i));
            }
            mylist.add(map2);
        }


        try {
            adapter = new SimpleAdapter(getContext(), mylist, R.layout.pmfundamount_list,
                    new String[]{"one", "two","three","four","five","six"}, new int[]{
                    R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget, R.id.Fund, R.id.Fund1});
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
                String College = ((TextView) view.findViewById(R.id.College)).getText().toString();
                String PT = ((TextView) view.findViewById(R.id.ProjectTittle)).getText().toString();
                String Budget = ((TextView) view.findViewById(R.id.Budget)).getText().toString();
                String Fund = ((TextView) view.findViewById(R.id.Fund)).getText().toString();
                String img=  String.valueOf(R.drawable.smile_emoji);

               // String currentdate=getCurrentTimeStamp();

                Toast.makeText(getContext(), "Name: "+name, Toast.LENGTH_LONG).show();

                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.pmfund_prompt, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.fundamtPromt_et);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        Toast.makeText(getContext(), userInput.getText(), Toast.LENGTH_LONG).show();
                                        //result.setText(userInput.getText());
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

          /*      map2 = new HashMap<String, String>();

                map2.put("one", name);
                map2.put("two", College);
                map2.put("three", PT);
                map2.put("four", Budget);
                map2.put("five", Fund);
                map2.put("six", img);
                mylist.add(map2);

                try {
                    adapter = new SimpleAdapter(getContext(), mylist, R.layout.pmfundamount_list,
                            new String[]{"one", "two","three","four","five","six"}, new int[]{
                            R.id.Name, R.id.College, R.id.ProjectTittle, R.id.Budget, R.id.Fund,R.id.Fund1});
                    list.setAdapter(adapter);
                } catch (Exception e) {

                }*/
              /*  Intent i= new Intent(getContext(), PMUnapproved_DetailsActivity.class);
                i.putExtra("name",name);
                startActivity(i);*/
    /*        }

        });


    }*/
    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
    public void showActivity() {

        mylist = new ArrayList<HashMap<String, String>>();
        mylist_title = new ArrayList<HashMap<String, String>>();

        /**********Display the headings************/


        map1 = new HashMap<String, String>();

        // map1.put("slno", "Lead Id");
        map1.put("one", "Name");
        map1.put("two", "Project Title");
        map1.put("three","Approved Amount");
        map1.put("four","Disperse Amount");
        map1.put("five","Balance Amount");
        mylist_title.add(map1);


        try {
            adapter_title = new SimpleAdapter(getContext(), mylist_title, R.layout.pmfundamount_listhead,
                    new String[]{ "one", "two","three","four","five"}, new int[]{
                    R.id.Name, R.id.ProjectTittle, R.id.ApprovedAmt, R.id.DisperseAmt, R.id.BalanceAmt});
            list_head.setAdapter(adapter_title);
        } catch (Exception e) {

        }

        /********************************************************/

//        Log.i("Tag","fundProjectListObj="+fundProjectListObj.getStudent_name());
        Log.i("Tag","fundProjectList.listview_arr.size="+ FundProjectList.listview_arr.size());
        Log.i("Tag","fundProjectListModule.listview_arr.size="+ FundProjectListModule.listview_arr.size());

        /**********Display the contents************/
        for (int i = 0; i < FundProjectList.listview_arr.size(); i++) {

            map2 = new HashMap<String, String>();
            map2.put("one", FundProjectList.listview_arr.get(i).getStudent_name());
            map2.put("two", FundProjectList.listview_arr.get(i).getProject_tittle());
            map2.put("three", FundProjectList.listview_arr.get(i).getSanctionAmount());
            map2.put("four", FundProjectList.listview_arr.get(i).getGiventotal());
            map2.put("six", FundProjectList.listview_arr.get(i).getBalanceAmount());
            if(FundProjectList.listview_arr.get(i).getSanctionAmount().equals("0")){
                map2.put("five", String.valueOf(R.drawable.no_cash_bag));
            }
            else {
                map2.put("five", String.valueOf(R.drawable.cash_bag));
            }

            map2.put("seven", FundProjectList.listview_arr.get(i).getLead_id());
            map2.put("eight", FundProjectList.listview_arr.get(i).getProject_id());
            //map2.put("nine", FundProjectList.listview_arr.get(i).getBalanceAmount());

            mylist.add(map2);
        }

        try {
            adapter = new SimpleAdapter(getContext(), mylist, R.layout.pmfund_list,
                    new String[]{"one", "two","three","four","six","five","seven","eight"}, new int[]{
                    R.id.Name, R.id.ProjectTittle, R.id.tv_approved, R.id.tv_disperse, R.id.tv_balnce, R.id.Fund1,R.id.tv_leadid,R.id.tv_projId});
            list.setAdapter(adapter);
        } catch (Exception e) {

        }

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //  String item = ((TextView)view).getText().toString();
                //   String selectedItem = (String) parent.getItemAtPosition(position);
                Object o = list.getItemAtPosition(position);
                Log.i("Tag", "o==" + o.toString());
                Log.i("Tag", "position==" + position);

                String name = ((TextView) view.findViewById(R.id.Name)).getText().toString();

      /*          LeadId = FundProjectList.listview_arr.get(position).getLead_id();
                ProjectId = FundProjectList.listview_arr.get(position).getProject_id();*/

                LeadId = ((TextView) view.findViewById(R.id.tv_leadid)).getText().toString();
                ProjectId = ((TextView) view.findViewById(R.id.tv_projId)).getText().toString();

                Project_id = Integer.parseInt(ProjectId);


                //  Lead_id=Integer.parseInt(LeadId);

                //final String BalanceAmt = FundProjectList.listview_arr.get(position).getBalanceAmount();

                final String BalanceAmt = ((TextView) view.findViewById(R.id.tv_balnce)).getText().toString();

         /*       final String BalanceAmt;
                TextView txtViewBalance = (TextView) view.findViewById(R.id.tv_balAmt);
                if(txtViewBalance.getVisibility()==View.VISIBLE) {
                    BalanceAmt = ((TextView) view.findViewById(R.id.tv_balAmt)).getText().toString();
                }
                else{
                    BalanceAmt = ((TextView) view.findViewById(R.id.tv_balnce)).getText().toString();
                }*/

              //  Toast.makeText(getContext(), "item=" + name, Toast.LENGTH_LONG).show();
                Log.i("Tag", "Lead_id=" + LeadId);
                Log.i("Tag", "Project_id=" + ProjectId);
                Log.i("Tag", "BalanceAmt=" + BalanceAmt);
               /* Toast.makeText(getContext(), "Lead_id="+Lead_id, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Project_type="+Project_type+"Action_plan="+Action_plan, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(), "Benficiaries="+Benficiaries+"Objectivies="+Objectivies, Toast.LENGTH_LONG).show();*/
             /*   Intent i= new Intent(getContext(), PMUnapproved_DetailsActivity.class);
                i.putExtra("name",name);
                i.putExtra("lead_id",Lead_id);
                i.putExtra("Project_id",Project_id);
                startActivity(i);*/

                /*approvedAmount=  FundProjectList.listview_arr.get(position).getSanctionAmount();
                balanceAmount = FundProjectList.listview_arr.get(position).getBalanceAmount();
                giventotal = FundProjectList.listview_arr.get(position).getGiventotal();*/

                approvedAmount =  ((TextView) view.findViewById(R.id.tv_approved)).getText().toString();
                balanceAmount = BalanceAmt;
                giventotal = ((TextView) view.findViewById(R.id.tv_disperse)).getText().toString();



                Log.i("tag","giventotal="+giventotal);
                Log.i("tag","balanceAmount="+balanceAmount);

            /*    if (FundProjectList.listview_arr.get(position).getBalanceAmount().equals("0")) {
                    view.setEnabled(false);
                    view.setOnClickListener(null);
                    Toast.makeText(getActivity(),"Balance Amount is Zero ",Toast.LENGTH_LONG).show();
                }*/
                if (balanceAmount.equals("0")) {
                    view.setEnabled(false);
                    view.setOnClickListener(null);
                    Toast.makeText(getActivity(),"Balance Amount is Zero ",Toast.LENGTH_LONG).show();
                }
                  else {

                    LayoutInflater li = LayoutInflater.from(getContext());
                    View promptsView = li.inflate(R.layout.pmfund_prompt, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);
                    userInput = (EditText) promptsView.findViewById(R.id.fundamtPromt_et);
                    userInput.setText(BalanceAmt);

                    // set dialog message


                        alertDialogBuilder
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        // edit text
                                        //result.setText(userInput.getText());

                                   /*     if(userInput.toString().length() == 0) {
                                            userInput.setError( "Amount required!" );
                                        }
                                        if(Integer.valueOf(balanceAmount)>Integer.valueOf(giventotal)) {
                                            Toast.makeText(getContext(), "Fund amount cannot be more than balance amount ", Toast.LENGTH_LONG).show();                                        }
                                        else {
                                            //  long BalanceAmountdbl=Integer.valueOf(SanctionAmount).intValue()-Integer.valueOf(giventotal).intValue();
                                            SubmitFund submitFund = new SubmitFund(getActivity());
                                            submitFund.execute();
                                            Toast.makeText(getContext(), userInput.getText(), Toast.LENGTH_LONG).show();
                                        }*/

                                    }
                                })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                    Button theButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    theButton.setOnClickListener(new CustomListener(alertDialog));
                }
            }

        });
    }

    class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        public CustomListener(Dialog dialog) {
            this.dialog = dialog;
        }
        @Override
        public void onClick(View v) {
            // put your code here
          //  String mValue = userInput.getText().toString();
            if(validation()) {
                //    dialog.dismiss();

                if (fundAmt()) {
                    SubmitFund submitFund = new SubmitFund(getActivity());
                    submitFund.execute();
                } else {
                    Toast.makeText(getContext(), "Fund amount cannot be more than balance amount ", Toast.LENGTH_LONG).show();
                }
            }
            /*else{
                Toast.makeText(getActivity(), "Invalid data", Toast.LENGTH_SHORT).show();
            }*/
        }
    }
    public boolean validation(){

        Boolean bpmcomments=true,bamount=true;


        if( userInput.getText().toString().length() == 0 ){
            userInput.setError( "Amount required!" );bpmcomments=false;}

        if(bpmcomments&&bamount) {
            return true;
        }else{return false;}
    }
    public boolean fundAmt(){

        Boolean bpmcomments=true,bamount=true;

        String balanceAmt=userInput.getText().toString();
        Integer approvedAmtInt=Integer.parseInt(approvedAmount);
        Integer balanceAmtInt=Integer.parseInt(balanceAmt);
        Integer giventotalInt=Integer.parseInt(giventotal);

        Integer totalBalanceInt=approvedAmtInt-giventotalInt;

        Log.i("tag","balanceAmtInt="+balanceAmtInt+"giventotalInt="+giventotalInt+"totalBalanceInt="+totalBalanceInt);

        if( balanceAmtInt>totalBalanceInt ){
         //   Toast.makeText(getContext(), "Fund amount cannot be more than balance amount ", Toast.LENGTH_LONG).show();                                        }
         bpmcomments=false;}

        if(bpmcomments&&bamount) {
            return true;
        }else{return false;}
    }
    public class SubmitFund extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
      //  private ProgressBar progressBar;
        Context context;
        private ProgressDialog progressDialog;

        SubmitFund (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = SubmitFundDetails();

            //   SoapObject response = ReApplyProjectDetails();
            Log.d("Soap response issssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
       //    progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //   progressBar.setVisibility(View.VISIBLE);
            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result!=null) {
                if (result.toString().equals("Error")) {
                    Toast.makeText(context, "Error occured while saving to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Amount funded successfully", Toast.LENGTH_LONG).show();
                    Intent ittEditProjToProjStatus = new Intent(getActivity(), PMProjectDetailActivity.class);
                    ittEditProjToProjStatus.putExtra("pageCount", 3);
                    startActivity(ittEditProjToProjStatus);
                }
            }
         //   progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private SoapPrimitive SubmitFundDetails() {
        String METHOD_NAME = "AddFundAmount";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/AddFundAmount";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            // long projectIds = (long) Integer.valueOf(projectId);
            request.addProperty("PDId",Project_id);
            Log.d("PDIdssssssxxxx","hi");
            Log.d("PDIdssssssxxxx", String.valueOf(Project_id));
            request.addProperty("ManagerId",MDId);
            request.addProperty("LeadId",LeadId);
            Log.d("LeadIdxxxxxx",LeadId);
           /* int themeId = hashProjTypeId.get(spin_projectType.getSelectedItem().toString());
            Log.d("themeidsssssxxxx", String.valueOf(themeId));
            request.addProperty("Theme",String.valueOf(themeId));
            long beneficiaryNo = (long) Integer.valueOf(edt_noOfBeneficiaries.getText().toString());
            Log.d("beneficiaryNosssxxxx", String.valueOf(beneficiaryNo));
            request.addProperty("BeneficiaryNo",beneficiaryNo);
            Log.d("objectivesxxxxxx",edt_objectives.getText().toString());
            request.addProperty("Objectives",edt_objectives.getText().toString());
            Log.d("actionplansxxxxxx",edt_actionPlan.getText().toString());
            request.addProperty("ActionPlan",edt_actionPlan.getText().toString());
            Log.d("actionplansxxxxxx",edt_amount.getText().toString());
            request.addProperty("SanctionAmount",edt_amount.getText().toString());*/
            request.addProperty("Amount",userInput.getText().toString());
            Log.d("Amountsxxxxxx",userInput.getText().toString());
           // request.addProperty("ManagerComments",edt_pmcomment.getText().toString());
           // Log.d("Requestisxxxxx",request.toString());

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
                // Log.d("soap response1xxxx",envelope.getResponse().toString());

                //  SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                //Log.e("soapupdateProjectDtlsss",envelope.getResponse().toString());

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

    public class GetFundProjectDetails extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        GetFundProjectDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params [0];
            //SoapObject response = getStudDtls(leadid);

            //unapprovedProjectListObj = new UnapprovedProjectListModule();
            fundProjectListModules = new ArrayList<FundProjectListModule>();

            String METHOD_NAME = "GetFundstatusList";
            String NamespaceMail="http://mis.leadcampus.org/", SOAP_ACTION1="http://mis.leadcampus.org/GetFundstatusList";//namespace+methodname

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
                    Log.d("Tag","soap response Fund"+response.toString());

                    responseCount = response.getPropertyCount();


                    Log.d("count", String.valueOf(response.getPropertyCount()));

                    FundProjectListModule.listview_arr.clear();
                    FundProjectList.listview_arr.clear();
                    ArrayList<String> innerObj_Class_centers = null;
                    if(responseCount>0) {
                        //complitionProjectListModules = new ArrayList<ComplitionProjectListModule>();
                        //      ArrayList<String> arrayList = new ArrayList<String>();
                        fundInfosarr=new FundProjectListModule[responseCount];
                        for (i = 0; i < responseCount; i++)
                        {
                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                           /* fundProjectListObj.setStudent_name(response_soapobj.getProperty("StudentName").toString());
                            fundProjectListObj.setProject_tittle(response_soapobj.getProperty("Title").toString());
                            fundProjectListObj.setLead_id(response_soapobj.getProperty("Lead_Id").toString());
                            fundProjectListObj.setProject_id(response_soapobj.getProperty("PDId").toString());
                            fundProjectListObj.setSanctionAmount(response_soapobj.getProperty("SanctionAmount").toString());
                            fundProjectListObj.setGiventotal(response_soapobj.getProperty("giventotal").toString());

                            fundProjectListModules.add(fundProjectListObj);

                            Log.d("tag","fundProjectListObj.get(i)="+fundProjectListModules.get(i).getStudent_name());
                            Log.d("tag","fundProjectListModules="+fundProjectListModules.size());
                            Log.d("tag","fundProjectListObj="+fundProjectListObj.getStudent_name());
*/
                            String Sname=response_soapobj.getProperty("StudentName").toString();
                            String ProjectTittle=response_soapobj.getProperty("Title").toString();
                            String SanctionAmount=response_soapobj.getProperty("SanctionAmount").toString();
                            String Lead_id=response_soapobj.getProperty("Lead_Id").toString();
                            String ProjectId=response_soapobj.getProperty("PDId").toString();
                            String giventotal=response_soapobj.getProperty("giventotal").toString();
                            long BalanceAmountdbl=Integer.valueOf(SanctionAmount).intValue()-Integer.valueOf(giventotal).intValue();
                            String BalanceAmount=String.valueOf(BalanceAmountdbl);
                            Log.i("Tag","BalanceAmountdbl"+BalanceAmountdbl);
                            Log.i("Tag","BalanceAmountdbl"+BalanceAmountdbl);
                            fundInfosarr[i]=fundProjectListObj;
                            Log.i("Tag","Sname"+Sname);
                            Log.i("Tag","ProjectTittle"+ProjectTittle);
                            FundProjectList.listview_arr.add(new FundProjectList(Lead_id, ProjectId,Sname, ProjectTittle,SanctionAmount,giventotal,BalanceAmount));

                            Log.i("Tag","FundProjectListModule.getAmt="+ FundProjectList.listview_arr.size());
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

            if(result!=null) {
                Log.i("Tag", "fundInfosarr===" + fundInfosarr);
                Log.i("Tag", "fundInfosarr.length===" + fundInfosarr.length);
                showActivity();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}
