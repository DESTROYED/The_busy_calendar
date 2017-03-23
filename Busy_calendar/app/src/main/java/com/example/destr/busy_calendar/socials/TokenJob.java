package com.example.destr.busy_calendar.socials;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.loaders.ImageLoader;
import com.example.destr.busy_calendar.json.FacebookJsonParse;
import com.example.destr.busy_calendar.json.JsonFromUrl;
import com.example.destr.busy_calendar.json.VkJsonParse;

import org.json.JSONException;

public class TokenJob {
    private final VkJsonParse vkJsonParse = new VkJsonParse();
    private final FacebookJsonParse mFacebookJsonParse = new FacebookJsonParse();

    public TokenJob(Context mContext, Button facebookButton, Button vkButton, ImageView vkImage, ImageView facebookImage) {
        JsonFromUrl jsonFromUrl = new JsonFromUrl();

        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(mContext);
        String vkToken = logTest.getString(Constants.TokenJob.VK_TOKEN, Constants.OtherConstants.NULL_STRING);
        String facebookToken = logTest.getString(Constants.TokenJob.FACEBOOK_TOKEN, Constants.OtherConstants.NULL_STRING);
        if (!vkToken.isEmpty()) {
            vkImage.setVisibility(View.VISIBLE);

            TokenJob.this.getVkInfo(jsonFromUrl, vkToken, vkButton, vkImage);

        }
        if (!facebookToken.isEmpty()) {
            facebookImage.setVisibility(View.VISIBLE);

            TokenJob.this.getFacebookInfo(jsonFromUrl, facebookToken, facebookButton, facebookImage);

        }
    }
    private void getVkInfo(final JsonFromUrl jsonFromUrl, final String token, Button pButton, final ImageView pImageView) {
        pButton.setVisibility(View.GONE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final String src = vkJsonParse.parseImage((jsonFromUrl.getJSONFromUrl(String.format(Constants.UrlConstants.JSON_PARSE_VK,token))));
                    Log.d("Images Vk", String.valueOf(jsonFromUrl.getJSONFromUrl(String.format(Constants.UrlConstants.JSON_PARSE_VK,token))));
                    pImageView.post(new Runnable() {

                        @Override
                        public void run() {
                            pImageView.setVisibility(View.VISIBLE);

                            ImageLoader imageLoader = new ImageLoader();
                            imageLoader.loadBitmap(src,pImageView);
                        }
                    });
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }

            }
        }).start();

    }

    private void getFacebookInfo(final JsonFromUrl pJsonFromUrl, final String token, final Button pButton, final ImageView pImageView) {

        pButton.setVisibility(View.GONE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final String src = mFacebookJsonParse.FacebookImageParse(pJsonFromUrl.getJSONFromUrl(String.format(Constants.UrlConstants.JSON_PARSE_FACEBOOK,Constants.JsonParseConstants.FACEBOOK_MAX_PHOTO,Constants.JsonParseConstants.FACEBOOK_MAX_PHOTO,token)));
                    pImageView.post(new Runnable() {

                        @Override
                        public void run() {
                            ImageLoader imageLoader = new ImageLoader();
                            imageLoader.loadBitmap(src,pImageView);
                        }
                    });
                } catch (JSONException pE) {
                    pE.printStackTrace();
                }

            }
        }).start();

    }

}
