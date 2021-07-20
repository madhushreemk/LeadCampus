package com.leadcampusapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import com.covid.R;

public class Activity_setpin extends AppCompatActivity {


    public static final String sharedpreference_usercredential = "sharedpreferencebook_usercredential";
    public static final String KeyValue_userid = "KeyValue_userid";
    public static final String KeyValue_username = "KeyValue_username";
    public static final String KeyValue_user_mailid = "KeyValue_user_mailid";
    public static final String KeyValue_usercategory = "KeyValue_usercategory";
    public static final String KeyValue_usercellno = "KeyValue_usercellno";
    public static final String KeyValue_isuser_setpin = "KeyValue_isuser_setpin";


    SharedPreferences sharedpreference_usercredential_Obj;


    public static final String sharedpreference_setpincredential = "sharedpreference_pincredential";
    public static final String KeyValue_setpin = "KeyValue_setpin";
    SharedPreferences sharedpreference_setpin_Obj;
    SharedPreferences.Editor editor_obj;

    Button confirm_pin_bt;
    EditText otp1_et,otp2_et,otp3_et,otp4_et;
    TextView title_tv;
    EditText pin1masked_et,pin2masked_et,pin3masked_et,pin4masked_et;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setpin);

        confirm_pin_bt =(Button) findViewById(R.id.confirm_pin_bt);
        otp1_et=(EditText) findViewById(R.id.otp1_et);
        otp2_et=(EditText) findViewById(R.id.otp2_et);
        otp3_et=(EditText) findViewById(R.id.otp3_et);
        otp4_et=(EditText) findViewById(R.id.otp4_et);
        title_tv=(TextView)findViewById(R.id.title_tv);

        pin1masked_et=(EditText)findViewById(R.id.pin1masked_et);
        pin2masked_et=(EditText)findViewById(R.id.pin2masked_et);
        pin3masked_et=(EditText)findViewById(R.id.pin3masked_et);
        pin4masked_et=(EditText)findViewById(R.id.pin4masked_et);

        otp1_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        otp2_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        otp3_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        otp4_et.setInputType(InputType.TYPE_CLASS_NUMBER);

        sharedpreference_setpin_Obj=getSharedPreferences(sharedpreference_setpincredential, Context.MODE_PRIVATE);




        Intent myIntent = getIntent();

        if(myIntent!=null)
        {

            String str_fromconfirmoldpin="no";
            str_fromconfirmoldpin = myIntent.getStringExtra("Key_confirmoldpin");
            if(str_fromconfirmoldpin!=null && (str_fromconfirmoldpin.equalsIgnoreCase("yes")))
            {
                title_tv.setText("Set new PIN");
            }
        }




        otp1_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if(otp1_et.getText().toString().length()>=1)
                {

                    pin1masked_et.setVisibility(View.VISIBLE);
                    otp1_et.setVisibility(View.GONE);
                    pin1masked_et.setText("*");

                    otp2_et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        pin1masked_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(pin1masked_et.getText().toString().length()==0)
                {
                    pin1masked_et.setVisibility(View.GONE);
                    otp1_et.setVisibility(View.VISIBLE);
                    otp1_et.setText("");
                    otp1_et.requestFocus();
                }
            }
        });





        otp2_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if(otp2_et.getText().toString().length()>=1)
                {
                        pin2masked_et.setVisibility(View.VISIBLE);
                        otp2_et.setVisibility(View.GONE);
                        pin2masked_et.setText("*");

                        otp3_et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        pin2masked_et.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(pin2masked_et.getText().toString().length()==0)
                {
                    pin2masked_et.setVisibility(View.GONE);
                    otp2_et.setVisibility(View.VISIBLE);
                    otp2_et.setText("");
                    otp2_et.requestFocus();
                }
            }
        });



        otp3_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if(otp3_et.getText().toString().length()>=1)
                {

                        pin3masked_et.setVisibility(View.VISIBLE);
                        otp3_et.setVisibility(View.GONE);
                        pin3masked_et.setText("*");

                    otp4_et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        pin3masked_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(pin3masked_et.getText().toString().length()==0)
                {
                    pin3masked_et.setVisibility(View.GONE);
                    otp3_et.setVisibility(View.VISIBLE);
                    otp3_et.setText("");
                    otp3_et.requestFocus();
                }
            }
        });





        otp4_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

                if(otp4_et.getText().toString().length()>=1)
                {

                    pin4masked_et.setVisibility(View.VISIBLE);
                    otp4_et.setVisibility(View.GONE);
                    pin4masked_et.setText("*");

                    otp4_et.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });



    pin4masked_et.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable)
        {

            if(pin4masked_et.getText().toString().length()==0)
            {
                pin4masked_et.setVisibility(View.GONE);
                otp4_et.setVisibility(View.VISIBLE);
                otp4_et.setText("");
                otp4_et.requestFocus();
            }
        }
    });





        confirm_pin_bt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(validation())
                {

                    String str_setpin=otp1_et.getText().toString()+
                            otp2_et.getText().toString()+
                            otp3_et.getText().toString()+
                            otp4_et.getText().toString();


                    editor_obj = sharedpreference_setpin_Obj.edit();
                    editor_obj.putString(KeyValue_setpin,str_setpin);
                    editor_obj.commit();

                    Intent i = new Intent(Activity_setpin.this, Activity_confirmpin.class);
                    startActivity(i);
                    finish();
                }
            }
        });


    }


    public boolean validation()
    {
        boolean b_otp1, b_otp2, b_otp3, b_otp4;
        b_otp1=b_otp2=b_otp3=b_otp4=true;



        if (otp1_et.getText().toString().trim().length() == 0) {
            otp1_et.setError("Enter PIN");
            otp1_et.requestFocus();
            b_otp1 = false;
        }

        if (otp2_et.getText().toString().trim().length() == 0) {
            otp2_et.setError("Enter PIN");
            otp2_et.requestFocus();
            b_otp2 = false;
        }

        if (otp3_et.getText().toString().trim().length() == 0) {
            otp3_et.setError("Enter PIN");
            otp3_et.requestFocus();
            b_otp3 = false;
        }
        if(otp4_et.getText().toString().trim().length()==0)
        {
            otp4_et.setError("Enter PIN");
            otp4_et.requestFocus();
            b_otp4=false;
        }

        return (b_otp1 && b_otp2 && b_otp3&& b_otp4);
    }



    @Override
    public void onBackPressed()
    {

        /*AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_setpin.this);
        dialog.setCancelable(false);
        dialog.setTitle(R.string.alert);
        dialog.setMessage("Are you sure want to Exit");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                finish();
                System.exit(0);

            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Action for "Cancel".
                        dialog.dismiss();
                    }
                });

        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();*/
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }


}