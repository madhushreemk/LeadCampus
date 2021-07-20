package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

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
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatSpinner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.Days;
import org.joda.time.LocalDate;

public class AddProjectFragment extends Fragment {
    Context context;
    AppCompatSpinner spin_projectType;

    ImageButton imgBtn_addMaterial;
    LinearLayout lnrlyt_material;
    private LinearLayout parentLinearLayout;
    private LinearLayout parentLinearLayoutTM;

    //Button btn_addMaterial;
    Button delete_button;
    EditText edt_materialcost;

    double grandSum=0;

    int childLayoutId;

    ImageButton btn_addMaterial;

    ImageButton btn_addMember;


    List<EditText> listEdMaterialCost = new ArrayList<EditText>();
    List<EditText> listEdMaterialName = new ArrayList<EditText>();


    List<EditText> listEdMemberName = new ArrayList<EditText>();
    List<EditText> listEdMemberMailId = new ArrayList<EditText>();

   /* List<EditText> listEdMemb = new ArrayList<EditText>();
    List<EditText> listEdMemberMailId = new ArrayList<EditText>();*/


    ArrayList<String> listMaterialName = new ArrayList<String>();
    ArrayList<String> listMaterialCost = new ArrayList<String>();

    ArrayList<String> listMaterialName_newdup = new ArrayList<String>();
    ArrayList<String> listMaterialCost_newdup = new ArrayList<String>();

    ArrayList<String> listMemberName = new ArrayList<String>();
    ArrayList<String> listMemberMailId = new ArrayList<String>();



    EditText mainEdt_Material;
    EditText mainEdt_Cost;


    EditText mainEdt_MemberName;
    EditText mainEdt_MemberMailId;


    EditText edt_projTitle;
    EditText edt_beneficiaries;
    EditText edt_beneficiaries_name;

    EditText edt_objective;
    EditText edt_actionPlan;

    String str_Title;
    String str_projectType;
    String str_beneficiaries;
    String str_objectives;
    String str_actionPlan;
    private HashMap<String,Integer> hashProjTypeId = new HashMap<String,Integer>();
    private AddProjectObject addProjectObject = new AddProjectObject();


    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_SCellNo = "prefid_scellno"; //


    public static final String PrefId_S_AcademicId = "prefid_sacademicid";


    private SharedPreferences shardpref_S_obj;
    private String str_leadId,str_RegistrationId,str_academicCode;

    private ProgressDialog progressDialog;

    View view;

    Button btn_projsubmit;

    int count=0;

    private EditText edt_placeOfImpl;
    private EditText edt_currentSituation;

    private String memberMailId;
    private String mainMemberMailId;
    private String mainMemberMailIdCheckMandatory;
    //private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    Pattern pattern;
    Matcher matcher;
    int countEmailIds=0;


    static TextView clickstartprojectdate_tv;
    static TextView clickendprojectdate_tv;
    static String yyyyMMdd_startdate = "";
    static String yyyyMMdd_enddate = "";



    private LinearLayout parent_linear_layout1;
    private ImageButton add_field_button;


    private LinearLayout parent_linearlayout_material;
    private ImageButton addmaterial_ib;

    private static TextView numberofdays_tv;

    static Class_alert_msg obj_class_alert_msg;
    static Context static_context1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        context = getActivity().getBaseContext();

        view = inflater.inflate(R.layout.addproject_fragment, container, false);



        static_context1=view.getContext();
        obj_class_alert_msg = new Class_alert_msg(static_context1);
        edt_currentSituation = (EditText) view.findViewById(R.id.edt_currentSituation);

        parentLinearLayout = (LinearLayout) view.findViewById(R.id.parent_linear_layout);
        parentLinearLayoutTM = (LinearLayout) view.findViewById(R.id.parent_linear_layout_TM);



        parent_linear_layout1= (LinearLayout) view.findViewById(R.id.parent_linear_layout1);
        parent_linearlayout_material=(LinearLayout) view.findViewById(R.id.parent_linearlayout_material);


        spin_projectType = (AppCompatSpinner) view.findViewById(R.id.spin_projectType);
        getProjectType();
        //setProjectTypeSpinner();

        clickstartprojectdate_tv = (TextView) view.findViewById(R.id.clickstartprojectdate_TV);
        clickendprojectdate_tv = (TextView) view.findViewById(R.id.clickendprojectdate_TV);
        numberofdays_tv=(TextView)view.findViewById(R.id.numberofdays_tv);
        clickendprojectdate_tv.setVisibility(View.INVISIBLE);

