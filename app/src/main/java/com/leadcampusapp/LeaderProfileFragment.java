package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LeaderProfileFragment extends Fragment {
    private LinearLayout lnrlyt_projDtls;
    private LinearLayout lnrlyt_events;
    private LinearLayout lnrlyt_newsfeeds;

    public static final String  PREFBook_PM= "prefbook_pm";  //sharedpreference Book
    public static final String PrefID_PMID = "prefid_pmid"; //
    public static final String PrefID_pmName = "prefid_pmname"; //
    public static final String PrefID_PMEMailID = "prefid_pmemailid"; //
    public static final String PrefID_PMMobile = "prefid_pmmobile"; //
    public static final String PrefID_pmlocation = "prefid_pmlocation"; //
    public static final String PrefID_pmimageurl = "prefid_pmimgurl";



    public static final String  PREFBook_Stud= "prefbook_stud"; //sharedpreference Book
    public static final String PrefID_PM_ImagePath = "prefid_pm_imageUrl"; //
    SharedPreferences shardprefPM_obj;




    public static final String PrefID_SCellNo = "prefid_scellno";
    public static final String PrefID_PM_SName = "prefid_pmsname"; //
    public static final String PrefID_PM_SEmailID = "prefid_pm_semailid"; //
    public static final String PrefID_PM_S_ImagePath = "prefid_pm_imageUrl"; //
    public static final String PrefID_PM_S_CellNo = "prefid_pm_s_cellno";//
    public static final String PrefId_S_FB = "prefid_sfb";
    public static final String PrefId_S_TW = "prefid_stw";
    public static final String PrefId_S_IN = "prefid_sin";
    public static final String PrefId_S_Whatsapp = "prefid_swhatsapp";

    SharedPreferences shardpref_S_obj;

    SharedPreferences.Editor editor_S;


    private TextView txt_PMName;
    private TextView txt_PMMobileNumber;
    private TextView txt_PMMailId;
    TextView about_TV,projectDetails_TV,events_TV,news_TV,contactus_TV;
    ImageView about_IV,projectDetails_IV,events_IV,news_IV,contactus_IV;
    private ImageView img_PMPick;
    private ImageView fb_link,tw_link,inst_link,whatsapp_link;
    LinearLayout social_link;






    private String str_PMId,str_PMName,str_PMEmailId,str_PMMobile,str_PMLocation,str_PMImageUrl;
    private View view;
    private Result result2;

    private String getResult;
    public static final String PrefID_SLeadID = "prefid_sleadid";
    private String str_leadId;
    private String serverPath = Class_URL.ServerPath.toString().trim();
    private String str_PMImagePath;
    private String str_fb,str_tw,str_in,str_whatsapp;

    public static final String PrefID_PM_CellNo = "prefid_pm_cellno";//

    public static final String PrefID_S_ImgBase64 = "prefid_s_imgBase64"; //
    public static final String PrefID_PM_ImgBase64 = "prefid_pm_imgBase64";
    public static final String PrefId_S_IsFeesPaid = "prefid_sfeesPaid";
    private String str_isFeesPaid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Toast.makeText(getActivity(),"Inside Leader Profile",Toast.LENGTH_SHORT).show();

        view = inflater.inflate(R.layout.leaderprofile_fragment, container, false);

        shardprefPM_obj=getActivity().getSharedPreferences(PREFBook_PM, Context.MODE_PRIVATE);

        shardpref_S_obj=getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);

        initializeViews();


        if(getActivity().getIntent().hasExtra("fromeditProfile")){
            getResult = getActivity().getIntent().getStringExtra("fromeditProfile");
            Log.d("GetResultsss",getResult);

            shardpref_S_obj = getActivity().getSharedPreferences(PREFBook_Stud, Context.MODE_PRIVATE);



            shardpref_S_obj.getString(PrefID_SLeadID, "").trim();
            str_leadId = shardpref_S_obj.getString(PrefID_SLeadID, "").trim();

            GetStudentDetails getStudentDetails = new GetStudentDetails(getActivity());
            getStudentDetails.execute(str_leadId);

        }else {


            shardprefPM_obj.getString(PrefID_PMID, "").trim();
            str_PMId = shardprefPM_obj.getString(PrefID_PMID, "").trim();
            Log.d("str_PMId:", str_PMId);

        /* shardprefPM_obj.getString(PrefID_pmName, "").trim();
       str_PMName = shardprefPM_obj.getString(PrefID_pmName, "").trim();
        Log.d("str_PMName:",str_PMName);*/

            shardpref_S_obj.getString(PrefID_PM_SName, "").trim();
            str_PMName = shardpref_S_obj.getString(PrefID_PM_SName, "").trim();
            Log.d("str_PMName:", str_PMName);

      /*  shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        str_PMEmailId = shardprefPM_obj.getString(PrefID_PMEMailID, "").trim();
        Log.d("str_PMEmailId:",str_PMEmailId);*/

            shardpref_S_obj.getString(PrefID_PM_SEmailID, "").trim();
            str_PMEmailId = shardpref_S_obj.getString(PrefID_PM_SEmailID, "").trim();
            Log.d("str_PMEmailId:", str_PMEmailId);


       /* shardprefPM_obj.getString(PrefID_PMMobile, "").trim();
        str_PMMobile = shardprefPM_obj.getString(PrefID_PMMobile, "").trim();
        Log.d("str_PMMobile:",str_PMMobile);*/


            shardpref_S_obj.getString(PrefID_PM_S_CellNo, "").trim();
            str_PMMobile = shardpref_S_obj.getString(PrefID_PM_S_CellNo, "").trim();
            Log.e("str_PMMobile:", str_PMMobile);

            shardprefPM_obj.getString(PrefID_pmlocation, "").trim();
            str_PMLocation = shardprefPM_obj.getString(PrefID_pmlocation, "").trim();
            Log.d("str_PMLocation:", str_PMLocation);


            shardpref_S_obj.getString(PrefID_pmimageurl, "").trim();
           // str_PMImageUrl = shardpref_S_obj.getString(PrefID_pmimageurl, "").trim();
            str_PMImageUrl = shardpref_S_obj.getString(PrefID_PM_S_ImagePath, "").trim();
            Log.d("str_PMImageUrl:", String.valueOf(str_PMImageUrl));

            shardpref_S_obj.getString(PrefId_S_FB, "").trim();
            str_fb = shardpref_S_obj.getString(PrefId_S_FB, "").trim();
            Log.d("str_fb:", str_fb);

            shardpref_S_obj.getString(PrefId_S_IN, "").trim();
            str_in = shardpref_S_obj.getString(PrefId_S_IN, "").trim();
            Log.d("str_in:", str_in);

            shardpref_S_obj.getString(PrefId_S_Whatsapp, "").trim();
            str_whatsapp = shardpref_S_obj.getString(PrefId_S_Whatsapp, "").trim();
          //  str_whatsapp="918105227911";
            Log.d("str_whatsapp:", str_whatsapp);

            shardpref_S_obj.getString(PrefId_S_IsFeesPaid, "").trim();
            str_isFeesPaid = shardpref_S_obj.getString(PrefId_S_IsFeesPaid, "").trim();
            Log.d("FeesPaidisssssss",str_isFeesPaid);




            shardpref_S_obj.getString(PrefId_S_TW, "").trim();
            str_tw = shardpref_S_obj.getString(PrefId_S_TW, "").trim();
            Log.d("str_fb:", str_tw);


            if (str_PMName != null && !str_PMName.isEmpty() && str_PMName != "" && !str_PMName.equals("anyType{}")) {
                txt_PMName.setText(str_PMName);
            }

            if (str_PMMobile != null && !str_PMMobile.isEmpty() && str_PMMobile != "" && !str_PMMobile.equals("anyType{}")) {
                txt_PMMobileNumber.setText(str_PMMobile);
            }

            if (str_PMEmailId != null && !str_PMEmailId.isEmpty() && str_PMEmailId != "" && !str_PMEmailId.equals("anyType{}")) {
                txt_PMMailId.setText(str_PMEmailId);
            }


            if (!str_PMImageUrl.equals(null) && !str_PMImageUrl.equalsIgnoreCase("null") && !str_PMImageUrl.isEmpty() && str_PMImageUrl != "" && !str_PMImageUrl.equals("anyType{}") && !str_PMImageUrl.equals("{}")) {
                Log.d("InsideLoadProfileLeader", "Pick");
      /*          LoadProfilePick loadMgrProfilePick = new LoadProfilePick(getActivity());
                loadMgrProfilePick.execute();*/


                Glide.with(getActivity()).load(str_PMImageUrl)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img_PMPick);


                sociallinks();

                //Log.d("Mgrbitmapss:",StudentPMImageBitmap.getPmBitmap().toString());

                //img_PMPick.setImageBitmap(StudentPMImageBitmap.getPmBitmap());

      /*          shardpref_S_obj.getString(PrefID_PM_ImgBase64,"").trim();
                String str_pm_imgBase64 = shardpref_S_obj.getString(PrefID_PM_ImgBase64, "").trim();

                Log.d("str_pm_imgBase64isss",str_pm_imgBase64);

                try {
                    if (!str_pm_imgBase64.equalsIgnoreCase("")) {
                        byte[] b = Base64.decode(str_pm_imgBase64, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                        img_PMPick.setImageBitmap(bitmap);
                    }
                }catch(OutOfMemoryError ex){
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                        }
                    });
                }*/

            }


        }

        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        GridAdapter gridAdapter = new GridAdapter(getActivity());
        gridAdapter.setStr_isFeesPaid(str_isFeesPaid);
        gridView.setAdapter(gridAdapter);

        GridView gridViewSecond = (GridView) view.findViewById(R.id.gridview1);
        gridViewSecond.setAdapter(new GridAdapterSecond(getActivity()));

