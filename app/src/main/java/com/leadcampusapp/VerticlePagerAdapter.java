package com.leadcampusapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;
//import androidx.appcompat.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

//import com.android.sripad.leadnew_22_6_2018.R;
import com.codesgood.views.JustifiedTextView;
//import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.leadcampusapp.module.LeadStoryList;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by rizvan on 12/13/16.
 */

public class VerticlePagerAdapter extends PagerAdapter {//implements YouTubePlayer.OnInitializedListener {

    String mResources[] = {"Rally for rivers is a campaign launched by Isha Foundation in 2017 to tackle the scarcity of water cross rivers. Sadhguru launched the campaign on 3rd September from Isha Yoga " +
            "centre, Coimbatore. On October 3, a river revitalization " +
            "draft proposal was presented by Sadhguru to Narendra " +
            "Modi. Six states( Karnataka, Assam, Chhattisgarh, Punjab, " +
            "Maharashtra and Gujarat) signed Memorandums of " +
            "Understanding(MoU) with Isha Foundation to plant trees...",
    "Iron Man is a 2008 American superhero film based on the Marvel Comics character of the same name, produced by Marvel Studios and distributed by Paramount Pictures.1 It is the first film in the Marvel Cinematic Universe. The film was directed by Jon Favreau, with a screenplay by Mark Fergus & Hawk Ostby and Art Marcum & Matt Holloway. It stars Robert Downey Jr., Terrence Howard, Jeff Bridges, Shaun Toub and Gwyneth Paltrow. In Iron Man, Tony Stark, an industrialist and master engineer, builds a powered exoskeleton and becomes the technologically advanced superhero Iron Man.\n" +
            "\n"};

    public static final String API_KEY = "AIzaSyCe6tORd9Ch4lx-9Ku5SQ476uS9OtZYsWA";
    public static final String VIDEO_ID = "xtyTAjUz5aQ";
    public YouTubePlayerSupportFragment myContext;
    VideoView videoView;
    //  RelativeLayout video_frag;
    YouTubePlayerSupportFragment frag;
    Context mContext;
    LayoutInflater mLayoutInflater;
    private String serverPath = Class_URL.ServerPath.toString().trim();
    String PMImgUrl;
    ImageView imageView,fullimageView;
    TextView btn_readMore;
    String Story_Blog,Story_Type;
    File pictureFileDir;

    public static final String TAG = "bottom_sheet";

    private File pictureFile;
    private String picString;
    private String idCardImgEncodedString=null;

    private static int NUM_ITEMS = 0;

    public ArrayList<LeadStoryList> leadStoryLists;

    int flag =0;


  /*  public VerticlePagerAdapter(Context context,ArrayList<LeadStoryList> leadStoryLists) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        leadStoryLists = leadStoryLists;
    }*/

    public VerticlePagerAdapter(Context mContext, ArrayList<LeadStoryList> leadStoryLists) {
        this.mContext = mContext;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.leadStoryLists = leadStoryLists;
    }

    /*public VerticlePagerAdapter(LeadStoryFragment mcontext, ArrayList<LeadStoryList> listview_arr) {
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.leadStoryLists = leadStoryLists;
    }*/

    @Override
    public int getCount() {

      int xcount=leadStoryLists.size();
       // Log.i("tag","count=1="+count);

       int x=xcount;
        //return leadStoryLists.size();
       return xcount;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

  /*  @Override
    public int getCount() {
       // count=LeadStoryList.listview_arr.size();
        return LeadStoryList.listview_arr.size();
    }*/

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.content_main, container, false);
        CardView card_view=(CardView) itemView.findViewById(R.id.card_view);
        TextView txt_story=(TextView) itemView.findViewById(R.id.txt_story);

        Log.d("insideyyyyy","instantiateItemssssssss");

        JustifiedTextView label = (JustifiedTextView) itemView.findViewById(R.id.textView);

        imageView = (ImageView) itemView.findViewById(R.id.imageView);

        //TextView btn_readMore = (TextView) itemView.findViewById(R.id.btn_readMore);

        fullimageView =(ImageView) itemView.findViewById(R.id.fullimageView);
        WebView mWebView =(WebView) itemView.findViewById(R.id.webview_youtube);

