package com.leadcampusapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

public class Class_alert_msg
{
    private final Context mContext;

    private FragmentManager fragmentManager;

    public Class_alert_msg(Context context) {
        this.mContext = context;

    }






    public  static void alerts_dialog(String str_error,String ws,Context context)
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle("LeadMIS");
        dialog.setMessage("End Date should be after Start Date");

        Activity activity = (Activity) context;
       // fragmentManager = activity.getFragmentManager();


        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.frame_container, new Fragment_RHolidays());
                fragmentTransaction.commit();*/

                dialog.dismiss();

            }
        });




        final AlertDialog alert = dialog.create();
        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
            }
        });
        alert.show();


    }



    public  void alerts_dialog2(final Context context, final Class classname)
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setCancelable(false);
        dialog.setTitle("LEADCampus");
        dialog.setMessage("Are you sure you want to leave this page?");

        final Activity activity = (Activity) context;
        fragmentManager = activity.getFragmentManager();

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                /*FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.frame_container, new Fragment_RHolidays());
                fragmentTransaction.commit();*/



                 Intent lauchactivity = new Intent(context ,classname);
            context.startActivity(lauchactivity);
                ((Activity) context).finish();




            }
        });




        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i)
            {
                dialog.dismiss();
            }
        });

        final AlertDialog alert = dialog.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#004D40"));
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#ff0000"));
            }
        });
        alert.show();


    }






}
