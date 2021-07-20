package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.codesgood.views.JustifiedTextView;
import com.facebook.shimmer.ShimmerFrameLayout;
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

import pl.bclogic.pulsator4droid.library.PulsatorLayout;


public class MyProfileFragment extends Fragment {

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

   // private String[] descriptionData = {"Student", "Leader", "Master", "Ambassador"};
   private String[] descriptionData = {"Student", "Initiator","Change Maker","LEADer", "Master Leader"};

    private int mYear, mMonth, mDay;
    private int cYear, cMonth, cDay;

    public static final String  PREFBook_Stud= "prefbook_stud";  //sharedpreference Book
    public static final String PrefID_RegID = "prefid_regid"; //
    public static final String PrefID_SName = "prefid_sname"; //
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_PM_S_MID = "prefid_pm_s_mid"; //
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
    public static final String PrefID_SSisStudentLEADer ="prefid_ssisStudentLEADer";

    public static final String PrefId_S_TshirtStatus = "prefid_s_tshirtstatus";

    public static final String PrefID_SLLP = "prefID_SLLP";
    public static final String PrefID_SPrayana = "prefID_SPrayana";
    public static final String PrefID_SYuva = "prefID_SYuva";
    public static final String PrefID_SValedicotry = "prefID_SValedicotry";

    SharedPreferences shardpref_S_obj;

    private TextView txt_studName;
    private TextView txt_studCollegeName;
    private TextView txt_studType;
    private TextView txt_leadId;
    private ImageView img_studProfPick;
    private ImageView img_crownMan,img_crownWoman,img_studProfPick1,star_count,img_studProfPick2;
    private TextView txt_noOfProjects;
    private View view;
    private String str_leadId,str_studName,str_role,str_collegeName,str_studImgUrl,str_username,str_password,str_s_studentType,str_s_isStudentLEADer;

    private String str_regId,str_s_gender,str_s_starCount,str_tshirt_status,str_managerId;
    private String str_LLP,str_Prayana,str_Yuva,str_Valedicotry;
    private Result result;
    private String getResult;

    SharedPreferences.Editor editor_S;
    public static final String PrefId_S_Password = "prefid_spassword";
    public static final String PrefId_S_Username = "prefid_susername";
    private String serverPath = Class_URL.ServerPath.toString().trim();

    private FrameLayout fram_layout;
    private LinearLayout bages;
    ImageView img_badge1,img_badge2,img_badge3,img_badge4;
    ShimmerFrameLayout container1,container2,container3,container4;
    TextView badge1text,badge2text,badge3text,badge4text;
    private int starCount;
   // private Button btn_apply;
    private ImageView btn_TshirtApply;

    private StateProgressBar stateProgressBar;
    public static final String PrefId_S_IsFeesPaid = "prefid_sfeesPaid";
    private String str_isFeesPaid,str_gettshirtlistresponse,str_getTshirtStatus;



    Class_InternetDectector internetDectector;
    Boolean isInternetPresent = false;

    String Str_tshirterror1,Str_tshirterror2,str_tshirtresponse,str_tshirtsize;
    RadioGroup rg_tshirtradiogroup;
    private RadioButton rb_tshirtradiobutton;
    ImageView h_student,h_initiator,h_cm,h_leader,h_ml,h_amb;
    private PulsatorLayout mPulsator1,mPulsator2,mPulsator3,mPulsator4,mPulsator5,mPulsator6;



    View stud_right,initiater_left,initiater_right,cm_left,cm_right,leader_left,leader_right,ml_left,ml_right,amb_left;
    TextView stud_tv,initiater_tv,cm_tv,leader_tv,ml_tv,amb_tv;
    Dialog myDialog;
    ImageView ib_close;
    JustifiedTextView tv_popupinfo;
    TextView popupHeader;

  //  LinearLayout popup;


