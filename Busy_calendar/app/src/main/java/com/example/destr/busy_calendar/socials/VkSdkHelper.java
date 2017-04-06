package com.example.destr.busy_calendar.socials;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VkSdkHelper {
    Context context;
    public VkSdkHelper(Context context){
        this.context = context;
    }
    public void setPhoto(final ImageView imageView, final Button button){
        VKParameters params = new VKParameters();
        params.put(VKApiConst.FIELDS, "photo_max_orig");

        VKRequest request = new VKRequest("users.get",params);
        request.executeWithListener(new VKRequest.VKRequestListener() {

            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                JSONArray resp = null;
                try {
                    resp = response.json.getJSONArray("response");
                    JSONObject user = resp.getJSONObject(0);
                    String photo_max_orig_url = user.getString("photo_max_orig");
                    imageView.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                    Glide.with(context).load(photo_max_orig_url).into(imageView);
                } catch (JSONException e) {
                    Log.d("VkSdkHelper", String.valueOf(e));
                }
                }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }
    public void notificationBlock(){
        VKParameters params = new VKParameters();
        params.put("time","-1");
        VKRequest request = new VKRequest("account.setSilenceMode",params);
        request.executeWithListener(new VKRequest.VKRequestListener() {

            /*@Override
            public void onError(VKError error) {
                Log.d("Block", error.errorMessage);
            }*/
        });
    }
    public void notificationReturn(){
        VKParameters params = new VKParameters();
        params.put("time","0");
        VKRequest request = new VKRequest("account.setSilenceMode",params);
        request.executeWithListener(new VKRequest.VKRequestListener() {

            /*@Override
            public void onError(VKError error) {
                Log.d("RETURN NOTIFICATION",error.errorMessage);
            }*/
        });
    }
    public void setStatus(String status){
        VKParameters params = new VKParameters();
        params.put("text", status);

        VKRequest request = new VKRequest("status.set",params);
        request.executeWithListener(new VKRequest.VKRequestListener() {


            /*@Override
            public void onError(VKError error) {
                Log.d("Status",error.errorMessage);
            }*/
        });
    }
}