        add_field_button=(ImageButton) view.findViewById(R.id.add_field_button);
        addmaterial_ib=(ImageButton)view.findViewById(R.id.addmaterial_ib);



        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);


        shardpref_S_obj.getString(PrefId_S_AcademicId, "").trim();
        str_academicCode = shardpref_S_obj.getString(PrefId_S_AcademicId, "").trim();
        Log.d("str_academicCode:",str_academicCode);





        shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
        Log.d("str_leadId:",str_leadId);
        //Toast.makeText(getActivity(),"LeadId: "+str_leadId,Toast.LENGTH_LONG).show();

        shardpref_S_obj.getString(PrefID_RegID, "").trim();
        str_RegistrationId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
        Log.d("str_RegistrationId:",str_RegistrationId);
        //Toast.makeText(getActivity(),"RegistrationId: "+str_RegistrationId,Toast.LENGTH_LONG).show();



        listEdMaterialName.clear();
        listEdMaterialCost.clear();
        listEdMemberName.clear();
         listEdMemberMailId.clear();
        listMaterialName.clear();
        listMaterialCost.clear();
        listMemberName.clear();
        listMemberMailId.clear();






        mainEdt_MemberName = (EditText) view.findViewById(R.id.edt_memberName);
        mainEdt_MemberMailId = (EditText) view.findViewById(R.id.edt_memberMailId);


        mainEdt_MemberMailId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    mainMemberMailId = mainEdt_MemberMailId.getText().toString().trim();
                    if (mainMemberMailId.length() > 0) {
                        if (!isValidEmail(mainMemberMailId)) {
                            mainEdt_MemberMailId.setError("Enter correct mail id");
                            //mainEdt_MemberMailId.requestFocus();
                        }
                    }
                }
            }
        });









        edt_placeOfImpl = (EditText) view.findViewById(R.id.edt_placeOfImpl);


        btn_addMember = (ImageButton) view.findViewById(R.id.imgbtn_addMember);
        btn_addMember.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("insidepppp","onclick");
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

               final View rowView = inflater.inflate(R.layout.add_member_layout, null);
                // Add the new row before the add add_material_layout button.

                parentLinearLayoutTM.addView(rowView, parentLinearLayoutTM.getChildCount() - 1);

               Log.e("count",String.valueOf(parentLinearLayoutTM.getChildCount()));



                EditText edt_memberName = (EditText) rowView.findViewById(R.id.edt_memberName);
                listEdMemberName.add(edt_memberName);

                final EditText edt_memberMailId = (EditText) rowView.findViewById(R.id.edt_memberMailId);
                listEdMemberMailId.add(edt_memberMailId);




                edt_memberMailId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
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
                        parentLinearLayoutTM.removeView((View) v.getParent());

                        View rowView = (View) v.getParent();
                        final EditText edt_memberMailId = (EditText) rowView.findViewById(R.id.edt_memberMailId);
                        listEdMemberMailId.remove(edt_memberMailId);

                        final EditText edt_memberName = (EditText) rowView.findViewById(R.id.edt_memberName);
                        listEdMemberName.remove(edt_memberName);

                    }
                });
            }
        });// end add button


        //new

        add_field_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.field, null);
                // Add the new row before the add field button.
                parent_linear_layout1.addView(rowView, parent_linear_layout1.getChildCount() - 1);


                EditText edt_memberName = (EditText) rowView.findViewById(R.id.edt_memberName);
                listEdMemberName.add(edt_memberName);

                final EditText edt_memberMailId = (EditText) rowView.findViewById(R.id.edt_memberMailId);
                listEdMemberMailId.add(edt_memberMailId);




                edt_memberMailId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
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





        addmaterial_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LayoutInflater inflater = (LayoutInflater)view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                final View rowView = inflater.inflate(R.layout.field_material, null);
                // Add the new row before the add field button.
                parent_linearlayout_material.addView(rowView, parent_linearlayout_material.getChildCount() - 1);


                EditText edt_materialname = (EditText) rowView.findViewById(R.id.edt_materialname);
                listEdMaterialName.add(edt_materialname);
               /* String materialName_str=edt_materialname.getText().toString();
                Log.e("tag","materialName_str="+materialName_str);
                if(listEdMaterialName.contains(materialName_str)){
                    Toast.makeText(getActivity(),materialName_str+ "is duplicated",Toast.LENGTH_LONG).show();

                }else {
                    listEdMaterialName.add(edt_materialname);
*/
                    final EditText edt_materialcost = (EditText) rowView.findViewById(R.id.edt_materialcost);
                    listEdMaterialCost.add(edt_materialcost);
             //   }
               // final EditText edt_materialcost = (EditText) rowView.findViewById(R.id.edt_materialcost);
               // listEdMaterialCost.add(edt_materialcost);





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
















        //new















        btn_addMaterial = (ImageButton) view.findViewById(R.id.imgbtn_addmaterial1);
        btn_addMaterial.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("insidepppp","onclick");
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View rowView = inflater.inflate(R.layout.add_material_layout, null);
                // Add the new row before the add add_material_layout button.

                parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);



                EditText edt_materialName = (EditText) rowView.findViewById(R.id.edt_materialname);
                String materialName_str=edt_materialName.getText().toString();
                Log.e("tag","materialName_str="+materialName_str);
               /* if(listEdMaterialName.contains(materialName_str)){
                    Toast.makeText(getActivity(),materialName_str+ "is duplicated",Toast.LENGTH_LONG).show();

                }else {*/
                    listEdMaterialName.add(edt_materialName);

                    final EditText edt_materialcost = (EditText) rowView.findViewById(R.id.edt_materialcost);
                    listEdMaterialCost.add(edt_materialcost);
               // }

                Button del_button = (Button) rowView.findViewById(R.id.delete_button);
                del_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Insidexxx","delete button");
