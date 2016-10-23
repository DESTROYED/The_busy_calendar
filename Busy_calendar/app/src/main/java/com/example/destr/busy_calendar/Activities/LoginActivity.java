package com.example.destr.busy_calendar.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.example.destr.busy_calendar.Constants.ConstantsVkAndFacebook;
import com.example.destr.busy_calendar.Parse.TokenParse;
import com.example.destr.busy_calendar.R;

public class LoginActivity extends AppCompatActivity {
    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences logTest= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        final SharedPreferences.Editor logTestEditor=logTest.edit();
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        mWebView = (WebView) findViewById(R.id.lalka);
        final TokenParse mTokenParse = new TokenParse();
        final View vk_loginButton = findViewById(R.id.btn_login_vk);
        final View facebook_loginButton = findViewById(R.id.btn_login_facebook);
        final String[] urlToParseToken = new String[10];

        if(!logTest.getString("facebook_token","").isEmpty()){
            facebook_loginButton.setVisibility(View.GONE);
        }
        else {
            facebook_loginButton.setVisibility(View.VISIBLE);
        }
        if(!logTest.getString("vk_token","").isEmpty()){
            vk_loginButton.setVisibility(View.GONE);
        }
        else {
            vk_loginButton.setVisibility(View.VISIBLE);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                urlToParseToken[0] = view.getUrl();
                if (mTokenParse.MatcherLinks(urlToParseToken[0])[0]) {
                    if (mTokenParse.MatcherTockens(urlToParseToken[0])) {
                        mWebView.setVisibility(View.GONE);
                        logTestEditor.putString("vk_token",mTokenParse.TockenParse(urlToParseToken[0]));
                        logTestEditor.apply();
                        vk_loginButton.setVisibility(View.GONE);
                    } else mWebView.setVisibility(View.VISIBLE);

                } else if (mTokenParse.MatcherLinks(urlToParseToken[0])[1]) {
                    if (mTokenParse.MatcherTockens(urlToParseToken[0])) {
                        mWebView.setVisibility(View.GONE);
                        facebook_loginButton.setVisibility(View.GONE);
                        logTestEditor.putString("facebook_token",mTokenParse.TockenParse(urlToParseToken[0]));
                        logTestEditor.apply();
                    } else mWebView.setVisibility(View.VISIBLE);
                }
            }
        });
        vk_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("https://oauth.vk.com/authorize?client_id=" + ConstantsVkAndFacebook.CONSUMER_KEY_VK + "&response_type=token&redirect_uri="+ConstantsVkAndFacebook.CONSUMER_URL_VK);
            }
        });
        facebook_loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("https://www.facebook.com/v2.8/dialog/oauth?client_id="+ConstantsVkAndFacebook.CONSUMER_KEY_FACEBOOK+"&display=popup&response_type=token&redirect_uri="+ConstantsVkAndFacebook.CONSUMER_URL_FACEBOOK);
            }
        });
        //TODO if windows of login ad password opens, check code to send there my login and password
    }
}
