package com.example.destr.busy_calendar.json;


import android.util.Log;

import com.example.destr.busy_calendar.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookJsonParse {

    public String FacebookImageParse(JSONObject imageJsonObj)throws JSONException{
            JSONObject mPicture = imageJsonObj.getJSONObject(Constants.JsonParseConstants.PICTURE);
            JSONObject mData=mPicture.getJSONObject(Constants.JsonParseConstants.DATA);
            return mData.getString(Constants.JsonParseConstants.URL);
        }
    public String parseId(JSONObject pJSONObject)throws JSONException{
        Log.d("test1234567890", String.valueOf(pJSONObject));
        return "DONE";
    }
}
