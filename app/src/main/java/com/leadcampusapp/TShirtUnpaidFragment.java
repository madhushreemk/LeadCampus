package com.leadcampusapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.AppCompatSpinner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

//import com.android.sripad.leadnew_22_6_2018.R;

public class TShirtUnpaidFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{
    int count=0;

    private ArrayList<TShirtUnpaidModel> feesList;
    private ArrayList<TShirtSizeModel> sizeSList;
    private ArrayList<TShirtSizeModel> sizeMList;
    private ArrayList<TShirtSizeModel> sizeLList;
    private ArrayList<TShirtSizeModel> sizeXLList;
    private ArrayList<TShirtSizeModel> sizeXXLList;
    private View view;
    private boolean isCompletedflag=false;
    private int projectStatusIcon;
    private TShirtUnpaidAdapter adapter;
    private TShirtSizeAdapter adapterSize;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_SCellNo = "prefid_scellno"; //

    private SharedPreferences shardpref_S_obj;
    private String str_leadId,str_RegistrationId;
    private ProgressDialog progressDialog;

    private EditText etSearch;

    private ListView lview;
    private ArrayList<TShirtUnpaidModel> originalList = null;

    private int countProposed=0;
    private int countReapply=0;
    private int countRequestForCompletion=0;
    private int countApproved=0;
    private int countRejected=0;
    private int countCompleted=0;

    private Button btn_generateReport;
    public static final String PrefID_PMID = "prefid_pmid"; //
    public static final String  PREFBook_PM= "prefbook_pm";
    SharedPreferences shardprefPM_obj;
    public String str_MangerID;
    public static String str_MdID;
    private AppCompatSpinner spin_college;
    private TextView txt_selectAll;
    private LinkedHashSet<String> collegeNameLst;
    private ArrayList<String> collegeNameArrLst;

    private LinkedHashSet<String> tshirtSizeLst;
    private ArrayList<String> tshirtSizeArrLst;

    private Button btn_selectAll,btn_selectNone,btn_submit;
    private CheckBox chk_selectAll;
    private static Context context;
    private ArrayList<String> arrLstLeadIds;
    private String str_leadIdOverall="";

    private static ArrayList<String> arrLstLeadIds_static;
    private static ArrayList<String> arrLstreqId_static;
    //private String
    private int counts=0;
    private int counter=0;
    private ArrayList<String> arrayLeadId;
    private ArrayList<String> arrLstreqId;
    private ArrayList<String> arrLstSize;

    private String str_requestedIdOverall="";
    private ArrayList<String> arrayreqId;
    private SwipeRefreshLayout swipeLayout;
    private TextView txt_actualTotalCounts;

    int SCount,MCount,LCount,XLCount,XXLCount;
    TextView textCartTSCount,textCartTMCount,textCartTLCount,textCartTXLCount,textCartTXXLCount;
    LinearLayout small_tshirt,medium_tshirt,large_tshirt,xlarge_tshirt,xxlarge_tshirt;

    String SizeSelected,RequestIdSelected;
    String str_allotedS = null, str_allotedM = null, str_allotedL = null, str_allotedXL = null, str_allotedXXL = null, str_usedS = null, str_usedM = null, str_usedL = null, str_usedXL = null, str_usedXXL = null, str_status = null;
    boolean smallBoolean=false,mediumBoolean=false,largeBoolean=false,feesBoolean=false,XlargeBoolean=false,XXlargeBoolean=false;
    Integer allotedS_int;
    Integer allotedM_int;
    Integer allotedL_int;
    Integer allotedXL_int;
    Integer allotedXXL_int;
    Integer UsedS_Int;
    Integer UsedM_Int;
    Integer UsedL_Int;
    Integer UsedXL_Int;
    Integer UsedXXL_Int;

