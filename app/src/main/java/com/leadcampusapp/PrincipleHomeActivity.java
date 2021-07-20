package com.leadcampusapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;

public class PrincipleHomeActivity extends AppCompatActivity {

    SharedPreferences shardprefPrinciple_info;
    SharedPreferences.Editor editor_Principle_info;
    public static final String PREBook_Principle_info="prefbook_principle_info";
    public static final String PrefID_CollgeName="prefid_CollgName";
    public static final String PrefID_AcademicId="prefid_AcademicId";
    public static final String PrefID_PMailId="prefid_PMailId";
    public static final String PrefID_PWhatsapp="prefid_PWhatsapp";
    public static final String PrefID_PMobileNo="prefid_PMobileNo";
    public static final String PrefID_PName="prefid_PName";

    public static final String  PREFBook_LoginTrack= "prefbook_logintrack";
    public static final String PrefID_WhereToGo = "prefid_wheretogo"; //
    SharedPreferences shardprefLoginTrack_obj;
    SharedPreferences.Editor editor_LoginTrack;
    Class_URL  config_obj= new Class_URL();

    TextView txt_Name,txt_CollgName,txt_MailId,txt_mobile;
    String CollegeName,MailId,WhatsApp_No,Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principle_home);


        shardprefLoginTrack_obj =this.getSharedPreferences(PREFBook_LoginTrack,Context.MODE_PRIVATE);
        editor_LoginTrack = shardprefLoginTrack_obj.edit();
        editor_LoginTrack.putString(PrefID_WhereToGo,config_obj.packagename+"PrincipleHomeActivity");
        editor_LoginTrack.commit();

        txt_Name=(TextView) findViewById(R.id.txt_Name);
        txt_CollgName=(TextView) findViewById(R.id.txt_CollgName);
        txt_MailId=(TextView) findViewById(R.id.txt_MailId);
        txt_mobile=(TextView) findViewById(R.id.txt_mobile);

        shardprefPrinciple_info=getSharedPreferences(PREBook_Principle_info, Context.MODE_PRIVATE);

        shardprefPrinciple_info.getString(PrefID_CollgeName, "").trim();
        CollegeName = shardprefPrinciple_info.getString(PrefID_CollgeName, "").trim();
        Log.e("tag","CollegeName:"+CollegeName);

        shardprefPrinciple_info.getString(PrefID_PWhatsapp, "").trim();
        WhatsApp_No = shardprefPrinciple_info.getString(PrefID_PWhatsapp, "").trim();
        Log.e("tag","WhatsApp_No:"+WhatsApp_No);

        shardprefPrinciple_info.getString(PrefID_PName, "").trim();
        Name = shardprefPrinciple_info.getString(PrefID_PName, "").trim();
        Log.e("tag","Name:"+Name);

        shardprefPrinciple_info.getString(PrefID_PMailId, "").trim();
        MailId = shardprefPrinciple_info.getString(PrefID_PMailId, "").trim();
        Log.e("tag","MailId:"+MailId);

        txt_Name.setText(Name);
        txt_CollgName.setText(CollegeName);
        txt_mobile.setText(WhatsApp_No);
        txt_MailId.setText(MailId);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new GridAdapterPrinciple(this));
        GridView gridViewSecond = (GridView) findViewById(R.id.gridview1);
        gridViewSecond.setAdapter(new GridAdapterSecond(this)); //GridAdapterSecond.java

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_pm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {


            editor_LoginTrack = shardprefLoginTrack_obj.edit();
            editor_LoginTrack.putString(PrefID_WhereToGo, config_obj.packagename+"LoginActivity");
            editor_LoginTrack.commit();

            Intent ittFeesToLogin = new Intent(PrincipleHomeActivity.this ,LoginActivity.class);
            startActivity(ittFeesToLogin);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
