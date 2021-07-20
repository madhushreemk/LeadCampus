package com.leadcampusapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class ContactUsActivity extends AppCompatActivity
{

    Class_ContactUs[] class_contactus_arrayObj;
    String str_contactus_status;
    int int_count;

    Context context;
    private ListView listView;
    String str_callno;

    public static final String  PREFBook_Stud= "prefbook_stud";
    private SharedPreferences shardpref_S_obj;
    public static final String PrefID_Role = "prefid_role";
    public String str_role = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_new);
        context=ContactUsActivity.this;

        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));

        shardpref_S_obj=ContactUsActivity.this.getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);
        shardpref_S_obj.getString(PrefID_Role, "").trim();
        str_role = shardpref_S_obj.getString(PrefID_Role, "").trim();

        View actionBarView = getSupportActionBar().getCustomView();
        TextView actionBarTV = (TextView) actionBarView.findViewById(R.id.txt_actionBar);
        actionBarTV.setText("Contact Us");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        listView = (ListView) findViewById(R.id.customlistview_contactus);

        AsyncCallWS_GetContactUsDetails task = new AsyncCallWS_GetContactUsDetails(context);
        task.execute();



       /* final TextView btn_call=(TextView) findViewById(R.id.btn_call);

        final TextView txt_gmail=(TextView) findViewById(R.id.txt_gmail);

        final TextView btn_call1=(TextView) findViewById(R.id.btn_call1);

        final TextView txt_gmail1=(TextView) findViewById(R.id.txt_gmail1);

        final TextView txt_hubli_mail=(TextView) findViewById(R.id.txt_hubli_mail);
        final TextView txt_hubli_call=(TextView) findViewById(R.id.txt_hubli_call);
        final TextView txt_kakatiya_call=(TextView) findViewById(R.id.txt_kakatiya_call);
        final TextView txt_kakatiya_mail=(TextView) findViewById(R.id.txt_kakatiya_mail);
        final TextView txt_ek_call=(TextView) findViewById(R.id.txt_ek_call);
        final TextView txt_ek_mail=(TextView) findViewById(R.id.txt_ek_mail);
        final TextView txt_eklead_mail=(TextView) findViewById(R.id.eksoch_leadmail);
        final TextView txt_hublilead_mail=(TextView) findViewById(R.id.hubli_leadmail);
        final TextView txt_kakatiyalead_mail=(TextView) findViewById(R.id.kakatiya_leadmail);
        final TextView txt_nalgondalead_mail=(TextView) findViewById(R.id.nalgonda_leadmail);
        final TextView txt_nalgonda_call=(TextView) findViewById(R.id.txt_nalgonda_call);
        final TextView txt_nalgonda_mail=(TextView) findViewById(R.id.txt_nalgonda_mail);










        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+btn_call.getText().toString()));
                startActivity(intent);

             *//*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*//*
            }
        });
        btn_call1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+btn_call1.getText().toString()));
                startActivity(intent);

             *//*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*//*
            }
        });
        txt_ek_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_ek_call.getText().toString()));
                startActivity(intent);

             *//*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*//*
            }
        });
        txt_hubli_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_hubli_call.getText().toString()));
                startActivity(intent);

             *//*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*//*
            }
        });
        txt_kakatiya_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txt_kakatiya_call.getText().toString()));
                startActivity(intent);

             *//*   Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+btn_call.getText().toString()));

                if (ActivityCompat.checkSelfPermission(ContactUsActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);*//*
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
             *//*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*//*

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
             *//*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*//*
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
             *//*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*//*
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
             *//*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*//*
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
             *//*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*//*
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
             *//*   Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, txt_gmail.getText().toString());
                //intent.setData(Uri.parse("tel:"+txt_gmail.getText().toString()));
                startActivity(intent);*//*
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + txt_nalgonda_mail.getText().toString()));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Nalgonda Sandbox");
                // emailIntent.putExtra(Intent.EXTRA_TEXT, "abc");
//emailIntent.putExtra(Intent.EXTRA_HTML_TEXT, body); //If you are using HTML in your body text

                startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
            }
        });
*/







    }// end of create







    private class AsyncCallWS_GetContactUsDetails extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog;

        Context context;

        protected void onPreExecute() {
            //  Log.i(TAG, "onPreExecute---tab2");
            dialog.setMessage("Please wait..");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Log.i(TAG, "onProgressUpdate---tab2");
        }


        @Override
        protected Void doInBackground(String... params) {
            Log.i("DFTech", "doInBackground");
            //  GetAllEvents();
            GetContactList();  // call of details
            return null;
        }

        public AsyncCallWS_GetContactUsDetails(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result)
        {

           /* if ((this.dialog != null) && this.dialog.isShowing()) {
                dialog.dismiss();

            }*/

            dialog.dismiss();

            if (class_contactus_arrayObj != null)
            {
                CustomAdapter adapter = new CustomAdapter();
                listView.setAdapter(adapter);

                int x = class_contactus_arrayObj.length;

                System.out.println("Inside the if list adapter" + x);
            } else {
                Log.d("onPostExecute", "ondutyhistoryclass_arrayObj == null");
            }


           // Toast.makeText(getApplicationContext(),""+class_contactus_arrayObj[0].getEmailid1().toString(),Toast.LENGTH_LONG).show();



            // System.out.println("Reached the onPostExecute");

        }//end of onPostExecute
    }// end Async task



    public void GetContactList() {



        String URL =Class_URL.URL_Login.toString();
        String METHOD_NAME = "GetContactUsDetails";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/GetContactUsDetails";

        try {
            SoapObject request = new SoapObject(Namespace, METHOD_NAME);
            //request.addProperty("email", username);


            Log.e("request", request.toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            try {

                androidHttpTransport.call(SOAPACTION, envelope);
                //  Log.i(TAG, "GetAllLoginDetails is running");
                //    result1 = (Vector<SoapObject>) envelope.getResponse();
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("contact response", response.toString());
                int_count = response.getPropertyCount();

                Log.e("number of rows", "" + int_count);






                class_contactus_arrayObj = new Class_ContactUs[int_count];

                SoapObject so_list = (SoapObject) response.getProperty(0);

                str_contactus_status = (String) so_list.getProperty("Status").toString();
                // <Status>Holidays not found</Status>

                if (str_contactus_status.equalsIgnoreCase("Success"))
                {

                   // listitems_arraylist.clear();
                    for (int i = 0; i < int_count; i++) {
                        SoapObject list = (SoapObject) response.getProperty(i);
                        SoapPrimitive sandbox_name, Sandbox_Address, Contact_Person, Contact_Mailid1, Contact_Number1,Contact_Mailid2;
                        String str_Contact_Mailid2;
                        String approved = "";
                        sandbox_name = (SoapPrimitive) list.getProperty("Sandbox_Name");
                        Sandbox_Address = (SoapPrimitive) list.getProperty("Sandbox_Address");
                        Contact_Person = (SoapPrimitive) list.getProperty("Contact_Person");
                        Contact_Number1 = (SoapPrimitive) list.getProperty("Contact_Number1");
                        Contact_Mailid1 = (SoapPrimitive) list.getProperty("Contact_Mailid1");
                        //Contact_Mailid2=(SoapPrimitive) list.getProperty("Contact_Mailid2");
                        if(list.getProperty("Contact_Mailid2").toString().equals("anyType{}"))
                        {
                            str_Contact_Mailid2="anyType{}";
                        }
                        else
                        { str_Contact_Mailid2=(String)list.getProperty("Contact_Mailid2").toString(); }



                        Class_ContactUs innerObj = new Class_ContactUs();

                        innerObj.setSandbox_name(sandbox_name.toString());
                        innerObj.setSandbox_address(Sandbox_Address.toString());
                        innerObj.setContact_person(Contact_Person.toString());
                        innerObj.setContact_number1(Contact_Number1.toString());
                        innerObj.setEmailid1(Contact_Mailid1.toString());
                        innerObj.setEmailid2(str_Contact_Mailid2);


                        class_contactus_arrayObj[i] = innerObj;

                        //listitems_arraylist.add(innerObj);

                    }//End of for loop
                }
            }
            catch(Throwable t){
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("contactus fail", "> " + t.getMessage());
                str_contactus_status = "error";
            }
        } catch (Throwable t) {
            Log.e("UnRegister hlist Error", "> " + t.getMessage());
            str_contactus_status = "error";

        }



    }//End of contactus




    private class Holder {
        TextView holder_sandbox_name;
        TextView holder_sandbox_address;
        TextView holder_sandbox_contactperson;
        TextView holder_sandbox_contactnumber;
        TextView holder_sandbox_emailid1;
        TextView holder_sandbox_emailid2;
        LinearLayout contact_mailid2_ll;


    }



    public class CustomAdapter extends BaseAdapter {


        public CustomAdapter() {

            super();
            Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(class_contactus_arrayObj.length);
            System.out.println("class_contactus_arrayObj.length" + x);
            return class_contactus_arrayObj.length;
        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            System.out.println("getItem position" + x);
            Log.d("getItem position", "x");
            return class_contactus_arrayObj[position];
        }


        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);
            System.out.println("getItemId position" + x);
            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final Holder holder;

            Log.d("CustomAdapter", "position: " + position);

            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.listview_contact_us, parent, false);




                holder.holder_sandbox_name = (TextView) convertView.findViewById(R.id.header_TV);
                holder.holder_sandbox_address = (TextView) convertView.findViewById(R.id.sandboxaddress_TV);  //durationofdays_TV
                holder.holder_sandbox_contactperson = (TextView) convertView.findViewById(R.id.contactperson_TV);//Fdate_TV
                holder.holder_sandbox_contactnumber = (TextView) convertView.findViewById(R.id.btn_call);//Tdate_TV
                holder.holder_sandbox_emailid1 = (TextView) convertView.findViewById(R.id.txt_gmail);//reasonforleave_TV

                holder.holder_sandbox_emailid2=(TextView) convertView.findViewById(R.id.contact_mailid2_TV);
                holder.contact_mailid2_ll=(LinearLayout) convertView.findViewById(R.id.contact_mailid2_LL);

                Log.d("Inside If convertView", "Inside If convertView");

                convertView.setTag(holder);

            } else {
                holder = (Holder) convertView.getTag();
                Log.d("Inside else convertView", "Inside else convertView");
            }

            Class_ContactUs detail = (Class_ContactUs) getItem(position);



            if (detail != null) {
                holder.holder_sandbox_name.setText(detail.getSandbox_name());
                holder.holder_sandbox_address.setText(detail.getSandbox_address());
                holder.holder_sandbox_contactperson.setText(detail.getContact_person());
                holder.holder_sandbox_contactnumber.setText(detail.getContact_number1());
                holder.holder_sandbox_emailid1.setText(detail.getEmailid1());

                if(detail.getEmailid2().toString().equalsIgnoreCase("anyType{}"))
                { holder.contact_mailid2_ll.setVisibility(View.GONE);}
                else{ holder.contact_mailid2_ll.setVisibility(View.VISIBLE);
                    holder.holder_sandbox_emailid2.setText(detail.getEmailid2());}
               //

                str_callno=holder.holder_sandbox_contactnumber.getText().toString();

                holder.holder_sandbox_contactnumber.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+holder.holder_sandbox_contactnumber.getText().toString()));
                        startActivity(intent);
                    }
                });

                holder.holder_sandbox_emailid1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + holder.holder_sandbox_emailid1));
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Lead Help Line");
                        startActivity(Intent.createChooser(emailIntent, "Chooser Title"));
                    }
                });




            }

            return convertView;

        }//End of custom getView
    }//End of CustomAdapter



















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


    /*    if (id == R.id.action_editProfile) {
            Intent ittProjDtlsToEditProfile = new Intent(PMUnapproved_DetailsActivity.this ,PMEditProfileActivity.class);
            ittProjDtlsToEditProfile.putExtra("ManagerId",ManagerId);
            startActivity(ittProjDtlsToEditProfile);
            return true;
        }
        */

        if (id == R.id.action_logout) {
            Intent ittProjDtlsToLogin = new Intent(ContactUsActivity.this ,LoginActivity.class);
            startActivity(ittProjDtlsToLogin);
            return true;
        }


            if (id == android.R.id.home) {
              /*  String fromwhere = getIntent().getStringExtra("From");
                if(fromwhere.equalsIgnoreCase("student")) {
                    Intent ittAboutToHome = new Intent(ContactUsActivity.this, HomeActivity.class);
                    startActivity(ittAboutToHome);
                }else if(fromwhere.equalsIgnoreCase("Principle")) {
                    Intent ittAboutToHome = new Intent(ContactUsActivity.this, PrincipleHomeActivity.class);
                    startActivity(ittAboutToHome);
                }else{
                    Intent ittAboutToHome = new Intent(ContactUsActivity.this, PMHomeActivity.class);
                    startActivity(ittAboutToHome);
                }*/
                if(str_role.equals("Student")) {
                    Intent ittEventsToStudHome = new Intent(ContactUsActivity.this, HomeActivity.class);
                    startActivity(ittEventsToStudHome);
                }else if(str_role.equalsIgnoreCase("Principal")){
                    Intent ittEventsToStudHome = new Intent(ContactUsActivity.this, PrincipleHomeActivity.class);
                    startActivity(ittEventsToStudHome);
                }
                else{
                    Intent ittEventsToPMHome = new Intent(ContactUsActivity.this, PMHomeActivity.class);
                    startActivity(ittEventsToPMHome);
                }
                return true;
            }




        return super.onOptionsItemSelected(item);
    }
}
