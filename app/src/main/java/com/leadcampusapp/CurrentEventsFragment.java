package com.leadcampusapp;

/**
 * Created by Admin on 20-07-2018.
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
//import androidx.appcompat.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;

public class CurrentEventsFragment extends Fragment
{

    private LinearLayout currenteventsfragment_ViewLayout;
    Context context;



    int noOfobjects = 0;
    int noOfCurrentObject;
    int count1;
    ImageView Image1;
    byte[] imageInByte;
    Bitmap mIcon11;


    byte[] x;
    Bitmap bitmap1;

    Class_CurrentEvents[] class_currentevents_arrayObj,class_currentevents_arrayObj1;
    private ListView listView;

    private String serverPath = Class_URL.ServerPath.toString().trim();

    byte[] imageBytes={0};
    String Str_error1,Str_error2;

    String str_pastorcurrent,str_imageurl,str_applyurl,str_fromdate,str_todate;
    Class_CurrentEvents detail;



    ArrayList<Class_CurrentEvents> listitems_Image_arraylist = new ArrayList<>();
    private static RecyclerView.Adapter adapter_recycler;
    RecyclerView myrecyclerview_view;
    LinearLayoutManager MyLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
       /* currenteventsfragment_ViewLayout = (LinearLayout) inflater.inflate(R.layout.currentevents_fragment, container, false);
        context =currenteventsfragment_ViewLayout.getContext();
        listView = (ListView) currenteventsfragment_ViewLayout.findViewById(R.id.currentevent_listview);
*/



        View view = inflater.inflate(R.layout.currentevents_fragmentcard, container, false);
        myrecyclerview_view = (RecyclerView) view.findViewById(R.id.cardView);

        Str_error1=Str_error2="no";


        myrecyclerview_view.setHasFixedSize(true);
        MyLayoutManager = new LinearLayoutManager(getActivity());

        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myrecyclerview_view.setLayoutManager(MyLayoutManager);



       /* Display display;
        display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
*/
        // Getting application context
        context = getActivity();

        AsyncCallWS_currentevents task = new AsyncCallWS_currentevents(context);
        task.execute();





        //return currenteventsfragment_ViewLayout;
        return view;
    }


    private class AsyncCallWS_currentevents extends AsyncTask<String, Void, Void> {
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

            CurrentEvents();  // call of current events direct hit


            return null;
        }

        public AsyncCallWS_currentevents(Context context1) {
            context = context1;
            dialog = new ProgressDialog(context1);
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();



            if(Str_error1.equals("no")||Str_error2.equals("no"))
            {
                if (class_currentevents_arrayObj != null)
                {
                   /* CustomAdapter adapter = new CustomAdapter();
                    listView.setAdapter(adapter);*/


                    adapter_recycler = new MyAdapter_Card(listitems_Image_arraylist);
                   myrecyclerview_view.setAdapter(adapter_recycler);


                    int x1 = class_currentevents_arrayObj.length;

                    System.out.println("Inside the if list adapter" + x1);
                } else {
                    Log.d("onPostExecute", "leavelist == null");
                }
            }
            else
            {
                Toast.makeText(getActivity(), "Error: WS", Toast.LENGTH_SHORT).show();
            }

        }//end of OnPostExecute

    }// end Async task




    public void CurrentEvents()
    {


        //URL

        String URL = Class_URL.URL_Projects.toString().trim();
        String METHOD_NAME = "GetEvents";
        String Namespace = "http://mis.leadcampus.org/", SOAPACTION = "http://mis.leadcampus.org/GetEvents";
        //URL

            //Toast.makeText(getActivity(),"test image",Toast.LENGTH_SHORT).show();

        try {
            // String  versioncode = this.getPackageManager()
            //        .getPackageInfo(this.getPackageName(), 0).versionName;
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


//E/currentevents response: anyType{vmEvent=anyType{EventId=0; EventStatus=0; Status=Invalid  Project status; }; }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            try {
                androidHttpTransport.call(SOAPACTION, envelope);
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.e("currentevents response", response.toString());
                count1 = response.getPropertyCount();  // number of count in array in response 6,0-5,5

                Log.e("number current rows", "" + count1);

                noOfobjects = count1;
                noOfobjects = 1;
                Log.e("Events","events count"+Integer.toString(noOfobjects));



                noOfCurrentObject=0;
                for (int i = 0; i < count1; i++)
                {
                    SoapObject soapobject_response = (SoapObject) response.getProperty(i);
                    SoapPrimitive soapobject_pastorcurrent;
                    soapobject_pastorcurrent= (SoapPrimitive) soapobject_response.getProperty("EventStatus");
                    if(soapobject_pastorcurrent.toString().equals("1"))
                    {  noOfCurrentObject++; }
                }


               class_currentevents_arrayObj= new Class_CurrentEvents[noOfCurrentObject];

                //class_currentevents_arrayObj= new Class_CurrentEvents[0];
                listitems_Image_arraylist.clear();
                for (int i = 0,j=0; i < count1; i++)
                {
                    SoapObject soapobject_response = (SoapObject) response.getProperty(i);
                    SoapPrimitive soapobject_eventapplyurl, soapobject_eventurl, soapobject_imageURL,soapobject_pastorcurrent,soapobject_status,soapprimitive_fromdate,soapprimitive_todate;
                    String str_soapimageURL,str_status,str_fromdate,str_todate;
                    soapobject_eventapplyurl=(SoapPrimitive) soapobject_response.getProperty("EventApplyURL");//
                    soapobject_eventurl=(SoapPrimitive)soapobject_response.getProperty("EventURL"); //imageclickURL
                    soapobject_imageURL =(SoapPrimitive) soapobject_response.getProperty("Image_Path");
                    soapobject_pastorcurrent= (SoapPrimitive) soapobject_response.getProperty("EventStatus");
                    soapobject_status  = (SoapPrimitive) soapobject_response.getProperty("Status");
                    str_soapimageURL =(String) soapobject_response.getProperty("Image_Path").toString();
                    soapprimitive_fromdate =(SoapPrimitive)soapobject_response.getProperty("EventFromDate");//<EventFromDate>
                    soapprimitive_todate=(SoapPrimitive)soapobject_response.getProperty("EventToDate");//<EventToDate>

                if(soapobject_pastorcurrent.toString().equals("1")) {
                 //   Log.e("Soap status"," "+soapobject_pastorcurrent.toString());
                    Class_CurrentEvents innerObj = new Class_CurrentEvents();
                    innerObj.setEventApplyURL(soapobject_eventapplyurl.toString());
                    innerObj.setEventURL(soapobject_eventurl.toString());
                    innerObj.setPastorCurrent(soapobject_pastorcurrent.toString());
                    innerObj.setStatus(soapobject_status.toString());
                    innerObj.setFromDate(soapprimitive_fromdate.toString());
                    innerObj.setTodate(soapprimitive_todate.toString());


                    String arr[] = str_soapimageURL.split("/", 2);

                    String firstWord = arr[0];   //the
                    String secondWord = arr[1];

                    String Str_actualImgUrl = serverPath + secondWord;
                   // Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                   // Log.e("student URL", "" + Str_actualImgUrl);

                    // java.net.URL url = new URL(Str_actualImgUrl.toString().trim());

                    /*try {
                        InputStream in = new java.net.URL(Str_actualImgUrl).openStream();
                        mIcon11 = BitmapFactory.decodeStream(in);
                        Log.e("Error Image conversion", "e.getMessage()");
                    } catch (Exception e) {
                        Log.e("Error Image conversion", e.getMessage());
                        e.printStackTrace();
                    }*/

                    URL url = new URL(Str_actualImgUrl.toString().trim());
                    mIcon11 = BitmapFactory.decodeStream(url.openConnection().getInputStream());


                    ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
                    mIcon11.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
                    imageInByte = stream1.toByteArray();

                    innerObj.setEventImage(imageInByte);

                    class_currentevents_arrayObj[j] = innerObj;
                    j++;
                    listitems_Image_arraylist.add(innerObj);
                }

                }//end of For Loop




                } catch (Throwable t) {
                //Toast.makeText(MainActivity.this, "Request failed: " + t.toString(),
                //    Toast.LENGTH_LONG).show();
                Str_error1 ="yes";
                Log.e("request fail", "> " + t.getMessage());
            }
        } catch (Throwable t) {
            Str_error2="yes";
            Log.e("UnRegister  Error", "> " + t.getMessage());

        }

    }//end of newsandEvents







