package com.leadcampusapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class PMGetThemeWiseProjectCount extends AppCompatActivity {

    private int MDId;
    private ArrayList<String> listThemeName;
    private ArrayList<String> listCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmget_theme_wise_project_count);


        listThemeName = new ArrayList<String>();
        listCount = new ArrayList<String>();

        MDId = getIntent().getIntExtra("MDId",0);
        Log.d("MDIdiss", String.valueOf(MDId));

        GetThemeWiseProjCount getThemeWiseProjCount = new GetThemeWiseProjCount(PMGetThemeWiseProjectCount.this);
        getThemeWiseProjCount.execute();
    }


    public class GetThemeWiseProjCount extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        //AlertDialog alertDialog;
        private ProgressDialog progressDialog;

        //private ProgressBar progressBar;

        GetThemeWiseProjCount (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(Void... params) {
            //String str_leadId = (String) params [0];
            //String versionCode = (String) params[2];

            SoapObject response = getThemeWiseProjCount();

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

            Log.d("GetStudentDetailssresp",result.toString());

   /*         SoapPrimitive S_Status,S_ThemeName,S_Counts;
            Object O_Status,O_ThemeName,O_Counts;
            String str_Status=null,str_ThemeName=null,str_Counts=null;

            for(int i=0;i<result.)


            O_Status = result.getProperty("Status");
            if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                S_Status = (SoapPrimitive) result.getProperty("Status");
                Log.d("Status:", S_Status.toString());
                str_Status = S_Status.toString();
            }

            if(str_Status.equalsIgnoreCase("Success")){
                O_ThemeName = result.getProperty("ThemeName");
                if (!O_ThemeName.toString().equals("anyType{}") && !O_ThemeName.toString().equals(null)) {
                    S_ThemeName = (SoapPrimitive) result.getProperty("ThemeName");
                    Log.d("ThemeName", S_ThemeName.toString());
                    str_ThemeName = S_ThemeName.toString();
                }

                O_Counts = result.getProperty("Counts");
                if (!O_Counts.toString().equals("anyType{}") && !O_Counts.toString().equals(null)) {
                    S_Counts = (SoapPrimitive) result.getProperty("Counts");
                    Log.d("Countsss", S_Counts.toString());
                    str_Counts = S_Counts.toString();
                }

                if (str_ThemeName != null && !str_ThemeName.isEmpty() && str_ThemeName != "" && !str_ThemeName.equals("anyType{}") && str_ThemeName != "{}") {
                    //txt.setText(str_ThemeName);
                }

   *//*             if (str_StudCollegeName != null && !str_StudCollegeName.isEmpty() && str_StudCollegeName != "" && !str_StudCollegeName.equals("anyType{}") && str_StudCollegeName != "{}") {
                    txt_studCollegeName.setText(str_StudCollegeName);
                }

                if (str_StudLeadId != null && !str_StudLeadId.isEmpty() && str_StudLeadId != "" && !str_StudLeadId.equals("anyType{}") && str_StudLeadId != "{}") {
                    txt_leadId.setText(str_StudLeadId);
                }

                if (str_StudType != null && !str_StudType.isEmpty() && str_StudType != "" && !str_StudType.equals("anyType{}") && str_StudType != "{}") {
                    txt_studType.setText(str_StudType);
                }
*//*
                multiBarchartsInOne();

            }*/

            if(result!=null) {

                SoapPrimitive S_Status, S_ThemeName, S_Counts;
                Object O_Status, O_ThemeName, O_Counts;
                String str_Status = null, str_ThemeName = null, str_Counts = null;

                for (int i = 0; i < result.getPropertyCount(); i++) {
                    SoapObject list = (SoapObject) result.getProperty(i);

                    Log.d("finalStringssssss", list.toString());
                    SoapPrimitive indivisualObject = (SoapPrimitive) list.getProperty("Status");

                    O_Status = list.getProperty("Status");
                    if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                        S_Status = (SoapPrimitive) list.getProperty("Status");
                        Log.d("Status:", S_Status.toString());
                        str_Status = S_Status.toString();
                    }

                    if (str_Status.equalsIgnoreCase("Success")) {
                        O_ThemeName = list.getProperty("ThemeName");
                        if (!O_ThemeName.toString().equals("anyType{}") && !O_ThemeName.toString().equals(null)) {
                            S_ThemeName = (SoapPrimitive) list.getProperty("ThemeName");
                            Log.d("ThemeName", S_ThemeName.toString());
                            str_ThemeName = S_ThemeName.toString();
                        }

                        O_Counts = list.getProperty("Counts");
                        if (!O_Counts.toString().equals("anyType{}") && !O_Counts.toString().equals(null)) {
                            S_Counts = (SoapPrimitive) list.getProperty("Counts");
                            Log.d("Countsss", S_Counts.toString());
                            str_Counts = S_Counts.toString();
                        }

                        if (str_ThemeName != null && !str_ThemeName.isEmpty() && str_ThemeName != "" && !str_ThemeName.equals("anyType{}") && str_ThemeName != "{}") {
                            //txt.setText(str_ThemeName);
                            Log.d("ThemeNameIsss", str_ThemeName);
                            listThemeName.add(str_ThemeName);
                        }

                        if (str_Counts != null && !str_Counts.isEmpty() && str_Counts != "" && !str_Counts.equals("anyType{}") && str_Counts != "{}") {
                            //txt.setText(str_ThemeName);
                            Log.d("Countssissss", str_Counts);
                            listCount.add(str_Counts);
                        }

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

                multiBarchartsInOne();
            }

            progressDialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
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
        BarChart barChart = (BarChart) findViewById(R.id.barchart);

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












    private SoapObject getThemeWiseProjCount()
    {

        String METHOD_NAME = "GetProjectthemecount";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetProjectthemecount";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);


            /*request.addProperty("Username", mobilenum);
            request.addProperty("Password", leadid);*/

            /*request.addProperty("Username", leadid);
            request.addProperty("Password", mobilenum);*/

/*            String username=edt_leadid.getText().toString();
            String password=edt_mobnumber.getText().toString();*/

            request.addProperty("ManagerId", MDId);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Projects.toString().trim());
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


}
