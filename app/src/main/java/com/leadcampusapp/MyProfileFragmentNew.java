package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.kofigyan.stateprogressbar.StateProgressBar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MyProfileFragmentNew extends Fragment {

    private LinearLayout lnrlyt_projDtls;
    private LinearLayout lnrlyt_events;
    private LinearLayout lnrlyt_newsfeeds;
    private LinearLayout lnrlyt_aboutUs;

    private LinearLayout lnrlyt_viewProfile;

    TextView about_TV,projectDetails_TV,events_TV,news_TV,contactus_TV;
    ImageView about_IV,projectDetails_IV,events_IV,news_IV,contactus_IV;

    private Context context;
    private AppCompatSpinner spin_state;
    private AppCompatSpinner spin_city;
    private AppCompatSpinner spin_institution;
    private AppCompatSpinner spin_semester;
    private EditText edt_dob;

    private String[] descriptionData = {"Student", "Leader", "Master", "Ambassador"};

    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_SLeadID = "prefid_sleadid"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_SCellNo = "prefid_scellno"; //
    public static final String PrefID_Role = "prefid_role";
    public static final String PrefID_SCollegeName = "prefid_scollegename";
    public static final String PrefID_SImageUrl = "prefid_simgUrl";

    public static final String PrefId_S_Gender = "prefid_sgender";
    public static final String PrefId_S_StarCount = "prefid_sstarCount";

    public static final String PrefID_S_ImgBase64 = "prefid_s_imgBase64"; //
    public static final String PrefID_PM_ImgBase64 = "prefid_pm_imgBase64";


    public static final String PrefID_SStudentType = "prefid_sstudentType"; //

    SharedPreferences shardpref_S_obj;

    private TextView txt_studName;
    private TextView txt_studCollegeName;
    private TextView txt_studType;
    private TextView txt_leadId;
    private ImageView img_studProfPick;
    private ImageView img_crownMan,img_crownWoman,img_studProfPick1;
    private TextView txt_noOfProjects;
    private View view;
    private String str_leadId,str_studName,str_role,str_collegeName,str_studImgUrl,str_username,str_password,str_s_studentType,str_s_gender,str_s_starCount;
    private Result result;
    private String getResult;

    SharedPreferences.Editor editor_S;
    public static final String PrefId_S_Password = "prefid_spassword";
    public static final String PrefId_S_Username = "prefid_susername";
    private String serverPath = Class_URL.ServerPath.toString().trim();

    private FrameLayout fram_layout;
    private int starCount;
    private Button btn_apply;

    private StateProgressBar stateProgressBar;
    public static final String PrefId_S_IsFeesPaid = "prefid_sfeesPaid";
    private String str_isFeesPaid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.myprofile_fragment_new, container, false);
        context = getActivity().getBaseContext();

        initializeViews();



        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);

        if(getActivity().getIntent().hasExtra("fromeditProfile")){
            getResult = getActivity().getIntent().getStringExtra("fromeditProfile");
            Log.d("GetResultsss",getResult);


            shardpref_S_obj.getString(PrefId_S_Username, "").trim();
            str_username = shardpref_S_obj.getString(PrefId_S_Username, "").trim();
            Log.d("str_username",str_username);


            shardpref_S_obj.getString(PrefId_S_Password, "").trim();
            str_password = shardpref_S_obj.getString(PrefId_S_Password, "").trim();
            Log.d("str_password",str_password);

            shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
            str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();

            shardpref_S_obj.getString(PrefId_S_Gender,"").trim();
            str_s_gender = shardpref_S_obj.getString(PrefId_S_Gender, "").trim();

            shardpref_S_obj.getString(PrefId_S_StarCount,"").trim();
            str_s_starCount = shardpref_S_obj.getString(PrefId_S_StarCount, "").trim();
            starCount = Integer.parseInt(str_s_starCount);


            if(starCount>0){
                img_studProfPick1.setVisibility(View.GONE);
                fram_layout.setVisibility(View.VISIBLE);

                if(str_s_gender.equals("M")){

                    img_crownWoman.setVisibility(View.GONE);
                    img_crownMan.setVisibility(View.VISIBLE);

                    txt_noOfProjects.setText(str_s_starCount);
                    txt_noOfProjects.setVisibility(View.VISIBLE);
                }else{

                    img_crownWoman.setVisibility(View.VISIBLE);
                    img_crownMan.setVisibility(View.GONE);

                    txt_noOfProjects.setText(str_s_starCount);
                    txt_noOfProjects.setVisibility(View.VISIBLE);
                }
            }

            GetStudentDetails getStudentDetails = new GetStudentDetails(getActivity());
            getStudentDetails.execute(str_leadId);

        }

        //getStudentDetails();
        else {

            shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
            str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();

            shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
            str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();

            shardpref_S_obj.getString(PrefID_SName, "").trim();
            str_studName = shardpref_S_obj.getString(PrefID_SName, "").trim();

            shardpref_S_obj.getString(PrefID_Role, "").trim();
            str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();

            shardpref_S_obj.getString(PrefID_SCollegeName, "").trim();
            str_collegeName = shardpref_S_obj.getString(PrefID_SCollegeName, "").trim();


            shardpref_S_obj.getString(PrefID_SImageUrl, "").trim();
            str_studImgUrl = shardpref_S_obj.getString(PrefID_SImageUrl, "").trim();


            shardpref_S_obj.getString(PrefID_SStudentType, "").trim();
            str_s_studentType = shardpref_S_obj.getString(PrefID_SStudentType, "").trim();


            shardpref_S_obj.getString(PrefId_S_Gender,"").trim();
            str_s_gender = shardpref_S_obj.getString(PrefId_S_Gender, "").trim();

            shardpref_S_obj.getString(PrefId_S_StarCount,"").trim();
            str_s_starCount = shardpref_S_obj.getString(PrefId_S_StarCount, "").trim();
            starCount = Integer.parseInt(str_s_starCount);











            if (str_studName != null && !str_studName.isEmpty() && str_studName != "" && !str_studName.equals("anyType{}")) {
                txt_studName.setText(str_studName);
            }

            if (str_leadId != null && !str_leadId.isEmpty() && str_leadId != "" && !str_leadId.equals("anyType{}")) {
                txt_leadId.setText(str_leadId);
            }

            if (str_collegeName != null && !str_collegeName.isEmpty() && str_collegeName != "" && !str_collegeName.equals("anyType{}")) {
                txt_studCollegeName.setText(str_collegeName);
            }

         /*   if (str_role != null && !str_role.isEmpty() && str_role != "" && !str_role.equals("anyType{}")) {
                txt_studType.setText(str_role);
            }*/

            if (str_s_studentType != null && !str_s_studentType.isEmpty() && str_s_studentType != "" && !str_s_studentType.equals("anyType{}")) {
                txt_studType.setText(str_s_studentType);
            }

            if (!str_studImgUrl.equals(null) && !str_studImgUrl.equalsIgnoreCase("null") && !str_studImgUrl.isEmpty() && str_studImgUrl != "" && !str_studImgUrl.equals("anyType{}") && str_studImgUrl != "{}") {
                Log.d("InsideLoadProfile", "Pick");

                LoadStudProfilePick loadStudProfilePick = new LoadStudProfilePick(getActivity());
                loadStudProfilePick.execute();


                /*Glide.with(getActivity()).load(str_studImgUrl)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img_studProfPick);*/



           /*     if (img_studProfPick1.getVisibility() == View.VISIBLE) {
                    //img_studProfPick1.setImageBitmap(result);
                    Glide.with(getActivity()).load(str_studImgUrl)
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img_studProfPick1);
                } else {
                    //img_studProfPick.setImageBitmap(result);

                    Glide.with(getActivity()).load(str_studImgUrl)
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img_studProfPick);
                }*/


   /*             if(img_studProfPick1.getVisibility()==View.VISIBLE){

                    //Log.d("studentbitmapss:",StudentPMImageBitmap.getStudBitmap().toString());

                    shardpref_S_obj.getString(PrefID_S_ImgBase64,"").trim();
                    String str_s_imgBase64 = shardpref_S_obj.getString(PrefID_S_ImgBase64, "").trim();

                    Log.d("str_s_imgBase64issss",str_s_imgBase64);

                  *//*  byte[] byteArray = Base64.decode(str_s_imgBase64, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    img_studProfPick1.setImageBitmap(bitmap);*//*
                    try {
                        if (!str_s_imgBase64.equalsIgnoreCase("")) {
                            Log.d("str_s_imgBase64issss", str_s_imgBase64);
                            byte[] b = Base64.decode(str_s_imgBase64, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                            img_studProfPick1.setImageBitmap(bitmap);
                        }
                    }catch(OutOfMemoryError ex){
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                    //img_studProfPick1.setImageBitmap(StudentPMImageBitmap.getStudBitmap());

*//*                    byte[] imageinbytesArray={0};

                    Bitmap yourImage = StudentPMImageBitmap.getStudBitmap();


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    //imageInByte;
                    imageinbytesArray = stream.toByteArray();
                    //	Log.e("output before conversion", imageInByte.toString());
                    //	Log.d("Insert: ", "Inserting ..image");
                    Bitmap bm = BitmapFactory.decodeByteArray(imageinbytesArray, 0, imageinbytesArray.length);*//*
                    //	Log.d("Insert image report: ",bm.toString() );

                    //studPickBase64 = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);
                    //img_studProfPick1.setImageBitmap(bm);

                }
                else{
                    //img_studProfPick.setImageBitmap(StudentPMImageBitmap.getStudBitmap());

                    shardpref_S_obj.getString(PrefID_S_ImgBase64,"").trim();
                    String str_s_imgBase64 = shardpref_S_obj.getString(PrefID_S_ImgBase64, "").trim();



                    try {
                        if (!str_s_imgBase64.equalsIgnoreCase("")) {
                            byte[] b = Base64.decode(str_s_imgBase64, Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                            img_studProfPick.setImageBitmap(bitmap);
                        }
                    }catch(OutOfMemoryError ex){
                        getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                            }
                        });
                    }



                }*/

            }

            if(starCount>0){
                img_studProfPick1.setVisibility(View.GONE);
                fram_layout.setVisibility(View.VISIBLE);

                if(str_s_gender.equals("M")){



                    img_crownWoman.setVisibility(View.GONE);
                    img_crownMan.setVisibility(View.VISIBLE);

      /*              RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(-40,0,0,0);
                    txt_noOfProjects.setLayoutParams(params);*/
                    txt_noOfProjects.setText(str_s_starCount);
                    txt_noOfProjects.setVisibility(View.VISIBLE);


                }else{

            /*        img_studProfPick1.setVisibility(View.VISIBLE);
                    fram_layout.setVisibility(View.GONE);*/

                    img_crownWoman.setVisibility(View.VISIBLE);
                    img_crownMan.setVisibility(View.GONE);






                /*    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(-20,0,0,0);
                    txt_noOfProjects.setLayoutParams(params);*/
                    txt_noOfProjects.setText(str_s_starCount);
                    txt_noOfProjects.setVisibility(View.VISIBLE);
                }
            }


            //Toast.makeText(getActivity(),"Inside My Profile",Toast.LENGTH_SHORT).show();


        }


        shardpref_S_obj.getString(PrefID_SStudentType, "").trim();
        str_s_studentType = shardpref_S_obj.getString(PrefID_SStudentType, "").trim();


        stateProgressBar = (StateProgressBar) view.findViewById(R.id.usage_stateprogressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

        if (str_s_studentType.equals("Student")) {
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
        }

        if (str_s_studentType.equals("Leader")) {
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);


            GetLeaderStatus getLeaderStatus = new GetLeaderStatus(getActivity());
            getLeaderStatus.execute();
        }

        if (str_s_studentType.equals("Master Leader")) {
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);

            GetMasterStatus getMasterStatus = new GetMasterStatus(getActivity());
            getMasterStatus.execute();

        }

        if (str_s_studentType.equals("Lead Ambassador")) {
            stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
        }

        shardpref_S_obj.getString(PrefId_S_IsFeesPaid, "").trim();
        str_isFeesPaid = shardpref_S_obj.getString(PrefId_S_IsFeesPaid, "").trim();
        Log.d("FeesPaidisssssss",str_isFeesPaid);


        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(new GridAdapter(getActivity()));
        GridView gridViewSecond = (GridView) view.findViewById(R.id.gridview1);
        gridViewSecond.setAdapter(new GridAdapterSecond(getActivity())); //GridAdapterSecond.java




     /*   projectDetails_IV = (ImageView) view.findViewById(R.id.projectDetails_IV);
        projectDetails_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_isFeesPaid.equals("1")) {
                    Intent itthomeToProject = new Intent(getActivity().getBaseContext(), ProjectDetails.class);
                    // itthomeToProject.putExtra("From","Student");
                    startActivity(itthomeToProject);
                }else{
                    Toast.makeText(getActivity(),"You have not paid the fees. Please contact Project Manager",Toast.LENGTH_LONG).show();
                }
            }
        });
        projectDetails_TV = (TextView) view.findViewById(R.id.projectDetails_TV);
        projectDetails_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToProject = new Intent(getActivity().getBaseContext(), ProjectDetails.class);
                //  itthomeToProject.putExtra("From","Student");
                startActivity(itthomeToProject);
            }
        });

        events_IV = (ImageView) view.findViewById(R.id.events_IV);
        events_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), EventsActivity.class);
                //   itthomeToEvent.putExtra("From","Student");
                //Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), TestActivity2.class);
                startActivity(itthomeToEvent);
            }
        });
        events_TV = (TextView) view.findViewById(R.id.events_TV);
        events_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), EventsActivity.class);
                // itthomeToEvent.putExtra("From","Student");
                //Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), TestActivity2.class);
                startActivity(itthomeToEvent);
            }
        });

        news_IV = (ImageView) view.findViewById(R.id.news_IV);
        news_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToNewsFeeds = new Intent(getActivity().getBaseContext(), NewsFeedsActivity.class);
                //    itthomeToNewsFeeds.putExtra("From","Student");
                startActivity(itthomeToNewsFeeds);
            }
        });
        news_TV = (TextView) view.findViewById(R.id.news_TV);
        news_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToNewsFeeds = new Intent(getActivity().getBaseContext(), NewsFeedsActivity.class);
                //    itthomeToNewsFeeds.putExtra("From","Student");
                startActivity(itthomeToNewsFeeds);
            }
        });

        about_IV = (ImageView) view.findViewById(R.id.about_IV);
        about_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity().getBaseContext(), AboutUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });
        about_TV = (TextView) view.findViewById(R.id.about_TV);
        about_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity().getBaseContext(), AboutUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });

        contactus_IV = (ImageView) view.findViewById(R.id.contactus_IV);
        contactus_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity().getBaseContext(), ContactUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });
        contactus_TV = (TextView) view.findViewById(R.id.contactus_TV);
        contactus_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity().getBaseContext(), ContactUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });


*/











         /* lnrlyt_viewProfile = (LinearLayout) view.findViewById(R.id.lnrlyt_viewProfile);
        if(lnrlyt_viewProfile.getVisibility()==View.VISIBLE) {

            lnrlyt_projDtls = (LinearLayout) view.findViewById(R.id.lnrlyt_projectDtls);
            lnrlyt_projDtls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent itthomeToProject = new Intent(getActivity().getBaseContext(), ProjectDetails.class);
                    startActivity(itthomeToProject);
                }
            });

            lnrlyt_events = (LinearLayout) view.findViewById(R.id.lnrlyt_events);
            lnrlyt_events.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), EventsActivity.class);
                    startActivity(itthomeToEvent);
                }
            });

            lnrlyt_newsfeeds = (LinearLayout) view.findViewById(R.id.lnrlyt_newsfeeds);
            lnrlyt_newsfeeds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent itthomeToNewsFeeds = new Intent(getActivity().getBaseContext(), NewsFeedsActivity.class);
                    startActivity(itthomeToNewsFeeds);
                }
            });

        }

      else{
            spin_state = (AppCompatSpinner) view.findViewById(R.id.spin_state);
            spin_city = (AppCompatSpinner) view.findViewById(R.id.spin_city);
            spin_institution = (AppCompatSpinner) view.findViewById(R.id.spin_institution);
            spin_semester = (AppCompatSpinner) view.findViewById(R.id.spin_sem);
            edt_dob = (EditText) view.findViewById(R.id.edt_dob);


            setStateSpinner(view);
            setCitySpinner(view);
            setInstitutionSpinner(view);
            setSemesterSpinner(view);

            setDateOfBirth(view);
        }*/

        editor_S = shardpref_S_obj.edit();
        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_s_studentType.equals("Leader")){
                    ApplyForMasterLeader applyForMasterLeader = new ApplyForMasterLeader(getActivity());
                    applyForMasterLeader.execute();

                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);

                    str_s_studentType = "Master Leader";
                    txt_studType.setText(str_s_studentType);
                    editor_S.putString(PrefID_SStudentType,str_s_studentType);
                    editor_S.commit();

                }

                if(str_s_studentType.equals("Master Leader")){
                    ApplyForAmbassador applyForAmbassador = new ApplyForAmbassador(getActivity());
                    applyForAmbassador.execute();

                    stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);

                    str_s_studentType = "Lead Ambassador";
                    txt_studType.setText(str_s_studentType);
                    editor_S.putString(PrefID_SStudentType,str_s_studentType);
                    editor_S.commit();
                }
            }
        });

        return view;
    }


    public class GetLeaderStatus extends AsyncTask<Void, Void, SoapObject> {

        GetLeaderStatus (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getLeaderStatus();

            //Log.d("Soapresponseissssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if(result!=null) {

                SoapPrimitive S_Status;
                Object O_Status;
                String str_status = null;

                Log.d("Resultssssss", result.toString());

                O_Status = result.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) result.getProperty("Status");
                    Log.d("Status:", S_Status.toString());
                    str_status = S_Status.toString();
                }
                Log.d("str_statusissssss", str_status);

                if (Integer.valueOf(str_status) == 1) {
                    btn_apply.setVisibility(View.GONE);
                }
                if (Integer.valueOf(str_status) == 2) {
                    btn_apply.setText("Applied");
                    btn_apply.setVisibility(View.GONE);
                }

            }

        }
    }

    private SoapObject getLeaderStatus() {
        String METHOD_NAME = "Getleadmemberdetails";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Getleadmemberdetails";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("leadId",str_leadId);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapcompletionxxxx",response.toString());
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


    public class GetMasterStatus extends AsyncTask<Void, Void, SoapObject> {

        GetMasterStatus (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getMasterStatus();

            //Log.d("Soapresponseissssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if(result!=null) {
                SoapPrimitive S_Status;
                Object O_Status;
                String str_status = null;

                Log.d("Resultssssss", result.toString());

                O_Status = result.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) result.getProperty("Status");
                    Log.d("Status:", S_Status.toString());
                    str_status = S_Status.toString();
                }
                Log.d("str_statusissssss", str_status);

                if (Integer.valueOf(str_status) == 1) {
                    btn_apply.setVisibility(View.GONE);
                }
                if (Integer.valueOf(str_status) == 2) {
                    btn_apply.setText("Applied");
                    btn_apply.setVisibility(View.GONE);
                }
            }
        }
    }

    private SoapObject getMasterStatus() {
        String METHOD_NAME = "GetLeadAmbassadordetails";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetLeadAmbassadordetails";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("leadId",str_leadId);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("soapcompletionxxxx",response.toString());
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


























    public class ApplyForMasterLeader extends AsyncTask<Void, Void, SoapPrimitive> {

        private ProgressDialog progressDialog;

        ApplyForMasterLeader (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = applyForMasterLeader();

            //Log.d("Soapresponseissssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Applying");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {

            if(result!=null) {
                SoapPrimitive S_Status;
                Object O_Status;
                String str_status = null;

                Log.d("RsltApplyforMastr", result.toString());
                progressDialog.dismiss();

                if (result.toString().contains("success")) {
                    btn_apply.setText("Applied");
                }

    /*        O_Status = result.getProperty("Status");
            if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                S_Status = (SoapPrimitive) result.getProperty("Status");
                Log.d("Status:", S_Status.toString());
                str_status = S_Status.toString();
            }
            Log.d("str_statusissssss",str_status);

            if(Integer.valueOf(str_status) == 0){
                btn_apply.setVisibility(View.VISIBLE);
            }*/
            }

        }
    }

    private SoapPrimitive applyForMasterLeader() {
        String METHOD_NAME = "Applyformasterleader";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/Applyformasterleader";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("Lead_Id",str_leadId);
            request.addProperty("isApply_MasterLeader",1);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("soapcompletionxxxx",response.toString());
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

    public class ApplyForAmbassador extends AsyncTask<Void, Void, SoapPrimitive> {

        private ProgressDialog progressDialog;

        ApplyForAmbassador (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapPrimitive doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapPrimitive response = applyForAmbassador();

            //Log.d("Soapresponseissssss",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Applying");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapPrimitive result) {
            if(result!=null) {
                SoapPrimitive S_Status;
                Object O_Status;
                String str_status = null;

                Log.d("RsltApplyforMastr", result.toString());
                progressDialog.dismiss();

                if (result.toString().contains("success")) {
                    btn_apply.setText("Applied");
                }
            }
        }
    }

    private SoapPrimitive applyForAmbassador() {
        String METHOD_NAME = "ApplyforLeadAmbassador";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/ApplyforLeadAmbassador";
        String NAMESPACE = "http://mis.leadcampus.org/";

        try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            request.addProperty("Lead_Id",str_leadId);
            request.addProperty("isApply_LeadAmbassador",1);


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);

                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("soapcompletionxxxx",response.toString());
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












    public class GetStudentDetails extends AsyncTask<String, Void, SoapObject> {

        Context context;
        //AlertDialog alertDialog;
        private ProgressDialog progressDialog;

        //private ProgressBar progressBar;

        GetStudentDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(String... params) {
            String str_leadId = (String) params [0];
            //String versionCode = (String) params[2];

            SoapObject response = getStudDetails(str_leadId);

            Log.d("GetStudentDetailssresp",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
         /*   progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if(result!=null) {

                Log.d("GetStudentDetailssresp", result.toString());

                SoapPrimitive S_Status, O_ManagerName, S_StudName, S_StudCollegeName, S_StudType, S_StudLeadId, S_StudImagePath, S_ManagerMailId, S_ManagerMobileNumber, O_ManagerImagePath;
                Object O_Status, O_StudName, O_StudCollegeName, O_StudType, O_StudLeadId, O_StudImagePath;
                String str_Status = null, str_StudName = null, str_StudCollegeName = null, str_StudType = null, str_StudLeadId = null, str_StudImagePath = null;


                O_Status = result.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) result.getProperty("Status");
                    Log.d("Status:", S_Status.toString());
                    str_Status = S_Status.toString();
                }

                if (str_Status.equalsIgnoreCase("Success")) {
                    O_StudName = result.getProperty("StudentName");
                    if (!O_StudName.toString().equals("anyType{}") && !O_StudName.toString().equals(null)) {
                        S_StudName = (SoapPrimitive) result.getProperty("StudentName");
                        Log.d("StudentName", S_StudName.toString());
                        str_StudName = S_StudName.toString();
                    }

                    O_StudCollegeName = result.getProperty("College_Name");
                    if (!O_StudCollegeName.toString().equals("anyType{}") && !O_StudCollegeName.toString().equals(null)) {
                        S_StudCollegeName = (SoapPrimitive) result.getProperty("College_Name");
                        Log.d("S_StudCollegeName", S_StudCollegeName.toString());
                        str_StudCollegeName = S_StudCollegeName.toString();
                    }

                    O_StudType = result.getProperty("Student_Type");
                    if (!O_StudType.toString().equals("anyType{}") && !O_StudType.toString().equals(null)) {
                        S_StudType = (SoapPrimitive) result.getProperty("Student_Type");
                        Log.d("S_StudType", S_StudType.toString());
                        str_StudType = S_StudType.toString();
                    }

                    O_StudLeadId = result.getProperty("Lead_Id");
                    if (!O_StudLeadId.toString().equals("anyType{}") && !O_StudLeadId.toString().equals(null)) {
                        S_StudLeadId = (SoapPrimitive) result.getProperty("Lead_Id");
                        Log.d("S_StudLeadId", S_StudLeadId.toString());
                        str_StudLeadId = S_StudLeadId.toString();
                    }

                    O_StudImagePath = result.getProperty("Student_Image");
                    if (!O_StudImagePath.toString().equals("anyType{}") && !O_StudImagePath.toString().equals(null)) {
                        S_StudImagePath = (SoapPrimitive) result.getProperty("Student_Image");
                        Log.d("S_StudImagePath", S_StudImagePath.toString());
                        str_StudImagePath = S_StudImagePath.toString();
                    }

                    if (str_StudName != null && !str_StudName.isEmpty() && str_StudName != "" && !str_StudName.equals("anyType{}") && str_StudName != "{}") {
                        txt_studName.setText(str_StudName);
                    }

                    if (str_StudCollegeName != null && !str_StudCollegeName.isEmpty() && str_StudCollegeName != "" && !str_StudCollegeName.equals("anyType{}") && str_StudCollegeName != "{}") {
                        txt_studCollegeName.setText(str_StudCollegeName);
                    }

                    if (str_StudLeadId != null && !str_StudLeadId.isEmpty() && str_StudLeadId != "" && !str_StudLeadId.equals("anyType{}") && str_StudLeadId != "{}") {
                        txt_leadId.setText(str_StudLeadId);
                    }

                    if (str_StudType != null && !str_StudType.isEmpty() && str_StudType != "" && !str_StudType.equals("anyType{}") && str_StudType != "{}") {
                        txt_studType.setText(str_StudType);
                    }

                    if (str_StudImagePath != null && !str_StudImagePath.isEmpty() && str_StudImagePath != "" && !str_StudImagePath.equals("anyType{}") && str_StudImagePath != "{}") {
                        Log.d("InsideLoadProfile", "Pick");
                        str_studImgUrl = serverPath + str_StudImagePath.substring(2);

                        Log.d("str_studImgUrlsssssssss", str_studImgUrl);

                        LoadStudProfilePick loadStudProfilePick = new LoadStudProfilePick(getActivity());
                        loadStudProfilePick.execute();

                  /*  Glide.with(getActivity()).load(str_studImgUrl)
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(img_studProfPick);*/

         /*           if (img_studProfPick1.getVisibility() == View.VISIBLE) {
                        //img_studProfPick1.setImageBitmap(result);
                        Glide.with(getActivity()).load(str_studImgUrl)
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(img_studProfPick1);
                    } else {
                        //img_studProfPick.setImageBitmap(result);

                        Glide.with(getActivity()).load(str_studImgUrl)
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(img_studProfPick);
                    }*/


                    }
                }







/*            SoapPrimitive status = (SoapPrimitive) result.getProperty("Status");
            //Log.d("Status is:",status.toString());




            if(status.toString().equals("Success")) {
                SoapPrimitive role = (SoapPrimitive) result.getProperty("Role");
                Log.d("Role is:", role.toString());

                SoapPrimitive isProfileEdited = (SoapPrimitive) result.getProperty("isProfileEdit");
            }*/

                //progressBar.setVisibility(View.GONE);
            }
            progressDialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getStudDetails(String leadid)
    {

        String METHOD_NAME = "GetStudentDetailss";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetStudentDetailss";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);


            /*request.addProperty("Username", mobilenum);
            request.addProperty("Password", leadid);*/

            /*request.addProperty("Username", leadid);
            request.addProperty("Password", mobilenum);*/

/*            String username=edt_leadid.getText().toString();
            String password=edt_mobnumber.getText().toString();*/

            request.addProperty("leadId", leadid);

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
                Log.d("soap responseyyyyyyy",envelope.getResponse().toString());
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.d("soap responseyyyyyyy",response.toString());

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





    private void initializeViews() {
        txt_studName = (TextView) view.findViewById(R.id.txt_studName);
        txt_studCollegeName = (TextView) view.findViewById(R.id.txt_studCollegeName);
        txt_studType = (TextView) view.findViewById(R.id.txt_studType);
        txt_leadId = (TextView) view.findViewById(R.id.txt_leadId);
        img_studProfPick = (ImageView) view.findViewById(R.id.img_studProfPick);

        img_crownMan = (ImageView) view.findViewById(R.id.img_crownMan);
        img_crownWoman = (ImageView) view.findViewById(R.id.img_crownWoman);
        txt_noOfProjects = (TextView) view.findViewById(R.id.txt_noOfProjects);

        img_studProfPick1 = (ImageView) view.findViewById(R.id.img_studProfPick1);

        fram_layout = (FrameLayout) view.findViewById(R.id.fram_layout);

        btn_apply = (Button) view.findViewById(R.id.btn_apply);


        result = new Result();
    }


    public class LoadStudProfilePick extends AsyncTask<Void, Void, Bitmap> {

        private Context context;
        private ProgressDialog progressDialog;

        LoadStudProfilePick (Context context){
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap bitmaplogo=null;

            try {
                //Log.d("Urlssssssssssss",url.toString());
                //bitmaplogo = BitmapFactory.decodeStream(url.openStream());
                URL urlis = new URL(str_studImgUrl);
                InputStream inputStream = urlis.openStream();
                bitmaplogo = BitmapFactory.decodeStream(inputStream);
            }
            catch(FileNotFoundException ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Please update profile picture", Toast.LENGTH_LONG).show();
                    }
                });
            }

            catch (MalformedURLException e) {
                e.printStackTrace();
/*                result.setStudresult(e.getMessage().toString());*/

                final String exceptnMsg = e.getMessage().toString();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),exceptnMsg, Toast.LENGTH_LONG).show();
                    }
                });

                //return result;
            } catch (IOException e) {
                e.printStackTrace();
   /*             result.setStudresult(e.getMessage().toString());*/

                final String exceptnMsg = e.getMessage().toString();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),exceptnMsg, Toast.LENGTH_LONG).show();
                    }
                });
                //return result;
            }catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }




            //result.setStudbitmap(bitmaplogo);
            return bitmaplogo;
            //return result;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
         /*   bitmapList.add(bitmap);

            Log.d("count1iss", String.valueOf(count1));
            Log.d("count2iss", String.valueOf(count2));
            Log.d("Bitmapsizess", String.valueOf(bitmapList.size()));
            if(bitmapList.size()==count2){
                img_mainGallery.setImageBitmap(bitmapList.get(0));
                txt_numOfImages.setText("Images: "+bitmapList.size());
            }*/


            //}
            if(result!=null) {
                try {
                    if (img_studProfPick1.getVisibility() == View.VISIBLE) {
                        img_studProfPick1.setImageBitmap(result);
                    } else {
                        img_studProfPick.setImageBitmap(result);
                    }
                } catch (OutOfMemoryError ex) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(context, "Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }


        /*    if(result.getStudresult()!=null) {

                //Log.d("Inside", bitmap.toString());

                img_studProfPick.setImageBitmap(result.getStudbitmap());
            }
            else{
                Toast.makeText(getActivity(),"Exception occured student:"+result.getStudresult(),Toast.LENGTH_LONG).show();
            }*/

            //progressBar.setVisibility(GONE);

            progressDialog.dismiss();
        }
    }



