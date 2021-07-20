package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//import com.android.sripad.leadnew_22_6_2018.R;

public class LeadTWFragment extends Fragment {
    WebView webview1;
    WebView webView;
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

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.leadtw_fragment, container, false);
        webView = (WebView) view.findViewById(R.id.webview_tw);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        webView.setOnKeyListener(new View.OnKeyListener() {
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





        //webView.loadUrl("https://www.twitter.com/dcseLEAD");
        webView.loadUrl("https://mobile.twitter.com/dcseLEAD");


      /*  WebSettings wv = webView.getSettings();
        wv.setJavaScriptEnabled(true);
        wv.setLoadsImagesAutomatically(true);
        wv.setPluginState(WebSettings.PluginState.ON);
        wv.setAllowUniversalAccessFromFileURLs(true);
    //    wv.setPluginsEnabled(true);
        wv.setEnableSmoothTransition(true);
        wv.l("https://twitter.com/dcseLEAD");*/
        return view;
    }
   /* @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {

    }*/
}
