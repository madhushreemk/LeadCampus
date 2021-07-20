package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class ChatsActivity extends AppCompatActivity implements OnChartValueSelectedListener,ConnectivityReceiver.ConnectivityReceiverListener {

    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);

        Intent intent = getIntent();
        String UnapprovedCount=intent.getStringExtra("UnapprovedCount");
        String ComplitedCount=intent.getStringExtra("ComplitedCount");
        String ApprovedCount=intent.getStringExtra("ApprovedCount");
        String RequestForCompletion=intent.getStringExtra("RequestForCompletion");
        String RequestForModification=intent.getStringExtra("RequestForModification");
        String Rejected=intent.getStringExtra("Rejected");
        String str_studentcount=intent.getStringExtra("str_studentcount");
        String str_collegecount=intent.getStringExtra("str_collegecount");

        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        PieChart pieChart2 = (PieChart) findViewById(R.id.piechart2);
        pieChart.setUsePercentValues(true);
        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        Float unapproved_float=Float.valueOf(UnapprovedCount).floatValue();
        Float complited_float=Float.valueOf(ComplitedCount).floatValue();
        Float approved_float=Float.valueOf(ApprovedCount).floatValue();
        Float requestforcomplition_float=Float.valueOf(RequestForCompletion).floatValue();
        Float requestformodification_float=Float.valueOf(RequestForModification).floatValue();
        Float rejected_float=Float.valueOf(Rejected).floatValue();
        Float collegecount_float=Float.valueOf(str_collegecount).floatValue();

        Float studentcount_float=Float.valueOf(str_studentcount).floatValue();

       /* ArrayList<PieEntry> yvalues = new ArrayList<PieEntry>();
        ArrayList<String> xVals = new ArrayList<String>();

        if(unapproved_float!=0) {
            yvalues.add(new PieEntry(unapproved_float, "Proposed"));
            xVals.add("Proposed");
        }
        if(complited_float!=0) {
            yvalues.add(new PieEntry(complited_float, "Complited"));
            xVals.add("Complited");
        }
        if(approved_float!=0) {
            yvalues.add(new PieEntry(approved_float, "Approved"));
            xVals.add("Approved");
        }
        if(requestforcomplition_float!=0) {
            yvalues.add(new PieEntry(requestforcomplition_float, "Completion"));
            xVals.add("Completion");
        }
        if(requestformodification_float!=0) {
            yvalues.add(new PieEntry(requestformodification_float, "Modification"));
            xVals.add("Modification");
        }
        if(rejected_float!=0) {
            yvalues.add(new PieEntry(rejected_float, "Rejected"));
            xVals.add("Rejected");
        }*/
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        if(unapproved_float!=0) {
            yvalues.add(new Entry(unapproved_float, 0));
            xVals.add("Proposed");
        }
        if(complited_float!=0) {
            yvalues.add(new Entry(complited_float, 1));
            xVals.add("Complited");
        }
        if(approved_float!=0) {
            yvalues.add(new Entry(approved_float, 2));
            xVals.add("Approved");
        }
        if(requestforcomplition_float!=0) {
            yvalues.add(new Entry(requestforcomplition_float, 3));
            xVals.add("Completion");
        }
        if(requestformodification_float!=0) {
            yvalues.add(new Entry(requestformodification_float, 4));
            xVals.add("Modification");
        }
        if(rejected_float!=0) {
            yvalues.add(new Entry(rejected_float, 5));
            xVals.add("Rejected");
        }


      /*  yvalues.add(new Entry(unapproved_float, 0));
        yvalues.add(new Entry(complited_float, 1));
        yvalues.add(new Entry(approved_float, 2));
        yvalues.add(new Entry(requestforcomplition_float, 3));
        yvalues.add(new Entry(requestformodification_float, 4));
        yvalues.add(new Entry(rejected_float, 5));
*/
        PieDataSet dataSet = new PieDataSet(yvalues, "");


     /*   xVals.add("Proposed");
        xVals.add("Complited");
        xVals.add("Approved");
        xVals.add("Completion");
        xVals.add("Modification");
        xVals.add("Rejected");*/

        PieData data = new PieData(xVals,dataSet);
        // In Percentage term
        data.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setData(data);
        pieChart.setDescription("Project Counts");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);

        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateXY(1400, 1400);

      /*  BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(unapproved_float, 0));
        entries.add(new BarEntry(complited_float, 1));
        entries.add(new BarEntry(approved_float, 2));
        entries.add(new BarEntry(requestforcomplition_float, 3));
        entries.add(new BarEntry(requestformodification_float, 4));
        entries.add(new BarEntry(rejected_float, 5));



        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Proposed");
        labels.add("Complited");
        labels.add("Approved");
        labels.add("Completion");
        labels.add("Modification");
        labels.add("Rejected");*/

      /*  if(unapproved_float!=0) {
            entries.add(new BarEntry(unapproved_float, count++));
            labels.add("Proposed");
        }
        if(complited_float!=0) {
            entries.add(new BarEntry(complited_float, count++));
            labels.add("Complited");
        }
        if(approved_float!=0) {
            entries.add(new BarEntry(approved_float, count++));
            labels.add("Approved");
        }
        if(requestforcomplition_float!=0) {
            entries.add(new BarEntry(requestforcomplition_float, count++));
            labels.add("Completion");
        }
        if(requestformodification_float!=0) {
            entries.add(new BarEntry(requestformodification_float, count++));
            labels.add("Modification");
        }
        if(rejected_float!=0) {
            entries.add(new BarEntry(rejected_float, count++));
            labels.add("Rejected");
        }*/

        /*BarDataSet bardataset = new BarDataSet(entries, "Cells");

        BarData data1 = new BarData(labels, bardataset);
        barChart.setData(data1); // set the data and list of lables into chart

      //  barChart.setDescription("Set Bar Chart Description");  // set the description

        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        barChart.animateY(5000);*/
