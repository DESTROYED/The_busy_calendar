package com.example.destr.busy_calendar.constants;

public interface Constants {

    interface LoginConstants{
    String CONSUMER_KEY_VK = "5645451";
    String CONSUMER_KEY_FACEBOOK = "864214090351529";
    String CONSUMER_URL_VK = "https://oauth.vk.com/blank.html";
    String CONSUMER_URL_FACEBOOK = "https://graph.facebook.com/oauth/access_token";
}
    interface SqliteConstants{
        String SQL_TABLE_CREATE_TEMPLATE ="CREATE TABLE IF NOT EXISTS %s (%s);";
        String SQL_TABLE_CREATE_FIELD_TEMPLATE="%s %s";
    }
}
