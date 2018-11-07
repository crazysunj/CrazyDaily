/*
  Copyright 2017 Sun Jian
  <p>
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  <p>
  http://www.apache.org/licenses/LICENSE-2.0
  <p>
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */
package com.crazysunj.crazydaily.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author: sunjian
 * created on: 2017/9/20 下午6:23
 * description: https://github.com/crazysunj/CrazyDaily
 */
public class DateUtil {
    public static final String PATTERN_ONE = "yyyy年MM月dd日";

    private DateUtil() {
    }

    @SuppressLint("SimpleDateFormat")
    public static String formatDate(long millis, String pattern) {
        Date date = new Date(millis);
        final SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    @SuppressLint("SimpleDateFormat")
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
