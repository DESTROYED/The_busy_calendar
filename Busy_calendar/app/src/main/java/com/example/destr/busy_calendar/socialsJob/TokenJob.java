package com.example.destr.busy_calendar.socialsJob;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.destr.busy_calendar.http.HttpRequest;
import com.example.destr.busy_calendar.imageLoad.ImageLoader;
import com.example.destr.busy_calendar.json.FacebookJsonParseImages;
import com.example.destr.busy_calendar.json.JsonFromUrl;

import org.json.JSONException;
import org.json.JSONObject;

public class TokenJob{

    public TokenJob(Context mContext, Button facebookButton,Button vkButton,ImageView vkImage,ImageView facebookImage) {
        JsonFromUrl jsonFromUrl = new JsonFromUrl();
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(mContext);
        String vkToken= logTest.getString("vk_token", "");
        String facebookToken= logTest.getString("facebook_token", "");

        if (!vkToken.isEmpty()) {

            TokenJob.this.getVkInfo(jsonFromUrl,vkToken, vkButton,vkImage);

        }
        if (!facebookToken.isEmpty()) {

            TokenJob.this.getFacebookInfo(jsonFromUrl, facebookToken, facebookButton, facebookImage);

        }
    }


    private void getVkInfo (JsonFromUrl jsonFromUrl, String token, Button pButton, ImageView pImageView){

        jsonFromUrl.getJSONFromUrl(token);
            pButton.setVisibility(View.GONE);

    }

    private void getFacebookInfo (final JsonFromUrl pJsonFromUrl, final String token, final Button pButton, final ImageView pImageView){

            pButton.setVisibility(View.GONE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String src = new FacebookJsonParseImages(pJsonFromUrl.getJSONFromUrl("https://graph.facebook.com/me?fields=picture.width(800).height(800)&access_token="+token)).getUrl();
                        pImageView.post(new Runnable() {

                            @Override
                            public void run() {
                                ImageLoader imageLoader = new ImageLoader(pImageView);
                                imageLoader.execute(src);
                            }
                        });
                    } catch (JSONException pE) {
                        pE.printStackTrace();
                    }

                }
            }).start();


    }


}
