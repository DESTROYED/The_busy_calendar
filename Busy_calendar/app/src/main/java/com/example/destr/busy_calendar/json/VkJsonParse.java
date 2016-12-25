package com.example.destr.busy_calendar.json;

import android.util.Log;

import com.example.destr.busy_calendar.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VkJsonParse {


    public String parseImage(JSONObject imageJsonObj)throws JSONException{
        Log.d("test1234567890", String.valueOf(imageJsonObj));
        JSONArray mResponse = imageJsonObj.getJSONArray(Constants.JsonParseConstants.RESPONSE);
        JSONObject mData=mResponse.getJSONObject(0);
        return mData.getString(Constants.JsonParseConstants.SRC);
    }
    public String parseResponse(JSONObject pJSONObject)throws JSONException{
        Log.d("test1234567890", String.valueOf(pJSONObject));
        return pJSONObject.getString("response");
    }
}

