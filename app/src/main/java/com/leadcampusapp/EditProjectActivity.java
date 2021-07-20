package com.leadcampusapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.android.sripad.leadnew_22_6_2018.R;

public class EditProjectActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{

    private Context context;
    private AppCompatSpinner spin_projectType;
    private EditText edt_projectType;
    private EditText edt_title;
    private EditText edt_noOfBeneficiaries;
    private EditText edt_beneficiaries_name;
    private EditText edt_objectives;
    private EditText edt_currentSituation;
    private EditText edt_actionPlan;
    private TextView txt_PMComments;
    private Button chat_txt;
    private Button btn_submit;

    private LinearLayout lnrlyt_projectTypeSpin;
    private LinearLayout lnrlyt_projectTypeEdit;

    private Button btn_addMaterial;
    private LinearLayout parentLinearLayout;
  /*  String material[]={"Books","Digital camera","Pen","Mouse","xyz"};
    String cost[]={"100","200","300","400"};*/
    private ArrayList<String> lstMaterialName = new ArrayList<String>();
    private ArrayList<String> lstMaterialCost = new ArrayList<String>();

    private ArrayList<String> lstMemberName = new ArrayList<String>();
    private ArrayList<String> lstMemberMailId = new ArrayList<String>();

    private String projectId = null;
    private String str_totalCost = null;
    private HashMap<String,Integer> hashProjTypeId = new HashMap<String,Integer>();

    private int flag=0;
    private LinearLayout lnrlyt_edtProject;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_SCellNo = "prefid_scellno"; //

    private SharedPreferences shardpref_S_obj;
    private String str_leadId,str_RegistrationId;
    private EditText edt_placeOfImpl;
    private TextView pmcomments_txt;

    static TextView clickstartproject_editdate_tv;
    static TextView clickendproject_editdate_tv;
    static String yyyyMMdd_startdate = "";
    static String yyyyMMdd_enddate = "";

    private static TextView editstudent_numberofdays_tv;
   static LinearLayout editstudentdays_linearlayout;

    private ImageButton editproject_add_field_button;
    private LinearLayout parent_linear_layout1;
    List<EditText> listEdMemberName = new ArrayList<EditText>();
    List<EditText> listEdMemberMailId = new ArrayList<EditText>();
    private String memberMailId;
    ArrayList<String> listMemberName = new ArrayList<String>();
    ArrayList<String> listMemberMailId = new ArrayList<String>();

    String str_add,str_membername,str_membermailid;

    private LinearLayout parent_linearlayout_material;
    private ImageButton editproject_addmaterial_ib;
    List<EditText> listEdMaterialCost = new ArrayList<EditText>();
    List<EditText> listEdMaterialName = new ArrayList<EditText>();
    String str_material,str_eachmaterialname,str_eachmaterialcost;
    ArrayList<String> listMaterialName = new ArrayList<String>();
    ArrayList<String> listMaterialCost = new ArrayList<String>();
    double grandSum=0;

    EditText mainEdt_Material;
    LinearLayout editproject_date_linearlayout,editproject_datelabel_linearlayout;

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    String projectStatus;

    static Class_alert_msg obj_class_alert_msg;
    static Context static_context1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);

        context = EditProjectActivity.this;
        static_context1= EditProjectActivity.this;

        obj_class_alert_msg = new Class_alert_msg(static_context1);

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));


        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        //actionBarTV.setText("Edit Project");






        shardpref_S_obj=this.getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();

        Log.d("str_leadId:",str_leadId);
        //Toast.makeText(context,"LeadId: "+str_leadId,Toast.LENGTH_LONG).show();

        shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_RegistrationId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        Log.d("str_RegistrationId:",str_RegistrationId);
        //Toast.makeText(context,"RegistrationId: "+str_RegistrationId,Toast.LENGTH_LONG).show();




        spin_projectType = (AppCompatSpinner) findViewById(R.id.spin_projectType);
        edt_projectType = (EditText) findViewById(R.id.edt_projectType);
        edt_noOfBeneficiaries = (EditText) findViewById(R.id.edt_noOfBeneficiaries);
        edt_beneficiaries_name = (EditText) findViewById(R.id.edt_whoareBeneficiaries);
        edt_objectives = (EditText) findViewById(R.id.edt_objective);
        edt_actionPlan = (EditText) findViewById(R.id.edt_actionplan);
        edt_title = (EditText) findViewById(R.id.edt_title);
        txt_PMComments = (TextView) findViewById(R.id.txt_PMComments);
        chat_txt=(Button) findViewById(R.id.chat_txt);
        edt_currentSituation = (EditText) findViewById(R.id.edt_currentSituation);

        edt_placeOfImpl = (EditText) findViewById(R.id.edt_placeOfImpl);

        pmcomments_txt = (TextView) findViewById(R.id.pmcomments_txt);

        btn_submit = (Button) findViewById(R.id.btn_projsubmit);

        chat_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(EditProjectActivity.this,ChatActivity.class);
                i.putExtra("projectStatus",projectStatus);
                i.putExtra("projectId",projectId);
                i.putExtra("userType","Student");
                startActivity(i);
            }
        });
        editproject_add_field_button=(ImageButton) findViewById(R.id.editproject_add_field_button);
        parent_linear_layout1= (LinearLayout) findViewById(R.id.parent_linear_layout1);
        str_add="false";

        editproject_addmaterial_ib=(ImageButton) findViewById(R.id.editproject_addmaterial_ib);
        parent_linearlayout_material= (LinearLayout) findViewById(R.id.parent_linearlayout_material);
        str_material="false";



        clickstartproject_editdate_tv = (TextView) findViewById(R.id.clickstartproject_editdate_TV);
        clickendproject_editdate_tv = (TextView) findViewById(R.id.clickendproject_editdate_TV);

        editstudent_numberofdays_tv=(TextView)findViewById(R.id.editstudent_numberofdays_tv);
        editstudentdays_linearlayout=(LinearLayout)findViewById(R.id.editstudentdays_linearlayout);

        editproject_date_linearlayout=(LinearLayout)findViewById(R.id.editproject_date_linearlayout);
        editproject_datelabel_linearlayout=(LinearLayout)findViewById(R.id.editproject_datelabel_linearlayout);


        lnrlyt_projectTypeSpin = (LinearLayout) findViewById(R.id.lnrlyt_projectTypeSpin);
        lnrlyt_projectTypeEdit = (LinearLayout) findViewById(R.id.lnrlyt_projectTypeEdit);

        lnrlyt_edtProject = (LinearLayout) findViewById(R.id.lnrlyt_edtProject);


        mainEdt_Material = (EditText) findViewById(R.id.edt_materialname);
        listEdMaterialName.clear();
        listEdMaterialCost.clear();
        listEdMemberName.clear();
        listEdMemberMailId.clear();
        listMaterialName.clear();
        listMaterialCost.clear();
        listMemberName.clear();
        listMemberMailId.clear();


        editproject_add_field_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.field, null);
                // Add the new row before the add field button.
                parent_linear_layout1.addView(rowView, parent_linear_layout1.getChildCount() - 1);



                EditText edt_memberName = (EditText) rowView.findViewById(R.id.edt_memberName);
                listEdMemberName.add(edt_memberName);

                final EditText edt_memberMailId = (EditText) rowView.findViewById(R.id.edt_memberMailId);
                listEdMemberMailId.add(edt_memberMailId);

                if(str_add.equalsIgnoreCase("true"))
                { edt_memberName.setText(str_membername);
                    edt_memberMailId.setText(str_membermailid);
                    str_add="false";
                }


                edt_memberMailId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            memberMailId = edt_memberMailId.getText().toString().trim();
                            if (memberMailId.length() > 0) {
                                if (!isValidEmail(memberMailId)) {
                                    edt_memberMailId.setError("Enter correct mail id");
                                }
                            }
                        }
                    }
                });


                Button delete_button_member = (Button) rowView.findViewById(R.id.delete_button_member);
                delete_button_member.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Log.d("Insidexxx","delete button member");