    String  str_getTshirt_requestedID,str_getTshirt_tshirtSize,str_Tshirt_applySecondTime,str_reasontochange;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.myprofile_fragment_new, container, false);
        context = getActivity().getBaseContext();


        Str_tshirterror1=Str_tshirterror2=str_getTshirt_requestedID=str_Tshirt_applySecondTime="no";

        initializeViews();




        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);

        if(getActivity().getIntent().hasExtra("fromeditProfile"))
        {
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

            str_regId = shardpref_S_obj.getString(PrefID_RegID, "").trim();
            str_managerId = shardpref_S_obj.getString(PrefID_PM_S_MID, "").trim();



            shardpref_S_obj.getString(PrefId_S_Gender,"").trim();
            str_s_gender = shardpref_S_obj.getString(PrefId_S_Gender, "").trim();

            shardpref_S_obj.getString(PrefId_S_StarCount,"").trim();
            str_s_starCount = shardpref_S_obj.getString(PrefId_S_StarCount, "").trim();
            starCount = Integer.parseInt(str_s_starCount);


            str_tshirt_status = shardpref_S_obj.getString(PrefId_S_TshirtStatus, "").trim();

            str_LLP = shardpref_S_obj.getString(PrefID_SLLP, "").trim();
            str_Prayana = shardpref_S_obj.getString(PrefID_SPrayana, "").trim();
            str_Yuva = shardpref_S_obj.getString(PrefID_SYuva, "").trim();
            str_Valedicotry = shardpref_S_obj.getString(PrefID_SValedicotry, "").trim();


            if(starCount>0){
                img_studProfPick1.setVisibility(View.VISIBLE);
                fram_layout.setVisibility(View.GONE);
               // bages.setVisibility(View.VISIBLE);
                if(str_s_gender.equals("M")){

                  //  img_crownWoman.setVisibility(View.GONE);
                 //   img_crownMan.setVisibility(View.VISIBLE);
                    star_count.setVisibility(View.VISIBLE);
                    txt_noOfProjects.setText(str_s_starCount);
                    txt_noOfProjects.setVisibility(View.VISIBLE);
                }else{

               //     img_crownWoman.setVisibility(View.VISIBLE);
                //    img_crownMan.setVisibility(View.GONE);
                    star_count.setVisibility(View.VISIBLE);
                    txt_noOfProjects.setText(str_s_starCount);
                    txt_noOfProjects.setVisibility(View.VISIBLE);
                }
            }


            if(!str_Prayana.equals("0")){
                bages.setVisibility(View.VISIBLE);
                img_badge1.setVisibility(View.VISIBLE);
                badge1text.setVisibility(View.VISIBLE);
                badge1text.setText(str_Prayana);
                container1.startShimmer();
                result = new Result();
            }else{
              //  bages.setVisibility(View.GONE);
                img_badge1.setVisibility(View.GONE);
                badge1text.setVisibility(View.GONE);
                container1.stopShimmer();
                result = new Result();
            }
            if(!str_Yuva.equals("0")){
                bages.setVisibility(View.VISIBLE);
                img_badge2.setVisibility(View.VISIBLE);
                badge2text.setVisibility(View.VISIBLE);
                badge2text.setText(str_Yuva);
                container2.startShimmer();
                result = new Result();
            }else{
               // bages.setVisibility(View.GONE);
                img_badge2.setVisibility(View.GONE);
                badge2text.setVisibility(View.GONE);
                container2.stopShimmer();
                result = new Result();
            }
            if(!str_Valedicotry.equals("0")){
                bages.setVisibility(View.VISIBLE);
                img_badge3.setVisibility(View.VISIBLE);
                badge3text.setVisibility(View.VISIBLE);
                badge3text.setText(str_Valedicotry);
                container3.startShimmer();
                result = new Result();
            }else{
              //  bages.setVisibility(View.GONE);
                badge3text.setVisibility(View.GONE);
                img_badge3.setVisibility(View.GONE);
                container3.stopShimmer();
                result = new Result();
            }
            if(!str_LLP.equals("0")){
                bages.setVisibility(View.VISIBLE);
                img_badge4.setVisibility(View.VISIBLE);
                badge4text.setVisibility(View.VISIBLE);
                badge4text.setText(str_LLP);
                container4.startShimmer();
                result = new Result();
            }else{
             //   bages.setVisibility(View.GONE);
                img_badge4.setVisibility(View.GONE);
                badge4text.setVisibility(View.GONE);
                container4.stopShimmer();
                result = new Result();
            }

            GetStudentDetails getStudentDetails = new GetStudentDetails(getActivity());
            getStudentDetails.execute(str_leadId);




        }

        //getStudentDetails();
        else {

            shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
            str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();

            shardpref_S_obj.getString(PrefID_RegID, "").trim();
            str_regId = shardpref_S_obj.getString(PrefID_RegID, "").trim();

            str_managerId = shardpref_S_obj.getString(PrefID_PM_S_MID, "").trim();

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

            shardpref_S_obj.getString(PrefID_SSisStudentLEADer, "").trim();
            str_s_isStudentLEADer = shardpref_S_obj.getString(PrefID_SSisStudentLEADer, "").trim();

            shardpref_S_obj.getString(PrefId_S_Gender,"").trim();
            str_s_gender = shardpref_S_obj.getString(PrefId_S_Gender, "").trim();

            shardpref_S_obj.getString(PrefId_S_StarCount,"").trim();
            str_s_starCount = shardpref_S_obj.getString(PrefId_S_StarCount, "").trim();
            starCount = Integer.parseInt(str_s_starCount);



            str_tshirt_status = shardpref_S_obj.getString(PrefId_S_TshirtStatus, "").trim();


            str_LLP = shardpref_S_obj.getString(PrefID_SLLP, "").trim();
            str_Prayana = shardpref_S_obj.getString(PrefID_SPrayana, "").trim();
            str_Yuva = shardpref_S_obj.getString(PrefID_SYuva, "").trim();
            str_Valedicotry = shardpref_S_obj.getString(PrefID_SValedicotry, "").trim();


           /* str_Prayana="1";
            str_LLP="0";
            str_Yuva="1";*/

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
                img_studProfPick1.setVisibility(View.VISIBLE);
                fram_layout.setVisibility(View.GONE);
               // bages.setVisibility(View.VISIBLE);

                if(str_s_gender.equals("M")){



                 //   img_crownWoman.setVisibility(View.GONE);
               //     img_crownMan.setVisibility(View.VISIBLE);

      /*              RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(-40,0,0,0);
                    txt_noOfProjects.setLayoutParams(params);*/
                    star_count.setVisibility(View.VISIBLE);
                    txt_noOfProjects.setText(str_s_starCount);
                    txt_noOfProjects.setVisibility(View.VISIBLE);


                }else{

            /*        img_studProfPick1.setVisibility(View.VISIBLE);
                    fram_layout.setVisibility(View.GONE);*/

  //                  img_crownWoman.setVisibility(View.VISIBLE);
//                    img_crownMan.setVisibility(View.GONE);

                    star_count.setVisibility(View.VISIBLE);




                /*    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(-20,0,0,0);
                    txt_noOfProjects.setLayoutParams(params);*/
                    txt_noOfProjects.setText(str_s_starCount);
                    txt_noOfProjects.setVisibility(View.VISIBLE);
                }
            }


            if(!str_Prayana.equals("0")){
                bages.setVisibility(View.VISIBLE);
                img_badge1.setVisibility(View.VISIBLE);
                badge1text.setVisibility(View.VISIBLE);
                badge1text.setText(str_Prayana);
                container1.startShimmer();
                result = new Result();
            }else{
                //  bages.setVisibility(View.GONE);
                img_badge1.setVisibility(View.GONE);
                badge1text.setVisibility(View.GONE);
                container1.stopShimmer();
                result = new Result();
            }
            if(!str_Yuva.equals("0")){
                bages.setVisibility(View.VISIBLE);
                img_badge2.setVisibility(View.VISIBLE);
                badge2text.setVisibility(View.VISIBLE);
                badge2text.setText(str_Yuva);
                container2.startShimmer();
                result = new Result();
            }else{
                // bages.setVisibility(View.GONE);
                img_badge2.setVisibility(View.GONE);
                badge2text.setVisibility(View.GONE);
                container2.stopShimmer();
                result = new Result();
            }
            if(!str_Valedicotry.equals("0")){
                bages.setVisibility(View.VISIBLE);
                img_badge3.setVisibility(View.VISIBLE);
                badge3text.setVisibility(View.VISIBLE);
                badge3text.setText(str_Valedicotry);
                container3.startShimmer();
                result = new Result();
            }else{
                //  bages.setVisibility(View.GONE);
                badge3text.setVisibility(View.GONE);
                img_badge3.setVisibility(View.GONE);
                container3.stopShimmer();
                result = new Result();
            }
            if(!str_LLP.equals("0")){
                bages.setVisibility(View.VISIBLE);
                img_badge4.setVisibility(View.VISIBLE);
                badge4text.setVisibility(View.VISIBLE);
                badge4text.setText(str_LLP);
                container4.startShimmer();
                result = new Result();
            }else{
                //   bages.setVisibility(View.GONE);
                img_badge4.setVisibility(View.GONE);
                badge4text.setVisibility(View.GONE);
                container4.stopShimmer();
                result = new Result();
            }

            //Toast.makeText(getActivity(),"Inside My Profile",Toast.LENGTH_SHORT).show();


        }


        shardpref_S_obj.getString(PrefID_SStudentType, "").trim();
        str_s_studentType = shardpref_S_obj.getString(PrefID_SStudentType, "").trim();

        shardpref_S_obj.getString(PrefID_SSisStudentLEADer, "").trim();
        str_s_isStudentLEADer = shardpref_S_obj.getString(PrefID_SSisStudentLEADer, "").trim();

        stateProgressBar = (StateProgressBar) view.findViewById(R.id.usage_stateprogressbar);
        stateProgressBar.setStateDescriptionData(descriptionData);

        if (str_s_studentType.equals("Student")) {
            //stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
            h_student.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorWhite)));
            mPulsator1.start();
        }

        if (str_s_studentType.equals("Initiator")) {
           // stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
            h_student.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
            h_initiator.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorWhite)));
            mPulsator2.start();
         //   GetLeaderStatus getLeaderStatus = new GetLeaderStatus(getActivity());
           // getLeaderStatus.execute();
        }
        if (str_s_studentType.equals("Change Maker")) {
            //stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
            h_student.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
            h_initiator.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
            h_cm.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorWhite)));

            stud_right.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            initiater_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            initiater_right.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            cm_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            stud_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            initiater_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            cm_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));


            mPulsator3.start();
        }
        if (str_s_studentType.equals("LEADer")) {
            //stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
            h_student.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
            h_initiator.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
            h_cm.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
            h_leader.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorWhite)));



            stud_right.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            initiater_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            initiater_right.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            cm_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            cm_right.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));

            leader_left.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            stud_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            initiater_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            cm_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            leader_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));


            mPulsator4.start();
            GetLeaderStatus getLeaderStatus = new GetLeaderStatus(getActivity());
             getLeaderStatus.execute();
        }
        if (str_s_studentType.equals("Master Leader")) {
            //stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
            if(Integer.valueOf(str_s_isStudentLEADer)<1){
                h_leader.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.light_gray)));
                leader_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.light_gray));
            }else
            {
                h_leader.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
                leader_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            }
            h_student.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
            h_initiator.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
            h_cm.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));

            h_ml.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorWhite)));


            stud_right.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            initiater_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            initiater_right.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            cm_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            cm_right.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            leader_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
            leader_right.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            ml_left.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));

            stud_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            initiater_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            cm_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
            ml_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));



            mPulsator5.start();
            GetMasterStatus getMasterStatus = new GetMasterStatus(getActivity());
            getMasterStatus.execute();

        }

       if (str_s_studentType.equals("Lead Ambassador")) {
           // stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
           h_student.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
           h_initiator.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
           h_cm.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
           //h_leader.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));

           if(Integer.valueOf(str_s_isStudentLEADer)<1){
               h_leader.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.light_gray)));
               leader_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.light_gray));
           }else
           {
               h_leader.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));
               leader_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
           }




           h_ml.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPerpule)));


           stud_right.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
           initiater_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
           initiater_right.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
           cm_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
           cm_right.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
           leader_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
           leader_right.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
           ml_left.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPerpule));
           ml_right.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
           amb_left.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));

           stud_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
           initiater_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
           cm_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
           ml_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));
           amb_tv.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPerpule));



           h_amb.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorWhite)));
           mPulsator6.start();
        }

       /* h_initiator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.setVisibility(View.VISIBLE);
            }
        });*/

        h_initiator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                popupHeader.setText("Initiator");
                tv_popupinfo.setText("Any one fix the issue/any initiative by spending less than 8 hours of time.");
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        h_cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                popupHeader.setText("Change Maker");
                tv_popupinfo.setText("The one who engaged for 8 hours of minimum time and makes a feasible/visible/countable change in the community/people.");
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        h_leader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                popupHeader.setText("Leader");
                tv_popupinfo.setText("The one who works in a team, lead a minimum of three members make a change in the community/people, works minimum for a month with the same or changed team on same or new initiatives. Contributing a minimum of 30 hours.");
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        h_ml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                popupHeader.setText("Master Leader");
                tv_popupinfo.setText("The one who completes the change maker or leader phase\n" +
                        "\n" +
                        "• Minimum 3 projects or long term involvement in one the project which has a bigger impact\n" +
                        "\n" +
                        "• Minimum 6 months engagement with LEAD Program");
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
        h_amb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ib_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                popupHeader.setText("LEAD Ambassador");
                tv_popupinfo.setText("•Master LEADer who is active for three months is only eligible.\n" +
                        "\n" +
                        "• Qualifying personal interview.\n" +
                        "\n" +
                        "Should have a specific plan of action at least for the next 6 months. (Impact or entrepreneurial project or support team to LEAD)\n" +
                        "\n" +
                        "• Completed 1 academic year in the LEAD Program.");
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });



        shardpref_S_obj.getString(PrefId_S_IsFeesPaid, "").trim();
        str_isFeesPaid = shardpref_S_obj.getString(PrefId_S_IsFeesPaid, "").trim();
        Log.d("FeesPaidisssssss",str_isFeesPaid);


        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        GridAdapter gridAdapter = new GridAdapter(getActivity());//GridAdapter.java
        gridAdapter.setStr_isFeesPaid(str_isFeesPaid);
        gridView.setAdapter(gridAdapter);

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
        /*btn_apply.setOnClickListener(new View.OnClickListener()
        {
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
        });*/



