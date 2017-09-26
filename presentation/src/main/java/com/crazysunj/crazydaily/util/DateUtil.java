package com.crazysunj.crazydaily.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * author: sunjian
 * created on: 2017/9/20 下午6:23
 * description:
 */

public class DateUtil {
    private DateUtil() {
    }

    public static String getLocalTime(String time) {

        if (TextUtils.isEmpty(time)) {
            return "";
        }

        SimpleDateFormat utc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utc.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return local.format(utc.parse(time));
        } catch (ParseException e) {
            return time.replaceAll("(T|\\..*?Z)", " ");
        }
    }
}
