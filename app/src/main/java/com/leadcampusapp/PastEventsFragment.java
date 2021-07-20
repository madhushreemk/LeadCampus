package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

//import com.android.sripad.leadnew_22_6_2018.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Vector;

public class PastEventsFragment extends Fragment
{

    private LinearLayout pasteventsfragment_ViewLayout;
    Context context;

    private String serverPath = Class_URL.ServerPath.toString().trim();

    byte[] imageBytes={0};

    int noOfobjects = 0;
    int noOfPastObject;
    int count1;
    ImageView Image1;
    byte[] imageInByte;
    Bitmap mIcon11;


    byte[] x;
    Class_PastEvents[] class_pastevents_arrayObj,class_pastevents_arrayObj1;
    private ListView listView;
    Bitmap bitmap1;

    Class_PastEvents class_passtevents_obj;



    String str_pastevent,str_imageurl,str_applyurl,str_pasturlevent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        pasteventsfragment_ViewLayout = (LinearLayout) inflater.inflate(R.layout.pastevents_fragment, container, false);

        context =pasteventsfragment_ViewLayout.getContext();

        listView = (ListView) pasteventsfragment_ViewLayout.findViewById(R.id.pastevent_listview);

        // Getting application context
        context = getActivity();

        AsyncCallWS_pastevents task = new AsyncCallWS_pastevents(context);
        task.execute();
        return pasteventsfragment_ViewLayout;

    }//end of Oncreate







private class AsyncCallWS_pastevents extends AsyncTask<String, Void, Void> {
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
        Log.i("LeadMIS", "doInBackground");
        //  GetAllEvents();

        PastEvents();  // call of past events direct hit


