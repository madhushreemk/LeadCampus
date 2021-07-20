package com.leadcampusapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_ContactUs extends Fragment
{


    Context context;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.activity_contact_us, container, false);

        final TextView btn_call=(TextView) view.findViewById(R.id.btn_call);

        final TextView txt_gmail=(TextView) view.findViewById(R.id.txt_gmail);

        final TextView btn_call1=(TextView) view.findViewById(R.id.btn_call1);

        final TextView txt_gmail1=(TextView) view.findViewById(R.id.txt_gmail1);

        final TextView txt_hubli_mail=(TextView) view.findViewById(R.id.txt_hubli_mail);
        final TextView txt_hubli_call=(TextView) view.findViewById(R.id.txt_hubli_call);
        final TextView txt_kakatiya_call=(TextView) view.findViewById(R.id.txt_kakatiya_call);
        final TextView txt_kakatiya_mail=(TextView) view.findViewById(R.id.txt_kakatiya_mail);
        final TextView txt_ek_call=(TextView) view.findViewById(R.id.txt_ek_call);
        final TextView txt_ek_mail=(TextView) view.findViewById(R.id.txt_ek_mail);
        final TextView txt_eklead_mail=(TextView) view.findViewById(R.id.eksoch_leadmail);
        final TextView txt_hublilead_mail=(TextView) view.findViewById(R.id.hubli_leadmail);
        final TextView txt_kakatiyalead_mail=(TextView) view.findViewById(R.id.kakatiya_leadmail);
        final TextView txt_nalgondalead_mail=(TextView) view.findViewById(R.id.nalgonda_leadmail);
        final TextView txt_nalgonda_call=(TextView) view.findViewById(R.id.txt_nalgonda_call);
        final TextView txt_nalgonda_mail=(TextView) view.findViewById(R.id.txt_nalgonda_mail);


        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+btn_call.getText().toString()));
                startActivity(intent);

             /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*/
            }
        });
        btn_call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+btn_call1.getText().toString()));
                startActivity(intent);

             /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*/
            }
        });
        txt_ek_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_ek_call.getText().toString()));
                startActivity(intent);

             /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*/
            }
        });
        txt_hubli_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_hubli_call.getText().toString()));
                startActivity(intent);

             /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*/
            }
        });
        txt_kakatiya_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_kakatiya_call.getText().toString()));
                startActivity(intent);

             /*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*/
            }
        });
        txt_nalgonda_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_nalgonda_call.getText().toString()));
                startActivity(intent);

            }
        });
        txt_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*/
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_gmail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Help Line");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "abc");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
        txt_gmail1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*/
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_gmail1.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Help Line");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "abc");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
        txt_hublilead_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_hublilead_mail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Help Line");
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });

        txt_eklead_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_eklead_mail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Help Line");
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
        txt_nalgondalead_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_nalgondalead_mail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Help Line");
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });

        txt_kakatiyalead_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_kakatiyalead_mail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Help Line");
                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });

        txt_kakatiya_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*/
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_kakatiya_mail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Kakatiya Sandbox");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "abc");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
        txt_hubli_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*/
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_hubli_mail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Hubballi Sandbox");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "abc");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
        txt_ek_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*/
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_ek_mail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead EK Soch Sandbox");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "abc");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });

        txt_nalgonda_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*/
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_nalgonda_mail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Nalgonda Sandbox");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "abc");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });







        return view;
    }


}
