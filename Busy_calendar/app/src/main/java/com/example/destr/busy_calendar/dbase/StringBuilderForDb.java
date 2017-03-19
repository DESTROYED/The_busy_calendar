package com.example.destr.busy_calendar.dbase;

import com.example.destr.busy_calendar.constants.Constants;

class StringBuilderForDb {

    private StringBuilder mStringBuilder = new StringBuilder();

    String buildRequest() {
        mStringBuilder.append(Constants.DBConstants.CREATE_TABLE)
                .append(Constants.DBConstants.ID_FIELD)
                .append(Constants.DBConstants.ITEM_ID_FIELD)
                .append(Constants.DBConstants.EVENTNAME_FIELD)
                .append(Constants.DBConstants.DATE_FIELD)
                .append(Constants.DBConstants.START_TIME_FIELD)
                .append(Constants.DBConstants.END_TIME_FIELD)
                .append(Constants.DBConstants.ALARM_NAME_FIELD)
                .append(Constants.DBConstants.STATUS_FIELD)
                .append(Constants.DBConstants.DESCRIPTION_FIELD)
                .append(Constants.DBConstants.VK_INTEGER_FIELD)
                .append(Constants.DBConstants.FACEBOOK_INTEGER_FIELD)
                .append(Constants.DBConstants.DATABASE_END);
        return String.valueOf(mStringBuilder);
    }

}