/*
        lnrlyt_projDtls = (LinearLayout) view.findViewById(R.id.lnrlyt_projectDtls);
        lnrlyt_projDtls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToProject = new Intent(getActivity().getBaseContext(), ProjectDetails.class);
                startActivity(itthomeToProject);
            }
        });

        lnrlyt_events = (LinearLayout) view.findViewById(R.id.lnrlyt_events);
        lnrlyt_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), EventsActivity.class);
                startActivity(itthomeToEvent);
            }
        });

        lnrlyt_newsfeeds = (LinearLayout) view.findViewById(R.id.lnrlyt_newsfeeds);
        lnrlyt_newsfeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToNewsFeeds = new Intent(getActivity().getBaseContext(), NewsFeedsActivity.class);
                startActivity(itthomeToNewsFeeds);
            }
        });

        about_TV = (TextView) view.findViewById(R.id.about_TV);
        about_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity(),AboutUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });

        about_IV = (ImageView) view.findViewById(R.id.about_IV);
        about_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity(),AboutUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });

        contactus_TV = (TextView) view.findViewById(R.id.contactus_TV);
        contactus_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity(),ContactUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });

        contactus_IV = (ImageView) view.findViewById(R.id.contactus_IV);
        contactus_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity(),ContactUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });

*/

        shardpref_S_obj.getString(PrefId_S_IsFeesPaid, "").trim();
        str_isFeesPaid = shardpref_S_obj.getString(PrefId_S_IsFeesPaid, "").trim();
        Log.d("FeesPaidLeaderssss",str_isFeesPaid);


      /*  projectDetails_IV = (ImageView) view.findViewById(R.id.projectDetails_IV);
        projectDetails_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(str_isFeesPaid.equals("1")) {
                    Intent itthomeToProject = new Intent(getActivity().getBaseContext(), ProjectDetails.class);
                    // itthomeToProject.putExtra("From","Student");
                    startActivity(itthomeToProject);
                }else{
                    Toast.makeText(getActivity(),"You have not paid the fees. Please contact Project Manager",Toast.LENGTH_LONG).show();
                }
            }
        });
        projectDetails_TV = (TextView) view.findViewById(R.id.projectDetails_TV);
        projectDetails_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToProject = new Intent(getActivity().getBaseContext(), ProjectDetails.class);
                //  itthomeToProject.putExtra("From","Student");
                startActivity(itthomeToProject);
            }
        });

        events_IV = (ImageView) view.findViewById(R.id.events_IV);
        events_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), EventsActivity.class);
                //   itthomeToEvent.putExtra("From","Student");
                //Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), TestActivity2.class);
                startActivity(itthomeToEvent);
            }
        });
        events_TV = (TextView) view.findViewById(R.id.events_TV);
        events_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), EventsActivity.class);
                // itthomeToEvent.putExtra("From","Student");
                //Intent itthomeToEvent = new Intent(getActivity().getBaseContext(), TestActivity2.class);
                startActivity(itthomeToEvent);
            }
        });

        news_IV = (ImageView) view.findViewById(R.id.news_IV);
        news_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToNewsFeeds = new Intent(getActivity().getBaseContext(), NewsFeedsActivity.class);
                //    itthomeToNewsFeeds.putExtra("From","Student");
                startActivity(itthomeToNewsFeeds);
            }
        });
        news_TV = (TextView) view.findViewById(R.id.news_TV);
        news_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToNewsFeeds = new Intent(getActivity().getBaseContext(), NewsFeedsActivity.class);
                //    itthomeToNewsFeeds.putExtra("From","Student");
                startActivity(itthomeToNewsFeeds);
            }
        });

        about_IV = (ImageView) view.findViewById(R.id.about_IV);
        about_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity().getBaseContext(), AboutUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });
        about_TV = (TextView) view.findViewById(R.id.about_TV);
        about_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity().getBaseContext(), AboutUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });

        contactus_IV = (ImageView) view.findViewById(R.id.contactus_IV);
        contactus_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity().getBaseContext(), ContactUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });
        contactus_TV = (TextView) view.findViewById(R.id.contactus_TV);
        contactus_TV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itthomeToAboutUs = new Intent(getActivity().getBaseContext(), ContactUsActivity.class);
                itthomeToAboutUs.putExtra("From","Student");
                startActivity(itthomeToAboutUs);
            }
        });
*/


        return view;
    }

    public void sociallinks(){
        if (str_fb != null && !str_fb.isEmpty() && str_fb != "" && !str_fb.equals("anyType{}") && !str_fb.equals("{}")) {
            social_link.setVisibility(View.VISIBLE);
            fb_link.setVisibility(View.VISIBLE);

               /* fb_link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), StoryBlogActivity.class);
                        i.putExtra("Story_Blog", str_fb);
                        startActivity(i);
                    }
                });*/

            fb_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isAppInstalled()) {
                        //      Toast.makeText(getContext(), "facebook app already installed", Toast.LENGTH_SHORT).show();
                        try {
                            //  Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/100001821549638"));
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(str_fb));
                            //   Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/www.facebook.com/madhushree.kubsad.1"));
                            startActivity(intent);
                        } catch (Exception e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str_fb)));
                        }
                    } else {
                        //   Toast.makeText(getContext(), "facebook app not installing", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), StoryBlogActivity.class);
                        i.putExtra("Story_Blog", str_fb);
                        startActivity(i);
                    }

                }
            });
        }

        if (str_in != null && !str_in.isEmpty() && str_in != "" && !str_in.equals("anyType{}") && !str_in.equals("{}")) {
            social_link.setVisibility(View.VISIBLE);
            inst_link.setVisibility(View.VISIBLE);

            inst_link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        /*Intent i = new Intent(getActivity(), StoryBlogActivity.class);
                        i.putExtra("Story_Blog", str_in);

                        startActivity(i);*/

                    Uri uri = Uri.parse(str_in);
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                    likeIng.setPackage("com.instagram.android");

                    try {
                        startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(str_in)));
                    }
                }
            });
        }

        if (str_tw != null && !str_tw.isEmpty() && str_tw != "" && !str_tw.equals("anyType{}") && !str_tw.equals("{}")) {
            social_link.setVisibility(View.VISIBLE);
            tw_link.setVisibility(View.VISIBLE);

            tw_link.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                  /* Intent i = new Intent(getActivity(), StoryBlogActivity.class);
                                                   i.putExtra("Story_Blog", str_tw);
                                                   startActivity(i);*/

                                               try {
                                                   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str_tw)));
                                                   // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitter_user_name)));
                                               } catch (Exception e) {
                                                   if (str_tw != null) {
                                                       startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str_tw)));
                                                   }
                                                   // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitter_user_name)));
                                               }
                                           }
                                       }
            );


        }
        if (str_whatsapp != null && !str_whatsapp.isEmpty() && str_whatsapp != "" && !str_whatsapp.equals("anyType{}") && !str_whatsapp.equals("{}")) {
            social_link.setVisibility(View.VISIBLE);
            whatsapp_link.setVisibility(View.VISIBLE);

            whatsapp_link.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                  /* Intent i = new Intent(getActivity(), StoryBlogActivity.class);
                                                   i.putExtra("Story_Blog", str_tw);
                                                   startActivity(i);*/

                                                   /*try {
                                                       startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str_whatsapp)));
                                                       // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + twitter_user_name)));
                                                   } catch (Exception e) {
                                                       if (str_whatsapp != null) {
                                                           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str_whatsapp)));
                                                       }
                                                       // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/#!/" + twitter_user_name)));
                                                   }*/

                                                  /* Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                                                   whatsappIntent.setType("text/plain");
                                                   whatsappIntent.setPackage("com.whatsapp");
                                                   whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
                                                   try {
                                                       startActivity(whatsappIntent);
                                                   } catch (android.content.ActivityNotFoundException ex) {
                                                     //  Toast.makeText("Whatsapp have not been installed.",);
                                                       Toast.makeText(getContext(), "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();
                                                   }*/
                                                     String smsNumber = "91"+str_whatsapp; //without '+'
                                                     try {
                                                         Intent sendIntent = new Intent("android.intent.action.MAIN");
                                                         sendIntent.setComponent(new ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                                                         sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber)+"@s.whatsapp.net");
                                                         startActivity(sendIntent);
                                                     } catch(Exception e) {
                                                         Toast.makeText(getContext(), "Whatsapp have not been installed", Toast.LENGTH_SHORT).show();
                                                     }
                                                 }
                                             }
            );


        }
    }

    public boolean isAppInstalled() {
        try {
            getContext().getPackageManager().getApplicationInfo("com.facebook.katana", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
    private void initializeViews() {
        txt_PMName = (TextView) view.findViewById(R.id.txt_PMName);
        txt_PMMailId = (TextView) view.findViewById(R.id.txt_PMMailId);
        txt_PMMobileNumber = (TextView) view.findViewById(R.id.txt_PMMobileNumber);
        img_PMPick = (ImageView) view.findViewById(R.id.img_PMPick);

        social_link = (LinearLayout) view.findViewById(R.id.social_link);
        fb_link = (ImageView) view.findViewById(R.id.fb_link);
        tw_link = (ImageView) view.findViewById(R.id.tw_link);
        inst_link = (ImageView) view.findViewById(R.id.inst_link);
        whatsapp_link = (ImageView) view.findViewById(R.id.whatsapp_link);

        social_link.setVisibility(View.GONE);
        fb_link.setVisibility(View.GONE);
        tw_link.setVisibility(View.GONE);
        inst_link.setVisibility(View.GONE);
        whatsapp_link.setVisibility(View.GONE);
        result2 = new Result();
    }



    public class GetStudentDetails extends AsyncTask<String, Void, SoapObject> {

        Context context;
        //AlertDialog alertDialog;

        //private ProgressBar progressBar;

        private ProgressDialog progressDialog;

        GetStudentDetails (Context ctx){
            context = ctx;
            progressDialog = new ProgressDialog(context);
        }


        @Override
        protected SoapObject doInBackground(String... params) {
            String str_leadId = (String) params [0];
            //String versionCode = (String) params[2];

            SoapObject response = getStudDetails(str_leadId);

            Log.d("GetStudentDetailssresp",response.toString());

            return response;
        }

        @Override
        protected void onPreExecute() {
           /* progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(SoapObject result) {

            if(result!=null) {

                Log.d("GetStudentDetailssresp", result.toString());

                SoapPrimitive S_Status, S_ManagerName, S_ManagerMailId, S_ManagerMobileNumber, S_ManagerImagePath, S_FBLink, S_INLink, S_TWLink, S_WhatsApp;
                Object O_Status, O_ManagerName, O_ManagerMailId, O_ManagerMobileNumber, O_ManagerImagePath, O_FBLink, O_INLink, O_TWLink, O_WhatsApp;
                String str_Status = null, str_ManagerName = null, str_ManagerMailId = null, str_ManagerMobileNumber = null, str_ManagerImagePath = null, str_FBLink = null, str_INLink = null, str_TWLink = null, str_WhatsApp = null;


                editor_S = shardpref_S_obj.edit();


                O_Status = result.getProperty("Status");
                if (!O_Status.toString().equals("anyType{}") && !O_Status.toString().equals(null)) {
                    S_Status = (SoapPrimitive) result.getProperty("Status");
                    Log.d("Status:", S_Status.toString());
                    str_Status = S_Status.toString();
                }

                if (str_Status.equalsIgnoreCase("Success")) {
                    O_ManagerName = result.getProperty("ManagerName");
                    if (!O_ManagerName.toString().equals("anyType{}") && !O_ManagerName.toString().equals(null)) {
                        S_ManagerName = (SoapPrimitive) result.getProperty("ManagerName");
                        Log.d("S_ManagerName", S_ManagerName.toString());
                        str_ManagerName = S_ManagerName.toString();
                    }

                    O_ManagerMailId = result.getProperty("ManagerMailid");
                    if (!O_ManagerMailId.toString().equals("anyType{}") && !O_ManagerMailId.toString().equals(null)) {
                        S_ManagerMailId = (SoapPrimitive) result.getProperty("ManagerMailid");
                        Log.d("S_ManagerMailId", S_ManagerMailId.toString());
                        str_ManagerMailId = S_ManagerMailId.toString();
                    }

                    O_ManagerMobileNumber = result.getProperty("ManagerMobileNumber");
                    if (!O_ManagerMobileNumber.toString().equals("anyType{}") && !O_ManagerMobileNumber.toString().equals(null)) {
                        S_ManagerMobileNumber = (SoapPrimitive) result.getProperty("ManagerMobileNumber");
                        Log.d("S_ManagerMobileNumber", S_ManagerMobileNumber.toString());
                        str_ManagerMobileNumber = S_ManagerMobileNumber.toString();
                    }

                    O_ManagerImagePath = result.getProperty("ManagerImagePath");
                    if (!O_ManagerImagePath.toString().equals("anyType{}") && !O_ManagerImagePath.toString().equals(null)) {
                        S_ManagerImagePath = (SoapPrimitive) result.getProperty("ManagerImagePath");
                        Log.d("S_ManagerImagePath", S_ManagerImagePath.toString());
                        str_ManagerImagePath = S_ManagerImagePath.toString();
                    }

                    O_FBLink = result.getProperty("Manager_Facebook");
                    if (!O_FBLink.toString().equals("anyType{}") && !O_FBLink.toString().equals(null)) {
                        S_FBLink = (SoapPrimitive) result.getProperty("Manager_Facebook");
                        Log.d("S_FBLink", S_FBLink.toString());
                        str_fb = S_FBLink.toString();
                    }

                    O_INLink = result.getProperty("Manager_Instagram");
                    if (!O_INLink.toString().equals("anyType{}") && !O_INLink.toString().equals(null)) {
                        S_INLink = (SoapPrimitive) result.getProperty("Manager_Instagram");
                        Log.d("S_INLink", S_INLink.toString());
                        str_in = S_INLink.toString();
                    }

                    O_WhatsApp = result.getProperty("Manager_WhatsApp");
                    if (!O_WhatsApp.toString().equals("anyType{}") && !O_WhatsApp.toString().equals(null)) {
                        S_WhatsApp = (SoapPrimitive) result.getProperty("Manager_WhatsApp");
                        Log.d("O_WhatsApp", S_WhatsApp.toString());
                        str_whatsapp = S_WhatsApp.toString();
                    }

                    O_TWLink = result.getProperty("Manager_Twitter");
                    if (!O_TWLink.toString().equals("anyType{}") && !O_TWLink.toString().equals(null)) {
                        S_TWLink = (SoapPrimitive) result.getProperty("Manager_Twitter");
                        Log.d("S_TWLink", S_TWLink.toString());
                        str_tw = S_TWLink.toString();
                    }

                    if (str_fb != null && !str_fb.isEmpty() && str_fb != "" && !str_fb.equals("anyType{}") && str_fb != "{}") {
                        editor_S.putString(PrefId_S_FB, str_fb);
                    }

                    if (str_tw != null && !str_tw.isEmpty() && str_tw != "" && !str_tw.equals("anyType{}") && str_tw != "{}") {
                        editor_S.putString(PrefId_S_TW, str_tw);
                    }

                    if (str_in != null && !str_in.isEmpty() && str_in != "" && !str_in.equals("anyType{}") && str_in != "{}") {
                        editor_S.putString(PrefId_S_IN, str_in);
                    }
                    if (str_whatsapp != null && !str_whatsapp.isEmpty() && str_whatsapp != "" && !str_whatsapp.equals("anyType{}") && str_whatsapp != "{}") {
                        editor_S.putString(PrefId_S_Whatsapp, str_whatsapp);
                    }
                    // sociallinks();
                    if (str_ManagerName != null && !str_ManagerName.isEmpty() && str_ManagerName != "" && !str_ManagerName.equals("anyType{}") && str_ManagerName != "{}") {
                        txt_PMName.setText(str_ManagerName);
                        editor_S.putString(PrefID_PM_SName, str_ManagerName);
                    }

          /*      else{
                    shardpref_S_obj.getString(PrefID_PM_SName, "").trim();
                    str_PMName = shardpref_S_obj.getString(PrefID_PM_SName, "").trim();
                    txt_PMName.setText(str_PMName);
                }*/

                    if (str_ManagerMailId != null && !str_ManagerMailId.isEmpty() && str_ManagerMailId != "" && !str_ManagerMailId.equals("anyType{}") && str_ManagerMailId != "{}") {
                        txt_PMMailId.setText(str_ManagerMailId);


                        editor_S.putString(PrefID_PM_SEmailID, str_ManagerMailId);
                    }
              /*  else{
                    shardpref_S_obj.getString(PrefID_PM_SEmailID, "").trim();
                    str_PMEmailId = shardpref_S_obj.getString(PrefID_PM_SEmailID, "").trim();
                    txt_PMMailId.setText(str_PMEmailId);
                }*/

                    if (str_ManagerMobileNumber != null && !str_ManagerMobileNumber.isEmpty() && str_ManagerMobileNumber != "" && !str_ManagerMobileNumber.equals("anyType{}") && str_ManagerMobileNumber != "{}")
                    {
                        txt_PMMobileNumber.setText(str_ManagerMobileNumber);

                        editor_S.putString(PrefID_PM_S_CellNo, str_ManagerMobileNumber);
                    }



            /*    else{
                    shardpref_S_obj.getString(PrefID_PM_CellNo, "").trim();
                    str_PMMobile = shardpref_S_obj.getString(PrefID_PM_CellNo, "").trim();
                    txt_PMMobileNumber.setText(str_PMMobile);
                }*/

                    if (str_ManagerImagePath != null && !str_ManagerImagePath.isEmpty() && str_ManagerImagePath != "" && !str_ManagerImagePath.equals("anyType{}") && str_ManagerImagePath != "{}") {
                        Log.d("InsideLoadProfile", "Pick");
                        str_PMImageUrl = serverPath + str_ManagerImagePath.substring(2);

                        editor_S.putString(PrefID_pmimageurl, str_PMImageUrl);

                   /* LoadProfilePick loadStudProfilePick = new LoadProfilePick(getActivity());
                    loadStudProfilePick.execute();*/

                        Glide.with(getActivity()).load(str_PMImageUrl)
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(img_PMPick);
                    }


                    editor_S.commit();
                    sociallinks();
           /*     else{

                    shardpref_S_obj.getString(PrefID_PM_ImagePath, "").trim();
                    str_PMImagePath = shardpref_S_obj.getString(PrefID_PM_ImagePath, "").trim();

                    str_PMImageUrl = serverPath + str_PMImagePath.substring(2);
                    LoadProfilePick loadStudProfilePick = new LoadProfilePick(getActivity());
                    loadStudProfilePick.execute();
                }*/
                }
            }
            //progressBar.setVisibility(View.GONE);
            progressDialog.dismiss();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    private SoapObject getStudDetails(String leadid)
    {

        String METHOD_NAME = "GetStudentDetailss";
        String SOAP_ACTION1 = "http://mis.leadcampus.org/GetStudentDetailss";

        try{
            //mis.leadcampus.org

            SoapObject request = new SoapObject("http://mis.leadcampus.org/", METHOD_NAME);


            /*request.addProperty("Username", mobilenum);
            request.addProperty("Password", leadid);*/

            /*request.addProperty("Username", leadid);
            request.addProperty("Password", mobilenum);*/

/*            String username=edt_leadid.getText().toString();
            String password=edt_mobnumber.getText().toString();*/

            request.addProperty("leadId", leadid);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            //SoapSerializationEnvelope evp = new SoapSoapEnvelope.XSD;

            envelope.dotNet = true;
            //Set output SOAP object
            envelope.setOutputSoapObject(request);
            //Create HTTP call object
            //envelope.encodingStyle = SoapSerializationEnvelope.XSD;
            HttpTransportSE androidHttpTransport = new HttpTransportSE(Class_URL.URL_Login.toString().trim());
            //androidHttpTransport.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");

            try
            {
                androidHttpTransport.call(SOAP_ACTION1, envelope);
                Log.d("soap responseyyyyyyy",envelope.getResponse().toString());
                SoapObject response = (SoapObject) envelope.getResponse();
                Log.d("soap responseyyyyyyy",response.toString());

                return response;

            }
            catch (Exception t) {
                Log.e("request fail", "> " + t.getMessage().toString());
            }
        }catch (Exception t) {
            Log.d("exception outside",t.getMessage().toString());
        }
        return null;


    }



    public class LoadProfilePick extends AsyncTask<Void, Void, Bitmap> {

        private Context context;
        private ProgressDialog progressDialog;
        //private ProgressBar progressBar;

        LoadProfilePick (Context context){
            this.context = context;
            progressDialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
        /*    progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);*/

            progressDialog.setMessage("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(Void... params) {

            Bitmap bitmaplogo=null;

               try {
                    //Log.d("Urlssssssssssss",url.toString());
                    //bitmaplogo = BitmapFactory.decodeStream(url.openStream());
                    URL urlis = null;
                    if(!str_PMImageUrl.equals(null) && !str_PMImageUrl.equalsIgnoreCase("null")) {
                        urlis = new URL(str_PMImageUrl);
                        Log.d("str_PMImageUrlinleader",str_PMImageUrl);
                        InputStream inputStream = urlis.openStream();
                        bitmaplogo = BitmapFactory.decodeStream(inputStream);
                    }



                } catch (MalformedURLException e) {
                    e.printStackTrace();
/*                   result2.setMgrresult(e.getMessage().toString());*/

                   final String exceptnMsg = e.getMessage().toString();
                   getActivity().runOnUiThread(new Runnable() {
                       public void run() {
                           Toast.makeText(getActivity(),exceptnMsg, Toast.LENGTH_LONG).show();
                       }
                   });


                   //return result2;
                } catch (IOException e) {
                    e.printStackTrace();
             /*       result2.setMgrresult(e.getMessage().toString());*/

                    final String exceptnMsg = e.getMessage().toString();
                    getActivity().runOnUiThread(new Runnable() {
                       public void run() {
                           Toast.makeText(getActivity(),exceptnMsg, Toast.LENGTH_LONG).show();
                       }
                    });


                    //return result2;
                }catch(OutOfMemoryError ex){
                   getActivity().runOnUiThread(new Runnable() {
                       public void run() {
                           Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                       }
                   });
               }

            /*result2.setMgrbitmap(bitmaplogo);
            return result2;*/
            return bitmaplogo;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            img_PMPick.setImageBitmap(result);

            try {

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                result.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageinbytesArray = stream.toByteArray();
                String encodedImage = Base64.encodeToString(imageinbytesArray, Base64.DEFAULT);

     /*           shardpref_S_obj.edit();
                editor_S.putString(PrefID_PM_ImgBase64,encodedImage);*/


                editor_S = shardpref_S_obj.edit();
                editor_S.putString(PrefID_PM_ImgBase64, encodedImage);
                editor_S.commit();
            }catch(OutOfMemoryError ex){
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getActivity(),"Slow Internet or Internet Dropped", Toast.LENGTH_LONG).show();
                    }
                });
            }











         /*   bitmapList.add(bitmap);

            Log.d("count1iss", String.valueOf(count1));
            Log.d("count2iss", String.valueOf(count2));
            Log.d("Bitmapsizess", String.valueOf(bitmapList.size()));
            if(bitmapList.size()==count2){
                img_mainGallery.setImageBitmap(bitmapList.get(0));
                txt_numOfImages.setText("Images: "+bitmapList.size());
            }*/


            //}
/*

            if(result2.getMgrresult()!=null) {

                //Log.d("Inside", bitmap.toString());

                img_PMPick.setImageBitmap(result2.getMgrbitmap());
            }
            else{
                Toast.makeText(getActivity(),"Exception occured in Manager:"+result2.getMgrresult(),Toast.LENGTH_LONG).show();

            }*/



            //progressBar.setVisibility(GONE);
            progressDialog.dismiss();
        }
    }

}
