package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import com.android.sripad.leadnew_22_6_2018.R;

public class LeadFBFragment extends Fragment {
    WebView webview1;
    private class MyWebViewClient extends WebViewClient {
        //ProgressDialog pd = new ProgressDialog(LeadFBFragment.this);
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);

//            pd.setMessage("Please wait Loading...");
            //          pd.show();

            return true;
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("on finish");
            //        if (pd.isShowing()) {
            //          pd.dismiss();
            //    }
       /*     if (view.canGoBack()) {
                view.goBack();
            }
*/
        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.leadfb_fragment, container, false);
        webview1=(WebView)view.findViewById(R.id.webview1);

        //     pspinner.setVisibility(View.VISIBLE);
        webview1.setWebViewClient(new MyWebViewClient());


        webview1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    WebView webView = (WebView) v;

                    switch(keyCode)
                    {
                        case KeyEvent.KEYCODE_BACK:
                            if(webView.canGoBack())
                            {
                                Log.d("Inside","goBacksssssss");
                                webView.goBack();
                                return true;
                            }
                            break;
                    }
                }
                return false;
            }
        });












       /*     webview1.setInitialScale(30);
        webview1.getSettings().setJavaScriptEnabled(true);
        webview1.getSettings().setLoadWithOverviewMode(true);
        webview1.getSettings().setUseWideViewPort(true);
        webview1.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview1.setScrollbarFadingEnabled(false);*/

        /*if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            Toast.makeText(getContext(), "Large screen", Toast.LENGTH_LONG).show();
            webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=460&height=750&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");

        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            Toast.makeText(getContext(), "Normal sized screen", Toast.LENGTH_LONG).show();
            webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=350&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
          //  webview1.loadUrl("https://m.facebook.com/www.leadcampus.org/");
        }
        else if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            Toast.makeText(getContext(), "Small sized screen", Toast.LENGTH_LONG).show();
            webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=300&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
        }
        else {
            Toast.makeText(getContext(), "Screen size is neither large, normal or small", Toast.LENGTH_LONG).show();
            webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=250&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");

        }*/

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;

        if (density == DisplayMetrics.DENSITY_HIGH) {

        //    Toast.makeText(getContext(), "DENSITY_HIGH... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
            if(density>=230 || density<=250) {
                Log.e("tag","screen DENSITY_HIGH 230 to 250=="+String.valueOf(density));
                webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=460&height=750&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
            }
            else if(density>=250){
                Log.e("tag","screen DENSITY_HIGH >=250=="+String.valueOf(density));
                webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=480&height=750&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
            }
            else if(density<230){
                Log.e("tag","screen DENSITY_HIGH <230=="+String.valueOf(density));
                webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=440&height=750&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
            }
        }
        else if (density == DisplayMetrics.DENSITY_MEDIUM) {
            Log.e("tag","screen DENSITY_MEDIUM=="+String.valueOf(density));
       //     Toast.makeText(getContext(), "DENSITY_MEDIUM... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
            webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=400&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
        }
        else if (density == DisplayMetrics.DENSITY_LOW) {
            Log.e("tag","screen DENSITY_LOW=="+String.valueOf(density));
        //    Toast.makeText(getContext(), "DENSITY_LOW... Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
            webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=320&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
        }
        else if(density == DisplayMetrics.DENSITY_420){
            Log.e("tag","screen density420=="+String.valueOf(density));
        //    Toast.makeText(getContext(), "Density is neither HIGH, MEDIUM OR LOW.  Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
            webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=410&height=650&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
        }
        else if(density == DisplayMetrics.DENSITY_280){
            Log.e("tag","screen density420=="+String.valueOf(density));
            //    Toast.makeText(getContext(), "Density is neither HIGH, MEDIUM OR LOW.  Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
            webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=410&height=650&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
        }

        else {
       //     Toast.makeText(getContext(), "Density is neither HIGH, MEDIUM OR LOW.  Density is " + String.valueOf(density), Toast.LENGTH_LONG).show();
            if(density>=320 && density<=400) {
                Log.e("tag","screen 320 to 400=="+String.valueOf(density));
                webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=360&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
            }else if(density>=401 && density<=440){
                Log.e("tag","screen 401 to 440=="+String.valueOf(density));
                webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=390&height=650&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
            }else if(density>=441 && density<=500){
                Log.e("tag","screen 441 to 500=="+String.valueOf(density));
                webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=360&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
            }
            else {
                Log.e("tag","screen else=="+String.valueOf(density));
                webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=350&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
            }

        }

       //  webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2Fwww.leadcampus.org%2F&tabs=timeline&width=350&height=500&small_header=false&adapt_container_width=true&hide_cover=false&show_facepile=true&appId");      // webview1.loadData("https://www.facebook.com/plugins/post.php?href=https%3A%2F%2Fwww.facebook.com%2Fwww.leadcampus.org%2F&width=500&show_text=true&height=202&appId");
   //     webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=https%3A%2F%2Fwww.facebook.com%2Fwww.leadcampus.org%2F&tabs=timeline&width=500&height=500&small_header=false&adapt_container_width=true&hide_cover=false&show_facepile=true&appId");
        webview1.requestFocus();
        //openURL();

        return view;
    }

    private void openURL() {

        //  webview1.setInitialScale(getScale());

        webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=600&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");
        webview1.requestFocus();
        // pspinner.setVisibility(View.GONE);
    }
}
