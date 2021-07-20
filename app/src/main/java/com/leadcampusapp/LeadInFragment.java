package com.leadcampusapp;

/**
 * Created by Admin on 22-06-2018.
 */

import android.os.Bundle;
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

public class LeadInFragment extends Fragment {

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

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.leadin_fragment, container, false);
        WebView webView = (WebView) view.findViewById(R.id.webview);

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


        webView.loadUrl("https://www.instagram.com/leadcampus/");
        //openURL();

        return view;
    }

  /*  private void openURL() {

        //  webview1.setInitialScale(getScale());

        webview1.loadUrl("https://www.facebook.com/plugins/likebox.php?href=http%3A%2F%2Fwww.facebook.com%2Fdcselead&width=600&colorscheme=light&show_faces=true&border_color&stream=true&header=true&");

        webview1.requestFocus();
        // pspinner.setVisibility(View.GONE);
    }*/
}