// for T-shirt request
        //1: eligible for T-shirt ,2-requested but not approved by PM
        //3- already given Tshirt
        if(str_tshirt_status.equals("1")||str_tshirt_status.equals("2"))
        {
            bages.setVisibility(View.VISIBLE);
            btn_TshirtApply.setVisibility(View.VISIBLE);
            Animation startAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.blinking_animation);
            btn_TshirtApply.startAnimation(startAnimation);

            GetTshirtlist_AsyncCallWS task = new GetTshirtlist_AsyncCallWS(getActivity());
            task.execute();




            btn_TshirtApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    final Dialog myDialog = new Dialog(getActivity());
                    myDialog.setContentView(R.layout.tshirt_customdialog);
                    myDialog.setCancelable(false);

                     rg_tshirtradiogroup =(RadioGroup)myDialog.findViewById(R.id. tshirtradiogroup_RG);
                    Button dialog_bt_tshirtapply= (Button) myDialog.findViewById(R.id.tshirtapply_BT);
                    Button dialog_bt_tshirtcancel= (Button) myDialog.findViewById(R.id.tshirtcancel_BT);
                    TextView previous_request_tv =(TextView)myDialog.findViewById(R.id.previous_request_tv);

                    if(str_gettshirtlistresponse.equals("Success"))
                    {
                        previous_request_tv.setVisibility(View.VISIBLE);
                        previous_request_tv.setText("Previous request was:"+str_getTshirt_tshirtSize.toString());
                    }else{previous_request_tv.setVisibility(View.GONE);}


                    dialog_bt_tshirtapply.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            int selectedId = rg_tshirtradiogroup.getCheckedRadioButtonId();

                            rb_tshirtradiobutton = (RadioButton) myDialog.findViewById(selectedId);

                            String string=  rb_tshirtradiobutton.getText().toString().trim();
                            String[] vals = string.split(" ");
                            if (vals.length > 1)
                            { str_tshirtsize = vals[0]; }

                                internetDectector = new Class_InternetDectector(getActivity());
                                isInternetPresent = internetDectector.isConnectingToInternet();

                           /* Toast.makeText(getActivity(),"RegID: "+str_regId.toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"LeadID: "+str_leadId.toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"ManagerID: "+str_managerId.toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"StudentName: "+str_studName.toString(),Toast.LENGTH_LONG).show();
                            Toast.makeText(getActivity(),"T-shirtSize: "+str_tshirtsize.toString(),Toast.LENGTH_LONG).show();
*/
                            //str_studName

                            //Toast.makeText(getActivity(),"ManagerID"+str_leadId.toString(),Toast.LENGTH_LONG).show();

                                if (isInternetPresent)
                                {
                                    TshirtRequest_AsyncCallWS task = new TshirtRequest_AsyncCallWS(getActivity());
                                    task.execute();
                                    myDialog.cancel();
                                }
                                else
                                {
                                    Toast.makeText(getActivity(),"No Internet",Toast.LENGTH_LONG).show();
                                }

                        }
                    });



                    dialog_bt_tshirtcancel.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {

                            myDialog.cancel();
                        }
                    });

                    myDialog.show();
                }
            });

        }else if(str_tshirt_status.equals("3"))
        { btn_TshirtApply.setVisibility(View.GONE); }
        else{ btn_TshirtApply.setVisibility(View.GONE);

        }






        return view;

    }// end of onCreateView


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

             /*   if (Integer.valueOf(str_status) == 1) {
                    btn_apply.setVisibility(View.GONE);
                }
                if (Integer.valueOf(str_status) == 2) {
                    btn_apply.setText("Applied");
                    btn_apply.setVisibility(View.GONE);
                }*/
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
                {
                    str_status="0";
                }
                Log.d("str_statusissssss", str_status);

              /*  if (Integer.valueOf(str_status) == 1) {
                    btn_apply.setVisibility(View.GONE);
                }
                if (Integer.valueOf(str_status) == 2) {
                    btn_apply.setText("Applied");
                    btn_apply.setVisibility(View.GONE);
                }*/
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

               /* if (result.toString().contains("success")) {
                    btn_apply.setText("Applied");
                }*/

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

              /*  if (result.toString().contains("success")) {
                    btn_apply.setText("Applied");
                }*/
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
            }
            //progressBar.setVisibility(View.GONE);
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
        star_count = (ImageView) view.findViewById(R.id.star_count);
        bages=(LinearLayout) view.findViewById(R.id.bages);
       // bages.setVisibility(View.GONE);
      //  btn_apply = (Button) view.findViewById(R.id.btn_apply);

        img_badge1 =(ImageView) view.findViewById(R.id.badge1);
        img_badge2 =(ImageView) view.findViewById(R.id.badge2);
        img_badge3 =(ImageView) view.findViewById(R.id.badge3);
        img_badge4 =(ImageView) view.findViewById(R.id.badge4);

        badge1text =(TextView) view.findViewById(R.id.badge1text);
        badge2text =(TextView) view.findViewById(R.id.badge2text);
        badge3text =(TextView) view.findViewById(R.id.badge3text);
        badge4text =(TextView) view.findViewById(R.id.badge4text);


        container1 =
                (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view1);
         container2 =
                (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view2);
         container3 =
                (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view3);
         container4 =
                (ShimmerFrameLayout) view.findViewById(R.id.shimmer_view4);
       /* ShimmerLayout shimmerLayout = view.findViewById(R.id.shimmer_text);
        shimmerLayout.setShimmerColor(R.drawable.smaple_effect);
        shimmerLayout.startShimmerAnimation();*/

        btn_TshirtApply =(ImageView)view.findViewById(R.id.btn_tshirt_apply);

        h_student= (ImageView) view.findViewById(R.id.student);
        h_initiator= (ImageView) view.findViewById(R.id.initiater);
        h_cm= (ImageView) view.findViewById(R.id.changemaker);
        h_leader= (ImageView) view.findViewById(R.id.leader);
        h_ml= (ImageView) view.findViewById(R.id.master);
        h_amb= (ImageView) view.findViewById(R.id.ambasiter);
        mPulsator1 = (PulsatorLayout) view.findViewById(R.id.pulsator1);
        mPulsator2 = (PulsatorLayout) view.findViewById(R.id.pulsator2);
        mPulsator3 = (PulsatorLayout) view.findViewById(R.id.pulsator3);
        mPulsator4 = (PulsatorLayout) view.findViewById(R.id.pulsator4);
        mPulsator5 = (PulsatorLayout) view.findViewById(R.id.pulsator5);
        mPulsator6 = (PulsatorLayout) view.findViewById(R.id.pulsator6);
        stud_right = (View) view.findViewById(R.id.stud_right);
        initiater_left = (View) view.findViewById(R.id.initiater_left);
        initiater_right = (View) view.findViewById(R.id.initiater_right);
        cm_left = (View) view.findViewById(R.id.cm_left);
        cm_right = (View) view.findViewById(R.id.cm_right);
        leader_left = (View) view.findViewById(R.id.leader_left);
        leader_right = (View) view.findViewById(R.id.leader_right);
        ml_left = (View) view.findViewById(R.id.ml_left);
        ml_right = (View) view.findViewById(R.id.ml_right);
        amb_left = (View) view.findViewById(R.id.amb_left);
        stud_tv = (TextView) view.findViewById(R.id.stud_tv);
        initiater_tv = (TextView) view.findViewById(R.id.initiater_tv);
        cm_tv = (TextView) view.findViewById(R.id.cm_tv);
        leader_tv = (TextView) view.findViewById(R.id.leader_tv);
        ml_tv = (TextView) view.findViewById(R.id.ml_tv);
        amb_tv = (TextView) view.findViewById(R.id.amb_tv);
        myDialog = new Dialog(getContext());
        myDialog.setContentView(R.layout.stud_progression_popup);
        ib_close =(ImageView) myDialog.findViewById(R.id.ib_close);
        tv_popupinfo =(JustifiedTextView) myDialog.findViewById(R.id.tv_popupinfo);
        popupHeader = (TextView) myDialog.findViewById(R.id.popupHeader);
       // popup=(LinearLayout) view.findViewById(R.id.popup);
      //  btn_TshirtApply.setVisibility(View.INVISIBLE);


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
                     //   Toast.makeText(getActivity(),exceptnMsg, Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),"No Internet",Toast.LENGTH_LONG).show();
                    }
                });

                //return result;
            } catch (IOException e) {
                e.printStackTrace();
   /*             result.setStudresult(e.getMessage().toString());*/

                final String exceptnMsg = e.getMessage().toString();

                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                     //   Toast.makeText(getActivity(),exceptnMsg, Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),"No Internet",Toast.LENGTH_LONG).show();
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



    private class TshirtRequest_AsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("LeadMIS", "doInBackground");
            //  GetAllEvents();

            Tshirt_request();  //apply T-shirt request


            return null;
        }

        public TshirtRequest_AsyncCallWS(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();


            if(Str_tshirterror1.equals("no")||Str_tshirterror2.equals("no"))
            {
                if(str_tshirtresponse.equals("Success")||str_tshirtresponse.equals("success"))
                {

                    /*editor_S = shardpref_S_obj.edit();
                    editor_S.putString(PrefId_S_TshirtStatus,"1");
                    editor_S.commit();*/
                    Toast.makeText(getActivity(), "T-Shirt request sent Successfully", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getActivity(), HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getActivity(), "Error: WS", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getActivity(), "Error: WS", Toast.LENGTH_SHORT).show();
            }

        }//end of OnPostExecute

    }// end Async task




    public void Tshirt_request()
    {

        //URL
        String URL = Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "ApplyTshirtRequested";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/ApplyTshirtRequested";
        //URL


        if(str_Tshirt_applySecondTime.equals("no"))
        {
            str_getTshirt_requestedID="";
            str_reasontochange="";
        }

            Integer int_registrationId= Integer.parseInt(str_regId.toString().trim());
            Integer int_managerid =Integer.parseInt(str_managerId.toString().trim());


        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            //for re-request of t-shirt
            request.addProperty("RequestedId", str_getTshirt_requestedID);//<RequestedId>string</RequestedId>
            request.addProperty("TshirtModSize",str_tshirtsize.trim());//<TshirtModSize>string</TshirtModSize>

            //for re-request of t-shirt

            request.addProperty("RegistrationId", int_registrationId);//<RegistrationId>int</RegistrationId>
            request.addProperty("Lead_Id", str_leadId.toString().trim());//<Lead_Id>string</Lead_Id>
            request.addProperty("ManagerId", int_managerid);//<ManagerId>int</ManagerId>
            request.addProperty("MemberName", str_studName);//<MemberName>string</MemberName>
            request.addProperty("TshirtSize", str_tshirtsize.trim());//<TshirtSize>string</TshirtSize>
            request.addProperty("RequestedCount", 1);//<RequestedCount>int</RequestedCount>
            request.addProperty("ReapplyReson",str_reasontochange);//<ReapplyReson>string</ReapplyReson>


/*<RegistrationId>int</RegistrationId>
      <Lead_Id>string</Lead_Id>
      <ManagerId>int</ManagerId>
      <MemberName>string</MemberName>
      <TshirtSize>string</TshirtSize>
      <RequestedCount>int</RequestedCount>*/
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAPACTION, envelope);

                SoapPrimitive tshirt_response = (SoapPrimitive) envelope.getResponse();

            //   SoapObject tshirt_response = (SoapObject) envelope.getResponse();

                Log.e("tshirt response", tshirt_response.toString());

                str_tshirtresponse=tshirt_response.toString().trim();

            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Str_tshirterror1 ="yes";
                Log.e("request fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Str_tshirterror2="yes";
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }


    }//end of TshirtApply







    private class GetTshirtlist_AsyncCallWS extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");

            /*dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();*/

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("LeadMIS", "doInBackground");
            //  GetAllEvents();

            GetTshirtlist_request();  // call of GetTshirtlist


            return null;
        }

        public GetTshirtlist_AsyncCallWS(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result)
        {
           // dialog.dismiss();

            if(str_getTshirtStatus.equals("Success"))
            {
                str_Tshirt_applySecondTime="yes";
            }



        }//end of OnPostExecute

    }// end Async task


    public void GetTshirtlist_request()
    {

        //URL
        String URL = Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "GetTshirtlist";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/GetTshirtlist";
        //URL

        /*<RequestedId>16</RequestedId>
        <TshirtSize>S</TshirtSize>
        <RequestedDate>10/13/2018 1:41:44 PM</RequestedDate>
        <SanctionDate>10/13/2018 1:43:46 PM</SanctionDate>
        <TshirtStatus>Exchange</TshirtStatus>
        <Status>Success</Status>*/

        /*<vmtshirtlist>
        <RequestedId>0</RequestedId>
        <Status>There is no Tshirt request here</Status>
        </vmtshirtlist>*/

        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);


            request.addProperty("Lead_id", str_leadId.toString().trim());//<Lead_Id>string</Lead_Id>


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAPACTION, envelope);
                SoapObject gettshirtlist_response = (SoapObject) envelope.getResponse();

                Log.e("gettshirt list", gettshirtlist_response.toString());

                gettshirtlist_response= (SoapObject) gettshirtlist_response.getProperty(0);

                str_gettshirtlistresponse=gettshirtlist_response.getProperty("Status").toString();

                str_getTshirtStatus = gettshirtlist_response.getProperty("Status").toString();

                if(str_getTshirtStatus.equals("Success"))
                {

                    str_getTshirt_requestedID=(String)gettshirtlist_response.getProperty("RequestedId").toString();//<RequestedId>16</RequestedId>
                    str_getTshirt_tshirtSize=(String)gettshirtlist_response.getProperty("TshirtSize").toString();//<TshirtSize>S</TshirtSize>


                }

            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Str_tshirterror1 ="yes";
                Log.e("request fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Str_tshirterror2="yes";
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }


    }//end of TshirtApply














}