//----------------------------------------------------------------------------------------------------------------------------



    public class MyAdapter_Card extends RecyclerView.Adapter<MyViewHolder_X>
    {
        private ArrayList<Class_CurrentEvents> list;

        public MyAdapter_Card(ArrayList<Class_CurrentEvents> Data)
        {
            list = Data;
        }

        @Override
        public MyViewHolder_X onCreateViewHolder(ViewGroup parent, int viewType)
        {
            // create a new view
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_event, parent, false);
            MyViewHolder_X holder = new MyViewHolder_X(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder_X holder, int position)
        {




           // Log.e("listvalue", String.valueOf(list.get(position).getImageResourceId()));
           /* holder.coverImageView.setImageResource(list.get(position).getImageResourceId());
            holder.coverImageView.setTag(list.get(position).getImageResourceId());
            */

            x = list.get(position).getEventImage();
            str_pastorcurrent =list.get(position).getPastorCurrent();
            str_imageurl = list.get(position).getEventURL();
            // Log.e("imageURL","URL"+str_imageurl);
            str_applyurl= list.get(position).getEventApplyURL();
            str_fromdate=list.get(position).getFromDate();
            str_todate=list.get(position).getTodate();

            if(!listitems_Image_arraylist.isEmpty())
            {
                if (str_pastorcurrent.equals("1"))
                {

                    holder.holder_coverImageView.setVisibility(View.VISIBLE);
                    holder.holder_applybuttonc_bt.setVisibility(View.VISIBLE);

                    bitmap1 = BitmapFactory.decodeByteArray(x, 0, x.length);
                    holder.holder_coverImageView.setImageBitmap(bitmap1);

                    holder.holder_imageclickurlc_tv.setText(str_imageurl);
                    holder.holder_applybuttonurlc_tv.setText(str_applyurl);
                    holder.holder_fromdate_tv.setText(str_fromdate);
                    holder.holder_todate_tv.setText(str_todate);

                    holder.holder_coverImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //Toast.makeText(getActivity(),"imageurl"+str_imageurl,Toast.LENGTH_SHORT).show();
                            try{

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.holder_imageclickurlc_tv.getText().toString()));
                            startActivity(browserIntent);
                            }
                            catch (Exception e)
                            {
                             //   Toast.makeText(getActivity(),"WS:error",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                    if(str_applyurl.trim().length()==0)
                    {
                        holder.holder_applybuttonc_bt.setVisibility(View.GONE);
                    }else{

                        holder.holder_applybuttonc_bt.setVisibility(View.VISIBLE);
                    }

                    holder.holder_applybuttonc_bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            try {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.holder_applybuttonurlc_tv.getText().toString()));
                                startActivity(browserIntent);
                                //Toast.makeText(getActivity(),"imageurl"+str_applyurl,Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e)
                            {
                                Toast.makeText(getActivity(),"WS:error",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



                }
            }//end of if statement

            else
            {

            }

            /*holder.coverImageView.setTag(list.get(position).getImageResourceId());
            holder.likeImageView.setTag(R.drawable.ic_like);*/

        }

        @Override
        public int getItemCount() {
            Log.e("listsize", String.valueOf(list.size()));
            return list.size();

        }
    }

    public class MyViewHolder_X extends RecyclerView.ViewHolder
    {


        public ImageView holder_coverImageView;
        TextView holder_imageclickurlc_tv;
        Button holder_applybuttonc_bt;
        TextView holder_applybuttonurlc_tv;
        TextView holder_fromdate_tv;
        TextView holder_todate_tv;

        public MyViewHolder_X(View v)
        {
            super(v);

            holder_coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            holder_imageclickurlc_tv =(TextView) v.findViewById(R.id.imageurlc_TV);
            holder_applybuttonc_bt =(Button)v.findViewById(R.id.eventsapplyc_BT);
            holder_applybuttonurlc_tv =(TextView) v.findViewById(R.id.applyurlc_TV);
            holder_fromdate_tv=(TextView)v.findViewById(R.id.fromdate_currentevent_TV);
            holder_todate_tv=(TextView)v.findViewById(R.id.todate_currentevent_TV);

        }
    }

































