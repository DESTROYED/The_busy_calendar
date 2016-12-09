package com.example.destr.busy_calendar.json;


import org.json.JSONException;
import org.json.JSONObject;

public class FacebookJsonParseImages {
    public String getUrl() {
        return mUrl;
    }

    private final String mUrl;

    public FacebookJsonParseImages(JSONObject imageJsonObj)throws JSONException{
            JSONObject mPicture = imageJsonObj.getJSONObject("picture");
            JSONObject mData=mPicture.getJSONObject("data");
            mUrl = mData.getString("url");
        }
}
