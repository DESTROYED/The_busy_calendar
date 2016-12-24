package com.example.destr.busy_calendar.json;

import android.util.Log;

import com.example.destr.busy_calendar.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VkJsonParseImage {
    public String getUrl() {
        return mUrl;
    }

    private final String mUrl;

    public VkJsonParseImage(JSONObject imageJsonObj)throws JSONException{
        Log.d("test1234567890", String.valueOf(imageJsonObj));
        JSONArray mResponse = imageJsonObj.getJSONArray(Constants.JsonParseConstants.RESPONSE);
        JSONObject mData=mResponse.getJSONObject(0);
        mUrl = mData.getString(Constants.JsonParseConstants.SRC);
    }
}