//--------------------------------------------------------------------------------------------------------------------------

    //Holder for customAdapter
    private class Holder {
        ImageView holder_image;
        TextView holder_imageclickurl_tv;
        Button holder_applybutton_bt;
        TextView holder_applybuttonurl_tv;
        TextView holder_passorcurrent_tv;
        TextView holder_nocurrentevents_tv;
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

            String x = Integer.toString(class_currentevents_arrayObj.length);
            Log.e("class_arrayObj.length", x);
            return class_currentevents_arrayObj.length;

        }

        @Override
        public Object getItem(int position) {
            String x = Integer.toString(position);
            System.out.println("getItem position" + x);
            Log.d("getItem position", "x");
            return class_currentevents_arrayObj[position];
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.row_item_events, parent, false);


                holder.holder_image=(ImageView) convertView.findViewById(R.id.eventsimagedisplay_IV);
                holder.holder_imageclickurl_tv=(TextView)convertView.findViewById(R.id.imageurl_TV);
                holder.holder_applybuttonurl_tv=(TextView)convertView.findViewById(R.id.applyurl_TV);
                holder.holder_passorcurrent_tv=(TextView)convertView.findViewById(R.id.pastorcurrent_TV);
               holder.holder_applybutton_bt=(Button)convertView.findViewById(R.id.eventsapply_BT);
                holder.holder_nocurrentevents_tv =(TextView)convertView.findViewById(R.id.nocurrentevents_TV);
                Log.d("Inside If convertView", "Inside If convertView");

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
                Log.d("Inside else convertView", "Inside else convertView");
            }



            detail =(Class_CurrentEvents) getItem(position);
           // Log.e("Class postion",getItem(position).toString());

           // Class_CurrentEvents[] details =(Class_CurrentEvents) getItem(position);


            if(detail!=null)
            {
                x = detail.getEventImage();
                str_pastorcurrent =detail.getPastorCurrent();


                //Log.e("detail length"," length"+)

                str_imageurl = detail.getEventURL();
               // Log.e("imageURL","URL"+str_imageurl);
                str_applyurl= detail.getEventApplyURL();
                if(x!=null)
                {
                    if(str_pastorcurrent.equals("1"))
                    {
                       // Log.e("str_pastorcurrent:"," : "+str_pastorcurrent.toString());
                        holder.holder_image.setVisibility(View.VISIBLE);
                        holder.holder_applybutton_bt.setVisibility(View.VISIBLE);

                        bitmap1 = BitmapFactory.decodeByteArray(x, 0, x.length);
                        holder.holder_image.setImageBitmap(bitmap1);
                        holder.holder_imageclickurl_tv.setText(str_imageurl);
                        holder.holder_applybuttonurl_tv.setText(str_applyurl);


                        holder.holder_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //Toast.makeText(getActivity(),"imageurl"+str_imageurl,Toast.LENGTH_SHORT).show();
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.holder_imageclickurl_tv.getText().toString()));
                                startActivity(browserIntent);

                            }
                        });

                        holder.holder_applybutton_bt.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(holder.holder_applybuttonurl_tv.getText().toString()));
                                startActivity(browserIntent);
                                //Toast.makeText(getActivity(),"imageurl"+str_applyurl,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }//end of pastorcurrent
                } else
                {
                    //holder.holder_image.setVisibility(View.GONE);


                }

            }
            else{

               // holder.holder_nocurrentevents_tv.setVisibility(View.VISIBLE);
            }




            return convertView;


        }


    }//End of CustomAdapter















































}//end of fragment class




























/*
{

    ArrayList<CurrentEventsModel> currentEventList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.currentevents_fragment, container, false);

        currentEventList = new ArrayList<CurrentEventsModel>();

        ListView lview = (ListView) view.findViewById(R.id.listview);

        CurrentEventAdapter adapter = new CurrentEventAdapter(getActivity(), currentEventList);
        lview.setAdapter(adapter);

        populateList();

        adapter.notifyDataSetChanged();

        return view;
    }

    private void populateList() {

        CurrentEventsModel item1, item2, item3, item4, item5;

        item1 = new CurrentEventsModel(R.drawable.event1);
        currentEventList.add(item1);

        item2 = new CurrentEventsModel(R.drawable.event2);
        currentEventList.add(item2);

        item3 = new CurrentEventsModel(R.drawable.event3);
        currentEventList.add(item3);



    }
}
*/


