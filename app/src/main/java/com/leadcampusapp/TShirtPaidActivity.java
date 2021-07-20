package com.leadcampusapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
//import android.support.design.widget.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class TShirtPaidActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener{
    ImageButton img_eventsBack;

    public static final String  PREFBook_Stud= "prefbook_stud";
    private SharedPreferences shardpref_S_obj;
    public static final String PrefID_Role = "prefid_role";
    public String str_role = null;
    private ProgressDialog progressDialog;
    private Context context;
    private TextView txt_assignedTotal;
    private TextView txt_assignedSmall;
    private TextView txt_assignedMedium;
    private TextView txt_assignedLarge;
    private TextView txt_assignedXL;
    private TextView txt_assignedXXL;
    private TextView txt_balTotal;
    private TextView txt_balSmall;
    private TextView txt_balMedium;
    private TextView txt_balLarge;
    private TextView txt_balXL;
    private TextView txt_balXXL;
    public String str_MangerID;
    public static final String PrefID_PMID = "prefid_pmid";
    SharedPreferences shardprefPM_obj;
    public static final String  PREFBook_PM= "prefbook_pm";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tshirt_paid);


        context = TShirtPaidActivity.this;

        shardpref_S_obj=TShirtPaidActivity.this.getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_Role, "").trim();
        str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();

        shardprefPM_obj= getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);
        shardprefPM_obj.getString(PrefID_PMID, "").trim();
        str_MangerID = shardprefPM_obj.getString(PrefID_PMID, "").trim();
        Log.d("str_MangerID:",str_MangerID);

        Log.d("tag","AssignedTShirtListissss:"+str_MangerID);


        /* img_eventsBack = (ImageButton) findViewById(R.id.img_eventsBack);*/

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("TShirt Approval Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

/*
        View view1;
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.pm_tshirt_tablayout, null);

        View view2;
        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.pm_tshirt_acknowledged_tablayout, null);*/

/*        View view3;
        LayoutInflater inflater3 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view3 = inflater3.inflate(R.layout.pm_themeproject_tablayout, null);

        View view4;
        LayoutInflater inflater4 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view4 = inflater4.inflate(R.layout.pm_fundamt_tablayout, null);*/



  /*      TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));

        TabLayout.Tab selectedTab = tabLayout.getTabAt(0);
        View view = selectedTab.getCustomView();

        TextView selectedText = (TextView) view.findViewById(R.id.txt_pmTshirtReceived);
        selectedText.setTextColor(Color.parseColor("#000000"));


        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TShirtAdapter adapter = new TShirtAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_pmTshirtReceived);
                selectedText.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_pmTshirtReceived);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_pmTshirtReceived);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }
        });*/

        View view1;
        LayoutInflater inflater1 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = inflater1.inflate(R.layout.pm_tshirt_tablayout, null);

        View view2;
        LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater2.inflate(R.layout.pm_tshirt_acknowledged_tablayout, null);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));

        TabLayout.Tab selectedTab = tabLayout.getTabAt(0);
        View view = selectedTab.getCustomView();

        TextView selectedText = (TextView) view.findViewById(R.id.txt_pmTshirtReceived);
        selectedText.setTextColor(Color.parseColor("#000000"));


        //tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
        //tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final TShirtAdapter adapter = new TShirtAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_pmTshirtReceived);
                selectedText.setTextColor(Color.parseColor("#000000"));
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_pmTshirtReceived);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                TextView selectedText = (TextView) view.findViewById(R.id.txt_pmTshirtReceived);
                selectedText.setTextColor(Color.parseColor("#000000"));
            }
        });

        loadUpperTable();

    }

    private void loadUpperTable() {
        initializeAssignedBalance();

        GetAssignedTShirtlist getAssignedTShirtlist = new GetAssignedTShirtlist(context);
        getAssignedTShirtlist.execute();
    }

    private void initializeAssignedBalance() {
        txt_assignedTotal = (TextView) findViewById(R.id.txt_assignedTotal);
        txt_assignedSmall = (TextView) findViewById(R.id.txt_assignedSmall);
        txt_assignedMedium = (TextView) findViewById(R.id.txt_assignedMedium);
        txt_assignedLarge = (TextView) findViewById(R.id.txt_assignedLarge);
        txt_assignedXL = (TextView) findViewById(R.id.txt_assignedXL);
        txt_assignedXXL = (TextView) findViewById(R.id.txt_assignedXXL);

        txt_balTotal = (TextView) findViewById(R.id.txt_balTotal);
        txt_balSmall = (TextView) findViewById(R.id.txt_balSmall);
        txt_balMedium = (TextView) findViewById(R.id.txt_balMedium);
        txt_balLarge = (TextView) findViewById(R.id.txt_balLarge);
        txt_balXL = (TextView) findViewById(R.id.txt_balXL);
        txt_balXXL = (TextView) findViewById(R.id.txt_balXXL);
    }

    public class GetAssignedTShirtlist extends AsyncTask<Void, Void, SoapObject> {

        Context context;
        AlertDialog alertDialog;

        //private ProgressBar progressBar;

        GetAssignedTShirtlist (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
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
            progressDialog.setMessage("Loading");
            progressDialog.setProgressStyle(android.R.attr.progressBarStyleSmall);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(SoapObject result) {

            Log.d("Resultisssss:",result.toString());


            if(result != null) {

                SoapPrimitive S_AllotedS,S_AllotedM, S_AllotedL, S_AllotedXL, S_AllotedXXL,S_UsedS,S_UsedM,S_UsedL,S_UsedXL,S_UsedXXL,S_Status;
                Object O_AllotedS, O_AllotedM, O_AllotedL, O_AllotedXL, O_AllotedXXL, O_UsedS, O_UsedM, O_UsedL, O_UsedXL, O_UsedXXL,O_Status;
                String str_allotedS = null, str_allotedM = null, str_allotedL = null, str_allotedXL = null, str_allotedXXL = null, str_usedS = null, str_usedM = null, str_usedL = null, str_usedXL = null, str_usedXXL = null, str_status = null;



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

                        txt_assignedSmall.setText(str_allotedS);
                    }

                    O_AllotedM = result.getProperty("AllotedM");
                    if (!O_AllotedM.toString().equals("anyType{}") && !O_AllotedM.toString().equals(null)) {
                        S_AllotedM = (SoapPrimitive) result.getProperty("AllotedM");
                        Log.d("S_AllotedM:", S_AllotedM.toString());
                        str_allotedM = S_AllotedM.toString();

                        txt_assignedMedium.setText(str_allotedM);
                    }

                    O_AllotedL = result.getProperty("AllotedL");
                    if (!O_AllotedL.toString().equals("anyType{}") && !O_AllotedL.toString().equals(null)) {
                        S_AllotedL = (SoapPrimitive) result.getProperty("AllotedL");
                        str_allotedL = S_AllotedL.toString();
                        Log.d("S_AllotedL:", str_allotedL);

                        txt_assignedLarge.setText(str_allotedL);
                    }

                    O_AllotedXL = result.getProperty("AllotedXL");
                    if (!O_AllotedXL.toString().equals("anyType{}") && !O_AllotedXL.toString().equals(null)) {
                        S_AllotedXL = (SoapPrimitive) result.getProperty("AllotedXL");
                        Log.d("S_AllotedXL", S_AllotedXL.toString());
                        str_allotedXL = S_AllotedXL.toString();
                        Log.d("str_allotedXL", str_allotedXL);

                        txt_assignedXL.setText(str_allotedXL);
                    }

                    O_AllotedXXL = result.getProperty("AllotedXXL");
                    if (!O_AllotedXXL.toString().equals("anyType{}") && !O_AllotedXXL.toString().equals(null)) {
                        S_AllotedXXL = (SoapPrimitive) result.getProperty("AllotedXXL");
                        Log.d("S_AllotedXXL", S_AllotedXXL.toString());
                        str_allotedXXL = S_AllotedXXL.toString();
                        Log.d("str_allotedXXL", str_allotedXXL);

                        txt_assignedXXL.setText(str_allotedXXL);
                    }


                    O_UsedS = result.getProperty("UsedS");
                    if (!O_UsedS.toString().equals("anyType{}") && !O_UsedS.toString().equals(null)) {
                        S_UsedS = (SoapPrimitive) result.getProperty("UsedS");
                        Log.d("S_UsedS:", S_UsedS.toString());
                        str_usedS = S_UsedS.toString();

                        txt_balSmall.setText(str_usedS);
                    }

                    O_UsedM = result.getProperty("UsedM");
                    if (!O_UsedM.toString().equals("anyType{}") && !O_UsedM.toString().equals(null)) {
                        S_UsedM = (SoapPrimitive) result.getProperty("UsedM");
                        Log.d("S_UsedM:", S_UsedM.toString());
                        str_usedM = S_UsedM.toString();

                        txt_balMedium.setText(str_usedM);
                    }

                    O_UsedL = result.getProperty("UsedL");
                    if (!O_UsedL.toString().equals("anyType{}") && !O_UsedL.toString().equals(null)) {
                        S_UsedL = (SoapPrimitive) result.getProperty("UsedL");
                        Log.d("S_UsedL:", S_UsedL.toString());
                        str_usedL = S_UsedL.toString();

                        txt_balLarge.setText(str_usedL);
                    }

                    O_UsedXL = result.getProperty("UsedXL");
                    if (!O_UsedXL.toString().equals("anyType{}") && !O_UsedXL.toString().equals(null)) {
                        S_UsedXL = (SoapPrimitive) result.getProperty("UsedXL");
                        Log.d("S_UsedXL:", S_UsedXL.toString());
                        str_usedXL = S_UsedXL.toString();

                        txt_balXL.setText(str_usedXL);
                    }

                    O_UsedXXL = result.getProperty("UsedXXL");
                    if (!O_UsedXXL.toString().equals("anyType{}") && !O_UsedXXL.toString().equals(null)) {
                        S_UsedXXL = (SoapPrimitive) result.getProperty("UsedXXL");
                        Log.d("S_UsedXXL:", S_UsedXXL.toString());
                        str_usedXXL = S_UsedXXL.toString();

                        txt_balXXL.setText(str_usedXXL);
                    }

                    Integer allotedS_int=Integer.parseInt(str_allotedS);
                    Integer allotedM_int=Integer.parseInt(str_allotedM);
                    Integer allotedL_int=Integer.parseInt(str_allotedL);
                    Integer allotedXL_int=Integer.parseInt(str_allotedXL);
                    Integer allotedXXL_int=Integer.parseInt(str_allotedXXL);

                    Integer TotalAlloted_Int=allotedS_int+allotedM_int+allotedL_int+allotedXL_int+allotedXXL_int;
                    Log.i("tag","TotalAlloted_Int="+TotalAlloted_Int);
                    txt_assignedTotal.setText(TotalAlloted_Int.toString());


                    Integer UsedS_Int=Integer.parseInt(str_usedS);
                    Integer UsedM_Int=Integer.parseInt(str_usedM);
                    Integer UsedL_Int=Integer.parseInt(str_usedL);
                    Integer UsedXL_Int=Integer.parseInt(str_usedXL);
                    Integer UsedXXL_Int=Integer.parseInt(str_usedXXL);

                    Integer TotalUsed_Int=UsedS_Int+UsedM_Int+UsedL_Int+UsedXL_Int+UsedXXL_Int;
                    Log.i("tag","TotalUsed_Int="+TotalUsed_Int);
                    txt_balTotal.setText(TotalUsed_Int.toString());
                }

                else{
                    Toast.makeText(TShirtPaidActivity.this,"There is no tshirt",Toast.LENGTH_LONG).show();
                }


            }

            progressDialog.dismiss();

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
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
                final String exceptionStr = t.getMessage().toString();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
        catch(OutOfMemoryError ex){
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                }
            });
        }
        catch (Exception t) {
            Log.e("exception outside",t.getMessage().toString());
            final String exceptionStr = t.getMessage().toString();
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(context,"Web Service Error", Toast.LENGTH_LONG).show();
                }
            });
        }
        return null;

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

            AlertDialog.Builder adb = new AlertDialog.Builder(TShirtPaidActivity.this);
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
        }

/*        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        }

        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.coordinator_layout), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();*/
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem action_editProfile = menu.findItem(R.id.action_editProfile);
        action_editProfile.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent ittFeesPaidToHome = new Intent(TShirtPaidActivity.this ,PMHomeActivity.class);
            startActivity(ittFeesPaidToHome);
            return true;
        }


        if (id == R.id.action_logout) {
            Intent ittFeesToLogin = new Intent(TShirtPaidActivity.this ,LoginActivity.class);
            startActivity(ittFeesToLogin);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
