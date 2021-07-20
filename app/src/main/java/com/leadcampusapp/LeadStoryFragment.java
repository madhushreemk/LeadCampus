package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.leadcampusapp.module.LeadStoryList;
import com.leadcampusapp.module.LeadStoryListModule;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class LeadStoryFragment extends Fragment {

    AlertDialog alertDialog;
  //  static LeadStoryFragment mcontext=this;
    private ProgressBar progressBar;
    LeadStoryListModule leadStoryListObj;
    ArrayList<LeadStoryListModule> leadStoryListModules;
    LeadStoryListModule[] leadInfosarr;
    int responseCount=0;
    int i=0;
    public static FragmentActivity mcontext;

    Context context;
    static VerticalViewPager verticalViewPager;
    String strtext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view =inflater.inflate(R.layout.leadstory_fragment, container, false);
         strtext = getArguments().getString("story_possion").trim();
       // strtext = strtext.substring(0, strtext.length() - 1).trim();
         Log.e("tag","strtext="+strtext);
        verticalViewPager = (VerticalViewPager) view.findViewById(R.id.vPager);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mcontext=getActivity();


    //    Button btn_top= (Button) view.findViewById(R.id.btn_top);
        ImageView iv_top=(ImageView) view.findViewById(R.id.iv_top);

        GetLeadStoryDetails viewProject = new GetLeadStoryDetails(getActivity());
        viewProject.execute();


        Log.i("tag","LeadStoryList.listview_arr=="+LeadStoryList.listview_arr.size());
     //   verticalViewPager.setAdapter(new VerticlePagerAdapter(getContext(), LeadStoryList.listview_arr));


        iv_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity(), LeadStoryList.listview_arr));

                //verticalViewPager.setPageTransformer(true, new VerticalPageTransformerAnimate());
                verticalViewPager.setCurrentItem(4);
            }
        });
        return view;
    //    initSwipePager();
    }

    public static void verticalFunction(){

        verticalViewPager.setAdapter(new VerticlePagerAdapter(mcontext,LeadStoryList.listview_arr));

        //verticalViewPager.setPageTransformer(true, new VerticalPageTransformerAnimate());
        verticalViewPager.setCurrentItem(0);
    }
    public class GetLeadStoryDetails extends AsyncTask<Integer, Void, SoapObject> {

        Context context;
       // private ProgressDialog progressDialog;

        GetLeadStoryDetails (Context ctx){
            context = ctx;
         //   progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
           // Integer ManagerId = (Integer) params [0];
            //SoapObject response = getStudDtls(leadid);

            //unapprovedProjectListObj = new UnapprovedProjectListModule();
            leadStoryListModules = new ArrayList<LeadStoryListModule>();

            String METHOD_NAME = "GetMasterLeaderStoryList1";
            String NamespaceMail="http://mis.leadcampus.org/", SOAP_ACTION1="http://mis.leadcampus.org/GetMasterLeaderStoryList1";//namespace+methodname

            try{
                //mis.leadcampus.org

                SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

             //   request.addProperty("ManagerId", ManagerId);

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
                    Log.d("Tag","soap response LeadStory="+response.toString());

                    responseCount = response.getPropertyCount();

                    Log.d("count", String.valueOf(response.getPropertyCount()));

                    LeadStoryListModule.listview_arr.clear();
                    LeadStoryList.listview_arr.clear();
                    ArrayList<String> innerObj_Class_centers = null;
                    if(responseCount>0) {
                        //complitionProjectListModules = new ArrayList<ComplitionProjectListModule>();
                        //      ArrayList<String> arrayList = new ArrayList<String>();
                        leadInfosarr=new LeadStoryListModule[responseCount];
                        for (i = 0; i < responseCount; i++)
                        {

                            SoapObject response_soapobj = (SoapObject) response.getProperty(i); //resp starts from zero

                       /*     leadStoryListObj.setSlno(response_soapobj.getProperty("slno").toString());
                            leadStoryListObj.setStory_Title(response_soapobj.getProperty("Story_Title").toString());
                            leadStoryListObj.setStory_Description(response_soapobj.getProperty("Story_Description").toString());
                            leadStoryListObj.setImage_Path(response_soapobj.getProperty("Image_Path").toString());
                            leadStoryListObj.setCreated_Date(response_soapobj.getProperty("Created_Date").toString());
                            leadStoryListModules.add(leadStoryListObj);

                            Log.d("tag","leadStoryListModules.get(i)="+leadStoryListModules.get(i).getStory_Title());
                            Log.d("tag","leadStoryListModules="+leadStoryListModules.size());
                            Log.d("tag","leadStoryListObj.getSlno()="+leadStoryListObj.getSlno());
*/
                            String Slno=response_soapobj.getProperty("slno").toString();
                            String Story_Title=response_soapobj.getProperty("Story_Title").toString();
                            String Story_Description=response_soapobj.getProperty("Story_Description").toString();
                            String Image_Path=response_soapobj.getProperty("Image_Path").toString();
                            String Created_Date=response_soapobj.getProperty("Created_Date").toString();
                            String Story_Type=response_soapobj.getProperty("Story_Type").toString();
                            String Card_Image_Path=response_soapobj.getProperty("Card_Image_Path").toString();
                            String URL_Link=response_soapobj.getProperty("URL_Link").toString();
                            String Video_Story_URL=response_soapobj.getProperty("Video_Story_URL").toString();

                          /*  String Project_type=response_soapobj.getProperty("ThemeName").toString();
                            String ActionPlan=response_soapobj.getProperty("ActionPlan").toString();
                            String BeneficiaryNo=response_soapobj.getProperty("BeneficiaryNo").toString();
                            String Objectives=response_soapobj.getProperty("Objectives").toString();
*/
                            leadInfosarr[i]=leadStoryListObj;

                            LeadStoryList.listview_arr.add(new LeadStoryList(Slno, Story_Title,Story_Description, Image_Path, Story_Type,Card_Image_Path,URL_Link,Video_Story_URL));

                            Log.i("Tag","leadStoryListModules.getAmt="+ LeadStoryList.listview_arr.size());
                        }
                        Log.i("tag","LeadStoryList.listview_arr1=="+LeadStoryList.listview_arr.size());
                     //   verticalViewPager.setAdapter(new VerticlePagerAdapter(getContext(), LeadStoryList.listview_arr));
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
         /*   progressDialog.setMessage("Loading Feeds for you..!!");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();*/
        }

        @Override
        protected void onPostExecute(SoapObject result) {
            progressBar.setVisibility(View.GONE);

            if(result!=null) {
                //    progressDialog.dismiss();

                Log.i("Tag", "leadInfosarr===" + leadInfosarr);
                Log.i("Tag", "LeadStoryList.listview_arr.size()===" + LeadStoryList.listview_arr.size());
                Log.i("Tag", "LeadStoryList.listview_arr===" + LeadStoryList.listview_arr.get(0).getSlno());
                //    showActivity();

                Integer array_slno=0,notification_slno=0;
                for(int k=0;k<LeadStoryList.listview_arr.size();k++) {
                    Log.i("Tag", "LeadStoryList.listview_arr k===" + LeadStoryList.listview_arr.get(k).getSlno());
                    Log.i("Tag", "strtext k===" + strtext);

                    String str_slno= LeadStoryList.listview_arr.get(k).getSlno();
                  //  Integer int_txt= Integer.valueOf(strtext);

                    if(str_slno.equals(strtext)) {
                  //  if(str_slno==int_txt){
                        Log.i("Tag", "LeadStoryList.listview_arr k1===" + LeadStoryList.listview_arr.get(k).getSlno());
                        Log.i("Tag", "strtext k===" + strtext);
                        array_slno=k;
                        notification_slno=Integer.valueOf(strtext);
                       // verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity(), LeadStoryList.listview_arr));
                        //verticalViewPager.setCurrentItem(k);
                    }

                }
                if(LeadStoryList.listview_arr.get(array_slno).getSlno().equals(strtext)){
                    verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity(), LeadStoryList.listview_arr));
                    verticalViewPager.setCurrentItem(array_slno);
                }
                else{
                    verticalViewPager.setAdapter(new VerticlePagerAdapter(getActivity(), LeadStoryList.listview_arr));
                    verticalViewPager.setCurrentItem(0);
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

  /*  public class ViewProject extends AsyncTask<Void, Void, SoapObject> {


        ViewProject (Context ctx){
            context = ctx;
        }

        @Override
        protected SoapObject doInBackground(Void... params) {
            //String versionCode = (String) params[2];

            SoapObject response = getProjectDetails();

            //SoapPrimitive response =getProjectDetails();
            Log.d("Soap response is",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {

//            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(SoapObject result) {
    /*        SoapPrimitive S_ProjectTitle,S_ThemeName,S_BeneficiaryNo,S_Objective,S_actionPlan,S_ManagerComments,S_MaterialName,S_MaterialCost,S_TotalCost,S_ApprovalAmt;
            Object O_ProjectTitle,O_ThemeName,O_BeneficiaryNo,O_Objective,O_actionPlan,O_ManagerComments,O_MaterialName,O_MaterialCost,O_TotalCost,O_ApprovalAmt,O_Status;
            String str_ApprovalAmt= null,str_ProjectTitle = null,str_ThemeName = null,str_BeneficiaryNo = null,str_Objective = null,str_actionPlan = null,str_ManagerComments=null,str_materialName=null,str_materialCost=null;
            SoapObject SO_ThemeName;
            //   if(flag==0) {
            O_Status = result.getProperty("status");
            if(O_Status.toString().equals("Invalid project details")){
                Toast.makeText(getActivity(),"Invalid project details",Toast.LENGTH_LONG).show();
            }else {
                O_ProjectTitle = result.getProperty("Title");
                if (!O_ProjectTitle.toString().equals("anyType") && !O_ProjectTitle.toString().equals(null)) {
                    S_ProjectTitle = (SoapPrimitive) result.getProperty("Title");
                    Log.d("Titlesssssss", S_ProjectTitle.toString());
                    str_ProjectTitle = S_ProjectTitle.toString();
                }

                O_ThemeName = result.getProperty("ThemeName");
                if (O_ThemeName.toString().equals("anyType") && O_ThemeName.toString().equals(null)) {
                    SO_ThemeName = (SoapObject) result.getProperty("ThemeName");
                    Log.d("ThemeNamessss", SO_ThemeName.toString());
                    str_ThemeName = SO_ThemeName.toString();
                    //   spin_projectType.setSelection(0);
                }
                if (!O_ThemeName.toString().equals("anyType") && !O_ThemeName.toString().equals(null)) {
                    S_ThemeName = (SoapPrimitive) result.getProperty("ThemeName");
                    Log.d("ThemeNamessss", S_ThemeName.toString());
                    str_ThemeName = S_ThemeName.toString();
                    //    spin_projectType.setSelection(0);
                }

                O_BeneficiaryNo = result.getProperty("BeneficiaryNo");
                if (!O_BeneficiaryNo.toString().equals("anyType") && !O_BeneficiaryNo.toString().equals(null)) {
                    S_BeneficiaryNo = (SoapPrimitive) result.getProperty("BeneficiaryNo");
                    Log.d("BeneficiaryNo", S_BeneficiaryNo.toString());
                    str_BeneficiaryNo = S_BeneficiaryNo.toString();
                }

                O_ApprovalAmt = result.getProperty("SanctionAmount");
                if (!O_ApprovalAmt.toString().equals("anyType") && !O_ApprovalAmt.toString().equals(null)) {
                    S_ApprovalAmt = (SoapPrimitive) result.getProperty("SanctionAmount");
                    Log.d("BeneficiaryNo", S_ApprovalAmt.toString());
                    str_ApprovalAmt = S_ApprovalAmt.toString();
                }

                O_Objective = result.getProperty("Objectives");
                if (!O_Objective.toString().equals("anyType") && !O_Objective.toString().equals(null)) {
                    S_Objective = (SoapPrimitive) result.getProperty("Objectives");
                    Log.d("Objectivess", S_Objective.toString());
                    str_Objective = S_Objective.toString();
                }

                O_actionPlan = result.getProperty("ActionPlan");
                if (!O_actionPlan.toString().equals("anyType") && !O_actionPlan.toString().equals(null)) {
                    S_actionPlan = (SoapPrimitive) result.getProperty("ActionPlan");
                    Log.d("ActionPlansss", S_actionPlan.toString());
                    str_actionPlan = S_actionPlan.toString();
                }

                O_ManagerComments = result.getProperty("ManagerComments");
                if (!O_ManagerComments.toString().equals("anyType") && !O_ManagerComments.toString().equals(null)) {
                    S_ManagerComments = (SoapPrimitive) result.getProperty("ManagerComments");
                    Log.d("Manager Comments", S_ManagerComments.toString());
                    str_ManagerComments = S_ManagerComments.toString();
                }

                O_TotalCost = result.getProperty("Amount");
                if (!O_TotalCost.toString().equals("anyType") && !O_TotalCost.toString().equals(null)) {
                    S_TotalCost = (SoapPrimitive) result.getProperty("Amount");
                    Log.d("Total Cost", S_TotalCost.toString());
                    str_totalCost = S_TotalCost.toString();
                }
            }

*/
        //    progressBar.setVisibility(View.GONE);
 /*       }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getProjectDetails() {
        String METHOD_NAME = "GetMasterLeaderStoryList";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetMasterLeaderStoryList";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);

            //envelope.addMapping(NAMESPACE, "MediSenseLog",new MediSenseLog().getClass());
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE("http://testbed.dfindia.org:8080/leadws/Managerws.asmx?WSDL");
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                //SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                Log.e("Tag","soap response LeadStory"+response.toString());

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

    } */



    private class VerticalPageTransformerAnimate implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        @Override
        public void transformPage(View view, float position) {

            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            float alpha = 0;
            if (0 <= position && position <= 1) {
                alpha = 1 - position;
            } else if (-1 < position && position < 0) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
                float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horizontalMargin - verticalMargin / 2);
                } else {
                    view.setTranslationX(-horizontalMargin + verticalMargin / 2);
                }

                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                alpha = position + 1;
            }

            view.setAlpha(alpha);
            view.setTranslationX(view.getWidth() * -position);
            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);

        }
    }

}