/*                        Log.d("Materialcostxxxxxxxx",edt_materialcost.getText().toString());
                        Double removedCost = Double.parseDouble(edt_materialcost.getText().toString());
                        grandSum = grandSum - removedCost;*/
                        //Log.d("grandsumxxxxx", String.valueOf(grandSum));
                        parent_linear_layout1.removeView((View) v.getParent());

                        View rowView = (View) v.getParent();
                        final EditText edt_memberMailId = (EditText) rowView.findViewById(R.id.edt_memberMailId);
                        listEdMemberMailId.remove(edt_memberMailId);

                        final EditText edt_memberName = (EditText) rowView.findViewById(R.id.edt_memberName);
                        listEdMemberName.remove(edt_memberName);

                    }
                });
            }
        });



        editproject_addmaterial_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.field_material, null);
                // Add the new row before the add field button.
                parent_linearlayout_material.addView(rowView, parent_linearlayout_material.getChildCount() - 1);


                EditText edt_materialname = (EditText) rowView.findViewById(R.id.edt_materialname);
                listEdMaterialName.add(edt_materialname);

                final EditText edt_materialcost = (EditText) rowView.findViewById(R.id.edt_materialcost);
                listEdMaterialCost.add(edt_materialcost);


                if(str_material.equalsIgnoreCase("true"))
                { edt_materialname.setText(str_eachmaterialname);
                    edt_materialcost.setText(str_eachmaterialcost);
                    str_material="false";
                }


                Button delete_button_material = (Button) rowView.findViewById(R.id.delete_button_material);
                delete_button_material.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Log.d("Insidexxx","delete button member");
/*                        Log.d("Materialcostxxxxxxxx",edt_materialcost.getText().toString());
                        Double removedCost = Double.parseDouble(edt_materialcost.getText().toString());
                        grandSum = grandSum - removedCost;*/
                        //Log.d("grandsumxxxxx", String.valueOf(grandSum));
                        parent_linearlayout_material.removeView((View) v.getParent());

                        View rowView = (View) v.getParent();
                        final EditText edt_materialname = (EditText) rowView.findViewById(R.id.edt_materialname);
                        listEdMaterialName.remove(edt_materialname);

                        final EditText edt_materialcost = (EditText) rowView.findViewById(R.id.edt_materialcost);
                        listEdMaterialCost.remove(edt_materialcost);

                    }
                });



            }
        });
















        projectStatus = getIntent().getStringExtra("projstatus");
        projectId = getIntent().getStringExtra("projectId");

        if(projectStatus.equals("approved") || projectStatus.equals("completed") || projectStatus.equals("rejected") || projectStatus.equals("requested")){

            actionBarTV.setText("View Project Details");
            lnrlyt_projectTypeSpin.setVisibility(View.GONE);
            lnrlyt_projectTypeEdit.setVisibility(View.VISIBLE);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            btn_submit.setVisibility(View.GONE);

            flag = 0;

            ViewProject viewProject = new ViewProject(context);
            viewProject.execute();

        }
        else //pending , reapply,
            {
            actionBarTV.setText("Edit Project");

            lnrlyt_projectTypeSpin.setVisibility(View.VISIBLE);
            lnrlyt_projectTypeEdit.setVisibility(View.GONE);

            btn_submit.setVisibility(View.VISIBLE);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            flag = 1;

 /*           edt_projectType.setText("Health Care");
            edt_projectType.setTextSize(15);
            edt_title.setText("This is title");
            edt_title.setTextSize(15);
            edt_noOfBeneficiaries.setText("100");
            edt_noOfBeneficiaries.setTextSize(15);
            edt_objectives.setText("This is objectives");
            edt_objectives.setTextSize(15);
            edt_actionPlan.setText("This is Action Plan");
            edt_actionPlan.setTextSize(15);*/

            getProjectType();

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listMaterialName.clear();
                    listMaterialCost.clear();
                    for(int i = 0; i < listEdMaterialName.size(); i++){
                        String materialName = listEdMaterialName.get(i).getText().toString();
                        listMaterialName.add(materialName);
                    }
                    if(checkMandatoryFields()) {


                        if(!listEdMaterialCost.equals(null))
                        {

                            for (int i = 0; i < listEdMaterialCost.size(); i++) {

                                if (!listEdMaterialCost.get(i).getText().toString().equals("") && !listEdMaterialCost.get(i).getText().toString().isEmpty() && !listEdMaterialName.get(i).getText().toString().equals("") && !listEdMaterialName.get(i).getText().toString().isEmpty()) {
                                    String materialCost = listEdMaterialCost.get(i).getText().toString();
                                    listMaterialCost.add(materialCost);

                                    double sumval = Double.parseDouble(materialCost);
                                    grandSum = grandSum + sumval;
                                    Log.d("Value ", "Val " + materialCost);
                                }
                            }
                        }

                        for(int i = 0; i < listEdMemberName.size(); i++)
                        {
                            String memberName = listEdMemberName.get(i).getText().toString();
                            listMemberName.add(memberName);
                        }


                        if(!listEdMemberMailId.equals(null)) {
                            for (int i = 0; i < listEdMemberMailId.size(); i++) {
                                if (!listEdMemberMailId.get(i).getText().toString().equals("") && !listEdMemberMailId.get(i).getText().toString().isEmpty()) {
                                    String memberMailId = listEdMemberMailId.get(i).getText().toString();
                                    listMemberMailId.add(memberMailId);
                                }
                            }
                        }




                        SubmitProject submitProject = new SubmitProject(context);
                        submitProject.execute();
                    }
                }
            });

            /*ViewProject viewProject = new ViewProject(context);
            viewProject.execute();*/

            //initProjectTypeSpinner("Health Care");


        }






    /*    parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        btn_addMaterial = (Button) findViewById(R.id.btn_addMaterial);
        btn_addMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("insidepppp","onclick");
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.add_material_layout, null);
                // Add the new row before the add add_material_layout button.
                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);

            }
        });*/