//----------------------------------------------------------------------------------------------

        ArrayList<Entry> yvalues2 = new ArrayList<Entry>();
        ArrayList<String> xVals2 = new ArrayList<String>();

        yvalues2.add(new Entry(studentcount_float, 0));
        yvalues2.add(new Entry(collegecount_float,1));
        PieDataSet dataSet2 = new PieDataSet(yvalues2, "");

        xVals2.add("No of Students");
        xVals2.add("No of Colleges");

     /*   xVals.add("Proposed");
        xVals.add("Complited");
        xVals.add("Approved");
        xVals.add("Completion");
        xVals.add("Modification");
        xVals.add("Rejected");*/

        PieData data2 = new PieData(xVals2, dataSet2);

        data2.setValueFormatter(new PercentFormatter());
        // Default value
        //data.setValueFormatter(new DefaultValueFormatter(0));
        pieChart2.setData(data2);
     //   pieChart2.setDescription("Student Counts");

        pieChart2.setDrawHoleEnabled(true);
        pieChart2.setTransparentCircleRadius(25f);
        pieChart2.setHoleRadius(25f);

        dataSet2.setColors(ColorTemplate.JOYFUL_COLORS);
        data2.setValueTextSize(10f);
        data2.setValueTextColor(Color.DKGRAY);
        pieChart2.setOnChartValueSelectedListener(this);

        pieChart2.animateXY(1400, 1400);

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

            AlertDialog.Builder adb = new AlertDialog.Builder(ChatsActivity.this);
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

            //finish();
        }

    }














    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
        if(e.getXIndex()==0){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",0);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getXIndex()==2){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",1);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getXIndex()==1){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",3);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getXIndex()==3){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",2);
            startActivity(ittEditProjToProjStatus);
        }
    }

  /*  @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
        if(e.getX()==0){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",0);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getX()==2){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",1);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getX()==1){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",3);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getX()==3){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",2);
            startActivity(ittEditProjToProjStatus);
        }
    }
*/
    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
  /*  @Override
    public void onValueSelected(Entry e, int dataSetIndex) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
        if(e.getXIndex()==0){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",0);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getXIndex()==2){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",1);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getXIndex()==1){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",3);
            startActivity(ittEditProjToProjStatus);
        }
        if(e.getXIndex()==3){
            Intent ittEditProjToProjStatus = new Intent(ChatsActivity.this,PMProjectDetailActivity.class);
            ittEditProjToProjStatus.putExtra("pageCount",2);
            startActivity(ittEditProjToProjStatus);
        }
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }*/
}
