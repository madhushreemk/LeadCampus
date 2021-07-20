package com.leadcampusapp;

/**
 * Created by Admin on 20-07-2018.
 */

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
import com.github.mikephil.charting.utils.ColorTemplate;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

public class ChartFundAmtCountFragment extends Fragment
{

    private ArrayList<String> listThemeName;
    private ArrayList<String> listCount;

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    SharedPreferences shardprefPM_obj;
    String str_MangerID;
    Integer MDId;
    BarChart barChart;
    String AcademicYear;
    private HashMap<String,Integer> mapYearCode;
    AppCompatSpinner sandbox_spin,year_spin;
    PieChart pieChart;
    String str_Status=null,str_fundriserdamount=null,str_Counts=null,str_SanctionAmount,str_fundRelised,str_AcademicCode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.activity_pm_fundamt_count, container, false);
        listThemeName = new ArrayList<String>();
        listCount = new ArrayList<String>();

        shardprefPM_obj= getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MDId:",str_MangerID);
        //  Toast.makeText(this,"LeadId: "+str_MangerID,Toast.LENGTH_LONG).show();
        MDId=Integer.parseInt(str_MangerID);

        barChart = (BarChart) view.findViewById(R.id.piechart);

       /* pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);*/

        year_spin=(AppCompatSpinner) view.findViewById(R.id.spin_year);
        mapYearCode = new HashMap<String,Integer>();
        initializeSpinnerYear();

        year_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AcademicYear = year_spin.getSelectedItem().toString();

                if(AcademicYear!="Select Year"){
                    GetFundAmtCount getFundAmtCount = new GetFundAmtCount(getActivity());
                    getFundAmtCount.execute();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      //  GetFundAmtCount getFundAmtCount = new GetFundAmtCount(getActivity());
       // getFundAmtCount.execute();

        return view;
    }

    @SuppressLint("RestrictedApi")
    private void initializeSpinnerYear() {
        final ArrayList<String> listYear = new ArrayList<String>();

       // listYear.add("Select Year");
        listYear.add("2018-2019");
        listYear.add("2017-2018");
        listYear.add("2016-2017");
        listYear.add("2015-2016");
        listYear.add("2014-2015");
        listYear.add("Below 2014");

        mapYearCode.put("Below 2014",0);
        mapYearCode.put("2014-2015",1);
        mapYearCode.put("2015-2016",2);
        mapYearCode.put("2016-2017",3);
        mapYearCode.put("2017-2018",4);
        mapYearCode.put("2018-2019",5);

        ArrayAdapter dataAdapterListYear = new ArrayAdapter(getContext(), R.layout.simple_spinner_items, listYear);
        dataAdapterListYear.setDropDownViewResource(R.layout.spinnercustomstyle);

        year_spin.setAdapter(dataAdapterListYear);
        year_spin.setSupportBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.colorBlack));

    }

    public class GetFundAmtCount extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        //AlertDialog alertDialog;
        private ProgressDialog progressDialog;

        //private ProgressBar progressBar;

        GetFundAmtCount (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String str_leadId = (String) params [0];
            //String versionCode = (String) params[2];

            SoapObject response = getFundAmtCount();

            //Log.d("GetThemeWiseProjCout",response.toString());

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

            Log.d("GetFundAmt resp",result.toString());

            SoapPrimitive S_Status,S_ThemeName,S_Counts,S_fundriserdamount,S_SanctionAmount,S_fundRelised,S_AcademicCode;
            Object O_Status,O_ThemeName,O_Counts,O_fundriserdamount,O_SanctionAmount,O_fundRelised,O_AcademicCode;

            for(int i=0;i < result.getPropertyCount();i++) {
                SoapObject list = (SoapObject) result.getProperty(i);

                Log.d("finalStringssssss",list.toString());
                SoapPrimitive indivisualObject = (SoapPrimitive) list.getProperty("Status");

                O_Status = list.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) list.getProperty("Status");
                    Log.d("Status:", S_Status.toString());
                    str_Status = S_Status.toString();
                }


                if(str_Status.equalsIgnoreCase("Success")) {
                    O_AcademicCode = list.getProperty("AcademicCode");
                    if (!O_AcademicCode.toString().equals("anyType{}") && !O_AcademicCode.toString().equals(null)) {
                        S_AcademicCode = (SoapPrimitive) list.getProperty("AcademicCode");
                        Log.d("S_AcademicCode", S_AcademicCode.toString());
                        str_AcademicCode = S_AcademicCode.toString();
                    }
                    int yearId = mapYearCode.get(AcademicYear);
                    if(str_AcademicCode.equals(String.valueOf(yearId))){
                        O_fundriserdamount = list.getProperty("fundriserdamount");
                        if (!O_fundriserdamount.toString().equals("anyType{}") && !O_fundriserdamount.toString().equals(null)) {
                            S_fundriserdamount = (SoapPrimitive) list.getProperty("fundriserdamount");
                            Log.d("S_fundriserdamount", S_fundriserdamount.toString());
                            str_fundriserdamount = S_fundriserdamount.toString();
                        }

                        O_SanctionAmount = list.getProperty("SanctionAmount");
                        if (!O_SanctionAmount.toString().equals("anyType{}") && !O_SanctionAmount.toString().equals(null)) {
                            S_SanctionAmount = (SoapPrimitive) list.getProperty("SanctionAmount");
                            Log.d("SanctionAmount", S_SanctionAmount.toString());
                            str_SanctionAmount = S_SanctionAmount.toString();
                        }

                        O_fundRelised = list.getProperty("fundRelised");
                        if (!O_fundRelised.toString().equals("anyType{}") && !O_fundRelised.toString().equals(null)) {
                            S_fundRelised = (SoapPrimitive) list.getProperty("fundRelised");
                            Log.d("fundRelised", S_fundRelised.toString());
                            str_fundRelised = S_fundRelised.toString();
                        }

                    }



                   /* if (str_ThemeName != null && !str_ThemeName.isEmpty() && str_ThemeName != "" && !str_ThemeName.equals("anyType{}") && str_ThemeName != "{}") {
                        //txt.setText(str_ThemeName);
                        Log.d("ThemeNameIsss",str_ThemeName);
                        listThemeName.add(str_ThemeName);
                    }
*/
                   /* if (str_Counts != null && !str_Counts.isEmpty() && str_Counts != "" && !str_Counts.equals("anyType{}") && str_Counts != "{}") {
                        //txt.setText(str_ThemeName);
                        Log.d("Countssissss",str_Counts);
                        listCount.add(str_Counts);
                    }*/

      /*          if (str_StudCollegeName != null && !str_StudCollegeName.isEmpty() && str_StudCollegeName != "" && !str_StudCollegeName.equals("anyType{}") && str_StudCollegeName != "{}") {
                    txt_studCollegeName.setText(str_StudCollegeName);
                }

                if (str_StudLeadId != null && !str_StudLeadId.isEmpty() && str_StudLeadId != "" && !str_StudLeadId.equals("anyType{}") && str_StudLeadId != "{}") {
                    txt_leadId.setText(str_StudLeadId);
                }

                if (str_StudType != null && !str_StudType.isEmpty() && str_StudType != "" && !str_StudType.equals("anyType{}") && str_StudType != "{}") {
                    txt_studType.setText(str_StudType);
                }
*/
                }

            }

          //  multiBarchartsInOne();
        //    pieChartFunction();
            newbarchart();
            progressDialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private SoapObject getFundAmtCount()
    {

        String METHOD_NAME = "GetFundamountYearwise";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetFundamountYearwise";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);

            request.addProperty("ManagerId", MDId);

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

    private void pieChartFunction(){

        Float AcademicCode_float= Float.valueOf(str_AcademicCode).floatValue();
        Float fundriserdamount_float= Float.valueOf(str_fundriserdamount).floatValue();
        Float fundRelised_float= Float.valueOf(str_fundRelised).floatValue();
        Float SanctionAmount_float= Float.valueOf(str_SanctionAmount).floatValue();

        ArrayList<Entry> yvalues2 = new ArrayList<Entry>();
        ArrayList<String> xVals2 = new ArrayList<String>();

        if(fundriserdamount_float!=0) {
            yvalues2.add(new Entry(fundriserdamount_float, 0));
        }
        if(fundRelised_float!=0) {
            yvalues2.add(new Entry(fundRelised_float, 1));
        }
        if(SanctionAmount_float!=0) {
            yvalues2.add(new Entry(SanctionAmount_float, 2));
        }
        /*if(Not_Assigned_Per_float!=0) {
            yvalues2.add(new Entry(Not_Assigned_Per_float, 3));
        }*/


    //    PieDataSet dataSet2 = new PieDataSet(yvalues2, "");
      //  pieChart.getDescription().setEnabled(false);

        xVals2.add("Fund Raised");
        xVals2.add("Fund Released");
        xVals2.add("Approved Amount");
       // xVals2.add("Not_Assigned_Per");

     //   pieChart.setEntryLabelColor(Color.BLACK);
        PieDataSet dataSet = new PieDataSet(yvalues2, "");

        final int[] MY_COLORS = {Color.rgb(45, 170, 165), Color.rgb(198, 53, 53), Color.rgb(243, 200, 61),
                Color.rgb(45, 170, 165), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};
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
    private void multiBarchartsInOne() {
        // create BarEntry for Bar Group 1
/*        HorizontalBarChart barChart = (HorizontalBarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(8f, 0));
        bargroup1.add(new BarEntry(2f, 1));
        bargroup1.add(new BarEntry(5f, 2));
        bargroup1.add(new BarEntry(20f, 3));
        bargroup1.add(new BarEntry(15f, 4));
        bargroup1.add(new BarEntry(19f, 5));

// create BarEntry for Bar Group 1
        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(6f, 0));
        bargroup2.add(new BarEntry(10f, 1));
        bargroup2.add(new BarEntry(5f, 2));
        bargroup2.add(new BarEntry(25f, 3));
        bargroup2.add(new BarEntry(4f, 4));
        bargroup2.add(new BarEntry(17f, 5));

// creating dataset for Bar Group1
        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Bar Group 1");

//barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

// creating dataset for Bar Group 2
        BarDataSet barDataSet2 = new BarDataSet(bargroup2, "Bar Group 2");
        barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        List<IBarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);







        //BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");
        labels.add("2010");
        labels.add("2009");
        labels.add("2008");
        labels.add("2007");
        labels.add("2006");
        labels.add("2005");
        labels.add("2004");
        labels.add("2003");


        //BarData data = new BarData(labels,dataSets);
        BarData data = new BarData(labels,dataSets);
        barChart.setData(data); // set the data and list of lables into chart

        barChart.setDescription("Set Bar Chart Description");  // set the description

        //bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);*/





        //HorizontalBarChart barChart = (HorizontalBarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();


        for(int k=0;k<listCount.size();k++){
            entries.add(new BarEntry(Float.parseFloat(listCount.get(k)), k));
        }
/*        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(2f, 1));
        entries.add(new BarEntry(5f, 2));
        entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));*/

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2016");
        labels.add("2015");
        labels.add("2014");
        labels.add("2013");
        labels.add("2012");
        labels.add("2011");

        //   barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
        //   XAxis xAxis = barChart.getXAxis();
        //   xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        String[] arrString = new String[listThemeName.size()];

        for(int l=0;l<listThemeName.size();l++){
            arrString[l] = listThemeName.get(l);
            Log.d("arrStringissss:",arrString[l]);
        }

        //bardataset.

        //bardataset.setStackLabels(new String[]{"2011","2012","2013","2014","2015","2016"});
        bardataset.setStackLabels(arrString);

        BarData data = new BarData(labels, bardataset);
        //    BarData data = new BarData(bardataset);
        barChart.setData(data); // set the data and list of lables into chart

        //barChart.setDescription("Set Bar Chart Description");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);
    }
    public void newbarchart(){

        Float AcademicCode_float= Float.valueOf(str_AcademicCode).floatValue();
      //  if(str_fundriserdamount.equals())
        Float fundriserdamount_float=null;
        Float fundRelised_float=null;
        Float SanctionAmount_float = null;
        Log.i("tag","str_fundriserdamount="+str_fundriserdamount+"str_fundRelised="+str_fundRelised+"str_SanctionAmount="+str_SanctionAmount);
            if(!(str_fundriserdamount ==null)){
                fundriserdamount_float= Float.valueOf(str_fundriserdamount).floatValue();
            }
        if( !(str_fundRelised ==null)){
            fundRelised_float= Float.valueOf(str_fundRelised).floatValue();
        }
        if( !(str_SanctionAmount ==null)){
            SanctionAmount_float= Float.valueOf(str_SanctionAmount).floatValue();
        }

        /* fundRelised_float= Float.valueOf(str_fundRelised).floatValue();
         SanctionAmount_float= Float.valueOf(str_SanctionAmount).floatValue();*/

        ArrayList<BarEntry> entries = new ArrayList<>();
Log.i("tag","SanctionAmount_float="+SanctionAmount_float+"fundriserdamount_float="+fundriserdamount_float+"fundRelised_float="+fundRelised_float);
      if(SanctionAmount_float!=null) {
          if (SanctionAmount_float != 0) {
              entries.add(new BarEntry(SanctionAmount_float, 1));
          }
      }
        if(fundriserdamount_float!=null) {
            if (fundriserdamount_float != 0) {
                entries.add(new BarEntry(fundriserdamount_float, 0));
            }
        }
        if(fundRelised_float!=null) {
            if (fundRelised_float != 0) {
                entries.add(new BarEntry(fundRelised_float, 2));
            }
        }
        /*entries.add(new BarEntry(20f, 3));
        entries.add(new BarEntry(15f, 4));
        entries.add(new BarEntry(19f, 5));*/

     /*   for(int k=0;k<listCount.size();k++){
            entries.add(new BarEntry(Float.parseFloat(listCount.get(k)), k));
        }*/

        ArrayList<String> labels = new ArrayList<String>();
        if(SanctionAmount_float!=null) {
            if (SanctionAmount_float != 0) {
                labels.add("Approved Amount");
            }
        }
        if(fundriserdamount_float!=null) {
            if (fundriserdamount_float != 0) {
                labels.add("Requested Amount");
            }
        }
        if(fundRelised_float!=null) {
            if (fundRelised_float != 0) {
                labels.add("Fund Released");
            }
        }
        BarDataSet bardataset = new BarDataSet(entries, null);


       /* labels.add("2013");
        labels.add("2012");
        labels.add("2011");*/


        BarData data1 = new BarData(labels, bardataset);
        barChart.setData(data1); // set the data and list of lables into chart


        //   barChart.setDescription("");  // set the description
        final int[] MY_COLORS = {Color.rgb(45, 170, 165),Color.rgb(243, 200, 61), Color.rgb(198, 53, 53),
                Color.rgb(144, 193, 51), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};
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
        YAxis yLabels = (YAxis) barChart.getAxisLeft();
        yLabels.setTextColor(Color.BLACK);

        YAxis yLabels1 = (YAxis) barChart.getAxisRight();
        yLabels1.setTextColor(Color.BLACK);
       // xLabels.setLabelRotationAngle(-30f);

        barChart.setDescriptionColor(Color.WHITE);

        barChart.animateY(3000);

    }

}//end of fragment class