        return null;
    }

    public AsyncCallWS_pastevents(Context context1) {
        context = context1;
        dialog = new ProgressDialog(context1);
    }

    @Override
    protected void onPostExecute(Void result)
    {
        dialog.dismiss();

        System.out.println("Reached the onPostExecute");

        if (class_pastevents_arrayObj != null)
        {
            CustomAdapter adapter = new CustomAdapter();
            listView.setAdapter(adapter);

            int x1 = class_pastevents_arrayObj.length;

            System.out.println("Inside the if list adapter" + x1);
        } else {
            Log.d("onPostExecute", "leavelist == null");
        }


    }//end of onPostExecute
}// end Async task






    public void PastEvents()
    {
        Vector<SoapObject> result1 = null;

        //URL
        String URL = Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "GetEvents";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/GetEvents";
        //URL


        try {

            SoapObject request = new SoapObject(Namespace, METHOD_NAME);

            // request.addProperty("currentevents", "0");


            /*<EventId>long</EventId>
<EventName>string</EventName>
<EventFromDate>string</EventFromDate>
<EventToDate>string</EventToDate>
<EventDescription>string</EventDescription>
<EventApplyURL>string</EventApplyURL>
<EventURL>string</EventURL>
<Image_Path>string</Image_Path>
 <EventStatus>int</EventStatus>
<Status>string</Status>*/



            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAPACTION, envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                //Log.e("pastevents response", response.toString());
                count1 = response.getPropertyCount();  // number of count in array in response 6,0-5,5
                noOfobjects = count1;
                Log.e("Events","events count"+Integer.toString(noOfobjects));


                noOfPastObject=0;
                for (int i = 0; i < count1; i++)
                {
                    SoapObject soapobject_response = (SoapObject) response.getProperty(i);
                    SoapPrimitive soapobject_pastorcurrent;
                    soapobject_pastorcurrent= (SoapPrimitive) soapobject_response.getProperty("EventStatus");
                    if(soapobject_pastorcurrent.toString().equals("0"))
                    {  noOfPastObject++;}
                }



                Log.e("pasteventcount","noOfPastObject"+noOfPastObject);
                class_pastevents_arrayObj= new Class_PastEvents[noOfPastObject];
                for (int i = 0,j=0; i < count1; i++)
                {
                    SoapObject soapobject_response = (SoapObject) response.getProperty(i);
                    SoapPrimitive soapobject_eventapplyurl, soapobject_pasteventurl, soapobject_imageURL,soapobject_pastevent,soapobject_status;
                    String str_soapimageURL;

                   /* soapobject_eventapplyurl=(SoapPrimitive) soapobject_response.getProperty("EventApplyURL");//
                    soapobject_eventurl=(SoapPrimitive)soapobject_response.getProperty("EventURL"); //imageclickURL
                    soapobject_imageURL =(SoapPrimitive) soapobject_response.getProperty("Image_Path");*/
                    soapobject_pastevent= (SoapPrimitive) soapobject_response.getProperty("EventStatus");
                    soapobject_status  = (SoapPrimitive) soapobject_response.getProperty("Status");
                    str_soapimageURL =(String) soapobject_response.getProperty("Image_Path").toString();

                    soapobject_pasteventurl=(SoapPrimitive)soapobject_response.getProperty("EventURL"); //imageclickURL


             if(soapobject_pastevent.toString().equals("0"))
             {
                 Class_PastEvents innerObj = new Class_PastEvents();
                   /* innerObj.setEventApplyURL(soapobject_eventapplyurl.toString());
                    innerObj.setEventURL(soapobject_eventurl.toString());*/
                 innerObj.setPastorCurrent(soapobject_pastevent.toString());
                 innerObj.setStatus(soapobject_status.toString());
                 innerObj.setPastEventURL(soapobject_pasteventurl.toString());


                 String arr[] = str_soapimageURL.split("/", 2);

                 String firstWord = arr[0];   //the
                 String secondWord = arr[1];

                 String Str_actualImgUrl = serverPath + secondWord;
                 Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                 Log.e("student URL", "" + Str_actualImgUrl);

                 // java.net.URL url = new URL(Str_actualImgUrl.toString().trim());

                    /*try {
                        InputStream in = new java.net.URL(Str_actualImgUrl).openStream();
                        mIcon11 = BitmapFactory.decodeStream(in);
                        Log.e("Error Image conversion", "e.getMessage()");
                    } catch (Exception e) {
                        Log.e("Error Image conversion", e.getMessage());
                        e.printStackTrace();
                    }*/

                 java.net.URL url = new URL(Str_actualImgUrl.toString().trim());
                 mIcon11 = BitmapFactory.decodeStream(url.openConnection().getInputStream());


                 ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                 mIcon11.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
                 imageInByte = stream1.toByteArray();

                 innerObj.setEventImage(imageInByte);

                 class_pastevents_arrayObj[j] = innerObj;
                 j++;

             }

                }//end of For Loop




            } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Log.e("request fail Past", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }





    }//end of newsandEvents




    //Holder for customAdapter
    private class Holder {
        ImageView holder_image;
        /*TextView holder_imageclickurl_tv;
        Button holder_applybutton_bt;
        TextView holder_applybuttonurl_tv;*/
        TextView holder_passevent_tv;
        TextView holder_nopastevents_tv;
        TextView holder_pastevent_imageurl_tv;
    }
    //End of Holder


    public class CustomAdapter extends BaseAdapter
    {


        public CustomAdapter() {

            super();
            Log.d("Inside CustomAdapter()", "Inside CustomAdapter()");
        }

        @Override
        public int getCount() {

            String x = Integer.toString(class_pastevents_arrayObj.length);
            Log.d("class_arrayObj.length", x);
            return class_pastevents_arrayObj.length;

        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            System.out.println("getItem position" + x);
            Log.d("getItem position", "x");
            return class_pastevents_arrayObj[position];
        }

        @Override
        public long getItemId(int position) {
            String x = Integer.toString(position);
            System.out.println("getItemId position" + x);
            Log.d("getItemId position", x);
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            final Holder holder;

            Log.d("CustomAdapter", "position: " + position);
            // holder = new Holder();
            if (convertView == null) {
                holder = new Holder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.row_item_pastevents, parent, false);

               /* holder.date = (TextView) convertView.findViewById(R.id.approved_on2);
                holder.days = (TextView) convertView.findViewById(R.id.days2);  //durationofdays_TV
                holder.fromView = (TextView) convertView.findViewById(R.id.from2);//Fdate_TV
                holder.toView = (TextView) convertView.findViewById(R.id.to2);//Tdate_TV
                holder.reason = (TextView) convertView.findViewById(R.id.reason2);//
                holder.status = (TextView) convertView.findViewById(R.id.status2);//statusofleave_TV
                */

                // holder.cancel = (TextView) convertView.findViewById(R.id.cancelLeave_TV);

                //Typeface facebold = Typeface.createFromAsset(getResources().getAssets(), "fonts/centurygothic.ttf");
                //durationlabel_TV

                holder.holder_image=(ImageView) convertView.findViewById(R.id.pasteventsimagedisplay_IV);
                //holder_passevent_tv
                holder.holder_nopastevents_tv=(TextView) convertView.findViewById(R.id.nopastevent_TV);
                holder.holder_pastevent_imageurl_tv =(TextView)convertView.findViewById(R.id.pastevent_imageurl_TV);
                Log.d("Inside If convertView", "Inside If convertView");

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
                Log.d("Inside else convertView", "Inside else convertView");
            }



             class_passtevents_obj =(Class_PastEvents) getItem(position);

            // Class_CurrentEvents[] details =(Class_CurrentEvents) getItem(position);



            if(class_passtevents_obj!=null)
            {



                x = class_passtevents_obj.getEventImage();
                str_pastevent =class_passtevents_obj.getPastorCurrent();
                str_pasturlevent =class_passtevents_obj.getPastEventURL();
               /* str_imageurl = detail.getEventURL();
                Log.e("imageURL","URL"+str_imageurl);
                str_applyurl= detail.getEventApplyURL();*/
                if(x!=null)
                {
                    if(str_pastevent.equals("0"))
                    {
                       // holder.holder_image.setVisibility(View.VISIBLE);
                        bitmap1 = BitmapFactory.decodeByteArray(x, 0, x.length);
                        holder.holder_image.setImageBitmap(bitmap1);
                        holder.holder_pastevent_imageurl_tv.setText(str_pasturlevent);


                        holder.holder_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                try {

                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.holder_pastevent_imageurl_tv.getText().toString()));
                                    startActivity(browserIntent);
                                }
                                catch (Exception e)
                                {
                                    //   Toast.makeText(getActivity(),"WS:error",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }//end of pastorcurrent
                } else
                {


                    //holder.holder_image.setVisibility(View.GONE);

                    //  holder.holder_nopastevents_tv.setVisibility(View.VISIBLE);
                   // Toast.makeText(getActivity(),"No Past",Toast.LENGTH_SHORT).show();
                }

            }
            else{
                holder.holder_image.setVisibility(View.GONE);
               // holder.holder_nopastevents_tv.setVisibility(View.VISIBLE);
                //Toast.makeText(getActivity(),"No Past",Toast.LENGTH_SHORT).show();
            }




            return convertView;


        }


    }//End of CustomAdapter
















}//end of class