    static Integer finalCount_S,finalCount_M,finalCount_L,finalCount_XL,finalCount_XXL;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.frag_tshirt_unpaid, container, false);

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
     /*   swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);*/
        textCartTSCount = (TextView) view.findViewById(R.id.cart_badge_S);
        textCartTMCount=(TextView) view.findViewById(R.id.cart_badge_M);
        textCartTLCount=(TextView) view.findViewById(R.id.cart_badge_L);
        textCartTXLCount=(TextView) view.findViewById(R.id.cart_badge_XL);
        textCartTXXLCount=(TextView) view.findViewById(R.id.cart_badge_XXL);
        txt_actualTotalCounts = (TextView) view.findViewById(R.id.txt_actualTotalCount);

        small_tshirt = (LinearLayout) view.findViewById(R.id.small_tshirt);
        medium_tshirt = (LinearLayout) view.findViewById(R.id.medium_tshirt);
        large_tshirt = (LinearLayout) view.findViewById(R.id.large_tshirt);
        xlarge_tshirt = (LinearLayout) view.findViewById(R.id.x_large_tshirt);
        xxlarge_tshirt = (LinearLayout) view.findViewById(R.id.xx_large_tshirt);

        initializeviews();

        if (SCount == 0) {
            if (textCartTSCount.getVisibility() != View.GONE) {
                textCartTSCount.setVisibility(View.GONE);
            }
        }
        if (MCount == 0) {
            if (textCartTMCount.getVisibility() != View.GONE) {
                textCartTMCount.setVisibility(View.GONE);
            }
        }
        if (LCount == 0) {
            if (textCartTLCount.getVisibility() != View.GONE) {
                textCartTLCount.setVisibility(View.GONE);
            }
        }
        if (XLCount == 0) {
            if (textCartTXLCount.getVisibility() != View.GONE) {
                textCartTXLCount.setVisibility(View.GONE);
            }
        }
        if (XXLCount == 0) {
            if (textCartTXXLCount.getVisibility() != View.GONE) {
                textCartTXXLCount.setVisibility(View.GONE);
            }
        }

        GetAssignedTShirtlist getAssignedTShirtlist = new GetAssignedTShirtlist(context);
        getAssignedTShirtlist.execute();
        /*lview = (NonScrollListView) view.findViewById(R.id.listview_feesUnpaid);

        arrLstLeadIds = new ArrayList<String>();

        feesList = new ArrayList<FeesUnpaidModel>();
        adapter = new FeesUnpaidAdapter(getActivity(),feesList);
        lview.setAdapter(adapter);

        arrayLeadId = new ArrayList<String>();


        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MangerID:",str_MangerID);

        collegeNameLst = new LinkedHashSet<String>();
        collegeNameLst.add("Select College");
        collegeNameArrLst = new ArrayList<String>();

        spin_college = (AppCompatSpinner) view.findViewById(R.id.spin_college);

        btn_submit = (Button) view.findViewById(R.id.btn_submit);

        chk_selectAll = (CheckBox) view.findViewById(R.id.chk_selectAll);

        txt_selectAll = (TextView) view.findViewById(R.id.txt_selectAll);

        populateList();

        etSearch = (EditText) view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
                String text2 = s.toString();
                if(text2.equals("")) {
                    String text = spin_college.getSelectedItem().toString();
                    if(!text.equals("Select College")) {
                        adapter.filter(text, originalList,null);
                    }
                }
            }
        });

        spin_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String collegeName = spin_college.getSelectedItem().toString();

                chk_selectAll.setChecked(false);
                int size = feesList.size();
                for(int i=0;i<size;i++)
                {
                    FeesUnpaidModel feesUnpaidModel = feesList.get(i);
                    feesUnpaidModel.setStatus("0");
                }
                adapter.notifyDataSetChanged();


                if(!collegeName.equals("Select College")) {
                    adapter.filter(collegeName, originalList,null);
                }else{
                    collegeName = "";
                    adapter.filter(collegeName, originalList,null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chk_selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    Log.d("Checkedtruesssss","truesssss");
                    int size = feesList.size();
                    for(int i=0;i<size;i++)
                    {
                        FeesUnpaidModel feesUnpaidModel = feesList.get(i);
                        feesUnpaidModel.setStatus("1");
                    }
                    adapter.notifyDataSetChanged();
                }

                else{
                    Log.d("Checkedfalsessssssss","falsessss");
                    int size = feesList.size();
                    for(int i=0;i<size;i++)
                    {
                        FeesUnpaidModel feesUnpaidModel = feesList.get(i);
                        feesUnpaidModel.setStatus("0");
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lview.getCount()iss", String.valueOf(lview.getCount()));

                for ( ; counts < lview.getCount() ; counts++) {
                    View vw = lview.getAdapter().getView(counts,null,null);
                    CheckBox checkBox = (CheckBox) vw.findViewById(R.id.check_status);

                    if(checkBox.isChecked()) {
                        TextView txt_leadId = (TextView) vw.findViewById(R.id.txt_leadId);
                        Log.d("TextLeadIdissss", txt_leadId.getText().toString());

                        arrLstLeadIds.add(txt_leadId.getText().toString());
                        str_leadIdOverall = "'" + txt_leadId.getText().toString() + "'";

                        arrayLeadId.add(str_leadIdOverall);

                        Log.d("str_leadIdOverallis",str_leadIdOverall);

                        counter++;
                    }

                }

                if(counter!=0){
                    str_leadIdOverall = "";
                    for(int i=0;i<arrayLeadId.size();i++){
                        str_leadIdOverall = str_leadIdOverall + arrayLeadId.get(i) + ",";
                        Log.d("stringleadidssssssxxxxx",str_leadIdOverall);

                        if(i == arrayLeadId.size()-1){
                            str_leadIdOverall = str_leadIdOverall.substring(0,str_leadIdOverall.length()-1);
                        }

                    }
                }


                if(counter!=0) {
                    SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                    submitAcknowledgements.execute();
                }else{
                    Toast.makeText(getActivity(),"You have not selected any student",Toast.LENGTH_LONG).show();
                }


                if(lview.getCount()==0 && collegeNameLst.size()==1){
                    txt_selectAll.setVisibility(View.GONE);
                    chk_selectAll.setVisibility(View.GONE);
                }else{
                    txt_selectAll.setVisibility(View.VISIBLE);
                    chk_selectAll.setVisibility(View.VISIBLE);
                }
            }
        });

        Log.d("countisssss", String.valueOf(lview.getCount()));*/

        return view;
    }

    private void initializeviews() {
        lview = (NonScrollListView) view.findViewById(R.id.listview_tShirtUnpaid);

        arrLstLeadIds = new ArrayList<String>();
        arrLstreqId = new ArrayList<String>();
        arrLstSize = new ArrayList<String>();
        arrLstLeadIds_static =new ArrayList<>();
        arrLstreqId_static = new ArrayList<>();

        feesList = new ArrayList<TShirtUnpaidModel>();
        adapter = new TShirtUnpaidAdapter(getActivity(),feesList);
        lview.setAdapter(adapter);

        sizeSList = new ArrayList<TShirtSizeModel>();
        sizeMList = new ArrayList<TShirtSizeModel>();
        sizeLList = new ArrayList<TShirtSizeModel>();
        sizeXLList = new ArrayList<TShirtSizeModel>();
        sizeXXLList = new ArrayList<TShirtSizeModel>();

        sizeSList.clear();
        sizeMList.clear();
        sizeLList.clear();
        sizeXLList.clear();
        sizeXXLList.clear();
        SCount=0;
        MCount=0;
        LCount=0;
        XLCount=0;
        XXLCount=0;

        arrayLeadId = new ArrayList<String>();
        arrayreqId = new ArrayList<String>();

        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MdID=str_MangerID;
        Log.d("str_MangerID:",str_MangerID);

        collegeNameLst = new LinkedHashSet<String>();
        collegeNameLst.add("All");
        collegeNameArrLst = new ArrayList<String>();

        tshirtSizeLst = new LinkedHashSet<String>();
        tshirtSizeArrLst =new ArrayList<String>();

        spin_college = (AppCompatSpinner) view.findViewById(R.id.spin_college);

      /*  btn_selectAll = (Button) view.findViewById(R.id.btn_selectAll);

        btn_selectNone = (Button) view.findViewById(R.id.btn_selectNone);*/

        btn_submit = (Button) view.findViewById(R.id.btn_submit);

        chk_selectAll = (CheckBox) view.findViewById(R.id.chk_selectAll);

        txt_selectAll = (TextView) view.findViewById(R.id.txt_selectAll);

        txt_selectAll.setVisibility(View.GONE);
        chk_selectAll.setVisibility(View.GONE);
        //initCollegeSpinner();

        populateList();


       /* etSearch = (EditText) view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                String selectedCollege = spin_college.getSelectedItem().toString();

                if(!selectedCollege.equals("All")) {
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
           *//*     String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text,originalList);*//*

                String text2 = s.toString();
                if(text2.equals("")) {
                    String text = spin_college.getSelectedItem().toString();
                    if(!text.equals("All")) {
                        adapter.filter(text, originalList,null);
                    }
                }
            }
        });*/

        spin_college.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String collegeName = spin_college.getSelectedItem().toString();
                //adapter.filter(collegeName, originalList);
              /*  feesBoolean=true;
                chk_selectAll.setChecked(false);
                int size = feesList.size();
                for(int i=0;i<size;i++)
                {
                    TShirtUnpaidModel feesUnpaidModel = feesList.get(i);
                    feesUnpaidModel.setStatus("0");
                }
                adapter.notifyDataSetChanged();*/
                chk_selectAll.setVisibility(View.GONE);
                txt_selectAll.setVisibility(View.GONE);
             //   initTshirtSize();
                if(collegeName.equals("All")){

                   // adapter.filter(collegeName, originalList,null);
                    feesList.clear();
                    feesList.addAll(originalList);
                    initTshirtSize();
                }
                if(!collegeName.equals("All")) {
                    adapter.filter(collegeName, originalList,null);
                    SCount=0;
                    MCount=0;
                    LCount=0;
                    XLCount=0;
                    XXLCount=0;
                    initTshirtSize();
                }else{
                    collegeName = "";
                    adapter.filter(collegeName, originalList,null);

                    if(counter!=1){
                        collegeName = "";
                        adapter.filter(collegeName, originalList,null);
                    }
                }

                Log.d("tag","Totalcountssssonselectchange:"+ String.valueOf(lview.getCount()));
                txt_actualTotalCounts.setText(String.valueOf(lview.getCount()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chk_selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            /*    if(feesBoolean) {
                    if (isChecked) {
                        Log.d("Checkedtruesssss", "truesssss");
                        int size = feesList.size();
                        for (int i = 0; i < size; i++) {
                            TShirtUnpaidModel feesUnpaidModel = feesList.get(i);
                            feesUnpaidModel.setStatus("1");
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("Checkedfalsessssssss", "falsessss");
                        int size = feesList.size();
                        for (int i = 0; i < size; i++) {
                            TShirtUnpaidModel feesUnpaidModel = feesList.get(i);
                            feesUnpaidModel.setStatus("0");
                        }
                        adapter.notifyDataSetChanged();
                    }
                }*/

                if(smallBoolean) {
                    Log.d("smallBoolean", "true");
                    if (isChecked) {
                        Log.d("Checkedtruesssss", "truesssss");

                        int size = sizeSList.size();
                        for (int i = 0; i < size; i++) {
                            TShirtSizeModel tShirtSizeModel = sizeSList.get(i);
                            tShirtSizeModel.setStatus("1");
                        }
                        adapterSize.notifyDataSetChanged();
                    } else{
                        Log.d("Checkedfalsessssssss","falsessss");
                        int size = sizeSList.size();
                        for(int i=0;i<size;i++)
                        {
                            TShirtSizeModel tShirtSizeModel = sizeSList.get(i);
                            tShirtSizeModel.setStatus("0");
                        }
                        adapterSize.notifyDataSetChanged();
                    }
                }
                if(mediumBoolean){
                    Log.d("mediumBoolean","true");
                    if (isChecked) {
                        Log.d("Checkedtruesssss", "truesssss");
                        int size = sizeMList.size();
                        for (int i = 0; i < size; i++) {
                            TShirtSizeModel tShirtSizeModel = sizeMList.get(i);
                            tShirtSizeModel.setStatus("1");
                        }
                        adapterSize.notifyDataSetChanged();
                    } else{
                        Log.d("Checkedfalsessssssss","falsessss");
                        int size = sizeMList.size();
                        for(int i=0;i<size;i++)
                        {
                            TShirtSizeModel tShirtSizeModel = sizeMList.get(i);
                            tShirtSizeModel.setStatus("0");
                        }
                        adapterSize.notifyDataSetChanged();
                    }
                }
                if(largeBoolean){
                    Log.d("largeBoolean","true");
                    if (isChecked) {
                        Log.d("Checkedtruesssss", "truesssss");

                        int size = sizeLList.size();
                        for (int i = 0; i < size; i++) {
                            TShirtSizeModel tShirtSizeModel = sizeLList.get(i);
                            tShirtSizeModel.setStatus("1");
                        }
                        adapterSize.notifyDataSetChanged();
                    } else{
                        Log.d("Checkedfalsessssssss","falsessss");
                        int size = sizeLList.size();
                        for(int i=0;i<size;i++)
                        {
                            TShirtSizeModel tShirtSizeModel = sizeLList.get(i);
                            tShirtSizeModel.setStatus("0");
                        }
                        adapterSize.notifyDataSetChanged();
                    }
                }
                if(XlargeBoolean){
                    Log.d("XlargeBoolean","true");
                    if (isChecked) {
                        Log.d("Checkedtruesssss", "truesssss");

                        int size = sizeXLList.size();
                        for (int i = 0; i < size; i++) {
                            TShirtSizeModel tShirtSizeModel = sizeXLList.get(i);
                            tShirtSizeModel.setStatus("1");
                        }
                        adapterSize.notifyDataSetChanged();
                    } else{
                        Log.d("Checkedfalsessssssss","falsessss");
                        int size = sizeXLList.size();
                        for(int i=0;i<size;i++)
                        {
                            TShirtSizeModel tShirtSizeModel = sizeXLList.get(i);
                            tShirtSizeModel.setStatus("0");
                        }
                        adapterSize.notifyDataSetChanged();
                    }
                }
                 if(XXlargeBoolean){
                    Log.d("largeBoolean","true");
                    if (isChecked) {
                        Log.d("Checkedtruesssss", "truesssss");

                        int size = sizeXXLList.size();
                        for (int i = 0; i < size; i++) {
                            TShirtSizeModel tShirtSizeModel = sizeXXLList.get(i);
                            tShirtSizeModel.setStatus("1");
                        }
                        adapterSize.notifyDataSetChanged();
                    } else{
                        Log.d("Checkedfalsessssssss","falsessss");
                        int size = sizeXXLList.size();
                        for(int i=0;i<size;i++)
                        {
                            TShirtSizeModel tShirtSizeModel = sizeXXLList.get(i);
                            tShirtSizeModel.setStatus("0");
                        }
                        adapterSize.notifyDataSetChanged();
                    }
                }
            }
        });



       /* btn_selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = feesList.size();
                for(int i=0;i<size;i++)
                {
                    FeesUnpaidModel feesUnpaidModel = feesList.get(i);
                    feesUnpaidModel.setStatus("1");
                }

                adapter.notifyDataSetChanged();
            }
        });*/



       /* btn_selectNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = feesList.size();
                for(int i=0;i<size;i++)
                {
                    FeesUnpaidModel feesUnpaidModel = feesList.get(i);
                    feesUnpaidModel.setStatus("0");
                }
                adapter.notifyDataSetChanged();
            }
        });*/


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lview.getCount()iss", String.valueOf(lview.getCount()));
                arrLstSize.clear();
                arrLstreqId.clear();
                for ( counts=0 ; counts < lview.getCount() ; counts++) {
                    View vw = lview.getAdapter().getView(counts,null,null);
                    CheckBox checkBox = (CheckBox) vw.findViewById(R.id.check_status);

                    if(checkBox.isChecked()) {
                        TextView txt_leadId = (TextView) vw.findViewById(R.id.txt_leadId);
                        TextView txt_requestedId=(TextView) vw.findViewById(R.id.txt_requestedId);
                        TextView txt_tShirtSize=(TextView) vw.findViewById(R.id.txt_tShirtSize);
                        SizeSelected = txt_tShirtSize.getText().toString();
                        RequestIdSelected = txt_requestedId.getText().toString();
                        Log.d("TextLeadIdissss", txt_leadId.getText().toString());
                        Log.d("TextrequestedId", txt_requestedId.getText().toString());
                        arrLstLeadIds.add(txt_leadId.getText().toString());
                        arrLstreqId.add(txt_requestedId.getText().toString());
                        arrLstSize.add(txt_tShirtSize.getText().toString());
                        Log.d("arrLstSize=", String.valueOf(arrLstSize.size()));

                        arrLstLeadIds_static.add(txt_leadId.getText().toString());
                        arrLstreqId_static.add(txt_requestedId.getText().toString());
               /*         if(counts != (lview.getCount()-1)) {
                            str_leadIdOverall = str_leadIdOverall + "'" + txt_leadId.getText().toString() + "',";
                        }else{
                            str_leadIdOverall = str_leadIdOverall + "'" + txt_leadId.getText().toString() + "'";
                        }*/

                        str_leadIdOverall = "'" + txt_leadId.getText().toString() + "'";

                        str_requestedIdOverall = "'" + txt_requestedId.getText().toString() + "'";
                        arrayLeadId.add(str_leadIdOverall);
                        arrayreqId.add(str_requestedIdOverall);

                        Log.d("str_leadIdOverallis",str_leadIdOverall);
                        Log.d("int_requestedIdOverall",str_requestedIdOverall);

                        //

                        counter++;

                    }

                }
                //Log.d("countsssissss", String.valueOf(counts));

                //Log.d("countervalueiss", String.valueOf(counter));
                //Log.d("lviewcountsssiss", String.valueOf(lview.getCount()));

          /*      if(counter>0 && counter<lview.getCount()){
                    str_leadIdOverall = str_leadIdOverall.substring(0,str_leadIdOverall.length()-1);
                    Log.d("str_leadIdOverallisxxxxxxx",str_leadIdOverall);
                }*/

                if(counter!=0){
                    str_leadIdOverall = "";
                    str_requestedIdOverall = "";
                    for(int i=0;i<arrayLeadId.size();i++){
                        str_leadIdOverall = str_leadIdOverall + arrayLeadId.get(i) + ",";
                        str_requestedIdOverall = str_requestedIdOverall + arrayreqId.get(i) + ",";
                        Log.d("stringleadidssssssxxxxx",str_leadIdOverall);
                        Log.d("stringreqidssssssxxxxx",str_requestedIdOverall);

                        if(i == arrayLeadId.size()-1){
                            str_leadIdOverall = str_leadIdOverall.substring(0,str_leadIdOverall.length()-1);
                        }
                        if(i == arrayreqId.size()-1){
                            str_requestedIdOverall = str_requestedIdOverall.substring(0,str_requestedIdOverall.length()-1);
                        }

                    }
                }


                if(counter!=0) {
                    //Log.d("stringfinalisssddddd:",str_leadIdOverall);
        /*            getActivity().finish();
                    startActivity(getActivity().getIntent());*/

        Log.e("tag","arrLstSize.size()="+arrLstSize.size()+"UsedS_Int="+UsedS_Int+"allotedS_int="+allotedS_int);

        int count_s=0,count_m=0,count_l=0,count_xl=0,count_xxl=0;
        for(int s=0;s<arrLstSize.size();s++)
        {
           if(arrLstSize.get(s).equals("S")){
               count_s=count_s+1;
           }
            if(arrLstSize.get(s).equals("M")){
                count_m=count_m+1;
            }
            if(arrLstSize.get(s).equals("L")){
                count_l=count_l+1;
            }
            if(arrLstSize.get(s).equals("XL")){
                count_xl=count_xl+1;
            }
            if(arrLstSize.get(s).equals("XXL")){
                count_xxl=count_xxl+1;
            }
        }

        boolean s_status=false,m_status=false,l_status=false,xl_status=false,xxl_status=false;
        Integer finalCount_S=allotedS_int-UsedS_Int;
        Integer finalCount_M=allotedM_int-UsedM_Int;
        Integer finalCount_L=allotedL_int-UsedL_Int;
        Integer finalCount_XL=allotedXL_int-UsedXL_Int;
        Integer finalCount_XXL=allotedXXL_int-UsedXXL_Int;

           /*     if(count_s!=0) {
                    if (UsedS_Int == allotedS_int) {
                        s_status = false;
                    } else if (allotedS_int == 0 || count_s >= allotedS_int) {
                        s_status = false;
                    } else {
                        s_status = true;
                    }
                }else{
                    s_status=true;
                }*/
                      //  Toast.makeText(getActivity(), "S-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();

               /*     if(count_m!=0) {
                        if (UsedM_Int == allotedM_int) {
                            m_status = false;
                        } else if (allotedM_int == 0 || count_m >= allotedM_int || count_m>allotedM_int) {
                            m_status = false;
                        } else {
                            m_status = true;
                        }
                    }else{
                    m_status=true;
                    }*/

                /*    if(count_l!=0) {
                        if (UsedL_Int == allotedL_int) {
                            l_status = false;
                        } else if (allotedL_int == 0 || count_l == allotedL_int || count_l>allotedL_int) {
                            l_status = false;
                        } else {
                            l_status = true;
                        }
                    }else{
                        l_status=true;
                    }

                    if(count_xl!=0) {
                        if (UsedXL_Int == allotedXL_int) {
                            xl_status = false;
                        } else if (allotedXL_int == 0 || count_xl >= allotedXL_int) {
                            xl_status = false;
                        } else {
                            xl_status = true;
                        }
                    }else{
                        xl_status=true;
                    }
                    if(count_xxl!=0) {
                        if (UsedXXL_Int == allotedXXL_int) {
                            xxl_status = false;
                        } else if (allotedXXL_int == 0 || count_xxl >= allotedXXL_int) {
                            xxl_status = false;
                        } else {
                            xxl_status = true;
                        }
                    }else{
                        xxl_status=true;
                    }*/
                if( count_s!=0) {
                    if (count_s <= finalCount_S) {
                        s_status = true;
                    } else {
                        s_status = false;
                    }
                }else {
                    s_status = true;
                }
                if(count_m!=0) {
                    if (count_m <= finalCount_M) {
                        m_status = true;
                    } else {
                        m_status = false;
                    }
                }else {
                    m_status = true;
                }
                if(count_l!=0) {
                    if (count_l <= finalCount_L) {
                        l_status = true;
                    } else {
                        l_status = false;
                    }
                }else {
                    l_status = true;
                }
                if(count_xl!=0) {
                    if (count_xl <= finalCount_XL) {
                        xl_status = true;
                    } else {
                        xl_status = false;
                    }
                }else{
                    xl_status = true;
                }
                if(count_xxl!=0) {
                    if (count_xxl <= finalCount_XXL) {
                        xxl_status = true;
                    } else {
                        xxl_status = false;
                    }
                }else{
                    xxl_status = true;
                }
                    if(s_status==false) {
                        Toast.makeText(getActivity(), "S-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }
                    else if (m_status==false) {
                        Toast.makeText(getActivity(), "M-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }
                    else if (l_status==false) {
                        Toast.makeText(getActivity(), "L-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }
                    else if (xl_status==false) {
                        Toast.makeText(getActivity(), "XL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }
                    else if (xxl_status==false) {
                        Toast.makeText(getActivity(), "XXL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }

                         if(s_status==true&&m_status==true&&l_status==true&&xl_status==true&&xxl_status==true){
                             SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                             submitAcknowledgements.execute();
                         }


                   /* if(count_s > allotedS_int ){
                            Toast.makeText(getActivity(), "S-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }
                    else if( count_m > allotedM_int){
                            Toast.makeText(getActivity(), "M-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }
                    else  if ( count_l > allotedL_int) {
                        Toast.makeText(getActivity(), "L-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }
                    else if (count_xl > allotedXL_int) {
                        Toast.makeText(getActivity(), "XL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }
                    else if ( count_xxl > allotedXXL_int) {
                        Toast.makeText(getActivity(), "XXL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if(UsedS_Int == allotedS_int){
                            Toast.makeText(getActivity(), "S-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        }
                        else if(UsedM_Int == allotedM_int){
                                Toast.makeText(getActivity(), "M-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        }
                        else  if (UsedL_Int == allotedL_int) {
                            Toast.makeText(getActivity(), "L-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        }
                        else if (UsedXL_Int == allotedXL_int) {
                            Toast.makeText(getActivity(), "XL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        }
                        else if (UsedXXL_Int == allotedXXL_int) {
                            Toast.makeText(getActivity(), "XXL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        }
                        else {
                            SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                            submitAcknowledgements.execute();
                        }
                    }*/
      /*  if(UsedS_Int == allotedS_int || allotedS_int == 0 || count_s > allotedS_int){
            Toast.makeText(getActivity(), "S-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
        }
        else if(UsedM_Int == allotedM_int || allotedM_int == 0 || count_m > allotedM_int){
            Toast.makeText(getActivity(), "M-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
        }
        else  if (UsedL_Int == allotedL_int || allotedL_int == 0 || count_l > allotedL_int) {
            Toast.makeText(getActivity(), "L-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
        }
        else if (UsedXL_Int == allotedXL_int || allotedXL_int == 0 || count_xl > allotedXL_int) {
            Toast.makeText(getActivity(), "XL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
        }
        else if (UsedXXL_Int == allotedXXL_int || allotedXXL_int == 0 || count_xxl > allotedXXL_int) {
            Toast.makeText(getActivity(), "XXL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
        }
            else {
            SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
            submitAcknowledgements.execute();
        }*/
               /*     if (SizeSelected.equals("S")) {
                        if (UsedS_Int == allotedS_int || allotedS_int == 0) {
                            Toast.makeText(getActivity(), "S-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        } else {
                            SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                            submitAcknowledgements.execute();
                        }
                    } else if (SizeSelected.equals("M")) {
                        if (UsedM_Int == allotedM_int || allotedM_int == 0) {
                            Toast.makeText(getActivity(), "M-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        } else {
                            SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                            submitAcknowledgements.execute();
                        }
                    } else if (SizeSelected.equals("L")) {
                        if (UsedL_Int == allotedL_int || allotedL_int == 0) {
                            Toast.makeText(getActivity(), "L-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        } else {
                            SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                            submitAcknowledgements.execute();
                        }
                    } else if (SizeSelected.equals("XL")) {
                        if (UsedXL_Int == allotedXL_int || allotedXL_int == 0) {
                            Toast.makeText(getActivity(), "XL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        } else {
                            SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                            submitAcknowledgements.execute();
                        }
                    } else if (SizeSelected.equals("XXL")) {
                        if (UsedXXL_Int == allotedXXL_int || allotedXXL_int == 0) {
                            Toast.makeText(getActivity(), "XXL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                        } else {
                            SubmitAcknowledgements submitAcknowledgements = new SubmitAcknowledgements(getActivity());
                            submitAcknowledgements.execute();
                        }
                    }*/
                    //Toast.makeText(getActivity(),"Selected one student",Toast.LENGTH_LONG).show();
          /*          counter = 0;
                    getActivity().finish();
                    startActivity(getActivity().getIntent());*/

                }else{
                    Toast.makeText(getActivity(),"You have not selected any Tshirt",Toast.LENGTH_LONG).show();
                }


                /*if(lview.getCount()==0 && collegeNameLst.size()==1){
                    txt_selectAll.setVisibility(View.GONE);
                    chk_selectAll.setVisibility(View.GONE);
                }else{
                    txt_selectAll.setVisibility(View.VISIBLE);
                    chk_selectAll.setVisibility(View.VISIBLE);
                }*/
            }
        });



        //lview.setClickable(true);

     /*   lview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("insidelistview","listview clicked");
            }
        });*/

/*        lview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("insidelistview","listview clicked");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

   /*     lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {
                //CheckBox itemCheckbox = (CheckBox) view.findViewById(R.id.check_status);
                Log.d("insidelistview","listview clicked");
            }
        });*/

/*        if(spin_college.getSelectedItem()!=null){
            if(collegeNameArrLst.size()==1 && spin_college.getSelectedItem().toString().equals("Select College")){
                txt_selectAll.setVisibility(View.GONE);
                chk_selectAll.setVisibility(View.GONE);
            }
        }else{
            txt_selectAll.setVisibility(View.GONE);
            chk_selectAll.setVisibility(View.GONE);
        }*/

        Log.d("countisssss", String.valueOf(lview.getCount()));

  /*      if(lview.getCount()==0){
            txt_selectAll.setVisibility(View.GONE);
            chk_selectAll.setVisibility(View.GONE);
        }else{
            txt_selectAll.setVisibility(View.VISIBLE);
            chk_selectAll.setVisibility(View.VISIBLE);
        }*/

        Log.d("ListviewCountissssss", String.valueOf(lview.getCount()));
        //txt_actualTotalCount.setText(lview.getCount());


    }

 /*   private void loadCollegeSpinner() {

    }*/

    private void populateList() {

        /*GetFeesUnpaid getFeesUnpaid = new GetFeesUnpaid(getActivity());
        getFeesUnpaid.execute();*/

        GetTShirtUnpaid getTShirtUnpaid = new GetTShirtUnpaid(getActivity());
        getTShirtUnpaid.execute();

    }

    @Override
    public void onRefresh() {
        initializeviews();
        swipeLayout.setRefreshing(false);
    }

    public class SubmitAcknowledgements extends AsyncTask<Void, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //private ProgressBar progressBar;
        private ProgressDialog progressDialog;

        SubmitAcknowledgements (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = submitArrLystLeadIds();

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
           /* if(result!=null) {
                Log.i("result=",result.toString());
                SoapPrimitive S_Status;
                Object  O_Status;
                String  str_Status = null;

             *//*   for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    //Log.d("DistrictResult", list.toString());

                  //  O_Status = list.equals("Status");
                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {

                        //    if (result.toString().equals("Success")) {
                        Toast.makeText(context, "T-Shirt Issued successfully", Toast.LENGTH_LONG).show();
                        //counter = 0;
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    } else {
                        Toast.makeText(context, "Error occured while saving to database", Toast.LENGTH_LONG).show();
                    }
                }*//*
                O_Status = result.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) result.getProperty("Status");
                    Log.d("S_Status:", S_Status.toString());
                    str_Status = S_Status.toString();
                }

                Log.d("tag", "Str_StatusinTShirtPaidActivityisss:" + str_Status);


                if (str_Status.equalsIgnoreCase("Success")) {
                    Toast.makeText(context, "T-Shirt Issued successfully", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            }*/
            if(result.toString().equals("Success")){
                Toast.makeText(context,"T-Shirt Issued Successfully",Toast.LENGTH_LONG).show();
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
            else{
                Toast.makeText(context,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            //progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapPrimitive submitArrLystLeadIds() {
        String METHOD_NAME = "ApproveForTShirt";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ApproveForTShirt";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            JSONObject jsonObject = new JSONObject();
            /*for(int k=0;k<arrLstLeadIds.size() && k<arrLstreqId.size();k++){
                jsonObject.put(arrLstLeadIds.get(k),arrLstreqId.get(k));
            }*/
         for(int k=0;k<arrLstSize.size() && k<arrLstreqId.size();k++){
             jsonObject.put(arrLstreqId.get(k),arrLstSize.get(k));
             Log.e("tag","arrLstreqId.get(k)="+arrLstreqId.get(k).toString());
             Log.e("tag","arrLstSize.get(k)="+arrLstSize.get(k).toString());

         }
            request.addProperty("TshirtList",jsonObject.toString());
            request.addProperty("ManagerId",str_MangerID);
          //  request.addProperty("Size",SizeSelected);
            request.addProperty("RequestType","Approved");
            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            //   request.addProperty("Lead_Id",str_leadIdOverall);
            // request.addProperty("RequestedId",str_requestedIdOverall);
            /* request.addProperty("isFeePaid",1);*/

            Log.d("Requestisxxxxx",request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
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
                // Log.d("soap response1xxxx",envelope.getResponse().toString());


                //  SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("Responseissss",envelope.getResponse().toString());

                //return null;

                return response;

            }catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }

    public class GetAssignedTShirtlist extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetAssignedTShirtlist (Context ctx){
            context = ctx;
         //   progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getAssignedTShirtList();

            //Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
      /*      progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/
           /* progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            Log.d("Resultisssss:",result.toString());


            if(result != null) {

                SoapPrimitive S_AllotedS,S_AllotedM, S_AllotedL, S_AllotedXL, S_AllotedXXL,S_UsedS,S_UsedM,S_UsedL,S_UsedXL,S_UsedXXL,S_Status;
                Object O_AllotedS, O_AllotedM, O_AllotedL, O_AllotedXL, O_AllotedXXL, O_UsedS, O_UsedM, O_UsedL, O_UsedXL, O_UsedXXL,O_Status;



                O_Status = result.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) result.getProperty("Status");
                    Log.d("S_Status:", S_Status.toString());
                    str_status = S_Status.toString();
                }

                Log.d("tag","Str_StatusinTShirtPaidActivityisss:"+str_status);


                if (str_status.equalsIgnoreCase("Success")) {

                    O_AllotedS = result.getProperty("AllotedS");
                    if (!O_AllotedS.toString().equals("anyType{}") && !O_AllotedS.toString().equals(null)) {
                        S_AllotedS = (SoapPrimitive) result.getProperty("AllotedS");
                        Log.d("S_AllotedS:", S_AllotedS.toString());
                        str_allotedS = S_AllotedS.toString();

                        //txt_assignedSmall.setText(str_allotedS);
                    }

                    O_AllotedM = result.getProperty("AllotedM");
                    if (!O_AllotedM.toString().equals("anyType{}") && !O_AllotedM.toString().equals(null)) {
                        S_AllotedM = (SoapPrimitive) result.getProperty("AllotedM");
                        Log.d("S_AllotedM:", S_AllotedM.toString());
                        str_allotedM = S_AllotedM.toString();

                        //txt_assignedMedium.setText(str_allotedM);
                    }

                    O_AllotedL = result.getProperty("AllotedL");
                    if (!O_AllotedL.toString().equals("anyType{}") && !O_AllotedL.toString().equals(null)) {
                        S_AllotedL = (SoapPrimitive) result.getProperty("AllotedL");
                        str_allotedL = S_AllotedL.toString();
                        Log.d("S_AllotedL:", str_allotedL);

                        //txt_assignedLarge.setText(str_allotedL);
                    }

                    O_AllotedXL = result.getProperty("AllotedXL");
                    if (!O_AllotedXL.toString().equals("anyType{}") && !O_AllotedXL.toString().equals(null)) {
                        S_AllotedXL = (SoapPrimitive) result.getProperty("AllotedXL");
                        Log.d("S_AllotedXL", S_AllotedXL.toString());
                        str_allotedXL = S_AllotedXL.toString();
                        Log.d("str_allotedXL", str_allotedXL);

                        //txt_assignedXL.setText(str_allotedXL);
                    }

                    O_AllotedXXL = result.getProperty("AllotedXXL");
                    if (!O_AllotedXXL.toString().equals("anyType{}") && !O_AllotedXXL.toString().equals(null)) {
                        S_AllotedXXL = (SoapPrimitive) result.getProperty("AllotedXXL");
                        Log.d("S_AllotedXXL", S_AllotedXXL.toString());
                        str_allotedXXL = S_AllotedXXL.toString();
                        Log.d("str_allotedXXL", str_allotedXXL);

                     //   txt_assignedXXL.setText(str_allotedXXL);
                    }


                    O_UsedS = result.getProperty("UsedS");
                    if (!O_UsedS.toString().equals("anyType{}") && !O_UsedS.toString().equals(null)) {
                        S_UsedS = (SoapPrimitive) result.getProperty("UsedS");
                        Log.d("S_UsedS:", S_UsedS.toString());
                        str_usedS = S_UsedS.toString();

                       // txt_balSmall.setText(str_usedS);
                    }

                    O_UsedM = result.getProperty("UsedM");
                    if (!O_UsedM.toString().equals("anyType{}") && !O_UsedM.toString().equals(null)) {
                        S_UsedM = (SoapPrimitive) result.getProperty("UsedM");
                        Log.d("S_UsedM:", S_UsedM.toString());
                        str_usedM = S_UsedM.toString();

                    //    txt_balMedium.setText(str_usedM);
                    }

                    O_UsedL = result.getProperty("UsedL");
                    if (!O_UsedL.toString().equals("anyType{}") && !O_UsedL.toString().equals(null)) {
                        S_UsedL = (SoapPrimitive) result.getProperty("UsedL");
                        Log.d("S_UsedL:", S_UsedL.toString());
                        str_usedL = S_UsedL.toString();

                      //  txt_balLarge.setText(str_usedL);
                    }

                    O_UsedXL = result.getProperty("UsedXL");
                    if (!O_UsedXL.toString().equals("anyType{}") && !O_UsedXL.toString().equals(null)) {
                        S_UsedXL = (SoapPrimitive) result.getProperty("UsedXL");
                        Log.d("S_UsedXL:", S_UsedXL.toString());
                        str_usedXL = S_UsedXL.toString();

                      //  txt_balXL.setText(str_usedXL);
                    }

                    O_UsedXXL = result.getProperty("UsedXXL");
                    if (!O_UsedXXL.toString().equals("anyType{}") && !O_UsedXXL.toString().equals(null)) {
                        S_UsedXXL = (SoapPrimitive) result.getProperty("UsedXXL");
                        Log.d("S_UsedXXL:", S_UsedXXL.toString());
                        str_usedXXL = S_UsedXXL.toString();

                        //txt_balXXL.setText(str_usedXXL);
                    }

                     allotedS_int=Integer.parseInt(str_allotedS);
                     allotedM_int=Integer.parseInt(str_allotedM);
                     allotedL_int=Integer.parseInt(str_allotedL);
                     allotedXL_int=Integer.parseInt(str_allotedXL);
                     allotedXXL_int=Integer.parseInt(str_allotedXXL);

                    Integer TotalAlloted_Int=allotedS_int+allotedM_int+allotedL_int+allotedXL_int+allotedXXL_int;
                    Log.i("tag","TotalAlloted_Int="+TotalAlloted_Int);
                 //   txt_assignedTotal.setText(TotalAlloted_Int.toString());


                     UsedS_Int=Integer.parseInt(str_usedS);
                     UsedM_Int=Integer.parseInt(str_usedM);
                     UsedL_Int=Integer.parseInt(str_usedL);
                     UsedXL_Int=Integer.parseInt(str_usedXL);
                     UsedXXL_Int=Integer.parseInt(str_usedXXL);

                     finalCount_S=allotedS_int-UsedS_Int;
                     finalCount_M=allotedM_int-UsedM_Int;
                     finalCount_L=allotedL_int-UsedL_Int;
                     finalCount_XL=allotedXL_int-UsedXL_Int;
                     finalCount_XXL=allotedXXL_int-UsedXXL_Int;

                    Integer TotalUsed_Int=UsedS_Int+UsedM_Int+UsedL_Int+UsedXL_Int+UsedXXL_Int;
                    Log.i("tag","TotalUsed_Int="+TotalUsed_Int);
                   // txt_balTotal.setText(TotalUsed_Int.toString());
                }

                else{
                    Toast.makeText(getActivity(),"There is no tshirt",Toast.LENGTH_LONG).show();
                }


            }

//            progressDialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getAssignedTShirtList() {
        String METHOD_NAME = "GetAssignedTshirtlist";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetAssignedTshirtlist";
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
                Log.d("soapresponse1xxxxFees",envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapresponseyyyyyyyFees",response.toString());

                //return null;

                return response;

            }
            catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

    }
    public static class SubmitExchangeDetails extends AsyncTask<String, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //private ProgressBar progressBar;
        private ProgressDialog progressDialog;
        String str_resize;
        SubmitExchangeDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(String... params)
        {
            String str_leadId = (String) params[0];
            String str_requestId = (String) params[1];
            String str_size = (String) params[2];
             str_resize = (String) params[3];
            String str_reson = (String) params[4];

/*
Integer Scount=0,Mcount=0,Lcount=0,XLcount=0,XXLcount=0;
boolean exchange=true;
if(str_resize.equals("S")){
    Scount=Scount+1;
}else if(str_resize.equals("M")){
    Mcount=Mcount+1;
}else if(str_resize.equals("L")){
    Lcount=Lcount+1;
}else if(str_resize.equals("XL")){
    XLcount=XLcount+1;
}else if(str_resize.equals("XXL")){
    XXLcount=XXLcount+1;
}
*/


/*
    if (finalCount_S >= Scount) {
    exchange=false;
        Toast.makeText(context, "S-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
    } else if (finalCount_M >= Mcount) {
        exchange=false;
        Toast.makeText(context, "M-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
    } else if (finalCount_XL >= XLcount) {
        exchange=false;
        Toast.makeText(context, "XL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
    } else if (finalCount_L >= Lcount) {
        exchange=false;
        Toast.makeText(context, "L-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
    } else if (finalCount_XXL >= XXLcount) {
        exchange=false;
        Toast.makeText(context, "XXL-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
    } else{
        exchange=true;
    }

    if(exchange==true) {*/
        SoapPrimitive response = SubmitExchangeDetailsFun(str_leadId, str_requestId, str_size, str_resize, str_reson);

        Log.d("Soap response issssss", response.toString());

        return response;
   /* }else{
        if(isCancelled()){
        }
    }*/

            /*}
            else{
                String s="fail";
                SoapPrimitive response="fail";

            }*/
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
            /*if(result!=null) {
                Log.i("result=", result.toString());
                SoapPrimitive S_Status;
                Object O_Status;
                String str_Status = null;

                *//*for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    //Log.d("DistrictResult", list.toString());

                    //  O_Status = list.equals("Status");
                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {

                        //    if (result.toString().equals("Success")) {
                        Toast.makeText(context, "T-Shirt Issued successfully", Toast.LENGTH_LONG).show();
                        //counter = 0;


                    } else {
                        Toast.makeText(context, "Error occured while saving to database", Toast.LENGTH_LONG).show();
                    }
                }*//*
                O_Status = result.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) result.getProperty("Status");
                    Log.d("S_Status:", S_Status.toString());
                    str_Status = S_Status.toString();
                }

                Log.d("tag", "Str_StatusinTShirtPaidActivityisss:" + str_Status);


                if (str_Status.equalsIgnoreCase("Success")) {
                    Toast.makeText(context, "T-Shirt Exchanged successfully", Toast.LENGTH_LONG).show();
                    Intent ittFeesPaidToHome = new Intent(context ,TShirtPaidActivity.class);
                    context.startActivity(ittFeesPaidToHome);                }
            }*/
            if(result.toString().equals("Success")){
                Toast.makeText(context,"T-Shirt Exchanged Successfully",Toast.LENGTH_LONG).show();
                Intent ittEditProjToProjStatus = new Intent(context,TShirtPaidActivity.class);
                //  ittEditProjToProjStatus.putExtra("pageCount",0);
                context.startActivity(ittEditProjToProjStatus);
            }
            else if(result.toString().equals("unable to exchange the t-shirt")){
                Toast.makeText(context, str_resize+"-Size Tshirt is out of stock", Toast.LENGTH_LONG).show();
                Intent ittEditProjToProjStatus = new Intent(context,TShirtPaidActivity.class);
                //  ittEditProjToProjStatus.putExtra("pageCount",0);
                context.startActivity(ittEditProjToProjStatus);
            }
            else{
                Toast.makeText(context,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            //progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private static SoapPrimitive SubmitExchangeDetailsFun(String str_leadId,String str_requestId, String str_size, String str_resize, String str_reson) {
        String METHOD_NAME = "ExchangeForTShirt";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ExchangeForTShirt";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            JSONObject jsonObject = new JSONObject();
           /* for(int k=0;k<arrLstLeadIds_static.size() && k<arrLstreqId_static.size();k++){
                jsonObject.put(arrLstLeadIds_static.get(k),str_requestId);
            }*/
            jsonObject.put(str_leadId,str_requestId);
            request.addProperty("TshirtList",jsonObject.toString());
            request.addProperty("ManagerId",str_MdID);
            request.addProperty("Size",str_size);
            request.addProperty("NewTshirtSize",str_resize);
            request.addProperty("Reason",str_reson);
            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            //   request.addProperty("Lead_Id",str_leadIdOverall);
            // request.addProperty("RequestedId",str_requestedIdOverall);
            /* request.addProperty("isFeePaid",1);*/

            Log.d("Requestisxxxxx",request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
              HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
           // HttpTransportSE androidHttpTransport = new HttpTransportSE("http://testbed.dfindia.org/leadws/Managerws.asmx?WSDL");

            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                // Log.d("soap response1xxxx",envelope.getResponse().toString());


                //  SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("Responseissss",envelope.getResponse().toString());

                //return null;

                return response;

            }catch(OutOfMemoryError ex){
             /*   getActivity().runOnUiThread(new Runnable() {
                    public void run() {*/
                Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
//                    }
//                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
               /* getActivity().runOnUiThread(new Runnable() {
                    public void run() {*/
                Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
               /*     }
                });*/
            }
        }
        catch(OutOfMemoryError ex){
           /* getActivity().runOnUiThread(new Runnable() {
                public void run() {*/
            Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
              /*  }
            });*/
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
           /* getActivity().runOnUiThread(new Runnable() {
                public void run() {*/
            Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
             /*   }
            });*/
        }
        return null;

    }
    public static class SubmitRejectDetails extends AsyncTask<String, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //     private ProgressBar progressBar;

        SubmitRejectDetails (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapPrimitive doInBackground(String... params) {
            String str_request_id = (String) params[0];
            String str_reson=(String) params[1];
            //   String str_MdId=
            Log.d("str_request_id=",str_request_id+"str_reson="+str_reson);

            SoapPrimitive response = CancelProjectDetails(str_request_id,str_reson);

            //   SoapObject response = ReApplyProjectDetails();

            return response;
        }

        @Override
        protected void onPreExecute() {
            //  progressBar = (ProgressBar) findViewById(R.id.progressBar);
            //   progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result.toString().equals("Success")){
                Toast.makeText(context,"T-Shirt Rejected Successfully",Toast.LENGTH_LONG).show();
                Intent ittEditProjToProjStatus = new Intent(context,TShirtPaidActivity.class);
                //  ittEditProjToProjStatus.putExtra("pageCount",0);
                context.startActivity(ittEditProjToProjStatus);
            }
            else{
                Toast.makeText(context,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            // progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    private static SoapPrimitive CancelProjectDetails(String str_request_id, String str_reson) {
        String METHOD_NAME = "RejectForTshirt";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/RejectForTshirt";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            request.addProperty("ManagerId",str_MdID);
            Log.d("str_MangerID", String.valueOf(str_MdID));
            request.addProperty("RequestId",str_request_id);
            Log.d("Remark",str_reson);
            request.addProperty("Remark",str_reson);
            Log.d("Requestisxxxxx",request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
          //  HttpTransportSE androidHttpTransport = new HttpTransportSE("http://testbed.dfindia.org/leadws/Managerws.asmx");
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                // Log.d("soap response1xxxx",envelope.getResponse().toString());

                //  SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("soap response rejected==",envelope.getResponse().toString());

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

    public static class SubmitUnapprove extends AsyncTask<String, Void, SoapPrimitive> {

        AlertDialog alertDialog;
        //private ProgressBar progressBar;
        private ProgressDialog progressDialog;

        SubmitUnapprove (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(String... params) {
            String str_requestId = (String) params[0];
            String str_size = (String) params[1];
            String str_leadId = (String) params[2];

            SoapPrimitive response = SubmitUnapproveFun(str_requestId,str_size,str_leadId);

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
           /* if(result!=null) {
                Log.i("result=",result.toString());
                SoapPrimitive S_Status;
                Object  O_Status;
                String  str_Status = null;

             *//*   for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    //Log.d("DistrictResult", list.toString());

                  //  O_Status = list.equals("Status");
                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {

                        //    if (result.toString().equals("Success")) {
                        Toast.makeText(context, "T-Shirt Issued successfully", Toast.LENGTH_LONG).show();
                        //counter = 0;
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    } else {
                        Toast.makeText(context, "Error occured while saving to database", Toast.LENGTH_LONG).show();
                    }
                }*//*
                O_Status = result.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) result.getProperty("Status");
                    Log.d("S_Status:", S_Status.toString());
                    str_Status = S_Status.toString();
                }

                Log.d("tag", "Str_StatusinTShirtPaidActivityisss:" + str_Status);


                if (str_Status.equalsIgnoreCase("Success")) {
                    Toast.makeText(context, "T-Shirt Issued successfully", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            }*/
            if(result.toString().equals("Success")){
                Toast.makeText(context,"T-Shirt Unapproved Successfully",Toast.LENGTH_LONG).show();
                /*getActivity().finish();
                startActivity(getActivity().getIntent());*/
                /*Intent ittEditProjToProjStatus = new Intent(context,TShirtPaidActivity.class);
                //  ittEditProjToProjStatus.putExtra("pageCount",0);
                context.startActivity(ittEditProjToProjStatus);*/
                Intent ittEditProjToProjStatus = new Intent(context,TShirtPaidActivity.class);
                //  ittEditProjToProjStatus.putExtra("pageCount",0);
                context.startActivity(ittEditProjToProjStatus);
            }
            else{
                Toast.makeText(context,"Error occured while saving to database",Toast.LENGTH_LONG).show();
            }
            //progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private static SoapPrimitive SubmitUnapproveFun(String str_requestId,String str_size,String str_leadId) {
        String METHOD_NAME = "ApproveForTShirt";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ApproveForTShirt";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            JSONObject jsonObject = new JSONObject();
            /*for(int k=0;k<arrLstLeadIds.size() && k<arrLstreqId.size();k++){
                jsonObject.put(arrLstLeadIds.get(k),arrLstreqId.get(k));
            }*/
           // jsonObject.put(str_leadId,str_requestId);
            jsonObject.put(str_requestId,str_size);
            request.addProperty("TshirtList",jsonObject.toString());
            request.addProperty("ManagerId",str_MdID);
          //  request.addProperty("Size",str_size);
            request.addProperty("RequestType","ApproveRollBacked");
            //SoapObject users = new SoapObject("http://mis.leadcampus.org/", "users");
            //   request.addProperty("Lead_Id",str_leadIdOverall);
            // request.addProperty("RequestedId",str_requestedIdOverall);
            /* request.addProperty("isFeePaid",1);*/

            Log.d("Requestisxxxxx",request.toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
              HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Manager.toString().trim());
       //     HttpTransportSE androidHttpTransport = new HttpTransportSE("http://testbed.dfindia.org/leadws/Managerws.asmx?WSDL");

            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                //SoapPrimitive response1 = (SoapPrimitive) envelope.getResponse();
                // Log.d("soap response1xxxx",envelope.getResponse().toString());


                //  SoapObject response = (SoapObject) envelope.getResponse();
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("Responseissss",envelope.getResponse().toString());

                //return null;

                return response;

            }catch(OutOfMemoryError ex){
               /* getActivity().runOnUiThread(new Runnable() {
                    public void run() {*/
                Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    /*}
                });*/
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());

                final String exceptionStr = t.getMessage().toString();
              /*  getActivity().runOnUiThread(new Runnable() {
                    public void run() {*/
                Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                   /* }
                });*/
            }
        }
        catch(OutOfMemoryError ex){
            /*getActivity().runOnUiThread(new Runnable() {
                public void run() {*/
            Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                /*}
            });*/
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());

            final String exceptionStr = t.getMessage().toString();
           /* getActivity().runOnUiThread(new Runnable() {
                public void run() {*/
            Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                /*}
            });*/
        }
        return null;

    }



    public class GetTShirtUnpaid extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetTShirtUnpaid (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getTShirtUnpaid();

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

            String finalResult = result.toString();
            //String finals = finalResult.replace("anyType","");
            Log.d("Finalresultissis",finalResult);


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

            if(result != null) {

                SoapPrimitive S_LeadId, S_StudentName, S_CollegeName, S_MobileNo, S_Status,S_TShirtSize,S_ProjectCount,S_RequestedId;
                Object O_LeadId, O_StudentName, O_CollegeName, O_MobileNo, O_Status, O_TShirtSize,O_ProjectCount,O_RequestedId;
                String str_leadid = null, str_studentName = null, str_registrationDate = null, str_collegeName = null, str_MobileNo = null, str_Status = null, str_tshirtSize = null,str_projectCount = null,str_requestedId=null;

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

                   /*     O_RegistrationDate = list.getProperty("RegistrationDate");
                        if (!O_RegistrationDate.toString().equals("anyType{}") && !O_RegistrationDate.toString().equals(null)) {
                            S_RegistrationDate = (SoapPrimitive) list.getProperty("RegistrationDate");
                            Log.d("S_RegistrationDate", S_RegistrationDate.toString());
                            str_registrationDate = S_RegistrationDate.toString();
                            Log.d("str_registrationDate", str_registrationDate);
                        }*/

                        O_TShirtSize = list.getProperty("TshirtSize");
                        if (!O_TShirtSize.toString().equals("anyType{}") && !O_TShirtSize.toString().equals(null)) {
                            S_TShirtSize = (SoapPrimitive) list.getProperty("TshirtSize");
                            Log.d("S_TShirtSize", S_TShirtSize.toString());
                            str_tshirtSize = S_TShirtSize.toString();
                            Log.d("str_TShirtSize", str_tshirtSize);

                            tshirtSizeLst.add(str_tshirtSize);
                        }

                        O_CollegeName = list.getProperty("CollegeName");
                        if (!O_CollegeName.toString().equals("anyType{}") && !O_CollegeName.toString().equals(null)) {
                            S_CollegeName = (SoapPrimitive) list.getProperty("CollegeName");
                            Log.d("S_CollegeName", S_CollegeName.toString());
                            str_collegeName = S_CollegeName.toString();
                            Log.d("str_collegeName", str_collegeName);

                            collegeNameLst.add(str_collegeName);

                        }

                        O_MobileNo = list.getProperty("MobileNo");
                        if (!O_MobileNo.toString().equals("anyType{}") && !O_MobileNo.toString().equals(null)) {
                            S_MobileNo = (SoapPrimitive) list.getProperty("MobileNo");
                            Log.d("S_MobileNo", S_MobileNo.toString());
                            str_MobileNo = S_MobileNo.toString();
                        }

                        O_ProjectCount = list.getProperty("projectcount");
                        if (!O_ProjectCount.toString().equals("anyType{}") && !O_ProjectCount.toString().equals(null)) {
                            S_ProjectCount = (SoapPrimitive) list.getProperty("projectcount");
                            Log.d("S_ProjectCount", S_ProjectCount.toString());
                            str_projectCount = S_ProjectCount.toString();
                        }

                        Log.d("tag","StringProjectCountisss: "+str_projectCount);

                        O_RequestedId = list.getProperty("RequestedId");
                        if (!O_RequestedId.toString().equals("anyType{}") && !O_RequestedId.toString().equals(null)) {
                            S_RequestedId = (SoapPrimitive) list.getProperty("RequestedId");
                            Log.d("S_RequestedId", S_RequestedId.toString());
                            str_requestedId = S_RequestedId.toString();
                        }

                        Log.d("RequestedIdisss",str_requestedId);









                    /*    O_IsFeesPaid = list.getProperty("isFeePaid");
                        if (!O_IsFeesPaid.toString().equals("anyType{}") && !O_IsFeesPaid.toString().equals(null)) {
                            S_IsFeesPaid = (SoapPrimitive) list.getProperty("isFeePaid");
                            Log.d("S_IsFeesPaid", S_IsFeesPaid.toString());
                            str_IsFeesPaid = S_IsFeesPaid.toString();
                        }
*/
                        TShirtUnpaidModel item;

                        item = new TShirtUnpaidModel(str_studentName, str_leadid,str_tshirtSize,str_collegeName, str_MobileNo, str_Status,str_projectCount,str_requestedId);
                        feesList.add(item);
                    }

                }

                originalList = new ArrayList<TShirtUnpaidModel>();
                originalList.addAll(feesList);

                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);

                Log.d("Totalcountssss:", String.valueOf(lview.getCount()));
                txt_actualTotalCounts.setText(String.valueOf(lview.getCount()));

                initCollegeSpinner();

                //initTshirtSize();
            }

            progressDialog.dismiss();



        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getTShirtUnpaid() {
        String METHOD_NAME = "GetApplyTshirtRequestlist";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetApplyTshirtRequestlist";
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
                Log.d("tag","soapresponse1xxxxTShirtUnpaid"+envelope.getResponse().toString());


                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("tag","soapresponseyyyyyyyTShirtUnpaid"+response.toString());

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


    private void initTshirtSize(){

        tshirtSizeArrLst.addAll(tshirtSizeLst);
        Log.i("tag","tshirtSizeArrLst size="+tshirtSizeArrLst.size());
        Log.i("tag","tshirtSizeArrLst string="+tshirtSizeArrLst.toString());
        List<String> tshirt= tshirtSizeArrLst;
        List<TShirtUnpaidModel> allList = feesList;
        String student_name;
        String lead_id;
        String tshirt_size;
        //private String registration_date;
        String college_name;
        String phone_number;
        String status;
        String proj_count;
        String requested_id;
        Log.i("tag","tshirt string="+tshirt);
        Log.i("tag","feesList.size="+feesList.size());
        SCount=0;MCount=0;LCount=0;XLCount=0;XXLCount=0;
        setupS();setupM();setupL();setupXL();setupXXL();
        sizeSList.clear(); sizeMList.clear();sizeLList.clear();sizeXLList.clear();sizeXXLList.clear();

        adapter = new TShirtUnpaidAdapter(getActivity(),feesList);
        lview.setAdapter(adapter);

        for(int i=0;i<feesList.size();i++){
            Log.i("tag","feesList tshirt size="+feesList.get(i).getTshirt_size());
            String tshirtnew=feesList.get(i).getTshirt_size();

            if(tshirtnew.equals("S")){

                chk_selectAll.setChecked(false);
                smallBoolean=true;
                SCount=SCount+1;
                student_name=feesList.get(i).getStudent_name();
                lead_id=feesList.get(i).getLead_id();
                tshirt_size=feesList.get(i).getTshirt_size();
                college_name=feesList.get(i).getCollege_name();
                phone_number=feesList.get(i).getPhone_number();
                status=feesList.get(i).getStatus();
                proj_count=feesList.get(i).getProj_count();
                requested_id=feesList.get(i).getRequested_id();

                TShirtSizeModel item;
                item = new TShirtSizeModel(student_name, lead_id,tshirt_size,college_name, phone_number,status,proj_count,requested_id);
                sizeSList.add(item);
                Log.i("tag","SCount ="+SCount);

                setupS();
                small_tshirt.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                        adapterSize = new TShirtSizeAdapter(getActivity(),sizeSList);
                        lview.setAdapter(adapterSize);
                    }
                });
                //   adapter = new TShirtUnpaidAdapter(getActivity(),feesList);

                Log.i("tag","lview S=="+sizeSList.toString());
            }
            if(tshirtnew.equals("M")){


                MCount=MCount+1;
                chk_selectAll.setChecked(false);
                mediumBoolean=true;
                Log.i("tag","MCount ="+MCount);
                student_name=feesList.get(i).getStudent_name();
                lead_id=feesList.get(i).getLead_id();
                tshirt_size=feesList.get(i).getTshirt_size();
                college_name=feesList.get(i).getCollege_name();
                phone_number=feesList.get(i).getPhone_number();
                status=feesList.get(i).getStatus();
                proj_count=feesList.get(i).getProj_count();
                requested_id=feesList.get(i).getRequested_id();

                TShirtSizeModel item;
                item = new TShirtSizeModel(student_name, lead_id,tshirt_size,college_name, phone_number,status,proj_count,requested_id);
                sizeMList.add(item);
                setupM();

                medium_tshirt.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                        adapterSize = new TShirtSizeAdapter(getActivity(),sizeMList);
                        lview.setAdapter(adapterSize);
                    }
                });
                //  adapter = new TShirtUnpaidAdapter(getActivity(),feesList);

                Log.i("tag","lview M=="+sizeMList.toString());
            }
            if(tshirtnew.equals("L")){


                LCount=LCount+1;
                chk_selectAll.setChecked(false);
                largeBoolean=true;
               /* int size = sizeLList.size();
                for(int j=0;i<size;i++)
                {
                    TShirtSizeModel feesUnpaidModel = sizeLList.get(j);
                    feesUnpaidModel.setStatus("0");
                }
                adapterSize.notifyDataSetChanged();*/
                Log.i("tag","LCount ="+LCount);
                student_name=feesList.get(i).getStudent_name();
                lead_id=feesList.get(i).getLead_id();
                tshirt_size=feesList.get(i).getTshirt_size();
                college_name=feesList.get(i).getCollege_name();
                phone_number=feesList.get(i).getPhone_number();
                status=feesList.get(i).getStatus();
                proj_count=feesList.get(i).getProj_count();
                requested_id=feesList.get(i).getRequested_id();

                TShirtSizeModel item;
                item = new TShirtSizeModel(student_name, lead_id,tshirt_size,college_name, phone_number,"0",proj_count,requested_id);
                sizeLList.add(item);

                setupL();
                //  adapter = new TShirtUnpaidAdapter(getActivity(),feesList);
                large_tshirt.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                        adapterSize = new TShirtSizeAdapter(getActivity(),sizeLList);
                        lview.setAdapter(adapterSize);
                    }
                });
                Log.i("tag","lview L=="+sizeLList.toString());
            }
            if(tshirtnew.equals("XL")){


                XLCount=XLCount+1;
                chk_selectAll.setChecked(false);
                XlargeBoolean=true;
                int size = sizeXLList.size();
              /*  for(int j=0;i<size;i++)
                {
                    TShirtSizeModel feesUnpaidModel = sizeXLList.get(j);
                    feesUnpaidModel.setStatus("0");
                }
                adapter.notifyDataSetChanged();*/
                Log.i("tag","LCount ="+XLCount);
                student_name=feesList.get(i).getStudent_name();
                lead_id=feesList.get(i).getLead_id();
                tshirt_size=feesList.get(i).getTshirt_size();
                college_name=feesList.get(i).getCollege_name();
                phone_number=feesList.get(i).getPhone_number();
                status=feesList.get(i).getStatus();
                proj_count=feesList.get(i).getProj_count();
                requested_id=feesList.get(i).getRequested_id();

                TShirtSizeModel item;
                item = new TShirtSizeModel(student_name, lead_id,tshirt_size,college_name, phone_number,status,proj_count,requested_id);
                sizeXLList.add(item);

                setupXL();
                //  adapter = new TShirtUnpaidAdapter(getActivity(),feesList);
                xlarge_tshirt.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                        adapterSize = new TShirtSizeAdapter(getActivity(),sizeXLList);
                        lview.setAdapter(adapterSize);
                    }
                });
                Log.i("tag","lview XL=="+sizeXLList.toString());
            }
            if(tshirtnew.equals("XXL")){

              //  sizeXXLList.clear();
                XXLCount=XXLCount+1;
                chk_selectAll.setChecked(false);
                XXlargeBoolean=true;
                int size = sizeXXLList.size();
               /* for(int j=0;i<size;i++)
                {
                    TShirtSizeModel feesUnpaidModel = sizeXXLList.get(j);
                    feesUnpaidModel.setStatus("0");
                }
                adapter.notifyDataSetChanged();*/
                Log.i("tag","LCount ="+LCount);
                student_name=feesList.get(i).getStudent_name();
                lead_id=feesList.get(i).getLead_id();
                tshirt_size=feesList.get(i).getTshirt_size();
                college_name=feesList.get(i).getCollege_name();
                phone_number=feesList.get(i).getPhone_number();
                status=feesList.get(i).getStatus();
                proj_count=feesList.get(i).getProj_count();
                requested_id=feesList.get(i).getRequested_id();

                TShirtSizeModel item;
                item = new TShirtSizeModel(student_name, lead_id,tshirt_size,college_name, phone_number,status,proj_count,requested_id);
                sizeXXLList.add(item);

                setupXXL();
                //  adapter = new TShirtUnpaidAdapter(getActivity(),feesList);
                xxlarge_tshirt.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                        adapterSize = new TShirtSizeAdapter(getActivity(),sizeXXLList);
                        lview.setAdapter(adapterSize);
                    }
                });
                Log.i("tag","lview L=="+sizeXXLList.toString());
            }
        }
    }

    private void initCollegeSpinner() {
        //final ArrayList projType = new ArrayList();
   /*     Log.d("collegeNameLstisss",collegeNameLst.toString());

        for(String collegeName : collegeNameLst){
            Log.d("collegeNameissss",collegeName);
        }*/

        collegeNameArrLst.addAll(collegeNameLst);

        ArrayAdapter dataAdapter2 = new ArrayAdapter(getActivity(), R.layout.simple_spinner_items, collegeNameArrLst);

        // Drop down layout style - list view with radio button
        //dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(R.layout.spinnercustomstyle);

        // attaching data adapter to spinner
        spin_college.setAdapter(dataAdapter2);
        //spin_projectType.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

        //spin_college.setSupportBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.colorWhite));

        Log.d("countisssss", String.valueOf(lview.getCount()));

        /*if(lview.getCount()==0){
            txt_selectAll.setVisibility(View.GONE);
            chk_selectAll.setVisibility(View.GONE);
        }else{
            txt_selectAll.setVisibility(View.VISIBLE);
            chk_selectAll.setVisibility(View.VISIBLE);
        }*/

    }

    private void setupS() {

        if (textCartTSCount != null) {
            if (SCount == 0) {
                if (textCartTSCount.getVisibility() != View.GONE) {
                    textCartTSCount.setVisibility(View.GONE);
                }
            } else {
                //  textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 110)));
                textCartTSCount.setText(String.valueOf(SCount));
                if (textCartTSCount.getVisibility() != View.VISIBLE) {
                    textCartTSCount.setVisibility(View.VISIBLE);

                    //  adapterSize = new TShirtSizeAdapter(getActivity(),sizeList);
                    // lview.setAdapter(adapterSize);
                  /*  if(lview.getCount()==0){
                        txt_selectAll.setVisibility(View.GONE);
                        chk_selectAll.setVisibility(View.GONE);
                    }else{
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                    }*/
                }
            }
        }
    }

    private void setupM() {

        if (textCartTMCount != null) {
            if (MCount == 0) {
                if (textCartTMCount.getVisibility() != View.GONE) {
                    textCartTMCount.setVisibility(View.GONE);
                }
            } else {
                //  textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 110)));
                textCartTMCount.setText(String.valueOf(MCount));
                if (textCartTMCount.getVisibility() != View.VISIBLE) {
                    textCartTMCount.setVisibility(View.VISIBLE);

                    //  adapterSize = new TShirtSizeAdapter(getActivity(),sizeList);
                    // lview.setAdapter(adapterSize);
                  /*  if(lview.getCount()==0){
                        txt_selectAll.setVisibility(View.GONE);
                        chk_selectAll.setVisibility(View.GONE);
                    }else{
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                    }*/
                }
            }
        }
    }

    private void setupL() {

        if (textCartTLCount != null) {
            if (LCount == 0) {
                if (textCartTLCount.getVisibility() != View.GONE) {
                    textCartTLCount.setVisibility(View.GONE);
                }
            } else {
                //  textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 110)));
                textCartTLCount.setText(String.valueOf(LCount));
                if (textCartTLCount.getVisibility() != View.VISIBLE) {
                    textCartTLCount.setVisibility(View.VISIBLE);

                    //  adapterSize = new TShirtSizeAdapter(getActivity(),sizeList);
                    // lview.setAdapter(adapterSize);
                   /* if(lview.getCount()==0){
                        txt_selectAll.setVisibility(View.GONE);
                        chk_selectAll.setVisibility(View.GONE);
                    }else{
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                    }*/
                }
            }
        }
    }

    private void setupXL() {

        if (textCartTXLCount != null) {
            if (XLCount == 0) {
                if (textCartTXLCount.getVisibility() != View.GONE) {
                    textCartTXLCount.setVisibility(View.GONE);
                }
            } else {
                //  textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 110)));
                textCartTXLCount.setText(String.valueOf(XLCount));
                if (textCartTXLCount.getVisibility() != View.VISIBLE) {
                    textCartTXLCount.setVisibility(View.VISIBLE);

                    //  adapterSize = new TShirtSizeAdapter(getActivity(),sizeList);
                    // lview.setAdapter(adapterSize);
                   /* if(lview.getCount()==0){
                        txt_selectAll.setVisibility(View.GONE);
                        chk_selectAll.setVisibility(View.GONE);
                    }else{
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                    }*/
                }
            }
        }
    }

    private void setupXXL() {

        if (textCartTXXLCount != null) {
            if (XXLCount == 0) {
                if (textCartTXXLCount.getVisibility() != View.GONE) {
                    textCartTXXLCount.setVisibility(View.GONE);
                }
            } else {
                //  textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 110)));
                textCartTXXLCount.setText(String.valueOf(XXLCount));
                if (textCartTXXLCount.getVisibility() != View.VISIBLE) {
                    textCartTXXLCount.setVisibility(View.VISIBLE);

                    //  adapterSize = new TShirtSizeAdapter(getActivity(),sizeList);
                    // lview.setAdapter(adapterSize);
                   /* if(lview.getCount()==0){
                        txt_selectAll.setVisibility(View.GONE);
                        chk_selectAll.setVisibility(View.GONE);
                    }else{
                        txt_selectAll.setVisibility(View.VISIBLE);
                        chk_selectAll.setVisibility(View.VISIBLE);
                    }*/
                }
            }
        }
    }

}//end of fragment classxx

