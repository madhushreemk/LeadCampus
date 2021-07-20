package com.leadcampusapp;

import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;

public class ProjectStatusReport extends AppCompatActivity implements OnChartValueSelectedListener {

    private int countProposed=0;
    private int countReapply=0;
    private int countRequestForCompletion=0;
    private int countApproved=0;
    private int countRejected=0;
    private int countCompleted=0;
    private int total = 0;
    private float percentProposed=0;
    private float percentReapply=0;
    private float percentRequestForCompletion=0;
    private float percentApproved=0;
    private float percentRejected=0;
    private float percentCompleted=0;

    private int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_status_report);

        countProposed = getIntent().getIntExtra("Pending",0);
        countReapply = getIntent().getIntExtra("Reapply",0);
        countApproved = getIntent().getIntExtra("Approved",0);
        countRejected = getIntent().getIntExtra("Rejected",0);
        countRequestForCompletion = getIntent().getIntExtra("RequestForCompletion",0);
        countCompleted = getIntent().getIntExtra("Completed",0);

        Log.d("countProposed", String.valueOf(countProposed));
        Log.d("countReapply", String.valueOf(countReapply));
        Log.d("countApproved", String.valueOf(countApproved));
        Log.d("countRejected", String.valueOf(countRejected));
        Log.d("countReqstcompln", String.valueOf(countRequestForCompletion));
        Log.d("countCompleted", String.valueOf(countCompleted));

        total = countProposed + countReapply + countApproved + countRejected + countRequestForCompletion + countCompleted;

        Log.d("total is", String.valueOf(total));

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();

        if(countProposed != 0) {
            percentProposed = (total / countProposed) * 100;
            yvalues.add(new Entry(countProposed,0));
            xVals.add("Proposed");
        }
        if(countReapply!=0) {
            percentReapply = (total / countReapply ) * 100;
            yvalues.add(new Entry(countReapply, 1));
            xVals.add("Reapplied");
        }
        if(countApproved!=0) {
            percentApproved = (total / countApproved) * 100;
            yvalues.add(new Entry(countApproved, 2));
            xVals.add("Approved");
        }
        if(countRejected!=0) {
            percentRejected = (total / countRejected) * 100;
            yvalues.add(new Entry(countRejected, 3));
            xVals.add("Rejected");
        }
        if(countRequestForCompletion!=0) {
            percentRequestForCompletion = (total / countRequestForCompletion) * 100;
            yvalues.add(new Entry(countRequestForCompletion, 4));
            xVals.add("RequestForCompletion");
        }
        if(countCompleted!=0) {
            percentCompleted = (total / countCompleted) * 100;
            yvalues.add(new Entry(countCompleted, 5));
            xVals.add("Completed");
        }


        PieChart pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.


    /*    yvalues.add(new Entry(percentReapply, 1));
        yvalues.add(new Entry(percentApproved, 2));
        yvalues.add(new Entry(percentRejected, 3));
        yvalues.add(new Entry(percentRequestForCompletion, 4));
        yvalues.add(new Entry(percentCompleted, 5));*/

        PieDataSet dataSet = new PieDataSet(yvalues, "Project Overall");



        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
      //  pieChart.setDescription("This is Pie Chart");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(25f);

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.setOnChartValueSelectedListener(this);

        pieChart.animateXY(1400, 1400);

    }

   /* @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getXIndex() + ", xIndex: " + e.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }
*/
   @Override
   public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
       if (e == null)
           return;
       Log.i("VAL SELECTED",
               "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                       + ", DataSet index: " + dataSetIndex);
   }
    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }
}



