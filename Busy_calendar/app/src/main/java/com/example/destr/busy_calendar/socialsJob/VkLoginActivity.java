package com.example.destr.busy_calendar.socialsJob;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.destr.busy_calendar.R;
import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.parse.TokenParse;

public class VkLoginActivity extends Activity {

    WebView mWebView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor logTestEditor = logTest.edit();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * (0.8)), (int) (height * 0.8));
        mWebView = (WebView) findViewById(R.id.new_web);
        final TokenParse mTokenParse = new TokenParse();
        final String[] urlToParseToken = new String[10];
        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                urlToParseToken[0] = view.getUrl();
                if (mTokenParse.MatcherTockens(urlToParseToken[0])) {
                    mWebView.setVisibility(View.GONE);
                    logTestEditor.putString("vk_token", mTokenParse.TockenParse(urlToParseToken[0]));
                    logTestEditor.apply();
                } else {
                    mWebView.setVisibility(View.VISIBLE);
                }
            }
        });
        mWebView.loadUrl("https://oauth.vk.com/authorize?client_id=" + Constants.LoginConstants.CONSUMER_KEY_VK + "&response_type=token&redirect_uri=" + Constants.LoginConstants.CONSUMER_URL_VK);
    }
}
