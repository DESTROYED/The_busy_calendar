package com.example.destr.busy_calendar.ui.popups;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.socials.TokenParse;

public class LoginPopup extends Activity {

    WebView mWebView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor logTestEditor = logTest.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        String webViewUrl = null;
        String tokenPlace;
        if(intent.getExtras().getString("Social").equals("vk")){
            webViewUrl=String.format(Constants.UrlConstants.VK_WEBVIEW,Constants.LoginConstants.CONSUMER_KEY_VK,Constants.LoginConstants.CONSUMER_URL_VK);
            tokenPlace = Constants.TokenJob.VK_TOKEN;

        }
        else if(intent.getStringExtra("Social").equals("facebook")){
            webViewUrl=String.format(Constants.UrlConstants.FACEBOOK_WEBVIEW,Constants.LoginConstants.CONSUMER_KEY_FACEBOOK,Constants.LoginConstants.CONSUMER_URL_FACEBOOK);
            tokenPlace = Constants.TokenJob.FACEBOOK_TOKEN;

        }
        else {
            tokenPlace=null;
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * (0.8)), (int) (height * 0.8));
        mWebView = (WebView) findViewById(R.id.new_web);
        final TokenParse mTokenParse = new TokenParse();
        final String[] urlToParseToken = new String[10];
        final String finalTokenPlace = tokenPlace;
        mWebView.setWebViewClient(new WebViewClient() {

            // we have no better choice
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                urlToParseToken[0] = view.getUrl();
                if (mTokenParse.MatcherTockens(urlToParseToken[0])) {
                    mWebView.setVisibility(View.GONE);
                    logTestEditor.putString(finalTokenPlace, mTokenParse.TockenParse(urlToParseToken[0]));
                    logTestEditor.apply();
                } else {
                    mWebView.setVisibility(View.VISIBLE);
                }
            }
        });
        mWebView.loadUrl(webViewUrl);
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
    }
}