        final RelativeLayout share_images=(RelativeLayout) itemView.findViewById(R.id.share_images);
     //   videoView =(VideoView) itemView.findViewById(R.id.videoView);
   ///     video_frag =(RelativeLayout) itemView.findViewById(R.id.video_frag);
        /*frag = YouTubePlayerSupportFragment.newInstance();
        ((Activity) mContext).getFragmentManager().findFragmentById(R.id.youtube_fragment);
        frag.initialize(API_KEY, this);*/

            // frag = (YouTubePlayerFragment) myContext.getFragmentManager().findFragmentById(R.id.youtube_fragment);
        //frag.initialize(API_KEY,this);
      //  final RelativeLayout bottom_full=(RelativeLayout) itemView.findViewById(R.id.bottom_full);

        //ImageView btn_share=(ImageView) itemView.findViewById(R.id.shareit);

     // final RelativeLayout share_half=(RelativeLayout) itemView.findViewById(R.id.share_half);
       final RelativeLayout share_half_image =(RelativeLayout) itemView.findViewById(R.id.share_half_image);
      //  ImageView btn_share1=(ImageView) itemView.findViewById(R.id.shareit1);
        //TextView btn_readMore1=(TextView) itemView.findViewById(R.id.btn_readMore1);
        Story_Type=LeadStoryList.listview_arr.get(position).getStory_Type();
        Log.i("tag","Story_Type1="+Story_Type);
        fullimageView.setVisibility(View.GONE);
        share_images.setVisibility(View.GONE);
        mWebView.setVisibility(View.GONE);
       // videoView.setVisibility(View.GONE);
    //    btn_share1.setVisibility(View.GONE);
        share_half_image.setVisibility(View.GONE);
     //   btn_share.setVisibility(View.GONE);
      //  btn_readMore.setVisibility(View.GONE);
///        share_half.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        txt_story.setVisibility(View.GONE);
        label.setVisibility(View.GONE);
        //bottom_full.setVisibility(View.GONE);
       // btn_readMore1.setVisibility(View.GONE);
      //  makeTextViewResizable(label, 6, "View More", true);

    /*    share_half_image.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        btn_readMore.setVisibility(View.GONE);
        label.setVisibility(View.GONE);
        txt_story.setVisibility(View.GONE);
        share_half.setVisibility(View.GONE);
        btn_share.setVisibility(View.GONE);*/




       /* if(flag == 1){
            mWebView.clearFormData();
            mWebView.clearView();
            mWebView.destroy();
        }*/



