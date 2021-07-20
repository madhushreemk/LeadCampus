package com.leadcampusapp;

/**
 * Created by Admin on 20-07-2018.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class ChartProjectCountFragment extends Fragment //implements OnChartValueSelectedListener
{

    int count=0;
    Integer MDId;
    SharedPreferences shardprefPM_obj;
    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid";
    String str_MangerID;
    PieChart pieChart;

    String str_responsecount,str_responseStatus,str_studentcount,str_collegecount;
    String ApprovedCount="0",ComplitedCount="0",UnapprovedCount="0",RequestForCompletion="0",Rejected="0",RequestForModification="0";

    public static final String  PREFBook_PM_Count= "prefbook_pm_count";  //sharedpreference Book
    public static final String PrefID_UNCount = "prefid_UNCount"; //
    public static final String PrefID_ComCount = "prefid_ComCount"; //
    public static final String PrefID_AppCount = "prefid_AppCount"; //
    public static final String PrefID_ReqModCount = "prefid_ReqModCount"; //
    public static final String PrefID_RejCount = "prefid_RejCount"; //
    public static final String PrefID_ReqComCount = "prefid_ReqComCount"; //

    SharedPreferences shardprefPM_obj_count;
    SharedPreferences.Editor editor_PM_count;
    BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.frag_project_countchart, container, false);
        Intent intent = getActivity().getIntent();

      //  barChart = (BarChart) view.findViewById(R.id.piechart);

        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_leadId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);

        shardprefPM_obj_count=getActivity().getSharedPreferences(PREFBook_PM_Count, Context.MODE_PRIVATE);

        shardprefPM_obj_count.getString(PrefID_AppCount, "").trim();
        ApprovedCount = shardprefPM_obj_count.getString(PrefID_AppCount, "").trim();
        Log.d("ApprovedCount:",ApprovedCount);

        shardprefPM_obj_count.getString(PrefID_UNCount, "").trim();
        UnapprovedCount = shardprefPM_obj_count.getString(PrefID_UNCount, "").trim();
        Log.d("ApprovedCount:",UnapprovedCount);

        shardprefPM_obj_count.getString(PrefID_RejCount, "").trim();
        Rejected = shardprefPM_obj_count.getString(PrefID_RejCount, "").trim();
        Log.d("ApprovedCount:",Rejected);

        shardprefPM_obj_count.getString(PrefID_ReqComCount, "").trim();
        RequestForCompletion = shardprefPM_obj_count.getString(PrefID_ReqComCount, "").trim();
        Log.d("RequestForCompletion:",RequestForCompletion);

        shardprefPM_obj_count.getString(PrefID_ReqModCount, "").trim();
        RequestForModification = shardprefPM_obj_count.getString(PrefID_ReqModCount, "").trim();
        Log.d("PrefID_ReqModCount:",RequestForModification);

        shardprefPM_obj_count.getString(PrefID_ComCount, "").trim();
        ComplitedCount = shardprefPM_obj_count.getString(PrefID_ComCount, "").trim();
        Log.d("PrefID_ComCount:",ComplitedCount);

        /*String UnapprovedCount=intent.getStringExtra("UnapprovedCount");
        String ComplitedCount=intent.getStringExtra("ComplitedCount");
        String ApprovedCount=intent.getStringExtra("ApprovedCount");
        String RequestForCompletion=intent.getStringExtra("RequestForCompletion");
        String RequestForModification=intent.getStringExtra("RequestForModification");
        String Rejected=intent.getStringExtra("Rejected");
        String str_studentcount=intent.getStringExtra("str_studentcount");
        String str_collegecount=intent.getStringExtra("str_collegecount");*/

        pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        Log.i("tag","UnapprovedCount"+UnapprovedCount);


     /*   ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();


        if(unapproved_float!=0) {
            yvalues.add(new Entry(unapproved_float, 0));
            xVals.add("Proposed");
        }
        if(approved_float!=0) {
            yvalues.add(new Entry(approved_float, 2));
            xVals.add("Approved");
        }
        if(complited_float!=0) {
            yvalues.add(new Entry(complited_float, 1));
            xVals.add("Completed");
        }
        if(rejected_float!=0) {
            yvalues.add(new Entry(rejected_float, 5));
            xVals.add("Rejected");
        }
        if(requestforcomplition_float!=0) {
            yvalues.add(new Entry(requestforcomplition_float, 3));
            xVals.add("Completion");
        }
        if(requestformodification_float!=0) {
            yvalues.add(new Entry(requestformodification_float, 4));
            xVals.add("Modification");
        }
*/




      /*  PieDataSet dataSet = new PieDataSet(yvalues, "");
        PieData data = new PieData(xVals,dataSet);

        data.setValueFormatter(new DefaultValueFormatter(1));*/
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));

       /* Log.i("tag","data="+data.toString()+pieChart.getData());
        pieChart.getData();
        pieChart.setData(data);
        pieChart.setDescription("Project Counts");
        pieChart.setDescriptionColor(Color.WHITE);
        pieChart.setDescriptionPosition(450,100);
        pieChart.setDescriptionTextSize(20f);
        pieChart.getLegend().setTextColor(Color.BLACK);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);
        pieChart.setDrawSliceText(false);
        final int[] MY_COLORS = {Color.rgb(229, 154, 29),Color.rgb(243, 200, 61), Color.rgb(144, 193, 51),Color.rgb(198, 53, 53), Color.rgb(		255, 0, 128),
                 Color.rgb(45, 170, 165), Color.rgb(127, 0, 255), Color.rgb(185, 98, 237)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);
        dataSet.setColors(colors);

     //   dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setClickable(false);*/

    //   pieChart.setOnChartValueSelectedListener(this);

       // pieChart.animateXY(1400, 1400);

        // showActivity();
        pieChartFunction();
     //  newbarchart();
        return view;
    }
    private void newbarchart(){

        Float unapproved_float=Float.valueOf(UnapprovedCount).floatValue();
        Float complited_float=Float.valueOf(ComplitedCount).floatValue();
        Float approved_float=Float.valueOf(ApprovedCount).floatValue();
        Float requestforcomplition_float=Float.valueOf(RequestForCompletion).floatValue();
        Float requestformodification_float=Float.valueOf(RequestForModification).floatValue();
        Float rejected_float=Float.valueOf(Rejected).floatValue();


        ArrayList<BarEntry> entries = new ArrayList<>();
        if(unapproved_float!=0) {
            entries.add(new BarEntry(unapproved_float, 0));
        }
        if(approved_float!=0) {
            entries.add(new BarEntry(approved_float, 1));
        }
        if(complited_float!=0) {
            entries.add(new BarEntry(complited_float, 2));
        }
        if(rejected_float!=0) {
            entries.add(new BarEntry(rejected_float, 3));
        }
        if(requestforcomplition_float!=0) {
            entries.add(new BarEntry(requestforcomplition_float, 4));
        }
        if(requestformodification_float!=0) {
            entries.add(new BarEntry(requestformodification_float, 5));
        }
        /*entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));*/

     /*   for(int k=0;k<listCount.size();k++){
            entries.add(new BarEntry(Float.parseFloat(listCount.get(k)), k));
        }*/

        ArrayList<String> labels = new ArrayList<String>();
        if(unapproved_float!=0) {
            labels.add("Proposed");
        }
        if(approved_float!=0) {
            labels.add("Approved");
        }
        if(complited_float!=0) {
            labels.add("Completed");
        }
        if(rejected_float!=0) {
            labels.add("Rejected");
        }
        if(requestforcomplition_float!=0) {
            labels.add("Completion");
        }
        if(requestformodification_float!=0) {
            labels.add("Modification");
        }

        BarDataSet bardataset = new BarDataSet(entries, null);


       /* labels.add("2013");
        labels.add("2012");
        labels.add("2011");*/


        BarData data1 = new BarData(labels, bardataset);
        barChart.setData(data1); // set the data and list of lables into chart


        //   barChart.setDescription("");  // set the description
        final int[] MY_COLORS = {Color.rgb(229, 154, 29),Color.rgb(243, 200, 61), Color.rgb(144, 193, 51),Color.rgb(198, 53, 53), Color.rgb(		255, 0, 128),
                Color.rgb(45, 170, 165), Color.rgb(127, 0, 255), Color.rgb(185, 98, 237)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);

        bardataset.setColors(colors);


        Legend legend = barChart.getLegend();
        legend.setEnabled(false);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setForm(Legend.LegendForm.SQUARE);
       /* legend.setColors(colors);
        legend.setLabels(labels);*/

        barChart.setDescription(null);
        XAxis xLabels = barChart.getXAxis();
        //  xLabels.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xLabels.setTextColor(Color.BLACK);
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        xLabels.setDrawLabels(true);
       // barChart.getXAxis().setAxisMinValue(6);
       // barChart.getXAxis().setAxisMaxValue(6);
        xLabels.setValues(labels);
        YAxis yLabels = (YAxis) barChart.getAxisLeft();
        yLabels.setTextColor(Color.BLACK);

        YAxis yLabels1 = (YAxis) barChart.getAxisRight();
        yLabels1.setTextColor(Color.BLACK);
        // xLabels.setLabelRotationAngle(-30f);

        barChart.setDescriptionColor(Color.WHITE);
barChart.getXAxis().setAxisMaxValue(6);
        barChart.animateY(3000);

    }

    private void pieChartFunction(){

        Float unapproved_float=Float.valueOf(UnapprovedCount).floatValue();
        Float complited_float=Float.valueOf(ComplitedCount).floatValue();
        Float approved_float=Float.valueOf(ApprovedCount).floatValue();
        Float requestforcomplition_float=Float.valueOf(RequestForCompletion).floatValue();
        Float requestformodification_float=Float.valueOf(RequestForModification).floatValue();
        Float rejected_float=Float.valueOf(Rejected).floatValue();

        Log.i("tag","Proposed="+unapproved_float+"complited_float="+complited_float);
        Log.i("tag","approved_float="+approved_float+"requestforcomplition_float="+requestforcomplition_float);
        Log.i("tag","requestformodification_float="+requestformodification_float+"rejected_float="+rejected_float);

        ArrayList<Entry> yvalues2 = new ArrayList<Entry>();
        ArrayList<String> xVals2 = new ArrayList<String>();

        if(unapproved_float!=0) {
            yvalues2.add(new Entry(unapproved_float, 0));
        }
        if(approved_float!=0) {
            yvalues2.add(new Entry(approved_float, 1));
        }
        if(complited_float!=0) {
            yvalues2.add(new Entry(complited_float, 2));
        }
        if(rejected_float!=0) {
            yvalues2.add(new Entry(rejected_float, 3));
        }
        if(requestforcomplition_float!=0) {
            yvalues2.add(new Entry(requestforcomplition_float, 4));
        }
        if(requestformodification_float!=0) {
            yvalues2.add(new Entry(requestformodification_float, 5));
        }

        /*if(Not_Assigned_Per_float!=0) {
            yvalues2.add(new Entry(Not_Assigned_Per_float, 3));
        }*/


        //    PieDataSet dataSet2 = new PieDataSet(yvalues2, "");
        //  pieChart.getDescription().setEnabled(false);

        xVals2.add("Proposed");
        xVals2.add("Approved");
        xVals2.add("Completed");
        xVals2.add("Rejected");
        xVals2.add("Completion");
        xVals2.add("Modification");
        // xVals2.add("Not_Assigned_Per");

        //   pieChart.setEntryLabelColor(Color.BLACK);
        PieDataSet dataSet = new PieDataSet(yvalues2, "");

        final int[] MY_COLORS = {Color.rgb(229, 154, 29),Color.rgb(243, 200, 61), Color.rgb(144, 193, 51),Color.rgb(198, 53, 53), Color.rgb(		255, 0, 128),
                Color.rgb(45, 170, 165), Color.rgb(127, 0, 255), Color.rgb(185, 98, 237)};
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for(int c: MY_COLORS) colors.add(c);

     /*  ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/

        //  colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals2,dataSet);
        // In Percentage term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
       /* pieChart.setDescription("Student and College Counts");
        pieChart.setDescriptionColor(Color.WHITE);
        pieChart.setDescriptionPosition(450,100);
        pieChart.setDescriptionTextSize(20f);*/
        pieChart.getLegend().setTextColor(Color.BLACK);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);
        pieChart.setDrawSliceText(false);
        //  dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setDescriptionColor(Color.WHITE);

        // pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateXY(1400, 1400);

    }

  /*  @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);*/
     /*   if(e.getXIndex()==0){
            Intent ittEditProjToProjStatus = new Intent(getContext(),PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",0);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getXIndex()==2){
            Intent ittEditProjToProjStatus = new Intent(getContext(),PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",1);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getXIndex()==1){
            Intent ittEditProjToProjStatus = new Intent(getContext(),PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",3);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getXIndex()==3){
            Intent ittEditProjToProjStatus = new Intent(getContext(),PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",2);
            startActivity(ittEditProjToProjStatus);
        }*/
   /* }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
*/


    public class GetProjectCount extends AsyncTask<Integer, Void, SoapObject> {

        Context context;

        //ProgressBar progressBar;

        GetProjectCount (Context ctx){
            context = ctx;
        }


        @Override
        protected SoapObject doInBackground(Integer... params) {
            Integer ManagerId = (Integer) params [0];
            //SoapObject response = getStudDtls(leadid);

            String METHOD_NAME = "GetProjectCount";
            String NamespaceMail="http://mis.leadcampus.org/", SOAP_ACTION1="http://mis.leadcampus.org/GetProjectCount";//namespace+methodname

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


                //anyType{Counts=1; ProjectStatus=Approved; Status=Success; };

                try
                {
                    androidHttpTransport.call(SOAP_ACTION1, envelope);
                    SoapObject response = (SoapObject) envelope.getResponse();
                    Log.d("tag","soap response project count"+response.toString());

                    int int_projectcount =response.getPropertyCount();

                    Log.e("projectcount", Integer.toString(int_projectcount));

                    for(int i=0;i<int_projectcount;i++)
                    {
                        SoapObject innerresponse = (SoapObject) response.getProperty(i);
                        str_responsecount =innerresponse.getProperty("Counts").toString();
                        str_responseStatus=innerresponse.getProperty("ProjectStatus").toString();

                        if(str_responseStatus.equals("Approved")){
                            ApprovedCount=str_responsecount;
                        }
                        else if(str_responseStatus.equals("Completed")){
                            ComplitedCount=str_responsecount;
                        }
                        else if(str_responseStatus.equals("Proposed")){
                            UnapprovedCount=str_responsecount;
                        }
                        else if(str_responseStatus.equals("Rejected")){
                            Rejected=str_responsecount;
                        }
                        else if(str_responseStatus.equals("RequestForCompletion")){
                            RequestForCompletion=str_responsecount;
                        }
                        else if(str_responseStatus.equals("RequestForModification")){
                            RequestForModification=str_responsecount;
                        }

                        Log.e("innerresponse",str_responsecount+str_responseStatus);
                    }
                    Log.e("innerresponse",str_responsecount+str_responseStatus);

                    String Counts=response.getProperty(0).toString();
                    Log.e("Counts",Counts);
                    Log.e("ApprovedCount",ApprovedCount);
                    Log.e("ComplitedCount",ComplitedCount);
                    Log.e("UnapprovedCount",UnapprovedCount);

                   /* String Counts=response.getProperty("Counts").toString();
                       String ProjectStatus=response.getProperty("ProjectStatus").toString();

                      Log.d("Counts",Counts);
                      Log.d("ProjectStatus",ProjectStatus);*/

                    // return response;

                }
                catch (Exception t) {
                    Log.e("request fail PCount", "> " + t.getMessage().toString());
                }
            }catch (Exception t) {
                Log.d("exception outside",t.getMessage().toString());
            }

            //Log.d("Soap response is",response.toString());

            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(SoapObject result) {
            /*compeleted_tv.setText(ComplitedCount);
            pending_tv.setText(UnapprovedCount);
            approved_tv.setText(ApprovedCount);*/
           /* setupComplited();
            setupApproved();
            setupUnapproved();*/
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

}//end of fragment class