/*    private void setStateSpinner(View view) {


        final ArrayList liststate = new ArrayList();
        liststate.add("Karnataka");
        liststate.add("Maharashtra");
        liststate.add("AndhraPradesh");

        ArrayAdapter dataAdapterState = new ArrayAdapter(context, R.layout.simple_spinner_items, liststate);

        // Drop down layout style - list view with radio button
        dataAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_state.setAdapter(dataAdapterState);
        spin_state.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
    }

    private void setCitySpinner(View view) {


        final ArrayList listcity = new ArrayList();
        listcity.add("Hubballi");
        listcity.add("Dharwad");
        listcity.add("Belgaum");

        ArrayAdapter dataAdapterCity = new ArrayAdapter(context, R.layout.simple_spinner_items, listcity);

        // Drop down layout style - list view with radio button
        dataAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_city.setAdapter(dataAdapterCity);
        spin_city.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
    }

    private void setInstitutionSpinner(View view) {


        final ArrayList listinstitution = new ArrayList();
        listinstitution.add("JG College of commerce");
        listinstitution.add("B.V.B College of Engineering");

        ArrayAdapter dataAdapterInstitution = new ArrayAdapter(context, R.layout.simple_spinner_items, listinstitution);

        // Drop down layout style - list view with radio button
        dataAdapterInstitution.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_institution.setAdapter(dataAdapterInstitution);
        spin_institution.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
    }

    private void setSemesterSpinner(View view) {


        final ArrayList listsemester = new ArrayList();
        listsemester.add("1");
        listsemester.add("2");
        listsemester.add("3");

        //ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_semester, listsemester);
        ArrayAdapter dataAdapterSem = new ArrayAdapter(context, R.layout.simple_spinner_items, listsemester);

        // Drop down layout style - list view with radio button
        dataAdapterSem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin_semester.setAdapter(dataAdapterSem);
        spin_semester.setSupportBackgroundTintList(ContextCompat.getColorStateList(context, R.color.colorWhite));
    }

    private void setDateOfBirth(View view) {

        final Calendar c = Calendar.getInstance();

        edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                cDay = dayOfMonth;
                                cMonth = monthOfYear;
                                cYear = year;

                                //TextView txtExactDate = (TextView) findViewById(R.id.txt_exactDate);
                                edt_dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                //txtDate.edita
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });


    }*/


}