        if(Story_Type.equals("3"))
        {
          //  Toast.makeText(mContext,"Inside 3",Toast.LENGTH_LONG).show();

           /* mWebView.clearFormData();
            mWebView.clearView();
            mWebView.destroy();*/

            //mWebView.removeJavascriptInterface();


            fullimageView.setVisibility(View.VISIBLE);
            share_images.setVisibility(View.VISIBLE);
         //   btn_share1.setVisibility(View.VISIBLE);
           // bottom_full.setVisibility(View.VISIBLE);
         //   videoView.setVisibility(View.GONE);
            mWebView.setVisibility(View.GONE);
            imageView.setVisibility(View.GONE);
           // btn_readMore.setVisibility(View.GONE);
            label.setVisibility(View.GONE);
            txt_story.setVisibility(View.GONE);
           // btn_share.setVisibility(View.GONE);
       //     share_half.setVisibility(View.GONE);
         //   share_half_image.setVisibility(View.GONE);
          //  Story_Blog=LeadStoryList.listview_arr.get(position).getURL_Link();

            if (position % 2 != 0)
            {
             //   label.setText(LeadStoryList.listview_arr.get(position).getStory_Description());
                String Image_Path=LeadStoryList.listview_arr.get(position).getCard_Image_Path();
               // txt_story.setText(LeadStoryList.listview_arr.get(position).getStory_Title());
                String Imagestring = Image_Path;
                if(Imagestring.equals("null")||Imagestring.equals("anyType{}") || Imagestring.equals("<empty>")){
                    // PMImgUrl="null";
                    fullimageView.setImageResource(R.drawable.n1);
                }else {
                    String arr[] = Imagestring.split("/", 2);

                    String firstWord = arr[0];   //the
                    String secondWord = arr[1];

                    PMImgUrl = serverPath + secondWord;

                    Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                    try
                    {
                        Glide.with(mContext)
                                .load(PMImgUrl)
                                .asBitmap()
                                .into(fullimageView);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                  /*  LoadGalleryFullImage loadGalleryImage=new LoadGalleryFullImage();
                    loadGalleryImage.execute();*/
                }
                Story_Blog = LeadStoryList.listview_arr.get(position).getURL_Link();

               /* if (Story_Blog.equals("<empty>") || Story_Blog.equals("anyType{}") || Story_Blog.equals("null") || Story_Blog.equals("")) {
                    //  Toast.makeText(mContext,"URL Not Found",Toast.LENGTH_LONG).show();
                    btn_readMore1.setVisibility(View.GONE);
                } else {
                    btn_readMore1.setVisibility(View.VISIBLE);
                    btn_readMore1.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            Story_Blog = LeadStoryList.listview_arr.get(position).getURL_Link();
                       *//* Intent i = new Intent(mContext, StoryBlogActivity.class);
                        i.putExtra("Story_Blog",Story_Blog);
                        mContext.startActivity(i);*//*

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Story_Blog));
                            mContext.startActivity(browserIntent);
                        }
                    });
                }*/
            }
            else {
                //txt_story.setText(LeadStoryList.listview_arr.get(position).getStory_Title());
              //  label.setText(LeadStoryList.listview_arr.get(position).getStory_Description());
                String Image_Path = LeadStoryList.listview_arr.get(position).getCard_Image_Path();
                String Imagestring = Image_Path;
                if (Imagestring.equals("null") || Imagestring.equals("anyType{}") || Imagestring.equals("<empty>")) {
                    // PMImgUrl="null";
                    fullimageView.setImageResource(R.drawable.n1);
                } else {
                    String arr[] = Imagestring.split("/", 2);

                    String firstWord = arr[0];   //the
                    String secondWord = arr[1];

                    PMImgUrl = serverPath + secondWord;

                    Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                    try
                    {
                        Glide.with(mContext)
                                .load(PMImgUrl)
                                .asBitmap()
                                .into(fullimageView);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                   /* LoadGalleryFullImage loadGalleryImage = new LoadGalleryFullImage();
                    loadGalleryImage.execute();*/
                }
                /*fullimageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Story_Blog = LeadStoryList.listview_arr.get(position).getURL_Link();

                        if (Story_Blog.equals("<empty>") || Story_Blog.equals("anyType{}") || Story_Blog.equals("null") || Story_Blog.equals("")) {
                            //  Toast.makeText(mContext,"URL Not Found",Toast.LENGTH_LONG).show();
                        } else {
                       *//* Intent i = new Intent(mContext, StoryBlogActivity.class);
                        i.putExtra("Story_Blog",Story_Blog);
                        mContext.startActivity(i);*//*

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Story_Blog));
                            mContext.startActivity(browserIntent);
                        }
                    }
                });*/
                Story_Blog = LeadStoryList.listview_arr.get(position).getURL_Link();

//                if (Story_Blog.equals("<empty>") || Story_Blog.equals("anyType{}") || Story_Blog.equals("null") || Story_Blog.equals("")) {
//                    //  Toast.makeText(mContext,"URL Not Found",Toast.LENGTH_LONG).show();
//                    btn_readMore1.setVisibility(View.GONE);
//                } else {
//                    btn_readMore1.setVisibility(View.VISIBLE);
//                    btn_readMore1.setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View v) {
//
//                            Story_Blog = LeadStoryList.listview_arr.get(position).getURL_Link();
//                       /* Intent i = new Intent(mContext, StoryBlogActivity.class);
//                        i.putExtra("Story_Blog",Story_Blog);
//                        mContext.startActivity(i);*/
//
//                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Story_Blog));
//                            mContext.startActivity(browserIntent);
//                        }
//                    });
//                }
            }

          /*  if(Story_Type.equals("3")) {

            }else{
                fullimageView.setEnabled(false);
            }
*/

           /* btn_share1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    File file = saveBitMap(mContext, share_images);
                    if (file != null) {
                        Log.i("TAG", "Drawing saved to the gallery!");
                    } else {
                        Log.i("TAG", "Oops! Image could not be saved.");
                    }
                    shareImage();

                   // shareImage_fullimg();
                    // shareIt();
                 *//*   Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    for (int i=0; i<=position;i++) {
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Leader Story");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, LeadStoryList.listview_arr.get(position).getStory_Description());
                    }
                    mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));*//*
                }
            });*/

            //mWebView.stopLoading();
            //mWebView.reload();


        }
        else if(Story_Type.equals("4")){
            flag = 1;

     //       Toast.makeText(mContext,"Inside 4",Toast.LENGTH_LONG).show();

            fullimageView.setVisibility(View.GONE);
            share_images.setVisibility(View.GONE);
            share_half_image.setVisibility(View.VISIBLE);
            // share_half.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.GONE);
        //    videoView.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.VISIBLE);
            label.setVisibility(View.VISIBLE);
            txt_story.setVisibility(View.VISIBLE);

            String item = "http://www.youtube.com/embed/";

            String ss = LeadStoryList.listview_arr.get(position).getVideo_Story_URL(); //"5PUC9yGS4RI";
           // String ss = "5PUC9yGS4RI";
            // ss = ss.substring(ss.indexOf("v=") + 2);
            item += ss;

            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int w1 = (int) (metrics.widthPixels / metrics.density), h1 = w1 * 3 / 5;
            mWebView.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
            }
           // WebViewClient mWebViewClient = new WebViewClient();
           // mWebView.setWebViewClient(mWebViewClient);
          //  mWebView.setWebViewClient(WebView);//
            mWebView.setWebChromeClient(chromeClient);
            mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
            //  mWebView.getSettings().setPluginsEnabled(true);
            String frameVideo1 = "<html><body>Youtube video .. <br> <iframe id=\"ytplayer\" type=\"text/html\" width=\"640\" height=\"360\"\n" +
                    "            src=\"https://www.youtube.com/embed/M7lc1UVf-VE?autoplay=1&origin=http://example.com\"\n" +
                    "            frameborder=\"0\"></iframe></body></html>";

            String frameVideo = "<html><body>Youtube video .. <br> <iframe width=\"320\" height=\"315\" src=\"src=\"https://www.youtube.com/embed/videoseries?list=PLrjT1qW1v6ykZlUHxFkha1AXR9xlZW2ga\" ?autoplay=\"1\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        //    String video = "<html> <iframe width=\""+(w1-20)+"\" height=\""+h1+"\" src=\""+item+"\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
            String video = "<html><body> <iframe width=\""+(w1-0)+"\" height=\""+h1+"\" src=\""+item+"\" style=\"margin-left:-8px!important;margin-top:-8px;\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            try {
                Log.i("tag","item="+item);

                mWebView.loadData(video, "text/html", "utf-8");
               /* mWebView.loadData(
                        "<html><body><iframe class=\"youtube-player\" type=\"text/html5\" width=\""
                                + (w1 - 20)
                                + "\" height=\""
                                + h1
                                + "\" src=\""
                                + item
                                + "\" frameborder=\"0\"\"allowfullscreen\"></iframe></body></html>",
                        "text/html5", "utf-8");*/
            } catch (Exception e) {
                e.printStackTrace();
            }

            //mWebView.stopLoading();


            label.setText(LeadStoryList.listview_arr.get(position).getStory_Description());
            txt_story.setText(LeadStoryList.listview_arr.get(position).getStory_Title());
            /*String videoPath = "http://mis.leadcampus.org/Documents/Videos/1.mp4";
            Uri uri = Uri.parse(videoPath);
            videoView.setVideoURI(uri);
            videoView.start();
            MediaController mediaController = new MediaController(mContext);
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);

            label.setText(LeadStoryList.listview_arr.get(position).getStory_Description());
            txt_story.setText(LeadStoryList.listview_arr.get(position).getStory_Title());*/
        }
        else {

//            Toast.makeText(mContext,"Inside else",Toast.LENGTH_LONG).show();

            /*mWebView.clearFormData();
            mWebView.clearView();
            mWebView.destroy();*/

     /*       imageView.setVisibility(View.VISIBLE);
            btn_readMore.setVisibility(View.VISIBLE);
            label.setVisibility(View.VISIBLE);
            txt_story.setVisibility(View.VISIBLE);
            share_half.setVisibility(View.VISIBLE);
            share_half_image.setVisibility(View.VISIBLE);
            btn_share.setVisibility(View.VISIBLE);*/

     String Story_Type=LeadStoryList.listview_arr.get(position).getStory_Type();
            fullimageView.setVisibility(View.GONE);
            share_images.setVisibility(View.GONE);
         //   videoView.setVisibility(View.GONE);
            mWebView.setVisibility(View.GONE);
          //  btn_share1.setVisibility(View.GONE);
            //bottom_full.setVisibility(View.GONE);

         /*   if(Story_Type.equals("1"))
            {
                btn_readMore.setVisibility(View.GONE);
            }
            else{
                btn_readMore.setVisibility(View.VISIBLE);
            }*/
           // btn_share.setVisibility(View.VISIBLE);
            share_half_image.setVisibility(View.VISIBLE);
           // share_half.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            label.setVisibility(View.VISIBLE);
            txt_story.setVisibility(View.VISIBLE);


            imageView.setClickable(false);
            imageView.setFocusable(false);
            imageView.setEnabled(false);

            label.setClickable(false);
            label.setFocusable(false);
            label.setEnabled(false);
            fullimageView.setEnabled(false);
            txt_story.setFocusable(false);
            txt_story.setClickable(false);
            txt_story.setEnabled(false);

            if (position % 2 != 0)
            {
                label.setText(LeadStoryList.listview_arr.get(position).getStory_Description());
                String Image_Path=LeadStoryList.listview_arr.get(position).getImage_Path();
                txt_story.setText(LeadStoryList.listview_arr.get(position).getStory_Title());
                String Imagestring = Image_Path;
                if(Imagestring.equals("null")||Imagestring.equals("anyType{}") || Imagestring.equals("<empty>")){
                    // PMImgUrl="null";
                    imageView.setImageResource(R.drawable.n1);
                }else {
                    String arr[] = Imagestring.split("/", 2);

                    String firstWord = arr[0];   //the
                    String secondWord = arr[1];

                    PMImgUrl = serverPath + secondWord;

                    Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                    try
                    {
                        Glide.with(mContext)
                                .load(PMImgUrl)
                                .asBitmap()
                                .into(imageView);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                   /* LoadGalleryImage loadGalleryImage=new LoadGalleryImage();
                    loadGalleryImage.execute();*/
                }}
            else
                {
                txt_story.setText(LeadStoryList.listview_arr.get(position).getStory_Title());
                label.setText(LeadStoryList.listview_arr.get(position).getStory_Description());
                String Image_Path = LeadStoryList.listview_arr.get(position).getImage_Path();
                String Imagestring = Image_Path;
                if (Imagestring.equals("null") || Imagestring.equals("anyType{}") || Imagestring.equals("<empty>")){
                    // PMImgUrl="null";
                    imageView.setImageResource(R.drawable.n1);
                } else {
                    String arr[] = Imagestring.split("/", 2);

                    String firstWord = arr[0];   //the
                    String secondWord = arr[1];

                    PMImgUrl = serverPath + secondWord;

                    Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                    try
                    {
                        Glide.with(mContext)
                                .load(PMImgUrl)
                                .asBitmap()
                                .into(imageView);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                   /* LoadGalleryImage loadGalleryImage = new LoadGalleryImage();
                    loadGalleryImage.execute();*/
                }
            }



          /*  btn_share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    File file = saveBitMap(mContext, share_half_image);
                    if (file != null) {
                        Log.i("TAG", "Drawing saved to the gallery!");
                    } else {
                        Log.i("TAG", "Oops! Image could not be saved.");
                    }

                    shareImage();
                }
            });*/

           //mWebView.stopLoading();
           //mWebView.clearFormData();
           //mWebView.reload();

        }




      /* btn_readMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Story_Blog=LeadStoryList.listview_arr.get(position).getURL_Link();
                if(Story_Blog.equals("<empty>") || Story_Blog.equals("anyType{}") || Story_Blog.equals("null") || Story_Blog.equals("")){
                    Toast.makeText(mContext,"URL Not Found",Toast.LENGTH_LONG).show();
                }else {
                   *//* Intent i = new Intent(mContext, StoryBlogActivity.class);
                    i.putExtra("Story_Blog",Story_Blog);
                    mContext.startActivity(i);*//*

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Story_Blog));
                    mContext.startActivity(browserIntent);
                }
            }
        });*/



        Log.i("Tag","position="+position);

     //   count=LeadStoryList.listview_arr.size();
      //  Log.i("Tag","count="+count);

      //  for (i
        //
        //
        // nt j=0;j<count; j++){

      //  }
    /*    if (position % 2 == 0) {
            label.setText(LeadStoryList.listview_arr.get(0).getStory_Description());
            String Image_Path=LeadStoryList.listview_arr.get(0).getImage_Path();
            String Imagestring = Image_Path;
            if(Imagestring.equals("null")||Imagestring.equals("anyType{}")){
                // PMImgUrl="null";
                imageView.setImageResource(R.drawable.n1);
            }else {
                String arr[] = Imagestring.split("/", 2);

                String firstWord = arr[0];   //the
        *//*        String secondWord = arr[1];

                PMImgUrl = serverPath + secondWord;

                Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                LoadGalleryImage loadGalleryImage=new LoadGalleryImage(mContext);
                loadGalleryImage.execute();
            }
          //  imageView.setImageResource(R.drawable.story01);
           /* label.setText(mResources[0]);
            imageView.setImageResource(R.drawable.story01);*/
     /*   }
        else{
         /*   label.setText(mResources[1]);
            imageView.setImageResource(R.drawable.ironman);*/
    /*        label.setText(LeadStoryList.listview_arr.get(1).getStory_Description());
            String Image_Path=LeadStoryList.listview_arr.get(1).getImage_Path();
            String Imagestring = Image_Path;
            if(Imagestring.equals("null")||Imagestring.equals("anyType{}")){
                // PMImgUrl="null";
                imageView.setImageResource(R.drawable.n1);
            }else {
                String arr[] = Imagestring.split("/", 2);

                String firstWord = arr[0];   //the
                String secondWord = arr[1];

                PMImgUrl = serverPath + secondWord;

                Log.i("tag", "firstWord=" + firstWord + " secondWord=" + secondWord);
                LoadGalleryImage loadGalleryImage=new LoadGalleryImage(mContext);
                loadGalleryImage.execute();
            }
        }*/

        card_view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Story_Type = LeadStoryList.listview_arr.get(position).getStory_Type();
                Story_Blog = LeadStoryList.listview_arr.get(position).getURL_Link();
                String Story_Video = LeadStoryList.listview_arr.get(position).getVideo_Story_URL();
                   Log.i("tag","Story_Video="+Story_Video);
                if (share_images.getVisibility() == View.VISIBLE) {
                    // Its visible
                  /*  File file = saveBitMap(mContext, share_images);
                    if (file != null) {
                        Log.i("TAG", "Drawing saved to the gallery!");
                    } else {
                        Log.i("TAG", "Oops! Image could not be saved.");
                    }*/

                      BottomSheetFragment fragment = new BottomSheetFragment(mContext, share_images, Story_Blog, pictureFile, Story_Type);
                      fragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), TAG);

                } else if (share_half_image.getVisibility() == View.VISIBLE) {
                    // Either gone or invisible
                    /*File file = saveBitMap(mContext, share_half_image);
                    if (file != null) {
                        Log.i("TAG", "Drawing saved to the gallery!");
                    } else {
                        Log.i("TAG", "Oops! Image could not be saved.");
                    }*/
                    if(Story_Type.equals("4")) {
                        BottomSheetFragment fragment = new BottomSheetFragment(mContext, share_half_image, Story_Blog, pictureFile, Story_Type, Story_Video);
                        fragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), TAG);
                    }
                    else {
                        BottomSheetFragment fragment = new BottomSheetFragment(mContext, share_half_image, Story_Blog, pictureFile, Story_Type);
                        fragment.show(((FragmentActivity) mContext).getSupportFragmentManager(), TAG);
                    }
                }
                Log.i("TAG", "pictureFile===="+pictureFile);
              //  BottomSheetFragment fragment = new BottomSheetFragment(mContext,share_images,Story_Blog,pictureFile);


            }
        });
        container.addView(itemView);

        return itemView;
    }

    private WebChromeClient chromeClient = new WebChromeClient() {

        @Override
        public void onShowCustomView(View view, CustomViewCallback callback) {
            super.onShowCustomView(view, callback);
            if (view instanceof FrameLayout) {
                FrameLayout frame = (FrameLayout) view;
                if (frame.getFocusedChild() instanceof VideoView) {
                    VideoView video = (VideoView) frame.getFocusedChild();
                    frame.removeView(video);
                    video.start();
                }
            }

        }
    };
  /*  @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player,
                                        boolean wasRestored) {
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored) {
            //I assume the below String value is your video id
            player.cueVideo(VIDEO_ID);
        }

    }
    @Override
    public void onInitializationFailure (YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason){
        Toast.makeText(mContext, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };*/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private File saveBitMap(Context context, View drawView){
        pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"Logicchip");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
        pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        Log.i("tag","pictureFile="+pictureFile);
        return pictureFile;
    }

    private void shareImage() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");

        String imagePath= pictureFile.getAbsolutePath();
        File imageFileToShare = new File(imagePath);

        Uri uri = Uri.fromFile(imageFileToShare);
        share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Leader Story");
        share.putExtra(Intent.EXTRA_STREAM, uri);

        mContext.startActivity(Intent.createChooser(share, "Share Image!"));
       // File fdelete = new File(imagePath);
       // if (pictureFileDir.exists()) {

        //}
    }

    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap;
        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.M){
             returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        }else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP){
             returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        }else if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1){
            returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        }else {
            returnedBitmap = Bitmap.createBitmap(1080, 1500, Bitmap.Config.ARGB_8888);
        }
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }

    public static void deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                File child = new File(dir, children[i]);
                if (child.isDirectory()) {
                    deleteDirectory(child);
                    child.delete();
                } else {
                    child.delete();

                }
            }
            dir.delete();
        }
    }
    /*   public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                String text;
                int lineEndIndex;
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }
                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(
                        addClickablePartTextViewResizable( Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);
            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    tv.setLayoutParams(tv.getLayoutParams());
                    tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                    tv.invalidate();
                    if (viewMore) {
                        makeTextViewResizable(tv, -1, "View Less", false);
                    } else {
                        makeTextViewResizable(tv, 6, "View More", true);
                    }

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }*/
    public class LoadGalleryImage extends AsyncTask<Void, Object, Bitmap> {

        private Context context;
        //   private ProgressBar progressBar;

      /*  LoadGalleryImage (Context context){
            this.context = context;
        }*/

        @Override
        protected void onPreExecute() {
            //  progressBar = (ProgressBar) findViewById(R.id.progressBar);
            //  progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            ArrayList<Bitmap> bitmapLst=null;
            Bitmap bitmaplogo=null;

            try {
                URL url= new URL(PMImgUrl);
                Log.d("Urlssssssssssss",url.toString());
                bitmaplogo = BitmapFactory.decodeStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmaplogo;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // bitmapList.add(bitmap);
            imageView.setImageBitmap(bitmap);
            //  progressBar.setVisibility(GONE);
        }
    }

    public class LoadGalleryFullImage extends AsyncTask<Void, Object, Bitmap> {

        private Context cxt;
        //   private ProgressBar progressBar;

     /*   LoadGalleryFullImage (Context context){
            this.cxt = context;
        }

        public LoadGalleryFullImage() {

        }*/

        @Override
        protected void onPreExecute() {
            //  progressBar = (ProgressBar) findViewById(R.id.progressBar);
            //  progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            ArrayList<Bitmap> bitmapLst=null;
            Bitmap bitmaplogo=null;

            try {
                URL url= new URL(PMImgUrl);
                Log.d("Urlssssssssssss",url.toString());
                bitmaplogo = BitmapFactory.decodeStream(url.openStream());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmaplogo;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // bitmapList.add(bitmap);
            fullimageView.setImageBitmap(bitmap);
            //  progressBar.setVisibility(GONE);
        }
    }

    /*  private void shareIt() {
//sharing implementation here
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Leader Story");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,mResources[i]);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }*/

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