/*                        Log.d("Materialcostxxxxxxxx",edt_materialcost.getText().toString());
                        Double removedCost = Double.parseDouble(edt_materialcost.getText().toString());
                        grandSum = grandSum - removedCost;*/
                        Log.d("grandsumxxxxx", String.valueOf(grandSum));
                        parentLinearLayout.removeView((View) v.getParent());

                        View rowView = (View) v.getParent();
                        final EditText edt_materialcost = (EditText) rowView.findViewById(R.id.edt_materialcost);
                        listEdMaterialCost.remove(edt_materialcost);

                        final EditText edt_materialName = (EditText) rowView.findViewById(R.id.edt_materialname);
                        listEdMaterialName.remove(edt_materialName);

                    }
                });

           /*     edt_materialcost.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //materialCost1 = s.toString();
                        //calculategrandSum();
                    }
                });*/


            }
        });

        edt_materialcost = (EditText) view.findViewById(R.id.edt_materialcost);

      /*  edt_materialcost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //materialCost2 = s.toString();
                //calculategrandSum();
            }
        });*/


        edt_projTitle = (EditText) view.findViewById(R.id.edt_title);
        edt_beneficiaries = (EditText) view.findViewById(R.id.edt_noOfBeneficiaries);

        edt_beneficiaries_name = (EditText) view.findViewById(R.id.edt_whoareBeneficiaries);

        edt_objective = (EditText) view.findViewById(R.id.edt_objective);
        edt_actionPlan = (EditText) view.findViewById(R.id.edt_actionplan);

        btn_projsubmit = (Button) view.findViewById(R.id.btn_projsubmit);
        mainEdt_Material = (EditText) view.findViewById(R.id.edt_materialname);
        mainEdt_Cost = (EditText) view.findViewById(R.id.edt_materialcost);

        btn_projsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                countEmailIds = 0;
                listMaterialName.clear();
                listMaterialCost.clear();
                if(!(mainEdt_Material.getText().toString().isEmpty()) && !(mainEdt_Material.getText().toString().equals(""))) {
                    Log.e("mainEdt_Material","mainEdt_Material="+mainEdt_Material.getText().toString());
                    Log.e("mainEdt_Cost","mainEdt_Cost="+mainEdt_Cost.getText().toString());
                    listMaterialName.add(mainEdt_Material.getText().toString());
                    listMaterialCost.add(mainEdt_Cost.getText().toString());

                }
                if(listMaterialName.size()!=0) {
                    String arr_materialName[] = new String[0];
                    for (int i = 0; i < listEdMaterialName.size(); i++) {
                        String materialName = listEdMaterialName.get(i).getText().toString();

                        listMaterialName.add(materialName);

                    }

                //    Log.e("tag","bruteforce(listMaterialName)=="+bruteforce(listMaterialName));

                }
                if(checkMandatoryFields())
                {
                    btn_projsubmit.setVisibility(View.GONE);

                    Log.d("Insideyyyyy","true");



                    if(!(mainEdt_MemberName.getText().toString().isEmpty()) && !(mainEdt_MemberName.getText().toString().equals(""))) {
                        listMemberName.add(mainEdt_MemberName.getText().toString());
                        listMemberMailId.add(mainEdt_MemberMailId.getText().toString());
                    }


                    /*for(int i = 0; i < listEdMaterialName.size(); i++)
                    {

                        if(!listMaterialName.contains(listEdMaterialName.get(i).getText().toString()))
                        {
                            listMaterialName.add(listEdMaterialName.get(i).getText().toString());
                        }
                        else{
                            Log.e("tag","duplicate");
                        }
                    }*/


                    Log.e("tag","listMaterialName=="+listMaterialName);
                    Log.e("tag","listMaterialName_newdup=="+listMaterialName_newdup);
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

                    if(!listEdMemberMailId.equals(null))
                    {
                        for (int i = 0; i < listEdMemberMailId.size(); i++) {
                            if (!listEdMemberMailId.get(i).getText().toString().equals("") && !listEdMemberMailId.get(i).getText().toString().isEmpty()) {
                                String memberMailId = listEdMemberMailId.get(i).getText().toString();
                                listMemberMailId.add(memberMailId);
                            }
                        }
                    }

                    /*for (int j1=0;j1<listMaterialName.size();j1++){
                        for(int i1=0;i1<listMaterialName.size();i1++){
                            if(listMaterialName.get(j1).equalsIgnoreCase(listMaterialName.get(i1))){
                                mainEdt_Material.setError("Duplicate Value Found");
                                Log.e("tag","Duplicate Value Found");
                            }else{
                                listMaterialName_newdup.add(listMaterialName.get(i1));
                                listMaterialCost_newdup.add(listMaterialCost.get(i1));
                            }
                        }
                    }*/






                    if(!edt_materialcost.getText().toString().equals("") && !edt_materialcost.getText().toString().isEmpty() && !mainEdt_Material.getText().toString().isEmpty() && !mainEdt_Material.getText().toString().equals(null) && !mainEdt_Material.getText().toString().equals("")) {
                        grandSum = grandSum + Double.parseDouble(edt_materialcost.getText().toString());
                        Log.d("grandsumzzz",String.valueOf(grandSum));
                    }
                    Log.d("grandsumissss",String.valueOf(grandSum));

                    for(int j=0;j<listMaterialName.size();j++){
                        Log.d("List vmmaterial Name: ",listMaterialName.get(j).toString());
                    }


                   // Toast.makeText(getActivity(),"Submitted",Toast.LENGTH_LONG).show();

                    AddProject addProject = new AddProject(getActivity());
                    addProject.execute();


                }
            }
        });


        clickstartprojectdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment fromdateFragment = new DatePickerFragmentFromDate();
                fromdateFragment.show(getActivity().getFragmentManager(), "Date Picker");
            }
        });


        clickendprojectdate_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // settodate();
                DialogFragment dFragment = new DatePickerFragmentEndDate();
                // Show the date picker dialog fragment
                dFragment.show(getActivity().getFragmentManager(), "Date Picker");


            }
        });










        return view;
    }// end of onCreate()




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


            clickstartprojectdate_tv.setText(dateFormat.format(calendar.getTime()));

            // SimpleDateFormat mdyFormat = new SimpleDateFormat("MM/dd/yyyy");//2017-06-22

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");
            yyyyMMdd_startdate = mdyFormat.format(calendar.getTime());

            clickendprojectdate_tv.setText("Click for Calendar");

            clickendprojectdate_tv.setVisibility(View.VISIBLE);

            //  System.out.println("From date:"+ yyyyMMdd_fromdate);
        }

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

        public void setDate(final Calendar calendar) {
            final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
            //((TextView) findViewById(R.id.showDate)).setText(dateFormat.format(calendar.getTime()));

            clickendprojectdate_tv.setText(dateFormat.format(calendar.getTime()));

            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

            yyyyMMdd_enddate = mdyFormat.format(calendar.getTime());

            //  System.out.println("To Date:"+ yyyyMMdd_todate);

            int days_count = Days.daysBetween(new LocalDate(yyyyMMdd_startdate), new LocalDate(yyyyMMdd_enddate)).getDays();
            int int_days=days_count+1;


            numberofdays_tv.setText("Number of Days: "+String.valueOf(int_days));
            System.out.println ("Days: "+int_days);



            if((int_days<0))
            {
               // obj_class_alert_msg.alerts_dialog("pass","End Date must be after Start Date",static_context1);

               // alerts_dialog("pass","End Date must be after Start Date",static_context1);

                clickendprojectdate_tv.setText("Click for Calendar");
                numberofdays_tv.setText("Number of Days: ");



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

    }


//----------------------------date------------------------------------


    private void calculategrandSum()
    {


     /*   if(materialCost2 == null || materialCost2.isEmpty() || materialCost2.equals("")){
            materialCost2 = "0";
        }*/

    /*    if(materialCost1 == null || materialCost1.isEmpty() || materialCost1.equals("")){
            materialCost1 = "0";
        }*/



        Double materialCostMain = 0.0;
        if(edt_materialcost!=null || !(edt_materialcost.getText().toString().isEmpty()) || !(edt_materialcost.getText().toString().equals(""))) {
            materialCostMain = Double.parseDouble(edt_materialcost.getText().toString());
        }

        if(listEdMaterialCost.isEmpty()){
            grandSum = materialCostMain;
        }
        else {

            for (int i = 0; i < listEdMaterialCost.size(); i++) {
                count++;

                String materialCost = "0";
                if (listEdMaterialCost.get(i) == null || !(listEdMaterialCost.get(i).getText().toString().isEmpty()) || !(listEdMaterialCost.get(i).getText().toString().equals(""))) {
                    materialCost = listEdMaterialCost.get(i).getText().toString();
                }
                //listMaterialCost.add(materialCost);

                double sumval = Double.parseDouble(materialCost);

                grandSum = grandSum + sumval + materialCostMain;

               /* if(count==2) {
                    grandSum = sumval + materialCostMain;
                }else{
                    grandSum = grandSum + sumval + materialCostMain;
                }*/
                //Log.d("Value ","Val " +materialCost );
            }

        }



        // grandSum = grandSum + Double.parseDouble(materialCost1) + Double.parseDouble(materialCost2);

        Log.d("calculategrandsum", String.valueOf(grandSum));
    }


    public class AddProject extends AsyncTask<Void, Void, SoapPrimitive> {

        Context context;
        AlertDialog alertDialog;

   /*     private ProgressBar progressBar;*/

        AddProject (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = addProjectDetails();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
/*            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Submitting");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            /*progressBar.setVisibility(View.GONE);*/

            progressDialog.dismiss();

            if(result!=null) {
                if (result.toString().equalsIgnoreCase("success")) {
                    Toast.makeText(getActivity(), "Project Added Successfully", Toast.LENGTH_LONG).show();
                    clearAllFields();
                    getActivity().finish();
                    Intent ittEditProjToProjDtls = new Intent(getActivity(), ProjectDetails.class);
                    startActivity(ittEditProjToProjDtls);
                } else {
                    Toast.makeText(getActivity(), result.toString(), Toast.LENGTH_LONG).show();
                }
            }else{

            }

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

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private void clearAllFields() {
        getActivity().finish();
    }

    private SoapPrimitive addProjectDetails()
    {

        String METHOD_NAME = "AddProjectproposal1json";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/AddProjectproposal1json";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");

            ArrayList<vmmaterial> matModel = new ArrayList<vmmaterial>();

            vmmaterial material = new vmmaterial();
            material.setSlno(1);
            material.setMeterialCost(50);
            material.setMeterialName("Material 1");
            matModel.add(material);

            request.addProperty("Student_Id",str_RegistrationId);
            request.addProperty("Lead_Id",str_leadId);
            request.addProperty("Title",edt_projTitle.getText().toString());
            request.addProperty("BeneficiaryNo",Integer.valueOf(edt_beneficiaries.getText().toString()));

            request.addProperty("Beneficiary",edt_beneficiaries_name.getText().toString());

            str_projectType = spin_projectType.getSelectedItem().toString();
            long projectTypeId = (long) hashProjTypeId.get(str_projectType);

            Log.d("projectIdinSubmissionxx", String.valueOf(projectTypeId));

            request.addProperty("ProjectType",String.valueOf(projectTypeId));
            request.addProperty("Objectives",edt_objective.getText().toString());
            request.addProperty("ActionPlan",edt_actionPlan.getText().toString());


            JSONObject jsonObject = new JSONObject();

            for(int k=0;k<listMaterialName.size() && k<listMaterialCost.size();k++)
            {
                jsonObject.put(listMaterialName.get(k),listMaterialCost.get(k));
            }
          /*  for(int k1=0;k1<listEdMaterialCost.size();k1++) {
                String matval=listEdMaterialCost.get(k1).getText().toString();
                double sumval = Double.parseDouble(matval);
                grandSum = grandSum + sumval;
                Log.d("Value ", "Val " + sumval);
            }*/
            request.addProperty("materials",jsonObject.toString());
            Log.e("material json",jsonObject.toString());


            JSONObject jsonObjectMember = new JSONObject();

            for(int k=0;k<listMemberName.size() && k<listMemberMailId.size();k++){
                jsonObjectMember.put(listMemberName.get(k),listMemberMailId.get(k));
            }

            Log.d("jsonObjectMember",jsonObjectMember.toString());
            request.addProperty("Budget",String.valueOf(grandSum));
            request.addProperty("teammembers",jsonObjectMember.toString());

            request.addProperty("AcademicID",str_academicCode);
            request.addProperty("placeofimplimentation",edt_placeOfImpl.getText().toString());

            Log.d("edtcurrentsituationi:",edt_currentSituation.getText().toString());

            request.addProperty("CurrentSituation",edt_currentSituation.getText().toString());

            request.addProperty("ProjectStartDate",yyyyMMdd_startdate.toString());//<ProjectStartDate>string</ProjectStartDate>
            request.addProperty("ProjectEndDate",yyyyMMdd_enddate.toString());//<ProjectEndDate>string</ProjectEndDate>



            Log.d("grandSumissss",String.valueOf(grandSum));

            Log.d("AddProject_request",request.toString());

            //request.addProperty("materials",)

/*
            SoapObject groupSoap=new SoapObject("http://mis.leadcampus.org/",METHOD_NAME);
            SoapObject vmMaterial=new SoapObject("http://mis.leadcampus.org/",METHOD_NAME);

            vmMaterial.addProperty("slno",1);
            vmMaterial.addProperty("MeterialName","material1");
            vmMaterial.addProperty("MeterialCost",5);

            groupSoap.addProperty("vmmaterial",vmMaterial);


            PropertyInfo materialProp = new PropertyInfo();
            materialProp.setName("materials");i
            materialProp.setValue(groupSoap);*/
            //materialProp.setType(groupSoap.getClass());

            //request.addProperty(materialProp);




            //request.addProperty("materials",matModel);




            //request.addProperty("vmp",addProjectObject);
            //users.ad


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            ///envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                // Log.d("soap response1xxxx",envelope.getResponse().toString());


                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("soap responseyyyyyyy",response.toString());

                return response;

                //return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            t.printStackTrace();
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;
    }

    private boolean checkMandatoryFields()
    {
        String str_Title = edt_projTitle.getText().toString();
        String str_beneficiaries = edt_beneficiaries.getText().toString();
        String str_beneficiaries_name = edt_beneficiaries_name.getText().toString();
        String str_objectives = edt_objective.getText().toString();
        String str_actionPlan = edt_actionPlan.getText().toString();
        String str_projectType = spin_projectType.getSelectedItem().toString();
        String str_placeOfImpl = edt_placeOfImpl.getText().toString();
        String str_currentSituation = edt_currentSituation.getText().toString();




        Boolean  b_currentsituation,b_titlename,b_beneficiaries,b_beneficiaries_name,
        b_placeofimpl,b_selecttype,b_objectives,b_datevalidation1,
        b_actionplan,b_datevalidation2,b_materialName;

        b_currentsituation=b_titlename=b_beneficiaries=b_beneficiaries_name=
        b_placeofimpl=b_selecttype=b_objectives=b_datevalidation1=
        b_actionplan=b_datevalidation2= true;

       /* if(listEdMaterialName.size()!=0){
            for(int i1=0;i1<listEdMaterialName.size();i1++){

            }
        }*/
        b_materialName=bruteforce(listMaterialName);
        if(!b_materialName){
            mainEdt_Material.setError("Material Name should not be duplicate");
            mainEdt_Material.requestFocus();
            b_materialName= false;
        }
        mainMemberMailIdCheckMandatory = mainEdt_MemberMailId.getText().toString().trim();

        if (mainMemberMailIdCheckMandatory.length() > 0)
        {
            if (!isValidEmail(mainMemberMailIdCheckMandatory))
            {
                mainEdt_MemberMailId.setError("Enter correct mail id");
                mainEdt_MemberMailId.requestFocus();
                return false;
            }
        }



        if(!listEdMemberMailId.equals(null))
        {
            Log.d("Insidedddd","listEdMemberMailIdnotnull");
            for (int i = 0; i < listEdMemberMailId.size(); i++)
            {
        /*        if (listEdMemberMailId.get(i).getError()!=null) {
          *//*          String memberMailId = listEdMemberMailId.get(i).getText().toString();
                    listMemberMailId.add(memberMailId);*//*
                    //return true;
                    countEmailIds++;
                }*/

                memberMailId = listEdMemberMailId.get(i).getText().toString().trim();
                if (memberMailId.length() > 0)
                {
                    if (!isValidEmail(memberMailId)) {
                        listEdMemberMailId.get(i).setError("Enter correct mail id");
                        listEdMemberMailId.get(i).requestFocus();
                        return false;
                    }
                }


            }

            /*Log.d("countValueissss", String.valueOf(countEmailIds));

            if(countEmailIds!=0){
                return false;
            }*/



/*
            if(countEmailIds==listEdMemberMailId.size()){
          *//*      if(mainEdt_MemberMailId!=null && !mainEdt_MemberMailId.getText().toString().isEmpty() && mainEdt_MemberMailId.getError()!=null){
                    return true;
                }*//*
                return true;
            }
            else{
                return false;
            }*/
        }





        if(str_currentSituation.equals(null) || str_currentSituation.equals("") || str_currentSituation.isEmpty()){
            edt_currentSituation.setError("Enter current situation");
            edt_currentSituation.requestFocus();
            b_currentsituation=false;
           // return false;
        }


        if(str_Title.equals(null) || str_Title.equals("") || str_Title.isEmpty()){
            edt_projTitle.setError("Enter title of the project");
            edt_projTitle.requestFocus();
            b_titlename=false;
            //return false;
        }

        if(str_beneficiaries.equals(null) || str_beneficiaries.equals("") || str_beneficiaries.isEmpty()){
            edt_beneficiaries.setError("Enter no. of beneficiaries");
            edt_beneficiaries.requestFocus();
            b_beneficiaries=false;
            //return false;
        }




        if(str_beneficiaries_name.equals(null) || str_beneficiaries_name.equals("") || str_beneficiaries_name.isEmpty()){
            edt_beneficiaries_name.setError("Enter who are they");
            edt_beneficiaries_name.requestFocus();
            b_beneficiaries_name=false;
           // return false;
        }

        if(str_placeOfImpl.equals(null) || str_placeOfImpl.equals("") || str_placeOfImpl.isEmpty()){
            edt_placeOfImpl.setError("Enter place of Implementation");
            edt_placeOfImpl.requestFocus();
            b_placeofimpl=false;
            //return false;
        }

        if(str_projectType.contains("Select")){
/*            edt_beneficiaries.setError("Enter no. of beneficiaries");
            edt_beneficiaries.requestFocus();*/
            Toast.makeText(context,"Please select the Project Type",Toast.LENGTH_LONG).show();
            b_selecttype=false;
           // return false;
        }




        if(str_objectives.equals(null) || str_objectives.equals("") || str_objectives.isEmpty()){
            edt_objective.setError("Enter objective of the project");
            edt_objective.requestFocus();
            b_objectives=false;
            //return false;
        }



//vijay
        /*if(clickstartprojectdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar")
                ||clickendprojectdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar"))
        {
            Toast.makeText(context, "Kindly enter the date", Toast.LENGTH_SHORT).show();
            b_datevalidation1=false;
           // return false;
        }*/



        if(clickstartprojectdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar")
                ||clickendprojectdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar"))
        {
            Toast.makeText(context, "Kindly enter the date", Toast.LENGTH_SHORT).show();
            b_datevalidation1=false;
        }




        if((!clickstartprojectdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar"))
            &&(!clickendprojectdate_tv.getText().toString().equalsIgnoreCase("Click for Calendar")))

        {




            if ((clickstartprojectdate_tv.getText().toString().length() != 0) && (clickendprojectdate_tv.getText().toString().length() != 0)) {
        /*if(date1.compareTo(date2)<0){ //0 comes when two date are same,
            //1 comes when date1 is higher then date2
            //-1 comes when date1 is lower then date2 }*/


                SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");  //2017-06-22

                try {
                    Date fromdate = mdyFormat.parse(yyyyMMdd_startdate);
                    Date todate = mdyFormat.parse(yyyyMMdd_enddate);

                    if (fromdate.compareTo(todate) <= 0)
                    {
                        //return true;
                    } else {
                        Toast.makeText(context, "Kindly enter valid date", Toast.LENGTH_SHORT).show();
                        b_datevalidation2 = false;
                        //return false;
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }//end of if

        }



        if(str_actionPlan.equals(null) || str_actionPlan.equals("") || str_actionPlan.isEmpty()){
            edt_actionPlan.setError("Enter action plan of the project");
            edt_actionPlan.requestFocus();
            b_actionplan=false;
           // return false;
        }
        /*else{
            return true;
        }*/



        return( b_currentsituation&&b_titlename&&b_beneficiaries&&b_beneficiaries_name&&
        b_placeofimpl&&b_selecttype&& b_objectives&&b_datevalidation1&&
        b_actionplan&&b_datevalidation2&&b_materialName);


    }

    private void setProjectTypeSpinner(ArrayList<String> projectTypeList) {

        //getProjectType();

        /*final ArrayList projType = new ArrayList();
        projType.add("Education");
        projType.add("Health Care");
        projType.add("Sports");*/

        ArrayAdapter dataAdapter2 = new ArrayAdapter(context, R.layout.simple_spinner_items, projectTypeList);

        // Drop down layout style - list view with radio button
        //dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        spin_projectType.setAdapter(dataAdapter2);
        //spin_projectType.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));

    }

    private void getProjectType() {
        ProjectType projType = new ProjectType(getActivity());
        projType.execute();
    }

    public class ProjectType extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        ProjectType (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
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
                    Log.d("GetThemeList:",response.toString());

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

                    t.printStackTrace();
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
                Log.d("exception outside",t.getMessage().toString());

                t.printStackTrace();
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Web Service Error", Toast.LENGTH_LONG).show();
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

                ArrayList<String> projectTypeList = new ArrayList<String>();
                projectTypeList.add("Select Project Type");

                Log.d("finalResultsssss", finalResult);

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);
                    SoapPrimitive S_ThemeId, S_ThemeName, S_Status;
                    Object O_ThemeId, O_ThemeName, O_Status;

                    int projectTypeId = 0;

                    String str_ProjectTypeId = null, str_ProjectType = null, str_status = null;

                    O_ThemeId = list.getProperty("ThemeId");
                    if (!O_ThemeId.toString().equals("anyType") && !O_ThemeId.toString().equals(null) && !O_ThemeId.toString().equals("anyType{}")) {
                        S_ThemeId = (SoapPrimitive) list.getProperty("ThemeId");
                        Log.d("ThemeId", S_ThemeId.toString());
                        str_ProjectTypeId = O_ThemeId.toString();
                        projectTypeId = Integer.valueOf(str_ProjectTypeId);
                    }

                    O_ThemeName = list.getProperty("ThemeName");
                    if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null) && !O_ThemeName.toString().equals("anyType{}")) {
                        S_ThemeName = (SoapPrimitive) list.getProperty("ThemeName");
                        Log.d("ProjectType Name", S_ThemeName.toString());
                        str_ProjectType = O_ThemeName.toString();

                        projectTypeList.add(str_ProjectType);

                        hashProjTypeId.put(str_ProjectType, projectTypeId);
                    }


                }

                setProjectTypeSpinner(projectTypeList);

            }
            //SoapPrimitive status = (SoapPrimitive) result.getProperty("Status");
            //Log.d("Status is:",status.toString());

            /*if(status.toString().equals("Success")) {
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


            //setProjectTypeSpinner();





        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{3,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }





    public static void alerts_dialog(String str_error,String ws,Context context)
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(static_context1);
        dialog.setCancelable(false);
        dialog.setTitle("LeadMIS");
        dialog.setMessage("Kindly submit the data before exiting");

        /*Activity activity = (Activity) context;
        fragmentManager = activity.getFragmentManager();
*/


        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.frame_container, new Fragment_RHolidays());
                fragmentTransaction.commit();*/

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


}
