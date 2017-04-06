package com.example.destr.busy_calendar.socials;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.destr.busy_calendar.json.FacebookJsonParse;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookSdkHelper {
    Context context;
    public  FacebookSdkHelper(Context context){
        this.context = context;
    }
    public void setPhoto(final ImageView imageView, final Button button){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me?fields=picture.width(500).height(500)",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        try {
                            Log.d("LOG onComplete", String.valueOf(AccessToken.getCurrentAccessToken()));
                            String url=new FacebookJsonParse().FacebookImageParse(response.getJSONObject());
                            imageView.setVisibility(View.VISIBLE);
                            button.setVisibility(View.GONE);
                            Glide.with(context).load(url).into(imageView);
                        } catch (JSONException e) {
                            Log.d("FacebookSdkHelper",e.getMessage());
                        }
                    }
                }
        ).executeAsync();
    }
    public void notificationBlock(){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me?fields=notifications.limit(0)",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                    }
                }
        ).executeAsync();
    }
    public void notificationReturn(){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me?fields=notifications.limit(10)",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                    }
                }
        ).executeAsync();
    }
    public void setStatus(String status){
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "me/feed?message="+status,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                    }
                }
        ).executeAsync();
    }

    }
