package com.example.destr.busy_calendar.socialsJob;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.destr.busy_calendar.constants.Constants;
import com.example.destr.busy_calendar.imageLoad.ImageLoader;
import com.example.destr.busy_calendar.json.FacebookJsonParseImages;
import com.example.destr.busy_calendar.json.JsonFromUrl;
import com.example.destr.busy_calendar.json.VkJsonParseImage;

import org.json.JSONException;

public class TokenJob {
    public TokenJob(Context mContext, Button facebookButton, Button vkButton, ImageView vkImage, ImageView facebookImage) {
        JsonFromUrl jsonFromUrl = new JsonFromUrl();
        final SharedPreferences logTest = PreferenceManager.getDefaultSharedPreferences(mContext);
        String vkToken = logTest.getString(Constants.TokenJob.VK_TOKEN, Constants.OtherConstants.NULL_STRING);
        String facebookToken = logTest.getString(Constants.TokenJob.FACEBOOK_TOKEN, Constants.OtherConstants.NULL_STRING);
        String height= Constants.JsonParseConstants.FACEBOOK_MAX_PHOTO;
        String width= Constants.JsonParseConstants.FACEBOOK_MAX_PHOTO;
        if (!vkToken.isEmpty()) {

            TokenJob.this.getVkInfo(jsonFromUrl, vkToken, vkButton, vkImage);

        }
        if (!facebookToken.isEmpty()) {

            TokenJob.this.getFacebookInfo(jsonFromUrl, facebookToken, facebookButton, facebookImage, width ,height);

        }
    }
    private void getVkInfo(final JsonFromUrl jsonFromUrl, final String token, Button pButton, final ImageView pImageView) {

        pButton.setVisibility(View.GONE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final String src = new VkJsonParseImage((jsonFromUrl.getJSONFromUrl(String.format(Constants.UrlConstants.JSON_PARSE_VK,token)))).getUrl();
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

    private void getFacebookInfo(final JsonFromUrl pJsonFromUrl, final String token, final Button pButton, final ImageView pImageView,final String width,final String height) {

        pButton.setVisibility(View.GONE);
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    final String src = new FacebookJsonParseImages(pJsonFromUrl.getJSONFromUrl(String.format(Constants.UrlConstants.JSON_PARSE_FACEBOOK,Constants.JsonParseConstants.FACEBOOK_MAX_PHOTO,Constants.JsonParseConstants.FACEBOOK_MAX_PHOTO,token))).getUrl();
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
