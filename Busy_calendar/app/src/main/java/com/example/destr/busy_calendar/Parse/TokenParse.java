package com.example.destr.busy_calendar.parse;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenParse {

    public String TockenParse(String needToParse) {
        Pattern p = Pattern.compile("access_token=(.*?)&");
        Matcher parsed = p.matcher(needToParse);
        if (parsed.find()) {
            return parsed.group(1);
        } else {
            return "Not found!";
        }
    }

    public Boolean[] MatcherLinks(String needToFindMatches) {
        Boolean[] matches = new Boolean[2];
        matches[0] = false;
        matches[1] = false;
        Pattern vk = Pattern.compile("vk.com");
        Pattern facebook = Pattern.compile("facebook.com");
        Matcher checkVk = vk.matcher(needToFindMatches);
        Matcher checkFacebook = facebook.matcher(needToFindMatches);
        if (checkVk.find()) {
            matches[0] = true;
        } else if (checkFacebook.find()) {
            matches[1] = true;
        }
        return matches;
    }

    public Boolean MatcherTockens(@NonNull String needToFindMatches) {
        Pattern pattern = Pattern.compile("access_token=");
        Matcher check = pattern.matcher(needToFindMatches);
        return check.find();
    }
}
