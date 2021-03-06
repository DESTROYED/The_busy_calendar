package com.example.destr.busy_calendar.json;

import android.util.Log;

import com.example.destr.busy_calendar.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookJsonParse {

    public String FacebookImageParse(JSONObject imageJsonObj)throws JSONException{
            Log.d("JsonParse", String.valueOf(imageJsonObj));
            JSONObject mPicture = imageJsonObj.getJSONObject(Constants.JsonParseConstants.PICTURE);
            JSONObject mData=mPicture.getJSONObject(Constants.JsonParseConstants.DATA);
            return mData.getString(Constants.JsonParseConstants.URL);
        }

}