/*        material.add("Books");
        material.add("Digital camera");
        material.add("Pen");
        material.add("Mouse");

        cost.add("100");
        cost.add("200");
        cost.add("300");
        cost.add("400");*/




   /*     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/






        clickstartproject_editdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment fromdateFragment = new DatePickerFragmentFromDate();
                fromdateFragment.show(getFragmentManager(), "Date Picker");

            }
        });


        clickendproject_editdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // settodate();
                DialogFragment dFragment = new DatePickerFragmentEndDate();
                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");


            }
        });






    }// end o Oncreate()

    private void initControls() {


    }

    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDate(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(EditProjectActivity.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }

    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }
    private void showSnack(boolean isConnected) {

        if (!isConnected) {

            AlertDialog.Builder adb = new AlertDialog.Builder(EditProjectActivity.this);
            //adb.setView(alertDialogView);

            adb.setTitle("Sorry! Not connected to the internet");

            adb.setIcon(android.R.drawable.ic_dialog_alert);

            adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });


            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                }
            });

            adb.setCancelable(true);
            adb.show();
        }

/*        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();*/
    }


    private boolean checkMandatoryFields()
    {
        String str_Title = edt_title.getText().toString();
        String str_beneficiaries = edt_noOfBeneficiaries.getText().toString();
        String str_beneficiaryName = edt_beneficiaries_name.getText().toString();

        String str_objectives = edt_objectives.getText().toString();
        String str_actionPlan = edt_actionPlan.getText().toString();
        String str_projectType = spin_projectType.getSelectedItem().toString();
        String str_placeOfImplement = edt_placeOfImpl.getText().toString();

        Boolean bool_title,bool_beneficiaries,bool_beneficiaryname,bool_objectives,bool_actionPlan;
        Boolean bool_placeofimplement,bool_implement,datevalidationresult1,datevalidationresult2;
        Boolean bool_membermailid,b_materialName;

       bool_title=bool_beneficiaries=bool_beneficiaryname=bool_objectives=bool_actionPlan=true;
        bool_placeofimplement=bool_implement=datevalidationresult1=datevalidationresult2=true;
        bool_membermailid=b_materialName=true;

        b_materialName=bruteforce(listMaterialName);
        if(!b_materialName){
            mainEdt_Material.setError("Material Name should not be duplicate");
            mainEdt_Material.requestFocus();
            Toast.makeText(context,"Material Name should not be duplicate",Toast.LENGTH_LONG).show();
            b_materialName= false;
        }
        if(str_Title.equals(null) || str_Title.equals("") || str_Title.isEmpty())
        {
            edt_title.setError("Enter title of the project");
            edt_title.requestFocus();
            bool_title=false;
            //return false;
        }

        if(str_beneficiaries.equals(null) || str_beneficiaries.equals("") || str_beneficiaries.isEmpty())
        {
            edt_noOfBeneficiaries.setError("Enter no. of beneficiaries");
            edt_noOfBeneficiaries.requestFocus();
            bool_beneficiaries=false;
           // return false;
        }

        if(str_beneficiaryName.equals(null) || str_beneficiaryName.equals("") || str_beneficiaryName.isEmpty()){
            edt_beneficiaries_name.setError("Enter beneficiaryName");
            edt_beneficiaries_name.requestFocus();
            bool_beneficiaryname=false;
            //return false;
        }

        if(str_placeOfImplement.equals(null) || str_placeOfImplement.equals("") || str_placeOfImplement.isEmpty()){
            edt_placeOfImpl.setError("Enter Place Of Implementation");
            edt_placeOfImpl.requestFocus();
            bool_implement=false;
            //return false;
        }



        if(str_objectives.equals(null) || str_objectives.equals("") || str_objectives.isEmpty()){
            edt_objectives.setError("Enter objective of the project");
            edt_objectives.requestFocus();
            bool_objectives=false;
            return false;
        }
        if(str_actionPlan.equals(null) || str_actionPlan.equals("") || str_actionPlan.isEmpty())
        {
            edt_actionPlan.setError("Enter action plan of the project");
            edt_actionPlan.requestFocus();
            bool_actionPlan=false;
            return false;
        }


        if(clickstartproject_editdate_tv.getText().toString().length()==0
                ||clickendproject_editdate_tv.getText().toString().length()==0
                ||clickstartproject_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00")
                ||clickendproject_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00")     )
        {
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            datevalidationresult1=false;
        }


        if(clickstartproject_editdate_tv.getText().toString().equalsIgnoreCase("Click for calendar")||
                clickendproject_editdate_tv.getText().toString().equalsIgnoreCase("Click for calendar"))
        {
            Toast.makeText(getApplicationContext(), "Kindly enter the date", Toast.LENGTH_SHORT).show();
            datevalidationresult1=false;
        }

        //if((yyyyMMdd_startdate.toString().length()!=0)&&(yyyyMMdd_enddate.toString().length()!=0) )

       /* if(clickstartproject_editdate_tv.getText().toString().contains("0123456789-")&&
                clickendproject_editdate_tv.getText().toString().contains("0123456789-"))*/
       // {
          //  if ((clickstartproject_editdate_tv.getText().toString().length() == 0) && (clickendproject_editdate_tv.getText().toString().length() == 0)) {
        /*if(date1.compareTo(date2)<0){ //0 comes when two date are same,
            //1 comes when date1 is higher then date2
            //-1 comes when date1 is lower then date2 }*/



        if( !(clickstartproject_editdate_tv.getText().toString().length()==0)
                        &&!(clickendproject_editdate_tv.getText().toString().length()==0)
                        &&!(clickstartproject_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00"))
                &&!(clickendproject_editdate_tv.getText().toString().equalsIgnoreCase("0000-00-00")))
        {
                SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

                try {
                    Date fromdate = mdyFormat.parse(clickstartproject_editdate_tv.getText().toString());
                    Date todate = mdyFormat.parse(clickendproject_editdate_tv.getText().toString());

                    if (fromdate.compareTo(todate) <= 0) {
                        datevalidationresult2 = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "Kindly enter valid date", Toast.LENGTH_SHORT).show();
                        datevalidationresult2 = false;
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }//end of if

        //}


        if(!listEdMemberMailId.equals(null)) {
            Log.d("Insidedddd", "listEdMemberMailIdnotnull");
            for (int i = 0; i < listEdMemberMailId.size(); i++) {
                /*        if (listEdMemberMailId.get(i).getError()!=null) {
                 *//*          String memberMailId = listEdMemberMailId.get(i).getText().toString();
                    listMemberMailId.add(memberMailId);*//*
                    //return true;
                    countEmailIds++;
                }*/

                memberMailId = listEdMemberMailId.get(i).getText().toString().trim();
                if (memberMailId.length() > 0) {
                    if (!isValidEmail(memberMailId)) {
                        listEdMemberMailId.get(i).setError("Enter correct mail id");
                        listEdMemberMailId.get(i).requestFocus();
                        bool_membermailid=false;
                    }
                }
            }
        }





        if(bool_title&&bool_beneficiaries&&bool_beneficiaryname&&bool_objectives&&bool_actionPlan
        &&bool_placeofimplement&&bool_implement&&datevalidationresult1&&datevalidationresult2
        &&bool_actionPlan&&bool_membermailid&&b_materialName )
        {
            return true;
        }
        else{
            return false;
        }


    }



    public class SubmitProject extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //private ProgressBar progressBar;
        private ProgressDialog progressDialog;

         SubmitProject (Context ctx){
            context = ctx;
             progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = updateProjectDetails();

            Log.d("Soap response issssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
       /*     progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Submitting");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            progressDialog.dismiss();

            if(result!=null) {
                if (result.toString().equals("Error")) {
                    Toast.makeText(context, "Error occured while saving to database", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Project Updated Successfully", Toast.LENGTH_LONG).show();
                    Intent ittEditProjToProjStatus = new Intent(EditProjectActivity.this, ProjectDetails.class);
                    startActivity(ittEditProjToProjStatus);
                }
            }
            //progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive updateProjectDetails() {
        String METHOD_NAME = "PSUpdateprojectDetails1";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/PSUpdateprojectDetails1";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");

            long projectIds = (long) Integer.valueOf(projectId);
            request.addProperty("PDId",projectIds);
            Log.d("PDIdssssssxxxx", String.valueOf(projectIds));
            request.addProperty("Lead_Id",str_leadId);
            request.addProperty("Title",edt_title.getText().toString());
            Log.d("Titlesssssxxxx",edt_title.getText().toString());
            int themeId = hashProjTypeId.get(spin_projectType.getSelectedItem().toString());
            Log.d("themeidsssssxxxx", String.valueOf(themeId));
            request.addProperty("Theme",String.valueOf(themeId));
            long beneficiaryNo = (long) Integer.valueOf(edt_noOfBeneficiaries.getText().toString());
            Log.d("beneficiaryNosssxxxx", String.valueOf(beneficiaryNo));
            request.addProperty("BeneficiaryNo",beneficiaryNo);

            Log.d("beneficiaryName",edt_beneficiaries_name.getText().toString());
            request.addProperty("Beneficiaries",edt_beneficiaries_name.getText().toString());


            Log.d("objectivesxxxxxx",edt_objectives.getText().toString());
            request.addProperty("Objectives",edt_objectives.getText().toString());
            Log.d("actionplansxxxxxx",edt_actionPlan.getText().toString());
            request.addProperty("ActionPlan",edt_actionPlan.getText().toString());

            request.addProperty("placeofimplimentation",edt_placeOfImpl.getText().toString());

            request.addProperty("CurrentSituation",edt_currentSituation.getText().toString());



          /*  request.addProperty("StartDate",yyyyMMdd_startdate.toString());//<StartDate>string</StartDate>
            request.addProperty("EndDate",yyyyMMdd_enddate.toString());//<EndDate>string</EndDate>
*/

            request.addProperty("StartDate",clickstartproject_editdate_tv.getText().toString());//<StartDate>string</StartDate>
            request.addProperty("EndDate",clickendproject_editdate_tv.getText().toString());//<EndDate>string</EndDate>



            JSONObject jsonObject = new JSONObject();
            for(int k=0;k<listMaterialName.size() && k<listMaterialCost.size();k++)
            {
                jsonObject.put(listMaterialName.get(k),listMaterialCost.get(k));
            }
            request.addProperty("materials",jsonObject.toString());//<materials>string</materials>

            JSONObject jsonObjectMember = new JSONObject();

            for(int k=0;k<listMemberName.size() && k<listMemberMailId.size();k++)
            {
                jsonObjectMember.put(listMemberName.get(k),listMemberMailId.get(k));
                Log.e("member",listMemberName.get(k).toString());
            }

            Log.d("jsonObjectMember",jsonObjectMember.toString());
            request.addProperty("teammembers",jsonObjectMember.toString());//<teammembers>string</teammembers>


            request.addProperty("Student_Id",str_RegistrationId); // <Student_Id>string</Student_Id>


            float float_grandsum= (float) grandSum;
            int int_grandSum= (int) grandSum;
            request.addProperty("Budget",int_grandSum);
            //float but even though we are sending int but its accepting the result
            //6july2019 12:36pm





            Log.d("Requestisxxxxx",request.toString());


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


                //SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("soapupdateProjectDtlsss",envelope.getResponse().toString());

                //return null;

                return response;

            }catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }


    public class ViewProject extends AsyncTask<Void, Void, SoapObject> {

        AlertDialog alertDialog;
        private ProgressBar progressBar;
        private ProgressDialog progressDialog;

        ViewProject (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getProjectDetails();

            Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result)
        {
            SoapPrimitive S_ProjectTitle, S_ThemeName, S_BeneficiaryNo, S_Objective, S_actionPlan, S_ManagerComments, S_MaterialName, S_MaterialCost, S_MemberName , S_MemberMailId ,S_TotalCost, S_Status, S_Beneficiarieslist,S_PlaceOfImplement,S_CurrentSituation;
            Object O_ProjectTitle, O_ThemeName, O_BeneficiaryNo, O_Objective, O_actionPlan, O_ManagerComments, O_MaterialName, O_MaterialCost, O_MemberName, O_MemberMailId,O_TotalCost, O_Status,O_BeneficiariesList,O_PlaceOfImplement,O_CurrentSituation;
            String str_ProjectTitle = null, str_ThemeName = null, str_BeneficiaryNo = null, str_Objective = null, str_actionPlan = null, str_ManagerComments = null, str_materialName = null, str_materialCost = null, str_memberName=null, str_memberMailId = null, str_status = null, str_beneficiariesList = null, str_placeOfImpl = null, str_currentSituation = null;

            SoapPrimitive S_startdate,S_enddate;
            Object O_startdate,O_enddate = null;
            String str_startdate,str_enddate;

            Log.d("Resultissssss",result.toString());



            O_Status = result.getProperty("status");
            if (!O_Status.toString().equals("anyType") && !O_Status.toString().equals(null) && !O_Status.toString().equals("anyType{}")) {
                S_Status = (SoapPrimitive) result.getProperty("status");
                Log.d("Statusssssssss", S_Status.toString());
                str_status = S_Status.toString();
            }

            if (str_status.equalsIgnoreCase("success"))
            {
                if (flag == 0) // for approved completed rejected requested
                {
                    O_ProjectTitle = result.getProperty("Title");
                    if (!O_ProjectTitle.toString().equals("anyType") && !O_ProjectTitle.toString().equals(null) && !O_ProjectTitle.toString().equals("anyType{}")) {
                        S_ProjectTitle = (SoapPrimitive) result.getProperty("Title");
                        Log.d("Titlesssssss", S_ProjectTitle.toString());
                        str_ProjectTitle = S_ProjectTitle.toString();
                    }

                    O_ThemeName = result.getProperty("ThemeName");
                    if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}")) {
                        S_ThemeName = (SoapPrimitive) result.getProperty("ThemeName");
                        Log.d("ThemeNamessss", S_ThemeName.toString());
                        str_ThemeName = S_ThemeName.toString();
                    }

                    O_BeneficiaryNo = result.getProperty("BeneficiaryNo");
                    if (!O_BeneficiaryNo.toString().equals("anyType") && !O_BeneficiaryNo.toString().equals(null) && !O_BeneficiaryNo.toString().equals("anyType{}")) {
                        S_BeneficiaryNo = (SoapPrimitive) result.getProperty("BeneficiaryNo");
                        Log.d("BeneficiaryNo", S_BeneficiaryNo.toString());
                        str_BeneficiaryNo = S_BeneficiaryNo.toString();
                    }

                    if(result.toString().contains("BeneficiariesList")) {
                        O_BeneficiariesList = result.getProperty("BeneficiariesList");
                        if (!O_BeneficiariesList.toString().equals("anyType") && !O_BeneficiariesList.toString().equals(null) && !O_BeneficiariesList.toString().equals("anyType{}")) {
                            S_Beneficiarieslist = (SoapPrimitive) result.getProperty("BeneficiariesList");
                            Log.d("Beneficiarieslist", S_Beneficiarieslist.toString());
                            str_beneficiariesList = S_Beneficiarieslist.toString();
                        }
                    }


                    if(result.toString().contains("Placeofimplement")) {

                        O_PlaceOfImplement = result.getProperty("Placeofimplement");
                        if (!O_PlaceOfImplement.toString().equals("anyType") && !O_PlaceOfImplement.toString().equals(null) && !O_PlaceOfImplement.toString().equals("anyType{}")) {
                            S_PlaceOfImplement = (SoapPrimitive) result.getProperty("Placeofimplement");
                            Log.d("PlaceOfImplementation", S_PlaceOfImplement.toString());
                            str_placeOfImpl = S_PlaceOfImplement.toString();
                        }
                    }




                    O_Objective = result.getProperty("Objectives");
                    if (!O_Objective.toString().equals("anyType") && !O_Objective.toString().equals(null) && !O_Objective.toString().equals("anyType{}")) {
                        S_Objective = (SoapPrimitive) result.getProperty("Objectives");
                        Log.d("Objectivess", S_Objective.toString());
                        str_Objective = S_Objective.toString();
                    }

                    O_actionPlan = result.getProperty("ActionPlan");
                    if (!O_actionPlan.toString().equals("anyType") && !O_actionPlan.toString().equals(null) && !O_actionPlan.toString().equals("anyType{}")) {
                        S_actionPlan = (SoapPrimitive) result.getProperty("ActionPlan");
                        Log.d("ActionPlansss", S_actionPlan.toString());
                        str_actionPlan = S_actionPlan.toString();
                    }

                    O_ManagerComments = result.getProperty("ManagerComments");
                    if (!O_ManagerComments.toString().equals("anyType") && !O_ManagerComments.toString().equals(null) && !O_ManagerComments.toString().equals("anyType{}")) {
                        S_ManagerComments = (SoapPrimitive) result.getProperty("ManagerComments");
                        Log.d("Manager Comments", S_ManagerComments.toString());
                        str_ManagerComments = S_ManagerComments.toString();
                    }else{
                        Log.d("Insideelse","EditProjectActivity");
                        pmcomments_txt.setVisibility(View.GONE);
                        txt_PMComments.setVisibility(View.GONE);
                    }

                    O_TotalCost = result.getProperty("Amount");
                    if (!O_TotalCost.toString().equals("anyType") && !O_TotalCost.toString().equals(null) && !O_TotalCost.toString().equals("anyType{}")) {
                        S_TotalCost = (SoapPrimitive) result.getProperty("Amount");
                        Log.d("Total Cost", S_TotalCost.toString());
                        str_totalCost = S_TotalCost.toString();
                    }

                    O_CurrentSituation = result.getProperty("CurrentSituation");
                    if (!O_CurrentSituation.toString().equals("anyType") && !O_CurrentSituation.toString().equals(null) && !O_CurrentSituation.toString().equals("anyType{}")) {
                        S_CurrentSituation = (SoapPrimitive) result.getProperty("CurrentSituation");
                        Log.d("CurrentSituation", S_CurrentSituation.toString());
                        str_currentSituation = S_CurrentSituation.toString();
                    }else{
                        str_currentSituation = " ";
                    }






                    O_startdate= result.getProperty("ProjectStartDate");
                    Log.e("startDate",O_startdate.toString());
                    if (O_startdate.toString().equals("anyType{}")||O_startdate.toString().equals("0000-00-00")) {
                      //  S_startdate = O_startdate;

                       // Log.e("startDate",S_startdate.toString());

                        str_startdate = "Click for Calendar";
                    }else{


                        str_startdate = O_startdate.toString();
                    }

                    // Toast.makeText(context,""+O_startdate.toString(),Toast.LENGTH_LONG).show();
                    clickstartproject_editdate_tv.setText(str_startdate.toString());


                    O_enddate= result.getProperty("ProjectEndDate");
                    if (O_enddate.toString().equals("anyType{}")||O_enddate.toString().equals("0000-00-00")) {
                       /* S_enddate = (SoapPrimitive) result.getProperty("ProjectEndDate");

                        Log.e("endDate",S_enddate.toString());*/
                        str_enddate = "Click for Calendar";
                    }else{
                        str_enddate = O_enddate.toString();
                    }
                    clickendproject_editdate_tv.setText(str_enddate.toString());


                    if(str_startdate.toString().equals("anyType{}")
                            || str_startdate.toString().equals(null)
                            ||str_startdate.toString().equals("0000-00-00")
                            ||str_startdate.toString().equalsIgnoreCase("Click for Calendar")
                            ||str_enddate.toString().equals("anyType{}")
                            ||str_enddate.toString().equals(null)
                            ||str_enddate.toString().equals("0000-00-00")
                            ||str_enddate.toString().equalsIgnoreCase("Click for Calendar"))
                    {
                        editstudentdays_linearlayout.setVisibility(View.GONE);
                        editproject_date_linearlayout.setVisibility(View.GONE);
                        editproject_datelabel_linearlayout.setVisibility(View.GONE);


                    }else
                    {


                        try{
                            int days_count = Days.daysBetween(new LocalDate(str_startdate), new LocalDate(str_enddate)).getDays();
                            int int_days=days_count+1;
                            Log.e("days",String.valueOf(int_days));
                            editstudent_numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
                        }catch(Throwable t)
                        {
                            editstudent_numberofdays_tv.setText(" ");
                        }
                    }











                    edt_projectType.setText(str_ThemeName);
                    edt_projectType.setFocusable(false);
                    edt_projectType.setInputType(0);
                    edt_projectType.setTextSize(15);

                    edt_title.setText(str_ProjectTitle);
                    edt_title.setFocusable(false);
                    edt_title.setInputType(0);
                    edt_title.setTextSize(15);

                    edt_noOfBeneficiaries.setText(str_BeneficiaryNo);
                    edt_noOfBeneficiaries.setFocusable(false);
                    edt_noOfBeneficiaries.setInputType(0);
                    edt_noOfBeneficiaries.setTextSize(15);


                    edt_beneficiaries_name.setText(str_beneficiariesList);
                    edt_beneficiaries_name.setFocusable(false);
                    edt_beneficiaries_name.setInputType(0);
                    edt_beneficiaries_name.setTextSize(15);

                    edt_placeOfImpl.setText(str_placeOfImpl);
                    edt_placeOfImpl.setFocusable(false);
                    edt_placeOfImpl.setInputType(0);
                    edt_placeOfImpl.setTextSize(15);



                    edt_objectives.setText(str_Objective);
                    edt_objectives.setFocusable(false);
                    edt_objectives.setInputType(0);
                    edt_objectives.setTextSize(15);

                    edt_actionPlan.setText(str_actionPlan);
                    edt_actionPlan.setFocusable(false);
                    edt_actionPlan.setInputType(0);
                    edt_actionPlan.setTextSize(15);

                    edt_currentSituation.setText(str_currentSituation);
                    edt_currentSituation.setFocusable(false);
                    edt_currentSituation.setInputType(0);
                    edt_currentSituation.setTextSize(15);

                    txt_PMComments.setText(str_ManagerComments);

                    if(result.toString().contains("Members"))
                    {
                        SoapObject memberList = (SoapObject) result.getProperty("Members");
                        for (int i = 0; i < memberList.getPropertyCount(); i++)
                        {
                            SoapObject vmMemberlist = (SoapObject) memberList.getProperty(i);

                            O_MemberName = vmMemberlist.getProperty("MemberName");
                            if (!O_MemberName.toString().equals("anyType{}") && !O_MemberName.toString().equals(null) && !O_MemberName.toString().equals("anyType")) {
                                S_MemberName = (SoapPrimitive) vmMemberlist.getProperty("MemberName");
                                Log.d("MemberNamess", S_MemberName.toString());
                                str_memberName = S_MemberName.toString();

                                lstMemberName.add(str_memberName);
                            }

                            O_MemberMailId = vmMemberlist.getProperty("MemberEmail");
                            if (!O_MemberMailId.toString().equals("anyType") && !O_MemberMailId.toString().equals(null) && !O_MemberMailId.toString().equals("anyType{}")) {
                                S_MemberMailId = (SoapPrimitive) vmMemberlist.getProperty("MemberEmail");
                                Log.d("MemberMailId", S_MemberMailId.toString());
                                str_memberMailId = S_MemberMailId.toString();

                                lstMemberMailId.add(str_memberMailId);
                            }
                        }
                        initMembers();

                    }

                    SoapObject materialList = (SoapObject) result.getProperty("materials");
                    for (int i = 0; i < materialList.getPropertyCount(); i++) {
                        SoapObject vmmateriallist = (SoapObject) materialList.getProperty(i);

                        O_MaterialName = vmmateriallist.getProperty("MeterialName");
                        if (!O_MaterialName.toString().equals("anyType{}") && !O_MaterialName.toString().equals(null)) {
                            S_MaterialName = (SoapPrimitive) vmmateriallist.getProperty("MeterialName");
                            Log.d("MaterialNamesssss", S_MaterialName.toString());
                            str_materialName = S_MaterialName.toString();

                            lstMaterialName.add(str_materialName);
                        }

                        O_MaterialCost = vmmateriallist.getProperty("MeterialCost");
                        if (!O_MaterialCost.toString().equals("anyType") && !O_MaterialCost.toString().equals(null) && !O_MaterialCost.toString().equals("anyType{}")) {
                            S_MaterialCost = (SoapPrimitive) vmmateriallist.getProperty("MeterialCost");
                            Log.d("MaterialCostsssss", S_MaterialCost.toString());
                            str_materialCost = S_MaterialCost.toString();

                            lstMaterialCost.add(str_materialCost);
                        }
                    }
                    initMaterials();




                } else

                    {

                    Log.d("Inside", "elsexxxxxx");

                    Log.d("Resultssssss", result.toString());

                    O_ProjectTitle = result.getProperty("Title");
                    if (!O_ProjectTitle.toString().equals("anyType{}") && !O_ProjectTitle.toString().equals(null)) {
                        S_ProjectTitle = (SoapPrimitive) result.getProperty("Title");
                        Log.d("Titlesssssss", S_ProjectTitle.toString());
                        str_ProjectTitle = S_ProjectTitle.toString();
                    }

                    O_ThemeName = result.getProperty("ThemeName");
                    if (!O_ThemeName.toString().equals("anyType{}") && !O_ThemeName.toString().equals(null)) {
                        S_ThemeName = (SoapPrimitive) result.getProperty("ThemeName");
                        Log.d("ThemeNamessss", S_ThemeName.toString());
                        str_ThemeName = S_ThemeName.toString();
                    }

                    O_BeneficiaryNo = result.getProperty("BeneficiaryNo");
                    if (!O_BeneficiaryNo.toString().equals("anyType{}") && !O_BeneficiaryNo.toString().equals(null)) {
                        S_BeneficiaryNo = (SoapPrimitive) result.getProperty("BeneficiaryNo");
                        Log.d("BeneficiaryNo", S_BeneficiaryNo.toString());
                        str_BeneficiaryNo = S_BeneficiaryNo.toString();
                    }

                    if(result.toString().contains("BeneficiariesList")) {
                        O_BeneficiariesList = result.getProperty("BeneficiariesList");
                        if (!O_BeneficiariesList.toString().equals("anyType") && !O_BeneficiariesList.toString().equals(null) && !O_BeneficiariesList.toString().equals("anyType{}")) {
                            S_Beneficiarieslist = (SoapPrimitive) result.getProperty("BeneficiariesList");
                            Log.d("Beneficiarieslist", S_Beneficiarieslist.toString());
                            str_beneficiariesList = S_Beneficiarieslist.toString();
                        }
                    }

                    if(result.toString().contains("Placeofimplement")) {
                        O_PlaceOfImplement = result.getProperty("Placeofimplement");
                        if (!O_PlaceOfImplement.toString().equals("anyType") && !O_PlaceOfImplement.toString().equals(null) && !O_PlaceOfImplement.toString().equals("anyType{}")) {
                            S_PlaceOfImplement = (SoapPrimitive) result.getProperty("Placeofimplement");
                            Log.d("PlaceOfImplementation", S_PlaceOfImplement.toString());
                            str_placeOfImpl = S_PlaceOfImplement.toString();
                        }
                    }





                    O_Objective = result.getProperty("Objectives");
                    if (!O_Objective.toString().equals("anyType{}") && !O_Objective.toString().equals(null)) {
                        S_Objective = (SoapPrimitive) result.getProperty("Objectives");
                        Log.d("Objectivess", S_Objective.toString());
                        str_Objective = S_Objective.toString();
                    }

                    O_actionPlan = result.getProperty("ActionPlan");
                    if (!O_actionPlan.toString().equals("anyType{}") && !O_actionPlan.toString().equals(null)) {
                        S_actionPlan = (SoapPrimitive) result.getProperty("ActionPlan");
                        Log.d("ActionPlansss", S_actionPlan.toString());
                        str_actionPlan = S_actionPlan.toString();
                    }

                    O_ManagerComments = result.getProperty("ManagerComments");
                    if (!O_ManagerComments.toString().equals("anyType{}") && !O_ManagerComments.toString().equals(null)) {
                        S_ManagerComments = (SoapPrimitive) result.getProperty("ManagerComments");
                        Log.d("Manager Comments", S_ManagerComments.toString());
                        str_ManagerComments = S_ManagerComments.toString();
                    }else{
                        Log.d("Insideelse","EditProjectActivity");
                        pmcomments_txt.setVisibility(View.GONE);
                        txt_PMComments.setVisibility(View.GONE);
                    }

                    O_TotalCost = result.getProperty("Amount");
                    if (!O_TotalCost.toString().equals("anyType{}") && !O_TotalCost.toString().equals(null)) {
                        S_TotalCost = (SoapPrimitive) result.getProperty("Amount");
                        Log.d("Total Cost", S_TotalCost.toString());
                        str_totalCost = S_TotalCost.toString();
                    }

                    O_CurrentSituation = result.getProperty("CurrentSituation");
                    if (!O_CurrentSituation.toString().equals("anyType") && !O_CurrentSituation.toString().equals(null) && !O_CurrentSituation.toString().equals("anyType{}")) {
                        S_CurrentSituation = (SoapPrimitive) result.getProperty("CurrentSituation");
                        Log.d("CurrentSituationxxxx", S_CurrentSituation.toString());
                        str_currentSituation = S_CurrentSituation.toString();
                    }else{
                        str_currentSituation = " ";
                    }



                    O_startdate= result.getProperty("ProjectStartDate");
                    Log.e("startDate",O_startdate.toString());
                    if (O_startdate.toString().equals("anyType{}")||O_startdate.toString().equals("0000-00-00")) {

                       // S_startdate = (SoapPrimitive) result.getProperty("ProjectStartDate");
                        //Log.e("startDate",S_startdate.toString());


                        str_startdate = "Click for Calendar";

                    }else{

                        str_startdate = O_startdate.toString();
                    }

                    // Toast.makeText(context,""+O_startdate.toString(),Toast.LENGTH_LONG).show();
                    clickstartproject_editdate_tv.setText(str_startdate.toString());


                    O_enddate= result.getProperty("ProjectEndDate");
                    if (O_enddate.toString().equals("anyType{}")||O_enddate.toString().equals("0000-00-00"))
                    {
                        /*S_enddate = (SoapPrimitive) result.getProperty("ProjectEndDate");
                        Log.e("endDate",S_enddate.toString());*/

                        str_enddate = "Click for Calendar";
                        editproject_date_linearlayout.setVisibility(View.GONE);
                    }else{
                        str_enddate = O_enddate.toString();
                        yyyyMMdd_enddate=O_enddate.toString();

                    }
                    clickendproject_editdate_tv.setText(str_enddate.toString());




                        //vijay



                        if(!str_startdate.toString().equals("anyType{}")
                                && !str_startdate.toString().equals(null) &&
                                !str_startdate.toString().equals("0000-00-00")
                                && !str_startdate.toString().equals("Click for Calendar")
                                &&!str_enddate.toString().equals("anyType{}")
                                && !str_enddate.toString().equals(null) &&
                                !str_enddate.toString().equals("0000-00-00")
                                && !str_enddate.toString().equals("Click for Calendar"))
                        {
                            try{
                                int days_count = Days.daysBetween(new LocalDate(str_startdate), new LocalDate(str_enddate)).getDays();
                                int int_days=days_count+1;
                                Log.e("days",String.valueOf(int_days));
                                editstudent_numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
                            }catch(Throwable t)
                            {
                                editstudent_numberofdays_tv.setText(" ");
                            }

                        }else
                        { editstudentdays_linearlayout.setVisibility(View.GONE);

                        }






                        //vijay

                    edt_currentSituation.setText(str_currentSituation);
                    edt_currentSituation.setTextSize(15);


        /*      edt_projectType.setText(str_ThemeName);
                edt_projectType.setFocusable(false);
                edt_projectType.setInputType(0);
                edt_projectType.setTextSize(15);*/


                    edt_title.setText(str_ProjectTitle);
                    edt_title.setTextSize(15);

                    edt_noOfBeneficiaries.setText(str_BeneficiaryNo);
                    edt_noOfBeneficiaries.setTextSize(15);

                    edt_beneficiaries_name.setText(str_beneficiariesList);
                    edt_beneficiaries_name.setTextSize(15);

                        edt_placeOfImpl.setText(str_placeOfImpl);
                        edt_placeOfImpl.setTextSize(15);

                    edt_objectives.setText(str_Objective);
                    edt_objectives.setTextSize(15);

                    edt_actionPlan.setText(str_actionPlan);
                    edt_actionPlan.setTextSize(15);

                    txt_PMComments.setText(str_ManagerComments);

                    if(result.toString().contains("Members"))
                    {
                        SoapObject memberList = (SoapObject) result.getProperty("Members");
                        for (int i = 0; i < memberList.getPropertyCount(); i++)
                        {
                            SoapObject vmMemberlist = (SoapObject) memberList.getProperty(i);

                            O_MemberName = vmMemberlist.getProperty("MemberName");
                            if (!O_MemberName.toString().equals("anyType{}") && !O_MemberName.toString().equals(null)) {
                                S_MemberName = (SoapPrimitive) vmMemberlist.getProperty("MemberName");
                                Log.d("MemberNamess", S_MemberName.toString());
                                str_memberName = S_MemberName.toString();

                                lstMemberName.add(str_memberName);
                            }

                            O_MemberMailId = vmMemberlist.getProperty("MemberEmail");
                            if (!O_MemberMailId.toString().equals("anyType") && !O_MemberMailId.toString().equals(null) && !O_MemberMailId.toString().equals("anyType{}")) {
                                S_MemberMailId = (SoapPrimitive) vmMemberlist.getProperty("MemberEmail");
                                Log.d("MemberMailId", S_MemberMailId.toString());
                                str_memberMailId = S_MemberMailId.toString();

                                lstMemberMailId.add(str_memberMailId);
                            }
                        }
                        initMembers();


                    }//

                    SoapObject materialList = (SoapObject) result.getProperty("materials");
                    for (int i = 0; i < materialList.getPropertyCount(); i++) {
                        SoapObject vmmateriallist = (SoapObject) materialList.getProperty(i);

                        O_MaterialName = vmmateriallist.getProperty("MeterialName");
                        if (!O_MaterialName.toString().equals("anyType{}") && !O_MaterialName.toString().equals(null)) {
                            S_MaterialName = (SoapPrimitive) vmmateriallist.getProperty("MeterialName");
                            Log.d("MaterialNamesssss", S_MaterialName.toString());
                            str_materialName = S_MaterialName.toString();

                            lstMaterialName.add(str_materialName);
                        }

                        O_MaterialCost = vmmateriallist.getProperty("MeterialCost");
                        if (!O_MaterialCost.toString().equals("anyType") && !O_MaterialCost.toString().equals(null) && !O_MaterialCost.toString().equals("anyType{}")) {
                            S_MaterialCost = (SoapPrimitive) vmmateriallist.getProperty("MeterialCost");
                            Log.d("MaterialCostsssss", S_MaterialCost.toString());
                            str_materialCost = S_MaterialCost.toString();

                            lstMaterialCost.add(str_materialCost);
                        }
                    }


                    initProjectTypeSpinner(str_ThemeName);


                    initMaterials();
                }

        }
            else{
                Toast.makeText(context,str_status,Toast.LENGTH_LONG).show();
                lnrlyt_edtProject.setVisibility(View.GONE);
            }

            //progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getProjectDetails()
    {
        String METHOD_NAME = "PSGetProjectDetails";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/PSGetProjectDetails";
        String NAMESPACE = "http://mis.leadcampus.org/";


           try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
               Log.e("leadID pdid:",""+str_leadId+","+projectId);
            request.addProperty("leadId",str_leadId);
            request.addProperty("PDId",projectId);

            Log.d("projectIdsssss",projectId);
            Log.d("LeadIDsss",str_leadId);
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
                 runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
        }
        return null;

    }




    private void initProjectTypeSpinner(String projectTypeName) {
        final ArrayList projType = new ArrayList();

        Set<String> projTypeList = hashProjTypeId.keySet();


        for(String key: projTypeList){
            projType.add(key);
        }

/*        projType.add("Education");
        projType.add("Health Care");
        projType.add("Sports");*/

        ArrayAdapter dataAdapter2 = new ArrayAdapter(context, R.layout.simple_spinner_items, projType);



        // Drop down layout style - list view with radio button
        //dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        spin_projectType.setAdapter(dataAdapter2);
        //spin_projectType.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        //spin_projectType.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));

        if(projectTypeName!="" || projectTypeName!=null) {
            for (int i = 0; i < spin_projectType.getCount(); i++) {
                if (spin_projectType.getItemAtPosition(i).equals(projectTypeName)) {
                    spin_projectType.setSelection(i);
                    break;
                }
            }
        }

    }


    public void initMaterials()
    {
        TableLayout stk = (TableLayout) findViewById(R.id.table_material);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        String matrl_name = "<u> Material Name </u>";
        tv0.setText(Html.fromHtml(matrl_name));
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(5, 10, 5, 5);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        String matrl_cost = "<u> Cost </u>";
        tv1.setText(Html.fromHtml(matrl_cost));
        tv1.setTextColor(Color.BLACK);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setPadding(50, 10, 0, 5);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        for (int i = 0; i < lstMaterialName.size() && i < lstMaterialCost.size(); i++)
        {
            //cost
            str_material="true";
            str_eachmaterialname=lstMaterialName.get(i).toString();
            str_eachmaterialcost=lstMaterialCost.get(i).toString();

            editproject_addmaterial_ib.performClick();



            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(lstMaterialName.get(i));
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.LEFT);
            t1v.setPadding(5, 5, 5, 5);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(lstMaterialCost.get(i));
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setPadding(50, 5, 0, 5);
            tbrow.addView(t2v);

            stk.addView(tbrow);
        }

        TableRow tbrow = new TableRow(this);
        TextView t1v = new TextView(this);
        t1v.setText("Total");
        t1v.setTypeface(null, Typeface.BOLD);
        t1v.setTextColor(Color.BLACK);
        t1v.setGravity(Gravity.LEFT);
        t1v.setPadding(5, 5, 5, 5);
        tbrow.addView(t1v);

        TextView t2v = new TextView(this);
        t2v.setText(str_totalCost);
        t2v.setTypeface(null, Typeface.BOLD);
        t2v.setTextColor(Color.BLACK);
        t2v.setGravity(Gravity.CENTER);
        t2v.setPadding(50, 5, 0, 5);
        tbrow.addView(t2v);

        stk.addView(tbrow);
    }


    public void initMembers() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_teamMember);
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        String member_name = "<u> Member Name </u>";
        tv0.setText(Html.fromHtml(member_name));
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTypeface(null, Typeface.BOLD);
        tv0.setPadding(5, 10, 5, 5);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        String member_MailId = "<u> Mail Id </u>";
        tv1.setText(Html.fromHtml(member_MailId));
        tv1.setTextColor(Color.BLACK);
        tv1.setGravity(Gravity.CENTER);
        tv1.setTypeface(null, Typeface.BOLD);
        tv1.setPadding(50, 10, 0, 5);
        tbrow0.addView(tv1);
        stk.addView(tbrow0);
        for (int i = 0; i < lstMemberName.size() && i < lstMemberMailId.size(); i++)
        {
            str_add="true";
            str_membername=lstMemberName.get(i).toString();
            str_membermailid=lstMemberMailId.get(i).toString();
            //vijay
            editproject_add_field_button.performClick();


            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);
            t1v.setText(lstMemberName.get(i));
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.LEFT);
            t1v.setPadding(5, 5, 5, 5);
            tbrow.addView(t1v);

            TextView t2v = new TextView(this);
            t2v.setText(lstMemberMailId.get(i));
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setPadding(50, 5, 0, 5);
            tbrow.addView(t2v);

            stk.addView(tbrow);


        }

    }



    private void getProjectType()
    {
        ProjectType projType = new ProjectType(context);
        projType.execute();
    }

    public class ProjectType extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        private ProgressBar progressBar;
        private ProgressDialog progressDialog;

        ProjectType (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params)
        {
            //String versionCode = (String) params[2];
            String METHOD_NAME = "GetThemeList";
            String SOAP_ACTION1 = "http://mis.leadcampus.org/GetThemeList";
            String NAMESPACE = "http://mis.leadcampus.org/";


            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);


                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

                envelope.dotNet = true;
                //Set output SOAP object
                envelope.setOutputSoapObject(request);
                //Create HTTP call object
                //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
                HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
                //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("soap responseyyyyyyy",response.toString());

                    return response;

                }catch(OutOfMemoryError ex){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception t) {
                    Log.e("request fail", "> " + t.getMessage().toString());

                    final String exceptionStr = t.getMessage().toString();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            catch(OutOfMemoryError ex){
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;


            //Log.d("Soap response is",response.toString());

            //return response;
        }

        @Override
        protected void onPreExecute() {
    /*        progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            //progressBar.setVisibility(View.GONE);

            progressDialog.dismiss();


            String finalResult = result.toString();
            String finals = finalResult.replace("anyType","");
            Log.d("Finals is",finals);

            ArrayList<String> projectTypeList = new ArrayList<String>();

            Log.d("finalResultsssss",finalResult);

            for(int i=0;i < result.getPropertyCount();i++)
            {
                SoapObject list = (SoapObject) result.getProperty(i);
                SoapPrimitive S_ThemeId,S_ThemeName,S_Status;
                Object O_ThemeId,O_ThemeName,O_Status;

                int projectTypeId=0;

                String str_ProjectTypeId=null,str_ProjectType=null,str_status=null;

                O_ThemeId = list.getProperty("ThemeId");
                if(!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals(null) && !O_ThemeId.toString().equals("anyType{}")){
                    S_ThemeId = (SoapPrimitive) list.getProperty("ThemeId");
                    Log.d("ProjectId",S_ThemeId.toString());
                    str_ProjectTypeId = O_ThemeId.toString();
                    projectTypeId = Integer.valueOf(str_ProjectTypeId);
                }

                O_ThemeName = list.getProperty("ThemeName");
                if(!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}"))
                {
                    S_ThemeName = (SoapPrimitive) list.getProperty("ThemeName");
                    Log.d("ProjectType Name",S_ThemeName.toString());
                    str_ProjectType = O_ThemeName.toString();

                    projectTypeList.add(str_ProjectType);

                    hashProjTypeId.put(str_ProjectType,projectTypeId);
                }

            }

            ViewProject viewProject = new ViewProject(context);
            viewProject.execute();

            //setProjectTypeSpinner(projectTypeList);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_editProfile) {
            Intent ittProjDtlsToEditProfile = new Intent(ProjectDetails.this ,EditProfileActivity.class);
            startActivity(ittProjDtlsToEditProfile);
            return true;
        }

        if (id == R.id.action_logout) {
            Intent ittProjDtlsToLogin = new Intent(ProjectDetails.this ,LoginActivity.class);
            startActivity(ittProjDtlsToLogin);
            return true;
        }

        if (id == android.R.id.home) {
            Intent ittProjDtlsToHome = new Intent(ProjectDetails.this ,HomeActivity.class);
            startActivity(ittProjDtlsToHome);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent ittEditProjToProjDtls = new Intent(EditProjectActivity.this ,ProjectDetails.class);
            startActivity(ittEditProjToProjDtls);
            return true;
        }



        return super.onOptionsItemSelected(item);
    }


//-------------------------------date--------------------------------

    // start of from date
    @SuppressLint("ValidFragment")
    public static class DatePickerFragmentFromDate extends  DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            /*return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);*/





            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                    this, year, month, day);


       /* dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
*/

       /* dialog.getDatePicker().setMinDate(c.getTimeInMillis());
        c.add(Calendar.DAY_OF_MONTH,150);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
*/
            return dialog;


        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar cal = new GregorianCalendar(year, month, day);
            setDate(cal);
        }

        public void setDate(final Calendar calendar) {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            //((TextView) findViewById(R.id.showDate)).setText(dateFormat.format(calendar.getTime()));


           // clickstartproject_editdate_tv.setText(dateFormat.format(calendar.getTime()));

            // SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");//2017-06-22

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
            yyyyMMdd_startdate = mdyFormat.format(calendar.getTime());

            clickstartproject_editdate_tv.setText(yyyyMMdd_startdate);
            clickendproject_editdate_tv.setVisibility(View.VISIBLE);
            //  System.out.println("From date:"+ yyyyMMdd_fromdate);
        }

    }




    public static boolean bruteforce(ArrayList<String> input) {
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.size(); j++) {
                if (input.get(i).equals(input.get(j)) && i != j) {
                    return false;
                }
            }
        }
        return true;
    }
    public static class DatePickerFragmentEndDate extends  DialogFragment
            implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            /*return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener)
                            getActivity(), year, month, day);*/





            DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                    this, year, month, day);

            // dialog.getDatePicker().setMaxDate(c.getTimeInMillis()); // this will set maximum date validation
            //dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            //dialog.getDatePicker().setMaxDate(c.getTimeInMillis());

            // dialog.getDatePicker().setMaxDate(c.getTimeInMillis()+((1000*60*60*24*90)));//Error:fromDate: Sun Jun 18 12:50:44 GMT+05:30 2017 does not precede toDate: Fri Jun 09 02:45:11 GMT+05:30 2017


           /* dialog.getDatePicker().setMinDate(c.getTimeInMillis());
            c.add(Calendar.DAY_OF_MONTH,150);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());*/

            return dialog;


        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar cal = new GregorianCalendar(year, month, day);
            setDate(cal);
        }

        public void setDate(final Calendar calendar)
        {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            //((TextView) findViewById(R.id.showDate)).setText(dateFormat.format(calendar.getTime()));

           // clickendproject_editdate_tv.setText(dateFormat.format(calendar.getTime()));

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

            yyyyMMdd_enddate = mdyFormat.format(calendar.getTime());
            clickendproject_editdate_tv.setText(yyyyMMdd_enddate);

            //  System.out.println("To Date:"+ yyyyMMdd_todate);






            if(clickstartproject_editdate_tv.getText().toString().equals("anyType{}")
                    || clickstartproject_editdate_tv.getText().toString().equals(null)
                    ||clickstartproject_editdate_tv.getText().toString().equals("0000-00-00")
                    ||clickstartproject_editdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar")
                    )
            {

            }
            else {

                editstudentdays_linearlayout.setVisibility(View.VISIBLE);
                yyyyMMdd_startdate = clickstartproject_editdate_tv.getText().toString();


                int days_count = Days.daysBetween(new LocalDate(yyyyMMdd_startdate), new LocalDate(yyyyMMdd_enddate)).getDays();
                int int_days = days_count + 1;


                editstudent_numberofdays_tv.setText("Number of Days: " + String.valueOf(int_days));
                //System.out.println("Days: " + int_days);

                if((int_days<0))
                {
                    // obj_class_alert_msg.alerts_dialog("pass","End Date must be after Start Date",static_context1);

                    // alerts_dialog("pass","End Date must be after Start Date",static_context1);

                    clickendproject_editdate_tv.setText("Click for Calendar");
                    editstudent_numberofdays_tv.setText("Number of Days: ");



                    AlertDialog.Builder dialog;
                    dialog = new AlertDialog.Builder(static_context1);
                    dialog.setCancelable(false);
                    dialog.setTitle("LeadCampus");
                    dialog.setMessage("End Date should be after Start Date");



                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id)
                        {


                            dialog.dismiss();

                        }
                    });




                    final AlertDialog alert = dialog.create();
                    alert.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface arg0) {
                            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                        }
                    });
                    alert.show();


                }
                else{
                    //alerts_dialog_fortodate();
                }

















            }




           /* int days_count = Days.daysBetween(new LocalDate(yyyyMMdd_startdate), new LocalDate(yyyyMMdd_enddate)).getDays();
            int int_days=days_count+1;
            System.out.println ("Days: "+int_days);

            if((int_days<=6))
            {

            }
            else{
                //alerts_dialog_fortodate();
            }*/



        }

    }


//----------------------------date------------------------------------


}
