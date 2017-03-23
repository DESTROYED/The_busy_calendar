package com.example.destr.busy_calendar.constants;

public interface Constants {

    interface LoginConstants {

        String CONSUMER_KEY_VK = "5645451";
        String CONSUMER_KEY_FACEBOOK = "864214090351529";
        String CONSUMER_URL_VK = "https://oauth.vk.com/blank.html";
        String CONSUMER_URL_FACEBOOK = "https://graph.facebook.com/oauth/access_token";
    }

    interface DBConstants {
        String CREATE_TABLE = "create table events (";
        String ID_FIELD = "id integer primary key autoincrement,";
        String EVENTNAME_FIELD = "eventname text,";
        String DATE_FIELD = "date text,";
        String START_TIME_FIELD = "sttime text,";
        String END_TIME_FIELD = "endtime text,";
        String ALARM_NAME_FIELD = "alarmname text,";
        String STATUS_FIELD = "status text,";
        String DESCRIPTION_FIELD = "description text,";
        String VK_INTEGER_FIELD = "vk integer,";
        String FACEBOOK_INTEGER_FIELD = "facebook integer";
        String ITEM_ID_FIELD="item_id integer, ";
        String DATABASE_END = ");";
        String DB_NAME = "BusyCalendar";
        String EVENTNAME = "eventname";
        String DATE = "date";
        String START_TIME = "sttime";
        String END_TIME = "endtime";
        String ALARM_NAME = "alarmname";
        String STATUS = "status";
        String DESCRIPTION = "description";
        String VK_INTEGER = "vk";
        String FACEBOOK_INTEGER = "facebook";
        String TABLE_NAME = "events";
        String ITEM_ID="item_id";
    }

    interface JsonParseConstants {

        String PICTURE = "picture";
        String DATA = "data";
        String URL = "url";
        String RESPONSE = "response";
        String SRC = "photo_50";
        String FACEBOOK_MAX_PHOTO = "800";
    }

    interface TokenJob {

        String VK_PATTERN = "vk.com";
        String FACEBOOK_PATTERN = "facebook.com";
        String MATCHER_TOKEN_PATTERN = "access_token=";
        String TOKEN_PARSE_PATTERN = "access_token=(.*?)&";
        String FACEBOOK_TOKEN = "facebook_token";
        String VK_TOKEN = "vk_token";
    }

    interface UrlConstants {

        String JSON_PARSE_VK = "https://api.vk.com/method/users.get?fields=photo_50&rev=1&access_token=%s&v=V";
        String JSON_PARSE_FACEBOOK = "https://graph.facebook.com/me?fields=picture.width(%s).height(%s)&access_token=%s";
        String FACEBOOK_POST_SET = "https://graph.facebook.com/me/feed?message=test1&access_token=%s";
        String VK_WEBVIEW = "https://oauth.vk.com/authorize?client_id=%s&response_type=token&scope=status&redirect_uri=%s";
        String FACEBOOK_WEBVIEW = "https://www.facebook.com/v2.8/dialog/oauth?client_id=%s&display=popup&scope=publish_actions&response_type=token&redirect_uri=%s";
        String VK_STATUS_SET="https://api.vk.com/method/status.set?text=%s&access_token=%s&v=V";

    }

    interface OtherConstants {
        String STRING_ARRAY_EXTRA = "start_cursor_extra";
        String CURSOR_EXTRA="cursor_extra";
        String ONE_TIME="onetime";
        String GET_METHOD="GET";
        String POST_METHOD="POST";
        String TIMEPICKER_NAME = "timePicker";
        String NULL_STRING = "";
        String COLON = ":";
        String SPACE = " ";
        String LAST_ID="last_id";
        String NOTIFICATION_ID="notification_id";
    }

    interface GridCellAdapterConstants {

        String GRAY_COLOR = "GRAY";
        String WHITE_COLOR = "WHITE";
        String MINUS = "-";
    }
    interface Settings{
        String RINGER_SILENT_MOD="ringer_silent_mod";
        String SYSTEM_SOUNDS="system_sounds";
        String NOTIFICATIONS="notifications";
        String ALARM_SOUND="alarm_sound";
        String PHONE_RING="phone_ring";
        String MUSICK_SOUND="music_sound";
    }
}
