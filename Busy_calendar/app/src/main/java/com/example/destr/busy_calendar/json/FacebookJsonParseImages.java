package com.example.destr.busy_calendar.json;


import com.example.destr.busy_calendar.constants.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookJsonParseImages {
    public String getUrl() {
        return mUrl;
    }

    private final String mUrl;

    public FacebookJsonParseImages(JSONObject imageJsonObj)throws JSONException{
            JSONObject mPicture = imageJsonObj.getJSONObject(Constants.JsonParseConstants.PICTURE);
            JSONObject mData=mPicture.getJSONObject(Constants.JsonParseConstants.DATA);
            mUrl = mData.getString(Constants.JsonParseConstants.URL);
        }
}
