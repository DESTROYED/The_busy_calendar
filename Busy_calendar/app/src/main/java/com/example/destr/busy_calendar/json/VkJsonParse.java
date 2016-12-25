package com.example.destr.busy_calendar.json;

import android.util.Log;

import com.example.destr.busy_calendar.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VkJsonParse {


    public String parseImage(JSONObject imageJsonObj)throws JSONException{
        JSONArray mResponse = imageJsonObj.getJSONArray(Constants.JsonParseConstants.RESPONSE);
        JSONObject mData=mResponse.getJSONObject(0);
        return mData.getString(Constants.JsonParseConstants.SRC);
    }
}

